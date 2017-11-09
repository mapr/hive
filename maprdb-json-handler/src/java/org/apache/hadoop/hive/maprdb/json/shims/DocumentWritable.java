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

import com.mapr.db.mapreduce.impl.TableSplit;
import org.apache.hadoop.io.Writable;
import org.ojai.Document;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class DocumentWritable implements Writable {
  private Document document;
  private TableSplit inputFormat;

  public DocumentWritable(Document document) {
    this.document = document;
    inputFormat = new TableSplit();
  }

  public DocumentWritable() {
  }

  public Document getDocument() {
    return document;
  }

  public void setDocument(Document document) {
    this.document = document;
  }

  @Override
  public void write(DataOutput out) throws IOException {
    inputFormat.write(out);
  }

  @Override
  public void readFields(DataInput in) throws IOException {
    inputFormat.readFields(in);
  }

  @Override
  public String toString() {
    return "Document=" + document;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    DocumentWritable that = (DocumentWritable) o;

    return document != null ? document.equals(that.document) : that.document == null;
  }

  @Override
  public int hashCode() {
    return document != null ? document.hashCode() : 0;
  }
}

