package uk.co.optimisticpanda.gtest.dto.edit;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.co.optimisticpanda.gtest.dto.edit.Editors.changeValueOf;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import junit.framework.TestCase;
import ognl.ExpressionSyntaxException;
import ognl.OgnlException;
import uk.co.optimisticpanda.gtest.dto.TestUtilsContext;
import uk.co.optimisticpanda.gtest.dto.edit.IteratingCollectionEditor.CycleBehaviour;
import uk.co.optimisticpanda.gtest.dto.test.utils.TestDto1;

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
		TestUtilsContext.useOgnl();
	}

	/**
     * 
     */
	public void testExceptionHandling() {
		try {
			new IteratingCollectionEditor("", Collections.EMPTY_LIST, CycleBehaviour.CYCLE);
			fail("Should fail");
		} catch (RuntimeException e) {
			assertThat(e.getCause().getClass()).isEqualTo(ExpressionSyntaxException.class);
		}

		try {
			IteratingCollectionEditor empty = new IteratingCollectionEditor("name", Collections.EMPTY_LIST,
					CycleBehaviour.THROW_EXCEPTION);
			empty.edit(-1, list.get(0));
			fail("Should fail");
		} catch (IndexOutOfBoundsException e) {
			// do nothing
		}

		// Same as above but CycleBehavior says don't fail.
		IteratingCollectionEditor empty = new IteratingCollectionEditor("name", Collections.EMPTY_LIST,
				CycleBehaviour.LEAVE_UNTOUCHED);
		empty.edit(-1, list.get(0));

		try {
			IteratingCollectionEditor nullItemInCollection = new IteratingCollectionEditor("name", Arrays.asList("NEW VALUE"),
					CycleBehaviour.THROW_EXCEPTION);
			nullItemInCollection.edit(-1, null);
			fail("Should fail");
		} catch (RuntimeException e) {
			assertThat(e.getCause().getClass()).isEqualTo(OgnlException.class);
		}
	}

	public void testBasicExample() {
		Editor edit = changeValueOf("name").cycleBetween("newValue1", "newValue2", "newValue3");
		checkFirst3Calls(edit, "newValue1", "newValue2", "newValue3");
		checkSecond3Calls(edit, "newValue1", "newValue2", "newValue3");
	}

	public void testCycleBehaviorLeaveUntouched() {
		Editor edit = changeValueOf("name").use("newValue1", "newValue2").andLeaveTheRest();
		checkFirst3Calls(edit, "newValue1", "newValue2", "name 3");
		checkSecond3Calls(edit, "name 4", "name 5", "name 6");
	}

	public void testCycleBehaviorNullFill() {
		Editor edit = changeValueOf("name").use("newValue1", "newValue2").andThenFillWithNulls();
		checkFirst3Calls(edit, "newValue1", "newValue2", null);
		checkSecond3Calls(edit, null, null, null);
	}

	public void testCycleBehaviorThrowException() {
		Editor edit = changeValueOf("name").use("newValue1", "newValue2").andThenThrowException();
		try {
			checkFirst3Calls(edit, "newValue1", "newValue2", "Will throw an exception so this value not used");
			fail("Should fail");
		} catch (IndexOutOfBoundsException e) {
			// do nothing
		}
	}

	private void checkFirst3Calls(Editor edit, String expected1, String expected2, String expected3) {
		edit.edit(-1, list.get(0));
		assertThat(list.get(0).getName()).isEqualTo(expected1);
		edit.edit(-1, list.get(1));
		assertThat(list.get(1).getName()).isEqualTo(expected2);
		edit.edit(-1, list.get(2));
		assertThat(list.get(2).getName()).isEqualTo(expected3);
	}

	private void checkSecond3Calls(Editor edit, String expected1, String expected2, String expected3) {
		edit.edit(-1, list.get(3));
		assertThat(list.get(3).getName()).isEqualTo(expected1);
		edit.edit(-1, list.get(4));
		assertThat(list.get(4).getName()).isEqualTo(expected2);
		edit.edit(-1, list.get(5));
		assertThat(list.get(5).getName()).isEqualTo(expected3);
	}
}
