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

import uk.co.optimisticpanda.gtest.dto.condition.IndexCondition;

import junit.framework.TestCase;

/**
 * @author Andy Lee
 *
 */
public class IndexConditionTest extends TestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp();

    }

    /**
     * @throws Exception
     */
    @SuppressWarnings("boxing")
	public void testIndexIsNull() throws Exception {
        Integer nullIndex = null;
        try {
            new IndexCondition(nullIndex);
            fail("Should not not throw an exception");
        } catch (NullPointerException e) {
            // do nothing
        }
    }

    /**
     * @throws Exception
     */
    public void testIndexPrimVsClass() throws Exception {
        IndexCondition classIndexCondition = new IndexCondition(111);
        assertFalse(classIndexCondition.isValid(110, null));
        assertTrue(classIndexCondition.isValid(111, null));
        assertTrue(classIndexCondition.isValid(111, null));

        IndexCondition primIndexCondition = new IndexCondition(111);
        assertFalse(primIndexCondition.isValid(110, null));
        assertTrue(primIndexCondition.isValid(111, null));
        assertTrue(primIndexCondition.isValid(111, null));
    }

    // Above 128 to see if there is cache differences
    /**
     * @throws Exception
     */
    public void testIndexPrimVsClassAboveCache() throws Exception {
        IndexCondition classIndexCondition = new IndexCondition(567);
        assertFalse(classIndexCondition.isValid(566, null));
        assertTrue(classIndexCondition.isValid(567, null));
        assertTrue(classIndexCondition.isValid(567, null));

        IndexCondition primIndexCondition = new IndexCondition(567);
        assertFalse(primIndexCondition.isValid(566, null));
        assertTrue(primIndexCondition.isValid(567, null));
        assertTrue(primIndexCondition.isValid(567, null));
    }

}
