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
HIVE_VERSION_FILE="$MAPR_HOME"/hive/hiveversion
HIVE_VERSION=$(cat "$HIVE_VERSION_FILE")
HIVE_HOME="$MAPR_HOME"/hive/hive-"$HIVE_VERSION"
HIVE_BIN="$HIVE_HOME"/bin
HIVE_LIB="$HIVE_HOME"/lib
HIVE_CONF="$HIVE_HOME"/conf
HIVE_LOGS="$HIVE_HOME"/logs
HIVE_PIDS="$HIVE_HOME"/pids
HIVE_SITE="$HIVE_CONF"/hive-site.xml
HIVE_ENV="$HIVE_CONF"/hive-env.sh
HPLSQL_SITE="$HIVE_CONF"/hplsql-site.xml
RESTART_DIR="${RESTART_DIR:=$MAPR_HOME/conf/restart}"
RESTART_LOG_DIR="${RESTART_LOG_DIR:=${MAPR_HOME}/logs/restart_logs}"
METASTOREWAREHOUSE="/user/hive/warehouse"
ROLES_DIR="$MAPR_HOME/roles"
DERBY_CONNECTION_URL="jdbc:derby:;databaseName=$HIVE_BIN/metastore_db;create=true"
CONNECTION_URL_PROPERTY_NAME="javax.jdo.option.ConnectionURL"
HIVE_METASTORE_URIS_PROPERTY_NAME="hive.metastore.uris"
WEBHCAT_SITE="$HIVE_HOME"/hcatalog/etc/webhcat/webhcat-site.xml
REPORTER_TYPE="JSON_FILE,JMX"
HIVE_SERVER2_REPORTER_FILE_LOCATION="/tmp/hiveserver2_report.json"
HIVE_METASTORE_REPORTER_FILE_LOCATION="/tmp/hivemetastore_report.json"

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
FILES=("$HIVE_SITE" "$HPLSQL_SITE" "$WEBHCAT_SITE" "$HIVE_ENV")

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
# Backup security flag to file "$HIVE_BIN"/isSecure.backup
#
backup_security_flag() {
if [ -f "$HIVE_BIN"/isSecure ] ; then
  cp "$HIVE_BIN"/isSecure "$HIVE_BIN"/isSecure.backup
else
  touch "$HIVE_BIN"/isSecure.backup
fi
}

#
# Returns true if security flag was changed comparing current and previous run of configure.sh.
# E.g. user switches off the security (security ON --> security OFF) or
# user turns on security (security OFF --> security ON) then method returns true
# and false otherwise. This method is used for triggering security related configuration
#
is_security_changed(){
security_backup=$(cat "$HIVE_BIN"/isSecure.backup)
current_security=$(cat "$HIVE_BIN"/isSecure)
if [ "$security_backup" = "$current_security" ] ; then
  return 1; # 1 = false
else
  return 0; # 0 = true
fi
}

#
# Returns boolean 'true' if security is custom.
#
is_custom_security(){
if [ -f "$MAPR_HOME/conf/.customSecure" ]; then
  return 0; # 0 = true
else
  return 1; # 1 = false
fi
}

#
# Checks whether there is a need to configure security.
# We have to configure security if security type was changed and it is not in custom format
# or if hive was not configured yet.
#
security_has_to_be_configured(){
if ( is_security_changed || is_hive_not_configured_yet ) && ! is_custom_security ; then
  return 0; # 0 = true
else
  return 1; # 1 = false
fi
}

configure_security(){
HIVE_SITE="$1"
isSecure="$2"

if security_has_to_be_configured ; then
  . "${HIVE_BIN}"/conftool -path "$HIVE_SITE" "-maprsasl" -security "$isSecure"
fi
}

#
# Configures PAM an SSL encryption for HiveServer2 Web UI on MapR SASL cluster
#
configure_hs2_webui_pam_and_ssl(){
HIVE_SITE="$1"
isSecure="$2"

if security_has_to_be_configured ; then
  . "${HIVE_BIN}"/conftool -path "$HIVE_SITE" "-webuipamssl" -security "$isSecure"
fi
}

#
# Configures  SSL encryption for webHCat on MapR SASL cluster
#
configure_webhcat_ssl(){
WEBHCAT_SITE="$1"
isSecure="$2"
if security_has_to_be_configured ;  then
  . "${HIVE_BIN}"/conftool -path "$WEBHCAT_SITE" "-webhcatssl" -security "$isSecure"
fi
}

#
# Set SSL encryption by default for HiveServer2 on MapR SASL cluster
#
configure_hs2_ssl(){
HIVE_SITE="$1"
isSecure="$2"
if security_has_to_be_configured ; then
  . "${HIVE_BIN}"/conftool -path "$HIVE_SITE" "-hs2ssl" -security "$isSecure"
fi
}

