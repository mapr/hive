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

package org.apache.hadoop.hive.ql.io;

import org.apache.hadoop.hive.conf.HiveConf;
import org.apache.hadoop.hive.ql.ErrorMsg;
import org.apache.hadoop.hive.ql.hooks.Entity;
import org.apache.hadoop.hive.ql.hooks.ReadEntity;
import org.apache.hadoop.hive.ql.hooks.WriteEntity;
import org.apache.hadoop.hive.ql.lib.Node;
import org.apache.hadoop.hive.ql.metadata.Hive;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.metadata.InvalidTableException;
import org.apache.hadoop.hive.ql.metadata.Table;
import org.apache.hadoop.hive.ql.parse.ASTNode;
import org.apache.hadoop.hive.ql.parse.HiveParser;
import org.apache.hadoop.hive.ql.parse.SemanticException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Set;

import static org.apache.hadoop.hive.ql.parse.BaseSemanticAnalyzer.getDotName;
import static org.apache.hadoop.hive.ql.parse.BaseSemanticAnalyzer.getQualifiedTableName;

/**
 *  Utility class for working with tables.
 */

public final class TableUtils {
  private static final Logger LOG = LoggerFactory.getLogger(TableUtils.class.getName());

  private TableUtils() {
  }

  /**
   * Returns table from parse tree.
   * @param tree parse tree
   * @param conf configuration
   * @return table from parse tree
   * @throws SemanticException
   */

  public static Table findTable(ASTNode tree, HiveConf conf) throws SemanticException {
    try {
      return findTable(tree, Hive.get(conf));
    } catch (HiveException e) {
      throw new SemanticException(e.getMessage(), e);
    }
  }

  /**
   * Returns table from parse tree.
   * @param tree parse tree
   * @param db Database where to search table
   * @return table from parse tree
   * @throws SemanticException
   */

  public static Table findTable(ASTNode tree, Hive db) throws SemanticException {
    List<? extends Node> children = tree.getChildren();
    // The first child should be the table we are deleting from
    ASTNode tabName = (ASTNode) children.get(0);
    String[] tableName = getQualifiedTableName(tabName);
    Table mTable;
    try {
      mTable = db.getTable(tableName[0], tableName[1]);
    } catch (InvalidTableException e) {
      LOG.error("Failed to find table " + getDotName(tableName) + " got exception " + e.getMessage());
      throw new SemanticException(ErrorMsg.INVALID_TABLE.getMsg(getDotName(tableName)), e);
    } catch (HiveException e) {
      LOG.error("Failed to find table " + getDotName(tableName) + " got exception " + e.getMessage());
      throw new SemanticException(e.getMessage(), e);
    }
    return mTable;
  }

  /**
   * Column names are stored in metastore in lower case, regardless of the CREATE TABLE statement.
   * Unfortunately there is no single place that normalizes the input query.
   * @param colName not null
   */
  public static String normalizeColName(String colName) {
    return colName.toLowerCase();
  }

  /**
   * Adds columns to set.
   * This method find any columns on the right side of a set statement (thus rcols) and puts them
   * in a set so we can add them to the list of input cols to check.
   * @param node
   * @param setRCols
   */

  public static void addSetRCols(ASTNode node, Set<String> setRCols) {
    // See if this node is a TOK_TABLE_OR_COL.  If so, find the value and put it in the list.  If
    // not, recurse on any children
    if (node.getToken().getType() == HiveParser.TOK_TABLE_OR_COL) {
      ASTNode colName = (ASTNode) node.getChildren().get(0);
      assert colName.getToken().getType() == HiveParser.Identifier : "Expected column name";
      setRCols.add(normalizeColName(colName.getText()));
    } else if (node.getChildren() != null) {
      for (Node n : node.getChildren()) {
        addSetRCols((ASTNode) n, setRCols);
      }
    }
  }

  /**
   * Checks if input is partitioned.
   * @param inputs
   * @return
   */

  public static boolean inputIsPartitioned(Set<ReadEntity> inputs) {
    // We cannot simply look at the first entry, as in the case where the input is partitioned
    // there will be a table entry as well.  So look for at least one partition entry.
    for (ReadEntity re : inputs) {
      if (re.getTyp() == Entity.Type.PARTITION) {
        return true;
      }
    }
    return false;
  }

  /**
   * Check that {@code readEntity} is also being written.
   *
   */
  public static boolean isWritten(Entity readEntity, Set<WriteEntity> outputs) {
    for (Entity writeEntity : outputs) {
      //make sure to compare them as Entity, i.e. that it's the same table or partition, etc
      if (writeEntity.toString().equalsIgnoreCase(readEntity.toString())) {
        return true;
      }
    }
    return false;
  }
}
