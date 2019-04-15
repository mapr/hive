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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;

import static org.apache.hive.conftool.ConfToolUtil.*;

/**
 CLI manager to configure Hive components.
 */
public final class ConfCli {
  private ConfCli() {
  }

  private static final Logger LOG = LoggerFactory.getLogger(ConfCli.class.getName());
  private static final Options CMD_LINE_OPTIONS = new Options();
  private static final HelpFormatter HELP_FORMATTER = new HelpFormatter();
  private static final String HELP = "help";
  private static final String MAPR_SASL = "maprsasl";
  private static final String SECURITY = "security";
  private static final String PATH = "path";
  private static final String TOOL_NAME = "conftool";
  private static final String HS2_HA = "hs2ha";
  private static final String ZK_QUORUM = "zkquorum";
  private static final String INIT_META_STORE_URI = "initMetastoreUri";
  private static final String CONNECTION_URL = "connurl";
  private static final String WEB_UI_PAM_SSL = "webuipamssl";
  private static final String EXIST_PROPERTY = "existProperty";
  private static final String WEBHCAT_SSL = "webhcatssl";
  private static final String HS2_SSL = "hs2ssl";
  private static final String METASTORE_SSL = "hmetassl";
  private static final String ADMIN_USER = "adminUser";
  private static final String ADD_PROPERTY = "addProperty";
  private static final String APPEND_PROPERTY = "appendProperty";
  private static final String RESTRICTED_LIST = "restrictedList";
  private static final String DEL_PROPERTY = "delProperty";
  private static final String GET_PROPERTY = "getProperty";
  private static final String FALLBACK_AUTHORIZER = "fallBackAuthorizer";
  private static final String HIVE_SERVER2_METRICS_ENABLED = "hiveserver2_metrics_enabled";
  private static final String METASTORE_METRICS_ENABLED = "metastore_metrics_enabled";
  private static final String REPORTER_ENABLED = "reporter_enabled";
  private static final String METRICS_REPORTER_TYPE = "reporter_type";
  private static final String JSON_JMX_METASTORE_METRICS_FILE_LOCATION = "hmeta_report_file";
  private static final String JSON_JMX_HIVE_SERVER2_METRICS_FILE_LOCATION = "hs2_report_file";

