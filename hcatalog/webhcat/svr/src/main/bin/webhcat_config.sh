#!/usr/bin/env bash

# Licensed to the Apache Software Foundation (ASF) under one
# or more contributor license agreements.  See the NOTICE file
# distributed with this work for additional information
# regarding copyright ownership.  The ASF licenses this file
# to you under the Apache License, Version 2.0 (the
# "License"); you may not use this file except in compliance
# with the License.  You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing,
# software distributed under the License is distributed on an
# "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
# KIND, either express or implied.  See the License for the
# specific language governing permissions and limitations
# under the License.


# Print an error message and exit
function die() {
        echo "${this}: $@" 1>&2
        exit 1
}

#====================================
#Default config param values
#====================================

# The directory,file containing the running pid
PID_DIR=${WEBHCAT_PID_DIR:-/opt/mapr/hive/hive-2.3/pids/webhcat}
PID_FILE=${PID_DIR}/webhcat.pid
mkdir -p $PID_DIR 2>/dev/null

#default log directory
WEBHCAT_LOG_DIR=${WEBHCAT_LOG_DIR:-/opt/mapr/hive/hive-2.3/logs/`id -u -n`/webhcat}

# The console error log
ERROR_LOG=${WEBHCAT_LOG_DIR}/webhcat-console-error.log

# The console log
CONSOLE_LOG=${WEBHCAT_LOG_DIR}/webhcat-console.log

# The name of the webhcat jar file
WEBHCAT_JAR='hive-webhcat-*.jar'

# How long to wait before testing that the process started correctly
SLEEP_TIME_AFTER_START=10

#================================================
#See if the default configs have been overwritten
#================================================

#These parameters can be overriden by webhcat-env.sh
# the root of the WEBHCAT installation  ('this' is defined in webhcat_server.sh)
export WEBHCAT_PREFIX=`dirname "$this"`/..
export HCAT_PREFIX=${WEBHCAT_PREFIX}
export HIVE_DIR=${HCAT_PREFIX}"/.."
export HIVE_HOME="${HIVE_HOME:-$HIVE_DIR}"
export HIVE_CONF_DIR="${HIVE_HOME}/conf"

#check to see if the conf dir is given as an optional argument
if [ $# -gt 1 ]
then
    if [ "--config" = "$1" ]
          then
              shift
              confdir=$1
              shift
              WEBHCAT_CONF_DIR=$confdir
    fi
fi

# Allow alternate conf dir location.
if [ -e "${WEBHCAT_PREFIX}/etc/webhcat/webhcat-env.sh" -o -e "${WEBHCAT_PREFIX}/etc/webhcat/webhcat-site.xml" ]; then
  DEFAULT_CONF_DIR=${WEBHCAT_PREFIX}/"etc/webhcat"
elif [ -e "${WEBHCAT_PREFIX}/conf/webhcat-env.sh" -o -e "${WEBHCAT_PREFIX}/etc/webhcat/webhcat-site.xml" ]; then
  DEFAULT_CONF_DIR=${WEBHCAT_PREFIX}/"conf"
else
  DEFAULT_CONF_DIR="/etc/webhcat"
fi
export WEBHCAT_CONF_DIR="${WEBHCAT_CONF_DIR:-$DEFAULT_CONF_DIR}"

#users can add various env vars to webhcat-env.sh in the conf
#rather than having to export them before running the command
if [ -f "${WEBHCAT_CONF_DIR}/webhcat-env.sh" ]; then
  source "${WEBHCAT_CONF_DIR}/webhcat-env.sh"
fi

#set defaults for HCAT_PREFIX, HIVE_HOME, TEMPLETON_HOME that work for default directory structure
DEFAULT_HCAT_PREFIX="${WEBHCAT_PREFIX}"
export HCAT_PREFIX="${HCAT_PREFIX:-$DEFAULT_HCAT_PREFIX}"
if [ ! -f ${HCAT_PREFIX}/bin/hcat ]; then
    die "HCAT_PREFIX=${HCAT_PREFIX} is invalid";
fi

#hcat script can sometimes determine HIVE_HOME itslef
#so HIVE_HOME does not need to be always set at this point
DEFAULT_HIVE_HOME="${WEBHCAT_PREFIX}/.."
if [ -n "$HIVE_HOME" ]; then
    echo "Lenght of string is non zero"
    if  [ ! -f ${HIVE_HOME}/bin/hive ]; then
        die "HIVE_HOME=${HIVE_HOME} is invalid";
    fi
elif [ -f ${DEFAULT_HIVE_HOME}/bin/hive ]; then
    export HIVE_HOME="${HIVE_HOME:-$DEFAULT_HIVE_HOME}"
    echo "Setting HIVE_HOME $HIVE_HOME"
fi



DEFAULT_TEMPLETON_HOME="${WEBHCAT_PREFIX}"
export TEMPLETON_HOME="${TEMPLETON_HOME:-$DEFAULT_TEMPLETON_HOME}"
if [ ! -d ${TEMPLETON_HOME}/share/webhcat ]; then
    die "TEMPLETON_HOME=${TEMPLETON_HOME} is invalid";
fi

source $WEBHCAT_PREFIX/bin/common.sh
find_hadoop_home

#====================================
#determine where hadoop is
#====================================

#check HADOOP_HOME and then check HADOOP_PREFIX
if [ -f ${HADOOP_HOME}/bin/hadoop ]; then
  HADOOP_PREFIX=$HADOOP_HOME
#if this is an rpm install check for /usr/bin/hadoop
elif [ -f ${WEBHCAT_PREFIX}/bin/hadoop ]; then
  HADOOP_PREFIX=$WEBHCAT_PREFIX
#otherwise see if HADOOP_PREFIX is defined
elif [ ! -f ${HADOOP_PREFIX}/bin/hadoop ]; then
  echo "${this}: Hadoop not found."
  exit 1
fi
export BASEMAPR=${MAPR_HOME:-/opt/mapr}

#get the yarn version
if [ -f $BASEMAPR/conf/hadoop_version ];then
   MAPR_YARN_VERSION=`cat $BASEMAPR/conf/hadoop_version | grep yarn_version | awk -F'=' '{print $2}'`
   export HADOOP_PREFIX=$BASEMAPR/hadoop/hadoop-$MAPR_YARN_VERSION
   export HADOOP_CONFIG_DIR=$HADOOP_PREFIX/etc/hadoop
else
   export HADOOP_PREFIX=$HADOOP_HOME
   export HADOOP_CONFIG_DIR=$HADOOP_PREFIX/conf
fi

env=${BASEMAPR}/conf/env.sh
[ -f $env ] && . $env
export HADOOP_OPTS="$HADOOP_OPTS ${MAPR_HIVE_SERVER_LOGIN_OPTS}"
if [ "$MAPR_SECURITY_STATUS" = "true" ]; then
  HADOOP_OPTS="$HADOOP_OPTS -Dmapr_sec_enabled=true"
else
  HADOOP_OPTS="$HADOOP_OPTS -Dmapr_sec_enabled=false"
fi