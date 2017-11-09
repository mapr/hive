package org.apache.hadoop.hive.maprdb.json;

import com.google.common.base.Preconditions;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hive.common.JavaUtils;
import org.apache.hadoop.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLDecoder;
import java.text.MessageFormat;
import java.util.*;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import static com.google.common.base.Charsets.UTF_8;

public final class Utils {
  private Utils(){}
  private static final Logger LOG = LoggerFactory.getLogger(Utils.class);
  private static final String CLASS = ".class";
  private static final String FILE = "file:";
  private static final String JAR = "jar";

  // Thanks, HBase
  static void addDependencyJars(Configuration conf, Class<?>... classes) throws IOException {
    FileSystem localFs = FileSystem.getLocal(conf);
    Set<String> jars = new HashSet<>();
    // Add jars that are already in the tmpjars variable
    jars.addAll(conf.getStringCollection("tmpjars"));

    // add jars as we find them to a map of contents jar name so that we can
    // avoid
    // creating new jars for classes that have already been packaged.
    Map<String,String> packagedClasses = new HashMap<>();

    // Add jars containing the specified classes
    for (Class<?> clazz : classes) {
      if (clazz == null)
        continue;

      Path path = findOrCreateJar(clazz, localFs, packagedClasses);
      if (path == null) {
        LOG.warn("Could not find jar for class " + clazz + " in order to ship it to the cluster.");
        continue;
      }
      if (!localFs.exists(path)) {
        LOG.warn("Could not validate jar file " + path + " for class " + clazz);
        continue;
      }
      jars.add(path.toString());
    }
    if (jars.isEmpty())
      return;

    conf.set("tmpjars", StringUtils.arrayToString(jars.toArray(new String[jars.size()])));
  }

  /**
   * If org.apache.hadoop.util.JarFinder is available (0.23+ hadoop), finds the Jar for a class or
   * creates it if it doesn't exist. If the class is in a directory in the classpath, it creates a
   * Jar on the fly with the contents of the directory and returns the path to that Jar. If a Jar is
   * created, it is created in the system temporary directory. Otherwise, returns an existing jar
   * that contains a class of the same name. Maintains a mapping from jar contents to the tmp jar
   * created.
   *
   * @param myClass
   *          the class to find.
   * @param fs
   *          the FileSystem with which to qualify the returned path.
   * @param packagedClasses
   *          a map of class name to path.
   * @return a jar file that contains the class.
   * @throws IOException
   */
  @SuppressWarnings("deprecation")
  private static Path findOrCreateJar(Class<?> myClass, FileSystem fs,
                                      Map<String,String> packagedClasses) throws IOException {
    // attempt to locate an existing jar for the class.
    String jar = findContainingJar(myClass, packagedClasses);
    if (null == jar || jar.isEmpty()) {
      jar = getJar(myClass);
      updateMap(jar, packagedClasses);
    }

    if (null == jar || jar.isEmpty()) {
      return null;
    }
    if(LOG.isDebugEnabled()) {
      LOG.debug(String.format("For class %s, using jar %s", myClass.getName(), jar));
    }
    return new Path(jar).makeQualified(fs);
  }

  /**
   * Add entries to <code>packagedClasses</code> corresponding to class files contained in
   * <code>jar</code>.
   *
   * @param jar
   *          The jar who's content to list.
   * @param packagedClasses
   *          map[class -> jar]
   */
  private static void updateMap(String jar, Map<String,String> packagedClasses) throws IOException {
    if (null == jar || jar.isEmpty()) {
      return;
    }

    try (ZipFile zip = new ZipFile(jar)) {
      for (Enumeration<? extends ZipEntry> iter = zip.entries(); iter.hasMoreElements();) {
        ZipEntry entry = iter.nextElement();
        if (entry.getName().endsWith("class")) {
          packagedClasses.put(entry.getName(), jar);
        }
      }
    }
  }

  /**
   * Find a jar that contains a class of the same name, if any. It will return a jar file, even if
   * that is not the first thing on the class path that has a class with the same name. Looks first
   * on the classpath and then in the <code>packagedClasses</code> map.
   *
   * @param myClass
   *          the class to find.
   * @return a jar file that contains the class, or null.
   * @throws IOException
   */
  private static String findContainingJar(Class<?> myClass, Map<String,String> packagedClasses)
    throws IOException {
    ClassLoader loader = myClass.getClassLoader();
    String class_file = myClass.getName().replaceAll("\\.", "/") + CLASS;

    // first search the classpath
    for (Enumeration<URL> itr = loader.getResources(class_file); itr.hasMoreElements();) {
      URL url = itr.nextElement();
      if (JAR.equals(url.getProtocol())) {
        String toReturn = url.getPath();
        if (toReturn.startsWith(FILE)) {
          toReturn = toReturn.substring(FILE.length());
        }
        // URLDecoder is a misnamed class, since it actually decodes
        // x-www-form-urlencoded MIME type rather than actual
        // URL encoding (which the file path has). Therefore it would
        // decode +s to ' 's which is incorrect (spaces are actually
        // either unencoded or encoded as "%20"). Replace +s first, so
        // that they are kept sacred during the decoding process.
        toReturn = toReturn.replaceAll("\\+", "%2B");
        toReturn = URLDecoder.decode(toReturn, UTF_8.name());
        return toReturn.replaceAll("!.*$", "");
      }
    }

    // now look in any jars we've packaged using JarFinder. Returns null
    // when
    // no jar is found.
    return packagedClasses.get(class_file);
  }

