/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.hadoop.hive.ql.optimizer;

import org.apache.hadoop.hive.ql.QueryProperties;
import org.apache.hadoop.hive.ql.exec.FetchTask;
import org.apache.hadoop.hive.ql.exec.FileSinkOperator;
import org.apache.hadoop.hive.ql.exec.FilterOperator;
import org.apache.hadoop.hive.ql.exec.Operator;
import org.apache.hadoop.hive.ql.exec.TableScanOperator;
import org.apache.hadoop.hive.ql.exec.TaskFactory;
import org.apache.hadoop.hive.ql.exec.Utilities;
import org.apache.hadoop.hive.ql.lib.DefaultGraphWalker;
import org.apache.hadoop.hive.ql.lib.DefaultRuleDispatcher;
import org.apache.hadoop.hive.ql.lib.Dispatcher;
import org.apache.hadoop.hive.ql.lib.GraphWalker;
import org.apache.hadoop.hive.ql.lib.Node;
import org.apache.hadoop.hive.ql.lib.NodeProcessor;
import org.apache.hadoop.hive.ql.lib.NodeProcessorCtx;
import org.apache.hadoop.hive.ql.lib.Rule;
import org.apache.hadoop.hive.ql.lib.RuleRegExp;
import org.apache.hadoop.hive.ql.metadata.Table;
import org.apache.hadoop.hive.ql.parse.ParseContext;
import org.apache.hadoop.hive.ql.parse.SemanticException;
import org.apache.hadoop.hive.ql.plan.ExprNodeColumnDesc;
import org.apache.hadoop.hive.ql.plan.ExprNodeConstantDesc;
import org.apache.hadoop.hive.ql.plan.ExprNodeDesc;
import org.apache.hadoop.hive.ql.plan.ExprNodeGenericFuncDesc;
import org.apache.hadoop.hive.ql.plan.FetchWork;
import org.apache.hadoop.hive.ql.plan.FilterDesc;
import org.apache.hadoop.hive.ql.plan.MapRDbJsonFetchByIdWork;
import org.apache.hadoop.hive.ql.plan.OperatorDesc;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDFOPAnd;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDFOPEqual;
import org.apache.hadoop.hive.serde2.objectinspector.PrimitiveObjectInspector;
import org.apache.hadoop.hive.serde2.typeinfo.PrimitiveTypeInfo;
import org.apache.hadoop.hive.serde2.typeinfo.TypeInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import static org.apache.hadoop.hive.ql.io.MapRDbJsonUtils.isMapRDbJsonTable;

/**
 * Simple MapR DB JSON optimizer.
 *
 * This optimizer is designed for queries like
 *
 *   SELECT *
 *   FROM <mapr_db_json_table>
 *   WHERE _id = <constant_string_value>;
 *
 * and
 *
 *   SELECT *
 *   FROM <mapr_db_json_table>
 *   WHERE _id = <constant_string_value> AND (<condition_1>) AND (<condition_2>) ... AND (<condition_N>);
 *
 *
 * and
 *
 *   SELECT *
 *   FROM <mapr_db_json_table>
 *   WHERE <Constant false operator>
 *
 * Here <_id> is key column of MapR DB Json table. It provides usage
 * of findById() method of MapR DB JSON Table.
 */
public class MapRDbJsonFetchByIdOptimizer extends Transform {
  private static final Logger LOG = LoggerFactory.getLogger(MapRDbJsonFetchByIdOptimizer.class.getName());
  private String mapRDbColumn = "";
  private String searchValue = "";
  private boolean optimized = false;
  private boolean isEmpty = false;

  private static class SampleMapRDbJsonCtx implements NodeProcessorCtx {
  }

