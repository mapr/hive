package org.apache.hive.encryptiontool;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hive.common.FileUtils;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

public class EncryptionCliTest {
  private static final Logger LOG = LoggerFactory.getLogger(EncryptionCliTest.class.getName());

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


  @Test
  public void printHelpTest() throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    System.setOut(new PrintStream(baos));
    EncryptionCli.main(new String[]{});
    System.out.println(baos.toString());
  }



  @AfterClass
  public static void cleanUp() throws IOException {
    FileUtils.deleteDirectory(new File(tmp));
  }

}
