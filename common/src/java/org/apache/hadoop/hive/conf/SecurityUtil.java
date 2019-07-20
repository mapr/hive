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

/**
 * Utility class for reading security configuration JVM options.
 */
public final class SecurityUtil {
  private static final String MAPR_SEC_ENABLED = "mapr_sec_enabled";
  private static final String CUSTOM_SEC_ENABLED = "custom_sec_enabled";

  private SecurityUtil(){}

  /**
   * Check if mapr security is enabled on the cluster.
   * Value mapr_sec_enabled is set in $HIVE_BIN/hive script
   * @return true if mapr security is enabled
   */

  public static boolean isMapRSecurityEnabled() {
    String mapRSecurityEnabled = System.getProperty(MAPR_SEC_ENABLED);
    if (!isMapRSecurityFlagSet()) {
      return false;
    }
    return "true".equalsIgnoreCase(mapRSecurityEnabled.trim());
  }

  /**
   * Checks if security flag is set and has non empty value.
   * @return true if security flag is set and has non empty value
   */

  public static boolean isMapRSecurityFlagSet() {
    String mapRSecurityEnabled = System.getProperty(MAPR_SEC_ENABLED);
    return mapRSecurityEnabled != null && !mapRSecurityEnabled.isEmpty();
  }


  /**
   * Check if custom security is enabled on the cluster.
   * Value custom_sec_enabled is set in $HIVE_BIN/hive script
   * @return true if custom security is enabled
   */

  static boolean isCustomSecurityEnabled() {
    String customSecurityEnabled = System.getProperty(CUSTOM_SEC_ENABLED);
    if (!isCustomSecurityFlagSet()) {
      return false;
    }
    return "true".equalsIgnoreCase(customSecurityEnabled.trim());
  }

  /**
   * Checks if custom security flag is set and has non empty value.
   * @return true if security flag is set and has non empty value
   */

  private static boolean isCustomSecurityFlagSet() {
    String customSecurityEnabled = System.getProperty(CUSTOM_SEC_ENABLED);
    return customSecurityEnabled != null && !customSecurityEnabled.isEmpty();
  }

  /**
   * Checks for no security.
   *
   * @return true if cluster is insecure
   */
  static boolean isNoSecurity(){
    return !isCustomSecurityEnabled() && !isMapRSecurityEnabled();
  }
}
