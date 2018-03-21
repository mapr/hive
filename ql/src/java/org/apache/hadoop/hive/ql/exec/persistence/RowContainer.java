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

package org.apache.hadoop.hive.ql.exec.persistence;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.hadoop.fs.CommonConfigurationKeysPublic;
import org.apache.hadoop.fs.FileUtil;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.LocalFileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hive.conf.HiveConf;
import org.apache.hadoop.hive.ql.exec.FileSinkOperator.RecordWriter;
import org.apache.hadoop.hive.ql.exec.Utilities;
import org.apache.hadoop.hive.ql.io.HiveFileFormatUtils;
import org.apache.hadoop.mapred.OutputFormat;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.metadata.HiveUtils;
import org.apache.hadoop.hive.ql.plan.TableDesc;
import org.apache.hadoop.hive.serde2.AbstractSerDe;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorUtils;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorUtils.ObjectInspectorCopyOption;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.mapred.InputFormat;
import org.apache.hadoop.mapred.InputSplit;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapred.RecordReader;
import org.apache.hadoop.net.DNS;
import org.apache.hadoop.util.ReflectionUtils;

/**
 * Simple persistent container for rows.
 *
 * This container interface only accepts adding or appending new rows and iterating through the rows
 * in the order of their insertions.
 *
 * The iterator interface is a lightweight first()/next() API rather than the Java Iterator
 * interface. This way we do not need to create an Iterator object every time we want to start a new
 * iteration. Below is simple example of how to convert a typical Java's Iterator code to the LW
 * iterator interface.
 *
 * Iterator itr = rowContainer.iterator(); while (itr.hasNext()) { v = itr.next(); // do anything
 * with v }
 *
 * can be rewritten to:
 *
 * for ( v = rowContainer.first(); v != null; v = rowContainer.next()) { // do anything with v }
 *
 * Once the first is called, it will not be able to write again. So there can not be any writes
 * after read. It can be read multiple times, but it does not support multiple reader interleaving
 * reading.
 *
 */
