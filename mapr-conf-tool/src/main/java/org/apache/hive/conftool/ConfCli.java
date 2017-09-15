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
    OptionBuilder.withArgName("path-to-hive-site");
    OptionBuilder.withDescription("Path to hive-site.xml");
    CMD_LINE_OPTIONS.addOption(OptionBuilder.create(PATH));
  }

  public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException, TransformerException {
    CommandLineParser cmdParser = new GnuParser();
    CommandLine line = null;
    try {
      line = cmdParser.parse(ConfCli.CMD_LINE_OPTIONS, args);
    } catch (ParseException e) {
      LOG.error( "{} : parsing failed.  Reason: {}", TOOL_NAME, e.getLocalizedMessage());
      HELP_FORMATTER.printHelp(TOOL_NAME, CMD_LINE_OPTIONS);
    }
    if(line == null){
      LOG.error("{} : parsing failed.  Reason: unknown", TOOL_NAME);
      return;
    }
    if (line.hasOption(HELP)) {
      HELP_FORMATTER.printHelp(TOOL_NAME, CMD_LINE_OPTIONS);
    } else if(line.hasOption(PATH)){
      boolean security = false;
      String pathToHiveSite = line.getOptionValue(PATH);
      if(line.hasOption(SECURE)){
        security = true;
      } else if (line.hasOption(UNSECURE)){
        security = false;
      } else {
        HELP_FORMATTER.printHelp(TOOL_NAME, CMD_LINE_OPTIONS);
      }
      ConfTool.setMaprSasl(pathToHiveSite, security);
      ConfTool.enableEncryption(pathToHiveSite, security);
    } else {
      HELP_FORMATTER.printHelp(TOOL_NAME, CMD_LINE_OPTIONS);
    }
  }
}
