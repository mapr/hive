/**
 * Autogenerated by Thrift Compiler (0.13.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package org.apache.hadoop.hive.metastore.api;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.13.0)")
public class RuntimeStat implements org.apache.thrift.TBase<RuntimeStat, RuntimeStat._Fields>, java.io.Serializable, Cloneable, Comparable<RuntimeStat> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("RuntimeStat");

  private static final org.apache.thrift.protocol.TField CREATE_TIME_FIELD_DESC = new org.apache.thrift.protocol.TField("createTime", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField WEIGHT_FIELD_DESC = new org.apache.thrift.protocol.TField("weight", org.apache.thrift.protocol.TType.I32, (short)2);
  private static final org.apache.thrift.protocol.TField PAYLOAD_FIELD_DESC = new org.apache.thrift.protocol.TField("payload", org.apache.thrift.protocol.TType.STRING, (short)3);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new RuntimeStatStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new RuntimeStatTupleSchemeFactory();

  private int createTime; // optional
  private int weight; // required
  private @org.apache.thrift.annotation.Nullable java.nio.ByteBuffer payload; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    CREATE_TIME((short)1, "createTime"),
    WEIGHT((short)2, "weight"),
    PAYLOAD((short)3, "payload");

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
        case 1: // CREATE_TIME
          return CREATE_TIME;
        case 2: // WEIGHT
          return WEIGHT;
        case 3: // PAYLOAD
          return PAYLOAD;
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
  private static final int __CREATETIME_ISSET_ID = 0;
  private static final int __WEIGHT_ISSET_ID = 1;
  private byte __isset_bitfield = 0;
  private static final _Fields optionals[] = {_Fields.CREATE_TIME};
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.CREATE_TIME, new org.apache.thrift.meta_data.FieldMetaData("createTime", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.WEIGHT, new org.apache.thrift.meta_data.FieldMetaData("weight", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.PAYLOAD, new org.apache.thrift.meta_data.FieldMetaData("payload", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING        , true)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(RuntimeStat.class, metaDataMap);
  }

  public RuntimeStat() {
  }

  public RuntimeStat(
    int weight,
    java.nio.ByteBuffer payload)
  {
    this();
    this.weight = weight;
    setWeightIsSet(true);
    this.payload = org.apache.thrift.TBaseHelper.copyBinary(payload);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public RuntimeStat(RuntimeStat other) {
    __isset_bitfield = other.__isset_bitfield;
    this.createTime = other.createTime;
    this.weight = other.weight;
    if (other.isSetPayload()) {
      this.payload = org.apache.thrift.TBaseHelper.copyBinary(other.payload);
    }
  }

  public RuntimeStat deepCopy() {
    return new RuntimeStat(this);
  }

  @Override
  public void clear() {
    setCreateTimeIsSet(false);
    this.createTime = 0;
    setWeightIsSet(false);
    this.weight = 0;
    this.payload = null;
  }

  public int getCreateTime() {
    return this.createTime;
  }

  public void setCreateTime(int createTime) {
    this.createTime = createTime;
    setCreateTimeIsSet(true);
  }

  public void unsetCreateTime() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __CREATETIME_ISSET_ID);
  }

  /** Returns true if field createTime is set (has been assigned a value) and false otherwise */
  public boolean isSetCreateTime() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __CREATETIME_ISSET_ID);
  }

  public void setCreateTimeIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __CREATETIME_ISSET_ID, value);
  }

  public int getWeight() {
    return this.weight;
  }

  public void setWeight(int weight) {
    this.weight = weight;
    setWeightIsSet(true);
  }

  public void unsetWeight() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __WEIGHT_ISSET_ID);
  }

  /** Returns true if field weight is set (has been assigned a value) and false otherwise */
  public boolean isSetWeight() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __WEIGHT_ISSET_ID);
  }

  public void setWeightIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __WEIGHT_ISSET_ID, value);
  }

  public byte[] getPayload() {
    setPayload(org.apache.thrift.TBaseHelper.rightSize(payload));
    return payload == null ? null : payload.array();
  }

  public java.nio.ByteBuffer bufferForPayload() {
    return org.apache.thrift.TBaseHelper.copyBinary(payload);
  }

  public void setPayload(byte[] payload) {
    this.payload = payload == null ? (java.nio.ByteBuffer)null   : java.nio.ByteBuffer.wrap(payload.clone());
  }

  public void setPayload(@org.apache.thrift.annotation.Nullable java.nio.ByteBuffer payload) {
    this.payload = org.apache.thrift.TBaseHelper.copyBinary(payload);
  }

  public void unsetPayload() {
    this.payload = null;
  }

  /** Returns true if field payload is set (has been assigned a value) and false otherwise */
  public boolean isSetPayload() {
    return this.payload != null;
  }

  public void setPayloadIsSet(boolean value) {
    if (!value) {
      this.payload = null;
    }
  }

  public void setFieldValue(_Fields field, @org.apache.thrift.annotation.Nullable java.lang.Object value) {
    switch (field) {
    case CREATE_TIME:
      if (value == null) {
        unsetCreateTime();
      } else {
        setCreateTime((java.lang.Integer)value);
      }
      break;

    case WEIGHT:
      if (value == null) {
        unsetWeight();
      } else {
        setWeight((java.lang.Integer)value);
      }
      break;

    case PAYLOAD:
      if (value == null) {
        unsetPayload();
      } else {
        if (value instanceof byte[]) {
          setPayload((byte[])value);
        } else {
          setPayload((java.nio.ByteBuffer)value);
        }
      }
      break;

    }
  }

  @org.apache.thrift.annotation.Nullable
  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case CREATE_TIME:
      return getCreateTime();

    case WEIGHT:
      return getWeight();

    case PAYLOAD:
      return getPayload();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case CREATE_TIME:
      return isSetCreateTime();
    case WEIGHT:
      return isSetWeight();
    case PAYLOAD:
      return isSetPayload();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof RuntimeStat)
      return this.equals((RuntimeStat)that);
    return false;
  }

  public boolean equals(RuntimeStat that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_createTime = true && this.isSetCreateTime();
    boolean that_present_createTime = true && that.isSetCreateTime();
    if (this_present_createTime || that_present_createTime) {
      if (!(this_present_createTime && that_present_createTime))
        return false;
      if (this.createTime != that.createTime)
        return false;
    }

    boolean this_present_weight = true;
    boolean that_present_weight = true;
    if (this_present_weight || that_present_weight) {
      if (!(this_present_weight && that_present_weight))
        return false;
      if (this.weight != that.weight)
        return false;
    }

    boolean this_present_payload = true && this.isSetPayload();
    boolean that_present_payload = true && that.isSetPayload();
    if (this_present_payload || that_present_payload) {
      if (!(this_present_payload && that_present_payload))
        return false;
      if (!this.payload.equals(that.payload))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((isSetCreateTime()) ? 131071 : 524287);
    if (isSetCreateTime())
      hashCode = hashCode * 8191 + createTime;

    hashCode = hashCode * 8191 + weight;

    hashCode = hashCode * 8191 + ((isSetPayload()) ? 131071 : 524287);
    if (isSetPayload())
      hashCode = hashCode * 8191 + payload.hashCode();

    return hashCode;
  }

  @Override
  public int compareTo(RuntimeStat other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.valueOf(isSetCreateTime()).compareTo(other.isSetCreateTime());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCreateTime()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.createTime, other.createTime);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetWeight()).compareTo(other.isSetWeight());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetWeight()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.weight, other.weight);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetPayload()).compareTo(other.isSetPayload());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPayload()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.payload, other.payload);
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
    java.lang.StringBuilder sb = new java.lang.StringBuilder("RuntimeStat(");
    boolean first = true;

    if (isSetCreateTime()) {
      sb.append("createTime:");
      sb.append(this.createTime);
      first = false;
    }
    if (!first) sb.append(", ");
    sb.append("weight:");
    sb.append(this.weight);
    first = false;
    if (!first) sb.append(", ");
    sb.append("payload:");
    if (this.payload == null) {
      sb.append("null");
    } else {
      org.apache.thrift.TBaseHelper.toString(this.payload, sb);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    if (!isSetWeight()) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'weight' is unset! Struct:" + toString());
    }

    if (!isSetPayload()) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'payload' is unset! Struct:" + toString());
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

  private static class RuntimeStatStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public RuntimeStatStandardScheme getScheme() {
      return new RuntimeStatStandardScheme();
    }
  }

  private static class RuntimeStatStandardScheme extends org.apache.thrift.scheme.StandardScheme<RuntimeStat> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, RuntimeStat struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // CREATE_TIME
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.createTime = iprot.readI32();
              struct.setCreateTimeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // WEIGHT
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.weight = iprot.readI32();
              struct.setWeightIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // PAYLOAD
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.payload = iprot.readBinary();
              struct.setPayloadIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, RuntimeStat struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.isSetCreateTime()) {
        oprot.writeFieldBegin(CREATE_TIME_FIELD_DESC);
        oprot.writeI32(struct.createTime);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(WEIGHT_FIELD_DESC);
      oprot.writeI32(struct.weight);
      oprot.writeFieldEnd();
      if (struct.payload != null) {
        oprot.writeFieldBegin(PAYLOAD_FIELD_DESC);
        oprot.writeBinary(struct.payload);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class RuntimeStatTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public RuntimeStatTupleScheme getScheme() {
      return new RuntimeStatTupleScheme();
    }
  }

  private static class RuntimeStatTupleScheme extends org.apache.thrift.scheme.TupleScheme<RuntimeStat> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, RuntimeStat struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      oprot.writeI32(struct.weight);
      oprot.writeBinary(struct.payload);
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetCreateTime()) {
        optionals.set(0);
      }
      oprot.writeBitSet(optionals, 1);
      if (struct.isSetCreateTime()) {
        oprot.writeI32(struct.createTime);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, RuntimeStat struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      struct.weight = iprot.readI32();
      struct.setWeightIsSet(true);
      struct.payload = iprot.readBinary();
      struct.setPayloadIsSet(true);
      java.util.BitSet incoming = iprot.readBitSet(1);
      if (incoming.get(0)) {
        struct.createTime = iprot.readI32();
        struct.setCreateTimeIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

