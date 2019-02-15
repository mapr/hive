/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.hadoop.hive.ql.udf.generic;

import org.apache.hadoop.hive.common.type.HiveDecimal;
import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.exec.UDFArgumentLengthException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.serde2.io.HiveDecimalWritable;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.PrimitiveObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.BooleanObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.ByteObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.DateObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.DoubleObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.FloatObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.HiveDecimalObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.IntObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.LongObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorConverter;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorUtils;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.ShortObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.StringObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.TimestampObjectInspector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.threeten.bp.Instant;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneId;
import org.threeten.bp.ZonedDateTime;
import org.threeten.bp.ZoneOffset;
import org.threeten.bp.format.DateTimeFormatter;
import org.threeten.bp.format.DateTimeFormatterBuilder;
import org.threeten.bp.temporal.ChronoField;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Implementation of from_utc_timestamp_new with backported java.time API to Java 7.
 *
 */
@Description(name = "from_utc_timestamp_new", value =
    "from_utc_timestamp_new(timestamp, string timezone) - "
        + "Assumes given timestamp is UTC and converts to given timezone")
public class GenericUDFFromUtcTimestampNew extends GenericUDF {
  static final Logger LOG =
      LoggerFactory.getLogger(GenericUDFFromUtcTimestamp.class);

  private transient PrimitiveObjectInspector[] argumentOIs;
  private transient PrimitiveObjectInspectorConverter.TextConverter
      textConverter;
  private transient ZoneId tzUTC = ZoneId.of("UTC");

  @Override
  public ObjectInspector initialize(ObjectInspector[] arguments)
      throws UDFArgumentException {
    if (arguments.length != 2) {
      throw new UDFArgumentLengthException(
          "The function " + getName() + " requires two " + "argument, got "
              + arguments.length);
    }
    try {
      argumentOIs = new PrimitiveObjectInspector[2];
      argumentOIs[0] = (PrimitiveObjectInspector) arguments[0];
      argumentOIs[1] = (PrimitiveObjectInspector) arguments[1];
    } catch (ClassCastException e) {
      throw new UDFArgumentException(
          "The function " + getName() + " takes only primitive types");
    }
    textConverter =
        new PrimitiveObjectInspectorConverter.TextConverter(argumentOIs[1]);
    return PrimitiveObjectInspectorFactory.javaTimestampObjectInspector;
  }

  @Override
  public Object evaluate(DeferredObject[] arguments) throws HiveException {
    Object o0 = arguments[0].get();
    if (o0 == null) {
      return null;
    }
    Object o1 = arguments[1].get();
    if (o1 == null) {
      return null;
    }

    String converted0 = getLocalDateTime(o0, argumentOIs[0]).toString();
    if (converted0 == null) {
      return null;
    }
    DateTimeFormatter formatter =
        new DateTimeFormatterBuilder().appendPattern("yyyy-MM-dd")
            .appendLiteral("T").appendPattern("HH:mm[:ss]").optionalStart()
            .appendFraction(ChronoField.NANO_OF_SECOND, 0, 9, true)
            .optionalEnd().toFormatter();

    LocalDateTime inputDateTime =
        LocalDateTime.from(formatter.parse(converted0));

    String tzStr = textConverter.convert(o1).toString();
    ZoneId timezone = ZoneId.of(tzStr);

    ZoneId fromTz;
    ZoneId toTz;
    if (invert()) {
      fromTz = timezone;
      toTz = tzUTC;
    } else {
      fromTz = tzUTC;
      toTz = timezone;
    }

    ZonedDateTime dateTimeInTz = ZonedDateTime.of(inputDateTime, fromTz);
    ZonedDateTime resultDateTimeInTz = dateTimeInTz.withZoneSameInstant(toTz);

    return Timestamp
        .valueOf(resultDateTimeInTz.format(formatter).replace("T", " "));
  }

  @Override
  public String getDisplayString(String[] children) {
    StringBuilder sb = new StringBuilder();
    sb.append("Converting field ");
    sb.append(children[0]);
    sb.append(" from UTC to timezone: ");
    if (children.length > 1) {
      sb.append(children[1]);
    }
    return sb.toString();
  }

  public String getName() {
    return "from_utc_timestamp_new";
  }

  protected boolean invert() {
    return false;
  }

