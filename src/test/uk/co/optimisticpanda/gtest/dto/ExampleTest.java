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
package uk.co.optimisticpanda.gtest.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import junit.framework.TestCase;

import uk.co.optimisticpanda.gtest.dto.IDataEditor;
import uk.co.optimisticpanda.gtest.dto.RuleUtils;
import uk.co.optimisticpanda.gtest.dto.SimpleDataEditor;
import uk.co.optimisticpanda.gtest.dto.TestUtilsContext;
import uk.co.optimisticpanda.gtest.dto.rule.IRule;
import uk.co.optimisticpanda.gtest.dto.rulebuilder.impl.RuleFactory;

/**
 * @author Andy Lee A class showing basic usage
 */
public class ExampleTest extends TestCase {

	/**
	 */
	public void testExample() {

		// Configure the TestUtilsContext to use a PropertySupportFactory
		// This is used in introspection.
		TestUtilsContext.useOgnl();

		// RuleUtils provide easy access to built in edit and conditions
		RuleUtils<SampleDto> utils = new RuleUtils<SampleDto>();

		// Rules are created from a rule factory and need an edit to start.
		RuleFactory.startRule(utils.increment("name", "sample-"));

		// Each subsequent chained method call return interfaces that only
		// allows the writer to //call methods in the correct order.
		RuleFactory.startRule(utils.increment("name", "sample-")) // 
				.and(utils.set("date", new Date(System.currentTimeMillis())));

		// --------------------------------------------------------------------
		// Define some rules.
		IRule<SampleDto> rule1 = RuleFactory.startRule(utils.increment("name", "sample-")) //
				.and(utils.set("date", new Date(System.currentTimeMillis()))) //
				.where(utils.index(3)) //
				.orNot(utils.odd()) //
				.build();

		IRule<SampleDto> rule2 = RuleFactory.startRule(utils.set("name", "CHANGED")) //
				.where(utils.eq("name", "sample-3")) //
				.build();

		System.out.println("RULE1:" + rule1);
		System.out.println("RULE2:" + rule2);

		// Add the rules to a data editor
		IDataEditor<SampleDto> editor = new SimpleDataEditor<SampleDto>() //
				.addRule(rule1) //
				.addRule(rule2); //

		// Perform the actual editing
		List<SampleDto> list = new ArrayList<SampleDto>();
		for (int i = 0; i < 5; i++) {
			SampleDto dto = new SampleDto();
			editor.edit(i, dto);
			list.add(dto);
		}

		// See the output
		for (SampleDto sampleDto : list) {
			System.out.println(sampleDto);
		}

	}

	static class SampleDto {
		private String name;
		private Date date;

		public SampleDto() {
			// empty block
		}

		@Override
		public String toString() {
			return "name:" + name + "\tdate:" + date;
		}

	}

}
