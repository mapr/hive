/**
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

package org.apache.hive.conftool;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;

import static org.apache.hive.conftool.ConfTool.addProperty;
import static org.apache.hive.conftool.ConfTool.delProperty;
import static org.apache.hive.conftool.ConfTool.propertyExists;
import static org.apache.hive.conftool.ConfToolParseUtil.readDocument;
import static org.apache.hive.conftool.ConfToolParseUtil.saveToFile;
import static org.apache.hive.conftool.ConfToolUtil.printBool;

/**
 * This class is designed to process commands from command line interface.
 * It accepts following commands:
 * - existProperty,
 * - delProperty,
 * - addProperty
 */
public final class PropertyProcessor {
  private PropertyProcessor() {
  }

  private static final Options CMD_LINE_OPTIONS = new Options();
  private static final HelpFormatter HELP_FORMATTER = new HelpFormatter();
  private static final String HELP = "help";
  private static final String PATH = "path";
  private static final String TOOL_NAME = "conftool";
  private static final String EXIST_PROPERTY = "existProperty";
  private static final String ADD_PROPERTY = "addProperty";
  private static final String DEL_PROPERTY = "delProperty";

  static {
    OptionBuilder.hasArg(false);
    OptionBuilder.withDescription("Print help information");
    CMD_LINE_OPTIONS.addOption(OptionBuilder.create(HELP));

    OptionBuilder.hasArg();
    OptionBuilder.withArgName("path to xml file");
    OptionBuilder.withDescription("Path to xml file to configure (hive-site.xml, webhcat-site" + ".xml etc).");
    CMD_LINE_OPTIONS.addOption(OptionBuilder.create(PATH));

    OptionBuilder.hasArg();
    OptionBuilder.withArgName("property to check");
    OptionBuilder.withDescription("Checks if property is set in xml file.");
    CMD_LINE_OPTIONS.addOption(OptionBuilder.create(EXIST_PROPERTY));

    OptionBuilder.withValueSeparator();
    OptionBuilder.hasArgs(2);
    OptionBuilder.withArgName("property=value");
    OptionBuilder.withDescription("Key, value of property that should be written to xml file. In "
        + "case it already exists, it is replaced with new value. Property is added like: \n" + "<property>\n"
        + "  <name>property-name<\\name>\n" + "  <value>property-value<\\value>\n" + "<\\property>");
    CMD_LINE_OPTIONS.addOption(OptionBuilder.create(ADD_PROPERTY));

    OptionBuilder.hasArg();
    OptionBuilder.withArgName("property-name");
    OptionBuilder.withDescription("Removes property from xml file");
    CMD_LINE_OPTIONS.addOption(OptionBuilder.create(DEL_PROPERTY));
  }

  /**
   * This method process command line argument of conftool.
   * Used in HPE web installer.
   *
   * @param args arguments from command line
   */
  public static void processArgs(String[] args) {
    CommandLineParser cmdParser = new GnuParser();
    if (isValid(cmdParser, args)) {
      try {
        CommandLine line = cmdParser.parse(CMD_LINE_OPTIONS, args);
        if (hasPath(line)) {
          String pathToXmlFile = line.getOptionValue(PATH);
          Document xmlFile = readDocument(pathToXmlFile);
          processPropertyCmd(xmlFile, cmdParser.parse(CMD_LINE_OPTIONS, args));
          saveToFile(xmlFile, pathToXmlFile);
        } else {
          printHelp();
        }
      } catch (ParseException | ParserConfigurationException | IOException | SAXException | TransformerException e) {
        throw new ConfToolParseException(TOOL_NAME + ": Parsing failed.  Reason: " + e.getLocalizedMessage());
      }
    }
  }

  /**
   * Returns true if there is any option in args[] array that requires processing.
   *
   * @param args list of arguments to process
   * @return true if there is any option in args[] array that requires processing
   */
  public static boolean requiresArgProcessing(String[] args) {
    CommandLineParser cmdParser = new GnuParser();
    if (isValid(cmdParser, args)) {
      try {
        CommandLine line = cmdParser.parse(CMD_LINE_OPTIONS, args);
        return isExistVerification(line) || isDelProperty(line) || isAddProperty(line) || isHelp(line);
      } catch (ParseException e) {
        throw new ConfToolParseException(TOOL_NAME + ": Parsing failed.  Reason: " + e.getLocalizedMessage());
      }
    }
    return false;
  }

  /**
   * Checks a set of arguments is valid.
   *
   * it means it is parseble and does not contain help command.
   * @param cmdParser parser
   * @param args arguments
   * @return true if a set of arguments is valid.
   */
  private static boolean isValid(CommandLineParser cmdParser, String[] args) {
    CommandLine line;
    try {
      line = cmdParser.parse(CMD_LINE_OPTIONS, args);
    } catch (ParseException e) {
      printHelp();
      throw new ConfToolParseException(TOOL_NAME + ": Parsing failed.  Reason: " + e.getLocalizedMessage());
    }
    if (line == null) {
      throw new ConfToolParseException(TOOL_NAME + ": parsing failed.  Reason: unknown");
    }
    if (isHelp(line)) {
      printHelp();
    }
    return true;
  }

  /**
   * Here we process three types of argument from command line:
   *  - existProperty
   *  - delProperty
   *  - addProperty
   *
   * @param hiveSite parsed hive-site.xml
   * @param line parsed command line options
   */
  private static void processPropertyCmd(Document hiveSite, CommandLine line) {
    if (isExistVerification(line)) {
      if (hasValidExistPropertyOptions(line)) {
        String property = line.getOptionValue(EXIST_PROPERTY);
        printBool(propertyExists(hiveSite, property));
      } else {
        printHelp();
        throw new ConfToolParseException("Incorrect property verification options");
      }
    }

    if (isDelProperty(line)) {
      String property = line.getOptionValue(DEL_PROPERTY);
      delProperty(hiveSite, property);
    }

    if (isAddProperty(line)) {
      String[] optionValues = line.getOptionValues(ADD_PROPERTY);
      if (optionValues.length == 2) {
        String property = optionValues[0];
        String value = optionValues[1];
        addProperty(hiveSite, property, value);
      }
    }
  }

  private static boolean isExistVerification(CommandLine line) {
    return line.hasOption(EXIST_PROPERTY);
  }

  private static boolean hasPath(CommandLine line) {
    return line.hasOption(PATH);
  }

  private static boolean hasValidExistPropertyOptions(CommandLine line) {
    return line.hasOption(EXIST_PROPERTY) && line.getOptionValue(EXIST_PROPERTY) != null && !line.getOptionValue(
        EXIST_PROPERTY).isEmpty();
  }

  private static boolean isDelProperty(CommandLine line) {
    return line.hasOption(DEL_PROPERTY);
  }

  private static boolean isAddProperty(CommandLine line) {
    return line.hasOption(ADD_PROPERTY);
  }

  private static boolean isHelp(CommandLine line) {
    return line.hasOption(HELP);
  }

  private static void printHelp() {
    HELP_FORMATTER.printHelp(TOOL_NAME, CMD_LINE_OPTIONS);
  }
}
