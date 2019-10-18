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

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Joiner;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hive.conf.HiveConf;
import org.apache.hadoop.hive.conf.HiveConf.ConfVars;
import org.apache.hive.hcatalog.templeton.AppConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import static org.apache.hadoop.hive.conf.HiveConf.ConfVars.*;

/**
 *  Helper for configuring Hive.
 */

public final class ConfTool {
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
  private static final String METASTORE_SECURE_AUTH_MANAGER =
      "org.apache.hadoop.hive.ql.security.authorization.StorageBasedAuthorizationProvider";
  private static final HiveConf.ConfVars[] IMMUTABLE_OPTIONS =
      { PREEXECHOOKS, POSTEXECHOOKS, ONFAILUREHOOKS, QUERYREDACTORHOOKS, SEMANTIC_ANALYZER_HOOK,
          HIVE_QUERY_LIFETIME_HOOKS, HIVE_DRIVER_RUN_HOOKS, HIVE_SERVER2_SESSION_HOOK };
  private static final String OPTIONS_AS_LIST = getDefaultList() + "," + buildMapRList(IMMUTABLE_OPTIONS);
  private static final String FALLBACK_HIVE_AUTHORIZER_FACTORY =
      "org.apache.hadoop.hive.ql.security.authorization.plugin.fallback.FallbackHiveAuthorizerFactory";
  private static final String EMPTY = "";

  /**
   * Configures MetaStore authentication.
   *
   * @param doc xml document
   * @param authMethod authentication method
   * @throws TransformerException
   * @throws IOException
   * @throws SAXException
   * @throws ParserConfigurationException
   */

  static void setMetaStoreUseThriftSasl(Document doc, AuthMethod authMethod) {
    switch (authMethod) {
    case CUSTOM:
      return;
    case MAPRSASL:
    case KERBEROS:
      LOG.info(String.format("Configuring Hive for %s", authMethod));
      set(doc, METASTORE_USE_THRIFT_SASL, TRUE);
      break;
    case NONE:
      LOG.info("Configuring Hive for no security");
      set(doc, METASTORE_USE_THRIFT_SASL, FALSE);
      break;
    default:
      return;
    }
  }

  /**
   * Configures MetaStore authentication.
   *
   * @param pathToHiveSite hive-site location
   * @param authMethod authentication method
   * @throws TransformerException
   * @throws IOException
   * @throws SAXException
   * @throws ParserConfigurationException
   */

  static void setMetaStoreUseThriftSasl(String pathToHiveSite, AuthMethod authMethod)
      throws TransformerException, IOException, SAXException, ParserConfigurationException {
    Document doc = readDocument(pathToHiveSite);
    LOG.info("Reading hive-site.xml from path : {}", pathToHiveSite);
    setMetaStoreUseThriftSasl(doc, authMethod);
    saveToFile(doc, pathToHiveSite);
  }

  /**
   * Configures hive.metastore.execute.setugi. When one need to set "true" value, this method
   * just removes property from hive-site.xml since it has default true value.
   *
   * @param doc xml document
   * @param authMethod true if Mapr Sasl security is enabled on the cluster
   * @throws TransformerException
   * @throws IOException
   * @throws SAXException
   * @throws ParserConfigurationException
   */

  static void setMetaStoreUgi(Document doc, AuthMethod authMethod) {
    switch (authMethod) {
    case CUSTOM:
      return;
    case MAPRSASL:
    case KERBEROS:
      LOG.info("Configuring metastore not to use UGI");
      set(doc, METASTORE_EXECUTE_SET_UGI, FALSE);
      break;
    case NONE:
      LOG.info("Configuring metastore to use UGI. Default is true, so removing property from "
          + "hive-site.xml to enable it");
      remove(doc, METASTORE_EXECUTE_SET_UGI);
      break;
    default:
      return;
    }
  }

  /**
   * Configures hive.metastore.execute.setugi. When one need to set "true" value, this method
   * just removes property from hive-site.xml since it has default true value.
   *
   * @param pathToHiveSite  hive-site location
   * @param authMethod true if Mapr Sasl security is enabled on the cluster
   * @throws TransformerException
   * @throws IOException
   * @throws SAXException
   * @throws ParserConfigurationException
   */

  static void setMetaStoreUgi(String pathToHiveSite, AuthMethod authMethod)
      throws TransformerException, IOException, SAXException, ParserConfigurationException {
    Document doc = readDocument(pathToHiveSite);
    setMetaStoreUgi(doc, authMethod);
    saveToFile(doc, pathToHiveSite);
  }

  /**
   * Configures hive.security.metastore.authorization.manager property to use StorageBasedAuthorizationProvider if secure
   * or remove this property if not secure.
   * @param doc xml document
   * @param authMethod true if Mapr Sasl security is enabled on the cluster
   * @throws TransformerException
   * @throws IOException
   * @throws SAXException
   * @throws ParserConfigurationException
   */
  static void setMetaStoreAuthManager(Document doc, AuthMethod authMethod) {
    switch (authMethod) {
    case CUSTOM:
      return;
    case MAPRSASL:
    case KERBEROS:
      LOG.info("Configuring metastore authorization manager to StorageBasedAuthorizationProvider");
      set(doc, HIVE_METASTORE_AUTHORIZATION_MANAGER, METASTORE_SECURE_AUTH_MANAGER);
      break;
    case NONE:
      LOG.info("Configuring metastore authorization manager to default. Removing property from hive-site.xml");
      remove(doc, HIVE_METASTORE_AUTHORIZATION_MANAGER);
      break;
    default:
      return;
    }
  }

