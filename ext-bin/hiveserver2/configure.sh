#!/usr/bin/env bash

# Copyright (c) 2017 & onwards. MapR Tech, Inc., All rights reserved

############################################################################
#
#
# Configure script for Hive
#
##
# This script is normally run by the core configure.sh to setup HiveServer2
# during install. If it is run standalone, need to correctly initialize the
# variables that it normally inherits from the master configure.sh
#
############################################################################


#
# Globals
#
MAPR_HOME="${BASEMAPR:-/opt/mapr}"
HIVE_SERVER2_DEFAULT_PORT=10000
HIVE_VERSION="2.1"
HIVE_HOME="$MAPR_HOME"/hive/hive-"$HIVE_VERSION"
HIVE_BIN="$HIVE_HOME"/bin
HIVE_SERVER2_HOME="$MAPR_HOME"/hiveserver2/

#
# Copy HiveServer2 Warden files
#
copy_warden_files(){
mkdir -p "$MAPR_HOME"/conf/conf.d
if [ -f "$HIVE_HOME"/conf/warden.hs2.conf ]; then
  cp -Rp "$HIVE_HOME"/conf/warden.hs2.conf "$MAPR_HOME"/conf/conf.d/.
fi
}

#
# Copy restart file
#
copy_restart_file(){
cp -p "$HIVE_HOME"/ext-bin/hiveserver2/hiveserver2-2.1.restart "$RESTART_DIR"
}

#
# Run common configuration for all roles
#
source "$HIVE_BIN"/configure.sh "$@"

#
# Run specific configuration for HiveServer2 role
#
grant_admin_permissions_to "$HIVE_SERVER2_HOME"

create_version_file "hiveserver2version"

copy_restart_file

check_port "$HIVE_SERVER2_DEFAULT_PORT"

copy_warden_files
