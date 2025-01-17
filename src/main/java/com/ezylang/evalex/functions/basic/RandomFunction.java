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
import com.ezylang.evalex.parser.Token;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.ArrayList;

/** Random function produces a random value between 0 and 1. */
public class RandomFunction extends AbstractFunction {

  @Override
  public EvaluationValue evaluate(
    Expression expression, Token functionToken, EvaluationValue... parameterValues) throws EvaluationException {

    SecureRandom secureRandom = new SecureRandom();
    if(parameterValues.length>1){
      throw new EvaluationException(functionToken, "Parameter count should less or equal to one");
    }
    if(parameterValues.length>0){
      EvaluationValue parameter = parameterValues[0];
      ArrayList<BigDecimal> list = new ArrayList<>();
      var length = parameter.getNumberValue();
      for (var i = 0;i < length.intValue(); i ++){
        list.add(expression.convertDoubleValue(secureRandom.nextDouble()).getNumberValue());
      }
      return new EvaluationValue(list);
    }
    else{
      return expression.convertDoubleValue(secureRandom.nextDouble());
    }


  }
}
