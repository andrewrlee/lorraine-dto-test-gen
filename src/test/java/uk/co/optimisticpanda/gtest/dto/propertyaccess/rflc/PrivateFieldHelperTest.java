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
package uk.co.optimisticpanda.gtest.dto.propertyaccess.rflc;

import uk.co.optimisticpanda.gtest.dto.propertyaccess.PropertyAccessException;
import uk.co.optimisticpanda.gtest.dto.test.utils.TestDto1;
import uk.co.optimisticpanda.gtest.dto.util.PrivateFieldHelper;
import junit.framework.TestCase;

/**
 * @author Andy Lee
 * 
 */
public class PrivateFieldHelperTest extends TestCase {

	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}

	/**
	 * @throws Exception
	 */
	public void testGetField() throws Exception {
		PrivateFieldHelper helper = new PrivateFieldHelper("name");
		Object retrievedValue = helper.get(new TestDto1("nameValue"));
		assertEquals("nameValue", retrievedValue);
	}

	/**
	 * @throws Exception
	 */
	public void testGetFieldDoesntExist() throws Exception {
		PrivateFieldHelper helper = new PrivateFieldHelper("fielddoesntexist");
		try {
			helper.get(new TestDto1("nameValue"));
			fail("Expected an exception as there is no property named fielddoesnotexist");
		} catch (PropertyAccessException e) {
			assertEquals("Could not find field name called:fielddoesntexist", e.getMessage());
		}
	}

	/**
	 * @throws Exception
	 */
	public void testSetField() throws Exception {
		PrivateFieldHelper helper = new PrivateFieldHelper("name");
		TestDto1 testDto1 = new TestDto1("nameValue");
		helper.set(testDto1, "newNameValue");
		assertEquals("newNameValue", testDto1.getName());
	}

	/**
	 * @throws Exception
	 */
	public void testSetFieldDoesntExist() throws Exception {
		PrivateFieldHelper helper = new PrivateFieldHelper("fielddoesntexist");
		TestDto1 testDto1 = new TestDto1("nameValue");
		try {
			helper.set(testDto1, "newNameValue");
			fail("Expected an exception as there is no property named fielddoesnotexist");
		} catch (PropertyAccessException e) {
			assertEquals("Could not find field name called:fielddoesntexist", e.getMessage());
		}
	}

}
