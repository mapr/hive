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

package org.apache.hadoop.hive.ql.plan;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hive.ql.plan.Explain.Level;
import org.apache.hadoop.hive.ql.plan.Explain.Vectorization;

/**
 * MapR DB JSON Fetch By Id Work.
 *
 */
@Explain(displayName = "MapR DB JSON Fetch By Id Operator", explainLevels = { Level.USER, Level.DEFAULT,
    Level.EXTENDED }, vectorization = Vectorization.SUMMARY_PATH) public class MapRDbJsonFetchByIdWork
    extends FetchWork {
  private static final long serialVersionUID = 1L;
  private final String searchValue;
  private final String mapRDbTableName;
  private final boolean isEmpty;

  public MapRDbJsonFetchByIdWork(Path tblDir, TableDesc tblDesc, String searchValue, String mapRDbTableName, boolean isEmpty) {
    this(tblDir, tblDesc, -1, searchValue, mapRDbTableName, isEmpty);
  }

  private MapRDbJsonFetchByIdWork(Path tblDir, TableDesc tblDesc, int limit, String searchValue, String mapRDbTableName, boolean isEmpty) {
    super(tblDir, tblDesc, limit);
    this.searchValue = searchValue;
    this.mapRDbTableName = mapRDbTableName;
    this.isEmpty = isEmpty;
  }

  public String getSearchValue() {
    return searchValue;
  }

  public String getMapRDbTableName() {
    return mapRDbTableName;
  }

  public boolean isEmpty() {
    return isEmpty;
  }

  @Override public int getLeastNumRows() {
    return 1;
  }
}
