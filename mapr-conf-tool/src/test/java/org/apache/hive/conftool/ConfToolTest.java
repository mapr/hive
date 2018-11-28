package org.apache.hive.conftool;

import org.apache.hadoop.hive.conf.HiveConf.ConfVars;
import org.apache.hive.hcatalog.templeton.AppConfig;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.net.URL;

import static org.apache.hadoop.hive.conf.HiveConf.ConfVars.USERS_IN_ADMIN_ROLE;
import static org.apache.hive.conftool.Security.NONE;
import static org.apache.hive.conftool.Security.MAPRSASL;
import static org.apache.hive.conftool.Security.CUSTOM;

public class ConfToolTest {
  private static final Logger LOG = LoggerFactory.getLogger(ConfTool.class.getName());

  @Test public void setMaprSaslTrueTest()
      throws IOException, ParserConfigurationException, SAXException, TransformerException {
    URL url = Thread.currentThread().getContextClassLoader().getResource("hive-site-003.xml");
    String pathToHiveSite = url.getPath();
    ConfTool.setMaprSasl(pathToHiveSite, MAPRSASL);

    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
    Document doc = docBuilder.parse(pathToHiveSite);
    Assert.assertEquals("true", ConfTool.getProperty(doc, ConfVars.METASTORE_USE_THRIFT_SASL));
  }

  @Test public void enableHs2HaTest()
      throws IOException, ParserConfigurationException, SAXException, TransformerException {
    URL url = Thread.currentThread().getContextClassLoader().getResource("hive-site-010.xml");
    String pathToHiveSite = url.getPath();
    ConfTool.enableHs2Ha(pathToHiveSite, "node1,node2");

    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
    Document doc = docBuilder.parse(pathToHiveSite);
    Assert.assertEquals("true", ConfTool.getProperty(doc, ConfVars.HIVE_SERVER2_SUPPORT_DYNAMIC_SERVICE_DISCOVERY));
    Assert.assertEquals("node1,node2", ConfTool.getProperty(doc, ConfVars.HIVE_ZOOKEEPER_QUORUM));
  }

  @Test public void setMaprSaslTrueTest2()
      throws IOException, ParserConfigurationException, SAXException, TransformerException {
    URL url = Thread.currentThread().getContextClassLoader().getResource("hive-site-005.xml");
    String pathToHiveSite = url.getPath();
    ConfTool.setMaprSasl(pathToHiveSite, MAPRSASL);

    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
    Document doc = docBuilder.parse(pathToHiveSite);
    Assert.assertEquals("true", ConfTool.getProperty(doc, ConfVars.METASTORE_USE_THRIFT_SASL));
  }

  @Test public void setMaprSaslFalseTest()
      throws IOException, ParserConfigurationException, SAXException, TransformerException {
    URL url = Thread.currentThread().getContextClassLoader().getResource("hive-site-004.xml");
    String pathToHiveSite = url.getPath();
    ConfTool.setMaprSasl(pathToHiveSite, NONE);

    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
    Document doc = docBuilder.parse(pathToHiveSite);
    Assert.assertEquals("false", ConfTool.getProperty(doc, ConfVars.METASTORE_USE_THRIFT_SASL));
  }

  @Test public void setMaprSaslCustomTest()
      throws IOException, ParserConfigurationException, SAXException, TransformerException {
    URL url = Thread.currentThread().getContextClassLoader().getResource("hive-site-038.xml");
    String pathToHiveSite = url.getPath();
    ConfTool.setMaprSasl(pathToHiveSite, CUSTOM);

    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
    Document doc = docBuilder.parse(pathToHiveSite);
    Assert.assertEquals("MyCustomValue", ConfTool.getProperty(doc, ConfVars.METASTORE_USE_THRIFT_SASL));
  }

