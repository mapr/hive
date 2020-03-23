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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Utility class for reading security configuration from cluster configuration files.
 */
public final class MapRSecurityUtil {
  private static final Logger LOG = LoggerFactory.getLogger(MapRSecurityUtil.class.getName());
  private static String authMethod = "not-defined";
  private static String mapRHome = null;
  private static HiveConf hiveConf = null;

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
   * Returns SSL protocol version.
   *
   * @return SSL protocol version
   */
  public static String getSslProtocolVersion() {
    if (hiveConf == null) {
      hiveConf = new HiveConf();
    }
    return HiveConf.getVar(hiveConf, HiveConf.ConfVars.HIVE_SSL_PROTOCOL_VERSION);
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
  private static String findMapRHome() {
    String maprHome = System.getenv("MAPR_HOME");
    if (maprHome == null) {
      LOG.warn("Environment variable MAPR_HOME is null");
      maprHome = System.getProperty("mapr.home.dir");
      if (maprHome == null) {
        LOG.warn("System property mapr.home.dir is null");
        maprHome = "/opt/mapr/";
        LOG.warn("Setting MapR home as /opt/mapr/ by default");
      }
    }
    return maprHome;
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
