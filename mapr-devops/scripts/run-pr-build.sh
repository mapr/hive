#!/usr/bin/env bash
set -e

if [ -z "${gitURL}" ]; then
    echo "Need to set gitURL"
    exit 1
fi

if [ -z "${gitBranch}" ]; then
    echo "Need to set gitBranch"
    exit 1
fi

if [ "${pr}" == true ]; then
    prPrefix="pull/${gitBranch}/head:"
    gitBranch="prbranch${gitBranch}"
fi

mkdir -p mapr-hive
cd mapr-hive
git clone ${gitURL}
cd private-hive
git fetch origin ${prPrefix}${gitBranch}
git checkout ${gitBranch}

echo "Test Running ..... "

export MAPR_MIRROR=central
export MAVEN_CENTRAL=http://maven.corp.maprtech.com/nexus/content/groups/public/
export MAPR_CENTRAL=http://maven.corp.maprtech.com/nexus/content/groups/public/
export MAPR_SNAPSHOTS_REPO=http://maven.corp.maprtech.com/nexus/content/repositories/snapshots/
export MAPR_MAVEN_REPO=${MAPR_SNAPSHOTS_REPO}
export MAPR_RELEASES_REPO=http://maven.corp.maprtech.com/nexus/content/repositories/releases/

mvn clean install -DskipTests
mvn clean install -T 6 -fae -pl \!ql
mvn surefire-report:report-only -Daggregate=true
