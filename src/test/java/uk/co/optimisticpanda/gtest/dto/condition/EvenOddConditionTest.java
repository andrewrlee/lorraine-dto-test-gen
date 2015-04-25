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

import uk.co.optimisticpanda.gtest.dto.condition.EvenOddCondition;

import junit.framework.TestCase;
import static org.assertj.core.api.Assertions.assertThat;
/**
 * @author Andy Lee
 *
 */
public class EvenOddConditionTest extends TestCase {

    private EvenOddCondition even;
    private EvenOddCondition odd;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        even = EvenOddCondition.EVEN;
        odd = EvenOddCondition.ODD;
    }

    /**
     * @throws Exception
     */
    @SuppressWarnings({ "boxing", "null" })
	public void testEven() throws Exception {
        assertThat(even.isValid(0, "a")).isTrue();
        assertThat(even.isValid(0, null)).isTrue();
        try {
            Integer a = null;
            even.isValid(a, null);
            fail("Should fail if index is null");
        } catch (NullPointerException e) {
            // do nothing
        }
        assertThat(even.isValid(1, null)).isFalse();
        assertThat(even.isValid(2, null)).isTrue();
        assertThat(even.isValid(3, null)).isFalse();
    }

    /**
     * @throws Exception
     */
    @SuppressWarnings({ "boxing", "null" })
	public void testOdd() throws Exception {
        assertThat(odd.isValid(0, "a")).isFalse();
        assertThat(odd.isValid(0, null)).isFalse();
        try {
            Integer a = null;
            even.isValid(a, null);
            fail("Should fail if index is null");
        } catch (NullPointerException e) {
            // do nothing
        }
        assertThat(odd.isValid(1, null)).isTrue();
        assertThat(odd.isValid(2, null)).isFalse();
        assertThat(odd.isValid(3, null)).isTrue();
    }

}
