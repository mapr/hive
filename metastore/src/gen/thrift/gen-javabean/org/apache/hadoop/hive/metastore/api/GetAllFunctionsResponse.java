/**
 * Autogenerated by Thrift Compiler (0.12.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package org.apache.hadoop.hive.metastore.api;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.12.0)")
public class GetAllFunctionsResponse implements org.apache.thrift.TBase<GetAllFunctionsResponse, GetAllFunctionsResponse._Fields>, java.io.Serializable, Cloneable, Comparable<GetAllFunctionsResponse> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("GetAllFunctionsResponse");

  private static final org.apache.thrift.protocol.TField FUNCTIONS_FIELD_DESC = new org.apache.thrift.protocol.TField("functions", org.apache.thrift.protocol.TType.LIST, (short)1);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new GetAllFunctionsResponseStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new GetAllFunctionsResponseTupleSchemeFactory();

  private @org.apache.thrift.annotation.Nullable java.util.List<Function> functions; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    FUNCTIONS((short)1, "functions");

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
        case 1: // FUNCTIONS
          return FUNCTIONS;
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
  private static final _Fields optionals[] = {_Fields.FUNCTIONS};
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.FUNCTIONS, new org.apache.thrift.meta_data.FieldMetaData("functions", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, Function.class))));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(GetAllFunctionsResponse.class, metaDataMap);
  }

  public GetAllFunctionsResponse() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public GetAllFunctionsResponse(GetAllFunctionsResponse other) {
    if (other.isSetFunctions()) {
      java.util.List<Function> __this__functions = new java.util.ArrayList<Function>(other.functions.size());
      for (Function other_element : other.functions) {
        __this__functions.add(new Function(other_element));
      }
      this.functions = __this__functions;
    }
  }

  public GetAllFunctionsResponse deepCopy() {
    return new GetAllFunctionsResponse(this);
  }

  @Override
  public void clear() {
    this.functions = null;
  }

  public int getFunctionsSize() {
    return (this.functions == null) ? 0 : this.functions.size();
  }

  @org.apache.thrift.annotation.Nullable
  public java.util.Iterator<Function> getFunctionsIterator() {
    return (this.functions == null) ? null : this.functions.iterator();
  }

  public void addToFunctions(Function elem) {
    if (this.functions == null) {
      this.functions = new java.util.ArrayList<Function>();
    }
    this.functions.add(elem);
  }

  @org.apache.thrift.annotation.Nullable
  public java.util.List<Function> getFunctions() {
    return this.functions;
  }

  public void setFunctions(@org.apache.thrift.annotation.Nullable java.util.List<Function> functions) {
    this.functions = functions;
  }

  public void unsetFunctions() {
    this.functions = null;
  }

  /** Returns true if field functions is set (has been assigned a value) and false otherwise */
  public boolean isSetFunctions() {
    return this.functions != null;
  }

  public void setFunctionsIsSet(boolean value) {
    if (!value) {
      this.functions = null;
    }
  }

  public void setFieldValue(_Fields field, @org.apache.thrift.annotation.Nullable java.lang.Object value) {
    switch (field) {
    case FUNCTIONS:
      if (value == null) {
        unsetFunctions();
      } else {
        setFunctions((java.util.List<Function>)value);
      }
      break;

    }
  }

  @org.apache.thrift.annotation.Nullable
  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case FUNCTIONS:
      return getFunctions();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case FUNCTIONS:
      return isSetFunctions();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof GetAllFunctionsResponse)
      return this.equals((GetAllFunctionsResponse)that);
    return false;
  }

  public boolean equals(GetAllFunctionsResponse that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_functions = true && this.isSetFunctions();
    boolean that_present_functions = true && that.isSetFunctions();
    if (this_present_functions || that_present_functions) {
      if (!(this_present_functions && that_present_functions))
        return false;
      if (!this.functions.equals(that.functions))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((isSetFunctions()) ? 131071 : 524287);
    if (isSetFunctions())
      hashCode = hashCode * 8191 + functions.hashCode();

    return hashCode;
  }

  @Override
  public int compareTo(GetAllFunctionsResponse other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.valueOf(isSetFunctions()).compareTo(other.isSetFunctions());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetFunctions()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.functions, other.functions);
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
    java.lang.StringBuilder sb = new java.lang.StringBuilder("GetAllFunctionsResponse(");
    boolean first = true;

    if (isSetFunctions()) {
      sb.append("functions:");
      if (this.functions == null) {
        sb.append("null");
      } else {
        sb.append(this.functions);
      }
      first = false;
    }
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
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class GetAllFunctionsResponseStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public GetAllFunctionsResponseStandardScheme getScheme() {
      return new GetAllFunctionsResponseStandardScheme();
    }
  }

  private static class GetAllFunctionsResponseStandardScheme extends org.apache.thrift.scheme.StandardScheme<GetAllFunctionsResponse> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, GetAllFunctionsResponse struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // FUNCTIONS
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list634 = iprot.readListBegin();
                struct.functions = new java.util.ArrayList<Function>(_list634.size);
                @org.apache.thrift.annotation.Nullable Function _elem635;
                for (int _i636 = 0; _i636 < _list634.size; ++_i636)
                {
                  _elem635 = new Function();
                  _elem635.read(iprot);
                  struct.functions.add(_elem635);
                }
                iprot.readListEnd();
              }
              struct.setFunctionsIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, GetAllFunctionsResponse struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.functions != null) {
        if (struct.isSetFunctions()) {
          oprot.writeFieldBegin(FUNCTIONS_FIELD_DESC);
          {
            oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.functions.size()));
            for (Function _iter637 : struct.functions)
            {
              _iter637.write(oprot);
            }
            oprot.writeListEnd();
          }
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class GetAllFunctionsResponseTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public GetAllFunctionsResponseTupleScheme getScheme() {
      return new GetAllFunctionsResponseTupleScheme();
    }
  }

  private static class GetAllFunctionsResponseTupleScheme extends org.apache.thrift.scheme.TupleScheme<GetAllFunctionsResponse> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, GetAllFunctionsResponse struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetFunctions()) {
        optionals.set(0);
      }
      oprot.writeBitSet(optionals, 1);
      if (struct.isSetFunctions()) {
        {
          oprot.writeI32(struct.functions.size());
          for (Function _iter638 : struct.functions)
          {
            _iter638.write(oprot);
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, GetAllFunctionsResponse struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(1);
      if (incoming.get(0)) {
        {
          org.apache.thrift.protocol.TList _list639 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
          struct.functions = new java.util.ArrayList<Function>(_list639.size);
          @org.apache.thrift.annotation.Nullable Function _elem640;
          for (int _i641 = 0; _i641 < _list639.size; ++_i641)
          {
            _elem640 = new Function();
            _elem640.read(iprot);
            struct.functions.add(_elem640);
          }
        }
        struct.setFunctionsIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

