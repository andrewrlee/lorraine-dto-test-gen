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

import java.util.Arrays;
import java.util.List;

import uk.co.optimisticpanda.gtest.dto.SimpleDataEditor;
import uk.co.optimisticpanda.gtest.dto.TestUtilsContext;
import uk.co.optimisticpanda.gtest.dto.condition.EvenOddCondition;
import uk.co.optimisticpanda.gtest.dto.condition.IndexCondition;
import uk.co.optimisticpanda.gtest.dto.condition.ValueEqualsCondition;
import uk.co.optimisticpanda.gtest.dto.edit.IEdit;
import uk.co.optimisticpanda.gtest.dto.edit.IncrementingNameEdit;
import uk.co.optimisticpanda.gtest.dto.edit.SetValueEdit;
import uk.co.optimisticpanda.gtest.dto.rule.BaseRule;
import uk.co.optimisticpanda.gtest.dto.rule.IRule;
import uk.co.optimisticpanda.gtest.dto.test.utils.TestDto1;

import junit.framework.TestCase;


/**
 * The idea is that you would testHelper methods would always create varied sized lists of generic entities.
 * You would also pass in a TestDataEditor which would act like a callback to alter the list in some ways.
 * public List<Dto> getList(int numberOfDtosToBeCreated, TestDataEditor editor){
 *      List<Dto> dtos = new ArrayList<Dto>();
 *      for( int i=0; i< numberOfDtosToBeCreated ; i++){
 *          Dto dto = createDto();
 *          editor.edit(i, dto);
 *          store(dto);
 *          dtos.add(dto);
 *      }
 *      return dtos;
 * } 
 *   @author Andy Lee
 * */
public class SimpleDataEditorTest extends TestCase {

    private List<TestDto1> list;
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        TestUtilsContext.useOgnl();
        list = Arrays.asList( //
                new TestDto1("0"), //
                new TestDto1("1"), //
                new TestDto1("2"), //
                new TestDto1("3"), //
                new TestDto1("4"), //
                new TestDto1("5"), //
                new TestDto1("HELLO"));
    }

    /**
     * @throws Exception
     */
    public void testTestDataEditorWithNoConfiguration() throws Exception {
        SimpleDataEditor<TestDto1> testDto = new SimpleDataEditor<TestDto1>();

        testDto.edit(list);

        assertEquals("0", list.get(0).getName());
        assertEquals("1", list.get(1).getName());
        assertEquals("2", list.get(2).getName());
        assertEquals("3", list.get(3).getName());
        assertEquals("4", list.get(4).getName());
        assertEquals("5", list.get(5).getName());
    }

    /**
     * @throws Exception
     */
    public void testTestDataEditorWithSimpleConfiguration() throws Exception {
        IEdit<TestDto1> edit = new IncrementingNameEdit<TestDto1>("name", "basename-");
        IRule<TestDto1> rule = new BaseRule<TestDto1>(edit, EvenOddCondition.EVEN);
        
        SimpleDataEditor<TestDto1> editor = new SimpleDataEditor<TestDto1>();
        editor.addRule(rule);
        editor.edit(list);

        assertEquals("basename-0", list.get(0).getName());
        assertEquals("1", list.get(1).getName());
        assertEquals("basename-2", list.get(2).getName());
        assertEquals("3", list.get(3).getName());
        assertEquals("basename-4", list.get(4).getName());
        assertEquals("5", list.get(5).getName());
    }

    /**
     * @throws Exception
     */
    public void testTestDataEditorWithMultipleMatchers() throws Exception {
        IEdit<TestDto1> edit = new IncrementingNameEdit<TestDto1>("name", "basename-");
        IRule<TestDto1> rule = new BaseRule<TestDto1>(edit, EvenOddCondition.EVEN, new IndexCondition(5));

        SimpleDataEditor<TestDto1> editor = new SimpleDataEditor<TestDto1>();
        editor.addRule(rule);
        editor.edit(list);

        assertEquals("basename-0", list.get(0).getName());
        assertEquals("1", list.get(1).getName());
        assertEquals("basename-2", list.get(2).getName());
        assertEquals("3", list.get(3).getName());
        assertEquals("basename-4", list.get(4).getName());
        assertEquals("basename-5", list.get(5).getName());
    }

    /**
     * @throws Exception
     */
    public void testTestDataEditorConfiguredToAlterSpecificElement() throws Exception {
        IEdit<TestDto1> edit = new SetValueEdit<TestDto1>("name", "CENSORED");
        IRule<TestDto1> rule = new BaseRule<TestDto1>(edit, new IndexCondition(4), new ValueEqualsCondition("name", "HELLO"));

        SimpleDataEditor<TestDto1> editor = new SimpleDataEditor<TestDto1>();
        editor.addRule(rule);
        editor.edit(list);

        assertEquals("0", list.get(0).getName());
        assertEquals("1", list.get(1).getName());
        assertEquals("2", list.get(2).getName());
        assertEquals("3", list.get(3).getName());
        assertEquals("CENSORED", list.get(4).getName());
        assertEquals("5", list.get(5).getName());
        assertEquals("CENSORED", list.get(6).getName());
    }

    /**
     * @throws Exception
     */
    public void testTestDataEditorConfiguredToAlterSpecificElementOnSpecificElements() throws Exception {
        IEdit<TestDto1> edit = new SetValueEdit<TestDto1>("name", "CENSORED");
        IRule<TestDto1> rule = new BaseRule<TestDto1>(edit, new IndexCondition(4), new ValueEqualsCondition("name", "HELLO"));

        SimpleDataEditor<TestDto1> editor = new SimpleDataEditor<TestDto1>();
        editor.addRule(rule);
        editor.edit(list);

        editor.edit(1, list.get(0));
        assertEquals("0", list.get(0).getName());
        editor.edit(4, list.get(0));
        assertEquals("CENSORED", list.get(0).getName());
    }

}
