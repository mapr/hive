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
  JAR=${HIVE_LIB}/hive-service-*.jar

  # hadoop 20 or newer - skip the aux_jars option and hiveconf

  export HADOOP_OPTS="$HIVE_METASTORE_HADOOP_OPTS $HADOOP_OPTS"
  if [ "$MAPR_HIVE_SERVER_LOGIN_OPTS" = "" ]; then
    export HADOOP_OPTS="$HADOOP_OPTS ${MAPR_ECOSYSTEM_LOGIN_OPTS}"
  else
    export HADOOP_OPTS="$HADOOP_OPTS ${MAPR_HIVE_SERVER_LOGIN_OPTS}"
  fi

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