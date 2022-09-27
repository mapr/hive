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
 * ConfToolUtil class for configuration tool.
 */
public final class ConfToolUtil {
  private ConfToolUtil() {
  }

  /**
   * Get path of file in resources.
   *
   * @param fileName file in resources
   * @return path of file in resources
   */
  static String getPath(String fileName) {
    return Thread.currentThread().getContextClassLoader().getResource(fileName).toExternalForm();
  }
  
  /**
   * Finds document transformer file location.
   *
   * @return transformer file location from conf dir
   */
  static String getTransformerLocation() {
    return getPath("docTransformer.xslt");
  }
}
