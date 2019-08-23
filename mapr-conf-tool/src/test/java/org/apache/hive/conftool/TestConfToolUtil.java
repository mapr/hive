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

import org.apache.hadoop.hive.conf.HiveConf;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.Map;

/**
 * Util methods for testing.
 */
final class TestConfToolUtil {

  private static final String NAME = "name";
  private static final String VALUE = "value";
  private static final String CONFIGURATION = "configuration";
  private static final String EMPTY = "";

  private TestConfToolUtil() {}

  /**
   * Parse xml from resource
   *
   * @param fileName xml file
   * @return xml document
   * @throws IOException
   * @throws SAXException
   * @throws ParserConfigurationException
   */
  static Document parseFrom(String fileName) throws IOException, SAXException, ParserConfigurationException {
    String pathToHiveSite = getPath(fileName);
    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
    return docBuilder.parse(pathToHiveSite);
  }

  /**
   * Get path of file in resources.
   *
   * @param fileName file in resources
   * @return path of file in resources
   */
  static String getPath(String fileName) {
    URL url = Thread.currentThread().getContextClassLoader().getResource(fileName);
    return url.getPath();
  }

  /**
   * Converts xml doc to String.
   *
   * @param doc Document to print
   * @return xml document as String
   */

  static String toString(Document doc) {

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
   *  Return property value from xml file.
   *
   * @param url hive-site location
   * @param property name of the property
   * @return
   * @throws IOException
   * @throws SAXException
   * @throws ParserConfigurationException
   */

  static String getStringVal(URL url, String property)
      throws IOException, SAXException, ParserConfigurationException {
    Document doc = readDocument(url.getPath());
    return getStringProperty(doc, property);
  }




  /**
   *  Return property value from xml file.
   *
   * @param url hive-site location
   * @param confVar name of the property
   * @return
   * @throws IOException
   * @throws SAXException
   * @throws ParserConfigurationException
   */

  static String getStringVal(URL url, HiveConf.ConfVars confVar)
      throws IOException, SAXException, ParserConfigurationException {
    Document doc = readDocument(url.getPath());
    return getStringProperty(doc, confVar.varname);
  }


  /**
   *  Return property value from xml file.
   *
   * @param url hive-site location
   * @param confVar name of the property
   * @return
   * @throws IOException
   * @throws SAXException
   * @throws ParserConfigurationException
   */

  static boolean getBoolVal(URL url, HiveConf.ConfVars confVar)
      throws IOException, SAXException, ParserConfigurationException {
    Document doc = readDocument(url.getPath());
    return Boolean.parseBoolean(getStringProperty(doc, confVar.varname));
  }




  private static String getStringProperty(Document doc, String property) {
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


  private static Node getConfigurationNode(Document doc) {
    NodeList nodes = doc.getChildNodes();
    int length = nodes.getLength();
    for (int i = 0; i <= length - 1; i++) {
      Node node = nodes.item(i);
      if (CONFIGURATION.equals(node.getNodeName())) {
        return node;
      }
    }
    throw new IllegalArgumentException("No <configuration> tag");
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


  private static Document readDocument(String pathToHiveSite)
      throws ParserConfigurationException, IOException, SAXException {
    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
    return docBuilder.parse(pathToHiveSite);
  }
}
