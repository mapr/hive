/**
 * Autogenerated by Thrift Compiler (0.12.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package org.apache.hadoop.hive.metastore.api;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.12.0)")
public class LockComponent implements org.apache.thrift.TBase<LockComponent, LockComponent._Fields>, java.io.Serializable, Cloneable, Comparable<LockComponent> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("LockComponent");

  private static final org.apache.thrift.protocol.TField TYPE_FIELD_DESC = new org.apache.thrift.protocol.TField("type", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField LEVEL_FIELD_DESC = new org.apache.thrift.protocol.TField("level", org.apache.thrift.protocol.TType.I32, (short)2);
  private static final org.apache.thrift.protocol.TField DBNAME_FIELD_DESC = new org.apache.thrift.protocol.TField("dbname", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField TABLENAME_FIELD_DESC = new org.apache.thrift.protocol.TField("tablename", org.apache.thrift.protocol.TType.STRING, (short)4);
  private static final org.apache.thrift.protocol.TField PARTITIONNAME_FIELD_DESC = new org.apache.thrift.protocol.TField("partitionname", org.apache.thrift.protocol.TType.STRING, (short)5);
  private static final org.apache.thrift.protocol.TField OPERATION_TYPE_FIELD_DESC = new org.apache.thrift.protocol.TField("operationType", org.apache.thrift.protocol.TType.I32, (short)6);
  private static final org.apache.thrift.protocol.TField IS_TRANSACTIONAL_FIELD_DESC = new org.apache.thrift.protocol.TField("isTransactional", org.apache.thrift.protocol.TType.BOOL, (short)7);
  private static final org.apache.thrift.protocol.TField IS_DYNAMIC_PARTITION_WRITE_FIELD_DESC = new org.apache.thrift.protocol.TField("isDynamicPartitionWrite", org.apache.thrift.protocol.TType.BOOL, (short)8);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new LockComponentStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new LockComponentTupleSchemeFactory();

  private @org.apache.thrift.annotation.Nullable LockType type; // required
  private @org.apache.thrift.annotation.Nullable LockLevel level; // required
  private @org.apache.thrift.annotation.Nullable java.lang.String dbname; // required
  private @org.apache.thrift.annotation.Nullable java.lang.String tablename; // optional
  private @org.apache.thrift.annotation.Nullable java.lang.String partitionname; // optional
  private @org.apache.thrift.annotation.Nullable DataOperationType operationType; // optional
  private boolean isTransactional; // optional
  private boolean isDynamicPartitionWrite; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    /**
     * 
     * @see LockType
     */
    TYPE((short)1, "type"),
    /**
     * 
     * @see LockLevel
     */
    LEVEL((short)2, "level"),
    DBNAME((short)3, "dbname"),
    TABLENAME((short)4, "tablename"),
    PARTITIONNAME((short)5, "partitionname"),
    /**
     * 
     * @see DataOperationType
     */
    OPERATION_TYPE((short)6, "operationType"),
    IS_TRANSACTIONAL((short)7, "isTransactional"),
    IS_DYNAMIC_PARTITION_WRITE((short)8, "isDynamicPartitionWrite");

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
        case 1: // TYPE
          return TYPE;
        case 2: // LEVEL
          return LEVEL;
        case 3: // DBNAME
          return DBNAME;
        case 4: // TABLENAME
          return TABLENAME;
        case 5: // PARTITIONNAME
          return PARTITIONNAME;
        case 6: // OPERATION_TYPE
          return OPERATION_TYPE;
        case 7: // IS_TRANSACTIONAL
          return IS_TRANSACTIONAL;
        case 8: // IS_DYNAMIC_PARTITION_WRITE
          return IS_DYNAMIC_PARTITION_WRITE;
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
  private static final int __ISTRANSACTIONAL_ISSET_ID = 0;
  private static final int __ISDYNAMICPARTITIONWRITE_ISSET_ID = 1;
  private byte __isset_bitfield = 0;
  private static final _Fields optionals[] = {_Fields.TABLENAME,_Fields.PARTITIONNAME,_Fields.OPERATION_TYPE,_Fields.IS_TRANSACTIONAL,_Fields.IS_DYNAMIC_PARTITION_WRITE};
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.TYPE, new org.apache.thrift.meta_data.FieldMetaData("type", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.EnumMetaData(org.apache.thrift.protocol.TType.ENUM, LockType.class)));
    tmpMap.put(_Fields.LEVEL, new org.apache.thrift.meta_data.FieldMetaData("level", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.EnumMetaData(org.apache.thrift.protocol.TType.ENUM, LockLevel.class)));
    tmpMap.put(_Fields.DBNAME, new org.apache.thrift.meta_data.FieldMetaData("dbname", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.TABLENAME, new org.apache.thrift.meta_data.FieldMetaData("tablename", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.PARTITIONNAME, new org.apache.thrift.meta_data.FieldMetaData("partitionname", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.OPERATION_TYPE, new org.apache.thrift.meta_data.FieldMetaData("operationType", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.EnumMetaData(org.apache.thrift.protocol.TType.ENUM, DataOperationType.class)));
    tmpMap.put(_Fields.IS_TRANSACTIONAL, new org.apache.thrift.meta_data.FieldMetaData("isTransactional", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.BOOL)));
    tmpMap.put(_Fields.IS_DYNAMIC_PARTITION_WRITE, new org.apache.thrift.meta_data.FieldMetaData("isDynamicPartitionWrite", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.BOOL)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(LockComponent.class, metaDataMap);
  }

  public LockComponent() {
    this.operationType = org.apache.hadoop.hive.metastore.api.DataOperationType.UNSET;

    this.isTransactional = false;

    this.isDynamicPartitionWrite = false;

  }

  public LockComponent(
    LockType type,
    LockLevel level,
    java.lang.String dbname)
  {
    this();
    this.type = type;
    this.level = level;
    this.dbname = dbname;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public LockComponent(LockComponent other) {
    __isset_bitfield = other.__isset_bitfield;
    if (other.isSetType()) {
      this.type = other.type;
    }
    if (other.isSetLevel()) {
      this.level = other.level;
    }
    if (other.isSetDbname()) {
      this.dbname = other.dbname;
    }
    if (other.isSetTablename()) {
      this.tablename = other.tablename;
    }
    if (other.isSetPartitionname()) {
      this.partitionname = other.partitionname;
    }
    if (other.isSetOperationType()) {
      this.operationType = other.operationType;
    }
    this.isTransactional = other.isTransactional;
    this.isDynamicPartitionWrite = other.isDynamicPartitionWrite;
  }

  public LockComponent deepCopy() {
    return new LockComponent(this);
  }

  @Override
  public void clear() {
    this.type = null;
    this.level = null;
    this.dbname = null;
    this.tablename = null;
    this.partitionname = null;
    this.operationType = org.apache.hadoop.hive.metastore.api.DataOperationType.UNSET;

    this.isTransactional = false;

    this.isDynamicPartitionWrite = false;

  }

  /**
   * 
   * @see LockType
   */
  @org.apache.thrift.annotation.Nullable
  public LockType getType() {
    return this.type;
  }

  /**
   * 
   * @see LockType
   */
  public void setType(@org.apache.thrift.annotation.Nullable LockType type) {
    this.type = type;
  }

  public void unsetType() {
    this.type = null;
  }

  /** Returns true if field type is set (has been assigned a value) and false otherwise */
  public boolean isSetType() {
    return this.type != null;
  }

  public void setTypeIsSet(boolean value) {
    if (!value) {
      this.type = null;
    }
  }

  /**
   * 
   * @see LockLevel
   */
  @org.apache.thrift.annotation.Nullable
  public LockLevel getLevel() {
    return this.level;
  }

  /**
   * 
   * @see LockLevel
   */
  public void setLevel(@org.apache.thrift.annotation.Nullable LockLevel level) {
    this.level = level;
  }

  public void unsetLevel() {
    this.level = null;
  }

  /** Returns true if field level is set (has been assigned a value) and false otherwise */
  public boolean isSetLevel() {
    return this.level != null;
  }

  public void setLevelIsSet(boolean value) {
    if (!value) {
      this.level = null;
    }
  }

  @org.apache.thrift.annotation.Nullable
  public java.lang.String getDbname() {
    return this.dbname;
  }

  public void setDbname(@org.apache.thrift.annotation.Nullable java.lang.String dbname) {
    this.dbname = dbname;
  }

  public void unsetDbname() {
    this.dbname = null;
  }

  /** Returns true if field dbname is set (has been assigned a value) and false otherwise */
  public boolean isSetDbname() {
    return this.dbname != null;
  }

  public void setDbnameIsSet(boolean value) {
    if (!value) {
      this.dbname = null;
    }
  }

  @org.apache.thrift.annotation.Nullable
  public java.lang.String getTablename() {
    return this.tablename;
  }

  public void setTablename(@org.apache.thrift.annotation.Nullable java.lang.String tablename) {
    this.tablename = tablename;
  }

  public void unsetTablename() {
    this.tablename = null;
  }

  /** Returns true if field tablename is set (has been assigned a value) and false otherwise */
  public boolean isSetTablename() {
    return this.tablename != null;
  }

  public void setTablenameIsSet(boolean value) {
    if (!value) {
      this.tablename = null;
    }
  }

  @org.apache.thrift.annotation.Nullable
  public java.lang.String getPartitionname() {
    return this.partitionname;
  }

  public void setPartitionname(@org.apache.thrift.annotation.Nullable java.lang.String partitionname) {
    this.partitionname = partitionname;
  }

  public void unsetPartitionname() {
    this.partitionname = null;
  }

  /** Returns true if field partitionname is set (has been assigned a value) and false otherwise */
  public boolean isSetPartitionname() {
    return this.partitionname != null;
  }

  public void setPartitionnameIsSet(boolean value) {
    if (!value) {
      this.partitionname = null;
    }
  }

  /**
   * 
   * @see DataOperationType
   */
  @org.apache.thrift.annotation.Nullable
  public DataOperationType getOperationType() {
    return this.operationType;
  }

  /**
   * 
   * @see DataOperationType
   */
  public void setOperationType(@org.apache.thrift.annotation.Nullable DataOperationType operationType) {
    this.operationType = operationType;
  }

  public void unsetOperationType() {
    this.operationType = null;
  }

  /** Returns true if field operationType is set (has been assigned a value) and false otherwise */
  public boolean isSetOperationType() {
    return this.operationType != null;
  }

  public void setOperationTypeIsSet(boolean value) {
    if (!value) {
      this.operationType = null;
    }
  }

  public boolean isIsTransactional() {
    return this.isTransactional;
  }

  public void setIsTransactional(boolean isTransactional) {
    this.isTransactional = isTransactional;
    setIsTransactionalIsSet(true);
  }

  public void unsetIsTransactional() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __ISTRANSACTIONAL_ISSET_ID);
  }

  /** Returns true if field isTransactional is set (has been assigned a value) and false otherwise */
  public boolean isSetIsTransactional() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __ISTRANSACTIONAL_ISSET_ID);
  }

  public void setIsTransactionalIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __ISTRANSACTIONAL_ISSET_ID, value);
  }

  public boolean isIsDynamicPartitionWrite() {
    return this.isDynamicPartitionWrite;
  }

  public void setIsDynamicPartitionWrite(boolean isDynamicPartitionWrite) {
    this.isDynamicPartitionWrite = isDynamicPartitionWrite;
    setIsDynamicPartitionWriteIsSet(true);
  }

  public void unsetIsDynamicPartitionWrite() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __ISDYNAMICPARTITIONWRITE_ISSET_ID);
  }

  /** Returns true if field isDynamicPartitionWrite is set (has been assigned a value) and false otherwise */
  public boolean isSetIsDynamicPartitionWrite() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __ISDYNAMICPARTITIONWRITE_ISSET_ID);
  }

  public void setIsDynamicPartitionWriteIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __ISDYNAMICPARTITIONWRITE_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, @org.apache.thrift.annotation.Nullable java.lang.Object value) {
    switch (field) {
    case TYPE:
      if (value == null) {
        unsetType();
      } else {
        setType((LockType)value);
      }
      break;

    case LEVEL:
      if (value == null) {
        unsetLevel();
      } else {
        setLevel((LockLevel)value);
      }
      break;

    case DBNAME:
      if (value == null) {
        unsetDbname();
      } else {
        setDbname((java.lang.String)value);
      }
      break;

    case TABLENAME:
      if (value == null) {
        unsetTablename();
      } else {
        setTablename((java.lang.String)value);
      }
      break;

    case PARTITIONNAME:
      if (value == null) {
        unsetPartitionname();
      } else {
        setPartitionname((java.lang.String)value);
      }
      break;

    case OPERATION_TYPE:
      if (value == null) {
        unsetOperationType();
      } else {
        setOperationType((DataOperationType)value);
      }
      break;

    case IS_TRANSACTIONAL:
      if (value == null) {
        unsetIsTransactional();
      } else {
        setIsTransactional((java.lang.Boolean)value);
      }
      break;

    case IS_DYNAMIC_PARTITION_WRITE:
      if (value == null) {
        unsetIsDynamicPartitionWrite();
      } else {
        setIsDynamicPartitionWrite((java.lang.Boolean)value);
      }
      break;

    }
  }

  @org.apache.thrift.annotation.Nullable
  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case TYPE:
      return getType();

    case LEVEL:
      return getLevel();

    case DBNAME:
      return getDbname();

    case TABLENAME:
      return getTablename();

    case PARTITIONNAME:
      return getPartitionname();

    case OPERATION_TYPE:
      return getOperationType();

    case IS_TRANSACTIONAL:
      return isIsTransactional();

    case IS_DYNAMIC_PARTITION_WRITE:
      return isIsDynamicPartitionWrite();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case TYPE:
      return isSetType();
    case LEVEL:
      return isSetLevel();
    case DBNAME:
      return isSetDbname();
    case TABLENAME:
      return isSetTablename();
    case PARTITIONNAME:
      return isSetPartitionname();
    case OPERATION_TYPE:
      return isSetOperationType();
    case IS_TRANSACTIONAL:
      return isSetIsTransactional();
    case IS_DYNAMIC_PARTITION_WRITE:
      return isSetIsDynamicPartitionWrite();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof LockComponent)
      return this.equals((LockComponent)that);
    return false;
  }

  public boolean equals(LockComponent that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_type = true && this.isSetType();
    boolean that_present_type = true && that.isSetType();
    if (this_present_type || that_present_type) {
      if (!(this_present_type && that_present_type))
        return false;
      if (!this.type.equals(that.type))
        return false;
    }

    boolean this_present_level = true && this.isSetLevel();
    boolean that_present_level = true && that.isSetLevel();
    if (this_present_level || that_present_level) {
      if (!(this_present_level && that_present_level))
        return false;
      if (!this.level.equals(that.level))
        return false;
    }

    boolean this_present_dbname = true && this.isSetDbname();
    boolean that_present_dbname = true && that.isSetDbname();
    if (this_present_dbname || that_present_dbname) {
      if (!(this_present_dbname && that_present_dbname))
        return false;
      if (!this.dbname.equals(that.dbname))
        return false;
    }

    boolean this_present_tablename = true && this.isSetTablename();
    boolean that_present_tablename = true && that.isSetTablename();
    if (this_present_tablename || that_present_tablename) {
      if (!(this_present_tablename && that_present_tablename))
        return false;
      if (!this.tablename.equals(that.tablename))
        return false;
    }

    boolean this_present_partitionname = true && this.isSetPartitionname();
    boolean that_present_partitionname = true && that.isSetPartitionname();
    if (this_present_partitionname || that_present_partitionname) {
      if (!(this_present_partitionname && that_present_partitionname))
        return false;
      if (!this.partitionname.equals(that.partitionname))
        return false;
    }

    boolean this_present_operationType = true && this.isSetOperationType();
    boolean that_present_operationType = true && that.isSetOperationType();
    if (this_present_operationType || that_present_operationType) {
      if (!(this_present_operationType && that_present_operationType))
        return false;
      if (!this.operationType.equals(that.operationType))
        return false;
    }

    boolean this_present_isTransactional = true && this.isSetIsTransactional();
    boolean that_present_isTransactional = true && that.isSetIsTransactional();
    if (this_present_isTransactional || that_present_isTransactional) {
      if (!(this_present_isTransactional && that_present_isTransactional))
        return false;
      if (this.isTransactional != that.isTransactional)
        return false;
    }

    boolean this_present_isDynamicPartitionWrite = true && this.isSetIsDynamicPartitionWrite();
    boolean that_present_isDynamicPartitionWrite = true && that.isSetIsDynamicPartitionWrite();
    if (this_present_isDynamicPartitionWrite || that_present_isDynamicPartitionWrite) {
      if (!(this_present_isDynamicPartitionWrite && that_present_isDynamicPartitionWrite))
        return false;
      if (this.isDynamicPartitionWrite != that.isDynamicPartitionWrite)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((isSetType()) ? 131071 : 524287);
    if (isSetType())
      hashCode = hashCode * 8191 + type.getValue();

    hashCode = hashCode * 8191 + ((isSetLevel()) ? 131071 : 524287);
    if (isSetLevel())
      hashCode = hashCode * 8191 + level.getValue();

    hashCode = hashCode * 8191 + ((isSetDbname()) ? 131071 : 524287);
    if (isSetDbname())
      hashCode = hashCode * 8191 + dbname.hashCode();

    hashCode = hashCode * 8191 + ((isSetTablename()) ? 131071 : 524287);
    if (isSetTablename())
      hashCode = hashCode * 8191 + tablename.hashCode();

    hashCode = hashCode * 8191 + ((isSetPartitionname()) ? 131071 : 524287);
    if (isSetPartitionname())
      hashCode = hashCode * 8191 + partitionname.hashCode();

    hashCode = hashCode * 8191 + ((isSetOperationType()) ? 131071 : 524287);
    if (isSetOperationType())
      hashCode = hashCode * 8191 + operationType.getValue();

    hashCode = hashCode * 8191 + ((isSetIsTransactional()) ? 131071 : 524287);
    if (isSetIsTransactional())
      hashCode = hashCode * 8191 + ((isTransactional) ? 131071 : 524287);

    hashCode = hashCode * 8191 + ((isSetIsDynamicPartitionWrite()) ? 131071 : 524287);
    if (isSetIsDynamicPartitionWrite())
      hashCode = hashCode * 8191 + ((isDynamicPartitionWrite) ? 131071 : 524287);

    return hashCode;
  }

  @Override
  public int compareTo(LockComponent other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.valueOf(isSetType()).compareTo(other.isSetType());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetType()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.type, other.type);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetLevel()).compareTo(other.isSetLevel());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetLevel()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.level, other.level);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetDbname()).compareTo(other.isSetDbname());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetDbname()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.dbname, other.dbname);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetTablename()).compareTo(other.isSetTablename());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTablename()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.tablename, other.tablename);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetPartitionname()).compareTo(other.isSetPartitionname());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPartitionname()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.partitionname, other.partitionname);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetOperationType()).compareTo(other.isSetOperationType());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetOperationType()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.operationType, other.operationType);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetIsTransactional()).compareTo(other.isSetIsTransactional());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetIsTransactional()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.isTransactional, other.isTransactional);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetIsDynamicPartitionWrite()).compareTo(other.isSetIsDynamicPartitionWrite());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetIsDynamicPartitionWrite()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.isDynamicPartitionWrite, other.isDynamicPartitionWrite);
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
    java.lang.StringBuilder sb = new java.lang.StringBuilder("LockComponent(");
    boolean first = true;

    sb.append("type:");
    if (this.type == null) {
      sb.append("null");
    } else {
      sb.append(this.type);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("level:");
    if (this.level == null) {
      sb.append("null");
    } else {
      sb.append(this.level);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("dbname:");
    if (this.dbname == null) {
      sb.append("null");
    } else {
      sb.append(this.dbname);
    }
    first = false;
    if (isSetTablename()) {
      if (!first) sb.append(", ");
      sb.append("tablename:");
      if (this.tablename == null) {
        sb.append("null");
      } else {
        sb.append(this.tablename);
      }
      first = false;
    }
    if (isSetPartitionname()) {
      if (!first) sb.append(", ");
      sb.append("partitionname:");
      if (this.partitionname == null) {
        sb.append("null");
      } else {
        sb.append(this.partitionname);
      }
      first = false;
    }
    if (isSetOperationType()) {
      if (!first) sb.append(", ");
      sb.append("operationType:");
      if (this.operationType == null) {
        sb.append("null");
      } else {
        sb.append(this.operationType);
      }
      first = false;
    }
    if (isSetIsTransactional()) {
      if (!first) sb.append(", ");
      sb.append("isTransactional:");
      sb.append(this.isTransactional);
      first = false;
    }
    if (isSetIsDynamicPartitionWrite()) {
      if (!first) sb.append(", ");
      sb.append("isDynamicPartitionWrite:");
      sb.append(this.isDynamicPartitionWrite);
      first = false;
    }
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    if (!isSetType()) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'type' is unset! Struct:" + toString());
    }

    if (!isSetLevel()) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'level' is unset! Struct:" + toString());
    }

    if (!isSetDbname()) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'dbname' is unset! Struct:" + toString());
    }

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
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class LockComponentStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public LockComponentStandardScheme getScheme() {
      return new LockComponentStandardScheme();
    }
  }

  private static class LockComponentStandardScheme extends org.apache.thrift.scheme.StandardScheme<LockComponent> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, LockComponent struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // TYPE
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.type = org.apache.hadoop.hive.metastore.api.LockType.findByValue(iprot.readI32());
              struct.setTypeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // LEVEL
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.level = org.apache.hadoop.hive.metastore.api.LockLevel.findByValue(iprot.readI32());
              struct.setLevelIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // DBNAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.dbname = iprot.readString();
              struct.setDbnameIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // TABLENAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.tablename = iprot.readString();
              struct.setTablenameIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // PARTITIONNAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.partitionname = iprot.readString();
              struct.setPartitionnameIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // OPERATION_TYPE
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.operationType = org.apache.hadoop.hive.metastore.api.DataOperationType.findByValue(iprot.readI32());
              struct.setOperationTypeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 7: // IS_TRANSACTIONAL
            if (schemeField.type == org.apache.thrift.protocol.TType.BOOL) {
              struct.isTransactional = iprot.readBool();
              struct.setIsTransactionalIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 8: // IS_DYNAMIC_PARTITION_WRITE
            if (schemeField.type == org.apache.thrift.protocol.TType.BOOL) {
              struct.isDynamicPartitionWrite = iprot.readBool();
              struct.setIsDynamicPartitionWriteIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, LockComponent struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.type != null) {
        oprot.writeFieldBegin(TYPE_FIELD_DESC);
        oprot.writeI32(struct.type.getValue());
        oprot.writeFieldEnd();
      }
      if (struct.level != null) {
        oprot.writeFieldBegin(LEVEL_FIELD_DESC);
        oprot.writeI32(struct.level.getValue());
        oprot.writeFieldEnd();
      }
      if (struct.dbname != null) {
        oprot.writeFieldBegin(DBNAME_FIELD_DESC);
        oprot.writeString(struct.dbname);
        oprot.writeFieldEnd();
      }
      if (struct.tablename != null) {
        if (struct.isSetTablename()) {
          oprot.writeFieldBegin(TABLENAME_FIELD_DESC);
          oprot.writeString(struct.tablename);
          oprot.writeFieldEnd();
        }
      }
      if (struct.partitionname != null) {
        if (struct.isSetPartitionname()) {
          oprot.writeFieldBegin(PARTITIONNAME_FIELD_DESC);
          oprot.writeString(struct.partitionname);
          oprot.writeFieldEnd();
        }
      }
      if (struct.operationType != null) {
        if (struct.isSetOperationType()) {
          oprot.writeFieldBegin(OPERATION_TYPE_FIELD_DESC);
          oprot.writeI32(struct.operationType.getValue());
          oprot.writeFieldEnd();
        }
      }
      if (struct.isSetIsTransactional()) {
        oprot.writeFieldBegin(IS_TRANSACTIONAL_FIELD_DESC);
        oprot.writeBool(struct.isTransactional);
        oprot.writeFieldEnd();
      }
      if (struct.isSetIsDynamicPartitionWrite()) {
        oprot.writeFieldBegin(IS_DYNAMIC_PARTITION_WRITE_FIELD_DESC);
        oprot.writeBool(struct.isDynamicPartitionWrite);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class LockComponentTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public LockComponentTupleScheme getScheme() {
      return new LockComponentTupleScheme();
    }
  }

  private static class LockComponentTupleScheme extends org.apache.thrift.scheme.TupleScheme<LockComponent> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, LockComponent struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      oprot.writeI32(struct.type.getValue());
      oprot.writeI32(struct.level.getValue());
      oprot.writeString(struct.dbname);
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetTablename()) {
        optionals.set(0);
      }
      if (struct.isSetPartitionname()) {
        optionals.set(1);
      }
      if (struct.isSetOperationType()) {
        optionals.set(2);
      }
      if (struct.isSetIsTransactional()) {
        optionals.set(3);
      }
      if (struct.isSetIsDynamicPartitionWrite()) {
        optionals.set(4);
      }
      oprot.writeBitSet(optionals, 5);
      if (struct.isSetTablename()) {
        oprot.writeString(struct.tablename);
      }
      if (struct.isSetPartitionname()) {
        oprot.writeString(struct.partitionname);
      }
      if (struct.isSetOperationType()) {
        oprot.writeI32(struct.operationType.getValue());
      }
      if (struct.isSetIsTransactional()) {
        oprot.writeBool(struct.isTransactional);
      }
      if (struct.isSetIsDynamicPartitionWrite()) {
        oprot.writeBool(struct.isDynamicPartitionWrite);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, LockComponent struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      struct.type = org.apache.hadoop.hive.metastore.api.LockType.findByValue(iprot.readI32());
      struct.setTypeIsSet(true);
      struct.level = org.apache.hadoop.hive.metastore.api.LockLevel.findByValue(iprot.readI32());
      struct.setLevelIsSet(true);
      struct.dbname = iprot.readString();
      struct.setDbnameIsSet(true);
      java.util.BitSet incoming = iprot.readBitSet(5);
      if (incoming.get(0)) {
        struct.tablename = iprot.readString();
        struct.setTablenameIsSet(true);
      }
      if (incoming.get(1)) {
        struct.partitionname = iprot.readString();
        struct.setPartitionnameIsSet(true);
      }
      if (incoming.get(2)) {
        struct.operationType = org.apache.hadoop.hive.metastore.api.DataOperationType.findByValue(iprot.readI32());
        struct.setOperationTypeIsSet(true);
      }
      if (incoming.get(3)) {
        struct.isTransactional = iprot.readBool();
        struct.setIsTransactionalIsSet(true);
      }
      if (incoming.get(4)) {
        struct.isDynamicPartitionWrite = iprot.readBool();
        struct.setIsDynamicPartitionWriteIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

