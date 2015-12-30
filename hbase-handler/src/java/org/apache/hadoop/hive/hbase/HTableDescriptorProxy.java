package org.apache.hadoop.hive.hbase;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;

public class HTableDescriptorProxy {

  private static final Log LOG = LogFactory.getLog(HTableDescriptorProxy.class);

  private static boolean addFamily_InitError_ = false;
  private static Method addFamily_Method_ = null;
  private static RuntimeException addFamily_InitException_ = null;

  private static boolean setName_bytes_InitError_ = false;
  private static Method setName_bytes_Method_ = null;
  private static RuntimeException setName_bytes_InitException_ = null;

  private static boolean setName_TableName_InitError_ = false;
  private static Method setName_TableName_Method_ = null;
  private static RuntimeException setName_TableName_InitException_ = null;

  private static boolean setValue_bytes_bytes_InitError_ = false;
  private static Method setValue_bytes_bytes_Method_ = null;
  private static RuntimeException setValue_bytes_bytes_InitException_ = null;

  private static boolean setValue_ImmutableBytesWritable_ImmutableBytesWritable_InitError_ = false;
  private static Method setValue_ImmutableBytesWritable_ImmutableBytesWritable_Method_ = null;
  private static RuntimeException setValue_ImmutableBytesWritable_ImmutableBytesWritable_InitException_ = null;

  private static boolean setValue_String_String_InitError_ = false;
  private static Method setValue_String_String_Method_ = null;
  private static RuntimeException setValue_String_String_InitException_ = null;

  static {
    try {
      addFamily_Method_ = HTableDescriptor.class.getMethod("addFamily", HColumnDescriptor.class);
    } catch (NoSuchMethodException | SecurityException e) {
      addFamily_InitException_ = new RuntimeException("cannot find org.apache.hadoop.hbase.HTableDescriptor.addFamily(HColumnDescriptor) ", e);
      addFamily_InitError_ = true;
    }
    try {
      setName_bytes_Method_ = HTableDescriptor.class.getMethod("setName", byte[].class);
    } catch (NoSuchMethodException | SecurityException e) {
      setName_bytes_InitException_ = new RuntimeException("cannot find org.apache.hadoop.hbase.HTableDescriptor.setName(byte[]) ", e);
      setName_bytes_InitError_ = true;
    }
    try {
      setName_TableName_Method_ = HTableDescriptor.class.getMethod("setName", TableName.class);
    } catch (NoSuchMethodException | SecurityException e) {
      setName_TableName_InitException_ = new RuntimeException("cannot find org.apache.hadoop.hbase.HTableDescriptor.setName(TableName) ", e);
      setName_TableName_InitError_ = true;
    }
    try {
      setValue_bytes_bytes_Method_ = HTableDescriptor.class.getMethod("setValue", byte[].class, byte[].class);
    } catch (NoSuchMethodException | SecurityException e) {
      setValue_bytes_bytes_InitException_ = new RuntimeException("cannot find org.apache.hadoop.hbase.HTableDescriptor.setValue(byte[], byte[]) ", e);
      setValue_bytes_bytes_InitError_ = true;
    }
    try {
      setValue_ImmutableBytesWritable_ImmutableBytesWritable_Method_ = HTableDescriptor.class.getMethod("setValue", ImmutableBytesWritable.class, ImmutableBytesWritable.class);
    } catch (NoSuchMethodException | SecurityException e) {
      setValue_ImmutableBytesWritable_ImmutableBytesWritable_InitException_ = new RuntimeException("cannot find org.apache.hadoop.hbase.HTableDescriptor.setValue(ImmutableBytesWritable, ImmutableBytesWritable) ", e);
      setValue_ImmutableBytesWritable_ImmutableBytesWritable_InitError_ = true;
    }
    try {
      setValue_String_String_Method_ = HTableDescriptor.class.getMethod("setValue", String.class, String.class);
    } catch (NoSuchMethodException | SecurityException e) {
      setValue_String_String_InitException_ = new RuntimeException("cannot find org.apache.hadoop.hbase.HTableDescriptor.setValue(String, String) ", e);
      setValue_String_String_InitError_ = true;
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

  public static void setName(HTableDescriptor object, byte[] param1) {
    if (setName_bytes_InitError_) {
      throw setName_bytes_InitException_;
    }

    try {
      if (setName_bytes_Method_ != null) {
        LOG.debug("Call HTableDescriptor::setName(byte[])");
        setName_bytes_Method_.invoke(object, param1);
        return;
      }
      throw new RuntimeException("No HTableDescriptor::setName(byte[]) function found.");
    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
      throw new RuntimeException(e);
    }
  }

  public static void setName(HTableDescriptor object, TableName param1) {
    if (setName_TableName_InitError_) {
      throw setName_TableName_InitException_;
    }

    try {
      if (setName_TableName_Method_ != null) {
        LOG.debug("Call HTableDescriptor::setName(TableName)");
        setName_TableName_Method_.invoke(object, param1);
        return;
      }
      throw new RuntimeException("No HTableDescriptor::setName(TableName) function found.");
    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
      throw new RuntimeException(e);
    }
  }

  public static void setValue(HTableDescriptor object, byte[] param1, byte[] param2) {
    if (setValue_bytes_bytes_InitError_) {
      throw setValue_bytes_bytes_InitException_;
    }

    try {
      if (setValue_bytes_bytes_Method_ != null) {
        LOG.debug("Call HTableDescriptor::setValue(byte[],byte[])");
        setValue_bytes_bytes_Method_.invoke(object, param1, param2);
        return;
      }
      throw new RuntimeException("No HTableDescriptor::setValue(byte[],byte[]) function found.");
    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
      throw new RuntimeException(e);
    }
  }

  public static void setValue(HTableDescriptor object, ImmutableBytesWritable param1, ImmutableBytesWritable param2) {
    if (setValue_ImmutableBytesWritable_ImmutableBytesWritable_InitError_) {
      throw setValue_ImmutableBytesWritable_ImmutableBytesWritable_InitException_;
    }

    try {
      if (setValue_ImmutableBytesWritable_ImmutableBytesWritable_Method_ != null) {
        LOG.debug("Call HTableDescriptor::setValue(ImmutableBytesWritable,ImmutableBytesWritable)");
        setValue_ImmutableBytesWritable_ImmutableBytesWritable_Method_.invoke(object, param1, param2);
        return;
      }
      throw new RuntimeException("No HTableDescriptor::setValue(ImmutableBytesWritable,ImmutableBytesWritable) function found.");
    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
      throw new RuntimeException(e);
    }
  }

  public static void setValue(HTableDescriptor object, String param1, String param2) {
    if (setValue_String_String_InitError_) {
      throw setValue_String_String_InitException_;
    }

    try {
      if (setValue_String_String_Method_ != null) {
        LOG.debug("Call HTableDescriptor::setValue(String,String)");
        setValue_String_String_Method_.invoke(object, param1, param2);
        return;
      }
      throw new RuntimeException("No HTableDescriptor::setValue(String,String) function found.");
    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
      throw new RuntimeException(e);
    }
  }

}
