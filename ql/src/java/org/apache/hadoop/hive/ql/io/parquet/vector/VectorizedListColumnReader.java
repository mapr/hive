/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.hadoop.hive.ql.io.parquet.vector;

import org.apache.hadoop.hive.ql.exec.vector.ListColumnVector;
import org.apache.hadoop.hive.ql.exec.vector.DecimalColumnVector;
import org.apache.hadoop.hive.ql.exec.vector.BytesColumnVector;
import org.apache.hadoop.hive.ql.exec.vector.LongColumnVector;
import org.apache.hadoop.hive.ql.exec.vector.DoubleColumnVector;
import org.apache.hadoop.hive.ql.exec.vector.ColumnVector;
import org.apache.hadoop.hive.serde2.objectinspector.PrimitiveObjectInspector;
import org.apache.hadoop.hive.serde2.typeinfo.ListTypeInfo;
import org.apache.hadoop.hive.serde2.typeinfo.PrimitiveTypeInfo;
import org.apache.hadoop.hive.serde2.typeinfo.TypeInfo;
import org.apache.parquet.column.ColumnDescriptor;
import org.apache.parquet.column.page.PageReader;
import org.apache.parquet.schema.Type;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * It's column level Parquet reader which is used to read a batch of records for a list column.
 */
public class VectorizedListColumnReader extends BaseVectorizedColumnReader {

  // The value read in last time
  private Object lastValue;

  // flag to indicate if there is no data in parquet data page
  private boolean eof = false;

  // flag to indicate if it's the first time to read parquet data page with this instance
  boolean isFirstRow = true;

  public VectorizedListColumnReader(ColumnDescriptor descriptor, PageReader pageReader,
    boolean skipTimestampConversion, Type type) throws IOException {
    super(descriptor, pageReader, skipTimestampConversion, type);
  }

  @Override
  public void readBatch(int total, ColumnVector column, TypeInfo columnType) throws IOException {
    ListColumnVector lcv = (ListColumnVector) column;
    // Because the length of ListColumnVector.child can't be known now,
    // the valueList will save all data for ListColumnVector temporary.
    List<Object> valueList = new ArrayList<>();

    PrimitiveObjectInspector.PrimitiveCategory category =
        ((PrimitiveTypeInfo) ((ListTypeInfo) columnType).getListElementTypeInfo()).getPrimitiveCategory();

    // read the first row in parquet data page, this will be only happened once for this instance
    if(isFirstRow){
      if (!fetchNextValue(category)) {
        return;
      }
      isFirstRow = false;
    }

    int index = 0;
    while (!eof && index < total) {
      // add element to ListColumnVector one by one
      addElement(lcv, valueList, category, index);
      index++;
    }

    // Decode the value if necessary
    if (isCurrentPageDictionaryEncoded) {
      valueList = decodeDictionaryIds(valueList);
    }
    // Convert valueList to array for the ListColumnVector.child
    convertValueListToListColumnVector(category, lcv, valueList, index);
  }

  private int readPageIfNeed() throws IOException {
    // Compute the number of values we want to read in this page.
    int leftInPage = (int) (endOfPageValueCount - valuesRead);
    if (leftInPage == 0) {
      // no data left in current page, load data from new page
      readPage();
      leftInPage = (int) (endOfPageValueCount - valuesRead);
    }
    return leftInPage;
  }

  private boolean fetchNextValue(PrimitiveObjectInspector.PrimitiveCategory category) throws IOException {
    int left = readPageIfNeed();
    if (left > 0) {
      // get the values of repetition and definitionLevel
      readRepetitionAndDefinitionLevels();
      // read the data if it isn't null
      if (definitionLevel == maxDefLevel) {
        if (isCurrentPageDictionaryEncoded) {
          lastValue = dataColumn.readValueDictionaryId();
        } else {
          lastValue = readPrimitiveTypedRow(category);
        }
      }
      return true;
    } else {
      eof = true;
      return false;
    }
  }

