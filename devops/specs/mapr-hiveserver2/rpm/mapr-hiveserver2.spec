%undefine __check_files

summary:     HPE DataFabric Ecosystem Pack: Apache Hive HiveServer2
license:     Hewlett Packard Enterprise, CopyRight
Vendor:      Hewlett Packard Enterprise
name:        mapr-hiveserver2
version:     __RELEASE_VERSION__
release:     1
prefix:      /
group:       HPE
buildarch:   noarch
requires:    mapr-hive = __RELEASE_VERSION__
Requires(preun):    mapr-hive
AutoReqProv: no

%description
Apache Hive (HiveServer2) distribution included in HPE DataFabric Software Ecosystem Pack
Tag: __RELEASE_BRANCH__
Commit: __GIT_COMMIT__


%clean
echo "NOOP"


%files
__PREFIX__/roles

%pre
if [ "$1" = "2" ]; then
    #
    # This is 3.x --> 3.y (y > x) upgrade scenario
    #
    CURRENT_VERSION=__VERSION_3DIGIT__
    HIVE_HOME=__PREFIX__/hive/hive-$CURRENT_VERSION
    DAEMON_CONF="__PREFIX__/conf/daemon.conf"
    OLD_VERSION=$(rpm -qi mapr-hive | awk -F': ' '/Version/ {print $2}' | cut -d'.' -f1-3)
    OLD_HIVE_FILE=__PREFIX__/hive/hive-"$OLD_HIVE_VERSION"/bin/hive

    if [ -f "$DAEMON_CONF" ]; then
      export HIVE_IDENT_STRING=$( awk -F = '$1 == "mapr.daemon.user" { print $2 }' $DAEMON_CONF)
    else
      export HIVE_IDENT_STRING="mapr"
    fi

    MAPR_USER=$HIVE_IDENT_STRING

    if [ -f "$OLD_HIVE_FILE" ]; then
       if sudo -u "$MAPR_USER" "$OLD_HIVE_FILE" --service hiveserver2 --status > /dev/null 2>&1 ; then
            echo "Stopping hive HS2 service"
            OUTPUT=$(sudo -u "$MAPR_USER" "$OLD_HIVE_FILE" --service hiveserver2 --stop 2>&1)
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

#
# if current mapr-core release < 6.0 (has old configuration scheme) returns 0, else returns 1
#

%preun
# In order for the HiveServer2 (HS2) startup/shutdown script to stop the HS2,
# it needs the user who started the HS2 so that it can find the proper
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
  if [ -f __PREFIX__/conf/conf.d/warden.hs2.conf ]; then
    rm -Rf __PREFIX__/conf/conf.d/warden.hs2.conf
  fi
fi

if [ -f "$HIVE_FILE" ]; then
   if sudo -u "$MAPR_USER" "$HIVE_FILE" --service hiveserver2 --status  > /dev/null 2>&1 ; then
        echo "Stopping hive HS2 service"
        OUTPUT=$(sudo -u "$MAPR_USER" "$HIVE_FILE" --service hiveserver2 --stop 2>&1)
        if [ $? -ne 0 ]; then
           echo "$OUTPUT"
        fi
    fi
fi

%postun

#
# If this is an uninstall, ....
#

if [ -f __PREFIX__/conf/restart/hiveserver2-__VERSION_3DIGIT__.restart ]; then
    rm -f __PREFIX__/conf/restart/hiveserver2-__VERSION_3DIGIT__.restart
fi

%posttrans