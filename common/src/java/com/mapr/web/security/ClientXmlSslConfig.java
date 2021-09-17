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
package com.mapr.web.security;

import com.google.common.io.Closeables;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hive.conf.MapRSecurityUtil;

import static org.apache.hadoop.hive.conf.MapRSecurityUtil.isClusterAdminProcess;

/**
 * All configuration regarding Ssl as separate class. Unlike the sources from MapR which uses
 *
 *   conf.addResource(inStreamClient);
 *
 * this class uses
 *
 *   conf.addResource(inStreamClient, false);
 *
 * And that allows to avoid 'Unable to determine current user' when there is no MapR ticket for user
 * that wants to get ssl configuration. This is the only reason of this class.
 */
public class ClientXmlSslConfig implements Closeable {
  private static final String SSL_CLIENT_XML = "ssl-client.xml";
  private static final String SSL_SERVER_XML = "ssl-server.xml";
  private final String clientTruststoreLocation;
  private final String clientKeystoreLocation;
  private final char[] clientKeystorePassword;
  private final char[] clientTruststorePassword;

  public ClientXmlSslConfig() {
    String maprConfDir = MapRSecurityUtil.findMapRHome() + "/conf/";
    File sslServerXml = new File(maprConfDir, SSL_SERVER_XML);
    File sslClientXml = new File(maprConfDir, SSL_CLIENT_XML);
    FileInputStream inStreamClient = null;
    FileInputStream inStreamServer = null;

    try {
      Configuration conf = new Configuration(true);
      inStreamClient = new FileInputStream(sslClientXml);
      // This is the reason I am creating ClientXmlSslConfig: to avoid default value for restrictedParser
      // evaluation which leads to 'Unable to determine current user' if there is no MapR ticket even
      // if PAM is used.
      if (isClusterAdminProcess()) {
        inStreamServer = new FileInputStream(sslServerXml);
        conf.addResource(inStreamServer, false);
      }
      conf.addResource(inStreamClient, false);

      this.clientTruststoreLocation = conf.get("ssl.client.truststore.location");
      this.clientKeystoreLocation = conf.get("ssl.client.keystore.location");
      this.clientKeystorePassword = conf.getPassword("ssl.client.keystore.password");
      this.clientTruststorePassword = conf.getPassword("ssl.client.truststore.password");
    } catch (IOException e) {
      throw new SecurityException("Unable to read SSL configuration from XML files", e);
    } finally {
      try {
        Closeables.close(inStreamClient, true);
        Closeables.close(inStreamServer, true);
      } catch (IOException ignored) {
      }
    }
  }

  public String getClientTruststoreLocation() throws SecurityException {
    return this.clientTruststoreLocation;
  }

  public char[] getClientTruststorePassword() throws SecurityException {
    return this.clientTruststorePassword == null ? null : Arrays
        .copyOf(this.clientTruststorePassword, this.clientTruststorePassword.length);
  }

  public String getClientKeystoreLocation() throws SecurityException {
    return this.clientKeystoreLocation;
  }

  public char[] getClientKeystorePassword() throws SecurityException {
    return this.clientKeystorePassword == null ? null : Arrays
        .copyOf(this.clientKeystorePassword, this.clientKeystorePassword.length);
  }

  @Override
  public void close() {
    char[][] passwords =
        new char[][] {this.clientKeystorePassword, this.clientTruststorePassword };
    for (char[] password : passwords) {
      if (password != null) {
        Arrays.fill(password, (char) 0);
      }
    }
  }
}
