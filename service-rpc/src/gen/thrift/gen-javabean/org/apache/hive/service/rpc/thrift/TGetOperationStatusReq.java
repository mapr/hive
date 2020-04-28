/**
 * Autogenerated by Thrift Compiler (0.13.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package org.apache.hive.service.rpc.thrift;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.13.0)")
public class TGetOperationStatusReq implements org.apache.thrift.TBase<TGetOperationStatusReq, TGetOperationStatusReq._Fields>, java.io.Serializable, Cloneable, Comparable<TGetOperationStatusReq> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("TGetOperationStatusReq");

  private static final org.apache.thrift.protocol.TField OPERATION_HANDLE_FIELD_DESC = new org.apache.thrift.protocol.TField("operationHandle", org.apache.thrift.protocol.TType.STRUCT, (short)1);
  private static final org.apache.thrift.protocol.TField GET_PROGRESS_UPDATE_FIELD_DESC = new org.apache.thrift.protocol.TField("getProgressUpdate", org.apache.thrift.protocol.TType.BOOL, (short)2);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new TGetOperationStatusReqStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new TGetOperationStatusReqTupleSchemeFactory();

  private @org.apache.thrift.annotation.Nullable TOperationHandle operationHandle; // required
  private boolean getProgressUpdate; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    OPERATION_HANDLE((short)1, "operationHandle"),
    GET_PROGRESS_UPDATE((short)2, "getProgressUpdate");

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
        case 1: // OPERATION_HANDLE
          return OPERATION_HANDLE;
        case 2: // GET_PROGRESS_UPDATE
          return GET_PROGRESS_UPDATE;
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
  private static final int __GETPROGRESSUPDATE_ISSET_ID = 0;
  private byte __isset_bitfield = 0;
  private static final _Fields optionals[] = {_Fields.GET_PROGRESS_UPDATE};
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.OPERATION_HANDLE, new org.apache.thrift.meta_data.FieldMetaData("operationHandle", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, TOperationHandle.class)));
    tmpMap.put(_Fields.GET_PROGRESS_UPDATE, new org.apache.thrift.meta_data.FieldMetaData("getProgressUpdate", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.BOOL)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(TGetOperationStatusReq.class, metaDataMap);
  }

  public TGetOperationStatusReq() {
  }

  public TGetOperationStatusReq(
    TOperationHandle operationHandle)
  {
    this();
    this.operationHandle = operationHandle;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public TGetOperationStatusReq(TGetOperationStatusReq other) {
    __isset_bitfield = other.__isset_bitfield;
    if (other.isSetOperationHandle()) {
      this.operationHandle = new TOperationHandle(other.operationHandle);
    }
    this.getProgressUpdate = other.getProgressUpdate;
  }

  public TGetOperationStatusReq deepCopy() {
    return new TGetOperationStatusReq(this);
  }

  @Override
  public void clear() {
    this.operationHandle = null;
    setGetProgressUpdateIsSet(false);
    this.getProgressUpdate = false;
  }

  @org.apache.thrift.annotation.Nullable
  public TOperationHandle getOperationHandle() {
    return this.operationHandle;
  }

  public void setOperationHandle(@org.apache.thrift.annotation.Nullable TOperationHandle operationHandle) {
    this.operationHandle = operationHandle;
  }

  public void unsetOperationHandle() {
    this.operationHandle = null;
  }

  /** Returns true if field operationHandle is set (has been assigned a value) and false otherwise */
  public boolean isSetOperationHandle() {
    return this.operationHandle != null;
  }

  public void setOperationHandleIsSet(boolean value) {
    if (!value) {
      this.operationHandle = null;
    }
  }

  public boolean isGetProgressUpdate() {
    return this.getProgressUpdate;
  }

  public void setGetProgressUpdate(boolean getProgressUpdate) {
    this.getProgressUpdate = getProgressUpdate;
    setGetProgressUpdateIsSet(true);
  }

  public void unsetGetProgressUpdate() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __GETPROGRESSUPDATE_ISSET_ID);
  }

  /** Returns true if field getProgressUpdate is set (has been assigned a value) and false otherwise */
  public boolean isSetGetProgressUpdate() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __GETPROGRESSUPDATE_ISSET_ID);
  }

  public void setGetProgressUpdateIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __GETPROGRESSUPDATE_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, @org.apache.thrift.annotation.Nullable java.lang.Object value) {
    switch (field) {
    case OPERATION_HANDLE:
      if (value == null) {
        unsetOperationHandle();
      } else {
        setOperationHandle((TOperationHandle)value);
      }
      break;

    case GET_PROGRESS_UPDATE:
      if (value == null) {
        unsetGetProgressUpdate();
      } else {
        setGetProgressUpdate((java.lang.Boolean)value);
      }
      break;

    }
  }

  @org.apache.thrift.annotation.Nullable
  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case OPERATION_HANDLE:
      return getOperationHandle();

    case GET_PROGRESS_UPDATE:
      return isGetProgressUpdate();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case OPERATION_HANDLE:
      return isSetOperationHandle();
    case GET_PROGRESS_UPDATE:
      return isSetGetProgressUpdate();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof TGetOperationStatusReq)
      return this.equals((TGetOperationStatusReq)that);
    return false;
  }

  public boolean equals(TGetOperationStatusReq that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_operationHandle = true && this.isSetOperationHandle();
    boolean that_present_operationHandle = true && that.isSetOperationHandle();
    if (this_present_operationHandle || that_present_operationHandle) {
      if (!(this_present_operationHandle && that_present_operationHandle))
        return false;
      if (!this.operationHandle.equals(that.operationHandle))
        return false;
    }

    boolean this_present_getProgressUpdate = true && this.isSetGetProgressUpdate();
    boolean that_present_getProgressUpdate = true && that.isSetGetProgressUpdate();
    if (this_present_getProgressUpdate || that_present_getProgressUpdate) {
      if (!(this_present_getProgressUpdate && that_present_getProgressUpdate))
        return false;
      if (this.getProgressUpdate != that.getProgressUpdate)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((isSetOperationHandle()) ? 131071 : 524287);
    if (isSetOperationHandle())
      hashCode = hashCode * 8191 + operationHandle.hashCode();

    hashCode = hashCode * 8191 + ((isSetGetProgressUpdate()) ? 131071 : 524287);
    if (isSetGetProgressUpdate())
      hashCode = hashCode * 8191 + ((getProgressUpdate) ? 131071 : 524287);

    return hashCode;
  }

  @Override
  public int compareTo(TGetOperationStatusReq other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.valueOf(isSetOperationHandle()).compareTo(other.isSetOperationHandle());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetOperationHandle()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.operationHandle, other.operationHandle);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetGetProgressUpdate()).compareTo(other.isSetGetProgressUpdate());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetGetProgressUpdate()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.getProgressUpdate, other.getProgressUpdate);
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
    java.lang.StringBuilder sb = new java.lang.StringBuilder("TGetOperationStatusReq(");
    boolean first = true;

    sb.append("operationHandle:");
    if (this.operationHandle == null) {
      sb.append("null");
    } else {
      sb.append(this.operationHandle);
    }
    first = false;
    if (isSetGetProgressUpdate()) {
      if (!first) sb.append(", ");
      sb.append("getProgressUpdate:");
      sb.append(this.getProgressUpdate);
      first = false;
    }
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    if (!isSetOperationHandle()) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'operationHandle' is unset! Struct:" + toString());
    }

    // check for sub-struct validity
    if (operationHandle != null) {
      operationHandle.validate();
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
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class TGetOperationStatusReqStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public TGetOperationStatusReqStandardScheme getScheme() {
      return new TGetOperationStatusReqStandardScheme();
    }
  }

  private static class TGetOperationStatusReqStandardScheme extends org.apache.thrift.scheme.StandardScheme<TGetOperationStatusReq> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, TGetOperationStatusReq struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // OPERATION_HANDLE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.operationHandle = new TOperationHandle();
              struct.operationHandle.read(iprot);
              struct.setOperationHandleIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // GET_PROGRESS_UPDATE
            if (schemeField.type == org.apache.thrift.protocol.TType.BOOL) {
              struct.getProgressUpdate = iprot.readBool();
              struct.setGetProgressUpdateIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, TGetOperationStatusReq struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.operationHandle != null) {
        oprot.writeFieldBegin(OPERATION_HANDLE_FIELD_DESC);
        struct.operationHandle.write(oprot);
        oprot.writeFieldEnd();
      }
      if (struct.isSetGetProgressUpdate()) {
        oprot.writeFieldBegin(GET_PROGRESS_UPDATE_FIELD_DESC);
        oprot.writeBool(struct.getProgressUpdate);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class TGetOperationStatusReqTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public TGetOperationStatusReqTupleScheme getScheme() {
      return new TGetOperationStatusReqTupleScheme();
    }
  }

  private static class TGetOperationStatusReqTupleScheme extends org.apache.thrift.scheme.TupleScheme<TGetOperationStatusReq> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, TGetOperationStatusReq struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      struct.operationHandle.write(oprot);
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetGetProgressUpdate()) {
        optionals.set(0);
      }
      oprot.writeBitSet(optionals, 1);
      if (struct.isSetGetProgressUpdate()) {
        oprot.writeBool(struct.getProgressUpdate);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, TGetOperationStatusReq struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      struct.operationHandle = new TOperationHandle();
      struct.operationHandle.read(iprot);
      struct.setOperationHandleIsSet(true);
      java.util.BitSet incoming = iprot.readBitSet(1);
      if (incoming.get(0)) {
        struct.getProgressUpdate = iprot.readBool();
        struct.setGetProgressUpdateIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

