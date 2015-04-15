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
package uk.co.optimisticpanda.gtest.dto.condition;

import uk.co.optimisticpanda.gtest.dto.RuleUtils;
import uk.co.optimisticpanda.gtest.dto.condition.NthPlaceCondition;
import uk.co.optimisticpanda.gtest.dto.test.utils.TestDto1;

import junit.framework.TestCase;

/**
 * @author Andy Lee
 *
 */
public class NthPlaceConditionTest extends TestCase{

    /** 
     * 
     */
    public void setUp() throws Exception{
        super.setUp();
    }
    
    /**
     * 
     */
    public void testNthPlaceCondition2(){
        NthPlaceCondition condition = new NthPlaceCondition(2);
        assertTrue(condition.isValid(0, null));
        assertFalse(condition.isValid(1, null));
        assertTrue(condition.isValid(2, null));
        assertFalse(condition.isValid(3, null));
        assertTrue(condition.isValid(4, null));
    }

    /**
     * 
     */
    public void testNthPlaceCondition3(){
        RuleUtils<TestDto1> utils =new RuleUtils<TestDto1>();
    	ICondition condition = utils.nth(3);
        assertTrue(condition.isValid(0, null));
        assertFalse(condition.isValid(1, null));
        assertFalse(condition.isValid(2, null));
        assertTrue(condition.isValid(3, null));
        assertFalse(condition.isValid(5, null));
        assertTrue(condition.isValid(9, null));
    }

}
