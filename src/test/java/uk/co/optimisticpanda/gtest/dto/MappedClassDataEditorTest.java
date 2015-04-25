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
package uk.co.optimisticpanda.gtest.dto;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;

import junit.framework.TestCase;
import uk.co.optimisticpanda.gtest.dto.condition.AlwaysCondition;
import uk.co.optimisticpanda.gtest.dto.edit.Editor;
import uk.co.optimisticpanda.gtest.dto.edit.SetValueEditor;
import uk.co.optimisticpanda.gtest.dto.rule.BaseEdit;
import uk.co.optimisticpanda.gtest.dto.rule.Edit;
import uk.co.optimisticpanda.gtest.dto.test.utils.TestDto1;
import uk.co.optimisticpanda.gtest.dto.test.utils.TestDto2;

/**
 * @author Andy Lee
 *
 */
public class MappedClassDataEditorTest extends TestCase{

    /**
     * 
     */
    public static final String CHANGE_FOR_DTO1 = "DTO1";
    /**
     * 
     */
    public static final String CHANGE_FOR_DTO2 = "DTO2";
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        TestUtilsContext.useOgnl();
    }

    /**
     * 
     */
    public void testMappedClassDataEditor(){
        Editor<TestDto1> editForDto1 = new SetValueEditor<TestDto1>("name",CHANGE_FOR_DTO1);
        Editor<TestDto2> editForDto2 = new SetValueEditor<TestDto2>("name",CHANGE_FOR_DTO2);
        
        Edit<TestDto1> rule1 = new BaseEdit<TestDto1>(editForDto1, AlwaysCondition.ALWAYS);
        Edit<TestDto2> rule2 = new BaseEdit<TestDto2>(editForDto2, AlwaysCondition.ALWAYS);

        MappedClassDataEditor editor = new MappedClassDataEditor();
        editor.addEditForClass(TestDto1.class, rule1);
        editor.addEditForClass(TestDto2.class, rule2);
        
        TestDto1 dto1 = new TestDto1("name");
        TestDto2 dto2 = new TestDto2("name", "description");
        
        editor.edit(Arrays.asList(dto1, dto2));
        
        assertThat(dto1.getName()).isEqualTo(CHANGE_FOR_DTO1);
        assertThat(dto2.getName()).isEqualTo(CHANGE_FOR_DTO2);

    }
   
    
}
