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

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Base64;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.security.UserGroupInformation;
import org.apache.hadoop.security.scram.CredentialCache;
import org.apache.hadoop.security.scram.ScramCredential;
import org.apache.hadoop.security.scram.ScramFormatter;
import org.apache.hadoop.security.scram.ScramMechanism;
import org.apache.hadoop.security.token.Token;
import org.apache.hadoop.security.token.delegation.AbstractDelegationTokenSecretManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.apache.hive.FipsUtil.getScramMechanismName;
import static org.apache.hive.FipsUtil.isScram;

/**
 * A Hive specific delegation token secret manager.
 * The secret manager is responsible for generating and accepting the password
 * for each token.
 */
public class DelegationTokenSecretManager
    extends AbstractDelegationTokenSecretManager<DelegationTokenIdentifier> {
  private static final Logger LOG = LoggerFactory.getLogger(DelegationTokenSecretManager.class);
  private static final boolean isScram = isScram();

  private CredentialCache.Cache<ScramCredential> scramCredentialCache;

  /**
   * Create a secret manager
   * @param delegationKeyUpdateInterval the number of seconds for rolling new
   *        secret keys.
   * @param delegationTokenMaxLifetime the maximum lifetime of the delegation
   *        tokens
   * @param delegationTokenRenewInterval how often the tokens must be renewed
   * @param delegationTokenRemoverScanInterval how often the tokens are scanned
   *        for expired tokens
   */
  public DelegationTokenSecretManager(long delegationKeyUpdateInterval,
                                      long delegationTokenMaxLifetime,
                                      long delegationTokenRenewInterval,
                                      long delegationTokenRemoverScanInterval) {
    super(delegationKeyUpdateInterval, delegationTokenMaxLifetime,
          delegationTokenRenewInterval, delegationTokenRemoverScanInterval);
     if(isScram) {
       CredentialCache credentialCache = new CredentialCache();
       String mechanismName = getScramMechanismName();
       credentialCache.createCache(mechanismName, ScramCredential.class);
       this.scramCredentialCache = credentialCache.cache(mechanismName, ScramCredential.class);
     }
  }

  @Override
  public DelegationTokenIdentifier createIdentifier() {
    return new DelegationTokenIdentifier();
  }

  /**
   * Verify token string
   * @param tokenStrForm
   * @return user name
   * @throws IOException
   */
  public synchronized String verifyDelegationToken(String tokenStrForm) throws IOException {
    Token<DelegationTokenIdentifier> t = new Token<DelegationTokenIdentifier>();
    t.decodeFromUrlString(tokenStrForm);

    DelegationTokenIdentifier id = getTokenIdentifier(t);
    verifyToken(id, t.getPassword());
    return id.getUser().getShortUserName();
  }

  protected DelegationTokenIdentifier getTokenIdentifier(Token<DelegationTokenIdentifier> token)
      throws IOException {
    // turn bytes back into identifier for cache lookup
    ByteArrayInputStream buf = new ByteArrayInputStream(token.getIdentifier());
    DataInputStream in = new DataInputStream(buf);
    DelegationTokenIdentifier id = createIdentifier();
    id.readFields(in);
    return id;
  }

  public synchronized void cancelDelegationToken(String tokenStrForm) throws IOException {
    Token<DelegationTokenIdentifier> t= new Token<DelegationTokenIdentifier>();
    t.decodeFromUrlString(tokenStrForm);
    String user = UserGroupInformation.getCurrentUser().getUserName();
    cancelToken(t, user);
    if (isScram) {
      removeUserFromScramCache(user);
    }
  }

  public synchronized long renewDelegationToken(String tokenStrForm) throws IOException {
    Token<DelegationTokenIdentifier> t= new Token<DelegationTokenIdentifier>();
    t.decodeFromUrlString(tokenStrForm);
    String user = UserGroupInformation.getCurrentUser().getUserName();
    if (isScram) {
      addUserToScramCache(user, new String(encodePassword(t.getPassword())));
    }
    return renewToken(t, user);
  }

  public synchronized String getDelegationToken(String renewer) throws IOException {
    UserGroupInformation ugi = UserGroupInformation.getCurrentUser();
    Text owner = new Text(ugi.getUserName());
    Text realUser = null;
    if (ugi.getRealUser() != null) {
      realUser = new Text(ugi.getRealUser().getUserName());
    }
    DelegationTokenIdentifier ident =
      new DelegationTokenIdentifier(owner, new Text(renewer), realUser);
    Token<DelegationTokenIdentifier> t = new Token<DelegationTokenIdentifier>(
        ident, this);
    if (isScram) {
      addUserToScramCache(owner.toString(), new String(encodePassword(t.getPassword())));
    }
    return t.encodeToUrlString();
  }

  public CredentialCache.Cache<ScramCredential> getScramCredentialCache() {
    return scramCredentialCache;
  }

  private void addUserToScramCache(String userName, String password){
    try {
      ScramFormatter formatter = new ScramFormatter(ScramMechanism.SCRAM_SHA_256);
      ScramCredential generatedCred = formatter.generateCredential(password, 4096);
      scramCredentialCache.put(userName, generatedCred);
    } catch (NoSuchAlgorithmException e) {
      LOG.error(e.toString());
    }
  }

  private void removeUserFromScramCache(String userName) {
    scramCredentialCache.remove(userName);
  }

  private static char[] encodePassword(byte[] password) {
    return new String(Base64.encodeBase64(password)).toCharArray();
  }

  public String getUserFromToken(String tokenStr) throws IOException {
    Token<DelegationTokenIdentifier> delegationToken = new Token<DelegationTokenIdentifier>();
    delegationToken.decodeFromUrlString(tokenStr);

    ByteArrayInputStream buf = new ByteArrayInputStream(delegationToken.getIdentifier());
    DataInputStream in = new DataInputStream(buf);
    DelegationTokenIdentifier id = createIdentifier();
    id.readFields(in);
    return id.getUser().getShortUserName();
  }
}

