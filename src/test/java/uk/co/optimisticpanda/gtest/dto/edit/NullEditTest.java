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

import uk.co.optimisticpanda.gtest.dto.test.utils.TestDto1;
import uk.co.optimisticpanda.gtest.dto.test.utils.TestDto2;
import junit.framework.TestCase;
import static org.assertj.core.api.Assertions.assertThat;
/**
 * @author Andy Lee
 */
public class NullEditTest extends TestCase{

	private TestDto2 testDto1;
	private NullEditor<TestDto1> nullEdit;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		testDto1 = new TestDto2("name1", "description1");
		nullEdit = new NullEditor<TestDto1>("name");     
	}

	/**
	 * @throws Exception
	 */
	public void testNullEdit() throws Exception {
		assertThat(testDto1.getName()).isEqualTo("name1");
		nullEdit.edit(0, testDto1);
		assertThat(testDto1.getName()).isEqualTo(null);
	}
	
}
