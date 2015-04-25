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
package uk.co.optimisticpanda.gtest.dto.edit;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import ognl.ExpressionSyntaxException;
import ognl.OgnlException;
import uk.co.optimisticpanda.gtest.dto.RuleUtils;
import uk.co.optimisticpanda.gtest.dto.edit.IteratingCollectionEditor;
import uk.co.optimisticpanda.gtest.dto.edit.IteratingCollectionEditor.CycleBehaviour;
import uk.co.optimisticpanda.gtest.dto.test.utils.TestDto1;

import junit.framework.TestCase;
import static org.assertj.core.api.Assertions.assertThat;
/**
 * @author Andy Lee
 * 
 */
public class IteratingCollectionEditTest extends TestCase {

	private List<TestDto1> list;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		list = Arrays.asList(new TestDto1("name 1"),// 
				new TestDto1("name 2"),// 
				new TestDto1("name 3"),// 
				new TestDto1("name 4"),//
				new TestDto1("name 5"),//
				new TestDto1("name 6"));
	}

	/**
     * 
     */
	public void testExceptionHandling() {
		try {
			new IteratingCollectionEditor<TestDto1>("", Collections.EMPTY_LIST, CycleBehaviour.CYCLE);
			fail("Should fail");
		} catch (RuntimeException e) {
			assertThat(e.getCause().getClass()).isEqualTo(ExpressionSyntaxException.class);
		}

		try {
			IteratingCollectionEditor<TestDto1> empty = new IteratingCollectionEditor<TestDto1>("name", Collections.EMPTY_LIST,
					CycleBehaviour.THROW_EXCEPTION);
			empty.edit(-1, list.get(0));
			fail("Should fail");
		} catch (IndexOutOfBoundsException e) {
			// do nothing
		}

		// Same as above but CycleBehavior says don't fail.
		IteratingCollectionEditor<TestDto1> empty = new IteratingCollectionEditor<TestDto1>("name", Collections.EMPTY_LIST,
				CycleBehaviour.LEAVE_UNTOUCHED);
		empty.edit(-1, list.get(0));

		try {
			IteratingCollectionEditor<TestDto1> nullItemInCollection = new IteratingCollectionEditor<TestDto1>("name", Arrays.asList("NEW VALUE"),
					CycleBehaviour.THROW_EXCEPTION);
			nullItemInCollection.edit(-1, null);
			fail("Should fail");
		} catch (RuntimeException e) {
			assertThat(e.getCause().getClass()).isEqualTo(OgnlException.class);
		}
	}

	/**
     * 
     */
	public void testBasicExample() {
		List<String> values = Arrays.asList("newValue1", "newValue2", "newValue3");
		RuleUtils utils = new RuleUtils();
		Editor<TestDto1> edit = utils.iterate("name", values, CycleBehaviour.CYCLE);
		checkFirst3Calls(edit, "newValue1", "newValue2", "newValue3");
		checkSecond3Calls(edit, "newValue1", "newValue2", "newValue3");
	}

	/**
     * 
     */
	public void testCycleBehaviorLeaveUntouched() {
		List<String> values = Arrays.asList("newValue1", "newValue2");
		IteratingCollectionEditor<TestDto1> edit = new IteratingCollectionEditor<TestDto1>("name", values, CycleBehaviour.LEAVE_UNTOUCHED);
		checkFirst3Calls(edit, "newValue1", "newValue2", "name 3");
		checkSecond3Calls(edit, "name 4", "name 5", "name 6");
	}

	/**
     * 
     */
	public void testCycleBehaviorNullFill() {
		List<String> values = Arrays.asList("newValue1", "newValue2");
		IteratingCollectionEditor<TestDto1> edit = new IteratingCollectionEditor<TestDto1>("name", values, CycleBehaviour.NULL_FILL);
		checkFirst3Calls(edit, "newValue1", "newValue2", null);
		checkSecond3Calls(edit, null, null, null);
	}

	/**
     * 
     */
	public void testCycleBehaviorThrowException() {
		List<String> values = Arrays.asList("newValue1", "newValue2");
		IteratingCollectionEditor<TestDto1> edit = new IteratingCollectionEditor<TestDto1>("name", values, CycleBehaviour.THROW_EXCEPTION);
		try {
			checkFirst3Calls(edit, "newValue1", "newValue2", "Will throw an exception so this value not used");
			fail("Should fail");
		} catch (IndexOutOfBoundsException e) {
			// do nothing
		}
	}

	private void checkFirst3Calls(Editor<TestDto1> edit, String expected1, String expected2, String expected3) {
		edit.edit(-1, list.get(0));
		assertThat(list.get(0).getName()).isEqualTo(expected1);
		edit.edit(-1, list.get(1));
		assertThat(list.get(1).getName()).isEqualTo(expected2);
		edit.edit(-1, list.get(2));
		assertThat(list.get(2).getName()).isEqualTo(expected3);
	}

	private void checkSecond3Calls(Editor<TestDto1> edit, String expected1, String expected2, String expected3) {
		edit.edit(-1, list.get(3));
		assertThat(list.get(3).getName()).isEqualTo(expected1);
		edit.edit(-1, list.get(4));
		assertThat(list.get(4).getName()).isEqualTo(expected2);
		edit.edit(-1, list.get(5));
		assertThat(list.get(5).getName()).isEqualTo(expected3);
	}
}
