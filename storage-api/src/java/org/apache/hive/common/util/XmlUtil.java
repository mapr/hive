package org.apache.hive.common.util;

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
  private XmlUtil() {
  }

  private static final Logger LOG = LoggerFactory.getLogger(XmlUtil.class.getName());
  private static final String CONFIGURATION = "configuration";
  private static final String NAME = "name";
  private static final String VALUE = "value";
  private static final String EMPTY = "";

  /**
   * Checks if property presents in file.
   *
   * @param url hive-site location
   * @param property property name
   * @return true if property is in hive-site.xml
   */

  public static boolean existsIn(URL url, String property) {
    try {
      Document doc = readDocument(url);
      return propertyExists(doc, property);
    } catch (Exception e) {
      LOG.error(e.toString());
    }
    return false;
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

  public static String getProperty(URL url, String property) {
    try {
      Document doc = readDocument(url);
      return getProperty(doc, property);
    } catch (Exception e) {
      LOG.error(e.toString());
    }
    return "";
  }

  private static Document readDocument(URL url) throws ParserConfigurationException, IOException, SAXException {
    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
    return docBuilder.parse(url.getPath());
  }

  private static boolean propertyExists(Document doc, String property) {
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

  private static String getProperty(Document doc, String property) {
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
