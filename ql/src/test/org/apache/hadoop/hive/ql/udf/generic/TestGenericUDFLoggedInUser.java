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
package org.apache.hadoop.hive.ql.udf.generic;

import org.apache.hadoop.hive.conf.HiveConf;
import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.session.SessionState;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

public class TestGenericUDFLoggedInUser {

  private GenericUDFLoggedInUser udf = null;

  @Test
  public void testSystemUser() throws HiveException, IOException {
    String userName = "test_user";
    HiveConf conf = spy(new HiveConf());
    when(conf.getUser()).thenReturn(userName);

    udf = new GenericUDFLoggedInUser();
    SessionState ss = new SessionState(conf);
    SessionState.start(ss);

    ObjectInspector[] arguments = {};
    udf.initialize(arguments);

    GenericUDF.DeferredObject[] evaluateArgs = {};
    assertEquals(userName, udf.evaluate(evaluateArgs).toString());
  }

  @Test(expected = UDFArgumentException.class)
  public void testExpectException() throws IOException, HiveException {
    udf = new GenericUDFLoggedInUser();
    ObjectInspector valueOIOne = PrimitiveObjectInspectorFactory.writableStringObjectInspector;
    ObjectInspector[] arguments = { valueOIOne };
    udf.initialize(arguments);
    udf.close();
  }

}
