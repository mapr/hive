/**
 * Autogenerated by Thrift Compiler (0.12.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package org.apache.hadoop.hive.metastore.api;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.12.0)")
public class OpenTxnsResponse implements org.apache.thrift.TBase<OpenTxnsResponse, OpenTxnsResponse._Fields>, java.io.Serializable, Cloneable, Comparable<OpenTxnsResponse> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("OpenTxnsResponse");

  private static final org.apache.thrift.protocol.TField TXN_IDS_FIELD_DESC = new org.apache.thrift.protocol.TField("txn_ids", org.apache.thrift.protocol.TType.LIST, (short)1);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new OpenTxnsResponseStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new OpenTxnsResponseTupleSchemeFactory();

  private @org.apache.thrift.annotation.Nullable java.util.List<java.lang.Long> txn_ids; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    TXN_IDS((short)1, "txn_ids");

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
        case 1: // TXN_IDS
          return TXN_IDS;
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
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.TXN_IDS, new org.apache.thrift.meta_data.FieldMetaData("txn_ids", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64))));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(OpenTxnsResponse.class, metaDataMap);
  }

  public OpenTxnsResponse() {
  }

  public OpenTxnsResponse(
    java.util.List<java.lang.Long> txn_ids)
  {
    this();
    this.txn_ids = txn_ids;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public OpenTxnsResponse(OpenTxnsResponse other) {
    if (other.isSetTxn_ids()) {
      java.util.List<java.lang.Long> __this__txn_ids = new java.util.ArrayList<java.lang.Long>(other.txn_ids);
      this.txn_ids = __this__txn_ids;
    }
  }

  public OpenTxnsResponse deepCopy() {
    return new OpenTxnsResponse(this);
  }

  @Override
  public void clear() {
    this.txn_ids = null;
  }

  public int getTxn_idsSize() {
    return (this.txn_ids == null) ? 0 : this.txn_ids.size();
  }

  @org.apache.thrift.annotation.Nullable
  public java.util.Iterator<java.lang.Long> getTxn_idsIterator() {
    return (this.txn_ids == null) ? null : this.txn_ids.iterator();
  }

  public void addToTxn_ids(long elem) {
    if (this.txn_ids == null) {
      this.txn_ids = new java.util.ArrayList<java.lang.Long>();
    }
    this.txn_ids.add(elem);
  }

  @org.apache.thrift.annotation.Nullable
  public java.util.List<java.lang.Long> getTxn_ids() {
    return this.txn_ids;
  }

  public void setTxn_ids(@org.apache.thrift.annotation.Nullable java.util.List<java.lang.Long> txn_ids) {
    this.txn_ids = txn_ids;
  }

  public void unsetTxn_ids() {
    this.txn_ids = null;
  }

  /** Returns true if field txn_ids is set (has been assigned a value) and false otherwise */
  public boolean isSetTxn_ids() {
    return this.txn_ids != null;
  }

  public void setTxn_idsIsSet(boolean value) {
    if (!value) {
      this.txn_ids = null;
    }
  }

  public void setFieldValue(_Fields field, @org.apache.thrift.annotation.Nullable java.lang.Object value) {
    switch (field) {
    case TXN_IDS:
      if (value == null) {
        unsetTxn_ids();
      } else {
        setTxn_ids((java.util.List<java.lang.Long>)value);
      }
      break;

    }
  }

  @org.apache.thrift.annotation.Nullable
  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case TXN_IDS:
      return getTxn_ids();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case TXN_IDS:
      return isSetTxn_ids();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof OpenTxnsResponse)
      return this.equals((OpenTxnsResponse)that);
    return false;
  }

  public boolean equals(OpenTxnsResponse that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_txn_ids = true && this.isSetTxn_ids();
    boolean that_present_txn_ids = true && that.isSetTxn_ids();
    if (this_present_txn_ids || that_present_txn_ids) {
      if (!(this_present_txn_ids && that_present_txn_ids))
        return false;
      if (!this.txn_ids.equals(that.txn_ids))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((isSetTxn_ids()) ? 131071 : 524287);
    if (isSetTxn_ids())
      hashCode = hashCode * 8191 + txn_ids.hashCode();

    return hashCode;
  }

  @Override
  public int compareTo(OpenTxnsResponse other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.valueOf(isSetTxn_ids()).compareTo(other.isSetTxn_ids());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTxn_ids()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.txn_ids, other.txn_ids);
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
    java.lang.StringBuilder sb = new java.lang.StringBuilder("OpenTxnsResponse(");
    boolean first = true;

    sb.append("txn_ids:");
    if (this.txn_ids == null) {
      sb.append("null");
    } else {
      sb.append(this.txn_ids);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    if (!isSetTxn_ids()) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'txn_ids' is unset! Struct:" + toString());
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
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class OpenTxnsResponseStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public OpenTxnsResponseStandardScheme getScheme() {
      return new OpenTxnsResponseStandardScheme();
    }
  }

  private static class OpenTxnsResponseStandardScheme extends org.apache.thrift.scheme.StandardScheme<OpenTxnsResponse> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, OpenTxnsResponse struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // TXN_IDS
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list508 = iprot.readListBegin();
                struct.txn_ids = new java.util.ArrayList<java.lang.Long>(_list508.size);
                long _elem509;
                for (int _i510 = 0; _i510 < _list508.size; ++_i510)
                {
                  _elem509 = iprot.readI64();
                  struct.txn_ids.add(_elem509);
                }
                iprot.readListEnd();
              }
              struct.setTxn_idsIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, OpenTxnsResponse struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.txn_ids != null) {
        oprot.writeFieldBegin(TXN_IDS_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.I64, struct.txn_ids.size()));
          for (long _iter511 : struct.txn_ids)
          {
            oprot.writeI64(_iter511);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class OpenTxnsResponseTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public OpenTxnsResponseTupleScheme getScheme() {
      return new OpenTxnsResponseTupleScheme();
    }
  }

  private static class OpenTxnsResponseTupleScheme extends org.apache.thrift.scheme.TupleScheme<OpenTxnsResponse> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, OpenTxnsResponse struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      {
        oprot.writeI32(struct.txn_ids.size());
        for (long _iter512 : struct.txn_ids)
        {
          oprot.writeI64(_iter512);
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, OpenTxnsResponse struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      {
        org.apache.thrift.protocol.TList _list513 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.I64, iprot.readI32());
        struct.txn_ids = new java.util.ArrayList<java.lang.Long>(_list513.size);
        long _elem514;
        for (int _i515 = 0; _i515 < _list513.size; ++_i515)
        {
          _elem514 = iprot.readI64();
          struct.txn_ids.add(_elem514);
        }
      }
      struct.setTxn_idsIsSet(true);
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

