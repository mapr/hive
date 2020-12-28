package org.apache.hive.maprminicluster;


import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hive.shims.HadoopShims;
import org.apache.hadoop.mapred.JobConf;

import java.io.IOException;
import java.util.Map;

public class MapRMiniMrShim implements HadoopShims.MiniMrShim{

  private final Configuration conf;
  private final MapRMiniDFSCluster mr;

  public MapRMiniMrShim() throws IOException {
    conf = new Configuration();
    conf.set("fs.default.name", "file:///");
    mr = new MapRMiniDFSCluster(conf);
  }

  public MapRMiniMrShim(Configuration conf) throws IOException {
    this.conf = conf;
    JobConf jConf = new JobConf(conf);
    jConf.set("yarn.scheduler.capacity.root.queues", "default");
    jConf.set("yarn.scheduler.capacity.root.default.capacity", "100");
    mr = new MapRMiniDFSCluster(jConf);
  }

  @Override
  public int getJobTrackerPort() throws UnsupportedOperationException {
    String address = conf.get("yarn.resourcemanager.address");
    address = StringUtils.substringAfterLast(address, ":");

    if (StringUtils.isBlank(address)) {
      throw new IllegalArgumentException("Invalid YARN resource manager port.");
    }

    return Integer.parseInt(address);
  }

  @Override
  public void shutdown() throws IOException {
    mr.shutdown();
  }

  @Override
  public void setupConfiguration(Configuration conf) {
    JobConf jConf = mr.createJobConf();
    for (Map.Entry<String, String> pair: jConf) {
      conf.set(pair.getKey(), pair.getValue());
    }
  }
}