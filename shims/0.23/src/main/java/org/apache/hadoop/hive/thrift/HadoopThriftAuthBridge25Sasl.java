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
package org.apache.hadoop.hive.thrift;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.security.SaslRpcServer;
import org.apache.hadoop.security.SaslRpcServer.AuthMethod;
import org.apache.hadoop.security.UserGroupInformation;
import org.apache.hadoop.security.rpcauth.RpcAuthMethod;
import org.apache.thrift.transport.TSaslServerTransport;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

import static org.apache.hadoop.fs.CommonConfigurationKeys.HADOOP_SECURITY_AUTHENTICATION;
import static org.apache.hadoop.hive.thrift.ThriftTransportHelper.*;

/**
 * Functions that bridge Thrift's SASL transports to Hadoop's SASL callback
 * handlers and authentication classes.
 *
 * This is a 0.25/2.x specific implementation and maprsasl enabled
 */
public class HadoopThriftAuthBridge25Sasl extends HadoopThriftAuthBridge23 {

  @Override public Server createServer(String keytabFile, String principalConf)
      throws TTransportException {
    if (keytabFile.isEmpty() || principalConf.isEmpty()) {
      return new Server();
    } else {
      return new Server(keytabFile, principalConf);
    }
  }

  @Override public Client createClientWithConf(String authType) {
    Configuration conf = new Configuration();
    conf.set(HADOOP_SECURITY_AUTHENTICATION, authType);
    UserGroupInformation.setConfiguration(conf);
    return new Client();
  }

  public static class Server extends HadoopThriftAuthBridge.Server {
    public Server() throws TTransportException {
      super();
    }

    /**
     * Create a server with a kerberos keytab/principal.
     */
    protected Server(String keytabFile, String principalConf) throws TTransportException {
      super(keytabFile, principalConf);
    }

    /**
     * Create a TTransportFactory that, upon connection of a client socket,
     * negotiates a SASL transport (The current supported SASL authentication methods are KERBEROS or MaprSasl).
     * The resulting TTransportFactory can be passed as both the input and output transport factory when
     * instantiating a TThreadPoolServer, for example.
     *
     * @param saslProps Map of SASL properties
     */
    @Override public TSaslServerTransport.Factory createSaslServerTransportFactory(Map<String, String> saslProps) {
      List<RpcAuthMethod> rpcAuthMethods = realUgi.getRpcAuthMethodList();
      TSaslServerTransport.Factory transFactory = new TSaslServerTransport.Factory();
      for (RpcAuthMethod rpcAuthMethod : rpcAuthMethods) {
        if (rpcAuthMethod.getAuthenticationMethod().equals(UserGroupInformation.AuthenticationMethod.KERBEROS)) {
          // Parse out the kerberos principal, host, realm.
          String kerberosName = realUgi.getUserName();
          final String[] names = SaslRpcServer.splitKerberosName(kerberosName);
          if (names.length == 3) {
            transFactory.addServerDefinition(rpcAuthMethod.getMechanismName(), names[0], names[1],
                // two parts of kerberos principal
                saslProps, rpcAuthMethod.createCallbackHandler());
          }
        } else {
          transFactory
              .addServerDefinition(rpcAuthMethod.getMechanismName(), null, SaslRpcServer.SASL_DEFAULT_REALM, saslProps,
                  rpcAuthMethod.createCallbackHandler());
        }
      }
      transFactory
          .addServerDefinition(AuthMethod.DIGEST.getMechanismName(), null, SaslRpcServer.SASL_DEFAULT_REALM, saslProps,
              new SaslDigestCallbackHandler(secretManager));
      return transFactory;
    }
  }

  @Override public Client createClient() {
    return new Client();
  }

  public static class Client extends HadoopThriftAuthBridge.Client {

    /**
     * Create a client-side SASL transport that wraps an underlying transport.
     *
     * @param methodStr The authentication method to use. Currently KERBEROS and MAPRSasl are
     * supported.
     * @param principalConfig The Kerberos principal of the target server.
     * @param underlyingTransport The underlying transport mechanism, usually a TSocket.
     * @param saslProps the sasl properties to create the client with
     */

    @Override public TTransport createClientTransport(String principalConfig, String host, String methodStr,
        String tokenStrForm, TTransport underlyingTransport, Map<String, String> saslProps) throws IOException {
      switch (format(methodStr)) {
      case "DIGEST":
      case "TOKEN":
        return createTokenTransport(underlyingTransport, tokenStrForm, saslProps);
      case "KERBEROS":
        return createKerberosTransport(principalConfig, host, underlyingTransport, saslProps);
      case "MAPRSASL":
      case "PAM":
      case "LDAP":
        return createMapRSaslTransport(underlyingTransport, saslProps);
      }
      throw new IOException("Unsupported authentication method: " + methodStr);
    }
  }
}

