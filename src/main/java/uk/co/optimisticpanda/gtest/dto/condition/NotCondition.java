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

/**
 * This condition wraps an {@link ICondition} and returns the negation of its
 * {@link ICondition#isValid(int, Object)} method.
 * 
 * @author Andy Lee
 * */
public class NotCondition implements ICondition {

	private final ICondition condition;

	/**
	 * Create a condition that reverses the result of the passed in condition
	 * 
	 * @param condition
	 *            the condition to inverse
	 */
	public NotCondition(ICondition condition) {
		this.condition = condition;
	}

	/**
	 * @see uk.co.optimisticpanda.gtest.dto.condition.ICondition#isValid(int,
	 *      java.lang.Object)
	 */
	@Override
	public <D> boolean isValid(int index, D dataItem) {
		return !condition.isValid(index, dataItem);
	}

	/**
	 * A human readable representation of this {@link ICondition}.
	 */
	@Override
	public String toString() {
		return "NOT [" + condition + "]";
	}
}