  /**
   * Configures hive.security.metastore.authorization.manager property to use StorageBasedAuthorizationProvider if secure
   * or remove this property if not secure.
   * @param pathToHiveSite hive-site location
   * @param authMethod true if Mapr Sasl security is enabled on the cluster
   * @throws TransformerException
   * @throws IOException
   * @throws SAXException
   * @throws ParserConfigurationException
   */
  static void setMetaStoreAuthManager(String pathToHiveSite, AuthMethod authMethod)
      throws TransformerException, IOException, SAXException, ParserConfigurationException {
    Document doc = readDocument(pathToHiveSite);
    LOG.info("Reading hive-site.xml from path : {}", pathToHiveSite);
    setMetaStoreAuthManager(doc, authMethod);
    saveToFile(doc, pathToHiveSite);
  }

  /**
   * Configures PAM and SSL encryption for HiveServer2 web UI.
   *
   * @param doc xml document
   * @param authMethod true if Mapr Sasl security is enabled on the cluster
   * @throws TransformerException
   * @throws IOException
   * @throws SAXException
   * @throws ParserConfigurationException
   */

  static void setHs2WebUiPamSsl(Document doc, AuthMethod authMethod) {
    switch (authMethod) {
    case CUSTOM:
      return;
    case MAPRSASL:
    case KERBEROS:
      LOG.info("Configuring Hive for HiveServer2 web UI PAM authentication and SSL encryption");
      set(doc, HIVE_SERVER2_WEBUI_USE_PAM, TRUE);
      set(doc, HIVE_SERVER2_WEBUI_USE_SSL, TRUE);
      break;
    case NONE:
      LOG.info("Disabling PAM authentication and SSL encryption for HiveServer2 web UI");
      remove(doc, HIVE_SERVER2_WEBUI_USE_PAM);
      remove(doc, HIVE_SERVER2_WEBUI_USE_SSL);
      remove(doc, HIVE_SERVER2_WEBUI_SSL_KEYSTORE_PATH);
      break;
    default:
      return;
    }
  }

  /**
   * Configures PAM and SSL encryption for HiveServer2 web UI.
   *
   * @param pathToHiveSite hive-site location
   * @param authMethod true if Mapr Sasl security is enabled on the cluster
   * @throws TransformerException
   * @throws IOException
   * @throws SAXException
   * @throws ParserConfigurationException
   */

  static void setHs2WebUiPamSsl(String pathToHiveSite, AuthMethod authMethod)
      throws TransformerException, IOException, SAXException, ParserConfigurationException {
    Document doc = readDocument(pathToHiveSite);
    LOG.info("Reading hive-site.xml from path : {}", pathToHiveSite);
    setHs2WebUiPamSsl(doc, authMethod);
    saveToFile(doc, pathToHiveSite);
  }

  /**
   * Configures Ssl encryption for webHCat server.
   *
   * @param doc xml document
   * @param authMethod true if Mapr Sasl security is enabled on the cluster
   * @throws TransformerException
   * @throws IOException
   * @throws SAXException
   * @throws ParserConfigurationException
   */

  static void setWebHCatSsl(Document doc, AuthMethod authMethod) {
    switch (authMethod) {
    case CUSTOM:
      return;
    case MAPRSASL:
    case KERBEROS:
      LOG.info("Configuring webHCat for SSL encryption");
      set(doc, AppConfig.USE_SSL, TRUE);
      break;
    case NONE:
      LOG.info("Removing SSL encryption for webHCat");
      remove(doc, AppConfig.USE_SSL);
      break;
    default:
      return;
    }
  }

  /**
   * Configures Ssl encryption for webHCat server.
   *
   * @param pathToWebHCatSite webhcat-site.xml location
   * @param authMethod true if Mapr Sasl security is enabled on the cluster
   * @throws TransformerException
   * @throws IOException
   * @throws SAXException
   * @throws ParserConfigurationException
   */

  static void setWebHCatSsl(String pathToWebHCatSite, AuthMethod authMethod)
      throws TransformerException, IOException, SAXException, ParserConfigurationException {
    Document doc = readDocument(pathToWebHCatSite);
    LOG.info("Reading webhcat-site.xml from path : {}", pathToWebHCatSite);
    switch (authMethod) {
    case CUSTOM:
      return;
    case MAPRSASL:
    case KERBEROS:
      LOG.info("Configuring webHCat for SSL encryption");
      set(doc, AppConfig.USE_SSL, TRUE);
      break;
    case NONE:
      LOG.info("Removing SSL encryption for webHCat");
      remove(doc, AppConfig.USE_SSL);
      break;
    default:
      return;
    }
    saveToFile(doc, pathToWebHCatSite);
  }

