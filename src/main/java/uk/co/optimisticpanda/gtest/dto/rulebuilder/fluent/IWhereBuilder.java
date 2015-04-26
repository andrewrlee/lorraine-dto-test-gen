package uk.co.optimisticpanda.gtest.dto.rulebuilder.fluent;

import uk.co.optimisticpanda.gtest.dto.condition.Condition;

/**
 * An interface used by the fluent builder. 
 * @param <D>
 *            Type of the object this rule is being created for.
 * @author Andy Lee
 */
public interface IWhereBuilder {
	
	/**
	 * @param condition base condition
	 * @return this to allow chaining
	 */
	IEndBuilder where(Condition condition);
}
