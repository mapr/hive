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

MAPR_HOME="${BASEMAPR:-/opt/mapr}"
HIVE_VERSION_FILE="$MAPR_HOME/hive/hiveversion"
HIVE_VERSION=$(cat "$HIVE_VERSION_FILE")
HIVE_HOME="$MAPR_HOME/hive/hive-$HIVE_VERSION"

THISSERVICE=metastore
export SERVICE_LIST="${SERVICE_LIST}${THISSERVICE} "

metastore() {
  echo "$(timestamp): Starting Hive Metastore Server"
  CLASS=org.apache.hadoop.hive.metastore.HiveMetaStore
  if $cygwin; then
    HIVE_LIB=`cygpath -w "$HIVE_LIB"`
  fi
  JAR=${HIVE_LIB}/hive-metastore-*.jar

  # hadoop 20 or newer - skip the aux_jars option and hiveconf

  export HADOOP_CLIENT_OPTS=" -Dproc_metastore $HADOOP_CLIENT_OPTS "
  export HADOOP_OPTS="$HIVE_METASTORE_HADOOP_OPTS $HADOOP_OPTS"
  export HADOOP_OPTS="$HADOOP_OPTS ${MAPR_HIVE_SERVER_LOGIN_OPTS}"
  exec $HADOOP jar $JAR $CLASS "$@"
}

metastore_help() {
  metastore -h
}

timestamp()
{
 date +"%Y-%m-%d %T"
}

set_mode_flag() {
  # Path to the hive-site.xml file
  local hive_site_xml="${HIVE_HOME}/conf/hive-site.xml"

  # Check if the file exists
  if [[ ! -f "$hive_site_xml" ]]; then
    echo "Error: hive-site.xml file not found at $hive_site_xml"
  fi

  # Extract the value of the ConnectionURL property
  local connection_url
  connection_url=$(grep -A1 "<name>javax.jdo.option.ConnectionURL</name>" "$hive_site_xml" | grep -oP '(?<=<value>).*?(?=</value>)')

  if [[ -z "$connection_url" ]]; then
    echo "Error: ConnectionURL property not found or empty in $hive_site_xml"
  elif [[ "$connection_url" == *":derby:"* ]]; then
    maprcli insight cluster -trialmode true -json
  else
    maprcli insight cluster -trialmode false -json
  fi
}

metastore_start() {
log=$HIVE_LOG_DIR/hive-$HIVE_IDENT_STRING-metastore-$HOSTNAME.out
pid=$HIVE_PID_DIR/hive-$HIVE_IDENT_STRING-metastore.pid

  if [ -f $pid ]; then
    if kill -0 `cat $pid` > /dev/null 2>&1; then
      echo metastore running as process `cat $pid`.  Stop it first.
      exit 1
    fi
  fi

  mkdir -p "$HIVE_LOG_DIR"
  mkdir -p "$HIVE_PID_DIR"

#  echo starting metastore, logging to $log
  echo "Starting Hive Metastore Server, logging to $log"
  CLASS=org.apache.hadoop.hive.metastore.HiveMetaStore
  if $cygwin; then
    HIVE_LIB=`cygpath -w "$HIVE_LIB"`
  fi
  JAR=${HIVE_LIB}/hive-service-[0-9].*.jar

  # hadoop 20 or newer - skip the aux_jars option and hiveconf

  export HADOOP_OPTS="$HIVE_METASTORE_HADOOP_OPTS $HADOOP_OPTS"
  if [ "$MAPR_HIVE_SERVER_LOGIN_OPTS" = "" ]; then
    export HADOOP_OPTS="$HADOOP_OPTS ${MAPR_ECOSYSTEM_LOGIN_OPTS}"
  else
    export HADOOP_OPTS="$HADOOP_OPTS ${MAPR_HIVE_SERVER_LOGIN_OPTS}"
  fi

  # setting mode flag
  set_mode_flag

  ln -sf $pid ${BASEMAPR}/pid
  nohup $HADOOP jar $JAR $CLASS "$@" >> "$log" 2>&1 < /dev/null &
  echo $! > $pid
  echo "`date` metastore started, pid `cat $pid`" >> "$log" 2>&1 < /dev/null
}

metastore_stop() {
log=$HIVE_LOG_DIR/hive-$HIVE_IDENT_STRING-metastore-$HOSTNAME.out
pid=$HIVE_PID_DIR/hive-$HIVE_IDENT_STRING-metastore.pid
  if [ -f $pid ]; then
    if kill -0 `cat $pid` > /dev/null 2>&1; then
      echo stopping metastore
      kill `cat $pid`
      echo "`date` metastore stopped, pid `cat $pid`" >> "$log" 2>&1 < /dev/null
      rm  ${BASEMAPR}/pid/hive-$HIVE_IDENT_STRING-metastore.pid
      rm $pid
    else
      echo no metastore to stop
    fi
  else
    echo no metastore to stop
  fi
}

metastore_status() {
log=$HIVE_LOG_DIR/hive-$HIVE_IDENT_STRING-metastore-$HOSTNAME.out
pid=$HIVE_PID_DIR/hive-$HIVE_IDENT_STRING-metastore.pid
  if [ -f $pid ]; then
    if kill -0 `cat $pid`; then
      echo metastore running as process `cat $pid`.
      exit 0
    fi
    echo $pid exists with pid `cat $pid` but no metastore.
    exit 1
  fi
  echo metastore not running.
  exit 1
}