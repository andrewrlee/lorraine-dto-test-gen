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

import junit.framework.TestCase;
import uk.co.optimisticpanda.gtest.dto.RuleUtils;
import uk.co.optimisticpanda.gtest.dto.TestUtilsContext;
import uk.co.optimisticpanda.gtest.dto.condition.ICondition;
import uk.co.optimisticpanda.gtest.dto.rulebuilder.fluent.IWhereClauseBuilder;
import uk.co.optimisticpanda.gtest.dto.test.utils.TestDto1;
import static org.assertj.core.api.Assertions.assertThat;
/**
 * @author Andy Lee
 * 
 *         Indirectly covers ConditionBuilder too.
 * 
 */
public class WhereClauseBuilderTest extends TestCase {

	private RuleUtils ruleUtils;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		TestUtilsContext.useOgnl();
		ruleUtils = new RuleUtils();
	}

	/**
	 * @throws Exception
	 */
	public void testWhere() throws Exception {
		ICondition condition = getBuilder(ruleUtils.index(3))//
				.or(ruleUtils.even())//
				.and(ruleUtils.not(ruleUtils.eq("name", "notValue")))//
				.getCondition();//
		assertThat(condition.isValid(3, new TestDto1("notValue"))).isFalse();
		assertThat(condition.isValid(3, new TestDto1("vvvvvvvv"))).isTrue();
		assertThat(condition.isValid(5, new TestDto1("vvvvvvvv"))).isFalse();
		assertThat(condition.isValid(5, new TestDto1("notValue"))).isFalse();
		assertThat(condition.isValid(2, new TestDto1("vvvvvvvv"))).isTrue();
		assertThat(condition.isValid(2, new TestDto1("notValue"))).isFalse();
	}

	/**
	 * @throws Exception
	 */
	public void testAndNotWhere() throws Exception {
		ICondition condition = getBuilder(ruleUtils.index(3))//
				.or(ruleUtils.even())//
				.andNot(ruleUtils.eq("name", "notValue"))//
				.getCondition();//
		assertThat(condition.isValid(3, new TestDto1("notValue"))).isFalse();
		assertThat(condition.isValid(3, new TestDto1("vvvvvvvv"))).isTrue();
		assertThat(condition.isValid(5, new TestDto1("vvvvvvvv"))).isFalse();
		assertThat(condition.isValid(5, new TestDto1("notValue"))).isFalse();
		assertThat(condition.isValid(2, new TestDto1("vvvvvvvv"))).isTrue();
		assertThat(condition.isValid(2, new TestDto1("notValue"))).isFalse();
	}

	/**
	 * @throws Exception
	 */
	public void testAndNotWhereClause() throws Exception {
		ICondition condition = getBuilder(ruleUtils.index(3))//
				.or(ruleUtils.even())//
				.andNot(getClauseBuilder(ruleUtils.eq("name", "notValue")))//
				.getCondition();//
		assertThat(condition.isValid(3, new TestDto1("notValue"))).isFalse();
		assertThat(condition.isValid(3, new TestDto1("vvvvvvvv"))).isTrue();
		assertThat(condition.isValid(5, new TestDto1("vvvvvvvv"))).isFalse();
		assertThat(condition.isValid(5, new TestDto1("notValue"))).isFalse();
		assertThat(condition.isValid(2, new TestDto1("vvvvvvvv"))).isTrue();
		assertThat(condition.isValid(2, new TestDto1("notValue"))).isFalse();
	}

	/**
	 * @throws Exception
	 */
	public void testOrNotWhere() throws Exception {
		ICondition condition = getBuilder(ruleUtils.index(3))//
				.or(ruleUtils.even())//
				.orNot(ruleUtils.eq("name", "notValue"))//
				.getCondition();//
		assertThat(condition.isValid(3, new TestDto1("notValue"))).isTrue();
		assertThat(condition.isValid(3, new TestDto1("vvvvvvvv"))).isTrue();
		assertThat(condition.isValid(5, new TestDto1("vvvvvvvv"))).isTrue();
		assertThat(condition.isValid(5, new TestDto1("notValue"))).isFalse();
		assertThat(condition.isValid(2, new TestDto1("vvvvvvvv"))).isTrue();
		assertThat(condition.isValid(2, new TestDto1("notValue"))).isTrue();
	}

	/**
	 * @throws Exception
	 */
	public void testOrNotWhereClause() throws Exception {
		ICondition condition = getBuilder(ruleUtils.index(3))//
				.or(ruleUtils.even())//
				.orNot(getClauseBuilder(ruleUtils.eq("name", "notValue")))//
				.getCondition();//
		assertThat(condition.isValid(3, new TestDto1("notValue"))).isTrue();
		assertThat(condition.isValid(3, new TestDto1("vvvvvvvv"))).isTrue();
		assertThat(condition.isValid(5, new TestDto1("vvvvvvvv"))).isTrue();
		assertThat(condition.isValid(5, new TestDto1("notValue"))).isFalse();
		assertThat(condition.isValid(2, new TestDto1("vvvvvvvv"))).isTrue();
		assertThat(condition.isValid(2, new TestDto1("notValue"))).isTrue();
	}

	/**
	 * @throws Exception
	 */
	public void testOrWhere() throws Exception {
		ICondition condition = getBuilder(ruleUtils.index(3))//
				.or(ruleUtils.even())//
				.or(ruleUtils.eq("name", "notValue"))//
				.getCondition();//
		assertThat(condition.isValid(3, new TestDto1("notValue"))).isTrue();
		assertThat(condition.isValid(3, new TestDto1("vvvvvvvv"))).isTrue();
		assertThat(condition.isValid(5, new TestDto1("vvvvvvvv"))).isFalse();
		assertThat(condition.isValid(5, new TestDto1("notValue"))).isTrue();
		assertThat(condition.isValid(2, new TestDto1("vvvvvvvv"))).isTrue();
		assertThat(condition.isValid(2, new TestDto1("notValue"))).isTrue();
	}

	/**
	 * @throws Exception
	 */
	public void testOrWhereClause() throws Exception {
		ICondition condition = getBuilder(ruleUtils.index(3))//
				.or(ruleUtils.even())//
				.or(getClauseBuilder(ruleUtils.eq("name", "notValue")))//
				.getCondition();//
		assertThat(condition.isValid(3, new TestDto1("notValue"))).isTrue();
		assertThat(condition.isValid(3, new TestDto1("vvvvvvvv"))).isTrue();
		assertThat(condition.isValid(5, new TestDto1("vvvvvvvv"))).isFalse();
		assertThat(condition.isValid(5, new TestDto1("notValue"))).isTrue();
		assertThat(condition.isValid(2, new TestDto1("vvvvvvvv"))).isTrue();
		assertThat(condition.isValid(2, new TestDto1("notValue"))).isTrue();
	}
	
	/**
	 * @throws Exception
	 */
	public void testAndWhere() throws Exception {
		ICondition condition = getBuilder(ruleUtils.index(3))//
				.or(ruleUtils.even())//
				.and(ruleUtils.eq("name", "notValue"))//
				.getCondition();//
		assertThat(condition.isValid(3, new TestDto1("notValue"))).isTrue();
		assertThat(condition.isValid(3, new TestDto1("vvvvvvvv"))).isFalse();
		assertThat(condition.isValid(5, new TestDto1("vvvvvvvv"))).isFalse();
		assertThat(condition.isValid(5, new TestDto1("notValue"))).isFalse();
		assertThat(condition.isValid(2, new TestDto1("vvvvvvvv"))).isFalse();
		assertThat(condition.isValid(2, new TestDto1("notValue"))).isTrue();
	}

	/**
	 * @throws Exception
	 */
	public void testAndWhereBuilder() throws Exception {
		ICondition condition = getBuilder(ruleUtils.index(3))//
				.or(ruleUtils.even())//
				.and(getClauseBuilder(ruleUtils.eq("name", "notValue")))//
				.getCondition();//
		assertThat(condition.isValid(3, new TestDto1("notValue"))).isTrue();
		assertThat(condition.isValid(3, new TestDto1("vvvvvvvv"))).isFalse();
		assertThat(condition.isValid(5, new TestDto1("vvvvvvvv"))).isFalse();
		assertThat(condition.isValid(5, new TestDto1("notValue"))).isFalse();
		assertThat(condition.isValid(2, new TestDto1("vvvvvvvv"))).isFalse();
		assertThat(condition.isValid(2, new TestDto1("notValue"))).isTrue();
	}
	
	// Both these two methods return the same type of object but it replicates
	// how a user would access them.
	private IWhereClauseBuilder<TestDto1> getBuilder(ICondition condition) {
		return new WhereClauseBuilder<TestDto1>(condition);
	}

	private IWhereClauseBuilder<TestDto1> getClauseBuilder(ICondition condition) {
		return RuleFactory.createWhereClause(condition);
	}
}
