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
package org.apache.hive;

import org.apache.hadoop.security.scram.ScramAuthMethod;

import java.security.Provider;
import java.security.Security;

/**
 * Utility class for FIPS related operations.
 */
public final class FipsUtil {
  private FipsUtil() {
  }

  /**
   * Checks if cluster supports and configured for FIPS
   *
   * @return true if cluster supports and configured for FIPS
   */
  public static boolean isFips() {
    Provider[] providers = Security.getProviders();
    for (Provider provider : providers) {
      if (provider.getName().toLowerCase().contains("fips"))
        return true;
    }
    return false;
  }

  /**
   * Returns default name for Scram mechanism. Direct usage of
   *
   *    ScramAuthMethod.INSTANCE.getMechanismName()
   *
   * on non FIPS cluster and mapr-core 6.x leads to Thrift error because
   * ScramAuthMethod.INSTANCE is not available on non FIPS cluster and mapr-core 6.x.
   *
   * @return default name for Scram mechanism.
   */
  public static String getScramMechanismName() {
    if (isFips()) {
      return ScramAuthMethod.INSTANCE.getMechanismName();
    }
    return "SCRAM-SHA-256"; // We use string value for non FIPS clusters and old mapr-cores
  }
}
