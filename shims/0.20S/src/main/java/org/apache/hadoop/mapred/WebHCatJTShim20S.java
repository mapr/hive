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
package org.apache.hadoop.mapred;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.hive.shims.HadoopShims.WebHCatJTShim;
import org.apache.hadoop.ipc.RPC;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.net.NetUtils;
import org.apache.hadoop.security.UserGroupInformation;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.URI;

/**
 * This is in org.apache.hadoop.mapred package because it relies on
 * JobSubmissionProtocol which is package private
 */
public class WebHCatJTShim20S implements WebHCatJTShim {
  private static final Log LOG = LogFactory.getLog(WebHCatJTShim20S.class);
  private JobSubmissionProtocol cnx;

  /**
   * Create a connection to the Job Tracker.
   */
  public WebHCatJTShim20S(Configuration conf, UserGroupInformation ugi)
          throws IOException {
    cnx = (JobSubmissionProtocol)
            RPC.getProxy(JobSubmissionProtocol.class,
                    JobSubmissionProtocol.versionID,
                    getAddress(conf),
                    ugi,
                    conf,
                    NetUtils.getSocketFactory(conf,
                            JobSubmissionProtocol.class));
  }

  /**
   * Grab a handle to a job that is already known to the JobTracker.
   *
   * @return Profile of the job, or null if not found.
   */
  public JobProfile getJobProfile(org.apache.hadoop.mapred.JobID jobid)
          throws IOException {
    return cnx.getJobProfile(jobid);
  }

  /**
   * Grab a handle to a job that is already known to the JobTracker.
   *
   * @return Status of the job, or null if not found.
   */
  public org.apache.hadoop.mapred.JobStatus getJobStatus(org.apache.hadoop.mapred.JobID jobid)
          throws IOException {
    return cnx.getJobStatus(jobid);
  }


  /**
   * Kill a job.
   */
  public void killJob(org.apache.hadoop.mapred.JobID jobid)
          throws IOException {
    cnx.killJob(jobid);
  }

  /**
   * Get all the jobs submitted.
   */
  public org.apache.hadoop.mapred.JobStatus[] getAllJobs()
          throws IOException {
    return cnx.getAllJobs();
  }

  /**
   * Close the connection to the Job Tracker.
   */
  public void close() {
    RPC.stopProxy(cnx);
  }
  private InetSocketAddress getAddress(Configuration conf) {
    try {
	  FileSystem fs = FileSystem.get(conf);
	  Class fscls = Class.forName("org.apache.hadoop.fs.FileSystem");
	  Method method = fscls.getDeclaredMethod("getJobTrackerAddrs", new Class[] { Configuration.class });
	  InetSocketAddress[] jobTrackerAddr = (InetSocketAddress[]) method.invoke(fs, conf);
	  if (jobTrackerAddr == null || jobTrackerAddr[0] == null) {
	    throw new Exception("JobTracker HA returned is not valid");
	  }  
	    return jobTrackerAddr[0];
	} catch (Exception e) {
	  LOG.error("Unable to get JobTracker address: " + e.getMessage());
    }
    return null;
  }
  
  @Override
  public void addCacheFile(URI uri, Job job) {
    DistributedCache.addCacheFile(uri, job.getConfiguration());
  }
}

