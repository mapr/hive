/**
 * Autogenerated by Thrift Compiler (0.16.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package org.apache.hadoop.hive.metastore.api;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.16.0)")
@org.apache.hadoop.classification.InterfaceAudience.Public @org.apache.hadoop.classification.InterfaceStability.Stable public class WMGetResourcePlanRequest implements org.apache.thrift.TBase<WMGetResourcePlanRequest, WMGetResourcePlanRequest._Fields>, java.io.Serializable, Cloneable, Comparable<WMGetResourcePlanRequest> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("WMGetResourcePlanRequest");

  private static final org.apache.thrift.protocol.TField RESOURCE_PLAN_NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("resourcePlanName", org.apache.thrift.protocol.TType.STRING, (short)1);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new WMGetResourcePlanRequestStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new WMGetResourcePlanRequestTupleSchemeFactory();

  private @org.apache.thrift.annotation.Nullable java.lang.String resourcePlanName; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    RESOURCE_PLAN_NAME((short)1, "resourcePlanName");

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
        case 1: // RESOURCE_PLAN_NAME
          return RESOURCE_PLAN_NAME;
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
  private static final _Fields optionals[] = {_Fields.RESOURCE_PLAN_NAME};
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.RESOURCE_PLAN_NAME, new org.apache.thrift.meta_data.FieldMetaData("resourcePlanName", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(WMGetResourcePlanRequest.class, metaDataMap);
  }

  public WMGetResourcePlanRequest() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public WMGetResourcePlanRequest(WMGetResourcePlanRequest other) {
    if (other.isSetResourcePlanName()) {
      this.resourcePlanName = other.resourcePlanName;
    }
  }

  public WMGetResourcePlanRequest deepCopy() {
    return new WMGetResourcePlanRequest(this);
  }

  @Override
  public void clear() {
    this.resourcePlanName = null;
  }

  @org.apache.thrift.annotation.Nullable
  public java.lang.String getResourcePlanName() {
    return this.resourcePlanName;
  }

  public void setResourcePlanName(@org.apache.thrift.annotation.Nullable java.lang.String resourcePlanName) {
    this.resourcePlanName = resourcePlanName;
  }

  public void unsetResourcePlanName() {
    this.resourcePlanName = null;
  }

  /** Returns true if field resourcePlanName is set (has been assigned a value) and false otherwise */
  public boolean isSetResourcePlanName() {
    return this.resourcePlanName != null;
  }

  public void setResourcePlanNameIsSet(boolean value) {
    if (!value) {
      this.resourcePlanName = null;
    }
  }

  public void setFieldValue(_Fields field, @org.apache.thrift.annotation.Nullable java.lang.Object value) {
    switch (field) {
    case RESOURCE_PLAN_NAME:
      if (value == null) {
        unsetResourcePlanName();
      } else {
        setResourcePlanName((java.lang.String)value);
      }
      break;

    }
  }

  @org.apache.thrift.annotation.Nullable
  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case RESOURCE_PLAN_NAME:
      return getResourcePlanName();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case RESOURCE_PLAN_NAME:
      return isSetResourcePlanName();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that instanceof WMGetResourcePlanRequest)
      return this.equals((WMGetResourcePlanRequest)that);
    return false;
  }

  public boolean equals(WMGetResourcePlanRequest that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_resourcePlanName = true && this.isSetResourcePlanName();
    boolean that_present_resourcePlanName = true && that.isSetResourcePlanName();
    if (this_present_resourcePlanName || that_present_resourcePlanName) {
      if (!(this_present_resourcePlanName && that_present_resourcePlanName))
        return false;
      if (!this.resourcePlanName.equals(that.resourcePlanName))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((isSetResourcePlanName()) ? 131071 : 524287);
    if (isSetResourcePlanName())
      hashCode = hashCode * 8191 + resourcePlanName.hashCode();

    return hashCode;
  }

  @Override
  public int compareTo(WMGetResourcePlanRequest other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.compare(isSetResourcePlanName(), other.isSetResourcePlanName());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetResourcePlanName()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.resourcePlanName, other.resourcePlanName);
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
    java.lang.StringBuilder sb = new java.lang.StringBuilder("WMGetResourcePlanRequest(");
    boolean first = true;

    if (isSetResourcePlanName()) {
      sb.append("resourcePlanName:");
      if (this.resourcePlanName == null) {
        sb.append("null");
      } else {
        sb.append(this.resourcePlanName);
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

  private static class WMGetResourcePlanRequestStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public WMGetResourcePlanRequestStandardScheme getScheme() {
      return new WMGetResourcePlanRequestStandardScheme();
    }
  }

  private static class WMGetResourcePlanRequestStandardScheme extends org.apache.thrift.scheme.StandardScheme<WMGetResourcePlanRequest> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, WMGetResourcePlanRequest struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // RESOURCE_PLAN_NAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.resourcePlanName = iprot.readString();
              struct.setResourcePlanNameIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, WMGetResourcePlanRequest struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.resourcePlanName != null) {
        if (struct.isSetResourcePlanName()) {
          oprot.writeFieldBegin(RESOURCE_PLAN_NAME_FIELD_DESC);
          oprot.writeString(struct.resourcePlanName);
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class WMGetResourcePlanRequestTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public WMGetResourcePlanRequestTupleScheme getScheme() {
      return new WMGetResourcePlanRequestTupleScheme();
    }
  }

  private static class WMGetResourcePlanRequestTupleScheme extends org.apache.thrift.scheme.TupleScheme<WMGetResourcePlanRequest> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, WMGetResourcePlanRequest struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetResourcePlanName()) {
        optionals.set(0);
      }
      oprot.writeBitSet(optionals, 1);
      if (struct.isSetResourcePlanName()) {
        oprot.writeString(struct.resourcePlanName);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, WMGetResourcePlanRequest struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(1);
      if (incoming.get(0)) {
        struct.resourcePlanName = iprot.readString();
        struct.setResourcePlanNameIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

