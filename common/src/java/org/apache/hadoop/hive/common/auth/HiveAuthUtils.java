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
package org.apache.hadoop.hive.common.auth;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509ExtendedTrustManager;

import org.apache.hadoop.hive.conf.HiveConf;
import org.apache.thrift.transport.TSSLTransportFactory;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class helps in some aspects of authentication. It creates the proper Thrift classes for the
 * given configuration as well as helps with authenticating requests.
 */
public class HiveAuthUtils {
  private static final Logger LOG = LoggerFactory.getLogger(HiveAuthUtils.class);
  private static HiveConf hiveConf = null;

  public static TTransport getSocketTransport(String host, int port, int loginTimeout) {
    return new TSocket(host, port, loginTimeout);
  }


  public static TTransport getSSLSocket(String host, int port, int loginTimeout,
    String trustStorePath, String trustStorePassWord, String sslProtocolVersion) throws TTransportException {
    TSSLTransportFactory.TSSLTransportParameters params =
        new TSSLTransportFactory.TSSLTransportParameters(sslProtocolVersion, null);
    params.setTrustStore(trustStorePath, trustStorePassWord);
    params.requireClientAuth(true);
    // The underlying SSLSocket object is bound to host:port with the given SO_TIMEOUT and
    // SSLContext created with the given params
    TSocket tSSLSocket = TSSLTransportFactory.getClientSocket(host, port, loginTimeout, params);
    return getSSLSocketWithHttps(tSSLSocket);
  }

  // Using endpoint identification algorithm as HTTPS enables us to do
  // CNAMEs/subjectAltName verification
  private static TSocket getSSLSocketWithHttps(TSocket tSSLSocket) throws TTransportException {
    SSLSocket sslSocket = (SSLSocket) tSSLSocket.getSocket();
    SSLParameters sslParams = sslSocket.getSSLParameters();
    sslParams.setEndpointIdentificationAlgorithm("HTTPS");
    sslSocket.setSSLParameters(sslParams);
    return new TSocket(sslSocket);
  }

  public static TServerSocket getServerSocket(String hiveHost, int portNum)
    throws TTransportException {
    InetSocketAddress serverAddress;
    if (hiveHost == null || hiveHost.isEmpty()) {
      // Wildcard bind
      serverAddress = new InetSocketAddress(portNum);
    } else {
      serverAddress = new InetSocketAddress(hiveHost, portNum);
    }
    return new TServerSocket(serverAddress);
  }

  public static TServerSocket getServerSSLSocket(String hiveHost, int portNum, String keyStorePath,
      String keyStorePassWord, List<String> sslVersionBlacklist, String sslProtocolVersion) throws TTransportException,
      UnknownHostException {
    TSSLTransportFactory.TSSLTransportParameters params =
        new TSSLTransportFactory.TSSLTransportParameters(sslProtocolVersion, null);
    params.setKeyStore(keyStorePath, keyStorePassWord);
    InetSocketAddress serverAddress;
    if (hiveHost == null || hiveHost.isEmpty()) {
      // Wildcard bind
      serverAddress = new InetSocketAddress(portNum);
    } else {
      serverAddress = new InetSocketAddress(hiveHost, portNum);
    }
    TServerSocket thriftServerSocket =
        TSSLTransportFactory.getServerSocket(portNum, 0, serverAddress.getAddress(), params);
    if (thriftServerSocket.getServerSocket() instanceof SSLServerSocket) {
      List<String> sslVersionBlacklistLocal = new ArrayList<String>();
      for (String sslVersion : sslVersionBlacklist) {
        sslVersionBlacklistLocal.add(sslVersion.trim().toLowerCase());
      }
      SSLServerSocket sslServerSocket = (SSLServerSocket) thriftServerSocket.getServerSocket();
      List<String> enabledProtocols = new ArrayList<String>();
      for (String protocol : sslServerSocket.getEnabledProtocols()) {
        if (sslVersionBlacklistLocal.contains(protocol.toLowerCase())) {
          LOG.debug("Disabling SSL Protocol: " + protocol);
        } else {
          enabledProtocols.add(protocol);
        }
      }
      sslServerSocket.setEnabledProtocols(enabledProtocols.toArray(new String[0]));
      LOG.info("SSL Server Socket Enabled Protocols: "
          + Arrays.toString(sslServerSocket.getEnabledProtocols()));
    }
    return thriftServerSocket;
  }

  //Create SSL Socket for MAPRSASL connection. Ignore SSL trusted servers as MAPRSASL perform encryption by itself
  public static TTransport getTrustAllSSLSocket(String host, int port, int loginTimeout) throws TTransportException {
    TrustManager[] trustAllCerts = new TrustManager[]{
        new X509ExtendedTrustManager() {
          @Override
          public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
          }
          @Override
          public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
          }
          @Override
          public X509Certificate[] getAcceptedIssuers() {
            return null;
          }
          @Override
          public void checkClientTrusted(X509Certificate[] x509Certificates, String s, Socket socket) throws CertificateException {
          }
          @Override
          public void checkServerTrusted(X509Certificate[] x509Certificates, String s, Socket socket) throws CertificateException {
          }
          @Override
          public void checkClientTrusted(X509Certificate[] x509Certificates, String s, SSLEngine sslEngine) throws CertificateException {
          }
          @Override
          public void checkServerTrusted(X509Certificate[] x509Certificates, String s, SSLEngine sslEngine) throws CertificateException {
          }
        }
    };
    SSLSocket socket;
    try {
      SSLContext sslContext = SSLContext.getInstance(getSslProtocolVersion());
      sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
      SSLSocketFactory factory = sslContext.getSocketFactory();
      socket = (SSLSocket) factory.createSocket(host, port);
      socket.setSoTimeout(loginTimeout);
    } catch (NoSuchAlgorithmException | IOException | KeyManagementException e) {
      throw new TTransportException("Couldn't create Trust All SSL socket", e);
    }
    return new TSocket(socket);
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


}