  /**
   * Invoke 'getJar' on a JarFinder implementation. Useful for some job configuration contexts
   * (HBASE-8140) and also for testing on MRv2. First check if we have HADOOP-9426. Lacking that,
   * fall back to the backport.
   *
   * @param myClass
   *          the class to find.
   * @return a jar file that contains the class, or null.
   */
  private static String getJar(Class<?> myClass) {
    String ret = null;
    String hadoopJarFinder = "org.apache.hadoop.util.JarFinder";
    Class<?> jarFinder = null;
    try {
      LOG.debug("Looking for " + hadoopJarFinder + ".");
      jarFinder = JavaUtils.loadClass(hadoopJarFinder);
      LOG.debug(hadoopJarFinder + " found.");
      Method getJar = jarFinder.getMethod("getJar", Class.class);
      ret = (String) getJar.invoke(null, myClass);
    } catch (ClassNotFoundException e) {
      LOG.debug("Using backported JarFinder.");
      ret = jarFinderGetJar(myClass);
    } catch (InvocationTargetException e) {
      // function was properly called, but threw it's own exception.
      // Unwrap it
      // and pass it on.
      throw new RuntimeException(e.getCause());
    } catch (Exception e) {
      // toss all other exceptions, related to reflection failure
      throw new RuntimeException("getJar invocation failed.", e);
    }

    return ret;
  }

  /**
   * Returns the full path to the Jar containing the class. It always return a JAR.
   *
   * @param clazz
   *          class.
   *
   * @return path to the Jar containing the class.
   */
  @SuppressWarnings("rawtypes")
  public static String jarFinderGetJar(Class clazz) {
    Preconditions.checkNotNull(clazz, "clazz");
    ClassLoader loader = clazz.getClassLoader();
    if (loader != null) {
      String classFile = clazz.getName().replaceAll("\\.", "/") + CLASS;
      try {
        for (Enumeration itr = loader.getResources(classFile); itr.hasMoreElements();) {
          URL url = (URL) itr.nextElement();
          String path = url.getPath();
          if (path.startsWith(FILE)) {
            path = path.substring(FILE.length());
          }
          path = URLDecoder.decode(path, UTF_8.name());
          if (JAR.equals(url.getProtocol())) {
            path = URLDecoder.decode(path, UTF_8.name());
            return path.replaceAll("!.*$", "");
          } else if ("file".equals(url.getProtocol())) {
            String klassName = clazz.getName();
            klassName = klassName.replace(".", "/") + CLASS;
            path = path.substring(0, path.length() - klassName.length());
            File baseDir = new File(path);
            File testDir = new File(System.getProperty("test.build.dir", "target/test-dir"));
            testDir = testDir.getAbsoluteFile();
            if (!testDir.exists()) {
              testDir.mkdirs();
            }
            File tempJar = File.createTempFile("hadoop-", "", testDir);
            tempJar = new File(tempJar.getAbsolutePath() + ".jar");
            createJar(baseDir, tempJar);
            return tempJar.getAbsolutePath();
          }
        }
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
    return null;
  }

  private static void copyToZipStream(InputStream is, ZipEntry entry, ZipOutputStream zos)
    throws IOException {
    zos.putNextEntry(entry);
    byte[] arr = new byte[4096];
    int read = is.read(arr);
    while (read > -1) {
      zos.write(arr, 0, read);
      read = is.read(arr);
    }
    is.close();
    zos.closeEntry();
  }

  public static void jarDir(File dir, String relativePath, ZipOutputStream zos) throws IOException {
    Preconditions.checkNotNull(relativePath, "relativePath");
    Preconditions.checkNotNull(zos, "zos");

    // by JAR spec, if there is a manifest, it must be the first entry in
    // the
    // ZIP.
    File manifestFile = new File(dir, JarFile.MANIFEST_NAME);
    ZipEntry manifestEntry = new ZipEntry(JarFile.MANIFEST_NAME);
    if (!manifestFile.exists()) {
      zos.putNextEntry(manifestEntry);
      new Manifest().write(new BufferedOutputStream(zos));
      zos.closeEntry();
    } else {
      InputStream is = new FileInputStream(manifestFile);
      copyToZipStream(is, manifestEntry, zos);
    }
    zos.closeEntry();
    zipDir(dir, relativePath, zos, true);
    zos.close();
  }

  private static void zipDir(File dir, String relativePath, ZipOutputStream zos, boolean start)
    throws IOException {
    String[] dirList = dir.list();
    for (String aDirList : dirList) {
      File f = new File(dir, aDirList);
      if (!f.isHidden()) {
        if (f.isDirectory()) {
          if (!start) {
            ZipEntry dirEntry = new ZipEntry(relativePath + f.getName() + "/");
            zos.putNextEntry(dirEntry);
            zos.closeEntry();
          }
          String filePath = f.getPath();
          File file = new File(filePath);
          zipDir(file, relativePath + f.getName() + "/", zos, false);
        } else {
          String path = relativePath + f.getName();
          if (!path.equals(JarFile.MANIFEST_NAME)) {
            ZipEntry anEntry = new ZipEntry(path);
            InputStream is = new FileInputStream(f);
            copyToZipStream(is, anEntry, zos);
          }
        }
      }
    }
  }

  private static void createJar(File dir, File jarFile) throws IOException {
    Preconditions.checkNotNull(dir, "dir");
    Preconditions.checkNotNull(jarFile, "jarFile");
    File jarDir = jarFile.getParentFile();
    if (!jarDir.exists()) {
      if (!jarDir.mkdirs()) {
        throw new IOException(MessageFormat.format("could not create dir [{0}]", jarDir));
      }
    }
    JarOutputStream zos = new JarOutputStream(new FileOutputStream(jarFile));
    jarDir(dir, "", zos);
  }
}