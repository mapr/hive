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
package org.apache.hive.common.util;

import org.junit.Assert;
import org.junit.Test;

import java.net.URL;

import static org.apache.hive.common.util.XmlUtil.existsIn;

public class TestXmlUtil {

  @Test
  public void existsInPositiveTest() {
    URL url = getClass().getClassLoader().getResource("hive-site-xml-util-test.xml");
    Assert.assertTrue(existsIn(url, "hive.metastore.sasl.enabled"));
  }

  @Test
  public void existsInNegativeTest() {
    URL url = getClass().getClassLoader().getResource("hive-site-xml-util-test.xml");
    Assert.assertFalse(existsIn(url, "hive.metastore.authentication"));
  }
}
