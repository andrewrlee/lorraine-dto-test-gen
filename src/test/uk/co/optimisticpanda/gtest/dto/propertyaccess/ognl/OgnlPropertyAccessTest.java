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
package uk.co.optimisticpanda.gtest.dto.propertyaccess.ognl;

import java.util.Date;

import uk.co.optimisticpanda.gtest.dto.TestUtilsContext;
import uk.co.optimisticpanda.gtest.dto.propertyaccess.IPropertyAccess;
import uk.co.optimisticpanda.gtest.dto.propertyaccess.IPropertyAccessFactory;
import uk.co.optimisticpanda.gtest.dto.propertyaccess.PropertyAccessException;
import uk.co.optimisticpanda.gtest.dto.test.utils.DetailedTestDto;
import uk.co.optimisticpanda.gtest.dto.test.utils.TestDto1;
import uk.co.optimisticpanda.gtest.dto.util.PrivateFieldHelper;

import junit.framework.TestCase;


/**
 * @author Andy Lee
 *
 */
public class OgnlPropertyAccessTest extends TestCase {

    private static final String STARTING_STRING_VALUE = "start";
    private static final String CHANGED_STRING_VALUE = "changed";
    private static final Date CHANGED_DATE_VALUE = new Date(1L);
	private IPropertyAccessFactory propertyAccessFactory;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        TestUtilsContext.useOgnl();
        propertyAccessFactory = TestUtilsContext.getPropertyAccessFactory();
    }

    /**
     * @throws Exception
     */
    public void testSimpleOgnlExpressionAccess() throws Exception {
        TestDto1 dto = new TestDto1(STARTING_STRING_VALUE);
        IPropertyAccess propertyAccess = propertyAccessFactory.createPropertyAccess("name");
        Object value = propertyAccess.getValue(dto);
        assertEquals(STARTING_STRING_VALUE, value);
    }

    /**
     * @throws Exception
     */
    public void testOgnlExpressionPrivateMemberAccess() throws Exception {
        Date date = new Date();
        DetailedTestDto dto2 = new DetailedTestDto(STARTING_STRING_VALUE, date, 5);
        IPropertyAccess propertyAccess = propertyAccessFactory.createPropertyAccess("date.fastTime");
        Object value2 = propertyAccess.getValue( dto2);
        assertEquals(Long.valueOf(date.getTime()), value2);
    }

    /**
     * @throws Exception
     */
    public void testSimpleOgnlExpressionSetting() throws Exception {
        TestDto1 dto = new TestDto1(STARTING_STRING_VALUE);
        IPropertyAccess propertyAccess = propertyAccessFactory.createPropertyAccess("name");
        
        propertyAccess.setValue( dto, CHANGED_STRING_VALUE);
        assertEquals(CHANGED_STRING_VALUE, dto.getName());
    }

    /**
     * @throws Exception
     */
    public void testOgnlExpressionPrivateMemberSetting() throws Exception {
        Date date = new Date();
        DetailedTestDto dto2 = new DetailedTestDto("hello", date, 5);

        IPropertyAccess propertyAccess = propertyAccessFactory.createPropertyAccess("date.fastTime");

        propertyAccess.setValue(dto2, Long.valueOf(CHANGED_DATE_VALUE.getTime()));
        
        PrivateFieldHelper helper = new PrivateFieldHelper("date");
        Date changedDate = (Date)helper.get(dto2);
        assertEquals(CHANGED_DATE_VALUE, changedDate);
    }
    
    /**
     * 
     */
    public void testOgnlFactoryException() {
    	propertyAccessFactory.createPropertyAccess("dsfdsgdhfdfhdfhdf");

		try {
			propertyAccessFactory.createPropertyAccess(new String[0]);
			fail("Should throw exception!");
		} catch (PropertyAccessException e) {
			// do nothing as expected
		}
	}}
