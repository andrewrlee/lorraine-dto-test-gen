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
package uk.co.optimisticpanda.gtest.dto.edit;

import uk.co.optimisticpanda.gtest.dto.edit.IncrementingNameEdit;
import uk.co.optimisticpanda.gtest.dto.test.utils.TestDto1;


import junit.framework.TestCase;
import static org.assertj.core.api.Assertions.assertThat;
/**
 * @author Andy Lee
 *
 */
public class IncrementingNameEditTest extends TestCase{
    
    private IncrementingNameEdit<TestDto1> incrementingNameEdit;
    
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        incrementingNameEdit = new IncrementingNameEdit<TestDto1>("name" , "myName-");
    }

    
    /**
     * @throws Exception
     */
    public void testIncrementingNameEdit() throws Exception {
        TestDto1 testDto1 = new TestDto1(null);
        incrementingNameEdit.edit(1, testDto1);
        assertThat(testDto1.getName()).isEqualTo("myName-1");

        TestDto1 testDto2 = new TestDto1("test1");
        incrementingNameEdit.edit(1, testDto2);
        assertThat(testDto2.getName()).isEqualTo("myName-1");

        TestDto1 testDto3 = new TestDto1("test1");
        incrementingNameEdit.edit(2, testDto3);
        assertThat(testDto3.getName()).isEqualTo("myName-2");
    }
}
