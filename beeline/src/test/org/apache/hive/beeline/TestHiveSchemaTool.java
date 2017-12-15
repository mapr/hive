/*
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
package org.apache.hive.beeline;

import org.apache.hadoop.hive.conf.HiveConf;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class TestHiveSchemaTool {

  private String scriptFile = getFileFromClasspath("someScript.sql");

  private static HiveConf hiveConf = new HiveConf();
  static {hiveConf.set("fs.default.name", "file:///");}
  private HiveSchemaTool.CommandBuilder builder;
  private String pasword = "reallySimplePassword";

  @Before
  public void setup() {
    builder = new HiveSchemaTool.CommandBuilder(hiveConf, null, null, "testUser", pasword, scriptFile);
  }

  @Test
  public void shouldReturnStrippedPassword() throws IOException {
    assertFalse(builder.buildToLog().contains(pasword));
  }

  @Test
  public void shouldReturnActualPassword() throws IOException {
    String[] strings = builder.buildToRun();
    assertTrue(Arrays.asList(strings).contains(pasword));
  }

  private static String getFileFromClasspath(String name) {
    URL url = ClassLoader.getSystemResource(name);
    if (url == null) {
      throw new IllegalArgumentException("Could not find " + name);
    }
    return url.getPath();
  }
}
