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
    boolean isMerge = tree.getType() == HiveParser.TOK_MERGE;
    List<? extends Node> children = tree.getChildren();
    // The first child should be the table we are deleting from
    ASTNode astNode = (ASTNode) children.get(0);

    if (isMerge){
      astNode = (ASTNode) astNode.getChildren().get(0);
    }
    String[] tableName = getQualifiedTableName(astNode);
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
}
