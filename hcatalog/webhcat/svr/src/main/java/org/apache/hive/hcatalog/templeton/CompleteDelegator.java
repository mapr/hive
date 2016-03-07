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
package org.apache.hive.hcatalog.templeton;

import java.io.IOException;
import java.net.URL;
import java.security.PrivilegedExceptionAction;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.hadoop.hive.common.classification.InterfaceAudience;
import org.apache.hadoop.hive.conf.HiveConf;
import org.apache.hadoop.hive.metastore.HiveMetaStoreClient;
import org.apache.hadoop.hive.metastore.IMetaStoreClient;
import org.apache.hadoop.security.UserGroupInformation;
import org.apache.hive.hcatalog.common.HCatUtil;
import org.apache.hive.hcatalog.templeton.tool.DelegationTokenCache;
import org.apache.hive.hcatalog.templeton.tool.JobState;
import org.apache.hive.hcatalog.templeton.tool.TempletonUtils;

/**
 * Complete a job.  This will run the callback if
 *
 * - the job is done
 * - there is a callback
 * - the callback has not yet been called
 *
 * There is a small chance for a race condition if two callers run
 * this at the same time.  That should never happen.
 *
 * We use a Hadoop config var to notify this class on the completion
 * of a job.  Hadoop will call us multiple times in the event of
 * failure.  Even if the failure is that the client callback failed.
 *
 * See LauncherDelegator for the HADOOP_END_RETRY* vars that are set.
 */
@InterfaceAudience.Private
public class CompleteDelegator extends TempletonDelegator {
  private static final Logger LOG = LoggerFactory.getLogger(CompleteDelegator.class);

  public CompleteDelegator(AppConfig appConf) {
    super(appConf);
  }

  public CompleteBean run(String user, final String id, final String jobStatus)
    throws CallbackFailedException, IOException, InterruptedException {
    if (id == null)
      acceptWithError("No jobid given");

    if (user == null)
      acceptWithError("No user id given");

    UserGroupInformation ugi = UgiFactory.getUgi(user);
    return ugi.doAs(new PrivilegedExceptionAction<CompleteBean>() {
      public CompleteBean run() throws Exception {
        JobState state = null;
        boolean cancelMetastoreToken = false;
        try {
          state = new JobState(id, Main.getAppConfigInstance());
          if (state.getCompleteStatus() == null) {
            failed("Job not yet complete. jobId=" + id + " Status from JobTracker=" + jobStatus, null);
          }
          Long notified = state.getNotifiedTime();
          if (notified != null) {
            cancelMetastoreToken = true;
            return acceptWithError("Callback already run for jobId=" + id +
              " at " + new Date(notified));
          }

          String callback = state.getCallback();
          if (callback == null) {
            cancelMetastoreToken = true;
            return new CompleteBean("No callback registered");
          }
          try {
            doCallback(state.getId(), callback);
            cancelMetastoreToken = true;
          } catch (Exception e) {
            failed("Callback failed " + callback + " for " + id, e);
          }
          state.setNotifiedTime(System.currentTimeMillis());
          return new CompleteBean("Callback sent");
        } catch( CallbackFailedException ex) {
          throw new Exception(ex);
        } finally {
          state.close();
          HiveMetaStoreClient client = null;
          try {
            if(cancelMetastoreToken) {
              String metastoreTokenStrForm =
                DelegationTokenCache.getStringFormTokenCache().getDelegationToken(id);
                if(metastoreTokenStrForm != null) {
                  client = HCatUtil.getHiveClient(new HiveConf());
                  client.cancelDelegationToken(metastoreTokenStrForm);
                  LOG.debug("Cancelled token for jobId=" + id + " status from JT=" + jobStatus);
                  DelegationTokenCache.getStringFormTokenCache().removeDelegationToken(id);
                }
            }
          } catch(Exception ex) {
            LOG.warn("Failed to cancel metastore delegation token for jobId=" + id, ex);
          } finally {
            HCatUtil.closeHiveClientQuietly(client);
          }
        }
      }
    });
  }

  /**
   * Call the callback url with the jobid to let them know it's
   * finished.  If the url has the string $jobId in it, it will be
   * replaced with the completed jobid.
   */
  public static void doCallback(String jobid, String url) throws IOException {
    if (url.contains("$jobId"))
      url = url.replace("$jobId", jobid);
    TempletonUtils.fetchUrl(new URL(url));
  }

  private static void failed(String msg, Exception e)
    throws CallbackFailedException {
    if (e != null)
      LOG.error(msg, e);
    else
      LOG.error(msg);
    throw new CallbackFailedException(msg);
  }

  private static CompleteBean acceptWithError(String msg) {
    LOG.error(msg);
    return new CompleteBean(msg);
  }
}
