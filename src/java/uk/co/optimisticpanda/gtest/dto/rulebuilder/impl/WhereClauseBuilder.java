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

import uk.co.optimisticpanda.gtest.dto.condition.ICondition;
import uk.co.optimisticpanda.gtest.dto.rulebuilder.fluent.IWhereClauseBuilder;


class WhereClauseBuilder<D> implements IWhereClauseBuilder<D> {

	private ConditionBuilder<D> whereBuilder;

	public WhereClauseBuilder(ICondition condition) {
		this.whereBuilder = new ConditionBuilder<D>(condition);
	}

	@Override
	public IWhereClauseBuilder<D> and(ICondition condition) {
		this.whereBuilder.and(condition);
		return this;
	}

	@Override
	public IWhereClauseBuilder<D> and(IWhereClauseBuilder<D> builder) {
		this.whereBuilder.and(builder);
		return this;
	}

	@Override
	public IWhereClauseBuilder<D> andNot(ICondition condition) {
		this.whereBuilder.andNot(condition);
		return this;
	}

	@Override
	public IWhereClauseBuilder<D> andNot(IWhereClauseBuilder<D> builder) {
		this.whereBuilder.andNot(builder);
		return this;
	}

	@Override
	public IWhereClauseBuilder<D> or(ICondition condition) {
		this.whereBuilder.or(condition);
		return this;
	}

	@Override
	public IWhereClauseBuilder<D> or(IWhereClauseBuilder<D> builder) {
		this.whereBuilder.or(builder);
		return this;
	}

	@Override
	public IWhereClauseBuilder<D> orNot(ICondition condition) {
		this.whereBuilder.orNot(condition);
		return this;
	}

	@Override
	public IWhereClauseBuilder<D> orNot(IWhereClauseBuilder<D> builder) {
		this.whereBuilder.orNot(builder);
		return this;
	}

	@Override
	public ICondition getCondition() {
		return this.whereBuilder.build();
	}

}
