package uk.co.optimisticpanda.gtest.dto.condition;

/**
 * This condition wraps an {@link Condition} and returns the negation of its
 * {@link Condition#isValid(int, Object)} method.
 * 
 * @author Andy Lee
 * */
class NotCondition implements Condition {

	private final Condition condition;

	/**
	 * Create a condition that reverses the result of the passed in condition
	 * 
	 * @param condition
	 *            the condition to inverse
	 */
	NotCondition(Condition condition) {
		this.condition = condition;
	}

	/**
	 * @see uk.co.optimisticpanda.gtest.dto.condition.Condition#isValid(int,
	 *      java.lang.Object)
	 */
	@Override
	public <D> boolean isValid(int index, D dataItem) {
		return !condition.isValid(index, dataItem);
	}

	/**
	 * A human readable representation of this {@link Condition}.
	 */
	@Override
	public String toString() {
		return "NOT [" + condition + "]";
	}
}
