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

import java.util.Date;

import uk.co.optimisticpanda.gtest.dto.TestUtilsContext;
import uk.co.optimisticpanda.gtest.dto.propertyaccess.IPropertyAccess;
import uk.co.optimisticpanda.gtest.dto.propertyaccess.IPropertyAccessFactory;
import uk.co.optimisticpanda.gtest.dto.propertyaccess.PropertyAccessException;
import uk.co.optimisticpanda.gtest.dto.test.utils.DetailedTestDto;
import uk.co.optimisticpanda.gtest.dto.test.utils.TestDto1;
import uk.co.optimisticpanda.gtest.dto.util.PrivateFieldHelper;

import junit.framework.TestCase;
import static org.assertj.core.api.Assertions.assertThat;
/**
 * @author Andy Lee
 * 
 */
public class ReflectionExpressionTest extends TestCase {

	private static final String STARTING_STRING_VALUE = "start";
	private static final String CHANGED_STRING_VALUE = "changed";
	private static final Date CHANGED_DATE_VALUE = new Date(1L);
	private IPropertyAccessFactory propertyAccessFactory;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		TestUtilsContext.useReflection();
		propertyAccessFactory = TestUtilsContext.getPropertyAccessFactory();
	}

	/**
	 * @throws Exception
	 */
	public void testSimpleReflectionPropertyAccess() throws Exception {
		TestDto1 dto = new TestDto1(STARTING_STRING_VALUE);
		IPropertyAccess propertyAccess = propertyAccessFactory.createPropertyAccess("name");
		Object value = propertyAccess.getValue(dto);
		assertThat(value).isEqualTo(STARTING_STRING_VALUE);
	}

	/**
	 * @throws Exception
	 */
	public void testReflectionPropertyAccessPrivateMemberAccess() throws Exception {
		Date date = new Date();
		DetailedTestDto dto2 = new DetailedTestDto(STARTING_STRING_VALUE, date, 5);

		IPropertyAccess datePropertyAccess = propertyAccessFactory.createPropertyAccess("date");
		Date value = (Date) datePropertyAccess.getValue(dto2);

		IPropertyAccess timePropertyAccess = propertyAccessFactory.createPropertyAccess("fastTime");
		Long fastTime = (Long) timePropertyAccess.getValue(value);

		assertThat(fastTime.longValue()).isEqualTo(date.getTime());
	}

	/**
	 * @throws Exception
	 */
	public void testSimpleOgnlExpressionSetting() throws Exception {
		TestDto1 dto = new TestDto1(STARTING_STRING_VALUE);
		IPropertyAccess propertyAccess = propertyAccessFactory.createPropertyAccess("name");
		propertyAccess.setValue(dto, CHANGED_STRING_VALUE);
		assertThat(dto.getName()).isEqualTo(CHANGED_STRING_VALUE);
	}

	/**
	 * @throws Exception
	 */
	public void testOgnlExpressionPrivateMemberSetting() throws Exception {
		Date date = new Date();
		DetailedTestDto dto2 = new DetailedTestDto("hello", date, 5);

		IPropertyAccess datePropertyAccess = propertyAccessFactory.createPropertyAccess("date");
		Date value = (Date) datePropertyAccess.getValue(dto2);

		IPropertyAccess timePropertyAccess = propertyAccessFactory.createPropertyAccess("fastTime");
		timePropertyAccess.setValue(value, Long.valueOf(CHANGED_DATE_VALUE.getTime()));

		PrivateFieldHelper helper = new PrivateFieldHelper("date");
		Date changedDate = (Date) helper.get(dto2);
		assertThat(changedDate).isEqualTo(CHANGED_DATE_VALUE);
	}

	/**
	 * @throws Exception
	 */
	public void testPropertyAccessFactory() throws Exception {

		propertyAccessFactory.createPropertyAccess("dsfdsgdhfdfhdfhdf");

		try {
			propertyAccessFactory.createPropertyAccess(new String[0]);
			fail("Should throw exception!");
		} catch (PropertyAccessException e) {
			// do nothing as expected
		}
	}
}
