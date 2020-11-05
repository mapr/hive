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

package org.apache.hive.common.util;

import com.google.common.annotations.VisibleForTesting;
import com.mapr.login.MapRLoginException;
import com.mapr.security.JNISecurity;
import com.mapr.security.MutableInt;
import com.mapr.security.Security;
import com.mapr.fs.proto.Security.TicketAndKey;
import com.mapr.fs.proto.Security.ServerKeyType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.TimeZone;

import static com.mapr.fs.proto.Security.ServerKeyType.CldbKey;
import static com.mapr.fs.proto.Security.ServerKeyType.ServerKeyTypeMax;

/**
 * Utility class for reading MapR Ticket information.
 */
public final class MapRTicketUtil {
  private static final Logger LOG = LoggerFactory.getLogger(MapRTicketUtil.class);

  private MapRTicketUtil() {
  }

  /**
   * Validates current ticket expiration date.
   */

  public static void validateExpiryTime() {
    if (isExpired()) {
      throw new MapRTicketValidationException(buildExpirationMessage());
    }
  }

  /**
   * Returns true if MapR ticked for current user is expired.
   *
   * @return true if MapR ticked for current user is expired.
   */
  private static boolean isExpired() {
    Date today = Calendar.getInstance().getTime();
    long expiryTime = getCurrentUserTicketExpiryTime();
    return today.getTime() > expiryTime;
  }

  /**
   * Builds message with information on expired MapR ticket.
   *
   * @return message with information on expired MapR ticket.
   */
  private static String buildExpirationMessage() {
    final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    final TimeZone timeZone = Calendar.getInstance().getTimeZone();
    sdf.setTimeZone(timeZone);

    long expiryTime = getCurrentUserTicketExpiryTime();
    String userName = getCurrentUserNameFromTicket();
    String cluster = getCluster();
    return String.format(
        "ERROR: Unable to obtain MapR credentials. Found ticket for cluster '%s' user '%s' but it has expired."
            + " Expiration date is %s %s.", cluster, userName, sdf.format(new Date(expiryTime)),
        timeZone.getDisplayName());
  }

  /**
   * Verifies if CanImpersonate = true. Throws MapRTicketValidationException if CanImpersonate = false for MapR ticket.
   */
  public static void validateCanImpersonate() {
    String userName = getCurrentUserNameFromTicket();
    if (!getCurrentUserTicketCanImpersonate()) {
      throw new MapRTicketValidationException(String.format(
          "ERROR: MapR ticket for user '%s' configured so CanImpersonate = false. Renew current user MapR ticket and set CanImpersonate = true.",
          userName));
    }
  }

  /**
   * Returns current cluster name.
   *
   * @return current cluster name.
   */
  private static String getCluster() {
    try {
      return String.join(",", getClusterSet(getMapRTicketLocation()));
    } catch (IOException e) {
      throw new MapRTicketValidationException(e);
    }
  }

  /**
   * Returns user name set in MapR ticket.
   *
   * @return user name set in MapR ticket.
   */
  private static String getCurrentUserNameFromTicket() {
    String userName = "";
    try {
      TicketAndKey ticketAndKey = getCurrentUserTicket();
      if (ticketAndKey != null && ticketAndKey.getUserCreds() != null) {
        userName = ticketAndKey.getUserCreds().getUserName();
      }
    } catch (IOException e) {
      throw new MapRTicketValidationException(e);
    }
    return userName;
  }

  /**
   * Gets expiry time for current MapR ticket in milliseconds.
   *
   * @return expiry time for current MapR ticket in milliseconds.
   */
  private static long getCurrentUserTicketExpiryTime() {
    try {
      TicketAndKey currentUserTicket = getCurrentUserTicket();
      if (currentUserTicket != null) {
        return currentUserTicket.getExpiryTime() * 1000; // converting to milliseconds.
      } else {
        throw new MapRTicketValidationException("Current user MapR ticket is not available. Use maprlogin password.");
      }
    } catch (IOException e) {
      throw new MapRTicketValidationException(e);
    }
  }

  /**
   * Checks if impersonation is enabled in MapR ticket.
   *
   * @return true if impersonation is enabled in MapR ticket.
   */
  private static boolean getCurrentUserTicketCanImpersonate() {
    try {
      TicketAndKey currentUserTicket = getCurrentUserTicket();
      if (currentUserTicket != null) {
        return currentUserTicket.getCanUserImpersonate();
      } else {
        throw new MapRTicketValidationException("Current user MapR ticket is not available. Use maprlogin password.");
      }
    } catch (IOException e) {
      throw new MapRTicketValidationException(e);
    }
  }

  /**
   * Finds MapR ticket for current user.
   *
   * @return TicketAndKey instance with MapR ticket.
   * @throws IOException when empty ticket file or problem reading ticket file
   */
  private static TicketAndKey getCurrentUserTicket() throws IOException {
    for (TicketAndKey ticketAndKey : getTicketAndKeys(getClusterSet(getMapRTicketLocation()))) {
      if (ticketAndKey.hasUserCreds() && ticketAndKey.getUserCreds() != null) {
        String userName = ticketAndKey.getUserCreds().getUserName();
        if (userName != null) {
          return ticketAndKey;
        }
      }
    }
    return null;
  }

  /**
   * Finds set of MapR tickets for current cluster and current user.
   * @param clusterSet set of clusters where to search MapR tickets.
   * @return set of MapR tickets for current cluster and current user.
   */
  private static Set<TicketAndKey> getTicketAndKeys(Set<String> clusterSet) {
    Set<TicketAndKey> ticketAndKeys = new HashSet<>();
    MutableInt err = new MutableInt();
    for (String cluster : clusterSet) {
      for (int i = CldbKey.getNumber(); i <= ServerKeyTypeMax.getNumber(); i++) {
        ServerKeyType sKType = ServerKeyType.valueOf(i);
        TicketAndKey tk = Security.GetTicketAndKeyForCluster(sKType, cluster, err);
        if (tk == null) {
          continue;
        }
        ticketAndKeys.add(tk);
      }
    }
    return ticketAndKeys;
  }

  /**
   * Finds set of clusters where MapR ticket is available.
   *
   * @param file MapR ticket location
   * @return set of clusters where MapR ticket is available
   * @throws IOException when empty ticket file or problem reading ticket file
   */
  @VisibleForTesting
  public static Set<String> getClusterSet(File file) throws IOException {
    Set<String> clusterSet = new HashSet<>();
    try (FileInputStream inRaw = new FileInputStream(file); BufferedReader in = new BufferedReader(
        new InputStreamReader(inRaw, StandardCharsets.UTF_8))) {
      String line = in.readLine();
      if (line == null) {
        throw new MapRLoginException("Empty ticket file");
      }
      while (line != null) {
        String cluster = line.split(" ")[0];
        clusterSet.add(cluster);
        LOG.info("Found cluster {}", cluster);
        line = in.readLine();
      }
    }
    return clusterSet;
  }

  /**
   * Finds location of MapR ticket for current user.
   *
   * @return location of MapR ticket for current user.
   * @throws MapRLoginException when keyfile is not found or priblem reading keyfile.
   */
  private static File getMapRTicketLocation() throws MapRLoginException {
    String location = JNISecurity.GetUserTicketAndKeyFileLocation();
    LOG.info("Opening keyfile {}", location);
    File file = new File(location);
    if (!file.exists()) {
      throw new MapRLoginException("keyfile not found");
    }
    int errno = Security.SetTicketAndKeyFile(location);
    if (errno != 0) {
      throw new MapRLoginException("Problem reading keyfile, error = " + errno);
    }
    return new File(location);
  }
}
