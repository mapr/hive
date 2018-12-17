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

package org.apache.hadoop.hive.ql.udf;

import junit.framework.TestCase;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.Text;

import java.math.BigDecimal;

public class TestUDFStringFromHBaseBytes extends TestCase {
  public void testHBaseBytesConversion() {
    BigDecimal bigDecimal =
        new BigDecimal("9999999999999999999.8888888888888888888");
    byte[] bytes = Bytes.toBytes(bigDecimal);
    BytesWritable writable = new BytesWritable(bytes);
    UDFStringFromHBaseBytes udf = new UDFStringFromHBaseBytes();
    Text text = udf.evaluate(writable);
    String hbaseDecimal = text.toString();
    assertEquals("9999999999999999999.8888888888888888888", hbaseDecimal);
  }
}
