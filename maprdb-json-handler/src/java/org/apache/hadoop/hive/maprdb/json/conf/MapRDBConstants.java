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

package org.apache.hadoop.hive.maprdb.json.conf;

import org.ojai.DocumentConstants;

public final class MapRDBConstants {

  private MapRDBConstants() {
  }

  public static final String MAPRDB_PFX = "maprdb.";
  public static final String MAPRDB_TABLE_NAME = MAPRDB_PFX + "table.name";
  public static final String MAPRDB_INPUT_TABLE_NAME = MAPRDB_PFX + "mapreduce.inputtable";
  public static final String MAPRDB_OUTPUT_TABLE_NAME = MAPRDB_PFX + "mapred.outputtable";
  public static final String ID_KEY = DocumentConstants.ID_KEY;
  //map '_id' to one of column names
  public static final String MAPRDB_COLUMN_ID = MAPRDB_PFX + "column.id";
  //class to load
  public static final String MAPRDB_CLASS = "com.mapr.db.MapRDB";
}