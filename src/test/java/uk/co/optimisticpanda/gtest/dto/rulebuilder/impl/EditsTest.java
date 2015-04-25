/*
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

import static org.assertj.core.api.Assertions.assertThat;
import static uk.co.optimisticpanda.gtest.dto.condition.Conditions.always;
import static uk.co.optimisticpanda.gtest.dto.condition.Conditions.index;
import static uk.co.optimisticpanda.gtest.dto.condition.Conditions.not;
import static uk.co.optimisticpanda.gtest.dto.condition.Conditions.valueOf;

import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;
import uk.co.optimisticpanda.gtest.dto.RuleUtils;
import uk.co.optimisticpanda.gtest.dto.SimpleDataEditor;
import uk.co.optimisticpanda.gtest.dto.TestUtilsContext;
import uk.co.optimisticpanda.gtest.dto.rule.Edit;
import uk.co.optimisticpanda.gtest.dto.rule.LabeledEdit;
import uk.co.optimisticpanda.gtest.dto.rulebuilder.fluent.IAddEditOrWhereBuilder;
import uk.co.optimisticpanda.gtest.dto.test.utils.TestDto2;
/**
 * This indirectly tests the rulebuilder impl tests
 * 
 * @author Andy Lee
 */
public class EditsTest extends TestCase {

	private static final String EDITTED_VALUE = "_editted_";
	private RuleUtils utils;
	private IAddEditOrWhereBuilder<TestDto2> edits;
	private List<TestDto2> dtos;

	@Override
	protected void setUp() throws Exception {
		TestUtilsContext.useOgnl();
		utils = new RuleUtils();
		edits = Edits.doThis(utils.<TestDto2>set("name", EDITTED_VALUE));
		TestDto2 testDto1 = new TestDto2("name1", "description1");
		TestDto2 testDto2 = new TestDto2("name2", "description2");
		TestDto2 testDto3 = new TestDto2("name3", "description3");
		dtos = Arrays.asList(testDto1, testDto2, testDto3);
	}

	/**
	 * @throws Exception
	 */
	public void testEdit() throws Exception {
		Edit<TestDto2> edit = edits//
				.where(always())//
				.build();
		new SimpleDataEditor<TestDto2>().addEdit(edit).edit(dtos);
		checkDto(dtos.get(0), EDITTED_VALUE, "description1");
		checkDto(dtos.get(1), EDITTED_VALUE, "description2");
	}

	/**
	 * @throws Exception
	 */
	public void testEditAnd() throws Exception {
		Edit<TestDto2> edit = edits.andThen(utils.increment("description", ""))//
				.where(always())//
				.build();
		new SimpleDataEditor<TestDto2>().addEdit(edit).edit(dtos);
		checkDto(dtos.get(0), EDITTED_VALUE, "0");
		checkDto(dtos.get(1), EDITTED_VALUE, "1");
	}

	/**
	 * @throws Exception
	 */
	public void testWhereOr() throws Exception {
		Edit<TestDto2> edit = edits.andThen(utils.increment("description", ""))//
				.where(index().isOdd())//
				.or(index().is(0))//
				.build();
		new SimpleDataEditor<TestDto2>().addEdit(edit).edit(dtos);
		checkDto(dtos.get(0), EDITTED_VALUE, "0");
		checkDto(dtos.get(1), EDITTED_VALUE, "1");
		checkDto(dtos.get(2), "name3", "description3");
	}

	/**
	 * @throws Exception
	 */
	public void testWhereAnd() throws Exception {
		Edit<TestDto2> edit = edits.andThen(utils.increment("description", ""))//
				.where(index().isEven())//
				.and(valueOf("name").is("name3"))//
				.build();
		
		new SimpleDataEditor<TestDto2>().addEdit(edit).edit(dtos);
		checkDto(dtos.get(0), "name1", "description1");
		checkDto(dtos.get(1), "name2", "description2");
		checkDto(dtos.get(2), EDITTED_VALUE, "2");
	}

	/**
	 * @throws Exception
	 */
	public void testWhereAndNot() throws Exception {
		Edit<TestDto2> edit = edits.andThen(utils.increment("description", ""))//
				.where(index().isEven())//
				.and(not(valueOf("name").is("name3")))//
				.build();
		new SimpleDataEditor<TestDto2>().addEdit(edit).edit(dtos);
		checkDto(dtos.get(0), EDITTED_VALUE, "0");
		checkDto(dtos.get(1), "name2", "description2");
		checkDto(dtos.get(2), "name3", "description3");
	}

	/**
	 * @throws Exception
	 */
	public void testWhereOrNot() throws Exception {
		Edit<TestDto2> edit = edits.andThen(utils.increment("description", ""))//
				.where(index().isOdd())//
				.or(not(valueOf("name").is("name2")))//
				.build();
		new SimpleDataEditor<TestDto2>().addEdit(edit).edit(dtos);
		checkDto(dtos.get(0), EDITTED_VALUE, "0");
		checkDto(dtos.get(1), EDITTED_VALUE, "1");
		checkDto(dtos.get(2), EDITTED_VALUE, "2");
	}

	/**
	 * @throws Exception
	 */
	public void testLabel() throws Exception {
		Edit<TestDto2> edit1 = edits//
				.where(always())//
				.build();
		assertThat(edit1 instanceof LabeledEdit<?>).isFalse();

		Edit<TestDto2> edit2 = edits//
				.where(always())//
				.setLabel("this is the rules label")//
				.build();
		assertThat(edit2 instanceof LabeledEdit<?>).isTrue();
		LabeledEdit<?> labledRule = (LabeledEdit<?>) edit2;
		assertThat(labledRule.getLabel()).isEqualTo("this is the rules label");
		assertThat(labledRule.toString()).isEqualTo("this is the rules label [SET ['name' to '_editted_'] WHERE ALWAYS]");

		Edit<TestDto2> edit3 = edits//
				.where(always())//
				.setLabel("this is the rules label")//
				.setLabel("changed label")//
				.build();
		assertThat(edit3 instanceof LabeledEdit<?>).isTrue();
		LabeledEdit<?> labledRule2 = (LabeledEdit<?>) edit3;
		assertThat("this is the rules label".equals(labledRule2.getLabel())).isFalse();
		
	}

	private void checkDto(TestDto2 dto, String expectedName, String expectedDescription) {
		assertThat(dto.getName()).isEqualTo(expectedName);
		assertThat(dto.getDescription()).isEqualTo(expectedDescription);
	}
}
