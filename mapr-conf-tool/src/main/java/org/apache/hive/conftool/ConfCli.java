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

import org.apache.hive.common.util.MapRSecurityUtil;
import org.apache.commons.io.FileUtils;
import org.apache.hadoop.hive.conf.HiveConf;
import org.apache.hive.beeline.HiveSchemaTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.GroupPrincipal;
import java.nio.file.attribute.UserPrincipal;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.apache.hadoop.hive.conf.HiveConf.ConfVars.METASTORECONNECTURLKEY;
import static org.apache.hive.common.util.MapRSecurityUtil.getMapRHome;
import static org.apache.hive.conftool.ConfCommonUtilities.changeGroup;
import static org.apache.hive.conftool.ConfCommonUtilities.changeOwner;
import static org.apache.hive.conftool.ConfCommonUtilities.exists;
import static org.apache.hive.conftool.ConfCommonUtilities.findGroupByName;
import static org.apache.hive.conftool.ConfCommonUtilities.findUserByName;
import static org.apache.hive.conftool.ConfCommonUtilities.normalize;
import static org.apache.hive.conftool.ConfTool.configureHiveMetastoreMetricsFileLocation;
import static org.apache.hive.conftool.ConfTool.configureHiveServer2MetricsFileLocation;
import static org.apache.hive.conftool.ConfTool.configureHs2Metrics;
import static org.apache.hive.conftool.ConfTool.configureMetastoreMetrics;
import static org.apache.hive.conftool.ConfTool.configureMetricsReporterType;
import static org.apache.hive.conftool.ConfTool.configureTokenAuth;
import static org.apache.hive.conftool.ConfTool.enableHs2Ha;
import static org.apache.hive.conftool.ConfTool.initMetaStoreUri;
import static org.apache.hive.conftool.ConfTool.propertyExists;
import static org.apache.hive.conftool.ConfTool.setAdminUser;
import static org.apache.hive.conftool.ConfTool.setConnectionUrl;
import static org.apache.hive.conftool.ConfTool.setEncryption;
import static org.apache.hive.conftool.ConfTool.setFallbackAuthorizer;
import static org.apache.hive.conftool.ConfTool.setHs2Ssl;
import static org.apache.hive.conftool.ConfTool.setHs2WebUiPamSsl;
import static org.apache.hive.conftool.ConfTool.setMetaStoreAuthManager;
import static org.apache.hive.conftool.ConfTool.setMetaStoreAuthPreEventListener;
import static org.apache.hive.conftool.ConfTool.setMetaStoreUgi;
import static org.apache.hive.conftool.ConfTool.setMetaStoreUseThriftSasl;
import static org.apache.hive.conftool.ConfTool.setRestrictedList;
import static org.apache.hive.conftool.ConfTool.setWebHCatHeaders;
import static org.apache.hive.conftool.ConfTool.setWebHCatSsl;
import static org.apache.hive.conftool.ConfTool.setWebUiHeaders;
import static org.apache.hive.conftool.ConfToolParseUtil.readDocument;
import static org.apache.hive.conftool.ConfToolParseUtil.saveToFile;
import static org.apache.hive.conftool.PropertyProcessor.processArgs;
import static org.apache.hive.conftool.PropertyProcessor.requiresArgProcessing;

/**
 * This class is used for configuring xml files (hive-site.xml and others).
 * Here we read xml files, change its contents and write changes to file. We created this
 * class to avoid multiple call of conftool script from bash shel, since
 * each time we perform that call, a new JVM is created and it takes time, which leads to performance degradation.
 * Here we process all actions that need JVM in a singe class, starting new JVM only once.
 */
public final class ConfCli {
  private ConfCli() {
  }

  private static final Logger LOG = LoggerFactory.getLogger(ConfCli.class.getName());

  private static final String MAPR_HOME = getMapRHome();
  private static final String MAPR_ROLES = findMapRRoles();
  private static final String HIVE_HOME = findHiveHome();
  private static final String HIVE_CONF = findHiveConf();
  private static final String HIVE_BIN = findHiveBin();

  /**
   * Enter point method for all configurations.
   *
   * @param args not used here
   */
  public static void main(String[] args)
      throws IOException, ParserConfigurationException, SAXException, TransformerException {
    if (requiresArgProcessing(args)) {
      processArgs(args);
    } else {
      configureRegularXmlFiles();
    }
  }

