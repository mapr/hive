/*
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

package org.apache.hadoop.hive.metastore.security;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.security.SaslRpcServer;
import org.apache.hadoop.security.SecurityUtil;
import org.apache.hadoop.security.UserGroupInformation;
import org.apache.hadoop.security.rpcauth.RpcAuthRegistry;
import org.apache.hadoop.security.token.Token;
import org.apache.thrift.transport.TSaslClientTransport;
import org.apache.thrift.transport.TTransport;

import javax.security.sasl.SaslException;
import java.io.IOException;
import java.util.Map;

/**
 * Help class for creating Thrift transport.
 */
final class ThriftTransportHelper {
  private ThriftTransportHelper() {
  }

  private static final Log LOG = LogFactory.getLog(ThriftTransportHelper.class);

  /**
   * Creates token thrift transport.
   *
   * @param underlyingTransport The underlying transport mechanism, usually a TSocket.
   * @param tokenStrForm encoded string of the delegation token
   * @param saslProps map of sasl properties
   * @return token thrift transport
   * @throws IOException when can't create thrift transport.
   */
  static TTransport createTokenTransport(TTransport underlyingTransport, String tokenStrForm,
      Map<String, String> saslProps) throws IOException {
    Token<DelegationTokenIdentifier> t = new Token<>();
    t.decodeFromUrlString(tokenStrForm);
    TTransport saslTransport = new TSaslClientTransport(SaslRpcServer.AuthMethod.TOKEN.getMechanismName(), null, null,
        SaslRpcServer.SASL_DEFAULT_REALM, saslProps, new HadoopThriftAuthBridge.Client.SaslClientCallbackHandler(t),
        underlyingTransport);
    LOG.info(String.format("User authentication with method: %s", SaslRpcServer.AuthMethod.TOKEN.getMechanismName()));
    return new TUGIAssumingTransport(saslTransport, UserGroupInformation.getCurrentUser());
  }

  /**
   * Creates MapRSASL thrift transport.
   *
   * @param underlyingTransport The underlying transport mechanism, usually a TSocket.
   * @param saslProps map of sasl properties
   * @return MapRSASL transport
   * @throws IOException
   */
  static TTransport createMapRSaslTransport(TTransport underlyingTransport, Map<String, String> saslProps)
      throws IOException {
    try {
      TTransport saslTransport = new TSaslClientTransport(
          RpcAuthRegistry.getAuthMethod(UserGroupInformation.AuthenticationMethod.CUSTOM).getMechanismName(), null,
          null, SaslRpcServer.SASL_DEFAULT_REALM, saslProps, null, underlyingTransport);
      LOG.info(String.format("User authentication with method: %s",
          RpcAuthRegistry.getAuthMethod(UserGroupInformation.AuthenticationMethod.CUSTOM).getMechanismName()));
      return new TUGIAssumingTransport(saslTransport, UserGroupInformation.getCurrentUser());
    } catch (SaslException se) {
      throw new IOException("Could not instantiate SASL transport", se);
    }
  }


  /**
   * Creates Kerberos thrift transport.
   *
   * @param principalConfig the Kerberos principal name configuration value to convert when converting
   *                        Kerberos principal name pattern to valid Kerberos principal names
   * @param host the fully-qualified domain name used for substitution
   * @param underlyingTransport The underlying transport mechanism, usually a TSocket.
   * @param saslProps map of sasl properties
   * @return Kerberos thrift transport
   * @throws IOException when can't create thrift transport.
   */

  static TTransport createKerberosTransport(String principalConfig, String host, TTransport underlyingTransport,
      Map<String, String> saslProps) throws IOException {
    String serverPrincipal = SecurityUtil.getServerPrincipal(principalConfig, host);
    String[] names = SaslRpcServer.splitKerberosName(serverPrincipal);
    if (names.length != 3) {
      throw new IOException("Kerberos principal name does NOT have the expected hostname part: " + serverPrincipal);
    }
    try {
      TTransport saslTransport =
          new TSaslClientTransport(SaslRpcServer.AuthMethod.KERBEROS.getMechanismName(), null, names[0], names[1],
              saslProps, null, underlyingTransport);
      LOG.info(String.format("User authentication with method: %s", SaslRpcServer.AuthMethod.KERBEROS.getMechanismName()));
      return new TUGIAssumingTransport(saslTransport, UserGroupInformation.getCurrentUser());
    } catch (SaslException se) {
      throw new IOException("Could not instantiate SASL transport", se);
    }
  }

  /**
   * Format authentication method name.
   *
   * @param authMethod authentication method name
   * @return trimmed and upper cased method name
   */
  static String format(String authMethod) {
    return authMethod == null ? "" : authMethod.trim().toUpperCase();
  }
}
