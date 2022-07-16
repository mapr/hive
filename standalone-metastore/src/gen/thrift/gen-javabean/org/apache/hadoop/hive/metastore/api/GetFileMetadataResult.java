/**
 * Autogenerated by Thrift Compiler (0.16.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package org.apache.hadoop.hive.metastore.api;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.16.0)")
@org.apache.hadoop.classification.InterfaceAudience.Public @org.apache.hadoop.classification.InterfaceStability.Stable public class GetFileMetadataResult implements org.apache.thrift.TBase<GetFileMetadataResult, GetFileMetadataResult._Fields>, java.io.Serializable, Cloneable, Comparable<GetFileMetadataResult> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("GetFileMetadataResult");

  private static final org.apache.thrift.protocol.TField METADATA_FIELD_DESC = new org.apache.thrift.protocol.TField("metadata", org.apache.thrift.protocol.TType.MAP, (short)1);
  private static final org.apache.thrift.protocol.TField IS_SUPPORTED_FIELD_DESC = new org.apache.thrift.protocol.TField("isSupported", org.apache.thrift.protocol.TType.BOOL, (short)2);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new GetFileMetadataResultStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new GetFileMetadataResultTupleSchemeFactory();

  private @org.apache.thrift.annotation.Nullable java.util.Map<java.lang.Long,java.nio.ByteBuffer> metadata; // required
  private boolean isSupported; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    METADATA((short)1, "metadata"),
    IS_SUPPORTED((short)2, "isSupported");

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
        case 1: // METADATA
          return METADATA;
        case 2: // IS_SUPPORTED
          return IS_SUPPORTED;
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
  private static final int __ISSUPPORTED_ISSET_ID = 0;
  private byte __isset_bitfield = 0;
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.METADATA, new org.apache.thrift.meta_data.FieldMetaData("metadata", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.MapMetaData(org.apache.thrift.protocol.TType.MAP, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64), 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING            , true))));
    tmpMap.put(_Fields.IS_SUPPORTED, new org.apache.thrift.meta_data.FieldMetaData("isSupported", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.BOOL)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(GetFileMetadataResult.class, metaDataMap);
  }

  public GetFileMetadataResult() {
  }

  public GetFileMetadataResult(
    java.util.Map<java.lang.Long,java.nio.ByteBuffer> metadata,
    boolean isSupported)
  {
    this();
    this.metadata = metadata;
    this.isSupported = isSupported;
    setIsSupportedIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public GetFileMetadataResult(GetFileMetadataResult other) {
    __isset_bitfield = other.__isset_bitfield;
    if (other.isSetMetadata()) {
      java.util.Map<java.lang.Long,java.nio.ByteBuffer> __this__metadata = new java.util.HashMap<java.lang.Long,java.nio.ByteBuffer>(other.metadata);
      this.metadata = __this__metadata;
    }
    this.isSupported = other.isSupported;
  }

  public GetFileMetadataResult deepCopy() {
    return new GetFileMetadataResult(this);
  }

  @Override
  public void clear() {
    this.metadata = null;
    setIsSupportedIsSet(false);
    this.isSupported = false;
  }

  public int getMetadataSize() {
    return (this.metadata == null) ? 0 : this.metadata.size();
  }

  public void putToMetadata(long key, java.nio.ByteBuffer val) {
    if (this.metadata == null) {
      this.metadata = new java.util.HashMap<java.lang.Long,java.nio.ByteBuffer>();
    }
    this.metadata.put(key, val);
  }

  @org.apache.thrift.annotation.Nullable
  public java.util.Map<java.lang.Long,java.nio.ByteBuffer> getMetadata() {
    return this.metadata;
  }

  public void setMetadata(@org.apache.thrift.annotation.Nullable java.util.Map<java.lang.Long,java.nio.ByteBuffer> metadata) {
    this.metadata = metadata;
  }

  public void unsetMetadata() {
    this.metadata = null;
  }

  /** Returns true if field metadata is set (has been assigned a value) and false otherwise */
  public boolean isSetMetadata() {
    return this.metadata != null;
  }

  public void setMetadataIsSet(boolean value) {
    if (!value) {
      this.metadata = null;
    }
  }

  public boolean isIsSupported() {
    return this.isSupported;
  }

  public void setIsSupported(boolean isSupported) {
    this.isSupported = isSupported;
    setIsSupportedIsSet(true);
  }

  public void unsetIsSupported() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __ISSUPPORTED_ISSET_ID);
  }

  /** Returns true if field isSupported is set (has been assigned a value) and false otherwise */
  public boolean isSetIsSupported() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __ISSUPPORTED_ISSET_ID);
  }

  public void setIsSupportedIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __ISSUPPORTED_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, @org.apache.thrift.annotation.Nullable java.lang.Object value) {
    switch (field) {
    case METADATA:
      if (value == null) {
        unsetMetadata();
      } else {
        setMetadata((java.util.Map<java.lang.Long,java.nio.ByteBuffer>)value);
      }
      break;

    case IS_SUPPORTED:
      if (value == null) {
        unsetIsSupported();
      } else {
        setIsSupported((java.lang.Boolean)value);
      }
      break;

    }
  }

  @org.apache.thrift.annotation.Nullable
  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case METADATA:
      return getMetadata();

    case IS_SUPPORTED:
      return isIsSupported();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case METADATA:
      return isSetMetadata();
    case IS_SUPPORTED:
      return isSetIsSupported();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that instanceof GetFileMetadataResult)
      return this.equals((GetFileMetadataResult)that);
    return false;
  }

  public boolean equals(GetFileMetadataResult that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_metadata = true && this.isSetMetadata();
    boolean that_present_metadata = true && that.isSetMetadata();
    if (this_present_metadata || that_present_metadata) {
      if (!(this_present_metadata && that_present_metadata))
        return false;
      if (!this.metadata.equals(that.metadata))
        return false;
    }

    boolean this_present_isSupported = true;
    boolean that_present_isSupported = true;
    if (this_present_isSupported || that_present_isSupported) {
      if (!(this_present_isSupported && that_present_isSupported))
        return false;
      if (this.isSupported != that.isSupported)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((isSetMetadata()) ? 131071 : 524287);
    if (isSetMetadata())
      hashCode = hashCode * 8191 + metadata.hashCode();

    hashCode = hashCode * 8191 + ((isSupported) ? 131071 : 524287);

    return hashCode;
  }

  @Override
  public int compareTo(GetFileMetadataResult other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.compare(isSetMetadata(), other.isSetMetadata());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetMetadata()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.metadata, other.metadata);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.compare(isSetIsSupported(), other.isSetIsSupported());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetIsSupported()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.isSupported, other.isSupported);
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
    java.lang.StringBuilder sb = new java.lang.StringBuilder("GetFileMetadataResult(");
    boolean first = true;

    sb.append("metadata:");
    if (this.metadata == null) {
      sb.append("null");
    } else {
      sb.append(this.metadata);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("isSupported:");
    sb.append(this.isSupported);
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    if (!isSetMetadata()) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'metadata' is unset! Struct:" + toString());
    }

    if (!isSetIsSupported()) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'isSupported' is unset! Struct:" + toString());
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

  private static class GetFileMetadataResultStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public GetFileMetadataResultStandardScheme getScheme() {
      return new GetFileMetadataResultStandardScheme();
    }
  }

  private static class GetFileMetadataResultStandardScheme extends org.apache.thrift.scheme.StandardScheme<GetFileMetadataResult> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, GetFileMetadataResult struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // METADATA
            if (schemeField.type == org.apache.thrift.protocol.TType.MAP) {
              {
                org.apache.thrift.protocol.TMap _map766 = iprot.readMapBegin();
                struct.metadata = new java.util.HashMap<java.lang.Long,java.nio.ByteBuffer>(2*_map766.size);
                long _key767;
                @org.apache.thrift.annotation.Nullable java.nio.ByteBuffer _val768;
                for (int _i769 = 0; _i769 < _map766.size; ++_i769)
                {
                  _key767 = iprot.readI64();
                  _val768 = iprot.readBinary();
                  struct.metadata.put(_key767, _val768);
                }
                iprot.readMapEnd();
              }
              struct.setMetadataIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // IS_SUPPORTED
            if (schemeField.type == org.apache.thrift.protocol.TType.BOOL) {
              struct.isSupported = iprot.readBool();
              struct.setIsSupportedIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, GetFileMetadataResult struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.metadata != null) {
        oprot.writeFieldBegin(METADATA_FIELD_DESC);
        {
          oprot.writeMapBegin(new org.apache.thrift.protocol.TMap(org.apache.thrift.protocol.TType.I64, org.apache.thrift.protocol.TType.STRING, struct.metadata.size()));
          for (java.util.Map.Entry<java.lang.Long, java.nio.ByteBuffer> _iter770 : struct.metadata.entrySet())
          {
            oprot.writeI64(_iter770.getKey());
            oprot.writeBinary(_iter770.getValue());
          }
          oprot.writeMapEnd();
        }
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(IS_SUPPORTED_FIELD_DESC);
      oprot.writeBool(struct.isSupported);
      oprot.writeFieldEnd();
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class GetFileMetadataResultTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public GetFileMetadataResultTupleScheme getScheme() {
      return new GetFileMetadataResultTupleScheme();
    }
  }

  private static class GetFileMetadataResultTupleScheme extends org.apache.thrift.scheme.TupleScheme<GetFileMetadataResult> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, GetFileMetadataResult struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      {
        oprot.writeI32(struct.metadata.size());
        for (java.util.Map.Entry<java.lang.Long, java.nio.ByteBuffer> _iter771 : struct.metadata.entrySet())
        {
          oprot.writeI64(_iter771.getKey());
          oprot.writeBinary(_iter771.getValue());
        }
      }
      oprot.writeBool(struct.isSupported);
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, GetFileMetadataResult struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      {
        org.apache.thrift.protocol.TMap _map772 = iprot.readMapBegin(org.apache.thrift.protocol.TType.I64, org.apache.thrift.protocol.TType.STRING); 
        struct.metadata = new java.util.HashMap<java.lang.Long,java.nio.ByteBuffer>(2*_map772.size);
        long _key773;
        @org.apache.thrift.annotation.Nullable java.nio.ByteBuffer _val774;
        for (int _i775 = 0; _i775 < _map772.size; ++_i775)
        {
          _key773 = iprot.readI64();
          _val774 = iprot.readBinary();
          struct.metadata.put(_key773, _val774);
        }
      }
      struct.setMetadataIsSet(true);
      struct.isSupported = iprot.readBool();
      struct.setIsSupportedIsSet(true);
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

