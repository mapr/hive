/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.hive.conftool;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import com.google.common.annotations.VisibleForTesting;
import org.apache.commons.lang.StringUtils;
import org.apache.hive.hcatalog.templeton.AppConfig;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.hadoop.hive.conf.HiveConf.ConfVars;
import static org.apache.hadoop.hive.conf.HiveConf.ConfVars.METASTORE_USE_THRIFT_SASL;
import static org.apache.hadoop.hive.conf.HiveConf.ConfVars.HIVE_SERVER2_THRIFT_SASL_QOP;
import static org.apache.hadoop.hive.conf.HiveConf.ConfVars.HIVE_SERVER2_SUPPORT_DYNAMIC_SERVICE_DISCOVERY;
import static org.apache.hadoop.hive.conf.HiveConf.ConfVars.HIVE_ZOOKEEPER_QUORUM;
import static org.apache.hadoop.hive.conf.HiveConf.ConfVars.METASTOREURIS;
import static org.apache.hadoop.hive.conf.HiveConf.ConfVars.METASTORECONNECTURLKEY;
import static org.apache.hadoop.hive.conf.HiveConf.ConfVars.METASTOREPWD;
import static org.apache.hadoop.hive.conf.HiveConf.ConfVars.HIVE_SERVER2_WEBUI_USE_PAM;
import static org.apache.hadoop.hive.conf.HiveConf.ConfVars.HIVE_SERVER2_WEBUI_USE_SSL;
import static org.apache.hadoop.hive.conf.HiveConf.ConfVars.HIVE_SERVER2_WEBUI_SSL_KEYSTORE_PATH;
import static org.apache.hadoop.hive.conf.HiveConf.ConfVars.HIVE_SERVER2_WEBUI_SSL_KEYSTORE_PASSWORD;
import static org.apache.hadoop.hive.conf.HiveConf.ConfVars.METASTORE_EXECUTE_SET_UGI;

/**
 *  Helper for configuring Hive
 */

class ConfTool {
  private ConfTool() {
  }

  private static final Logger LOG = LoggerFactory.getLogger(ConfTool.class.getName());
  private static final String NAME = "name";
  private static final String VALUE = "value";
  private static final String PROPERTY = "property";
  private static final String CONFIGURATION = "configuration";
  private static final String TRUE = "true";
  private static final String FALSE = "false";
  private static final String AUTH_CONF = "auth-conf";
  private static final String THRIFT_LOCAL_HOST = "thrift://localhost:9083";
  private static final String MAPR_DEFAULT_SSL_KEYSTORE_PATH = "/opt/mapr/conf/ssl_keystore";
  private static final String EMPTY = "";

  /**
   * Converts xml doc to String
   *
    * @param doc Document to print
   * @return xml document as String
   */

  static String toString(Document doc){

    Node configuration = doc.getFirstChild();
    NodeList properties = configuration.getChildNodes();
    int length = properties.getLength();

    StringBuilder sb = new StringBuilder();
    for (int i = 0; i <= length - 1; i++) {
      Node node = properties.item(i);
      NodeList nameValueDesc = node.getChildNodes();
      int childLength = nameValueDesc.getLength();
      for (int j = 0; j <= childLength - 1; j++) {
        Node childNode = nameValueDesc.item(j);
        sb.append(childNode.getNodeName() + " = " + childNode.getTextContent());
        sb.append("\n");
      }
    }
    return sb.toString();
  }

  /**
   * Enables/disable Mapr-Sasl security for Hive.
   *
   * @param pathToHiveSite hive-site location
   * @param secure true if Mapr Sasl security is enabled on the cluster
   * @throws TransformerException
   * @throws IOException
   * @throws SAXException
   * @throws ParserConfigurationException
   */

  static void setMaprSasl(String pathToHiveSite, boolean secure) throws TransformerException, IOException, SAXException, ParserConfigurationException {
    Document doc = readDocument(pathToHiveSite);
    LOG.info("Reading hive-site.xml from path : {}", pathToHiveSite);
    if (secure) {
      LOG.info("Configuring Hive for MAPR-SASL");
      set(doc, METASTORE_USE_THRIFT_SASL, TRUE);
    } else {
      LOG.info("Configuring Hive for no security");
      set(doc, METASTORE_USE_THRIFT_SASL, FALSE);
    }
    saveToFile(doc, pathToHiveSite);
  }

