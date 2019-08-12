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

package org.apache.hadoop.hive.maprdb.json.util;

import com.mapr.db.impl.MapRDBImpl;
import org.apache.hadoop.hive.ql.ErrorMsg;
import org.apache.hadoop.hive.ql.lib.Node;
import org.apache.hadoop.hive.ql.parse.ASTNode;
import org.apache.hadoop.hive.ql.parse.HiveParser;
import org.apache.hadoop.hive.ql.parse.SemanticException;
import org.ojai.store.QueryCondition;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.apache.hadoop.hive.maprdb.json.conf.MapRDBConstants.ID_KEY;

/**
 * Utility class for parsing delete statement.
 */
public final class MapRDbJsonParseUtil {
  private MapRDbJsonParseUtil(){}

  /**
   * Build MapR Db Json condition over input values.
   * Condition looks like
   *
   *     (_id != value1) AND (_id != value2) AND (_id != value3) ...
   *
   *     where _id is id key for MapR Db Json table.
   *           value1, ... is list of values for id keys.
   *
   *  Purpose of the condition is to query all id keys except value1, value2, ...
   *  Condition is used in
   *
   *           DELETE FROM <table_name> WHERE _id NOT IN(valu1, value2);
   *
   * @param values set of values not to delete
   * @return MapR Db Json query condition.
   */
  public static QueryCondition buildQueryCondition(Set<String> values) {
    QueryCondition queryCondition = MapRDBImpl.newCondition();
    boolean first = true;
    for (String value : values) {
      if (first) {
        queryCondition = queryCondition.and().is(ID_KEY, QueryCondition.Op.NOT_EQUAL, value);
        first = false;
        continue;
      }
      queryCondition = queryCondition.is(ID_KEY, QueryCondition.Op.NOT_EQUAL, value);
    }
    return queryCondition.close().build();
  }

  /**
   * Parses AST tree and finds value for query.
   *
   *         DELETE FROM <table_name> WHERE <_id> = <value>
   *
   * @param children list of AST nodes, children of TOK_DELETE_FROM node.
   * @return value to delete placed in a set object
   */
  public static Set<String> buildSingleValueSetToDelete(List<? extends Node> children) {
    Set<String> singleValueSetToDelete = new HashSet<>();
    ASTNode whereNode = (ASTNode) children.get(1);
    ASTNode equalsNode = (ASTNode) whereNode.getChildren().get(0);
    ASTNode valueNode = (ASTNode) equalsNode.getChildren().get(1);
    singleValueSetToDelete.add(removeQuotes(valueNode.getText()));
    return Collections.unmodifiableSet(singleValueSetToDelete);
  }

  /**
   * Checks if delete operator has structure DELETE FROM <table_name> WHERE <_id> = <value>.
   *
   * Abstract semantic tree for the operator above is:
   *
   * TOK_DELETE_FROM
   * |
   * +---TOK_TABNAME
   * |
   * +---TOK_WHERE
   *     |
   *     +---AST_NODE "=" or "=="
   *         |
   *         +---TOK_TABLE_OR_COL
   *         |
   *         +---"<value>"
   *
   * @param children list of AST nodes, children of TOK_DELETE_FROM node.
   * @param columnId column used in where condition
   * @return true if delete operator has needed structure.
   * @throws SemanticException if column is not _id column of MapR Db Json table
   */
  public static boolean isSingeEqualsClause(List<? extends Node> children, String columnId) throws SemanticException {
    if (children == null || children.size() < 2) {
      return false;
    }
    if (((ASTNode) children.get(1)).getToken().getType() != HiveParser.TOK_WHERE) {
      return false;
    }
    ASTNode whereNode = (ASTNode) children.get(1);
    if (whereNode.getChildren().size() > 1) {
      return false;
    }
    ASTNode equalsNode = (ASTNode) whereNode.getChildren().get(0);
    if (!"=".equals(equalsNode.getText()) && !"==".equals(equalsNode.getText())) {
      return false;
    }
    if (equalsNode.getChildren().size() != 2) {
      return false;
    }
    ASTNode tabOrColNode = (ASTNode) equalsNode.getChildren().get(0);
    String deleteColumn = ((ASTNode) tabOrColNode.getChildren().get(0)).getToken().getText();
    if (!columnId.equalsIgnoreCase(deleteColumn)) {
      throw new SemanticException(ErrorMsg.DELETION_OVER_COLUMN_IS_FORBIDDEN, deleteColumn, columnId);
    }
    return true;
  }

