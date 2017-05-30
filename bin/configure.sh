#!/usr/bin/env bash

# Copyright (c) 2009 & onwards. MapR Tech, Inc., All rights reserved

############################################################################
#
#
# Configure script for Hive
#
##
# This script is normally run by the core configure.sh to setup Hive during
# install. If it is run standalone, need to correctly initialize the
# variables that it normally inherits from the master configure.sh
#
############################################################################

MAPR_HOME="${BASEMAPR:-/opt/mapr}"

source "$MAPR_HOME"/server/common-ecosystem.sh 2>/dev/null
{ set +x; } 2>/dev/null

if [ $? -ne 0 ]; then
  logError "MAPR_HOME seems to not be set correctly or mapr-core not installed"
  exit 1
fi

#
# Initialize environment
#
initCfgEnv

#
# Globals
#
HIVE_VERSION="2.1"
HIVE_HOME="$MAPR_HOME"/hive/hive-"$HIVE_VERSION"
HIVE_BIN="$HIVE_HOME"/bin
HIVE_LIB="$HIVE_HOME"/lib
HIVE_CONF="$HIVE_HOME"/conf
HIVE_CONFIG_TOOL_MAIN_CLASS="org.apache.hive.conftool.ConfCli"
HIVE_SITE="$HIVE_HOME"/conf/hive-site.xml
DAEMON_CONF="$MAPR_HOME/conf/daemon.conf"
RESTART_DIR="$MAPR_HOME/conf/restart"
WARDEN_HCAT_CONF="$HIVE_HOME"/conf.new/warden.hcat.conf
WARDEN_HIVEMETA_CONF="$HIVE_HOME"/conf.new/warden.hivemeta.conf
WARDEN_HS2_CONF="$HIVE_HOME"/conf.new/warden.hs2.conf
NOW=$(date "+%Y%m%d_%H%M%S")

if [ "$HADOOP_CLASSPATH" != "" ]; then
  HADOOP_CLASSPATH="$HIVE_LIB/*:${HADOOP_CLASSPATH}"
else
  HADOOP_CLASSPATH="$HIVE_LIB/*":$(hadoop classpath)
fi

#
# Find mapr user and group
#
find_mapr_user_and_group() {
if [ -z "$MAPR_USER" ] ; then
  MAPR_USER=$( awk -F = '$1 == "mapr.daemon.user" { print $2 }' "$DAEMON_CONF")
fi
if [ -z "$MAPR_GROUP" ] ; then
  MAPR_GROUP=$( awk -F = '$1 == "mapr.daemon.group" { print $2 }' "$DAEMON_CONF")
fi

if [ -z "$MAPR_USER" ] ; then
  MAPR_USER=mapr
fi
if [ -z "$MAPR_GROUP" ] ; then
  MAPR_GROUP=mapr
fi
}

#
# Saves security flag to file "$HIVE_BIN"/isSecure
#
save_security_flag() {
# isSecure is set in server/configure.sh
if [ -n "$isSecure" ]; then
  echo "$isSecure" > "$HIVE_BIN"/isSecure
else
  if isSecurityEnabled 2>/dev/null; then
    echo true > "$HIVE_BIN"/isSecure
  else
    echo false > "$HIVE_BIN"/isSecure
  fi
fi
}

update_hive_site_xml(){
HIVE_SITE="$1"
isSecure="$2"

if [ "$isSecure" = "true" ];  then
  java -cp "$HADOOP_CLASSPATH" "$HIVE_CONFIG_TOOL_MAIN_CLASS" -path "$HIVE_SITE" "-secure"
fi

if [ "$isSecure" = "false" ];  then
  java -cp "$HADOOP_CLASSPATH" "$HIVE_CONFIG_TOOL_MAIN_CLASS" -path "$HIVE_SITE" "-unsecure"
fi
}


#
# Configure service log location
#
set_service_log_location() {
SERVICE_CONF_FILE=$1
SERVICE_LOG_LOCATION=$2
sed -i "s|.*service.logs.location=.*|service.logs.location=$SERVICE_LOG_LOCATION|g" "$SERVICE_CONF_FILE"
}

#
# Third party library management
# If slf4j-api-*.jar or slf4j-log4j12-*.jar is present in HIVE_HOME/lib and /opt/mapr/lib,
# delete them from HIVE_HOME/lib
#
delete_lib() {
  LIB_NAME=$1
  if [ -f /opt/mapr/lib/"$LIB_NAME" ] && [ -f "$HIVE_HOME/lib/$LIB_NAME" ]; then
    rm -f "$HIVE_HOME/lib/$LIB_NAME"
  fi
}

