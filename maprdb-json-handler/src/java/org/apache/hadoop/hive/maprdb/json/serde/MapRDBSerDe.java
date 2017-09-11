/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.hadoop.hive.maprdb.json.serde;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hive.maprdb.json.shims.DocumentWritable;
import org.apache.hadoop.hive.maprdb.json.shims.MapRDBProxy;
import org.apache.hadoop.hive.serde.serdeConstants;
import org.apache.hadoop.hive.serde2.AbstractSerDe;
import org.apache.hadoop.hive.serde2.SerDeException;
import org.apache.hadoop.hive.serde2.SerDeStats;
import org.apache.hadoop.hive.serde2.io.DateWritable;
import org.apache.hadoop.hive.serde2.io.TimestampWritable;
import org.apache.hadoop.hive.serde2.objectinspector.*;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.*;
import org.apache.hadoop.hive.serde2.typeinfo.*;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.Writable;
import org.ojai.Document;
import org.ojai.Value;
import org.ojai.types.ODate;
import org.ojai.types.OTimestamp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.*;

import static java.lang.String.format;
import static org.apache.hadoop.hive.maprdb.json.conf.MapRDBConstants.*;

public class MapRDBSerDe extends AbstractSerDe {
  private static final Logger LOG = LoggerFactory.getLogger(MapRDBSerDe.class);

  private List<String> columnNames;
  public List<TypeInfo> columnTypes;
  private StructTypeInfo rowTypeInfo;
  private StructObjectInspector objectInspector;

  // A row represents a row in the Hive table
  private List<Object> row = new ArrayList<>();

  private Map<String, String> mappings;

  @Override
  public void initialize(Configuration conf, Properties tbl) throws SerDeException {
    LOG.info("Initializing SerDe");
    String columnNameProperty = tbl.getProperty(serdeConstants.LIST_COLUMNS);
    String columnTypeProperty = tbl.getProperty(serdeConstants.LIST_COLUMN_TYPES);

    mappings = new HashMap<>();
    mappings.put(tbl.getProperty((MAPRDB_COLUMN_ID)), ID_KEY);

    if (LOG.isDebugEnabled()) {
      LOG.debug("columns " + columnNameProperty + " types " + columnTypeProperty);
    }

    // all table column names
    if (columnNameProperty.length() == 0) {
      columnNames = new ArrayList<>();
    } else {
      columnNames = Arrays.asList(columnNameProperty.split(","));
    }

    // all column types
    if (columnTypeProperty.length() == 0) {
      columnTypes = new ArrayList<>();
    } else {
      columnTypes = TypeInfoUtils.getTypeInfosFromTypeString(columnTypeProperty);
    }
    assert (columnNames.size() == columnTypes.size());

    // Create row related objects
    rowTypeInfo = (StructTypeInfo) TypeInfoFactory.getStructTypeInfo(columnNames, columnTypes);
    objectInspector = (StructObjectInspector) TypeInfoUtils.getStandardJavaObjectInspectorFromTypeInfo(rowTypeInfo);
  }

  @Override
  public Writable serialize(Object obj, ObjectInspector objInspector) throws SerDeException {
    if (objInspector.getCategory() != ObjectInspector.Category.STRUCT) {
      throw new SerDeException(getClass().toString()
              + " can only serialize struct types, but we got: "
              + objInspector.getTypeName());
    }

    Document doc = serializeStruct(obj, (StructObjectInspector) objInspector, columnNames);
    return new DocumentWritable(doc);
  }

  private Document serializeStruct(Object obj, StructObjectInspector soi, List<String> columnNames) {
    if (null == obj) return null;

    List<? extends StructField> fields = soi.getAllStructFieldRefs();
    Map<String, Object> jsonMap = new HashMap<>(fields.size());

    for (int i = 0; i < fields.size(); i++) {
      StructField sf = fields.get(i);
      Object data = soi.getStructFieldData(obj, sf);

      if (null != data) {
        jsonMap.put(
                getSerializedFieldName(columnNames, i, sf),
                serializeField(data, sf.getFieldObjectInspector()));
      }
    }
    return MapRDBProxy.newDocument(jsonMap);
  }

