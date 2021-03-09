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
package org.apache.hive.service.servlet;

import org.apache.hadoop.hive.conf.HiveConf;
import org.apache.hadoop.hive.metastore.HiveMetaStoreClient;
import org.apache.hadoop.hive.metastore.api.FieldSchema;
import org.apache.hadoop.hive.metastore.api.SerDeInfo;
import org.apache.hadoop.hive.metastore.api.StorageDescriptor;
import org.apache.hadoop.hive.metastore.api.Table;
import org.apache.hadoop.hive.ql.io.HiveInputFormat;
import org.apache.hadoop.hive.ql.io.HiveOutputFormat;
import org.apache.hadoop.hive.serde.serdeConstants;
import org.apache.hadoop.hive.serde2.lazy.LazySimpleSerDe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Returns OK status for the HiveServer2 service
 */
public class HiveServer2StatusServlet extends HttpServlet {
  private static final Logger LOG = LoggerFactory.getLogger(HiveServer2StatusServlet.class);

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.setContentType("text/plain"); // sets the content type
    response.setCharacterEncoding("UTF-8"); // sets the encoding
    try {
      HiveMetaStoreClient client = new HiveMetaStoreClient(new HiveConf(this.getClass()));
      String defaultDb = "default";
      Table table = new Table();
      table.setTemporary(true);
      table.setDbName(defaultDb);
      String tblName = String.format("status_%s", System.currentTimeMillis());
      table.setTableName(tblName);
      List<FieldSchema> cols = new ArrayList<>();
      cols.add(new FieldSchema("c1", serdeConstants.INT_TYPE_NAME, ""));
      addSd(cols, table);
      client.createTable(table);
      LOG.info("Created temp table {}", tblName);
      client.dropTable(defaultDb, tblName);
      LOG.info("Dropped temp table {}" , tblName);
    } catch (Throwable e) {
      response.getWriter().write("2"); // running, not responding to simple test
      return;
    }
    response.getWriter().write("0"); // running and simple interaction test succeeded
  }

  private void addSd(List<FieldSchema> cols, Table tbl) {
    StorageDescriptor sd = new StorageDescriptor();
    sd.setCols(cols);
    sd.setCompressed(false);
    sd.setNumBuckets(1);
    sd.setParameters(new HashMap<>());
    sd.setBucketCols(new ArrayList<>());
    sd.setSerdeInfo(new SerDeInfo());
    sd.getSerdeInfo().setName(tbl.getTableName());
    sd.getSerdeInfo().setParameters(new HashMap<>());
    sd.getSerdeInfo().getParameters().put(serdeConstants.SERIALIZATION_FORMAT, "1");
    sd.setSortCols(new ArrayList<>());
    sd.getSerdeInfo().setSerializationLib(LazySimpleSerDe.class.getName());
    sd.setInputFormat(HiveInputFormat.class.getName());
    sd.setOutputFormat(HiveOutputFormat.class.getName());
    tbl.setSd(sd);
  }
}
