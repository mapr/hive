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
package org.apache.hive.conftool;

import org.apache.hadoop.hive.conf.HiveConf.ConfVars;
import org.apache.hive.hcatalog.templeton.AppConfig;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;

import static org.apache.hadoop.hive.conf.HiveConf.ConfVars.USERS_IN_ADMIN_ROLE;
import static org.apache.hive.conftool.Security.NONE;
import static org.apache.hive.conftool.Security.MAPRSASL;
import static org.apache.hive.conftool.Security.CUSTOM;
import static org.apache.hive.conftool.TestConfToolUtil.parseFrom;

public class ConfToolTest {
  private static final Logger LOG = LoggerFactory.getLogger(ConfTool.class.getName());

  @Test public void setMaprSaslTrueTest() throws IOException, ParserConfigurationException, SAXException {
    Document doc = parseFrom("hive-site-003.xml");
    ConfTool.setMaprSasl(doc, MAPRSASL);
    Assert.assertEquals("true", ConfTool.getProperty(doc, ConfVars.METASTORE_USE_THRIFT_SASL));
  }

  @Test public void enableHs2HaTest() throws IOException, ParserConfigurationException, SAXException {
    Document doc = parseFrom("hive-site-010.xml");
    ConfTool.enableHs2Ha(doc, "node1,node2");
    Assert.assertEquals("true", ConfTool.getProperty(doc, ConfVars.HIVE_SERVER2_SUPPORT_DYNAMIC_SERVICE_DISCOVERY));
    Assert.assertEquals("node1,node2", ConfTool.getProperty(doc, ConfVars.HIVE_ZOOKEEPER_QUORUM));
  }

  @Test public void setMaprSaslTrueTest2() throws IOException, ParserConfigurationException, SAXException {
    Document doc = parseFrom("hive-site-005.xml");
    ConfTool.setMaprSasl(doc, MAPRSASL);
    Assert.assertEquals("true", ConfTool.getProperty(doc, ConfVars.METASTORE_USE_THRIFT_SASL));
  }

  @Test public void setMaprSaslFalseTest() throws IOException, ParserConfigurationException, SAXException {
    Document doc = parseFrom("hive-site-004.xml");
    ConfTool.setMaprSasl(doc, NONE);
    Assert.assertEquals("false", ConfTool.getProperty(doc, ConfVars.METASTORE_USE_THRIFT_SASL));
  }

  @Test public void setMaprSaslCustomTest() throws IOException, ParserConfigurationException, SAXException {
    Document doc = parseFrom("hive-site-038.xml");
    ConfTool.setMaprSasl(doc, CUSTOM);
    Assert.assertEquals("MyCustomValue", ConfTool.getProperty(doc, ConfVars.METASTORE_USE_THRIFT_SASL));
  }

  @Test public void enableEncryptionTrueTest() throws IOException, ParserConfigurationException, SAXException {
    Document doc = parseFrom("hive-site-008.xml");
    ConfTool.setEncryption(doc, MAPRSASL);
    Assert.assertEquals("auth-conf", ConfTool.getProperty(doc, ConfVars.HIVE_SERVER2_THRIFT_SASL_QOP));
  }

  @Test public void enableEncryptionFalseTest() throws IOException, ParserConfigurationException, SAXException {
    Document doc = parseFrom("hive-site-008.xml");
    ConfTool.setEncryption(doc, NONE);
    Assert.assertFalse(ConfTool.propertyExists(doc, ConfVars.HIVE_SERVER2_THRIFT_SASL_QOP));
  }

  @Test public void enableEncryptionCustomTest() throws IOException, ParserConfigurationException, SAXException {
    Document doc = parseFrom("hive-site-039.xml");
    ConfTool.setEncryption(doc, CUSTOM);
    Assert.assertEquals("MyCustomValue", ConfTool.getProperty(doc, ConfVars.HIVE_SERVER2_THRIFT_SASL_QOP));
  }

  @Test public void hiveMetaStoreSaslEnabledExistsTestTrue()
      throws ParserConfigurationException, IOException, SAXException {
    Document doc = parseFrom("hive-site-001.xml");
    Assert.assertTrue(ConfTool.propertyExists(doc, ConfVars.METASTORE_USE_THRIFT_SASL));
  }

