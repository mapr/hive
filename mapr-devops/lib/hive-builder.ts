import {frameworkForNodeJSInstance} from "private-devops-automation-framework/dist/framework/nodejs/framework-for-node-js-instance";

async function buildHive() {
    let cluster = undefined;
    try {
        const frameworkInstance = frameworkForNodeJSInstance;
        const mesosEnv = "MesosDockerFarm";
        const buildTemplateId = `buildImageCentOS6`;
        const createSSHDirCmd = 'mkdir -p /root/.ssh';
        const copyKey = `cp /deployKeys/private-hive/git_key /root/.ssh`;
        const changePerm = `chmod 600 /root/.ssh/git_key`;
        const addToConfig = `echo -e "StrictHostKeyChecking no\nhost source.devops.lab\nHostName source.devops.lab\nIdentityFile /root/.ssh/git_key\nUser git" > /root/.ssh/config`;

        const gitURL = frameworkForNodeJSInstance.process.environmentVariableNamed(`gitURL`);
        const gitBranch = frameworkForNodeJSInstance.process.environmentVariables.hasKey(`ghprbPullId`)
            ? frameworkForNodeJSInstance.process.environmentVariableNamed(`ghprbPullId`)
            : frameworkForNodeJSInstance.process.environmentVariableNamed(`gitBranch`);

        const prValue = frameworkForNodeJSInstance.process.environmentVariables.hasKey(`pr`)
            ? `pr=${frameworkForNodeJSInstance.process.environmentVariableNamed(`pr`)}`
            : ``;

        const targetMesosEnvironment = frameworkInstance.docker.newMesosEnvironmentFromConfig(mesosEnv);
        cluster = await frameworkInstance.docker.newClusterTemplateFromConfig(buildTemplateId).provision(targetMesosEnvironment);

        const nodeIp = cluster.nodes.first.host;
        console.log(`IP address of launched machine : ${nodeIp}`);
        await cluster.nodes.first.executeShellCommand(createSSHDirCmd);
        await cluster.nodes.first.executeShellCommand(copyKey);
        await cluster.nodes.first.executeShellCommands(changePerm, addToConfig);
        await cluster.nodes.first.upload(__dirname + `/../scripts/run-pr-build.sh`, `/root/`);
        const buildRunResult = await cluster.nodes.first.newSSHSession().then(sshClient => sshClient.executeCommand(`gitURL=${gitURL} gitBranch=${gitBranch} ${prValue} sh /root/run-pr-build.sh`).onProgress(p => console.log(p.stdOut || p.stdErr)));
        await cluster.nodes.first.download(`/root/mapr-hive/private-hive/target/site/surefire-report.html`, `./`);
        console.log(`Test Result Status : ${buildRunResult.processResult.processExitCode}`);
        cluster.destroy();
    }
    catch (e) {
        if (cluster)
            cluster.destroy();
        console.log(e);
        process.exit(1);
    }
}

buildHive().catch(e => e.toString());
