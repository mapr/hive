/**
 * Autogenerated by Thrift Compiler (0.12.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package org.apache.hadoop.hive.metastore.api;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.12.0)")
public class WMGetResourcePlanResponse implements org.apache.thrift.TBase<WMGetResourcePlanResponse, WMGetResourcePlanResponse._Fields>, java.io.Serializable, Cloneable, Comparable<WMGetResourcePlanResponse> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("WMGetResourcePlanResponse");

  private static final org.apache.thrift.protocol.TField RESOURCE_PLAN_FIELD_DESC = new org.apache.thrift.protocol.TField("resourcePlan", org.apache.thrift.protocol.TType.STRUCT, (short)1);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new WMGetResourcePlanResponseStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new WMGetResourcePlanResponseTupleSchemeFactory();

  private @org.apache.thrift.annotation.Nullable WMFullResourcePlan resourcePlan; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    RESOURCE_PLAN((short)1, "resourcePlan");

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
        case 1: // RESOURCE_PLAN
          return RESOURCE_PLAN;
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
  private static final _Fields optionals[] = {_Fields.RESOURCE_PLAN};
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.RESOURCE_PLAN, new org.apache.thrift.meta_data.FieldMetaData("resourcePlan", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, WMFullResourcePlan.class)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(WMGetResourcePlanResponse.class, metaDataMap);
  }

  public WMGetResourcePlanResponse() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public WMGetResourcePlanResponse(WMGetResourcePlanResponse other) {
    if (other.isSetResourcePlan()) {
      this.resourcePlan = new WMFullResourcePlan(other.resourcePlan);
    }
  }

  public WMGetResourcePlanResponse deepCopy() {
    return new WMGetResourcePlanResponse(this);
  }

  @Override
  public void clear() {
    this.resourcePlan = null;
  }

  @org.apache.thrift.annotation.Nullable
  public WMFullResourcePlan getResourcePlan() {
    return this.resourcePlan;
  }

  public void setResourcePlan(@org.apache.thrift.annotation.Nullable WMFullResourcePlan resourcePlan) {
    this.resourcePlan = resourcePlan;
  }

  public void unsetResourcePlan() {
    this.resourcePlan = null;
  }

  /** Returns true if field resourcePlan is set (has been assigned a value) and false otherwise */
  public boolean isSetResourcePlan() {
    return this.resourcePlan != null;
  }

  public void setResourcePlanIsSet(boolean value) {
    if (!value) {
      this.resourcePlan = null;
    }
  }

  public void setFieldValue(_Fields field, @org.apache.thrift.annotation.Nullable java.lang.Object value) {
    switch (field) {
    case RESOURCE_PLAN:
      if (value == null) {
        unsetResourcePlan();
      } else {
        setResourcePlan((WMFullResourcePlan)value);
      }
      break;

    }
  }

  @org.apache.thrift.annotation.Nullable
  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case RESOURCE_PLAN:
      return getResourcePlan();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case RESOURCE_PLAN:
      return isSetResourcePlan();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof WMGetResourcePlanResponse)
      return this.equals((WMGetResourcePlanResponse)that);
    return false;
  }

  public boolean equals(WMGetResourcePlanResponse that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_resourcePlan = true && this.isSetResourcePlan();
    boolean that_present_resourcePlan = true && that.isSetResourcePlan();
    if (this_present_resourcePlan || that_present_resourcePlan) {
      if (!(this_present_resourcePlan && that_present_resourcePlan))
        return false;
      if (!this.resourcePlan.equals(that.resourcePlan))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((isSetResourcePlan()) ? 131071 : 524287);
    if (isSetResourcePlan())
      hashCode = hashCode * 8191 + resourcePlan.hashCode();

    return hashCode;
  }

  @Override
  public int compareTo(WMGetResourcePlanResponse other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.valueOf(isSetResourcePlan()).compareTo(other.isSetResourcePlan());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetResourcePlan()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.resourcePlan, other.resourcePlan);
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
    java.lang.StringBuilder sb = new java.lang.StringBuilder("WMGetResourcePlanResponse(");
    boolean first = true;

    if (isSetResourcePlan()) {
      sb.append("resourcePlan:");
      if (this.resourcePlan == null) {
        sb.append("null");
      } else {
        sb.append(this.resourcePlan);
      }
      first = false;
    }
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
    if (resourcePlan != null) {
      resourcePlan.validate();
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

  private static class WMGetResourcePlanResponseStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public WMGetResourcePlanResponseStandardScheme getScheme() {
      return new WMGetResourcePlanResponseStandardScheme();
    }
  }

  private static class WMGetResourcePlanResponseStandardScheme extends org.apache.thrift.scheme.StandardScheme<WMGetResourcePlanResponse> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, WMGetResourcePlanResponse struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // RESOURCE_PLAN
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.resourcePlan = new WMFullResourcePlan();
              struct.resourcePlan.read(iprot);
              struct.setResourcePlanIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, WMGetResourcePlanResponse struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.resourcePlan != null) {
        if (struct.isSetResourcePlan()) {
          oprot.writeFieldBegin(RESOURCE_PLAN_FIELD_DESC);
          struct.resourcePlan.write(oprot);
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class WMGetResourcePlanResponseTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public WMGetResourcePlanResponseTupleScheme getScheme() {
      return new WMGetResourcePlanResponseTupleScheme();
    }
  }

  private static class WMGetResourcePlanResponseTupleScheme extends org.apache.thrift.scheme.TupleScheme<WMGetResourcePlanResponse> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, WMGetResourcePlanResponse struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetResourcePlan()) {
        optionals.set(0);
      }
      oprot.writeBitSet(optionals, 1);
      if (struct.isSetResourcePlan()) {
        struct.resourcePlan.write(oprot);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, WMGetResourcePlanResponse struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(1);
      if (incoming.get(0)) {
        struct.resourcePlan = new WMFullResourcePlan();
        struct.resourcePlan.read(iprot);
        struct.setResourcePlanIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

