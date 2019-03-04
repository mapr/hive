/**
 * Autogenerated by Thrift Compiler (0.12.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package org.apache.hadoop.hive.metastore.api;


@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.12.0)")
public enum DataOperationType implements org.apache.thrift.TEnum {
  SELECT(1),
  INSERT(2),
  UPDATE(3),
  DELETE(4),
  UNSET(5),
  NO_TXN(6);

  private final int value;

  private DataOperationType(int value) {
    this.value = value;
  }

  /**
   * Get the integer value of this enum value, as defined in the Thrift IDL.
   */
  public int getValue() {
    return value;
  }

  /**
   * Find a the enum type by its integer value, as defined in the Thrift IDL.
   * @return null if the value is not found.
   */
  @org.apache.thrift.annotation.Nullable
  public static DataOperationType findByValue(int value) { 
    switch (value) {
      case 1:
        return SELECT;
      case 2:
        return INSERT;
      case 3:
        return UPDATE;
      case 4:
        return DELETE;
      case 5:
        return UNSET;
      case 6:
        return NO_TXN;
      default:
        return null;
    }
  }
}
