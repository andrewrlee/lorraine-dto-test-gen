/*
 * Copyright 2009 Andy Lee.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package uk.co.optimisticpanda.gtest.dto.condition;

import static uk.co.optimisticpanda.gtest.dto.condition.CombinedCondition.BoolOp.AND;
import static uk.co.optimisticpanda.gtest.dto.condition.CombinedCondition.BoolOp.OR;

/**
 * A check to see if a rule should fire or not
 * 
 * @author Andy Lee
 */
public interface Condition {

	<D> boolean isValid(int index, D dataItem);

	default Condition and(Condition condition){
		return new CombinedCondition(AND, this, condition);
	}
	
	default Condition or(Condition condition){
		return new CombinedCondition(OR, this, condition);
	}
}
