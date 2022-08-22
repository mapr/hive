/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to you under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.hive.conftool;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.attribute.GroupPrincipal;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ConfCommonUtilitiesTest {

  @Test
  public void changeGroupTest() throws IOException {
    String testDir = System.getProperty("test.tmp.dir") + File.separator + "test";
    String adminGroupName = System.getProperty("mapr.user");
    File directory = new File(testDir);
    if (directory.mkdir()) {
      GroupPrincipal adminGroup = ConfCommonUtilities.findGroupByName(adminGroupName);
      ConfCommonUtilities.changeGroup(testDir, adminGroup);
      assertThat(adminGroup, is(ConfCommonUtilities.readGroup(testDir)));
    }
  }
}
