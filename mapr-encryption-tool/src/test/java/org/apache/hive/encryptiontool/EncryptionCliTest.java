package org.apache.hive.encryptiontool;

import org.apache.hadoop.fs.Path;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.BasicConfigurator;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
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
  private static final String tmp = System.getProperty("java.io.tmpdir");

  static {
    BasicConfigurator.configure();
  }

  private final ByteArrayOutputStream baos = new ByteArrayOutputStream();

  @Before
  public void init() {
    System.setOut(new PrintStream(baos));
  }

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
    EncryptionCli.main(new String[]{"--help"});
    Assert.assertTrue(output().contains("Print help information"));
  }

  @Test
  public void createKeyStoreTest() throws IOException {
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
    Assert.assertFalse(output().contains("Print help information"));
  }

  @Test
  public void createKeyStoreOverwriteTest() throws IOException {
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
    Assert.assertFalse(output().contains("Print help information"));
  }

  @Test
  public void aliasExistsTrueTest() {
    String jceksFileName = "aliasExists.jceks";
    String pathToJceks = tmp + Path.SEPARATOR + jceksFileName;
    LOG.info("Path to jcek file: " + pathToJceks);
    String property = "test.property";
    String value = "test.value";
    EncryptionCli.main(new String[]{"--keyStorePath", pathToJceks, "--property", property + "=" +
        value, "--inTestMode", "--overwrite"});
    EncryptionCli.main(new String[]{"--keyStorePath", pathToJceks, "--aliasExists", property, "--inTestMode"});
    Assert.assertEquals("true", output());
  }

  @Test
  public void aliasExistsFalseTest() {
    String jceksFileName = "aliasExistsFalse.jceks";
    String pathToJceks = tmp + Path.SEPARATOR + jceksFileName;
    LOG.info("Path to jcek file: " + pathToJceks);
    String property = "test.property";
    EncryptionCli.main(new String[]{"--keyStorePath", pathToJceks, "--aliasExists", property, "--inTestMode"});
    Assert.assertEquals("false", output());
  }



  @Test
  public void parsingErrorTest() {
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("Parsing failed");
    EncryptionCli.main(new String[]{"--lhjlk", "-/--l)9"});
  }

  @Test
  public void noArgumentsTest() {
    EncryptionCli.main(new String[]{""});
    Assert.assertTrue(output().contains("Print help information"));
  }

  @Test
  public void nullArgumentsTest() {
    EncryptionCli.main(null);
    Assert.assertTrue(output().contains("Print help information"));
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

  private String output() {
    return baos.toString();
  }
}