  @Test public void enableEncryptionTrueTest()
      throws IOException, ParserConfigurationException, SAXException, TransformerException {
    URL url = Thread.currentThread().getContextClassLoader().getResource("hive-site-008.xml");
    String pathToHiveSite = url.getPath();
    ConfTool.setEncryption(pathToHiveSite, MAPRSASL);

    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
    Document doc = docBuilder.parse(pathToHiveSite);
    Assert.assertEquals("auth-conf", ConfTool.getProperty(doc, ConfVars.HIVE_SERVER2_THRIFT_SASL_QOP));
  }

  @Test public void enableEncryptionFalseTest()
      throws IOException, ParserConfigurationException, SAXException, TransformerException {
    URL url = Thread.currentThread().getContextClassLoader().getResource("hive-site-008.xml");
    String pathToHiveSite = url.getPath();
    ConfTool.setEncryption(pathToHiveSite, NONE);

    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
    Document doc = docBuilder.parse(pathToHiveSite);
    Assert.assertFalse(ConfTool.propertyExists(doc, ConfVars.HIVE_SERVER2_THRIFT_SASL_QOP));
  }

  @Test public void enableEncryptionCustomTest()
      throws IOException, ParserConfigurationException, SAXException, TransformerException {
    URL url = Thread.currentThread().getContextClassLoader().getResource("hive-site-039.xml");
    String pathToHiveSite = url.getPath();
    ConfTool.setEncryption(pathToHiveSite, CUSTOM);

    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
    Document doc = docBuilder.parse(pathToHiveSite);
    Assert.assertEquals("MyCustomValue", ConfTool.getProperty(doc, ConfVars.HIVE_SERVER2_THRIFT_SASL_QOP));
  }

  @Test public void hiveMetaStoreSaslEnabledExistsTestTrue()
      throws ParserConfigurationException, IOException, SAXException {
    URL url = Thread.currentThread().getContextClassLoader().getResource("hive-site-001.xml");
    String pathToHiveSite = url.getPath();

    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
    Document doc = docBuilder.parse(pathToHiveSite);
    Assert.assertTrue(ConfTool.propertyExists(doc, ConfVars.METASTORE_USE_THRIFT_SASL));
  }

  @Test public void propertyExistsTestFalse() throws ParserConfigurationException, IOException, SAXException {
    URL url = Thread.currentThread().getContextClassLoader().getResource("hive-site-002.xml");
    String pathToHiveSite = url.getPath();

    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
    Document doc = docBuilder.parse(pathToHiveSite);
    Assert.assertFalse(ConfTool.propertyExists(doc, ConfVars.METASTORE_USE_THRIFT_SASL));
  }

  @Test public void getPropertyTest() throws ParserConfigurationException, IOException, SAXException {
    URL url = Thread.currentThread().getContextClassLoader().getResource("hive-site-001.xml");
    String pathToHiveSite = url.getPath();

    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
    Document doc = docBuilder.parse(pathToHiveSite);

    Assert.assertEquals("true", ConfTool.getProperty(doc, ConfVars.METASTORE_USE_THRIFT_SASL));
    Assert.assertEquals("MAPRSASL", ConfTool.getProperty(doc, ConfVars.HIVE_SERVER2_AUTHENTICATION));
  }

  @Test public void setPropertyTest() throws ParserConfigurationException, IOException, SAXException {
    URL url = Thread.currentThread().getContextClassLoader().getResource("hive-site-001.xml");
    String pathToHiveSite = url.getPath();

    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
    Document doc = docBuilder.parse(pathToHiveSite);

    String testValue = "AAbbbCCC";
    ConfTool.setProperty(doc, ConfVars.METASTORE_USE_THRIFT_SASL, testValue);
    Assert.assertEquals(testValue, ConfTool.getProperty(doc, ConfVars.METASTORE_USE_THRIFT_SASL));
  }

  @Test public void addPropertyTest() throws ParserConfigurationException, IOException, SAXException {
    URL url = Thread.currentThread().getContextClassLoader().getResource("hive-site-002.xml");
    String pathToHiveSite = url.getPath();

    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
    Document doc = docBuilder.parse(pathToHiveSite);

    Assert.assertFalse(ConfTool.propertyExists(doc, ConfVars.METASTORE_USE_THRIFT_SASL));

    String testValue = "AAbbbCCC";
    ConfTool.addProperty(doc, ConfVars.METASTORE_USE_THRIFT_SASL, testValue);

    Assert.assertEquals(testValue, ConfTool.getProperty(doc, ConfVars.METASTORE_USE_THRIFT_SASL));
  }