  static {
    OptionBuilder.hasArg(false);
    OptionBuilder.withDescription("Print help information");
    CMD_LINE_OPTIONS.addOption(OptionBuilder.create(HELP));

    OptionBuilder.hasArg(false);
    OptionBuilder.withDescription("Configures hive-site.xml for MapR-SASL security");
    CMD_LINE_OPTIONS.addOption(OptionBuilder.create(MAPR_SASL));

    OptionBuilder.hasArg();
    OptionBuilder.withArgName("true or false for security");
    OptionBuilder.withDescription("Shows current status of security");
    CMD_LINE_OPTIONS.addOption(OptionBuilder.create(SECURITY));

    OptionBuilder.hasArg();
    OptionBuilder.withArgName("path to xml file");
    OptionBuilder.withDescription("Path to xml file to configure (hive-site.xml, webhcat-site" + ".xml etc).");
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
    OptionBuilder
        .withDescription("Configures hive-site.xml for HiveServer2 web UI PAM authentication and SSL encryption");
    CMD_LINE_OPTIONS.addOption(OptionBuilder.create(WEB_UI_PAM_SSL));

    OptionBuilder.hasArg(false);
    OptionBuilder.withDescription("Configures webhcat-site.xml for webHCat SSL encryption");
    CMD_LINE_OPTIONS.addOption(OptionBuilder.create(WEBHCAT_SSL));

    OptionBuilder.hasArg(false);
    OptionBuilder.withDescription("Configures hive-site.xml for HS2 SSL encryption");
    CMD_LINE_OPTIONS.addOption(OptionBuilder.create(HS2_SSL));

    OptionBuilder.hasArg(false);
    OptionBuilder.withDescription("Configures hive-site.xml for Hive Metastore SSL encryption");
    CMD_LINE_OPTIONS.addOption(OptionBuilder.create(METASTORE_SSL));

    OptionBuilder.withValueSeparator();
    OptionBuilder.hasArgs(1);
    OptionBuilder.withArgName("user name");
    OptionBuilder.withDescription("Admin user will be appended to existing property(hive.user.in.admin.role) "
        + "using coma separator. In case it does not exist, it will be added as a new value.");
    CMD_LINE_OPTIONS.addOption(OptionBuilder.create(ADMIN_USER));

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

    OptionBuilder.withValueSeparator();
    OptionBuilder.hasArgs(2);
    OptionBuilder.withArgName("property=appendedValue");
    OptionBuilder.withDescription("Value will be appended to existing property using coma separator. In "
        + "case it does not exist, it will be added as a new value.");
    CMD_LINE_OPTIONS.addOption(OptionBuilder.create(APPEND_PROPERTY));

    OptionBuilder.hasArg(false);
    OptionBuilder.withDescription("Configures restricted list of options that are immutable at runtime");
    CMD_LINE_OPTIONS.addOption(OptionBuilder.create(RESTRICTED_LIST));

    OptionBuilder.hasArg(false);
    OptionBuilder.withDescription("Configures FallbackHiveAuthorizerFactory by default on MapR SASL cluster");
    CMD_LINE_OPTIONS.addOption(OptionBuilder.create(FALLBACK_AUTHORIZER));

    OptionBuilder.hasArg();
    OptionBuilder.withArgName("property-name");
    OptionBuilder.withDescription("Removes property from xml file");
    CMD_LINE_OPTIONS.addOption(OptionBuilder.create(DEL_PROPERTY));

    OptionBuilder.hasArg();
    OptionBuilder.withArgName("property-name");
    OptionBuilder.withDescription("Retrieves the property value.");
    CMD_LINE_OPTIONS.addOption(OptionBuilder.create(GET_PROPERTY));

    OptionBuilder.hasArg();
    OptionBuilder.withArgName("true/false value");
    OptionBuilder.withDescription("Enables/disables Hiveserver2 for collecting metrics");
    CMD_LINE_OPTIONS.addOption(OptionBuilder.create(HIVE_SERVER2_METRICS_ENABLED));

    OptionBuilder.hasArg();
    OptionBuilder.withArgName("true/false value");
    OptionBuilder.withDescription("Enables/disables Hive Metastore for collecting metrics");
    CMD_LINE_OPTIONS.addOption(OptionBuilder.create(METASTORE_METRICS_ENABLED));

    OptionBuilder.hasArg();
    OptionBuilder.withArgName("true/false value");
    OptionBuilder.withDescription("Enables/disables reporter for collecting metrics");
    CMD_LINE_OPTIONS.addOption(OptionBuilder.create(REPORTER_ENABLED));

    OptionBuilder.hasArg();
    OptionBuilder.withArgName("metrics reporter type");
    OptionBuilder.withDescription("Configures metrics reporter type");
    CMD_LINE_OPTIONS.addOption(OptionBuilder.create(METRICS_REPORTER_TYPE));

    OptionBuilder.hasArg();
    OptionBuilder.withArgName("metrics file location");
    OptionBuilder.withDescription("Configures HiveServer2 metrics output file location");
    CMD_LINE_OPTIONS.addOption(OptionBuilder.create(JSON_JMX_HIVE_SERVER2_METRICS_FILE_LOCATION));

    OptionBuilder.hasArg();
    OptionBuilder.withArgName("metrics file location");
    OptionBuilder.withDescription("Configures Metastore metrics output file location");
    CMD_LINE_OPTIONS.addOption(OptionBuilder.create(JSON_JMX_METASTORE_METRICS_FILE_LOCATION));
  }

