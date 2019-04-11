/**
 * Autogenerated by Thrift Compiler (0.12.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package org.apache.hadoop.hive.metastore.api;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.12.0)")
public class ResourceUri implements org.apache.thrift.TBase<ResourceUri, ResourceUri._Fields>, java.io.Serializable, Cloneable, Comparable<ResourceUri> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("ResourceUri");

  private static final org.apache.thrift.protocol.TField RESOURCE_TYPE_FIELD_DESC = new org.apache.thrift.protocol.TField("resourceType", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField URI_FIELD_DESC = new org.apache.thrift.protocol.TField("uri", org.apache.thrift.protocol.TType.STRING, (short)2);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new ResourceUriStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new ResourceUriTupleSchemeFactory();

  private @org.apache.thrift.annotation.Nullable ResourceType resourceType; // required
  private @org.apache.thrift.annotation.Nullable java.lang.String uri; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    /**
     * 
     * @see ResourceType
     */
    RESOURCE_TYPE((short)1, "resourceType"),
    URI((short)2, "uri");

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
        case 1: // RESOURCE_TYPE
          return RESOURCE_TYPE;
        case 2: // URI
          return URI;
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
    tmpMap.put(_Fields.RESOURCE_TYPE, new org.apache.thrift.meta_data.FieldMetaData("resourceType", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.EnumMetaData(org.apache.thrift.protocol.TType.ENUM, ResourceType.class)));
    tmpMap.put(_Fields.URI, new org.apache.thrift.meta_data.FieldMetaData("uri", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(ResourceUri.class, metaDataMap);
  }

  public ResourceUri() {
  }

  public ResourceUri(
    ResourceType resourceType,
    java.lang.String uri)
  {
    this();
    this.resourceType = resourceType;
    this.uri = uri;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public ResourceUri(ResourceUri other) {
    if (other.isSetResourceType()) {
      this.resourceType = other.resourceType;
    }
    if (other.isSetUri()) {
      this.uri = other.uri;
    }
  }

  public ResourceUri deepCopy() {
    return new ResourceUri(this);
  }

  @Override
  public void clear() {
    this.resourceType = null;
    this.uri = null;
  }

  /**
   * 
   * @see ResourceType
   */
  @org.apache.thrift.annotation.Nullable
  public ResourceType getResourceType() {
    return this.resourceType;
  }

  /**
   * 
   * @see ResourceType
   */
  public void setResourceType(@org.apache.thrift.annotation.Nullable ResourceType resourceType) {
    this.resourceType = resourceType;
  }

  public void unsetResourceType() {
    this.resourceType = null;
  }

  /** Returns true if field resourceType is set (has been assigned a value) and false otherwise */
  public boolean isSetResourceType() {
    return this.resourceType != null;
  }

  public void setResourceTypeIsSet(boolean value) {
    if (!value) {
      this.resourceType = null;
    }
  }

  @org.apache.thrift.annotation.Nullable
  public java.lang.String getUri() {
    return this.uri;
  }

  public void setUri(@org.apache.thrift.annotation.Nullable java.lang.String uri) {
    this.uri = uri;
  }

  public void unsetUri() {
    this.uri = null;
  }

  /** Returns true if field uri is set (has been assigned a value) and false otherwise */
  public boolean isSetUri() {
    return this.uri != null;
  }

  public void setUriIsSet(boolean value) {
    if (!value) {
      this.uri = null;
    }
  }

  public void setFieldValue(_Fields field, @org.apache.thrift.annotation.Nullable java.lang.Object value) {
    switch (field) {
    case RESOURCE_TYPE:
      if (value == null) {
        unsetResourceType();
      } else {
        setResourceType((ResourceType)value);
      }
      break;

    case URI:
      if (value == null) {
        unsetUri();
      } else {
        setUri((java.lang.String)value);
      }
      break;

    }
  }

  @org.apache.thrift.annotation.Nullable
  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case RESOURCE_TYPE:
      return getResourceType();

    case URI:
      return getUri();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case RESOURCE_TYPE:
      return isSetResourceType();
    case URI:
      return isSetUri();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof ResourceUri)
      return this.equals((ResourceUri)that);
    return false;
  }

  public boolean equals(ResourceUri that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_resourceType = true && this.isSetResourceType();
    boolean that_present_resourceType = true && that.isSetResourceType();
    if (this_present_resourceType || that_present_resourceType) {
      if (!(this_present_resourceType && that_present_resourceType))
        return false;
      if (!this.resourceType.equals(that.resourceType))
        return false;
    }

    boolean this_present_uri = true && this.isSetUri();
    boolean that_present_uri = true && that.isSetUri();
    if (this_present_uri || that_present_uri) {
      if (!(this_present_uri && that_present_uri))
        return false;
      if (!this.uri.equals(that.uri))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((isSetResourceType()) ? 131071 : 524287);
    if (isSetResourceType())
      hashCode = hashCode * 8191 + resourceType.getValue();

    hashCode = hashCode * 8191 + ((isSetUri()) ? 131071 : 524287);
    if (isSetUri())
      hashCode = hashCode * 8191 + uri.hashCode();

    return hashCode;
  }

  @Override
  public int compareTo(ResourceUri other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.valueOf(isSetResourceType()).compareTo(other.isSetResourceType());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetResourceType()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.resourceType, other.resourceType);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetUri()).compareTo(other.isSetUri());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetUri()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.uri, other.uri);
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
    java.lang.StringBuilder sb = new java.lang.StringBuilder("ResourceUri(");
    boolean first = true;

    sb.append("resourceType:");
    if (this.resourceType == null) {
      sb.append("null");
    } else {
      sb.append(this.resourceType);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("uri:");
    if (this.uri == null) {
      sb.append("null");
    } else {
      sb.append(this.uri);
    }
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
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class ResourceUriStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public ResourceUriStandardScheme getScheme() {
      return new ResourceUriStandardScheme();
    }
  }

  private static class ResourceUriStandardScheme extends org.apache.thrift.scheme.StandardScheme<ResourceUri> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, ResourceUri struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // RESOURCE_TYPE
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.resourceType = org.apache.hadoop.hive.metastore.api.ResourceType.findByValue(iprot.readI32());
              struct.setResourceTypeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // URI
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.uri = iprot.readString();
              struct.setUriIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, ResourceUri struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.resourceType != null) {
        oprot.writeFieldBegin(RESOURCE_TYPE_FIELD_DESC);
        oprot.writeI32(struct.resourceType.getValue());
        oprot.writeFieldEnd();
      }
      if (struct.uri != null) {
        oprot.writeFieldBegin(URI_FIELD_DESC);
        oprot.writeString(struct.uri);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class ResourceUriTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public ResourceUriTupleScheme getScheme() {
      return new ResourceUriTupleScheme();
    }
  }

  private static class ResourceUriTupleScheme extends org.apache.thrift.scheme.TupleScheme<ResourceUri> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, ResourceUri struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetResourceType()) {
        optionals.set(0);
      }
      if (struct.isSetUri()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetResourceType()) {
        oprot.writeI32(struct.resourceType.getValue());
      }
      if (struct.isSetUri()) {
        oprot.writeString(struct.uri);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, ResourceUri struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(2);
      if (incoming.get(0)) {
        struct.resourceType = org.apache.hadoop.hive.metastore.api.ResourceType.findByValue(iprot.readI32());
        struct.setResourceTypeIsSet(true);
      }
      if (incoming.get(1)) {
        struct.uri = iprot.readString();
        struct.setUriIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

