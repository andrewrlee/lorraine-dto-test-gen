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

import java.util.Date;

import uk.co.optimisticpanda.gtest.dto.edit.SetValueEdit;
import uk.co.optimisticpanda.gtest.dto.test.utils.DetailedTestDto;


import junit.framework.TestCase;

/**
 * @author Andy Lee
 *
 */
public class SetValueEditTest extends TestCase {

    private static final Date DATE_1 = new Date(1234567L);
    private DetailedTestDto testDto1;
    private DetailedTestDto testDto3;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        testDto1 = new DetailedTestDto("1", DATE_1, 4);
        testDto3 = new DetailedTestDto(null, null, 4);
    }

    /**
     * 
     */
    public void testPublicFieldWithGetterEquals(){
        SetValueEdit<DetailedTestDto> valueEdit = new SetValueEdit<DetailedTestDto>("name", "newValue");
        valueEdit.edit(1, testDto1);
        assertEquals("newValue", testDto1.getName());

        valueEdit.edit(1, testDto3);
        assertEquals("newValue", testDto1.getName());
        
    }
}
