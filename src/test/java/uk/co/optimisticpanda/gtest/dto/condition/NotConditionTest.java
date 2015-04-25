package uk.co.optimisticpanda.gtest.dto.condition;

import uk.co.optimisticpanda.gtest.dto.condition.AlwaysCondition;
import uk.co.optimisticpanda.gtest.dto.condition.NotCondition;

import junit.framework.TestCase;
import static org.assertj.core.api.Assertions.assertThat;
/**
 * @author Andy Lee
 *
 */
public class NotConditionTest extends TestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    /**
     * 
     */
    public void testNotCondition(){
        NotCondition notCondition = new NotCondition(AlwaysCondition.ALWAYS);
        assertThat(notCondition.isValid(-1, null)).isFalse();
    }

}
