/**
 * Autogenerated by Thrift Compiler (0.13.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package org.apache.hadoop.hive.metastore.api;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.13.0)")
public class DateColumnStatsData implements org.apache.thrift.TBase<DateColumnStatsData, DateColumnStatsData._Fields>, java.io.Serializable, Cloneable, Comparable<DateColumnStatsData> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("DateColumnStatsData");

  private static final org.apache.thrift.protocol.TField LOW_VALUE_FIELD_DESC = new org.apache.thrift.protocol.TField("lowValue", org.apache.thrift.protocol.TType.STRUCT, (short)1);
  private static final org.apache.thrift.protocol.TField HIGH_VALUE_FIELD_DESC = new org.apache.thrift.protocol.TField("highValue", org.apache.thrift.protocol.TType.STRUCT, (short)2);
  private static final org.apache.thrift.protocol.TField NUM_NULLS_FIELD_DESC = new org.apache.thrift.protocol.TField("numNulls", org.apache.thrift.protocol.TType.I64, (short)3);
  private static final org.apache.thrift.protocol.TField NUM_DVS_FIELD_DESC = new org.apache.thrift.protocol.TField("numDVs", org.apache.thrift.protocol.TType.I64, (short)4);
  private static final org.apache.thrift.protocol.TField BIT_VECTORS_FIELD_DESC = new org.apache.thrift.protocol.TField("bitVectors", org.apache.thrift.protocol.TType.STRING, (short)5);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new DateColumnStatsDataStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new DateColumnStatsDataTupleSchemeFactory();

  private @org.apache.thrift.annotation.Nullable Date lowValue; // optional
  private @org.apache.thrift.annotation.Nullable Date highValue; // optional
  private long numNulls; // required
  private long numDVs; // required
  private @org.apache.thrift.annotation.Nullable java.lang.String bitVectors; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    LOW_VALUE((short)1, "lowValue"),
    HIGH_VALUE((short)2, "highValue"),
    NUM_NULLS((short)3, "numNulls"),
    NUM_DVS((short)4, "numDVs"),
    BIT_VECTORS((short)5, "bitVectors");

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
        case 1: // LOW_VALUE
          return LOW_VALUE;
        case 2: // HIGH_VALUE
          return HIGH_VALUE;
        case 3: // NUM_NULLS
          return NUM_NULLS;
        case 4: // NUM_DVS
          return NUM_DVS;
        case 5: // BIT_VECTORS
          return BIT_VECTORS;
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
  private static final int __NUMNULLS_ISSET_ID = 0;
  private static final int __NUMDVS_ISSET_ID = 1;
  private byte __isset_bitfield = 0;
  private static final _Fields optionals[] = {_Fields.LOW_VALUE,_Fields.HIGH_VALUE,_Fields.BIT_VECTORS};
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.LOW_VALUE, new org.apache.thrift.meta_data.FieldMetaData("lowValue", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, Date.class)));
    tmpMap.put(_Fields.HIGH_VALUE, new org.apache.thrift.meta_data.FieldMetaData("highValue", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, Date.class)));
    tmpMap.put(_Fields.NUM_NULLS, new org.apache.thrift.meta_data.FieldMetaData("numNulls", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.NUM_DVS, new org.apache.thrift.meta_data.FieldMetaData("numDVs", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.BIT_VECTORS, new org.apache.thrift.meta_data.FieldMetaData("bitVectors", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(DateColumnStatsData.class, metaDataMap);
  }

  public DateColumnStatsData() {
  }

  public DateColumnStatsData(
    long numNulls,
    long numDVs)
  {
    this();
    this.numNulls = numNulls;
    setNumNullsIsSet(true);
    this.numDVs = numDVs;
    setNumDVsIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public DateColumnStatsData(DateColumnStatsData other) {
    __isset_bitfield = other.__isset_bitfield;
    if (other.isSetLowValue()) {
      this.lowValue = new Date(other.lowValue);
    }
    if (other.isSetHighValue()) {
      this.highValue = new Date(other.highValue);
    }
    this.numNulls = other.numNulls;
    this.numDVs = other.numDVs;
    if (other.isSetBitVectors()) {
      this.bitVectors = other.bitVectors;
    }
  }

  public DateColumnStatsData deepCopy() {
    return new DateColumnStatsData(this);
  }

  @Override
  public void clear() {
    this.lowValue = null;
    this.highValue = null;
    setNumNullsIsSet(false);
    this.numNulls = 0;
    setNumDVsIsSet(false);
    this.numDVs = 0;
    this.bitVectors = null;
  }

  @org.apache.thrift.annotation.Nullable
  public Date getLowValue() {
    return this.lowValue;
  }

  public void setLowValue(@org.apache.thrift.annotation.Nullable Date lowValue) {
    this.lowValue = lowValue;
  }

  public void unsetLowValue() {
    this.lowValue = null;
  }

  /** Returns true if field lowValue is set (has been assigned a value) and false otherwise */
  public boolean isSetLowValue() {
    return this.lowValue != null;
  }

  public void setLowValueIsSet(boolean value) {
    if (!value) {
      this.lowValue = null;
    }
  }

  @org.apache.thrift.annotation.Nullable
  public Date getHighValue() {
    return this.highValue;
  }

  public void setHighValue(@org.apache.thrift.annotation.Nullable Date highValue) {
    this.highValue = highValue;
  }

  public void unsetHighValue() {
    this.highValue = null;
  }

  /** Returns true if field highValue is set (has been assigned a value) and false otherwise */
  public boolean isSetHighValue() {
    return this.highValue != null;
  }

  public void setHighValueIsSet(boolean value) {
    if (!value) {
      this.highValue = null;
    }
  }

  public long getNumNulls() {
    return this.numNulls;
  }

  public void setNumNulls(long numNulls) {
    this.numNulls = numNulls;
    setNumNullsIsSet(true);
  }

  public void unsetNumNulls() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __NUMNULLS_ISSET_ID);
  }

  /** Returns true if field numNulls is set (has been assigned a value) and false otherwise */
  public boolean isSetNumNulls() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __NUMNULLS_ISSET_ID);
  }

  public void setNumNullsIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __NUMNULLS_ISSET_ID, value);
  }

  public long getNumDVs() {
    return this.numDVs;
  }

  public void setNumDVs(long numDVs) {
    this.numDVs = numDVs;
    setNumDVsIsSet(true);
  }

  public void unsetNumDVs() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __NUMDVS_ISSET_ID);
  }

  /** Returns true if field numDVs is set (has been assigned a value) and false otherwise */
  public boolean isSetNumDVs() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __NUMDVS_ISSET_ID);
  }

  public void setNumDVsIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __NUMDVS_ISSET_ID, value);
  }

  @org.apache.thrift.annotation.Nullable
  public java.lang.String getBitVectors() {
    return this.bitVectors;
  }

  public void setBitVectors(@org.apache.thrift.annotation.Nullable java.lang.String bitVectors) {
    this.bitVectors = bitVectors;
  }

  public void unsetBitVectors() {
    this.bitVectors = null;
  }

  /** Returns true if field bitVectors is set (has been assigned a value) and false otherwise */
  public boolean isSetBitVectors() {
    return this.bitVectors != null;
  }

  public void setBitVectorsIsSet(boolean value) {
    if (!value) {
      this.bitVectors = null;
    }
  }

  public void setFieldValue(_Fields field, @org.apache.thrift.annotation.Nullable java.lang.Object value) {
    switch (field) {
    case LOW_VALUE:
      if (value == null) {
        unsetLowValue();
      } else {
        setLowValue((Date)value);
      }
      break;

    case HIGH_VALUE:
      if (value == null) {
        unsetHighValue();
      } else {
        setHighValue((Date)value);
      }
      break;

    case NUM_NULLS:
      if (value == null) {
        unsetNumNulls();
      } else {
        setNumNulls((java.lang.Long)value);
      }
      break;

    case NUM_DVS:
      if (value == null) {
        unsetNumDVs();
      } else {
        setNumDVs((java.lang.Long)value);
      }
      break;

    case BIT_VECTORS:
      if (value == null) {
        unsetBitVectors();
      } else {
        setBitVectors((java.lang.String)value);
      }
      break;

    }
  }

  @org.apache.thrift.annotation.Nullable
  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case LOW_VALUE:
      return getLowValue();

    case HIGH_VALUE:
      return getHighValue();

    case NUM_NULLS:
      return getNumNulls();

    case NUM_DVS:
      return getNumDVs();

    case BIT_VECTORS:
      return getBitVectors();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case LOW_VALUE:
      return isSetLowValue();
    case HIGH_VALUE:
      return isSetHighValue();
    case NUM_NULLS:
      return isSetNumNulls();
    case NUM_DVS:
      return isSetNumDVs();
    case BIT_VECTORS:
      return isSetBitVectors();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof DateColumnStatsData)
      return this.equals((DateColumnStatsData)that);
    return false;
  }

  public boolean equals(DateColumnStatsData that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_lowValue = true && this.isSetLowValue();
    boolean that_present_lowValue = true && that.isSetLowValue();
    if (this_present_lowValue || that_present_lowValue) {
      if (!(this_present_lowValue && that_present_lowValue))
        return false;
      if (!this.lowValue.equals(that.lowValue))
        return false;
    }

    boolean this_present_highValue = true && this.isSetHighValue();
    boolean that_present_highValue = true && that.isSetHighValue();
    if (this_present_highValue || that_present_highValue) {
      if (!(this_present_highValue && that_present_highValue))
        return false;
      if (!this.highValue.equals(that.highValue))
        return false;
    }

    boolean this_present_numNulls = true;
    boolean that_present_numNulls = true;
    if (this_present_numNulls || that_present_numNulls) {
      if (!(this_present_numNulls && that_present_numNulls))
        return false;
      if (this.numNulls != that.numNulls)
        return false;
    }

    boolean this_present_numDVs = true;
    boolean that_present_numDVs = true;
    if (this_present_numDVs || that_present_numDVs) {
      if (!(this_present_numDVs && that_present_numDVs))
        return false;
      if (this.numDVs != that.numDVs)
        return false;
    }

    boolean this_present_bitVectors = true && this.isSetBitVectors();
    boolean that_present_bitVectors = true && that.isSetBitVectors();
    if (this_present_bitVectors || that_present_bitVectors) {
      if (!(this_present_bitVectors && that_present_bitVectors))
        return false;
      if (!this.bitVectors.equals(that.bitVectors))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((isSetLowValue()) ? 131071 : 524287);
    if (isSetLowValue())
      hashCode = hashCode * 8191 + lowValue.hashCode();

    hashCode = hashCode * 8191 + ((isSetHighValue()) ? 131071 : 524287);
    if (isSetHighValue())
      hashCode = hashCode * 8191 + highValue.hashCode();

    hashCode = hashCode * 8191 + org.apache.thrift.TBaseHelper.hashCode(numNulls);

    hashCode = hashCode * 8191 + org.apache.thrift.TBaseHelper.hashCode(numDVs);

    hashCode = hashCode * 8191 + ((isSetBitVectors()) ? 131071 : 524287);
    if (isSetBitVectors())
      hashCode = hashCode * 8191 + bitVectors.hashCode();

    return hashCode;
  }

  @Override
  public int compareTo(DateColumnStatsData other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.valueOf(isSetLowValue()).compareTo(other.isSetLowValue());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetLowValue()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.lowValue, other.lowValue);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetHighValue()).compareTo(other.isSetHighValue());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetHighValue()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.highValue, other.highValue);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetNumNulls()).compareTo(other.isSetNumNulls());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetNumNulls()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.numNulls, other.numNulls);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetNumDVs()).compareTo(other.isSetNumDVs());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetNumDVs()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.numDVs, other.numDVs);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetBitVectors()).compareTo(other.isSetBitVectors());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetBitVectors()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.bitVectors, other.bitVectors);
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
    java.lang.StringBuilder sb = new java.lang.StringBuilder("DateColumnStatsData(");
    boolean first = true;

    if (isSetLowValue()) {
      sb.append("lowValue:");
      if (this.lowValue == null) {
        sb.append("null");
      } else {
        sb.append(this.lowValue);
      }
      first = false;
    }
    if (isSetHighValue()) {
      if (!first) sb.append(", ");
      sb.append("highValue:");
      if (this.highValue == null) {
        sb.append("null");
      } else {
        sb.append(this.highValue);
      }
      first = false;
    }
    if (!first) sb.append(", ");
    sb.append("numNulls:");
    sb.append(this.numNulls);
    first = false;
    if (!first) sb.append(", ");
    sb.append("numDVs:");
    sb.append(this.numDVs);
    first = false;
    if (isSetBitVectors()) {
      if (!first) sb.append(", ");
      sb.append("bitVectors:");
      if (this.bitVectors == null) {
        sb.append("null");
      } else {
        sb.append(this.bitVectors);
      }
      first = false;
    }
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    if (!isSetNumNulls()) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'numNulls' is unset! Struct:" + toString());
    }

    if (!isSetNumDVs()) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'numDVs' is unset! Struct:" + toString());
    }

    // check for sub-struct validity
    if (lowValue != null) {
      lowValue.validate();
    }
    if (highValue != null) {
      highValue.validate();
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

  private static class DateColumnStatsDataStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public DateColumnStatsDataStandardScheme getScheme() {
      return new DateColumnStatsDataStandardScheme();
    }
  }

  private static class DateColumnStatsDataStandardScheme extends org.apache.thrift.scheme.StandardScheme<DateColumnStatsData> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, DateColumnStatsData struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // LOW_VALUE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.lowValue = new Date();
              struct.lowValue.read(iprot);
              struct.setLowValueIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // HIGH_VALUE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.highValue = new Date();
              struct.highValue.read(iprot);
              struct.setHighValueIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // NUM_NULLS
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.numNulls = iprot.readI64();
              struct.setNumNullsIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // NUM_DVS
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.numDVs = iprot.readI64();
              struct.setNumDVsIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // BIT_VECTORS
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.bitVectors = iprot.readString();
              struct.setBitVectorsIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, DateColumnStatsData struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.lowValue != null) {
        if (struct.isSetLowValue()) {
          oprot.writeFieldBegin(LOW_VALUE_FIELD_DESC);
          struct.lowValue.write(oprot);
          oprot.writeFieldEnd();
        }
      }
      if (struct.highValue != null) {
        if (struct.isSetHighValue()) {
          oprot.writeFieldBegin(HIGH_VALUE_FIELD_DESC);
          struct.highValue.write(oprot);
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldBegin(NUM_NULLS_FIELD_DESC);
      oprot.writeI64(struct.numNulls);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(NUM_DVS_FIELD_DESC);
      oprot.writeI64(struct.numDVs);
      oprot.writeFieldEnd();
      if (struct.bitVectors != null) {
        if (struct.isSetBitVectors()) {
          oprot.writeFieldBegin(BIT_VECTORS_FIELD_DESC);
          oprot.writeString(struct.bitVectors);
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class DateColumnStatsDataTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public DateColumnStatsDataTupleScheme getScheme() {
      return new DateColumnStatsDataTupleScheme();
    }
  }

  private static class DateColumnStatsDataTupleScheme extends org.apache.thrift.scheme.TupleScheme<DateColumnStatsData> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, DateColumnStatsData struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      oprot.writeI64(struct.numNulls);
      oprot.writeI64(struct.numDVs);
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetLowValue()) {
        optionals.set(0);
      }
      if (struct.isSetHighValue()) {
        optionals.set(1);
      }
      if (struct.isSetBitVectors()) {
        optionals.set(2);
      }
      oprot.writeBitSet(optionals, 3);
      if (struct.isSetLowValue()) {
        struct.lowValue.write(oprot);
      }
      if (struct.isSetHighValue()) {
        struct.highValue.write(oprot);
      }
      if (struct.isSetBitVectors()) {
        oprot.writeString(struct.bitVectors);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, DateColumnStatsData struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      struct.numNulls = iprot.readI64();
      struct.setNumNullsIsSet(true);
      struct.numDVs = iprot.readI64();
      struct.setNumDVsIsSet(true);
      java.util.BitSet incoming = iprot.readBitSet(3);
      if (incoming.get(0)) {
        struct.lowValue = new Date();
        struct.lowValue.read(iprot);
        struct.setLowValueIsSet(true);
      }
      if (incoming.get(1)) {
        struct.highValue = new Date();
        struct.highValue.read(iprot);
        struct.setHighValueIsSet(true);
      }
      if (incoming.get(2)) {
        struct.bitVectors = iprot.readString();
        struct.setBitVectorsIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

