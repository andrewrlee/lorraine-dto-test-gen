package uk.co.optimisticpanda.gtest.dto.condition;

public class Conditions {
	
	public static IndexConditionBuilder index(){
		return new IndexConditionBuilder();
	}

	public static Condition not(Condition condition){
		return new NotCondition(condition);
	}
	
	public static ValueEqualsBuilder valueOf(Object context){
		return new ValueEqualsBuilder(context);
	}

	public static Condition always(){
		return AlwaysCondition.ALWAYS;
	}
	
	public static Condition everyNth(int n) {
		return new NthPlaceCondition(n);
	}
	
	public static class IndexConditionBuilder{
		public Condition is(int indexToMatchOn){
			return new IndexCondition(indexToMatchOn);
		}
		public Condition isOdd(){
			return EvenOddCondition.ODD;
		}
		public Condition isEven(){
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
