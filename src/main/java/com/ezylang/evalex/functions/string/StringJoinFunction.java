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
package com.ezylang.evalex.functions.string;

import com.ezylang.evalex.Expression;
import com.ezylang.evalex.data.EvaluationValue;
import com.ezylang.evalex.functions.AbstractFunction;
import com.ezylang.evalex.functions.FunctionParameter;
import com.ezylang.evalex.parser.Token;

/** Converts the given value to lower case. */
@FunctionParameter(name = "delimiter")
@FunctionParameter(name = "value", isVarArg = true)
public class StringJoinFunction extends AbstractFunction {
  @Override
  public EvaluationValue evaluate(
      Expression expression, Token functionToken, EvaluationValue... parameterValues) {
    StringBuilder sb = new StringBuilder();
    boolean first = true;
    var delimiter = parameterValues[0].getStringValue();
    for(int i = 1;i<parameterValues.length;i++){
      var item = parameterValues[i];
      if(item.isArrayValue()){
        for (var sub : item.getArrayValue()) {
          if(!first)
            sb.append(delimiter);
          sb.append(sub.getStringValue());
          first = false;
        }

      }
      else {
        if(!first)
          sb.append(delimiter);
        sb.append(item.getStringValue());
        first = false;
      }
    }
    return new EvaluationValue(sb.toString());
  }
}
