/*
  Copyright 2012-2022 Udo Klimaschewski

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

      http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
*/
package com.ezylang.evalex.operators.arithmetic;

import static com.ezylang.evalex.operators.OperatorIfc.OPERATOR_PRECEDENCE_ADDITIVE;

import com.ezylang.evalex.EvaluationException;
import com.ezylang.evalex.Expression;
import com.ezylang.evalex.data.EvaluationValue;
import com.ezylang.evalex.operators.AbstractOperator;
import com.ezylang.evalex.operators.InfixOperator;
import com.ezylang.evalex.parser.Token;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/** Subtraction of two numbers. */
@InfixOperator(precedence = OPERATOR_PRECEDENCE_ADDITIVE)
public class InfixMinusOperator extends AbstractOperator {

  @Override
  public EvaluationValue evaluate(
      Expression expression, Token operatorToken, EvaluationValue... operands)
      throws EvaluationException {
    EvaluationValue leftOperand = operands[0];
    EvaluationValue rightOperand = operands[1];
    if (leftOperand.isNumberValue() && rightOperand.isNumberValue()) {
      return new EvaluationValue(
          leftOperand
              .getNumberValue()
              .subtract(
                  rightOperand.getNumberValue(), expression.getConfiguration().getMathContext()));
    } if (leftOperand.isArrayValue() && rightOperand.isArrayValue()) {
      var left = leftOperand.getArrayValue();
      var right = rightOperand.getArrayValue();
      if(left.size()!=right.size()){
        throw new EvaluationException(operatorToken, "Array count should be equal");
      }
      List<BigDecimal> list = new ArrayList<>();
      for (int i = 0;i<left.size();i++){
        list.add((
                left.get(i)
                        .getNumberValue()
                        .subtract(
                                right.get(i).getNumberValue(), expression.getConfiguration().getMathContext())));
      }
      return new EvaluationValue(list);
    }
    else {
      throw EvaluationException.ofUnsupportedDataTypeInOperation(operatorToken);
    }
  }
}
