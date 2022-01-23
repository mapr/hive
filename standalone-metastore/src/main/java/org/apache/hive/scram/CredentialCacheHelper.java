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
package org.apache.hive.scram;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.security.scram.CredentialCache;
import org.apache.hadoop.security.scram.ScramCredential;
import org.apache.hadoop.security.scram.ScramFormatter;
import org.apache.hadoop.security.scram.ScramMechanism;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import static org.apache.hive.scram.ScramUtil.getScramMechanismName;

/**
 * Creates SCRAM-SHA-256 credential cache.
 */
public final class CredentialCacheHelper {
  private static final Log LOG = LogFactory.getLog(CredentialCacheHelper.class);
  private static final char[] PASSWORD = readPassword();
  private static final ScramFormatter SCRAM_FORMATTER = createScramFormatter();
  private static final ScramCredential SCRAM_CREDENTIAL =
      SCRAM_FORMATTER.generateCredential(new String(getScramPassword()), 4096);
  private static final CredentialCache.Cache<ScramCredential> SCRAM_CACHE = createScramCache();

  private CredentialCacheHelper() {
  }

  /**
   * Returns credentials cache for SCRAM-SHA-256.
   *
   * @return credentials cache for SCRAM-SHA-256
   */
  public static CredentialCache.Cache<ScramCredential> getScramCache() {
    return SCRAM_CACHE;
  }

  /**
   * Gets password that was read before from scram/scram-site.xml.
   *
   * @return password that was read before from scram/scram-site.xml
   */
  public static char[] getScramPassword() {
    return PASSWORD;
  }

  /**
   * Reads password from scram/scram-site.xml.
   *
   * @return password from scram/scram-site.xml
   */
  private static char[] readPassword() {
    try {
      Configuration conf = new Configuration();
      conf.addResource("scram/scram-site.xml");
      return conf.getPassword("scram.password");
    } catch (IOException e) {
      LOG.error("Error reading scram.password property");
      throw new ScramPasswordException(e);
    }
  }

  /**
   * Creates SCRAM-SHA-256 cache.
   *
   * @return SCRAM-SHA-256 cache.
   */
  private static CredentialCache.Cache<ScramCredential> createScramCache() {
    CredentialCache credentialCache = new CredentialCache();
    credentialCache.createCache(getScramMechanismName(), ScramCredential.class);
    CredentialCache.Cache<ScramCredential> sha256Cache =
        credentialCache.cache(getScramMechanismName(), ScramCredential.class);
    return sha256Cache;
  }

  /**
   * Adds user to SCARM credentials cache. Use the same password for all users.
   *
   * @param userName name of the user.
   */

  public static void addUserToScramCache(String userName) {
    getScramCache().put(userName, SCRAM_CREDENTIAL);
  }

  /**
   * Removes a user from SCARM credentials cache.
   *
   * @param userName name of the user.
   */
  public static void removeUserFromScramCache(String userName) {
    getScramCache().remove(userName);
  }

  /**
   * Creates SCRAM formatter for credentials.
   *
   * @return SCRAM formatter for credentials
   */
  private static ScramFormatter createScramFormatter() {
    try {
      return new ScramFormatter(ScramMechanism.SCRAM_SHA_256);
    } catch (NoSuchAlgorithmException e) {
      LOG.error(String.format("Can't find %s algorithm.", getScramMechanismName()));
      throw new NoScramAlgorithmException(e);
    }
  }
}
