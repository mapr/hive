#!/usr/bin/env bash
artifactory:baseURL() {
	echo 'ERROR - attempted to call interface: artifactory:baseURL() {'; exit 1
}

artifactory:localFlatFiles:publish() { local localArtifactPath=$1; local remoteArtifactPath=$2;
	echo 'ERROR - attempted to call interface: artifactory:localFlatFiles:publish() { local localArtifactPath=$1; local remoteArtifactPath=$2;'; exit 1
}

artifactory:localFlatFiles:validateArtifactDoesNotExist() { local remoteArtifactPath=$1; local messageToShowIfError=$2
	echo 'ERROR - attempted to call interface: originalLine'; exit 1
}

artifactory:localFlatFiles:artifactExists() { local remoteArtifactPath=$1;
	echo 'ERROR - attempted to call interface: artifactory:localFlatFiles:artifactExists() { local remoteArtifactPath=$1;'; exit 1
}

artifactory:localFlatFiles:artifactDoesNotExist() { local remoteArtifactPath=$1;
	echo 'ERROR - attempted to call interface: artifactory:localFlatFiles:artifactDoesNotExist() { local remoteArtifactPath=$1;'; exit 1
}

artifactory:credentials:prepareNpmRCFile() {
	echo 'ERROR - attempted to call interface: artifactory:credentials:prepareNpmRCFile() {'; exit 1
}

bashCompletion:path() {
	echo 'ERROR - attempted to call interface: bashCompletion:path() {'; exit 1
}

bashCompletion:configDirectory() {
	echo 'ERROR - attempted to call interface: bashCompletion:configDirectory() {'; exit 1
}

bashCompletion:install() {
	echo 'ERROR - attempted to call interface: bashCompletion:install() {'; exit 1
}

brewCask:install() {
	echo 'ERROR - attempted to call interface: brewCask:install() {'; exit 1
}

brewCask:tap:versions() {
	echo 'ERROR - attempted to call interface: brewCask:tap:versions() {'; exit 1
}

browser:openURL() { local urlToOpen=$1
	echo 'ERROR - attempted to call interface: browser:openURL() { local urlToOpen=$1'; exit 1
}

bugzilla:issue:appearsInString() { local message=$1
	echo 'ERROR - attempted to call interface: bugzilla:issue:appearsInString() { local message=$1'; exit 1
}

pipeline:build() {
	echo 'ERROR - attempted to call interface: pipeline:build() {'; exit 1
}

pipeline:verifyStandardChecks() {
	echo 'ERROR - attempted to call interface: pipeline:verifyStandardChecks() {'; exit 1
}

curl:install() {
	echo 'ERROR - attempted to call interface: curl:install() {'; exit 1
}

directory:exists() { local path=$1
	echo 'ERROR - attempted to call interface: directory:exists() { local path=$1'; exit 1
}

directory:doesNotExist() { local path=$1
	echo 'ERROR - attempted to call interface: directory:doesNotExist() { local path=$1'; exit 1
}

directory:newTemporary() {
	echo 'ERROR - attempted to call interface: directory:newTemporary() {'; exit 1
}

docker:install() {
	echo 'ERROR - attempted to call interface: docker:install() {'; exit 1
}

dockerMachine:isHostDefined() {
	echo 'ERROR - attempted to call interface: dockerMachine:isHostDefined() {'; exit 1
}

dockerMachine:createHost() {
	echo 'ERROR - attempted to call interface: dockerMachine:createHost() {'; exit 1
}

dockerMachine:start() {
	echo 'ERROR - attempted to call interface: dockerMachine:start() {'; exit 1
}

dockerMachine:stop() {
	echo 'ERROR - attempted to call interface: dockerMachine:stop() {'; exit 1
}

dockerMachine:use() {
	echo 'ERROR - attempted to call interface: dockerMachine:use() {'; exit 1
}

dockerMachine:install() {
	echo 'ERROR - attempted to call interface: dockerMachine:install() {'; exit 1
}

dockerMachine:isRunning() {
	echo 'ERROR - attempted to call interface: dockerMachine:isRunning() {'; exit 1
}

dvm:install() {
	echo 'ERROR - attempted to call interface: dvm:install() {'; exit 1
}

dvm:install() {
	echo 'ERROR - attempted to call interface: dvm:install() {'; exit 1
}

dvm:loader:path() {
	echo 'ERROR - attempted to call interface: dvm:loader:path() {'; exit 1
}

