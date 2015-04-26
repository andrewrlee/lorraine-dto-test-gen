package uk.co.optimisticpanda.gtest.dto.rulebuilder.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.co.optimisticpanda.gtest.dto.condition.Conditions.always;
import static uk.co.optimisticpanda.gtest.dto.condition.Conditions.index;
import static uk.co.optimisticpanda.gtest.dto.condition.Conditions.not;
import static uk.co.optimisticpanda.gtest.dto.condition.Conditions.valueOf;
import static uk.co.optimisticpanda.gtest.dto.edit.Editors.changeValueOf;
import static uk.co.optimisticpanda.gtest.dto.edit.Editors.incrementEach;

import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;
import uk.co.optimisticpanda.gtest.dto.SimpleDataEditor;
import uk.co.optimisticpanda.gtest.dto.TestUtilsContext;
import uk.co.optimisticpanda.gtest.dto.rule.Edit;
import uk.co.optimisticpanda.gtest.dto.rule.LabeledEdit;
import uk.co.optimisticpanda.gtest.dto.test.utils.TestDto2;
public class EditsTest extends TestCase {

	private static final String EDITTED_VALUE = "_editted_";
	private List<TestDto2> dtos;
	private Edits edits;

	@Override
	protected void setUp() throws Exception {
		TestUtilsContext.useOgnl();
		edits = Edits.doThis(changeValueOf("name").to(EDITTED_VALUE));
		TestDto2 testDto1 = new TestDto2("name1", "description1");
		TestDto2 testDto2 = new TestDto2("name2", "description2");
		TestDto2 testDto3 = new TestDto2("name3", "description3");
		dtos = Arrays.asList(testDto1, testDto2, testDto3);
	}

	public void testEdit() {
		Edit<TestDto2> edit = edits//
				.where(always())//
				.forTheType(TestDto2.class);
		SimpleDataEditor.<TestDto2>create().add(edit).edit(dtos);
		checkDto(dtos.get(0), EDITTED_VALUE, "description1");
		checkDto(dtos.get(1), EDITTED_VALUE, "description2");
	}

	public void testEditAnd() {
		Edit<TestDto2> edit = edits.and(incrementEach("description").withBase(""))
				.inEveryCase()
				.forTheType(TestDto2.class);
		SimpleDataEditor.<TestDto2>create().add(edit).edit(dtos);
		checkDto(dtos.get(0), EDITTED_VALUE, "0");
		checkDto(dtos.get(1), EDITTED_VALUE, "1");
	}

	public void testWhereOr() {
		Edit<TestDto2> edit = edits.and(incrementEach("description").withBase(""))//
				.where(index().isOdd().or(index().is(0)))//
				.forTheType(TestDto2.class);
		SimpleDataEditor.<TestDto2>create().add(edit).edit(dtos);
		checkDto(dtos.get(0), EDITTED_VALUE, "0");
		checkDto(dtos.get(1), EDITTED_VALUE, "1");
		checkDto(dtos.get(2), "name3", "description3");
	}

	public void testWhereAnd() {
		Edit<TestDto2> edit = edits.and(incrementEach("description").withBase(""))//
				.where(index().isEven().and(valueOf("name").is("name3")))//
				.forTheType(TestDto2.class);
		
		SimpleDataEditor.<TestDto2>create().add(edit).edit(dtos);
		checkDto(dtos.get(0), "name1", "description1");
		checkDto(dtos.get(1), "name2", "description2");
		checkDto(dtos.get(2), EDITTED_VALUE, "2");
	}

	public void testWhereAndNot() {
		Edit<TestDto2> edit = edits.and(incrementEach("description").withBase(""))//
				.where(index().isEven().and(not(valueOf("name").is("name3"))))//
				.forTheType(TestDto2.class);
		SimpleDataEditor.<TestDto2>create().add(edit).edit(dtos);
		checkDto(dtos.get(0), EDITTED_VALUE, "0");
		checkDto(dtos.get(1), "name2", "description2");
		checkDto(dtos.get(2), "name3", "description3");
	}

	public void testWhereOrNot() {
		Edit<TestDto2> edit = edits.and(incrementEach("description").withBase(""))//
				.where(index().isOdd().or(not(valueOf("name").is("name2"))))//
				.forTheType(TestDto2.class);
		SimpleDataEditor.<TestDto2>create().add(edit).edit(dtos);
		checkDto(dtos.get(0), EDITTED_VALUE, "0");
		checkDto(dtos.get(1), EDITTED_VALUE, "1");
		checkDto(dtos.get(2), EDITTED_VALUE, "2");
	}

	public void testLabel() {
		Edit<TestDto2> edit1 = edits//
				.where(always())//
				.forTheType(TestDto2.class);
		assertThat(edit1 instanceof LabeledEdit<?>).isFalse();

		Edit<TestDto2> edit2 = edits//
				.where(always())//
				.setLabel("this is the rules label")//
				.forTheType(TestDto2.class);
		assertThat(edit2 instanceof LabeledEdit<?>).isTrue();
		LabeledEdit<?> labledRule = (LabeledEdit<?>) edit2;
		assertThat(labledRule.getLabel()).isEqualTo("this is the rules label");
		assertThat(labledRule.toString()).isEqualTo("this is the rules label [SET ['name' to '_editted_'] WHERE ALWAYS]");

		Edit<TestDto2> edit3 = edits//
				.where(always())//
				.setLabel("this is the rules label")//
				.setLabel("changed label")//
				.forTheType(TestDto2.class);
		assertThat(edit3 instanceof LabeledEdit<?>).isTrue();
		LabeledEdit<?> labledRule2 = (LabeledEdit<?>) edit3;
		assertThat("this is the rules label".equals(labledRule2.getLabel())).isFalse();
		
	}

	private void checkDto(TestDto2 dto, String expectedName, String expectedDescription) {
		assertThat(dto.getName()).isEqualTo(expectedName);
		assertThat(dto.getDescription()).isEqualTo(expectedDescription);
	}
}