  @Test public void propertyExistsTestFalse() throws ParserConfigurationException, IOException, SAXException {
    Document doc = parseFrom("hive-site-002.xml");
    Assert.assertFalse(ConfTool.propertyExists(doc, ConfVars.METASTORE_USE_THRIFT_SASL));
  }

  @Test public void getPropertyTest() throws ParserConfigurationException, IOException, SAXException {
    Document doc = parseFrom("hive-site-001.xml");
    Assert.assertEquals("true", ConfTool.getProperty(doc, ConfVars.METASTORE_USE_THRIFT_SASL));
    Assert.assertEquals("MAPRSASL", ConfTool.getProperty(doc, ConfVars.HIVE_SERVER2_AUTHENTICATION));
  }

  @Test public void setPropertyTest() throws ParserConfigurationException, IOException, SAXException {
    Document doc = parseFrom("hive-site-001.xml");
    String testValue = "AAbbbCCC";
    ConfTool.setProperty(doc, ConfVars.METASTORE_USE_THRIFT_SASL, testValue);
    Assert.assertEquals(testValue, ConfTool.getProperty(doc, ConfVars.METASTORE_USE_THRIFT_SASL));
  }

  @Test public void addPropertyTest() throws ParserConfigurationException, IOException, SAXException {
    Document doc = parseFrom("hive-site-002.xml");
    Assert.assertFalse(ConfTool.propertyExists(doc, ConfVars.METASTORE_USE_THRIFT_SASL));
    String testValue = "AAbbbCCC";
    ConfTool.addProperty(doc, ConfVars.METASTORE_USE_THRIFT_SASL, testValue);
    Assert.assertEquals(testValue, ConfTool.getProperty(doc, ConfVars.METASTORE_USE_THRIFT_SASL));
  }

  @Test public void addPropertyAlreadyExistsTest() throws SAXException, ParserConfigurationException, IOException {
    Document doc = parseFrom("hive-site-017.xml");
    String property = "test.property.to.add";
    String oldValue = "old.value";
    String newValue = "test.value.to.add";

    Assert.assertEquals(oldValue, ConfTool.getProperty(doc, property));
    ConfTool.addProperty(doc, property, newValue);
    Assert.assertEquals(newValue, ConfTool.getProperty(doc, property));
  }

  @Test public void getConfigurationNodeTest() throws ParserConfigurationException, IOException, SAXException {
    Document doc = parseFrom("hive-site-006.xml");
    Assert.assertNotNull(ConfTool.getConfigurationNode(doc));
  }

  @Test public void setMaprSaslTrueComplexTest() throws IOException, ParserConfigurationException, SAXException {
    Document doc = parseFrom("hive-site-006.xml");
    ConfTool.setMaprSasl(doc, MAPRSASL);
    Assert.assertEquals("true", ConfTool.getProperty(doc, ConfVars.METASTORE_USE_THRIFT_SASL));
  }

  @Test public void removePropertyTest() throws IOException, SAXException, ParserConfigurationException {
    Document doc = parseFrom("hive-site-011.xml");
    Assert.assertTrue(ConfTool.propertyExists(doc, ConfVars.HIVE_SERVER2_THRIFT_SASL_QOP));
    ConfTool.removeProperty(doc, ConfVars.HIVE_SERVER2_THRIFT_SASL_QOP);
    LOG.info(TestConfToolUtil.toString(doc));
    Assert.assertFalse(ConfTool.propertyExists(doc, ConfVars.HIVE_SERVER2_THRIFT_SASL_QOP));
  }

  @Test public void setConnectionUrlTest() throws IOException, ParserConfigurationException, SAXException {
    String connectionUrl = "jdbc:derby:;databaseName=/opt/mapr/hive/hive-2.1/bin/metastore_db;create=true";
    Document doc = parseFrom("hive-site-012.xml");
    ConfTool.setConnectionUrl(doc, connectionUrl);
    Assert.assertEquals(connectionUrl, ConfTool.getProperty(doc, ConfVars.METASTORECONNECTURLKEY));
  }

  @Test public void hs2WebUiPamSslSecurityOnTest() throws IOException, ParserConfigurationException, SAXException {
    Document doc = parseFrom("hive-site-013.xml");
    ConfTool.setHs2WebUiPamSsl(doc, MAPRSASL);
    Assert.assertEquals("true", ConfTool.getProperty(doc, ConfVars.HIVE_SERVER2_WEBUI_USE_PAM));
    Assert.assertEquals("true", ConfTool.getProperty(doc, ConfVars.HIVE_SERVER2_WEBUI_USE_SSL));
  }

