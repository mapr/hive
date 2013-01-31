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

THISSERVICE="metastore start_metastore stop_metastore status_metastore "
export SERVICE_LIST="${SERVICE_LIST}${THISSERVICE} "

metastore() {
  echo "Starting Hive Metastore Server"
  CLASS=org.apache.hadoop.hive.metastore.HiveMetaStore
  if $cygwin; then
    HIVE_LIB=`cygpath -w "$HIVE_LIB"`
  fi
  JAR=${HIVE_LIB}/hive-service-*.jar

  # hadoop 20 or newer - skip the aux_jars option and hiveconf

  export HADOOP_OPTS="$HIVE_METASTORE_HADOOP_OPTS $HADOOP_OPTS"
  exec $HADOOP jar $JAR $CLASS "$@"
}

HIVE_METASTORE_LOG="${HIVE_LOG_DIR}/hive-${USER}-metastore.log"
HIVE_METASTORE_PID="${HIVE_PID_DIR}/hive-${USER}-metastore.pid"

start_metastore() {
  if [ -f $HIVE_METASTORE_PID ]; then
    if kill -0 `cat $HIVE_METASTORE_PID` > /dev/null 2>&1; then
      echo Hive Metastore Server is running as process `cat $HIVE_METASTORE_PID`.  Stop it first.
      exit 1
    fi
  fi
  echo "`date` Starting Hive Metastore Server"
  # create dirs first
  mkdir -p ${HIVE_LOG_DIR} 
  mkdir -p ${HIVE_PID_DIR}
  CLASS=org.apache.hadoop.hive.metastore.HiveMetaStore
  if $cygwin; then
    HIVE_LIB=`cygpath -w "$HIVE_LIB"`
  fi
  JAR=${HIVE_LIB}/hive-service-*.jar
  # rotate logs 
  hive_rotate_log $HIVE_METASTORE_LOG
  # change max heapsize to 1GB
  export HADOOP_HEAPSIZE="1024" 
  # hadoop 20 or newer - skip the aux_jars option and hiveconf
  exec nohup $HADOOP jar $JAR $CLASS "$@" >> $HIVE_METASTORE_LOG 2>&1 &
  pid=$!
  echo "pid is $pid"
  echo $pid > $HIVE_METASTORE_PID
}

stop_metastore() {
  if [ -f "$HIVE_METASTORE_PID" ]; then
    if kill -0 `cat $HIVE_METASTORE_PID` > /dev/null 2>&1; then
      echo "Stopping Hive Metastore Server"
      kill -9 `cat $HIVE_METASTORE_PID`
      echo "`date` Hive Metastore Server stopped, pid `cat $HIVE_METASTORE_PID`"
    else
      echo "Hive Metastore Server is not running"
    fi
  else
    echo "Hive Metastore Server is not running. pid file $HIVE_METASTORE_PID does not exist."
  fi
}

status_metastore() {
  if [ -f "$HIVE_METASTORE_PID" ]; then
    if kill -0 `cat $HIVE_METASTORE_PID` > /dev/null 2>&1; then
      echo Hive Metastore Server running as process `cat $HIVE_METASTORE_PID`.
      exit 0
    fi
    echo "$HIVE_METASTORE_PID exists with pid `cat $HIVE_METASTORE_PID` but no process running."
    exit 1
  fi
  echo "Hive Metastore Server not running."
  exit 1
}

metastore_help() {
  metastore -h
}

