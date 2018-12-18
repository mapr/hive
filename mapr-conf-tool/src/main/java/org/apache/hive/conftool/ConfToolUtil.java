/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.hive.conftool;

/**
 * ConfToolUtil class for configuration tool
 */
public final class ConfToolUtil {
  private ConfToolUtil() {
  }

  private static final String TRUE = "true";
  private static final String FALSE = "false";

  /**
   * Check if string not empty and not null.
   *
   * @param value string to check
   * @return true if string not empty and not null
   */
  static boolean isNotNullNotEmpty(String value) {
    return value != null && !value.trim().isEmpty();
  }

  /**
   * Check if string has true or false value.
   *
   * @param value string to check
   * @return true if string has true or false value
   */
  static boolean isTrueOrFalse(String value) {
    return TRUE.equalsIgnoreCase(value) || FALSE.equalsIgnoreCase(value);
  }

  /**
   * Print boolean value to stdout.
   *
   * @param value value to print
   */
  static void printBool(boolean value) {
    System.out.print(Boolean.toString(value));
  }

  /**
   * Check if string has 'true' or 'false' or 'custom' value .
   *
   * @param value string to check
   * @return true if string has 'true' or 'false' or 'custom' value
   */
  static boolean isTrueOrFalseOrCustom(String value) {
    for (Security security : Security.values()) {
      if (security.value().equalsIgnoreCase(value.trim())) {
        return true;
      }
    }
    return false;
  }
}
