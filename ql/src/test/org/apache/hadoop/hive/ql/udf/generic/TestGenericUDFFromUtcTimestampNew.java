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

package org.apache.hadoop.hive.ql.udf.generic;

import org.apache.hadoop.hive.common.type.HiveVarchar;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF.DeferredJavaObject;
import org.apache.hadoop.hive.serde2.io.ByteWritable;
import org.apache.hadoop.hive.serde2.io.DateWritableV2;
import org.apache.hadoop.hive.serde2.io.DoubleWritable;
import org.apache.hadoop.hive.serde2.io.HiveDecimalWritable;
import org.apache.hadoop.hive.serde2.io.HiveVarcharWritable;
import org.apache.hadoop.hive.serde2.io.ShortWritable;
import org.apache.hadoop.hive.serde2.io.TimestampWritableV2;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.io.BooleanWritable;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Suite;

import org.apache.hadoop.hive.common.type.Timestamp;
import java.util.Arrays;
import java.util.Collection;
import java.util.TimeZone;

import static org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory.writableBooleanObjectInspector;
import static org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory.writableByteObjectInspector;
import static org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory.writableDateObjectInspector;
import static org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory.writableDoubleObjectInspector;
import static org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory.writableFloatObjectInspector;
import static org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory.writableHiveDecimalObjectInspector;
import static org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory.writableHiveVarcharObjectInspector;
import static org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory.writableIntObjectInspector;
import static org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory.writableLongObjectInspector;
import static org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory.writableShortObjectInspector;
import static org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory.writableStringObjectInspector;
import static org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory.writableTimestampObjectInspector;
import static org.junit.Assert.assertEquals;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    TestGenericUDFFromUtcTimestampNew.TestsWithParameters.class,
    TestGenericUDFFromUtcTimestampNew.TestsWithoutParameters.class })
public class TestGenericUDFFromUtcTimestampNew {

  @RunWith(Parameterized.class)
  public static class TestsWithParameters {
    private DeferredJavaObject[] values;
    private ObjectInspector[] objectInspectors;
    private String expectedString;

    public TestsWithParameters(ObjectInspector timeObjectInspectors,
        Object timeValue, String zoneValues, String expectedString) {

      this.objectInspectors = new ObjectInspector[] { timeObjectInspectors,
          writableStringObjectInspector };
      this.values =
          new DeferredJavaObject[] { new DeferredJavaObject(timeValue),
              new DeferredJavaObject(new Text(zoneValues)) };
      this.expectedString = expectedString;
    }

    @Parameterized.Parameters(name = "{0}")
    public static Collection<Object[]> data() {
      return Arrays.asList(new Object[][] {
          //BOOLEAN
          { writableBooleanObjectInspector, new BooleanWritable(true),
              "Australia/Sydney", "1970-01-01 10:00:00.001" },
          { writableBooleanObjectInspector, new BooleanWritable(false),
              "Australia/Sydney", "1970-01-01 10:00:00" },
          //TINYINT
          { writableByteObjectInspector, new ByteWritable((byte) 0),
              "Australia/Sydney", "1970-01-01 10:00:00" },
          { writableByteObjectInspector, new ByteWritable((byte) 127),
              "Australia/Sydney", "1970-01-01 10:00:00.127" },
          { writableByteObjectInspector, new ByteWritable((byte) -128),
              "Australia/Sydney", "1970-01-01 09:59:59.872" },
          //SMALLINT
          { writableShortObjectInspector, new ShortWritable((short) 0),
              "Australia/Sydney", "1970-01-01 10:00:00" },
          { writableShortObjectInspector, new ShortWritable((short) 32767),
              "Australia/Sydney", "1970-01-01 10:00:32.767" },
          { writableShortObjectInspector, new ShortWritable((short) -32768),
              "Australia/Sydney", "1970-01-01 09:59:27.232" },
          //INT
          { writableIntObjectInspector, new IntWritable(0), "Australia/Sydney",
              "1970-01-01 10:00:00" },
          { writableIntObjectInspector, new IntWritable(2147483647),
              "Australia/Sydney", "1970-01-26 06:31:23.647" },
          { writableIntObjectInspector, new IntWritable(-2147483648),
              "Australia/Sydney", "1969-12-07 13:28:36.352" },
          //BIGINT, 2018-10-07 02:00:00.0 UTC = 1538877600000L
          { writableLongObjectInspector, new LongWritable(0L),
              "Australia/Sydney", "1970-01-01 10:00:00" },
          { writableLongObjectInspector, new LongWritable(1538877600000L),
              "Australia/Sydney", "2018-10-07 13:00:00" },
          //FLOAT
          { writableFloatObjectInspector, new FloatWritable(0.123456789f),
              "Australia/Sydney", "1970-01-01 10:00:00.123456791" },
          { writableFloatObjectInspector,
              new FloatWritable(1538877600.123456789f), "Australia/Sydney",
              "2018-10-07 12:59:28" },
          //DOUBLE
          { writableDoubleObjectInspector, new DoubleWritable(0.123456789),
              "Australia/Sydney", "1970-01-01 10:00:00.123456789" },
          { writableDoubleObjectInspector,
              new DoubleWritable(1538877600.123456789), "Australia/Sydney",
              "2018-10-07 13:00:00.1234567" },
          //DECIMAL
          { writableHiveDecimalObjectInspector,
              new HiveDecimalWritable("0.123456789"), "Australia/Sydney",
              "1970-01-01 10:00:00.123456789" },
          { writableHiveDecimalObjectInspector,
              new HiveDecimalWritable("1538877600.123456789"),
              "Australia/Sydney", "2018-10-07 13:00:00.123456789" },
          //STRING
          { writableStringObjectInspector, new Text("2018-10-07 02:00:00"),
              "Australia/Sydney", "2018-10-07 13:00:00" },
          //VARCHAR
          { writableHiveVarcharObjectInspector, new HiveVarcharWritable(
              new HiveVarchar("1970-01-01 00:00:00.123456789", 55)),
              "Australia/Sydney", "1970-01-01 10:00:00.123456789" },
          { writableHiveVarcharObjectInspector, new HiveVarcharWritable(
              new HiveVarchar("2018-10-07 02:00:00", 55)), "Australia/Sydney",
              "2018-10-07 13:00:00" } });
    }