  /**
   * Configures security headers for webHCat server.
   *
   * @param pathToWebHCatSite webhcat-site.xml location
   * @param authMethod true if Mapr Sasl security is enabled on the cluster
   * @param headers xml file containing headers
   * @throws TransformerException
   * @throws IOException
   * @throws SAXException
   * @throws ParserConfigurationException
   */

  static void setWebHCatHeaders(String pathToWebHCatSite, AuthMethod authMethod, String headers)
      throws TransformerException, IOException, SAXException, ParserConfigurationException {
    Document doc = readDocument(pathToWebHCatSite);
    LOG.info("Reading webhcat-site.xml from path : {}", pathToWebHCatSite);
    setWebHCatHeaders(doc, authMethod, headers);
    saveToFile(doc, pathToWebHCatSite);
  }


  /**
   * Configures security headers for webHCat server.
   *
   * @param doc webhcat-site.xml location
   * @param authMethod true if Mapr Sasl security is enabled on the cluster
   * @param headers xml file containing headers
   * @throws TransformerException
   * @throws IOException
   * @throws SAXException
   * @throws ParserConfigurationException
   */

  static void setWebHCatHeaders(Document doc, AuthMethod authMethod, String headers){
    switch (authMethod) {
    case CUSTOM:
      return;
    case MAPRSASL:
    case KERBEROS:
      LOG.info("Configuring webHCat security headers");
      set(doc, AppConfig.HEADERS_FILE, headers);
      break;
    case NONE:
      LOG.info("Removing webHCat security headers");
      remove(doc, AppConfig.HEADERS_FILE);
      break;
    default:
      return;
    }
  }


  /**
   * Configures Ssl encryption for HiveServer2.
   *
   * @param doc xml document
   * @param authMethod true if Mapr Sasl security is enabled on the cluster
   * @throws TransformerException
   * @throws IOException
   * @throws SAXException
   * @throws ParserConfigurationException
   */
  static void setHs2Ssl(Document doc, AuthMethod authMethod) {
    switch (authMethod) {
    case CUSTOM:
      return;
    case MAPRSASL:
    case KERBEROS:
      LOG.info("Configuring HS2 for  SSL encryption");
      set(doc, ConfVars.HIVE_SERVER2_USE_SSL, TRUE);
      break;
    case NONE:
      remove(doc, ConfVars.HIVE_SERVER2_USE_SSL);
      break;
    default:
      return;
    }
  }

  /**
   * Configures security headers for HS2 web UI server.
   *
   * @param pathToHiveSite hive-site.xml location
   * @param authMethod true if Mapr Sasl security is enabled on the cluster
   * @param headers xml file containing headers
   * @throws TransformerException
   * @throws IOException
   * @throws SAXException
   * @throws ParserConfigurationException
   */

  static void setWebUiHeaders(String pathToHiveSite, AuthMethod authMethod, String headers)
      throws TransformerException, IOException, SAXException, ParserConfigurationException {
    Document doc = readDocument(pathToHiveSite);
    LOG.info("Reading hive-site.xml from path : {}", pathToHiveSite);
    setWebUiHeaders(doc, authMethod, headers);
    saveToFile(doc, pathToHiveSite);
  }

  /**
   * Configures security headers for HS2 web UI server.
   *
   * @param doc hive-site.xml location
   * @param authMethod true if Mapr Sasl security is enabled on the cluster
   * @param headers xml file containing headers
   * @throws TransformerException
   * @throws IOException
   * @throws SAXException
   * @throws ParserConfigurationException
   */

  static void setWebUiHeaders(Document doc, AuthMethod authMethod, String headers) {
    switch (authMethod) {
    case CUSTOM:
      return;
    case MAPRSASL:
    case KERBEROS:
      LOG.info("Configuring HS2 Web UI security headers");
      set(doc, ConfVars.HIVE_SERVER2_WEBUI_JETTY_RESPONSE_HEADERS_FILE, headers);
      break;
    case NONE:
      LOG.info("Removing HS2 Web UI security headers");
      remove(doc, ConfVars.HIVE_SERVER2_WEBUI_JETTY_RESPONSE_HEADERS_FILE);
      break;
    default:
      return;
    }
  }


  /**
   * Configures Ssl encryption for Hive Metastore.
   *
   * @param doc xml document
   * @param authMethod true if Mapr Sasl security is enabled on the cluster
   * @throws TransformerException
   * @throws IOException
   * @throws SAXException
   * @throws ParserConfigurationException
   */
  static void setHMetaSsl(Document doc, AuthMethod authMethod) {
    switch (authMethod) {
    case CUSTOM:
      return;
    case MAPRSASL:
    case KERBEROS:
      LOG.info("Configuring Hive Metastore for  SSL encryption");
      set(doc, ConfVars.HIVE_METASTORE_USE_SSL, TRUE);
      break;
    case NONE:
      remove(doc, ConfVars.HIVE_METASTORE_USE_SSL);
      break;
    default:
      return;
    }
  }

