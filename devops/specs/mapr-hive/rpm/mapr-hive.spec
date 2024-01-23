%undefine __check_files

summary:     Ezmeral
license:     Hewlett Packard Enterprise, CopyRight
Vendor:      Hewlett Packard Enterprise, <ezmeral_software_support@hpe.com>
name:        mapr-hive
version:     __RELEASE_VERSION__
release:     1
prefix:      /
group:       HPE
buildarch:   noarch
requires:    mapr-client
AutoReqProv: no

%description
Ezmeral Ecosystem Pack: Hive Package
Tag: __RELEASE_BRANCH__
Commit: __GIT_COMMIT__


%clean
echo "NOOP"


%files
__PREFIX__/hive
__PREFIX__/roles

%pre
# $1 -eq 1 install
# $1 -eq 2 upgrade
# N/A     uninstall
[ -n "$VERBOSE" ] && echo "pre install called with argument \`$1'" >&2
[ -n "$VERBOSE" ] && set -x ;

#
# Returns 0 (true) if the installed version if Hive has format X.Y.Z. E.g. 2.3.202205150306
#
is_two_digit_version_installed() {
RAW_HIVE_VERSION=$(rpm -qi mapr-hive | awk -F': ' '/Version/ {print $2}')
DOTS_COUNT=$(echo "$RAW_HIVE_VERSION" | awk -F"." '{print NF-1}')
if [ "$DOTS_COUNT" -eq 2  ];  then
  return 0; # true
else
  return 1; # false
fi
}

#
# Returns 0 (true) if the installed version if Hive has format X.Y.Z.A.B. E.g. 3.1.3.0.202208260502
#
is_four_digit_version_installed() {
RAW_HIVE_VERSION=$(rpm -qi mapr-hive | awk -F': ' '/Version/ {print $2}')
DOTS_COUNT=$(echo "$RAW_HIVE_VERSION" | awk -F"." '{print NF-1}')
if [ "$DOTS_COUNT" -eq 4  ];  then
  return 0; # true
else
  return 1; # false
fi
}


