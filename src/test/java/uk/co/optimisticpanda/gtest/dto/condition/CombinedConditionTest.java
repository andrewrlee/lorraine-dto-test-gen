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
import static uk.co.optimisticpanda.gtest.dto.condition.CombinedCondition.BoolOp.AND;
import static uk.co.optimisticpanda.gtest.dto.condition.CombinedCondition.BoolOp.OR;
import junit.framework.TestCase;

import org.assertj.core.api.AbstractBooleanAssert;

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
        trueCondition = AlwaysCondition.ALWAYS;
        falseCondition = new NotCondition(AlwaysCondition.ALWAYS);
    }

    /**
     * @throws Exception
     */
    public void testCombinedAndCondition() throws Exception {
        check(AND,  trueCondition).isTrue();
        check(AND,  falseCondition).isFalse();
        check(AND,  falseCondition, falseCondition).isFalse();
        check(AND,  trueCondition, falseCondition).isFalse();
        check(AND,  trueCondition, trueCondition, falseCondition).isFalse();
    }
    
    /**
     * @throws Exception
     */
    public void testCombinedOrCondition() throws Exception {
        check(OR,  trueCondition).isTrue();
        check(OR,  falseCondition).isFalse();
        check(OR,  falseCondition, falseCondition).isFalse();
        check(OR,  trueCondition, falseCondition).isTrue();
        check(OR,  trueCondition, trueCondition, falseCondition).isTrue();
    }

    private AbstractBooleanAssert<?> check(CombinedCondition.BoolOp opp, ICondition... conditions) {
        CombinedCondition condition = new CombinedCondition(opp, conditions);
        return assertThat(condition.isValid(-1, null));
    }
    
}
