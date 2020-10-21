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

import com.mapr.web.security.ClientXmlSslConfig;

/**
 * Utility class for ssl default configuration.
 */

public final class MapRKeystoreReader {
  private MapRKeystoreReader() {
  }

  /**
   * Reads client keystore location.
   * @return client keystore location as string
   */

  public static String getClientKeystoreLocation() {
    try (ClientXmlSslConfig clientXmlSslConfig = new ClientXmlSslConfig()) {
      return clientXmlSslConfig.getClientKeystoreLocation();
    }
  }

  /**
   * Reads client truststore location.
   * @return client truststore location as string
   */

  public static String getClientTruststoreLocation() {
    try (ClientXmlSslConfig clientXmlSslConfig = new ClientXmlSslConfig()) {
      return clientXmlSslConfig.getClientTruststoreLocation();
    }
  }


  /**
   * Reads client keystore password value.
   * @return client keystore password value as string
   */

  public static String getClientKeystorePassword() {
    try (ClientXmlSslConfig clientXmlSslConfig = new ClientXmlSslConfig()) {
      return new String(clientXmlSslConfig.getClientKeystorePassword());
    }
  }

  /**
   * Reads client truststore password value.
   * @return client truststore password value as string
   */

  public static String getClientTruststorePassword() {
    try (ClientXmlSslConfig clientXmlSslConfig = new ClientXmlSslConfig()) {
      return new String(clientXmlSslConfig.getClientTruststorePassword());
    }
  }
}
