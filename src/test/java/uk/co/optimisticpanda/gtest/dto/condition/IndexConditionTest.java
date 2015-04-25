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

import static org.assertj.core.api.Assertions.assertThat;
import static uk.co.optimisticpanda.gtest.dto.condition.Conditions.index;
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
    @SuppressWarnings({ "boxing", "null" })
	public void testIndexIsNull() throws Exception {
        Integer nullIndex = null;
        try {
        	index().is(nullIndex);
            fail("Should not not throw an exception");
        } catch (NullPointerException e) {
            // do nothing
        }
    }

    /**
     * @throws Exception
     */
    public void testIndexPrimVsClass() throws Exception {
        ICondition classIndexCondition = index().is(111);
        assertThat(classIndexCondition.isValid(110, null)).isFalse();
        assertThat(classIndexCondition.isValid(111, null)).isTrue();
        assertThat(classIndexCondition.isValid(111, null)).isTrue();

        ICondition primIndexCondition = index().is(111);
        assertThat(primIndexCondition.isValid(110, null)).isFalse();
        assertThat(primIndexCondition.isValid(111, null)).isTrue();
        assertThat(primIndexCondition.isValid(111, null)).isTrue();
    }

    // Above 128 to see if there is cache differences
    /**
     * @throws Exception
     */
    public void testIndexPrimVsClassAboveCache() throws Exception {
        ICondition classIndexCondition = index().is(567);
        assertThat(classIndexCondition.isValid(566, null)).isFalse();
        assertThat(classIndexCondition.isValid(567, null)).isTrue();
        assertThat(classIndexCondition.isValid(567, null)).isTrue();

        ICondition primIndexCondition = index().is(567);
        assertThat(primIndexCondition.isValid(566, null)).isFalse();
        assertThat(primIndexCondition.isValid(567, null)).isTrue();
        assertThat(primIndexCondition.isValid(567, null)).isTrue();
    }

}
