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
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.hadoop.hive.maprdb.json;

import com.mapr.db.impl.MapRDBImpl;
import com.mapr.db.rowcol.DBValueBuilderImpl;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hive.maprdb.json.serde.MapRDBSerDe;
import org.apache.hadoop.hive.maprdb.json.serde.MapRDBSerDeUtils;
import org.apache.hadoop.hive.maprdb.json.shims.DocumentWritable;
import org.apache.hadoop.hive.serde.serdeConstants;
import org.apache.hadoop.hive.serde2.SerDeException;
import org.apache.hadoop.hive.serde2.objectinspector.*;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.typeinfo.TypeInfoUtils;
import org.junit.Before;
import org.junit.Test;
import org.ojai.Document;
import org.ojai.Value;
import org.ojai.types.ODate;
import org.ojai.types.OTimestamp;

import java.nio.ByteBuffer;
import org.apache.hadoop.hive.common.type.Date;
import org.apache.hadoop.hive.common.type.Timestamp;
import java.util.*;

import static org.apache.hadoop.hive.maprdb.json.conf.MapRDBConstants.ID_KEY;
import static org.apache.hadoop.hive.maprdb.json.conf.MapRDBConstants.MAPRDB_COLUMN_ID;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

public class MapRDBSerDeTest {

  private MapRDBSerDe serDe;

  @Before
  public void init() {
    serDe = new MapRDBSerDe();
  }

  @Test
  public void testString() throws SerDeException {
    String columnNames = "s";
    String columnTypes = "string";
    String value = "value";
    Value ojaiValue = DBValueBuilderImpl.KeyValueBuilder.initFrom(value);

    Object result = deserialize(serDe, columnNames, columnTypes, ojaiValue);
    assertThat(value, equalTo(result));

    ObjectInspector innerInspector = PrimitiveObjectInspectorFactory.getPrimitiveObjectInspectorFromClass(String.class);
    Document document = MapRDBImpl.newDocument();
    document.set(columnNames, value);

    Object serialized = serialize(columnNames, innerInspector, value, serDe);
    assertThat(new DocumentWritable(document), equalTo(serialized));
  }

  @Test
  public void testInt() throws SerDeException {
    String columnNames = "i";
    String columnTypes = "int";
    Integer value = 1234;
    Value ojaiValue = DBValueBuilderImpl.KeyValueBuilder.initFrom(value);

    Object result = deserialize(serDe, columnNames, columnTypes, ojaiValue);
    assertThat(value, equalTo(result));

    ObjectInspector innerInspector = PrimitiveObjectInspectorFactory.getPrimitiveObjectInspectorFromClass(Integer.class);
    Document document = MapRDBImpl.newDocument();
    document.set(columnNames, value);

    Object serialized = serialize(columnNames, innerInspector, value, serDe);
    assertThat(new DocumentWritable(document), equalTo(serialized));
  }

  @Test
  public void testBoolean() throws SerDeException {
    String columnNames = "bool";
    String columnTypes = "boolean";
    Boolean value = false;
    Value ojaiValue = DBValueBuilderImpl.KeyValueBuilder.initFrom(value);

    Object result = deserialize(serDe, columnNames, columnTypes, ojaiValue);
    assertThat(value, equalTo(result));

    ObjectInspector innerInspector = PrimitiveObjectInspectorFactory.getPrimitiveObjectInspectorFromClass(Boolean.class);
    Document document = MapRDBImpl.newDocument();
    document.set(columnNames, value);

    Object serialized = serialize(columnNames, innerInspector, value, serDe);
    assertThat(new DocumentWritable(document), equalTo(serialized));
  }

  @Test
  public void testDouble() throws SerDeException {
    String columnNames = "doub";
    String columnTypes = "double";
    Double value = 1.1D;
    Value ojaiValue = DBValueBuilderImpl.KeyValueBuilder.initFrom(value);

    Object result = deserialize(serDe, columnNames, columnTypes, ojaiValue);
    assertThat(value, equalTo(result));

    ObjectInspector innerInspector = PrimitiveObjectInspectorFactory.getPrimitiveObjectInspectorFromClass(Double.class);
    Document document = MapRDBImpl.newDocument();
    document.set(columnNames, value);

    Object serialized = serialize(columnNames, innerInspector, value, serDe);
    assertThat(new DocumentWritable(document), equalTo(serialized));
  }