  /**
   * Converts input of Hive data type to LocalDateTime
   * as replacement of java.sql.Timestamp for avoiding calculation anomalies.
   * @param o
   * @param inputOI
   * @return
   */
  private  LocalDateTime getLocalDateTime(Object o,
      PrimitiveObjectInspector inputOI) {
    if (o == null) {
      return null;
    }
    LocalDateTime result;
    Instant instant;
    String stringValue;
    Timestamp ts;
    DateTimeFormatter formatter =
        new DateTimeFormatterBuilder().appendPattern("yyyy-MM-dd")
            .appendLiteral(" ").appendPattern("HH:mm[:ss]").optionalStart()
            .appendFraction(ChronoField.NANO_OF_SECOND, 0, 9, true)
            .optionalEnd().toFormatter();
    long longValue = 0;
    switch (inputOI.getPrimitiveCategory()) {
    case VOID:
      result = null;
      break;
    case BOOLEAN:
      longValue = ((BooleanObjectInspector) inputOI).get(o) ? 1 : 0;
      instant = Instant.ofEpochMilli(longValue);
      result = LocalDateTime.ofInstant(instant, ZoneOffset.UTC);
      break;
    case BYTE:
      longValue = ((ByteObjectInspector) inputOI).get(o);
      instant = Instant.ofEpochMilli(longValue);
      result = LocalDateTime.ofInstant(instant, ZoneOffset.UTC);
      break;
    case SHORT:
      longValue = ((ShortObjectInspector) inputOI).get(o);
      instant = Instant.ofEpochMilli(longValue);
      result = LocalDateTime.ofInstant(instant, ZoneOffset.UTC);
      break;
    case INT:
      longValue = ((IntObjectInspector) inputOI).get(o);
      instant = Instant.ofEpochMilli(longValue);
      result = LocalDateTime.ofInstant(instant, ZoneOffset.UTC);
      break;
    case LONG:
      longValue = ((LongObjectInspector) inputOI).get(o);
      instant = Instant.ofEpochMilli(longValue);
      result = LocalDateTime.ofInstant(instant, ZoneOffset.UTC);
      break;
    case FLOAT:
      instant = doubleToInstant(((FloatObjectInspector) inputOI).get(o));
      result = LocalDateTime.ofInstant(instant, ZoneOffset.UTC);
      break;
    case DOUBLE:
      instant = doubleToInstant(((DoubleObjectInspector) inputOI).get(o));
      result = LocalDateTime.ofInstant(instant, ZoneOffset.UTC);
      break;
    case DECIMAL:
      instant = decimalToInstant(
          ((HiveDecimalObjectInspector) inputOI).getPrimitiveJavaObject(o));
      result = LocalDateTime.ofInstant(instant, ZoneOffset.UTC);
      break;
    case STRING:
      StringObjectInspector soi = (StringObjectInspector) inputOI;
      stringValue = soi.getPrimitiveJavaObject(o);
      result = LocalDateTime.from(formatter.parse(stringValue));
      break;
    case CHAR:
    case VARCHAR:
      stringValue = PrimitiveObjectInspectorUtils.getString(o, inputOI);
      result = LocalDateTime.from(formatter.parse(stringValue));
      break;
    case DATE:
      ts = new Timestamp(
          ((DateObjectInspector) inputOI).getPrimitiveWritableObject(o).get()
              .getTime());
      result = LocalDateTime.from(formatter.parse(ts.toString()));
      break;
    case TIMESTAMP:
      ts = ((TimestampObjectInspector) inputOI).getPrimitiveWritableObject(o)
          .getTimestamp();
      result = LocalDateTime.from(formatter.parse(ts.toString()));
      break;
    default:
      throw new RuntimeException(
          "Hive 2 Internal error: unknown type: " + inputOI.getTypeName());
    }
    return result;
  }

  /**
   * Takes a HiveDecimal and return the Instant representation where the fraction
   * part is the nanoseconds and integer part is the number of seconds.
   * @param dec
   * @return
   */
  private Instant decimalToInstant(HiveDecimal dec) {

    HiveDecimalWritable nanosWritable = new HiveDecimalWritable(dec);
    nanosWritable.mutateFractionPortion();
    nanosWritable.mutateScaleByPowerOfTen(9);
    if (!nanosWritable.isSet() || !nanosWritable.isInt()) {
      return null;
    }
    int nanos = nanosWritable.intValue();
    if (nanos < 0) {
      nanos += 1000000000;
    }
    nanosWritable.setFromLong(nanos);

    HiveDecimalWritable nanoInstant = new HiveDecimalWritable(dec);
    nanoInstant.mutateScaleByPowerOfTen(9);

    nanoInstant.mutateSubtract(nanosWritable);
    nanoInstant.mutateScaleByPowerOfTen(-9);
    if (!nanoInstant.isSet() || !nanoInstant.isLong()) {
      return null;
    }
    long seconds = nanoInstant.longValue();
    Instant t = Instant.ofEpochMilli(seconds * 1000);
    t = t.plusNanos(nanos);
    return t;
  }

  /**
   * Takes a double and return the Instant representation where the fraction part
   * is the nanoseconds and integer part is the number of seconds.
   * @param doubleValue
   * @return
   */
  private Instant doubleToInstant(double doubleValue) {
    long seconds = (long) doubleValue;

    BigDecimal bd = new BigDecimal(String.valueOf(doubleValue));

    bd = bd.subtract(new BigDecimal(seconds))
        .multiply(new BigDecimal(1000000000));
    int nanos = bd.intValue();

    long millis = seconds * 1000;
    if (nanos < 0) {
      millis -= 1000;
      nanos += 1000000000;
    }
    Instant t = Instant.ofEpochMilli(millis);

    t = t.plusNanos(nanos);
    return t;
  }
}
