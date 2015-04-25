package uk.co.optimisticpanda.gtest.dto.condition;

import java.util.Date;

import uk.co.optimisticpanda.gtest.dto.TestUtilsContext;
import uk.co.optimisticpanda.gtest.dto.condition.ValueEqualsCondition;
import uk.co.optimisticpanda.gtest.dto.test.utils.DetailedTestDto;
import junit.framework.TestCase;
import static org.assertj.core.api.Assertions.assertThat;
import static uk.co.optimisticpanda.gtest.dto.condition.Conditions.valueOf;

public class ValueEqualsConditionTest extends TestCase {

    private static final Date DATE_1 = new Date(1234567L);
    private static final Date DATE_2 = new Date(7654321L);
    private final DetailedTestDto testDto1 = new DetailedTestDto("1", DATE_1, 4);
    private final DetailedTestDto testDto2 = new DetailedTestDto("2", DATE_2, 5);
    private final DetailedTestDto testDto3 = new DetailedTestDto(null, null, 4);

    {
    	TestUtilsContext.useOgnl();
    }
    
    public void testNullDto() throws Exception {
        ValueEqualsCondition valueEqualsCondition = valueOf("name").is("1");
        Object nullDto = null;
        try {
            valueEqualsCondition.isValid(1, nullDto);
            fail("Beanwrapper should throw illegal argument exception");
        } catch (RuntimeException e) {
            // do nothing
        }
    }
    
    public void testPublicFieldWithGetterEquals(){
        ValueEqualsCondition valueEqualsCondition = valueOf("name").is("1");
        assertThat(valueEqualsCondition.isValid(0, testDto1)).isTrue();
        assertThat(valueEqualsCondition.isValid(0, testDto2)).isFalse();
        assertThat(valueEqualsCondition.isValid(0, testDto3)).isFalse();
        
        ValueEqualsCondition nullValueEqualsCondition = valueOf("name").is(null);
        assertThat(nullValueEqualsCondition.isValid(0, testDto1)).isFalse();
        assertThat(nullValueEqualsCondition.isValid(0, testDto2)).isFalse();
        assertThat(nullValueEqualsCondition.isValid(0, testDto3)).isTrue();
    }
    
    public void testPublicFieldWithoutGetterEquals(){
        ValueEqualsCondition valueEqualsCondition = valueOf("date").is(DATE_1);
        assertThat(valueEqualsCondition.isValid(0, testDto1)).isTrue();
        assertThat(valueEqualsCondition.isValid(0, testDto2)).isFalse();
        assertThat(valueEqualsCondition.isValid(0, testDto3)).isFalse();

        ValueEqualsCondition valueEqualsCondition1 = valueOf("date").is(DATE_2);
        assertThat(valueEqualsCondition1.isValid(0, testDto1)).isFalse();
        assertThat(valueEqualsCondition1.isValid(0, testDto2)).isTrue();
        assertThat(valueEqualsCondition1.isValid(0, testDto3)).isFalse();
        
        ValueEqualsCondition nullValueEqualsCondition = valueOf("date").is(null);
        assertThat(nullValueEqualsCondition.isValid(0, testDto1)).isFalse();
        assertThat(nullValueEqualsCondition.isValid(0, testDto2)).isFalse();
        assertThat(nullValueEqualsCondition.isValid(0, testDto3)).isTrue();
    }

    public void testPrivateFieldWithoutGetterEquals(){
        ValueEqualsCondition valueEqualsCondition = valueOf("number").is(4);
        assertThat(valueEqualsCondition.isValid(0, testDto1)).isTrue();
        assertThat(valueEqualsCondition.isValid(0, testDto2)).isFalse();
        
        ValueEqualsCondition nullValueEqualsCondition = valueOf("date").is(null);
        assertThat(nullValueEqualsCondition.isValid(0, testDto1)).isFalse();
        assertThat(nullValueEqualsCondition.isValid(0, testDto2)).isFalse();
    }
  

}
