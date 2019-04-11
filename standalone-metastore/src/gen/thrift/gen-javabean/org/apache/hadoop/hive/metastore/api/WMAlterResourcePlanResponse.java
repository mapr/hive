/**
 * Autogenerated by Thrift Compiler (0.12.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package org.apache.hadoop.hive.metastore.api;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.12.0)")
public class WMAlterResourcePlanResponse implements org.apache.thrift.TBase<WMAlterResourcePlanResponse, WMAlterResourcePlanResponse._Fields>, java.io.Serializable, Cloneable, Comparable<WMAlterResourcePlanResponse> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("WMAlterResourcePlanResponse");

  private static final org.apache.thrift.protocol.TField FULL_RESOURCE_PLAN_FIELD_DESC = new org.apache.thrift.protocol.TField("fullResourcePlan", org.apache.thrift.protocol.TType.STRUCT, (short)1);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new WMAlterResourcePlanResponseStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new WMAlterResourcePlanResponseTupleSchemeFactory();

  private @org.apache.thrift.annotation.Nullable WMFullResourcePlan fullResourcePlan; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    FULL_RESOURCE_PLAN((short)1, "fullResourcePlan");

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
        case 1: // FULL_RESOURCE_PLAN
          return FULL_RESOURCE_PLAN;
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
  private static final _Fields optionals[] = {_Fields.FULL_RESOURCE_PLAN};
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.FULL_RESOURCE_PLAN, new org.apache.thrift.meta_data.FieldMetaData("fullResourcePlan", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, WMFullResourcePlan.class)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(WMAlterResourcePlanResponse.class, metaDataMap);
  }

  public WMAlterResourcePlanResponse() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public WMAlterResourcePlanResponse(WMAlterResourcePlanResponse other) {
    if (other.isSetFullResourcePlan()) {
      this.fullResourcePlan = new WMFullResourcePlan(other.fullResourcePlan);
    }
  }

  public WMAlterResourcePlanResponse deepCopy() {
    return new WMAlterResourcePlanResponse(this);
  }

  @Override
  public void clear() {
    this.fullResourcePlan = null;
  }

  @org.apache.thrift.annotation.Nullable
  public WMFullResourcePlan getFullResourcePlan() {
    return this.fullResourcePlan;
  }

  public void setFullResourcePlan(@org.apache.thrift.annotation.Nullable WMFullResourcePlan fullResourcePlan) {
    this.fullResourcePlan = fullResourcePlan;
  }

  public void unsetFullResourcePlan() {
    this.fullResourcePlan = null;
  }

  /** Returns true if field fullResourcePlan is set (has been assigned a value) and false otherwise */
  public boolean isSetFullResourcePlan() {
    return this.fullResourcePlan != null;
  }

  public void setFullResourcePlanIsSet(boolean value) {
    if (!value) {
      this.fullResourcePlan = null;
    }
  }

  public void setFieldValue(_Fields field, @org.apache.thrift.annotation.Nullable java.lang.Object value) {
    switch (field) {
    case FULL_RESOURCE_PLAN:
      if (value == null) {
        unsetFullResourcePlan();
      } else {
        setFullResourcePlan((WMFullResourcePlan)value);
      }
      break;

    }
  }

  @org.apache.thrift.annotation.Nullable
  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case FULL_RESOURCE_PLAN:
      return getFullResourcePlan();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case FULL_RESOURCE_PLAN:
      return isSetFullResourcePlan();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof WMAlterResourcePlanResponse)
      return this.equals((WMAlterResourcePlanResponse)that);
    return false;
  }

  public boolean equals(WMAlterResourcePlanResponse that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_fullResourcePlan = true && this.isSetFullResourcePlan();
    boolean that_present_fullResourcePlan = true && that.isSetFullResourcePlan();
    if (this_present_fullResourcePlan || that_present_fullResourcePlan) {
      if (!(this_present_fullResourcePlan && that_present_fullResourcePlan))
        return false;
      if (!this.fullResourcePlan.equals(that.fullResourcePlan))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((isSetFullResourcePlan()) ? 131071 : 524287);
    if (isSetFullResourcePlan())
      hashCode = hashCode * 8191 + fullResourcePlan.hashCode();

    return hashCode;
  }

  @Override
  public int compareTo(WMAlterResourcePlanResponse other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.valueOf(isSetFullResourcePlan()).compareTo(other.isSetFullResourcePlan());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetFullResourcePlan()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.fullResourcePlan, other.fullResourcePlan);
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
    java.lang.StringBuilder sb = new java.lang.StringBuilder("WMAlterResourcePlanResponse(");
    boolean first = true;

    if (isSetFullResourcePlan()) {
      sb.append("fullResourcePlan:");
      if (this.fullResourcePlan == null) {
        sb.append("null");
      } else {
        sb.append(this.fullResourcePlan);
      }
      first = false;
    }
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
    if (fullResourcePlan != null) {
      fullResourcePlan.validate();
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
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class WMAlterResourcePlanResponseStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public WMAlterResourcePlanResponseStandardScheme getScheme() {
      return new WMAlterResourcePlanResponseStandardScheme();
    }
  }

  private static class WMAlterResourcePlanResponseStandardScheme extends org.apache.thrift.scheme.StandardScheme<WMAlterResourcePlanResponse> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, WMAlterResourcePlanResponse struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // FULL_RESOURCE_PLAN
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.fullResourcePlan = new WMFullResourcePlan();
              struct.fullResourcePlan.read(iprot);
              struct.setFullResourcePlanIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, WMAlterResourcePlanResponse struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.fullResourcePlan != null) {
        if (struct.isSetFullResourcePlan()) {
          oprot.writeFieldBegin(FULL_RESOURCE_PLAN_FIELD_DESC);
          struct.fullResourcePlan.write(oprot);
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class WMAlterResourcePlanResponseTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public WMAlterResourcePlanResponseTupleScheme getScheme() {
      return new WMAlterResourcePlanResponseTupleScheme();
    }
  }

  private static class WMAlterResourcePlanResponseTupleScheme extends org.apache.thrift.scheme.TupleScheme<WMAlterResourcePlanResponse> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, WMAlterResourcePlanResponse struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetFullResourcePlan()) {
        optionals.set(0);
      }
      oprot.writeBitSet(optionals, 1);
      if (struct.isSetFullResourcePlan()) {
        struct.fullResourcePlan.write(oprot);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, WMAlterResourcePlanResponse struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(1);
      if (incoming.get(0)) {
        struct.fullResourcePlan = new WMFullResourcePlan();
        struct.fullResourcePlan.read(iprot);
        struct.setFullResourcePlanIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

