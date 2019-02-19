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

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapred.RecordReader;
import org.ojai.Document;
import org.ojai.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Simple record reader wrapper.
 */
public class RecordReaderWrapper implements RecordReader<NullWritable, DocumentWritable> {

  private static final Logger LOG = LoggerFactory.getLogger(RecordReaderWrapper.class);
  private final org.apache.hadoop.mapreduce.RecordReader<Value, Document> recordReader;

  public RecordReaderWrapper(org.apache.hadoop.mapreduce.RecordReader<Value, Document> recordReader) {
    this.recordReader = recordReader;
  }

  @Override public boolean next(NullWritable key, DocumentWritable value) throws IOException {
    try {
      boolean next = recordReader.nextKeyValue();
      if (next) {
        value.setDocument(recordReader.getCurrentValue());
      }
      return next;
    } catch (InterruptedException e) {
      throw new IOException("Error reading the next key/value pair from the input", e);
    }
  }

  @Override public NullWritable createKey() {
    return NullWritable.get();
  }

  @Override public DocumentWritable createValue() {
    return new DocumentWritable();
  }

  @Override public long getPos() throws IOException {
    return 0L;
  }

  @Override public void close() throws IOException {
    LOG.info("Closing Record Reader");
    recordReader.close();
  }

  @Override public float getProgress() throws IOException {
    try {
      return recordReader.getProgress();
    } catch (InterruptedException e) {
      throw new IOException("Failed to get current progress of the record reader", e);
    }
  }
}