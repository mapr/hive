/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.hadoop.hive.ql.log;

import org.apache.logging.log4j.ThreadContext;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.pattern.ConverterKeys;
import org.apache.logging.log4j.core.pattern.LogEventPatternConverter;

import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;

/**
 * LogEvent converter that converts ${ctx:pid} pattern to <process-id>@<hostname> information
 * obtained at runtime.
 *
 * Example usage:
 *   <Routing name="RoutingAppender">
 *         <Routes pattern="${ctx:pid}">
 *           ...
 *         </Routes>
 *   </Routing>
 *
 * Will generate output file with name containing <process-id>@<hostname> like below
 * test.log.95232@localhost.gz
 */

@Plugin(name = "ProcessIdPatternConverter", category = "Converter")
@ConverterKeys({ "pid", "processId" })
public final class ProcessIdPatternConverter extends LogEventPatternConverter {
  private final String pid;

  private ProcessIdPatternConverter(String[] options) {
    super("Process ID", "pid");
    String temp = options.length > 0 ? options[0] : "???";
    try {
      // likely works on most platforms
      temp = ManagementFactory.getRuntimeMXBean().getName();
    } catch (final Exception ex) {
      try {
        // try a Linux-specific way
        temp = new File("/proc/self").getCanonicalFile().getName();
      } catch (final IOException ignored) {}
    }
    pid = temp;
    ThreadContext.put("pid", pid);
  }

  /**
   * Obtains an instance of ProcessIdPatternConverter.
   *
   * @param options users may specify a default like {@code %pid{NOPID} }
   * @return instance of ProcessIdPatternConverter.
   */
  public static ProcessIdPatternConverter newInstance(final String[] options) {
    return new ProcessIdPatternConverter(options);
  }

  @Override
  public void format(final LogEvent event, final StringBuilder toAppendTo) {
    toAppendTo.append(pid);
  }
}

