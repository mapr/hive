package org.apache.hive.maprminicluster;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseCluster;
import org.apache.hadoop.hbase.HBaseCommonTestingUtility;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.MiniHBaseCluster;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.master.HMaster;
import org.apache.hadoop.hbase.util.FSUtils;
import org.apache.hadoop.hbase.zookeeper.MiniZooKeeperCluster;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class MapRHBaseTestingUtility extends HBaseCommonTestingUtility {

  private MapRHBaseTestingUtility.MapRHBaseAdminForTests hbaseAdmin;
  private volatile Connection connection;
  protected Configuration conf;
  protected static final Log LOG = LogFactory.getLog(MapRHBaseTestingUtility.class);
  private MapRMiniDFSCluster dfsCluster;
  private File clusterTestDir;
  private File dataTestDir;
  private String hadoopLogDir;
  private Path dataTestDirOnTestFS;
  private volatile boolean miniClusterRunning;
  private volatile HBaseCluster hbaseCluster;
  private MiniZooKeeperCluster zkCluster;
  private boolean passedZkCluster;
  private Path workDir = new Path(System.getProperty("test.tmp.dir",
    "target" + File.separator + "test" + File.separator + "tmp"));

  public MapRHBaseTestingUtility(Configuration configuration){
    this.conf = configuration;
  }

  public MiniHBaseCluster startMiniCluster() throws Exception {
    return this.startMiniCluster(1, 1);
  }

  public MiniHBaseCluster startMiniCluster(int numMasters, int numSlaves) throws Exception {
    return this.startMiniCluster(numMasters, numSlaves, (String[])null, false);
  }

  public MiniHBaseCluster startMiniCluster(int numMasters, int numSlaves, String[] dataNodeHosts, boolean create) throws Exception {
    return this.startMiniCluster(numMasters, numSlaves, numSlaves, dataNodeHosts, (Class)null, (Class)null, create);
  }

  public MiniHBaseCluster startMiniCluster(int numMasters, int numSlaves, int numDataNodes, String[] dataNodeHosts, Class<? extends HMaster> masterClass, Class<? extends MiniHBaseCluster.MiniHBaseClusterRegionServer> regionserverClass, boolean create) throws Exception {
    if(dataNodeHosts != null && dataNodeHosts.length != 0) {
      numDataNodes = dataNodeHosts.length;
    }

    LOG.info("Starting up minicluster with " + numMasters + " master(s) and " + numSlaves + " regionserver(s) and " + numDataNodes + " datanode(s)");
    if(this.miniClusterRunning) {
      throw new IllegalStateException("A mini-cluster is already running");
    } else {
      this.miniClusterRunning = true;
      this.setupClusterTestDir();
      System.setProperty("test.build.data", this.clusterTestDir.getPath());
      if(this.dfsCluster == null) {
        this.dfsCluster = this.startMiniDFSCluster();
      }

      if(this.zkCluster == null) {
        this.startMiniZKCluster(this.clusterTestDir);
      }

      return this.startMiniHBaseCluster(numMasters, numSlaves, masterClass, regionserverClass, create);
    }
  }


  private MiniZooKeeperCluster startMiniZKCluster(File dir) throws Exception {
    return this.startMiniZKCluster(dir, 1, (int[])null);
  }


  private MiniZooKeeperCluster startMiniZKCluster(File dir, int zooKeeperServerNum, int[] clientPortList) throws Exception {
    if(this.zkCluster != null) {
      throw new IOException("Cluster already running at " + dir);
    } else {
      this.passedZkCluster = false;
      this.zkCluster = new MiniZooKeeperCluster(this.getConfiguration());
      int defPort = this.conf.getInt("test.hbase.zookeeper.property.clientPort", 0);
      if(defPort > 0) {
        this.zkCluster.setDefaultClientPort(defPort);
      }

      int clientPort;
      if(clientPortList != null) {
        clientPort = clientPortList.length <= zooKeeperServerNum?clientPortList.length:zooKeeperServerNum;

        for(int i = 0; i < clientPort; ++i) {
          this.zkCluster.addClientPort(clientPortList[i]);
        }
      }

      clientPort = this.zkCluster.startup(dir, zooKeeperServerNum);
      this.conf.set("hbase.zookeeper.property.clientPort", Integer.toString(clientPort));
      return this.zkCluster;
    }
  }

  public synchronized HBaseAdmin getHBaseAdmin() throws IOException {
    if(this.hbaseAdmin == null) {
      this.hbaseAdmin = new MapRHBaseTestingUtility.MapRHBaseAdminForTests(this.getConnection());
    }

    return this.hbaseAdmin;
  }


  private static class MapRHBaseAdminForTests extends HBaseAdmin {
    public MapRHBaseAdminForTests(Connection connection) throws MasterNotRunningException, ZooKeeperConnectionException, IOException {
      super(connection);

    }

    public synchronized void close() throws IOException {
      MapRHBaseTestingUtility.LOG.warn("close() called on HBaseAdmin instance returned from HBaseTestingUtility.getHBaseAdmin()");
    }

    private synchronized void close0() throws IOException {
      super.close();
    }
  }

  public Connection getConnection() throws IOException {
    if(this.connection == null) {
      this.connection = ConnectionFactory.createConnection(this.conf);
    }

    return this.connection;
  }


  public MapRMiniDFSCluster startMiniDFSCluster() throws Exception {
    this.createDirsAndSetProperties();
    this.dfsCluster = new MapRMiniDFSCluster(this.conf);
    this.dfsCluster.waitClusterUp();
    return this.dfsCluster;
  }


  private void createDirsAndSetProperties() throws IOException {
    this.setupClusterTestDir();
    System.setProperty("test.build.data", this.clusterTestDir.getPath());
    this.createDirAndSetProperty("cache_data", "test.cache.data");
    this.createDirAndSetProperty("hadoop_tmp", "hadoop.tmp.dir");
    this.hadoopLogDir = this.createDirAndSetProperty("hadoop_logs", "hadoop.log.dir");
    this.createDirAndSetProperty("mapred_local", "mapreduce.cluster.local.dir");
    this.createDirAndSetProperty("mapred_temp", "mapreduce.cluster.temp.dir");
    this.enableShortCircuit();
    Path root = this.getDataTestDirOnTestFS("hadoop");
    this.conf.set("mapreduce.output.fileoutputformat.outputdir", (new Path(root, "mapred-output-dir")).toString());
    this.conf.set("mapreduce.jobtracker.system.dir", (new Path(root, "mapred-system-dir")).toString());
    this.conf.set("mapreduce.jobtracker.staging.root.dir", (new Path(root, "mapreduce-jobtracker-staging-root-dir")).toString());
    this.conf.set("mapreduce.job.working.dir", (new Path(root, "mapred-working-dir")).toString());
  }


  private void setupClusterTestDir() {
    if(this.clusterTestDir == null) {
      Path testDir = this.getDataTestDir("dfscluster_" + UUID.randomUUID().toString());
      this.clusterTestDir = (new File(testDir.toString())).getAbsoluteFile();
      boolean b = this.deleteOnExit();
      if(b) {
        this.clusterTestDir.deleteOnExit();
      }

      this.conf.set("test.build.data", this.clusterTestDir.getPath());
      LOG.info("Created new mini-cluster data directory: " + this.clusterTestDir + ", deleteOnExit=" + b);
    }
  }

  private String createDirAndSetProperty(String relPath, String property) {
    String path = this.getDataTestDir(relPath).toString();
    System.setProperty(property, path);
    this.conf.set(property, path);
    (new File(path)).mkdirs();
    LOG.info("Setting " + property + " to " + path + " in system properties and HBase conf");
    return path;
  }

  public Path getDataTestDir(String subdirName) {
    return new Path(this.getDataTestDir(), subdirName);
  }

  public Path getDataTestDir() {
    if(this.dataTestDir == null) {
      this.setupDataTestDir();
    }

    return new Path(this.dataTestDir.getAbsolutePath());
  }

  protected Path setupDataTestDir() {
    if(this.dataTestDir != null) {
      LOG.warn("Data test dir already setup in " + this.dataTestDir.getAbsolutePath());
      return null;
    } else {
      String randomStr = UUID.randomUUID().toString();
      Path testPath = new Path(this.getBaseTestDir(), randomStr);
      this.dataTestDir = (new File(testPath.toString())).getAbsoluteFile();
      System.setProperty("test.build.dir", this.dataTestDir.toString());
      if(this.deleteOnExit()) {
        this.dataTestDir.deleteOnExit();
      }

      this.createSubDir("hbase.local.dir", testPath, "hbase-local-dir");
      return testPath;
    }
  }


  public boolean isReadShortCircuitOn() {
    String propName = "hbase.tests.use.shortcircuit.reads";
    String readOnProp = System.getProperty("hbase.tests.use.shortcircuit.reads");
    return readOnProp != null?Boolean.parseBoolean(readOnProp):this.conf.getBoolean("hbase.tests.use.shortcircuit.reads", false);
  }

  private void enableShortCircuit() {
    if(this.isReadShortCircuitOn()) {
      String curUser = System.getProperty("user.name");
      LOG.info("read short circuit is ON for user " + curUser);
      this.conf.set("dfs.block.local-path-access.user", curUser);
      this.conf.setBoolean("dfs.client.read.shortcircuit", true);
      this.conf.setBoolean("dfs.client.read.shortcircuit.skip.checksum", true);
    } else {
      LOG.info("read short circuit is OFF");
    }

  }


  private Path getNewDataTestDirOnTestFS() throws IOException {
    FileSystem fs = this.getTestFileSystem();
    Path newDataTestDir = null;
    if(fs.getUri().getScheme().equals(FileSystem.getLocal(this.conf).getUri().getScheme())) {
      File base = new File(this.getDataTestDir().toString());
      if(this.deleteOnExit()) {
        base.deleteOnExit();
      }

      newDataTestDir = new Path(base.getAbsolutePath());
    } else {
      Path base1 = this.getBaseTestDirOnTestFS();
      String randomStr = UUID.randomUUID().toString();
      newDataTestDir = new Path(base1, randomStr);
      if(this.deleteOnExit()) {
        fs.deleteOnExit(newDataTestDir);
      }
    }
    return newDataTestDir;
  }

  public FileSystem getTestFileSystem() throws IOException {
    FileSystem fs = FileSystem.getLocal(conf);
    fs.setWorkingDirectory(workDir);
    return fs;
  }


  public Path getDataTestDirOnTestFS() throws IOException {
    if(this.dataTestDirOnTestFS == null) {
      this.setupDataTestDirOnTestFS();
    }

    return this.dataTestDirOnTestFS;
  }

  private void setupDataTestDirOnTestFS() throws IOException {
    if(this.dataTestDirOnTestFS != null) {
      LOG.warn("Data test on test fs dir already setup in " + this.dataTestDirOnTestFS.toString());
    } else {
      this.dataTestDirOnTestFS = this.getNewDataTestDirOnTestFS();
    }
  }

  public Path getDataTestDirOnTestFS(String subdirName) throws IOException {
    return new Path(this.getDataTestDirOnTestFS(), subdirName);
  }

  boolean deleteOnExit() {
    String v = System.getProperty("hbase.testing.preserve.testdir");
    return v == null?true:!Boolean.parseBoolean(v);
  }

  private Path getBaseTestDirOnTestFS() throws IOException {
    FileSystem fs = this.getTestFileSystem();
    return new Path(fs.getWorkingDirectory(), "test-data");
  }

  protected void createSubDir(String propertyName, Path parent, String subDirName) {
    Path newPath = new Path(parent, subDirName);
    File newDir = (new File(newPath.toString())).getAbsoluteFile();
    if(this.deleteOnExit()) {
      newDir.deleteOnExit();
    }

    this.conf.set(propertyName, newDir.getAbsolutePath());
  }

  private Path getBaseTestDir() {
    String PathName = System.getProperty("test.build.data.basedirectory", "target/test-data");
    return new Path(PathName);
  }

  public Configuration getConfiguration() {
    return conf;
  }

  public void shutdownMiniCluster() throws Exception {
    LOG.info("Shutting down minicluster");
    if(this.connection != null && !this.connection.isClosed()) {
      this.connection.close();
      this.connection = null;
    }

    this.shutdownMiniHBaseCluster();
    if(!this.passedZkCluster) {
      this.shutdownMiniZKCluster();
    }

    this.shutdownMiniDFSCluster();
    this.cleanupTestDir();
    this.miniClusterRunning = false;
    LOG.info("Minicluster is down");
  }

  public void shutdownMiniZKCluster() throws IOException {
    if(this.zkCluster != null) {
      this.zkCluster.shutdown();
      this.zkCluster = null;
    }

  }


  public boolean cleanupTestDir() throws IOException {

    if(this.deleteDir(this.clusterTestDir)) {
      this.clusterTestDir = null;
      return true;
    } else {
      return false;
    }
  }

  public void shutdownMiniHBaseCluster() throws IOException {
    if(this.hbaseAdmin != null) {
      this.hbaseAdmin.close0();
      this.hbaseAdmin = null;
    }

    this.conf.setInt("hbase.master.wait.on.regionservers.mintostart", -1);
    this.conf.setInt("hbase.master.wait.on.regionservers.maxtostart", -1);
    if(this.hbaseCluster != null) {
      this.hbaseCluster.shutdown();
      this.hbaseCluster.waitUntilShutDown();
      this.hbaseCluster = null;
    }
  }

  public void shutdownMiniDFSCluster() throws IOException {
    if(this.dfsCluster != null) {
      this.dfsCluster.shutdown();
      this.dfsCluster = null;
      this.dataTestDirOnTestFS = null;
      FSUtils.setFsDefault(this.conf, new Path("file:///"));
    }

  }

  public MiniHBaseCluster startMiniHBaseCluster(int numMasters, int numSlaves, Class<? extends HMaster> masterClass, Class<? extends MiniHBaseCluster.MiniHBaseClusterRegionServer> regionserverClass, boolean create) throws IOException, InterruptedException {
    this.createRootDir(create);
    if(this.conf.getInt("hbase.master.wait.on.regionservers.mintostart", -1) == -1) {
      this.conf.setInt("hbase.master.wait.on.regionservers.mintostart", numSlaves);
    }

    if(this.conf.getInt("hbase.master.wait.on.regionservers.maxtostart", -1) == -1) {
      this.conf.setInt("hbase.master.wait.on.regionservers.maxtostart", numSlaves);
    }

    Configuration c = new Configuration(this.conf);
    this.hbaseCluster = new MiniHBaseCluster(c, numMasters, numSlaves, masterClass, regionserverClass);
    HTable t = new HTable(c, TableName.META_TABLE_NAME);
    ResultScanner s = t.getScanner(new Scan());

    while(s.next() != null) {
      ;
    }

    s.close();
    t.close();
    this.getHBaseAdmin();
    LOG.info("Minicluster is up");
    this.setHBaseFsTmpDir();
    return (MiniHBaseCluster)this.hbaseCluster;
  }


  public Path createRootDir(boolean create) throws IOException {
    FileSystem fs = FileSystem.get(this.conf);
    Path hbaseRootdir = this.getDefaultRootDirPath(create);
    FSUtils.setRootDir(this.conf, hbaseRootdir);
    fs.mkdirs(hbaseRootdir);
    FSUtils.setVersion(fs, hbaseRootdir);
    return hbaseRootdir;
  }

  public Path getDefaultRootDirPath(boolean create) throws IOException {
    return !create?this.getDataTestDirOnTestFS():this.getNewDataTestDirOnTestFS();
  }

  private void setHBaseFsTmpDir() throws IOException {
    String hbaseFsTmpDirInString = this.conf.get("hbase.fs.tmp.dir");
    if(hbaseFsTmpDirInString == null) {
      this.conf.set("hbase.fs.tmp.dir", this.getDataTestDirOnTestFS("hbase-staging").toString());
      LOG.info("Setting hbase.fs.tmp.dir to " + this.conf.get("hbase.fs.tmp.dir"));
    } else {
      LOG.info("The hbase.fs.tmp.dir is set to " + hbaseFsTmpDirInString);
    }

  }

  boolean deleteDir(File dir) throws IOException {
    if(dir != null && dir.exists()) {
      int ntries = 0;

      do {
        ++ntries;

        try {
          if(this.deleteOnExit()) {
            FileUtils.deleteDirectory(dir);
          }

          return true;
        } catch (IOException var4) {
          LOG.warn("Failed to delete " + dir.getAbsolutePath());
        } catch (IllegalArgumentException var5) {
          LOG.warn("Failed to delete " + dir.getAbsolutePath(), var5);
        }
      } while(ntries < 30);

      return ntries < 30;
    } else {
      return true;
    }
  }

}