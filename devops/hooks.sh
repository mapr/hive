#!/usr/bin/env bash
source "interfaces.sh"

dev:setup() {
    git:hooks:install
    hive:setup
    log:warn "dev:setup in devops/hooks.sh has not been set up yet. It should completely prepare a new dev environment from scratch."
}

git:hooks:preCommit() {
    log:warn "git:hooks:preCommit in devops/hooks.sh has not been set up yet. It should perform quick local test to catch errors before running a full PR build"
}

git:pullRequest:onCodeChange() {
    set -e
    pipeline:verifyStandardChecks
    hive:setup
    export gitURL=git@github.com:mapr/private-hive.git
    export pr=true
    rm -rf mapr-devops/surefire-report.html
    ./bin/hive-build
    log:warn "git:pullRequest:onCodeChange in devops/hooks.sh has not been set up yet. It should perform the build and test steps for a Pull Request"
}

git:merge:onComplete() { local artifactoryCredentials=${artifactoryCredentials}
    log:warn "git:merge:onComplete in devops/hooks.sh has not been set up yet. It should ensure built, tested artifacts have been published to appropriate artifactory repositories"
}

hive:setup() {
    nvm:useNodeVersion "v8.1.4"
    cd devops/
    artifactory:credentials:prepareNpmRCFile
    mv ../.npmrc .
    npm install
    npm run build
}