  /**
   *  Checks if delete operator has structure DELETE FROM <table_name>;.
   *
   * Abstract semantic tree for the operator above is:
   *
   * TOK_DELETE_FROM
   * |
   * +---TOK_TABNAME
   *
   * @param children list of AST nodes, children of TOK_DELETE_FROM node.
   * @return true if delete operator has needed structure.
   */
  public static boolean isDeleteAllClause(List<? extends Node> children) {
    return children != null && children.size() == 1 && ((ASTNode)children.get(0)).getToken().getType() == HiveParser.TOK_TABNAME;
  }

  /**
   * Builds set of values from abstract semantic tree needed to be preserved in table during delete operation.
   * Delete operator looks like:
   *
   *     DELETE FROM <table_name> WHERE <_id> NOT IN (<value1>, <value2>,...);
   *
   * @param children list of AST nodes, children of TOK_DELETE_FROM node.
   * @return immutable set of values <value1>, <value2>,...
   */
  public static Set<String> buildSetToPreserve(List<? extends Node> children) {
    Set<String> valuesToPreserve = new HashSet<>();
    ASTNode whereNode = (ASTNode) children.get(1);
    ASTNode notNode = (ASTNode) whereNode.getChildren().get(0);
    ASTNode functionNode = (ASTNode) notNode.getChildren().get(0);
    boolean first = true;
    boolean second = true;
    for (Node node : functionNode.getChildren()) {
      if (first) {
        first = false;
        continue;
      }
      if(second) {
        second = false;
        continue;
      }
      valuesToPreserve.add(removeQuotes(((ASTNode) node).getText()));
    }
    return Collections.unmodifiableSet(valuesToPreserve);
  }


  /**
   * Builds set of values from abstract semantic tree needed to be deleted in table during delete operation.
   * Delete operator looks like:
   *
   *     DELETE FROM <table_name> WHERE <_id> IN (<value1>, <value2>,...);
   *
   * @param children list of AST nodes, children of TOK_DELETE_FROM node.
   * @return immutable set of values <value1>, <value2>,...
   */
  public static Set<String> buildSetToDelete(List<? extends Node> children) {
    Set<String> valuesToDelete =  new HashSet<>();
    ASTNode whereNode = (ASTNode) children.get(1);
    ASTNode functionNode = (ASTNode) whereNode.getChildren().get(0);

    boolean first = true;
    boolean second = true;
    for (Node node : functionNode.getChildren()) {
      if (first) {
        first = false;
        continue;
      }
      if(second) {
        second = false;
        continue;
      }
      valuesToDelete.add(removeQuotes(((ASTNode) node).getText()));
    }

    return Collections.unmodifiableSet(valuesToDelete);
  }

  /**
   * Checks if delete operator has structure DELETE FROM <table_name> WHERE NOT IN (<value1>, <value1>,...).
   *
   * Abstract semantic tree for the operator above is:
   *
   * TOK_DELETE_FROM
   * |
   * +---TOK_TABNAME
   * |
   * +---TOK_WHERE
   *     |
   *     +---NOT
   *         |
   *         +---TOK_FUNCTION
   *             |
   *             +---"in"
   *             |
   *             +---TOK_TABLE_OR_COL
   *             |
   *             +---"<value1>"
   *             |
   *             +---"<value2>"
   *             |
   *             +---"<value3>"
   *
   * @param children list of AST nodes, children of TOK_DELETE_FROM node.
   * @param columnId column used in where condition
   * @return true if delete operator has needed structure.
   * @throws SemanticException if column is not _id column of MapR Db Json table
   */
  public static boolean isNotInListClause(List<? extends Node> children, String columnId) throws SemanticException {
    if (children == null || children.size() < 2) {
      return false;
    }
    if (((ASTNode) children.get(1)).getToken().getType() != HiveParser.TOK_WHERE) {
      return false;
    }
    ASTNode whereNode = (ASTNode) children.get(1);
    if (whereNode.getChildren().isEmpty() || whereNode.getChildren().size() != 1) {
      return false;
    }
    ASTNode notNode = (ASTNode) whereNode.getChildren().get(0);
    if (!"not".equalsIgnoreCase(notNode.getText())) {
      return false;
    }
    if (((ASTNode) notNode.getChildren().get(0)).getToken().getType() != HiveParser.TOK_FUNCTION) {
      return false;
    }
    ASTNode functionNode = (ASTNode) notNode.getChildren().get(0);
    if (functionNode.getChildren().size() < 3) {
      return false;
    }
    if (((ASTNode) functionNode.getChildren().get(1)).getToken().getType() != HiveParser.TOK_TABLE_OR_COL) {
      return false;
    }
    ASTNode inNode = (ASTNode) functionNode.getChildren().get(0);
    if (!"in".equalsIgnoreCase(inNode.getToken().getText())) {
      return false;
    }
    ASTNode tabOrColNode = (ASTNode) functionNode.getChildren().get(1);
    String deleteColumn = ((ASTNode) tabOrColNode.getChildren().get(0)).getToken().getText();
    if (!columnId.equalsIgnoreCase(deleteColumn)) {
      throw new SemanticException(ErrorMsg.DELETION_OVER_COLUMN_IS_FORBIDDEN, deleteColumn, columnId);
    }
    return true;
  }

