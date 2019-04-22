/*
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

import com.mapr.db.MapRDB;
import org.apache.hadoop.conf.Configuration;

/**
 * Wrapper for MapRDB class.
 * Hides usage of MapRDB.newAdmin(conf) call from the Hive packages and
 * escapes clashing of dependencies in hadoop from mapr-core (where MapRDB is placed)
 * and direct dependency in Hive modules.
 *
 */
public class MapRDBAdmin {

  /**
   * Deletes MapR db Table.
   *
   * @param conf configuration file
   * @param tableName table name
   */
  public static void deleteTable(Configuration conf, String tableName) {
    MapRDB.newAdmin(conf).deleteTable(tableName);
  }
}