dvm:loader:run() {
	echo 'ERROR - attempted to call interface: dvm:loader:run() {'; exit 1
}

dvm:makeAvailableInTerminal() {
	echo 'ERROR - attempted to call interface: dvm:makeAvailableInTerminal() {'; exit 1
}

file:exists() { local path=$1
	echo 'ERROR - attempted to call interface: file:exists() { local path=$1'; exit 1
}

file:doesNotExist() { local path=$1
	echo 'ERROR - attempted to call interface: file:doesNotExist() { local path=$1'; exit 1
}

file:contains() { local filePath=$1; local soughtString=$2
	echo 'ERROR - attempted to call interface: file:contains() { local filePath=$1; local soughtString=$2'; exit 1
}

file:doesNotContain() { local filePath=$1; local soughtString=$2
	echo 'ERROR - attempted to call interface: file:doesNotContain() { local filePath=$1; local soughtString=$2'; exit 1
}

file:replaceStringInFileContent() { local filePath=$1; local stringToReplace=$2; local replaceWithThisString=$3
	echo 'ERROR - attempted to call interface: originalLine'; exit 1
}

finder:showHiddenFiles() {
	echo 'ERROR - attempted to call interface: finder:showHiddenFiles() {'; exit 1
}

git:repositoryName() {
	echo 'ERROR - attempted to call interface: git:repositoryName() {'; exit 1
}

git:install() {
	echo 'ERROR - attempted to call interface: git:install() {'; exit 1
}

git:verifyPWDIsGitRoot() {
	echo 'ERROR - attempted to call interface: git:verifyPWDIsGitRoot() {'; exit 1
}

git:rootFolder() {
	echo 'ERROR - attempted to call interface: git:rootFolder() {'; exit 1
}

git:verifyCleanWorkingTree() {
	echo 'ERROR - attempted to call interface: git:verifyCleanWorkingTree() {'; exit 1
}

pipeline:isSelfExecuting() {
	echo 'ERROR - attempted to call interface: pipeline:isSelfExecuting() {'; exit 1
}

git:hooks:postRewrite() { local operationType=$1
	echo 'ERROR - attempted to call interface: git:hooks:postRewrite() { local operationType=$1'; exit 1
}

git:hooks:install() {
	echo 'ERROR - attempted to call interface: git:hooks:install() {'; exit 1
}

git:hooks:commitMessage() { local commitMessageTempFilePath=$1
	echo 'ERROR - attempted to call interface: git:hooks:commitMessage() { local commitMessageTempFilePath=$1'; exit 1
}

git:message:verify:unitTestClaim() { local message=$1
	echo 'ERROR - attempted to call interface: git:message:verify:unitTestClaim() { local message=$1'; exit 1
}

git:message:lowercaseFirst80Chars() { local message=$1
	echo 'ERROR - attempted to call interface: git:message:lowercaseFirst80Chars() { local message=$1'; exit 1
}

git:message:verify() { local message=$1
	echo 'ERROR - attempted to call interface: git:message:verify() { local message=$1'; exit 1
}

git:headCommit:message() {
	echo 'ERROR - attempted to call interface: git:headCommit:message() {'; exit 1
}

git:messageForSHA() { local gitSHA=$1
	echo 'ERROR - attempted to call interface: git:messageForSHA() { local gitSHA=$1'; exit 1
}

git:remotes:urlFor() { local remoteName=$1
	echo 'ERROR - attempted to call interface: git:remotes:urlFor() { local remoteName=$1'; exit 1
}

git:remotes:origin:httpURL() {
	echo 'ERROR - attempted to call interface: git:remotes:origin:httpURL() {'; exit 1
}

git:remotes:upstream:sshURL() {
	echo 'ERROR - attempted to call interface: git:remotes:upstream:sshURL() {'; exit 1
}

git:remotes:upstream:homePageURL() {
	echo 'ERROR - attempted to call interface: git:remotes:upstream:homePageURL() {'; exit 1
}

git:branch:create() { local branchName=$1
	echo 'ERROR - attempted to call interface: git:branch:create() { local branchName=$1'; exit 1
}

git:branch:current() {
	echo 'ERROR - attempted to call interface: git:branch:current() {'; exit 1
}

git:githubUserId() {
	echo 'ERROR - attempted to call interface: git:githubUserId() {'; exit 1
}

git:branch:markRestricted() { local branchName=$1
	echo 'ERROR - attempted to call interface: git:branch:markRestricted() { local branchName=$1'; exit 1
}

