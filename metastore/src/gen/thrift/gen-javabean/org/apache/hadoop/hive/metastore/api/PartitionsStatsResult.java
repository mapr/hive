/**
 * Autogenerated by Thrift Compiler (0.13.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package org.apache.hadoop.hive.metastore.api;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.13.0)")
public class PartitionsStatsResult implements org.apache.thrift.TBase<PartitionsStatsResult, PartitionsStatsResult._Fields>, java.io.Serializable, Cloneable, Comparable<PartitionsStatsResult> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("PartitionsStatsResult");

  private static final org.apache.thrift.protocol.TField PART_STATS_FIELD_DESC = new org.apache.thrift.protocol.TField("partStats", org.apache.thrift.protocol.TType.MAP, (short)1);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new PartitionsStatsResultStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new PartitionsStatsResultTupleSchemeFactory();

  private @org.apache.thrift.annotation.Nullable java.util.Map<java.lang.String,java.util.List<ColumnStatisticsObj>> partStats; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    PART_STATS((short)1, "partStats");

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
        case 1: // PART_STATS
          return PART_STATS;
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
    tmpMap.put(_Fields.PART_STATS, new org.apache.thrift.meta_data.FieldMetaData("partStats", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.MapMetaData(org.apache.thrift.protocol.TType.MAP, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING), 
            new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
                new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, ColumnStatisticsObj.class)))));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(PartitionsStatsResult.class, metaDataMap);
  }

  public PartitionsStatsResult() {
  }

  public PartitionsStatsResult(
    java.util.Map<java.lang.String,java.util.List<ColumnStatisticsObj>> partStats)
  {
    this();
    this.partStats = partStats;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public PartitionsStatsResult(PartitionsStatsResult other) {
    if (other.isSetPartStats()) {
      java.util.Map<java.lang.String,java.util.List<ColumnStatisticsObj>> __this__partStats = new java.util.HashMap<java.lang.String,java.util.List<ColumnStatisticsObj>>(other.partStats.size());
      for (java.util.Map.Entry<java.lang.String, java.util.List<ColumnStatisticsObj>> other_element : other.partStats.entrySet()) {

        java.lang.String other_element_key = other_element.getKey();
        java.util.List<ColumnStatisticsObj> other_element_value = other_element.getValue();

        java.lang.String __this__partStats_copy_key = other_element_key;

        java.util.List<ColumnStatisticsObj> __this__partStats_copy_value = new java.util.ArrayList<ColumnStatisticsObj>(other_element_value.size());
        for (ColumnStatisticsObj other_element_value_element : other_element_value) {
          __this__partStats_copy_value.add(new ColumnStatisticsObj(other_element_value_element));
        }

        __this__partStats.put(__this__partStats_copy_key, __this__partStats_copy_value);
      }
      this.partStats = __this__partStats;
    }
  }

  public PartitionsStatsResult deepCopy() {
    return new PartitionsStatsResult(this);
  }

  @Override
  public void clear() {
    this.partStats = null;
  }

  public int getPartStatsSize() {
    return (this.partStats == null) ? 0 : this.partStats.size();
  }

  public void putToPartStats(java.lang.String key, java.util.List<ColumnStatisticsObj> val) {
    if (this.partStats == null) {
      this.partStats = new java.util.HashMap<java.lang.String,java.util.List<ColumnStatisticsObj>>();
    }
    this.partStats.put(key, val);
  }

  @org.apache.thrift.annotation.Nullable
  public java.util.Map<java.lang.String,java.util.List<ColumnStatisticsObj>> getPartStats() {
    return this.partStats;
  }

  public void setPartStats(@org.apache.thrift.annotation.Nullable java.util.Map<java.lang.String,java.util.List<ColumnStatisticsObj>> partStats) {
    this.partStats = partStats;
  }

  public void unsetPartStats() {
    this.partStats = null;
  }

  /** Returns true if field partStats is set (has been assigned a value) and false otherwise */
  public boolean isSetPartStats() {
    return this.partStats != null;
  }

  public void setPartStatsIsSet(boolean value) {
    if (!value) {
      this.partStats = null;
    }
  }

  public void setFieldValue(_Fields field, @org.apache.thrift.annotation.Nullable java.lang.Object value) {
    switch (field) {
    case PART_STATS:
      if (value == null) {
        unsetPartStats();
      } else {
        setPartStats((java.util.Map<java.lang.String,java.util.List<ColumnStatisticsObj>>)value);
      }
      break;

    }
  }

  @org.apache.thrift.annotation.Nullable
  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case PART_STATS:
      return getPartStats();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case PART_STATS:
      return isSetPartStats();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof PartitionsStatsResult)
      return this.equals((PartitionsStatsResult)that);
    return false;
  }

  public boolean equals(PartitionsStatsResult that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_partStats = true && this.isSetPartStats();
    boolean that_present_partStats = true && that.isSetPartStats();
    if (this_present_partStats || that_present_partStats) {
      if (!(this_present_partStats && that_present_partStats))
        return false;
      if (!this.partStats.equals(that.partStats))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((isSetPartStats()) ? 131071 : 524287);
    if (isSetPartStats())
      hashCode = hashCode * 8191 + partStats.hashCode();

    return hashCode;
  }

  @Override
  public int compareTo(PartitionsStatsResult other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.valueOf(isSetPartStats()).compareTo(other.isSetPartStats());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPartStats()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.partStats, other.partStats);
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
    java.lang.StringBuilder sb = new java.lang.StringBuilder("PartitionsStatsResult(");
    boolean first = true;

    sb.append("partStats:");
    if (this.partStats == null) {
      sb.append("null");
    } else {
      sb.append(this.partStats);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    if (!isSetPartStats()) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'partStats' is unset! Struct:" + toString());
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

  private static class PartitionsStatsResultStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public PartitionsStatsResultStandardScheme getScheme() {
      return new PartitionsStatsResultStandardScheme();
    }
  }

  private static class PartitionsStatsResultStandardScheme extends org.apache.thrift.scheme.StandardScheme<PartitionsStatsResult> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, PartitionsStatsResult struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // PART_STATS
            if (schemeField.type == org.apache.thrift.protocol.TType.MAP) {
              {
                org.apache.thrift.protocol.TMap _map370 = iprot.readMapBegin();
                struct.partStats = new java.util.HashMap<java.lang.String,java.util.List<ColumnStatisticsObj>>(2*_map370.size);
                @org.apache.thrift.annotation.Nullable java.lang.String _key371;
                @org.apache.thrift.annotation.Nullable java.util.List<ColumnStatisticsObj> _val372;
                for (int _i373 = 0; _i373 < _map370.size; ++_i373)
                {
                  _key371 = iprot.readString();
                  {
                    org.apache.thrift.protocol.TList _list374 = iprot.readListBegin();
                    _val372 = new java.util.ArrayList<ColumnStatisticsObj>(_list374.size);
                    @org.apache.thrift.annotation.Nullable ColumnStatisticsObj _elem375;
                    for (int _i376 = 0; _i376 < _list374.size; ++_i376)
                    {
                      _elem375 = new ColumnStatisticsObj();
                      _elem375.read(iprot);
                      _val372.add(_elem375);
                    }
                    iprot.readListEnd();
                  }
                  struct.partStats.put(_key371, _val372);
                }
                iprot.readMapEnd();
              }
              struct.setPartStatsIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, PartitionsStatsResult struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.partStats != null) {
        oprot.writeFieldBegin(PART_STATS_FIELD_DESC);
        {
          oprot.writeMapBegin(new org.apache.thrift.protocol.TMap(org.apache.thrift.protocol.TType.STRING, org.apache.thrift.protocol.TType.LIST, struct.partStats.size()));
          for (java.util.Map.Entry<java.lang.String, java.util.List<ColumnStatisticsObj>> _iter377 : struct.partStats.entrySet())
          {
            oprot.writeString(_iter377.getKey());
            {
              oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, _iter377.getValue().size()));
              for (ColumnStatisticsObj _iter378 : _iter377.getValue())
              {
                _iter378.write(oprot);
              }
              oprot.writeListEnd();
            }
          }
          oprot.writeMapEnd();
        }
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class PartitionsStatsResultTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public PartitionsStatsResultTupleScheme getScheme() {
      return new PartitionsStatsResultTupleScheme();
    }
  }

  private static class PartitionsStatsResultTupleScheme extends org.apache.thrift.scheme.TupleScheme<PartitionsStatsResult> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, PartitionsStatsResult struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      {
        oprot.writeI32(struct.partStats.size());
        for (java.util.Map.Entry<java.lang.String, java.util.List<ColumnStatisticsObj>> _iter379 : struct.partStats.entrySet())
        {
          oprot.writeString(_iter379.getKey());
          {
            oprot.writeI32(_iter379.getValue().size());
            for (ColumnStatisticsObj _iter380 : _iter379.getValue())
            {
              _iter380.write(oprot);
            }
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, PartitionsStatsResult struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      {
        org.apache.thrift.protocol.TMap _map381 = new org.apache.thrift.protocol.TMap(org.apache.thrift.protocol.TType.STRING, org.apache.thrift.protocol.TType.LIST, iprot.readI32());
        struct.partStats = new java.util.HashMap<java.lang.String,java.util.List<ColumnStatisticsObj>>(2*_map381.size);
        @org.apache.thrift.annotation.Nullable java.lang.String _key382;
        @org.apache.thrift.annotation.Nullable java.util.List<ColumnStatisticsObj> _val383;
        for (int _i384 = 0; _i384 < _map381.size; ++_i384)
        {
          _key382 = iprot.readString();
          {
            org.apache.thrift.protocol.TList _list385 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
            _val383 = new java.util.ArrayList<ColumnStatisticsObj>(_list385.size);
            @org.apache.thrift.annotation.Nullable ColumnStatisticsObj _elem386;
            for (int _i387 = 0; _i387 < _list385.size; ++_i387)
            {
              _elem386 = new ColumnStatisticsObj();
              _elem386.read(iprot);
              _val383.add(_elem386);
            }
          }
          struct.partStats.put(_key382, _val383);
        }
      }
      struct.setPartStatsIsSet(true);
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

