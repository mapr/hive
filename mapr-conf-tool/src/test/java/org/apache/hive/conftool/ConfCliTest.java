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

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;

import static org.apache.hadoop.hive.conf.HiveConf.ConfVars.USERS_IN_ADMIN_ROLE;
import static org.apache.hive.conftool.TestConfToolUtil.getPath;

public class ConfCliTest {

  @Rule public ExpectedException thrown = ExpectedException.none();

  private ByteArrayOutputStream baos = new ByteArrayOutputStream();

  @Before public void init() {
    System.setOut(new PrintStream(baos));
  }

  private String output() {
    return baos.toString();
  }

  @Test public void printHelpTest()
      throws ParserConfigurationException, TransformerException, SAXException, IOException {
    ConfCli.main(new String[] { "--help" });
    Assert.assertTrue(output().contains("Print help information"));
  }

  @Test public void noArgumentsTest()
      throws ParserConfigurationException, TransformerException, SAXException, IOException {
    ConfCli.main(new String[] { "" });
    Assert.assertTrue(output().contains("Print help information"));
  }

  @Test public void nullArgumentsTest()
      throws ParserConfigurationException, TransformerException, SAXException, IOException {
    ConfCli.main(null);
    Assert.assertTrue(output().contains("Print help information"));
  }

  @Test public void parsingErrorTest()
      throws ParserConfigurationException, TransformerException, SAXException, IOException {
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("Parsing failed");
    ConfCli.main(new String[] { "--lhjlk", "-/--l)9" });
  }

  @Test public void addPropertyTest()
      throws ParserConfigurationException, TransformerException, SAXException, IOException {
    String pathToHiveSite = getPath("hive-site-018.xml");
    String property = "test.property";
    String value = "test.value";
    ConfCli.main(new String[] { "--addProperty", property + "=" + value, "--path", pathToHiveSite });
    Assert.assertTrue(ConfTool.exists(pathToHiveSite, property));
    Assert.assertFalse(output().contains("Print help information"));
  }

  @Test public void addPropertyIncorrectArgumentsTest()
      throws ParserConfigurationException, TransformerException, SAXException, IOException {
    String pathToHiveSite = getPath("hive-site-022.xml");
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("Missing argument for option");
    ConfCli.main(new String[] { "--addProperty", "--path", pathToHiveSite });
    Assert.assertTrue(output().contains("Print help information"));
  }

  @Test public void adminUserSecurityOnTest()
      throws ParserConfigurationException, TransformerException, SAXException, IOException {
    String pathToHiveSite = getPath("hive-site-024.xml");
    String adminUser = "mapr";
    ConfCli.main(new String[] { "--adminUser", adminUser, "--path", pathToHiveSite, "--security", "true" });
    Assert.assertEquals("mapr", ConfTool.getProperty(pathToHiveSite, USERS_IN_ADMIN_ROLE.varname));
    Assert.assertFalse(output().contains("Print help information"));
  }

  @Test public void adminUserSecurityOffTest()
      throws ParserConfigurationException, TransformerException, SAXException, IOException {
    String pathToHiveSite = getPath("hive-site-025.xml");
    String adminUser = "mapr";
    ConfCli.main(new String[] { "--adminUser", adminUser, "--path", pathToHiveSite, "--security", "false" });
    Assert.assertFalse(ConfTool.exists(pathToHiveSite, USERS_IN_ADMIN_ROLE.varname));
    Assert.assertFalse(output().contains("Print help information"));
  }

  @Test public void adminUserSecurityCustomTest()
      throws ParserConfigurationException, TransformerException, SAXException, IOException {
    String pathToHiveSite = getPath("hive-site-031.xml");
    String adminUser = "mapr";
    ConfCli.main(new String[] { "--adminUser", adminUser, "--path", pathToHiveSite, "--security", "custom" });
    Assert.assertTrue(ConfTool.exists(pathToHiveSite, USERS_IN_ADMIN_ROLE.varname));
    Assert.assertFalse(output().contains("Print help information"));
  }

  @Test public void appendPropertyTest()
      throws ParserConfigurationException, TransformerException, SAXException, IOException {
    String pathToHiveSite = getPath("hive-site-028.xml");
    String property = "cli.test";
    String value = "mapr";
    ConfCli.main(new String[] { "--appendProperty", property + "=" + value, "--path", pathToHiveSite });
    Assert.assertEquals("mapruser,mapr", ConfTool.getProperty(pathToHiveSite, property));
    Assert.assertFalse(output().contains("Print help information"));
  }