  public static void main(String[] args)
      throws IOException, ParserConfigurationException, SAXException, TransformerException {
    CommandLineParser cmdParser = new GnuParser();
    CommandLine line = null;
    try {
      line = cmdParser.parse(CMD_LINE_OPTIONS, args);
    } catch (ParseException e) {
      printHelp();
      throw new IllegalArgumentException(TOOL_NAME + ": Parsing failed.  Reason: " + e.getLocalizedMessage());
    }
    if (line == null) {
      throw new IllegalArgumentException(TOOL_NAME + ": parsing failed.  Reason: unknown");
    }
    if (line.hasOption(HELP)) {
      printHelp();
    } else if (line.hasOption(PATH)) {
      String pathToXmlFile = line.getOptionValue(PATH);
      if (isSecurityConfig(line)) {
        configureSecurity(pathToXmlFile, getSecurity(line));
      }

      if (isHs2HaConfig(line)) {
        if (hasValidHs2HaOptions(line)) {
          String zookeeperQuorum = line.getOptionValue(ZK_QUORUM);
          ConfTool.enableHs2Ha(pathToXmlFile, zookeeperQuorum);
        } else {
          printHelp();
          throw new IllegalArgumentException("Incorrect HS2 HA configuration options");
        }
      }

      if (isMetaStoreUriConfig(line)) {
        ConfTool.initMetaStoreUri(pathToXmlFile);
      }

      if (isExistVerification(line)) {
        if (hasValidExistPropertyOptions(line)) {
          String property = line.getOptionValue(EXIST_PROPERTY);
          printBool(ConfTool.exists(pathToXmlFile, property));
        } else {
          printHelp();
          throw new IllegalArgumentException("Incorrect property verification options");
        }
      }

      if (isConnectionUrlConfig(line)) {
        if (hasValidConnectionUrlOptions(line)) {
          String connectionUrl = line.getOptionValue(CONNECTION_URL);
          ConfTool.setConnectionUrl(pathToXmlFile, connectionUrl);
        } else {
          printHelp();
          throw new IllegalArgumentException("Incorrect connection URL configuration options");
        }
      }

      if (isDelProperty(line)) {
        String property = line.getOptionValue(DEL_PROPERTY);
        ConfTool.delProperty(pathToXmlFile, property);
      }

      if (isAddProperty(line)) {
        String[] optionValues = line.getOptionValues(ADD_PROPERTY);
        if (optionValues.length == 2) {
          String property = optionValues[0];
          String value = optionValues[1];
          ConfTool.addProperty(pathToXmlFile, property, value);
        }
      }

      if (isAppendProperty(line)) {
        String[] optionValues = line.getOptionValues(APPEND_PROPERTY);
        if (optionValues.length == 2) {
          String property = optionValues[0];
          String value = optionValues[1];
          ConfTool.appendProperty(pathToXmlFile, property, value);
        }
      }

      if (isRestrictedList(line)) {
        ConfTool.setRestrictedList(pathToXmlFile, getSecurity(line));
      }

      if (isGetProperty(line)) {
        String property = line.getOptionValue(GET_PROPERTY);
        String propertyValue = ConfTool.getProperty(pathToXmlFile, property);
        if (!propertyValue.isEmpty()) {
          System.out.print(propertyValue);
        } else {
          throw new IllegalArgumentException("Property does not exist!");
        }
      }

      if (isWebUiHs2PamSslConfig(line)) {
        ConfTool.setHs2WebUiPamSsl(pathToXmlFile, getSecurity(line));
      }

      if (isWeHCatSslConfig(line)) {
        ConfTool.setWebHCatSsl(pathToXmlFile, getSecurity(line));
      }

      if (isHs2SslConfig(line)) {
        ConfTool.setHs2Ssl(pathToXmlFile, getSecurity(line));
      }

      if (isHMetaSslConfig(line)) {
        ConfTool.setHMetaSsl(pathToXmlFile, getSecurity(line));
      }

      if (isAdminUser(line)) {
        String adminUser = line.getOptionValue(ADMIN_USER);
        ConfTool.setAdminUser(pathToXmlFile, adminUser, getSecurity(line));
      }

      if (isFallbackAuthorizer(line)) {
        ConfTool.setFallbackAuthorizer(pathToXmlFile, getSecurity(line));
      }
      if (isHiveServer2Metrics(line)) {
        boolean isMetricsEnabled = Boolean.parseBoolean(line.getOptionValue(HIVE_SERVER2_METRICS_ENABLED));
        ConfTool.configureHs2Metrics(pathToXmlFile, isMetricsEnabled);
      }

      if (isMetasoreMetrics(line)) {
        boolean isMetricsEnabled = Boolean.parseBoolean(line.getOptionValue(METASTORE_METRICS_ENABLED));
        ConfTool.configureMetastoreMetrics(pathToXmlFile, isMetricsEnabled);
      }

      if (isReporterConfig(line)) {
        boolean isReporterEnabled = Boolean.parseBoolean(line.getOptionValue(REPORTER_ENABLED));
        if (isMetricsReporterType(line)) {
          String reporterType = line.getOptionValue(METRICS_REPORTER_TYPE);
          if (isNotNullNotEmpty(reporterType)) {
            ConfTool.configureMetricsReporterType(pathToXmlFile, isReporterEnabled, reporterType);
          } else {
            throw new IllegalArgumentException("Incorrect metrics reporter type: empty string");
          }
        }

        if (isHiveServer2MetricsReporterFileLocation(line)) {
          String fileLocation = line.getOptionValue(JSON_JMX_HIVE_SERVER2_METRICS_FILE_LOCATION);
          if (isNotNullNotEmpty(fileLocation)) {
            ConfTool.configureHiveServer2MetricsFileLocation(pathToXmlFile, isReporterEnabled, fileLocation);
          } else {
            throw new IllegalArgumentException("Incorrect metrics reporter file location: empty string");
          }
        }

        if (isMetastoreMetricsReporterFileLocation(line)) {
          String fileLocation = line.getOptionValue(JSON_JMX_METASTORE_METRICS_FILE_LOCATION);
          if (isNotNullNotEmpty(fileLocation)) {
            ConfTool.configureHiveMetastoreMetricsFileLocation(pathToXmlFile, isReporterEnabled, fileLocation);
          } else {
            throw new IllegalArgumentException("Incorrect metrics reporter file location: empty string");
          }
        }
      }

    } else {
      printHelp();
    }
  }

  private static boolean isConnectionUrlConfig(CommandLine line) {
    return line.hasOption(CONNECTION_URL);
  }