  @Test
  public void testFloat() throws SerDeException {
    String columnNames = "fl";
    String columnTypes = "float";
    Float value = 1.1F;
    Value ojaiValue = DBValueBuilderImpl.KeyValueBuilder.initFrom(value);

    Object result = deserialize(serDe, columnNames, columnTypes, ojaiValue);
    assertThat(value, equalTo(result));

    ObjectInspector innerInspector = PrimitiveObjectInspectorFactory.getPrimitiveObjectInspectorFromClass(Float.class);
    Document document = MapRDBImpl.newDocument();
    document.set(columnNames, value);

    Object serialized = serialize(columnNames, innerInspector, value, serDe);
    assertThat(new DocumentWritable(document), equalTo(serialized));
  }

  @Test
  public void testList() throws SerDeException {
    String columnNames = "a";
    String columnTypes = "array<string>";

    String innerValueOne = "first";
    String innerValueTwo = "second";

    List<String> expected = new ArrayList<>();
    expected.add(innerValueOne);
    expected.add(innerValueTwo);
    Value ojaiValue = DBValueBuilderImpl.KeyValueBuilder.initFrom(expected);

    Object actual = deserialize(serDe, columnNames, columnTypes, ojaiValue);
    assertTrue(actual instanceof List);

    List<String> result = (List<String>) actual;
    assertTrue(result.containsAll(expected));

    ObjectInspector innerInspector = PrimitiveObjectInspectorFactory.getPrimitiveObjectInspectorFromClass(String.class);
    ListObjectInspector listInspector = ObjectInspectorFactory.getStandardListObjectInspector(innerInspector);

    Document document = MapRDBImpl.newDocument();
    document.set(columnNames, expected);

    Object serialized = serialize(columnNames, listInspector, expected, serDe);
    assertThat(new DocumentWritable(document), equalTo(serialized));
  }

  @Test
  public void testMap() throws SerDeException {
    String columnNames = "m";
    String columnTypes = "map<string,int>";

    Map<String, Integer> map = new HashMap<>();
    String oneKey = "one";
    int oneValue = 10;
    map.put(oneKey, oneValue);
    String twoKey = "two";
    int twoValue = 20;
    map.put(twoKey, twoValue);
    Value ojaiValue = DBValueBuilderImpl.KeyValueBuilder.initFrom(map);

    Object result = deserialize(serDe, columnNames, columnTypes, ojaiValue);
    assertThat(map, equalTo(result));

    ObjectInspector keyInspector =
      PrimitiveObjectInspectorFactory.getPrimitiveObjectInspectorFromClass(String.class);
    ObjectInspector valueInspector =
      PrimitiveObjectInspectorFactory.getPrimitiveObjectInspectorFromClass(Integer.class);
    MapObjectInspector mapInspector =
      ObjectInspectorFactory.getStandardMapObjectInspector(keyInspector, valueInspector);

    Document document = MapRDBImpl.newDocument();
    document.set(columnNames, map);

    Object serialized = serialize(columnNames, mapInspector, map, serDe);
    assertThat(new DocumentWritable(document), equalTo(serialized));
  }

