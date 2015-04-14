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


/**
 * @author Andy Lee
 *
 */
public class CombinedEditTest extends TestCase{

    private static final String EDITED_TEXT = "EDITED";
    private TestDto2 testDto1;
    private TestDto2 testDto2;
    private TestDto2 testDto3;
    private IEdit<TestDto2> nameValueEdit;
    private IEdit<TestDto2> descriptionValueEdit;
    private CombinedEdit<TestDto2> multipleEdit;
	private CombinedEdit<TestDto2> multipleEditWithList;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        TestUtilsContext.useOgnl();
        testDto1 = new TestDto2("name1", "description1");
        testDto2 = new TestDto2("name2", "description2");
        testDto3 = new TestDto2("name3", "description3");
        nameValueEdit = new SetValueEdit<TestDto2>("name", EDITED_TEXT);
        descriptionValueEdit = new SetValueEdit<TestDto2>("description", EDITED_TEXT);
        multipleEdit = new CombinedEdit<TestDto2>(nameValueEdit).addEdit(descriptionValueEdit); 

        List<IEdit<TestDto2>> list = new ArrayList<IEdit<TestDto2>>();
        list.add(nameValueEdit);
        list.add(descriptionValueEdit);
        multipleEditWithList = new CombinedEdit<TestDto2>(list); 

    }

    /**
     * 
     */
    public void testEmptyCombinedEdit(){
    	CombinedEdit<TestDto2> edit = new CombinedEdit<TestDto2>();
    	edit.edit(0, testDto1);
    	assertEquals("name1", testDto1.getName());
    	assertEquals("description1", testDto1.getDescription());
    }
    
    /**
     * 
     */
    public void testMultipleEdit(){
        nameValueEdit.edit(1, testDto1);
        assertEquals(EDITED_TEXT, testDto1.getName());
        assertEquals("description1", testDto1.getDescription());

        descriptionValueEdit.edit(1, testDto2);
        assertEquals("name2", testDto2.getName());
        assertEquals(EDITED_TEXT, testDto2.getDescription());

        multipleEdit.edit(1, testDto3);
        assertEquals(EDITED_TEXT, testDto3.getName());
        assertEquals(EDITED_TEXT, testDto3.getDescription());
    }

    /**
     * 
     */
    public void testAddEditWhenNullPassedIntoConstructor(){
    	List<IEdit<TestDto2>> list =null;
        CombinedEdit<TestDto2> combinedEdit = new CombinedEdit<TestDto2>(list);
        combinedEdit.addEdit(nameValueEdit);
    }
    
    /**
     * 
     */
    public void testMultipleEditWithListConstructor(){
        multipleEditWithList.edit(1, testDto3);
        assertEquals(EDITED_TEXT, testDto3.getName());
        assertEquals(EDITED_TEXT, testDto3.getDescription());
    }
}
