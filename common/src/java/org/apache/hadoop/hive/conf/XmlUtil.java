/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.hadoop.hive.conf;

import com.google.common.annotations.VisibleForTesting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URL;

/**
 * Utility class to read hive-site.xml.
 */
public final class XmlUtil {
  private XmlUtil(){}

  private static final Logger LOG = LoggerFactory.getLogger(XmlUtil.class.getName());
  private static final String CONFIGURATION = "configuration";
  private static final String NAME = "name";

  /**
   * Checks if property presents in file.
   *
   * @param url hive-site location
   * @param confVars property name
   * @return true if property is in hive-site.xml
   */

  static boolean existsIn(URL url, HiveConf.ConfVars confVars) {
    try {
      Document doc = readDocument(url);
      return propertyExists(doc, confVars);
    } catch (Exception e) {
      LOG.error(e.toString());
    }
    return false;
  }


  private static Document readDocument(URL url)
      throws ParserConfigurationException, IOException, SAXException {
    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
    return docBuilder.parse(url.getPath());
  }

  @VisibleForTesting
  static boolean propertyExists(Document doc, HiveConf.ConfVars confVars) {
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


  @VisibleForTesting
  static Node getConfigurationNode(Document doc) {
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
}
