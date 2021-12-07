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

import org.apache.hadoop.hive.common.type.HiveDecimal;
import org.apache.hadoop.hive.ql.exec.vector.BytesColumnVector;
import org.apache.hadoop.hive.ql.exec.vector.DecimalColumnVector;
import org.apache.hadoop.hive.ql.exec.vector.VectorExpressionDescriptor;
import org.apache.hadoop.hive.ql.exec.vector.VectorizedRowBatch;

import java.util.Arrays;

/**
 * Cast a string to a decimal.
 *
 * If other functions besides cast need to take a string in and produce a decimal,
 * you can subclass this class or convert it to a superclass, and
 * implement different "func()" methods for each operation.
 */
public class CastStringToDecimal extends VectorExpression {
  private static final long serialVersionUID = 1L;
  int inputColumn;
  int outputColumn;

  public CastStringToDecimal(int inputColumn, int outputColumn) {
    this.inputColumn = inputColumn;
    this.outputColumn = outputColumn;
    this.outputType = "decimal";
  }

  public CastStringToDecimal() {
    super();
    this.outputType = "decimal";
  }

  /**
   * Convert input string to a decimal, at position i in the respective vectors.
   */
  protected void func(DecimalColumnVector outV, BytesColumnVector inV, int i) {
    String s;
    try {

      /* If this conversion is frequently used, this should be optimized,
       * e.g. by converting to decimal from the input bytes directly without
       * making a new string.
       */
      s = new String(inV.vector[i], inV.start[i], inV.length[i], "UTF-8");
      outV.vector[i].set(HiveDecimal.create(s));
    } catch (Exception e) {

      // for any exception in conversion to decimal, produce NULL
      outV.noNulls = false;
      outV.isNull[i] = true;
    }
  }

  @Override
  public void evaluate(VectorizedRowBatch batch) {
    if (childExpressions != null) {
      super.evaluateChildren(batch);
    }

    BytesColumnVector inV = (BytesColumnVector) batch.cols[inputColumn];
    int[] sel = batch.selected;
    int n = batch.size;
    DecimalColumnVector outV = (DecimalColumnVector) batch.cols[outputColumn];
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
        func(outV, inV, 0);
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
            func(outV, inV, i);
          }
        } else {
          for(int j = 0; j != n; j++) {
            final int i = sel[j];
            func(outV, inV, i);
          }
        }
      } else {
        if (!outV.noNulls) {
          Arrays.fill(outputIsNull, false);
          outV.noNulls = true;
        }
        for(int i = 0; i != n; i++) {
          func(outV, inV, i);
        }
      }
    } else {
      // Do careful maintenance of the outV.noNulls flag.
      if (batch.selectedInUse) {
        for(int j = 0; j != n; j++) {
          int i = sel[j];
          if (!inV.isNull[i]) {
            outV.isNull[i] = false;
            func(outV, inV, i);
          } else {
            outV.isNull[i] = true;
            outV.noNulls = false;
          }
        }
      } else {
        System.arraycopy(inV.isNull, 0, outV.isNull, 0, n);
        for(int i = 0; i != n; i++) {
          if (!inV.isNull[i]) {
            outV.isNull[i] = false;
            func(outV, inV, i);
          } else {
            outV.isNull[i] = true;
            outV.noNulls = false;
          }
        }
      }
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