  /**
   * Checks if delete operator has structure DELETE FROM <table_name> WHERE IN (<value1>, <value1>,...).
   *
   * Abstract semantic tree for the operator above is:
   *
   * TOK_DELETE_FROM
   * |
   * +---TOK_TABNAME
   * |
   * +---TOK_WHERE
   *     |
   *     +---TOK_FUNCTION
   *         |
   *         +---"in"
   *         |
   *         +---TOK_TABLE_OR_COL
   *         |
   *         +---"<value1>"
   *         |
   *         +---"<value2>"
   *         |
   *         +---"<value3>"
   *
   * @param children list of AST nodes, children of TOK_DELETE_FROM node.
   * @param columnId column used in where condition
   * @return true if delete operator has needed structure.
   * @throws SemanticException if column is not _id column of MapR Db Json table
   */
  public static boolean isInListClause(List<? extends Node> children, String columnId) throws SemanticException {
    if (children == null || children.size() < 2) {
      return false;
    }
    if (((ASTNode) children.get(1)).getToken().getType() != HiveParser.TOK_WHERE) {
      return false;
    }
    ASTNode whereNode = (ASTNode) children.get(1);
    if (whereNode.getChildren().isEmpty() || whereNode.getChildren().size() != 1) {
      return false;
    }
    if (((ASTNode) whereNode.getChildren().get(0)).getToken().getType() != HiveParser.TOK_FUNCTION) {
      return false;
    }
    ASTNode functionNode = (ASTNode) whereNode.getChildren().get(0);
    if (functionNode.getChildren().size() < 3) {
      return false;
    }
    if (((ASTNode) functionNode.getChildren().get(1)).getToken().getType() != HiveParser.TOK_TABLE_OR_COL) {
      return false;
    }
    ASTNode inNode = (ASTNode) functionNode.getChildren().get(0);
    if (!"in".equalsIgnoreCase(inNode.getToken().getText())) {
      return false;
    }
    ASTNode tabOrColNode = (ASTNode) functionNode.getChildren().get(1);
    String deleteColumn = ((ASTNode) tabOrColNode.getChildren().get(0)).getToken().getText();
    if (!columnId.equalsIgnoreCase(deleteColumn)) {
      throw new SemanticException(ErrorMsg.DELETION_OVER_COLUMN_IS_FORBIDDEN, deleteColumn, columnId);
    }
    return true;
  }

  /**
   * Checks if WHEN clause of MERGE statement has needed structure.
   *
   * TOK_MATCHED
   * |
   * +--- TOK_DELETE
   *
   * to prevent unsupported condition like this
   *
   * WHEN MATCHED AND <boolean expression> THEN DELETE
   *
   * @param node abstract semantic tree node
   * @return true if the we have clause WHEN MATCHED THEN DELETE (without boolean expression)
   */
  public static boolean isWhenMatchedDeleteClause(ASTNode node){
    List<Node> children = node.getChildren();
    if (children.isEmpty()) {
      return false;
    }
    ASTNode deleteNode = (ASTNode) children.get(0);
    if (deleteNode.getType() != HiveParser.TOK_DELETE) {
      return false;
    }
    return children.size() == 1;
  }

  /**
   * Check if the node represents a subquery.
   *
   * @param node abstract semantic tree node
   * @return true if the node represents a subquery
   */
  public static boolean isSubQuery(ASTNode node){
    return node.getType() == HiveParser.TOK_SUBQUERY;
  }

  /**
   * Removes single and double quotes from string.
   *
   * @param value string to remove quotes from
   * @return string without single and double quotes
   */
  private static String removeQuotes(String value) {
    return value.replace("\"", "").replace("'", "");
  }
}
