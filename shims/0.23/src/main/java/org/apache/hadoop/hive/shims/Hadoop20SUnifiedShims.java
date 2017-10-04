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
package org.apache.hadoop.hive.shims;

import java.io.IOException;
import java.lang.Integer;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.mapred.JobConf;

/**
 * Implemention of shims against Unified interface of Hadoop 1.0 on YARN Mixed mode.
 */
public class Hadoop20SUnifiedShims extends Hadoop23Shims {

  @Override
  public String getTaskAttemptLogUrl(JobConf conf,
                                     String taskTrackerHttpAddress, String taskAttemptId)
    throws MalformedURLException {
    String taskLogURL = null;
    try {
      Class<?> taskLogClass = Class.forName("org.apache.hadoop.mapred.TaskLogServlet");
      Method taskLogMethod = taskLogClass.getDeclaredMethod("getTaskLogUrl", String.class, String.class, String.class);
      URL taskTrackerHttpURL = new URL(taskTrackerHttpAddress);
      taskLogURL = (String) taskLogMethod.invoke(null, taskTrackerHttpURL.getHost(),
        Integer.toString(taskTrackerHttpURL.getPort()), taskAttemptId);
    } catch (IllegalArgumentException e) {
      LOG.error("Error trying to get task log URL", e);
      throw new MalformedURLException("Could not execute getTaskLogUrl: " + e.getCause());
    } catch (IllegalAccessException e) {
      LOG.error("Error trying to get task log URL", e);
      throw new MalformedURLException("Could not execute getTaskLogUrl: " + e.getCause());
    } catch (InvocationTargetException e) {
      LOG.error("Error trying to get task log URL", e);
      throw new MalformedURLException("Could not execute getTaskLogUrl: " + e.getCause());
    } catch (SecurityException e) {
      LOG.error("Error trying to get task log URL", e);
      throw new MalformedURLException("Could not execute getTaskLogUrl: " + e.getCause());
    } catch (NoSuchMethodException e) {
      LOG.error("Error trying to get task log URL", e);
      throw new MalformedURLException("Method getTaskLogUrl not found: " + e.getCause());
    } catch (ClassNotFoundException e) {
      LOG.warn("Can't fetch tasklog: TaskLogServlet is not found in unified hadoop API.");
    }

    return taskLogURL;
  }

  @Override
  public boolean isLocalMode(Configuration conf) {
    return "local".equals(getJobLauncherRpcAddress(conf));
  }

  @Override
  public String getJobLauncherRpcAddress(Configuration conf) {
    return conf.get("mapred.job.tracker");
  }

  @Override
  public void setJobLauncherRpcAddress(Configuration conf, String val) {
    conf.set("mapred.job.tracker", val);
  }

  @Override
  public String getJobLauncherHttpAddress(Configuration conf) {
    return conf.get("mapred.job.tracker.http.address");
  }



  private volatile HCatHadoopShims hcatShimInstance;
  @Override
  public HCatHadoopShims getHCatShim() {
    if(hcatShimInstance == null) {
      hcatShimInstance = new HCatHadoopShims20SUnified();
    }
    return hcatShimInstance;
  }
  private final class HCatHadoopShims20SUnified extends HCatHadoopShims23 {
    @Override
    public String getPropertyName(PropertyName name) {
      switch (name) {
        case CACHE_ARCHIVES:
          return "mapred.cache.archives";
        case CACHE_FILES:
          return "mapred.cache.archives";
        case CACHE_SYMLINK:
          return "mapred.create.symlink";
      }

      return "";
    }
  }
}