  /**
   * Configures hive-site.xml and webhcat-site.xml.
   *
   * Method assumes that hive-site.xml, webhcat-site.xml and headers.xml exist and located at their default
   * paths in HIVE-HOME. It also assumes that MAPR_HOME/conf/mapr-clusters.conf exists and contains
   * security configuration information.
   *
   * @throws ParserConfigurationException
   * @throws IOException
   * @throws SAXException
   * @throws TransformerException
   */
  private static void configureRegularXmlFiles()
      throws ParserConfigurationException, IOException, SAXException, TransformerException {
    String pathToHiveSite = findHiveSite();
    String pathToWebHCatSite = findWebHCatSite();
    String headers = findHeaders();
    AuthMethod authMethod = AuthMethod.parse(MapRSecurityUtil.getAuthMethod());
    String adminUser = MapRSecurityUtil.findAdminUser();
    Document hiveSite = readDocument(pathToHiveSite);
    Document webHCatSite = readDocument(pathToWebHCatSite);

    saveAuthMethodFlag(authMethod);

    if (securityHasToBeConfigured()) {
      configureSecurity(hiveSite, webHCatSite, authMethod, headers, adminUser);
    }

    if (isHiveServer2HA()) {
      configureHiveServer2HA(hiveSite);
    }
    initDerbySchema(hiveSite);
    configureHiveServer2MetricAndReport(hiveSite);
    configureMetastoreMetricAndReport(hiveSite);
    configureReporterType(hiveSite);

    saveToFile(hiveSite, pathToHiveSite);
    saveToFile(webHCatSite, pathToWebHCatSite);
  }

  /**
   * Reporter type for metric class org.apache.hadoop.hive.common.metrics.metrics2.CodahaleMetrics.
   * Comma separated list of JMX, CONSOLE, JSON_FILE, HADOOP2.
   *
   * For metric class org.apache.hadoop.hive.common.metrics.metrics2.CodahaleMetrics JSON_FILE reporter,
   * the location of local JSON metrics file.This file will get overwritten at every interval.
   *
   *
   * @param hiveSite parsed hive-site.xml
   */
  private static void configureReporterType(Document hiveSite) {
    if (isHiveNotConfiguredYet()) {
      LOG.info("Configuring report type");
      configureMetricsReporterType(hiveSite, true, "JSON_FILE,JMX");
    }
  }

  /**
   * Configures Metastore for collecting metrics in hive-site.xml.
   * Property com.sun.management.jmxremote is configured in $HIVE_BIN/hive
   *
   * @param hiveSite parsed hive-site.xml
   */
  private static void configureMetastoreMetricAndReport(Document hiveSite) {
    if (isHiveNotConfiguredYet()) {
      LOG.info("Configuring Metastore metric and report location");
      configureMetastoreMetrics(hiveSite, true);
      configureHiveMetastoreMetricsFileLocation(hiveSite, true, "/tmp/hivemetastore_report.json");
    }
  }

  /**
   * Configures HiveServer2 for collecting metrics in hive-site.xml.
   * Property com.sun.management.jmxremote is configured in $HIVE_BIN/hive
   *
   * @param hiveSite parsed hive-site.xml
   */
  private static void configureHiveServer2MetricAndReport(Document hiveSite) {
    if (isHiveNotConfiguredYet()) {
      LOG.info("Configuring HiveServer2 metric and report location");
      configureHs2Metrics(hiveSite, true);
      configureHiveServer2MetricsFileLocation(hiveSite, true, "/tmp/hiveserver2_report.json");
    }
  }

  /**
   * Enables HiveServer2 High Availability
   *
   * @param hiveSite parsed hive-site.xml
   */
  private static void configureHiveServer2HA(Document hiveSite) {
    int numHiveServer2 = findNumHiveServer2();
    String zookeeperQuorum = findZookeeperQuorum();
    LOG.info("Configuring HiveServer2 HA for zookeeper quorum {} and amount of HiveServers2 is {}", zookeeperQuorum,
        numHiveServer2);
    enableHs2Ha(hiveSite, zookeeperQuorum);
    setNumberOfHiveServer2inWardenFile(numHiveServer2);
  }

  private static void saveAuthMethodFlag(AuthMethod authMethod) throws FileNotFoundException {
    try (PrintWriter out = new PrintWriter(HIVE_CONF + File.separator + ".authMethod")) {
      out.println(authMethod.value());
    }
  }

  /**
   * Initialize Derby Db schema for mapr admin user.
   *
   * @param hiveSite parsed hive-site.xml
   * @throws IOException
   */
  private static void initDerbySchema(Document hiveSite) throws IOException {
    if (!isConnectionUrlConfigured(hiveSite) && isHiveNotConfiguredYet()) {
      LOG.info("Start processing derby DB schema");
      String derbyDefaultName = findDefaultDerbyName();
      if (exists(derbyDefaultName)) {
        LOG.info("Deleting {}", derbyDefaultName);
        FileUtils.deleteDirectory(new File(derbyDefaultName));
      }
      String connectionUrl = String.format("jdbc:derby:;databaseName=%s/metastore_db;create=true", HIVE_BIN);
      setConnectionUrl(hiveSite, connectionUrl);
      setAdminGroupTo(HIVE_BIN);
      setAdminOwnerTo(HIVE_BIN);
      runDerbySchemaTool(connectionUrl);
      if (hasMetastore() && !isMetastoreUrisConfigured(hiveSite)) {
        initMetaStoreUri(hiveSite);
      }
    }
  }

