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
package org.apache.hadoop.hive.ql.security.authorization;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hive.metastore.Warehouse;
import org.apache.hadoop.hive.metastore.api.Database;
import org.apache.hadoop.hive.metastore.conf.MetastoreConf;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.security.HadoopDefaultMetastoreAuthenticator;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestStorageBasedAuthorizationProviderWarehouse {

  @Test public void testWarehouseUpdate() throws HiveException {
    String testWarehouseDir = System.getProperty("test.warehouse.dir");
    MetastoreConf.ConfVars warehouse = MetastoreConf.ConfVars.WAREHOUSE;

    StorageBasedAuthorizationProvider sbap = new StorageBasedAuthorizationProvider();
    Configuration metastoreConf = MetastoreConf.newMetastoreConf();
    metastoreConf.set("fs.defaultFS", "file:///");

    sbap.setConf(metastoreConf);
    sbap.setAuthenticator(new HadoopDefaultMetastoreAuthenticator());
    Privilege[] privileges = new Privilege[1];
    Privilege privilege = new Privilege();
    privilege.setPriv(PrivilegeType.SELECT);
    privileges[0] = privilege;
    Database db = new Database();
    db.setName(Warehouse.DEFAULT_DATABASE_NAME);

    MetastoreConf.setVar(metastoreConf, warehouse, testWarehouseDir + "/test1");
    sbap.authorizeDbLevelOperations(privileges, privileges, null, null);
    assertThat(normalize(sbap.getDbLocation(db).toString()),
        is(normalize(MetastoreConf.getVar(metastoreConf, warehouse))));

    MetastoreConf.setVar(metastoreConf, warehouse, testWarehouseDir + "/test2");
    sbap.authorizeDbLevelOperations(privileges, privileges, null, null);
    assertThat(normalize(sbap.getDbLocation(db).toString()),
        is(normalize(MetastoreConf.getVar(metastoreConf, warehouse))));

    MetastoreConf.setVar(metastoreConf, warehouse, testWarehouseDir + "/test3");
    sbap.authorizeDbLevelOperations(privileges, privileges, null, null);
    assertThat(normalize(sbap.getDbLocation(db).toString()),
        is(normalize(MetastoreConf.getVar(metastoreConf, warehouse))));
  }

  private static String normalize(String path) {
    return path.replace("///", "/").replace("//", "/");
  }
}
