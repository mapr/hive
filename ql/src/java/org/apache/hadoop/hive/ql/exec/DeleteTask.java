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

import com.mapr.db.Admin;
import com.mapr.db.MapRDB;
import com.mapr.db.Table;
import com.mapr.db.impl.MapRDBImpl;
import org.apache.hadoop.hive.ql.DriverContext;
import org.apache.hadoop.hive.ql.plan.api.StageType;
import org.apache.hadoop.hive.ql.plan.DeleteWork;
import org.ojai.Document;
import org.ojai.DocumentStream;

import java.io.Serializable;

import static org.apache.hadoop.hive.maprdb.json.util.MapRDbJsonParseUtil.buildQueryCondition;

/**
 * Task to remove data from MapR Db Json table.
 */
public class DeleteTask extends Task<DeleteWork> implements Serializable {

  @Override protected int execute(DriverContext driverContext) {
    String mapRDbTableName = work.getMapRDbTableName();
    switch (work.getDeleteOperation()) {
    case DELETE_ALL:
      try (Admin admin = MapRDB.newAdmin()) {
        if (admin.tableExists(mapRDbTableName)) {
          admin.deleteTable(mapRDbTableName);
          admin.createTable(mapRDbTableName);
        }
      }
      return 0;
    case DELETE_ALL_IN_SET:
      try (Table table = MapRDBImpl.getTable(mapRDbTableName)) {
        for (String id : work.getValues()) {
          table.delete(id);
        }
      }
      return 0;
    case DELETE_ALL_EXCEPT_IN_SET:
      try (Table table = MapRDBImpl.getTable(mapRDbTableName); DocumentStream documentStream = table
          .find(buildQueryCondition(work.getValues()))) {
        for (Document document : documentStream) {
          table.delete(document);
        }
        return 0;
      }
    case DELETE_SINGLE:
      try (Table table = MapRDBImpl.getTable(mapRDbTableName)) {
          table.delete(work.getValue());
      }
      return 0;
    }
    return 0;
  }

  @Override public StageType getType() {
    return StageType.MOVE;
  }

  @Override public String getName() {
    return "DELETE";
  }
}