  /**
   * Execute schema tool for derby DB in a separate thread.
   */
  private static void runDerbySchemaTool(final String connectionUrl) {
    LOG.info("Starting schema tool for derby DB");
    ExecutorService executor = Executors.newSingleThreadExecutor();
    executor.submit(() -> {
      HiveConf hiveConf = new HiveConf(HiveSchemaTool.class);
      hiveConf.setVar(METASTORECONNECTURLKEY, connectionUrl);
      HiveSchemaTool.main(new String[] { "-dbType", "derby", "-initSchema" }, hiveConf, HIVE_HOME);
    });
  }


  /**
   * Check if metastore URIs are configured.
   *
   * @param hiveSite parsed hive-site.xml
   * @return true if metastore URIs are configured
   */
  private static boolean isMetastoreUrisConfigured(Document hiveSite) {
    return propertyExists(hiveSite, "hive.metastore.uris");
  }

  /**
   * Checks if Metastore is installed using roles files for that.
   *
   * @return true if Metastore is installed
   */
  private static boolean hasMetastore() {
    return exists(MAPR_ROLES + File.separator + "hivemetastore");
  }

  /**
   * Finds cluster admin and sets it as owner to certain path.
   * Works as chown in shell.
   *
   * @param path path to set up owner.
   * @throws IOException
   */
  private static void setAdminOwnerTo(String path) throws IOException {
    String adminUser = MapRSecurityUtil.findAdminUser();
    LOG.info("Setting owner {} for path {}", adminUser, path);
    UserPrincipal adminGroup = findUserByName(adminUser);
    changeOwner(path, adminGroup);
  }

  /**
   * Finds cluster admin group and sets it to certain path.
   * Works as chgr in shell.
   *
   * @param path path to set up group.
   * @throws IOException
   */
  private static void setAdminGroupTo(String path) throws IOException {
    String adminUser = MapRSecurityUtil.findAdminUser();
    LOG.info("Setting group {} for path {}", adminUser, path);
    GroupPrincipal adminGroup = findGroupByName(adminUser);
    changeGroup(path, adminGroup);
  }

  /**
   * Check if javax.jdo.option.ConnectionURL is configured or not.
   *
   * @param hiveSite parsed hive-site.xml
   * @return true if javax.jdo.option.ConnectionURL is configured
   */
  private static boolean isConnectionUrlConfigured(Document hiveSite) {
    return propertyExists(hiveSite, "javax.jdo.option.ConnectionURL");
  }

  /**
   * Gets default location for derby DB.
   *
   * @return path to derby DB location
   */
  private static String findDefaultDerbyName() {
    return HIVE_BIN + File.separator + "metastore_db";
  }

  /**
   * Checks whether there is a need to configure security.
   * We have to configure security if security type was changed, and it is not in custom format
   * or if hive was not configured yet.
   *
   * @return true if we need to configure security
   * @throws IOException
   */
  private static boolean securityHasToBeConfigured() throws IOException {
    return (isAuthMethodChanged() || isHiveNotConfiguredYet()) && !isCustomSecurity();
  }

  /**
   * Returns true if security flag was changed comparing current and previous run of configure.sh.
   * E.g. user switches off the security (security ON --> security OFF) or
   * user turns on security (security OFF --> security ON) then method returns true
   * and false otherwise. This method is used for triggering security related configuration
   *
   * @return true if authentication method changed
   * @throws IOException
   */
  private static boolean isAuthMethodChanged() throws IOException {
    String authMethodPath = HIVE_CONF + File.separator + ".authMethod";
    String authMethodPathBackup = HIVE_CONF + File.separator + ".authMethod.backup";
    if (exists(authMethodPath) && exists(authMethodPathBackup)) {
      String authMethodBackup = Files.readString(Path.of(authMethodPathBackup));
      String currentAuthMethod = Files.readString(Path.of(authMethodPath));
      return !authMethodBackup.equals(currentAuthMethod);
    }
    LOG.warn("No HIVE_HOME/conf/.authMethod or HIVE_HOME/conf/.authMethod.backup files");
    return false;
  }

  /**
   * Checks if Hive has been already configured.
   *
   * @return true if Hive is not configured
   * @throws IOException
   */
  private static boolean isHiveNotConfiguredYet() {
    return exists(findHiveConf() + File.separator + ".not_configured_yet");
  }

