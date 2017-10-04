package org.apache.hadoop.hive.metastore.hbase;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;

public class HTableDescriptorProxy {

  private static final Log LOG = LogFactory.getLog(HTableDescriptorProxy.class);

  private static boolean addFamily_InitError_ = false;
  private static Method addFamily_Method_ = null;
  private static RuntimeException addFamily_InitException_ = null;


  static {
    try {
      addFamily_Method_ = HTableDescriptor.class.getMethod("addFamily", HColumnDescriptor.class);
    } catch (NoSuchMethodException | SecurityException e) {
      addFamily_InitException_ = new RuntimeException("cannot find org.apache.hadoop.hbase.HTableDescriptor.addFamily(HColumnDescriptor) ", e);
      addFamily_InitError_ = true;
    }
  }

  public static void addFamily(HTableDescriptor object, HColumnDescriptor param) {
    if (addFamily_InitError_) {
      throw addFamily_InitException_;
    }

    try {
      if (addFamily_Method_ != null) {
        LOG.debug("Call HTableDescriptor::addFamily()");
        addFamily_Method_.invoke(object, param);
        return;
      }
      throw new RuntimeException("No HTableDescriptor::addFamily() function found.");
    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
      throw new RuntimeException(e);
    }
  }

}
