%undefine __check_files

summary:     HPE DataFabric Ecosystem Pack: Apache Hive HiveWebHcat
license:     Hewlett Packard Enterprise, CopyRight
Vendor:      Hewlett Packard Enterprise
name:        mapr-hivewebhcat
version:     __RELEASE_VERSION__
release:     1
prefix:      /
group:       HPE
buildarch:   noarch
requires:    mapr-hive = __RELEASE_VERSION__
Requires(preun):    mapr-hive
AutoReqProv: no

%description
Apache Hive (HiveWebHcat) distribution included in HPE DataFabric Software Ecosystem Pack
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
    OLD_HCAT_FILE=__PREFIX__/hive/hive-"$OLD_HIVE_VERSION"/hcatalog/sbin/webhcat_server.sh
    HIVE_HOSTNAME="$(hostname -f)"
    if [ -z "$HIVE_WEBHCAT_HOSTNAME" ]; then
      HIVE_HOSTNAME="$(hostname)"
    fi

    if [ -f "$DAEMON_CONF" ]; then
        export HIVE_IDENT_STRING=$( awk -F = '$1 == "mapr.daemon.user" { print $2 }' $DAEMON_CONF)
    else
        export HIVE_IDENT_STRING="mapr"
    fi

    MAPR_USER=$HIVE_IDENT_STRING

    if [ -f "$OLD_HCAT_FILE" ]; then
        if sudo -u "$MAPR_USER" "$OLD_HCAT_FILE" status > /dev/null 2>&1 ; then
            echo "Stopping hive WebHcat service"
            OUTPUT=$(sudo -u "$MAPR_USER" "$OLD_HCAT_FILE" stop 2>&1)
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
# In order for the hcat startup/shutdown script to stop the hcat,
# it needs the user who started the hcat so that it can find the proper
# pid file in HIVE_HOME/logs directory.
MAPR_HOME="__PREFIX__"
HIVE_WEBHCAT_HOSTNAME="$(hostname -f)"
if [ -z "$HIVE_WEBHCAT_HOSTNAME" ]; then
  HIVE_WEBHCAT_HOSTNAME="$(hostname)"
fi
DAEMON_CONF="$MAPR_HOME/conf/daemon.conf"
if [ -f "$DAEMON_CONF" ]; then
  MAPR_USER=$( awk -F = '$1 == "mapr.daemon.user" { print $2 }' $DAEMON_CONF)
else
  MAPR_USER="mapr"
fi

# Removing an rpm. Need to remove warden*conf file. 0 = remove
if [ "$1" = "0" ]; then
  if [ -f  __PREFIX__/conf/conf.d/warden.hcat.conf ]; then
    rm -Rf __PREFIX__/conf/conf.d/warden.hcat.conf
  fi
fi

if sudo -u "$MAPR_USER" "$MAPR_HOME"/hive/hive-__VERSION_3DIGIT__/hcatalog/sbin/webhcat_server.sh status > /dev/null 2>&1 ; then
  echo "Stopping hive WebHcat service"
  OUTPUT=$(sudo -u "$MAPR_USER" "$MAPR_HOME"/hive/hive-__VERSION_3DIGIT__/hcatalog/sbin/webhcat_server.sh stop 2>&1)
  if [ $? -ne 0 ]; then
    echo "$OUTPUT"
  fi
fi

%postun

#
# If this is an uninstall, ....
#
if [ "$1" = "0" ]; then
  rm -f __PREFIX__/bin/hcat
  rm -f /usr/bin/hcat
  rm -f __PREFIX__/bin/webhcat_server
  rm -f /usr/bin/webhcat_server
fi

if [ -f __PREFIX__/conf/restart/hivewebhcat-__VERSION_3DIGIT__.restart ]; then
  rm -f __PREFIX__/conf/restart/hivewebhcat-__VERSION_3DIGIT__.restart
fi

%posttrans

