/**
 * Autogenerated by Thrift Compiler (0.12.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package org.apache.hadoop.hive.metastore.api;


@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.12.0)")
public enum SchemaValidation implements org.apache.thrift.TEnum {
  LATEST(1),
  ALL(2);

  private final int value;

  private SchemaValidation(int value) {
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
  public static SchemaValidation findByValue(int value) { 
    switch (value) {
      case 1:
        return LATEST;
      case 2:
        return ALL;
      default:
        return null;
    }
  }
}