  /**
   * Configures Ssl encryption for HiveServer2.
   *
   * @param pathToHiveSite hive-site.xml location
   * @param authMethod true if Mapr Sasl security is enabled on the cluster
   * @throws TransformerException
   * @throws IOException
   * @throws SAXException
   * @throws ParserConfigurationException
   */
  static void setHs2Ssl(String pathToHiveSite, AuthMethod authMethod)
      throws TransformerException, IOException, SAXException, ParserConfigurationException {
    Document doc = readDocument(pathToHiveSite);
    setHs2Ssl(doc, authMethod);
    saveToFile(doc, pathToHiveSite);
  }

  /**
   * Configures Ssl encryption for Hive Metastore.
   *
   * @param pathToHiveSite hive-site.xml location
   * @param authMethod true if Mapr Sasl security is enabled on the cluster
   * @throws TransformerException
   * @throws IOException
   * @throws SAXException
   * @throws ParserConfigurationException
   */
  static void setHMetaSsl(String pathToHiveSite, AuthMethod authMethod)
      throws TransformerException, IOException, SAXException, ParserConfigurationException {
    Document doc = readDocument(pathToHiveSite);
    setHMetaSsl(doc, authMethod);
    saveToFile(doc, pathToHiveSite);
  }

  /**
   * Configures hive.users.in.admin role for secure cluster. If cluster is not secure the property will be removed.
   * @param pathToHiveSite hive-site location
   * @param adminUser admin user to add
   * @param authMethod true if MapR Sasl security is enabled on the cluster
   * @throws SAXException
   * @throws TransformerException
   * @throws ParserConfigurationException
   * @throws IOException
   */
  static void setAdminUser(String pathToHiveSite, String adminUser, AuthMethod authMethod)
      throws SAXException, TransformerException, ParserConfigurationException, IOException {
    Document doc = readDocument(pathToHiveSite);
    setAdminUser(doc, adminUser, authMethod);
    saveToFile(doc, pathToHiveSite);
  }

  /**
   * Configures hive.users.in.admin role for secure cluster. If cluster is not secure the property will be removed.
   * @param doc xml document
   * @param adminUser admin user to add
   * @param authMethod true if MapR Sasl security is enabled on the cluster
   * @throws SAXException
   * @throws TransformerException
   * @throws ParserConfigurationException
   * @throws IOException
   */
  static void setAdminUser(Document doc, String adminUser, AuthMethod authMethod) {
    switch (authMethod) {
    case CUSTOM:
      return;
    case MAPRSASL:
    case KERBEROS:
      appendProperty(doc, USERS_IN_ADMIN_ROLE.varname, adminUser);
      break;
    case NONE:
      delProperty(doc, USERS_IN_ADMIN_ROLE.varname);
      break;
    }
  }

  /**
   * Configures Ssl encryption for HiveServer2.
   *
   * @param doc xml document
   * @param authMethod true if Mapr Sasl security is enabled on the cluster
   * @throws TransformerException
   * @throws IOException
   * @throws SAXException
   * @throws ParserConfigurationException
   */

  static void setEncryption(Document doc, AuthMethod authMethod) {
    switch (authMethod) {
    case CUSTOM:
      return;
    case MAPRSASL:
    case KERBEROS:
      LOG.info("Configuring auth-conf by default");
      set(doc, HIVE_SERVER2_THRIFT_SASL_QOP, AUTH_CONF);
      break;
    case NONE:
      LOG.info("Removing auth-conf");
      remove(doc, HIVE_SERVER2_THRIFT_SASL_QOP);
      break;
    default:
      return;
    }
  }

  /**
   * Configures Ssl encryption for HiveServer2.
   *
   * @param pathToHiveSite hive-site location
   * @param authMethod true if Mapr Sasl security is enabled on the cluster
   * @throws TransformerException
   * @throws IOException
   * @throws SAXException
   * @throws ParserConfigurationException
   */

  static void setEncryption(String pathToHiveSite, AuthMethod authMethod)
      throws TransformerException, IOException, SAXException, ParserConfigurationException {
    Document doc = readDocument(pathToHiveSite);
    LOG.info("Reading hive-site.xml from path : {}", pathToHiveSite);
    setEncryption(doc, authMethod);
    saveToFile(doc, pathToHiveSite);
  }

  /**
   * Fixes CVE-2018-11777. Configures Hive for usage of FallbackHiveAuthorizerFactory.
   *
   * @param pathToHiveSite hive-site location
   * @param authMethod true if Mapr Sasl security is enabled on the cluster
   * @throws IOException
   * @throws SAXException
   * @throws ParserConfigurationException
   * @throws TransformerException
   */
  static void setFallbackAuthorizer(String pathToHiveSite, AuthMethod authMethod)
      throws IOException, SAXException, ParserConfigurationException, TransformerException {
    Document doc = readDocument(pathToHiveSite);
    LOG.info("Reading hive-site.xml from path : {}", pathToHiveSite);
    setFallbackAuthorizer(doc, authMethod);
    saveToFile(doc, pathToHiveSite);
  }