helloMinikube:echoServer:start() {
	echo 'ERROR - attempted to call interface: helloMinikube:echoServer:start() {'; exit 1
}

helloMinikube:echoServer:view() {
	echo 'ERROR - attempted to call interface: helloMinikube:echoServer:view() {'; exit 1
}

helm:install() {
	echo 'ERROR - attempted to call interface: helm:install() {'; exit 1
}

homebrew:install() {
	echo 'ERROR - attempted to call interface: homebrew:install() {'; exit 1
}

homebrew:update() {
	echo 'ERROR - attempted to call interface: homebrew:update() {'; exit 1
}

dev:setup() {
	echo 'ERROR - attempted to call interface: dev:setup() {'; exit 1
}

git:hooks:preCommit() {
	echo 'ERROR - attempted to call interface: git:hooks:preCommit() {'; exit 1
}

git:pullRequest:onCodeChange() {
	echo 'ERROR - attempted to call interface: git:pullRequest:onCodeChange() {'; exit 1
}

git:merge:onComplete() { local artifactoryCredentials=${artifactoryCredentials}
	echo 'ERROR - attempted to call interface: git:merge:onComplete() { local artifactoryCredentials=${artifactoryCredentials}'; exit 1
}

pipeline:install() { export enableGit
	echo 'ERROR - attempted to call interface: pipeline:install() { export enableGit'; exit 1
}

pipeline:install:confirmationMessage() {
	echo 'ERROR - attempted to call interface: pipeline:install:confirmationMessage() {'; exit 1
}

pipeline:install:interfaces() {
	echo 'ERROR - attempted to call interface: pipeline:install:interfaces() {'; exit 1
}

pipeline:install:hooks() {
	echo 'ERROR - attempted to call interface: pipeline:install:hooks() {'; exit 1
}

pipeline:install:selfExtractor() { export pipelineSelfExtractorPath
	echo 'ERROR - attempted to call interface: pipeline:install:selfExtractor() { export pipelineSelfExtractorPath'; exit 1
}

pipeline:install:bashCompletion() {
	echo 'ERROR - attempted to call interface: pipeline:install:bashCompletion() {'; exit 1
}

pipeline:install:branchWithInstallationChanges() {
	echo 'ERROR - attempted to call interface: pipeline:install:branchWithInstallationChanges() {'; exit 1
}

pipeline:install:branchToEnable() {
	echo 'ERROR - attempted to call interface: pipeline:install:branchToEnable() {'; exit 1
}

java:mac:install() {
	echo 'ERROR - attempted to call interface: java:mac:install() {'; exit 1
}

java:mac:v7:uploadToArtifactory() {
	echo 'ERROR - attempted to call interface: java:mac:v7:uploadToArtifactory() {'; exit 1
}

java:mac:v7:oracle:downloadURL() {
	echo 'ERROR - attempted to call interface: java:mac:v7:oracle:downloadURL() {'; exit 1
}

java:mac:v7:artifactory:downloadURL() {
	echo 'ERROR - attempted to call interface: java:mac:v7:artifactory:downloadURL() {'; exit 1
}

jenkins:master:url() {
	echo 'ERROR - attempted to call interface: jenkins:master:url() {'; exit 1
}

jenkins:pullRequestBuilder:shaToBuild() {
	echo 'ERROR - attempted to call interface: jenkins:pullRequestBuilder:shaToBuild() {'; exit 1
}

jenkins:credential() {
	echo 'ERROR - attempted to call interface: jenkins:credential() {'; exit 1
}

jenkins:host() {
	echo 'ERROR - attempted to call interface: jenkins:host() {'; exit 1
}

jenkins:crumb() { local credential=$1
	echo 'ERROR - attempted to call interface: jenkins:crumb() { local credential=$1'; exit 1
}

jenkins:pullRequestJob:create() { local credential=$1
	echo 'ERROR - attempted to call interface: jenkins:pullRequestJob:create() { local credential=$1'; exit 1
}

jenkins:job:create() { local jobTemplatePath=$1; local jobName=$2; local credential=$3
	echo 'ERROR - attempted to call interface: jenkins:job:create() { local jobTemplatePath=$1; local jobName=$2; local credential=$3'; exit 1
}

jenkins:job:urlWithCredentialForJobName() { local jobName=$1; local credential=$2
	echo 'ERROR - attempted to call interface: jenkins:job:urlWithCredentialForJobName() { local jobName=$1; local credential=$2'; exit 1
}