  @Test
  public void testStruct() throws SerDeException {
    String columnNames = "doc";
    String columnTypes = "struct<one:int,two:string>";

    Document value = MapRDBImpl.newDocument();
    int oneValue = 10;
    String twoValue = "key";
    value.set("one", oneValue);
    value.set("two", twoValue);

    // Structs == arrays in hive
    List<Object> returned = new ArrayList<>();
    returned.add(oneValue);
    returned.add(twoValue);

    Object result = deserialize(serDe, columnNames, columnTypes, value);
    List<Object> ar = (ArrayList<Object>) result;

    assertEquals(ar.size(), value.size());

    assertEquals(ar.get(0), value.getInt("one"));
    assertEquals(ar.get(1), value.getString("two"));

    List<ObjectInspector> innerInspectorList = new ArrayList<>();
    innerInspectorList.add(PrimitiveObjectInspectorFactory.getPrimitiveObjectInspectorFromClass(Integer.class));
    innerInspectorList.add(PrimitiveObjectInspectorFactory.getPrimitiveObjectInspectorFromClass(String.class));

    List<String> innerFieldsList = new ArrayList<>();
    innerFieldsList.add("one");
    innerFieldsList.add("two");

    StructObjectInspector structInspector = ObjectInspectorFactory.
      getStandardStructObjectInspector(innerFieldsList, innerInspectorList);
    StructObjectInspector oi = createObjectInspector(columnNames, structInspector);

    Document document = MapRDBImpl.newDocument();
    document.set(columnNames, value);

    // structs are stored as array inside hive
    ArrayList<Object> obj = new ArrayList<>();
    obj.add(returned);

    Object serialized = serDe.serialize(obj, oi);
    assertThat(new DocumentWritable(document), equalTo(serialized));
  }

  @Test
  public void testDate() throws SerDeException {
    String columnNames = "d";
    String columnTypes = "date";
    long dateNow = System.currentTimeMillis();

    Date sqlDate = Date.ofEpochMilli(dateNow);
    ODate value = new ODate(dateNow);
    Value ojaiValue = DBValueBuilderImpl.KeyValueBuilder.initFrom(value);

    Object result = deserialize(serDe, columnNames, columnTypes, ojaiValue);
    assertEquals(sqlDate.toString(), result.toString());

    ObjectInspector innerInspector = PrimitiveObjectInspectorFactory
      .getPrimitiveObjectInspectorFromClass(Date.class);
    Document document = MapRDBImpl.newDocument();
    document.set(columnNames, value);

    Object serialized = serialize(columnNames, innerInspector, sqlDate, serDe);
    assertThat(new DocumentWritable(document), equalTo(serialized));
  }

  @Test
  public void testTimeStamp() throws SerDeException {
    String columnNames = "ts";
    String columnTypes = "timestamp";
    long dateNow = System.currentTimeMillis();

    Timestamp timestamp = Timestamp.ofEpochMilli(dateNow);
    OTimestamp value = new OTimestamp(dateNow);
    Value ojaiValue = DBValueBuilderImpl.KeyValueBuilder.initFrom(value);

    Object result = deserialize(serDe, columnNames, columnTypes, ojaiValue);
    assertEquals(result, timestamp);

    ObjectInspector innerInspector = PrimitiveObjectInspectorFactory
      .getPrimitiveObjectInspectorFromClass(Timestamp.class);
    Document document = MapRDBImpl.newDocument();
    document.set(columnNames, value);

    Object serialized = serialize(columnNames, innerInspector, timestamp, serDe);
    assertThat(new DocumentWritable(document), equalTo(serialized));
  }

  @Test
  public void testTinyInt() throws SerDeException {
    String columnNames = "ti";
    String columnTypes = "tinyint";
    byte value = 12;
    Value ojaiValue = DBValueBuilderImpl.KeyValueBuilder.initFrom(value);

    Object result = deserialize(serDe, columnNames, columnTypes, ojaiValue);
    assertEquals(value, result);

    ObjectInspector innerInspector = PrimitiveObjectInspectorFactory.getPrimitiveObjectInspectorFromClass(Byte.class);
    Document document = MapRDBImpl.newDocument();
    document.set(columnNames, value);

    Object serialized = serialize(columnNames, innerInspector, value, serDe);
    assertThat(new DocumentWritable(document), equalTo(serialized));
  }

  @Test
  public void testSmallInt() throws SerDeException {
    String columnNames = "si";
    String columnTypes = "smallint";
    short value = 12;
    Value ojaiValue = DBValueBuilderImpl.KeyValueBuilder.initFrom(value);

    Object result = deserialize(serDe, columnNames, columnTypes, ojaiValue);
    assertEquals(value, result);

    ObjectInspector innerInspector = PrimitiveObjectInspectorFactory.getPrimitiveObjectInspectorFromClass(Short.class);
    Document document = MapRDBImpl.newDocument();
    document.set(columnNames, value);

    Object serialized = serialize(columnNames, innerInspector, value, serDe);
    assertThat(new DocumentWritable(document), equalTo(serialized));
  }

