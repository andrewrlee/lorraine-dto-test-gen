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

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;
import uk.co.optimisticpanda.gtest.dto.TestUtilsContext;
import uk.co.optimisticpanda.gtest.dto.test.utils.TestDto2;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Andy Lee
 *
 */
public class CombinedEditTest extends TestCase{

    private static final String EDITED_TEXT = "EDITED";
    private TestDto2 testDto1;
    private TestDto2 testDto2;
    private TestDto2 testDto3;
    private Editor<TestDto2> nameValueEdit;
    private Editor<TestDto2> descriptionValueEdit;
    private CombinedEditor<TestDto2> multipleEdit;
	private CombinedEditor<TestDto2> multipleEditWithList;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        TestUtilsContext.useOgnl();
        testDto1 = new TestDto2("name1", "description1");
        testDto2 = new TestDto2("name2", "description2");
        testDto3 = new TestDto2("name3", "description3");
        nameValueEdit = new SetValueEditor<TestDto2>("name", EDITED_TEXT);
        descriptionValueEdit = new SetValueEditor<TestDto2>("description", EDITED_TEXT);
        multipleEdit = new CombinedEditor<TestDto2>(nameValueEdit).addEdit(descriptionValueEdit); 

        List<Editor<TestDto2>> list = new ArrayList<Editor<TestDto2>>();
        list.add(nameValueEdit);
        list.add(descriptionValueEdit);
        multipleEditWithList = new CombinedEditor<TestDto2>(list); 

    }

    /**
     * 
     */
    public void testEmptyCombinedEdit(){
    	CombinedEditor<TestDto2> edit = new CombinedEditor<TestDto2>();
    	edit.edit(0, testDto1);
    	assertThat(testDto1.getName()).isEqualTo("name1");
    	assertThat(testDto1.getDescription()).isEqualTo("description1");
    }
    
    /**
     * 
     */
    public void testMultipleEdit(){
        nameValueEdit.edit(1, testDto1);
        assertThat(testDto1.getName()).isEqualTo(EDITED_TEXT);
        assertThat(testDto1.getDescription()).isEqualTo("description1");

        descriptionValueEdit.edit(1, testDto2);
        assertThat(testDto2.getName()).isEqualTo("name2");
        assertThat(testDto2.getDescription()).isEqualTo(EDITED_TEXT);

        multipleEdit.edit(1, testDto3);
        assertThat(testDto3.getName()).isEqualTo(EDITED_TEXT);
        assertThat(testDto3.getDescription()).isEqualTo(EDITED_TEXT);
    }

    /**
     * 
     */
    public void testAddEditWhenNullPassedIntoConstructor(){
    	List<Editor<TestDto2>> list =null;
        CombinedEditor<TestDto2> combinedEdit = new CombinedEditor<TestDto2>(list);
        combinedEdit.addEdit(nameValueEdit);
    }
    
    /**
     * 
     */
    public void testMultipleEditWithListConstructor(){
        multipleEditWithList.edit(1, testDto3);
        assertThat(testDto3.getName()).isEqualTo(EDITED_TEXT);
        assertThat(testDto3.getDescription()).isEqualTo(EDITED_TEXT);
    }
}
