/**
 * Autogenerated by Thrift Compiler (0.13.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package org.apache.hadoop.hive.metastore.api;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.13.0)")
public class PartitionSpec implements org.apache.thrift.TBase<PartitionSpec, PartitionSpec._Fields>, java.io.Serializable, Cloneable, Comparable<PartitionSpec> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("PartitionSpec");

  private static final org.apache.thrift.protocol.TField DB_NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("dbName", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField TABLE_NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("tableName", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField ROOT_PATH_FIELD_DESC = new org.apache.thrift.protocol.TField("rootPath", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField SHARED_SDPARTITION_SPEC_FIELD_DESC = new org.apache.thrift.protocol.TField("sharedSDPartitionSpec", org.apache.thrift.protocol.TType.STRUCT, (short)4);
  private static final org.apache.thrift.protocol.TField PARTITION_LIST_FIELD_DESC = new org.apache.thrift.protocol.TField("partitionList", org.apache.thrift.protocol.TType.STRUCT, (short)5);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new PartitionSpecStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new PartitionSpecTupleSchemeFactory();

  private @org.apache.thrift.annotation.Nullable java.lang.String dbName; // required
  private @org.apache.thrift.annotation.Nullable java.lang.String tableName; // required
  private @org.apache.thrift.annotation.Nullable java.lang.String rootPath; // required
  private @org.apache.thrift.annotation.Nullable PartitionSpecWithSharedSD sharedSDPartitionSpec; // optional
  private @org.apache.thrift.annotation.Nullable PartitionListComposingSpec partitionList; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    DB_NAME((short)1, "dbName"),
    TABLE_NAME((short)2, "tableName"),
    ROOT_PATH((short)3, "rootPath"),
    SHARED_SDPARTITION_SPEC((short)4, "sharedSDPartitionSpec"),
    PARTITION_LIST((short)5, "partitionList");

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
        case 1: // DB_NAME
          return DB_NAME;
        case 2: // TABLE_NAME
          return TABLE_NAME;
        case 3: // ROOT_PATH
          return ROOT_PATH;
        case 4: // SHARED_SDPARTITION_SPEC
          return SHARED_SDPARTITION_SPEC;
        case 5: // PARTITION_LIST
          return PARTITION_LIST;
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
  private static final _Fields optionals[] = {_Fields.SHARED_SDPARTITION_SPEC,_Fields.PARTITION_LIST};
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.DB_NAME, new org.apache.thrift.meta_data.FieldMetaData("dbName", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.TABLE_NAME, new org.apache.thrift.meta_data.FieldMetaData("tableName", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.ROOT_PATH, new org.apache.thrift.meta_data.FieldMetaData("rootPath", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.SHARED_SDPARTITION_SPEC, new org.apache.thrift.meta_data.FieldMetaData("sharedSDPartitionSpec", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, PartitionSpecWithSharedSD.class)));
    tmpMap.put(_Fields.PARTITION_LIST, new org.apache.thrift.meta_data.FieldMetaData("partitionList", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, PartitionListComposingSpec.class)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(PartitionSpec.class, metaDataMap);
  }

  public PartitionSpec() {
  }

  public PartitionSpec(
    java.lang.String dbName,
    java.lang.String tableName,
    java.lang.String rootPath)
  {
    this();
    this.dbName = dbName;
    this.tableName = tableName;
    this.rootPath = rootPath;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public PartitionSpec(PartitionSpec other) {
    if (other.isSetDbName()) {
      this.dbName = other.dbName;
    }
    if (other.isSetTableName()) {
      this.tableName = other.tableName;
    }
    if (other.isSetRootPath()) {
      this.rootPath = other.rootPath;
    }
    if (other.isSetSharedSDPartitionSpec()) {
      this.sharedSDPartitionSpec = new PartitionSpecWithSharedSD(other.sharedSDPartitionSpec);
    }
    if (other.isSetPartitionList()) {
      this.partitionList = new PartitionListComposingSpec(other.partitionList);
    }
  }

  public PartitionSpec deepCopy() {
    return new PartitionSpec(this);
  }

  @Override
  public void clear() {
    this.dbName = null;
    this.tableName = null;
    this.rootPath = null;
    this.sharedSDPartitionSpec = null;
    this.partitionList = null;
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
  public java.lang.String getTableName() {
    return this.tableName;
  }

  public void setTableName(@org.apache.thrift.annotation.Nullable java.lang.String tableName) {
    this.tableName = tableName;
  }

  public void unsetTableName() {
    this.tableName = null;
  }

  /** Returns true if field tableName is set (has been assigned a value) and false otherwise */
  public boolean isSetTableName() {
    return this.tableName != null;
  }

  public void setTableNameIsSet(boolean value) {
    if (!value) {
      this.tableName = null;
    }
  }

  @org.apache.thrift.annotation.Nullable
  public java.lang.String getRootPath() {
    return this.rootPath;
  }

  public void setRootPath(@org.apache.thrift.annotation.Nullable java.lang.String rootPath) {
    this.rootPath = rootPath;
  }

  public void unsetRootPath() {
    this.rootPath = null;
  }

  /** Returns true if field rootPath is set (has been assigned a value) and false otherwise */
  public boolean isSetRootPath() {
    return this.rootPath != null;
  }

  public void setRootPathIsSet(boolean value) {
    if (!value) {
      this.rootPath = null;
    }
  }

  @org.apache.thrift.annotation.Nullable
  public PartitionSpecWithSharedSD getSharedSDPartitionSpec() {
    return this.sharedSDPartitionSpec;
  }

  public void setSharedSDPartitionSpec(@org.apache.thrift.annotation.Nullable PartitionSpecWithSharedSD sharedSDPartitionSpec) {
    this.sharedSDPartitionSpec = sharedSDPartitionSpec;
  }

  public void unsetSharedSDPartitionSpec() {
    this.sharedSDPartitionSpec = null;
  }

  /** Returns true if field sharedSDPartitionSpec is set (has been assigned a value) and false otherwise */
  public boolean isSetSharedSDPartitionSpec() {
    return this.sharedSDPartitionSpec != null;
  }

  public void setSharedSDPartitionSpecIsSet(boolean value) {
    if (!value) {
      this.sharedSDPartitionSpec = null;
    }
  }

  @org.apache.thrift.annotation.Nullable
  public PartitionListComposingSpec getPartitionList() {
    return this.partitionList;
  }

  public void setPartitionList(@org.apache.thrift.annotation.Nullable PartitionListComposingSpec partitionList) {
    this.partitionList = partitionList;
  }

  public void unsetPartitionList() {
    this.partitionList = null;
  }

  /** Returns true if field partitionList is set (has been assigned a value) and false otherwise */
  public boolean isSetPartitionList() {
    return this.partitionList != null;
  }

  public void setPartitionListIsSet(boolean value) {
    if (!value) {
      this.partitionList = null;
    }
  }

  public void setFieldValue(_Fields field, @org.apache.thrift.annotation.Nullable java.lang.Object value) {
    switch (field) {
    case DB_NAME:
      if (value == null) {
        unsetDbName();
      } else {
        setDbName((java.lang.String)value);
      }
      break;

    case TABLE_NAME:
      if (value == null) {
        unsetTableName();
      } else {
        setTableName((java.lang.String)value);
      }
      break;

    case ROOT_PATH:
      if (value == null) {
        unsetRootPath();
      } else {
        setRootPath((java.lang.String)value);
      }
      break;

    case SHARED_SDPARTITION_SPEC:
      if (value == null) {
        unsetSharedSDPartitionSpec();
      } else {
        setSharedSDPartitionSpec((PartitionSpecWithSharedSD)value);
      }
      break;

    case PARTITION_LIST:
      if (value == null) {
        unsetPartitionList();
      } else {
        setPartitionList((PartitionListComposingSpec)value);
      }
      break;

    }
  }

  @org.apache.thrift.annotation.Nullable
  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case DB_NAME:
      return getDbName();

    case TABLE_NAME:
      return getTableName();

    case ROOT_PATH:
      return getRootPath();

    case SHARED_SDPARTITION_SPEC:
      return getSharedSDPartitionSpec();

    case PARTITION_LIST:
      return getPartitionList();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case DB_NAME:
      return isSetDbName();
    case TABLE_NAME:
      return isSetTableName();
    case ROOT_PATH:
      return isSetRootPath();
    case SHARED_SDPARTITION_SPEC:
      return isSetSharedSDPartitionSpec();
    case PARTITION_LIST:
      return isSetPartitionList();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof PartitionSpec)
      return this.equals((PartitionSpec)that);
    return false;
  }

  public boolean equals(PartitionSpec that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_dbName = true && this.isSetDbName();
    boolean that_present_dbName = true && that.isSetDbName();
    if (this_present_dbName || that_present_dbName) {
      if (!(this_present_dbName && that_present_dbName))
        return false;
      if (!this.dbName.equals(that.dbName))
        return false;
    }

    boolean this_present_tableName = true && this.isSetTableName();
    boolean that_present_tableName = true && that.isSetTableName();
    if (this_present_tableName || that_present_tableName) {
      if (!(this_present_tableName && that_present_tableName))
        return false;
      if (!this.tableName.equals(that.tableName))
        return false;
    }

    boolean this_present_rootPath = true && this.isSetRootPath();
    boolean that_present_rootPath = true && that.isSetRootPath();
    if (this_present_rootPath || that_present_rootPath) {
      if (!(this_present_rootPath && that_present_rootPath))
        return false;
      if (!this.rootPath.equals(that.rootPath))
        return false;
    }

    boolean this_present_sharedSDPartitionSpec = true && this.isSetSharedSDPartitionSpec();
    boolean that_present_sharedSDPartitionSpec = true && that.isSetSharedSDPartitionSpec();
    if (this_present_sharedSDPartitionSpec || that_present_sharedSDPartitionSpec) {
      if (!(this_present_sharedSDPartitionSpec && that_present_sharedSDPartitionSpec))
        return false;
      if (!this.sharedSDPartitionSpec.equals(that.sharedSDPartitionSpec))
        return false;
    }

    boolean this_present_partitionList = true && this.isSetPartitionList();
    boolean that_present_partitionList = true && that.isSetPartitionList();
    if (this_present_partitionList || that_present_partitionList) {
      if (!(this_present_partitionList && that_present_partitionList))
        return false;
      if (!this.partitionList.equals(that.partitionList))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((isSetDbName()) ? 131071 : 524287);
    if (isSetDbName())
      hashCode = hashCode * 8191 + dbName.hashCode();

    hashCode = hashCode * 8191 + ((isSetTableName()) ? 131071 : 524287);
    if (isSetTableName())
      hashCode = hashCode * 8191 + tableName.hashCode();

    hashCode = hashCode * 8191 + ((isSetRootPath()) ? 131071 : 524287);
    if (isSetRootPath())
      hashCode = hashCode * 8191 + rootPath.hashCode();

    hashCode = hashCode * 8191 + ((isSetSharedSDPartitionSpec()) ? 131071 : 524287);
    if (isSetSharedSDPartitionSpec())
      hashCode = hashCode * 8191 + sharedSDPartitionSpec.hashCode();

    hashCode = hashCode * 8191 + ((isSetPartitionList()) ? 131071 : 524287);
    if (isSetPartitionList())
      hashCode = hashCode * 8191 + partitionList.hashCode();

    return hashCode;
  }

  @Override
  public int compareTo(PartitionSpec other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.valueOf(isSetDbName()).compareTo(other.isSetDbName());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetDbName()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.dbName, other.dbName);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetTableName()).compareTo(other.isSetTableName());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTableName()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.tableName, other.tableName);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetRootPath()).compareTo(other.isSetRootPath());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetRootPath()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.rootPath, other.rootPath);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetSharedSDPartitionSpec()).compareTo(other.isSetSharedSDPartitionSpec());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSharedSDPartitionSpec()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.sharedSDPartitionSpec, other.sharedSDPartitionSpec);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetPartitionList()).compareTo(other.isSetPartitionList());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPartitionList()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.partitionList, other.partitionList);
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
    java.lang.StringBuilder sb = new java.lang.StringBuilder("PartitionSpec(");
    boolean first = true;

    sb.append("dbName:");
    if (this.dbName == null) {
      sb.append("null");
    } else {
      sb.append(this.dbName);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("tableName:");
    if (this.tableName == null) {
      sb.append("null");
    } else {
      sb.append(this.tableName);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("rootPath:");
    if (this.rootPath == null) {
      sb.append("null");
    } else {
      sb.append(this.rootPath);
    }
    first = false;
    if (isSetSharedSDPartitionSpec()) {
      if (!first) sb.append(", ");
      sb.append("sharedSDPartitionSpec:");
      if (this.sharedSDPartitionSpec == null) {
        sb.append("null");
      } else {
        sb.append(this.sharedSDPartitionSpec);
      }
      first = false;
    }
    if (isSetPartitionList()) {
      if (!first) sb.append(", ");
      sb.append("partitionList:");
      if (this.partitionList == null) {
        sb.append("null");
      } else {
        sb.append(this.partitionList);
      }
      first = false;
    }
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
    if (sharedSDPartitionSpec != null) {
      sharedSDPartitionSpec.validate();
    }
    if (partitionList != null) {
      partitionList.validate();
    }
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

  private static class PartitionSpecStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public PartitionSpecStandardScheme getScheme() {
      return new PartitionSpecStandardScheme();
    }
  }

  private static class PartitionSpecStandardScheme extends org.apache.thrift.scheme.StandardScheme<PartitionSpec> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, PartitionSpec struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // DB_NAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.dbName = iprot.readString();
              struct.setDbNameIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // TABLE_NAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.tableName = iprot.readString();
              struct.setTableNameIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // ROOT_PATH
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.rootPath = iprot.readString();
              struct.setRootPathIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // SHARED_SDPARTITION_SPEC
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.sharedSDPartitionSpec = new PartitionSpecWithSharedSD();
              struct.sharedSDPartitionSpec.read(iprot);
              struct.setSharedSDPartitionSpecIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // PARTITION_LIST
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.partitionList = new PartitionListComposingSpec();
              struct.partitionList.read(iprot);
              struct.setPartitionListIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, PartitionSpec struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.dbName != null) {
        oprot.writeFieldBegin(DB_NAME_FIELD_DESC);
        oprot.writeString(struct.dbName);
        oprot.writeFieldEnd();
      }
      if (struct.tableName != null) {
        oprot.writeFieldBegin(TABLE_NAME_FIELD_DESC);
        oprot.writeString(struct.tableName);
        oprot.writeFieldEnd();
      }
      if (struct.rootPath != null) {
        oprot.writeFieldBegin(ROOT_PATH_FIELD_DESC);
        oprot.writeString(struct.rootPath);
        oprot.writeFieldEnd();
      }
      if (struct.sharedSDPartitionSpec != null) {
        if (struct.isSetSharedSDPartitionSpec()) {
          oprot.writeFieldBegin(SHARED_SDPARTITION_SPEC_FIELD_DESC);
          struct.sharedSDPartitionSpec.write(oprot);
          oprot.writeFieldEnd();
        }
      }
      if (struct.partitionList != null) {
        if (struct.isSetPartitionList()) {
          oprot.writeFieldBegin(PARTITION_LIST_FIELD_DESC);
          struct.partitionList.write(oprot);
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class PartitionSpecTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public PartitionSpecTupleScheme getScheme() {
      return new PartitionSpecTupleScheme();
    }
  }

  private static class PartitionSpecTupleScheme extends org.apache.thrift.scheme.TupleScheme<PartitionSpec> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, PartitionSpec struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetDbName()) {
        optionals.set(0);
      }
      if (struct.isSetTableName()) {
        optionals.set(1);
      }
      if (struct.isSetRootPath()) {
        optionals.set(2);
      }
      if (struct.isSetSharedSDPartitionSpec()) {
        optionals.set(3);
      }
      if (struct.isSetPartitionList()) {
        optionals.set(4);
      }
      oprot.writeBitSet(optionals, 5);
      if (struct.isSetDbName()) {
        oprot.writeString(struct.dbName);
      }
      if (struct.isSetTableName()) {
        oprot.writeString(struct.tableName);
      }
      if (struct.isSetRootPath()) {
        oprot.writeString(struct.rootPath);
      }
      if (struct.isSetSharedSDPartitionSpec()) {
        struct.sharedSDPartitionSpec.write(oprot);
      }
      if (struct.isSetPartitionList()) {
        struct.partitionList.write(oprot);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, PartitionSpec struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(5);
      if (incoming.get(0)) {
        struct.dbName = iprot.readString();
        struct.setDbNameIsSet(true);
      }
      if (incoming.get(1)) {
        struct.tableName = iprot.readString();
        struct.setTableNameIsSet(true);
      }
      if (incoming.get(2)) {
        struct.rootPath = iprot.readString();
        struct.setRootPathIsSet(true);
      }
      if (incoming.get(3)) {
        struct.sharedSDPartitionSpec = new PartitionSpecWithSharedSD();
        struct.sharedSDPartitionSpec.read(iprot);
        struct.setSharedSDPartitionSpecIsSet(true);
      }
      if (incoming.get(4)) {
        struct.partitionList = new PartitionListComposingSpec();
        struct.partitionList.read(iprot);
        struct.setPartitionListIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

