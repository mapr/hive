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
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapred.FileSplit;
import org.apache.hadoop.mapred.InputSplit;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Wrapper for TableSplit.
 */
public class MapRDBJsonSplit extends FileSplit implements InputSplit {

  private TableSplit tableSplit;

  public MapRDBJsonSplit(TableSplit tableSplit, Path path) {
    super(path, 0, 0, (String[]) null);
    this.tableSplit = tableSplit;
  }

  public MapRDBJsonSplit() {
    super((Path) null, 0, 0, (String[]) null);
    tableSplit = new TableSplit();
  }

  public TableSplit getTableSplit() {
    return tableSplit;
  }

  @Override public void readFields(DataInput in) throws IOException {
    super.readFields(in);
    tableSplit.readFields(in);
  }

  @Override public void write(DataOutput out) throws IOException {
    super.write(out);
    tableSplit.write(out);
  }

  @Override public long getLength() {
    return tableSplit.getLength();
  }

  @Override public String[] getLocations() {
    return tableSplit.getLocations();
  }
}