  @Override public ParseContext transform(ParseContext pctx) throws SemanticException {
    Map<String, TableScanOperator> topOps = pctx.getTopOps();
    QueryProperties qp = pctx.getQueryProperties();
    if (qp.isQuery() && !qp.isAnalyzeCommand() && topOps.size() == 1) {
      // no join, no group by, no distinct, no lateral view, no sub query,
      // no CTAS or insert, not analyze command, and single sourced.
      TableScanOperator topOp = topOps.values().iterator().next();
      processTableScan(pctx, topOp);
    }
    return pctx;
  }

  /**
   * Checks if optimization is possible and saves optimized fetch work to ParseContext.
   *
   * @param pctx ParseContext with optimized fetch task
   * @param topOp scan operator to optimize
   * @throws SemanticException
   */
  private void processTableScan(ParseContext pctx, TableScanOperator topOp) throws SemanticException {
    Table table = topOp.getConf().getTableMetadata();
    if (isMapRDbJsonTable(table)) {
      mapRDbColumn = table.getMapRDbColumnId();
      Map<Rule, NodeProcessor> opRules = new LinkedHashMap<>();
      opRules
          .put(new RuleRegExp("R1", TableScanOperator.getOperatorName() + "%" + FilterOperator.getOperatorName() + "%"),
              getSimpleFilterProcessor());

      // The dispatcher fires the processor corresponding to the closest matching
      // rule and passes the context along
      Dispatcher dispatcher =
          new DefaultRuleDispatcher(getDefaultFilterProcessor(), opRules, new SampleMapRDbJsonCtx());
      GraphWalker graphWalker = new DefaultGraphWalker(dispatcher);

      // Create a list of top-op nodes
      List<Node> topNodes = new ArrayList<Node>(pctx.getTopOps().values());
      graphWalker.startWalking(topNodes, null);

      if (optimized) {
        pctx.setFetchTask(createFetchTask(pctx, topOp, table));
      }
    }
  }

  /**
   * Returns non-null FetchTask instance when succeeded.
   *
   * @param pctx parse context
   * @param source table scan operator for source table
   * @param table table metadata
   * @return non-null FetchTask instance when succeeded
   */
  @SuppressWarnings("unchecked") private FetchTask createFetchTask(ParseContext pctx, TableScanOperator source,
      Table table) {
    String mapRDbTableName = table.getMapRDbTableName();
    FetchWork fetchWork =
        new MapRDbJsonFetchByIdWork(table.getPath(), Utilities.getTableDesc(table), searchValue, mapRDbTableName,
            isEmpty);
    Operator<?> op = source.getChildOperators().get(0);
    fetchWork.setSink(SimpleFetchOptimizer.replaceFSwithLS(getFileSink(op), fetchWork.getSerializationNullFormat()));
    FetchTask fetchTask = (FetchTask) TaskFactory.get(fetchWork, pctx.getConf());
    fetchWork.setSource(source);
    return fetchTask;
  }

  /**
   * Returns first File Sink operator starting from root operator.
   * We expect that there exists at list one File Sink operator.
   *
   * @param rootOperator root of the tree with operators
   * @return File Sink operator or null when there is no File Sink operator.
   */
  private Operator<?> getFileSink(Operator<?> rootOperator) {
    for (Operator<? extends OperatorDesc> operator : rootOperator.getChildOperators()) {
      if (operator instanceof FileSinkOperator) {
        return operator;
      }
      Operator<?> child = null;
      if (!operator.getChildOperators().isEmpty()) {
        child = getFileSink(operator);
      }
      if (child != null) {
        return child;
      }
    }
    return null;
  }

  private NodeProcessor getSimpleFilterProcessor() {
    return new SimpleFilterProcessor();
  }

  private static NodeProcessor getDefaultFilterProcessor() {
    return new DefaultFilterProcessor();
  }

