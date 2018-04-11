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

public class ConfToolTest {
  private static final Logger LOG = LoggerFactory.getLogger(ConfTool.class.getName());

  @Test
  public void setMaprSaslTrueTest() throws IOException, ParserConfigurationException, SAXException, TransformerException {
    URL url = Thread.currentThread().getContextClassLoader().getResource("hive-site-003.xml");
    String pathToHiveSite = url.getPath();
    ConfTool.setMaprSasl(pathToHiveSite, true);

    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
    Document doc = docBuilder.parse(pathToHiveSite);
    Assert.assertEquals("true", ConfTool.getProperty(doc, ConfVars.METASTORE_USE_THRIFT_SASL));
  }

  @Test
  public void enableHs2HaTest() throws IOException, ParserConfigurationException, SAXException, TransformerException {
    URL url = Thread.currentThread().getContextClassLoader().getResource("hive-site-010.xml");
    String pathToHiveSite = url.getPath();
    ConfTool.enableHs2Ha(pathToHiveSite, "node1,node2");

    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
    Document doc = docBuilder.parse(pathToHiveSite);
    Assert.assertEquals("true", ConfTool.getProperty(doc, ConfVars.HIVE_SERVER2_SUPPORT_DYNAMIC_SERVICE_DISCOVERY));
    Assert.assertEquals("node1,node2", ConfTool.getProperty(doc, ConfVars.HIVE_ZOOKEEPER_QUORUM));
  }

  @Test
  public void setMaprSaslTrueTest2() throws IOException, ParserConfigurationException, SAXException, TransformerException {
    URL url = Thread.currentThread().getContextClassLoader().getResource("hive-site-005.xml");
    String pathToHiveSite = url.getPath();
    ConfTool.setMaprSasl(pathToHiveSite, true);

    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
    Document doc = docBuilder.parse(pathToHiveSite);
    Assert.assertEquals("true", ConfTool.getProperty(doc, ConfVars.METASTORE_USE_THRIFT_SASL));
  }

  @Test
  public void setMaprSaslFalseTest() throws IOException, ParserConfigurationException, SAXException, TransformerException {
    URL url = Thread.currentThread().getContextClassLoader().getResource("hive-site-004.xml");
    String pathToHiveSite = url.getPath();
    ConfTool.setMaprSasl(pathToHiveSite, false);

    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
    Document doc = docBuilder.parse(pathToHiveSite);
    Assert.assertEquals("false", ConfTool.getProperty(doc, ConfVars.METASTORE_USE_THRIFT_SASL));
  }


  @Test
  public void enableEncryptionTrueTest() throws IOException, ParserConfigurationException, SAXException, TransformerException {
    URL url = Thread.currentThread().getContextClassLoader().getResource("hive-site-008.xml");
    String pathToHiveSite = url.getPath();
    ConfTool.setEncryption(pathToHiveSite, true);

    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
    Document doc = docBuilder.parse(pathToHiveSite);
    Assert.assertEquals("auth-conf", ConfTool.getProperty(doc, ConfVars.HIVE_SERVER2_THRIFT_SASL_QOP));
  }

  @Test
  public void enableEncryptionFalseTest() throws IOException, ParserConfigurationException, SAXException, TransformerException {
    URL url = Thread.currentThread().getContextClassLoader().getResource("hive-site-008.xml");
    String pathToHiveSite = url.getPath();
    ConfTool.setEncryption(pathToHiveSite, false);

    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
    Document doc = docBuilder.parse(pathToHiveSite);
    Assert.assertFalse(ConfTool.propertyExists(doc, ConfVars.HIVE_SERVER2_THRIFT_SASL_QOP));
  }


  @Test
  public void hiveMetaStoreSaslEnabledExistsTestTrue() throws ParserConfigurationException, IOException, SAXException {
    URL url = Thread.currentThread().getContextClassLoader().getResource("hive-site-001.xml");
    String pathToHiveSite = url.getPath();

    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
    Document doc = docBuilder.parse(pathToHiveSite);
    Assert.assertTrue(ConfTool.propertyExists(doc, ConfVars.METASTORE_USE_THRIFT_SASL));
  }


  @Test
  public void propertyExistsTestFalse() throws ParserConfigurationException, IOException, SAXException {
    URL url = Thread.currentThread().getContextClassLoader().getResource("hive-site-002.xml");
    String pathToHiveSite = url.getPath();

    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
    Document doc = docBuilder.parse(pathToHiveSite);
    Assert.assertFalse(ConfTool.propertyExists(doc, ConfVars.METASTORE_USE_THRIFT_SASL));
  }

