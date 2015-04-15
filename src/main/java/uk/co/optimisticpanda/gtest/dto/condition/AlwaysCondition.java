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
 * A Condition that returns true in all cases.
 * 
 * @author Andy Lee
 */
public enum AlwaysCondition implements ICondition {

	/**
	 * The singleton instance
	 */
	INSTANCE;

	/**
	 * @see uk.co.optimisticpanda.gtest.dto.condition.ICondition#isValid(int,
	 *      java.lang.Object)
	 */
	public boolean isValid(int index, Object dataItem) {
		return true;
	}

	/**
	 * A human readable representation of this {@link ICondition}.
	 */
	@Override
	public String toString() {
		return "ALWAYS";
	}
}
