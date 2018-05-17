package org.apache.hive.encryptiontool;

import org.apache.hadoop.fs.Path;
import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

public class EncryptionCliTest {
  private static final Logger LOG = LoggerFactory.getLogger(EncryptionCliTest.class.getName());
  private static String tmp = System.getProperty("java.io.tmpdir");

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @BeforeClass
  public static void setUp() {
    File tmpDir = new File(tmp);
    if (!tmpDir.exists()) {
      if(!tmpDir.mkdir()){
        Assert.fail("Error cretaing tmp dir: " + tmpDir);
      }
    }
  }

  @Test
  public void printHelpTest() {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    System.setOut(new PrintStream(baos));
    EncryptionCli.main(new String[]{"--help"});
    String output = baos.toString();
    Assert.assertTrue(output.contains("Print help information"));
  }

  @Test
  public void createKeyStoreTest() throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    System.setOut(new PrintStream(baos));
    String jceksFileName = "test.jceks";
    String pathToJceks = tmp + Path.SEPARATOR + jceksFileName;
    LOG.info("Path to jcek file: " + pathToJceks);
    String property = "test.property";
    String value = "test.value";
    EncryptionCli.main(new String[]{"--keyStorePath", pathToJceks, "--property", property + "=" +
    value, "--inTestMode"});
    File jceksFile = new File(pathToJceks);
    Assert.assertTrue(jceksFile.exists());
    Assert.assertFalse(jceksFile.isDirectory());
    Assert.assertEquals(value, EncryptionTool.getProperty(property, pathToJceks, true));
    String output = baos.toString();
    Assert.assertFalse(output.contains("Print help information"));
  }

  @Test
  public void createKeyStoreOverwriteTest() throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    System.setOut(new PrintStream(baos));
    String jceksFileName = "testOverwrite.jceks";
    String pathToJceks = tmp + Path.SEPARATOR + jceksFileName;
    LOG.info("Path to jcek file: " + pathToJceks);
    String property = "test.property";
    String value = "test.value";
    String newValue = "new.test.value";
    EncryptionCli.main(new String[]{"--keyStorePath", pathToJceks, "--property", property + "=" +
        value, "--inTestMode"});
    File jceksFile = new File(pathToJceks);
    Assert.assertTrue(jceksFile.exists());
    Assert.assertFalse(jceksFile.isDirectory());
    Assert.assertEquals(value, EncryptionTool.getProperty(property, pathToJceks, true));
    EncryptionCli.main(new String[]{"--keyStorePath", pathToJceks, "--property", property + "=" +
        newValue, "--inTestMode", "--overwrite"});
    Assert.assertEquals(newValue, EncryptionTool.getProperty(property, pathToJceks, true));
    String output = baos.toString();
    Assert.assertFalse(output.contains("Print help information"));
  }

  @Test
  public void aliasExistsTrueTest() {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    System.setOut(new PrintStream(baos));
    String jceksFileName = "aliasExists.jceks";
    String pathToJceks = tmp + Path.SEPARATOR + jceksFileName;
    LOG.info("Path to jcek file: " + pathToJceks);
    String property = "test.property";
    String value = "test.value";
    EncryptionCli.main(new String[]{"--keyStorePath", pathToJceks, "--property", property + "=" +
        value, "--inTestMode", "--overwrite"});
    EncryptionCli.main(new String[]{"--keyStorePath", pathToJceks, "--aliasExists", property, "--inTestMode"});
    String output = baos.toString();
    Assert.assertEquals("true", output);
  }

  @Test
  public void aliasExistsFalseTest() {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    System.setOut(new PrintStream(baos));
    String jceksFileName = "aliasExistsFalse.jceks";
    String pathToJceks = tmp + Path.SEPARATOR + jceksFileName;
    LOG.info("Path to jcek file: " + pathToJceks);
    String property = "test.property";
    EncryptionCli.main(new String[]{"--keyStorePath", pathToJceks, "--aliasExists", property, "--inTestMode"});
    String output = baos.toString();
    Assert.assertEquals("false", output);
  }



  @Test
  public void parsingErrorTest() {
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("Parsing failed");
    EncryptionCli.main(new String[]{"--lhjlk", "-/--l)9"});
  }

  @Test
  public void noArgumentsTest() {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    System.setOut(new PrintStream(baos));
    EncryptionCli.main(new String[]{""});
    String output = baos.toString();
    Assert.assertTrue(output.contains("Print help information"));
  }

  @Test
  public void nullArgumentsTest() {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    System.setOut(new PrintStream(baos));
    EncryptionCli.main(null);
    String output = baos.toString();
    Assert.assertTrue(output.contains("Print help information"));
  }


  @Test
  public void tooManyArgumentsTest() {
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("Parsing failed");
    String jceksFileName = "test.jceks";
    String pathToJceks = tmp + Path.SEPARATOR + jceksFileName;
    LOG.info("Path to jcek file: " + pathToJceks);
    String property = "test.property";
    String value = "test.value";
    EncryptionCli.main(new String[]{"--keyStorePath", pathToJceks, "--property", property + "=" +
        value, "--inTestMode", "--invalidArg", "--unknownArg"});
  }



  @Test
  public void noKeyStorePathTest() {
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("Invalid arguments");
    String property = "test.property";
    String value = "test.value";
    EncryptionCli.main(new String[]{"--property", property + "=" +
        value, "--inTestMode"});
  }


  @Test
  public void noPropertyTest() {
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("Invalid arguments");
    String jceksFileName = "test.jceks";
    String pathToJceks = tmp + Path.SEPARATOR + jceksFileName;
    EncryptionCli.main(new String[]{"--keyStorePath", pathToJceks, "--inTestMode"});
  }


  @AfterClass
  public static void cleanUp() throws IOException {
    FileUtils.deleteDirectory(new File(tmp));
  }
}
