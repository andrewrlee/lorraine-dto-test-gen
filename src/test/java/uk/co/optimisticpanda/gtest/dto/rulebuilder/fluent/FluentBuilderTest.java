package uk.co.optimisticpanda.gtest.dto.rulebuilder.fluent;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.co.optimisticpanda.gtest.dto.edit.Editors.changeValueOf;
import static uk.co.optimisticpanda.gtest.dto.edit.Editors.incrementEach;

import org.junit.Test;

import uk.co.optimisticpanda.gtest.dto.TestUtilsContext;
import uk.co.optimisticpanda.gtest.dto.rule.Edit;
import uk.co.optimisticpanda.gtest.dto.rulebuilder.impl.Edits;
import uk.co.optimisticpanda.gtest.dto.test.utils.TestDto2;

public class FluentBuilderTest {

	{
		TestUtilsContext.useOgnl();
	}

	@Test
	public void testFlexibleBuilder() {
		
		Edit<TestDto2> edit = Edits.doThis(incrementEach("name").withBase("nameValue-")
											.and(changeValueOf("description").to("set value")))
										.inEveryCase()
										.forTheType(TestDto2.class);
		
		TestDto2 testDto = new TestDto2();
		edit.edit(0, testDto);
		assertThat(testDto.getName()).isEqualTo("nameValue-0");
		assertThat(testDto.getDescription()).isEqualTo("set value");
		
	}
}
