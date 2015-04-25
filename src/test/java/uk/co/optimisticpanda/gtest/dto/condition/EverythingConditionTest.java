package uk.co.optimisticpanda.gtest.dto.condition;

import uk.co.optimisticpanda.gtest.dto.condition.AlwaysCondition;

import junit.framework.TestCase;
import static org.assertj.core.api.Assertions.assertThat;
/**
 * @author Andy Lee
 *
 */
public class EverythingConditionTest extends TestCase{

    private AlwaysCondition instance;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        instance = AlwaysCondition.ALWAYS;
    }
    
    /**
     * @throws Exception
     */
    @SuppressWarnings({ "boxing", "null" })
	public void testNullIndex() throws Exception {
        Integer nullInt = null;
        try{
            instance.isValid(nullInt, null);
            fail("Should get a NPE");
        }catch(NullPointerException e){
            //do nothing
        }
        assertThat(instance.isValid(1, null)).isTrue();
    }
    
}
