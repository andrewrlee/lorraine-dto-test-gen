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

import static uk.co.optimisticpanda.gtest.dto.condition.Conditions.*;
import uk.co.optimisticpanda.gtest.dto.edit.Editor;
import uk.co.optimisticpanda.gtest.dto.edit.IncrementingNameEditor;
import uk.co.optimisticpanda.gtest.dto.rule.BaseEdit;
import uk.co.optimisticpanda.gtest.dto.rule.CombinedEdit;
import uk.co.optimisticpanda.gtest.dto.test.utils.TestDto1;


import junit.framework.TestCase;
import static org.assertj.core.api.Assertions.assertThat;
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
        Editor<TestDto1> edit1 = new IncrementingNameEditor<TestDto1>("name", EVEN_BASE_NAME);
        BaseEdit<TestDto1> evenRule = new BaseEdit<TestDto1>(edit1, index().isEven());
 
        Editor<TestDto1> edit2 = new IncrementingNameEditor<TestDto1>("name", ODD_BASE_NAME);
        BaseEdit<TestDto1> oddRule = new BaseEdit<TestDto1>(edit2, index().isOdd());
        
        //create empty rule
        CombinedEdit<TestDto1> combinedRule = new CombinedEdit<TestDto1>();
        
        //Add first rule
        combinedRule.addEdit(oddRule);
        
        TestDto1 dto1 = createTestDto();
        combinedRule.edit(1, dto1);
        assertThat(dto1.getName()).isEqualTo(ODD_BASE_NAME + 1);

        TestDto1 dto2 = createTestDto();
        combinedRule.edit(2, dto2);
        assertThat(dto2.getName() ).isEqualTo(UNCHANGED );

        //Add second rule
        combinedRule.addEdit(evenRule);
        
        TestDto1 dto3 = createTestDto();
        combinedRule.edit(1, dto3);
        assertThat(dto3.getName()).isEqualTo(ODD_BASE_NAME + 1);
        
        TestDto1 dto4 = createTestDto();
        combinedRule.edit(2, dto4);
        assertThat(dto4.getName() ).isEqualTo(EVEN_BASE_NAME + 2);
        
    }
    
    private TestDto1 createTestDto(){
        return new TestDto1(UNCHANGED );
    }
    
}
