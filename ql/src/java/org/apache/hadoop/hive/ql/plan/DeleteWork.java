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

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Contains list of data to be deleted from MapR Db Json table.
 */
@Explain(displayName = "Deletion from MapR DB Json Table", explainLevels = { Explain.Level.USER, Explain.Level.DEFAULT,
    Explain.Level.EXTENDED }) public class DeleteWork implements Serializable {
  private final Set<String> values;
  private final String mapRDbTableName;
  private final DeleteOperation deleteOperation;

  /**
   * List of supported conditions of WHERE close while deleting a row from MapR Db Json table.
   */
  public enum DeleteOperation {
    DELETE_ALL, DELETE_ALL_IN_SET, DELETE_ALL_EXCEPT_IN_SET, DELETE_SINGLE
  }

  public DeleteWork(String mapRDbTableName) {
    this.values = new HashSet<>();
    this.mapRDbTableName = mapRDbTableName;
    deleteOperation = DeleteOperation.DELETE_ALL;
  }

  public DeleteWork(Set<String> values, String mapRDbTableName, DeleteOperation deleteOperation) {
    this.values = values;
    this.mapRDbTableName = mapRDbTableName;
    this.deleteOperation = deleteOperation;
  }

  public Set<String> getValues() {
    return values;
  }

  public String getMapRDbTableName() {
    return mapRDbTableName;
  }

  public DeleteOperation getDeleteOperation() {
    return deleteOperation;
  }

  public String getValue() {
    Iterator<String> iterator = values.iterator();
    if (iterator.hasNext()) {
      return iterator.next();
    }
    throw new RuntimeException("Set has no next value");
  }
}
