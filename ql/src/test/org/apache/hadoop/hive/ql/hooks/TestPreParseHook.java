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
package org.apache.hadoop.hive.ql.hooks;

import static org.apache.hadoop.hive.ql.hooks.HookUtils.getHooks;

import org.apache.hadoop.hive.conf.HiveConf;
import org.apache.hadoop.hive.ql.Context;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.List;

public class TestPreParseHook {

  @Test
  public void testWithoutHook() throws Exception {
    String command = "query";
    Context context = Mockito.mock(Context.class);
    HiveConf hiveConf = new HiveConf();
    List<PreParseHook> preParseHooks =
        getHooks(hiveConf, HiveConf.ConfVars.HIVE_PRE_PARSE_HOOKS,
            PreParseHook.class);
    Assert.assertEquals(0, preParseHooks.size());
    for (PreParseHook preParseHook : preParseHooks) {
      command = preParseHook.getCustomCommand(context, command);
    }
    String expected = "query";
    Assert.assertEquals(expected, command);
  }

  @Test
  public void testPareParseHookExtraction() throws Exception {
    HiveConf hiveConf = new HiveConf();
    hiveConf.set(HiveConf.ConfVars.HIVE_PRE_PARSE_HOOKS.varname,
        "org.apache.hadoop.hive.ql.hooks.TestPreParseHook$SimpleCustomSQL,"
            + "org.apache.hadoop.hive.ql.hooks.TestPreParseHook$BrokenPreParseHook");
    List<PreParseHook> preParseHooks =
        getHooks(hiveConf, HiveConf.ConfVars.HIVE_PRE_PARSE_HOOKS,
            PreParseHook.class);
    Assert.assertEquals(2, preParseHooks.size());
  }

  @Test
  public void testPreParseHook() throws Exception {
    String command = "SLCT %for id = 1 to 5% query";
    Context context = Mockito.mock(Context.class);
    HiveConf hiveConf = new HiveConf();
    hiveConf.set(HiveConf.ConfVars.HIVE_PRE_PARSE_HOOKS.varname,
        "org.apache.hadoop.hive.ql.hooks.TestPreParseHook$SimpleCustomSQL");
    List<PreParseHook> preParseHooks =
        getHooks(hiveConf, HiveConf.ConfVars.HIVE_PRE_PARSE_HOOKS,
            PreParseHook.class);
    Assert.assertEquals(1, preParseHooks.size());
    for (PreParseHook preParseHook : preParseHooks) {
      command = preParseHook.getCustomCommand(context, command);
    }
    String expected = "SELECT %for id = 1 to 5% query";
    Assert.assertEquals(expected, command);
  }

  @Test
  public void testBrokenHook() {
    String command = "query";
    Context context = Mockito.mock(Context.class);
    HiveConf hiveConf = new HiveConf();
    hiveConf.set(HiveConf.ConfVars.HIVE_PRE_PARSE_HOOKS.varname,
        "org.apache.hadoop.hive.ql.hooks.TestPreParseHook$BrokenPreParseHook");
    try {
      List<PreParseHook> preParseHooks = getHooks(hiveConf, HiveConf.ConfVars.HIVE_PRE_PARSE_HOOKS,
          PreParseHook.class);
      for (PreParseHook preParseHook : preParseHooks) {
        command = preParseHook.getCustomCommand(context, command);
      }
    } catch (Exception e) {
      Assert.assertEquals(e.getMessage(), "broken hook");
    }
  }

  public static class SimpleCustomSQL implements PreParseHook {

    public String getCustomCommand(Context context, String command)
        throws Exception {
      return command.replace("SLCT", "SELECT");
    }

  }

  public static class BrokenPreParseHook implements PreParseHook {

    public String getCustomCommand(Context context, String command)
        throws Exception {
      throw new Exception("broken hook");
    }
  }
}
