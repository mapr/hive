/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.hadoop.hive.maprdb.json.output;

import com.mapr.db.mapreduce.TableOutputFormat;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hive.maprdb.json.shims.DocumentWritable;
import org.apache.hadoop.hive.maprdb.json.shims.RecordWriterWrapper;
import org.apache.hadoop.hive.ql.exec.FileSinkOperator;
import org.apache.hadoop.hive.ql.io.HiveOutputFormat;
import org.apache.hadoop.hive.shims.ShimLoader;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.OutputFormat;
import org.apache.hadoop.mapred.RecordWriter;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.util.Progressable;
import org.ojai.Document;
import org.ojai.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;

import static org.apache.hadoop.hive.maprdb.json.conf.MapRDBConstants.MAPRDB_OUTPUT_TABLE_NAME;
import static org.apache.hadoop.hive.maprdb.json.conf.MapRDBConstants.MAPRDB_TABLE_NAME;

public class HiveMapRDBJsonOutputFormat extends TableOutputFormat
  implements HiveOutputFormat<NullWritable, DocumentWritable> {

  private static final Logger LOG = LoggerFactory.getLogger(HiveMapRDBJsonOutputFormat.class);

  @Override
  public RecordWriter<NullWritable, DocumentWritable> getRecordWriter(
    FileSystem ignored, JobConf jobConf, String name, Progressable progress) throws IOException {

    return getRecordWriterWrapper(jobConf, progress);
  }

  @Override
  public void checkOutputSpecs(FileSystem ignored, JobConf jobConf) throws IOException {
    Job job = new Job(jobConf);
    JobContext jobContext = ShimLoader.getHadoopShims().newJobContext(job);
    try {
      checkOutputSpecs(jobContext);
    } catch (InterruptedException e) {
      throw new IOException("Error validating output specification for the job", e);
    }
  }

  @Override
  public FileSinkOperator.RecordWriter getHiveRecordWriter(JobConf jc, Path finalOutPath,
      Class<? extends Writable> valueClass, boolean isCompressed, Properties tableProperties, Progressable progress)
      throws IOException {

    return getRecordWriterWrapper(jc, progress);
  }

  private RecordWriterWrapper getRecordWriterWrapper(JobConf jobConf, Progressable progress)
      throws IOException {
    if (LOG.isDebugEnabled()) LOG.debug(MAPRDB_OUTPUT_TABLE_NAME + " = {}", jobConf.get(MAPRDB_TABLE_NAME));

    jobConf.set(MAPRDB_OUTPUT_TABLE_NAME, jobConf.get(MAPRDB_TABLE_NAME));

    org.apache.hadoop.mapreduce.TaskAttemptContext tac =
        ShimLoader.getHadoopShims().newTaskAttemptContext(jobConf, progress);

    try {
      org.apache.hadoop.mapreduce.RecordWriter<Value, Document> recordWriter = getRecordWriter(tac);
      return new RecordWriterWrapper(recordWriter, tac);
    } catch (InterruptedException e) {
      throw new IOException("Failed to initialize RecordWriter", e);
    }
  }
}