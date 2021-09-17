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

package org.apache.hadoop.hive.conf;

import org.apache.commons.lang3.SystemUtils;
import org.apache.hadoop.conf.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

import static org.apache.hadoop.hive.conf.HiveConf.applySystemProperties;
import static org.apache.hadoop.hive.conf.HiveConf.findConfigFile;
import static org.apache.hadoop.hive.conf.HiveConf.isLoadHiveServer2Config;
import static org.apache.hadoop.hive.conf.HiveConf.isLoadMetastoreConfig;

/**
 * Utility class for reading security configuration from cluster configuration files.
 */
public final class MapRSecurityUtil {
  private static final Logger LOG = LoggerFactory.getLogger(MapRSecurityUtil.class.getName());
  private static String authMethod = "not-defined";
  private static String mapRHome = null;
  private static ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

  static {
    if (classLoader == null) {
      classLoader = MapRSecurityUtil.class.getClassLoader();
    }
  }

  private MapRSecurityUtil() {
  }

  /**
   * Check if mapr security is enabled on the cluster.
   * Value auth_method is set in $HIVE_BIN/hive script
   * @return true if mapr security is enabled
   */

  public static boolean isMapRSecurityEnabled() {
    if (isAuthMethodSet()) {
      return "maprsasl".equals(authMethod);
    }
    return mapRClusterConfContains("secure=true") && !mapRClusterConfContains("kerberosEnable=true") && !isCustomSecureFlagExists();
  }

  /**
   * Check if kerberos security is enabled on the cluster.
   * Value auth_method is set in $HIVE_BIN/hive script
   * @return true if kerberos security is enabled
   */

  static boolean isKerberosEnabled() {
    if (isAuthMethodSet()) {
      return "kerberos".equals(authMethod);
    }
    return mapRClusterConfContains("kerberosEnable=true") && !isCustomSecureFlagExists();
  }

  /**
   * Checks if security flag is set and has valid value.
   * @return true if security flag is set and has valid value
   */

  public static boolean isAuthMethodSet() {
    return !"not-defined".equals(authMethod);
  }

  /**
   * Check if custom security is enabled on the cluster.
   * Value auth_method is set in $HIVE_BIN/hive script
   * @return true if custom security is enabled
   */

  static boolean isCustomSecurityEnabled() {
    if (isAuthMethodSet()) {
      return "custom".equals(authMethod);
    }
    return isCustomSecureFlagExists();
  }

  /**
   * Returns current authentication method.
   *
   * @return current authentication method.
   */
  public static String getAuthMethod() {
    if (isMapRSecurityEnabled()) {
      return "maprsasl";
    }
    if (isKerberosEnabled()) {
      return "kerberos";
    }
    if (isCustomSecurityEnabled()) {
      return "custom";
    }
    if (isNoSecurity()) {
      return "none";
    }
    LOG.warn("Authentication method is not defined");
    return "";
  }

  /**
   * Builds Configuration object and initializes it from default files:
   *  - hive-site.xml
   *  - hivemetastore-site.xml
   *  - hiveserver2-site.xml
   *  Does not use restricted parser and does not require UserGroupInformation calls. This is
   *  lightweight analog of HiveConf object but more simpler and faster.
   *
   * @return initialized Configuration object
   */
  public static Configuration buildConfigurationFromDefaultFiles(){
    Configuration conf = new Configuration();
    // Look for hive-site.xml on the CLASSPATH and log its location if found.
    URL hiveSiteURL = findConfigFile(classLoader, "hive-site.xml", true);
    URL hivemetastoreSiteUrl = findConfigFile(classLoader, "hivemetastore-site.xml", false);
    URL hiveServer2SiteUrl = findConfigFile(classLoader, "hiveserver2-site.xml", false);

    if (hiveSiteURL != null) {
      conf.addResource(hiveSiteURL, false);
    }

    if (hivemetastoreSiteUrl != null && isLoadMetastoreConfig()) {
      conf.addResource(hivemetastoreSiteUrl, false);
    }

    if (hiveServer2SiteUrl != null && isLoadHiveServer2Config()) {
      conf.addResource(hiveServer2SiteUrl, false);
    }

    applySystemProperties(conf);
    return conf;
  }