  @Test
  public void getPropertyTest() throws ParserConfigurationException, IOException, SAXException {
    URL url = Thread.currentThread().getContextClassLoader().getResource("hive-site-001.xml");
    String pathToHiveSite = url.getPath();

    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
    Document doc = docBuilder.parse(pathToHiveSite);

    Assert.assertEquals("true", ConfTool.getProperty(doc, ConfVars.METASTORE_USE_THRIFT_SASL));
    Assert.assertEquals("MAPRSASL", ConfTool.getProperty(doc, ConfVars.HIVE_SERVER2_AUTHENTICATION));
  }

  @Test
  public void setPropertyTest() throws ParserConfigurationException, IOException, SAXException {
    URL url = Thread.currentThread().getContextClassLoader().getResource("hive-site-001.xml");
    String pathToHiveSite = url.getPath();

    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
    Document doc = docBuilder.parse(pathToHiveSite);

    String testValue = "AAbbbCCC";
    ConfTool.setProperty(doc, ConfVars.METASTORE_USE_THRIFT_SASL, testValue);
    Assert.assertEquals(testValue, ConfTool.getProperty(doc, ConfVars.METASTORE_USE_THRIFT_SASL));
  }

  @Test
  public void AddPropertyTest() throws ParserConfigurationException, IOException, SAXException {
    URL url = Thread.currentThread().getContextClassLoader().getResource("hive-site-002.xml");
    String pathToHiveSite = url.getPath();

    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
    Document doc = docBuilder.parse(pathToHiveSite);

    Assert.assertFalse(ConfTool.propertyExists(doc, ConfVars.METASTORE_USE_THRIFT_SASL));

    String testValue = "AAbbbCCC";
    ConfTool.addProperty(doc, ConfVars.METASTORE_USE_THRIFT_SASL, testValue);
    LOG.info(ConfTool.toString(doc));

    Assert.assertTrue(ConfTool.propertyExists(doc, ConfVars.METASTORE_USE_THRIFT_SASL));
    Assert.assertEquals(testValue, ConfTool.getProperty(doc, ConfVars.METASTORE_USE_THRIFT_SASL));
  }


  @Test
  public void getConfigurationNodeTest() throws ParserConfigurationException, IOException, SAXException {
    URL url = Thread.currentThread().getContextClassLoader().getResource("hive-site-006.xml");
    String pathToHiveSite = url.getPath();
    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
    Document doc = docBuilder.parse(pathToHiveSite);
    Assert.assertNotNull(ConfTool.getConfigurationNode(doc));
  }

  @Test
  public void setMaprSaslTrueComplexTest() throws IOException, ParserConfigurationException, SAXException, TransformerException {
    URL url = Thread.currentThread().getContextClassLoader().getResource("hive-site-006.xml");
    String pathToHiveSite = url.getPath();
    ConfTool.setMaprSasl(pathToHiveSite, true);

    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
    Document doc = docBuilder.parse(pathToHiveSite);
    Assert.assertEquals("true", ConfTool.getProperty(doc, ConfVars.METASTORE_USE_THRIFT_SASL));
  }