  private String getSerializedFieldName(List<String> columnNames, int pos, StructField sf) {
    String n = (columnNames == null ? sf.getFieldName() : columnNames.get(pos));
    return mappings.containsKey(n) ? mappings.get(n) : n;
  }

  @Override
  public Object deserialize(Writable blob) throws SerDeException {
    if (!(blob instanceof DocumentWritable)) {
      throw new SerDeException(
              format("%s requires a Writable object, not %s", getClass(), blob.getClass()));
    }
    row.clear();

    Object value;
    Document doc = ((DocumentWritable) blob).getDocument();

    List<String> structFieldNames = rowTypeInfo.getAllStructFieldNames();
    for (String fieldName : structFieldNames) {
      try {
        TypeInfo fieldTypeInfo = rowTypeInfo.getStructFieldTypeInfo(fieldName);

        String mapRMapping = mappings.containsKey(fieldName)
                ? mappings.get(fieldName)
                : fieldName;

        value = deserializeField(doc.getValue(mapRMapping), fieldTypeInfo);
      } catch (Exception e) {
        LOG.warn("Could not find the appropriate field for name " + fieldName);
        value = null;
      }
      row.add(value);
    }
    return row;
  }

  private Object deserializePrimitive(Object value, PrimitiveTypeInfo valueTypeInfo) {
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

  public Object deserializeField(Object value, TypeInfo valueTypeInfo) {
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

  private Object deserializeUnion(Object value, UnionTypeInfo valueTypeInfo) {
    return deserializeField(value, valueTypeInfo);
  }

  private Object deserializeStruct(Object value, StructTypeInfo valueTypeInfo) {
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

  private Object deserializeMap(Object value, MapTypeInfo valueTypeInfo) {
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

  private Object deserializeList(Object value, ListTypeInfo valueTypeInfo) {
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

  @Override
  public ObjectInspector getObjectInspector() throws SerDeException {
    return objectInspector;
  }

  @Override
  public Class<? extends Writable> getSerializedClass() {
    return DocumentWritable.class;
  }

  @Override
  public SerDeStats getSerDeStats() {
    // no support for statistics
    return null;
  }

  private Object serializeField(Object o, ObjectInspector oi) {
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
        result = serializeList(o, (ListObjectInspector) oi);
        break;
      }
      case MAP: {
        result = serializeMap(o, (MapObjectInspector) oi);
        break;
      }
      case STRUCT: {
        result = serializeStruct(o, (StructObjectInspector) oi, null);
        break;
      }
      case UNION: {
        result = serializeUnion(o, (UnionObjectInspector) oi);
        break;
      }
      default:
        throw new IllegalArgumentException("Unknown type in ObjectInspector!");
    }
    return result;
  }

  private Object serializeList(Object obj, ListObjectInspector loi) {
    if (obj == null) return null;

    List<?> field = loi.getList(obj);
    List<Object> ar = new ArrayList<>();

    for (Object elem : field) {
      ar.add(serializeField(elem, loi.getListElementObjectInspector()));
    }
    return ar;
  }

  private Object serializeUnion(Object obj, UnionObjectInspector oi) {
    if (obj == null) return null;
    return serializeField(obj, oi.getObjectInspectors().get(oi.getTag(obj)));
  }

  private Object serializeMap(Object obj, MapObjectInspector moi) {
    if (obj == null) return null;

    Map<String, Object> map = new HashMap<>();
    Map m = moi.getMap(obj);

    for (Object k : m.keySet()) {
      map.put(serializeField(k, moi.getMapKeyObjectInspector()).toString(),
              serializeField(m.get(k), moi.getMapValueObjectInspector()));
    }
    return map;
  }
}