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

import static org.assertj.core.api.Assertions.assertThat;
import static uk.co.optimisticpanda.gtest.dto.edit.Editors.changeValueOf;
import junit.framework.TestCase;
import uk.co.optimisticpanda.gtest.dto.TestUtilsContext;
import uk.co.optimisticpanda.gtest.dto.test.utils.TestDto2;

public class CombinedEditTest extends TestCase{

    private static final String EDITED_TEXT = "EDITED";
    private TestDto2 testDto1 = new TestDto2("name1", "description1");
    private TestDto2 testDto2 = new TestDto2("name2", "description2");
    private TestDto2 testDto3 = new TestDto2("name3", "description3");
    private Editor nameValueEdit;
    private Editor descriptionValueEdit;
    private Editor multipleEdit;
	private Editor multipleEditWithList;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        TestUtilsContext.useOgnl();
        nameValueEdit = changeValueOf("name").to(EDITED_TEXT);
        descriptionValueEdit = changeValueOf("description").to(EDITED_TEXT);
        multipleEdit = nameValueEdit.and(descriptionValueEdit); 
        multipleEditWithList = new CombinedEditor(nameValueEdit, descriptionValueEdit); 
    }

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

    public void testMultipleEditWithListConstructor(){
        multipleEditWithList.edit(1, testDto3);
        assertThat(testDto3.getName()).isEqualTo(EDITED_TEXT);
        assertThat(testDto3.getDescription()).isEqualTo(EDITED_TEXT);
    }
}