  /**
   * Node processor for simple and complex conditions.
   *
   *
   * Simple node processor
   * =====================
   * It searches following structure and is used for simple condition:
   *
   * WHERE _id = <constant_string_value>
   *
   * TableScanOperator
   *        \
   *        FilterOperator
   *           \
   *            Predicate.getGenericUDF() == GenericUDFOPEqual
   *              |
   *              +-children[0] == MapR DB JSON _id column
   *              |
   *              +-children[1] == <constant_string_value>
   *
   *
   *
   * Complex node processor
   * ======================
   * Processor is used for condition:
   *
   * WHERE _id = <constant_string_value> AND (<condition_1>) AND (<condition_2>) ... AND (<condition_N>)
   *
   * It searches following structure.
   *
   * TableScanOperator
   *        \
   *        FilterOperator
   *           \
   *            Predicate.getGenericUDF() == GenericUDFOPAnd
   *               |
   *               +-children[0]
   *               |
   *               +-children[1]
   *               |
   *              ...
   *               |
   *               +-children[i]  <-- This child must present at least at once among all children
   *               |    |
   *               |    +- GenericUDFOPEqual
   *               |         |
   *               |         +-children[0] == MapR DB JSON _id column
   *               |         |
   *               |         +-children[1] == <constant_string_value>
   *               |
   *              ...
   *               |
   *               +-children[N]
   *
   *
   * Constant false operator
   * =======================
   *
   * It searches following structure:
   *
   * TableScanOperator
   *        \
   *        FilterOperator
   *           \
   *            Predicate.getGenericUDF() == ExprNodeConstantDesc <-- Type is Boolean and value is 'false'
   *
   * And returns empty set while fetching data.
   *
   */
  private class SimpleFilterProcessor implements NodeProcessor {

    @Override public Object process(Node nd, Stack<Node> stack, NodeProcessorCtx procCtx, Object... nodeOutputs)
        throws SemanticException {
      if (nd instanceof FilterOperator) {
        FilterOperator fo = (FilterOperator) nd;
        FilterDesc fd = fo.getConf();
        ExprNodeDesc predicate = fd.getPredicate();
        // We optimize condition
        // WHERE _id = <constant_string_value>
        if (isEquals(predicate) && isIdComparedToConstant(predicate)) {
          searchValue = parseSearchValue(predicate);
          optimized = true;
          return null;
        }
        // We optimize only conditions like
        // WHERE (<condition_1>) AND (<condition_2>) ... AND (<condition_N>)
        List<ExprNodeDesc> children = predicate.getChildren();
        if (isAnd(predicate) && hasIdComparisonAmongChildren(children)) {
          searchValue = parseSearchValue(children);
          optimized = true;
          return null;
        }
        // No rows in result set
        if (isConstantFalse(predicate)) {
          searchValue = null;
          isEmpty = true;
          optimized = true;
          return null;
        }
      }
      return null;
    }

    /**
     * Returns true if expression node description is 'Equal' predicate.
     *
     * @param end expression node description
     * @return true if expression node description is 'Equal' predicate
     */
    private boolean isEquals(ExprNodeDesc end) {
      if (end instanceof ExprNodeGenericFuncDesc) {
        ExprNodeGenericFuncDesc engfd = (ExprNodeGenericFuncDesc) end;
        GenericUDF genericUDF = engfd.getGenericUDF();
        // we support only equal sign in this type of optimization
        if (genericUDF instanceof GenericUDFOPEqual) {
          LOG.info("Operation equals");
          return true;
        }
      }
      return false;
    }

    /**
     * Returns true if a predicate is constant false predicate.
     * E.g. 1 == 0 or column == <aaa> AND column = <BBB>
     *
     * @param end expression node description
     * @return true if a predicate is constant false predicate
     */
    private boolean isConstantFalse(ExprNodeDesc end) {
      if (end instanceof ExprNodeConstantDesc) {
        ExprNodeConstantDesc encd = (ExprNodeConstantDesc) end;
        TypeInfo typeInfo = encd.getTypeInfo();
        if (typeInfo instanceof PrimitiveTypeInfo) {
          PrimitiveTypeInfo pti = (PrimitiveTypeInfo) typeInfo;
          if (PrimitiveObjectInspector.PrimitiveCategory.BOOLEAN == pti.getPrimitiveCategory()) {
            return !(Boolean) encd.getValue();
          }
        }
      }
      return false;
    }