  @Test public void restrictedListSecurityOnTest()
      throws ParserConfigurationException, TransformerException, SAXException, IOException {
    String pathToHiveSite = getPath("hive-site-046.xml");
    ConfCli.main(new String[] { "--restrictedList", "--security", "true", "--path", pathToHiveSite });
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
        + "hive.server2.session.hook", ConfTool.getProperty(pathToHiveSite, "hive.conf.restricted.list"));
    Assert.assertFalse(output().contains("Print help information"));
  }

  @Test public void restrictedListSecurityOffTest()
      throws ParserConfigurationException, TransformerException, SAXException, IOException {
    String pathToHiveSite = getPath("hive-site-027.xml");
    ConfCli.main(new String[] { "--restrictedList", "--security", "false", "--path", pathToHiveSite });
    Assert.assertFalse(ConfTool.exists(pathToHiveSite, "hive.conf.restricted.list"));
    Assert.assertFalse(output().contains("Print help information"));
  }

  @Test public void restrictedListSecurityCustomTest()
      throws ParserConfigurationException, TransformerException, SAXException, IOException {
    String pathToHiveSite = getPath("hive-site-032.xml");
    ConfCli.main(new String[] { "--restrictedList", "--security", "custom", "--path", pathToHiveSite });
    Assert.assertTrue(ConfTool.exists(pathToHiveSite, "hive.conf.restricted.list"));
    Assert.assertFalse(output().contains("Print help information"));
  }

  @Test public void delPropertyTest()
      throws ParserConfigurationException, TransformerException, SAXException, IOException {
    String pathToHiveSite = getPath("hive-site-019.xml");
    String property = "test.property.to.delete";
    Assert.assertTrue(ConfTool.exists(pathToHiveSite, property));
    ConfCli.main(new String[] { "--delProperty", property, "--path", pathToHiveSite });
    Assert.assertFalse(ConfTool.exists(pathToHiveSite, property));
  }

  @Test public void delPropertyIncorrectArgumentsTest()
      throws ParserConfigurationException, TransformerException, SAXException, IOException {
    String pathToHiveSite = getPath("hive-site-022.xml");
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("Missing argument for option");
    ConfCli.main(new String[] { "--delProperty", "--path", pathToHiveSite });
    Assert.assertTrue(output().contains("Print help information"));
  }

  @Test public void getPropertyTest()
      throws ParserConfigurationException, TransformerException, SAXException, IOException {
    String pathToHiveSite = getPath("hive-site-021.xml");
    String property = "test.property.to.get";
    ConfCli.main(new String[] { "--getProperty", property, "--path", pathToHiveSite });
    Assert.assertEquals("test.value", output());
  }

  @Test public void getPropertyIncorrectArgumentsTest()
      throws ParserConfigurationException, TransformerException, SAXException, IOException {
    String pathToHiveSite = getPath("hive-site-021.xml");
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("Missing argument for option");
    ConfCli.main(new String[] { "--getProperty", "--path", pathToHiveSite });
  }

  @Test public void getPropertyNonexistentProperty()
      throws ParserConfigurationException, TransformerException, SAXException, IOException {
    String pathToHiveSite = getPath("hive-site-021.xml");
    String property = "test.nonexistent.property";
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("Property does not exist!");
    ConfCli.main(new String[] { "--getProperty", property, "--path", pathToHiveSite });
  }

  @Test public void configureSecurityEnabledTest()
      throws ParserConfigurationException, TransformerException, SAXException, IOException {
    String pathToHiveSite = getPath("hive-site-020.xml");
    ConfCli.main(new String[] { "--security", "true", "--maprsasl", "--path", pathToHiveSite });
    Assert.assertTrue(ConfTool.exists(pathToHiveSite, "hive.metastore.sasl.enabled"));
    Assert.assertTrue(ConfTool.exists(pathToHiveSite, "hive.server2.thrift.sasl.qop"));
    Assert.assertTrue(ConfTool.exists(pathToHiveSite, "hive.metastore.execute.setugi"));
    Assert.assertTrue(ConfTool.exists(pathToHiveSite, "hive.security.metastore.authorization.manager"));
    Assert.assertEquals("true", ConfTool.getProperty(pathToHiveSite, "hive.metastore.sasl.enabled"));
    Assert.assertEquals("auth-conf", ConfTool.getProperty(pathToHiveSite, "hive.server2.thrift.sasl.qop"));
    Assert.assertEquals("false", ConfTool.getProperty(pathToHiveSite, "hive.metastore.execute.setugi"));
    Assert.assertEquals("org.apache.hadoop.hive.ql.security.authorization.StorageBasedAuthorizationProvider",
        ConfTool.getProperty(pathToHiveSite, "hive.security.metastore.authorization.manager"));
  }

  @Test public void configureSecurityDisabledTest()
      throws ParserConfigurationException, TransformerException, SAXException, IOException {
    String pathToHiveSite = getPath("hive-site-022.xml");
    ConfCli.main(new String[] { "--security", "false", "--maprsasl", "--path", pathToHiveSite });
    Assert.assertTrue(ConfTool.exists(pathToHiveSite, "hive.metastore.sasl.enabled"));
    Assert.assertFalse(ConfTool.exists(pathToHiveSite, "hive.server2.thrift.sasl.qop"));
    Assert.assertFalse(ConfTool.exists(pathToHiveSite, "hive.metastore.execute.setugi"));
    Assert.assertFalse(ConfTool.exists(pathToHiveSite, "hive.security.metastore.authorization.manager"));
    Assert.assertEquals("false", ConfTool.getProperty(pathToHiveSite, "hive.metastore.sasl.enabled"));
  }

  @Test public void configureSecurityCustomTest()
      throws ParserConfigurationException, TransformerException, SAXException, IOException {
    String pathToHiveSite = getPath("hive-site-033.xml");
    ConfCli.main(new String[] { "--security", "custom", "--maprsasl", "--path", pathToHiveSite });
    Assert.assertFalse(ConfTool.exists(pathToHiveSite, "hive.metastore.sasl.enabled"));
    Assert.assertTrue(ConfTool.exists(pathToHiveSite, "hive.server2.thrift.sasl.qop"));
    Assert.assertTrue(ConfTool.exists(pathToHiveSite, "hive.metastore.execute.setugi"));
    Assert.assertFalse(ConfTool.exists(pathToHiveSite, "hive.security.metastore.authorization.manager"));
  }

  @Test public void configureSecurityIncorrectArgumentsTest()
      throws ParserConfigurationException, TransformerException, SAXException, IOException {
    String pathToHiveSite = getPath("hive-site-022.xml");
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("Incorrect security configuration options");
    ConfCli.main(new String[] { "--maprsasl", "--path", pathToHiveSite });
    Assert.assertTrue(output().contains("Print help information"));
  }

  @Test public void configureSecurityIncorrectValueTest()
      throws ParserConfigurationException, TransformerException, SAXException, IOException {
    String pathToHiveSite = getPath("hive-site-022.xml");
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("Incorrect security configuration options");
    ConfCli.main(new String[] { "--maprsasl", "--security", "akhkj", "--path", pathToHiveSite });
    Assert.assertTrue(output().contains("Print help information"));
  }

  @Test public void configureHs2HaTest()
      throws ParserConfigurationException, TransformerException, SAXException, IOException {
    String pathToHiveSite = getPath("hive-site-022.xml");
    String zkQuorum = "host1,host2";
    ConfCli.main(new String[] { "--hs2ha", "--zkquorum", zkQuorum, "--path", pathToHiveSite });
    Assert.assertTrue(ConfTool.exists(pathToHiveSite, "hive.server2.support.dynamic.service.discovery"));
    Assert.assertTrue(ConfTool.exists(pathToHiveSite, "hive.zookeeper.quorum"));
    Assert.assertEquals("true", ConfTool.getProperty(pathToHiveSite, "hive.server2.support.dynamic.service.discovery"));
    Assert.assertEquals(zkQuorum, ConfTool.getProperty(pathToHiveSite, "hive.zookeeper.quorum"));
  }

  @Test public void configureHs2HaIncorrectArgumentsTest()
      throws ParserConfigurationException, TransformerException, SAXException, IOException {
    String pathToHiveSite = getPath("hive-site-022.xml");
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("Incorrect HS2 HA configuration options");
    ConfCli.main(new String[] { "--hs2ha", "--path", pathToHiveSite });
    Assert.assertTrue(output().contains("Print help information"));
  }

  @Test public void configureMetastoreUriTest()
      throws ParserConfigurationException, TransformerException, SAXException, IOException {
    String pathToHiveSite = getPath("hive-site-022.xml");
    ConfCli.main(new String[] { "--initMetastoreUri", "--path", pathToHiveSite });
    Assert.assertTrue(ConfTool.exists(pathToHiveSite, "hive.metastore.uris"));
    Assert.assertEquals("thrift://localhost:9083", ConfTool.getProperty(pathToHiveSite, "hive.metastore.uris"));
  }

  @Test public void existPropertyTrueTest()
      throws ParserConfigurationException, TransformerException, SAXException, IOException {
    String pathToHiveSite = getPath("hive-site-022.xml");
    ConfCli.main(new String[] { "--existProperty", "datanucleus.schema.autoCreateAll", "--path", pathToHiveSite });
    Assert.assertTrue(output().contains("true"));
  }

  @Test public void existPropertyFalseTest()
      throws ParserConfigurationException, TransformerException, SAXException, IOException {
    String pathToHiveSite = getPath("hive-site-022.xml");
    ConfCli.main(new String[] { "--existProperty", "no.such.property.test", "--path", pathToHiveSite });
    Assert.assertTrue(output().contains("false"));
  }

  @Test public void existPropertyIncorrectArgumentsTest()
      throws ParserConfigurationException, TransformerException, SAXException, IOException {
    String pathToHiveSite = getPath("hive-site-022.xml");
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("Missing argument for option");
    ConfCli.main(new String[] { "--path", pathToHiveSite, "--existProperty" });
    Assert.assertTrue(output().contains("Print help information"));
  }

  @Test public void configureConnectionUrlTest()
      throws ParserConfigurationException, TransformerException, SAXException, IOException {
    String pathToHiveSite = getPath("hive-site-022.xml");
    String connectionUrl = "jdbc:derby:;databaseName=/opt/mapr/hive/hive-2.1/bin/metastore_db;create=true";
    ConfCli.main(new String[] { "--connurl", connectionUrl, "--path", pathToHiveSite });
    Assert.assertTrue(ConfTool.exists(pathToHiveSite, "javax.jdo.option.ConnectionURL"));
    Assert.assertEquals(connectionUrl, ConfTool.getProperty(pathToHiveSite, "javax.jdo.option.ConnectionURL"));
  }

  @Test public void configureConnectionUrlIncorrectArgumentsTest()
      throws ParserConfigurationException, TransformerException, SAXException, IOException {
    String pathToHiveSite = getPath("hive-site-022.xml");
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("Missing argument for option");
    ConfCli.main(new String[] { "--connurl", "--path", pathToHiveSite });
    Assert.assertTrue(output().contains("Print help information"));
  }

  @Test public void configureWebUiHs2PamSslSecurityOnTest()
      throws ParserConfigurationException, TransformerException, SAXException, IOException {
    String pathToHiveSite = getPath("hive-site-022.xml");
    ConfCli.main(new String[] { "--webuipamssl", "--path", pathToHiveSite, "--security", "true" });
    Assert.assertTrue(ConfTool.exists(pathToHiveSite, "hive.server2.webui.use.pam"));
    Assert.assertTrue(ConfTool.exists(pathToHiveSite, "hive.server2.webui.use.ssl"));
    Assert.assertEquals("true", ConfTool.getProperty(pathToHiveSite, "hive.server2.webui.use.pam"));
    Assert.assertEquals("true", ConfTool.getProperty(pathToHiveSite, "hive.server2.webui.use.ssl"));
  }

  @Test public void configureWebUiHs2PamSslSecurityOffTest()
      throws ParserConfigurationException, TransformerException, SAXException, IOException {
    String pathToHiveSite = getPath("hive-site-034.xml");
    ConfCli.main(new String[] { "--webuipamssl", "--path", pathToHiveSite, "--security", "false" });
    Assert.assertFalse(ConfTool.exists(pathToHiveSite, "hive.server2.webui.use.pam"));
    Assert.assertFalse(ConfTool.exists(pathToHiveSite, "hive.server2.webui.use.ssl"));
  }

  @Test public void configureWebUiHs2PamSslSecurityCustomTest()
      throws ParserConfigurationException, TransformerException, SAXException, IOException {
    String pathToHiveSite = getPath("hive-site-035.xml");
    ConfCli.main(new String[] { "--webuipamssl", "--path", pathToHiveSite, "--security", "custom" });
    Assert.assertTrue(ConfTool.exists(pathToHiveSite, "hive.server2.webui.use.pam"));
    Assert.assertTrue(ConfTool.exists(pathToHiveSite, "hive.server2.webui.use.ssl"));
  }

  @Test public void configureWebHCatSslSecurityOnTest()
      throws ParserConfigurationException, TransformerException, SAXException, IOException {
    String pathToWebHCatSite = getPath("webhcat-site-002.xml");
    ConfCli.main(new String[] { "--webhcatssl", "--path", pathToWebHCatSite, "--security", "true" });
    Assert.assertTrue(ConfTool.exists(pathToWebHCatSite, "templeton.use.ssl"));
  }

  @Test public void configureWebHCatSslSecurityOffTest()
      throws ParserConfigurationException, TransformerException, SAXException, IOException {
    String pathToWebHCatSite = getPath("webhcat-site-003.xml");
    ConfCli.main(new String[] { "--webhcatssl", "--path", pathToWebHCatSite, "--security", "false" });
    Assert.assertFalse(ConfTool.exists(pathToWebHCatSite, "templeton.use.ssl"));
  }

  @Test public void configureWebHCatSslSecurityCustomTest()
      throws ParserConfigurationException, TransformerException, SAXException, IOException {
    String pathToWebHCatSite = getPath("webhcat-site-004.xml");
    ConfCli.main(new String[] { "--webhcatssl", "--path", pathToWebHCatSite, "--security", "custom" });
    Assert.assertTrue(ConfTool.exists(pathToWebHCatSite, "templeton.use.ssl"));
  }

  @Test public void configureHs2SslSecurityOnTest()
      throws ParserConfigurationException, TransformerException, SAXException, IOException {
    String pathToHiveSite = getPath("hive-site-023.xml");
    ConfCli.main(new String[] { "--hs2ssl", "--path", pathToHiveSite, "--security", "true" });
    Assert.assertTrue(ConfTool.exists(pathToHiveSite, "hive.server2.use.SSL"));
    Assert.assertEquals("true", ConfTool.getProperty(pathToHiveSite, "hive.server2.use.SSL"));
  }

  @Test public void configureHs2SslSecurityOffTest()
      throws ParserConfigurationException, TransformerException, SAXException, IOException {
    String pathToHiveSite = getPath("hive-site-036.xml");
    ConfCli.main(new String[] { "--hs2ssl", "--path", pathToHiveSite, "--security", "false" });
    Assert.assertFalse(ConfTool.exists(pathToHiveSite, "hive.server2.use.SSL"));
  }

  @Test public void configureHs2SslSecurityCustomTest()
      throws ParserConfigurationException, TransformerException, SAXException, IOException {
    String pathToHiveSite = getPath("hive-site-037.xml");
    ConfCli.main(new String[] { "--hs2ssl", "--path", pathToHiveSite, "--security", "custom" });
    Assert.assertTrue(ConfTool.exists(pathToHiveSite, "hive.server2.use.SSL"));
  }

  @Test public void configureHMetaSslSecurityOnTest()
      throws ParserConfigurationException, TransformerException, SAXException, IOException {
    String pathToHiveSite = getPath("hive-site-056.xml");
    ConfCli.main(new String[] { "--hmetassl", "--path", pathToHiveSite, "--security", "true" });
    Assert.assertTrue(ConfTool.exists(pathToHiveSite, "hive.metastore.use.SSL"));
    Assert.assertEquals("true", ConfTool.getProperty(pathToHiveSite, "hive.metastore.use.SSL"));
  }

  @Test public void configureHMetaSslSecurityOffTest()
      throws ParserConfigurationException, TransformerException, SAXException, IOException {
    String pathToHiveSite = getPath("hive-site-056.xml");
    ConfCli.main(new String[] { "--hmetassl", "--path", pathToHiveSite, "--security", "false" });
    Assert.assertFalse(ConfTool.exists(pathToHiveSite, "hive.metastore.use.SSL"));
  }

  @Test public void configureHMetaSslSecurityCustomTest()
      throws ParserConfigurationException, TransformerException, SAXException, IOException {
    String pathToHiveSite = getPath("hive-site-057.xml");
    ConfCli.main(new String[] { "--hmetassl", "--path", pathToHiveSite, "--security", "custom" });
    Assert.assertTrue(ConfTool.exists(pathToHiveSite, "hive.metastore.use.SSL"));
    Assert.assertEquals("MyCustomValue", ConfTool.getProperty(pathToHiveSite, "hive.metastore.use.SSL"));
  }

  @Test public void configureHs2SslWithoutSecurityArgsTest()
      throws ParserConfigurationException, TransformerException, SAXException, IOException {
    String pathToHiveSite = getPath("hive-site-022.xml");
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("Incorrect security configuration options");
    ConfCli.main(new String[] { "--hs2ssl", "--path", pathToHiveSite });
    Assert.assertTrue(output().contains("Print help information"));
  }

  @Test public void configureHs2SslMissingSecurityArgsTest()
      throws ParserConfigurationException, TransformerException, SAXException, IOException {
    String pathToHiveSite = getPath("hive-site-022.xml");
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("Missing argument for option: security");
    ConfCli.main(new String[] { "--hs2ssl", "--path", pathToHiveSite, "--security" });
    Assert.assertTrue(output().contains("Print help information"));
  }

  @Test public void setFallbackAuthorizerSecurityOnTest()
      throws IOException, SAXException, ParserConfigurationException, TransformerException {
    String pathToHiveSite = getPath("hive-site-047.xml");
    ConfCli.main(new String[] { "--fallBackAuthorizer", "--path", pathToHiveSite, "--security", "true" });
    Assert.assertEquals("true", ConfTool.getProperty(pathToHiveSite, "hive.security.authorization.enabled"));
    Assert
        .assertEquals("org.apache.hadoop.hive.ql.security.authorization.plugin.fallback.FallbackHiveAuthorizerFactory",
            ConfTool.getProperty(pathToHiveSite, "hive.security.authorization.manager"));
  }

  @Test public void setFallbackAuthorizerSecurityOffTest()
      throws IOException, SAXException, ParserConfigurationException, TransformerException {
    String pathToHiveSite = getPath("hive-site-048.xml");
    ConfCli.main(new String[] { "--fallBackAuthorizer", "--path", pathToHiveSite, "--security", "false" });
    Assert.assertFalse(ConfTool.exists(pathToHiveSite, "hive.security.authorization.enabled"));
    Assert.assertFalse(ConfTool.exists(pathToHiveSite, "hive.security.authorization.manager"));
  }

  @Test public void setFallbackAuthorizerSecurityCustomTest()
      throws IOException, SAXException, ParserConfigurationException, TransformerException {
    String pathToHiveSite = getPath("hive-site-049.xml");
    ConfCli.main(new String[] { "--fallBackAuthorizer", "--path", pathToHiveSite, "--security", "custom" });
    Assert.assertEquals("MyCustomValue", ConfTool.getProperty(pathToHiveSite, "hive.security.authorization.enabled"));
    Assert.assertEquals("MyCustomValue", ConfTool.getProperty(pathToHiveSite, "hive.security.authorization.manager"));
  }

  @Test public void configureHs2MetricsEnabledTest()
      throws ParserConfigurationException, TransformerException, SAXException, IOException {
    String pathToHiveSite = getPath("hive-site-050.xml");
    ConfCli.main(new String[] { "--hiveserver2_metrics_enabled", "true", "--path", pathToHiveSite });
    Assert.assertTrue(ConfTool.exists(pathToHiveSite, "hive.server2.metrics.enabled"));
    Assert.assertEquals("true", ConfTool.getProperty(pathToHiveSite, "hive.server2.metrics.enabled"));
  }

  @Test public void configureHs2MetricsDisabledTest()
      throws ParserConfigurationException, TransformerException, SAXException, IOException {
    String pathToHiveSite = getPath("hive-site-051.xml");
    ConfCli.main(new String[] { "--hiveserver2_metrics_enabled", "false", "--path", pathToHiveSite });
    Assert.assertTrue(ConfTool.exists(pathToHiveSite, "hive.server2.metrics.enabled"));
    Assert.assertEquals("false", ConfTool.getProperty(pathToHiveSite, "hive.server2.metrics.enabled"));
  }

  @Test public void configureMetastoreMetricsEnabledTest()
      throws ParserConfigurationException, TransformerException, SAXException, IOException {
    String pathToHiveSite = getPath("hive-site-052.xml");
    ConfCli.main(new String[] { "--metastore_metrics_enabled", "true", "--path", pathToHiveSite });
    Assert.assertTrue(ConfTool.exists(pathToHiveSite, "hive.metastore.metrics.enabled"));
    Assert.assertEquals("true", ConfTool.getProperty(pathToHiveSite, "hive.metastore.metrics.enabled"));
  }

  @Test public void configureMetastoreMetricsDisabledTest()
      throws ParserConfigurationException, TransformerException, SAXException, IOException {
    String pathToHiveSite = getPath("hive-site-053.xml");
    ConfCli.main(new String[] { "--metastore_metrics_enabled", "false", "--path", pathToHiveSite });
    Assert.assertTrue(ConfTool.exists(pathToHiveSite, "hive.metastore.metrics.enabled"));
    Assert.assertEquals("false", ConfTool.getProperty(pathToHiveSite, "hive.metastore.metrics.enabled"));
  }

  @Test public void configureMetastoreMetricsReportEnabled()
      throws IOException, SAXException, ParserConfigurationException, TransformerException {
    String pathToHiveSite = getPath("hive-site-054.xml");
    ConfCli.main(new String[] { "--reporter_type", "JSON_FILE,JMX", "--hmeta_report_file", "/tmp/hivemetastore_report.json",
        "--reporter_enabled", "true", "--path", pathToHiveSite });
    Assert.assertEquals("JSON_FILE,JMX", ConfTool.getProperty(pathToHiveSite, "hive.service.metrics.reporter"));
    Assert.assertEquals("/tmp/hivemetastore_report.json",
        ConfTool.getProperty(pathToHiveSite, "hive.metastore.metrics.file.location"));
  }

  @Test public void configureMetastoreMetricsReportDisabled()
      throws IOException, SAXException, ParserConfigurationException, TransformerException {
    String pathToHiveSite = getPath("hive-site-055.xml");
    ConfCli.main(new String[] { "--reporter_type", "JSON_FILE,JMX", "--hmeta_report_file", "/tmp/hivemetastore_report.json",
        "--reporter_enabled", "false", "--path", pathToHiveSite });
    Assert.assertFalse(ConfTool.exists(pathToHiveSite, "hive.service.metrics.reporter"));
    Assert.assertFalse(ConfTool.exists(pathToHiveSite, "hive.metastore.metrics.file.location"));
  }

  @Test public void configureHiveServer2MetricsReportEnabled()
      throws IOException, SAXException, ParserConfigurationException, TransformerException {
    String pathToHiveSite = getPath("hive-site-054.xml");
    ConfCli.main(new String[] { "--reporter_type", "JSON_FILE,JMX", "--hs2_report_file", "/tmp/hiveserver2_report.json",
        "--reporter_enabled", "true", "--path", pathToHiveSite });
    Assert.assertEquals("JSON_FILE,JMX", ConfTool.getProperty(pathToHiveSite, "hive.service.metrics.reporter"));
    Assert.assertEquals("/tmp/hiveserver2_report.json",
        ConfTool.getProperty(pathToHiveSite, "hive.server2.metrics.file.location"));
  }

  @Test public void configureHiveServer2MetricsReportDisabled()
      throws IOException, SAXException, ParserConfigurationException, TransformerException {
    String pathToHiveSite = getPath("hive-site-055.xml");
    ConfCli.main(new String[] { "--reporter_type", "JSON_FILE,JMX", "--hs2_report_file", "/tmp/hiveserver2_report.json",
        "--reporter_enabled", "false", "--path", pathToHiveSite });
    Assert.assertFalse(ConfTool.exists(pathToHiveSite, "hive.service.metrics.reporter"));
    Assert.assertFalse(ConfTool.exists(pathToHiveSite, "hive.server2.metrics.file.location"));
  }
}