jenkins:job:urlWithoutCredentialForJobName() { local jobName=$1
	echo 'ERROR - attempted to call interface: jenkins:job:urlWithoutCredentialForJobName() { local jobName=$1'; exit 1
}

jenkins:mergeJob:create() { local credential=$1
	echo 'ERROR - attempted to call interface: jenkins:mergeJob:create() { local credential=$1'; exit 1
}

jenkins:pullRequestJob:name() {
	echo 'ERROR - attempted to call interface: jenkins:pullRequestJob:name() {'; exit 1
}

jenkins:mergeJob:name() {
	echo 'ERROR - attempted to call interface: jenkins:mergeJob:name() {'; exit 1
}

jenkins:pullRequestJob:url() {
	echo 'ERROR - attempted to call interface: jenkins:pullRequestJob:url() {'; exit 1
}

jenkins:mergeJob:url() {
	echo 'ERROR - attempted to call interface: jenkins:mergeJob:url() {'; exit 1
}

jenkins:jobs:list() {
	echo 'ERROR - attempted to call interface: jenkins:jobs:list() {'; exit 1
}

jenkins:isPresent() {
	echo 'ERROR - attempted to call interface: jenkins:isPresent() {'; exit 1
}

jenkins:isAbsent() {
	echo 'ERROR - attempted to call interface: jenkins:isAbsent() {'; exit 1
}

jira:issue:appearsInString() { local stringThatMightContainJIRAReference=$1
	echo 'ERROR - attempted to call interface: jira:issue:appearsInString() { local stringThatMightContainJIRAReference=$1'; exit 1
}

jq:install() {
	echo 'ERROR - attempted to call interface: jq:install() {'; exit 1
}

kubectl:install() {
	echo 'ERROR - attempted to call interface: kubectl:install() {'; exit 1
}

kubectl:bashCompletion:install() {
	echo 'ERROR - attempted to call interface: kubectl:bashCompletion:install() {'; exit 1
}

kubernetes:dashboard() {
	echo 'ERROR - attempted to call interface: kubernetes:dashboard() {'; exit 1
}

log:colors:green() {
	echo 'ERROR - attempted to call interface: log:colors:green() {'; exit 1
}

log:colors:red() {
	echo 'ERROR - attempted to call interface: log:colors:red() {'; exit 1
}

log:colors:yellow() {
	echo 'ERROR - attempted to call interface: log:colors:yellow() {'; exit 1
}

log:colors:orange() {
	echo 'ERROR - attempted to call interface: log:colors:orange() {'; exit 1
}

log:colors:default() {
	echo 'ERROR - attempted to call interface: log:colors:default() {'; exit 1
}

log:level:get() {
	echo 'ERROR - attempted to call interface: log:level:get() {'; exit 1
}

log:info() { local logMessage=$1
	echo 'ERROR - attempted to call interface: log:info() { local logMessage=$1'; exit 1
}

log:warn() { local logMessage=$1
	echo 'ERROR - attempted to call interface: log:warn() { local logMessage=$1'; exit 1
}

log:error() { local logMessage=$1
	echo 'ERROR - attempted to call interface: log:error() { local logMessage=$1'; exit 1
}

log:notImplemented() {
	echo 'ERROR - attempted to call interface: log:notImplemented() {'; exit 1
}

log:timestamp() {
	echo 'ERROR - attempted to call interface: log:timestamp() {'; exit 1
}

log:errorAndExit() { local errorMessage=$1
	echo 'ERROR - attempted to call interface: log:errorAndExit() { local errorMessage=$1'; exit 1
}

log:success() { local successMessage=$1
	echo 'ERROR - attempted to call interface: log:success() { local successMessage=$1'; exit 1
}

minikube:dashboard() {
	echo 'ERROR - attempted to call interface: minikube:dashboard() {'; exit 1
}

minikube:install() {
	echo 'ERROR - attempted to call interface: minikube:install() {'; exit 1
}

minikube:start() {
	echo 'ERROR - attempted to call interface: minikube:start() {'; exit 1
}

minikube:stop() {
	echo 'ERROR - attempted to call interface: minikube:stop() {'; exit 1
}

minikube:ip() {
	echo 'ERROR - attempted to call interface: minikube:ip() {'; exit 1
}

nodejs:version:actual() {
	echo 'ERROR - attempted to call interface: nodejs:version:actual() {'; exit 1
}