    /**
     * Returns true if condition in WHERE contains _id = <constant_string_value>.
     *
     * @param children list of expression node descriptions
     * @return true if condition in WHERE contains _id = <constant_string_value>
     * @throws SemanticException when error during parsing
     */
    private boolean hasIdComparisonAmongChildren(List<ExprNodeDesc> children) throws SemanticException {
      for (ExprNodeDesc child : children) {
        // Check if _id = <constant_string_value> is present
        if (isIdComparedToConstant(child)) {
          return true;
        }
      }
      return false;
    }

    /**
     * Returns true if expression node description is 'And' predicate.
     *
     * @param end expression node description
     * @return true if expression node description is 'And' predicate
     */
    private boolean isAnd(ExprNodeDesc end) {
      if (end instanceof ExprNodeGenericFuncDesc) {
        ExprNodeGenericFuncDesc engfd = (ExprNodeGenericFuncDesc) end;
        GenericUDF genericUDF = engfd.getGenericUDF();
        // we support only equal sign in this type of optimization
        if (genericUDF instanceof GenericUDFOPAnd) {
          LOG.info("Operation and");
          return true;
        }
      }
      return false;
    }

    /**
     * Returns true in case of comparision _id = <constant_string_value>.
     *
     * @param end expression node description
     * @return true in case of comparision _id = <constant_string_value>
     * @throws SemanticException when error during parsing
     */
    private boolean isIdComparedToConstant(ExprNodeDesc end) throws SemanticException {
      List<ExprNodeDesc> children = end.getChildren();
      if (children != null && children.size() == 2) {
        if (children.get(0) instanceof ExprNodeColumnDesc) {
          ExprNodeColumnDesc nodeColumnDesc = (ExprNodeColumnDesc) children.get(0);
          String column = nodeColumnDesc.getColumn();
          // Condition should be over MapRD b Json _id column
          if (mapRDbColumn.equals(column)) {
            LOG.info(String.format("Column %s", column));
            // Search value should be constant
            if (children.get(1) instanceof ExprNodeConstantDesc) {
              LOG.info("Value is constant literal");
              return true;
            }
          }
        }
      }
      return false;
    }

    /**
     * Parses <constant_string_value> from condition WHERE ... AND _id = <constant_string_value> AND ...
     *
     * @param children list of expression node descriptions
     * @return String representation of <constant_string_value> and empty string in case of any error
     */
    private String parseSearchValue(List<ExprNodeDesc> children) throws SemanticException {
      for (ExprNodeDesc child : children) {
        // Check if _id = <constant_string_value> is present
        if (isIdComparedToConstant(child)) {
          return parseSearchValue(child);
        }
      }
      return "";
    }

    /**
     * Parses <constant_string_value> from condition WHERE _id = <constant_string_value>.
     *
     * @param end expression node description
     * @return String representation of <constant_string_value> and empty string in case of any error
     */
    private String parseSearchValue(ExprNodeDesc end) {
      List<ExprNodeDesc> children = end.getChildren();
      boolean hasTwoChildren = children != null && children.size() == 2;
      if (hasTwoChildren) {
        boolean isConstantDesc = children.get(1) instanceof ExprNodeConstantDesc;
        // Search value should be constant
        if (isConstantDesc) {
          ExprNodeConstantDesc nodeConstantDesc = (ExprNodeConstantDesc) children.get(1);
          String value = (String) nodeConstantDesc.getValue();
          LOG.info(String.format("Value = %s", value));
          return value;
        }
      }
      return "";
    }
  }

  /**
   * Default Filter Processor is stub class here.
   */
  public static class DefaultFilterProcessor implements NodeProcessor {
    @Override public Object process(Node nd, Stack<Node> stack, NodeProcessorCtx procCtx, Object... nodeOutputs)
        throws SemanticException {
      return null;
    }
  }
}