  @Test
  public void testBigInt() throws SerDeException {
    String columnNames = "bi";
    String columnTypes = "bigint";
    long value = 18273993L;
    Value ojaiValue = DBValueBuilderImpl.KeyValueBuilder.initFrom(value);

    Object result = deserialize(serDe, columnNames, columnTypes, ojaiValue);
    assertEquals(value, result);

    ObjectInspector innerInspector = PrimitiveObjectInspectorFactory.getPrimitiveObjectInspectorFromClass(Long.class);
    Document document = MapRDBImpl.newDocument();
    document.set(columnNames, value);

    Object serialized = serialize(columnNames, innerInspector, value, serDe);
    assertThat(new DocumentWritable(document), equalTo(serialized));
  }

  @Test
  public void testBinary() throws SerDeException {
    String columnNames = "bin";
    String columnTypes = "binary";
    byte[] bytes = {1, 2, 3, 125, 37, 64};
    ByteBuffer value = ByteBuffer.wrap(bytes);

    Value ojaiValue = DBValueBuilderImpl.KeyValueBuilder.initFrom(value);

    Object result = deserialize(serDe, columnNames, columnTypes, ojaiValue);
    assertArrayEquals(bytes, (byte[]) result);

    ObjectInspector innerInspector = PrimitiveObjectInspectorFactory.getPrimitiveObjectInspectorFromClass(byte[].class);
    Document document = MapRDBImpl.newDocument();
    document.set(columnNames, bytes);

    Object serialized = serialize(columnNames, innerInspector, bytes, serDe);
    assertThat(new DocumentWritable(document), equalTo(serialized));
  }

  @Test
  public void testNullValue() throws SerDeException {
    String columnNames = "s";
    String columnTypes = "string";
    String value = "null";
    Value ojaiValue = DBValueBuilderImpl.KeyValueBuilder.initFromNull();

    Object result = deserialize(serDe, columnNames, columnTypes, ojaiValue);
    assertNull(result);

    ObjectInspector innerInspector = PrimitiveObjectInspectorFactory.getPrimitiveObjectInspectorFromClass(String.class);
    Document document = MapRDBImpl.newDocument();
    document.set(columnNames, value);

    Object serialized = serialize(columnNames, innerInspector, value, serDe);
    assertThat(new DocumentWritable(document), equalTo(serialized));
  }

  private Object serialize(String columnNames, ObjectInspector innerInspector, Object value, MapRDBSerDe serDe)
    throws SerDeException {
    StructObjectInspector oi = createObjectInspector(columnNames, innerInspector);
    ArrayList<Object> obj = new ArrayList<>();
    obj.add(value);
    return serDe.serialize(obj, oi);
  }

  private Object deserialize(MapRDBSerDe serDe, String columnNames, String columnTypes, Object value)
    throws SerDeException {
    Properties tblProperties = new Properties();
    tblProperties.setProperty(MAPRDB_COLUMN_ID, ID_KEY);
    tblProperties.setProperty(serdeConstants.LIST_COLUMNS, columnNames);
    tblProperties.setProperty(serdeConstants.LIST_COLUMN_TYPES, columnTypes);

    serDe.initialize(new Configuration(), tblProperties);
    return MapRDBSerDeUtils.deserializeField(value, TypeInfoUtils.getTypeInfosFromTypeString(columnTypes).get(0));
  }

  private StructObjectInspector createObjectInspector(final String columnNames, final ObjectInspector oi) {
    ArrayList<String> fieldNames = new ArrayList<>();
    fieldNames.add(columnNames);
    ArrayList<ObjectInspector> fieldInspectors = new ArrayList<>();
    fieldInspectors.add(oi);

    return ObjectInspectorFactory.getStandardStructObjectInspector(fieldNames, fieldInspectors);
  }
}