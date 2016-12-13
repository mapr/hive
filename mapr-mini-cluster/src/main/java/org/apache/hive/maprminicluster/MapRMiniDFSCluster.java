package org.apache.hive.maprminicluster;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.File;
import java.io.IOException;

public class MapRMiniDFSCluster {
  private Configuration conf;
  private FileSystem fs;

  private Path workDir = new Path(System.getProperty("test.tmp.dir",
          "target" + File.separator + "test" + File.separator + "tmp"));

  public MapRMiniDFSCluster() throws IOException {
    conf = new Configuration();
    conf.set("fs.default.name", "file:///");
    fs = FileSystem.getLocal(conf);
    fs.setWorkingDirectory(workDir);
  }

  public FileSystem getFileSystem() throws IOException {
    return fs;
  }

  public void shutdown() {

  }
}
