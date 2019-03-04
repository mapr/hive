/**
 * Autogenerated by Thrift Compiler (0.12.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package org.apache.hadoop.hive.ql.plan.api;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.12.0)")
public class Operator implements org.apache.thrift.TBase<Operator, Operator._Fields>, java.io.Serializable, Cloneable, Comparable<Operator> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("Operator");

  private static final org.apache.thrift.protocol.TField OPERATOR_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("operatorId", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField OPERATOR_TYPE_FIELD_DESC = new org.apache.thrift.protocol.TField("operatorType", org.apache.thrift.protocol.TType.I32, (short)2);
  private static final org.apache.thrift.protocol.TField OPERATOR_ATTRIBUTES_FIELD_DESC = new org.apache.thrift.protocol.TField("operatorAttributes", org.apache.thrift.protocol.TType.MAP, (short)3);
  private static final org.apache.thrift.protocol.TField OPERATOR_COUNTERS_FIELD_DESC = new org.apache.thrift.protocol.TField("operatorCounters", org.apache.thrift.protocol.TType.MAP, (short)4);
  private static final org.apache.thrift.protocol.TField DONE_FIELD_DESC = new org.apache.thrift.protocol.TField("done", org.apache.thrift.protocol.TType.BOOL, (short)5);
  private static final org.apache.thrift.protocol.TField STARTED_FIELD_DESC = new org.apache.thrift.protocol.TField("started", org.apache.thrift.protocol.TType.BOOL, (short)6);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new OperatorStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new OperatorTupleSchemeFactory();

  private @org.apache.thrift.annotation.Nullable java.lang.String operatorId; // required
  private @org.apache.thrift.annotation.Nullable OperatorType operatorType; // required
  private @org.apache.thrift.annotation.Nullable java.util.Map<java.lang.String,java.lang.String> operatorAttributes; // required
  private @org.apache.thrift.annotation.Nullable java.util.Map<java.lang.String,java.lang.Long> operatorCounters; // required
  private boolean done; // required
  private boolean started; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    OPERATOR_ID((short)1, "operatorId"),
    /**
     * 
     * @see OperatorType
     */
    OPERATOR_TYPE((short)2, "operatorType"),
    OPERATOR_ATTRIBUTES((short)3, "operatorAttributes"),
    OPERATOR_COUNTERS((short)4, "operatorCounters"),
    DONE((short)5, "done"),
    STARTED((short)6, "started");

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
        case 1: // OPERATOR_ID
          return OPERATOR_ID;
        case 2: // OPERATOR_TYPE
          return OPERATOR_TYPE;
        case 3: // OPERATOR_ATTRIBUTES
          return OPERATOR_ATTRIBUTES;
        case 4: // OPERATOR_COUNTERS
          return OPERATOR_COUNTERS;
        case 5: // DONE
          return DONE;
        case 6: // STARTED
          return STARTED;
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
  private static final int __DONE_ISSET_ID = 0;
  private static final int __STARTED_ISSET_ID = 1;
  private byte __isset_bitfield = 0;
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.OPERATOR_ID, new org.apache.thrift.meta_data.FieldMetaData("operatorId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.OPERATOR_TYPE, new org.apache.thrift.meta_data.FieldMetaData("operatorType", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.EnumMetaData(org.apache.thrift.protocol.TType.ENUM, OperatorType.class)));
    tmpMap.put(_Fields.OPERATOR_ATTRIBUTES, new org.apache.thrift.meta_data.FieldMetaData("operatorAttributes", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.MapMetaData(org.apache.thrift.protocol.TType.MAP, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING), 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING))));
    tmpMap.put(_Fields.OPERATOR_COUNTERS, new org.apache.thrift.meta_data.FieldMetaData("operatorCounters", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.MapMetaData(org.apache.thrift.protocol.TType.MAP, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING), 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64))));
    tmpMap.put(_Fields.DONE, new org.apache.thrift.meta_data.FieldMetaData("done", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.BOOL)));
    tmpMap.put(_Fields.STARTED, new org.apache.thrift.meta_data.FieldMetaData("started", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.BOOL)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(Operator.class, metaDataMap);
  }

  public Operator() {
  }

  public Operator(
    java.lang.String operatorId,
    OperatorType operatorType,
    java.util.Map<java.lang.String,java.lang.String> operatorAttributes,
    java.util.Map<java.lang.String,java.lang.Long> operatorCounters,
    boolean done,
    boolean started)
  {
    this();
    this.operatorId = operatorId;
    this.operatorType = operatorType;
    this.operatorAttributes = operatorAttributes;
    this.operatorCounters = operatorCounters;
    this.done = done;
    setDoneIsSet(true);
    this.started = started;
    setStartedIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public Operator(Operator other) {
    __isset_bitfield = other.__isset_bitfield;
    if (other.isSetOperatorId()) {
      this.operatorId = other.operatorId;
    }
    if (other.isSetOperatorType()) {
      this.operatorType = other.operatorType;
    }
    if (other.isSetOperatorAttributes()) {
      java.util.Map<java.lang.String,java.lang.String> __this__operatorAttributes = new java.util.HashMap<java.lang.String,java.lang.String>(other.operatorAttributes);
      this.operatorAttributes = __this__operatorAttributes;
    }
    if (other.isSetOperatorCounters()) {
      java.util.Map<java.lang.String,java.lang.Long> __this__operatorCounters = new java.util.HashMap<java.lang.String,java.lang.Long>(other.operatorCounters);
      this.operatorCounters = __this__operatorCounters;
    }
    this.done = other.done;
    this.started = other.started;
  }

  public Operator deepCopy() {
    return new Operator(this);
  }

  @Override
  public void clear() {
    this.operatorId = null;
    this.operatorType = null;
    this.operatorAttributes = null;
    this.operatorCounters = null;
    setDoneIsSet(false);
    this.done = false;
    setStartedIsSet(false);
    this.started = false;
  }

  @org.apache.thrift.annotation.Nullable
  public java.lang.String getOperatorId() {
    return this.operatorId;
  }

  public void setOperatorId(@org.apache.thrift.annotation.Nullable java.lang.String operatorId) {
    this.operatorId = operatorId;
  }

  public void unsetOperatorId() {
    this.operatorId = null;
  }

  /** Returns true if field operatorId is set (has been assigned a value) and false otherwise */
  public boolean isSetOperatorId() {
    return this.operatorId != null;
  }

  public void setOperatorIdIsSet(boolean value) {
    if (!value) {
      this.operatorId = null;
    }
  }

  /**
   * 
   * @see OperatorType
   */
  @org.apache.thrift.annotation.Nullable
  public OperatorType getOperatorType() {
    return this.operatorType;
  }

  /**
   * 
   * @see OperatorType
   */
  public void setOperatorType(@org.apache.thrift.annotation.Nullable OperatorType operatorType) {
    this.operatorType = operatorType;
  }

  public void unsetOperatorType() {
    this.operatorType = null;
  }

  /** Returns true if field operatorType is set (has been assigned a value) and false otherwise */
  public boolean isSetOperatorType() {
    return this.operatorType != null;
  }

  public void setOperatorTypeIsSet(boolean value) {
    if (!value) {
      this.operatorType = null;
    }
  }

  public int getOperatorAttributesSize() {
    return (this.operatorAttributes == null) ? 0 : this.operatorAttributes.size();
  }

  public void putToOperatorAttributes(java.lang.String key, java.lang.String val) {
    if (this.operatorAttributes == null) {
      this.operatorAttributes = new java.util.HashMap<java.lang.String,java.lang.String>();
    }
    this.operatorAttributes.put(key, val);
  }

  @org.apache.thrift.annotation.Nullable
  public java.util.Map<java.lang.String,java.lang.String> getOperatorAttributes() {
    return this.operatorAttributes;
  }

  public void setOperatorAttributes(@org.apache.thrift.annotation.Nullable java.util.Map<java.lang.String,java.lang.String> operatorAttributes) {
    this.operatorAttributes = operatorAttributes;
  }

  public void unsetOperatorAttributes() {
    this.operatorAttributes = null;
  }

  /** Returns true if field operatorAttributes is set (has been assigned a value) and false otherwise */
  public boolean isSetOperatorAttributes() {
    return this.operatorAttributes != null;
  }

  public void setOperatorAttributesIsSet(boolean value) {
    if (!value) {
      this.operatorAttributes = null;
    }
  }

  public int getOperatorCountersSize() {
    return (this.operatorCounters == null) ? 0 : this.operatorCounters.size();
  }

  public void putToOperatorCounters(java.lang.String key, long val) {
    if (this.operatorCounters == null) {
      this.operatorCounters = new java.util.HashMap<java.lang.String,java.lang.Long>();
    }
    this.operatorCounters.put(key, val);
  }

  @org.apache.thrift.annotation.Nullable
  public java.util.Map<java.lang.String,java.lang.Long> getOperatorCounters() {
    return this.operatorCounters;
  }

  public void setOperatorCounters(@org.apache.thrift.annotation.Nullable java.util.Map<java.lang.String,java.lang.Long> operatorCounters) {
    this.operatorCounters = operatorCounters;
  }

  public void unsetOperatorCounters() {
    this.operatorCounters = null;
  }

  /** Returns true if field operatorCounters is set (has been assigned a value) and false otherwise */
  public boolean isSetOperatorCounters() {
    return this.operatorCounters != null;
  }

  public void setOperatorCountersIsSet(boolean value) {
    if (!value) {
      this.operatorCounters = null;
    }
  }

  public boolean isDone() {
    return this.done;
  }

  public void setDone(boolean done) {
    this.done = done;
    setDoneIsSet(true);
  }

  public void unsetDone() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __DONE_ISSET_ID);
  }

  /** Returns true if field done is set (has been assigned a value) and false otherwise */
  public boolean isSetDone() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __DONE_ISSET_ID);
  }

  public void setDoneIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __DONE_ISSET_ID, value);
  }

  public boolean isStarted() {
    return this.started;
  }

  public void setStarted(boolean started) {
    this.started = started;
    setStartedIsSet(true);
  }

  public void unsetStarted() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __STARTED_ISSET_ID);
  }

  /** Returns true if field started is set (has been assigned a value) and false otherwise */
  public boolean isSetStarted() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __STARTED_ISSET_ID);
  }

  public void setStartedIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __STARTED_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, @org.apache.thrift.annotation.Nullable java.lang.Object value) {
    switch (field) {
    case OPERATOR_ID:
      if (value == null) {
        unsetOperatorId();
      } else {
        setOperatorId((java.lang.String)value);
      }
      break;

    case OPERATOR_TYPE:
      if (value == null) {
        unsetOperatorType();
      } else {
        setOperatorType((OperatorType)value);
      }
      break;

    case OPERATOR_ATTRIBUTES:
      if (value == null) {
        unsetOperatorAttributes();
      } else {
        setOperatorAttributes((java.util.Map<java.lang.String,java.lang.String>)value);
      }
      break;

    case OPERATOR_COUNTERS:
      if (value == null) {
        unsetOperatorCounters();
      } else {
        setOperatorCounters((java.util.Map<java.lang.String,java.lang.Long>)value);
      }
      break;

    case DONE:
      if (value == null) {
        unsetDone();
      } else {
        setDone((java.lang.Boolean)value);
      }
      break;

    case STARTED:
      if (value == null) {
        unsetStarted();
      } else {
        setStarted((java.lang.Boolean)value);
      }
      break;

    }
  }

  @org.apache.thrift.annotation.Nullable
  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case OPERATOR_ID:
      return getOperatorId();

    case OPERATOR_TYPE:
      return getOperatorType();

    case OPERATOR_ATTRIBUTES:
      return getOperatorAttributes();

    case OPERATOR_COUNTERS:
      return getOperatorCounters();

    case DONE:
      return isDone();

    case STARTED:
      return isStarted();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case OPERATOR_ID:
      return isSetOperatorId();
    case OPERATOR_TYPE:
      return isSetOperatorType();
    case OPERATOR_ATTRIBUTES:
      return isSetOperatorAttributes();
    case OPERATOR_COUNTERS:
      return isSetOperatorCounters();
    case DONE:
      return isSetDone();
    case STARTED:
      return isSetStarted();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof Operator)
      return this.equals((Operator)that);
    return false;
  }

  public boolean equals(Operator that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_operatorId = true && this.isSetOperatorId();
    boolean that_present_operatorId = true && that.isSetOperatorId();
    if (this_present_operatorId || that_present_operatorId) {
      if (!(this_present_operatorId && that_present_operatorId))
        return false;
      if (!this.operatorId.equals(that.operatorId))
        return false;
    }

    boolean this_present_operatorType = true && this.isSetOperatorType();
    boolean that_present_operatorType = true && that.isSetOperatorType();
    if (this_present_operatorType || that_present_operatorType) {
      if (!(this_present_operatorType && that_present_operatorType))
        return false;
      if (!this.operatorType.equals(that.operatorType))
        return false;
    }

    boolean this_present_operatorAttributes = true && this.isSetOperatorAttributes();
    boolean that_present_operatorAttributes = true && that.isSetOperatorAttributes();
    if (this_present_operatorAttributes || that_present_operatorAttributes) {
      if (!(this_present_operatorAttributes && that_present_operatorAttributes))
        return false;
      if (!this.operatorAttributes.equals(that.operatorAttributes))
        return false;
    }

    boolean this_present_operatorCounters = true && this.isSetOperatorCounters();
    boolean that_present_operatorCounters = true && that.isSetOperatorCounters();
    if (this_present_operatorCounters || that_present_operatorCounters) {
      if (!(this_present_operatorCounters && that_present_operatorCounters))
        return false;
      if (!this.operatorCounters.equals(that.operatorCounters))
        return false;
    }

    boolean this_present_done = true;
    boolean that_present_done = true;
    if (this_present_done || that_present_done) {
      if (!(this_present_done && that_present_done))
        return false;
      if (this.done != that.done)
        return false;
    }

    boolean this_present_started = true;
    boolean that_present_started = true;
    if (this_present_started || that_present_started) {
      if (!(this_present_started && that_present_started))
        return false;
      if (this.started != that.started)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((isSetOperatorId()) ? 131071 : 524287);
    if (isSetOperatorId())
      hashCode = hashCode * 8191 + operatorId.hashCode();

    hashCode = hashCode * 8191 + ((isSetOperatorType()) ? 131071 : 524287);
    if (isSetOperatorType())
      hashCode = hashCode * 8191 + operatorType.getValue();

    hashCode = hashCode * 8191 + ((isSetOperatorAttributes()) ? 131071 : 524287);
    if (isSetOperatorAttributes())
      hashCode = hashCode * 8191 + operatorAttributes.hashCode();

    hashCode = hashCode * 8191 + ((isSetOperatorCounters()) ? 131071 : 524287);
    if (isSetOperatorCounters())
      hashCode = hashCode * 8191 + operatorCounters.hashCode();

    hashCode = hashCode * 8191 + ((done) ? 131071 : 524287);

    hashCode = hashCode * 8191 + ((started) ? 131071 : 524287);

    return hashCode;
  }

  @Override
  public int compareTo(Operator other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.valueOf(isSetOperatorId()).compareTo(other.isSetOperatorId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetOperatorId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.operatorId, other.operatorId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetOperatorType()).compareTo(other.isSetOperatorType());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetOperatorType()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.operatorType, other.operatorType);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetOperatorAttributes()).compareTo(other.isSetOperatorAttributes());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetOperatorAttributes()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.operatorAttributes, other.operatorAttributes);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetOperatorCounters()).compareTo(other.isSetOperatorCounters());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetOperatorCounters()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.operatorCounters, other.operatorCounters);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetDone()).compareTo(other.isSetDone());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetDone()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.done, other.done);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetStarted()).compareTo(other.isSetStarted());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetStarted()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.started, other.started);
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
    java.lang.StringBuilder sb = new java.lang.StringBuilder("Operator(");
    boolean first = true;

    sb.append("operatorId:");
    if (this.operatorId == null) {
      sb.append("null");
    } else {
      sb.append(this.operatorId);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("operatorType:");
    if (this.operatorType == null) {
      sb.append("null");
    } else {
      sb.append(this.operatorType);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("operatorAttributes:");
    if (this.operatorAttributes == null) {
      sb.append("null");
    } else {
      sb.append(this.operatorAttributes);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("operatorCounters:");
    if (this.operatorCounters == null) {
      sb.append("null");
    } else {
      sb.append(this.operatorCounters);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("done:");
    sb.append(this.done);
    first = false;
    if (!first) sb.append(", ");
    sb.append("started:");
    sb.append(this.started);
    first = false;
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
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class OperatorStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public OperatorStandardScheme getScheme() {
      return new OperatorStandardScheme();
    }
  }

  private static class OperatorStandardScheme extends org.apache.thrift.scheme.StandardScheme<Operator> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, Operator struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // OPERATOR_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.operatorId = iprot.readString();
              struct.setOperatorIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // OPERATOR_TYPE
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.operatorType = org.apache.hadoop.hive.ql.plan.api.OperatorType.findByValue(iprot.readI32());
              struct.setOperatorTypeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // OPERATOR_ATTRIBUTES
            if (schemeField.type == org.apache.thrift.protocol.TType.MAP) {
              {
                org.apache.thrift.protocol.TMap _map24 = iprot.readMapBegin();
                struct.operatorAttributes = new java.util.HashMap<java.lang.String,java.lang.String>(2*_map24.size);
                @org.apache.thrift.annotation.Nullable java.lang.String _key25;
                @org.apache.thrift.annotation.Nullable java.lang.String _val26;
                for (int _i27 = 0; _i27 < _map24.size; ++_i27)
                {
                  _key25 = iprot.readString();
                  _val26 = iprot.readString();
                  struct.operatorAttributes.put(_key25, _val26);
                }
                iprot.readMapEnd();
              }
              struct.setOperatorAttributesIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // OPERATOR_COUNTERS
            if (schemeField.type == org.apache.thrift.protocol.TType.MAP) {
              {
                org.apache.thrift.protocol.TMap _map28 = iprot.readMapBegin();
                struct.operatorCounters = new java.util.HashMap<java.lang.String,java.lang.Long>(2*_map28.size);
                @org.apache.thrift.annotation.Nullable java.lang.String _key29;
                long _val30;
                for (int _i31 = 0; _i31 < _map28.size; ++_i31)
                {
                  _key29 = iprot.readString();
                  _val30 = iprot.readI64();
                  struct.operatorCounters.put(_key29, _val30);
                }
                iprot.readMapEnd();
              }
              struct.setOperatorCountersIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // DONE
            if (schemeField.type == org.apache.thrift.protocol.TType.BOOL) {
              struct.done = iprot.readBool();
              struct.setDoneIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // STARTED
            if (schemeField.type == org.apache.thrift.protocol.TType.BOOL) {
              struct.started = iprot.readBool();
              struct.setStartedIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, Operator struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.operatorId != null) {
        oprot.writeFieldBegin(OPERATOR_ID_FIELD_DESC);
        oprot.writeString(struct.operatorId);
        oprot.writeFieldEnd();
      }
      if (struct.operatorType != null) {
        oprot.writeFieldBegin(OPERATOR_TYPE_FIELD_DESC);
        oprot.writeI32(struct.operatorType.getValue());
        oprot.writeFieldEnd();
      }
      if (struct.operatorAttributes != null) {
        oprot.writeFieldBegin(OPERATOR_ATTRIBUTES_FIELD_DESC);
        {
          oprot.writeMapBegin(new org.apache.thrift.protocol.TMap(org.apache.thrift.protocol.TType.STRING, org.apache.thrift.protocol.TType.STRING, struct.operatorAttributes.size()));
          for (java.util.Map.Entry<java.lang.String, java.lang.String> _iter32 : struct.operatorAttributes.entrySet())
          {
            oprot.writeString(_iter32.getKey());
            oprot.writeString(_iter32.getValue());
          }
          oprot.writeMapEnd();
        }
        oprot.writeFieldEnd();
      }
      if (struct.operatorCounters != null) {
        oprot.writeFieldBegin(OPERATOR_COUNTERS_FIELD_DESC);
        {
          oprot.writeMapBegin(new org.apache.thrift.protocol.TMap(org.apache.thrift.protocol.TType.STRING, org.apache.thrift.protocol.TType.I64, struct.operatorCounters.size()));
          for (java.util.Map.Entry<java.lang.String, java.lang.Long> _iter33 : struct.operatorCounters.entrySet())
          {
            oprot.writeString(_iter33.getKey());
            oprot.writeI64(_iter33.getValue());
          }
          oprot.writeMapEnd();
        }
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(DONE_FIELD_DESC);
      oprot.writeBool(struct.done);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(STARTED_FIELD_DESC);
      oprot.writeBool(struct.started);
      oprot.writeFieldEnd();
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class OperatorTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public OperatorTupleScheme getScheme() {
      return new OperatorTupleScheme();
    }
  }

  private static class OperatorTupleScheme extends org.apache.thrift.scheme.TupleScheme<Operator> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, Operator struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetOperatorId()) {
        optionals.set(0);
      }
      if (struct.isSetOperatorType()) {
        optionals.set(1);
      }
      if (struct.isSetOperatorAttributes()) {
        optionals.set(2);
      }
      if (struct.isSetOperatorCounters()) {
        optionals.set(3);
      }
      if (struct.isSetDone()) {
        optionals.set(4);
      }
      if (struct.isSetStarted()) {
        optionals.set(5);
      }
      oprot.writeBitSet(optionals, 6);
      if (struct.isSetOperatorId()) {
        oprot.writeString(struct.operatorId);
      }
      if (struct.isSetOperatorType()) {
        oprot.writeI32(struct.operatorType.getValue());
      }
      if (struct.isSetOperatorAttributes()) {
        {
          oprot.writeI32(struct.operatorAttributes.size());
          for (java.util.Map.Entry<java.lang.String, java.lang.String> _iter34 : struct.operatorAttributes.entrySet())
          {
            oprot.writeString(_iter34.getKey());
            oprot.writeString(_iter34.getValue());
          }
        }
      }
      if (struct.isSetOperatorCounters()) {
        {
          oprot.writeI32(struct.operatorCounters.size());
          for (java.util.Map.Entry<java.lang.String, java.lang.Long> _iter35 : struct.operatorCounters.entrySet())
          {
            oprot.writeString(_iter35.getKey());
            oprot.writeI64(_iter35.getValue());
          }
        }
      }
      if (struct.isSetDone()) {
        oprot.writeBool(struct.done);
      }
      if (struct.isSetStarted()) {
        oprot.writeBool(struct.started);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, Operator struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(6);
      if (incoming.get(0)) {
        struct.operatorId = iprot.readString();
        struct.setOperatorIdIsSet(true);
      }
      if (incoming.get(1)) {
        struct.operatorType = org.apache.hadoop.hive.ql.plan.api.OperatorType.findByValue(iprot.readI32());
        struct.setOperatorTypeIsSet(true);
      }
      if (incoming.get(2)) {
        {
          org.apache.thrift.protocol.TMap _map36 = new org.apache.thrift.protocol.TMap(org.apache.thrift.protocol.TType.STRING, org.apache.thrift.protocol.TType.STRING, iprot.readI32());
          struct.operatorAttributes = new java.util.HashMap<java.lang.String,java.lang.String>(2*_map36.size);
          @org.apache.thrift.annotation.Nullable java.lang.String _key37;
          @org.apache.thrift.annotation.Nullable java.lang.String _val38;
          for (int _i39 = 0; _i39 < _map36.size; ++_i39)
          {
            _key37 = iprot.readString();
            _val38 = iprot.readString();
            struct.operatorAttributes.put(_key37, _val38);
          }
        }
        struct.setOperatorAttributesIsSet(true);
      }
      if (incoming.get(3)) {
        {
          org.apache.thrift.protocol.TMap _map40 = new org.apache.thrift.protocol.TMap(org.apache.thrift.protocol.TType.STRING, org.apache.thrift.protocol.TType.I64, iprot.readI32());
          struct.operatorCounters = new java.util.HashMap<java.lang.String,java.lang.Long>(2*_map40.size);
          @org.apache.thrift.annotation.Nullable java.lang.String _key41;
          long _val42;
          for (int _i43 = 0; _i43 < _map40.size; ++_i43)
          {
            _key41 = iprot.readString();
            _val42 = iprot.readI64();
            struct.operatorCounters.put(_key41, _val42);
          }
        }
        struct.setOperatorCountersIsSet(true);
      }
      if (incoming.get(4)) {
        struct.done = iprot.readBool();
        struct.setDoneIsSet(true);
      }
      if (incoming.get(5)) {
        struct.started = iprot.readBool();
        struct.setStartedIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