  /**
   * Configures hive.metastore.execute.setugi. When one need to set "true" value, this method
   * just removes property from hive-site.xml since it has default true value.
   *
   * @param pathToHiveSite  hive-site location
   * @param security true if Mapr Sasl security is enabled on the cluster
   * @throws TransformerException
   * @throws IOException
   * @throws SAXException
   * @throws ParserConfigurationException
   */

  static void setMetaStoreUgi(String pathToHiveSite, boolean security) throws TransformerException,
      IOException, SAXException, ParserConfigurationException {
    Document doc = readDocument(pathToHiveSite);
    LOG.info("Reading hive-site.xml from path : {}", pathToHiveSite);
    if (security) {
      LOG.info("Configuring metastore not to use UGI");
      set(doc, METASTORE_EXECUTE_SET_UGI, FALSE);
    } else {
      LOG.info("Configuring metastore to use UGI. Default is true, so removing property from "
          + "hive-site.xml to enable it");
      remove(doc, METASTORE_EXECUTE_SET_UGI);
    }
    saveToFile(doc, pathToHiveSite);
  }

  /**
   * Configures PAM and SSL encryption for HiveServer2 web UI
   *
   * @param pathToHiveSite hive-site location
   * @param security true if Mapr Sasl security is enabled on the cluster
   * @throws TransformerException
   * @throws IOException
   * @throws SAXException
   * @throws ParserConfigurationException
   */

  static void setHs2WebUiPamSsl(String pathToHiveSite, boolean security) throws
      TransformerException, IOException,
      SAXException, ParserConfigurationException {
    Document doc = readDocument(pathToHiveSite);
    LOG.info("Reading hive-site.xml from path : {}", pathToHiveSite);
    if(security) {
      LOG.info("Configuring Hive for HiveServer2 web UI PAM authentication and SSL encryption");
      set(doc, HIVE_SERVER2_WEBUI_USE_PAM, TRUE);
      set(doc, HIVE_SERVER2_WEBUI_USE_SSL, TRUE);
      set(doc, HIVE_SERVER2_WEBUI_SSL_KEYSTORE_PATH, MAPR_DEFAULT_SSL_KEYSTORE_PATH);
      set(doc, HIVE_SERVER2_WEBUI_SSL_KEYSTORE_PASSWORD, new String
          (new char[]{'m', 'a', 'p', 'r', '1', '2', '3'}));
    } else {
      LOG.info("Disabling PAM authentication and SSL encryption for HiveServer2 web UI");
      remove(doc, HIVE_SERVER2_WEBUI_USE_PAM);
      remove(doc, HIVE_SERVER2_WEBUI_USE_SSL);
      remove(doc, HIVE_SERVER2_WEBUI_SSL_KEYSTORE_PATH);
      remove(doc, HIVE_SERVER2_WEBUI_SSL_KEYSTORE_PATH);
    }
    saveToFile(doc, pathToHiveSite);
  }

  /**
   * Configures Ssl encryption for webHCat server
   *
   * @param pathToWebHCatSite webhcat-site.xml location
   * @param security true if Mapr Sasl security is enabled on the cluster
   * @throws TransformerException
   * @throws IOException
   * @throws SAXException
   * @throws ParserConfigurationException
   */

  static void setWebHCatSsl(String pathToWebHCatSite, boolean security) throws
      TransformerException, IOException,
      SAXException, ParserConfigurationException {
    Document doc = readDocument(pathToWebHCatSite);
    LOG.info("Reading webhcat-site.xml from path : {}", pathToWebHCatSite);
    if(security) {
      LOG.info("Configuring webHCat for  SSL encryption");
      set(doc, AppConfig.USE_SSL, TRUE);
      set(doc, AppConfig.KEY_STORE_PATH, MAPR_DEFAULT_SSL_KEYSTORE_PATH);
      set(doc, AppConfig.KEY_STORE_PASSWORD, new String
          (new char[]{'m', 'a', 'p', 'r', '1', '2', '3'}));
    } else {
      remove(doc, AppConfig.USE_SSL);
      remove(doc, AppConfig.KEY_STORE_PATH);
      remove(doc, AppConfig.KEY_STORE_PASSWORD);
    }
    saveToFile(doc, pathToWebHCatSite);
  }