#
# Set SSL encryption by default for Hive Metastore on MapR SASL cluster
#
configure_hmeta_ssl(){
HIVE_SITE="$1"
isSecure="$2"
if security_has_to_be_configured ; then
  . "${HIVE_BIN}"/conftool -path "$HIVE_SITE" "-hmetassl" -security "$isSecure"
fi
}

#
# Check that port is available
#
check_port_for(){
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
# Checks if configuration_file_check_sum exists after upgradeSchema function was
# used between Hive upgrade from one version to another
#
is_checksum_file_exist(){
if [ -f ""$HIVE_CONF"/.configuration_file_check_sum" ]; then
  return 0 # true
else
  return 1;
fi
}

#
# Checks if Hive has been already configured and save initial checksums after first launch
#
init_backup(){
if is_hive_not_configured_yet || ! is_checksum_file_exist; then
  save_all_checksums
fi
}


#
#  Save off current configuration files if any of the files was changed
#
backup_configuration(){
for FILE in "${FILES[@]}" ; do
  if [ -f ${FILE} ]; then
    if is_check_sum_changed_for ${FILE}; then
      backup_all_files
      save_all_checksums
      break
    fi
  fi
done
}

#
# Checks if checksum was changed in configuration-file for appropriate file
#
is_check_sum_changed_for(){
local file=$1
local old_checksum=$(read_checksum_for "$file")
local new_checksum=$(cksum "$file" | cut -d ' ' -f 1)
if [ "$old_checksum" != "$new_checksum" ]; then
  return 0 # true
else
  return 1;
fi
}

#
#  Save off all configuration files
#
backup_all_files(){
mkdir -p "$HIVE_CONF".backup."$NOW"/ && cp "$HIVE_CONF"/{hive-site.xml,hplsql-site.xml} "$_"
cp "$WEBHCAT_SITE" "$HIVE_CONF".backup."$NOW"/
if is_hive_env_file; then
  cp "$HIVE_ENV" "$HIVE_CONF".backup."$NOW"
fi
}

#
# Save all checksums in configuration_file
#
save_all_checksums(){
if is_hive_env_file; then
  beforeConfigurationFileCksum=$(cksum "$HPLSQL_SITE" "$HIVE_SITE" "$WEBHCAT_SITE" "$HIVE_ENV"| cut -d' ' -f 1,3)
else
  beforeConfigurationFileCksum=$(cksum "$HPLSQL_SITE" "$HIVE_SITE" "$WEBHCAT_SITE" | cut -d' ' -f 1,3)
fi
echo "$beforeConfigurationFileCksum" > "$HIVE_CONF"/.configuration_file_check_sum
}

#
# Read file checksum
#
read_checksum_for(){
grep "${1}$" ""$HIVE_CONF"/.configuration_file_check_sum" | cut -d ' ' -f 1
}

#
# Check if exists hive-env.sh file
#
is_hive_env_file(){
if [ -f ${HIVE_ENV} ]; then
  return 0 # true
else
  return 1;
fi
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
copy_warden_files_for(){
ROLE=$1
mkdir -p "$MAPR_HOME"/conf/conf.d
if [ -f "$HIVE_HOME/conf/warden.${MAPRCLI[$ROLE]}.conf.template" ]; then
  cp -Rp "$HIVE_HOME/conf/warden.${MAPRCLI[$ROLE]}.conf.template" "$MAPR_HOME/conf/conf.d/warden.${MAPRCLI[$ROLE]}.conf"
fi
}

#
# Create restart file for role
#
create_restart_file_for(){
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
    LOG4J_API_JAR_PATH=$(ls "${HIVE_LIB}"/log4j-api* | awk '{print $1}')
    LOG4J_API_JAR_NAME=$(basename "${LOG4J_API_JAR_PATH}")
    HADOOP_VERSION=$(cat "${MAPR_HOME}"/hadoop/hadoopversion | awk -F'=' '{print $1}')
    HADOOP_SHARE_COMMON_PATH="${MAPR_HOME}/hadoop/hadoop-${HADOOP_VERSION}/share/hadoop/common/lib/"
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
# Sets admin permissions for warden configuration file according to role
#
grant_owner_group_for(){
ROLE=$1
path_to_warden_conf="$MAPR_HOME/conf/conf.d/warden.${MAPRCLI[$ROLE]}.conf"
if [ -f "$path_to_warden_conf" ]; then
  if [ ! -z "$MAPR_USER" ]; then
    chown "$MAPR_USER" "$path_to_warden_conf"
  fi
  if [ ! -z "$MAPR_GROUP" ]; then
    chgrp "$MAPR_GROUP" "$path_to_warden_conf"
  fi
fi
}

#
# Check if warden configuration file is present for role
#
is_warden_file_available_for() {
ROLE=$1
path_to_warden_conf="$MAPR_HOME/conf/conf.d/warden.${MAPRCLI[$ROLE]}.conf"
if [ -f "$path_to_warden_conf" ]; then
  return 0; # 0 = true
else
  return 1; # 1 = false
fi
}

#
# Configure Hive roles
#
configure_roles(){
ROLES=(hivemetastore hiveserver2 hivewebhcat)
for ROLE in "${ROLES[@]}"; do
  if hasRole "${ROLE}"; then
    check_port_for "${ROLE}"
    # copy warden files only if there is fresh install and Hive is not configured yet,
    # in order not to overwrite existing custom Warden configuration (e.g. HA configuration)
    if is_hive_not_configured_yet || ! is_warden_file_available_for "${ROLE}"; then
      copy_warden_files_for "${ROLE}"
      grant_owner_group_for "${ROLE}"
    fi
    # only create restart files when required
    if is_hive_site_changed && ! is_hive_not_configured_yet ; then
      create_restart_file_for "${ROLE}"
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
  . "${HIVE_BIN}"/conftool -path "$HIVE_SITE" -hs2ha -zkquorum "$zk_hosts"
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
output=$(. "${HIVE_BIN}"/conftool -existProperty "$CONNECTION_URL_PROPERTY_NAME" -path "$HIVE_SITE")
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
output=$(. "${HIVE_BIN}"/conftool -existProperty "$HIVE_METASTORE_URIS_PROPERTY_NAME" -path "$HIVE_SITE")
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
    . "${HIVE_BIN}"/conftool -path "$HIVE_SITE" -connurl "$DERBY_CONNECTION_URL"
  fi
  cd "$HIVE_BIN" || return
  set_admin_user_group_to "$HIVE_BIN"
  nohup sudo -bnu "$MAPR_USER" "${HIVE_BIN}"/schematool -dbType derby -initSchema > "$HIVE_LOGS"/init_derby_db_$(date +%s)_$$.log 2>&1 < /dev/null &
  if has_metastore && ! is_metastore_uris_configured; then
    . "${HIVE_BIN}"/conftool -initMetastoreUri -path "$HIVE_SITE"
  fi
fi
}


#
# Check if directory exists in MapR-Fs
#
exists_in_mapr_fs(){
dir="$1"
if $(sudo -u "$MAPR_USER" -E hadoop fs -test -d "$dir") ; then
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
      sudo -u "$MAPR_USER" -E hadoop fs -mkdir -p "$METASTOREWAREHOUSE"
    fi
    sudo -u "$MAPR_USER" -E hadoop fs -chmod 777 "$METASTOREWAREHOUSE"
  fi
fi
}

#
# Sets default 0644 permissions to hive-site.xml
#
grant_permissions_to_hive_site(){
chmod 0644 "$HIVE_SITE"
}

#
# Configures hive.conf.restricted.list which contains comma separated list of
# configuration options which are immutable at runtime
#

configure_restricted_list(){
HIVE_SITE="$1"
isSecure="$2"
if security_has_to_be_configured ; then
  . "${HIVE_BIN}"/conftool -path "$HIVE_SITE" -restrictedList -security "$isSecure"
fi
}

#
# Configures FallbackHiveAuthorizerFactory and enables Hive security authorization. See CVE-2018-11777.
#

configure_fallback_authorizer(){
HIVE_SITE="$1"
isSecure="$2"
if security_has_to_be_configured ; then
  . "${HIVE_BIN}"/conftool -path "$HIVE_SITE" -fallBackAuthorizer -security "$isSecure"
fi
}

#
# Grant owners
#
set_admin_user_group_to(){
path=$1
option=$2
if [ "$option" = "recursive" ] ; then
  option="-R"
fi
if [ ! -z "$MAPR_USER" ]; then
  chown ${option} "$MAPR_USER" "$path"
fi
if [ ! -z "$MAPR_GROUP" ]; then
  chgrp ${option} "$MAPR_GROUP" "$path"
fi
}


#
# Set owner and group for log directory
#
set_log_dir_owner_group(){
if [ -f "$HIVE_LOGS" ] ; then
  if [ -f "$HIVE_LOGS/$MAPR_USER" ] ; then
  set_admin_user_group_to "$HIVE_LOGS/$MAPR_USER" recursive
  fi
fi
}

#
# Set owner and group for all dirs in $HIVE_HOME. Skips the contents of dirs in exception list
#
set_all_dirs_owner_group(){
# list of exception where not to change owner/group
declare -A is_exception=(["$HIVE_LOGS"]=1 ["$HIVE_PIDS"]=1)
for dir in "$HIVE_HOME"/* ; do
  # folder is not in exception list
  if [ ! -n "${is_exception[$dir]}" ] ; then
    set_admin_user_group_to "$dir" recursive
  else
    set_admin_user_group_to "$dir"
  fi
done
}


#
# Configures owner/group and read/write permissions of Hive components
#
configure_permissions(){
set_all_dirs_owner_group
set_log_dir_owner_group
set_admin_user_group_to "$HIVE_VERSION_FILE"
set_admin_user_group_to "$HIVE_HOME"
grant_permissions_to_hive_site
grant_write_permission_in_logs_dir
}

#Adds mapr user to the property "hive.users.in.admin.role" in hive-site.xml.
#This is required to access hiveserver logs from web ui.
configure_users_in_admin_role(){
  HIVE_SITE="$1"
  ADMIN_USER="$2"
  isSecure="$3"
  if security_has_to_be_configured ; then
  . ${HIVE_BIN}/conftool -path "$HIVE_SITE" -adminUser "$ADMIN_USER" -security "$isSecure"
  fi
}

#
# Configures HiveServer2 for collecting metrics in hive-site.xml.
# Property com.sun.management.jmxremote is configured in $HIVE_BIN/hive
#
configure_hs2_metrics() {
HIVE_SITE="$1"
if is_hive_not_configured_yet ; then
. ${HIVE_BIN}/conftool -path "$HIVE_SITE" -hiveserver2_metrics_enabled true
. ${HIVE_BIN}/conftool -path "$HIVE_SITE" -hs2_report_file "$HIVE_SERVER2_REPORTER_FILE_LOCATION" -reporter_enabled true
fi
}

#
# Configures Metastore for collecting metrics in hive-site.xml.
# Property com.sun.management.jmxremote is configured in $HIVE_BIN/hive
#
configure_metastore_metrics() {
HIVE_SITE="$1"
if is_hive_not_configured_yet ; then
  . ${HIVE_BIN}/conftool -path "$HIVE_SITE" -metastore_metrics_enabled true
  . ${HIVE_BIN}/conftool -path "$HIVE_SITE" -hmeta_report_file "$HIVE_METASTORE_REPORTER_FILE_LOCATION" -reporter_enabled true
fi
}

#
# Reporter type for metric class org.apache.hadoop.hive.common.metrics.metrics2.CodahaleMetrics.
# Comma separated list of JMX, CONSOLE, JSON_FILE, HADOOP2.
#
# For metric class org.apache.hadoop.hive.common.metrics.metrics2.CodahaleMetrics JSON_FILE reporter,
# the location of local JSON metrics file.This file will get overwritten at every interval.
#
configure_reporter_type_and_file_location() {
HIVE_SITE="$1"
if is_hive_not_configured_yet ; then
  . ${HIVE_BIN}/conftool -path "$HIVE_SITE" -reporter_type "$REPORTER_TYPE" -reporter_enabled true
fi
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
        ecOpts=("$2")
        shift 2
        restOpts="$@"
        eval set -- "${ecOpts[*]} --"
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

init_backup

backup_configuration

backup_security_flag

find_mapr_user_and_group

save_security_flag

configure_security "$HIVE_SITE" "$isSecure"

configure_impersonation "$isSecure"

configure_hs2_webui_pam_and_ssl "$HIVE_SITE" "$isSecure"

configure_webhcat_ssl "$WEBHCAT_SITE" "$isSecure"

configure_hs2_ssl "$HIVE_SITE" "$isSecure"

# Comment out according MAPR-HIVE-507 : hive.metastore.use.SSL should be set to false by default
# TODO: uncomment after hive.metastore.use.SSL back-porting to Hive-1.2 Spark branch
# configure_hmeta_ssl "$HIVE_SITE" "$isSecure"

configure_users_in_admin_role "$HIVE_SITE" "$MAPR_USER" "$isSecure"

configure_restricted_list "$HIVE_SITE" "$isSecure"

configure_fallback_authorizer "$HIVE_SITE" "$isSecure"

init_derby_schema

configure_hs2_ha "$HIVE_SITE" "$isHS2HA"

configure_hs2_metrics "$HIVE_SITE"

configure_metastore_metrics "$HIVE_SITE"

configure_reporter_type_and_file_location "$HIVE_SITE"

configure_roles

copy_log4j_for_hadoop_common_classpath

remove_fresh_install_indicator

configure_permissions

save_all_checksums