  /**
   * Fixes CVE-2018-11777. Configures Hive for usage of FallbackHiveAuthorizerFactory.
   *
   * @param doc hive-site document
   * @param authMethod true if Mapr Sasl security is enabled on the cluster
   * @throws IOException
   * @throws SAXException
   * @throws ParserConfigurationException
   * @throws TransformerException
   */
  static void setFallbackAuthorizer(Document doc, AuthMethod authMethod) throws TransformerException {
    switch (authMethod) {
    case CUSTOM:
      return;
    case MAPRSASL:
    case KERBEROS:
      set(doc, HIVE_AUTHORIZATION_ENABLED, TRUE);
      set(doc, HIVE_AUTHORIZATION_MANAGER, FALLBACK_HIVE_AUTHORIZER_FACTORY);
      break;
    case NONE:
      remove(doc, HIVE_AUTHORIZATION_ENABLED);
      remove(doc, HIVE_AUTHORIZATION_MANAGER);
      break;
    default:
      return;
    }
  }

  /**
   * Enables HiveServer2 HA.
   *
   * @param doc xml document
   * @param zookeeperQuorum comma separated list of nodes of Zookeeper quorum
   * @throws ParserConfigurationException
   * @throws IOException
   * @throws SAXException
   * @throws TransformerException
   */

  static void enableHs2Ha(Document doc, String zookeeperQuorum) {
    LOG.info("Enabling Zookeeper HA");
    set(doc, HIVE_SERVER2_SUPPORT_DYNAMIC_SERVICE_DISCOVERY, TRUE);
    set(doc, HIVE_ZOOKEEPER_QUORUM, zookeeperQuorum);
  }

  /**
   * Enables HiveServer2 HA.
   *
   * @param pathToHiveSite hive-site location
   * @param zookeeperQuorum comma separated list of nodes of Zookeeper quorum
   * @throws ParserConfigurationException
   * @throws IOException
   * @throws SAXException
   * @throws TransformerException
   */

  static void enableHs2Ha(String pathToHiveSite, String zookeeperQuorum)
      throws ParserConfigurationException, IOException, SAXException, TransformerException {
    Document doc = readDocument(pathToHiveSite);
    LOG.info("Reading hive-site.xml from path : {}", pathToHiveSite);
    enableHs2Ha(doc, zookeeperQuorum);
    saveToFile(doc, pathToHiveSite);
  }

  /**
   * Checks if property presents in file.
   *
   * @param pathToHiveSite hive-site location
   * @param property property name
   * @return true if property is in hive-site.xml
   * @throws IOException
   * @throws SAXException
   * @throws ParserConfigurationException
   */

  static boolean exists(String pathToHiveSite, String property)
      throws IOException, SAXException, ParserConfigurationException {
    Document doc = readDocument(pathToHiveSite);
    return propertyExists(doc, property);
  }

  /**
   * Reads all properties from file.
   *
   * @param pathToHiveSite hive-site location
   * @return Map that represents properties in hive-site.xml
   * @throws IOException
   * @throws SAXException
   * @throws ParserConfigurationException
   */

  public static Map<String, String> readAllProperties(String pathToHiveSite)
      throws IOException, SAXException, ParserConfigurationException {
    Document doc = readDocument(pathToHiveSite);
    return readAllProperties(doc);
  }

  /**
   *  Removes property from xml file.
   *  IN-827
   * @param pathToHiveSite hive-site location
   * @throws IOException
   * @throws SAXException
   * @throws ParserConfigurationException
   */
  static void delProperty(String pathToHiveSite, String property)
      throws IOException, SAXException, ParserConfigurationException, TransformerException {
    Document doc = readDocument(pathToHiveSite);
    LOG.info("Reading xml from path : {}", pathToHiveSite);
    LOG.info("Removing {} property from {}", property, pathToHiveSite);
    delProperty(doc, property);
    saveToFile(doc, pathToHiveSite);
  }

  /**
   *  Removes property from xml file.
   *  IN-827
   * @param  doc xml document
   * @throws IOException
   * @throws SAXException
   * @throws ParserConfigurationException
   */
  static void delProperty(Document doc, String property) {
    remove(doc, property);
  }

  /**
   * Configures restricted list of options that are immutable at runtime.
   *
   * @param pathToHiveSite hive-site location
   * @param authMethod true if MapR Sasl security is enabled on the cluster
   * @throws IOException
   * @throws SAXException
   * @throws ParserConfigurationException
   */

  static void setRestrictedList(String pathToHiveSite, AuthMethod authMethod)
      throws IOException, SAXException, ParserConfigurationException, TransformerException {
    Document doc = readDocument(pathToHiveSite);
    LOG.info("Reading hive-site.xml from path : {}", pathToHiveSite);
    setRestrictedList(doc, authMethod);
    saveToFile(doc, pathToHiveSite);
  }

  /**
   * Configures restricted list of options that are immutable at runtime.
   *
   * @param doc xml document
   * @param authMethod true if MapR Sasl security is enabled on the cluster
   * @throws IOException
   * @throws SAXException
   * @throws ParserConfigurationException
   */

