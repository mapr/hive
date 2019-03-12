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
package org.apache.hadoop.hive.ql.session;

import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hive.conf.HiveConf;
import org.apache.hadoop.hive.ql.testutil.FileTestUtil;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobID;
import org.apache.hadoop.mapred.RunningJob;
import org.apache.hadoop.yarn.api.records.ApplicationId;
import org.apache.hadoop.yarn.api.records.ApplicationReport;
import org.apache.hadoop.yarn.api.records.YarnApplicationState;
import org.apache.hadoop.yarn.client.api.YarnClient;
import org.apache.hadoop.yarn.exceptions.YarnException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class) public class TestClearScratchDirUtil {
  private FileSystem fs;
  @Mock private YarnClient yarnClient;
  @Mock private ApplicationReport applicationReport;
  @Mock private JobClient jobClient;
  @Mock private RunningJob runningJob;

  @Before public void setup() throws IOException {
    HiveConf conf = new HiveConf();
    conf.set("fs.default.name", "file:///");
    fs = FileSystem.get(conf);
  }

  @Test public void parseTest() {
    String appIdAsString = "application_1551963414414_0047";
    long clusterTimeStamp = 1551963414414L;
    int id = 47;
    ApplicationId actualAppId = ClearScratchDirUtil.parseFrom(appIdAsString);
    ApplicationId expectedAppId = ApplicationId.newInstance(clusterTimeStamp, id);
    Assert.assertEquals(expectedAppId, actualAppId);
  }

  @Test public void hasActiveJobsTestTrue() throws IOException {
    Path activeJobsPath = new Path(FileTestUtil.getPathFromResources("cleaning/active_jobs"));
    JobID jobID_0 = JobID.forName("job_1551963414414_0004");
    JobID jobID_1 = JobID.forName("job_1551963414418_0005");
    when(runningJob.isComplete()).thenReturn(false);
    when(jobClient.getJob(jobID_0)).thenReturn(runningJob);
    when(jobClient.getJob(jobID_1)).thenReturn(runningJob);
    Assert.assertTrue(ClearScratchDirUtil.hasActiveJobs(fs, activeJobsPath, jobClient));
  }

  @Test public void hasActiveJobsTestFalse() throws IOException {
    Path activeJobsPath = new Path(FileTestUtil.getPathFromResources("cleaning/active_jobs"));
    JobID jobID_0 = JobID.forName("job_1551963414414_0004");
    JobID jobID_1 = JobID.forName("job_1551963414418_0005");
    when(runningJob.isComplete()).thenReturn(true);
    when(jobClient.getJob(jobID_0)).thenReturn(runningJob);
    when(jobClient.getJob(jobID_1)).thenReturn(runningJob);
    Assert.assertFalse(ClearScratchDirUtil.hasActiveJobs(fs, activeJobsPath, jobClient));
  }

  @Test public void hasActiveAppsTestTrue() throws IOException, YarnException {
    Path activeAppsPath = new Path(FileTestUtil.getPathFromResources("cleaning/active_apps"));
    ApplicationId applicationId_0 = ClearScratchDirUtil.parseFrom("application_1551963414414_0046");
    ApplicationId applicationId_1 = ClearScratchDirUtil.parseFrom("application_1551963414428_0056");
    when(applicationReport.getYarnApplicationState()).thenReturn(YarnApplicationState.RUNNING);
    when(yarnClient.getApplicationReport(applicationId_0)).thenReturn(applicationReport);
    when(yarnClient.getApplicationReport(applicationId_1)).thenReturn(applicationReport);
    Assert.assertTrue(ClearScratchDirUtil.hasActiveApps(fs, activeAppsPath, yarnClient));
  }

  @Test public void hasActiveAppsTestFalse() throws IOException, YarnException {
    Path activeAppsPath = new Path(FileTestUtil.getPathFromResources("cleaning/active_apps"));
    ApplicationId applicationId_0 = ClearScratchDirUtil.parseFrom("application_1551963414414_0046");
    ApplicationId applicationId_1 = ClearScratchDirUtil.parseFrom("application_1551963414428_0056");
    when(applicationReport.getYarnApplicationState()).thenReturn(YarnApplicationState.FINISHED);
    when(yarnClient.getApplicationReport(applicationId_0)).thenReturn(applicationReport);
    when(yarnClient.getApplicationReport(applicationId_1)).thenReturn(applicationReport);
    Assert.assertFalse(ClearScratchDirUtil.hasActiveApps(fs, activeAppsPath, yarnClient));
  }
}
