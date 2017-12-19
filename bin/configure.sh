#!/usr/bin/env bash
#set -x

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
MAPR_ENABLE_LOGS="${MAPR_ENABLE_LOGS:-false}"

configure_logging(){
{ set +x; } 2> /dev/null
if [ "$MAPR_ENABLE_LOGS" == "true" ]; then
  set -x;
fi
}

source "$MAPR_HOME"/server/common-ecosystem.sh 2>/dev/null
configure_logging
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
HIVE_LOGS="$HIVE_HOME"/logs
HIVE_SITE="$HIVE_CONF"/hive-site.xml
HIVE_CONFIG_TOOL_MAIN_CLASS="org.apache.hive.conftool.ConfCli"
RESTART_DIR="${RESTART_DIR:=$MAPR_HOME/conf/restart}"
RESTART_LOG_DIR="${RESTART_LOG_DIR:=${MAPR_HOME}/logs/restart_logs}"
METASTOREWAREHOUSE="/user/hive/warehouse"
ROLES_DIR="$MAPR_HOME/roles"
DERBY_CONNECTION_URL="jdbc:derby:;databaseName=$HIVE_BIN/metastore_db;create=true"
CONNECTION_URL_PROPERTY_NAME="javax.jdo.option.ConnectionURL"
HIVE_METASTORE_URIS_PROPERTY_NAME="hive.metastore.uris"

NOW=$(date "+%Y%m%d_%H%M%S")
DAEMON_CONF="$MAPR_HOME/conf/daemon.conf"
isHS2HA=false
rm_ip=""
tl_ip=""
configChanged=0
beforeHiveSiteCksum=""
afterHiveSiteCksum=""
DEFAULT_DERBY_DB_NAME="metastore_db"
declare -A MAPRCLI=( ["hivemetastore"]="hivemeta" ["hiveserver2"]="hs2" ["hivewebhcat"]="hcat")
declare -A PORTS=( ["hivemetastore"]="9083" ["hiveserver2"]="10000" ["hivewebhcat"]="50111")

if [ "$HADOOP_CLASSPATH" != "" ]; then
  HADOOP_CLASSPATH="$HIVE_LIB/*:${HADOOP_CLASSPATH}"
else
  HADOOP_CLASSPATH="$HIVE_LIB/*":$(hadoop classpath)
fi

#
# Checks if HiveServer2 High Availability flag exists
#
if [ -f "$HIVE_CONF/enable_hs2_ha" ]; then
  isHS2HA=true
fi

#
# Checks if metastore is installed
#
has_metastore(){
if [ -f "$ROLES_DIR/hivemetastore" ]; then
  return 0; # 0 = true
else
  return 1;
fi
}

