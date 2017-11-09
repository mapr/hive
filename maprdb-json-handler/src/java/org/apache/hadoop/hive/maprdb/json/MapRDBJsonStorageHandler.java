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

package org.apache.hadoop.hive.maprdb.json;

import com.mapr.db.Admin;
import com.mapr.db.MapRDB;
import org.apache.hadoop.hive.maprdb.json.input.HiveMapRDBJsonInputFormat;
import org.apache.hadoop.hive.maprdb.json.output.HiveMapRDBJsonOutputFormat;
import org.apache.hadoop.hive.maprdb.json.serde.MapRDBSerDe;
import org.apache.hadoop.hive.metastore.HiveMetaHook;
import org.apache.hadoop.hive.metastore.utils.MetaStoreUtils;
import org.apache.hadoop.hive.metastore.api.FieldSchema;
import org.apache.hadoop.hive.metastore.api.MetaException;
import org.apache.hadoop.hive.metastore.api.Table;
import org.apache.hadoop.hive.ql.metadata.DefaultStorageHandler;
import org.apache.hadoop.hive.ql.plan.TableDesc;
import org.apache.hadoop.hive.serde2.AbstractSerDe;
import org.apache.hadoop.mapred.InputFormat;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.OutputFormat;
import org.apache.hadoop.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import static java.lang.String.format;
import static org.apache.hadoop.hive.maprdb.json.conf.MapRDBConstants.*;
import static org.apache.hadoop.hive.serde.serdeConstants.BINARY_TYPE_NAME;
import static org.apache.hadoop.hive.serde.serdeConstants.STRING_TYPE_NAME;

public class MapRDBJsonStorageHandler extends DefaultStorageHandler implements HiveMetaHook {

  private static final Logger LOG = LoggerFactory.getLogger(MapRDBJsonStorageHandler.class);

  private Admin admin;

  private Admin getMapRDBAdmin() {
    if (admin == null) {
      admin = MapRDB.newAdmin(getConf());
    }
    return admin;
  }

  private String getMapRDBTableName(Table tbl) {
    return tbl.getParameters().get(MAPRDB_TABLE_NAME);
  }

  @Override
  public Class<? extends InputFormat> getInputFormatClass() {
    return HiveMapRDBJsonInputFormat.class;
  }

  @Override
  public Class<? extends OutputFormat> getOutputFormatClass() {
    return HiveMapRDBJsonOutputFormat.class;
  }

  @SuppressWarnings("deprecation")
  @Override
  public Class<? extends AbstractSerDe> getSerDeClass() {
    return MapRDBSerDe.class;
  }

  @Override
  public HiveMetaHook getMetaHook() {
    return this;
  }

  @Override
  public void configureInputJobProperties(TableDesc tableDesc, Map<String, String> jobProperties) {
    Properties tableProperties = tableDesc.getProperties();

    getConf().set(MAPRDB_INPUT_TABLE_NAME, tableProperties.getProperty(MAPRDB_TABLE_NAME));
    jobProperties.put(MAPRDB_INPUT_TABLE_NAME, tableProperties.getProperty(MAPRDB_TABLE_NAME));
  }

  @Override
  public void configureOutputJobProperties(TableDesc tableDesc, Map<String, String> jobProperties) {
    Properties tableProperties = tableDesc.getProperties();

    String property = tableProperties.getProperty(MAPRDB_OUTPUT_TABLE_NAME);
    if (property != null) {
      getConf().set(MAPRDB_OUTPUT_TABLE_NAME, property);
    } else {
      getConf().set(MAPRDB_OUTPUT_TABLE_NAME, tableProperties.getProperty(MAPRDB_TABLE_NAME));
    }
  }

  @Override
  public void configureJobConf(TableDesc tableDesc, JobConf jobConf) {
    try {
      Utils.addDependencyJars(jobConf, MapRDBJsonStorageHandler.class);
    } catch (IOException e) {
      LOG.warn("Can't set properties for job", e);
    }
  }

  @Override
  public void preCreateTable(Table tbl) throws MetaException {
    checkPreConditions(tbl);
    boolean isExternal = MetaStoreUtils.isExternalTable(tbl);

    try {
      String tableName = getMapRDBTableName(tbl);

      if (!getMapRDBAdmin().tableExists(tableName)) {
        // if it is not an external table and it doesn't exists then create one
        if (!isExternal) {
          getMapRDBAdmin().createTable(tableName);
        } else {
          // an external table
          throw new MetaException("MapRDB table " + tableName +
            " doesn't exist while the table is declared as an external table.");
        }

      } else {
        if (!isExternal) {
          throw new MetaException("Table " + tableName + " already exists; use CREATE EXTERNAL TABLE instead to"
            + " register it in Hive.");
        }
      }
    } catch (Exception se) {
      throw new MetaException(StringUtils.stringifyException(se));
    } finally {
      if (admin != null) {
        admin.close();
      }
    }
  }

  private void checkPreConditions(Table tbl) throws MetaException {
    Map<String, String> tblParams = tbl.getParameters();

    if (!(tblParams.containsKey(MAPRDB_TABLE_NAME))) {
      throw new MetaException(
        format("You must specify '%s' in TBLPROPERTIES", MAPRDB_TABLE_NAME));
    }
    if (!(tblParams.containsKey(MAPRDB_TABLE_NAME)) || !tblParams.get(MAPRDB_TABLE_NAME).startsWith("/")) {
      throw new MetaException(
        format("MapRDB should start with \"/\", actual table name '%s'", MAPRDB_TABLE_NAME));
    }
    if (!(tblParams.containsKey(MAPRDB_COLUMN_ID)) ||
      tblParams.get(MAPRDB_COLUMN_ID).isEmpty()) {
      throw new MetaException(
        format("You must specify '%s' in TBLPROPERTIES", MAPRDB_COLUMN_ID));
    }

    String bindedId = tblParams.get(MAPRDB_COLUMN_ID).trim();
    boolean match = false;

    for (FieldSchema field : tbl.getSd().getCols()) {
      if (field.getName().equalsIgnoreCase(bindedId)) {
        String type = field.getType();

        if (!(type.equalsIgnoreCase(BINARY_TYPE_NAME) || type.equalsIgnoreCase(STRING_TYPE_NAME))) {
          throw new MetaException(
            format("'%s' must be STRING or BINARY type, actual: '%s'",
              MAPRDB_COLUMN_ID,
              field.getType().toUpperCase()));
        }
        match = true;
        break;
      }
    }
    if (!match) {
      throw new MetaException(format("'%s' should matches column name.", MAPRDB_COLUMN_ID));
    }
  }

  @Override
  public void rollbackCreateTable(Table table) throws MetaException {
    boolean isExternal = MetaStoreUtils.isExternalTable(table);
    String tableName = getMapRDBTableName(table);
    if (!isExternal && getMapRDBAdmin().tableExists(tableName)) {
      getMapRDBAdmin().deleteTable(tableName);
    }
  }

  @Override
  public void commitCreateTable(Table table) throws MetaException {
    //nothing to do
  }

  @Override
  public void preDropTable(Table table) throws MetaException {
    //nothing to do
  }

  @Override
  public void rollbackDropTable(Table table) throws MetaException {
    //nothing to do
  }

  @Override
  public void commitDropTable(Table table, boolean deleteData) throws MetaException {
    String tableName = getMapRDBTableName(table);
    boolean isExternal = MetaStoreUtils.isExternalTable(table);
    boolean exists = getMapRDBAdmin().tableExists(tableName);
    if (deleteData && !isExternal && exists) {
      getMapRDBAdmin().deleteTable(tableName);
    }
  }
}