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

package org.apache.hadoop.hive.ql.exec;

import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

/**
 * MapR Db JSON fetch by Id task implementation.
 **/
public class MapRDbJsonFetchByIdTask extends FetchTask {

  private static final transient Logger LOG = LoggerFactory.getLogger(MapRDbJsonFetchByIdTask.class);
  private boolean processed = false;

  public MapRDbJsonFetchByIdTask() {
    super();
  }

  FetchOperator createFetchOperator() throws HiveException {
    return new MapRDbJsonFetchByIdOperator(work, job, source);
  }

  @Override public boolean fetch(List res) throws IOException {
    sink.reset(res);
    try {
      if (!processed) {
        boolean tableHasData = fetch.pushRow();
        // No data
        if (!tableHasData) {
          LOG.info("No data fetched");
          // Closing the operator can sometimes yield more rows (HIVE-11892)
          fetch.closeOperator();
          return false;
        }
        processed = true;
        return true;
      }
      return false;
    } catch (IOException e) {
      throw e;
    } catch (Exception e) {
      throw new IOException(e);
    } finally {
      totalRows += sink.getNumRows();
    }
  }
}
