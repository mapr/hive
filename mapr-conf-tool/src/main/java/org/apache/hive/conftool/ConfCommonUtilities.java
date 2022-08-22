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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.attribute.GroupPrincipal;
import java.nio.file.attribute.PosixFileAttributeView;
import java.nio.file.attribute.PosixFileAttributes;
import java.nio.file.attribute.UserPrincipal;
import java.nio.file.attribute.UserPrincipalLookupService;

/**
 * Contains utility methods for configuration processing.
 */
public final class ConfCommonUtilities {
  private ConfCommonUtilities() {
  }

  /**
   * Check if file exists.
   *
   * @param path path to file
   * @return true if file exists.
   */
  public static boolean exists(String path) {
    if (path != null && !path.isEmpty()) {
      return new File(path).exists();
    }
    return false;
  }

  /**
   * Reads file, replaces line that starts with prefix to target line, and saves file.
   *
   * @param path path to file
   * @param prefix prefix to find
   * @param target target to replace
   */
  public static void replaceLine(String path, String prefix, String target) {
    try (BufferedReader file = new BufferedReader(
        new FileReader(path)); FileOutputStream fileOut = new FileOutputStream(path)) {
      StringBuilder sb = new StringBuilder();
      String line;
      while ((line = file.readLine()) != null) {
        if (line.startsWith(prefix)) {
          line = target;
        }
        sb.append(line);
        sb.append('\n');
      }
      // write the new string with the replaced line OVER the same file
      fileOut.write(sb.toString().getBytes());
    } catch (Exception e) {
      throw new HiveHaException(String.format("Error reading file %s", path));
    }
  }

  /**
   * Change group if a given file.
   * Similar as chgrp.
   *
   * @param file path to file
   * @param group group to set
   * @throws IOException
   */
  public static void changeGroup(String file, GroupPrincipal group) throws IOException {
    if (exists(file)) {
      Files.getFileAttributeView(Path.of(file), PosixFileAttributeView.class, LinkOption.NOFOLLOW_LINKS)
          .setGroup(group);
    }
  }

  /**
   * Finds admin group object by its name.
   *
   * @param groupName group name
   * @return group principal
   * @throws IOException
   */
  public static GroupPrincipal findGroupByName(String groupName) throws IOException {
    UserPrincipalLookupService lookupService = FileSystems.getDefault().getUserPrincipalLookupService();
    return lookupService.lookupPrincipalByGroupName(groupName);
  }

  /**
   * Change owner of a given file.
   * Similar to chgrp.
   *
   * @param file path to file
   * @param user owner to set
   * @throws IOException
   */
  public static void changeOwner(String file, UserPrincipal user) throws IOException {
    if (exists(file)) {
      Files.setOwner(Path.of(file), user);
    }
  }

  /**
   * Finds admin user object by its name.
   *
   * @param userName username
   * @return user principal
   * @throws IOException
   */
  public static UserPrincipal findUserByName(String userName) throws IOException {
    UserPrincipalLookupService lookupService = FileSystems.getDefault().getUserPrincipalLookupService();
    return lookupService.lookupPrincipalByName(userName);
  }

  /**
   * Reads group from file.
   *
   * @param file path to file.
   * @return group principal
   * @throws IOException
   */
  public static GroupPrincipal readGroup(String file) throws IOException {
    if (exists(file)) {
      return Files.readAttributes(Path.of(file), PosixFileAttributes.class, LinkOption.NOFOLLOW_LINKS).group();
    }
    return null;
  }

  /**
   * Removes new line symbols from a string.
   *
   * @param input input string
   * @return string without new lines symbols
   */
  public static String normalize(String input) {
    if (input != null) {
      return input.replace("\n", "");
    }
    return "";
  }
}
