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
import uk.co.optimisticpanda.gtest.dto.condition.CombinedCondition;
import uk.co.optimisticpanda.gtest.dto.condition.ICondition;
import uk.co.optimisticpanda.gtest.dto.condition.NotCondition;
import uk.co.optimisticpanda.gtest.dto.condition.CombinedCondition.BoolOp;

import junit.framework.TestCase;

/**
 * @author Andy Lee
 *
 */
public class CombinedConditionTest extends TestCase {

    private AlwaysCondition trueCondition;
    private NotCondition falseCondition;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        trueCondition = AlwaysCondition.INSTANCE;
        falseCondition = new NotCondition(AlwaysCondition.INSTANCE);
    }

    /**
     * @throws Exception
     */
    public void testCombinedAndCondition() throws Exception {
        checkCondition(true, BoolOp.AND,  trueCondition);
        checkCondition(false, BoolOp.AND,  falseCondition);
        checkCondition(false, BoolOp.AND,  falseCondition, falseCondition);
        checkCondition(false, BoolOp.AND,  trueCondition, falseCondition);
        checkCondition(false, BoolOp.AND,  trueCondition, trueCondition, falseCondition);
    }
    
    /**
     * @throws Exception
     */
    public void testCombinedOrCondition() throws Exception {
        checkCondition(true, BoolOp.OR,  trueCondition);
        checkCondition(false, BoolOp.OR,  falseCondition);
        checkCondition(false, BoolOp.OR,  falseCondition, falseCondition);
        checkCondition(true, BoolOp.OR,  trueCondition, falseCondition);
        checkCondition(true, BoolOp.OR,  trueCondition, trueCondition, falseCondition);
    }

    private void checkCondition(boolean expectedResult, CombinedCondition.BoolOp opp, ICondition... conditions) {
        CombinedCondition condition = new CombinedCondition(opp, conditions);
        assertEquals(expectedResult, condition.isValid(-1, null));
    }
    
}