  @Test public void addPropertyAlreadyExistsTest()
      throws SAXException, TransformerException, ParserConfigurationException, IOException {
    URL url = Thread.currentThread().getContextClassLoader().getResource("hive-site-017.xml");
    String pathToHiveSite = url.getPath();
    String property = "test.property.to.add";
    String oldValue = "old.value";
    String newValue = "test.value.to.add";

    Assert.assertEquals(oldValue, ConfTool.getProperty(pathToHiveSite, property));
    ConfTool.addProperty(pathToHiveSite, property, newValue);
    Assert.assertEquals(newValue, ConfTool.getProperty(pathToHiveSite, property));
  }

  @Test public void getConfigurationNodeTest() throws ParserConfigurationException, IOException, SAXException {
    URL url = Thread.currentThread().getContextClassLoader().getResource("hive-site-006.xml");
    String pathToHiveSite = url.getPath();
    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
    Document doc = docBuilder.parse(pathToHiveSite);
    Assert.assertNotNull(ConfTool.getConfigurationNode(doc));
  }

  @Test public void setMaprSaslTrueComplexTest()
      throws IOException, ParserConfigurationException, SAXException, TransformerException {
    URL url = Thread.currentThread().getContextClassLoader().getResource("hive-site-006.xml");
    String pathToHiveSite = url.getPath();
    ConfTool.setMaprSasl(pathToHiveSite, MAPRSASL);

    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
    Document doc = docBuilder.parse(pathToHiveSite);
    Assert.assertEquals("true", ConfTool.getProperty(doc, ConfVars.METASTORE_USE_THRIFT_SASL));
  }

  @Test public void removePropertyTest() throws IOException, SAXException, ParserConfigurationException {
    URL url = Thread.currentThread().getContextClassLoader().getResource("hive-site-011.xml");
    String pathToHiveSite = url.getPath();

    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
    Document doc = docBuilder.parse(pathToHiveSite);

    Assert.assertTrue(ConfTool.propertyExists(doc, ConfVars.HIVE_SERVER2_THRIFT_SASL_QOP));
    ConfTool.removeProperty(doc, ConfVars.HIVE_SERVER2_THRIFT_SASL_QOP);
    LOG.info(ConfTool.toString(doc));
    Assert.assertFalse(ConfTool.propertyExists(doc, ConfVars.HIVE_SERVER2_THRIFT_SASL_QOP));
  }

  @Test public void setConnectionUrlTest()
      throws IOException, ParserConfigurationException, SAXException, TransformerException {
    URL url = Thread.currentThread().getContextClassLoader().getResource("hive-site-012.xml");
    String pathToHiveSite = url.getPath();
    String connectionUrl = "jdbc:derby:;databaseName=/opt/mapr/hive/hive-2.1/bin/metastore_db;create=true";
    ConfTool.setConnectionUrl(pathToHiveSite, connectionUrl);

    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
    Document doc = docBuilder.parse(pathToHiveSite);
    Assert.assertEquals(connectionUrl, ConfTool.getProperty(doc, ConfVars.METASTORECONNECTURLKEY));
  }

  @Test public void hs2WebUiPamSslSecurityOnTest()
      throws IOException, ParserConfigurationException, SAXException, TransformerException {
    URL url = Thread.currentThread().getContextClassLoader().getResource("hive-site-013.xml");
    String pathToHiveSite = url.getPath();
    ConfTool.setHs2WebUiPamSsl(pathToHiveSite, MAPRSASL);

    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
    Document doc = docBuilder.parse(pathToHiveSite);
    Assert.assertEquals("true", ConfTool.getProperty(doc, ConfVars.HIVE_SERVER2_WEBUI_USE_PAM));
    Assert.assertEquals("true", ConfTool.getProperty(doc, ConfVars.HIVE_SERVER2_WEBUI_USE_SSL));
  }

