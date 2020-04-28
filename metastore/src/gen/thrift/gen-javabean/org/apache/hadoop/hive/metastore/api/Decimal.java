/**
 * Autogenerated by Thrift Compiler (0.13.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package org.apache.hadoop.hive.metastore.api;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.13.0)")
public class Decimal implements org.apache.thrift.TBase<Decimal, Decimal._Fields>, java.io.Serializable, Cloneable, Comparable<Decimal> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("Decimal");

  private static final org.apache.thrift.protocol.TField UNSCALED_FIELD_DESC = new org.apache.thrift.protocol.TField("unscaled", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField SCALE_FIELD_DESC = new org.apache.thrift.protocol.TField("scale", org.apache.thrift.protocol.TType.I16, (short)3);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new DecimalStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new DecimalTupleSchemeFactory();

  private @org.apache.thrift.annotation.Nullable java.nio.ByteBuffer unscaled; // required
  private short scale; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    UNSCALED((short)1, "unscaled"),
    SCALE((short)3, "scale");

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
        case 1: // UNSCALED
          return UNSCALED;
        case 3: // SCALE
          return SCALE;
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
  private static final int __SCALE_ISSET_ID = 0;
  private byte __isset_bitfield = 0;
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.UNSCALED, new org.apache.thrift.meta_data.FieldMetaData("unscaled", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING        , true)));
    tmpMap.put(_Fields.SCALE, new org.apache.thrift.meta_data.FieldMetaData("scale", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I16)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(Decimal.class, metaDataMap);
  }

  public Decimal() {
  }

  public Decimal(
    java.nio.ByteBuffer unscaled,
    short scale)
  {
    this();
    this.unscaled = org.apache.thrift.TBaseHelper.copyBinary(unscaled);
    this.scale = scale;
    setScaleIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public Decimal(Decimal other) {
    __isset_bitfield = other.__isset_bitfield;
    if (other.isSetUnscaled()) {
      this.unscaled = org.apache.thrift.TBaseHelper.copyBinary(other.unscaled);
    }
    this.scale = other.scale;
  }

  public Decimal deepCopy() {
    return new Decimal(this);
  }

  @Override
  public void clear() {
    this.unscaled = null;
    setScaleIsSet(false);
    this.scale = 0;
  }

  public byte[] getUnscaled() {
    setUnscaled(org.apache.thrift.TBaseHelper.rightSize(unscaled));
    return unscaled == null ? null : unscaled.array();
  }

  public java.nio.ByteBuffer bufferForUnscaled() {
    return org.apache.thrift.TBaseHelper.copyBinary(unscaled);
  }

  public void setUnscaled(byte[] unscaled) {
    this.unscaled = unscaled == null ? (java.nio.ByteBuffer)null   : java.nio.ByteBuffer.wrap(unscaled.clone());
  }

  public void setUnscaled(@org.apache.thrift.annotation.Nullable java.nio.ByteBuffer unscaled) {
    this.unscaled = org.apache.thrift.TBaseHelper.copyBinary(unscaled);
  }

  public void unsetUnscaled() {
    this.unscaled = null;
  }

  /** Returns true if field unscaled is set (has been assigned a value) and false otherwise */
  public boolean isSetUnscaled() {
    return this.unscaled != null;
  }

  public void setUnscaledIsSet(boolean value) {
    if (!value) {
      this.unscaled = null;
    }
  }

  public short getScale() {
    return this.scale;
  }

  public void setScale(short scale) {
    this.scale = scale;
    setScaleIsSet(true);
  }

  public void unsetScale() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __SCALE_ISSET_ID);
  }

  /** Returns true if field scale is set (has been assigned a value) and false otherwise */
  public boolean isSetScale() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __SCALE_ISSET_ID);
  }

  public void setScaleIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __SCALE_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, @org.apache.thrift.annotation.Nullable java.lang.Object value) {
    switch (field) {
    case UNSCALED:
      if (value == null) {
        unsetUnscaled();
      } else {
        if (value instanceof byte[]) {
          setUnscaled((byte[])value);
        } else {
          setUnscaled((java.nio.ByteBuffer)value);
        }
      }
      break;

    case SCALE:
      if (value == null) {
        unsetScale();
      } else {
        setScale((java.lang.Short)value);
      }
      break;

    }
  }

  @org.apache.thrift.annotation.Nullable
  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case UNSCALED:
      return getUnscaled();

    case SCALE:
      return getScale();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case UNSCALED:
      return isSetUnscaled();
    case SCALE:
      return isSetScale();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof Decimal)
      return this.equals((Decimal)that);
    return false;
  }

  public boolean equals(Decimal that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_unscaled = true && this.isSetUnscaled();
    boolean that_present_unscaled = true && that.isSetUnscaled();
    if (this_present_unscaled || that_present_unscaled) {
      if (!(this_present_unscaled && that_present_unscaled))
        return false;
      if (!this.unscaled.equals(that.unscaled))
        return false;
    }

    boolean this_present_scale = true;
    boolean that_present_scale = true;
    if (this_present_scale || that_present_scale) {
      if (!(this_present_scale && that_present_scale))
        return false;
      if (this.scale != that.scale)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((isSetUnscaled()) ? 131071 : 524287);
    if (isSetUnscaled())
      hashCode = hashCode * 8191 + unscaled.hashCode();

    hashCode = hashCode * 8191 + scale;

    return hashCode;
  }

  @Override
  public int compareTo(Decimal other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.valueOf(isSetUnscaled()).compareTo(other.isSetUnscaled());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetUnscaled()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.unscaled, other.unscaled);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetScale()).compareTo(other.isSetScale());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetScale()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.scale, other.scale);
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
    java.lang.StringBuilder sb = new java.lang.StringBuilder("Decimal(");
    boolean first = true;

    sb.append("unscaled:");
    if (this.unscaled == null) {
      sb.append("null");
    } else {
      org.apache.thrift.TBaseHelper.toString(this.unscaled, sb);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("scale:");
    sb.append(this.scale);
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    if (!isSetUnscaled()) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'unscaled' is unset! Struct:" + toString());
    }

    if (!isSetScale()) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'scale' is unset! Struct:" + toString());
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

  private static class DecimalStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public DecimalStandardScheme getScheme() {
      return new DecimalStandardScheme();
    }
  }

  private static class DecimalStandardScheme extends org.apache.thrift.scheme.StandardScheme<Decimal> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, Decimal struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // UNSCALED
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.unscaled = iprot.readBinary();
              struct.setUnscaledIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // SCALE
            if (schemeField.type == org.apache.thrift.protocol.TType.I16) {
              struct.scale = iprot.readI16();
              struct.setScaleIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, Decimal struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.unscaled != null) {
        oprot.writeFieldBegin(UNSCALED_FIELD_DESC);
        oprot.writeBinary(struct.unscaled);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(SCALE_FIELD_DESC);
      oprot.writeI16(struct.scale);
      oprot.writeFieldEnd();
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class DecimalTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public DecimalTupleScheme getScheme() {
      return new DecimalTupleScheme();
    }
  }

  private static class DecimalTupleScheme extends org.apache.thrift.scheme.TupleScheme<Decimal> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, Decimal struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      oprot.writeBinary(struct.unscaled);
      oprot.writeI16(struct.scale);
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, Decimal struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      struct.unscaled = iprot.readBinary();
      struct.setUnscaledIsSet(true);
      struct.scale = iprot.readI16();
      struct.setScaleIsSet(true);
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