#
# Creates links for Hive services
#
create_service_links() {
rm -f  "$MAPR_HOME"/bin/hive
ln -sf "$HIVE_HOME"/bin/hive "$MAPR_HOME"/bin/hive
ln -sf "$HIVE_HOME"/bin/hive /usr/bin/hive

rm -f  "$MAPR_HOME"/bin/hcat
ln -sf "$HIVE_HOME"/hcatalog/bin/hcat "$MAPR_HOME"/bin/hcat
ln -sf "$HIVE_HOME"/hcatalog/bin/hcat /usr/bin/hcat

rm -f  "$MAPR_HOME"/bin/webhcat_server
ln -sf "$HIVE_HOME"/hcatalog/sbin/webhcat_server.sh "$MAPR_HOME"/bin/webhcat_server
ln -sf "$HIVE_HOME"/hcatalog/sbin/webhcat_server.sh /usr/bin/webhcat_server
}

#
# Copy new configuration files from HIVE_HOME/conf.new to HIVE_HOME/conf if and only if they are
# not already present in HIVE_HOME/conf
#
copy_hive_conf_files(){
mkdir -p "$HIVE_HOME"/conf

diffR=$(diff -r "$HIVE_HOME"/conf "$HIVE_HOME"/conf.new | grep "^Only in " | grep "conf.new" | sed "s/^Only in //" | sed "s/: /\//")
for i in ${diffR}; do
  j=$(echo "$i" | sed 's/conf.new/conf/g')
  cp -Rp "$i" "$j"
done
}

#
# Grant owners
#
grant_admin_permissions_to(){
if [ -f "$DAEMON_CONF" ]; then
  if [ ! -z "$MAPR_USER" ]; then
    chown -R "$MAPR_USER" "$1"
  fi
  if [ ! -z "$MAPR_GROUP" ]; then
    chgrp -R "$MAPR_GROUP" "$1"
  fi
fi
}

#
# Configures HCat, HiveMeta and HiveServer2 service locations
#
configure_all_services_log_location(){
if [ -f "$DAEMON_CONF" ]; then
  if [ ! -z "$MAPR_USER" ]; then
    HIVE_MAPR_USER_LOG_DIR="$HIVE_HOME"/logs/"$MAPR_USER"
    set_service_log_location "$WARDEN_HCAT_CONF" "$HIVE_MAPR_USER_LOG_DIR"
    set_service_log_location "$WARDEN_HIVEMETA_CONF" "$HIVE_MAPR_USER_LOG_DIR"
    set_service_log_location "$WARDEN_HS2_CONF" "$HIVE_MAPR_USER_LOG_DIR"
  fi
fi
}

#
# create logs directory and grant permissions
#
create_log_dirs(){
mkdir -p "$HIVE_HOME"/logs
chmod 1777 "$HIVE_HOME"/logs
}



#
# Create hiveversion file
#
create_version_file(){
VERSION_FILE="$1"
echo "$HIVE_VERSION" > "$MAPR_HOME/hive/$VERSION_FILE"
}

#
# Check that port is available
#
check_port(){
PORT="$1"
if checkNetworkPortAvailability "$PORT" 2>/dev/null; then
  { set +x; } 2>/dev/null
else
  { set +x; } 2>/dev/null
  logError "Port $PORT is busy"
fi
}

#
#  Save off current configuration file
#
backup_configuration(){
if [ -d "$HIVE_CONF" ]; then
  cp -pa "$HIVE_CONF" "$HIVE_CONF"."$NOW"
fi
}

#
# Create restart folder if does not exist
#
create_restart_dir(){
if [ ! -d "$RESTART_DIR" ]; then
  mkdir "$RESTART_DIR"
fi
}

#
# main
#
# typically called from core configure.sh
#

USAGE="usage: $0 [--secure|--unsecure|--customSecure|--help]"

if [ ${#} -gt 1 ]; then
  echo "$USAGE"
  return 1 2>/dev/null || exit 1
fi

while [ $# -gt 0 ]; do
  case "$1" in
    --secure)
    isSecure="true"
    shift
    ;;
    --customSecure)
    isSecure="custom"
    shift
    ;;
    --unsecure)
    isSecure="false"
    shift
    ;;
    --help|-h)
    echo "$USAGE"
    return 0 2>/dev/null || exit 0
    ;;
    -EC)
    shift
    ;;
    *)
      echo "$USAGE"
      return 1 2>/dev/null || exit 1
    ;;
  esac
done

create_version_file "hiveversion"

create_restart_dir

backup_configuration

find_mapr_user_and_group

save_security_flag

configure_all_services_log_location

copy_hive_conf_files

update_hive_site_xml "$HIVE_SITE" "$isSecure"

create_service_links

delete_lib "slf4j-api-*.jar"

delete_lib "slf4j-log4j12-*.jar"

create_log_dirs

grant_admin_permissions_to "$HIVE_HOME"