package org.apache.hadoop.hive.thrift;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.security.UserGroupInformation;

import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.AccessControlContext;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.List;

/**
 * This class help to use org.apache.hadoop.security.rpcauth.RpcAuthMethod in Hadoop-2.4.1 while it was added in
 * Hadoop-2.5.1
 * */

public class RpcAuthMethodHelper {
  static final Log LOG = LogFactory.getLog(RpcAuthMethodHelper.class);

  public enum AuthMethod {
    MAPRSASL;

    /**
     * @return  Mechanism name for Mapr authentication method. Should return MAPR-SECURITY
     * */

    public String getMechanismName() {
      try {
        String className = "org.apache.hadoop.security.rpcauth.MaprAuthMethod";
        String methodName = "getMechanismName";
        Class<?> clazz = Class.forName(className);
        Field field = clazz.getDeclaredField("INSTANCE");
        field.setAccessible(true);
        Object maprAuthMethod = field.get(null);;
        Method method;
        method = maprAuthMethod.getClass().getMethod(methodName);
        return (String) method.invoke(maprAuthMethod);
      }  catch (Exception e) {
        LOG.error("Creating MaprAuthMethod Error.", e);
        return "";
      }
    }
  }

  /**
   * We use this method only for Mapr-4.0.1 which os based on hadoop-2.4.1
   *
   * @return instance of class MaprSaslCallbackHandler
   */

  public static CallbackHandler createCallbackHandler() {
    try {
      AccessControlContext e = AccessController.getContext();
      Subject subject = Subject.getSubject(e);
      UserGroupInformation current = UserGroupInformation.getCurrentUser();
      String className = "com.mapr.security.callback.MaprSaslCallbackHandler";
      Class<?> clazz = Class.forName(className);
      Constructor<?> ctor = clazz.getConstructor(Subject.class, String.class);
      return (CallbackHandler)ctor.newInstance(new Object[] { subject, current.getShortUserName()});
    } catch (Exception var4) {
      LOG.error("Creating MaprSaslCallbackhandler Error.", var4);
      return null;
    }
  }

  /**
   *
   * @param realUgi user group information. This instance contains method getMechanismName() implemented only
   *                in hadoop-2.4.1 so we use reflection here to get list of authentication methods.
   * @return list of authentication methods composed of Strings
   */

  public static List<String> getRpcAuthMethodList(UserGroupInformation realUgi){
    List<String> rpcAuthMethodList = new ArrayList<String>();
    try {
    Class<?> clazz = UserGroupInformation.class;
    String methodName = "getRpcAuthMethodList";
      Method method = clazz.getMethod(methodName);
      List<Object> objects = (List<Object>) method.invoke(realUgi);
      methodName = "getMechanismName";
      for (Object object : objects){
        method = object.getClass().getMethod(methodName);
        String rpcAuthMethod = (String)method.invoke(object);
        rpcAuthMethodList.add(rpcAuthMethod);
      }
    } catch (NoSuchMethodException e) {
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }

    return rpcAuthMethodList;
  }

}
