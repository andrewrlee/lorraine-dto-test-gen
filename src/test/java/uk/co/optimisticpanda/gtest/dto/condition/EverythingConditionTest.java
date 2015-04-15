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

import uk.co.optimisticpanda.gtest.dto.condition.AlwaysCondition;

import junit.framework.TestCase;

/**
 * @author Andy Lee
 *
 */
public class EverythingConditionTest extends TestCase{

    private AlwaysCondition instance;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        instance = AlwaysCondition.INSTANCE;
    }
    
    /**
     * @throws Exception
     */
    @SuppressWarnings("boxing")
	public void testNullIndex() throws Exception {
        Integer nullInt = null;
        try{
            instance.isValid(nullInt, null);
            fail("Should get a NPE");
        }catch(NullPointerException e){
            //do nothing
        }
        assertTrue(instance.isValid(1, null));
    }
    
}
