/**
 * Autogenerated by Thrift Compiler (0.12.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package org.apache.hadoop.hive.metastore.api;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.12.0)")
public class PrimaryKeysRequest implements org.apache.thrift.TBase<PrimaryKeysRequest, PrimaryKeysRequest._Fields>, java.io.Serializable, Cloneable, Comparable<PrimaryKeysRequest> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("PrimaryKeysRequest");

  private static final org.apache.thrift.protocol.TField DB_NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("db_name", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField TBL_NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("tbl_name", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField CAT_NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("catName", org.apache.thrift.protocol.TType.STRING, (short)3);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new PrimaryKeysRequestStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new PrimaryKeysRequestTupleSchemeFactory();

  private @org.apache.thrift.annotation.Nullable java.lang.String db_name; // required
  private @org.apache.thrift.annotation.Nullable java.lang.String tbl_name; // required
  private @org.apache.thrift.annotation.Nullable java.lang.String catName; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    DB_NAME((short)1, "db_name"),
    TBL_NAME((short)2, "tbl_name"),
    CAT_NAME((short)3, "catName");

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
        case 1: // DB_NAME
          return DB_NAME;
        case 2: // TBL_NAME
          return TBL_NAME;
        case 3: // CAT_NAME
          return CAT_NAME;
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
  private static final _Fields optionals[] = {_Fields.CAT_NAME};
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.DB_NAME, new org.apache.thrift.meta_data.FieldMetaData("db_name", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.TBL_NAME, new org.apache.thrift.meta_data.FieldMetaData("tbl_name", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.CAT_NAME, new org.apache.thrift.meta_data.FieldMetaData("catName", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(PrimaryKeysRequest.class, metaDataMap);
  }

  public PrimaryKeysRequest() {
  }

  public PrimaryKeysRequest(
    java.lang.String db_name,
    java.lang.String tbl_name)
  {
    this();
    this.db_name = db_name;
    this.tbl_name = tbl_name;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public PrimaryKeysRequest(PrimaryKeysRequest other) {
    if (other.isSetDb_name()) {
      this.db_name = other.db_name;
    }
    if (other.isSetTbl_name()) {
      this.tbl_name = other.tbl_name;
    }
    if (other.isSetCatName()) {
      this.catName = other.catName;
    }
  }

  public PrimaryKeysRequest deepCopy() {
    return new PrimaryKeysRequest(this);
  }

  @Override
  public void clear() {
    this.db_name = null;
    this.tbl_name = null;
    this.catName = null;
  }

  @org.apache.thrift.annotation.Nullable
  public java.lang.String getDb_name() {
    return this.db_name;
  }

  public void setDb_name(@org.apache.thrift.annotation.Nullable java.lang.String db_name) {
    this.db_name = db_name;
  }

  public void unsetDb_name() {
    this.db_name = null;
  }

  /** Returns true if field db_name is set (has been assigned a value) and false otherwise */
  public boolean isSetDb_name() {
    return this.db_name != null;
  }

  public void setDb_nameIsSet(boolean value) {
    if (!value) {
      this.db_name = null;
    }
  }

  @org.apache.thrift.annotation.Nullable
  public java.lang.String getTbl_name() {
    return this.tbl_name;
  }

  public void setTbl_name(@org.apache.thrift.annotation.Nullable java.lang.String tbl_name) {
    this.tbl_name = tbl_name;
  }

  public void unsetTbl_name() {
    this.tbl_name = null;
  }

  /** Returns true if field tbl_name is set (has been assigned a value) and false otherwise */
  public boolean isSetTbl_name() {
    return this.tbl_name != null;
  }

  public void setTbl_nameIsSet(boolean value) {
    if (!value) {
      this.tbl_name = null;
    }
  }

  @org.apache.thrift.annotation.Nullable
  public java.lang.String getCatName() {
    return this.catName;
  }

  public void setCatName(@org.apache.thrift.annotation.Nullable java.lang.String catName) {
    this.catName = catName;
  }

  public void unsetCatName() {
    this.catName = null;
  }

  /** Returns true if field catName is set (has been assigned a value) and false otherwise */
  public boolean isSetCatName() {
    return this.catName != null;
  }

  public void setCatNameIsSet(boolean value) {
    if (!value) {
      this.catName = null;
    }
  }

  public void setFieldValue(_Fields field, @org.apache.thrift.annotation.Nullable java.lang.Object value) {
    switch (field) {
    case DB_NAME:
      if (value == null) {
        unsetDb_name();
      } else {
        setDb_name((java.lang.String)value);
      }
      break;

    case TBL_NAME:
      if (value == null) {
        unsetTbl_name();
      } else {
        setTbl_name((java.lang.String)value);
      }
      break;

    case CAT_NAME:
      if (value == null) {
        unsetCatName();
      } else {
        setCatName((java.lang.String)value);
      }
      break;

    }
  }

  @org.apache.thrift.annotation.Nullable
  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case DB_NAME:
      return getDb_name();

    case TBL_NAME:
      return getTbl_name();

    case CAT_NAME:
      return getCatName();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case DB_NAME:
      return isSetDb_name();
    case TBL_NAME:
      return isSetTbl_name();
    case CAT_NAME:
      return isSetCatName();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof PrimaryKeysRequest)
      return this.equals((PrimaryKeysRequest)that);
    return false;
  }

  public boolean equals(PrimaryKeysRequest that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_db_name = true && this.isSetDb_name();
    boolean that_present_db_name = true && that.isSetDb_name();
    if (this_present_db_name || that_present_db_name) {
      if (!(this_present_db_name && that_present_db_name))
        return false;
      if (!this.db_name.equals(that.db_name))
        return false;
    }

    boolean this_present_tbl_name = true && this.isSetTbl_name();
    boolean that_present_tbl_name = true && that.isSetTbl_name();
    if (this_present_tbl_name || that_present_tbl_name) {
      if (!(this_present_tbl_name && that_present_tbl_name))
        return false;
      if (!this.tbl_name.equals(that.tbl_name))
        return false;
    }

    boolean this_present_catName = true && this.isSetCatName();
    boolean that_present_catName = true && that.isSetCatName();
    if (this_present_catName || that_present_catName) {
      if (!(this_present_catName && that_present_catName))
        return false;
      if (!this.catName.equals(that.catName))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((isSetDb_name()) ? 131071 : 524287);
    if (isSetDb_name())
      hashCode = hashCode * 8191 + db_name.hashCode();

    hashCode = hashCode * 8191 + ((isSetTbl_name()) ? 131071 : 524287);
    if (isSetTbl_name())
      hashCode = hashCode * 8191 + tbl_name.hashCode();

    hashCode = hashCode * 8191 + ((isSetCatName()) ? 131071 : 524287);
    if (isSetCatName())
      hashCode = hashCode * 8191 + catName.hashCode();

    return hashCode;
  }

  @Override
  public int compareTo(PrimaryKeysRequest other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.valueOf(isSetDb_name()).compareTo(other.isSetDb_name());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetDb_name()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.db_name, other.db_name);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetTbl_name()).compareTo(other.isSetTbl_name());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTbl_name()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.tbl_name, other.tbl_name);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetCatName()).compareTo(other.isSetCatName());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCatName()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.catName, other.catName);
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
    java.lang.StringBuilder sb = new java.lang.StringBuilder("PrimaryKeysRequest(");
    boolean first = true;

    sb.append("db_name:");
    if (this.db_name == null) {
      sb.append("null");
    } else {
      sb.append(this.db_name);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("tbl_name:");
    if (this.tbl_name == null) {
      sb.append("null");
    } else {
      sb.append(this.tbl_name);
    }
    first = false;
    if (isSetCatName()) {
      if (!first) sb.append(", ");
      sb.append("catName:");
      if (this.catName == null) {
        sb.append("null");
      } else {
        sb.append(this.catName);
      }
      first = false;
    }
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    if (!isSetDb_name()) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'db_name' is unset! Struct:" + toString());
    }

    if (!isSetTbl_name()) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'tbl_name' is unset! Struct:" + toString());
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

  private static class PrimaryKeysRequestStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public PrimaryKeysRequestStandardScheme getScheme() {
      return new PrimaryKeysRequestStandardScheme();
    }
  }

  private static class PrimaryKeysRequestStandardScheme extends org.apache.thrift.scheme.StandardScheme<PrimaryKeysRequest> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, PrimaryKeysRequest struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // DB_NAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.db_name = iprot.readString();
              struct.setDb_nameIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // TBL_NAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.tbl_name = iprot.readString();
              struct.setTbl_nameIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // CAT_NAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.catName = iprot.readString();
              struct.setCatNameIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, PrimaryKeysRequest struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.db_name != null) {
        oprot.writeFieldBegin(DB_NAME_FIELD_DESC);
        oprot.writeString(struct.db_name);
        oprot.writeFieldEnd();
      }
      if (struct.tbl_name != null) {
        oprot.writeFieldBegin(TBL_NAME_FIELD_DESC);
        oprot.writeString(struct.tbl_name);
        oprot.writeFieldEnd();
      }
      if (struct.catName != null) {
        if (struct.isSetCatName()) {
          oprot.writeFieldBegin(CAT_NAME_FIELD_DESC);
          oprot.writeString(struct.catName);
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class PrimaryKeysRequestTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public PrimaryKeysRequestTupleScheme getScheme() {
      return new PrimaryKeysRequestTupleScheme();
    }
  }

  private static class PrimaryKeysRequestTupleScheme extends org.apache.thrift.scheme.TupleScheme<PrimaryKeysRequest> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, PrimaryKeysRequest struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      oprot.writeString(struct.db_name);
      oprot.writeString(struct.tbl_name);
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetCatName()) {
        optionals.set(0);
      }
      oprot.writeBitSet(optionals, 1);
      if (struct.isSetCatName()) {
        oprot.writeString(struct.catName);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, PrimaryKeysRequest struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      struct.db_name = iprot.readString();
      struct.setDb_nameIsSet(true);
      struct.tbl_name = iprot.readString();
      struct.setTbl_nameIsSet(true);
      java.util.BitSet incoming = iprot.readBitSet(1);
      if (incoming.get(0)) {
        struct.catName = iprot.readString();
        struct.setCatNameIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

