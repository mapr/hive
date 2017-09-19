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
HIVE_VERSION=$(cat "$MAPR_HOME"/hive/hiveversion)
HIVE_HOME="$MAPR_HOME"/hive/hive-"$HIVE_VERSION"
HIVE_BIN="$HIVE_HOME"/bin
HIVE_LIB="$HIVE_HOME"/lib
HIVE_CONF="$HIVE_HOME"/conf
HIVE_SITE="$HIVE_CONF"/hive-site.xml
HIVE_CONFIG_TOOL_MAIN_CLASS="org.apache.hive.conftool.ConfCli"
RESTART_DIR="$MAPR_HOME/conf/restart"
NOW=$(date "+%Y%m%d_%H%M%S")
declare -A MAPRCLI=( ["hivemetastore"]="hivemeta" ["hiveserver2"]="hs2" ["hivewebhcat"]="hcat")
declare -A PORTS=( ["hivemetastore"]="9083" ["hiveserver2"]="10000" ["hivewebhcat"]="50111")

if [ "$HADOOP_CLASSPATH" != "" ]; then
  HADOOP_CLASSPATH="$HIVE_LIB/*:${HADOOP_CLASSPATH}"
else
  HADOOP_CLASSPATH="$HIVE_LIB/*":$(hadoop classpath)
fi

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
# Check that port is available
#
check_port(){
ROLE="$1"
if checkNetworkPortAvailability "${PORTS[$ROLE]}" 2>/dev/null; then
  { set +x; } 2>/dev/null
else
  { set +x; } 2>/dev/null
  logError "Port ${PORTS[$ROLE]} is busy"
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
# Copy warden file depending on the role
#
copy_warden_files(){
ROLE=$1
mkdir -p "$MAPR_HOME"/conf/conf.d
if [ -f "$HIVE_HOME/conf/warden.${MAPRCLI[$ROLE]}.conf" ]; then
  cp -Rp "$HIVE_HOME/conf/warden.${MAPRCLI[$ROLE]}.conf" "$MAPR_HOME"/conf/conf.d/.
fi
}

#
# Create restart file for role
#
create_restart_file(){
ROLE=$1
mkdir -p "$RESTART_DIR"
echo "maprcli node services -action restart -name ${MAPRCLI[$ROLE]} -nodes $(hostname)" > "$RESTART_DIR/$ROLE-$HIVE_VERSION.restart"
}

#
# Configure Hive roles
#
configure_roles(){
ROLES=(hivemetastore hiveserver2 hivewebhcat)
for ROLE in "${ROLES[@]}"; do
  if hasRole "${ROLE}"; then
    check_port "${ROLE}"
    copy_warden_files "${ROLE}"
    create_restart_file "${ROLE}"
  fi
done
}

#
# Removes file $HIVE_HOME/conf/.not_configured_yet after first run of Hive configure.sh
#
remove_fresh_install_indicator(){
if [ -f "$HIVE_HOME/conf/.not_configured_yet" ]; then
    rm -f "$HIVE_HOME/conf/.not_configured_yet"
fi
}

#
# Trim string
#
trim(){
STRING=$1
echo "$(echo -e "${STRING}" | sed -e 's/^[[:space:]]*//' -e 's/[[:space:]]*$//')"
}

#
# main
#
# typically called from core configure.sh
#

USAGE="Usage: $0 [options]
where options include:
    --secure|-s           configure MapR-SASL security cluster
    --unsecure|-u         configure MapR unsecure cluster
    --customSecure|-cs    keep existing security configuration
    --help|-h             print help
    -EC                   unused option. Added for compatibility purpose
    -R                    unused option. Added for compatibility purpose
    -OT [comma separated open Tsdb Server List]
                          unused option. Added for compatibility purpose"

while [ $# -gt 0 ]; do
  OPTION=$(trim $1)
  case "$OPTION" in
    --secure|-s)
    isSecure="true"
    shift
    ;;
    --customSecure|-cs)
    if [ -f "$HIVE_HOME/conf/.not_configured_yet" ]; then
      # If the file exist and our configure.sh is passed --customSecure, then we need to
      # translate this to doing what we normally do for --secure (best we can do)
      isSecure="true"
    else
      isSecure="custom"
    fi
    shift
    ;;
    --unsecure|-u)
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
    -R)
    shift
    ;;
    -OT)
    shift;
    ;;
    *)
    shift
    ;;
  esac
done

backup_configuration

save_security_flag

update_hive_site_xml "$HIVE_SITE" "$isSecure"

configure_roles

remove_fresh_install_indicator