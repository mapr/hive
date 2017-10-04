package org.apache.hadoop.hive.hbase;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Durability;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class PutProxy {
  private static final Log LOG = LogFactory.getLog(PutProxy.class);

  private static boolean setDurability_InitError_ = false;
  private static Method setDurability_Method_ = null;
  private static RuntimeException setDurability_InitException_ = null;


  static {
    try {
      setDurability_Method_ = Put.class.getMethod("setDurability", Durability.class);
    } catch (NoSuchMethodException | SecurityException e) {
      setDurability_InitException_ = new RuntimeException("cannot find org.apache.hadoop.hbase.client.Put.setDurability(Durability) ", e);
      setDurability_InitError_ = true;
    }
  }

  public static void setDurability(Put object, Durability param) {
    if (setDurability_InitError_) {
      throw setDurability_InitException_;
    }

    try {
      if (setDurability_Method_ != null) {
        LOG.debug("Call Put::setDurability()");
        setDurability_Method_.invoke(object, param);
        return;
      }
      throw new RuntimeException("No org.apache.hadoop.hbase.client.Put::setDurability(Durability) method found.");
    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
      throw new RuntimeException(e);
    }
  }
}