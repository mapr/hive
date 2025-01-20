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

package org.apache.iceberg.mr.hive.compaction.evaluator.amoro;

public class TableRuntimeMeta {

  private String catalogName;
  private long tableId;
  private String dbName;
  private String tableName;
  private TableFormat format;
  private long lastMinorOptimizingTime;
  private long lastFullOptimizingTime;
  private TableConfiguration tableConfig;
  private String summary;

  public TableRuntimeMeta() {

  }

  public String getCatalogName() {
    return catalogName;
  }

  public String getTableName() {
    return tableName;
  }

  public TableFormat getFormat() {
    return format;
  }


  public long getLastMinorOptimizingTime() {
    return lastMinorOptimizingTime;
  }

  public long getLastFullOptimizingTime() {
    return lastFullOptimizingTime;
  }

  public TableConfiguration getTableConfig() {
    return tableConfig;
  }

  public String getSummary() {
    return summary;
  }

  public void setCatalogName(String catalogName) {
    this.catalogName = catalogName;
  }

  public void setTableName(String tableName) {
    this.tableName = tableName;
  }

  public void setFormat(TableFormat format) {
    this.format = format;
  }

  public void setLastMinorOptimizingTime(long lastMinorOptimizingTime) {
    this.lastMinorOptimizingTime = lastMinorOptimizingTime;
  }

  public void setLastFullOptimizingTime(long lastFullOptimizingTime) {
    this.lastFullOptimizingTime = lastFullOptimizingTime;
  }

  public void setTableConfig(TableConfiguration tableConfig) {
    this.tableConfig = tableConfig;
  }

  public void setSummary(String summary) {
    this.summary = summary;
  }

  public long getTableId() {
    return tableId;
  }

  public void  setTableId(long tableId) {
    this.tableId = tableId;
  }

  public String getDbName() {
    return dbName;
  }

  public void setDbName(String dbName) {
    this.dbName = dbName;
  }
}