nodejs:someVersionIsInstalled() {
	echo 'ERROR - attempted to call interface: nodejs:someVersionIsInstalled() {'; exit 1
}

nodejs:version:expected() {
	echo 'ERROR - attempted to call interface: nodejs:version:expected() {'; exit 1
}

nodejs:expectedVersionIsInstalled() {
	echo 'ERROR - attempted to call interface: nodejs:expectedVersionIsInstalled() {'; exit 1
}

nodejs:install() {
	echo 'ERROR - attempted to call interface: nodejs:install() {'; exit 1
}

nodejs:version:isUnpublished() {
	echo 'ERROR - attempted to call interface: nodejs:version:isUnpublished() {'; exit 1
}

nvm:installDirectory() {
	echo 'ERROR - attempted to call interface: nvm:installDirectory() {'; exit 1
}

nvm:install() {
	echo 'ERROR - attempted to call interface: nvm:install() {'; exit 1
}

nvm:loader:create() {
	echo 'ERROR - attempted to call interface: nvm:loader:create() {'; exit 1
}

nvm:loader:path() {
	echo 'ERROR - attempted to call interface: nvm:loader:path() {'; exit 1
}

nvm:loader:run() {
	echo 'ERROR - attempted to call interface: nvm:loader:run() {'; exit 1
}

nvm:useNodeVersion() { local desiredNodeVersion=$1
	echo 'ERROR - attempted to call interface: nvm:useNodeVersion() { local desiredNodeVersion=$1'; exit 1
}

nvm:uninstall() {
	echo 'ERROR - attempted to call interface: nvm:uninstall() {'; exit 1
}

nvm:makeAvailableInTerminal() {
	echo 'ERROR - attempted to call interface: nvm:makeAvailableInTerminal() {'; exit 1
}

operatingSystem:packageManager() {
	echo 'ERROR - attempted to call interface: operatingSystem:packageManager() {'; exit 1
}

operatingSystem:isOsx() {
	echo 'ERROR - attempted to call interface: operatingSystem:isOsx() {'; exit 1
}

operatingSystem:isLinux() {
	echo 'ERROR - attempted to call interface: operatingSystem:isLinux() {'; exit 1
}

prompt:forConfirmation() { local promptText=$1
	echo 'ERROR - attempted to call interface: prompt:forConfirmation() { local promptText=$1'; exit 1
}

prompt:forPassword() { local promptText=$1
	echo 'ERROR - attempted to call interface: prompt:forPassword() { local promptText=$1'; exit 1
}

prompt:forValue() { local promptText=$1
	echo 'ERROR - attempted to call interface: prompt:forValue() { local promptText=$1'; exit 1
}

reflection:currentFunctionName() {
	echo 'ERROR - attempted to call interface: reflection:currentFunctionName() {'; exit 1
}

reflection:ancestorFunctionName() { local ancestorNumberWhere0IsCallingFunction=$1
	echo 'ERROR - attempted to call interface: reflection:ancestorFunctionName() { local ancestorNumberWhere0IsCallingFunction=$1'; exit 1
}

reflection:sortedPublicFunctions() {
	echo 'ERROR - attempted to call interface: reflection:sortedPublicFunctions() {'; exit 1
}

reflection:generateInterfaces() {
	echo 'ERROR - attempted to call interface: reflection:generateInterfaces() {'; exit 1
}

reflection:shouldInstall() { local programName=$1
	echo 'ERROR - attempted to call interface: reflection:shouldInstall() { local programName=$1'; exit 1
}

reflection:programExists() { local programName=$1
	echo 'ERROR - attempted to call interface: reflection:programExists() { local programName=$1'; exit 1
}

reflection:publicFunctionWithAbbreviation() {
	echo 'ERROR - attempted to call interface: reflection:publicFunctionWithAbbreviation() {'; exit 1
}

reflection:bashSourcesDirectory:set() { local thePathToBashSourceRoot=$1
	echo 'ERROR - attempted to call interface: reflection:bashSourcesDirectory:set() { local thePathToBashSourceRoot=$1'; exit 1
}

reflection:bashSourcesDirectory() {
	echo 'ERROR - attempted to call interface: reflection:bashSourcesDirectory() {'; exit 1
}

reflection:interfacesFilePath() {
	echo 'ERROR - attempted to call interface: reflection:interfacesFilePath() {'; exit 1
}

