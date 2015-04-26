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
 * A Condition that returns true on odd or even indexes
 * 
 * @author Andy Lee
 */
enum EvenOddCondition implements Condition {
	/**
	 * This matches on indexes that are even
	 */
	EVEN,
	/**
	 * This matches on indexes that are odd
	 */
	ODD;

	/**
	 * @see uk.co.optimisticpanda.gtest.dto.condition.Condition#isValid(int,
	 *      java.lang.Object)
	 */
	public boolean isValid(int index, Object dataItem) {
		switch (this) {
		case EVEN:
			return index % 2 == 0;
		case ODD:
			return index % 2 != 0;
		default:
			throw new IllegalStateException("Case not covered:" + this.name());
		}
	}

	/**
	 * A human readable representation of this {@link Condition}.
	 */
	@Override
	public String toString() {
		return this.name().toString();
	}
}