  @Test public void hs2WebUiPamSslSecurityOffTest()
      throws IOException, ParserConfigurationException, SAXException, TransformerException {
    URL url = Thread.currentThread().getContextClassLoader().getResource("hive-site-013.xml");
    String pathToHiveSite = url.getPath();
    ConfTool.setHs2WebUiPamSsl(pathToHiveSite, NONE);

    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
    Document doc = docBuilder.parse(pathToHiveSite);
    Assert.assertFalse(ConfTool.propertyExists(doc, ConfVars.HIVE_SERVER2_WEBUI_USE_PAM));
    Assert.assertFalse(ConfTool.propertyExists(doc, ConfVars.HIVE_SERVER2_WEBUI_USE_SSL));
  }

  @Test public void hs2WebUiPamSslSecurityCustomTest()
      throws IOException, ParserConfigurationException, SAXException, TransformerException {
    URL url = Thread.currentThread().getContextClassLoader().getResource("hive-site-040.xml");
    String pathToHiveSite = url.getPath();
    ConfTool.setHs2WebUiPamSsl(pathToHiveSite, CUSTOM);

    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
    Document doc = docBuilder.parse(pathToHiveSite);
    Assert.assertEquals("MyCustomValue", ConfTool.getProperty(doc, ConfVars.HIVE_SERVER2_WEBUI_USE_PAM));
    Assert.assertEquals("MyCustomValue", ConfTool.getProperty(doc, ConfVars.HIVE_SERVER2_WEBUI_USE_SSL));
  }

  @Test public void webHCatSslSecurityOnTest()
      throws IOException, ParserConfigurationException, SAXException, TransformerException {
    URL url = Thread.currentThread().getContextClassLoader().getResource("webhcat-site-001.xml");
    String pathToWebHCatSite = url.getPath();
    ConfTool.setWebHCatSsl(pathToWebHCatSite, MAPRSASL);

    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
    Document doc = docBuilder.parse(pathToWebHCatSite);
    Assert.assertEquals("true", ConfTool.getProperty(doc, AppConfig.USE_SSL));
  }

  @Test public void webHCatSslSecurityOffTest()
      throws IOException, ParserConfigurationException, SAXException, TransformerException {
    URL url = Thread.currentThread().getContextClassLoader().getResource("webhcat-site-001.xml");
    String pathToWebHCatSite = url.getPath();
    ConfTool.setWebHCatSsl(pathToWebHCatSite, NONE);

    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
    Document doc = docBuilder.parse(pathToWebHCatSite);
    Assert.assertFalse(ConfTool.propertyExists(doc, AppConfig.USE_SSL));
  }

  @Test public void webHCatSslSecurityCustomTest()
      throws IOException, ParserConfigurationException, SAXException, TransformerException {
    URL url = Thread.currentThread().getContextClassLoader().getResource("webhcat-site-005.xml");
    String pathToWebHCatSite = url.getPath();
    ConfTool.setWebHCatSsl(pathToWebHCatSite, CUSTOM);

    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
    Document doc = docBuilder.parse(pathToWebHCatSite);
    Assert.assertEquals("MyCustomValue", ConfTool.getProperty(doc, AppConfig.USE_SSL));
  }

  @Test public void hs2SslSecurityOnTest()
      throws IOException, ParserConfigurationException, SAXException, TransformerException {
    URL url = Thread.currentThread().getContextClassLoader().getResource("hive-site-023.xml");
    String pathToHiveSite = url.getPath();
    ConfTool.setHs2Ssl(pathToHiveSite, MAPRSASL);

    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
    Document doc = docBuilder.parse(pathToHiveSite);
    Assert.assertEquals("true", ConfTool.getProperty(doc, ConfVars.HIVE_SERVER2_USE_SSL));
  }

