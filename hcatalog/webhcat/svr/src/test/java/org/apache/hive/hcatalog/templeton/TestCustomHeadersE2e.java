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
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.hive.hcatalog.templeton;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.hadoop.hive.metastore.MetaStoreUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.TreeMap;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestCustomHeadersE2e {
  private static final Logger LOG = LoggerFactory.getLogger(TestWebHCatE2e.class);
  private static String templetonBaseUrl = "http://localhost:50111/templeton/v1";
  private static Main templetonServer;

  @BeforeClass
  public static void startHebHcatInMem() throws Exception {
    Exception webhcatException = null;
    int webhcatPort = 0;
    boolean webhcatStarted = false;

    for (int tryCount = 0; tryCount < MetaStoreUtils.RETRY_COUNT; tryCount++) {
      try {
        if (tryCount == MetaStoreUtils.RETRY_COUNT - 1) {
          // down to the last try.  try default port 50111
          webhcatPort = 50111;
        } else {
          webhcatPort = MetaStoreUtils.findFreePort();
        }
        templetonBaseUrl = templetonBaseUrl.replace("50111", Integer.toString(webhcatPort));
        templetonServer = new Main(
            new String[] { "-D" + AppConfig.UNIT_TEST_MODE + "=true", "-D" + AppConfig.PORT + "=" + webhcatPort,
                "-D" + AppConfig.HEADERS_FILE + "=" + getPath("headers.xml") });
        LOG.info("Starting Main; WebHCat using port: " + webhcatPort);
        templetonServer.run();
        LOG.info("Main started");
        webhcatStarted = true;
        break;
      } catch (Exception ce) {
        LOG.info("Attempt to Start WebHCat using port: " + webhcatPort + " failed");
        webhcatException = ce;
      }
    }

    if (!webhcatStarted) {
      throw webhcatException;
    }
  }

  @AfterClass
  public static void stopWebHcatInMem() {
    if (templetonServer != null) {
      LOG.info("Stopping Main");
      templetonServer.stop();
      LOG.info("Main stopped");
    }
  }

  @Test
  public void testCustomHeaders() {
    LOG.debug("+getStatus()");
    MethodCallRetVal p = doHttpCall(templetonBaseUrl + "/status");
    assertThat(p.httpStatusCode, is(HttpStatus.OK_200));
    // Must be deterministic order map for comparison across Java versions
    assertThat(jsonStringToSortedMap(p.responseBody),
        is(jsonStringToSortedMap("{\"status\":\"ok\",\"version\":\"v1\"}")));
    assertThat(p.headers, containsString("X-Content-Type-Options=nosniff"));
    assertThat(p.headers, containsString("X-XSS-Protection=1; mode=block"));
    assertThat(p.headers, containsString("Strict-Transport-Security=max-age=31536000"));
    assertThat(p.headers, containsString("Content-Security-Policy=default-src https:"));
    LOG.debug("-getStatus()");
  }

  /**
   * Encapsulates information from HTTP method call
   */
  private static class MethodCallRetVal {
    private final int httpStatusCode;
    private final String responseBody;
    private final String headers;

    private MethodCallRetVal(int httpStatusCode, String responseBody, String headers) {
      this.httpStatusCode = httpStatusCode;
      this.responseBody = responseBody;
      this.headers = headers;
    }
  }

  /**
   * Does a basic HTTP GET and returns Http Status code + response body
   * Will add the dummy user query string
   */
  private static MethodCallRetVal doHttpCall(String uri) {
    HttpClient client = new HttpClient();
    HttpMethod method;
    method = new GetMethod(uri);
    try {
      LOG.debug("GET" + ": " + method.getURI().getEscapedURI());
      int httpStatus = client.executeMethod(method);
      LOG.debug("Http Status Code=" + httpStatus);
      String resp = method.getResponseBodyAsString();
      LOG.debug("response: " + resp);
      return new MethodCallRetVal(httpStatus, resp, toString(method.getResponseHeaders()));
    } catch (IOException ex) {
      LOG.error("doHttpCall() failed", ex);
    } finally {
      method.releaseConnection();
    }
    return new MethodCallRetVal(-1, "Http GET failed; see log file for details", "");
  }

  /**
   * Coverts headers array to string header=value,header=value.
   *
   * @param headers array of headers.
   * @return string comma separated headers.
   */
  private static String toString(Header[] headers) {
    StringBuilder sb = new StringBuilder();
    boolean first = true;
    for (Header header : headers) {
      if (first) {
        first = false;
        sb.append(header.getName()).append("=").append(header.getValue());
      } else {
        sb.append(",");
        sb.append(header.getName()).append("=").append(header.getValue());
      }
    }
    return sb.toString();
  }

  /**
   * Get absolute path of file/dir in resources.
   *
   * @param pathToFile file in resources folder.
   * @return absolute path of file/dir in resources.
   */
  private static String getPath(String pathToFile) {
    ClassLoader classLoader = TestCustomHeadersE2e.class.getClassLoader();
    URL url = classLoader.getResource(pathToFile);
    if (url != null) {
      return url.getFile();
    }
    return "";
  }

  /**
   * Converts string to JSON map.
   *
   * @param jsonStr JSON string
   * @return JSON Map
   */
  private static Map<String, String> jsonStringToSortedMap(String jsonStr) {
    Map<String, String> sortedMap;
    try {
      sortedMap = (new ObjectMapper()).readValue(jsonStr, new TypeReference<TreeMap<String, String>>() {
      });
    } catch (Exception ex) {
      throw new RuntimeException("Exception converting json string to sorted map " + ex, ex);
    }
    return sortedMap;
  }
}
