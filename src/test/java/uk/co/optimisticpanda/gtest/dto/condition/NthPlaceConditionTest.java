package uk.co.optimisticpanda.gtest.dto.condition;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.co.optimisticpanda.gtest.dto.condition.Conditions.everyNth;
import junit.framework.TestCase;

public class NthPlaceConditionTest extends TestCase{

    /** 
     * 
     */
    public void setUp() throws Exception{
        super.setUp();
    }
    
    public void testNthPlaceCondition2(){
    	Condition condition = everyNth(2);
        assertThat(condition.isValid(0, null)).isTrue();
        assertThat(condition.isValid(1, null)).isFalse();
        assertThat(condition.isValid(2, null)).isTrue();
        assertThat(condition.isValid(3, null)).isFalse();
        assertThat(condition.isValid(4, null)).isTrue();
    }

    public void testNthPlaceCondition3(){
    	Condition condition = everyNth(3);
        assertThat(condition.isValid(0, null)).isTrue();
        assertThat(condition.isValid(1, null)).isFalse();
        assertThat(condition.isValid(2, null)).isFalse();
        assertThat(condition.isValid(3, null)).isTrue();
        assertThat(condition.isValid(5, null)).isFalse();
        assertThat(condition.isValid(9, null)).isTrue();
    }

}
