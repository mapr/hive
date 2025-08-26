%undefine __check_files

summary:     HPE DataFabric Ecosystem Pack: Apache Hive Metastore
license:     Hewlett Packard Enterprise, CopyRight
Vendor:      Hewlett Packard Enterprise
name:        mapr-hivemetastore
version:     __RELEASE_VERSION__
release:     1
prefix:      /
group:       HPE
buildarch:   noarch
requires:    mapr-hive = __RELEASE_VERSION__
Requires(preun):    mapr-hive
AutoReqProv: no

%description
Apache Hive (Metastore) distribution included in HPE DataFabric Software Ecosystem Pack
Tag: __RELEASE_BRANCH__
Commit: __GIT_COMMIT__


%clean
echo "NOOP"


%files
__PREFIX__/roles

%pre
# Perform whatever maintenance must occur before the upgrade begins. 2 = upgrade
if [ "$1" = "2" ]; then
    #
    # This is 3.x --> 3.y (y > x) upgrade scenario
    #
    CURRENT_VERSION=__VERSION_3DIGIT__
    HIVE_HOME=__PREFIX__/hive/hive-$CURRENT_VERSION
    DAEMON_CONF="__PREFIX__/conf/daemon.conf"
    OLD_VERSION=$(rpm -qi mapr-hive | awk -F': ' '/Version/ {print $2}' | cut -d'.' -f1-3)
    OLD_HIVE_FILE=__PREFIX__/hive/hive-"$OLD_HIVE_VERSION"/bin/hive
    HIVE_HOSTNAME="$(hostname -f)"

    if [ -f "$DAEMON_CONF" ]; then
        export HIVE_IDENT_STRING=$( awk -F = '$1 == "mapr.daemon.user" { print $2 }' $DAEMON_CONF)
    else
        export HIVE_IDENT_STRING="mapr"
    fi

    MAPR_USER=$HIVE_IDENT_STRING

    if [ -f "$OLD_HIVE_FILE" ]; then
        if sudo -u "$MAPR_USER" "$OLD_HIVE_FILE" --service metastore --status > /dev/null 2>&1 ; then
            echo "Stopping hive Metastore service"
            OUTPUT=$(sudo -u "$MAPR_USER" "$OLD_HIVE_FILE" --service metastore --stop 2>&1)
            if [ $? -ne 0 ]; then
                echo "$OUTPUT"
            fi
        fi
    fi
fi

%post
##
## If this is an UPGRADE, ...
##

%preun
# In order for the metastore startup/shutdown script to stop the metastore,
# it needs the user who started the metastore so that it can find the proper
# pid file in HIVE_HOME/logs directory.
DAEMON_CONF="__PREFIX__/conf/daemon.conf"
HIVE_FILE=__PREFIX__/hive/hive-__VERSION_3DIGIT__/bin/hive

if [ -f "$DAEMON_CONF" ]; then
  export HIVE_IDENT_STRING=$( awk -F = '$1 == "mapr.daemon.user" { print $2 }' $DAEMON_CONF)
else
  export HIVE_IDENT_STRING="mapr"
fi

MAPR_USER=$HIVE_IDENT_STRING

# Removing an rpm. Need to remove warden*conf file. 0 = remove
if [ "$1" = "0" ]; then
  if [ -f __PREFIX__/conf/conf.d/warden.hivemeta.conf ]; then
    rm -Rf __PREFIX__/conf/conf.d/warden.hivemeta.conf
  fi
fi

if [ -f "$HIVE_FILE" ]; then
  if sudo -u "$MAPR_USER" "$HIVE_FILE" --service metastore --status > /dev/null 2>&1 ; then
    echo "Stopping hive Metastore service"
    OUTPUT=$(sudo -u "$MAPR_USER" "$HIVE_FILE" --service metastore --stop 2>&1)
    if [ $? -ne 0 ]; then
      echo "$OUTPUT"
    fi
  fi
fi

%postun

#
# If this is an uninstall, ....
#

if [ -f __PREFIX__/conf/restart/hivemetastore-__VERSION_3DIGIT__.restart ]; then
    rm -f __PREFIX__/conf/restart/hivemetastore-__VERSION_3DIGIT__.restart
fi


%posttrans