/**
 * Autogenerated by Thrift Compiler (0.16.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package org.apache.hadoop.hive.metastore.api;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.16.0)")
@org.apache.hadoop.classification.InterfaceAudience.Public @org.apache.hadoop.classification.InterfaceStability.Stable public class HiveObjectRef implements org.apache.thrift.TBase<HiveObjectRef, HiveObjectRef._Fields>, java.io.Serializable, Cloneable, Comparable<HiveObjectRef> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("HiveObjectRef");

  private static final org.apache.thrift.protocol.TField OBJECT_TYPE_FIELD_DESC = new org.apache.thrift.protocol.TField("objectType", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField DB_NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("dbName", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField OBJECT_NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("objectName", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField PART_VALUES_FIELD_DESC = new org.apache.thrift.protocol.TField("partValues", org.apache.thrift.protocol.TType.LIST, (short)4);
  private static final org.apache.thrift.protocol.TField COLUMN_NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("columnName", org.apache.thrift.protocol.TType.STRING, (short)5);
  private static final org.apache.thrift.protocol.TField CAT_NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("catName", org.apache.thrift.protocol.TType.STRING, (short)6);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new HiveObjectRefStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new HiveObjectRefTupleSchemeFactory();

  private @org.apache.thrift.annotation.Nullable HiveObjectType objectType; // required
  private @org.apache.thrift.annotation.Nullable java.lang.String dbName; // required
  private @org.apache.thrift.annotation.Nullable java.lang.String objectName; // required
  private @org.apache.thrift.annotation.Nullable java.util.List<java.lang.String> partValues; // required
  private @org.apache.thrift.annotation.Nullable java.lang.String columnName; // required
  private @org.apache.thrift.annotation.Nullable java.lang.String catName; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    /**
     * 
     * @see HiveObjectType
     */
    OBJECT_TYPE((short)1, "objectType"),
    DB_NAME((short)2, "dbName"),
    OBJECT_NAME((short)3, "objectName"),
    PART_VALUES((short)4, "partValues"),
    COLUMN_NAME((short)5, "columnName"),
    CAT_NAME((short)6, "catName");

    private static final java.util.Map<java.lang.String, _Fields> byName = new java.util.HashMap<java.lang.String, _Fields>();

    static {
      for (_Fields field : java.util.EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    @org.apache.thrift.annotation.Nullable
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // OBJECT_TYPE
          return OBJECT_TYPE;
        case 2: // DB_NAME
          return DB_NAME;
        case 3: // OBJECT_NAME
          return OBJECT_NAME;
        case 4: // PART_VALUES
          return PART_VALUES;
        case 5: // COLUMN_NAME
          return COLUMN_NAME;
        case 6: // CAT_NAME
          return CAT_NAME;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new java.lang.IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    @org.apache.thrift.annotation.Nullable
    public static _Fields findByName(java.lang.String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final java.lang.String _fieldName;

    _Fields(short thriftId, java.lang.String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public java.lang.String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  private static final _Fields optionals[] = {_Fields.CAT_NAME};
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.OBJECT_TYPE, new org.apache.thrift.meta_data.FieldMetaData("objectType", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.EnumMetaData(org.apache.thrift.protocol.TType.ENUM, HiveObjectType.class)));
    tmpMap.put(_Fields.DB_NAME, new org.apache.thrift.meta_data.FieldMetaData("dbName", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.OBJECT_NAME, new org.apache.thrift.meta_data.FieldMetaData("objectName", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.PART_VALUES, new org.apache.thrift.meta_data.FieldMetaData("partValues", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING))));
    tmpMap.put(_Fields.COLUMN_NAME, new org.apache.thrift.meta_data.FieldMetaData("columnName", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.CAT_NAME, new org.apache.thrift.meta_data.FieldMetaData("catName", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(HiveObjectRef.class, metaDataMap);
  }

  public HiveObjectRef() {
  }

  public HiveObjectRef(
    HiveObjectType objectType,
    java.lang.String dbName,
    java.lang.String objectName,
    java.util.List<java.lang.String> partValues,
    java.lang.String columnName)
  {
    this();
    this.objectType = objectType;
    this.dbName = dbName;
    this.objectName = objectName;
    this.partValues = partValues;
    this.columnName = columnName;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public HiveObjectRef(HiveObjectRef other) {
    if (other.isSetObjectType()) {
      this.objectType = other.objectType;
    }
    if (other.isSetDbName()) {
      this.dbName = other.dbName;
    }
    if (other.isSetObjectName()) {
      this.objectName = other.objectName;
    }
    if (other.isSetPartValues()) {
      java.util.List<java.lang.String> __this__partValues = new java.util.ArrayList<java.lang.String>(other.partValues);
      this.partValues = __this__partValues;
    }
    if (other.isSetColumnName()) {
      this.columnName = other.columnName;
    }
    if (other.isSetCatName()) {
      this.catName = other.catName;
    }
  }

  public HiveObjectRef deepCopy() {
    return new HiveObjectRef(this);
  }

  @Override
  public void clear() {
    this.objectType = null;
    this.dbName = null;
    this.objectName = null;
    this.partValues = null;
    this.columnName = null;
    this.catName = null;
  }

  /**
   * 
   * @see HiveObjectType
   */
  @org.apache.thrift.annotation.Nullable
  public HiveObjectType getObjectType() {
    return this.objectType;
  }

  /**
   * 
   * @see HiveObjectType
   */
  public void setObjectType(@org.apache.thrift.annotation.Nullable HiveObjectType objectType) {
    this.objectType = objectType;
  }

  public void unsetObjectType() {
    this.objectType = null;
  }

  /** Returns true if field objectType is set (has been assigned a value) and false otherwise */
  public boolean isSetObjectType() {
    return this.objectType != null;
  }

  public void setObjectTypeIsSet(boolean value) {
    if (!value) {
      this.objectType = null;
    }
  }

  @org.apache.thrift.annotation.Nullable
  public java.lang.String getDbName() {
    return this.dbName;
  }

  public void setDbName(@org.apache.thrift.annotation.Nullable java.lang.String dbName) {
    this.dbName = dbName;
  }

  public void unsetDbName() {
    this.dbName = null;
  }

  /** Returns true if field dbName is set (has been assigned a value) and false otherwise */
  public boolean isSetDbName() {
    return this.dbName != null;
  }

  public void setDbNameIsSet(boolean value) {
    if (!value) {
      this.dbName = null;
    }
  }

  @org.apache.thrift.annotation.Nullable
  public java.lang.String getObjectName() {
    return this.objectName;
  }

  public void setObjectName(@org.apache.thrift.annotation.Nullable java.lang.String objectName) {
    this.objectName = objectName;
  }

  public void unsetObjectName() {
    this.objectName = null;
  }

  /** Returns true if field objectName is set (has been assigned a value) and false otherwise */
  public boolean isSetObjectName() {
    return this.objectName != null;
  }

  public void setObjectNameIsSet(boolean value) {
    if (!value) {
      this.objectName = null;
    }
  }

  public int getPartValuesSize() {
    return (this.partValues == null) ? 0 : this.partValues.size();
  }

  @org.apache.thrift.annotation.Nullable
  public java.util.Iterator<java.lang.String> getPartValuesIterator() {
    return (this.partValues == null) ? null : this.partValues.iterator();
  }

  public void addToPartValues(java.lang.String elem) {
    if (this.partValues == null) {
      this.partValues = new java.util.ArrayList<java.lang.String>();
    }
    this.partValues.add(elem);
  }

  @org.apache.thrift.annotation.Nullable
  public java.util.List<java.lang.String> getPartValues() {
    return this.partValues;
  }

  public void setPartValues(@org.apache.thrift.annotation.Nullable java.util.List<java.lang.String> partValues) {
    this.partValues = partValues;
  }

  public void unsetPartValues() {
    this.partValues = null;
  }

  /** Returns true if field partValues is set (has been assigned a value) and false otherwise */
  public boolean isSetPartValues() {
    return this.partValues != null;
  }

  public void setPartValuesIsSet(boolean value) {
    if (!value) {
      this.partValues = null;
    }
  }

  @org.apache.thrift.annotation.Nullable
  public java.lang.String getColumnName() {
    return this.columnName;
  }

  public void setColumnName(@org.apache.thrift.annotation.Nullable java.lang.String columnName) {
    this.columnName = columnName;
  }

  public void unsetColumnName() {
    this.columnName = null;
  }

  /** Returns true if field columnName is set (has been assigned a value) and false otherwise */
  public boolean isSetColumnName() {
    return this.columnName != null;
  }

  public void setColumnNameIsSet(boolean value) {
    if (!value) {
      this.columnName = null;
    }
  }

  @org.apache.thrift.annotation.Nullable
  public java.lang.String getCatName() {
    return this.catName;
  }

  public void setCatName(@org.apache.thrift.annotation.Nullable java.lang.String catName) {
    this.catName = catName;
  }

  public void unsetCatName() {
    this.catName = null;
  }

  /** Returns true if field catName is set (has been assigned a value) and false otherwise */
  public boolean isSetCatName() {
    return this.catName != null;
  }

  public void setCatNameIsSet(boolean value) {
    if (!value) {
      this.catName = null;
    }
  }

  public void setFieldValue(_Fields field, @org.apache.thrift.annotation.Nullable java.lang.Object value) {
    switch (field) {
    case OBJECT_TYPE:
      if (value == null) {
        unsetObjectType();
      } else {
        setObjectType((HiveObjectType)value);
      }
      break;

    case DB_NAME:
      if (value == null) {
        unsetDbName();
      } else {
        setDbName((java.lang.String)value);
      }
      break;

    case OBJECT_NAME:
      if (value == null) {
        unsetObjectName();
      } else {
        setObjectName((java.lang.String)value);
      }
      break;

    case PART_VALUES:
      if (value == null) {
        unsetPartValues();
      } else {
        setPartValues((java.util.List<java.lang.String>)value);
      }
      break;

    case COLUMN_NAME:
      if (value == null) {
        unsetColumnName();
      } else {
        setColumnName((java.lang.String)value);
      }
      break;

    case CAT_NAME:
      if (value == null) {
        unsetCatName();
      } else {
        setCatName((java.lang.String)value);
      }
      break;

    }
  }

  @org.apache.thrift.annotation.Nullable
  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case OBJECT_TYPE:
      return getObjectType();

    case DB_NAME:
      return getDbName();

    case OBJECT_NAME:
      return getObjectName();

    case PART_VALUES:
      return getPartValues();

    case COLUMN_NAME:
      return getColumnName();

    case CAT_NAME:
      return getCatName();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case OBJECT_TYPE:
      return isSetObjectType();
    case DB_NAME:
      return isSetDbName();
    case OBJECT_NAME:
      return isSetObjectName();
    case PART_VALUES:
      return isSetPartValues();
    case COLUMN_NAME:
      return isSetColumnName();
    case CAT_NAME:
      return isSetCatName();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that instanceof HiveObjectRef)
      return this.equals((HiveObjectRef)that);
    return false;
  }

  public boolean equals(HiveObjectRef that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_objectType = true && this.isSetObjectType();
    boolean that_present_objectType = true && that.isSetObjectType();
    if (this_present_objectType || that_present_objectType) {
      if (!(this_present_objectType && that_present_objectType))
        return false;
      if (!this.objectType.equals(that.objectType))
        return false;
    }

    boolean this_present_dbName = true && this.isSetDbName();
    boolean that_present_dbName = true && that.isSetDbName();
    if (this_present_dbName || that_present_dbName) {
      if (!(this_present_dbName && that_present_dbName))
        return false;
      if (!this.dbName.equals(that.dbName))
        return false;
    }

    boolean this_present_objectName = true && this.isSetObjectName();
    boolean that_present_objectName = true && that.isSetObjectName();
    if (this_present_objectName || that_present_objectName) {
      if (!(this_present_objectName && that_present_objectName))
        return false;
      if (!this.objectName.equals(that.objectName))
        return false;
    }

    boolean this_present_partValues = true && this.isSetPartValues();
    boolean that_present_partValues = true && that.isSetPartValues();
    if (this_present_partValues || that_present_partValues) {
      if (!(this_present_partValues && that_present_partValues))
        return false;
      if (!this.partValues.equals(that.partValues))
        return false;
    }

    boolean this_present_columnName = true && this.isSetColumnName();
    boolean that_present_columnName = true && that.isSetColumnName();
    if (this_present_columnName || that_present_columnName) {
      if (!(this_present_columnName && that_present_columnName))
        return false;
      if (!this.columnName.equals(that.columnName))
        return false;
    }

    boolean this_present_catName = true && this.isSetCatName();
    boolean that_present_catName = true && that.isSetCatName();
    if (this_present_catName || that_present_catName) {
      if (!(this_present_catName && that_present_catName))
        return false;
      if (!this.catName.equals(that.catName))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((isSetObjectType()) ? 131071 : 524287);
    if (isSetObjectType())
      hashCode = hashCode * 8191 + objectType.getValue();

    hashCode = hashCode * 8191 + ((isSetDbName()) ? 131071 : 524287);
    if (isSetDbName())
      hashCode = hashCode * 8191 + dbName.hashCode();

    hashCode = hashCode * 8191 + ((isSetObjectName()) ? 131071 : 524287);
    if (isSetObjectName())
      hashCode = hashCode * 8191 + objectName.hashCode();

    hashCode = hashCode * 8191 + ((isSetPartValues()) ? 131071 : 524287);
    if (isSetPartValues())
      hashCode = hashCode * 8191 + partValues.hashCode();

    hashCode = hashCode * 8191 + ((isSetColumnName()) ? 131071 : 524287);
    if (isSetColumnName())
      hashCode = hashCode * 8191 + columnName.hashCode();

    hashCode = hashCode * 8191 + ((isSetCatName()) ? 131071 : 524287);
    if (isSetCatName())
      hashCode = hashCode * 8191 + catName.hashCode();

    return hashCode;
  }

  @Override
  public int compareTo(HiveObjectRef other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.compare(isSetObjectType(), other.isSetObjectType());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetObjectType()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.objectType, other.objectType);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.compare(isSetDbName(), other.isSetDbName());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetDbName()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.dbName, other.dbName);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.compare(isSetObjectName(), other.isSetObjectName());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetObjectName()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.objectName, other.objectName);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.compare(isSetPartValues(), other.isSetPartValues());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPartValues()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.partValues, other.partValues);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.compare(isSetColumnName(), other.isSetColumnName());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetColumnName()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.columnName, other.columnName);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.compare(isSetCatName(), other.isSetCatName());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCatName()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.catName, other.catName);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  @org.apache.thrift.annotation.Nullable
  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    scheme(iprot).read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    scheme(oprot).write(oprot, this);
  }

  @Override
  public java.lang.String toString() {
    java.lang.StringBuilder sb = new java.lang.StringBuilder("HiveObjectRef(");
    boolean first = true;

    sb.append("objectType:");
    if (this.objectType == null) {
      sb.append("null");
    } else {
      sb.append(this.objectType);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("dbName:");
    if (this.dbName == null) {
      sb.append("null");
    } else {
      sb.append(this.dbName);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("objectName:");
    if (this.objectName == null) {
      sb.append("null");
    } else {
      sb.append(this.objectName);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("partValues:");
    if (this.partValues == null) {
      sb.append("null");
    } else {
      sb.append(this.partValues);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("columnName:");
    if (this.columnName == null) {
      sb.append("null");
    } else {
      sb.append(this.columnName);
    }
    first = false;
    if (isSetCatName()) {
      if (!first) sb.append(", ");
      sb.append("catName:");
      if (this.catName == null) {
        sb.append("null");
      } else {
        sb.append(this.catName);
      }
      first = false;
    }
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, java.lang.ClassNotFoundException {
    try {
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class HiveObjectRefStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public HiveObjectRefStandardScheme getScheme() {
      return new HiveObjectRefStandardScheme();
    }
  }

  private static class HiveObjectRefStandardScheme extends org.apache.thrift.scheme.StandardScheme<HiveObjectRef> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, HiveObjectRef struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // OBJECT_TYPE
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.objectType = org.apache.hadoop.hive.metastore.api.HiveObjectType.findByValue(iprot.readI32());
              struct.setObjectTypeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // DB_NAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.dbName = iprot.readString();
              struct.setDbNameIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // OBJECT_NAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.objectName = iprot.readString();
              struct.setObjectNameIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // PART_VALUES
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list8 = iprot.readListBegin();
                struct.partValues = new java.util.ArrayList<java.lang.String>(_list8.size);
                @org.apache.thrift.annotation.Nullable java.lang.String _elem9;
                for (int _i10 = 0; _i10 < _list8.size; ++_i10)
                {
                  _elem9 = iprot.readString();
                  struct.partValues.add(_elem9);
                }
                iprot.readListEnd();
              }
              struct.setPartValuesIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // COLUMN_NAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.columnName = iprot.readString();
              struct.setColumnNameIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // CAT_NAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.catName = iprot.readString();
              struct.setCatNameIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, HiveObjectRef struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.objectType != null) {
        oprot.writeFieldBegin(OBJECT_TYPE_FIELD_DESC);
        oprot.writeI32(struct.objectType.getValue());
        oprot.writeFieldEnd();
      }
      if (struct.dbName != null) {
        oprot.writeFieldBegin(DB_NAME_FIELD_DESC);
        oprot.writeString(struct.dbName);
        oprot.writeFieldEnd();
      }
      if (struct.objectName != null) {
        oprot.writeFieldBegin(OBJECT_NAME_FIELD_DESC);
        oprot.writeString(struct.objectName);
        oprot.writeFieldEnd();
      }
      if (struct.partValues != null) {
        oprot.writeFieldBegin(PART_VALUES_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRING, struct.partValues.size()));
          for (java.lang.String _iter11 : struct.partValues)
          {
            oprot.writeString(_iter11);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      if (struct.columnName != null) {
        oprot.writeFieldBegin(COLUMN_NAME_FIELD_DESC);
        oprot.writeString(struct.columnName);
        oprot.writeFieldEnd();
      }
      if (struct.catName != null) {
        if (struct.isSetCatName()) {
          oprot.writeFieldBegin(CAT_NAME_FIELD_DESC);
          oprot.writeString(struct.catName);
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class HiveObjectRefTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public HiveObjectRefTupleScheme getScheme() {
      return new HiveObjectRefTupleScheme();
    }
  }

  private static class HiveObjectRefTupleScheme extends org.apache.thrift.scheme.TupleScheme<HiveObjectRef> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, HiveObjectRef struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetObjectType()) {
        optionals.set(0);
      }
      if (struct.isSetDbName()) {
        optionals.set(1);
      }
      if (struct.isSetObjectName()) {
        optionals.set(2);
      }
      if (struct.isSetPartValues()) {
        optionals.set(3);
      }
      if (struct.isSetColumnName()) {
        optionals.set(4);
      }
      if (struct.isSetCatName()) {
        optionals.set(5);
      }
      oprot.writeBitSet(optionals, 6);
      if (struct.isSetObjectType()) {
        oprot.writeI32(struct.objectType.getValue());
      }
      if (struct.isSetDbName()) {
        oprot.writeString(struct.dbName);
      }
      if (struct.isSetObjectName()) {
        oprot.writeString(struct.objectName);
      }
      if (struct.isSetPartValues()) {
        {
          oprot.writeI32(struct.partValues.size());
          for (java.lang.String _iter12 : struct.partValues)
          {
            oprot.writeString(_iter12);
          }
        }
      }
      if (struct.isSetColumnName()) {
        oprot.writeString(struct.columnName);
      }
      if (struct.isSetCatName()) {
        oprot.writeString(struct.catName);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, HiveObjectRef struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(6);
      if (incoming.get(0)) {
        struct.objectType = org.apache.hadoop.hive.metastore.api.HiveObjectType.findByValue(iprot.readI32());
        struct.setObjectTypeIsSet(true);
      }
      if (incoming.get(1)) {
        struct.dbName = iprot.readString();
        struct.setDbNameIsSet(true);
      }
      if (incoming.get(2)) {
        struct.objectName = iprot.readString();
        struct.setObjectNameIsSet(true);
      }
      if (incoming.get(3)) {
        {
          org.apache.thrift.protocol.TList _list13 = iprot.readListBegin(org.apache.thrift.protocol.TType.STRING);
          struct.partValues = new java.util.ArrayList<java.lang.String>(_list13.size);
          @org.apache.thrift.annotation.Nullable java.lang.String _elem14;
          for (int _i15 = 0; _i15 < _list13.size; ++_i15)
          {
            _elem14 = iprot.readString();
            struct.partValues.add(_elem14);
          }
        }
        struct.setPartValuesIsSet(true);
      }
      if (incoming.get(4)) {
        struct.columnName = iprot.readString();
        struct.setColumnNameIsSet(true);
      }
      if (incoming.get(5)) {
        struct.catName = iprot.readString();
        struct.setCatNameIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

