/**
 * Autogenerated by Thrift Compiler (0.12.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package org.apache.hadoop.hive.metastore.api;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.12.0)")
public class CheckConstraintsResponse implements org.apache.thrift.TBase<CheckConstraintsResponse, CheckConstraintsResponse._Fields>, java.io.Serializable, Cloneable, Comparable<CheckConstraintsResponse> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("CheckConstraintsResponse");

  private static final org.apache.thrift.protocol.TField CHECK_CONSTRAINTS_FIELD_DESC = new org.apache.thrift.protocol.TField("checkConstraints", org.apache.thrift.protocol.TType.LIST, (short)1);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new CheckConstraintsResponseStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new CheckConstraintsResponseTupleSchemeFactory();

  private @org.apache.thrift.annotation.Nullable java.util.List<SQLCheckConstraint> checkConstraints; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    CHECK_CONSTRAINTS((short)1, "checkConstraints");

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
        case 1: // CHECK_CONSTRAINTS
          return CHECK_CONSTRAINTS;
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
    tmpMap.put(_Fields.CHECK_CONSTRAINTS, new org.apache.thrift.meta_data.FieldMetaData("checkConstraints", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, SQLCheckConstraint.class))));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(CheckConstraintsResponse.class, metaDataMap);
  }

  public CheckConstraintsResponse() {
  }

  public CheckConstraintsResponse(
    java.util.List<SQLCheckConstraint> checkConstraints)
  {
    this();
    this.checkConstraints = checkConstraints;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public CheckConstraintsResponse(CheckConstraintsResponse other) {
    if (other.isSetCheckConstraints()) {
      java.util.List<SQLCheckConstraint> __this__checkConstraints = new java.util.ArrayList<SQLCheckConstraint>(other.checkConstraints.size());
      for (SQLCheckConstraint other_element : other.checkConstraints) {
        __this__checkConstraints.add(new SQLCheckConstraint(other_element));
      }
      this.checkConstraints = __this__checkConstraints;
    }
  }

  public CheckConstraintsResponse deepCopy() {
    return new CheckConstraintsResponse(this);
  }

  @Override
  public void clear() {
    this.checkConstraints = null;
  }

  public int getCheckConstraintsSize() {
    return (this.checkConstraints == null) ? 0 : this.checkConstraints.size();
  }

  @org.apache.thrift.annotation.Nullable
  public java.util.Iterator<SQLCheckConstraint> getCheckConstraintsIterator() {
    return (this.checkConstraints == null) ? null : this.checkConstraints.iterator();
  }

  public void addToCheckConstraints(SQLCheckConstraint elem) {
    if (this.checkConstraints == null) {
      this.checkConstraints = new java.util.ArrayList<SQLCheckConstraint>();
    }
    this.checkConstraints.add(elem);
  }

  @org.apache.thrift.annotation.Nullable
  public java.util.List<SQLCheckConstraint> getCheckConstraints() {
    return this.checkConstraints;
  }

  public void setCheckConstraints(@org.apache.thrift.annotation.Nullable java.util.List<SQLCheckConstraint> checkConstraints) {
    this.checkConstraints = checkConstraints;
  }

  public void unsetCheckConstraints() {
    this.checkConstraints = null;
  }

  /** Returns true if field checkConstraints is set (has been assigned a value) and false otherwise */
  public boolean isSetCheckConstraints() {
    return this.checkConstraints != null;
  }

  public void setCheckConstraintsIsSet(boolean value) {
    if (!value) {
      this.checkConstraints = null;
    }
  }

  public void setFieldValue(_Fields field, @org.apache.thrift.annotation.Nullable java.lang.Object value) {
    switch (field) {
    case CHECK_CONSTRAINTS:
      if (value == null) {
        unsetCheckConstraints();
      } else {
        setCheckConstraints((java.util.List<SQLCheckConstraint>)value);
      }
      break;

    }
  }

  @org.apache.thrift.annotation.Nullable
  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case CHECK_CONSTRAINTS:
      return getCheckConstraints();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case CHECK_CONSTRAINTS:
      return isSetCheckConstraints();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof CheckConstraintsResponse)
      return this.equals((CheckConstraintsResponse)that);
    return false;
  }

  public boolean equals(CheckConstraintsResponse that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_checkConstraints = true && this.isSetCheckConstraints();
    boolean that_present_checkConstraints = true && that.isSetCheckConstraints();
    if (this_present_checkConstraints || that_present_checkConstraints) {
      if (!(this_present_checkConstraints && that_present_checkConstraints))
        return false;
      if (!this.checkConstraints.equals(that.checkConstraints))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((isSetCheckConstraints()) ? 131071 : 524287);
    if (isSetCheckConstraints())
      hashCode = hashCode * 8191 + checkConstraints.hashCode();

    return hashCode;
  }

  @Override
  public int compareTo(CheckConstraintsResponse other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.valueOf(isSetCheckConstraints()).compareTo(other.isSetCheckConstraints());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCheckConstraints()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.checkConstraints, other.checkConstraints);
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
    java.lang.StringBuilder sb = new java.lang.StringBuilder("CheckConstraintsResponse(");
    boolean first = true;

    sb.append("checkConstraints:");
    if (this.checkConstraints == null) {
      sb.append("null");
    } else {
      sb.append(this.checkConstraints);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    if (!isSetCheckConstraints()) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'checkConstraints' is unset! Struct:" + toString());
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

  private static class CheckConstraintsResponseStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public CheckConstraintsResponseStandardScheme getScheme() {
      return new CheckConstraintsResponseStandardScheme();
    }
  }

  private static class CheckConstraintsResponseStandardScheme extends org.apache.thrift.scheme.StandardScheme<CheckConstraintsResponse> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, CheckConstraintsResponse struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // CHECK_CONSTRAINTS
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list360 = iprot.readListBegin();
                struct.checkConstraints = new java.util.ArrayList<SQLCheckConstraint>(_list360.size);
                @org.apache.thrift.annotation.Nullable SQLCheckConstraint _elem361;
                for (int _i362 = 0; _i362 < _list360.size; ++_i362)
                {
                  _elem361 = new SQLCheckConstraint();
                  _elem361.read(iprot);
                  struct.checkConstraints.add(_elem361);
                }
                iprot.readListEnd();
              }
              struct.setCheckConstraintsIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, CheckConstraintsResponse struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.checkConstraints != null) {
        oprot.writeFieldBegin(CHECK_CONSTRAINTS_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.checkConstraints.size()));
          for (SQLCheckConstraint _iter363 : struct.checkConstraints)
          {
            _iter363.write(oprot);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class CheckConstraintsResponseTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public CheckConstraintsResponseTupleScheme getScheme() {
      return new CheckConstraintsResponseTupleScheme();
    }
  }

  private static class CheckConstraintsResponseTupleScheme extends org.apache.thrift.scheme.TupleScheme<CheckConstraintsResponse> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, CheckConstraintsResponse struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      {
        oprot.writeI32(struct.checkConstraints.size());
        for (SQLCheckConstraint _iter364 : struct.checkConstraints)
        {
          _iter364.write(oprot);
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, CheckConstraintsResponse struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      {
        org.apache.thrift.protocol.TList _list365 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
        struct.checkConstraints = new java.util.ArrayList<SQLCheckConstraint>(_list365.size);
        @org.apache.thrift.annotation.Nullable SQLCheckConstraint _elem366;
        for (int _i367 = 0; _i367 < _list365.size; ++_i367)
        {
          _elem366 = new SQLCheckConstraint();
          _elem366.read(iprot);
          struct.checkConstraints.add(_elem366);
        }
      }
      struct.setCheckConstraintsIsSet(true);
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