  static void setRestrictedList(Document doc, AuthMethod authMethod) {
    switch (authMethod) {
    case CUSTOM:
      return;
    case MAPRSASL:
    case KERBEROS:
      LOG.info("Enabling additional options for restricted list : {}", OPTIONS_AS_LIST);
      set(doc, HIVE_CONF_RESTRICTED_LIST, OPTIONS_AS_LIST);
      break;
    case NONE:
      LOG.info("Disabling additional options for restricted list");
      remove(doc, HIVE_CONF_RESTRICTED_LIST);
      break;
    default:
    }
  }

  /**
   * Wrapper for method configureHs2Metrics()
   *
   * @param pathToHiveSite hive-site location
   * @param isMetricsEnabled true if metrics should be enabled
   * @throws IOException
   * @throws SAXException
   * @throws ParserConfigurationException
   * @throws TransformerException
   */
  static void configureHs2Metrics(String pathToHiveSite, boolean isMetricsEnabled)
      throws IOException, SAXException, ParserConfigurationException, TransformerException {
    Document doc = readDocument(pathToHiveSite);
    LOG.info("Reading hive-site.xml from path : {}", pathToHiveSite);
    configureHs2Metrics(doc, isMetricsEnabled);
    saveToFile(doc, pathToHiveSite);
  }

  /**
   * Configures HiveServer2 for connecting metrics. JVM parameters should be added in HIVE_HOME/bin/hive file.
   * Security does not play role here.
   *
   * @param doc xml document
   * @param isMetricsEnabled true if metrics should be enabled
   */
  static void configureHs2Metrics(Document doc, boolean isMetricsEnabled) {
    if (isMetricsEnabled) {
      LOG.info("Enabling metrics for HiveServer2");
      set(doc, HIVE_SERVER2_METRICS_ENABLED, TRUE);
    } else {
      LOG.info("Disabling metrics for HiveServer2");
      set(doc, HIVE_SERVER2_METRICS_ENABLED, FALSE);
    }
  }

  /**
   * Configures metrics reported type.
   *
   * @param pathToHiveSite hive-site location
   * @param reporterType reporter type for metric class
   */
  static void configureMetricsReporterType(String pathToHiveSite, boolean isReporterEnabled, String reporterType)
      throws IOException, SAXException, ParserConfigurationException, TransformerException {
    Document doc = readDocument(pathToHiveSite);
    LOG.info("Reading hive-site.xml from path : {}", pathToHiveSite);
    configureMetricsReporterType(doc, isReporterEnabled, reporterType);
    saveToFile(doc, pathToHiveSite);
  }

  /**
   * Configures metrics reported type.
   *
   * @param doc xml document
   * @param reporterType reporter type for metric class
   */
  static void configureMetricsReporterType(Document doc, boolean isReporterEnabled, String reporterType) {
    if (isReporterEnabled) {
      LOG.info("Configuring metrics reporter : {}", reporterType);
      set(doc, HIVE_METRICS_REPORTER, reporterType);
    } else {
      LOG.info("Removing metrics reporter");
      remove(doc, HIVE_METRICS_REPORTER);
    }
  }

  /**
   * Configures HiveServer2 metrics file location.
   *
   * @param pathToHiveSite hive-site location
   * @param fileLocation the location of local JSON metrics file
   */
  static void configureHiveServer2MetricsFileLocation(String pathToHiveSite, boolean isReporterEnabled,
      String fileLocation) throws SAXException, TransformerException, ParserConfigurationException, IOException {
    configureMetricsFileLocation(pathToHiveSite, isReporterEnabled, HIVE_SERVER2_METRICS_JSON_FILE_LOCATION,
        fileLocation);
  }

  /**
   * Configures Hive Metastore metrics file location.
   *
   * @param pathToHiveSite hive-site location
   * @param fileLocation the location of local JSON metrics file
   */
  static void configureHiveMetastoreMetricsFileLocation(String pathToHiveSite, boolean isReporterEnabled,
      String fileLocation) throws SAXException, TransformerException, ParserConfigurationException, IOException {
    configureMetricsFileLocation(pathToHiveSite, isReporterEnabled, HIVE_METASTORE_METRICS_JSON_FILE_LOCATION,
        fileLocation);
  }

  /**
   * Configures metrics file location.
   *
   * @param pathToHiveSite hive-site location
   * @param servicePropertyName HiveConf property for specific
   * @param fileLocation the location of local JSON metrics file
   */
  static void configureMetricsFileLocation(String pathToHiveSite, boolean isReporterEnabled,
      ConfVars servicePropertyName, String fileLocation)
      throws IOException, SAXException, ParserConfigurationException, TransformerException {
    Document doc = readDocument(pathToHiveSite);
    LOG.info("Reading hive-site.xml from path : {}", pathToHiveSite);
    configureMetricsFileLocation(doc, isReporterEnabled, servicePropertyName, fileLocation);
    saveToFile(doc, pathToHiveSite);
  }

