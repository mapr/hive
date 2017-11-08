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

THISSERVICE=hiveserver2
export SERVICE_LIST="${SERVICE_LIST}${THISSERVICE} "

hiveserver2() {
  >&2 echo "$(timestamp): Starting HiveServer2"
  CLASS=org.apache.hive.service.server.HiveServer2
  if $cygwin; then
    HIVE_LIB=`cygpath -w "$HIVE_LIB"`
  fi
  JAR=${HIVE_LIB}/hive-service-[0-9].*.jar
  export HADOOP_OPTS="$HADOOP_OPTS ${MAPR_HIVE_SERVER_LOGIN_OPTS}"

  export HADOOP_CLIENT_OPTS=" -Dproc_hiveserver2 $HADOOP_CLIENT_OPTS "
  export HADOOP_OPTS="$HIVESERVER2_HADOOP_OPTS $HADOOP_OPTS"
  exec $HADOOP jar $JAR $CLASS $HIVE_OPTS "$@"
}

hiveserver2_help() {
  hiveserver2 -H
}

timestamp()
{
 date +"%Y-%m-%d %T"
}

hiveserver2_start() {
log=$HIVE_LOG_DIR/hive-$HIVE_IDENT_STRING-hiveserver2-$HOSTNAME.out
pid=$HIVE_PID_DIR/hive-$HIVE_IDENT_STRING-hiveserver2.pid

  if [ -f $pid ]; then
    if kill -0 `cat $pid` > /dev/null 2>&1; then
      echo hiveserver2 running as process `cat $pid`.  Stop it first.
      exit 1
    fi
  fi

  mkdir -p "$HIVE_LOG_DIR"
  mkdir -p "$HIVE_PID_DIR"
  echo "hiveserver2_start()"
  CLASS=org.apache.hive.service.server.HiveServer2
  if $cygwin; then
    HIVE_LIB=`cygpath -w "$HIVE_LIB"`
  fi
  JAR=${HIVE_LIB}/hive-service-[0-9].*.jar

  if [ "$MAPR_HIVE_SERVER_LOGIN_OPTS" = "" ]; then
    export HADOOP_OPTS="$HADOOP_OPTS ${MAPR_ECOSYSTEM_LOGIN_OPTS}"
  else
    export HADOOP_OPTS="$HADOOP_OPTS ${MAPR_HIVE_SERVER_LOGIN_OPTS}"
  fi

  ln -sf $pid ${BASEMAPR}/pid
  nohup $HADOOP jar $JAR $CLASS "$@" >> "$log" 2>&1 < /dev/null &
  echo $! > $pid
  echo "`date` hiveserver2 started, pid `cat $pid`" >> "$log" 2>&1 < /dev/null
}

hiveserver2_stop() {
log=$HIVE_LOG_DIR/hive-$HIVE_IDENT_STRING-hiveserver2-$HOSTNAME.out
pid=$HIVE_PID_DIR/hive-$HIVE_IDENT_STRING-hiveserver2.pid
  if [ -f $pid ]; then
    if kill -0 `cat $pid` > /dev/null 2>&1; then
      echo stopping hiveserver2
      kill `cat $pid`
      echo "`date` hiveserver2 stopped, pid `cat $pid`" >> "$log" 2>&1 < /dev/null
      rm ${BASEMAPR}/pid/hive-$HIVE_IDENT_STRING-hiveserver2.pid
      rm $pid
    else
      echo no hiveserver2 to stop
    fi
  else
    echo no hiveserver2 to stop
  fi
}

hiveserver2_status() {
log=$HIVE_LOG_DIR/hive-$HIVE_IDENT_STRING-hiveserver2-$HOSTNAME.out
pid=$HIVE_PID_DIR/hive-$HIVE_IDENT_STRING-hiveserver2.pid
  if [ -f $pid ]; then
    if kill -0 `cat $pid` > /dev/null 2>&1; then
      echo hiveserver2 running as process `cat $pid`.
      exit 0
    fi
    echo $pid exists with pid `cat $pid` but no hiveserver2.
    exit 1
  fi
  echo hiveserver2 not running.
  exit 1
}