  /**
   * The function will set all data from parquet data page for an element in ListColumnVector
   */
  private void addElement(ListColumnVector lcv, List<Object> elements, PrimitiveObjectInspector.PrimitiveCategory category, int index) throws IOException {
    lcv.offsets[index] = elements.size();

    // Return directly if last value is null
    if (definitionLevel < maxDefLevel) {
      lcv.isNull[index] = true;
      lcv.lengths[index] = 0;
      // fetch the data from parquet data page for next call
      fetchNextValue(category);
      return;
    }

    do {
      // add all data for an element in ListColumnVector, get out the loop if there is no data or the data is for new element
      elements.add(lastValue);
    } while (fetchNextValue(category) && (repetitionLevel != 0));

    lcv.isNull[index] = false;
    lcv.lengths[index] = elements.size() - lcv.offsets[index];
  }

  private Object readPrimitiveTypedRow(PrimitiveObjectInspector.PrimitiveCategory category) {
    switch (category) {
      case INT:
      case BYTE:
      case SHORT:
        return dataColumn.readInteger();
      case DATE:
      case INTERVAL_YEAR_MONTH:
      case LONG:
        return dataColumn.readLong();
      case BOOLEAN:
        return dataColumn.readBoolean() ? 1 : 0;
      case DOUBLE:
        return dataColumn.readDouble();
      case BINARY:
      case STRING:
      case CHAR:
      case VARCHAR:
        return dataColumn.readBytes().getBytesUnsafe();
      case FLOAT:
        return dataColumn.readFloat();
      case DECIMAL:
        return dataColumn.readBytes().getBytesUnsafe();
      case INTERVAL_DAY_TIME:
      case TIMESTAMP:
      default:
        throw new RuntimeException("Unsupported type in the list: " + type);
    }
  }

  private List decodeDictionaryIds(List valueList) {
    int total = valueList.size();
    List resultList;
    List<Integer> intList = (List<Integer>) valueList;
    switch (descriptor.getType()) {
      case INT32:
        resultList = new ArrayList<Integer>(total);
        for (int i = 0; i < total; ++i) {
          resultList.add(dictionary.decodeToInt(intList.get(i)));
        }
        break;
      case INT64:
        resultList = new ArrayList<Long>(total);
        for (int i = 0; i < total; ++i) {
          resultList.add(dictionary.decodeToLong(intList.get(i)));
        }
        break;
      case FLOAT:
        resultList = new ArrayList<Float>(total);
        for (int i = 0; i < total; ++i) {
          resultList.add(dictionary.decodeToFloat(intList.get(i)));
        }
        break;
      case DOUBLE:
        resultList = new ArrayList<Double>(total);
        for (int i = 0; i < total; ++i) {
          resultList.add(dictionary.decodeToDouble(intList.get(i)));
        }
        break;
      case BINARY:
      case FIXED_LEN_BYTE_ARRAY:
        resultList = new ArrayList<byte[]>(total);
        for (int i = 0; i < total; ++i) {
          resultList.add(dictionary.decodeToBinary(intList.get(i)).getBytesUnsafe());
        }
        break;
      default:
        throw new UnsupportedOperationException("Unsupported type: " + descriptor.getType());
    }
    return resultList;
  }

  /**
   * The lengths & offsets will be initialized as default size (1024),
   * it should be set to the actual size according to the element number.
   */
  private void setChildrenInfo(ListColumnVector lcv, int itemNum, int elementNum) {
    lcv.childCount = itemNum;
    long[] lcvLength = new long[elementNum];
    long[] lcvOffset = new long[elementNum];
    System.arraycopy(lcv.lengths, 0, lcvLength, 0, elementNum);
    System.arraycopy(lcv.offsets, 0, lcvOffset, 0, elementNum);
    lcv.lengths = lcvLength;
    lcv.offsets = lcvOffset;
  }

