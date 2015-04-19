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

import java.util.Date;

import uk.co.optimisticpanda.gtest.dto.condition.ValueEqualsCondition;
import uk.co.optimisticpanda.gtest.dto.test.utils.DetailedTestDto;


import junit.framework.TestCase;
import static org.assertj.core.api.Assertions.assertThat;
/**
 * @author Andy Lee
 *
 */
public class ValueEqualsConditionTest extends TestCase {

    private static final Date DATE_1 = new Date(1234567L);
    private static final Date DATE_2 = new Date(7654321L);
    private DetailedTestDto testDto1;
    private DetailedTestDto testDto2;
    private DetailedTestDto testDto3;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        testDto1 = new DetailedTestDto("1", DATE_1, 4);
        testDto2 = new DetailedTestDto("2", DATE_2, 5);
        testDto3 = new DetailedTestDto(null, null, 4);
    }

    /**
     * @throws Exception
     */
    public void testNullDto() throws Exception {
        ValueEqualsCondition valueEqualsCondition = new ValueEqualsCondition("name", "1");
        Object nullDto = null;
        try {
            valueEqualsCondition.isValid(1, nullDto);
            fail("Beanwrapper should throw illegal argument exception");
        } catch (RuntimeException e) {
            // do nothing
        }
    }
    
    /**
     * 
     */
    public void testPublicFieldWithGetterEquals(){
        ValueEqualsCondition valueEqualsCondition = new ValueEqualsCondition("name", "1");
        assertThat(valueEqualsCondition.isValid(0, testDto1)).isTrue();
        assertThat(valueEqualsCondition.isValid(0, testDto2)).isFalse();
        assertThat(valueEqualsCondition.isValid(0, testDto3)).isFalse();
        
        ValueEqualsCondition nullValueEqualsCondition = new ValueEqualsCondition("name", null);
        assertThat(nullValueEqualsCondition.isValid(0, testDto1)).isFalse();
        assertThat(nullValueEqualsCondition.isValid(0, testDto2)).isFalse();
        assertThat(nullValueEqualsCondition.isValid(0, testDto3)).isTrue();
    }
    
    /**
     * 
     */
    public void testPublicFieldWithoutGetterEquals(){
        ValueEqualsCondition valueEqualsCondition = new ValueEqualsCondition("date", DATE_1);
        assertThat(valueEqualsCondition.isValid(0, testDto1)).isTrue();
        assertThat(valueEqualsCondition.isValid(0, testDto2)).isFalse();
        assertThat(valueEqualsCondition.isValid(0, testDto3)).isFalse();

        ValueEqualsCondition valueEqualsCondition1 = new ValueEqualsCondition("date", DATE_2);
        assertThat(valueEqualsCondition1.isValid(0, testDto1)).isFalse();
        assertThat(valueEqualsCondition1.isValid(0, testDto2)).isTrue();
        assertThat(valueEqualsCondition1.isValid(0, testDto3)).isFalse();
        
        ValueEqualsCondition nullValueEqualsCondition = new ValueEqualsCondition("date", null);
        assertThat(nullValueEqualsCondition.isValid(0, testDto1)).isFalse();
        assertThat(nullValueEqualsCondition.isValid(0, testDto2)).isFalse();
        assertThat(nullValueEqualsCondition.isValid(0, testDto3)).isTrue();
    }

    /**
     * 
     */
    public void testPrivateFieldWithoutGetterEquals(){
        ValueEqualsCondition valueEqualsCondition = new ValueEqualsCondition("number", new Integer(4));
        assertThat(valueEqualsCondition.isValid(0, testDto1)).isTrue();
        assertThat(valueEqualsCondition.isValid(0, testDto2)).isFalse();
        
        ValueEqualsCondition nullValueEqualsCondition = new ValueEqualsCondition("date", null);
        assertThat(nullValueEqualsCondition.isValid(0, testDto1)).isFalse();
        assertThat(nullValueEqualsCondition.isValid(0, testDto2)).isFalse();
    }
  

}
