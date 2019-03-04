/**
 * Autogenerated by Thrift Compiler (0.12.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package org.apache.hadoop.hive.serde.test;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.12.0)")
public class ThriftTestObj implements org.apache.thrift.TBase<ThriftTestObj, ThriftTestObj._Fields>, java.io.Serializable, Cloneable, Comparable<ThriftTestObj> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("ThriftTestObj");

  private static final org.apache.thrift.protocol.TField FIELD1_FIELD_DESC = new org.apache.thrift.protocol.TField("field1", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField FIELD2_FIELD_DESC = new org.apache.thrift.protocol.TField("field2", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField FIELD3_FIELD_DESC = new org.apache.thrift.protocol.TField("field3", org.apache.thrift.protocol.TType.LIST, (short)3);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new ThriftTestObjStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new ThriftTestObjTupleSchemeFactory();

  private int field1; // required
  private @org.apache.thrift.annotation.Nullable java.lang.String field2; // required
  private @org.apache.thrift.annotation.Nullable java.util.List<InnerStruct> field3; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    FIELD1((short)1, "field1"),
    FIELD2((short)2, "field2"),
    FIELD3((short)3, "field3");

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
        case 1: // FIELD1
          return FIELD1;
        case 2: // FIELD2
          return FIELD2;
        case 3: // FIELD3
          return FIELD3;
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
  private static final int __FIELD1_ISSET_ID = 0;
  private byte __isset_bitfield = 0;
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.FIELD1, new org.apache.thrift.meta_data.FieldMetaData("field1", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.FIELD2, new org.apache.thrift.meta_data.FieldMetaData("field2", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.FIELD3, new org.apache.thrift.meta_data.FieldMetaData("field3", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, InnerStruct.class))));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(ThriftTestObj.class, metaDataMap);
  }

  public ThriftTestObj() {
  }

  public ThriftTestObj(
    int field1,
    java.lang.String field2,
    java.util.List<InnerStruct> field3)
  {
    this();
    this.field1 = field1;
    setField1IsSet(true);
    this.field2 = field2;
    this.field3 = field3;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public ThriftTestObj(ThriftTestObj other) {
    __isset_bitfield = other.__isset_bitfield;
    this.field1 = other.field1;
    if (other.isSetField2()) {
      this.field2 = other.field2;
    }
    if (other.isSetField3()) {
      java.util.List<InnerStruct> __this__field3 = new java.util.ArrayList<InnerStruct>(other.field3.size());
      for (InnerStruct other_element : other.field3) {
        __this__field3.add(new InnerStruct(other_element));
      }
      this.field3 = __this__field3;
    }
  }

  public ThriftTestObj deepCopy() {
    return new ThriftTestObj(this);
  }

  @Override
  public void clear() {
    setField1IsSet(false);
    this.field1 = 0;
    this.field2 = null;
    this.field3 = null;
  }

  public int getField1() {
    return this.field1;
  }

  public void setField1(int field1) {
    this.field1 = field1;
    setField1IsSet(true);
  }

  public void unsetField1() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __FIELD1_ISSET_ID);
  }

  /** Returns true if field field1 is set (has been assigned a value) and false otherwise */
  public boolean isSetField1() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __FIELD1_ISSET_ID);
  }

  public void setField1IsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __FIELD1_ISSET_ID, value);
  }

  @org.apache.thrift.annotation.Nullable
  public java.lang.String getField2() {
    return this.field2;
  }

  public void setField2(@org.apache.thrift.annotation.Nullable java.lang.String field2) {
    this.field2 = field2;
  }

  public void unsetField2() {
    this.field2 = null;
  }

  /** Returns true if field field2 is set (has been assigned a value) and false otherwise */
  public boolean isSetField2() {
    return this.field2 != null;
  }

  public void setField2IsSet(boolean value) {
    if (!value) {
      this.field2 = null;
    }
  }

  public int getField3Size() {
    return (this.field3 == null) ? 0 : this.field3.size();
  }

  @org.apache.thrift.annotation.Nullable
  public java.util.Iterator<InnerStruct> getField3Iterator() {
    return (this.field3 == null) ? null : this.field3.iterator();
  }

  public void addToField3(InnerStruct elem) {
    if (this.field3 == null) {
      this.field3 = new java.util.ArrayList<InnerStruct>();
    }
    this.field3.add(elem);
  }

  @org.apache.thrift.annotation.Nullable
  public java.util.List<InnerStruct> getField3() {
    return this.field3;
  }

  public void setField3(@org.apache.thrift.annotation.Nullable java.util.List<InnerStruct> field3) {
    this.field3 = field3;
  }

  public void unsetField3() {
    this.field3 = null;
  }

  /** Returns true if field field3 is set (has been assigned a value) and false otherwise */
  public boolean isSetField3() {
    return this.field3 != null;
  }

  public void setField3IsSet(boolean value) {
    if (!value) {
      this.field3 = null;
    }
  }

  public void setFieldValue(_Fields field, @org.apache.thrift.annotation.Nullable java.lang.Object value) {
    switch (field) {
    case FIELD1:
      if (value == null) {
        unsetField1();
      } else {
        setField1((java.lang.Integer)value);
      }
      break;

    case FIELD2:
      if (value == null) {
        unsetField2();
      } else {
        setField2((java.lang.String)value);
      }
      break;

    case FIELD3:
      if (value == null) {
        unsetField3();
      } else {
        setField3((java.util.List<InnerStruct>)value);
      }
      break;

    }
  }

  @org.apache.thrift.annotation.Nullable
  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case FIELD1:
      return getField1();

    case FIELD2:
      return getField2();

    case FIELD3:
      return getField3();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case FIELD1:
      return isSetField1();
    case FIELD2:
      return isSetField2();
    case FIELD3:
      return isSetField3();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof ThriftTestObj)
      return this.equals((ThriftTestObj)that);
    return false;
  }

  public boolean equals(ThriftTestObj that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_field1 = true;
    boolean that_present_field1 = true;
    if (this_present_field1 || that_present_field1) {
      if (!(this_present_field1 && that_present_field1))
        return false;
      if (this.field1 != that.field1)
        return false;
    }

    boolean this_present_field2 = true && this.isSetField2();
    boolean that_present_field2 = true && that.isSetField2();
    if (this_present_field2 || that_present_field2) {
      if (!(this_present_field2 && that_present_field2))
        return false;
      if (!this.field2.equals(that.field2))
        return false;
    }

    boolean this_present_field3 = true && this.isSetField3();
    boolean that_present_field3 = true && that.isSetField3();
    if (this_present_field3 || that_present_field3) {
      if (!(this_present_field3 && that_present_field3))
        return false;
      if (!this.field3.equals(that.field3))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + field1;

    hashCode = hashCode * 8191 + ((isSetField2()) ? 131071 : 524287);
    if (isSetField2())
      hashCode = hashCode * 8191 + field2.hashCode();

    hashCode = hashCode * 8191 + ((isSetField3()) ? 131071 : 524287);
    if (isSetField3())
      hashCode = hashCode * 8191 + field3.hashCode();

    return hashCode;
  }

  @Override
  public int compareTo(ThriftTestObj other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.valueOf(isSetField1()).compareTo(other.isSetField1());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetField1()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.field1, other.field1);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetField2()).compareTo(other.isSetField2());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetField2()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.field2, other.field2);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetField3()).compareTo(other.isSetField3());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetField3()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.field3, other.field3);
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
    java.lang.StringBuilder sb = new java.lang.StringBuilder("ThriftTestObj(");
    boolean first = true;

    sb.append("field1:");
    sb.append(this.field1);
    first = false;
    if (!first) sb.append(", ");
    sb.append("field2:");
    if (this.field2 == null) {
      sb.append("null");
    } else {
      sb.append(this.field2);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("field3:");
    if (this.field3 == null) {
      sb.append("null");
    } else {
      sb.append(this.field3);
    }
    first = false;
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
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class ThriftTestObjStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public ThriftTestObjStandardScheme getScheme() {
      return new ThriftTestObjStandardScheme();
    }
  }

  private static class ThriftTestObjStandardScheme extends org.apache.thrift.scheme.StandardScheme<ThriftTestObj> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, ThriftTestObj struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // FIELD1
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.field1 = iprot.readI32();
              struct.setField1IsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // FIELD2
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.field2 = iprot.readString();
              struct.setField2IsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // FIELD3
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list0 = iprot.readListBegin();
                struct.field3 = new java.util.ArrayList<InnerStruct>(_list0.size);
                @org.apache.thrift.annotation.Nullable InnerStruct _elem1;
                for (int _i2 = 0; _i2 < _list0.size; ++_i2)
                {
                  _elem1 = new InnerStruct();
                  _elem1.read(iprot);
                  struct.field3.add(_elem1);
                }
                iprot.readListEnd();
              }
              struct.setField3IsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, ThriftTestObj struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(FIELD1_FIELD_DESC);
      oprot.writeI32(struct.field1);
      oprot.writeFieldEnd();
      if (struct.field2 != null) {
        oprot.writeFieldBegin(FIELD2_FIELD_DESC);
        oprot.writeString(struct.field2);
        oprot.writeFieldEnd();
      }
      if (struct.field3 != null) {
        oprot.writeFieldBegin(FIELD3_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.field3.size()));
          for (InnerStruct _iter3 : struct.field3)
          {
            _iter3.write(oprot);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class ThriftTestObjTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public ThriftTestObjTupleScheme getScheme() {
      return new ThriftTestObjTupleScheme();
    }
  }

  private static class ThriftTestObjTupleScheme extends org.apache.thrift.scheme.TupleScheme<ThriftTestObj> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, ThriftTestObj struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetField1()) {
        optionals.set(0);
      }
      if (struct.isSetField2()) {
        optionals.set(1);
      }
      if (struct.isSetField3()) {
        optionals.set(2);
      }
      oprot.writeBitSet(optionals, 3);
      if (struct.isSetField1()) {
        oprot.writeI32(struct.field1);
      }
      if (struct.isSetField2()) {
        oprot.writeString(struct.field2);
      }
      if (struct.isSetField3()) {
        {
          oprot.writeI32(struct.field3.size());
          for (InnerStruct _iter4 : struct.field3)
          {
            _iter4.write(oprot);
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, ThriftTestObj struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(3);
      if (incoming.get(0)) {
        struct.field1 = iprot.readI32();
        struct.setField1IsSet(true);
      }
      if (incoming.get(1)) {
        struct.field2 = iprot.readString();
        struct.setField2IsSet(true);
      }
      if (incoming.get(2)) {
        {
          org.apache.thrift.protocol.TList _list5 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
          struct.field3 = new java.util.ArrayList<InnerStruct>(_list5.size);
          @org.apache.thrift.annotation.Nullable InnerStruct _elem6;
          for (int _i7 = 0; _i7 < _list5.size; ++_i7)
          {
            _elem6 = new InnerStruct();
            _elem6.read(iprot);
            struct.field3.add(_elem6);
          }
        }
        struct.setField3IsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