  private static boolean hasValidConnectionUrlOptions(CommandLine line) {
    return line.getOptionValue(CONNECTION_URL) != null && !line.getOptionValue(CONNECTION_URL).isEmpty();
  }

  private static boolean isSecurityConfig(CommandLine line) {
    return line.hasOption(MAPR_SASL);
  }

  private static boolean hasValidSecurityOptions(CommandLine line) {
    return line.hasOption(SECURITY) && isTrueOrFalseOrCustom(line.getOptionValue(SECURITY));
  }

  private static void configureSecurity(String pathToHiveSite, Security security)
      throws IOException, ParserConfigurationException, SAXException, TransformerException {
    ConfTool.setMaprSasl(pathToHiveSite, security);
    ConfTool.setEncryption(pathToHiveSite, security);
    ConfTool.setMetaStoreUgi(pathToHiveSite, security);
    ConfTool.setMetaStoreAuthManager(pathToHiveSite, security);
  }

  private static Security getSecurity(CommandLine line) {
    if (hasValidSecurityOptions(line)) {
      return Security.parse(line.getOptionValue(SECURITY));
    } else {
      printHelp();
      throw new IllegalArgumentException("Incorrect security configuration options");
    }
  }

  private static boolean isHs2HaConfig(CommandLine line) {
    return line.hasOption(HS2_HA);
  }

  private static boolean isMetaStoreUriConfig(CommandLine line) {
    return line.hasOption(INIT_META_STORE_URI);
  }

  private static boolean hasValidHs2HaOptions(CommandLine line) {
    return line.hasOption(HS2_HA) && line.getOptionValue(ZK_QUORUM) != null && !line.getOptionValue(ZK_QUORUM)
        .isEmpty();
  }

  private static boolean isDelProperty(CommandLine line) {
    return line.hasOption(DEL_PROPERTY);
  }

  private static boolean isAddProperty(CommandLine line) {
    return line.hasOption(ADD_PROPERTY);
  }

  private static boolean isAppendProperty(CommandLine line) {
    return line.hasOption(APPEND_PROPERTY);
  }

  private static boolean isRestrictedList(CommandLine line) {
    return line.hasOption(RESTRICTED_LIST);
  }

  private static boolean isGetProperty(CommandLine line) {
    return line.hasOption(GET_PROPERTY);
  }

  private static boolean isWebUiHs2PamSslConfig(CommandLine line) {
    return line.hasOption(WEB_UI_PAM_SSL);
  }

  private static boolean isExistVerification(CommandLine line) {
    return line.hasOption(EXIST_PROPERTY);
  }

  private static boolean hasValidExistPropertyOptions(CommandLine line) {
    return line.hasOption(EXIST_PROPERTY) && line.getOptionValue(EXIST_PROPERTY) != null && !line
        .getOptionValue(EXIST_PROPERTY).isEmpty();
  }

  private static boolean isWeHCatSslConfig(CommandLine line) {
    return line.hasOption(WEBHCAT_SSL);
  }

  private static boolean isHs2SslConfig(CommandLine line) {
    return line.hasOption(HS2_SSL);
  }

  private static boolean isHMetaSslConfig(CommandLine line) {
    return line.hasOption(METASTORE_SSL);
  }

  private static boolean isAdminUser(CommandLine line) {
    return line.hasOption(ADMIN_USER);
  }

  private static boolean isFallbackAuthorizer(CommandLine line) {
    return line.hasOption(FALLBACK_AUTHORIZER);
  }

  private static boolean isHiveServer2Metrics(CommandLine line) {
    return line.hasOption(HIVE_SERVER2_METRICS_ENABLED) && isTrueOrFalse(
        line.getOptionValue(HIVE_SERVER2_METRICS_ENABLED));
  }

  private static boolean isMetasoreMetrics(CommandLine line) {
    return line.hasOption(METASTORE_METRICS_ENABLED) && isTrueOrFalse(line.getOptionValue(METASTORE_METRICS_ENABLED));
  }

  private static boolean isReporterConfig(CommandLine line) {
    return line.hasOption(REPORTER_ENABLED) && isTrueOrFalse(line.getOptionValue(REPORTER_ENABLED));
  }

  private static boolean isMetricsReporterType(CommandLine line) {
    return line.hasOption(METRICS_REPORTER_TYPE);
  }

  private static boolean isMetastoreMetricsReporterFileLocation(CommandLine line) {
    return line.hasOption(JSON_JMX_METASTORE_METRICS_FILE_LOCATION);
  }

  private static boolean isHiveServer2MetricsReporterFileLocation(CommandLine line) {
    return line.hasOption(JSON_JMX_HIVE_SERVER2_METRICS_FILE_LOCATION);
  }

  private static void printHelp() {
    HELP_FORMATTER.printHelp(TOOL_NAME, CMD_LINE_OPTIONS);
  }
}