  @Test public void hs2SslSecurityOffTest()
      throws IOException, ParserConfigurationException, SAXException, TransformerException {
    URL url = Thread.currentThread().getContextClassLoader().getResource("hive-site-023.xml");
    String pathToHiveSite = url.getPath();
    ConfTool.setHs2Ssl(pathToHiveSite, NONE);

    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
    Document doc = docBuilder.parse(pathToHiveSite);
    Assert.assertFalse(ConfTool.propertyExists(doc, ConfVars.HIVE_SERVER2_USE_SSL));
  }

  @Test public void hs2SslSecurityCustomTest()
      throws IOException, ParserConfigurationException, SAXException, TransformerException {
    URL url = Thread.currentThread().getContextClassLoader().getResource("hive-site-041.xml");
    String pathToHiveSite = url.getPath();
    ConfTool.setHs2Ssl(pathToHiveSite, CUSTOM);

    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
    Document doc = docBuilder.parse(pathToHiveSite);
    Assert.assertEquals("MyCustomValue", ConfTool.getProperty(doc, ConfVars.HIVE_SERVER2_USE_SSL));
  }

  @Test public void metaStoreUgiSecurityOnTest()
      throws IOException, ParserConfigurationException, SAXException, TransformerException {
    URL url = Thread.currentThread().getContextClassLoader().getResource("hive-site-014.xml");
    String pathToWebHCatSite = url.getPath();
    ConfTool.setMetaStoreUgi(pathToWebHCatSite, MAPRSASL);

    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
    Document doc = docBuilder.parse(pathToWebHCatSite);
    Assert.assertEquals("false", ConfTool.getProperty(doc, ConfVars.METASTORE_EXECUTE_SET_UGI));
  }

  @Test public void metaStoreUgiSecurityOffTest()
      throws IOException, ParserConfigurationException, SAXException, TransformerException {
    URL url = Thread.currentThread().getContextClassLoader().getResource("hive-site-015.xml");
    String pathToWebHCatSite = url.getPath();
    ConfTool.setMetaStoreUgi(pathToWebHCatSite, NONE);

    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
    Document doc = docBuilder.parse(pathToWebHCatSite);
    Assert.assertFalse(ConfTool.propertyExists(doc, ConfVars.METASTORE_EXECUTE_SET_UGI));
  }

  @Test public void metaStoreUgiSecurityCustomTest()
      throws IOException, ParserConfigurationException, SAXException, TransformerException {
    URL url = Thread.currentThread().getContextClassLoader().getResource("hive-site-042.xml");
    String pathToWebHCatSite = url.getPath();
    ConfTool.setMetaStoreUgi(pathToWebHCatSite, CUSTOM);

    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
    Document doc = docBuilder.parse(pathToWebHCatSite);
    Assert.assertEquals("MyCustomValue", ConfTool.getProperty(doc, ConfVars.METASTORE_EXECUTE_SET_UGI));
  }

  @Test public void metaStoreAuthManagerSecurityOnTest()
      throws IOException, ParserConfigurationException, SAXException, TransformerException {
    URL url = Thread.currentThread().getContextClassLoader().getResource("hive-site-014.xml");
    String pathToWebHCatSite = url.getPath();
    ConfTool.setMetaStoreAuthManager(pathToWebHCatSite, MAPRSASL);

    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
    Document doc = docBuilder.parse(pathToWebHCatSite);
    Assert.assertEquals("org.apache.hadoop.hive.ql.security.authorization.StorageBasedAuthorizationProvider",
        ConfTool.getProperty(doc, ConfVars.HIVE_METASTORE_AUTHORIZATION_MANAGER));
  }

  @Test public void metaStoreAuthManagerSecurityOffTest()
      throws IOException, ParserConfigurationException, SAXException, TransformerException {
    URL url = Thread.currentThread().getContextClassLoader().getResource("hive-site-015.xml");
    String pathToWebHCatSite = url.getPath();
    ConfTool.setMetaStoreAuthManager(pathToWebHCatSite, NONE);

    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
    Document doc = docBuilder.parse(pathToWebHCatSite);
    Assert.assertFalse(ConfTool.propertyExists(doc, ConfVars.HIVE_METASTORE_AUTHORIZATION_MANAGER));
  }

