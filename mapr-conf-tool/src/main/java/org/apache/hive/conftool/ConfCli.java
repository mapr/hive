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
package org.apache.hive.conftool;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;

public class ConfCli {
  private ConfCli(){}
  private static final Logger LOG = LoggerFactory.getLogger(ConfCli.class.getName());
  private static final Options CMD_LINE_OPTIONS = new Options();
  private static final HelpFormatter HELP_FORMATTER = new HelpFormatter();
  private static final String HELP = "help";
  private static final String SECURE = "secure";
  private static final String UNSECURE = "unsecure";
  private static final String PATH = "path";
  private static final String TOOL_NAME = "conftool";
  private static final String HS2_HA = "hs2ha";
  private static final String ZK_QUORUM = "zkquorum";
  private static final String INIT_META_STORE_URI = "initMetastoreUri";
  private static final String CONNECTION_URL = "connurl";
  private static final String REMOVE_PASSWORD_PROPERTY = "removePasswordProperty";
  private static final String WEB_UI_PAM_SSL = "webuipamssl";
  private static final String IS_CONFIGURED = "isConfigured";
  private static final String WEBHCAT_SSL = "webhcatssl";

  static {
    OptionBuilder.hasArg(false);
    OptionBuilder.withDescription("Print help information");
    CMD_LINE_OPTIONS.addOption(OptionBuilder.create(HELP));

    OptionBuilder.hasArg(false);
    OptionBuilder.withDescription("Configures hive-site.xml for MapR-SASL security");
    CMD_LINE_OPTIONS.addOption(OptionBuilder.create(SECURE));

    OptionBuilder.hasArg(false);
    OptionBuilder.withDescription("Disables MapR-SASL security in hive-site.xml");
    CMD_LINE_OPTIONS.addOption(OptionBuilder.create(UNSECURE));

    OptionBuilder.hasArg();
    OptionBuilder.withArgName("path to xml file");
    OptionBuilder.withDescription("Path to xml file to configure (hive-site.xml, webhcat-site"
        + ".xml etc).");
    CMD_LINE_OPTIONS.addOption(OptionBuilder.create(PATH));

    OptionBuilder.hasArg(false);
    OptionBuilder.withDescription("Configures hive-site.xml for HiveServer2 High Availability");
    CMD_LINE_OPTIONS.addOption(OptionBuilder.create(HS2_HA));

    OptionBuilder.hasArg(false);
    OptionBuilder.withDescription("Initializes Hive metastore Uri to run on local host");
    CMD_LINE_OPTIONS.addOption(OptionBuilder.create(INIT_META_STORE_URI));

    OptionBuilder.hasArg();
    OptionBuilder.withArgName("quorum");
    OptionBuilder.withDescription("Hive Zookeeper Quorum");
    CMD_LINE_OPTIONS.addOption(OptionBuilder.create(ZK_QUORUM));

    OptionBuilder.hasArg();
    OptionBuilder.withArgName("connection-url");
    OptionBuilder.withDescription("Metastore DB connection URL");
    CMD_LINE_OPTIONS.addOption(OptionBuilder.create(CONNECTION_URL));

    OptionBuilder.hasArg(false);
    OptionBuilder.withDescription("Remove ConnectionPassword property from hive-site");
    CMD_LINE_OPTIONS.addOption(OptionBuilder.create(REMOVE_PASSWORD_PROPERTY));

    OptionBuilder.hasArg(false);
    OptionBuilder.withDescription("Configures hive-site.xml for HiveServer2 web UI PAM authentication and SSL encryption");
    CMD_LINE_OPTIONS.addOption(OptionBuilder.create(WEB_UI_PAM_SSL));

    OptionBuilder.hasArg(false);
    OptionBuilder.withDescription("Configures webhcat-site.xml for webHCat SSL encryption");
    CMD_LINE_OPTIONS.addOption(OptionBuilder.create(WEBHCAT_SSL));


    OptionBuilder.hasArg();
    OptionBuilder.withArgName("property to check");
    OptionBuilder.withDescription("Checks if property is set in xml file.");
    CMD_LINE_OPTIONS.addOption(OptionBuilder.create(IS_CONFIGURED));

  }

