/**
 * Autogenerated by Thrift Compiler (0.12.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package org.apache.hadoop.hive.metastore.api;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.12.0)")
public class PrimaryKeysResponse implements org.apache.thrift.TBase<PrimaryKeysResponse, PrimaryKeysResponse._Fields>, java.io.Serializable, Cloneable, Comparable<PrimaryKeysResponse> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("PrimaryKeysResponse");

  private static final org.apache.thrift.protocol.TField PRIMARY_KEYS_FIELD_DESC = new org.apache.thrift.protocol.TField("primaryKeys", org.apache.thrift.protocol.TType.LIST, (short)1);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new PrimaryKeysResponseStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new PrimaryKeysResponseTupleSchemeFactory();

  private @org.apache.thrift.annotation.Nullable java.util.List<SQLPrimaryKey> primaryKeys; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    PRIMARY_KEYS((short)1, "primaryKeys");

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
        case 1: // PRIMARY_KEYS
          return PRIMARY_KEYS;
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
    tmpMap.put(_Fields.PRIMARY_KEYS, new org.apache.thrift.meta_data.FieldMetaData("primaryKeys", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, SQLPrimaryKey.class))));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(PrimaryKeysResponse.class, metaDataMap);
  }

  public PrimaryKeysResponse() {
  }

  public PrimaryKeysResponse(
    java.util.List<SQLPrimaryKey> primaryKeys)
  {
    this();
    this.primaryKeys = primaryKeys;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public PrimaryKeysResponse(PrimaryKeysResponse other) {
    if (other.isSetPrimaryKeys()) {
      java.util.List<SQLPrimaryKey> __this__primaryKeys = new java.util.ArrayList<SQLPrimaryKey>(other.primaryKeys.size());
      for (SQLPrimaryKey other_element : other.primaryKeys) {
        __this__primaryKeys.add(new SQLPrimaryKey(other_element));
      }
      this.primaryKeys = __this__primaryKeys;
    }
  }

  public PrimaryKeysResponse deepCopy() {
    return new PrimaryKeysResponse(this);
  }

  @Override
  public void clear() {
    this.primaryKeys = null;
  }

  public int getPrimaryKeysSize() {
    return (this.primaryKeys == null) ? 0 : this.primaryKeys.size();
  }

  @org.apache.thrift.annotation.Nullable
  public java.util.Iterator<SQLPrimaryKey> getPrimaryKeysIterator() {
    return (this.primaryKeys == null) ? null : this.primaryKeys.iterator();
  }

  public void addToPrimaryKeys(SQLPrimaryKey elem) {
    if (this.primaryKeys == null) {
      this.primaryKeys = new java.util.ArrayList<SQLPrimaryKey>();
    }
    this.primaryKeys.add(elem);
  }

  @org.apache.thrift.annotation.Nullable
  public java.util.List<SQLPrimaryKey> getPrimaryKeys() {
    return this.primaryKeys;
  }

  public void setPrimaryKeys(@org.apache.thrift.annotation.Nullable java.util.List<SQLPrimaryKey> primaryKeys) {
    this.primaryKeys = primaryKeys;
  }

  public void unsetPrimaryKeys() {
    this.primaryKeys = null;
  }

  /** Returns true if field primaryKeys is set (has been assigned a value) and false otherwise */
  public boolean isSetPrimaryKeys() {
    return this.primaryKeys != null;
  }

  public void setPrimaryKeysIsSet(boolean value) {
    if (!value) {
      this.primaryKeys = null;
    }
  }

  public void setFieldValue(_Fields field, @org.apache.thrift.annotation.Nullable java.lang.Object value) {
    switch (field) {
    case PRIMARY_KEYS:
      if (value == null) {
        unsetPrimaryKeys();
      } else {
        setPrimaryKeys((java.util.List<SQLPrimaryKey>)value);
      }
      break;

    }
  }

  @org.apache.thrift.annotation.Nullable
  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case PRIMARY_KEYS:
      return getPrimaryKeys();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case PRIMARY_KEYS:
      return isSetPrimaryKeys();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof PrimaryKeysResponse)
      return this.equals((PrimaryKeysResponse)that);
    return false;
  }

  public boolean equals(PrimaryKeysResponse that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_primaryKeys = true && this.isSetPrimaryKeys();
    boolean that_present_primaryKeys = true && that.isSetPrimaryKeys();
    if (this_present_primaryKeys || that_present_primaryKeys) {
      if (!(this_present_primaryKeys && that_present_primaryKeys))
        return false;
      if (!this.primaryKeys.equals(that.primaryKeys))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((isSetPrimaryKeys()) ? 131071 : 524287);
    if (isSetPrimaryKeys())
      hashCode = hashCode * 8191 + primaryKeys.hashCode();

    return hashCode;
  }

  @Override
  public int compareTo(PrimaryKeysResponse other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.valueOf(isSetPrimaryKeys()).compareTo(other.isSetPrimaryKeys());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPrimaryKeys()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.primaryKeys, other.primaryKeys);
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
    java.lang.StringBuilder sb = new java.lang.StringBuilder("PrimaryKeysResponse(");
    boolean first = true;

    sb.append("primaryKeys:");
    if (this.primaryKeys == null) {
      sb.append("null");
    } else {
      sb.append(this.primaryKeys);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    if (!isSetPrimaryKeys()) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'primaryKeys' is unset! Struct:" + toString());
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

  private static class PrimaryKeysResponseStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public PrimaryKeysResponseStandardScheme getScheme() {
      return new PrimaryKeysResponseStandardScheme();
    }
  }

  private static class PrimaryKeysResponseStandardScheme extends org.apache.thrift.scheme.StandardScheme<PrimaryKeysResponse> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, PrimaryKeysResponse struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // PRIMARY_KEYS
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list322 = iprot.readListBegin();
                struct.primaryKeys = new java.util.ArrayList<SQLPrimaryKey>(_list322.size);
                @org.apache.thrift.annotation.Nullable SQLPrimaryKey _elem323;
                for (int _i324 = 0; _i324 < _list322.size; ++_i324)
                {
                  _elem323 = new SQLPrimaryKey();
                  _elem323.read(iprot);
                  struct.primaryKeys.add(_elem323);
                }
                iprot.readListEnd();
              }
              struct.setPrimaryKeysIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, PrimaryKeysResponse struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.primaryKeys != null) {
        oprot.writeFieldBegin(PRIMARY_KEYS_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.primaryKeys.size()));
          for (SQLPrimaryKey _iter325 : struct.primaryKeys)
          {
            _iter325.write(oprot);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class PrimaryKeysResponseTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public PrimaryKeysResponseTupleScheme getScheme() {
      return new PrimaryKeysResponseTupleScheme();
    }
  }

  private static class PrimaryKeysResponseTupleScheme extends org.apache.thrift.scheme.TupleScheme<PrimaryKeysResponse> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, PrimaryKeysResponse struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      {
        oprot.writeI32(struct.primaryKeys.size());
        for (SQLPrimaryKey _iter326 : struct.primaryKeys)
        {
          _iter326.write(oprot);
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, PrimaryKeysResponse struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      {
        org.apache.thrift.protocol.TList _list327 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
        struct.primaryKeys = new java.util.ArrayList<SQLPrimaryKey>(_list327.size);
        @org.apache.thrift.annotation.Nullable SQLPrimaryKey _elem328;
        for (int _i329 = 0; _i329 < _list327.size; ++_i329)
        {
          _elem328 = new SQLPrimaryKey();
          _elem328.read(iprot);
          struct.primaryKeys.add(_elem328);
        }
      }
      struct.setPrimaryKeysIsSet(true);
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}
