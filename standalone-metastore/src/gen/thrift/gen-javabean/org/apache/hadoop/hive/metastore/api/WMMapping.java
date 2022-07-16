/**
 * Autogenerated by Thrift Compiler (0.16.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package org.apache.hadoop.hive.metastore.api;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.16.0)")
@org.apache.hadoop.classification.InterfaceAudience.Public @org.apache.hadoop.classification.InterfaceStability.Stable public class WMMapping implements org.apache.thrift.TBase<WMMapping, WMMapping._Fields>, java.io.Serializable, Cloneable, Comparable<WMMapping> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("WMMapping");

  private static final org.apache.thrift.protocol.TField RESOURCE_PLAN_NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("resourcePlanName", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField ENTITY_TYPE_FIELD_DESC = new org.apache.thrift.protocol.TField("entityType", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField ENTITY_NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("entityName", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField POOL_PATH_FIELD_DESC = new org.apache.thrift.protocol.TField("poolPath", org.apache.thrift.protocol.TType.STRING, (short)4);
  private static final org.apache.thrift.protocol.TField ORDERING_FIELD_DESC = new org.apache.thrift.protocol.TField("ordering", org.apache.thrift.protocol.TType.I32, (short)5);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new WMMappingStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new WMMappingTupleSchemeFactory();

  private @org.apache.thrift.annotation.Nullable java.lang.String resourcePlanName; // required
  private @org.apache.thrift.annotation.Nullable java.lang.String entityType; // required
  private @org.apache.thrift.annotation.Nullable java.lang.String entityName; // required
  private @org.apache.thrift.annotation.Nullable java.lang.String poolPath; // optional
  private int ordering; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    RESOURCE_PLAN_NAME((short)1, "resourcePlanName"),
    ENTITY_TYPE((short)2, "entityType"),
    ENTITY_NAME((short)3, "entityName"),
    POOL_PATH((short)4, "poolPath"),
    ORDERING((short)5, "ordering");

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
        case 1: // RESOURCE_PLAN_NAME
          return RESOURCE_PLAN_NAME;
        case 2: // ENTITY_TYPE
          return ENTITY_TYPE;
        case 3: // ENTITY_NAME
          return ENTITY_NAME;
        case 4: // POOL_PATH
          return POOL_PATH;
        case 5: // ORDERING
          return ORDERING;
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
  private static final int __ORDERING_ISSET_ID = 0;
  private byte __isset_bitfield = 0;
  private static final _Fields optionals[] = {_Fields.POOL_PATH,_Fields.ORDERING};
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.RESOURCE_PLAN_NAME, new org.apache.thrift.meta_data.FieldMetaData("resourcePlanName", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.ENTITY_TYPE, new org.apache.thrift.meta_data.FieldMetaData("entityType", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.ENTITY_NAME, new org.apache.thrift.meta_data.FieldMetaData("entityName", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.POOL_PATH, new org.apache.thrift.meta_data.FieldMetaData("poolPath", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.ORDERING, new org.apache.thrift.meta_data.FieldMetaData("ordering", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(WMMapping.class, metaDataMap);
  }

  public WMMapping() {
  }

  public WMMapping(
    java.lang.String resourcePlanName,
    java.lang.String entityType,
    java.lang.String entityName)
  {
    this();
    this.resourcePlanName = resourcePlanName;
    this.entityType = entityType;
    this.entityName = entityName;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public WMMapping(WMMapping other) {
    __isset_bitfield = other.__isset_bitfield;
    if (other.isSetResourcePlanName()) {
      this.resourcePlanName = other.resourcePlanName;
    }
    if (other.isSetEntityType()) {
      this.entityType = other.entityType;
    }
    if (other.isSetEntityName()) {
      this.entityName = other.entityName;
    }
    if (other.isSetPoolPath()) {
      this.poolPath = other.poolPath;
    }
    this.ordering = other.ordering;
  }

  public WMMapping deepCopy() {
    return new WMMapping(this);
  }

  @Override
  public void clear() {
    this.resourcePlanName = null;
    this.entityType = null;
    this.entityName = null;
    this.poolPath = null;
    setOrderingIsSet(false);
    this.ordering = 0;
  }

  @org.apache.thrift.annotation.Nullable
  public java.lang.String getResourcePlanName() {
    return this.resourcePlanName;
  }

  public void setResourcePlanName(@org.apache.thrift.annotation.Nullable java.lang.String resourcePlanName) {
    this.resourcePlanName = resourcePlanName;
  }

  public void unsetResourcePlanName() {
    this.resourcePlanName = null;
  }

  /** Returns true if field resourcePlanName is set (has been assigned a value) and false otherwise */
  public boolean isSetResourcePlanName() {
    return this.resourcePlanName != null;
  }

  public void setResourcePlanNameIsSet(boolean value) {
    if (!value) {
      this.resourcePlanName = null;
    }
  }

  @org.apache.thrift.annotation.Nullable
  public java.lang.String getEntityType() {
    return this.entityType;
  }

  public void setEntityType(@org.apache.thrift.annotation.Nullable java.lang.String entityType) {
    this.entityType = entityType;
  }

  public void unsetEntityType() {
    this.entityType = null;
  }

  /** Returns true if field entityType is set (has been assigned a value) and false otherwise */
  public boolean isSetEntityType() {
    return this.entityType != null;
  }

  public void setEntityTypeIsSet(boolean value) {
    if (!value) {
      this.entityType = null;
    }
  }

  @org.apache.thrift.annotation.Nullable
  public java.lang.String getEntityName() {
    return this.entityName;
  }

  public void setEntityName(@org.apache.thrift.annotation.Nullable java.lang.String entityName) {
    this.entityName = entityName;
  }

  public void unsetEntityName() {
    this.entityName = null;
  }

  /** Returns true if field entityName is set (has been assigned a value) and false otherwise */
  public boolean isSetEntityName() {
    return this.entityName != null;
  }

  public void setEntityNameIsSet(boolean value) {
    if (!value) {
      this.entityName = null;
    }
  }

  @org.apache.thrift.annotation.Nullable
  public java.lang.String getPoolPath() {
    return this.poolPath;
  }

  public void setPoolPath(@org.apache.thrift.annotation.Nullable java.lang.String poolPath) {
    this.poolPath = poolPath;
  }

  public void unsetPoolPath() {
    this.poolPath = null;
  }

  /** Returns true if field poolPath is set (has been assigned a value) and false otherwise */
  public boolean isSetPoolPath() {
    return this.poolPath != null;
  }

  public void setPoolPathIsSet(boolean value) {
    if (!value) {
      this.poolPath = null;
    }
  }

  public int getOrdering() {
    return this.ordering;
  }

  public void setOrdering(int ordering) {
    this.ordering = ordering;
    setOrderingIsSet(true);
  }

  public void unsetOrdering() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __ORDERING_ISSET_ID);
  }

  /** Returns true if field ordering is set (has been assigned a value) and false otherwise */
  public boolean isSetOrdering() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __ORDERING_ISSET_ID);
  }

  public void setOrderingIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __ORDERING_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, @org.apache.thrift.annotation.Nullable java.lang.Object value) {
    switch (field) {
    case RESOURCE_PLAN_NAME:
      if (value == null) {
        unsetResourcePlanName();
      } else {
        setResourcePlanName((java.lang.String)value);
      }
      break;

    case ENTITY_TYPE:
      if (value == null) {
        unsetEntityType();
      } else {
        setEntityType((java.lang.String)value);
      }
      break;

    case ENTITY_NAME:
      if (value == null) {
        unsetEntityName();
      } else {
        setEntityName((java.lang.String)value);
      }
      break;

    case POOL_PATH:
      if (value == null) {
        unsetPoolPath();
      } else {
        setPoolPath((java.lang.String)value);
      }
      break;

    case ORDERING:
      if (value == null) {
        unsetOrdering();
      } else {
        setOrdering((java.lang.Integer)value);
      }
      break;

    }
  }

  @org.apache.thrift.annotation.Nullable
  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case RESOURCE_PLAN_NAME:
      return getResourcePlanName();

    case ENTITY_TYPE:
      return getEntityType();

    case ENTITY_NAME:
      return getEntityName();

    case POOL_PATH:
      return getPoolPath();

    case ORDERING:
      return getOrdering();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case RESOURCE_PLAN_NAME:
      return isSetResourcePlanName();
    case ENTITY_TYPE:
      return isSetEntityType();
    case ENTITY_NAME:
      return isSetEntityName();
    case POOL_PATH:
      return isSetPoolPath();
    case ORDERING:
      return isSetOrdering();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that instanceof WMMapping)
      return this.equals((WMMapping)that);
    return false;
  }

  public boolean equals(WMMapping that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_resourcePlanName = true && this.isSetResourcePlanName();
    boolean that_present_resourcePlanName = true && that.isSetResourcePlanName();
    if (this_present_resourcePlanName || that_present_resourcePlanName) {
      if (!(this_present_resourcePlanName && that_present_resourcePlanName))
        return false;
      if (!this.resourcePlanName.equals(that.resourcePlanName))
        return false;
    }

    boolean this_present_entityType = true && this.isSetEntityType();
    boolean that_present_entityType = true && that.isSetEntityType();
    if (this_present_entityType || that_present_entityType) {
      if (!(this_present_entityType && that_present_entityType))
        return false;
      if (!this.entityType.equals(that.entityType))
        return false;
    }

    boolean this_present_entityName = true && this.isSetEntityName();
    boolean that_present_entityName = true && that.isSetEntityName();
    if (this_present_entityName || that_present_entityName) {
      if (!(this_present_entityName && that_present_entityName))
        return false;
      if (!this.entityName.equals(that.entityName))
        return false;
    }

    boolean this_present_poolPath = true && this.isSetPoolPath();
    boolean that_present_poolPath = true && that.isSetPoolPath();
    if (this_present_poolPath || that_present_poolPath) {
      if (!(this_present_poolPath && that_present_poolPath))
        return false;
      if (!this.poolPath.equals(that.poolPath))
        return false;
    }

    boolean this_present_ordering = true && this.isSetOrdering();
    boolean that_present_ordering = true && that.isSetOrdering();
    if (this_present_ordering || that_present_ordering) {
      if (!(this_present_ordering && that_present_ordering))
        return false;
      if (this.ordering != that.ordering)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((isSetResourcePlanName()) ? 131071 : 524287);
    if (isSetResourcePlanName())
      hashCode = hashCode * 8191 + resourcePlanName.hashCode();

    hashCode = hashCode * 8191 + ((isSetEntityType()) ? 131071 : 524287);
    if (isSetEntityType())
      hashCode = hashCode * 8191 + entityType.hashCode();

    hashCode = hashCode * 8191 + ((isSetEntityName()) ? 131071 : 524287);
    if (isSetEntityName())
      hashCode = hashCode * 8191 + entityName.hashCode();

    hashCode = hashCode * 8191 + ((isSetPoolPath()) ? 131071 : 524287);
    if (isSetPoolPath())
      hashCode = hashCode * 8191 + poolPath.hashCode();

    hashCode = hashCode * 8191 + ((isSetOrdering()) ? 131071 : 524287);
    if (isSetOrdering())
      hashCode = hashCode * 8191 + ordering;

    return hashCode;
  }

  @Override
  public int compareTo(WMMapping other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.compare(isSetResourcePlanName(), other.isSetResourcePlanName());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetResourcePlanName()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.resourcePlanName, other.resourcePlanName);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.compare(isSetEntityType(), other.isSetEntityType());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetEntityType()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.entityType, other.entityType);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.compare(isSetEntityName(), other.isSetEntityName());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetEntityName()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.entityName, other.entityName);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.compare(isSetPoolPath(), other.isSetPoolPath());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPoolPath()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.poolPath, other.poolPath);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.compare(isSetOrdering(), other.isSetOrdering());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetOrdering()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.ordering, other.ordering);
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
    java.lang.StringBuilder sb = new java.lang.StringBuilder("WMMapping(");
    boolean first = true;

    sb.append("resourcePlanName:");
    if (this.resourcePlanName == null) {
      sb.append("null");
    } else {
      sb.append(this.resourcePlanName);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("entityType:");
    if (this.entityType == null) {
      sb.append("null");
    } else {
      sb.append(this.entityType);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("entityName:");
    if (this.entityName == null) {
      sb.append("null");
    } else {
      sb.append(this.entityName);
    }
    first = false;
    if (isSetPoolPath()) {
      if (!first) sb.append(", ");
      sb.append("poolPath:");
      if (this.poolPath == null) {
        sb.append("null");
      } else {
        sb.append(this.poolPath);
      }
      first = false;
    }
    if (isSetOrdering()) {
      if (!first) sb.append(", ");
      sb.append("ordering:");
      sb.append(this.ordering);
      first = false;
    }
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    if (!isSetResourcePlanName()) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'resourcePlanName' is unset! Struct:" + toString());
    }

    if (!isSetEntityType()) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'entityType' is unset! Struct:" + toString());
    }

    if (!isSetEntityName()) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'entityName' is unset! Struct:" + toString());
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

  private static class WMMappingStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public WMMappingStandardScheme getScheme() {
      return new WMMappingStandardScheme();
    }
  }

  private static class WMMappingStandardScheme extends org.apache.thrift.scheme.StandardScheme<WMMapping> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, WMMapping struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // RESOURCE_PLAN_NAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.resourcePlanName = iprot.readString();
              struct.setResourcePlanNameIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // ENTITY_TYPE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.entityType = iprot.readString();
              struct.setEntityTypeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // ENTITY_NAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.entityName = iprot.readString();
              struct.setEntityNameIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // POOL_PATH
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.poolPath = iprot.readString();
              struct.setPoolPathIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // ORDERING
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.ordering = iprot.readI32();
              struct.setOrderingIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, WMMapping struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.resourcePlanName != null) {
        oprot.writeFieldBegin(RESOURCE_PLAN_NAME_FIELD_DESC);
        oprot.writeString(struct.resourcePlanName);
        oprot.writeFieldEnd();
      }
      if (struct.entityType != null) {
        oprot.writeFieldBegin(ENTITY_TYPE_FIELD_DESC);
        oprot.writeString(struct.entityType);
        oprot.writeFieldEnd();
      }
      if (struct.entityName != null) {
        oprot.writeFieldBegin(ENTITY_NAME_FIELD_DESC);
        oprot.writeString(struct.entityName);
        oprot.writeFieldEnd();
      }
      if (struct.poolPath != null) {
        if (struct.isSetPoolPath()) {
          oprot.writeFieldBegin(POOL_PATH_FIELD_DESC);
          oprot.writeString(struct.poolPath);
          oprot.writeFieldEnd();
        }
      }
      if (struct.isSetOrdering()) {
        oprot.writeFieldBegin(ORDERING_FIELD_DESC);
        oprot.writeI32(struct.ordering);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class WMMappingTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public WMMappingTupleScheme getScheme() {
      return new WMMappingTupleScheme();
    }
  }

  private static class WMMappingTupleScheme extends org.apache.thrift.scheme.TupleScheme<WMMapping> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, WMMapping struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      oprot.writeString(struct.resourcePlanName);
      oprot.writeString(struct.entityType);
      oprot.writeString(struct.entityName);
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetPoolPath()) {
        optionals.set(0);
      }
      if (struct.isSetOrdering()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetPoolPath()) {
        oprot.writeString(struct.poolPath);
      }
      if (struct.isSetOrdering()) {
        oprot.writeI32(struct.ordering);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, WMMapping struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      struct.resourcePlanName = iprot.readString();
      struct.setResourcePlanNameIsSet(true);
      struct.entityType = iprot.readString();
      struct.setEntityTypeIsSet(true);
      struct.entityName = iprot.readString();
      struct.setEntityNameIsSet(true);
      java.util.BitSet incoming = iprot.readBitSet(2);
      if (incoming.get(0)) {
        struct.poolPath = iprot.readString();
        struct.setPoolPathIsSet(true);
      }
      if (incoming.get(1)) {
        struct.ordering = iprot.readI32();
        struct.setOrderingIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

