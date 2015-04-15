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
	public IndexCondition(int indexToMatchOn) {
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
