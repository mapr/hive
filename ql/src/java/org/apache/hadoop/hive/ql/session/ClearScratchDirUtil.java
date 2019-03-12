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

import com.google.common.annotations.VisibleForTesting;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.LocatedFileStatus;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.RemoteIterator;
import org.apache.hadoop.hive.conf.HiveConf;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobID;
import org.apache.hadoop.mapred.RunningJob;
import org.apache.hadoop.yarn.api.records.ApplicationId;
import org.apache.hadoop.yarn.api.records.ApplicationReport;
import org.apache.hadoop.yarn.api.records.YarnApplicationState;
import org.apache.hadoop.yarn.client.api.YarnClient;
import org.apache.hadoop.yarn.exceptions.YarnException;

import java.io.IOException;
import java.util.Arrays;

import static org.apache.hadoop.yarn.api.records.YarnApplicationState.FAILED;
import static org.apache.hadoop.yarn.api.records.YarnApplicationState.FINISHED;
import static org.apache.hadoop.yarn.api.records.YarnApplicationState.KILLED;

/**
 * Helper class for clear dangling scratch dir utility.
 */
final class ClearScratchDirUtil {
  private ClearScratchDirUtil() {
  }

  /**
   * Returns true if there is at least one active job that uses scratch dir.
   *
   * @param fs file system for scratch dir.
   * @param activeJobsPath path to list of active job ids.
   * @return true when there is at least one active job and false otherwise.
   * @throws IOException
   */
  static boolean hasActiveJobs(FileSystem fs, Path activeJobsPath, HiveConf conf) throws IOException {
    JobClient jobClient = new JobClient(conf);
    try {
      return hasActiveJobs(fs, activeJobsPath, jobClient);
    } finally {
      jobClient.close();
    }
  }

  @VisibleForTesting static boolean hasActiveJobs(FileSystem fs, Path activeJobsPath, JobClient jobClient)
      throws IOException {
    if (fs.exists(activeJobsPath)) {
      RemoteIterator<LocatedFileStatus> activeJobs = fs.listFiles(activeJobsPath, false);
      while (activeJobs.hasNext()) {
        RunningJob runningJob = jobClient.getJob(JobID.forName(activeJobs.next().getPath().getName()));
        if (runningJob != null && !runningJob.isComplete()) {
          // While there is at least one job that is not completed, we do not remove session dir.
          return true;
        }
      }
    }
    return false;
  }

  /**
   * Returns true if there is at least one active YARN application that uses scratch dir.
   *
   * @param fs file system for scratch dir.
   * @param activeAppsPath path to list of active applications ids.
   * @return true when there is at least one active YARN application and false otherwise.
   * @throws IOException
   * @throws YarnException
   */
  static boolean hasActiveApps(FileSystem fs, Path activeAppsPath, HiveConf conf) throws IOException, YarnException {
    try (YarnClient yarnClient = YarnClient.createYarnClient()) {
      yarnClient.init(conf);
      yarnClient.start();
      return hasActiveApps(fs, activeAppsPath, yarnClient);
    }
  }

  @VisibleForTesting static boolean hasActiveApps(FileSystem fs, Path activeAppsPath, YarnClient yarnClient)
      throws IOException, YarnException {
    if (fs.exists(activeAppsPath)) {
      RemoteIterator<LocatedFileStatus> activeApps = fs.listFiles(activeAppsPath, false);
      while (activeApps.hasNext()) {
        ApplicationId applicationId = parseFrom(activeApps.next().getPath().getName());
        ApplicationReport applicationReport = yarnClient.getApplicationReport(applicationId);
        YarnApplicationState state = applicationReport.getYarnApplicationState();
        YarnApplicationState[] done = { FINISHED, FAILED, KILLED };
        if (!Arrays.asList(done).contains(state)) {
          // While there is at least one YARN app that is not completed, we do not remove session dir.
          return true;
        }
      }
    }
    return false;
  }

  /**
   * Parses ApplicationId from string like application_XXXXXXXXXXXXX_YYYY.
   * Here XXXXXXXXXXXXX is timestamp as long.
   *      YYYY is task id.
   *
   * @param appIdAsString string representation of ApplicationId.
   * @return instance of ApplicationId.
   */
  static ApplicationId parseFrom(String appIdAsString) {
    String[] rawData = appIdAsString.split("\\_");
    long clusterTimestamp = Long.parseLong(rawData[1]);
    int id = Integer.parseInt(rawData[2]);
    return ApplicationId.newInstance(clusterTimestamp, id);
  }
}
