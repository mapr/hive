/**
 * Autogenerated by Thrift Compiler (0.12.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package org.apache.hadoop.hive.metastore.api;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.12.0)")
public class CheckLockRequest implements org.apache.thrift.TBase<CheckLockRequest, CheckLockRequest._Fields>, java.io.Serializable, Cloneable, Comparable<CheckLockRequest> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("CheckLockRequest");

  private static final org.apache.thrift.protocol.TField LOCKID_FIELD_DESC = new org.apache.thrift.protocol.TField("lockid", org.apache.thrift.protocol.TType.I64, (short)1);
  private static final org.apache.thrift.protocol.TField TXNID_FIELD_DESC = new org.apache.thrift.protocol.TField("txnid", org.apache.thrift.protocol.TType.I64, (short)2);
  private static final org.apache.thrift.protocol.TField ELAPSED_MS_FIELD_DESC = new org.apache.thrift.protocol.TField("elapsed_ms", org.apache.thrift.protocol.TType.I64, (short)3);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new CheckLockRequestStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new CheckLockRequestTupleSchemeFactory();

  private long lockid; // required
  private long txnid; // optional
  private long elapsed_ms; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    LOCKID((short)1, "lockid"),
    TXNID((short)2, "txnid"),
    ELAPSED_MS((short)3, "elapsed_ms");

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
        case 1: // LOCKID
          return LOCKID;
        case 2: // TXNID
          return TXNID;
        case 3: // ELAPSED_MS
          return ELAPSED_MS;
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
  private static final int __LOCKID_ISSET_ID = 0;
  private static final int __TXNID_ISSET_ID = 1;
  private static final int __ELAPSED_MS_ISSET_ID = 2;
  private byte __isset_bitfield = 0;
  private static final _Fields optionals[] = {_Fields.TXNID,_Fields.ELAPSED_MS};
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.LOCKID, new org.apache.thrift.meta_data.FieldMetaData("lockid", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.TXNID, new org.apache.thrift.meta_data.FieldMetaData("txnid", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.ELAPSED_MS, new org.apache.thrift.meta_data.FieldMetaData("elapsed_ms", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(CheckLockRequest.class, metaDataMap);
  }

  public CheckLockRequest() {
  }

  public CheckLockRequest(
    long lockid)
  {
    this();
    this.lockid = lockid;
    setLockidIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public CheckLockRequest(CheckLockRequest other) {
    __isset_bitfield = other.__isset_bitfield;
    this.lockid = other.lockid;
    this.txnid = other.txnid;
    this.elapsed_ms = other.elapsed_ms;
  }

  public CheckLockRequest deepCopy() {
    return new CheckLockRequest(this);
  }

  @Override
  public void clear() {
    setLockidIsSet(false);
    this.lockid = 0;
    setTxnidIsSet(false);
    this.txnid = 0;
    setElapsed_msIsSet(false);
    this.elapsed_ms = 0;
  }

  public long getLockid() {
    return this.lockid;
  }

  public void setLockid(long lockid) {
    this.lockid = lockid;
    setLockidIsSet(true);
  }

  public void unsetLockid() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __LOCKID_ISSET_ID);
  }

  /** Returns true if field lockid is set (has been assigned a value) and false otherwise */
  public boolean isSetLockid() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __LOCKID_ISSET_ID);
  }

  public void setLockidIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __LOCKID_ISSET_ID, value);
  }

  public long getTxnid() {
    return this.txnid;
  }

  public void setTxnid(long txnid) {
    this.txnid = txnid;
    setTxnidIsSet(true);
  }

  public void unsetTxnid() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __TXNID_ISSET_ID);
  }

  /** Returns true if field txnid is set (has been assigned a value) and false otherwise */
  public boolean isSetTxnid() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __TXNID_ISSET_ID);
  }

  public void setTxnidIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __TXNID_ISSET_ID, value);
  }

  public long getElapsed_ms() {
    return this.elapsed_ms;
  }

  public void setElapsed_ms(long elapsed_ms) {
    this.elapsed_ms = elapsed_ms;
    setElapsed_msIsSet(true);
  }

  public void unsetElapsed_ms() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __ELAPSED_MS_ISSET_ID);
  }

  /** Returns true if field elapsed_ms is set (has been assigned a value) and false otherwise */
  public boolean isSetElapsed_ms() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __ELAPSED_MS_ISSET_ID);
  }

  public void setElapsed_msIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __ELAPSED_MS_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, @org.apache.thrift.annotation.Nullable java.lang.Object value) {
    switch (field) {
    case LOCKID:
      if (value == null) {
        unsetLockid();
      } else {
        setLockid((java.lang.Long)value);
      }
      break;

    case TXNID:
      if (value == null) {
        unsetTxnid();
      } else {
        setTxnid((java.lang.Long)value);
      }
      break;

    case ELAPSED_MS:
      if (value == null) {
        unsetElapsed_ms();
      } else {
        setElapsed_ms((java.lang.Long)value);
      }
      break;

    }
  }

  @org.apache.thrift.annotation.Nullable
  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case LOCKID:
      return getLockid();

    case TXNID:
      return getTxnid();

    case ELAPSED_MS:
      return getElapsed_ms();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case LOCKID:
      return isSetLockid();
    case TXNID:
      return isSetTxnid();
    case ELAPSED_MS:
      return isSetElapsed_ms();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof CheckLockRequest)
      return this.equals((CheckLockRequest)that);
    return false;
  }

  public boolean equals(CheckLockRequest that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_lockid = true;
    boolean that_present_lockid = true;
    if (this_present_lockid || that_present_lockid) {
      if (!(this_present_lockid && that_present_lockid))
        return false;
      if (this.lockid != that.lockid)
        return false;
    }

    boolean this_present_txnid = true && this.isSetTxnid();
    boolean that_present_txnid = true && that.isSetTxnid();
    if (this_present_txnid || that_present_txnid) {
      if (!(this_present_txnid && that_present_txnid))
        return false;
      if (this.txnid != that.txnid)
        return false;
    }

    boolean this_present_elapsed_ms = true && this.isSetElapsed_ms();
    boolean that_present_elapsed_ms = true && that.isSetElapsed_ms();
    if (this_present_elapsed_ms || that_present_elapsed_ms) {
      if (!(this_present_elapsed_ms && that_present_elapsed_ms))
        return false;
      if (this.elapsed_ms != that.elapsed_ms)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + org.apache.thrift.TBaseHelper.hashCode(lockid);

    hashCode = hashCode * 8191 + ((isSetTxnid()) ? 131071 : 524287);
    if (isSetTxnid())
      hashCode = hashCode * 8191 + org.apache.thrift.TBaseHelper.hashCode(txnid);

    hashCode = hashCode * 8191 + ((isSetElapsed_ms()) ? 131071 : 524287);
    if (isSetElapsed_ms())
      hashCode = hashCode * 8191 + org.apache.thrift.TBaseHelper.hashCode(elapsed_ms);

    return hashCode;
  }

  @Override
  public int compareTo(CheckLockRequest other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.valueOf(isSetLockid()).compareTo(other.isSetLockid());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetLockid()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.lockid, other.lockid);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetTxnid()).compareTo(other.isSetTxnid());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTxnid()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.txnid, other.txnid);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetElapsed_ms()).compareTo(other.isSetElapsed_ms());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetElapsed_ms()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.elapsed_ms, other.elapsed_ms);
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
    java.lang.StringBuilder sb = new java.lang.StringBuilder("CheckLockRequest(");
    boolean first = true;

    sb.append("lockid:");
    sb.append(this.lockid);
    first = false;
    if (isSetTxnid()) {
      if (!first) sb.append(", ");
      sb.append("txnid:");
      sb.append(this.txnid);
      first = false;
    }
    if (isSetElapsed_ms()) {
      if (!first) sb.append(", ");
      sb.append("elapsed_ms:");
      sb.append(this.elapsed_ms);
      first = false;
    }
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    if (!isSetLockid()) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'lockid' is unset! Struct:" + toString());
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

  private static class CheckLockRequestStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public CheckLockRequestStandardScheme getScheme() {
      return new CheckLockRequestStandardScheme();
    }
  }

  private static class CheckLockRequestStandardScheme extends org.apache.thrift.scheme.StandardScheme<CheckLockRequest> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, CheckLockRequest struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // LOCKID
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.lockid = iprot.readI64();
              struct.setLockidIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // TXNID
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.txnid = iprot.readI64();
              struct.setTxnidIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // ELAPSED_MS
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.elapsed_ms = iprot.readI64();
              struct.setElapsed_msIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, CheckLockRequest struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(LOCKID_FIELD_DESC);
      oprot.writeI64(struct.lockid);
      oprot.writeFieldEnd();
      if (struct.isSetTxnid()) {
        oprot.writeFieldBegin(TXNID_FIELD_DESC);
        oprot.writeI64(struct.txnid);
        oprot.writeFieldEnd();
      }
      if (struct.isSetElapsed_ms()) {
        oprot.writeFieldBegin(ELAPSED_MS_FIELD_DESC);
        oprot.writeI64(struct.elapsed_ms);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class CheckLockRequestTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public CheckLockRequestTupleScheme getScheme() {
      return new CheckLockRequestTupleScheme();
    }
  }

  private static class CheckLockRequestTupleScheme extends org.apache.thrift.scheme.TupleScheme<CheckLockRequest> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, CheckLockRequest struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      oprot.writeI64(struct.lockid);
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetTxnid()) {
        optionals.set(0);
      }
      if (struct.isSetElapsed_ms()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetTxnid()) {
        oprot.writeI64(struct.txnid);
      }
      if (struct.isSetElapsed_ms()) {
        oprot.writeI64(struct.elapsed_ms);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, CheckLockRequest struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      struct.lockid = iprot.readI64();
      struct.setLockidIsSet(true);
      java.util.BitSet incoming = iprot.readBitSet(2);
      if (incoming.get(0)) {
        struct.txnid = iprot.readI64();
        struct.setTxnidIsSet(true);
      }
      if (incoming.get(1)) {
        struct.elapsed_ms = iprot.readI64();
        struct.setElapsed_msIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

