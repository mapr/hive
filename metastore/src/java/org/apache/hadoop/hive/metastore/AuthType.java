/*
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

package org.apache.hadoop.hive.metastore;

/**
 * Authentication types for Metastore clients.
 */
public enum AuthType {
  NONE("none"), KERBEROS("kerberos"), MAPRSASL("maprsasl");
  private final String authType;

  AuthType(String authType) {
    this.authType = authType;
  }

  public static AuthType parse(String authType) {
    switch (authType.trim().toLowerCase()) {
    case "none":
      return NONE;
    case "kerberos":
      return KERBEROS;
    case "maprsasl":
      return MAPRSASL;
    default:
      throw new RuntimeException(String.format("Unknown authentication type : %s", authType));
    }
  }
}
