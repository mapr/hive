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
package org.apache.hadoop.hive.maprdb.json.serde;

import org.apache.hadoop.hive.maprdb.json.shims.MapRDBProxy;
import org.apache.hadoop.hive.serde2.io.DateWritable;
import org.apache.hadoop.hive.serde2.io.TimestampWritable;
import org.apache.hadoop.hive.serde2.objectinspector.ListObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.MapObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.PrimitiveObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.StructField;
import org.apache.hadoop.hive.serde2.objectinspector.StructObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.UnionObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.BinaryObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.BooleanObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.ByteObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.DateObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.DoubleObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.FloatObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.IntObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.LongObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.ShortObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.StringObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.TimestampObjectInspector;
import org.apache.hadoop.hive.serde2.typeinfo.ListTypeInfo;
import org.apache.hadoop.hive.serde2.typeinfo.MapTypeInfo;
import org.apache.hadoop.hive.serde2.typeinfo.PrimitiveTypeInfo;
import org.apache.hadoop.hive.serde2.typeinfo.StructTypeInfo;
import org.apache.hadoop.hive.serde2.typeinfo.TypeInfo;
import org.apache.hadoop.hive.serde2.typeinfo.UnionTypeInfo;
import org.apache.hadoop.io.BytesWritable;
import org.ojai.Document;
import org.ojai.Value;
import org.ojai.types.ODate;
import org.ojai.types.OTimestamp;

import java.nio.ByteBuffer;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.String.format;

public final class MapRDBSerDeUtils {
  private MapRDBSerDeUtils(){}

  public static Object deserializeField(Object value, TypeInfo valueTypeInfo) {
    Object result = null;

    if (value != null) {
      switch (valueTypeInfo.getCategory()) {
        case LIST: {
          result = deserializeList(value, (ListTypeInfo) valueTypeInfo);
          break;
        }
        case MAP: {
          result = deserializeMap(value, (MapTypeInfo) valueTypeInfo);
          break;
        }
        case PRIMITIVE: {
          result = deserializePrimitive(value, (PrimitiveTypeInfo) valueTypeInfo);
          break;
        }
        case STRUCT: {
          result = deserializeStruct(value, (StructTypeInfo) valueTypeInfo);
          break;
        }
        case UNION: {
          result = deserializeUnion(value, (UnionTypeInfo) valueTypeInfo);
          break;
        }
        default:
          throw new IllegalArgumentException(format("Can't deserialize '%s'", value.getClass().getSimpleName()));
      }
    }
    return result;
  }

  public static Document serializeStruct(Object obj, StructObjectInspector soi, List<String> columnNames, Map<String, String> mappings) {
    if (null == obj) return null;

    List<? extends StructField> fields = soi.getAllStructFieldRefs();
    Map<String, Object> jsonMap = new HashMap<>(fields.size());

    for (int i = 0; i < fields.size(); i++) {
      StructField sf = fields.get(i);
      Object data = soi.getStructFieldData(obj, sf);

      if (null != data) {
        jsonMap.put(
          getSerializedFieldName(columnNames, i, sf, mappings),
          serializeField(data, sf.getFieldObjectInspector(), mappings));
      }
    }
    return MapRDBProxy.newDocument(jsonMap);
  }

  private static Object deserializePrimitive(Object value, PrimitiveTypeInfo valueTypeInfo) {
    switch (valueTypeInfo.getPrimitiveCategory()) {
      case BINARY:
        return ((Value) value).getBinary().array();
      case BOOLEAN:
        return ((Value) value).getBoolean();
      case DOUBLE:
        return ((Value) value).getDouble();
      case FLOAT:
        return ((Value) value).getFloat();
      case INT:
        return ((Value) value).getInt();
      case STRING:
        return ((Value) value).getString();
      case DATE:
        return Date.valueOf(((Value) value).getDate().toString());
      case TIMESTAMP:
        return new Timestamp(((Value) value).getTimestampAsLong());
      case BYTE: // tinyint
        return ((Value) value).getByte();
      case SHORT: //smallint
        return ((Value) value).getShort();
      case LONG: //bigint
        return ((Value) value).getLong();
      default:
        throw new IllegalArgumentException(format("Can't deserialize '%s'", value.getClass().getSimpleName()));
    }
  }


  private static Object deserializeUnion(Object value, UnionTypeInfo valueTypeInfo) {
    return deserializeField(value, valueTypeInfo);
  }

  private static Object deserializeStruct(Object value, StructTypeInfo valueTypeInfo) {
    Document val = (Document) value;

    List<String> structNames = valueTypeInfo.getAllStructFieldNames();
    List<TypeInfo> structTypes = valueTypeInfo.getAllStructFieldTypeInfos();

    List<Object> struct = new ArrayList<>(structNames.size());

    for (int i = 0; i < structNames.size(); i++) {
      String fieldName = structNames.get(i).toLowerCase();
      struct.add(deserializeField(val.getValue(fieldName), structTypes.get(i)));
    }
    return struct;
  }

