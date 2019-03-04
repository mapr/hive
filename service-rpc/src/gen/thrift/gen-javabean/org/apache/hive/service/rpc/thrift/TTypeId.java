/**
 * Autogenerated by Thrift Compiler (0.12.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package org.apache.hive.service.rpc.thrift;


@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.12.0)")
public enum TTypeId implements org.apache.thrift.TEnum {
  BOOLEAN_TYPE(0),
  TINYINT_TYPE(1),
  SMALLINT_TYPE(2),
  INT_TYPE(3),
  BIGINT_TYPE(4),
  FLOAT_TYPE(5),
  DOUBLE_TYPE(6),
  STRING_TYPE(7),
  TIMESTAMP_TYPE(8),
  BINARY_TYPE(9),
  ARRAY_TYPE(10),
  MAP_TYPE(11),
  STRUCT_TYPE(12),
  UNION_TYPE(13),
  USER_DEFINED_TYPE(14),
  DECIMAL_TYPE(15),
  NULL_TYPE(16),
  DATE_TYPE(17),
  VARCHAR_TYPE(18),
  CHAR_TYPE(19),
  INTERVAL_YEAR_MONTH_TYPE(20),
  INTERVAL_DAY_TIME_TYPE(21);

  private final int value;

  private TTypeId(int value) {
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
  public static TTypeId findByValue(int value) { 
    switch (value) {
      case 0:
        return BOOLEAN_TYPE;
      case 1:
        return TINYINT_TYPE;
      case 2:
        return SMALLINT_TYPE;
      case 3:
        return INT_TYPE;
      case 4:
        return BIGINT_TYPE;
      case 5:
        return FLOAT_TYPE;
      case 6:
        return DOUBLE_TYPE;
      case 7:
        return STRING_TYPE;
      case 8:
        return TIMESTAMP_TYPE;
      case 9:
        return BINARY_TYPE;
      case 10:
        return ARRAY_TYPE;
      case 11:
        return MAP_TYPE;
      case 12:
        return STRUCT_TYPE;
      case 13:
        return UNION_TYPE;
      case 14:
        return USER_DEFINED_TYPE;
      case 15:
        return DECIMAL_TYPE;
      case 16:
        return NULL_TYPE;
      case 17:
        return DATE_TYPE;
      case 18:
        return VARCHAR_TYPE;
      case 19:
        return CHAR_TYPE;
      case 20:
        return INTERVAL_YEAR_MONTH_TYPE;
      case 21:
        return INTERVAL_DAY_TIME_TYPE;
      default:
        return null;
    }
  }
}