  @Test
  public void removePropertyTest() throws IOException, SAXException, ParserConfigurationException {
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


  @Test
  public void setConnectionUrlTest() throws IOException, ParserConfigurationException, SAXException, TransformerException {
    URL url = Thread.currentThread().getContextClassLoader().getResource("hive-site-012.xml");
    String pathToHiveSite = url.getPath();
    String connectionUrl = "jdbc:derby:;databaseName=/opt/mapr/hive/hive-2.1/bin/metastore_db;create=true";
    ConfTool.setConnectionUrl(pathToHiveSite, connectionUrl);

    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
    Document doc = docBuilder.parse(pathToHiveSite);
    Assert.assertEquals(connectionUrl, ConfTool.getProperty(doc, ConfVars.METASTORECONNECTURLKEY));
  }


  @Test
  public void setHs2WebUiPamSslTest() throws IOException, ParserConfigurationException, SAXException, TransformerException {
    URL url = Thread.currentThread().getContextClassLoader().getResource("hive-site-013.xml");
    String pathToHiveSite = url.getPath();
    ConfTool.setHs2WebUiPamSsl(pathToHiveSite, true);

    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
    Document doc = docBuilder.parse(pathToHiveSite);
    Assert.assertEquals("true", ConfTool.getProperty(doc, ConfVars.HIVE_SERVER2_WEBUI_USE_PAM));
    Assert.assertEquals("true", ConfTool.getProperty(doc, ConfVars.HIVE_SERVER2_WEBUI_USE_SSL));
    Assert.assertEquals("/opt/mapr/conf/ssl_keystore", ConfTool.getProperty(doc, ConfVars.HIVE_SERVER2_WEBUI_SSL_KEYSTORE_PATH));
    Assert.assertEquals("mapr123", ConfTool.getProperty(doc, ConfVars.HIVE_SERVER2_WEBUI_SSL_KEYSTORE_PASSWORD));
  }


  @Test
  public void disableHs2WebUiPamSslTest() throws IOException, ParserConfigurationException,
      SAXException, TransformerException {
    URL url = Thread.currentThread().getContextClassLoader().getResource("hive-site-013.xml");
    String pathToHiveSite = url.getPath();
    ConfTool.setHs2WebUiPamSsl(pathToHiveSite, false);

    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
    Document doc = docBuilder.parse(pathToHiveSite);
    Assert.assertFalse(ConfTool.propertyExists(doc, ConfVars.HIVE_SERVER2_WEBUI_USE_PAM));
    Assert.assertFalse(ConfTool.propertyExists(doc, ConfVars.HIVE_SERVER2_WEBUI_USE_SSL));
    Assert.assertFalse(ConfTool.propertyExists(doc, ConfVars.HIVE_SERVER2_WEBUI_SSL_KEYSTORE_PATH));
    Assert.assertFalse(ConfTool.propertyExists(doc, ConfVars.HIVE_SERVER2_WEBUI_SSL_KEYSTORE_PASSWORD));
  }


  @Test
  public void setWebHCatSslTest() throws IOException, ParserConfigurationException, SAXException,
      TransformerException {
    URL url = Thread.currentThread().getContextClassLoader().getResource("webhcat-site-001.xml");
    String pathToWebHCatSite = url.getPath();
    ConfTool.setWebHCatSsl(pathToWebHCatSite, true);

    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
    Document doc = docBuilder.parse(pathToWebHCatSite);
    Assert.assertEquals("true", ConfTool.getProperty(doc, AppConfig.USE_SSL));
    Assert.assertEquals("/opt/mapr/conf/ssl_keystore", ConfTool.getProperty(doc, AppConfig.KEY_STORE_PATH));
    Assert.assertEquals("mapr123", ConfTool.getProperty(doc, AppConfig.KEY_STORE_PASSWORD));
  }

  @Test
  public void disableWebHCatSslTest() throws IOException, ParserConfigurationException,
      SAXException,
      TransformerException {
    URL url = Thread.currentThread().getContextClassLoader().getResource("webhcat-site-001.xml");
    String pathToWebHCatSite = url.getPath();
    ConfTool.setWebHCatSsl(pathToWebHCatSite, false);

    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
    Document doc = docBuilder.parse(pathToWebHCatSite);
    Assert.assertFalse(ConfTool.propertyExists(doc, AppConfig.USE_SSL));
    Assert.assertFalse(ConfTool.propertyExists(doc, AppConfig.KEY_STORE_PATH));
    Assert.assertFalse(ConfTool.propertyExists(doc, AppConfig.KEY_STORE_PASSWORD));
  }


  @Test
  public void setMetaStoreUgiTest() throws IOException, ParserConfigurationException, SAXException,
      TransformerException {
    URL url = Thread.currentThread().getContextClassLoader().getResource("hive-site-014.xml");
    String pathToWebHCatSite = url.getPath();
    ConfTool.setMetaStoreUgi(pathToWebHCatSite, true);

    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
    Document doc = docBuilder.parse(pathToWebHCatSite);
    Assert.assertEquals("false", ConfTool.getProperty(doc, ConfVars.METASTORE_EXECUTE_SET_UGI));
  }

  @Test
  public void disableMetaStoreUgiTest() throws IOException, ParserConfigurationException,
      SAXException,
      TransformerException {
    URL url = Thread.currentThread().getContextClassLoader().getResource("hive-site-015.xml");
    String pathToWebHCatSite = url.getPath();
    ConfTool.setMetaStoreUgi(pathToWebHCatSite, false);

    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
    Document doc = docBuilder.parse(pathToWebHCatSite);
    Assert.assertFalse(ConfTool.propertyExists(doc, ConfVars.METASTORE_EXECUTE_SET_UGI));
  }

  @Test
  public void delPropertyTest()
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

  @Test
  public void addPropertyTest()
      throws SAXException, TransformerException, ParserConfigurationException, IOException {
    URL url = Thread.currentThread().getContextClassLoader().getResource("hive-site-017.xml");
    String pathToHiveSite = url.getPath();
    String property = "test.property.to.add";
    String value = "test.value.to.add";
    ConfTool.addProperty(pathToHiveSite, property, value);

    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
    Document doc = docBuilder.parse(pathToHiveSite);
    Assert.assertTrue(ConfTool.propertyExists(doc, property));
  }

  @Test
  public void getPropertyFromFileTest()
      throws SAXException, ParserConfigurationException, IOException {
    URL url = Thread.currentThread().getContextClassLoader().getResource("hive-site-021.xml");
    String pathToHiveSite = url.getPath();
    String property = "test.property.to.get";
    ConfTool.getProperty(pathToHiveSite, property);
    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
    Assert.assertEquals("test.value", ConfTool.getProperty(pathToHiveSite, property));
  }
}
