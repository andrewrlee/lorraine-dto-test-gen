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
package uk.co.optimisticpanda.gtest.dto.rule;

import uk.co.optimisticpanda.gtest.dto.condition.EvenOddCondition;
import uk.co.optimisticpanda.gtest.dto.edit.IEdit;
import uk.co.optimisticpanda.gtest.dto.edit.IncrementingNameEdit;
import uk.co.optimisticpanda.gtest.dto.rule.BaseRule;
import uk.co.optimisticpanda.gtest.dto.test.utils.TestDto1;
import junit.framework.TestCase;


/**
 * @author Andy Lee
 *
 */
public class BaseRuleTest extends TestCase{

    private BaseRule<TestDto1> rule;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        IEdit<TestDto1> edit = new IncrementingNameEdit<TestDto1>("name", "basename-");
        rule = new BaseRule<TestDto1>(edit, EvenOddCondition.EVEN);
    }

    /**
     * @throws Exception
     */
    public void testSimpleRuleOnNullDto() throws Exception {
        try{
        rule.edit(1, null);
        fail("Should throw npe");
        }catch(RuntimeException e){
            //do nothing
        } 
    }
    
    /**
     * @throws Exception
     */
    public void testSimpleRule() throws Exception {
        TestDto1 dto = new TestDto1(null);
        assertFalse(rule.isValid(1, dto));
        assertTrue(rule.isValid(2, dto));

        rule.edit(2, dto);
        assertEquals("basename-" + 2, dto.getName());
    }
    
}
