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
package org.apache.hive.scram;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.security.scram.ScramAuthMethod;

/**
 * Utility class for work with SCRAM SASL.
 */
public final class ScramUtil {
  private static final Log LOG = LogFactory.getLog(ScramUtil.class);

  private ScramUtil() {
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
    if (isScramSupported()) {
      return ScramAuthMethod.INSTANCE.getMechanismName();
    }
    LOG.info("SCRAM is not supported.");
    return "SCRAM-SHA-256"; // We use string value for old mapr-cores
  }

  /**
   * Checks if ScramAuthMethod is available in classpath.
   * Hive can be installed on old mapr cores where SCRAM is not supported
   *
   * @return true of SCRAM supported.
   */
  public static boolean isScramSupported() {
    try {
      Class.forName("org.apache.hadoop.security.scram.ScramAuthMethod", false, ScramUtil.class.getClassLoader());
      return true;
    } catch (ClassNotFoundException e) {
      LOG.info("Class org.apache.hadoop.security.scram.ScramAuthMethod is not in the class path");
      return false;
    }
  }

  /**
   * Checks if Hadoop configured for SCRAM-SHA-256 in token authentication.
   *
   * @return true if Hadoop uses SCRAM-SHA-256
   */
  public static boolean isHadoopConfiguredForScram() {
    Configuration configuration = new Configuration();
    configuration.addResource("yarn-site.xml");
    String hadoopTokenAuth = configuration.get("hadoop.security.token.authentication.method");
    if (hadoopTokenAuth != null && !hadoopTokenAuth.isBlank()) {
      return hadoopTokenAuth.toUpperCase().contains("SCRAM");
    }
    return false;
  }
}
