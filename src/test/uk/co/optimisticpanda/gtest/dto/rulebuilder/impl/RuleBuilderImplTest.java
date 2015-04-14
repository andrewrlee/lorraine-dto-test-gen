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

import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;
import uk.co.optimisticpanda.gtest.dto.RuleUtils;
import uk.co.optimisticpanda.gtest.dto.SimpleDataEditor;
import uk.co.optimisticpanda.gtest.dto.TestUtilsContext;
import uk.co.optimisticpanda.gtest.dto.rule.IRule;
import uk.co.optimisticpanda.gtest.dto.rule.LabeledRule;
import uk.co.optimisticpanda.gtest.dto.rulebuilder.fluent.IAddEditOrWhereBuilder;
import uk.co.optimisticpanda.gtest.dto.test.utils.TestDto2;

/**
 * This indirectly tests the rulebuilder impl tests
 * 
 * @author Andy Lee
 */
public class RuleBuilderImplTest extends TestCase {

	private static final String EDITTED_VALUE = "_editted_";
	private RuleUtils<TestDto2> utils;
	private IAddEditOrWhereBuilder<TestDto2> ruleBuilder;
	private List<TestDto2> dtos;

	@Override
	protected void setUp() throws Exception {
		TestUtilsContext.useOgnl();
		utils = new RuleUtils<TestDto2>();
		ruleBuilder = new RuleBuilderImpl<TestDto2>(utils.set("name", EDITTED_VALUE));
		TestDto2 testDto1 = new TestDto2("name1", "description1");
		TestDto2 testDto2 = new TestDto2("name2", "description2");
		TestDto2 testDto3 = new TestDto2("name3", "description3");
		dtos = Arrays.asList(testDto1, testDto2, testDto3);
	}

	/**
	 * @throws Exception
	 */
	public void testEdit() throws Exception {
		IRule<TestDto2> rule = ruleBuilder//
				.where(utils.all())//
				.build();
		new SimpleDataEditor<TestDto2>().addRule(rule).edit(dtos);
		checkDto(dtos.get(0), EDITTED_VALUE, "description1");
		checkDto(dtos.get(1), EDITTED_VALUE, "description2");
	}

	/**
	 * @throws Exception
	 */
	public void testEditAnd() throws Exception {
		IRule<TestDto2> rule = ruleBuilder.and(utils.increment("description", ""))//
				.where(utils.all())//
				.build();
		new SimpleDataEditor<TestDto2>().addRule(rule).edit(dtos);
		checkDto(dtos.get(0), EDITTED_VALUE, "0");
		checkDto(dtos.get(1), EDITTED_VALUE, "1");
	}

	/**
	 * @throws Exception
	 */
	public void testWhereOr() throws Exception {
		IRule<TestDto2> rule = ruleBuilder.and(utils.increment("description", ""))//
				.where(utils.odd())//
				.or(utils.index(0))//
				.build();
		new SimpleDataEditor<TestDto2>().addRule(rule).edit(dtos);
		checkDto(dtos.get(0), EDITTED_VALUE, "0");
		checkDto(dtos.get(1), EDITTED_VALUE, "1");
		checkDto(dtos.get(2), "name3", "description3");
	}

	/**
	 * @throws Exception
	 */
	public void testWhereAnd() throws Exception {
		IRule<TestDto2> rule = ruleBuilder.and(utils.increment("description", ""))//
				.where(utils.even())//
				.and(utils.eq("name", "name3"))//
				.build();
		new SimpleDataEditor<TestDto2>().addRule(rule).edit(dtos);
		checkDto(dtos.get(0), "name1", "description1");
		checkDto(dtos.get(1), "name2", "description2");
		checkDto(dtos.get(2), EDITTED_VALUE, "2");
	}

	/**
	 * @throws Exception
	 */
	public void testWhereAndNot() throws Exception {
		IRule<TestDto2> rule = ruleBuilder.and(utils.increment("description", ""))//
				.where(utils.even())//
				.andNot(utils.eq("name", "name3"))//
				.build();
		new SimpleDataEditor<TestDto2>().addRule(rule).edit(dtos);
		checkDto(dtos.get(0), EDITTED_VALUE, "0");
		checkDto(dtos.get(1), "name2", "description2");
		checkDto(dtos.get(2), "name3", "description3");
	}

	/**
	 * @throws Exception
	 */
	public void testWhereOrNot() throws Exception {
		IRule<TestDto2> rule = ruleBuilder.and(utils.increment("description", ""))//
				.where(utils.odd())//
				.orNot(utils.eq("name", "name2"))//
				.build();
		new SimpleDataEditor<TestDto2>().addRule(rule).edit(dtos);
		checkDto(dtos.get(0), EDITTED_VALUE, "0");
		checkDto(dtos.get(1), EDITTED_VALUE, "1");
		checkDto(dtos.get(2), EDITTED_VALUE, "2");
	}

	/**
	 * @throws Exception
	 */
	public void testLabel() throws Exception {
		IRule<TestDto2> rule1 = ruleBuilder//
				.where(utils.all())//
				.build();
		assertFalse(rule1 instanceof LabeledRule<?>);

		IRule<TestDto2> rule2 = ruleBuilder//
				.where(utils.all())//
				.setLabel("this is the rules label")//
				.build();
		assertTrue(rule2 instanceof LabeledRule<?>);
		LabeledRule<?> labledRule = (LabeledRule<?>) rule2;
		assertEquals("this is the rules label", labledRule.getLabel());
		assertEquals("this is the rules label [SET ['name' to '_editted_'] WHERE ALWAYS]",labledRule.toString());

		IRule<TestDto2> rule3 = ruleBuilder//
				.where(utils.all())//
				.setLabel("this is the rules label")//
				.setLabel("changed label")//
				.build();
		assertTrue(rule3 instanceof LabeledRule<?>);
		LabeledRule<?> labledRule2 = (LabeledRule<?>) rule3;
		assertFalse("this is the rules label".equals(labledRule2.getLabel()));
		
	}

	private void checkDto(TestDto2 dto, String expectedName, String expectedDescription) {
		assertEquals(expectedName, dto.getName());
		assertEquals(expectedDescription, dto.getDescription());
	}
}
