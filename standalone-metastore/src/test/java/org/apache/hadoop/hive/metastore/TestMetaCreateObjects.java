/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to you under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.hadoop.hive.metastore;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.LocalFileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hive.metastore.annotation.MetastoreUnitTest;
import org.apache.hadoop.hive.metastore.api.Database;
import org.apache.hadoop.hive.metastore.api.MetaException;
import org.apache.hadoop.hive.metastore.conf.MetastoreConf;
import org.apache.hadoop.util.StringUtils;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Category(MetastoreUnitTest.class) public class TestMetaCreateObjects {

  private static final Logger LOG = LoggerFactory.getLogger(TestMetaCreateObjects.class);
  protected static HiveMetaStoreClient client;
  protected static Configuration conf;
  protected static Warehouse warehouse;

  @BeforeClass public static void setUp() throws Exception {
    conf = MetastoreConf.newMetastoreConf();
    MetastoreConf.setBoolVar(conf, MetastoreConf.ConfVars.METASTORE_ALLOW_NEW_DB_IN_EXISTING_DIRECTORY, false);
    MetastoreConf.setVar(conf, MetastoreConf.ConfVars.CONNECT_URL_KEY,
        "jdbc:derby:memory:${test.tmp.dir}/junit_metastore_db_test_meta_create_objects;create=true");
    MetastoreConf
        .setClass(conf, MetastoreConf.ConfVars.EXPRESSION_PROXY_CLASS, MockPartitionExpressionForMetastore.class,
            PartitionExpressionProxy.class);
    MetaStoreTestUtils.setConfForStandloneMode(conf);
    warehouse = new Warehouse(conf);
    client = new HiveMetaStoreClient(conf);
  }

  @AfterClass public static void tearDown() {
    try {
      client.close();
    } catch (Throwable e) {
      LOG.info("Unable to close metastore");
      LOG.info(StringUtils.stringifyException(e));
      throw e;
    }
  }

  @Test public void testDataBaseLocationAlreadyExistsNegative() throws Exception {
    String dbName = "db";
    FileSystem fs = new LocalFileSystem();
    fs.initialize(fs.getWorkingDirectory().toUri(), conf);
    String dbLocation = "/tmp/" + dbName;
    Path dbLocationPath = new Path(dbLocation);
    fs.mkdirs(dbLocationPath);

    Database db = new Database();
    db.setName(dbName);
    db.setLocationUri(dbLocation);
    try {
      client.createDatabase(db);
      Assert.fail("should throw exception.");
    } catch (Exception e) {
      Assert.assertTrue("unexpected MetaException",
          e.getMessage().contains("Failed to create database. Database directory already exists"));
    }
  }

  @Test public void testDataBaseLocationAlreadyExistsPositive() throws Exception {
    String dbName = "db";
    FileSystem fs = new LocalFileSystem();
    fs.initialize(fs.getWorkingDirectory().toUri(), conf);
    String dbLocation = "/tmp/" + dbName;
    Path dbLocationPath = new Path(dbLocation);
    if (fs.exists(dbLocationPath)) {
      fs.delete(dbLocationPath, true);
    }

    Database db = new Database();
    db.setName(dbName);
    db.setLocationUri(dbLocation);
    try {
      client.createDatabase(db);
    } catch (MetaException e) {
      Assert.fail("should not throw exception: " + e.getMessage());
    }
    client.dropDatabase(dbName, true, true);
  }
}