  @Test public void metaStoreAuthManagerSecurityCustomTest()
      throws IOException, ParserConfigurationException, SAXException, TransformerException {
    URL url = Thread.currentThread().getContextClassLoader().getResource("hive-site-043.xml");
    String pathToWebHCatSite = url.getPath();
    ConfTool.setMetaStoreAuthManager(pathToWebHCatSite, CUSTOM);

    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
    Document doc = docBuilder.parse(pathToWebHCatSite);
    Assert.assertEquals("MyCustomValue", ConfTool.getProperty(doc, ConfVars.HIVE_METASTORE_AUTHORIZATION_MANAGER));
  }

  @Test public void delPropertyTest()
      throws SAXException, TransformerException, ParserConfigurationException, IOException {
    URL url = Thread.currentThread().getContextClassLoader().getResource("hive-site-016.xml");
    String pathToHiveSite = url.getPath();
    String property = "test.property.to.delete";
    ConfTool.delProperty(pathToHiveSite, property);

    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
    Document doc = docBuilder.parse(pathToHiveSite);
    Assert.assertFalse(ConfTool.propertyExists(doc, property));
  }

  @Test public void getPropertyFromFileTest() throws SAXException, ParserConfigurationException, IOException {
    URL url = Thread.currentThread().getContextClassLoader().getResource("hive-site-021.xml");
    String pathToHiveSite = url.getPath();
    String property = "test.property.to.get";
    ConfTool.getProperty(pathToHiveSite, property);
    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
    Assert.assertEquals("test.value", ConfTool.getProperty(pathToHiveSite, property));
  }

  @Test public void appendPropertyToExistingTest()
      throws ParserConfigurationException, IOException, SAXException, TransformerException {
    URL url = Thread.currentThread().getContextClassLoader().getResource("hive-site-024.xml");
    String pathToHiveSite = url.getPath();

    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
    Document doc = docBuilder.parse(pathToHiveSite);

    ConfTool.appendProperty(doc, "append.to.existing.property", "newValue");
    Assert.assertEquals("oldValue,newValue", ConfTool.getProperty(doc, "append.to.existing.property"));
  }

  @Test public void appendPropertyToNonExistingTest()
      throws ParserConfigurationException, IOException, SAXException, TransformerException {
    URL url = Thread.currentThread().getContextClassLoader().getResource("hive-site-024.xml");
    String pathToHiveSite = url.getPath();

    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
    Document doc = docBuilder.parse(pathToHiveSite);

    ConfTool.appendProperty(doc, "append.non.existing", "newValue");
    Assert.assertEquals("newValue", ConfTool.getProperty(doc, "append.non.existing"));
  }

  @Test public void appendPropertyToEmptyTest()
      throws ParserConfigurationException, IOException, SAXException, TransformerException {
    URL url = Thread.currentThread().getContextClassLoader().getResource("hive-site-024.xml");
    String pathToHiveSite = url.getPath();

    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
    Document doc = docBuilder.parse(pathToHiveSite);

    ConfTool.appendProperty(doc, "append.to.empty.property", "newValue");
    Assert.assertEquals("newValue", ConfTool.getProperty(doc, "append.to.empty.property"));
  }

  @Test public void appendPropertyValueTheSameTest()
      throws ParserConfigurationException, IOException, SAXException, TransformerException {
    URL url = Thread.currentThread().getContextClassLoader().getResource("hive-site-024.xml");
    String pathToHiveSite = url.getPath();

    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
    Document doc = docBuilder.parse(pathToHiveSite);

    ConfTool.appendProperty(doc, "append.the.existing.value", "newValue");
    Assert.assertEquals("oldValue,newValue", ConfTool.getProperty(doc, "append.the.existing.value"));
  }

