package uk.co.optimisticpanda.gtest.dto.condition;

/**
 * A Condition that returns true on if the dto is at a specified index
 * 
 * @author Andy Lee
 */
public class IndexCondition implements ICondition {
	private final int indexToMatchOn;

	/**
	 * Create a condition that will return true on a specific index
	 * 
	 * @param indexToMatchOn
	 */
	IndexCondition(int indexToMatchOn) {
		this.indexToMatchOn = indexToMatchOn;
	}

	/**
	 * @see uk.co.optimisticpanda.gtest.dto.condition.ICondition#isValid(int,
	 *      java.lang.Object)
	 */
	public boolean isValid(int index, Object dataItem) {
		return index == indexToMatchOn;
	}

	/**
	 * A human readable representation of this {@link ICondition}.
	 */
	@Override
	public String toString() {
		return "INDEX [" + indexToMatchOn + "]";
	}
	
}
