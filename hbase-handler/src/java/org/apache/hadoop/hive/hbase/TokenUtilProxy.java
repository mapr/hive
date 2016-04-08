package org.apache.hadoop.hive.hbase;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.hbase.client.HConnection;
import org.apache.hadoop.hbase.security.User;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.hbase.security.token.TokenUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TokenUtilProxy {
  private static final Log LOG = LogFactory.getLog(TokenUtilProxy.class);

  private static boolean addTokenForJob_InitError_ = false;
  private static Method addTokenForJob_Method_ = null;
  private static RuntimeException addTokenForJob_InitException_ = null;
  private static Class<?> clazz = HConnection.class;

  static {
    try {
      //hbase 1.1.1?
      String className = "org.apache.hadoop.hbase.client.Connection";
      clazz = Class.forName(className);
      LOG.info("Class org.apache.hadoop.hbase.client.Connection was found. This is HBase 1.1.1 or higher");
    } catch (ClassNotFoundException | SecurityException e) {
      LOG.info("Cannot find org.apache.hadoop.hbase.client.Connection. This is HBase 0.98", e);
    }
    try {
      addTokenForJob_Method_ = TokenUtil.class.getMethod("addTokenForJob", clazz, User.class, Job.class);
    } catch (NoSuchMethodException | SecurityException e) {
      addTokenForJob_InitException_ = new RuntimeException("Cannot find org.apache.hadoop.hbase.security.token.TokenUtil.addTokenForJob(params) ", e);
      addTokenForJob_InitError_ = true;
    }
  }

  public static void addTokenForJob( HConnection conn, User curUser, Job job) {
    if (addTokenForJob_InitError_) {
      throw addTokenForJob_InitException_;
    }

    try {
      if (addTokenForJob_Method_ != null) {
        LOG.debug("Call TokenUtil::addTokenForJob()");
        // addTokenForJob() is static. No need for creating instance of TokenUtil here, therefore pass null to invoke()
        addTokenForJob_Method_.invoke(null, conn, curUser, job);
        return;
      }
      throw new RuntimeException("No TokenUtil::addTokenForJob(params) method found.");
    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
      throw new RuntimeException(e);
    }
  }
}