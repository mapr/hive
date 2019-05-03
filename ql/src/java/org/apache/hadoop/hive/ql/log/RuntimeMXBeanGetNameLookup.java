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

package org.apache.hadoop.hive.ql.log;

import java.lang.management.ManagementFactory;

import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.lookup.AbstractLookup;
import org.apache.logging.log4j.core.lookup.StrLookup;

/**
 * Returns PID from RuntimeMXBean.
 *
 * @see java.lang.management.RuntimeMXBean#getName()
 */
@Plugin(name = "rmx", category = StrLookup.CATEGORY)
public class RuntimeMXBeanGetNameLookup extends AbstractLookup {
  private final String pid;

  public RuntimeMXBeanGetNameLookup() {
    pid = ManagementFactory.getRuntimeMXBean().getName();
  }

  @Override
  public String lookup(LogEvent logEvent, String s) {
    return this.pid;
  }
}
