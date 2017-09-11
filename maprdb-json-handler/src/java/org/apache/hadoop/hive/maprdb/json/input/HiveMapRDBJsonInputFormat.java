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
import org.apache.hadoop.hive.maprdb.json.shims.ValueWritableComparable;
import org.apache.hadoop.hive.shims.ShimLoader;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.mapreduce.Job;
import org.ojai.Document;
import org.ojai.Value;

import java.io.IOException;
import java.util.List;

public class HiveMapRDBJsonInputFormat extends TableInputFormat
        implements InputFormat<ValueWritableComparable, DocumentWritable> {

  @Override
  public InputSplit[] getSplits(JobConf jobConf, int numSplits) throws IOException {

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
    return inputSplits;
  }

  @Override
  public RecordReader<ValueWritableComparable, DocumentWritable>
  getRecordReader(
          InputSplit split,
          JobConf jobConf,
          Reporter reporter) throws IOException {

    MapRDBJsonSplit fileSplit = (MapRDBJsonSplit) split;
    TableSplit tableSplit = fileSplit.getTableSplit();

    org.apache.hadoop.mapreduce.TaskAttemptContext tac =
            ShimLoader.getHadoopShims().newTaskAttemptContext(jobConf, reporter);

    org.apache.hadoop.mapreduce.RecordReader<Value, Document> recordReader;
    try {
      recordReader = createRecordReader(tableSplit, tac);
      recordReader.initialize(fileSplit, tac);
    } catch (InterruptedException e) {
      throw new IOException("Failed to initialize RecordReader", e);
    }

    final org.apache.hadoop.mapreduce.RecordReader<Value, Document> finalRecordReader = recordReader;

    return new RecordReader<ValueWritableComparable, DocumentWritable>() {

      @Override
      public boolean next(ValueWritableComparable key, DocumentWritable value) throws IOException {
        boolean next;
        try {
          next = finalRecordReader.nextKeyValue();
          if (next) {
            key.setValue(finalRecordReader.getCurrentKey());
            value.setDocument(finalRecordReader.getCurrentValue());
          }
        } catch (InterruptedException e) {
          throw new IOException("Error reading the next key/value pair from the input", e);
        }
        return next;
      }

      @Override
      public ValueWritableComparable createKey() {
        return new ValueWritableComparable();
      }

      @Override
      public DocumentWritable createValue() {
        return new DocumentWritable();
      }

      @Override
      public long getPos() throws IOException {
        return 0L;
      }

      @Override
      public void close() throws IOException {
        finalRecordReader.close();
      }

      @Override
      public float getProgress() throws IOException {
        float progress;
        try {
          progress = finalRecordReader.getProgress();
        } catch (InterruptedException e) {
          throw new IOException("Failed to get current progress of the record reader", e);
        }
        return progress;
      }
    };
  }
}