  private void fillColumnVector(PrimitiveObjectInspector.PrimitiveCategory category, ListColumnVector lcv,
      List valueList, int elementNum) {
    int total = valueList.size();
    setChildrenInfo(lcv, total, elementNum);
    switch (category) {
      case INT:
      case BYTE:
      case SHORT:
      case BOOLEAN:
        lcv.child = new LongColumnVector(total);
        for (int i = 0; i < valueList.size(); i++) {
          ((LongColumnVector)lcv.child).vector[i] = ((List<Integer>)valueList).get(i);
        }
        break;
      case DATE:
      case INTERVAL_YEAR_MONTH:
      case LONG:
        lcv.child = new LongColumnVector(total);
        for (int i = 0; i < valueList.size(); i++) {
          ((LongColumnVector)lcv.child).vector[i] = ((List<Long>)valueList).get(i);
        }
        break;
      case DOUBLE:
        lcv.child = new DoubleColumnVector(total);
        for (int i = 0; i < valueList.size(); i++) {
          ((DoubleColumnVector)lcv.child).vector[i] = ((List<Double>)valueList).get(i);
        }
        break;
      case BINARY:
      case STRING:
      case CHAR:
      case VARCHAR:
        lcv.child = new BytesColumnVector(total);
        lcv.child.init();
        for (int i = 0; i < valueList.size(); i++) {
          ((BytesColumnVector)lcv.child).setVal(i, ((List<byte[]>)valueList).get(i));
        }
        break;
      case FLOAT:
        lcv.child = new DoubleColumnVector(total);
        for (int i = 0; i < valueList.size(); i++) {
          ((DoubleColumnVector)lcv.child).vector[i] = ((List<Float>)valueList).get(i);
        }
        break;
      case DECIMAL:
        int precision = type.asPrimitiveType().getDecimalMetadata().getPrecision();
        int scale = type.asPrimitiveType().getDecimalMetadata().getScale();
        lcv.child = new DecimalColumnVector(total, precision, scale);
        for (int i = 0; i < valueList.size(); i++) {
          ((DecimalColumnVector)lcv.child).vector[i].set(((List<byte[]>)valueList).get(i), scale);
        }
        break;
      case INTERVAL_DAY_TIME:
      case TIMESTAMP:
      default:
        throw new RuntimeException("Unsupported type in the list: " + type);
    }
  }

  /**
   * Finish the result ListColumnVector with all collected information.
   */
  private void convertValueListToListColumnVector(PrimitiveObjectInspector.PrimitiveCategory category,
      ListColumnVector lcv, List valueList, int elementNum) {
    // Fill the child of ListColumnVector with valueList
    fillColumnVector(category, lcv, valueList, elementNum);
    setIsRepeating(lcv);
  }

  private void setIsRepeating(ListColumnVector lcv) {
    ColumnVector child0 = getChildData(lcv, 0);
    for (int i = 1; i < lcv.offsets.length; i++) {
      ColumnVector currentChild = getChildData(lcv, i);
      if (!compareColumnVector(child0, currentChild)) {
        lcv.isRepeating = false;
        return;
      }
    }
    lcv.isRepeating = true;
  }

  /**
   * Get the child ColumnVector of ListColumnVector
   */
  private ColumnVector getChildData(ListColumnVector lcv, int index) {
    if (lcv.offsets[index] > Integer.MAX_VALUE || lcv.lengths[index] > Integer.MAX_VALUE) {
      throw new RuntimeException("The element number in list is out of scope.");
    }
    if (lcv.isNull[index]) {
      return null;
    }
    int start = (int)lcv.offsets[index];
    int length = (int)lcv.lengths[index];
    ColumnVector child = lcv.child;
    ColumnVector resultCV = null;
    if (child instanceof LongColumnVector) {
      resultCV = new LongColumnVector(length);
      try {
        System.arraycopy(((LongColumnVector) lcv.child).vector, start,
            ((LongColumnVector) resultCV).vector, 0, length);
      } catch (Exception e) {
        throw new RuntimeException("colinmjj:index:" + index + ", start:" + start + ",length:" + length
            + ",vec len:" + ((LongColumnVector) lcv.child).vector.length + ", offset len:" + lcv.offsets.length
            + ", len len:" + lcv.lengths.length, e);
      }
    }
    if (child instanceof DoubleColumnVector) {
      resultCV = new DoubleColumnVector(length);
      System.arraycopy(((DoubleColumnVector) lcv.child).vector, start,
          ((DoubleColumnVector) resultCV).vector, 0, length);
    }
    if (child instanceof BytesColumnVector) {
      resultCV = new BytesColumnVector(length);
      System.arraycopy(((BytesColumnVector) lcv.child).vector, start,
          ((BytesColumnVector) resultCV).vector, 0, length);
    }
    if (child instanceof DecimalColumnVector) {
      resultCV = new DecimalColumnVector(length,
          ((DecimalColumnVector) child).precision, ((DecimalColumnVector) child).scale);
      System.arraycopy(((DecimalColumnVector) lcv.child).vector, start,
          ((DecimalColumnVector) resultCV).vector, 0, length);
    }
    return resultCV;
  }

