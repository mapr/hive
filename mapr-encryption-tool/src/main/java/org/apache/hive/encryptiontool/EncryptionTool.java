/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.hive.encryptiontool;


import java.io.IOException;

import com.google.common.annotations.VisibleForTesting;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.security.alias.CredentialProvider;
import org.apache.hadoop.security.alias.CredentialProviderFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *  Helper for Encrypting Hive passwords
 */

class EncryptionTool {
  private EncryptionTool() {
  }

  private static final Logger LOG = LoggerFactory.getLogger(EncryptionTool.class.getName());


  static void encryptPassword(String propertyName, String propertyValue, String pathToKeyStore)
      throws IOException {
    encryptPassword(propertyName, propertyValue, pathToKeyStore, false);
  }

  @VisibleForTesting
  static void encryptPassword(String propertyName, String propertyValue, String pathToKeyStore,
      boolean isLocalFs) throws IOException {
    Configuration configuration = new Configuration();
    String keyStorePath;
    if(isLocalFs){
      configuration.set("fs.default.name", "file:///");
      keyStorePath = "jceks://file/" + pathToKeyStore;
    } else{
      keyStorePath = "jceks://maprfs/" + pathToKeyStore;
    }
    configuration.set("hadoop.security.credential.clear-text-fallback", "true");
    configuration.set("hadoop.security.credential.provider.path", keyStorePath);

    LOG.debug("Creating alias " + propertyName + " under " + pathToKeyStore);

    CredentialProvider provider = CredentialProviderFactory.getProviders(configuration).get(0);
    provider.createCredentialEntry(propertyName, propertyValue.toCharArray());
    provider.flush();
  }
}