  public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException, TransformerException {
    CommandLineParser cmdParser = new GnuParser();
    CommandLine line = null;
    try {
      line = cmdParser.parse(ConfCli.CMD_LINE_OPTIONS, args);
    } catch (ParseException e) {
      LOG.error( "{} : parsing failed.  Reason: {}", TOOL_NAME, e.getLocalizedMessage());
      printHelp();
    }
    if(line == null){
      LOG.error("{} : parsing failed.  Reason: unknown", TOOL_NAME);
      return;
    }
    if (line.hasOption(HELP)) {
      printHelp();
    } else if(line.hasOption(PATH)){
      String pathToXmlFile = line.getOptionValue(PATH);
      if(isSecurityConfig(line)){
        if(hasValidSecurityOptions(line)){
          configureSecurity(pathToXmlFile, getSecurity(line));
        } else {
          printHelp();
        }
      }

      if(isHs2HaConfig(line)){
        if(hasValidHs2HaOptions(line)){
          String zookeeperQuorum = line.getOptionValue(ZK_QUORUM);
          ConfTool.enableHs2Ha(pathToXmlFile, zookeeperQuorum);
        } else {
          printHelp();
        }
      }

      if(isMetaStoreUriConfig(line)){
        ConfTool.initMetaStoreUri(pathToXmlFile);
      }

      if(hasIsConfiguredOption(line)){
        String property = line.getOptionValue(IS_CONFIGURED);
        printBool(ConfTool.isConfigured(pathToXmlFile, property));
      }

      if(isConnectionUrlConfig(line)){
        if(hasValidConnectionUrlOptions(line)){
          String connectionUrl = line.getOptionValue(CONNECTION_URL);
          ConfTool.setConnectionUrl(pathToXmlFile, connectionUrl);
        } else {
          printHelp();
        }
      }

      if (isRemoveProperty(line)) {
        ConfTool.removeConnectionPasswordProperty(pathToXmlFile);
      }

      if(isWebUiHs2PamSslConfig(line)){
        ConfTool.setHs2WebUiPamSsl(pathToXmlFile);
      }

      if(isWeHCatSslConfig(line)){
        ConfTool.setWebHCatSsl(pathToXmlFile);
      }

    } else {
      printHelp();
    }
  }

  private static boolean isConnectionUrlConfig(CommandLine line){
    return line.hasOption(CONNECTION_URL);
  }

  private static boolean hasValidConnectionUrlOptions(CommandLine line){
    return !line.getOptionValue(CONNECTION_URL).isEmpty();
  }


  private static boolean isSecurityConfig(CommandLine line){
    return line.hasOption(SECURE) || line.hasOption(UNSECURE);
  }

  private static boolean hasValidSecurityOptions(CommandLine line){
    return !(line.hasOption(SECURE) && line.hasOption(UNSECURE));
  }

  private static void configureSecurity(String pathToHiveSite, boolean security) throws IOException, ParserConfigurationException, SAXException, TransformerException {
    ConfTool.setMaprSasl(pathToHiveSite, security);
    ConfTool.setEncryption(pathToHiveSite, security);
  }

  private static boolean getSecurity(CommandLine line){
    if(line.hasOption(SECURE)){
      return true;
    } else if (line.hasOption(UNSECURE)){
      return false;
    }
    return true; // never happens
  }

  private static void printHelp(){
    HELP_FORMATTER.printHelp(TOOL_NAME, CMD_LINE_OPTIONS);
  }

  private static boolean isHs2HaConfig(CommandLine line){
    return line.hasOption(HS2_HA);
  }

  private static boolean isMetaStoreUriConfig(CommandLine line){
    return line.hasOption(INIT_META_STORE_URI);
  }

  private static boolean hasValidHs2HaOptions(CommandLine line){
    return line.hasOption(HS2_HA) && !line.getOptionValue(ZK_QUORUM).isEmpty();
  }

  /**
   * Returns true if an input param is "-removePasswordProperty"
   * @param line input param
   * @return
   */
  private static boolean isRemoveProperty(CommandLine line) {
    return line.hasOption(REMOVE_PASSWORD_PROPERTY);
  }

  private static boolean isWebUiHs2PamSslConfig(CommandLine line) {
    return line.hasOption(WEB_UI_PAM_SSL);
  }

  private static boolean hasIsConfiguredOption(CommandLine line) {
    return line.hasOption(IS_CONFIGURED);
  }

  private static boolean isWeHCatSslConfig(CommandLine line) {
    return line.hasOption(WEBHCAT_SSL);
  }

  private static void printBool(boolean value){
    System.out.print(Boolean.toString(value));
  }
}