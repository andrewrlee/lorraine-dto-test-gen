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
package uk.co.optimisticpanda.gtest.dto.rulebuilder.fluent;

import static org.assertj.core.api.Assertions.assertThat;
import junit.framework.TestCase;
import uk.co.optimisticpanda.gtest.dto.TestUtilsContext;
import uk.co.optimisticpanda.gtest.dto.condition.AlwaysCondition;
import uk.co.optimisticpanda.gtest.dto.edit.IncrementingNameEditor;
import uk.co.optimisticpanda.gtest.dto.edit.SetValueEditor;
import uk.co.optimisticpanda.gtest.dto.rule.Edit;
import uk.co.optimisticpanda.gtest.dto.rulebuilder.impl.Edits;
import uk.co.optimisticpanda.gtest.dto.test.utils.TestDto2;

/**
 * @author Andy Lee
 *
 */
public class FluentBuilderTest extends TestCase {

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		TestUtilsContext.useOgnl();
	}

	/**
	 * @throws Exception
	 */
	public void testFlexibleBuilder() throws Exception {
		Edit<TestDto2> editRule = Edits.doThis(
						new IncrementingNameEditor<TestDto2>("name", "nameValue-"))
				.andThen(   new SetValueEditor<TestDto2>("description", "unset value"))
				.where( AlwaysCondition.ALWAYS)
				.build();
		TestDto2 testDto = new TestDto2();
		editRule.edit(0, testDto);
		assertThat(testDto.getName()).isEqualTo("nameValue-0");
	}
}
