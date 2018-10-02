/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to you under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.hive.http.security;

import com.mapr.login.PasswordAuthentication;
import org.eclipse.jetty.http.HttpHeaders;
import org.eclipse.jetty.security.ServerAuthException;
import org.eclipse.jetty.security.UserAuthentication;
import org.eclipse.jetty.security.authentication.DeferredAuthentication;
import org.eclipse.jetty.security.authentication.LoginAuthenticator;
import org.eclipse.jetty.server.Authentication;
import org.eclipse.jetty.server.UserIdentity;
import org.eclipse.jetty.util.B64Code;
import org.eclipse.jetty.util.StringUtil;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PamAuthenticator extends LoginAuthenticator {

  @Override
  public String getAuthMethod() {
    return "pam";
  }

  @Override
  public Authentication validateRequest(ServletRequest req, ServletResponse res, boolean mandatory) throws ServerAuthException {
    HttpServletRequest request = (HttpServletRequest) req;
    HttpServletResponse response = (HttpServletResponse) res;
    String credentials = request.getHeader(HttpHeaders.AUTHORIZATION);

    try {
      if (!mandatory) return _deferred;

      if (credentials != null) {
        int space = credentials.indexOf(' ');
        if (space > 0) {
          String method = credentials.substring(0, space);
          if ("basic".equalsIgnoreCase(method)) {
            credentials = credentials.substring(space + 1);
            credentials = B64Code.decode(credentials, StringUtil.__ISO_8859_1);
            int i = credentials.indexOf(':');
            if (i > 0) {
              String username = credentials.substring(0, i);
              String password = credentials.substring(i + 1);

              UserIdentity user = login(username, password);
              if (user != null) {
                renewSessionOnAuthentication(request, response);
                return new UserAuthentication(getAuthMethod(), user);
              }
            }
          }
        }
      }

      if (DeferredAuthentication.isDeferred(response)) return Authentication.UNAUTHENTICATED;

      response.setHeader(HttpHeaders.WWW_AUTHENTICATE, "basic realm=\"" + _loginService.getName() + '"');
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
      return Authentication.SEND_CONTINUE;
    } catch (IOException e) {
      throw new ServerAuthException(e);
    }
  }

  protected UserIdentity login(String username, String password) {
    UserIdentity user = null;
    if (PasswordAuthentication.authenticate(username, password)) {
      user = new PamUserIdentity(username);
    }
    return user;
  }

  @Override
  public boolean secureResponse(ServletRequest servletRequest, ServletResponse servletResponse, boolean b, Authentication.User user) throws ServerAuthException {
    return true;
  }
}