  @Test public void hs2WebUiPamSslSecurityOffTest() throws IOException, ParserConfigurationException, SAXException {
    Document doc = parseFrom("hive-site-013.xml");
    ConfTool.setHs2WebUiPamSsl(doc, NONE);
    Assert.assertFalse(ConfTool.propertyExists(doc, ConfVars.HIVE_SERVER2_WEBUI_USE_PAM));
    Assert.assertFalse(ConfTool.propertyExists(doc, ConfVars.HIVE_SERVER2_WEBUI_USE_SSL));
  }

  @Test public void hs2WebUiPamSslSecurityCustomTest() throws IOException, ParserConfigurationException, SAXException {
    Document doc = parseFrom("hive-site-040.xml");
    ConfTool.setHs2WebUiPamSsl(doc, CUSTOM);
    Assert.assertEquals("MyCustomValue", ConfTool.getProperty(doc, ConfVars.HIVE_SERVER2_WEBUI_USE_PAM));
    Assert.assertEquals("MyCustomValue", ConfTool.getProperty(doc, ConfVars.HIVE_SERVER2_WEBUI_USE_SSL));
  }

  @Test public void webHCatSslSecurityOnTest()
      throws IOException, ParserConfigurationException, SAXException, TransformerException {
    Document doc = parseFrom("webhcat-site-001.xml");
    ConfTool.setWebHCatSsl(doc, MAPRSASL);
    Assert.assertEquals("true", ConfTool.getProperty(doc, AppConfig.USE_SSL));
  }

  @Test public void webHCatSslSecurityOffTest()
      throws IOException, ParserConfigurationException, SAXException, TransformerException {
    Document doc = parseFrom("webhcat-site-001.xml");
    ConfTool.setWebHCatSsl(doc, NONE);
    Assert.assertFalse(ConfTool.propertyExists(doc, AppConfig.USE_SSL));
  }

  @Test public void webHCatSslSecurityCustomTest()
      throws IOException, ParserConfigurationException, SAXException, TransformerException {
    Document doc = parseFrom("webhcat-site-005.xml");
    ConfTool.setWebHCatSsl(doc, CUSTOM);
    Assert.assertEquals("MyCustomValue", ConfTool.getProperty(doc, AppConfig.USE_SSL));
  }

  @Test public void hs2SslSecurityOnTest() throws IOException, ParserConfigurationException, SAXException {
    Document doc = parseFrom("hive-site-023.xml");
    ConfTool.setHs2Ssl(doc, MAPRSASL);
    Assert.assertEquals("true", ConfTool.getProperty(doc, ConfVars.HIVE_SERVER2_USE_SSL));
  }

  @Test public void hs2SslSecurityOffTest() throws IOException, ParserConfigurationException, SAXException {
    Document doc = parseFrom("hive-site-023.xml");
    ConfTool.setHs2Ssl(doc, NONE);
    Assert.assertFalse(ConfTool.propertyExists(doc, ConfVars.HIVE_SERVER2_USE_SSL));
  }

  @Test public void hs2SslSecurityCustomTest() throws IOException, ParserConfigurationException, SAXException {
    Document doc = parseFrom("hive-site-041.xml");
    ConfTool.setHs2Ssl(doc, CUSTOM);
    Assert.assertEquals("MyCustomValue", ConfTool.getProperty(doc, ConfVars.HIVE_SERVER2_USE_SSL));
  }

  @Test public void hMetaSslSecurityOnTest() throws IOException, ParserConfigurationException, SAXException {
    Document doc = parseFrom("hive-site-056.xml");
    ConfTool.setHMetaSsl(doc, MAPRSASL);
    Assert.assertEquals("true", ConfTool.getProperty(doc, ConfVars.HIVE_METASTORE_USE_SSL));
  }

  @Test public void hMetaSslSecurityOffTest() throws IOException, ParserConfigurationException, SAXException {
    Document doc = parseFrom("hive-site-056.xml");
    ConfTool.setHMetaSsl(doc, NONE);
    Assert.assertFalse(ConfTool.propertyExists(doc, ConfVars.HIVE_METASTORE_USE_SSL));
  }

