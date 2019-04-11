/**
 * Autogenerated by Thrift Compiler (0.12.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package org.apache.hadoop.hive.metastore.api;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.12.0)")
public class CmRecycleRequest implements org.apache.thrift.TBase<CmRecycleRequest, CmRecycleRequest._Fields>, java.io.Serializable, Cloneable, Comparable<CmRecycleRequest> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("CmRecycleRequest");

  private static final org.apache.thrift.protocol.TField DATA_PATH_FIELD_DESC = new org.apache.thrift.protocol.TField("dataPath", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField PURGE_FIELD_DESC = new org.apache.thrift.protocol.TField("purge", org.apache.thrift.protocol.TType.BOOL, (short)2);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new CmRecycleRequestStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new CmRecycleRequestTupleSchemeFactory();

  private @org.apache.thrift.annotation.Nullable java.lang.String dataPath; // required
  private boolean purge; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    DATA_PATH((short)1, "dataPath"),
    PURGE((short)2, "purge");

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
        case 1: // DATA_PATH
          return DATA_PATH;
        case 2: // PURGE
          return PURGE;
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
  private static final int __PURGE_ISSET_ID = 0;
  private byte __isset_bitfield = 0;
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.DATA_PATH, new org.apache.thrift.meta_data.FieldMetaData("dataPath", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.PURGE, new org.apache.thrift.meta_data.FieldMetaData("purge", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.BOOL)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(CmRecycleRequest.class, metaDataMap);
  }

  public CmRecycleRequest() {
  }

  public CmRecycleRequest(
    java.lang.String dataPath,
    boolean purge)
  {
    this();
    this.dataPath = dataPath;
    this.purge = purge;
    setPurgeIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public CmRecycleRequest(CmRecycleRequest other) {
    __isset_bitfield = other.__isset_bitfield;
    if (other.isSetDataPath()) {
      this.dataPath = other.dataPath;
    }
    this.purge = other.purge;
  }

  public CmRecycleRequest deepCopy() {
    return new CmRecycleRequest(this);
  }

  @Override
  public void clear() {
    this.dataPath = null;
    setPurgeIsSet(false);
    this.purge = false;
  }

  @org.apache.thrift.annotation.Nullable
  public java.lang.String getDataPath() {
    return this.dataPath;
  }

  public void setDataPath(@org.apache.thrift.annotation.Nullable java.lang.String dataPath) {
    this.dataPath = dataPath;
  }

  public void unsetDataPath() {
    this.dataPath = null;
  }

  /** Returns true if field dataPath is set (has been assigned a value) and false otherwise */
  public boolean isSetDataPath() {
    return this.dataPath != null;
  }

  public void setDataPathIsSet(boolean value) {
    if (!value) {
      this.dataPath = null;
    }
  }

  public boolean isPurge() {
    return this.purge;
  }

  public void setPurge(boolean purge) {
    this.purge = purge;
    setPurgeIsSet(true);
  }

  public void unsetPurge() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __PURGE_ISSET_ID);
  }

  /** Returns true if field purge is set (has been assigned a value) and false otherwise */
  public boolean isSetPurge() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __PURGE_ISSET_ID);
  }

  public void setPurgeIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __PURGE_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, @org.apache.thrift.annotation.Nullable java.lang.Object value) {
    switch (field) {
    case DATA_PATH:
      if (value == null) {
        unsetDataPath();
      } else {
        setDataPath((java.lang.String)value);
      }
      break;

    case PURGE:
      if (value == null) {
        unsetPurge();
      } else {
        setPurge((java.lang.Boolean)value);
      }
      break;

    }
  }

  @org.apache.thrift.annotation.Nullable
  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case DATA_PATH:
      return getDataPath();

    case PURGE:
      return isPurge();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case DATA_PATH:
      return isSetDataPath();
    case PURGE:
      return isSetPurge();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof CmRecycleRequest)
      return this.equals((CmRecycleRequest)that);
    return false;
  }

  public boolean equals(CmRecycleRequest that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_dataPath = true && this.isSetDataPath();
    boolean that_present_dataPath = true && that.isSetDataPath();
    if (this_present_dataPath || that_present_dataPath) {
      if (!(this_present_dataPath && that_present_dataPath))
        return false;
      if (!this.dataPath.equals(that.dataPath))
        return false;
    }

    boolean this_present_purge = true;
    boolean that_present_purge = true;
    if (this_present_purge || that_present_purge) {
      if (!(this_present_purge && that_present_purge))
        return false;
      if (this.purge != that.purge)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((isSetDataPath()) ? 131071 : 524287);
    if (isSetDataPath())
      hashCode = hashCode * 8191 + dataPath.hashCode();

    hashCode = hashCode * 8191 + ((purge) ? 131071 : 524287);

    return hashCode;
  }

  @Override
  public int compareTo(CmRecycleRequest other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.valueOf(isSetDataPath()).compareTo(other.isSetDataPath());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetDataPath()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.dataPath, other.dataPath);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetPurge()).compareTo(other.isSetPurge());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPurge()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.purge, other.purge);
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
    java.lang.StringBuilder sb = new java.lang.StringBuilder("CmRecycleRequest(");
    boolean first = true;

    sb.append("dataPath:");
    if (this.dataPath == null) {
      sb.append("null");
    } else {
      sb.append(this.dataPath);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("purge:");
    sb.append(this.purge);
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    if (!isSetDataPath()) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'dataPath' is unset! Struct:" + toString());
    }

    if (!isSetPurge()) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'purge' is unset! Struct:" + toString());
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

  private static class CmRecycleRequestStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public CmRecycleRequestStandardScheme getScheme() {
      return new CmRecycleRequestStandardScheme();
    }
  }

  private static class CmRecycleRequestStandardScheme extends org.apache.thrift.scheme.StandardScheme<CmRecycleRequest> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, CmRecycleRequest struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // DATA_PATH
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.dataPath = iprot.readString();
              struct.setDataPathIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // PURGE
            if (schemeField.type == org.apache.thrift.protocol.TType.BOOL) {
              struct.purge = iprot.readBool();
              struct.setPurgeIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, CmRecycleRequest struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.dataPath != null) {
        oprot.writeFieldBegin(DATA_PATH_FIELD_DESC);
        oprot.writeString(struct.dataPath);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(PURGE_FIELD_DESC);
      oprot.writeBool(struct.purge);
      oprot.writeFieldEnd();
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class CmRecycleRequestTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public CmRecycleRequestTupleScheme getScheme() {
      return new CmRecycleRequestTupleScheme();
    }
  }

  private static class CmRecycleRequestTupleScheme extends org.apache.thrift.scheme.TupleScheme<CmRecycleRequest> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, CmRecycleRequest struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      oprot.writeString(struct.dataPath);
      oprot.writeBool(struct.purge);
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, CmRecycleRequest struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      struct.dataPath = iprot.readString();
      struct.setDataPathIsSet(true);
      struct.purge = iprot.readBool();
      struct.setPurgeIsSet(true);
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

