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

package org.apache.hadoop.hive.maprdb.json.input;

import com.mapr.db.mapreduce.TableInputFormat;
import com.mapr.db.mapreduce.impl.TableSplit;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hive.maprdb.json.shims.DocumentWritable;
import org.apache.hadoop.hive.maprdb.json.shims.MapRDBJsonSplit;
import org.apache.hadoop.hive.maprdb.json.shims.RecordReaderWrapper;
import org.apache.hadoop.hive.shims.ShimLoader;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.mapreduce.Job;
import org.ojai.Document;
import org.ojai.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class HiveMapRDBJsonInputFormat extends TableInputFormat
  implements InputFormat<NullWritable, DocumentWritable> {

  private static final Logger LOG = LoggerFactory.getLogger(HiveMapRDBJsonInputFormat.class);

  @Override
  public InputSplit[] getSplits(JobConf jobConf, int numSplits) throws IOException {
    super.setConf(jobConf);
    List<org.apache.hadoop.mapreduce.InputSplit> splits;
    Job context = new Job(jobConf);
    try {
      splits = getSplits(context);
    } catch (InterruptedException e) {
      throw new IOException(e);
    }

    Path[] tablePaths = org.apache.hadoop.mapreduce.lib.input.FileInputFormat.getInputPaths(context);

    InputSplit[] inputSplits = null;
    if (splits != null) {
      inputSplits = new InputSplit[splits.size()];
      for (int i = 0; i < inputSplits.length; i++) {
        inputSplits[i] = new MapRDBJsonSplit((TableSplit) splits.get(i), tablePaths[0]);
      }
    }

    if (LOG.isDebugEnabled()){
      if (inputSplits != null) {
        long[] splitSizes = new long[inputSplits.length];
        for (int i = 0; i < inputSplits.length; i++) {
          splitSizes[i] = inputSplits[i].getLength();
        }
        LOG.debug("getSplits() => split number: {}, split size: {}", inputSplits.length, Arrays.toString(splitSizes));
      }else {
        LOG.debug("getSplits() => 0 splits");
      }
    }

    return inputSplits;
  }

  @Override
  public RecordReader<NullWritable, DocumentWritable>
  getRecordReader(
    InputSplit split,
    JobConf jobConf,
    Reporter reporter) throws IOException {

    MapRDBJsonSplit fileSplit = (MapRDBJsonSplit) split;
    TableSplit tableSplit = fileSplit.getTableSplit();

    org.apache.hadoop.mapreduce.TaskAttemptContext tac =
      ShimLoader.getHadoopShims().newTaskAttemptContext(jobConf, reporter);

    try {
      setConf(tac.getConfiguration());
      org.apache.hadoop.mapreduce.RecordReader<Value, Document> recordReader = createRecordReader(tableSplit, tac);
      recordReader.initialize(fileSplit, tac);
      return new RecordReaderWrapper(recordReader);
    } catch (InterruptedException e) {
      throw new IOException("Failed to initialize RecordReader", e);
    }
  }
}