  /**
   * Returns true if security is custom.
   *
   * @return true if security is custom.
   */
  private static boolean isCustomSecurity() {
    return exists(findHiveConf() + File.separator + ".customSecure");
  }

  /**
   * Find Hive home folder.
   *
   * @return Hive home
   */
  private static String findHiveHome() {
    String hiveHome = System.getenv("HIVE_HOME");
    if (hiveHome == null) {
      hiveHome = System.getProperty("hive.home.dir");
      if (hiveHome == null) {
        String hiveVersion = null;
        try {
          hiveVersion = Files.readString(Path.of(MAPR_HOME + File.separator + "hive" + File.separator + "hiveversion"));
        } catch (IOException e) {
          throw new HiveHomeException("Can not find HIVE_HOME");
        }
        hiveHome = MAPR_HOME + File.separator + "hive" + File.separator + "hive-" + hiveVersion;
      }
    }
    return normalize(hiveHome);
  }

  /**
   * Checks if we need to enable HiveServer2 High Availability.
   *
   * @return true if HA is required
   */
  private static boolean isHiveServer2HA() {
    return exists(HIVE_CONF + File.separator + "enable_hs2_ha");
  }

  /**
   * Get amount of HiveServer2 instances for configuring HA.
   * Value is set in mapR installer.
   *
   * @return amount of HiveServer2 instances for configuring HA.
   */
  private static int findNumHiveServer2() {
    String path = HIVE_CONF + File.separator + "num_hs2";
    if (exists(path)) {
      try {
        return Integer.parseInt(Files.readString(Path.of(path)));
      } catch (IOException e) {
        throw new HiveHaException(String.format("Error reading %s", path));
      }
    }
    return 1;
  }

  /**
   * Get zookeeper quorum for configuring HA.
   * Value is set in mapR installer.
   *
   * @return zookeeper quorum for configuring HA.
   */

  private static String findZookeeperQuorum() {
    String path = HIVE_CONF + File.separator + "zk_hosts";
    if (exists(path)) {
      try {
        return Files.readString(Path.of(path));
      } catch (IOException e) {
        throw new HiveHaException(String.format("Error reading %s", path));
      }
    }
    return "localhost";
  }

  /**
   * Updates hs2 warden file with number of HS2.
   *
   * @param numHiveServer2 amount of HiveServer2 instances
   */
  private static void setNumberOfHiveServer2inWardenFile(int numHiveServer2) {
    String path = MAPR_HOME + File.separator + File.separator + "conf" + File.separator + "conf.d" + File.separator
        + "warden.hs2.conf";
    if (exists(path)) {
      ConfCommonUtilities.replaceLine(path, "services=", String.format("services=hs2:%d:cldb", numHiveServer2));
    }
  }

  /**
   * Configure security related properties.
   *
   * @param hiveSite parsed hive-site.xml
   * @param authMethod authentication method
   */
  private static void configureSecurity(Document hiveSite, Document webHCatSite, AuthMethod authMethod, String headers,
      String adminUser) {
    LOG.info("Configuring security");
    setMetaStoreUseThriftSasl(hiveSite, authMethod);
    setEncryption(hiveSite, authMethod);
    setMetaStoreUgi(hiveSite, authMethod);
    setMetaStoreAuthManager(hiveSite, authMethod);
    setMetaStoreAuthPreEventListener(hiveSite, authMethod);
    setHs2WebUiPamSsl(hiveSite, authMethod);
    setHs2Ssl(hiveSite, authMethod);
    setWebUiHeaders(hiveSite, authMethod, headers);
    configureTokenAuth(hiveSite, authMethod);
    setAdminUser(hiveSite, adminUser, authMethod);
    setRestrictedList(hiveSite, authMethod);
    setFallbackAuthorizer(hiveSite, authMethod);
    setWebHCatSsl(webHCatSite, authMethod);
    setWebHCatHeaders(webHCatSite, authMethod, headers);
  }

  private static String findHiveConf() {
    return HIVE_HOME + File.separator + "conf";
  }

  private static String findHiveBin() {
    return HIVE_HOME + File.separator + "bin";
  }

  private static String findMapRRoles() {
    return MAPR_HOME + File.separator + "roles";
  }

  private static String findHiveSite() {
    return HIVE_CONF + File.separator + "hive-site.xml";
  }

  private static String findWebHCatSite() {
    return HIVE_HOME + File.separator + "hcatalog" + File.separator + "etc" + File.separator + "webhcat"
        + File.separator + "webhcat-site.xml";
  }

  private static String findHeaders() {
    return HIVE_CONF + File.separator + "headers.xml";
  }
}
