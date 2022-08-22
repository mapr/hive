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

#
# Executes bash script and log error if file does not exit. Hides the output.
#
run_bash() {
path_to_bash="$1"
if [ -f ${path_to_bash} ]; then
  source ${path_to_bash} 2>/dev/null
else
  echo "File does not exist ${path_to_bash}"
fi
}

run_bash "$MAPR_HOME"/conf/env.sh
run_bash "$MAPR_HOME"/server/common-ecosystem.sh

configure_logging
if [ $? -ne 0 ]; then
  echo "MAPR_HOME seems to not be set correctly or mapr-core not installed"
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
WEBHCAT_SITE="$HIVE_HOME"/hcatalog/etc/webhcat/webhcat-site.xml
MIN_NUM_CONF_BACKUPS=3
MAX_NUM_CONF_BACKUPS=10
CONF_BACKUPS_SAVING_TIME=30  # 30 days
BKP_PATTERN="${HIVE_HOME}/conf.backup.*_*"

NOW=$(date "+%Y%m%d_%H%M%S")
DAEMON_CONF="$MAPR_HOME/conf/daemon.conf"
rm_ip=""
tl_ip=""
beforeHiveSiteCksum=""
afterHiveSiteCksum=""
declare -A MAPRCLI=( ["hivemetastore"]="hivemeta" ["hiveserver2"]="hs2" ["hivewebhcat"]="hcat")
declare -A PORTS=( ["hivemetastore"]="9083" ["hiveserver2"]="10000" ["hivewebhcat"]="50111")
FILES=("$HIVE_SITE" "$HPLSQL_SITE" "$WEBHCAT_SITE" "$HIVE_ENV")