  /**
   * Configures Ssl encryption for HiveServer2
   *
   * @param pathToHiveSite hive-site location
   * @param secure true if Mapr Sasl security is enabled on the cluster
   * @throws TransformerException
   * @throws IOException
   * @throws SAXException
   * @throws ParserConfigurationException
   */

  static void setEncryption(String pathToHiveSite, boolean secure) throws TransformerException, IOException, SAXException, ParserConfigurationException {
    Document doc = readDocument(pathToHiveSite);
    LOG.info("Reading hive-site.xml from path : {}", pathToHiveSite);
    if (secure) {
      set(doc, HIVE_SERVER2_THRIFT_SASL_QOP, AUTH_CONF);
    } else {
      remove(doc, HIVE_SERVER2_THRIFT_SASL_QOP);
    }
    saveToFile(doc, pathToHiveSite);
  }

  /**
   * Enables HiveServer2 HA
   *
   * @param pathToHiveSite hive-site location
   * @param zookeeperQuorum comma separated list of nodes of Zookeeper quorum
   * @throws ParserConfigurationException
   * @throws IOException
   * @throws SAXException
   * @throws TransformerException
   */


  static void enableHs2Ha(String pathToHiveSite, String zookeeperQuorum) throws ParserConfigurationException, IOException, SAXException, TransformerException {
    Document doc = readDocument(pathToHiveSite);
    LOG.info("Reading hive-site.xml from path : {}", pathToHiveSite);
    set(doc, HIVE_SERVER2_SUPPORT_DYNAMIC_SERVICE_DISCOVERY, TRUE);
    set(doc, HIVE_ZOOKEEPER_QUORUM, zookeeperQuorum);
    saveToFile(doc, pathToHiveSite);
  }

  /**
   * Checks if property presents in file
   *
   * @param pathToHiveSite hive-site location
   * @param property property name
   * @return true if property is in hive-site.xml
   * @throws IOException
   * @throws SAXException
   * @throws ParserConfigurationException
   */

  static boolean exists(String pathToHiveSite, String property) throws IOException, SAXException, ParserConfigurationException {
    Document doc = readDocument(pathToHiveSite);
    return propertyExists(doc, property);
  }

  /**
   *  Removes property from xml file
   *  IN-827
   * @param pathToHiveSite hive-site location
   * @throws IOException
   * @throws SAXException
   * @throws ParserConfigurationException
   */
  static void delProperty(String pathToHiveSite, String property) throws IOException, SAXException, ParserConfigurationException, TransformerException {
    Document doc = readDocument(pathToHiveSite);
    LOG.info("Reading xml from path : {}", pathToHiveSite);
    LOG.info("Removing {} property from {}", property, pathToHiveSite);
    remove(doc, property);
    saveToFile(doc, pathToHiveSite);
  }

  /**
   * Adds property to xml file. If property already exists, it replaces its value with new one.
   * Uses following template
   * <property>
   *   <name>property.name</name>
   *   <value>property.value</value>
   * </property>
   *
   * @param pathToHiveSite hive-site location
   * @param property name of the property
   * @param value value of the property
   * @throws IOException
   * @throws SAXException
   * @throws ParserConfigurationException
   * @throws TransformerException
   */

  static void addProperty(String pathToHiveSite, String property, String value)
      throws IOException, SAXException, ParserConfigurationException, TransformerException {
    Document doc = readDocument(pathToHiveSite);
    addProperty(doc, property, value);
    saveToFile(doc, pathToHiveSite);
  }

  /**
   *  Return property value from xml file
   * @param pathToHiveSite hive-site location
   * @param property name of the property
   * @return
   * @throws IOException
   * @throws SAXException
   * @throws ParserConfigurationException
   */

  static String getProperty(String pathToHiveSite, String property) throws IOException, SAXException, ParserConfigurationException {
    Document doc = readDocument(pathToHiveSite);
    return getProperty(doc, property);
  }