  private boolean compareColumnVector(ColumnVector cv1, ColumnVector cv2) {
    if (cv1 == null && cv2 == null) {
      return true;
    } else {
      if (cv1 != null && cv2 != null) {
        if (cv1 instanceof LongColumnVector && cv2 instanceof LongColumnVector) {
          return compareLongColumnVector((LongColumnVector) cv1, (LongColumnVector) cv2);
        }
        if (cv1 instanceof DoubleColumnVector && cv2 instanceof DoubleColumnVector) {
          return compareDoubleColumnVector((DoubleColumnVector) cv1, (DoubleColumnVector) cv2);
        }
        if (cv1 instanceof BytesColumnVector && cv2 instanceof BytesColumnVector) {
          return compareBytesColumnVector((BytesColumnVector) cv1, (BytesColumnVector) cv2);
        }
        if (cv1 instanceof DecimalColumnVector && cv2 instanceof DecimalColumnVector) {
          return compareDecimalColumnVector((DecimalColumnVector) cv1, (DecimalColumnVector) cv2);
        }
        throw new RuntimeException("Unsupported ColumnVector comparision between " + cv1.getClass().getName()
            + " and " + cv2.getClass().getName());
      } else {
        return false;
      }
    }
  }

  private boolean compareLongColumnVector(LongColumnVector cv1, LongColumnVector cv2) {
    int length1 = cv1.vector.length;
    int length2 = cv2.vector.length;
    if (length1 == length2) {
      for (int i = 0; i < length1; i++) {
        if (cv1.vector[i] != cv2.vector[i]) {
          return false;
        }
      }
    } else {
      return false;
    }
    return true;
  }

  private boolean compareDoubleColumnVector(DoubleColumnVector cv1, DoubleColumnVector cv2) {
    int length1 = cv1.vector.length;
    int length2 = cv2.vector.length;
    if (length1 == length2) {
      for (int i = 0; i < length1; i++) {
        if (cv1.vector[i] != cv2.vector[i]) {
          return false;
        }
      }
    } else {
      return false;
    }
    return true;
  }

  private boolean compareDecimalColumnVector(DecimalColumnVector cv1, DecimalColumnVector cv2) {
    int length1 = cv1.vector.length;
    int length2 = cv2.vector.length;
    if (length1 == length2 && cv1.scale == cv2.scale && cv1.precision == cv2.precision) {
      for (int i = 0; i < length1; i++) {
        if (cv1.vector[i] != null && cv2.vector[i] == null
            || cv1.vector[i] == null && cv2.vector[i] != null
            || cv1.vector[i] != null && cv2.vector[i] != null && !cv1.vector[i].equals(cv2.vector[i])) {
          return false;
        }
      }
    } else {
      return false;
    }
    return true;
  }

  private boolean compareBytesColumnVector(BytesColumnVector cv1, BytesColumnVector cv2) {
    int length1 = cv1.vector.length;
    int length2 = cv2.vector.length;
    if (length1 == length2) {
      for (int i = 0; i < length1; i++) {
        int innerLen1 = cv1.vector[i].length;
        int innerLen2 = cv2.vector[i].length;
        if (innerLen1 == innerLen2) {
          for (int j = 0; j < innerLen1; j++) {
            if (cv1.vector[i][j] != cv2.vector[i][j]) {
              return false;
            }
          }
        } else {
          return false;
        }
      }
    } else {
      return false;
    }
    return true;
  }
}
