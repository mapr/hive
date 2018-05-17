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

import java.io.File;
import java.io.IOException;

public class EncryptionToolTest {
  private static final Logger LOG = LoggerFactory.getLogger(EncryptionToolTest.class.getName());

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
  public void encryptPasswordTest() throws IOException {
    String jceksFileName = "test.jceks";
    String pathToJceks = tmp + Path.SEPARATOR + jceksFileName;
    String propertyName = "aaa";
    String propertyValue = "bbb";
    LOG.info("Path to jcek file: " + pathToJceks);
    EncryptionTool.encryptPassword(propertyName, propertyValue, pathToJceks, true);
    File jceksFile = new File(pathToJceks);
    Assert.assertTrue(jceksFile.exists());
    Assert.assertFalse(jceksFile.isDirectory());
    Assert.assertEquals(propertyValue, EncryptionTool.getProperty(propertyName, pathToJceks, true));
  }

  @Test
  public void encryptPasswordAlreadyExistsTest() throws IOException {
    String jceksFileName = "alreadySpecifiedTest.jceks";
    String pathToJceks = tmp + Path.SEPARATOR + jceksFileName;
    String propertyName = "prop";
    String propertyValue = "val";
    LOG.info("Path to jcek file: " + pathToJceks);
    EncryptionTool.encryptPassword(propertyName, propertyValue, pathToJceks, true);
    thrown.expect(IOException.class);
    thrown.expectMessage(String.format("Credential %s already exists", propertyName));
    EncryptionTool.encryptPassword(propertyName, propertyValue, pathToJceks, true);
  }

  @Test
  public void deleteIfExistsTest() throws IOException {
    String jceksFileName = "overwriteTest.jceks";
    String pathToJceks = tmp + Path.SEPARATOR + jceksFileName;
    String propertyName = "prop";
    String propertyValue = "val";
    LOG.info("Path to jcek file: " + pathToJceks);
    EncryptionTool.encryptPassword(propertyName, propertyValue, pathToJceks, true);
    Assert.assertEquals(propertyValue, EncryptionTool.getProperty(propertyName, pathToJceks, true));
    EncryptionTool.deleteIfExists(propertyName, pathToJceks, true);
    Assert.assertEquals("", EncryptionTool.getProperty(propertyName, pathToJceks, true));
  }

  @Test
  public void aliasExistsTrueTest() throws IOException {
    String jceksFileName = "aliasExistsTest.jceks";
    String pathToJceks = tmp + Path.SEPARATOR + jceksFileName;
    String propertyName = "prop";
    String propertyValue = "val";
    LOG.info("Path to jcek file: " + pathToJceks);
    EncryptionTool.encryptPassword(propertyName, propertyValue, pathToJceks, true);
    Assert.assertEquals(true, EncryptionTool.aliasExists(propertyName, pathToJceks, true));
  }

  @Test
  public void aliasExistsFalseTest() throws IOException {
    String jceksFileName = "aliasExistsFalseTest.jceks";
    String pathToJceks = tmp + Path.SEPARATOR + jceksFileName;
    String propertyName = "prop";
    String propertyValue = "val";
    LOG.info("Path to jcek file: " + pathToJceks);
    EncryptionTool.encryptPassword(propertyName, propertyValue, pathToJceks, true);
    Assert.assertEquals(true, EncryptionTool.aliasExists(propertyName, pathToJceks, true));
    EncryptionTool.deleteIfExists(propertyName, pathToJceks, true);
    Assert.assertEquals(false, EncryptionTool.aliasExists(propertyName, pathToJceks, true));
  }

  @AfterClass
  public static void cleanUp() throws IOException {
    FileUtils.deleteDirectory(new File(tmp));
  }
}