    @Test
    public void testFromUtcTimestampInDSTZone() throws HiveException {
      TimeZone defaultTz = TimeZone.getDefault();
      TimeZone systemTimeZone = TimeZone.getTimeZone("Australia/Sydney");
      TimeZone.setDefault(systemTimeZone);

      GenericUDFFromUtcTimestampNew udf = new GenericUDFFromUtcTimestampNew();
      udf.initialize(objectInspectors);
      Object result = udf.evaluate(values);
      assertEquals(expectedString, result.toString());

      TimeZone.setDefault(defaultTz);
    }

    @Test
    public void testFromUtcTimestampNotInDSTZone() throws HiveException {
      TimeZone defaultTz = TimeZone.getDefault();
      TimeZone systemTimeZone = TimeZone.getTimeZone("UTC");
      TimeZone.setDefault(systemTimeZone);

      GenericUDFFromUtcTimestampNew udf = new GenericUDFFromUtcTimestampNew();
      udf.initialize(objectInspectors);
      Object result = udf.evaluate(values);
      assertEquals(expectedString, result.toString());

      TimeZone.setDefault(defaultTz);
    }
  }

  // DATE and TIMESTAMP should be created and handled in specific time zone
  // for preventing non-obvious calculations for testing
  public static class TestsWithoutParameters {

    @Test
    public void testTimestamp() throws HiveException {
      TimeZone defaultTz = TimeZone.getDefault();
      TimeZone systemTimeZone = TimeZone.getTimeZone("Australia/Sydney");
      TimeZone.setDefault(systemTimeZone);

      ObjectInspector[] objectInspectors =
          new ObjectInspector[] { writableTimestampObjectInspector,
              writableStringObjectInspector };
      DeferredJavaObject[] values = new DeferredJavaObject[] {
          new DeferredJavaObject(new TimestampWritableV2(
              Timestamp.valueOf("1970-01-01 00:00:00.0"))),
          new DeferredJavaObject(new Text("Australia/Sydney")) };
      GenericUDFFromUtcTimestampNew udf = new GenericUDFFromUtcTimestampNew();
      udf.initialize(objectInspectors);
      Object result = udf.evaluate(values);
      assertEquals("1970-01-01 10:00:00", result.toString());

      TimeZone.setDefault(defaultTz);
    }

    @Test
    public void testDate() throws HiveException {
      TimeZone defaultTz = TimeZone.getDefault();
      TimeZone systemTimeZone = TimeZone.getTimeZone("Australia/Sydney");
      TimeZone.setDefault(systemTimeZone);

      ObjectInspector[] objectInspectors =
          new ObjectInspector[] { writableDateObjectInspector,
              writableStringObjectInspector };
      DeferredJavaObject[] values = new DeferredJavaObject[] {
          new DeferredJavaObject(new DateWritableV2(0)),
          new DeferredJavaObject(new Text("Australia/Sydney")) };
      GenericUDFFromUtcTimestampNew udf = new GenericUDFFromUtcTimestampNew();
      udf.initialize(objectInspectors);
      Object result = udf.evaluate(values);
      assertEquals("1970-01-01 10:00:00", result.toString());

      TimeZone.setDefault(defaultTz);
    }
  }
}
