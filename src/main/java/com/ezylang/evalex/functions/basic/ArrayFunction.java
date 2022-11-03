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
package com.ezylang.evalex.functions.basic;

import com.ezylang.evalex.EvaluationException;
import com.ezylang.evalex.Expression;
import com.ezylang.evalex.data.EvaluationValue;
import com.ezylang.evalex.functions.AbstractFunction;
import com.ezylang.evalex.functions.FunctionParameter;
import com.ezylang.evalex.parser.Token;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/** Factorial function, calculates the factorial of a base value. */
@FunctionParameter(name = "start")
@FunctionParameter(name = "length", nonZero = true, nonNegative = true)
@FunctionParameter(name = "step", isOptional = true)
public class ArrayFunction extends AbstractFunction {

  @Override
  public EvaluationValue evaluate(
      Expression expression, Token functionToken, EvaluationValue... parameterValues) throws EvaluationException {
    if(parameterValues.length<2){
      throw new EvaluationException(functionToken, "Parameter count mismatch");
    }
    if(parameterValues.length>3){
      throw new EvaluationException(functionToken, "Parameter count mismatch");
    }
    var context = expression.getConfiguration().getMathContext();
    BigDecimal start = parameterValues[0].getNumberValue();
    int count = parameterValues[1].getNumberValue().intValue();
    BigDecimal step = start;
    if (parameterValues.length == 3){
      BigDecimal addStep = parameterValues[2].getNumberValue();
      List<BigDecimal> list = new ArrayList<>();
      for(int i=0;i<count;i++){
        list.add((step));
        step = step.add(addStep, context);
      }
      return new EvaluationValue(list);
    }
    else{
      List<BigDecimal> list = new ArrayList<>();
      for(int i=0;i<count;i++){
        list.add((step));
        step = step.add(new BigDecimal(1, context));
      }
      return new EvaluationValue(list);
    }
  }
}
