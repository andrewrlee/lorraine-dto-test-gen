package uk.co.optimisticpanda.gtest.dto.condition;

public class Conditions {

	
	public static IndexConditionBuilder index(){
		return new IndexConditionBuilder();
	}

	public static ICondition not(ICondition condition){
		return new NotCondition(condition);
	}
	
	public static ValueEqualsBuilder valueOf(Object context){
		return new ValueEqualsBuilder(context);
	}

	public static ICondition always(){
		return AlwaysCondition.ALWAYS;
	}
	
	public static class IndexConditionBuilder{
		public ICondition is(int indexToMatchOn){
			return new IndexCondition(indexToMatchOn);
		}
		public ICondition isOdd(){
			return EvenOddCondition.ODD;
		}
		public ICondition isEven(){
			return EvenOddCondition.EVEN;
		}
	}
	
	public static class ValueEqualsBuilder{
		private Object context;
		private ValueEqualsBuilder(Object context){
			this.context = context;
		}
		public ValueEqualsCondition is(Object value){
			return new ValueEqualsCondition(context, value);	
		}
	}
}
