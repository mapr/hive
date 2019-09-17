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

package org.apache.hive.conftool;

import org.apache.commons.io.FileUtils;
import org.apache.hadoop.hive.conf.HiveConf;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import static org.apache.hive.conftool.TestConfToolUtil.getPath;
import static org.apache.hive.conftool.TestConfToolUtil.getStringVal;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class SslDefaultTest {
  private static final HiveConf.ConfVars HS2_SSL_KEYSTORE_PATH = HiveConf.ConfVars.HIVE_SERVER2_SSL_KEYSTORE_PATH;
  private static final HiveConf.ConfVars HS2_SSL_KEYSTORE_PSWD = HiveConf.ConfVars.HIVE_SERVER2_SSL_KEYSTORE_PASSWORD;
  private static final HiveConf.ConfVars HS2_WEBUI_KEYSTORE_PATH =
      HiveConf.ConfVars.HIVE_SERVER2_WEBUI_SSL_KEYSTORE_PATH;
  private static final HiveConf.ConfVars HS2_WEBUI_KEYSTORE_PSWD =
      HiveConf.ConfVars.HIVE_SERVER2_WEBUI_SSL_KEYSTORE_PASSWORD;
  private static final HiveConf.ConfVars HMS_SSL_KEYSTORE_PATH = HiveConf.ConfVars.HIVE_METASTORE_SSL_KEYSTORE_PATH;
  private static final HiveConf.ConfVars HMS_SSL_KEYSTORE_PSWD = HiveConf.ConfVars.HIVE_METASTORE_SSL_KEYSTORE_PASSWORD;
  private static final HiveConf.ConfVars HMS_SSL_TRUSTSTORE_PATH = HiveConf.ConfVars.HIVE_METASTORE_SSL_TRUSTSTORE_PATH;
  private static final HiveConf.ConfVars HMS_SSL_TRUSTSTORE_PSWD =
      HiveConf.ConfVars.HIVE_METASTORE_SSL_TRUSTSTORE_PASSWORD;

  @Before
  public void init() throws IOException {
    String mapRConfPath = System.getenv("MAPR_HOME") + "conf";
    File mapRConfDir = new File(mapRConfPath);
    if (mapRConfDir.exists()) {
      FileUtils.deleteDirectory(mapRConfDir);
    }
    if (!mapRConfDir.mkdirs()) {
      Assert.fail(String.format("Error creating test dir %s", mapRConfPath));
    }
    File src = new File(getPath("maprsasl-enabled" + File.separator + "mapr-clusters.conf"));
    File tgt = new File(mapRConfPath + File.separator + "mapr-clusters.conf");
    FileUtils.copyFile(src, tgt);
    src = new File(getPath("maprsasl-enabled" + File.separator + "mapruserticket"));
    tgt = new File(mapRConfPath + File.separator + "mapruserticket");
    FileUtils.copyFile(src, tgt);
    src = new File(getPath("maprsasl-enabled" + File.separator + "ssl-client.xml"));
    tgt = new File(mapRConfPath + File.separator + "ssl-client.xml");
    FileUtils.copyFile(src, tgt);
  }

  @After
  public void cleanUp() throws IOException {
    String mapRConfPath = System.getenv("MAPR_HOME") + "conf";
    File mapRConfDir = new File(mapRConfPath);
    if (mapRConfDir.exists()) {
      FileUtils.deleteDirectory(mapRConfDir);
    }
  }

  @Test
  public void hs2SslTest() throws ParserConfigurationException, SAXException, IOException {
    String hiveSitePath = "ssl" + File.separator + "hive-site-hs2-ssl.xml";
    URL hiveSiteUrl = getClass().getClassLoader().getResource(hiveSitePath);
    if (hiveSiteUrl == null) {
      Assert.fail("Can't read " + hiveSitePath);
    }
    HiveConf.setHiveSiteLocation(hiveSiteUrl);
    HiveConf hiveConf = new HiveConf();
    hiveConf.addResource(hiveSiteUrl);
    URL sslClient = getClass().getClassLoader().getResource("maprsasl-enabled" + File.separator + "ssl-client.xml");
    if (sslClient == null) {
      Assert.fail("Can't read " + sslClient);
    }
    assertThat(hiveConf.getVar(HS2_SSL_KEYSTORE_PATH),
        equalTo(getStringVal(sslClient, "ssl.client.keystore.location")));
    assertThat(hiveConf.getVar(HS2_SSL_KEYSTORE_PSWD),
        equalTo(getStringVal(sslClient, "ssl.client.keystore.password")));
  }

  @Test
  public void hs2WebUiSslTest() throws ParserConfigurationException, SAXException, IOException {
    String hiveSitePath = "ssl" + File.separator + "hive-site-hs2-web-ui-ssl.xml";
    URL hiveSiteUrl = getClass().getClassLoader().getResource(hiveSitePath);
    if (hiveSiteUrl == null) {
      Assert.fail("Can't read " + hiveSitePath);
    }
    HiveConf.setHiveSiteLocation(hiveSiteUrl);
    HiveConf hiveConf = new HiveConf();
    hiveConf.addResource(hiveSiteUrl);
    URL sslClient = getClass().getClassLoader().getResource("maprsasl-enabled" + File.separator + "ssl-client.xml");
    if (sslClient == null) {
      Assert.fail("Can't read " + sslClient);
    }
    assertThat(hiveConf.getVar(HS2_WEBUI_KEYSTORE_PATH),
        equalTo(getStringVal(sslClient, "ssl.client.keystore.location")));
    assertThat(hiveConf.getVar(HS2_WEBUI_KEYSTORE_PSWD),
        equalTo(getStringVal(sslClient, "ssl.client.keystore.password")));
  }

  @Test
  public void hmsSslTest() throws ParserConfigurationException, SAXException, IOException {
    String hiveSitePath = "ssl" + File.separator + "hive-site-hms-ssl.xml";
    URL hiveSiteUrl = getClass().getClassLoader().getResource(hiveSitePath);
    if (hiveSiteUrl == null) {
      Assert.fail("Can't read " + hiveSitePath);
    }
    HiveConf.setHiveSiteLocation(hiveSiteUrl);
    HiveConf hiveConf = new HiveConf();
    hiveConf.addResource(hiveSiteUrl);
    URL sslClient = getClass().getClassLoader().getResource("maprsasl-enabled" + File.separator + "ssl-client.xml");
    if (sslClient == null) {
      Assert.fail("Can't read " + sslClient);
    }
    assertThat(hiveConf.getVar(HMS_SSL_KEYSTORE_PATH),
        equalTo(getStringVal(sslClient, "ssl.client.keystore.location")));
    assertThat(hiveConf.getVar(HMS_SSL_KEYSTORE_PSWD),
        equalTo(getStringVal(sslClient, "ssl.client.keystore.password")));
    assertThat(hiveConf.getVar(HMS_SSL_TRUSTSTORE_PATH),
        equalTo(getStringVal(sslClient, "ssl.client.truststore.location")));
    assertThat(hiveConf.getVar(HMS_SSL_TRUSTSTORE_PSWD),
        equalTo(getStringVal(sslClient, "ssl.client.truststore.password")));
  }
}