reflection:pipelineHooksFilePath() {
	echo 'ERROR - attempted to call interface: reflection:pipelineHooksFilePath() {'; exit 1
}

reflection:pipelineRootFolder() {
	echo 'ERROR - attempted to call interface: reflection:pipelineRootFolder() {'; exit 1
}

reflection:stackTrace() {
	echo 'ERROR - attempted to call interface: reflection:stackTrace() {'; exit 1
}

reflection:pipelineExecutablePath() {
	echo 'ERROR - attempted to call interface: reflection:pipelineExecutablePath() {'; exit 1
}

pipeline:version() {
	echo 'ERROR - attempted to call interface: pipeline:version() {'; exit 1
}

selfhosting:mount() {
	echo 'ERROR - attempted to call interface: selfhosting:mount() {'; exit 1
}

string:isEmpty() { local possiblyEmptyString=$1
	echo 'ERROR - attempted to call interface: string:isEmpty() { local possiblyEmptyString=$1'; exit 1
}

string:notEmpty() { local possiblyEmptyString=$1
	echo 'ERROR - attempted to call interface: string:notEmpty() { local possiblyEmptyString=$1'; exit 1
}

string:toLowercase() { local stringToConvert=$1
	echo 'ERROR - attempted to call interface: string:toLowercase() { local stringToConvert=$1'; exit 1
}

string:substring() { local fullString=$1; local startIndex=$2; local endIndex=$3
	echo 'ERROR - attempted to call interface: string:substring() { local fullString=$1; local startIndex=$2; local endIndex=$3'; exit 1
}

string:contains() { local stringToSearch=$1; local soughtString=$2
	echo 'ERROR - attempted to call interface: string:contains() { local stringToSearch=$1; local soughtString=$2'; exit 1
}

string:matchesRegex() { local stringToSearch=$1; local regularExpression=$2
	echo 'ERROR - attempted to call interface: string:matchesRegex() { local stringToSearch=$1; local regularExpression=$2'; exit 1
}

string:doesNotContain() { local stringToSearch=$1; local soughtString=$2
	echo 'ERROR - attempted to call interface: string:doesNotContain() { local stringToSearch=$1; local soughtString=$2'; exit 1
}

strings:areEqual() { local s1=$1; local s2=$2
	echo 'ERROR - attempted to call interface: strings:areEqual() { local s1=$1; local s2=$2'; exit 1
}

strings:areNotEqual() { local s1=$1; local s2=$2
	echo 'ERROR - attempted to call interface: strings:areNotEqual() { local s1=$1; local s2=$2'; exit 1
}

strings:replace() { local originalString=$1; local stringToReplace=$2; local replaceWithThisString=$3
	echo 'ERROR - attempted to call interface: originalLine'; exit 1
}

timing:shortWait() {
	echo 'ERROR - attempted to call interface: timing:shortWait() {'; exit 1
}

pipeline:update() {
	echo 'ERROR - attempted to call interface: pipeline:update() {'; exit 1
}

user:homeDirectory() {
	echo 'ERROR - attempted to call interface: user:homeDirectory() {'; exit 1
}

user:bashProfile:path() {
	echo 'ERROR - attempted to call interface: user:bashProfile:path() {'; exit 1
}

user:bashProfile:appendIfKeywordNotPresent() { local stringToAppendIfNotPresent=$1;
	echo 'ERROR - attempted to call interface: user:bashProfile:appendIfKeywordNotPresent() { local stringToAppendIfNotPresent=$1;'; exit 1
}

vagrant:install() {
	echo 'ERROR - attempted to call interface: vagrant:install() {'; exit 1
}

vagrant:scp:install() {
	echo 'ERROR - attempted to call interface: vagrant:scp:install() {'; exit 1
}

vagrant:osx:up() {
	echo 'ERROR - attempted to call interface: vagrant:osx:up() {'; exit 1
}

vagrant:osx:ssh() {
	echo 'ERROR - attempted to call interface: vagrant:osx:ssh() {'; exit 1
}

virtualbox:install() {
	echo 'ERROR - attempted to call interface: virtualbox:install() {'; exit 1
}

virtualboxExtension:brew:install() {
	echo 'ERROR - attempted to call interface: virtualboxExtension:brew:install() {'; exit 1
}

xcode:install() {
	echo 'ERROR - attempted to call interface: xcode:install() {'; exit 1
}

xcode:isInstalled() {
	echo 'ERROR - attempted to call interface: xcode:isInstalled() {'; exit 1
}