  /**
   * Adds hive.metastore.uris=localhost in hive-site.xml
   *
   * @param pathToHiveSite hive-site location
   * @throws ParserConfigurationException
   * @throws IOException
   * @throws SAXException
   * @throws TransformerException
   */

  static void initMetaStoreUri(String pathToHiveSite) throws ParserConfigurationException, IOException, SAXException, TransformerException {
    Document doc = readDocument(pathToHiveSite);
    LOG.info("Reading hive-site.xml from path : {}", pathToHiveSite);
    set(doc, METASTOREURIS, THRIFT_LOCAL_HOST);
    saveToFile(doc, pathToHiveSite);
  }

  /**
   * Sets javax.jdo.option.ConnectionURL
   *
   * @param pathToHiveSite hive-site location
   * @param connectionUrl value to set for ConnectionURL
   * @throws ParserConfigurationException
   * @throws IOException
   * @throws SAXException
   * @throws TransformerException
   */

  static void setConnectionUrl(String pathToHiveSite, String connectionUrl) throws ParserConfigurationException, IOException, SAXException, TransformerException {
    Document doc = readDocument(pathToHiveSite);
    LOG.info("Reading hive-site.xml from path : {}", pathToHiveSite);
    set(doc, METASTORECONNECTURLKEY, connectionUrl);
    saveToFile(doc, pathToHiveSite);
  }


  private static Document readDocument(String pathToHiveSite) throws ParserConfigurationException, IOException, SAXException {
    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
    return docBuilder.parse(pathToHiveSite);
  }


  private static void set(Document doc, String property, String value){
    if (propertyExists(doc, property)) {
      LOG.info("Property {} exists in xml file", property);
      setProperty(doc, property, value);
    } else {
      LOG.info("Property {} does not exist in xml file", property);
      addProperty(doc, property, value);
    }
  }


  private static void set(Document doc, ConfVars confVars, String value){
    set(doc, confVars.varname, value);
  }

  private static void remove(Document doc, ConfVars confVars){
    remove(doc, confVars.varname);
  }

  private static void remove(Document doc, String property){
    if (propertyExists(doc, property)) {
      LOG.info("Property {} exists in hive-site.xml", property);
      removeProperty(doc, property);
    }
  }

  private static void saveToFile(Document doc, String filepath) throws TransformerException {
    TransformerFactory transformerFactory = TransformerFactory.newInstance();
    Transformer transformer = transformerFactory.newTransformer();
    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
    transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
    DOMSource source = new DOMSource(doc);
    StreamResult result = new StreamResult(new File(filepath));
    transformer.transform(source, result);
  }

  @VisibleForTesting
  static void addProperty(Document doc, ConfVars confVars, String value) {
    addProperty(doc, confVars.varname, value);
  }

  private static void addProperty(Document doc, String property, String value) {
    LOG.info("Adding property to hive-site.xml: {} = {}", property, isPassword(property) ?
        hidePassword(value) : value);
    Element element = doc.createElement(PROPERTY);
    addName(doc, element, property);
    addValue(doc, element, value);
    getConfigurationNode(doc).appendChild(element);
  }

  @VisibleForTesting
  static void removeProperty(Document doc, ConfVars confVars){
    removeProperty(doc, confVars.varname);
  }

  private static void removeProperty(Document doc, String property){
    LOG.info("Removing property from hive-site.xml: {} = {}", property);
    Node configuration = getConfigurationNode(doc);
    NodeList childNodes = configuration.getChildNodes();
    int length = childNodes.getLength();
    for (int i = 0; i <= length - 1; i++) {
      Node node = childNodes.item(i);
      NodeList nameValueDesc = node.getChildNodes();
      int childLength = nameValueDesc.getLength();
      for (int j = 0; j <= childLength - 1; j++) {
        Node childNode = nameValueDesc.item(j);
        if (NAME.equals(childNode.getNodeName()) && property.equals(childNode.getTextContent())) {
          configuration.removeChild(childNodes.item(i));
          return;
        }
      }
    }
  }

  @VisibleForTesting
  static Node getConfigurationNode(Document doc){
    NodeList nodes = doc.getChildNodes();
    int length = nodes.getLength();
    for(int i = 0; i <= length - 1; i++){
      Node node = nodes.item(i);
      if(CONFIGURATION.equals(node.getNodeName())){
        return node;
      }
    }
    throw new IllegalArgumentException("No <configuration> tag");
  }


