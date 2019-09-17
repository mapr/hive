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
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hive.conf.HiveConf;
import org.apache.hadoop.hive.metastore.conf.MetastoreConf;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;

import static org.apache.hive.conftool.TestConfToolUtil.getBoolVal;
import static org.apache.hive.conftool.TestConfToolUtil.getPath;
import static org.apache.hive.conftool.TestConfToolUtil.getStringVal;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

@RunWith(Parameterized.class) public class KerberosAuthDefaultTest {

  private String group;
  private boolean isHs2AuthInHiveSite;
  private boolean isHmsAuthInHiveSite;
  private boolean isSaslEnabledInHiveSite;
  private String hs2Auth;
  private String hmsAuth;
  private boolean saslEnabled;

  private static final HiveConf.ConfVars HS2_AUTH = HiveConf.ConfVars.HIVE_SERVER2_AUTHENTICATION;
  private static final MetastoreConf.ConfVars HMS_AUTH = MetastoreConf.ConfVars.METASTORE_AUTHENTICATION;
  private static final MetastoreConf.ConfVars USE_THRIFT_SASL = MetastoreConf.ConfVars.USE_THRIFT_SASL;

  public KerberosAuthDefaultTest(String group, boolean isHs2AuthInHiveSite, boolean isHmsAuthInHiveSite,
      boolean isSaslEnabledInHiveSite, String hs2Auth, String hmsAuth, boolean saslEnabled) {
    this.group = group;
    this.isHs2AuthInHiveSite = isHs2AuthInHiveSite;
    this.isHmsAuthInHiveSite = isHmsAuthInHiveSite;
    this.isSaslEnabledInHiveSite = isSaslEnabledInHiveSite;
    this.hs2Auth = hs2Auth;
    this.hmsAuth = hmsAuth;
    this.saslEnabled = saslEnabled;
  }

