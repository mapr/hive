/**
 * Autogenerated by Thrift Compiler (0.13.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package org.apache.hadoop.hive.metastore.api;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.13.0)")
public class PartitionValuesResponse implements org.apache.thrift.TBase<PartitionValuesResponse, PartitionValuesResponse._Fields>, java.io.Serializable, Cloneable, Comparable<PartitionValuesResponse> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("PartitionValuesResponse");

  private static final org.apache.thrift.protocol.TField PARTITION_VALUES_FIELD_DESC = new org.apache.thrift.protocol.TField("partitionValues", org.apache.thrift.protocol.TType.LIST, (short)1);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new PartitionValuesResponseStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new PartitionValuesResponseTupleSchemeFactory();

  private @org.apache.thrift.annotation.Nullable java.util.List<PartitionValuesRow> partitionValues; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    PARTITION_VALUES((short)1, "partitionValues");

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
        case 1: // PARTITION_VALUES
          return PARTITION_VALUES;
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
    tmpMap.put(_Fields.PARTITION_VALUES, new org.apache.thrift.meta_data.FieldMetaData("partitionValues", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, PartitionValuesRow.class))));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(PartitionValuesResponse.class, metaDataMap);
  }

  public PartitionValuesResponse() {
  }

  public PartitionValuesResponse(
    java.util.List<PartitionValuesRow> partitionValues)
  {
    this();
    this.partitionValues = partitionValues;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public PartitionValuesResponse(PartitionValuesResponse other) {
    if (other.isSetPartitionValues()) {
      java.util.List<PartitionValuesRow> __this__partitionValues = new java.util.ArrayList<PartitionValuesRow>(other.partitionValues.size());
      for (PartitionValuesRow other_element : other.partitionValues) {
        __this__partitionValues.add(new PartitionValuesRow(other_element));
      }
      this.partitionValues = __this__partitionValues;
    }
  }

  public PartitionValuesResponse deepCopy() {
    return new PartitionValuesResponse(this);
  }

  @Override
  public void clear() {
    this.partitionValues = null;
  }

  public int getPartitionValuesSize() {
    return (this.partitionValues == null) ? 0 : this.partitionValues.size();
  }

  @org.apache.thrift.annotation.Nullable
  public java.util.Iterator<PartitionValuesRow> getPartitionValuesIterator() {
    return (this.partitionValues == null) ? null : this.partitionValues.iterator();
  }

  public void addToPartitionValues(PartitionValuesRow elem) {
    if (this.partitionValues == null) {
      this.partitionValues = new java.util.ArrayList<PartitionValuesRow>();
    }
    this.partitionValues.add(elem);
  }

  @org.apache.thrift.annotation.Nullable
  public java.util.List<PartitionValuesRow> getPartitionValues() {
    return this.partitionValues;
  }

  public void setPartitionValues(@org.apache.thrift.annotation.Nullable java.util.List<PartitionValuesRow> partitionValues) {
    this.partitionValues = partitionValues;
  }

  public void unsetPartitionValues() {
    this.partitionValues = null;
  }

  /** Returns true if field partitionValues is set (has been assigned a value) and false otherwise */
  public boolean isSetPartitionValues() {
    return this.partitionValues != null;
  }

  public void setPartitionValuesIsSet(boolean value) {
    if (!value) {
      this.partitionValues = null;
    }
  }

  public void setFieldValue(_Fields field, @org.apache.thrift.annotation.Nullable java.lang.Object value) {
    switch (field) {
    case PARTITION_VALUES:
      if (value == null) {
        unsetPartitionValues();
      } else {
        setPartitionValues((java.util.List<PartitionValuesRow>)value);
      }
      break;

    }
  }

  @org.apache.thrift.annotation.Nullable
  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case PARTITION_VALUES:
      return getPartitionValues();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case PARTITION_VALUES:
      return isSetPartitionValues();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof PartitionValuesResponse)
      return this.equals((PartitionValuesResponse)that);
    return false;
  }

  public boolean equals(PartitionValuesResponse that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_partitionValues = true && this.isSetPartitionValues();
    boolean that_present_partitionValues = true && that.isSetPartitionValues();
    if (this_present_partitionValues || that_present_partitionValues) {
      if (!(this_present_partitionValues && that_present_partitionValues))
        return false;
      if (!this.partitionValues.equals(that.partitionValues))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((isSetPartitionValues()) ? 131071 : 524287);
    if (isSetPartitionValues())
      hashCode = hashCode * 8191 + partitionValues.hashCode();

    return hashCode;
  }

  @Override
  public int compareTo(PartitionValuesResponse other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.valueOf(isSetPartitionValues()).compareTo(other.isSetPartitionValues());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPartitionValues()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.partitionValues, other.partitionValues);
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
    java.lang.StringBuilder sb = new java.lang.StringBuilder("PartitionValuesResponse(");
    boolean first = true;

    sb.append("partitionValues:");
    if (this.partitionValues == null) {
      sb.append("null");
    } else {
      sb.append(this.partitionValues);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    if (!isSetPartitionValues()) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'partitionValues' is unset! Struct:" + toString());
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

  private static class PartitionValuesResponseStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public PartitionValuesResponseStandardScheme getScheme() {
      return new PartitionValuesResponseStandardScheme();
    }
  }

  private static class PartitionValuesResponseStandardScheme extends org.apache.thrift.scheme.StandardScheme<PartitionValuesResponse> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, PartitionValuesResponse struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // PARTITION_VALUES
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list538 = iprot.readListBegin();
                struct.partitionValues = new java.util.ArrayList<PartitionValuesRow>(_list538.size);
                @org.apache.thrift.annotation.Nullable PartitionValuesRow _elem539;
                for (int _i540 = 0; _i540 < _list538.size; ++_i540)
                {
                  _elem539 = new PartitionValuesRow();
                  _elem539.read(iprot);
                  struct.partitionValues.add(_elem539);
                }
                iprot.readListEnd();
              }
              struct.setPartitionValuesIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, PartitionValuesResponse struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.partitionValues != null) {
        oprot.writeFieldBegin(PARTITION_VALUES_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.partitionValues.size()));
          for (PartitionValuesRow _iter541 : struct.partitionValues)
          {
            _iter541.write(oprot);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class PartitionValuesResponseTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public PartitionValuesResponseTupleScheme getScheme() {
      return new PartitionValuesResponseTupleScheme();
    }
  }

  private static class PartitionValuesResponseTupleScheme extends org.apache.thrift.scheme.TupleScheme<PartitionValuesResponse> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, PartitionValuesResponse struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      {
        oprot.writeI32(struct.partitionValues.size());
        for (PartitionValuesRow _iter542 : struct.partitionValues)
        {
          _iter542.write(oprot);
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, PartitionValuesResponse struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      {
        org.apache.thrift.protocol.TList _list543 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
        struct.partitionValues = new java.util.ArrayList<PartitionValuesRow>(_list543.size);
        @org.apache.thrift.annotation.Nullable PartitionValuesRow _elem544;
        for (int _i545 = 0; _i545 < _list543.size; ++_i545)
        {
          _elem544 = new PartitionValuesRow();
          _elem544.read(iprot);
          struct.partitionValues.add(_elem544);
        }
      }
      struct.setPartitionValuesIsSet(true);
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

