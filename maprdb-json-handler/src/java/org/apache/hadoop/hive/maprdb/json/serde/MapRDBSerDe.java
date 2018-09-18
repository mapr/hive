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

import com.mapr.db.rowcol.DBDocumentImpl;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hive.maprdb.json.shims.DocumentWritable;
import org.apache.hadoop.hive.serde.serdeConstants;
import org.apache.hadoop.hive.serde2.AbstractSerDe;
import org.apache.hadoop.hive.serde2.SerDeException;
import org.apache.hadoop.hive.serde2.SerDeStats;
import org.apache.hadoop.hive.serde2.objectinspector.*;
import org.apache.hadoop.hive.serde2.typeinfo.*;
import org.apache.hadoop.io.Writable;
import org.ojai.Document;
import org.ojai.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static java.lang.String.format;
import static org.apache.hadoop.hive.maprdb.json.conf.MapRDBConstants.*;
import static org.apache.hadoop.hive.maprdb.json.serde.MapRDBSerDeUtils.*;

public class MapRDBSerDe extends AbstractSerDe {
  private static final Logger LOG = LoggerFactory.getLogger(MapRDBSerDe.class);

  private List<String> columnNames;
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
    mappings.put(tbl.getProperty((MAPRDB_COLUMN_ID)).toLowerCase(), ID_KEY);

    if (LOG.isDebugEnabled()) {
      LOG.debug("Columns: {}, Types: {}", columnNameProperty, columnTypeProperty);
    }

    // all table column names
    if (columnNameProperty.length() == 0) {
      columnNames = new ArrayList<>();
    } else {
      columnNames = Arrays.asList(columnNameProperty.split(","));
    }

    columnNames = normalizeColNames(columnNames);

    List<TypeInfo> columnTypes;
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

  private static List<String> normalizeColNames(List<String> columnNames) {
    List<String> normalizedColNames = new ArrayList<>();
    for (String columnName : columnNames) {
      normalizedColNames.add(columnName.toLowerCase());
    }
    return normalizedColNames;
  }

  @Override
  public Writable serialize(Object obj, ObjectInspector objInspector) throws SerDeException {
    if (objInspector.getCategory() != ObjectInspector.Category.STRUCT) {
      throw new SerDeException(getClass().toString()
        + " can only serialize struct types, but we got: "
        + objInspector.getTypeName());
    }

    Document doc = serializeStruct(obj, (StructObjectInspector) objInspector, columnNames, mappings);
    return new DocumentWritable(doc);
  }

  @Override
  public Object deserialize(Writable blob) throws SerDeException {
    if (!(blob instanceof DocumentWritable)) {
      throw new SerDeException(
        format("%s requires a Writable object, not %s", getClass(), blob.getClass()));
    }
    row.clear();

    Object value;
    Document doc = normalizeKeys(((DocumentWritable) blob).getDocument());

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

  private static Document normalizeKeys(Document doc) {
    Document normalizedDoc = new DBDocumentImpl();
    for (Map.Entry<String, Value> entry : doc) {
      normalizedDoc.set(entry.getKey().toLowerCase(), entry.getValue());
    }
    return normalizedDoc;
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
}
