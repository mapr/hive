/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.hadoop.hive.maprdb.json.util;

import com.mapr.db.impl.MapRDBImpl;
import org.ojai.Document;

/**
 * Utility class for working with MapR DB Json tables.
 */

public final class MapRDbJsonTableUtil {
  private MapRDbJsonTableUtil(){}

  /**
   * Finds doc in MapR DB json table by doc id.
   *
   * @param tableName MapR DB json table name
   * @param id document id
   * @return ojai Document
   */
  public static Document findById(String tableName, String id) {
    return MapRDBImpl.getTable(tableName).findById(id);
  }
}