  private static void addName(Document doc, Node node, String property) {
    Element element = doc.createElement(NAME);
    element.appendChild(doc.createTextNode(property));
    node.appendChild(element);
  }

  private static void addValue(Document doc, Node property, String value) {
    Element name = doc.createElement(VALUE);
    name.appendChild(doc.createTextNode(value));
    property.appendChild(name);
  }

  @VisibleForTesting
  static boolean propertyExists(Document doc, ConfVars confVars) {
    return propertyExists(doc, confVars.varname);
  }


  @VisibleForTesting
  static boolean propertyExists(Document doc, String property) {
    LOG.info("Checking that property exists in hive-site.xml : {}", property);
    Node configuration = getConfigurationNode(doc);
    NodeList properties = configuration.getChildNodes();
    int length = properties.getLength();
    for (int i = 0; i <= length - 1; i++) {
      Node node = properties.item(i);
      NodeList nameValueDesc = node.getChildNodes();
      int childLength = nameValueDesc.getLength();
      for (int j = 0; j <= childLength - 1; j++) {
        Node childNode = nameValueDesc.item(j);
        if (NAME.equals(childNode.getNodeName()) && property.equals(childNode.getTextContent())) {
          return true;
        }
      }
    }
    return false;
  }

  private static boolean isPassword(String property){
    String propertyLowCase = property.toLowerCase();
    return propertyLowCase.contains("password") || propertyLowCase.contains("passwd");
  }

  private static String hidePassword(String password){
    return StringUtils.repeat("*", password.length());
  }

  @VisibleForTesting
  static void setProperty(Document doc, ConfVars confVars, String value) {
    set(doc, confVars.varname, value);
  }

  private static void setProperty(Document doc, String property, String value) {
    LOG.info("Setting value to existing property in xml file: {} = {}", property, isPassword(property) ?
        hidePassword(value) : value);
    Node configuration = getConfigurationNode(doc);
    NodeList properties = configuration.getChildNodes();
    int length = properties.getLength();
    for (int i = 0; i <= length - 1; i++) {
      Node node = properties.item(i);
      NodeList nameValueDesc = node.getChildNodes();
      int childLength = nameValueDesc.getLength();
      for (int j = 0; j <= childLength - 1; j++) {
        Node childNode = nameValueDesc.item(j);
        if (NAME.equals(childNode.getNodeName()) && property.equals(childNode.getTextContent())) {
          writeValue(nameValueDesc, value);
        }
      }
    }
  }

  @VisibleForTesting
  static String getProperty(Document doc, ConfVars confVars) {
    return getProperty(doc, confVars.varname);
  }

  @VisibleForTesting
  static String getProperty(Document doc, String property) {
    Node configuration = getConfigurationNode(doc);
    NodeList properties = configuration.getChildNodes();
    int length = properties.getLength();
    for (int i = 0; i <= length - 1; i++) {
      Node node = properties.item(i);
      NodeList nameValueDesc = node.getChildNodes();
      int childLength = nameValueDesc.getLength();
      for (int j = 0; j <= childLength - 1; j++) {
        Node childNode = nameValueDesc.item(j);
        if (NAME.equals(childNode.getNodeName()) && property.equals(childNode.getTextContent())) {
          return readValue(nameValueDesc);
        }
      }
    }
    return EMPTY;
  }

  private static void writeValue(NodeList nameValueDesc, String value) {
    int childLength = nameValueDesc.getLength();
    for (int j = 0; j <= childLength - 1; j++) {
      Node childNode = nameValueDesc.item(j);
      if (VALUE.equals(childNode.getNodeName())) {
        childNode.setTextContent(value);
      }
    }
  }

  private static String readValue(NodeList nameValueDesc) {
    int childLength = nameValueDesc.getLength();
    for (int j = 0; j <= childLength - 1; j++) {
      Node childNode = nameValueDesc.item(j);
      if (VALUE.equals(childNode.getNodeName())) {
        return childNode.getTextContent();
      }
    }
    return EMPTY;
  }
}
