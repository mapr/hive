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

package org.apache.hadoop.hive.ql.lockmgr.zookeeper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.hadoop.hive.conf.HiveConf;

public class CuratorFrameworkSingleton {
  private static HiveConf conf = null;
  private static CuratorFramework sharedClient = null;
  static final Log LOG = LogFactory.getLog("CuratorFrameworkSingleton");
  static {
    // Add shutdown hook.
    Runtime.getRuntime().addShutdownHook(new Thread() {
      @Override
      public void run() {
        closeAndReleaseInstance();
      }
    });
  }

  public static synchronized CuratorFramework getInstance(HiveConf hiveConf) {
    if (sharedClient == null) {
      // Create a client instance
      if (hiveConf == null) {
        conf = new HiveConf();
      } else {
        conf = hiveConf;
      }
      int sessionTimeout =  conf.getIntVar(HiveConf.ConfVars.HIVE_ZOOKEEPER_SESSION_TIMEOUT);
      int baseSleepTime = (int) conf.getIntVar(HiveConf.ConfVars.HIVE_ZOOKEEPER_CONNECTION_BASESLEEPTIME);
      int maxRetries = conf.getIntVar(HiveConf.ConfVars.HIVE_ZOOKEEPER_CONNECTION_MAX_RETRIES);
      String quorumServers = ZooKeeperHiveLockManager.getQuorumServers(conf);

      sharedClient = CuratorFrameworkFactory.builder().connectString(quorumServers)
          .sessionTimeoutMs(sessionTimeout)
          .retryPolicy(new ExponentialBackoffRetry(baseSleepTime, maxRetries))
          .build();
      sharedClient.start();
    }

    return sharedClient;
  }

  public static synchronized void closeAndReleaseInstance() {
    if (sharedClient != null) {
      sharedClient.close();
      sharedClient = null;
      String shutdownMsg = "Closing ZooKeeper client.";
      LOG.info(shutdownMsg);
    }
  }
}
