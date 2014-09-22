package org.apache.hadoop.hive.serde2.io;

import java.sql.Date;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static junit.framework.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class TestDateWritable {
  public static class DateTestCallable implements Callable<String> {
    public DateTestCallable() {
    }

    @Override
    public String call() throws Exception {
      // Iterate through each day of the year, make sure Date/DateWritable match
      Date originalDate = Date.valueOf("2014-01-01");
      Calendar cal = Calendar.getInstance();
      cal.setTimeInMillis(originalDate.getTime());
      for (int idx = 0; idx < 365; ++idx) {
        originalDate = new Date(cal.getTimeInMillis());
        // Make sure originalDate is at midnight in the local time zone,
        // since DateWritable will generate dates at that time.
        originalDate = Date.valueOf(originalDate.toString());
        DateWritable dateWritable = new DateWritable(originalDate);
        if (!originalDate.equals(dateWritable.get())) {
          return originalDate.toString();
        }
        cal.add(Calendar.DAY_OF_YEAR, 1);
      }
      // Success!
      return null;
    }
  }

  @Test
  public void testDaylightSavingsTime() throws InterruptedException, ExecutionException {
    String[] timeZones = {
        "GMT",
        "UTC",
        "America/Godthab",
        "America/Los_Angeles",
        "Asia/Jerusalem",
        "Australia/Melbourne",
        "Europe/London",
        // time zones with half hour boundaries
        "America/St_Johns",
        "Asia/Tehran",
    };

    for (String timeZone: timeZones) {
      TimeZone previousDefault = TimeZone.getDefault();
      TimeZone.setDefault(TimeZone.getTimeZone(timeZone));
      assertEquals("Default timezone should now be " + timeZone,
          timeZone, TimeZone.getDefault().getID());
      ExecutorService threadPool = Executors.newFixedThreadPool(1);
      try {
        Future<String> future = threadPool.submit(new DateTestCallable());
        String result = future.get();
        assertNull("Failed at timezone " + timeZone + ", date " + result, result);
      } finally {
        threadPool.shutdown(); TimeZone.setDefault(previousDefault);
      }
    }
  }

}