public class RowContainer<ROW extends List<Object>>
  implements AbstractRowContainer<ROW>, AbstractRowContainer.RowIterator<ROW> {

  protected static final Logger LOG = LoggerFactory.getLogger(RowContainer.class);

  // max # of rows can be put into one block
  private static final int BLOCKSIZE = 25000;

  private ROW[] currentWriteBlock; // the last block that add() should append to
  private ROW[] currentReadBlock; // the current block where the cursor is in
  // since currentReadBlock may assigned to currentWriteBlock, we need to store
  // original read block
  private ROW[] firstReadBlockPointer;
  private int blockSize; // number of objects in the block before it is spilled
  // to disk
  private int numFlushedBlocks; // total # of blocks
  private long size;    // total # of elements in the RowContainer
  private int itrCursor; // iterator cursor in the currBlock
  private int readBlockSize; // size of current read block
  private int addCursor; // append cursor in the lastBlock
  private AbstractSerDe serde; // serialization/deserialization for the row
  private ObjectInspector standardOI; // object inspector for the row

  private List<Object> keyObject;

  private TableDesc tblDesc;
  //spill file
  SpillFile spillFile = null;

  boolean firstCalled = false; // once called first, it will never be able to
  // write again.
  RecordWriter rw = null;
  InputFormat<WritableComparable, Writable> inputFormat = null;
  InputSplit[] inputSplits = null;
  RecordReader rr = null; // record reader
  private ROW dummyRow = null;
  private final Reporter reporter;
  private final String spillFileDirs;


  Writable val = null; // cached to use serialize data

  Configuration jc;

  public RowContainer(Configuration jc, Reporter reporter) throws HiveException {
    this(BLOCKSIZE, jc, reporter);
  }

  public RowContainer(int bs, Configuration jc, Reporter reporter
                     ) throws HiveException {
    // no 0-sized block
    this.blockSize = bs <= 0 ? BLOCKSIZE : bs;
    this.size = 0;
    this.itrCursor = 0;
    this.addCursor = 0;
    this.spillFileDirs = HiveUtils.getLocalDirList(jc);
    this.numFlushedBlocks = 0;
    this.currentWriteBlock = (ROW[]) new ArrayList[blockSize];
    this.currentReadBlock = this.currentWriteBlock;
    this.firstReadBlockPointer = currentReadBlock;
    this.serde = null;
    this.standardOI = null;
    this.jc = jc;
    if (reporter == null) {
      this.reporter = Reporter.NULL;
    } else {
      this.reporter = reporter;
    }
  }

  public void setSerDe(AbstractSerDe sd, ObjectInspector oi) {
    this.serde = sd;
    this.standardOI = oi;
  }

  @Override
  public void addRow(ROW t) throws HiveException {
    if (this.tblDesc != null) {
      if (willSpill()) { // spill the current block to tmp file
        spillBlock(currentWriteBlock, addCursor);
        addCursor = 0;
        if (numFlushedBlocks == 1) {
          currentWriteBlock = (ROW[]) new ArrayList[blockSize];
        }
      }
      currentWriteBlock[addCursor++] = t;
    } else if (t != null) {
      // the tableDesc will be null in the case that all columns in that table
      // is not used. we use a dummy row to denote all rows in that table, and
      // the dummy row is added by caller.
      this.dummyRow = t;
    }
    ++size;
  }

  @Override
  public AbstractRowContainer.RowIterator<ROW> rowIter() {
    return this;
  }

  @Override
  public ROW first() throws HiveException {
    if (size == 0) {
      return null;
    }

    try {
      firstCalled = true;
      // when we reach here, we must have some data already (because size >0).
      // We need to see if there are any data flushed into file system. If not,
      // we can
      // directly read from the current write block. Otherwise, we need to read
      // from the beginning of the underlying file.
      this.itrCursor = 0;
      closeWriter();
      closeReader();

      if (tblDesc == null) {
        this.itrCursor++;
        return dummyRow;
      }

      this.currentReadBlock = this.firstReadBlockPointer;
      if (this.numFlushedBlocks == 0) {
        this.readBlockSize = this.addCursor;
        this.currentReadBlock = this.currentWriteBlock;
      } else {

        rr = spillFile.initRecordReader(tblDesc);
        nextBlock(0);
      }
      // we are guaranteed that we can get data here (since 'size' is not zero)
      ROW ret = currentReadBlock[itrCursor++];
      removeKeys(ret);
      return ret;
    } catch (Exception e) {
      throw new HiveException(e);
    }

  }

  @Override
  public ROW next() throws HiveException {

    if (!firstCalled) {
      throw new RuntimeException("Call first() then call next().");
    }

    if (size == 0) {
      return null;
    }

    if (tblDesc == null) {
      if (this.itrCursor < size) {
        this.itrCursor++;
        return dummyRow;
      }
      return null;
    }

    ROW ret;
    if (itrCursor < this.readBlockSize) {
      ret = this.currentReadBlock[itrCursor++];
      removeKeys(ret);
      return ret;
    } else {
      nextBlock(0);
      if (this.readBlockSize == 0) {
        if (currentWriteBlock != null && currentReadBlock != currentWriteBlock) {
          setWriteBlockAsReadBlock();
        } else {
          return null;
        }
      }
      return next();
    }
  }

  private void removeKeys(ROW ret) {
    if (this.keyObject != null && this.currentReadBlock != this.currentWriteBlock) {
      int len = this.keyObject.size();
      int rowSize = ret.size();
      for (int i = 0; i < len; i++) {
        ret.remove(rowSize - i - 1);
      }
    }
  }

  private final ArrayList<Object> row = new ArrayList<Object>(2);
  
  private void spillBlock(ROW[] block, int length) throws HiveException {
    try {
      if (spillFile == null) {
        setupWriter();
      } else if (rw == null) {
        throw new HiveException("RowContainer has already been closed for writing.");
      }

      row.clear();
      row.add(null);
      row.add(null);

      if (this.keyObject != null) {
        row.set(1, this.keyObject);
        for (int i = 0; i < length; ++i) {
          ROW currentValRow = block[i];
          row.set(0, currentValRow);
          Writable outVal = serde.serialize(row, standardOI);
          rw.write(outVal);
        }
      } else {
        for (int i = 0; i < length; ++i) {
          ROW currentValRow = block[i];
          Writable outVal = serde.serialize(currentValRow, standardOI);
          rw.write(outVal);
        }
      }

      if (block == this.currentWriteBlock) {
        this.addCursor = 0;
      }

      this.numFlushedBlocks++;
    } catch (Exception e) {
      clearRows();
      LOG.error(e.toString(), e);
      if ( e instanceof HiveException ) {
        throw (HiveException) e;
      }
      throw new HiveException(e);
    }
  }


  @Override
  public boolean hasRows() {
    return size > 0;
  }

  @Override
  public boolean isSingleRow() {
    return size == 1;
  }

  /**
   * Get the number of elements in the RowContainer.
   *
   * @return number of elements in the RowContainer
   */
  @Override
  public int rowCount() {
    return (int)size;
  }

  protected boolean nextBlock(int readIntoOffset) throws HiveException {
    itrCursor = 0;
    this.readBlockSize = 0;
    if (this.numFlushedBlocks == 0) {
      return false;
    }

    try {
      if (val == null) {
        val = serde.getSerializedClass().newInstance();
      }
      boolean nextSplit = true;
      int i = readIntoOffset;

      if (rr != null) {
        Object key = rr.createKey();
        while (i < this.currentReadBlock.length && rr.next(key, val)) {
          nextSplit = false;
          this.currentReadBlock[i++] = (ROW) ObjectInspectorUtils.copyToStandardObject(serde
                  .deserialize(val), serde.getObjectInspector(), ObjectInspectorCopyOption.WRITABLE);
        }
      }

      if (nextSplit && spillFile.hasNextSplit()) {
        if (rr != null) {
          // close the current RecordReader
          rr.close();
        }
        // open record reader to read next split
        rr = spillFile.nextRecordReader();
        return nextBlock(0);
      }

      this.readBlockSize = i;
      return this.readBlockSize > 0;
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      try {
        this.clearRows();
      } catch (HiveException e1) {
        LOG.error(e.getMessage(), e);
      }
      throw new HiveException(e);
    }
  }

  public void copyToDFSDirecory(FileSystem destFs, Path destPath) throws IOException, HiveException {
    if (addCursor > 0) {
      this.spillBlock(this.currentWriteBlock, addCursor);
    }
    // if a spill file is created then copy to the path, otherwise just return as there is
    // nothing to copy
    if (spillFile == null)
      return;
    this.closeWriter();
    spillFile.copyToDFSDirectory(destFs, destPath);
    clearRows();
  }

  /**
   * Remove all elements in the RowContainer.
   */
  @Override
  public void clearRows() throws HiveException {
    itrCursor = 0;
    addCursor = 0;
    numFlushedBlocks = 0;
    this.readBlockSize = 0;
    this.firstCalled = false;
    addCursor = 0;

    size = 0;
    try {
      if (rw != null) {
        rw.close(false);
      }
      if (rr != null) {
        rr.close();
      }
    } catch (Exception e) {
      LOG.error(e.toString());
      throw new HiveException(e);
    } finally {
      rw = null;
      rr = null;
      if (spillFile != null) {
        spillFile.clean();
        spillFile = null;
      }
    }
  }

  private void deleteLocalFile(File file, boolean recursive) {
    try {
      if (file != null) {
        if (!file.exists()) {
          return;
        }
        if (file.isDirectory() && recursive) {
          File[] files = file.listFiles();
          for (File file2 : files) {
            deleteLocalFile(file2, true);
          }
        }
        boolean deleteSuccess = file.delete();
        if (!deleteSuccess) {
          LOG.error("Error deleting tmp file:" + file.getAbsolutePath());
        }
      }
    } catch (Exception e) {
      LOG.error("Error deleting tmp file:" + file.getAbsolutePath(), e);
    }
  }

  private void closeWriter() throws IOException {
    if (this.rw != null) {
      this.rw.close(false);
      this.rw = null;
    }
  }

  private void closeReader() throws IOException {
    if (this.rr != null) {
      this.rr.close();
      this.rr = null;
    }
  }

  public void setKeyObject(List<Object> dummyKey) {
    this.keyObject = dummyKey;
  }

  public void setTableDesc(TableDesc tblDesc) {
    this.tblDesc = tblDesc;
  }

  protected int getAddCursor() {
    return addCursor;
  }

  protected final boolean willSpill() {
    return addCursor >= blockSize;
  }

  protected int getBlockSize() {
    return blockSize;
  }

  protected void setupWriter() throws HiveException {
    try {
      if (spillFile != null)
        return;
      String suffix = ".tmp";
      if (this.keyObject != null) {
        suffix = "." + this.keyObject.toString() + suffix;
      }

      spillFile = getSpillFile(jc, reporter);
      spillFile.init(suffix);
      rw = spillFile.initRecordWriter(tblDesc, reporter, serde);
    } catch (Exception e) {
      clearRows();
      LOG.error(e.toString(), e);
      throw new HiveException(e);
    }

  }

  protected RecordWriter getRecordWriter() {
    return rw;
  }

  protected InputSplit[] getInputSplits() {
    if (spillFile != null) {
      return spillFile.getInputSplits();
    }
    return null;
  }

  protected boolean endOfCurrentReadBlock() {
    if (tblDesc == null) {
      return false;
    }
    return itrCursor >= this.readBlockSize;
  }

  protected int getCurrentReadBlockSize() {
    return readBlockSize;
  }

  protected void setWriteBlockAsReadBlock() {
    this.itrCursor = 0;
    this.readBlockSize = this.addCursor;
    this.firstReadBlockPointer = this.currentReadBlock;
    currentReadBlock = currentWriteBlock;
  }

  protected RecordReader setReaderAtSplit(int splitNum)
          throws IOException {
    if ( rr != null ) {
      rr.close();
    }
    rr = spillFile.getRecordReader(splitNum);
    return rr;
  }

  protected ROW getReadBlockRow(int rowOffset) {
    itrCursor = rowOffset + 1;
    return currentReadBlock[rowOffset];
  }

  protected void resetCurrentReadBlockToFirstReadBlock() {
    currentReadBlock = firstReadBlockPointer;
  }

  protected void resetReadBlocks() {
    this.currentReadBlock = this.currentWriteBlock;
    this.firstReadBlockPointer = currentReadBlock;
  }

  protected void close() throws HiveException {
    clearRows();
    currentReadBlock = firstReadBlockPointer = currentWriteBlock = null;
  }

  protected int getLastActualSplit() {
    return spillFile.actualSplitNum - 1;
  }

  abstract class SpillFile {

    protected JobConf jobConf;
    protected Reporter reporter;
    protected int currentSplitNumber = -1;
    protected int actualSplitNum = 0;
    protected InputSplit[] inputSplits = null;
    protected InputFormat<WritableComparable, Writable> inputFormat = null;

    abstract public void init(String suffix) throws IOException;

    abstract public RecordWriter initRecordWriter(TableDesc tblDesc, Reporter reporter, AbstractSerDe serde) throws Exception;

    abstract public String getParentDir();

    abstract public void copyToDFSDirectory(FileSystem destFs, Path destPath) throws IOException, HiveException;

    public RecordReader initRecordReader(TableDesc tblDesc) throws IOException {
      if (inputSplits == null) {
        if (this.inputFormat == null) {
          inputFormat = (InputFormat<WritableComparable, Writable>) ReflectionUtils.newInstance(
                  tblDesc.getInputFileFormatClass(), jobConf);
        }

        jobConf.set(FileInputFormat.INPUT_DIR, org.apache.hadoop.util.StringUtils.escapeString(getParentDir()));
        inputSplits = inputFormat.getSplits(jobConf, 1);
        actualSplitNum = inputSplits.length;
      }
      currentSplitNumber = 0;
      RecordReader rr = inputFormat.getRecordReader(inputSplits[currentSplitNumber], jobConf, reporter);
      currentSplitNumber++;
      return rr;
    }

    public RecordReader nextRecordReader() throws IOException {
      if (inputSplits == null) {
        throw new IOException("Initialize reading splill file");
      }

      RecordReader rr = inputFormat.getRecordReader(inputSplits[currentSplitNumber], jobConf, reporter);
      currentSplitNumber++;
      return rr;
    }

    public RecordReader getRecordReader(int splitNum) throws IOException {
      // open record reader to read next split
      RecordReader rr = inputFormat.getRecordReader(inputSplits[splitNum], jobConf, reporter);
      currentSplitNumber = splitNum + 1;
      return rr;
    }

    public boolean hasNextSplit() throws IOException {
      if (inputSplits == null) {
        throw new IOException("Initialize reading spill file");
      }

      return this.currentSplitNumber < this.actualSplitNum;
    }

    public void clean() {
      this.actualSplitNum = 0;
      this.currentSplitNumber = -1;
      this.inputSplits = null;
      this.inputFormat = null;
    }

    public InputSplit[] getInputSplits() {
      return inputSplits;
    }
  }

  class DFSSpillFile extends SpillFile {
    private FileSystem fs;
    private String spillDir;
    private String tmpFileParent;
    private String tmpFile;
    private Path tmpFilePath;

    public DFSSpillFile(String tmpDirDfs, Configuration jobConf, Reporter reporter) throws IOException {
      this.jobConf = new JobConf(jobConf);
      this.fs = FileSystem.get(jobConf);
      this.spillDir = tmpDirDfs;
      this.reporter = reporter;
    }

    @Override
    public void clean() {
      try {
        if (tmpFileParent != null)
          fs.delete(new Path(tmpFileParent), true);
      } catch (IOException e) {
        LOG.error(e.getMessage(), e);
      }

      tmpFileParent = null;
      tmpFile = null;
      tmpFilePath = null;
      super.clean();
    }

    @Override
    public String getParentDir() {
      return tmpFileParent;
    }

    @Override
    public void init(String suffix) throws IOException {
      fs.mkdirs(new Path(spillDir));
      while (true) {
        tmpFileParent = spillDir + "/hive-rowcontainer" + (new Random()).nextInt();
        Path tmpFileParentPath = new Path(tmpFileParent);
        if (!fs.exists(tmpFileParentPath)) {
          fs.mkdirs(tmpFileParentPath);
          break;
        }
        LOG.debug("retry creating tmp row-container directory...");
      }
      tmpFile = tmpFileParent + "/RowContainer" + suffix;
      LOG.info("RowContainer created temp file " + tmpFile);
    }

    @Override
    public RecordWriter initRecordWriter(TableDesc tblDesc, Reporter reporter, AbstractSerDe serde) throws Exception {
      OutputFormat<?, ?> outputFormat = tblDesc.getOutputFileFormatClass().newInstance();
      tmpFilePath = new Path(tmpFile.toString());
      return HiveFileFormatUtils.getRecordWriter(jobConf, outputFormat,
              serde.getSerializedClass(), false, tblDesc.getProperties(), tmpFilePath, reporter);
    }

    @Override
    public void copyToDFSDirectory(FileSystem destFs, Path destPath) throws IOException, HiveException {
      if (tmpFilePath == null || tmpFilePath.toString().trim().equals("")) {
        return;
      }
      LOG.info("RowContainer copied temp file " + tmpFile + " to dfs directory " + destPath.toString());
      FileUtil.copy(fs, tmpFilePath, destFs, new Path(destPath, new Path(tmpFilePath.getName())), false, jobConf);
    }
  }

  class LocalSpillFile extends SpillFile {
    private File tmpFileParent;
    private File tmpFile;
    private Path tmpFilePath;

    public LocalSpillFile(Configuration jobConf, Reporter reporter) {
      jobConf.set(CommonConfigurationKeysPublic.FS_DEFAULT_NAME_KEY,
              Utilities.HADOOP_LOCAL_FS);
      this.jobConf = new JobConf(jobConf);
      this.reporter = reporter;
    }

    @Override
    public void clean() {
      deleteLocalFile(tmpFileParent, true);
      tmpFile = null;
      tmpFileParent = null;
      tmpFilePath = null;
      super.clean();
    }

    @Override
    public String getParentDir() {
      return tmpFileParent.getAbsolutePath();
    }

    @Override
    public void init(String suffix) throws IOException {
      while (true) {
        tmpFileParent = File.createTempFile("hive-rowcontainer", "");
        boolean success = tmpFileParent.delete() && tmpFileParent.mkdir();
        if (success) {
          break;
        }
        LOG.debug("retry creating tmp row-container directory...");
      }

      tmpFile = File.createTempFile("RowContainer", suffix, tmpFileParent);
      LOG.info("RowContainer created temp file " + tmpFile.getAbsolutePath());
      // Delete the temp file if the JVM terminate normally through Hadoop job
      // kill command.
      // Caveat: it won't be deleted if JVM is killed by 'kill -9'.
      tmpFileParent.deleteOnExit();
      tmpFile.deleteOnExit();
    }

    @Override
    public RecordWriter initRecordWriter(TableDesc tblDesc, Reporter reporter, AbstractSerDe serde) throws Exception {
      OutputFormat<?, ?> outputFormat = tblDesc.getOutputFileFormatClass().newInstance();
      tmpFilePath = new Path(tmpFile.toString());
      return HiveFileFormatUtils.getRecordWriter(jobConf, outputFormat, serde.getSerializedClass(),
              false, tblDesc.getProperties(), tmpFilePath, reporter);
    }

    @Override
    public void copyToDFSDirectory(FileSystem destFs, Path destPath) throws IOException, HiveException {
      if (tmpFilePath == null || tmpFilePath.toString().trim().equals("")) {
        return;
      }
      LOG.info("RowContainer copied temp file " + tmpFile.getAbsolutePath() + " to dfs directory "
              + destPath.toString());
      destFs.copyFromLocalFile(true, tmpFilePath, new Path(destPath, new Path(tmpFilePath.getName())));
    }

    private void deleteLocalFile(File file, boolean recursive) {
      try {
        if (file != null) {
          if (!file.exists()) {
            return;
          }
          if (file.isDirectory() && recursive) {
            File[] files = file.listFiles();
            for (File file2 : files) {
              deleteLocalFile(file2, true);
            }
          }
          boolean deleteSuccess = file.delete();
          if (!deleteSuccess) {
            LOG.error("Error deleting tmp file:" + file.getAbsolutePath());
          }
        }
      } catch (Exception e) {
        LOG.error("Error deleting tmp file:" + file.getAbsolutePath(), e);
      }
    }
  }

  SpillFile getSpillFile(Configuration jobConf, Reporter reporter) throws IOException {
    boolean fTmpFileOnDfs = HiveConf.getBoolVar(jobConf, HiveConf.ConfVars.TMP_MAPRFS_VOLUME);
    String tmpDirDfs = null;
    if (fTmpFileOnDfs) {
      try {
        tmpDirDfs = getDfsTmpDir(jobConf);
        FileSystem dfs = FileSystem.get(jobConf);
        dfs.mkdirs(new Path(tmpDirDfs));
      } catch (Exception ex) {
        LOG.info("Failed to create temporary directory on MapRFS local volume. " +
                "Continuing with creating temporary files on local filesystem.");
        LOG.debug("Error: ", ex);
        tmpDirDfs = null;
      }
      if (tmpDirDfs != null)
        return new DFSSpillFile(tmpDirDfs, jobConf, reporter);
    }

    // write spill file to local file system
    return new LocalSpillFile(jobConf, reporter);
  }

  private static String getDfsTmpDir(Configuration jobConf) throws IOException {
    // Create tmp files under the local maprfs spill directory
    String tmpDirDfs = jobConf.get("mapr.mapred.localvolume.root.dir.path");

    if (tmpDirDfs == null || tmpDirDfs.isEmpty()) {
      LOG.debug("Can't find mapred volume path directly in JobConf. Constructing it from known properties in JobConf");

      // 1. mapr.localvolumes.path (ex. /var/mapr/local/)
      tmpDirDfs = jobConf.get("mapr.localvolumes.path", "/var/mapr/local");
      tmpDirDfs += "/";

      // 2. hostname from /opt/mapr/hostname
      tmpDirDfs += getMapRHostname(jobConf);

      // 3. append "mapred/taskTracker"
      tmpDirDfs += "/mapred/";
      tmpDirDfs += jobConf.get("mapr.mapred.localvolume.root.dir.name", "taskTracker");
    }
    tmpDirDfs += "/";
    // 4. append spill directory (spill or spill.U) from job config parameters
    tmpDirDfs += jobConf.get("mapr.localspill.dir", "spill");
    if (!jobConf.getBoolean("mapreduce.maprfs.use.compression", true)) {
      tmpDirDfs += ".U";
    }

    // 5. append jobId/taskId (retrieve job id, task id from JobConf)
    String jobId = jobConf.get("mapreduce.job.id");
    String taskId = jobConf.get("mapreduce.task.id");
    if (jobId == null || jobId.isEmpty() || taskId == null || taskId.isEmpty()) {
      LOG.debug("mapreduce.job.id or mapreduce.task.id are not set in JobConf");

      // retrieve job id, task id from current working directory
      String workDirSplits[] = null;
      String workDir = null;
      try {
        workDir = new File(".").getCanonicalPath();
        workDirSplits = workDir.split("/");
      } catch (IOException e) {
        if (LOG.isDebugEnabled()) {
          LOG.debug("Failed to get the current working directory of the task: " + e);
        }
        throw e;
      }
      // sample workDir: /tmp/mapr-hadoop/mapred/local/taskTracker/user1/jobcache/job_201306061142_0008/attempt_201306061142_0008_m_000000_0/work
      // extract the job id and task id names
      jobId = workDirSplits[workDirSplits.length - 3];
      taskId = workDirSplits[workDirSplits.length - 2];
    }
    tmpDirDfs += "/" + jobId;
    tmpDirDfs += "/" + taskId;

    if (LOG.isDebugEnabled()) {
      LOG.debug("tmpDirDfs: " + tmpDirDfs);
    }
    return tmpDirDfs;
  }

  /* Read hostname from /opt/mapr/hostname */
  private static String getMapRHostname(Configuration jobConf) {
    // Get hostname. If its set in conf file use it
    String hostName = jobConf.get("slave.host.name");
    if (hostName != null) {
      return hostName;
    }

    String maprHome = System.getProperty("mapr.home.dir");
    if (maprHome == null) {
      maprHome = System.getenv("MAPR_HOME");
      if (maprHome == null) {
        maprHome = "/opt/mapr/";
      }
    }

    String hostNameFile = maprHome + "/hostname";

    BufferedReader breader = null;
    FileReader freader = null;
    try {
      freader = new FileReader(hostNameFile);
      breader = new BufferedReader(freader);
      return breader.readLine();
    } catch (Exception e) {
      /* On any exception while reading hostname return null */
      if (LOG.isWarnEnabled()) {
        LOG.warn("Error while reading " + hostNameFile, e);
      }
    } finally {
      try {
        if (breader != null) {
          breader.close();
        }
      } catch (Throwable t) {
        if (LOG.isErrorEnabled()) {
          LOG.error("Failed to close breader", t);
        }
      }

      try {
        if (freader != null) {
          freader.close();
        }
      } catch (Throwable t) {
        if (LOG.isErrorEnabled()) {
          LOG.error("Failed to close " + hostNameFile, t);
        }
      }
    }

    if (hostName == null) {
      try {
        hostName = DNS.getDefaultHost(
                jobConf.get("mapred.tasktracker.dns.interface", "default"),
                jobConf.get("mapred.tasktracker.dns.nameserver", "default"));
      } catch (IOException ioe) {
        if (LOG.isErrorEnabled()) {
          LOG.error("Failed to retrieve local host name", ioe);
        }
        throw new RuntimeException(ioe);
      }
    }

    return hostName;
  }
}
