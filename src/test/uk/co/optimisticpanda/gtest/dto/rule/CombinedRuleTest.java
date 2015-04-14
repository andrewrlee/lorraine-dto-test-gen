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
import uk.co.optimisticpanda.gtest.dto.rule.CombinedRule;
import uk.co.optimisticpanda.gtest.dto.test.utils.TestDto1;


import junit.framework.TestCase;

/**
 * @author Andy Lee
 *
 */
public class CombinedRuleTest extends TestCase{

    private static final String EVEN_BASE_NAME = "even name-";
    private static final String ODD_BASE_NAME = "odd name-";
    private static final String UNCHANGED = "unchanged";

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    /**
     * @throws Exception
     */
    public void testCombinedEditRule() throws Exception {
        IEdit<TestDto1> edit1 = new IncrementingNameEdit<TestDto1>("name", EVEN_BASE_NAME);
        BaseRule<TestDto1> evenRule = new BaseRule<TestDto1>(edit1, EvenOddCondition.EVEN);
 
        IEdit<TestDto1> edit2 = new IncrementingNameEdit<TestDto1>("name", ODD_BASE_NAME);
        BaseRule<TestDto1> oddRule = new BaseRule<TestDto1>(edit2, EvenOddCondition.ODD);
        
        //create empty rule
        CombinedRule<TestDto1> combinedRule = new CombinedRule<TestDto1>();
        
        //Add first rule
        combinedRule.addEditRule(oddRule);
        
        TestDto1 dto1 = createTestDto();
        combinedRule.edit(1, dto1);
        assertEquals(ODD_BASE_NAME + 1, dto1.getName());

        TestDto1 dto2 = createTestDto();
        combinedRule.edit(2, dto2);
        assertEquals(UNCHANGED , dto2.getName() );

        //Add second rule
        combinedRule.addEditRule(evenRule);
        
        TestDto1 dto3 = createTestDto();
        combinedRule.edit(1, dto3);
        assertEquals(ODD_BASE_NAME + 1, dto3.getName());
        
        TestDto1 dto4 = createTestDto();
        combinedRule.edit(2, dto4);
        assertEquals(EVEN_BASE_NAME + 2, dto4.getName() );
        
    }
    
    private TestDto1 createTestDto(){
        return new TestDto1(UNCHANGED );
    }
    
}
