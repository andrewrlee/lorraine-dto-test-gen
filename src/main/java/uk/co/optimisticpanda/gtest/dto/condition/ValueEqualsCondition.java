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

import uk.co.optimisticpanda.gtest.dto.propertyaccess.IPropertyAccess;
import uk.co.optimisticpanda.gtest.dto.propertyaccess.IPropertyAccessFactory;
import uk.co.optimisticpanda.gtest.dto.propertyaccess.PropertyAccessSupport;

/**
 *A Condition that is true based on the value of a property on the passed in
 * dto.
 * 
 * @author Andy Lee
 */
class ValueEqualsCondition extends PropertyAccessSupport implements Condition {

	private final Object valueToBeMatched;

	/**
	 * Create a new condition that is valid based on the value of a specified
	 * property on the dto.
	 * <P>
	 * The specified property is accessed via an {@link IPropertyAccess} which
	 * is created by passing in the context object to the currently configured
	 * {@link IPropertyAccessFactory}.
	 * </p>
	 * @param context
	 *            An object used for specifying which property to match.
	 * 
	 *            <i>This context is passed to the
	 *            {@link IPropertyAccessFactory}.</i>
	 * @param valueToMatch
	 *            the value to match on the dto.
	 */
	ValueEqualsCondition(Object context, Object valueToMatch) {
		super(context);
		this.valueToBeMatched = valueToMatch;
	}

	/**
	 * @see uk.co.optimisticpanda.gtest.dto.condition.Condition#isValid(int,
	 *      java.lang.Object)
	 */
	public <D> boolean isValid(int index, D dataItem) {
		Object propertyValue = getValue(dataItem);
		if (valueToBeMatched == null) {
			return propertyValue == null;
		}
		return valueToBeMatched.equals(propertyValue);
	}

	/**
	 * A human readable representation of this {@link Condition}.
	 */
	@Override
	public String toString() {
		return "EQUALS ['" + getContext() + "' = '" + valueToBeMatched + "']";
	}
	
}