  private static Object deserializeMap(Object value, MapTypeInfo valueTypeInfo) {
    Map<String, Object> map = ((Value) value).getMap();

    TypeInfo mapValueTypeInfo = valueTypeInfo.getMapValueTypeInfo();
    Map<String, Object> result = new HashMap<>(map.size());

    for (Map.Entry<String, Object> entry : map.entrySet()) {
      if (mapValueTypeInfo.getCategory() == ObjectInspector.Category.PRIMITIVE) {
        result.put(entry.getKey(), entry.getValue());
      } else {
        result.put(entry.getKey(), deserializeField(entry.getValue(), mapValueTypeInfo));
      }
    }
    return result;
  }

  private static Object deserializeList(Object value, ListTypeInfo valueTypeInfo) {
    List<Object> list = ((Value) value).getList();

    TypeInfo listElemTypeInfo = valueTypeInfo.getListElementTypeInfo();
    List<Object> results = new ArrayList<>(list.size());

    for (int i = 0; i < list.size(); i++) {
      if (listElemTypeInfo.getCategory() == ObjectInspector.Category.PRIMITIVE) {
        results.add(i, list.get(i));
      } else {
        results.add(i, deserializeField(list.get(i), listElemTypeInfo));
      }
    }
    return results;
  }


  private static String getSerializedFieldName(List<String> columnNames, int pos, StructField sf, Map<String, String> mappings) {
    String n = (columnNames == null ? sf.getFieldName() : columnNames.get(pos));
    return mappings.containsKey(n) ? mappings.get(n) : n;
  }

  private static Object serializeField(Object o, ObjectInspector oi, Map<String, String> mappings) {
    if (o == null) return null;

    Object result;
    switch (oi.getCategory()) {
      case PRIMITIVE: {
        PrimitiveObjectInspector poi = (PrimitiveObjectInspector) oi;
        switch (poi.getPrimitiveCategory()) {
          case BINARY: {
            BytesWritable bw = ((BinaryObjectInspector) poi).getPrimitiveWritableObject(o);
            result = ByteBuffer.wrap(bw.getBytes());
            break;
          }
          case BOOLEAN: {
            result = (((BooleanObjectInspector) poi).get(o) ? Boolean.TRUE : Boolean.FALSE);
            break;
          }
          case INT: {
            result = (((IntObjectInspector) poi).get(o));
            break;
          }
          case BYTE: { // tinyint
            result = (((ByteObjectInspector) poi).get(o));
            break;
          }
          case SHORT: { // smallint
            result = (((ShortObjectInspector) poi).get(o));
            break;
          }
          case LONG: { // bigint
            result = (((LongObjectInspector) poi).get(o));
            break;
          }
          case FLOAT: {
            result = ((FloatObjectInspector) poi).get(o);
            break;
          }
          case DOUBLE: {
            result = (((DoubleObjectInspector) poi).get(o));
            break;
          }
          case STRING: {
            result = (((StringObjectInspector) poi).getPrimitiveJavaObject(o));
            break;
          }
          case DATE: {
            DateWritable dw = ((DateObjectInspector) poi).getPrimitiveWritableObject(o);
            result = new ODate(dw.get().getTime());
            break;
          }
          case TIMESTAMP: {
            TimestampWritable tsw = ((TimestampObjectInspector) poi).getPrimitiveWritableObject(o);
            result = new OTimestamp(tsw.getTimestamp().getTime());
            break;
          }
          default:
            throw new IllegalArgumentException(format("Unknown primitive type: '%s'", poi.getPrimitiveCategory()));
        }
      }
      break;

      case LIST: {
        result = serializeList(o, (ListObjectInspector) oi, mappings);
        break;
      }
      case MAP: {
        result = serializeMap(o, (MapObjectInspector) oi, mappings);
        break;
      }
      case STRUCT: {
        result = serializeStruct(o, (StructObjectInspector) oi, null, mappings);
        break;
      }
      case UNION: {
        result = serializeUnion(o, (UnionObjectInspector) oi, mappings);
        break;
      }
      default:
        throw new IllegalArgumentException("Unknown type in ObjectInspector!");
    }
    return result;
  }

  private static Object serializeList(Object obj, ListObjectInspector loi, Map<String, String> mappings) {
    if (obj == null) return null;

    List<?> field = loi.getList(obj);
    List<Object> ar = new ArrayList<>();

    for (Object elem : field) {
      ar.add(serializeField(elem, loi.getListElementObjectInspector(), mappings));
    }
    return ar;
  }

  private static Object serializeUnion(Object obj, UnionObjectInspector oi, Map<String, String> mappings) {
    if (obj == null) return null;
    return serializeField(obj, oi.getObjectInspectors().get(oi.getTag(obj)), mappings);
  }

  private static Object serializeMap(Object obj, MapObjectInspector moi, Map<String, String> mappings) {
    if (obj == null) return null;

    Map<String, Object> map = new HashMap<>();
    Map m = moi.getMap(obj);

    for (Object k : m.keySet()) {
      map.put(serializeField(k, moi.getMapKeyObjectInspector(), mappings).toString(),
        serializeField(m.get(k), moi.getMapValueObjectInspector(), mappings));
    }
    return map;
  }
}
