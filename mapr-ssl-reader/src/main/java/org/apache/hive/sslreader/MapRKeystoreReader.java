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

package org.apache.hive.sslreader;

import com.mapr.web.security.SslConfig.SslConfigScope;
import com.mapr.web.security.SslConfig;
import com.mapr.web.security.WebSecurityManager;

/**
 * Utility class for ssl default configuration.
 */

public final class MapRKeystoreReader {
  private static final String MAPR_SEC_ENABLED = "mapr_sec_enabled";
  private MapRKeystoreReader() {
  }

  /**
   * Reads client keystore location.
   * @return client keystore location as string
   */

  public static String getClientKeystoreLocation() {
    try (SslConfig sslConfig = WebSecurityManager.getSslConfig(SslConfigScope.SCOPE_CLIENT_ONLY)) {
      return sslConfig.getClientKeystoreLocation();
    }
  }

  /**
   * Reads client password value.
   * @return client password value as string
   */

  public static String getClientKeystorePassword() {
    try (SslConfig sslConfig = WebSecurityManager.getSslConfig(SslConfigScope.SCOPE_CLIENT_ONLY)) {
      return new String(sslConfig.getClientKeystorePassword());

    }
  }

  /**
   * Check if mapr security is enabled on the cluster.
   * Value mapr_sec_enabled is set in $HIVE_BIN/hive script
   * @return
   */

  public static boolean isSecurityEnabled() {
    String mapRSecurityEnabled = System.getProperty(MAPR_SEC_ENABLED);
    if (!isSecurityFlagSet()) {
      return false;
    }
    return "true".equalsIgnoreCase(mapRSecurityEnabled.trim());
  }

  /**
   * Checks if security flag is set and has non empty value.
   * @return true if security flag is set and has non empty value
   */

  public static boolean isSecurityFlagSet() {
    String mapRSecurityEnabled = System.getProperty(MAPR_SEC_ENABLED);
    return mapRSecurityEnabled != null && !mapRSecurityEnabled.isEmpty();
  }
}