if [ "$1" = "2" ]; then
    if is_two_digit_version_installed; then
      #
      # This is 2.x --> 3.y upgrade scenario
      #
      OLD_TIMESTAMP=$(rpm -qi mapr-hive | awk -F': ' '/Version/ {print $2}'| cut -d'.' -f3-3)
      OLD_VERSION=$(rpm -qi mapr-hive | awk -F': ' '/Version/ {print $2}' | cut -d'.' -f1-2)
    fi

    if is_four_digit_version_installed; then
      #
      # This is 3.x --> 3.y (y > x) upgrade scenario
      #
      OLD_TIMESTAMP=$(rpm -qi mapr-hive | awk -F': ' '/Version/ {print $2}'| cut -d'.' -f5-5)
      OLD_VERSION=$(rpm -qi mapr-hive | awk -F': ' '/Version/ {print $2}' | cut -d'.' -f1-3)
      fi
    mkdir -p __PREFIX__/hive/hive-${OLD_VERSION}.${OLD_TIMESTAMP}/conf
    cp -r __PREFIX__/hive/hive-${OLD_VERSION}/conf/* __PREFIX__/hive/hive-${OLD_VERSION}.${OLD_TIMESTAMP}/conf
    rm __PREFIX__/hive/hive-${OLD_VERSION}.${OLD_TIMESTAMP}/conf/*.template
    cp -r __PREFIX__/hive/hive-${OLD_VERSION}/hcatalog/etc/webhcat __PREFIX__/hive/hive-${OLD_VERSION}.${OLD_TIMESTAMP}

    DAEMON_CONF=__PREFIX__/conf/daemon.conf
    if [ -f "$DAEMON_CONF" ]; then
        MAPR_USER=$( awk -F = '$1 == "mapr.daemon.user" { print $2 }' $DAEMON_CONF)
        MAPR_GROUP=$( awk -F = '$1 == "mapr.daemon.group" { print $2 }' $DAEMON_CONF)
        if [ ! -z "$MAPR_USER" ]; then
            chown -R ${MAPR_USER}:${MAPR_GROUP} __PREFIX__/hive/hive-${OLD_VERSION}.${OLD_TIMESTAMP}
        fi
    fi
fi

%post

[ -n "$VERBOSE" ] && echo "postinst called with argument \`$1'" >&2
[ -n "$VERBOSE" ] && set -x


#
# Configure service log location
#

set_service_log_location() {
SERVICE_CONF_FILE=$1
SERVICE_LOG_LOCATION=$2
sed -i "s|.*service.logs.location=.*|service.logs.location=$SERVICE_LOG_LOCATION|g" "$SERVICE_CONF_FILE"
}


DAEMON_CONF="__PREFIX__/conf/daemon.conf"
WARDEN_HCAT_CONF="__INSTALL_3DIGIT__"/conf.new/warden.hcat.conf.template
WARDEN_HIVEMETA_CONF="__INSTALL_3DIGIT__"/conf.new/warden.hivemeta.conf.template
WARDEN_HS2_CONF="__INSTALL_3DIGIT__"/conf.new/warden.hs2.conf.template

if [ -f "$DAEMON_CONF" ]; then
  MAPR_USER=$( awk -F = '$1 == "mapr.daemon.user" { print $2 }' "$DAEMON_CONF")
  if [ ! -z "$MAPR_USER" ]; then
    HIVE_MAPR_USER_LOG_DIR="__INSTALL_3DIGIT__/logs/$MAPR_USER"
    set_service_log_location "$WARDEN_HCAT_CONF" "$HIVE_MAPR_USER_LOG_DIR"
    set_service_log_location "$WARDEN_HIVEMETA_CONF" "$HIVE_MAPR_USER_LOG_DIR"
    set_service_log_location "$WARDEN_HS2_CONF" "$HIVE_MAPR_USER_LOG_DIR"
  fi
fi

#
# this is install
#

mkdir -p "__INSTALL_3DIGIT__"/conf
mkdir -p "__INSTALL_3DIGIT__"/hcatalog/etc/webhcat

if [ "$1" = "1" ]; then
  touch "__INSTALL_3DIGIT__/conf/.not_configured_yet"
fi

diffR=$(diff -r "__INSTALL_3DIGIT__"/conf "__INSTALL_3DIGIT__"/conf.new | grep "^Only in " | grep "conf.new" | sed "s/^Only in //" | sed "s/: /\//")
for i in ${diffR}; do
  j=$(echo "$i" | sed 's/conf.new/conf/g')
  cp -Rp "$i" "$j"
done
diffR=$(diff -r "__INSTALL_3DIGIT__"/hcatalog/etc/webhcat "__INSTALL_3DIGIT__"/hcatalog/etc/webhcat.new | grep "^Only in " | grep "webhcat.new" | sed "s/^Only in //" | sed "s/: /\//")
for i in ${diffR}; do
    j=$(echo "$i" | sed 's/webhcat.new/webhcat/g')
    cp -Rp "$i" "$j"
done
rm -f  __PREFIX__/bin/hive
ln -sf __INSTALL_3DIGIT__/bin/hive __PREFIX__/bin/hive
ln -sf __INSTALL_3DIGIT__/bin/hive /usr/bin/hive

rm -f  __PREFIX__/bin/hcat
ln -sf __INSTALL_3DIGIT__/hcatalog/bin/hcat __PREFIX__/bin/hcat
ln -sf __INSTALL_3DIGIT__/hcatalog/bin/hcat /usr/bin/hcat

rm -f  __PREFIX__/bin/webhcat_server
ln -sf __INSTALL_3DIGIT__/hcatalog/sbin/webhcat_server.sh __PREFIX__/bin/webhcat_server
ln -sf __INSTALL_3DIGIT__/hcatalog/sbin/webhcat_server.sh /usr/bin/webhcat_server

#
# create logs directory and grant permissions
#
mkdir -p __INSTALL_3DIGIT__/logs
chmod 1777 __INSTALL_3DIGIT__/logs
##
## end of post install section
##

%preun

%postun

#
# If the package is being purged/uninstalled,
# the remove entire directory.
# (We may need to remove $MAPR_HOME_/hive/hive-X/.)
#
VERSION_FILE_CONTENT=$(cat __PREFIX__/hive/hiveversion 2>/dev/null)
CURRENT_VERSION=__VERSION_3DIGIT__
if [ "$1" = "0" ] || [ \( -n "$VERSION_FILE_CONTENT" -a "$CURRENT_VERSION" != "$VERSION_FILE_CONTENT" \) ]; then
  rm -Rf __INSTALL_3DIGIT__
  shopt -s nullglob
  HIVE_PID_FILES=( __PREFIX__/pid/hive*pid )
  for HIVE_PID_FILE in "${HIVE_PID_FILES[@]}"; do
    if [ -f "${HIVE_PID_FILE}" ]; then
      rm "${HIVE_PID_FILE}"
    fi
  done
fi
if [ "$1" = "0" ]; then
  rm -f __PREFIX__/bin/hive
  rm -f /usr/bin/hive
fi
##
## end of post uninstall section
##

##
## post transaction section
##
# %posttrans
