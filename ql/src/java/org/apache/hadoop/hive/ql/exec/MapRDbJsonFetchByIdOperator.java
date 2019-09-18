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

import org.apache.hadoop.hive.maprdb.json.shims.DocumentWritable;
import org.apache.hadoop.hive.maprdb.json.util.MapRDbJsonTableUtil;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.metadata.VirtualColumn;
import org.apache.hadoop.hive.ql.plan.FetchWork;
import org.apache.hadoop.hive.ql.plan.MapRDbJsonFetchByIdWork;
import org.apache.hadoop.hive.serde2.objectinspector.InspectableObject;

import org.apache.hadoop.mapred.JobConf;
import org.ojai.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;

/**
 * MapR DB JSON specific FetchTask implementation.
 * Uses MapR DB JSON method findById() to get document by Id.
 **/
public class MapRDbJsonFetchByIdOperator extends FetchOperator {

  static final Logger LOG = LoggerFactory.getLogger(MapRDbJsonFetchByIdOperator.class.getName());

  MapRDbJsonFetchByIdOperator(FetchWork work, JobConf job, Operator<?> operator)
      throws HiveException {
    super(work, job, operator, new ArrayList<VirtualColumn>());
  }

  @Override
  public InspectableObject getNextRow() throws IOException {
    try {
      MapRDbJsonFetchByIdWork mapRWork = (MapRDbJsonFetchByIdWork) work;
      if (mapRWork.isEmpty()) {
        return null;
      } else {
        Document document = MapRDbJsonTableUtil
            .findById(mapRWork.getMapRDbTableName(), mapRWork.getSearchValue());
        if (document == null) {
          return null;
        }
        currSerDe = tableSerDe;
        inspectable.oi = currSerDe.getObjectInspector();
        inspectable.o = currSerDe.deserialize(new DocumentWritable(document));
        return inspectable;
      }
    } catch (Exception e) {
      throw new IOException(e);
    }
  }
}
