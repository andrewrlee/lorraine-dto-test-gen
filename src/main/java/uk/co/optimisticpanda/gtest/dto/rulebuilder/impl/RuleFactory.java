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

import java.util.function.Function;

import uk.co.optimisticpanda.gtest.dto.RuleUtils;
import uk.co.optimisticpanda.gtest.dto.condition.ICondition;
import uk.co.optimisticpanda.gtest.dto.edit.IEdit;
import uk.co.optimisticpanda.gtest.dto.rulebuilder.fluent.IAddEditOrWhereBuilder;
import uk.co.optimisticpanda.gtest.dto.rulebuilder.fluent.IWhereClauseBuilder;

/**
 * @author Andy Lee
 * 
 */
public enum RuleFactory {
	/**
	 * The singleton instance
	 */
	INSTANCE;

	/**
	 * @param <D>
	 *            The type that we want to build a rule for.
	 * @param edit
	 *            The original edit
	 * @return an interface to allow chaining to continue building the rule.
	 */
	public static <D> IAddEditOrWhereBuilder<D> startRule(IEdit<D> edit) {
		return new RuleBuilderImpl<D>(edit);
	}

	/**
	 * @param <D>
	 *            The type that we want to build a rule for.
	 * @param edit
	 *            The original edit
	 * @return an interface to allow chaining to continue building the rule.
	 */
	public static <D> IAddEditOrWhereBuilder<D> startRule(Function<RuleUtils, IEdit<D>> edit) {
		return new RuleBuilderImpl<D>(edit.apply(new RuleUtils()));
	}
	
	/**
	 * @param <D>
	 *            The type that we want to build a rule for.
	 * @param condition
	 *            The initial condition
	 * @return an interface to allow chaining to continue building up the condition.
	 */
	public static <D> IWhereClauseBuilder<D> createWhereClause(ICondition condition) {
		return new WhereClauseBuilder<D>(condition);
	}
}
