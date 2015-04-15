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
import uk.co.optimisticpanda.gtest.dto.edit.IEdit;
import uk.co.optimisticpanda.gtest.dto.rule.BaseRule;
import uk.co.optimisticpanda.gtest.dto.rule.IRule;
import uk.co.optimisticpanda.gtest.dto.rule.LabeledRule;
import uk.co.optimisticpanda.gtest.dto.rulebuilder.fluent.IAddEditOrWhereBuilder;
import uk.co.optimisticpanda.gtest.dto.rulebuilder.fluent.IAddWhereBuilder;
import uk.co.optimisticpanda.gtest.dto.rulebuilder.fluent.IAddWhereOrEndBuilder;
import uk.co.optimisticpanda.gtest.dto.rulebuilder.fluent.IWhereClauseBuilder;


class RuleBuilderImpl<D> implements IAddEditOrWhereBuilder<D>,
		IAddWhereOrEndBuilder<D> {

	private EditBuilder<D> editBuilder;
	private ConditionBuilder<D> whereBuilder;
	private String label;
	
	public RuleBuilderImpl(IEdit<D> edit) {
		editBuilder = new EditBuilder<D>(edit);
	}

	/** {@link IAddEditOrWhereBuilder} Methods*/

	@Override
	public IAddEditOrWhereBuilder<D> and(IEdit<D> edit) {
		editBuilder.and(edit);
		return this;
	}

	@Override
	public IAddWhereOrEndBuilder<D> where(ICondition condition) {
		this.whereBuilder = new ConditionBuilder<D>(condition);
		return this;
	}

	/*** {@link IAddWhereBuilder} Methods*/
	
	@Override
	public IAddWhereOrEndBuilder<D> and(ICondition condition) {
		whereBuilder.and(condition);
		return this;
	}

	@Override
	public IAddWhereOrEndBuilder<D> and(IWhereClauseBuilder<D> builder) {
		whereBuilder.and(builder);
		return this;
	}

	@Override
	public IAddWhereOrEndBuilder<D> andNot(ICondition condition) {
		whereBuilder.andNot(condition);
		return this;
	}

	@Override
	public IAddWhereOrEndBuilder<D> andNot(IWhereClauseBuilder<D> builder) {
		this.whereBuilder.andNot(builder);
		return this;
	}

	@Override
	public IAddWhereOrEndBuilder<D> or(ICondition condition) {
		this.whereBuilder.or(condition);
		return this;
	}

	@Override
	public IAddWhereOrEndBuilder<D> or(IWhereClauseBuilder<D> builder) {
		this.whereBuilder.or(builder);
		return this;
	}

	@Override
	public IAddWhereOrEndBuilder<D> orNot(ICondition condition) {
		this.whereBuilder.orNot(condition);
		return this;
	}

	@Override
	public IAddWhereOrEndBuilder<D> orNot(IWhereClauseBuilder<D> builder) {
		this.whereBuilder.orNot(builder);
		return this;
	}

	/*** {@link IAddWhereOrEndBuilder} Methods*/

	@Override
	public IAddWhereOrEndBuilder<D> setLabel(String label) {
		this.label = label;
		return this;
	}

	@Override
	public IRule<D> build() {
		BaseRule<D> rule = new BaseRule<D>(editBuilder.build(), whereBuilder.build());
		if(this.label != null){
			rule = new LabeledRule<D>(this.label, rule);
		}
		return rule;
	}

}
