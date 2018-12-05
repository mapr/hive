/**
 * Autogenerated by Thrift Compiler (0.9.2)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package org.apache.hadoop.hive.metastore.api;

import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;

import org.apache.thrift.scheme.TupleScheme;
import org.apache.thrift.protocol.TTupleProtocol;
import org.apache.thrift.protocol.TProtocolException;
import org.apache.thrift.EncodingUtils;
import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.server.AbstractNonblockingServer.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;
import java.util.Set;
import java.util.HashSet;
import java.util.EnumSet;
import java.util.Collections;
import java.util.BitSet;
import java.nio.ByteBuffer;
import java.util.Arrays;
import javax.annotation.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked"})
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2015-8-3")
public class DropPartitionsExpr implements org.apache.thrift.TBase<DropPartitionsExpr, DropPartitionsExpr._Fields>, java.io.Serializable, Cloneable, Comparable<DropPartitionsExpr> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("DropPartitionsExpr");

  private static final org.apache.thrift.protocol.TField EXPR_FIELD_DESC = new org.apache.thrift.protocol.TField("expr", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField PART_ARCHIVE_LEVEL_FIELD_DESC = new org.apache.thrift.protocol.TField("partArchiveLevel", org.apache.thrift.protocol.TType.I32, (short)2);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new DropPartitionsExprStandardSchemeFactory());
    schemes.put(TupleScheme.class, new DropPartitionsExprTupleSchemeFactory());
  }

  private ByteBuffer expr; // required
  private int partArchiveLevel; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    EXPR((short)1, "expr"),
    PART_ARCHIVE_LEVEL((short)2, "partArchiveLevel");

    private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

    static {
      for (_Fields field : EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // EXPR
          return EXPR;
        case 2: // PART_ARCHIVE_LEVEL
          return PART_ARCHIVE_LEVEL;
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
      if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final String _fieldName;

    _Fields(short thriftId, String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  private static final int __PARTARCHIVELEVEL_ISSET_ID = 0;
  private byte __isset_bitfield = 0;
  private static final _Fields optionals[] = {_Fields.PART_ARCHIVE_LEVEL};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.EXPR, new org.apache.thrift.meta_data.FieldMetaData("expr", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING        , true)));
    tmpMap.put(_Fields.PART_ARCHIVE_LEVEL, new org.apache.thrift.meta_data.FieldMetaData("partArchiveLevel", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(DropPartitionsExpr.class, metaDataMap);
  }

  public DropPartitionsExpr() {
  }

  public DropPartitionsExpr(
    ByteBuffer expr)
  {
    this();
    this.expr = org.apache.thrift.TBaseHelper.copyBinary(expr);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public DropPartitionsExpr(DropPartitionsExpr other) {
    __isset_bitfield = other.__isset_bitfield;
    if (other.isSetExpr()) {
      this.expr = org.apache.thrift.TBaseHelper.copyBinary(other.expr);
    }
    this.partArchiveLevel = other.partArchiveLevel;
  }

  public DropPartitionsExpr deepCopy() {
    return new DropPartitionsExpr(this);
  }

  @Override
  public void clear() {
    this.expr = null;
    setPartArchiveLevelIsSet(false);
    this.partArchiveLevel = 0;
  }

  public byte[] getExpr() {
    setExpr(org.apache.thrift.TBaseHelper.rightSize(expr));
    return expr == null ? null : expr.array();
  }

  public ByteBuffer bufferForExpr() {
    return org.apache.thrift.TBaseHelper.copyBinary(expr);
  }

  public void setExpr(byte[] expr) {
    this.expr = expr == null ? (ByteBuffer)null : ByteBuffer.wrap(Arrays.copyOf(expr, expr.length));
  }

  public void setExpr(ByteBuffer expr) {
    this.expr = org.apache.thrift.TBaseHelper.copyBinary(expr);
  }

  public void unsetExpr() {
    this.expr = null;
  }

  /** Returns true if field expr is set (has been assigned a value) and false otherwise */
  public boolean isSetExpr() {
    return this.expr != null;
  }

  public void setExprIsSet(boolean value) {
    if (!value) {
      this.expr = null;
    }
  }

  public int getPartArchiveLevel() {
    return this.partArchiveLevel;
  }

  public void setPartArchiveLevel(int partArchiveLevel) {
    this.partArchiveLevel = partArchiveLevel;
    setPartArchiveLevelIsSet(true);
  }

  public void unsetPartArchiveLevel() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __PARTARCHIVELEVEL_ISSET_ID);
  }

  /** Returns true if field partArchiveLevel is set (has been assigned a value) and false otherwise */
  public boolean isSetPartArchiveLevel() {
    return EncodingUtils.testBit(__isset_bitfield, __PARTARCHIVELEVEL_ISSET_ID);
  }

  public void setPartArchiveLevelIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __PARTARCHIVELEVEL_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case EXPR:
      if (value == null) {
        unsetExpr();
      } else {
        setExpr((ByteBuffer)value);
      }
      break;

    case PART_ARCHIVE_LEVEL:
      if (value == null) {
        unsetPartArchiveLevel();
      } else {
        setPartArchiveLevel((Integer)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case EXPR:
      return getExpr();

    case PART_ARCHIVE_LEVEL:
      return Integer.valueOf(getPartArchiveLevel());

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case EXPR:
      return isSetExpr();
    case PART_ARCHIVE_LEVEL:
      return isSetPartArchiveLevel();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof DropPartitionsExpr)
      return this.equals((DropPartitionsExpr)that);
    return false;
  }

  public boolean equals(DropPartitionsExpr that) {
    if (that == null)
      return false;

    boolean this_present_expr = true && this.isSetExpr();
    boolean that_present_expr = true && that.isSetExpr();
    if (this_present_expr || that_present_expr) {
      if (!(this_present_expr && that_present_expr))
        return false;
      if (!this.expr.equals(that.expr))
        return false;
    }

    boolean this_present_partArchiveLevel = true && this.isSetPartArchiveLevel();
    boolean that_present_partArchiveLevel = true && that.isSetPartArchiveLevel();
    if (this_present_partArchiveLevel || that_present_partArchiveLevel) {
      if (!(this_present_partArchiveLevel && that_present_partArchiveLevel))
        return false;
      if (this.partArchiveLevel != that.partArchiveLevel)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_expr = true && (isSetExpr());
    list.add(present_expr);
    if (present_expr)
      list.add(expr);

    boolean present_partArchiveLevel = true && (isSetPartArchiveLevel());
    list.add(present_partArchiveLevel);
    if (present_partArchiveLevel)
      list.add(partArchiveLevel);

    return list.hashCode();
  }

  @Override
  public int compareTo(DropPartitionsExpr other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetExpr()).compareTo(other.isSetExpr());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetExpr()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.expr, other.expr);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetPartArchiveLevel()).compareTo(other.isSetPartArchiveLevel());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPartArchiveLevel()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.partArchiveLevel, other.partArchiveLevel);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("DropPartitionsExpr(");
    boolean first = true;

    sb.append("expr:");
    if (this.expr == null) {
      sb.append("null");
    } else {
      org.apache.thrift.TBaseHelper.toString(this.expr, sb);
    }
    first = false;
    if (isSetPartArchiveLevel()) {
      if (!first) sb.append(", ");
      sb.append("partArchiveLevel:");
      sb.append(this.partArchiveLevel);
      first = false;
    }
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    if (!isSetExpr()) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'expr' is unset! Struct:" + toString());
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

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    try {
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class DropPartitionsExprStandardSchemeFactory implements SchemeFactory {
    public DropPartitionsExprStandardScheme getScheme() {
      return new DropPartitionsExprStandardScheme();
    }
  }

  private static class DropPartitionsExprStandardScheme extends StandardScheme<DropPartitionsExpr> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, DropPartitionsExpr struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // EXPR
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.expr = iprot.readBinary();
              struct.setExprIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // PART_ARCHIVE_LEVEL
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.partArchiveLevel = iprot.readI32();
              struct.setPartArchiveLevelIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, DropPartitionsExpr struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.expr != null) {
        oprot.writeFieldBegin(EXPR_FIELD_DESC);
        oprot.writeBinary(struct.expr);
        oprot.writeFieldEnd();
      }
      if (struct.isSetPartArchiveLevel()) {
        oprot.writeFieldBegin(PART_ARCHIVE_LEVEL_FIELD_DESC);
        oprot.writeI32(struct.partArchiveLevel);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class DropPartitionsExprTupleSchemeFactory implements SchemeFactory {
    public DropPartitionsExprTupleScheme getScheme() {
      return new DropPartitionsExprTupleScheme();
    }
  }

  private static class DropPartitionsExprTupleScheme extends TupleScheme<DropPartitionsExpr> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, DropPartitionsExpr struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      oprot.writeBinary(struct.expr);
      BitSet optionals = new BitSet();
      if (struct.isSetPartArchiveLevel()) {
        optionals.set(0);
      }
      oprot.writeBitSet(optionals, 1);
      if (struct.isSetPartArchiveLevel()) {
        oprot.writeI32(struct.partArchiveLevel);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, DropPartitionsExpr struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      struct.expr = iprot.readBinary();
      struct.setExprIsSet(true);
      BitSet incoming = iprot.readBitSet(1);
      if (incoming.get(0)) {
        struct.partArchiveLevel = iprot.readI32();
        struct.setPartArchiveLevelIsSet(true);
      }
    }
  }

}
