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

execHiveCmd () {
  CLASS=$1;
  shift;

  # cli specific code
  if [ ! -f ${HIVE_LIB}/hive-cli-*.jar ]; then
    echo "Missing Hive CLI Jar"
    exit 3;
  fi

  if $cygwin; then
    HIVE_LIB=`cygpath -w "$HIVE_LIB"`
  fi

  # hadoop 20 or newer - skip the aux_jars option. picked up from hiveconf
  exec $HADOOP jar ${HIVE_LIB}/hive-cli-*.jar $CLASS $HIVE_OPTS "$@"
}

setMaprHadoopOpts () {
  LOGIN_TYPE=$1;

  if [ "$LOGIN_TYPE" = "KERBEROS" ]
  then
    export HADOOP_OPTS="$HADOOP_OPTS ${KERBEROS_LOGIN_OPTS}"
  elif [ "$LOGIN_TYPE" = "HYBRID" ]
  then
    export HADOOP_OPTS="$HADOOP_OPTS ${HYBRID_LOGIN_OPTS}"
  elif [ "$LOGIN_TYPE" = "MAPRSASL" ]
  then
    export HADOOP_OPTS="$HADOOP_OPTS ${MAPR_LOGIN_OPTS}"
  else
    export HADOOP_OPTS="$HADOOP_OPTS ${SIMPLE_LOGIN_OPTS}"
  fi
}
