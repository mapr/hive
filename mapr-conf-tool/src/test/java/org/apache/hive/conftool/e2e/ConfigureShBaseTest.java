/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to you under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.hive.conftool.e2e;

import org.apache.commons.io.FileUtils;
import org.apache.hive.conftool.TestConfToolUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.Map;

import static org.apache.hive.conftool.ConfTool.readAllProperties;
import static org.apache.hive.conftool.TestConfToolUtil.asPath;
import static org.apache.hive.conftool.TestConfToolUtil.copyFile;
import static org.apache.hive.conftool.TestConfToolUtil.copyFromResources;
import static org.apache.hive.conftool.TestConfToolUtil.mkdir;
import static org.apache.hive.conftool.TestConfToolUtil.normalize;
import static org.apache.hive.conftool.TestConfToolUtil.setExec;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public abstract class ConfigureShBaseTest {
  private String pathToConfigureSh;
  private String pathToHiveConf;
  private String pathToWebHCatConf;

  abstract String securityFlag();

  abstract Map<String, String> afterInstallHiveSite() throws IOException;

  abstract Map<String, String> afterUpdateHiveSite() throws IOException;

  abstract Map<String, String> afterInstallWebHCatSite() throws IOException;

  abstract Map<String, String> afterUpdateWebHCatSite() throws IOException;

  abstract void initSecurity(String mapRConfPath) throws IOException;

  @Before
  public void init() throws IOException {
    String mapRHome = System.getenv("MAPR_HOME");
    String bin = System.getenv("HIVE_BIN");
    String hiveVersion = TestConfToolUtil.readHiveVersion();
    String mapRConfPath = asPath(mapRHome, "conf");
    String mapRServerPath = asPath(mapRHome, "server");
    String hiveHome = asPath(mapRHome, "hive", "hive-" + hiveVersion);
    String hiveConfPath = asPath(hiveHome, "conf");
    String hiveLogsPath = asPath(hiveHome, "logs");
    String hiveBinPath = asPath(hiveHome, "bin");
    String hiveLibPath = asPath(hiveHome, "lib");
    String webHCatPath = asPath(hiveHome, "hcatalog", "etc", "webhcat");

    mkdir(mapRConfPath);
    mkdir(mapRServerPath);
    mkdir(hiveConfPath);
    mkdir(hiveLogsPath);
    mkdir(hiveBinPath);
    mkdir(hiveLibPath);
    mkdir(webHCatPath);

    initSecurity(mapRConfPath);
    copyFromResources(asPath("cluster-files", "common-ecosystem.sh"), asPath(mapRServerPath, "common-ecosystem.sh"));
    copyFromResources(asPath("cluster-files", "daemon.conf"), asPath(mapRConfPath, "daemon.conf"));
    copyFromResources(asPath("e2e", "hive-files", "hplsql-site.xml"), asPath(hiveConfPath, "hplsql-site.xml"));
    copyFromResources(asPath("e2e", "hive-files", "conftool-log4j2.properties"), asPath(hiveConfPath, "conftool-log4j2.properties"));
    copyFromResources(asPath("e2e", "hive-files", "hiveversion"), asPath(mapRHome, "hive", "hiveversion"));
    copyFile(asPath(bin, "configure.sh"), asPath(hiveBinPath, "configure.sh"));
    copyFile(asPath(bin, "conftool"), asPath(hiveBinPath, "conftool"));

    setExec(asPath(mapRServerPath, "common-ecosystem.sh"));
    setExec(asPath(hiveBinPath, "conftool"));
    setExec(asPath(hiveBinPath, "configure.sh"));

    pathToConfigureSh = asPath(hiveBinPath, "configure.sh");
    pathToHiveConf = hiveConfPath;
    pathToWebHCatConf = webHCatPath;
  }

  @After
  public void cleanUp() throws IOException {
    String mapRHome = System.getenv("MAPR_HOME");
    File mapRHomeDir = new File(mapRHome);
    if (mapRHomeDir.exists()) {
      FileUtils.deleteDirectory(mapRHomeDir);
    }
  }

  @Test
  public void afterInstallTest() throws IOException, InterruptedException, ParserConfigurationException, SAXException {
    configureFreshInstall();
    copyEmptyHiveSite();
    copyEmptyWebHCatSite();
    runConfigureSh();
    Map<String, String> actualHiveSite = readAllProperties(getPathToHiveSite());
    assertThat(actualHiveSite.keySet(), is(afterInstallHiveSite().keySet()));

    for (String key : actualHiveSite.keySet()) {
      assertThat(normalize(actualHiveSite.get(key)), is(normalize(afterInstallHiveSite().get(key))));
    }

    Map<String, String> actualWebHCatSite = readAllProperties(getPathToWebHCatSite());
    assertThat(actualWebHCatSite.keySet(), is(afterInstallWebHCatSite().keySet()));

    for (String key : actualHiveSite.keySet()) {
      assertThat(normalize(actualWebHCatSite.get(key)), is(normalize(afterInstallWebHCatSite().get(key))));
    }
  }

  @Test
  public void afterUpdateTest() throws IOException, InterruptedException, ParserConfigurationException, SAXException {
    copyCustomHiveSite();
    copyCustomWebHCatSite();
    runConfigureSh();
    Map<String, String> actualHiveSite = readAllProperties(getPathToHiveSite());
    assertThat(actualHiveSite.keySet(), is(afterUpdateHiveSite().keySet()));

    for (String key : actualHiveSite.keySet()) {
      assertThat(normalize(actualHiveSite.get(key)), is(normalize(afterUpdateHiveSite().get(key))));
    }

    Map<String, String> actualWebHCatSite = readAllProperties(getPathToWebHCatSite());
    assertThat(actualWebHCatSite.keySet(), is(afterUpdateWebHCatSite().keySet()));

    for (String key : actualHiveSite.keySet()) {
      assertThat(normalize(actualWebHCatSite.get(key)), is(normalize(afterUpdateWebHCatSite().get(key))));
    }
  }

  private void copyEmptyWebHCatSite() throws IOException {
    copyFromResources(asPath("e2e", "input", "empty-webhcat-site.xml"), getPathToWebHCatSite());
  }

  private void copyCustomWebHCatSite() throws IOException {
    copyFromResources(asPath("e2e", "input", "custom-webhcat-site.xml"), getPathToWebHCatSite());
  }

  private void copyEmptyHiveSite() throws IOException {
    copyFromResources(asPath("e2e", "input", "empty-hive-site.xml"), getPathToHiveSite());
  }

  private void copyCustomHiveSite() throws IOException {
    copyFromResources(asPath("e2e", "input", "custom-hive-site.xml"), getPathToHiveSite());
  }

  private void configureFreshInstall() throws IOException {
    copyFromResources(asPath("e2e", "hive-files", ".not_configured_yet"), asPath(pathToHiveConf, ".not_configured_yet"));
  }

  private String getPathToHiveSite() {
    return asPath(pathToHiveConf, "hive-site.xml");
  }

  private String getPathToWebHCatSite() {
    return asPath(pathToWebHCatConf,  "webhcat-site.xml");
  }

  private void runConfigureSh() throws IOException, InterruptedException {
    ProcessBuilder pb = new ProcessBuilder(pathToConfigureSh, securityFlag());

    Map<String, String> env = pb.environment();
    env.put("HADOOP_CLASSPATH", System.getenv("HADOOP_CLASSPATH"));
    env.put("BASEMAPR", System.getenv("BASEMAPR"));
    env.put("MAPR_HOME", System.getenv("MAPR_HOME"));
    env.put("MAPR_USER", System.getenv("MAPR_USER"));
    env.put("MAPR_GROUP", System.getenv("MAPR_GROUP"));
    Process process = pb.start();

    int exitVal = process.waitFor();
    if (exitVal != 0) {
      throw new RuntimeException("Error executing configure.sh");
    }
  }
}