  @Test public void appendPropertyValueSimilarValueTest()
      throws ParserConfigurationException, IOException, SAXException, TransformerException {
    URL url = Thread.currentThread().getContextClassLoader().getResource("hive-site-024.xml");
    String pathToHiveSite = url.getPath();

    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
    Document doc = docBuilder.parse(pathToHiveSite);

    ConfTool.appendProperty(doc, "hive.users", "mapr");
    Assert.assertEquals("mapruser,mapr", ConfTool.getProperty(doc, "hive.users"));
  }

  @Test public void adminUserSecurityOnTest()
      throws IOException, SAXException, ParserConfigurationException, TransformerException {
    URL url = Thread.currentThread().getContextClassLoader().getResource("hive-site-029.xml");
    String pathToHiveSite = url.getPath();
    String adminUser = "mapr";

    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
    Document doc = docBuilder.parse(pathToHiveSite);

    Assert.assertFalse(ConfTool.propertyExists(doc, USERS_IN_ADMIN_ROLE.varname));
    ConfTool.setAdminUser(doc, adminUser, MAPRSASL);
    Assert.assertEquals(adminUser, ConfTool.getProperty(doc, USERS_IN_ADMIN_ROLE.varname));
  }

  @Test public void adminUserSecurityOffTest()
      throws IOException, SAXException, ParserConfigurationException, TransformerException {
    URL url = Thread.currentThread().getContextClassLoader().getResource("hive-site-030.xml");
    String pathToHiveSite = url.getPath();
    String adminUser = "mapr";

    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
    Document doc = docBuilder.parse(pathToHiveSite);

    Assert.assertEquals(adminUser, ConfTool.getProperty(doc, USERS_IN_ADMIN_ROLE.varname));
    ConfTool.setAdminUser(doc, adminUser, NONE);
    Assert.assertFalse(ConfTool.propertyExists(doc, USERS_IN_ADMIN_ROLE.varname));
  }

  @Test public void adminUserSecurityCustomTest()
      throws IOException, SAXException, ParserConfigurationException, TransformerException {
    URL url = Thread.currentThread().getContextClassLoader().getResource("hive-site-044.xml");
    String pathToHiveSite = url.getPath();
    String adminUser = "MyCustomValue";

    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
    Document doc = docBuilder.parse(pathToHiveSite);

    ConfTool.setAdminUser(doc, adminUser, CUSTOM);
    Assert.assertEquals(adminUser, ConfTool.getProperty(doc, USERS_IN_ADMIN_ROLE.varname));
  }

