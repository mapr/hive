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

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import static org.apache.hive.conftool.ConfTool.propertyExists;
import static org.apache.hive.conftool.ConfToolParseUtil.readDocument;
import static org.apache.hive.conftool.TestConfToolUtil.getPath;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

public class ConfCliTest {

  @Rule public ExpectedException thrown = ExpectedException.none();

  private final ByteArrayOutputStream baos = new ByteArrayOutputStream();

  @Before
  public void init() {
    System.setOut(new PrintStream(baos));
  }

  private String output() {
    return baos.toString();
  }

  @Test
  public void printHelpTest() throws ParserConfigurationException, TransformerException, SAXException, IOException {
    ConfCli.main(new String[] { "--help" });
    assertThat(output(), containsString("Print help information"));
  }

  @Test
  public void addPropertyTest() throws ParserConfigurationException, TransformerException, SAXException, IOException {
    String pathToHiveSite = getPath("hive-site-018.xml");
    String property = "test.property";
    String value = "test.value";
    Document hiveSite = readDocument(pathToHiveSite);
    assertThat(propertyExists(hiveSite, property), is(false));
    ConfCli.main(new String[] { "--addProperty", property + "=" + value, "--path", pathToHiveSite });
    hiveSite = readDocument(pathToHiveSite);
    assertThat(propertyExists(hiveSite, property), is(true));
    assertThat(output(), not(containsString("Print help information")));
  }

  @Test
  public void addPropertyIncorrectArgumentsTest()
      throws ParserConfigurationException, TransformerException, SAXException, IOException {
    String pathToHiveSite = getPath("hive-site-022.xml");
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("Missing argument for option");
    ConfCli.main(new String[] { "--addProperty", "--path", pathToHiveSite });
    assertThat(output(), containsString("Print help information"));
  }

  @Test
  public void delPropertyTest() throws ParserConfigurationException, TransformerException, SAXException, IOException {
    String pathToHiveSite = getPath("hive-site-019.xml");
    String property = "test.property.to.delete";
    Document hiveSite = readDocument(pathToHiveSite);
    assertThat(propertyExists(hiveSite, property), is(true));
    ConfCli.main(new String[] { "--delProperty", property, "--path", pathToHiveSite });
    hiveSite = readDocument(pathToHiveSite);
    assertThat(propertyExists(hiveSite, property), is(false));
  }

  @Test
  public void delPropertyIncorrectArgumentsTest()
      throws ParserConfigurationException, TransformerException, SAXException, IOException {
    String pathToHiveSite = getPath("hive-site-022.xml");
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("Missing argument for option");
    ConfCli.main(new String[] { "--delProperty", "--path", pathToHiveSite });
    assertThat(output(), containsString("Print help information"));
  }

  @Test
  public void existPropertyTrueTest()
      throws ParserConfigurationException, TransformerException, SAXException, IOException {
    String pathToHiveSite = getPath("hive-site-022.xml");
    ConfCli.main(new String[] { "--existProperty", "datanucleus.schema.autoCreateAll", "--path", pathToHiveSite });
    assertThat(output(), containsString("true"));
  }

  @Test
  public void existPropertyFalseTest()
      throws ParserConfigurationException, TransformerException, SAXException, IOException {
    String pathToHiveSite = getPath("hive-site-022.xml");
    ConfCli.main(new String[] { "--existProperty", "no.such.property.test", "--path", pathToHiveSite });
    assertThat(output(), containsString("false"));
  }

  @Test
  public void existPropertyIncorrectArgumentsTest()
      throws ParserConfigurationException, TransformerException, SAXException, IOException {
    String pathToHiveSite = getPath("hive-site-022.xml");
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("Missing argument for option");
    ConfCli.main(new String[] { "--path", pathToHiveSite, "--existProperty" });
    assertThat(output(), containsString("Print help information"));
  }
}
