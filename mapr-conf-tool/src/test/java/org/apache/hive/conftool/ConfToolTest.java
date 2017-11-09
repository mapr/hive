package org.apache.hive.conftool;

import org.apache.hadoop.hive.conf.HiveConf.ConfVars;
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
}