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

import java.io.IOException;
import java.util.Map;

import static org.apache.hive.conftool.TestConfToolUtil.I;
import static org.apache.hive.conftool.TestConfToolUtil.copyFromResources;
import static org.apache.hive.conftool.TestConfToolUtil.readFrom;

public class ConfigureShMapRSaslTest extends ConfigureShBaseTest {

  @Override
  void initSecurity(String mapRConfPath) throws IOException {
    copyFromResources("maprsasl-enabled" + I + "mapr-clusters.conf", mapRConfPath + I + "mapr-clusters.conf");
    copyFromResources("maprsasl-enabled" + I + "mapruserticket", mapRConfPath + I + "mapruserticket");
    copyFromResources("maprsasl-enabled" + I + "ssl-client.xml", mapRConfPath + I + "ssl-client.xml");
  }

  @Override
  Map<String, String> afterInstallHiveSite() throws IOException {
    return readFrom("e2e" + I + "expected" + I + "mapr-sasl-hive-site-install.properties");
  }

  @Override
  Map<String, String> afterUpdateHiveSite() throws IOException {
    return readFrom("e2e" + I + "expected" + I + "mapr-sasl-hive-site-update.properties");
  }

  @Override
  Map<String, String> afterInstallWebHCatSite() throws IOException {
    return readFrom("e2e" + I + "expected" + I + "mapr-sasl-webhcat-site-install.properties");
  }

  @Override
  Map<String, String> afterUpdateWebHCatSite() throws IOException {
    return readFrom("e2e" + I + "expected" + I + "mapr-sasl-webhcat-site-update.properties");
  }

  @Override
  String securityFlag() {
    return "--secure";
  }
}