  @Test public void hMetaSslSecurityCustomTest() throws IOException, ParserConfigurationException, SAXException {
    Document doc = parseFrom("hive-site-057.xml");
    ConfTool.setHMetaSsl(doc, CUSTOM);
    Assert.assertEquals("MyCustomValue", ConfTool.getProperty(doc, ConfVars.HIVE_METASTORE_USE_SSL));
  }

  @Test public void metaStoreUgiSecurityOnTest() throws IOException, ParserConfigurationException, SAXException {
    Document doc = parseFrom("hive-site-014.xml");
    ConfTool.setMetaStoreUgi(doc, MAPRSASL);
    Assert.assertEquals("false", ConfTool.getProperty(doc, ConfVars.METASTORE_EXECUTE_SET_UGI));
  }

  @Test public void metaStoreUgiSecurityOffTest() throws IOException, ParserConfigurationException, SAXException {
    Document doc = parseFrom("hive-site-015.xml");
    ConfTool.setMetaStoreUgi(doc, NONE);
    Assert.assertFalse(ConfTool.propertyExists(doc, ConfVars.METASTORE_EXECUTE_SET_UGI));
  }

  @Test public void metaStoreUgiSecurityCustomTest() throws IOException, ParserConfigurationException, SAXException {
    Document doc = parseFrom("hive-site-042.xml");
    ConfTool.setMetaStoreUgi(doc, CUSTOM);
    Assert.assertEquals("MyCustomValue", ConfTool.getProperty(doc, ConfVars.METASTORE_EXECUTE_SET_UGI));
  }

  @Test public void metaStoreAuthManagerSecurityOnTest()
      throws IOException, ParserConfigurationException, SAXException {
    Document doc = parseFrom("hive-site-014.xml");
    ConfTool.setMetaStoreAuthManager(doc, MAPRSASL);
    Assert.assertEquals("org.apache.hadoop.hive.ql.security.authorization.StorageBasedAuthorizationProvider",
        ConfTool.getProperty(doc, ConfVars.HIVE_METASTORE_AUTHORIZATION_MANAGER));
  }

  @Test public void metaStoreAuthManagerSecurityOffTest()
      throws IOException, ParserConfigurationException, SAXException {
    Document doc = parseFrom("hive-site-015.xml");
    ConfTool.setMetaStoreAuthManager(doc, NONE);
    Assert.assertFalse(ConfTool.propertyExists(doc, ConfVars.HIVE_METASTORE_AUTHORIZATION_MANAGER));
  }

  @Test public void metaStoreAuthManagerSecurityCustomTest()
      throws IOException, ParserConfigurationException, SAXException {
    Document doc = parseFrom("hive-site-043.xml");
    ConfTool.setMetaStoreAuthManager(doc, CUSTOM);
    Assert.assertEquals("MyCustomValue", ConfTool.getProperty(doc, ConfVars.HIVE_METASTORE_AUTHORIZATION_MANAGER));
  }

  @Test public void delPropertyTest()
      throws SAXException, TransformerException, ParserConfigurationException, IOException {
    String property = "test.property.to.delete";
    Document doc = parseFrom("hive-site-016.xml");
    ConfTool.delProperty(doc, property);
    Assert.assertFalse(ConfTool.propertyExists(doc, property));
  }

  @Test public void getPropertyFromFileTest() throws SAXException, ParserConfigurationException, IOException {
    Document doc = parseFrom("hive-site-021.xml");
    String property = "test.property.to.get";
    ConfTool.getProperty(doc, property);
    Assert.assertEquals("test.value", ConfTool.getProperty(doc, property));
  }

  @Test public void appendPropertyToExistingTest() throws ParserConfigurationException, IOException, SAXException {
    Document doc = parseFrom("hive-site-024.xml");
    ConfTool.appendProperty(doc, "append.to.existing.property", "newValue");
    Assert.assertEquals("oldValue,newValue", ConfTool.getProperty(doc, "append.to.existing.property"));
  }

  @Test public void appendPropertyToNonExistingTest() throws ParserConfigurationException, IOException, SAXException {
    Document doc = parseFrom("hive-site-024.xml");
    ConfTool.appendProperty(doc, "append.non.existing", "newValue");
    Assert.assertEquals("newValue", ConfTool.getProperty(doc, "append.non.existing"));
  }

  @Test public void appendPropertyToEmptyTest() throws ParserConfigurationException, IOException, SAXException {
    Document doc = parseFrom("hive-site-024.xml");
    ConfTool.appendProperty(doc, "append.to.empty.property", "newValue");
    Assert.assertEquals("newValue", ConfTool.getProperty(doc, "append.to.empty.property"));
  }

