/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.hadoop.hive.metastore.tools;

import org.apache.commons.cli.*;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.security.alias.CredentialProvider;
import org.apache.hadoop.security.alias.CredentialProviderFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * This class provide Hive admins to encrypt
 * properties stored in hive-site.xml
 */
public final class HiveEncryptTool {

  private static final Logger LOG = LoggerFactory.getLogger(HiveEncryptTool.class.getName());
  private final Options cmdLineOptions = new Options();
  private HiveEncryptTool() {}

  @SuppressWarnings("static-access")
  private void init() {
    System.out.println("Initializing HiveEncryptTool");

    cmdLineOptions.addOption(OptionBuilder
      .hasArg(false)
      .withDescription("Print help information")
      .create("help"));

    cmdLineOptions.addOption(OptionBuilder
      .hasArg()
      .withArgName("keystorename")
      .withDescription("Create KeyStore to store properties from hive-site.xml using valid path in MapRFS")
      .create("keyStorePath"));

    cmdLineOptions.addOption(OptionBuilder
      .withValueSeparator()
      .hasArgs(2)
      .withArgName("property=value")
      .withDescription("Key, value of property that should be written to keystore")
      .create("property"));
  }

  private static void printAndExit(Options cmdLineOptions) {
    HelpFormatter formatter = new HelpFormatter();
    formatter.printHelp("encryptconf", cmdLineOptions);
    System.exit(1);
  }

  private static void encryptPassword(String propertyName, String propertyValue, String pathToKeyStore) throws IOException {
    Configuration configuration = new Configuration();
    String keyStorePath = "jceks://maprfs/" + pathToKeyStore;
    configuration.set("hadoop.security.credential.clear-text-fallback", "true");
    configuration.set("hadoop.security.credential.provider.path", keyStorePath);

    LOG.debug("Creating alias " + propertyName + " under " + pathToKeyStore);

    CredentialProvider provider = CredentialProviderFactory.getProviders(configuration).get(0);
    provider.createCredentialEntry(propertyName, propertyValue.toCharArray());
    provider.flush();
  }

  public static void main(String[] args) {
    HiveEncryptTool hiveEncryptTool = new HiveEncryptTool();
    hiveEncryptTool.init();
    CommandLineParser cmdParser = new GnuParser();
    CommandLine line = null;

    try {
      line = cmdParser.parse(hiveEncryptTool.cmdLineOptions, args);
    } catch (ParseException e) {
      System.err.println("HiveEncryptTool:Parsing failed.  Reason: " + e.getLocalizedMessage());
      printAndExit(hiveEncryptTool.cmdLineOptions);
    }

    if (line == null){
      printAndExit(hiveEncryptTool.cmdLineOptions);
    } else if (line.hasOption("help")) {
      HelpFormatter formatter = new HelpFormatter();
      formatter.printHelp("encryptconf", hiveEncryptTool.cmdLineOptions);
    } else if (line.hasOption("keyStorePath") && line.hasOption("property")) {
      String[] hiveVars = line.getOptionValues("property");

      if (hiveVars.length != 2) {
        System.err.println("HiveEncryptTool:property takes in 2 required arguments separated by = ;" +
          "was passed " + hiveVars.length + " arguments");
        printAndExit(hiveEncryptTool.cmdLineOptions);
      }

      String propertyName = hiveVars[0];
      String propertyValue = hiveVars[1];
      String pathToKeyStore = line.getOptionValue("keyStorePath");

      try {
        System.out.println("Creating keystore... ");
        HiveEncryptTool.encryptPassword(propertyName, propertyValue, pathToKeyStore);
      } catch (IOException e) {
        System.err.println("HiveEncryptTool:Error while creating credential keystore. Reason: " + e.getLocalizedMessage());
        printAndExit(hiveEncryptTool.cmdLineOptions);
      }

      System.out.println("KeyStore successfully created");

    } else {
      System.err.print("HiveEncryptTool:Parsing failed.  Reason: Invalid arguments: " );
      for (String s : line.getArgs()) {
        System.err.println(s + " ");
      }
      System.err.println();
    }
  }
}