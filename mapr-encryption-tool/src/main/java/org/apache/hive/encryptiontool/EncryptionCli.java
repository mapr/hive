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
package org.apache.hive.encryptiontool;

import com.google.common.annotations.VisibleForTesting;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static org.apache.hive.encryptiontool.EncryptionTool.encryptPassword;

/**
 CLI manager to encrypt Hive passwords for components
 */
public class EncryptionCli {
  private EncryptionCli(){}

  static {
    System.setProperty("log4j.configurationFile", "log4j2.properties");
  }

  private static final Logger LOG = LoggerFactory.getLogger(EncryptionCli.class.getName());
  private static final HelpFormatter HELP_FORMATTER = new HelpFormatter();
  private static final String TOOL_NAME = "encryptconf";
  private static final Options CMD_LINE_OPTIONS = new Options();
  private static final String HELP = "help";
  private static final String KEY_STORE_NAME = "keystorename";
  private static final String KEY_STORE_PATH = "keyStorePath";
  private static final String PROPERTY = "property";
  private static final String EMPTY_STRING = "";
  @VisibleForTesting
  private static final String IN_TEST_MODE = "inTestMode";

  static
  {
    LOG.info("Initializing HiveEncryptTool");

    OptionBuilder.hasArg(false);
    OptionBuilder.withDescription("Print help information");
    CMD_LINE_OPTIONS.addOption(OptionBuilder.create(HELP));

    OptionBuilder.hasArg(false);
    OptionBuilder.withDescription("Used for testing only");
    CMD_LINE_OPTIONS.addOption(OptionBuilder.create(IN_TEST_MODE));

    OptionBuilder.hasArg();
    OptionBuilder.withArgName(KEY_STORE_NAME);
    OptionBuilder.withDescription("Create KeyStore to store properties from *.xml using valid "
        + "path in MapRFS");
    CMD_LINE_OPTIONS.addOption(OptionBuilder.create(KEY_STORE_PATH));

    OptionBuilder.withValueSeparator();
    OptionBuilder.hasArgs(2);
    OptionBuilder.withArgName("property=value");
    OptionBuilder.withDescription("Key, value of property that should be written to keystore");
    CMD_LINE_OPTIONS.addOption(OptionBuilder.create(PROPERTY));
  }


  private static void printHelp(){
    HELP_FORMATTER.printHelp(TOOL_NAME, CMD_LINE_OPTIONS);
  }

  public static void main(String[] args) {
    CommandLineParser cmdParser = new GnuParser();
    CommandLine line = null;
    try {
      line = cmdParser.parse(CMD_LINE_OPTIONS, args);
    } catch (ParseException e) {
      printHelp();
      throw new IllegalArgumentException(TOOL_NAME + ": Parsing failed.  Reason: " + e
          .getLocalizedMessage());
    }

    if(line == null){
      throw new IllegalArgumentException(TOOL_NAME + ": parsing failed.  Reason: unknown");
    }

    if (line.hasOption(HELP) || hasEmptyOptionsOnly(line)) {
      printHelp();
    } else if (isValidKeyStoreConfiguration(line)) {
      String[] hiveVars = line.getOptionValues(PROPERTY);

      if (hiveVars.length != 2) {
        printHelp();
        throw new IllegalArgumentException(TOOL_NAME + ": property takes in 2 required arguments "
            + "separated by = ;" +
            "was passed " + hiveVars.length + " arguments");
      }

      String propertyName = hiveVars[0];
      String propertyValue = hiveVars[1];
      String pathToKeyStore = line.getOptionValue(KEY_STORE_PATH);

      try {
        LOG.info("Creating keystore... ");
        if(!isTestMode(line)) {
          encryptPassword(propertyName, propertyValue, pathToKeyStore);
        } else {
          encryptPassword(propertyName, propertyValue, pathToKeyStore, true);

        }
      } catch (IOException e) {
        printHelp();
        throw new IllegalArgumentException(TOOL_NAME + ": Error while creating credential "
            + "keystore. Reason: " + e
            .getLocalizedMessage());
      }

      LOG.info("KeyStore successfully created");

    } if(noPropertyArgument(line)) {
      throw new IllegalArgumentException(TOOL_NAME + ": Invalid arguments. No -" + PROPERTY + " "
          + "argument.");
    } if (noKeyStoreArgument(line)) {
      throw new IllegalArgumentException(TOOL_NAME + ": Invalid arguments. No -" + KEY_STORE_PATH + " "
          + "argument.");
    }
  }

  private static boolean hasEmptyOptionsOnly(CommandLine line){
    Option options[] = line.getOptions();
    for (Option option : options){
      if (!option.getValue().isEmpty()){
        return false;
      }
    }
    return true;
  }

  private static boolean isValidKeyStoreConfiguration(CommandLine line){
    return line.hasOption(KEY_STORE_PATH) && line.hasOption(PROPERTY);
  }

  private static boolean noPropertyArgument(CommandLine line){
    return line.hasOption(KEY_STORE_PATH) && !line.hasOption(PROPERTY);
  }

  private static boolean noKeyStoreArgument(CommandLine line){
    return !line.hasOption(KEY_STORE_PATH) && line.hasOption(PROPERTY);
  }

  private static boolean isTestMode(CommandLine line){
    return line.hasOption(IN_TEST_MODE);
  }
}