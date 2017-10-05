package org.apache.hive.maprminicluster;

import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.hive.shims.HadoopShims;

import java.io.IOException;

public class MapRMiniDFSShim implements HadoopShims.MiniDFSShim {
  private MapRMiniDFSCluster cluster;

  public MapRMiniDFSShim(MapRMiniDFSCluster cluster){
    this.cluster = cluster;
  }

  @Override
  public FileSystem getFileSystem() throws IOException {
    return cluster.getFileSystem();
  }

  @Override
  public void shutdown() throws IOException {
    cluster.shutdown();
  }
}