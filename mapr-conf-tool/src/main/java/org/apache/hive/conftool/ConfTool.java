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
  private static final String PAM = "PAM";
  private static final String NONE = "NONE";
  private static final String EMPTY = "";

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

  static void enableHs2Ha(String pathToHiveSite, String zookeeperQuorum) throws ParserConfigurationException, IOException, SAXException, TransformerException {
    Document doc = readDocument(pathToHiveSite);
    LOG.info("Reading hive-site.xml from path : {}", pathToHiveSite);
    set(doc, HIVE_SERVER2_SUPPORT_DYNAMIC_SERVICE_DISCOVERY, TRUE);
    set(doc, HIVE_ZOOKEEPER_QUORUM, zookeeperQuorum);
    saveToFile(doc, pathToHiveSite);
  }

  static void initMetaStoreUri(String pathToHiveSite) throws ParserConfigurationException, IOException, SAXException, TransformerException {
    Document doc = readDocument(pathToHiveSite);
    LOG.info("Reading hive-site.xml from path : {}", pathToHiveSite);
    set(doc, METASTOREURIS, THRIFT_LOCAL_HOST);
    saveToFile(doc, pathToHiveSite);
  }

  private static Document readDocument(String pathToHiveSite) throws ParserConfigurationException, IOException, SAXException {
    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
    return docBuilder.parse(pathToHiveSite);
  }

  private static void set(Document doc, ConfVars confVars, String value){
    if (propertyExists(doc, confVars)) {
      LOG.info("Property {} exists in hive-site.xml", confVars);
      setProperty(doc, confVars, value);
    } else {
      LOG.info("Property {} does not exist in hive-site.xml", confVars);
      addProperty(doc, confVars, value);
    }
  }

  private static void remove(Document doc, ConfVars confVars){
    if (propertyExists(doc, confVars)) {
      LOG.info("Property {} exists in hive-site.xml", confVars);
      removeProperty(doc, confVars);
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

  static void addProperty(Document doc, ConfVars confVars, String value) {
    LOG.info("Adding property to hive-site.xml: {} = {}", confVars.varname, value);
    Element property = doc.createElement(PROPERTY);
    addName(doc, property, confVars);
    addValue(doc, property, value);
    getConfigurationNode(doc).appendChild(property);
  }

  static void removeProperty(Document doc, ConfVars confVars){
    LOG.info("Removing property from hive-site.xml: {} = {}", confVars.varname);
    Node configuration = getConfigurationNode(doc);
    NodeList properties = configuration.getChildNodes();
    int length = properties.getLength();
    for (int i = 0; i <= length - 1; i++) {
      Node node = properties.item(i);
      NodeList nameValueDesc = node.getChildNodes();
      int childLength = nameValueDesc.getLength();
      for (int j = 0; j <= childLength - 1; j++) {
        Node childNode = nameValueDesc.item(j);
        if (NAME.equals(childNode.getNodeName()) && confVars.varname.equals(childNode.getTextContent())) {
          configuration.removeChild(properties.item(i));
          return;
        }
      }
    }
  }

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

  private static void addName(Document doc, Node property, ConfVars confVars) {
    Element name = doc.createElement(NAME);
    name.appendChild(doc.createTextNode(confVars.varname));
    property.appendChild(name);
  }

  private static void addValue(Document doc, Node property, String value) {
    Element name = doc.createElement(VALUE);
    name.appendChild(doc.createTextNode(value));
    property.appendChild(name);
  }



  static boolean propertyExists(Document doc, ConfVars confVars) {
    LOG.info("Checking that property exists in hive-site.xml : {}", confVars.varname);
    Node configuration = getConfigurationNode(doc);
    NodeList properties = configuration.getChildNodes();
    int length = properties.getLength();
    for (int i = 0; i <= length - 1; i++) {
      Node node = properties.item(i);
      NodeList nameValueDesc = node.getChildNodes();
      int childLength = nameValueDesc.getLength();
      for (int j = 0; j <= childLength - 1; j++) {
        Node childNode = nameValueDesc.item(j);
        if (NAME.equals(childNode.getNodeName()) && confVars.varname.equals(childNode.getTextContent())) {
          return true;
        }
      }
    }
    return false;
  }

  static void setProperty(Document doc, ConfVars confVars, String value) {
    LOG.info("Setting value to existing property in hive-site.xml: {} = {}", confVars.varname, value);
    Node configuration = getConfigurationNode(doc);
    NodeList properties = configuration.getChildNodes();
    int length = properties.getLength();
    for (int i = 0; i <= length - 1; i++) {
      Node node = properties.item(i);
      NodeList nameValueDesc = node.getChildNodes();
      int childLength = nameValueDesc.getLength();
      for (int j = 0; j <= childLength - 1; j++) {
        Node childNode = nameValueDesc.item(j);
        if (NAME.equals(childNode.getNodeName()) && confVars.varname.equals(childNode.getTextContent())) {
          writeValue(nameValueDesc, value);
        }
      }
    }
  }

  static String getProperty(Document doc, ConfVars confVars) {
    Node configuration = getConfigurationNode(doc);
    NodeList properties = configuration.getChildNodes();
    int length = properties.getLength();
    for (int i = 0; i <= length - 1; i++) {
      Node node = properties.item(i);
      NodeList nameValueDesc = node.getChildNodes();
      int childLength = nameValueDesc.getLength();
      for (int j = 0; j <= childLength - 1; j++) {
        Node childNode = nameValueDesc.item(j);
        if (NAME.equals(childNode.getNodeName()) && confVars.varname.equals(childNode.getTextContent())) {
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