  @Test public void restrictedListSecurityOnTest()
      throws IOException, SAXException, ParserConfigurationException, TransformerException {
    URL url = Thread.currentThread().getContextClassLoader().getResource("hive-site-026.xml");
    String pathToHiveSite = url.getPath();

    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
    Document doc = docBuilder.parse(pathToHiveSite);

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
            + "hive.server2.session.hook",
        ConfTool.getProperty(doc, "hive.conf.restricted.list"));
  }

  @Test public void restrictedListSecurityOffTest()
      throws IOException, SAXException, ParserConfigurationException, TransformerException {
    URL url = Thread.currentThread().getContextClassLoader().getResource("hive-site-027.xml");
    String pathToHiveSite = url.getPath();

    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
    Document doc = docBuilder.parse(pathToHiveSite);

    ConfTool.setRestrictedList(doc, NONE);
    Assert.assertFalse(ConfTool.propertyExists(doc, "hive.conf.restricted.list"));
  }

  @Test public void restrictedListSecurityCustomTest()
      throws IOException, SAXException, ParserConfigurationException, TransformerException {
    URL url = Thread.currentThread().getContextClassLoader().getResource("hive-site-045.xml");
    String pathToHiveSite = url.getPath();

    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
    Document doc = docBuilder.parse(pathToHiveSite);

    ConfTool.setRestrictedList(doc, CUSTOM);
    Assert.assertEquals("MyCustomValue", ConfTool.getProperty(doc, "hive.conf.restricted.list"));
  }

  @Test public void setFallbackAuthorizerSecurityOnTest()
      throws IOException, SAXException, ParserConfigurationException, TransformerException {
    URL url = Thread.currentThread().getContextClassLoader().getResource("hive-site-047.xml");
    String pathToHiveSite = url.getPath();

    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
    Document doc = docBuilder.parse(pathToHiveSite);

    ConfTool.setFallbackAuthorizer(doc, MAPRSASL);
    Assert.assertEquals("true", ConfTool.getProperty(doc, "hive.security.authorization.enabled"));
    Assert
        .assertEquals("org.apache.hadoop.hive.ql.security.authorization.plugin.fallback.FallbackHiveAuthorizerFactory",
            ConfTool.getProperty(doc, "hive.security.authorization.manager"));
  }

  @Test public void setFallbackAuthorizerSecurityOffTest()
      throws IOException, SAXException, ParserConfigurationException, TransformerException {
    URL url = Thread.currentThread().getContextClassLoader().getResource("hive-site-048.xml");
    String pathToHiveSite = url.getPath();

    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
    Document doc = docBuilder.parse(pathToHiveSite);

    ConfTool.setFallbackAuthorizer(doc, NONE);
    Assert.assertFalse(ConfTool.propertyExists(doc, "hive.security.authorization.enabled"));
    Assert.assertFalse(ConfTool.propertyExists(doc, "hive.security.authorization.manager"));
  }

  @Test public void setFallbackAuthorizerSecurityCustomTest()
      throws IOException, SAXException, ParserConfigurationException, TransformerException {
    URL url = Thread.currentThread().getContextClassLoader().getResource("hive-site-049.xml");
    String pathToHiveSite = url.getPath();

    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
    Document doc = docBuilder.parse(pathToHiveSite);

    ConfTool.setFallbackAuthorizer(doc, CUSTOM);
    Assert.assertEquals("MyCustomValue", ConfTool.getProperty(doc, "hive.security.authorization.enabled"));
    Assert.assertEquals("MyCustomValue", ConfTool.getProperty(doc, "hive.security.authorization.manager"));
  }

  @Test public void configureHs2MetricsEnabled()
      throws IOException, SAXException, ParserConfigurationException, TransformerException {
    URL url = Thread.currentThread().getContextClassLoader().getResource("hive-site-050.xml");
    String pathToHiveSite = url.getPath();

    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
    Document doc = docBuilder.parse(pathToHiveSite);

    ConfTool.configureHs2Metrics(doc, true);
    Assert.assertEquals("true", ConfTool.getProperty(doc, "hive.server2.metrics.enabled"));
  }


  @Test public void configureHs2MetricsDisabled()
      throws IOException, SAXException, ParserConfigurationException, TransformerException {
    URL url = Thread.currentThread().getContextClassLoader().getResource("hive-site-051.xml");
    String pathToHiveSite = url.getPath();

    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
    Document doc = docBuilder.parse(pathToHiveSite);

    ConfTool.configureHs2Metrics(doc, false);
    Assert.assertEquals("false", ConfTool.getProperty(doc, "hive.server2.metrics.enabled"));
  }


  @Test public void configureMetastoreMetricsEnabled()
      throws IOException, SAXException, ParserConfigurationException, TransformerException {
    URL url = Thread.currentThread().getContextClassLoader().getResource("hive-site-052.xml");
    String pathToHiveSite = url.getPath();

    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
    Document doc = docBuilder.parse(pathToHiveSite);

    ConfTool.configureMetastoreMetrics(doc, true);
    Assert.assertEquals("true", ConfTool.getProperty(doc, "hive.metastore.metrics.enabled"));
  }


  @Test public void configureMetastoreMetricsDisabled()
      throws IOException, SAXException, ParserConfigurationException, TransformerException {
    URL url = Thread.currentThread().getContextClassLoader().getResource("hive-site-053.xml");
    String pathToHiveSite = url.getPath();

    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
    Document doc = docBuilder.parse(pathToHiveSite);

    ConfTool.configureMetastoreMetrics(doc, false);
    Assert.assertEquals("false", ConfTool.getProperty(doc, "hive.metastore.metrics.enabled"));
  }
}
