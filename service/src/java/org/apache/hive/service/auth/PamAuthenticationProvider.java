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
package org.apache.hive.service.auth;

import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.LoginException;
import javax.security.auth.login.LoginContext;
import javax.security.sasl.AuthenticationException;

import org.apache.hadoop.hive.conf.HiveConf;

import java.io.IOException;

public class PamAuthenticationProvider implements PasswdAuthenticationProvider {

  private String pamJaasConfig;

  public PamAuthenticationProvider() {
    HiveConf conf = new HiveConf();
    pamJaasConfig = conf.getVar(HiveConf.ConfVars.HIVE_SERVER2_PAM_JAAS_CONFIG);
  }

  @Override
  public void Authenticate(String user, String  password)
          throws AuthenticationException {

    try {
      LoginContext ctx = new LoginContext(pamJaasConfig, new JAASCallbackHandler(user, password));
      ctx.login();
    } catch (LoginException ex) {
      throw new AuthenticationException("Failed authentication for user " + user, ex);
    } catch(LinkageError ex) {
      throw new AuthenticationException("Link Error. Failed to authenticate user " + user, ex);
    }
  }

  static class JAASCallbackHandler implements CallbackHandler {
    private String user;
    private String pass;

    public JAASCallbackHandler(String user, String pass) {
      this.user = user;
      this.pass = pass;
    }

    public void handle(Callback[] callbacks)
      throws IOException, UnsupportedCallbackException {
      for (int i = 0; i < callbacks.length; i++) {
        if (callbacks[i] instanceof NameCallback)
          ((NameCallback)callbacks[i]).setName(user);
        else if (callbacks[i] instanceof PasswordCallback)
          ((PasswordCallback)callbacks[i]).setPassword(pass.toCharArray());
        else
          throw new UnsupportedCallbackException(callbacks[i], "unsupported callback - " + callbacks[i].getClass().getName());
      }
    }
  }
}
