package org.apache.hive.encryptiontool;

import org.apache.hadoop.fs.Path;
import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public class EncryptionToolTest {
  private static final Logger LOG = LoggerFactory.getLogger(EncryptionToolTest.class.getName());

  private static String tmp = System.getProperty("java.io.tmpdir");

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
    LOG.info("Path to jcek file: " + pathToJceks);
    EncryptionTool.encryptPassword("aaa", "bbb", pathToJceks, true);
    File jceksFile = new File(pathToJceks);
    Assert.assertTrue(jceksFile.exists());
    Assert.assertFalse(jceksFile.isDirectory());
  }


  @AfterClass
  public static void cleanUp() throws IOException {
    FileUtils.deleteDirectory(new File(tmp));
  }
}
