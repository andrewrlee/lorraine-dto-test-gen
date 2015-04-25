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
import uk.co.optimisticpanda.gtest.dto.test.utils.TestDto1;
import junit.framework.TestCase;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Andy Lee
 *
 */
public class BaseRuleTest extends TestCase{

    private BaseEdit<TestDto1> rule;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        Editor<TestDto1> edit = new IncrementingNameEditor<TestDto1>("name", "basename-");
        rule = new BaseEdit<TestDto1>(edit, index().isEven());
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
        assertThat(rule.isValid(1, dto)).isFalse();
        assertThat(rule.isValid(2, dto)).isTrue();

        rule.edit(2, dto);
        assertThat(dto.getName()).isEqualTo("basename-" + 2);
    }
    
}
