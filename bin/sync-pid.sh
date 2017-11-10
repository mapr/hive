#!/usr/bin/env bash

# Licensed to the Apache Software Foundation (ASF) under one or more
# contributor license agreements.  See the NOTICE file distributed with
# this work for additional information regarding copyright ownership.
# The ASF licenses this file to You under the Apache License, Version 2.0
# (the "License"); you may not use this file except in compliance with
# the License.  You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.


#
# We assume here that:
#
#  1. $HIVE_PID_DIR and $HIVE_IDENT_STRING have valid values
#  2. There is only one HS2 and Metastore on a host
#  3. Process that starts sync-pid.sh has permissions to write PID file in $HIVE_PID_DIR
#

sync_daemon_pid(){
  DAEMON_REAL_PID=$(pgrep -af "$1" | grep -v grep | awk '{print $1}')
  DAEMON_PID_FILE=$2
  DAEMON_PID_FROM_FILE=""
  if [ -f "$DAEMON_PID_FILE" ] ; then
    DAEMON_PID_FROM_FILE=$(cat "$DAEMON_PID_FILE")
  fi

  #
  # No daemon but PID file exists
  #
  if [ -z "$DAEMON_REAL_PID" ]  && [ -n "$DAEMON_PID_FROM_FILE" ] ; then
    rm "$DAEMON_PID_FILE"
  fi

  #
  # No PID file, but daemon is running
  #
  if [ -z "$DAEMON_PID_FROM_FILE" ] && [ -n "$DAEMON_REAL_PID" ] ; then
    echo "$DAEMON_REAL_PID" > "$DAEMON_PID_FILE"
  fi

  #
  # PID files exists and daemon is running, but they are not equal
  #
  if [ -n "$DAEMON_PID_FROM_FILE" ] && [ -n "$DAEMON_REAL_PID" ] ; then
    NUM_LINES=$(wc -l < "$DAEMON_PID_FILE")
    if [ "$NUM_LINES" = 1 ]; then
      if [ "$DAEMON_PID_FROM_FILE" -ne "$DAEMON_REAL_PID" ] ; then
        echo "$DAEMON_REAL_PID" > "$DAEMON_PID_FILE"
      fi
    else
      echo "$DAEMON_REAL_PID" > "$DAEMON_PID_FILE"
    fi
  fi
}

#
# HiveServer2 PID synchronization
#
HS2_PID_NAME="org.apache.hive.service.server.HiveServer2"
HS2_PID_FILE="$HIVE_PID_DIR"/hive-$HIVE_IDENT_STRING-hiveserver2.pid

sync_daemon_pid "$HS2_PID_NAME" "$HS2_PID_FILE"

#
# Metastore PID synchronization
#
METASTORE_PID_NAME="org.apache.hadoop.hive.metastore.HiveMetaStore"
METASTORE_PID_FILE="$HIVE_PID_DIR"/hive-$HIVE_IDENT_STRING-metastore.pid

sync_daemon_pid "$METASTORE_PID_NAME" "$METASTORE_PID_FILE"

#
# WebHCat PID synchronization
#
WEBHCAT_PID_NAME="org.apache.hive.hcatalog.templeton.Main"
WEBHCAT_PID_FILE="$HIVE_PID_DIR"/webhcat/webhcat.pid

sync_daemon_pid "$WEBHCAT_PID_NAME" "$WEBHCAT_PID_FILE"