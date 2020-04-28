/**
 * Autogenerated by Thrift Compiler (0.13.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package org.apache.hadoop.hive.metastore.api;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.13.0)")
public class SerDeInfo implements org.apache.thrift.TBase<SerDeInfo, SerDeInfo._Fields>, java.io.Serializable, Cloneable, Comparable<SerDeInfo> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("SerDeInfo");

  private static final org.apache.thrift.protocol.TField NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("name", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField SERIALIZATION_LIB_FIELD_DESC = new org.apache.thrift.protocol.TField("serializationLib", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField PARAMETERS_FIELD_DESC = new org.apache.thrift.protocol.TField("parameters", org.apache.thrift.protocol.TType.MAP, (short)3);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new SerDeInfoStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new SerDeInfoTupleSchemeFactory();

  private @org.apache.thrift.annotation.Nullable java.lang.String name; // required
  private @org.apache.thrift.annotation.Nullable java.lang.String serializationLib; // required
  private @org.apache.thrift.annotation.Nullable java.util.Map<java.lang.String,java.lang.String> parameters; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    NAME((short)1, "name"),
    SERIALIZATION_LIB((short)2, "serializationLib"),
    PARAMETERS((short)3, "parameters");

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
        case 1: // NAME
          return NAME;
        case 2: // SERIALIZATION_LIB
          return SERIALIZATION_LIB;
        case 3: // PARAMETERS
          return PARAMETERS;
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
    tmpMap.put(_Fields.NAME, new org.apache.thrift.meta_data.FieldMetaData("name", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.SERIALIZATION_LIB, new org.apache.thrift.meta_data.FieldMetaData("serializationLib", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.PARAMETERS, new org.apache.thrift.meta_data.FieldMetaData("parameters", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.MapMetaData(org.apache.thrift.protocol.TType.MAP, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING), 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING))));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(SerDeInfo.class, metaDataMap);
  }

  public SerDeInfo() {
  }

  public SerDeInfo(
    java.lang.String name,
    java.lang.String serializationLib,
    java.util.Map<java.lang.String,java.lang.String> parameters)
  {
    this();
    this.name = name;
    this.serializationLib = serializationLib;
    this.parameters = parameters;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public SerDeInfo(SerDeInfo other) {
    if (other.isSetName()) {
      this.name = other.name;
    }
    if (other.isSetSerializationLib()) {
      this.serializationLib = other.serializationLib;
    }
    if (other.isSetParameters()) {
      java.util.Map<java.lang.String,java.lang.String> __this__parameters = new java.util.HashMap<java.lang.String,java.lang.String>(other.parameters);
      this.parameters = __this__parameters;
    }
  }

  public SerDeInfo deepCopy() {
    return new SerDeInfo(this);
  }

  @Override
  public void clear() {
    this.name = null;
    this.serializationLib = null;
    this.parameters = null;
  }

  @org.apache.thrift.annotation.Nullable
  public java.lang.String getName() {
    return this.name;
  }

  public void setName(@org.apache.thrift.annotation.Nullable java.lang.String name) {
    this.name = name;
  }

  public void unsetName() {
    this.name = null;
  }

  /** Returns true if field name is set (has been assigned a value) and false otherwise */
  public boolean isSetName() {
    return this.name != null;
  }

  public void setNameIsSet(boolean value) {
    if (!value) {
      this.name = null;
    }
  }

  @org.apache.thrift.annotation.Nullable
  public java.lang.String getSerializationLib() {
    return this.serializationLib;
  }

  public void setSerializationLib(@org.apache.thrift.annotation.Nullable java.lang.String serializationLib) {
    this.serializationLib = serializationLib;
  }

  public void unsetSerializationLib() {
    this.serializationLib = null;
  }

  /** Returns true if field serializationLib is set (has been assigned a value) and false otherwise */
  public boolean isSetSerializationLib() {
    return this.serializationLib != null;
  }

  public void setSerializationLibIsSet(boolean value) {
    if (!value) {
      this.serializationLib = null;
    }
  }

  public int getParametersSize() {
    return (this.parameters == null) ? 0 : this.parameters.size();
  }

  public void putToParameters(java.lang.String key, java.lang.String val) {
    if (this.parameters == null) {
      this.parameters = new java.util.HashMap<java.lang.String,java.lang.String>();
    }
    this.parameters.put(key, val);
  }

  @org.apache.thrift.annotation.Nullable
  public java.util.Map<java.lang.String,java.lang.String> getParameters() {
    return this.parameters;
  }

  public void setParameters(@org.apache.thrift.annotation.Nullable java.util.Map<java.lang.String,java.lang.String> parameters) {
    this.parameters = parameters;
  }

  public void unsetParameters() {
    this.parameters = null;
  }

  /** Returns true if field parameters is set (has been assigned a value) and false otherwise */
  public boolean isSetParameters() {
    return this.parameters != null;
  }

  public void setParametersIsSet(boolean value) {
    if (!value) {
      this.parameters = null;
    }
  }

  public void setFieldValue(_Fields field, @org.apache.thrift.annotation.Nullable java.lang.Object value) {
    switch (field) {
    case NAME:
      if (value == null) {
        unsetName();
      } else {
        setName((java.lang.String)value);
      }
      break;

    case SERIALIZATION_LIB:
      if (value == null) {
        unsetSerializationLib();
      } else {
        setSerializationLib((java.lang.String)value);
      }
      break;

    case PARAMETERS:
      if (value == null) {
        unsetParameters();
      } else {
        setParameters((java.util.Map<java.lang.String,java.lang.String>)value);
      }
      break;

    }
  }

  @org.apache.thrift.annotation.Nullable
  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case NAME:
      return getName();

    case SERIALIZATION_LIB:
      return getSerializationLib();

    case PARAMETERS:
      return getParameters();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case NAME:
      return isSetName();
    case SERIALIZATION_LIB:
      return isSetSerializationLib();
    case PARAMETERS:
      return isSetParameters();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof SerDeInfo)
      return this.equals((SerDeInfo)that);
    return false;
  }

  public boolean equals(SerDeInfo that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_name = true && this.isSetName();
    boolean that_present_name = true && that.isSetName();
    if (this_present_name || that_present_name) {
      if (!(this_present_name && that_present_name))
        return false;
      if (!this.name.equals(that.name))
        return false;
    }

    boolean this_present_serializationLib = true && this.isSetSerializationLib();
    boolean that_present_serializationLib = true && that.isSetSerializationLib();
    if (this_present_serializationLib || that_present_serializationLib) {
      if (!(this_present_serializationLib && that_present_serializationLib))
        return false;
      if (!this.serializationLib.equals(that.serializationLib))
        return false;
    }

    boolean this_present_parameters = true && this.isSetParameters();
    boolean that_present_parameters = true && that.isSetParameters();
    if (this_present_parameters || that_present_parameters) {
      if (!(this_present_parameters && that_present_parameters))
        return false;
      if (!this.parameters.equals(that.parameters))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((isSetName()) ? 131071 : 524287);
    if (isSetName())
      hashCode = hashCode * 8191 + name.hashCode();

    hashCode = hashCode * 8191 + ((isSetSerializationLib()) ? 131071 : 524287);
    if (isSetSerializationLib())
      hashCode = hashCode * 8191 + serializationLib.hashCode();

    hashCode = hashCode * 8191 + ((isSetParameters()) ? 131071 : 524287);
    if (isSetParameters())
      hashCode = hashCode * 8191 + parameters.hashCode();

    return hashCode;
  }

  @Override
  public int compareTo(SerDeInfo other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.valueOf(isSetName()).compareTo(other.isSetName());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetName()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.name, other.name);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetSerializationLib()).compareTo(other.isSetSerializationLib());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSerializationLib()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.serializationLib, other.serializationLib);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetParameters()).compareTo(other.isSetParameters());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetParameters()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.parameters, other.parameters);
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
    java.lang.StringBuilder sb = new java.lang.StringBuilder("SerDeInfo(");
    boolean first = true;

    sb.append("name:");
    if (this.name == null) {
      sb.append("null");
    } else {
      sb.append(this.name);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("serializationLib:");
    if (this.serializationLib == null) {
      sb.append("null");
    } else {
      sb.append(this.serializationLib);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("parameters:");
    if (this.parameters == null) {
      sb.append("null");
    } else {
      sb.append(this.parameters);
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

  private static class SerDeInfoStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public SerDeInfoStandardScheme getScheme() {
      return new SerDeInfoStandardScheme();
    }
  }

  private static class SerDeInfoStandardScheme extends org.apache.thrift.scheme.StandardScheme<SerDeInfo> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, SerDeInfo struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // NAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.name = iprot.readString();
              struct.setNameIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // SERIALIZATION_LIB
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.serializationLib = iprot.readString();
              struct.setSerializationLibIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // PARAMETERS
            if (schemeField.type == org.apache.thrift.protocol.TType.MAP) {
              {
                org.apache.thrift.protocol.TMap _map104 = iprot.readMapBegin();
                struct.parameters = new java.util.HashMap<java.lang.String,java.lang.String>(2*_map104.size);
                @org.apache.thrift.annotation.Nullable java.lang.String _key105;
                @org.apache.thrift.annotation.Nullable java.lang.String _val106;
                for (int _i107 = 0; _i107 < _map104.size; ++_i107)
                {
                  _key105 = iprot.readString();
                  _val106 = iprot.readString();
                  struct.parameters.put(_key105, _val106);
                }
                iprot.readMapEnd();
              }
              struct.setParametersIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, SerDeInfo struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.name != null) {
        oprot.writeFieldBegin(NAME_FIELD_DESC);
        oprot.writeString(struct.name);
        oprot.writeFieldEnd();
      }
      if (struct.serializationLib != null) {
        oprot.writeFieldBegin(SERIALIZATION_LIB_FIELD_DESC);
        oprot.writeString(struct.serializationLib);
        oprot.writeFieldEnd();
      }
      if (struct.parameters != null) {
        oprot.writeFieldBegin(PARAMETERS_FIELD_DESC);
        {
          oprot.writeMapBegin(new org.apache.thrift.protocol.TMap(org.apache.thrift.protocol.TType.STRING, org.apache.thrift.protocol.TType.STRING, struct.parameters.size()));
          for (java.util.Map.Entry<java.lang.String, java.lang.String> _iter108 : struct.parameters.entrySet())
          {
            oprot.writeString(_iter108.getKey());
            oprot.writeString(_iter108.getValue());
          }
          oprot.writeMapEnd();
        }
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class SerDeInfoTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public SerDeInfoTupleScheme getScheme() {
      return new SerDeInfoTupleScheme();
    }
  }

  private static class SerDeInfoTupleScheme extends org.apache.thrift.scheme.TupleScheme<SerDeInfo> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, SerDeInfo struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetName()) {
        optionals.set(0);
      }
      if (struct.isSetSerializationLib()) {
        optionals.set(1);
      }
      if (struct.isSetParameters()) {
        optionals.set(2);
      }
      oprot.writeBitSet(optionals, 3);
      if (struct.isSetName()) {
        oprot.writeString(struct.name);
      }
      if (struct.isSetSerializationLib()) {
        oprot.writeString(struct.serializationLib);
      }
      if (struct.isSetParameters()) {
        {
          oprot.writeI32(struct.parameters.size());
          for (java.util.Map.Entry<java.lang.String, java.lang.String> _iter109 : struct.parameters.entrySet())
          {
            oprot.writeString(_iter109.getKey());
            oprot.writeString(_iter109.getValue());
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, SerDeInfo struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(3);
      if (incoming.get(0)) {
        struct.name = iprot.readString();
        struct.setNameIsSet(true);
      }
      if (incoming.get(1)) {
        struct.serializationLib = iprot.readString();
        struct.setSerializationLibIsSet(true);
      }
      if (incoming.get(2)) {
        {
          org.apache.thrift.protocol.TMap _map110 = new org.apache.thrift.protocol.TMap(org.apache.thrift.protocol.TType.STRING, org.apache.thrift.protocol.TType.STRING, iprot.readI32());
          struct.parameters = new java.util.HashMap<java.lang.String,java.lang.String>(2*_map110.size);
          @org.apache.thrift.annotation.Nullable java.lang.String _key111;
          @org.apache.thrift.annotation.Nullable java.lang.String _val112;
          for (int _i113 = 0; _i113 < _map110.size; ++_i113)
          {
            _key111 = iprot.readString();
            _val112 = iprot.readString();
            struct.parameters.put(_key111, _val112);
          }
        }
        struct.setParametersIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

