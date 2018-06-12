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
import org.apache.hadoop.hive.metastore.api.MetaException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.metadata.HiveStorageHandler;
import org.apache.hadoop.hive.ql.metadata.HiveUtils;
import org.apache.hadoop.hive.ql.metadata.Table;
import org.apache.hadoop.hive.ql.parse.ASTNode;
import org.apache.hadoop.hive.ql.parse.SemanticException;
import org.apache.hadoop.hive.ql.session.SessionState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class for processing updates in MapRDbJson tables.
 */

public final class MapRDbJsonUtils {
  private static final Logger LOG = LoggerFactory.getLogger(MapRDbJsonUtils.class.getName());

  private MapRDbJsonUtils() {
  }

  private static final String MAPR_DB_JSON_STORAGE_HANDLER =
      "org.apache.hadoop.hive.maprdb.json.MapRDBJsonStorageHandler";

  /**
   * List of operations available for MapRDbJson tables.
   */
  public enum Operation {
    UNKNOWN, INSERT, UPDATE, DELETE;
  }

  /**
   * Checks if a table is MapRDbJson table.
   *
   * @param tree parse tree
   * @param conf Configuration
   * @return true if a table is MapRDbJson table
   * @throws SemanticException if it can't find table name in parse tree
   */

  public static boolean isMapRDbJsonTable(ASTNode tree, HiveConf conf) throws SemanticException {
    return isMapRDbJsonTable(TableUtils.findTable(tree, conf));
  }

  /**
   * Checks if a table is MapRDbJson table.
   * @param table table object
   * @return true if a table is MapRDbJson table
   */

  public static boolean isMapRDbJsonTable(Table table) {
    HiveStorageHandler hiveStorageHandler = table.getStorageHandler();
    return hiveStorageHandler != null && MAPR_DB_JSON_STORAGE_HANDLER
        .equals(hiveStorageHandler.getClass().getCanonicalName());
  }
}
