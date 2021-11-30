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

import org.apache.hadoop.hive.ql.exec.vector.DecimalColumnVector;
import org.apache.hadoop.hive.ql.exec.vector.VectorExpressionDescriptor;
import org.apache.hadoop.hive.ql.exec.vector.VectorizedRowBatch;

import java.util.Arrays;

/**
 * Cast a decimal to a decimal, accounting for precision and scale changes.
 *
 * If other functions besides cast need to take a decimal in and produce a decimal,
 * you can subclass this class or convert it to a superclass, and
 * implement different methods for each operation. If that's done, the
 * convert() method should be renamed to func() for consistency with other
 * similar super classes such as FuncLongToDecimal.
 */
public class CastDecimalToDecimal extends VectorExpression {
  private static final long serialVersionUID = 1L;
  int inputColumn;
  int outputColumn;

  public CastDecimalToDecimal(int inputColumn, int outputColumn) {
    this.inputColumn = inputColumn;
    this.outputColumn = outputColumn;
    this.outputType = "decimal";
  }

  public CastDecimalToDecimal() {
    super();
    this.outputType = "decimal";
  }

  /**
   * Convert input decimal value to a decimal with a possibly different precision and scale,
   * at position i in the respective vectors.
   */
  protected void convert(DecimalColumnVector outV, DecimalColumnVector inV, int i) {
    // The set routine enforces precision and scale.
    outV.vector[i].set(inV.vector[i]);
  }

  /**
   * Cast decimal(p1, s1) to decimal(p2, s2).
   *
   * The precision and scale are recorded in the input and output vectors,
   * respectively.
   */
  @Override
  public void evaluate(VectorizedRowBatch batch) {
    if (childExpressions != null) {
      super.evaluateChildren(batch);
    }

    DecimalColumnVector inV = (DecimalColumnVector) batch.cols[inputColumn];
    int[] sel = batch.selected;
    int n = batch.size;
    DecimalColumnVector outV = (DecimalColumnVector) batch.cols[outputColumn];
    boolean[] outputIsNull = outV.isNull;

    if (n == 0) {
      // Nothing to do
      return;
    }

    // We do not need to do a column reset since we are carefully changing the output.
    outV.isRepeating = false;
    if (inV.isRepeating) {
      outV.isRepeating = true;
      if (inV.noNulls || !inV.isNull[0]) {
        outV.isNull[0] = false;
        convert(outV, inV, 0);
      } else {
        outV.isNull[0] = true;
        outV.noNulls = false;
      }
      return;
    }

    if (inV.noNulls) {
      if (batch.selectedInUse) {
        if (!outV.noNulls) {
          for(int j = 0; j != n; j++) {
            final int i = sel[j];
            outputIsNull[i] = false;
            convert(outV, inV, i);
          }
        } else {
          for(int j = 0; j != n; j++) {
            final int i = sel[j];
            convert(outV, inV, i);
          }
        }
      } else {
        if (!outV.noNulls) {
          Arrays.fill(outputIsNull, false);
          outV.noNulls = true;
        }
        for(int i = 0; i != n; i++) {
          convert(outV, inV, i);
        }
      }
    } else {
      // Do careful maintenance of the outputColVector.noNulls flag.
      if (batch.selectedInUse) {
        for(int j = 0; j != n; j++) {
          int i = sel[j];
          if (!inV.isNull[i]) {
            outV.isNull[i] = false;
            convert(outV, inV, i);
          } else {
            outV.isNull[i] = true;
            outV.noNulls = false;
          }
        }
      } else {
        for(int i = 0; i != n; i++) {
          if (!inV.isNull[i]) {
            outV.isNull[i] = false;
            convert(outV, inV, i);
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
            VectorExpressionDescriptor.ArgumentType.DECIMAL)
        .setInputExpressionTypes(
            VectorExpressionDescriptor.InputExpressionType.COLUMN);
    return b.build();
  }
}