#
# Checks if webhcat is installed
#
has_webhcat(){
if [ -f "$ROLES_DIR/hivewebhcat" ]; then
  return 0; # 0 = true
else
  logInfo "WebHCat is not installed"
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
logInfo "Mapr User = ['$MAPR_USER'], Mapr Group = ['$MAPR_GROUP']"
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
# Backup authentication method to file "$HIVE_CONF"/.authMethod.backup
#
backup_auth_method_flag() {
if [ -f "$HIVE_CONF"/.authMethod ] ; then
  cp "$HIVE_CONF"/.authMethod "$HIVE_CONF"/.authMethod.backup
else
  touch "$HIVE_CONF"/.authMethod.backup
fi
logInfo "Backuped authentication method ['$HIVE_CONF'/.authMethod] to file ['$HIVE_CONF'/.authMethod.backup]"
}

#
# Returns true if security flag was changed comparing current and previous run of configure.sh.
# E.g. user switches off the security (security ON --> security OFF) or
# user turns on security (security OFF --> security ON) then method returns true
# and false otherwise. This method is used for triggering security related configuration
#
is_auth_method_changed(){
auth_method_backup=$(cat "$HIVE_CONF"/.authMethod.backup)
current_auth_method=$(cat "$HIVE_CONF"/.authMethod)
if [ "$auth_method_backup" = "$current_auth_method" ] ; then
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
if ( is_auth_method_changed || is_hive_not_configured_yet ) && ! is_custom_security ; then
  return 0; # 0 = true
else
  return 1; # 1 = false
fi
}

#
# Processes all xml files in Java.
#
configure_xml_files() {
. "${HIVE_BIN}"/conftool
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
if [ -f "$HIVE_CONF/.configuration_file_check_sum" ]; then
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
      logInfo "Saved current configuration file ['$FILE']"
      break
    fi
  fi
done
}

#
# Delete backups of configuration if they were created more than 30 days ago or if there are more than 10 backups
# but if the number of available backups more than 3.
#
remove_old_conf_backups() {
if has_min_backups; then
  remove_backups_that_stored_more_than_N_days
  remove_oldest_backups
fi
}

#
# Check if number of available backups is greater than minimal backups amount that should always be.
#
has_min_backups() {
numConfBackups=0
[ "$(compgen -G ${BKP_PATTERN})" ] && numConfBackups=$(find ${BKP_PATTERN} -type d | wc -l)
if [ "${numConfBackups}" -gt "${MIN_NUM_CONF_BACKUPS}" ]; then
  return 0; # 0 = true
else
  return 1; # 1 = false
fi
}

#
# Remove backups if the amount of backups are greater than 10.
#
remove_oldest_backups() {
local i total_num_files num_files_to_remove to_remove_dirs
i=0
total_num_files=0
[ "$(compgen -G ${BKP_PATTERN})" ] && total_num_files=$(ls -d1 ${BKP_PATTERN} -t | wc -l)
if [ "${total_num_files}" -gt "${MAX_NUM_CONF_BACKUPS}" ]; then
  num_files_to_remove=$((total_num_files - MAX_NUM_CONF_BACKUPS))
  while read -r line; do
    to_remove_dirs[$i]="$line"
    ((i++))
  done < <(ls -d1 ${BKP_PATTERN} -t | tail -n "${num_files_to_remove}")
  for dir in "${to_remove_dirs[@]}"; do
   rm -Rf "$dir"
   logInfo "Removed outdated configuration backup: $dir"
  done
fi
}

#
# Remove backups that are stored more than N days. N is configurable value.
#
remove_backups_that_stored_more_than_N_days(){
local i to_remove_dirs num_files_to_remove num_outdated_files
i=0
total_num_files=0
num_outdated_files=0
[ "$(compgen -G ${BKP_PATTERN})" ] && total_num_files=$(ls -d1 ${BKP_PATTERN} -t | wc -l)
[ "$(compgen -G ${BKP_PATTERN})" ] && num_outdated_files=$(find ${BKP_PATTERN} -type d -mtime +"${CONF_BACKUPS_SAVING_TIME}" -print | wc -l)
if [ $((total_num_files - num_outdated_files)) -lt "${MIN_NUM_CONF_BACKUPS}" ]; then
  num_files_to_remove=$((total_num_files - MIN_NUM_CONF_BACKUPS))
else
  num_files_to_remove="$num_outdated_files"
fi
while read -r line; do
  to_remove_dirs[$i]="$line"
  ((i++))
done < <(find ${BKP_PATTERN} -type d -mtime +"${CONF_BACKUPS_SAVING_TIME}" -print | tail -n "${num_files_to_remove}")
for dir in "${to_remove_dirs[@]}"; do
 rm -Rf "$dir"
 logInfo "Removed outdated configuration backup: $dir"
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
  logInfo "Check sum has been changed for file ['$file']"
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
logInfo "Save all checksums into configuration file ['$HIVE_CONF'/.configuration_file_check_sum]"
}

#
# Read file checksum
#
read_checksum_for(){
grep "${1}$" "$HIVE_CONF/.configuration_file_check_sum" | cut -d ' ' -f 1
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
logInfo "Created restart file ['$RESTART_DIR'/'$ROLE'-'$HIVE_VERSION'.restart] for role ['$ROLE']"
}

#
# Grant write permission for group in hive logs dir
#
grant_write_permission_in_logs_dir() {
  chmod -R g+w "$HIVE_LOGS"
  logInfo "Granted write permission for group in hive logs dir"
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
      logInfo "Copy log4j api lib ['$LOG4J_API_JAR_PATH'] to ['$HADOOP_SHARE_COMMON_PATH']"
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
logInfo "Created restart file ['$RESTART_DIR'/'$service'.restart] for service ['$service']"
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
  logInfo 'Set admin permissions for warden configuration file ['$path_to_warden_conf'] according to ['$ROLE'] role'
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
logInfo "Configuring Hive roles ..."
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
logInfo "Hive roles has been configured"
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
authMethod="$1"
if "${MAPR_HOME}"/initscripts/mapr-warden status > /dev/null 2>&1 ; then
  if [ "${authMethod}" = "maprsasl" ] && [ -f "${MAPR_HOME}/conf/mapruserticket" ]; then
    export MAPR_TICKETFILE_LOCATION="${MAPR_HOME}/conf/mapruserticket"
  fi
  if is_hive_not_configured_yet ; then
    logInfo "Configuring impersonation ..."
    if ! exists_in_mapr_fs "$METASTOREWAREHOUSE" ; then
      sudo -u "$MAPR_USER" -E hadoop fs -mkdir -p "$METASTOREWAREHOUSE"
      logInfo "Create warehouse folder ['$METASTOREWAREHOUSE'] in MapR-FS"
    fi
    sudo -u "$MAPR_USER" -E hadoop fs -chmod 777 "$METASTOREWAREHOUSE"
    logInfo "Set 777 permissions to warehouse folder ['$METASTOREWAREHOUSE']"
    logInfo "Impersonation has been configured"
  fi
fi
}

#
# Sets default 0644 permissions to hive-site.xml
#
grant_permissions_to_hive_site(){
if security_has_to_be_configured ; then
  chmod 0644 "$HIVE_SITE"
  logInfo "Set default 0644 permissions to hive-site.xml"
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
logInfo "Set admin user group to ['$path']"
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
# Set 444 permissions for a set of files
#
set_read_only_permissions(){
  chmod 444 "$HIVE_HOME"/scripts/llap/sql/serviceCheckScript.sql
}

#
# Configures owner/group and read/write permissions of Hive components
#
configure_permissions(){
logInfo "Configuring owner/group and read/write permissions of Hive components ..."
set_all_dirs_owner_group
set_log_dir_owner_group
set_read_only_permissions
set_admin_user_group_to "$HIVE_VERSION_FILE"
set_admin_user_group_to "$HIVE_HOME"
grant_permissions_to_hive_site
grant_write_permission_in_logs_dir
logInfo "Permissions has been configured"
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
          authMethod="maprsasl";
          shift 1;;
      --customSecure|-c)
          authMethod="custom";
          shift 1;;
      --unsecure|-u)
          authMethod="none";
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

if is_hive_not_configured_yet ; then
  logInfo "Hive is not configured"
else
  logInfo "Hive has been already configured"
fi

init_backup
backup_configuration
remove_old_conf_backups
backup_auth_method_flag
find_mapr_user_and_group
configure_xml_files
configure_impersonation "$authMethod"
configure_roles
copy_log4j_for_hadoop_common_classpath
remove_fresh_install_indicator
configure_permissions
save_all_checksums