  @Parameterized.Parameters
  public static Collection configurations() {
    return Arrays.asList(new Object[][] {
        // hs2 auth = PAM on Kerberos cluster
        { "PAM", true, true, true,    "PAM", "MAPRSASL", true },
        { "PAM", true, true, false,   "PAM", "MAPRSASL", true },
        { "PAM", true, false, true,   "PAM", "MAPRSASL", true },
        { "PAM", true, false, false,  "PAM", "MAPRSASL", true },
        { "PAM", false, true, true,   "PAM", "MAPRSASL", true },
        { "PAM", false, true, false,  "PAM", "MAPRSASL", true },
        { "PAM", false, false, true,  "PAM", "MAPRSASL", true },
        { "PAM", false, false, false, "PAM", "MAPRSASL", true },

        // hs2 auth = LDAP on Kerberos cluster
        { "LDAP", true, true, true,    "LDAP", "MAPRSASL", true },
        { "LDAP", true, true, false,   "LDAP", "MAPRSASL", true },
        { "LDAP", true, false, true,   "LDAP", "MAPRSASL", true },
        { "LDAP", true, false, false,  "LDAP", "MAPRSASL", true },
        { "LDAP", false, true, true,   "PAM", "MAPRSASL", true },
        { "LDAP", false, true, false,  "PAM", "MAPRSASL", true },
        { "LDAP", false, false, true,  "PAM", "MAPRSASL", true },
        { "LDAP", false, false, false, "PAM", "MAPRSASL", true },

        // hs2 auth = MAPRSASL on Kerberos cluster
        { "MAPRSASL", true, true, true,    "MAPRSASL", "MAPRSASL", true },
        { "MAPRSASL", true, true, false,   "MAPRSASL", "MAPRSASL", true },
        { "MAPRSASL", true, false, true,   "MAPRSASL", "MAPRSASL", true },
        { "MAPRSASL", true, false, false,  "MAPRSASL", "MAPRSASL", true },
        { "MAPRSASL", false, true, true,   "PAM", "MAPRSASL", true },
        { "MAPRSASL", false, true, false,  "PAM", "MAPRSASL", true },
        { "MAPRSASL", false, false, true,  "PAM", "MAPRSASL", true },
        { "MAPRSASL", false, false, false, "PAM", "MAPRSASL", true },

        // hs2 auth = KERBEROS on Kerberos cluster
        { "KERBEROS", true, true, true,    "KERBEROS", "KERBEROS", true },
        { "KERBEROS", true, true, false,   "KERBEROS", "KERBEROS", true },
        { "KERBEROS", true, false, true,   "KERBEROS", "KERBEROS", true },
        { "KERBEROS", true, false, false,  "KERBEROS", "KERBEROS", true },
        { "KERBEROS", false, true, true,   "PAM", "KERBEROS", true },
        { "KERBEROS", false, true, false,  "PAM", "KERBEROS", true },
        { "KERBEROS", false, false, true,  "PAM", "MAPRSASL", true },
        { "KERBEROS", false, false, false, "PAM", "MAPRSASL", true },

        // hs2 auth = NONE on Kerberos cluster
        { "NONE", true, true, true,    "NONE", "MAPRSASL", false },
        { "NONE", true, true, false,   "NONE", "MAPRSASL", true },
        { "NONE", true, false, true,   "NONE", "MAPRSASL", false },
        { "NONE", true, false, false,  "NONE", "MAPRSASL", true },
        { "NONE", false, true, true,   "PAM", "MAPRSASL", false },
        { "NONE", false, true, false,  "PAM", "MAPRSASL", true },
        { "NONE", false, false, true,  "PAM", "MAPRSASL", false },
        { "NONE", false, false, false, "PAM", "MAPRSASL", true },

    });
  }

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
    File src = new File(getPath("krb-enabled" + File.separator + "mapr-clusters.conf"));
    File tgt = new File(mapRConfPath + File.separator + "mapr-clusters.conf");
    FileUtils.copyFile(src, tgt);
  }

  @After
  public void cleanUp() throws IOException {
    String mapRConfPath = System.getenv("MAPR_HOME") + "conf";
    File mapRConfDir = new File(mapRConfPath);
    if(mapRConfDir.exists()) {
      FileUtils.deleteDirectory(mapRConfDir);
    }
  }

  @Test
  public void testDefaultConf() throws ParserConfigurationException, SAXException, IOException {
    String path =
        "default-conf/secure/" + buildHiveSiteName(group, isHs2AuthInHiveSite, isHmsAuthInHiveSite, isSaslEnabledInHiveSite);
    URL url = getClass().getClassLoader().getResource(path);
    if (url == null) {
      Assert.fail("Can't read " + path);
    }
    HiveConf.setHiveSiteLocation(url);
    HiveConf hiveConf = new HiveConf();
    hiveConf.addResource(url);

    MetastoreConf.setHiveSiteLocation(url);
    Configuration metaConf = MetastoreConf.newMetastoreConf();

    if (isHs2AuthInHiveSite) {
      assertThat(hiveConf.getVar(HS2_AUTH), equalTo(getStringVal(url, HS2_AUTH)));
    }

    if (isHmsAuthInHiveSite) {
      assertThat(MetastoreConf.getVar(metaConf, HMS_AUTH), equalTo(getStringVal(url, HMS_AUTH.getVarname())));
    }

    if (isSaslEnabledInHiveSite) {
      assertThat(MetastoreConf.getBoolVar(metaConf, USE_THRIFT_SASL), equalTo(getBoolVal(url, USE_THRIFT_SASL.getVarname())));
    }

    assertThat(hiveConf.getVar(HS2_AUTH), equalTo(hs2Auth));
    assertThat(MetastoreConf.getVar(metaConf, HMS_AUTH), equalTo(hmsAuth));
    assertThat(MetastoreConf.getBoolVar(metaConf, USE_THRIFT_SASL), equalTo(saslEnabled));
  }

  private static String buildHiveSiteName(String group, boolean isHs2AuthInHiveSite, boolean isHmsAuthInHiveSite,
      boolean isSaslEnabledAuthInHiveSite) {
    return "hive-site-" + group + "-" + convert(isHs2AuthInHiveSite) + convert(isHmsAuthInHiveSite) + convert(
        isSaslEnabledAuthInHiveSite) + ".xml";
  }

  private static String convert(boolean value) {
    return value ? "Y" : "N";
  }
}