  @Test public void appendPropertyValueTheSameTest() throws ParserConfigurationException, IOException, SAXException {
    Document doc = parseFrom("hive-site-024.xml");
    ConfTool.appendProperty(doc, "append.the.existing.value", "newValue");
    Assert.assertEquals("oldValue,newValue", ConfTool.getProperty(doc, "append.the.existing.value"));
  }

  @Test public void appendPropertyValueSimilarValueTest()
      throws ParserConfigurationException, IOException, SAXException {
    Document doc = parseFrom("hive-site-024.xml");
    ConfTool.appendProperty(doc, "hive.users", "mapr");
    Assert.assertEquals("mapruser,mapr", ConfTool.getProperty(doc, "hive.users"));
  }

  @Test public void adminUserSecurityOnTest() throws IOException, SAXException, ParserConfigurationException {
    String adminUser = "mapr";
    Document doc = parseFrom("hive-site-029.xml");
    Assert.assertFalse(ConfTool.propertyExists(doc, USERS_IN_ADMIN_ROLE.varname));
    ConfTool.setAdminUser(doc, adminUser, MAPRSASL);
    Assert.assertEquals(adminUser, ConfTool.getProperty(doc, USERS_IN_ADMIN_ROLE.varname));
  }

  @Test public void adminUserSecurityOffTest() throws IOException, SAXException, ParserConfigurationException {
    String adminUser = "mapr";
    Document doc = parseFrom("hive-site-030.xml");
    Assert.assertEquals(adminUser, ConfTool.getProperty(doc, USERS_IN_ADMIN_ROLE.varname));
    ConfTool.setAdminUser(doc, adminUser, NONE);
    Assert.assertFalse(ConfTool.propertyExists(doc, USERS_IN_ADMIN_ROLE.varname));
  }

  @Test public void adminUserSecurityCustomTest() throws IOException, SAXException, ParserConfigurationException {
    String adminUser = "MyCustomValue";
    Document doc = parseFrom("hive-site-044.xml");
    ConfTool.setAdminUser(doc, adminUser, CUSTOM);
    Assert.assertEquals(adminUser, ConfTool.getProperty(doc, USERS_IN_ADMIN_ROLE.varname));
  }

  @Test public void restrictedListSecurityOnTest() throws IOException, SAXException, ParserConfigurationException {
    Document doc = parseFrom("hive-site-026.xml");

    ConfTool.setRestrictedList(doc, MAPRSASL);
    Assert.assertEquals("hive.security.authenticator.manager,hive.security.authorization.manager,"
        + "hive.security.metastore.authorization.manager,hive.security.metastore.authenticator.manager,"
        + "hive.users.in.admin.role,hive.server2.xsrf.filter.enabled,hive.security.authorization.enabled,"
        + "hive.server2.authentication.ldap.baseDN,hive.server2.authentication.ldap.url,"
        + "hive.server2.authentication.ldap.Domain,hive.server2.authentication.ldap.groupDNPattern,"
        + "hive.server2.authentication.ldap.groupFilter,hive.server2.authentication.ldap.userDNPattern,"
        + "hive.server2.authentication.ldap.userFilter,hive.server2.authentication.ldap.groupMembershipKey,"
        + "hive.server2.authentication.ldap.userMembershipKey,hive.server2.authentication.ldap.groupClassKey,"
        + "hive.server2.authentication.ldap.customLDAPQuery,"
        + "hive.exec.pre.hooks,hive.exec.post.hooks,hive.exec.failure.hooks,hive.exec.query.redactor.hooks,"
        + "hive.semantic.analyzer.hook,hive.query.lifetime.hooks,hive.exec.driver.run.hooks,"
        + "hive.server2.session.hook", ConfTool.getProperty(doc, "hive.conf.restricted.list"));
  }

  @Test public void restrictedListSecurityOffTest() throws IOException, SAXException, ParserConfigurationException {
    Document doc = parseFrom("hive-site-027.xml");
    ConfTool.setRestrictedList(doc, NONE);
    Assert.assertFalse(ConfTool.propertyExists(doc, "hive.conf.restricted.list"));
  }

  @Test public void restrictedListSecurityCustomTest() throws IOException, SAXException, ParserConfigurationException {
    Document doc = parseFrom("hive-site-045.xml");
    ConfTool.setRestrictedList(doc, CUSTOM);
    Assert.assertEquals("MyCustomValue", ConfTool.getProperty(doc, "hive.conf.restricted.list"));
  }

