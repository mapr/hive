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

package org.apache.hadoop.hive.ql.exec.vector.expressions;

import org.apache.hadoop.hive.common.type.HiveIntervalYearMonth;
import org.apache.hadoop.hive.ql.exec.vector.BytesColumnVector;
import org.apache.hadoop.hive.ql.exec.vector.LongColumnVector;
import org.apache.hadoop.hive.ql.exec.vector.VectorExpressionDescriptor;
import org.apache.hadoop.hive.ql.exec.vector.VectorizedRowBatch;
import org.apache.hadoop.hive.serde.serdeConstants;

import java.util.Arrays;

/**
 * Casts a string vector to a interval year-month vector.
 */
public class CastStringToIntervalYearMonth extends VectorExpression {
  private static final long serialVersionUID = 1L;

  private int inputColumn;
  private int outputColumn;

  public CastStringToIntervalYearMonth() {

  }

  public CastStringToIntervalYearMonth(int inputColumn, int outputColumn) {
    this.inputColumn = inputColumn;
    this.outputColumn = outputColumn;
  }

  @Override
  public void evaluate(VectorizedRowBatch batch) {
    if (childExpressions != null) {
      super.evaluateChildren(batch);
    }

    BytesColumnVector inV = (BytesColumnVector) batch.cols[inputColumn];
    int[] sel = batch.selected;
    int n = batch.size;
    LongColumnVector outV = (LongColumnVector) batch.cols[outputColumn];
    boolean[] inputIsNull = inV.isNull;
    boolean[] outputIsNull = outV.isNull;

    if (n == 0) {
      // Nothing to do
      return;
    }

    // We do not need to do a column reset since we are carefully changing the output.
    outV.isRepeating = false;
    if (inV.isRepeating) {
      if (inV.noNulls || !inputIsNull[0]) {
        outputIsNull[0] = false;
        evaluate(outV, inV, 0);
      } else {
        outputIsNull[0] = true;
        outV.noNulls = false;
      }
      outV.isRepeating = true;
      return;
    }

    if (inV.noNulls) {
      if (batch.selectedInUse) {
        if (!outV.noNulls) {
          for(int j = 0; j != n; j++) {
            final int i = sel[j];
            outputIsNull[i] = false;
            evaluate(outV, inV, i);
          }
        } else {
          for(int j = 0; j != n; j++) {
            final int i = sel[j];
            evaluate(outV, inV, i);
          }
        }
      } else {
        if (!outV.noNulls) {
          Arrays.fill(outputIsNull, false);
          outV.noNulls = true;
        }
        for(int i = 0; i != n; i++) {
          evaluate(outV, inV, i);
        }
      }
    } else {
      // Do careful maintenance of the outV.noNulls flag.
      if (batch.selectedInUse) {
        for(int j = 0; j != n; j++) {
          int i = sel[j];
          if (!inV.isNull[i]) {
            outputIsNull[i] = false;
            evaluate(outV, inV, i);
          } else {
            outputIsNull[i] = true;
            outV.noNulls = false;
          }
        }
      } else {
        System.arraycopy(inV.isNull, 0, outV.isNull, 0, n);
        for(int i = 0; i != n; i++) {
          if (!inV.isNull[i]) {
            outputIsNull[i] = false;
            evaluate(outV, inV, i);
          } else {
            outputIsNull[i] = true;
            outV.noNulls = false;
          }
        }
      }
    }
  }

  private void evaluate(LongColumnVector outV, BytesColumnVector inV, int i) {
    try {
      HiveIntervalYearMonth interval = HiveIntervalYearMonth.valueOf(
          new String(inV.vector[i], inV.start[i], inV.length[i], "UTF-8"));
      outV.vector[i] = interval.getTotalMonths();
    } catch (Exception e) {
      outV.vector[i] = 1;
      outV.isNull[i] = true;
      outV.noNulls = false;
    }
  }

  @Override
  public int getOutputColumn() {
    return outputColumn;
  }

  public void setOutputColumn(int outputColumn) {
    this.outputColumn = outputColumn;
  }

  public int getInputColumn() {
    return inputColumn;
  }

  public void setInputColumn(int inputColumn) {
    this.inputColumn = inputColumn;
  }

  @Override
  public String getOutputType() {
    return serdeConstants.INTERVAL_YEAR_MONTH_TYPE_NAME;
  }

  @Override
  public String vectorExpressionParameters() {
    return "col " + inputColumn;
  }

  @Override
  public VectorExpressionDescriptor.Descriptor getDescriptor() {
    VectorExpressionDescriptor.Builder b = new VectorExpressionDescriptor.Builder();
    b.setMode(VectorExpressionDescriptor.Mode.PROJECTION)
        .setNumArguments(1)
        .setArgumentTypes(
            VectorExpressionDescriptor.ArgumentType.STRING_FAMILY)
        .setInputExpressionTypes(
            VectorExpressionDescriptor.InputExpressionType.COLUMN);
    return b.build();
  }
}