#
# Checks if webhcat is installed
#
has_webhcat(){
if [ -f "$ROLES_DIR/hivewebhcat" ]; then
  return 0; # 0 = true
else
  return 1;
fi
}


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
# Checks if Hive has been already configured
#
is_hive_not_configured_yet(){
if [ -f "$HIVE_HOME/conf/.not_configured_yet" ]; then
  return 0; # 0 = true
else
  return 1;
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

#
# Grant owners
#
grant_admin_permissions_to(){
if [ ! -z "$MAPR_USER" ]; then
  chown -R "$MAPR_USER" "$1"
fi
if [ ! -z "$MAPR_GROUP" ]; then
  chgrp -R "$MAPR_GROUP" "$1"
fi
}

configure_security(){
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
# Configures PAM an SSl encryption for HiveServer2 Web UI on MapR SASL cluster
#
configure_hs2_webui_pam_and_ssl(){
HIVE_SITE="$1"
isSecure="$2"

if [ "$isSecure" = "true" ];  then
  java -cp "$HADOOP_CLASSPATH" "$HIVE_CONFIG_TOOL_MAIN_CLASS" -path "$HIVE_SITE" "-webuipamssl"
fi
}


#
# Check that port is available
#
check_port(){
ROLE="$1"
if checkNetworkPortAvailability "${PORTS[$ROLE]}" 2>/dev/null; then
  configure_logging
  registerNetworkPort "$ROLE" "${PORTS[$ROLE]}"
  if [ $? -ne 0 ]; then
    logWarn "$ROLE - Failed to register port: ${PORTS[$ROLE]}"
  fi
else
  configure_logging
  service=$(whoHasNetworkPort "${PORTS[$ROLE]}")
  if [ "$service" != "$ROLE" ]; then
    logWarn "$ROLE - port ${PORTS[$ROLE]} in use by $service service"
  fi
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
# Save new hive-site.xml check sum to file
#
save_new_hive_site_check_sum(){
beforeHiveSiteCksum=$(cksum "$HIVE_SITE" | cut -d' ' -f1)
echo "$beforeHiveSiteCksum" > "$HIVE_CONF"/.hive_site_check_sum
}

#
# Read old hive-site.xml check sum from file
#
read_old_hive_site_check_sum(){
if [ -f "$HIVE_CONF"/.hive_site_check_sum ]; then
  echo $(cat "$HIVE_CONF"/.hive_site_check_sum)
else
  echo 0
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
cat <<EOF > "$RESTART_DIR/$ROLE-$HIVE_VERSION.restart"
#!/bin/bash
if ${MAPR_HOME}/initscripts/mapr-warden status > /dev/null 2>&1 ; then
  isSecured=\$(head -1 ${MAPR_HOME}/conf/mapr-clusters.conf | grep -o 'secure=\w*' | cut -d= -f2)
  if [ "\${isSecured}" = "true" ] && [ -f "${MAPR_HOME}/conf/mapruserticket" ]; then
    export MAPR_TICKETFILE_LOCATION="${MAPR_HOME}/conf/mapruserticket"
  fi
  nohup maprcli node services -action restart -name ${MAPRCLI[$ROLE]} -nodes $(hostname) > ${RESTART_LOG_DIR}/$ROLE-$HIVE_VERSION_restart_$(date +%s)_$$.log 2>&1 &
fi
EOF
chmod a+x "$RESTART_DIR/$ROLE-$HIVE_VERSION.restart"
chown "$MAPR_USER":"$MAPR_GROUP" "$RESTART_DIR/$ROLE-$HIVE_VERSION.restart"
}

#
# Grant write permission for group in hive logs dir
#
grant_write_permission_in_logs_dir() {
  chmod -R g+w "$HIVE_LOGS"
}

#
# Copy log4j api lib to hadoop common libs
#
function copy_log4j_for_hadoop_common_classpath() {
  if has_webhcat ; then
    LOG4J_API_JAR_PATH=$(ls ${HIVE_LIB}/log4j-api* | awk '{print $1}')
    LOG4J_API_JAR_NAME=$(basename ${LOG4J_API_JAR_PATH})
    HADOOP_VERSION=$(cat ${MAPR_HOME}/hadoop/hadoopversion | awk -F'=' '{print $1}')
    HADOOP_SHARE_COMMON_PATH=${MAPR_HOME}/hadoop/hadoop-${HADOOP_VERSION}/share/hadoop/common/lib/
    if [ -f "$LOG4J_API_JAR_PATH" ] && [ ! -L "$HADOOP_SHARE_COMMON_PATH/$LOG4J_API_JAR_NAME" ] ; then
      ln -s "$LOG4J_API_JAR_PATH" "$HADOOP_SHARE_COMMON_PATH"
    fi
  fi
}

#
# Create restart file for rm/tl
#
create_rm_tl_restart_file(){
service=$1
ip="$2"
ip=$(echo "$ip" | cut -d',' -f1)
mkdir -p "$RESTART_DIR"
cat <<EOF > "$RESTART_DIR/${service}.restart"
#!/bin/bash
if ${MAPR_HOME}/initscripts/mapr-warden status > /dev/null 2>&1 ; then
  isSecured=\$(head -1 ${MAPR_HOME}/conf/mapr-clusters.conf | grep -o 'secure=\w*' | cut -d= -f2)
  if [ "\${isSecured}" = "true" ] && [ -f "${MAPR_HOME}/conf/mapruserticket" ]; then
    export MAPR_TICKETFILE_LOCATION="${MAPR_HOME}/conf/mapruserticket"
  fi
  nohup maprcli node services -action restart -name $service -nodes $ip > ${RESTART_LOG_DIR}/${service}_restart_$(date +%s)_$$.log 2>&1 &
fi
EOF
chmod a+x "$RESTART_DIR/${service}.restart"
chown "$MAPR_USER":"$MAPR_GROUP" "$RESTART_DIR/${service}.restart"
}

#
# Checks id hive-site.xml is changed
#
is_hive_site_changed(){
beforeHiveSiteCksum=$(read_old_hive_site_check_sum)
afterHiveSiteCksum=$(cksum "$HIVE_SITE" | cut -d' ' -f1)
if [ "$beforeHiveSiteCksum" != "$afterHiveSiteCksum" ]; then
  return 0; # 0 = true
else
  return 1;
fi
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
    # only create restart files when required
    if is_hive_site_changed && ! is_hive_not_configured_yet ; then
      create_restart_file "${ROLE}"
    fi
    # we do want to restart RM/TL the first time we configure hive too
    if is_hive_site_changed || is_hive_not_configured_yet ; then
      if [ -n "$tl_ip" ] ; then
        if [ -n "$rm_ip" ] ; then
          create_rm_tl_restart_file resourcemanager "$rm_ip"
        fi
        create_rm_tl_restart_file timelineserver "$tl_ip"
      fi
    fi
  fi
done
}

#
# Removes file $HIVE_HOME/conf/.not_configured_yet after first run of Hive configure.sh
#
remove_fresh_install_indicator(){
if is_hive_not_configured_yet ; then
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
# Enables HiveServer2 High Availability
#
configure_hs2_ha(){
HIVE_SITE=$1
isHS2HA=$2
num_hs2=1
zk_hosts=$(hostname)
if [ "$isHS2HA" = "true" ]; then
  if [ -f "$HIVE_CONF/num_hs2" ]; then
    num_hs2=$(cat "$HIVE_CONF/num_hs2")
  fi
  if [ -f "$HIVE_CONF/zk_hosts" ]; then
    zk_hosts=$(cat "$HIVE_CONF/zk_hosts")
  fi
  java -cp "$HADOOP_CLASSPATH" "$HIVE_CONFIG_TOOL_MAIN_CLASS" -path "$HIVE_SITE" -hs2ha -zkquorum "$zk_hosts"
  set_num_h2_in_warden_file "$num_hs2"
fi
}

#
# Updates hs2 warden file with number of HS2
#
set_num_h2_in_warden_file(){
num_hs2=$1
ROLE=hiveserver2
warden_file="$HIVE_HOME/conf/warden.${MAPRCLI[$ROLE]}.conf"
if [ -f "$warden_file" ]; then
  beforeWardenCksum=$(cksum "$warden_file" | cut -d' ' -f1)
  sed -i "s/services=hs2:[[:alnum:]]*:cldb/services=hs2:${num_hs2}:cldb/" "${warden_file}"
  afterWardenCksum=$(cksum "$warden_file" | cut -d' ' -f1)
  if [ "$beforeWardenCksum" != "$afterWardenCksum" ]; then
    configChanged=1
  fi
fi
}

#
# Check if Metastore  database is initialized
#
is_meta_db_initialized(){
if [ -f "$HIVE_CONF/.meta_db_init_done" ]; then
  return 0; # 0 = true
else
  return 1;
fi
}

#
# Check if javax.jdo.option.ConnectionURL is configured or not
#

is_connection_url_configured(){
output=$(java -cp "$HADOOP_CLASSPATH" "$HIVE_CONFIG_TOOL_MAIN_CLASS" -isConfigured "$CONNECTION_URL_PROPERTY_NAME" -path "$HIVE_SITE")
if [ "$output" = "true" ] ; then
  return 0; # 0 = true
else
  return 1;
fi
}

#
# Check if metastore URIs are configured
#
is_metastore_uris_configured(){
output=$(java -cp "$HADOOP_CLASSPATH" "$HIVE_CONFIG_TOOL_MAIN_CLASS" -isConfigured "$HIVE_METASTORE_URIS_PROPERTY_NAME" -path "$HIVE_SITE")
if [ "$output" = "true" ] ; then
  return 0; # 0 = true
else
  return 1;
fi
}

#
# Initialize Derby Db schema for mapr admin user
#
init_derby_schema(){
if ! is_meta_db_initialized && is_hive_not_configured_yet; then
  if [ -d "$HIVE_BIN/$DEFAULT_DERBY_DB_NAME" ]; then
    rm -Rf "$HIVE_BIN/$DEFAULT_DERBY_DB_NAME"
  fi
  if ! is_connection_url_configured ;  then
    java -cp "$HADOOP_CLASSPATH" "$HIVE_CONFIG_TOOL_MAIN_CLASS" -path "$HIVE_SITE" -connurl "$DERBY_CONNECTION_URL"
  fi
  cd "$HIVE_BIN"
  nohup sudo -bnu "$MAPR_USER" "${HIVE_BIN}"/schematool -dbType derby -initSchema > "$HIVE_LOGS"/init_derby_db_$(date +%s)_$$.log 2>&1 < /dev/null &
  if has_metastore && ! is_metastore_uris_configured; then
    java -cp "$HADOOP_CLASSPATH" "$HIVE_CONFIG_TOOL_MAIN_CLASS" -initMetastoreUri -path "$HIVE_SITE"
  fi
fi
}


#
# Check if directory exists in MapR-Fs
#
exists_in_mapr_fs(){
dir="$1"
if $(hadoop fs -test -d "$dir") ; then
  return 0; # 0 = true
else
  return 1;
fi
}


#
# Create warehouse folder in MapRFS if this is fresh install
# Set 777 permissions to /user/hive/warehouse folder to enable creation of tables by other users
#
configure_impersonation(){
isSecure="$1"
if "${MAPR_HOME}"/initscripts/mapr-warden status > /dev/null 2>&1 ; then
  if [ "${isSecure}" = "true" ] && [ -f "${MAPR_HOME}/conf/mapruserticket" ]; then
    export MAPR_TICKETFILE_LOCATION="${MAPR_HOME}/conf/mapruserticket"
  fi
  if is_hive_not_configured_yet ; then
    if ! exists_in_mapr_fs "$METASTOREWAREHOUSE" ; then
      sudo -u "$MAPR_USER" hadoop fs -mkdir -p "$METASTOREWAREHOUSE"
    fi
    sudo -u "$MAPR_USER" hadoop fs -chmod 777 "$METASTOREWAREHOUSE"
  fi
fi
}

grant_permissions_to_hive_site(){
chmod 0640 "$HIVE_SITE"
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
    --customSecure|-c     keep existing security configuration
    --help|-h             print help
    -EC <commonEcoOPts>   clusterwide options like TL, RM, ES
    -R                    Roles only"

if [ $# -gt 0 ]; then
  OPTS=$(getopt -a -o chsuC:R -l EC: -l help -l customSecure -l secure -l unsecure -l R -- "$@")
  if [ $? != 0 ] ; then
      echo -e "${USAGE}"
      return 2 2>/dev/null || exit 2
  fi
  eval set -- "$OPTS"

  while (( "$#" )); do
    case "$1" in
      --EC|-C)
        #Parse Common options
        #Ingore ones we don't care about
        ecOpts=($2)
        shift 2
        restOpts="$@"
        eval set -- "${ecOpts[@]} --"
        while (( "$#" )); do
          case "$1" in
            --R|-R)
              HIVE_CONF_ASSUME_RUNNING_CORE=1
              shift 1;;
            --RM|-RM)
              rm_ip=$2;
              shift 2;;
            --TL|-TL)
              tl_ip=$2;
              shift 2;;
            --) shift
              break;;
              *)
              #echo "Ignoring common option $j"
              shift 1;;
          esac
        done
        shift 2
        eval set -- "$restOpts"
        ;;
      --R|-R)
        HIVE_CONF_ASSUME_RUNNING_CORE=1
        shift 1
        ;;
      --secure|-s)
          isSecure="true"
          shift 1;;
      --customSecure|-c)
          if is_hive_not_configured_yet ; then
            # If the file exist and our configure.sh is passed --customSecure, then we need to
            # translate this to doing what we normally do for --secure (best we can do)
            isSecure="true"
          else
            isSecure="custom"
          fi
          shift 1;;
      --unsecure|-u)
          isSecure="false"
          shift 1;;
      --help|-h)
          echo -e "${USAGE}"
          return 2 2>/dev/null || exit 2
          ;;
      --)
          shift 1;;
        *)
          echo "Unknown option $1"
          echo -e "${USAGE}"
          return 2 2>/dev/null || exit 2
          ;;
    esac
  done
fi


backup_configuration

find_mapr_user_and_group

save_security_flag

configure_security "$HIVE_SITE" "$isSecure"

configure_impersonation "$isSecure"

configure_hs2_webui_pam_and_ssl "$HIVE_SITE" "$isSecure"

init_derby_schema

configure_hs2_ha "$HIVE_SITE" "$isHS2HA"

configure_roles

grant_write_permission_in_logs_dir

copy_log4j_for_hadoop_common_classpath

remove_fresh_install_indicator

grant_admin_permissions_to "$HIVE_HOME"

grant_permissions_to_hive_site

save_new_hive_site_check_sum