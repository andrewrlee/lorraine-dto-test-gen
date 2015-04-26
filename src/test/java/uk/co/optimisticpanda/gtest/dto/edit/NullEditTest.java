package uk.co.optimisticpanda.gtest.dto.edit;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.co.optimisticpanda.gtest.dto.edit.Editors.changeValueOf;

import org.junit.Test;

import uk.co.optimisticpanda.gtest.dto.test.utils.TestDto2;

public class NullEditTest{

	@Test
	public void checkNullEdit() {
		TestDto2 dto = new TestDto2("name1", "description1");
		Editor nullEdit = changeValueOf("name").toNull();     

		assertThat(dto.getName()).isEqualTo("name1");
		nullEdit.edit(0, dto);
		assertThat(dto.getName()).isEqualTo(null);
	}
}
