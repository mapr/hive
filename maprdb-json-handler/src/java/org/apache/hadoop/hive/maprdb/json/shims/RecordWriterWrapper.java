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

package org.apache.hadoop.hive.maprdb.json.shims;

import org.apache.hadoop.hive.ql.exec.FileSinkOperator;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapred.RecordWriter;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.ojai.Document;
import org.ojai.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Simple record writer wrapper.
 */
public class RecordWriterWrapper
    implements RecordWriter<NullWritable, DocumentWritable>, FileSinkOperator.RecordWriter {

  private static final Logger LOG = LoggerFactory.getLogger(RecordWriterWrapper.class);
  private final org.apache.hadoop.mapreduce.RecordWriter<Value, Document> recordWriter;
  private final TaskAttemptContext tac;

  public RecordWriterWrapper(org.apache.hadoop.mapreduce.RecordWriter<Value, Document> recordWriter,
      TaskAttemptContext tac) {
    this.recordWriter = recordWriter;
    this.tac = tac;
  }

  @Override public void write(NullWritable key, DocumentWritable value) throws IOException {
    Document document = value.getDocument();
    try {
      recordWriter.write(document.getId(), document);
    } catch (InterruptedException e) {
      throw new IOException("Error writing key/value pair", e);
    }
  }

  @Override public void close(Reporter reporter) throws IOException {
    try {
      LOG.info("Closing Record Writer");
      recordWriter.close(tac);
    } catch (InterruptedException e) {
      throw new IOException("Error writing key/value pair", e);
    }
  }

  @Override public void write(Writable w) throws IOException {
    write(null, (DocumentWritable) w);
  }

  @Override public void close(boolean abort) throws IOException {
    close(null);
  }
}