  /**
   * Reads hive.validate.expiry.time.for.mapr.ticket and returns default value if not in hive-site.xml.
   *
   * @return hive.validate.expiry.time.for.mapr.ticket
   */
  public static boolean isExpiryTimeValidation() {
    return buildConfigurationFromDefaultFiles().getBoolean(HiveConf.ConfVars.HIVE_VALIDATE_EXPIRY_TIME_FOR_MAPR_TICKET.varname,
        HiveConf.ConfVars.HIVE_VALIDATE_EXPIRY_TIME_FOR_MAPR_TICKET.defaultBoolVal);
  }

  /**
   * Returns SSL protocol version.
   *
   * @return SSL protocol version
   */
  public static String getSslProtocolVersion() {
    return buildConfigurationFromDefaultFiles().get(HiveConf.ConfVars.HIVE_SSL_PROTOCOL_VERSION.varname,
        HiveConf.ConfVars.HIVE_SSL_PROTOCOL_VERSION.defaultStrVal);
  }


  /**
   * Checks for no security.
   *
   * @return true if cluster is insecure
   */
  static boolean isNoSecurity() {
    if (isAuthMethodSet()) {
      return "none".equals(authMethod);
    }
    return mapRClusterConfContains("secure=false") && !isCustomSecureFlagExists();
  }

  /**
   * Checks if file $MAPR_HOME/conf/mapr-clusters.conf contains certain value.
   *
   * @param value value to find
   * @return true if file mapr-clusters.conf contains string
   */
  private static boolean mapRClusterConfContains(String value) {
    String path = getMapRHome() + "/conf/mapr-clusters.conf";
    if (exists(path)) {
      return readFile(path).contains(value);
    }
    LOG.warn(String.format("File %s does not exists", path));
    return false;
  }

  /**
   * Checks if file $MAPR_HOME/conf/.customSecure exists.
   *
   * @return true if exists
   */
  private static boolean isCustomSecureFlagExists() {
    return exists(getMapRHome() + "/conf/.customSecure");
  }

  /**
   * Finds mapR home.
   *
   * @return MapR Home folder as string
   */
  public static String findMapRHome() {
    String maprHome = System.getenv("MAPR_HOME");
    if (maprHome == null) {
      LOG.warn("Environment variable MAPR_HOME is null");
      maprHome = System.getProperty("mapr.home.dir");
      if (maprHome == null) {
        LOG.warn("System property mapr.home.dir is null");
        maprHome = SystemUtils.IS_OS_WINDOWS ? "C:/opt/mapr" : "/opt/mapr";
        LOG.warn("Setting MapR home as {} by default", maprHome);
      }
    }
    return maprHome;
  }

  /**
   * Checks if the process is run under cluster admin.
   *
   * @return true if the process is run under cluster admin.
   */

  public static boolean isClusterAdminProcess() {
    String currentUser = System.getProperty("user.name");
    String clusterAdmin = findAdminUser();
    if (currentUser != null && !currentUser.isBlank() && clusterAdmin != null && !clusterAdmin.isBlank()) {
      return currentUser.equals(clusterAdmin);
    }
    return false;
  }

  /**
   * Find admin user of the cluster
   *
   * @return admin user of the cluster
   */
  public static String findAdminUser() {
    String pathToDaemonConf = findMapRHome() + File.separator + "conf" + File.separator + "daemon.conf";
    try (InputStream is = new FileInputStream(pathToDaemonConf)) {
      Properties properties = new Properties();
      properties.load(is);
      return properties.getProperty("mapr.daemon.user");
    } catch (IOException e) {
      LOG.error(e.toString());
    }
    return "";
  }

  /**
   * Lazy initialization for MapR Home.
   *
   * @return MAPR_HOME value
   */

  private static String getMapRHome() {
    if (mapRHome == null) {
      mapRHome = findMapRHome();
    }
    return mapRHome;
  }

  /**
   * Checks if file exists.
   *
   * @param filePath path to file
   * @return true if file exists
   */
  private static boolean exists(String filePath) {
    return new File(filePath).exists();
  }

  /**
   * Reads file as string.
   *
   * @param filePath path to file
   * @return file as string
   */
  private static String readFile(String filePath) {
    String content = "";
    try {
      content = new String(Files.readAllBytes(Paths.get(filePath)));
    } catch (IOException e) {
      LOG.error(e.toString());
    }
    return content;
  }
}
