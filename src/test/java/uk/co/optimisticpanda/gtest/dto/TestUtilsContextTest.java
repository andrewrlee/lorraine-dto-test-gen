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

import uk.co.optimisticpanda.gtest.dto.propertyaccess.ognl.OgnlPropertyAccessFactory;
import uk.co.optimisticpanda.gtest.dto.propertyaccess.rflc.ReflectionPropertyAccessFactory;
import junit.framework.TestCase;

/**
 * @author Andy Lee
 * 
 */
public class TestUtilsContextTest extends TestCase {

	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}

	/**
	 * @throws Exception
	 */
	public void testTestUtilsContextInitialisation() throws Exception {
		TestUtilsContext.clearContext();
		try {
			TestUtilsContext.getOgnlContext();
			fail("Should throw exception as not properly intialized");
		} catch (IllegalStateException e) {
			String expectedMessage = "Test Utils Context is not initialised correctly. Call TestUtilsContext.useOgnl() or TestUtilsContext.useReflection() before using.";
			assertEquals(expectedMessage, e.getMessage());

		}
	}

	/**
	 * @throws Exception
	 */
	public void testUseOgnl() throws Exception {
		TestUtilsContext.clearContext();
		TestUtilsContext.useOgnl();
		assertNotNull(TestUtilsContext.getOgnlContext());
		assertNotNull(TestUtilsContext.getPropertyAccessFactory());
		assertTrue(TestUtilsContext.getPropertyAccessFactory() instanceof OgnlPropertyAccessFactory);
	}
	
	/**
	 * @throws Exception
	 */
	public void testUseReflection() throws Exception {
		TestUtilsContext.clearContext();
		TestUtilsContext.useReflection();
		assertNull(TestUtilsContext.getOgnlContext());
		assertNotNull(TestUtilsContext.getPropertyAccessFactory());
		assertTrue(TestUtilsContext.getPropertyAccessFactory() instanceof ReflectionPropertyAccessFactory);
	}
}