  @Test public void setFallbackAuthorizerSecurityOnTest()
      throws IOException, SAXException, ParserConfigurationException, TransformerException {
    Document doc = parseFrom("hive-site-047.xml");
    ConfTool.setFallbackAuthorizer(doc, MAPRSASL);
    Assert.assertEquals("true", ConfTool.getProperty(doc, "hive.security.authorization.enabled"));
    Assert
        .assertEquals("org.apache.hadoop.hive.ql.security.authorization.plugin.fallback.FallbackHiveAuthorizerFactory",
            ConfTool.getProperty(doc, "hive.security.authorization.manager"));
  }

  @Test public void setFallbackAuthorizerSecurityOffTest()
      throws IOException, SAXException, ParserConfigurationException, TransformerException {
    Document doc = parseFrom("hive-site-048.xml");
    ConfTool.setFallbackAuthorizer(doc, NONE);
    Assert.assertFalse(ConfTool.propertyExists(doc, "hive.security.authorization.enabled"));
    Assert.assertFalse(ConfTool.propertyExists(doc, "hive.security.authorization.manager"));
  }

  @Test public void setFallbackAuthorizerSecurityCustomTest()
      throws IOException, SAXException, ParserConfigurationException, TransformerException {
    Document doc = parseFrom("hive-site-049.xml");
    ConfTool.setFallbackAuthorizer(doc, CUSTOM);
    Assert.assertEquals("MyCustomValue", ConfTool.getProperty(doc, "hive.security.authorization.enabled"));
    Assert.assertEquals("MyCustomValue", ConfTool.getProperty(doc, "hive.security.authorization.manager"));
  }

  @Test public void configureHs2MetricsEnabled() throws IOException, SAXException, ParserConfigurationException {
    Document doc = parseFrom("hive-site-050.xml");
    ConfTool.configureHs2Metrics(doc, true);
    Assert.assertEquals("true", ConfTool.getProperty(doc, "hive.server2.metrics.enabled"));
  }

  @Test public void configureHs2MetricsDisabled() throws IOException, SAXException, ParserConfigurationException {
    Document doc = parseFrom("hive-site-051.xml");
    ConfTool.configureHs2Metrics(doc, false);
    Assert.assertEquals("false", ConfTool.getProperty(doc, "hive.server2.metrics.enabled"));
  }

  @Test public void configureMetastoreMetricsEnabled() throws IOException, SAXException, ParserConfigurationException {
    Document doc = parseFrom("hive-site-052.xml");
    ConfTool.configureMetastoreMetrics(doc, true);
    Assert.assertEquals("true", ConfTool.getProperty(doc, "hive.metastore.metrics.enabled"));
  }

  @Test public void configureMetastoreMetricsDisabled() throws IOException, SAXException, ParserConfigurationException {
    Document doc = parseFrom("hive-site-053.xml");
    ConfTool.configureMetastoreMetrics(doc, false);
    Assert.assertEquals("false", ConfTool.getProperty(doc, "hive.metastore.metrics.enabled"));
  }

  @Test public void configureMetricsReportEnabled() throws IOException, SAXException, ParserConfigurationException {
    Document doc = parseFrom("hive-site-054.xml");
    ConfTool.configureMetricsReporterType(doc, true, "JSON_FILE,JMX");
    ConfTool.configureMetricsFileLocation(doc, true, "/tmp/hive_report.json");
    Assert.assertEquals("JSON_FILE,JMX", ConfTool.getProperty(doc, "hive.service.metrics.reporter"));
    Assert.assertEquals("/tmp/hive_report.json", ConfTool.getProperty(doc, "hive.service.metrics.file.location"));
  }

  @Test public void configureMetricsReportDisabled() throws IOException, SAXException, ParserConfigurationException {
    Document doc = parseFrom("hive-site-055.xml");
    ConfTool.configureMetricsReporterType(doc, false, "JSON_FILE,JMX");
    ConfTool.configureMetricsFileLocation(doc, false, "/tmp/hive_report.json");
    Assert.assertFalse(ConfTool.propertyExists(doc, "hive.service.metrics.reporter"));
    Assert.assertFalse(ConfTool.propertyExists(doc, "hive.service.metrics.file.location"));
  }
}