  /**
   * Configures metrics file location.
   *
   * @param doc xml document
   * @param servicePropertyName HiveConf property for specific
   * @param fileLocation the location of local JSON metrics file
   */
  static void configureMetricsFileLocation(Document doc, boolean isReporterEnabled, ConfVars servicePropertyName,
      String fileLocation) {
    if (isReporterEnabled) {
      LOG.info("Configuring metrics file location : {}", fileLocation);
      set(doc, servicePropertyName, fileLocation);
    } else {
      LOG.info("Removing metrics file location");
      remove(doc, servicePropertyName);
    }
  }

  /**
   * Wrapper for method configureMetastoreMetrics
   *
   * @param pathToHiveSite hive-site location
   * @param isMetricsEnabled true if metrics should be enabled
   * @throws IOException
   * @throws SAXException
   * @throws ParserConfigurationException
   * @throws TransformerException
   */
  static void configureMetastoreMetrics(String pathToHiveSite, boolean isMetricsEnabled)
      throws IOException, SAXException, ParserConfigurationException, TransformerException {
    Document doc = readDocument(pathToHiveSite);
    LOG.info("Reading hive-site.xml from path : {}", pathToHiveSite);
    configureMetastoreMetrics(doc, isMetricsEnabled);
    saveToFile(doc, pathToHiveSite);
  }

  /**
   * Configures Metastore for connecting metrics. JVM parameters should be added in HIVE_HOME/bin/hive file.
   * Security does not play role here.
   *
   * @param doc xml document
   * @param isMetricsEnabled true if metrics should be enabled
   */
  static void configureMetastoreMetrics(Document doc, boolean isMetricsEnabled) {
    if (isMetricsEnabled) {
      LOG.info("Enabling metrics for Metastore");
      set(doc, METASTORE_METRICS, TRUE);
    } else {
      LOG.info("Disabling metrics for Metastore");
      set(doc, METASTORE_METRICS, FALSE);
    }
  }

  /**
   * Adds property to xml file. If property already exists, it replaces its value with new one.
   * Uses following template
   * <property>
   *   <name>property.name</name>
   *   <value>property.value</value>
   * </property>
   *
   * @param doc xml document
   * @param property name of the property
   * @param value value of the property
   * @throws IOException
   * @throws SAXException
   * @throws ParserConfigurationException
   * @throws TransformerException
   */

