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
package uk.co.optimisticpanda.gtest.dto.rulebuilder.impl;

import static uk.co.optimisticpanda.gtest.dto.condition.CombinedCondition.BoolOp.AND;
import static uk.co.optimisticpanda.gtest.dto.condition.CombinedCondition.BoolOp.OR;

import java.util.Objects;

import uk.co.optimisticpanda.gtest.dto.condition.CombinedCondition;
import uk.co.optimisticpanda.gtest.dto.condition.ICondition;
import uk.co.optimisticpanda.gtest.dto.condition.NotCondition;
import uk.co.optimisticpanda.gtest.dto.rulebuilder.fluent.IWhereClauseBuilder;


class ConditionBuilder<D> {
	private ICondition condition;

	public ConditionBuilder(ICondition condition) {
		if(condition == null){
			throw new IllegalArgumentException("Condition must not be null");
		}
		this.condition = condition;
	}

	public void and(ICondition andCondition) {
		this.condition = new CombinedCondition(AND, this.condition, andCondition);
	}

	public void or(ICondition orCondition) {
		this.condition = new CombinedCondition(OR, this.condition, orCondition);
	}

	public void andNot(ICondition andNotCondition) {
		this.condition = new CombinedCondition(AND, this.condition, new NotCondition(andNotCondition));
	}

	public void orNot(ICondition orCondition) {
		this.condition = new CombinedCondition(OR, this.condition, new NotCondition(orCondition));
	}

	public void and(IWhereClauseBuilder<D> builder) {
		this.and(builder.getCondition());
	}

	public void or(IWhereClauseBuilder<D> builder) {
		this.or(builder.getCondition());
	}

	public void andNot(IWhereClauseBuilder<D> builder) {
		this.andNot(builder.getCondition());
	}

	public void orNot(IWhereClauseBuilder<D> builder) {
		this.orNot(builder.getCondition());
	}

	public ICondition build() {
		return condition;
	}

}
