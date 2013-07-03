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
package org.apache.hcatalog.templeton;

import java.lang.reflect.Method;
import java.net.InetSocketAddress;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;

/**
 * The helper class for all the Templeton delegator classes. A
 * delegator will call the underlying Templeton service such as hcat
 * or hive.
 */
public class TempletonDelegator {
    protected AppConfig appConf;
    private static final Log LOG = LogFactory.getLog(TempletonDelegator.class);

    public TempletonDelegator(AppConfig appConf) {
        this.appConf = appConf;
    }
    
    public static InetSocketAddress getAddress(Configuration conf) {
        try {
            FileSystem fs = FileSystem.get(conf);
            Class fscls = Class.forName("org.apache.hadoop.fs.FileSystem");
            Method method = fscls.getDeclaredMethod("getJobTrackerAddrs", new Class[] { Configuration.class });
            InetSocketAddress[] jobTrackerAddr = (InetSocketAddress[]) method.invoke(fs, conf);
            if (jobTrackerAddr == null || jobTrackerAddr[0] == null)
                throw new Exception("JobTracker HA returned is not valid");
            return jobTrackerAddr[0];
        } catch (Exception e) {
            LOG.error("Unable to get JobTracker address: " + e.getMessage());
        }
        return null;
    }
}