  static void addProperty(Document doc, String property, String value) {
    if (propertyExists(doc, property)) {
      setPropertyInternal(doc, property, value);
    } else {
      addPropertyInternal(doc, property, value);
    }
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
   * Adds property value joined by coma separator to xml file. If property does not exist, it adds a new one.
   *
   * @param pathToHiveSite hive-site location
   * @param property name of the property
   * @param value value of the property to append
   * @throws IOException
   * @throws SAXException
   * @throws ParserConfigurationException
   * @throws TransformerException
   */
  static void appendProperty(String pathToHiveSite, String property, String value)
      throws IOException, SAXException, ParserConfigurationException, TransformerException {
    Document doc = readDocument(pathToHiveSite);
    appendProperty(doc, property, value);
    saveToFile(doc, pathToHiveSite);
  }

  /**
   *  Return property value from xml file.
   *
   * @param pathToHiveSite hive-site location
   * @param property name of the property
   * @return
   * @throws IOException
   * @throws SAXException
   * @throws ParserConfigurationException
   */

  static String getProperty(String pathToHiveSite, String property)
      throws IOException, SAXException, ParserConfigurationException {
    Document doc = readDocument(pathToHiveSite);
    return getProperty(doc, property);
  }

  /**
   * Adds hive.metastore.uris=localhost in hive-site.xml.
   *
   * @param pathToHiveSite hive-site location
   * @throws ParserConfigurationException
   * @throws IOException
   * @throws SAXException
   * @throws TransformerException
   */

  static void initMetaStoreUri(String pathToHiveSite)
      throws ParserConfigurationException, IOException, SAXException, TransformerException {
    Document doc = readDocument(pathToHiveSite);
    LOG.info("Reading hive-site.xml from path : {}", pathToHiveSite);
    set(doc, METASTOREURIS, THRIFT_LOCAL_HOST);
    saveToFile(doc, pathToHiveSite);
  }

  /**
   * Sets javax.jdo.option.ConnectionURL.
   *
   * @param doc xml document
   * @param connectionUrl value to set for ConnectionURL
   * @throws ParserConfigurationException
   * @throws IOException
   * @throws SAXException
   * @throws TransformerException
   */

  static void setConnectionUrl(Document doc, String connectionUrl) {
    LOG.info("Configuring connection URL : {}");
    set(doc, METASTORECONNECTURLKEY, connectionUrl);
  }

  /**
   * Sets javax.jdo.option.ConnectionURL.
   *
   * @param pathToHiveSite hive-site location
   * @param connectionUrl value to set for ConnectionURL
   * @throws ParserConfigurationException
   * @throws IOException
   * @throws SAXException
   * @throws TransformerException
   */

  static void setConnectionUrl(String pathToHiveSite, String connectionUrl)
      throws ParserConfigurationException, IOException, SAXException, TransformerException {
    Document doc = readDocument(pathToHiveSite);
    LOG.info("Reading hive-site.xml from path : {}", pathToHiveSite);
    setConnectionUrl(doc, connectionUrl);
    saveToFile(doc, pathToHiveSite);
  }

  private static Document readDocument(String pathToHiveSite)
      throws ParserConfigurationException, IOException, SAXException {
    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
    return docBuilder.parse(pathToHiveSite);
  }

  /**
   * Append value to existing joined by coma(','). If new value is already present, nothing will be changed.
   * If property does not exist, it will be added as a new one.
   * @param doc xml document
   * @param property property name
   * @param value value to append
   */
  @VisibleForTesting
  static void appendProperty(Document doc, String property, String value) {
    if (propertyExists(doc, property)) {
      LOG.info("Property {} exists in xml file. Append value: {}", property, value);
      String oldPropertyValue = getProperty(doc, property);
      if (!new HashSet<>(Arrays.asList(oldPropertyValue.split(","))).contains(value)) {
        if (!oldPropertyValue.isEmpty()) {
          value = Joiner.on(",").join(oldPropertyValue, value);
        }
        setPropertyInternal(doc, property, value);
      }
    } else {
      LOG.info("Property {} does not exist in xml file", property);
      addPropertyInternal(doc, property, value);
    }
  }

  private static void set(Document doc, String property, String value) {
    if (propertyExists(doc, property)) {
      LOG.info("Property {} exists in xml file", property);
      setPropertyInternal(doc, property, value);
    } else {
      LOG.info("Property {} does not exist in xml file", property);
      addPropertyInternal(doc, property, value);
    }
  }

  private static void set(Document doc, ConfVars confVars, String value) {
    set(doc, confVars.varname, value);
  }

  private static void remove(Document doc, ConfVars confVars) {
    remove(doc, confVars.varname);
  }

  private static void remove(Document doc, String property) {
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
    addPropertyInternal(doc, confVars.varname, value);
  }

  private static void addPropertyInternal(Document doc, String property, String value) {
    LOG.info("Adding property to hive-site.xml: {} = {}", property, isPassword(property) ? hidePassword(value) : value);
    Element element = doc.createElement(PROPERTY);
    addName(doc, element, property);
    addValue(doc, element, value);
    getConfigurationNode(doc).appendChild(element);
  }

  @VisibleForTesting
  static void removeProperty(Document doc, ConfVars confVars) {
    removeProperty(doc, confVars.varname);
  }

  private static void removeProperty(Document doc, String property) {
    LOG.info("Removing property from hive-site.xml: {}", property);
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
          //Remove the new line text node that stands after node we need to remove.
          //Without this step removing will produce empty line.
          if (node.getNextSibling() != null && node.getNextSibling().getNodeType() == Node.TEXT_NODE && node
              .getNextSibling().getNodeValue().trim().isEmpty()) {
            configuration.removeChild(node.getNextSibling());
          }
          configuration.removeChild(node);
          return;
        }
      }
    }
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

  private static Map<String, String> readAllProperties(Document doc) {
    LOG.info("Reading all properties from hive-site.xml");
    Map<String, String> result = new HashMap<>();
    Node configuration = getConfigurationNode(doc);
    NodeList properties = configuration.getChildNodes();
    int length = properties.getLength();
    for (int i = 0; i <= length - 1; i++) {
      Node node = properties.item(i);
      NodeList nameValueDesc = node.getChildNodes();
      int childLength = nameValueDesc.getLength();
      String name = "";
      String value = "";
      for (int j = 0; j <= childLength - 1; j++) {
        Node childNode = nameValueDesc.item(j);
        if (NAME.equals(childNode.getNodeName())) {
          name = childNode.getTextContent();
        }
        if (VALUE.equals(childNode.getNodeName())) {
          value = childNode.getTextContent();
        }
      }
      if (!name.isEmpty()) {
        result.put(name, value);
      }
    }
    return result;
  }

  private static boolean isPassword(String property) {
    String propertyLowCase = property.toLowerCase();
    return propertyLowCase.contains("password") || propertyLowCase.contains("passwd");
  }

  private static String hidePassword(String password) {
    return StringUtils.repeat("*", password.length());
  }

  @VisibleForTesting
  static void setProperty(Document doc, ConfVars confVars, String value) {
    set(doc, confVars.varname, value);
  }

  private static void setPropertyInternal(Document doc, String property, String value) {
    LOG.info("Setting value to existing property in xml file: {} = {}", property,
        isPassword(property) ? hidePassword(value) : value);
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

  private static String buildMapRList(HiveConf.ConfVars[] options) {
    StringBuilder sb = new StringBuilder();
    boolean first = true;
    for (HiveConf.ConfVars option : options) {
      if (first) {
        sb.append(option.varname);
        first = false;
      } else {
        sb.append(",");
        sb.append(option.varname);
      }
    }
    return sb.toString();
  }

  private static String getDefaultList() {
    return ConfVars.HIVE_CONF_RESTRICTED_LIST.getDefaultValue();
  }
}
