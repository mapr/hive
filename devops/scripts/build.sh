#!/bin/bash
set -e

SCRIPT_DIR=$(dirname "${BASH_SOURCE[0]}")
. "${SCRIPT_DIR}/_initialize_package_variables.sh"
. "${SCRIPT_DIR}/_utils.sh"

cecho() {
  echo "--> $1"
}

build_hive() {
  cecho "Hive build start"
  cecho "GIT_COMMIT          : '$GIT_COMMIT'"
  cecho "INSTALLATION_PREFIX : '$INSTALLATION_PREFIX'"
  cecho "PKG_NAME            : '$PKG_NAME'"
  cecho "MVN_HIVE_VERSION    : '$MVN_HIVE_VERSION'"
  cecho "PKG_VERSION         : '$PKG_VERSION'"
  cecho "PKG_3DIGIT_VERSION  : '$PKG_3DIGIT_VERSION'"
  cecho "TIMESTAMP           : '$TIMESTAMP'"
  cecho "PKG_INSTALL_ROOT    : '$PKG_INSTALL_ROOT'"
  cecho "DIST_DIR            : '$DIST_DIR'"
  cecho "BUILD_ROOT          : '$BUILD_ROOT'"
  cecho "mvn_goals           : '$mvn_goals'"
  cecho "mvn_args            : '$mvn_args'"

  cecho "maven start"
  mvn ${mvn_goals} ${mvn_args}

  cecho "untar start"
  mkdir -p "${BUILD_ROOT}/build"
  package_archive="packaging/target/apache-hive-${MVN_HIVE_VERSION}-bin.tar.gz"

  # note #1: we need to strip one level because archive contains an extra root dir.
  # probably this was a hack for old pipelines, and we could just skip
  # that root dir during archive creation.
  # note #2: actually, could we just copy the folder that was just tarred?
  tar xvf ${package_archive} --strip-components=1 -C "${BUILD_ROOT}/build"

  cp -rv ext-conf/* ${BUILD_ROOT}/build/conf
  mv -v ${BUILD_ROOT}/build/conf ${BUILD_ROOT}/build/conf.new;
  mv -v ${BUILD_ROOT}/build/hcatalog/etc/webhcat ${BUILD_ROOT}/build/hcatalog/etc/webhcat.new;
}

main() {
  echo "Cleaning '${BUILD_ROOT}' dir..."
  rm -rf "${BUILD_ROOT}"

  # setting up maven
  # default hive building command: "mvn clean package -DskipTests -Pdist"
  mvn_goals="clean package"
  mvn_args="-DskipTests -Pdist"

  if [ "${DO_DEPLOY}" = "true" ] && [ "${OS}" = "redhat" ]; then
    echo "Deploy is enabled"
    mvn_goals+=" deploy"
    mvn_args+=" -DaltDeploymentRepository=deploy-repo-override::default::${MAPR_MAVEN_REPO}"
  fi

  echo "Building project..."
  build_hive

  echo "Preparing directory structure..."
  setup_role "mapr-hive"
  setup_role "mapr-hivemetastore"
  setup_role "mapr-hiveserver2"
  setup_role "mapr-hivewebhcat"

  setup_package "mapr-hive"

  echo "Building packages..."
  build_package "mapr-hive"
  build_package "mapr-hivemetastore"
  build_package "mapr-hiveserver2"
  build_package "mapr-hivewebhcat"

  echo "Resulting packages:"
  find "${DIST_DIR}" -exec readlink -f {} \;
}

main
