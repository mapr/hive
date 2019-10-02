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
package org.apache.hive.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;

/**
 * Security headers filter.
 */
public class CustomHeadersFilter implements Filter {
  public static final String HEADERS = "jetty.custom.headers.file.location";
  private final Properties properties = new Properties();
  private static final Logger LOG = LoggerFactory.getLogger(CustomHeadersFilter.class);

  @Override
  public void init(FilterConfig filterConfig) {
    String pathToHeaders = "";
    if (filterConfig != null) {
      pathToHeaders = filterConfig.getInitParameter(HEADERS);
    }
    if (pathToHeaders != null && !pathToHeaders.isEmpty()) {
      File headers = new File(pathToHeaders);
      if (headers.exists()) {
        loadFromFile(headers);
        LOG.info(String.format("Loaded headers from file: %s", headers.getAbsolutePath()));
      }
    }
  }

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
      throws IOException, ServletException {
    HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
    Set<String> keys = properties.stringPropertyNames();
    for (String key : keys) {
      httpResponse.addHeader(key, properties.getProperty(key));
    }
    filterChain.doFilter(servletRequest, servletResponse);
  }

  @Override
  public void destroy() {
    // nothing to do
  }

  private void loadFromFile(File headers) {
    try {
      properties.loadFromXML(new FileInputStream(headers));
    } catch (IOException e) {
      LOG.error(e.toString());
    }
  }
}
