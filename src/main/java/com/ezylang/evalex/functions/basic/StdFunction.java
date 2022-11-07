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

import com.ezylang.evalex.Expression;
import com.ezylang.evalex.data.EvaluationValue;
import com.ezylang.evalex.functions.AbstractFunction;
import com.ezylang.evalex.functions.FunctionParameter;
import com.ezylang.evalex.parser.Token;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;

/** Returns the sum value of all parameters. */
@FunctionParameter(name = "value", isVarArg = true)
public class StdFunction extends AbstractFunction {
  @Override
  public EvaluationValue evaluate(
      Expression expression, Token functionToken, EvaluationValue... parameterValues) {
    List<BigDecimal> numbers = new ArrayList<>();
    for (EvaluationValue parameter : parameterValues) {
      if(parameter.isArrayValue()){
        for (EvaluationValue evaluationValue:parameter.getArrayValue() ) {
          numbers.add(evaluationValue.getNumberValue() );
        }
      }
      else{
        numbers.add(parameter.getNumberValue() );
      }

    }
    return new EvaluationValue(POP_STD_dev(numbers.toArray(new BigDecimal[0]), expression.getConfiguration().getMathContext()));
  }


  // population standard deviation 总体标准差
  BigDecimal POP_STD_dev(BigDecimal[] data, MathContext context) {
    return POP_Variance(data,context).sqrt(context);
  }
  // population variance 总体方差
  BigDecimal POP_Variance(BigDecimal[] data, MathContext context) {
    BigDecimal variance = BigDecimal.ZERO;
    BigDecimal mean =  Mean(data,context);
    for (int i = 0; i < data.length; i++) {
      variance = variance.add ( (data[i].subtract(mean)).pow(2));
    }
    BigDecimal div = new BigDecimal( data.length - 1);
    variance = variance.divide(div,context);
    return variance;
  }

  BigDecimal Sum(BigDecimal[] data) {
    BigDecimal sum = BigDecimal.ZERO;
    for (int i = 0; i < data.length; i++)
      sum = sum.add( data[i]);
    return sum;
  }

  BigDecimal Mean(BigDecimal[] data, MathContext context) {
    return Sum(data).divide(new BigDecimal(data.length),context);
  }
}
