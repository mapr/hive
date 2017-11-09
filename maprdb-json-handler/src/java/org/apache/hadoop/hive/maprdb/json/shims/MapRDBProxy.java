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

import org.ojai.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import static org.apache.hadoop.hive.maprdb.json.conf.MapRDBConstants.*;

/**
 * Provide a shims between two version of MapRCore 6.x.x and 5.x.x
 */
public class MapRDBProxy {

  private static final Logger LOG = LoggerFactory.getLogger(MapRDBProxy.class);
  private static volatile Class<?> mapRDBClass;
  private static volatile Method mapRDMethod;

  private MapRDBProxy() {
  }

  private static void init() {
    if (mapRDBClass == null) {
      synchronized (MapRDBProxy.class) {
        if (mapRDBClass == null) {
          try {
            mapRDBClass = Class.forName(MAPRDB_CLASS);
            mapRDMethod = mapRDBClass.getDeclaredMethod("newDocument", Map.class);

            if (LOG.isDebugEnabled()) {
              LOG.debug("init() => loaded MapR-DB class: {}, method: {}", mapRDBClass.getName(), mapRDMethod);
            }
          } catch (ClassNotFoundException | NoSuchMethodException e) {
            throw new RuntimeException("Can't load class: " + MAPRDB_CLASS + " Check classpath.");
          }
        }
      }
    }
  }

  public static Document newDocument(Map<String, Object> jsonMap) {
    try {
      init();
      return (Document) mapRDMethod.invoke(null, jsonMap);
    } catch (IllegalAccessException | InvocationTargetException e) {
      throw new RuntimeException(e);
    }
  }
}