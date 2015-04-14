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
package uk.co.optimisticpanda.gtest.dto.propertyaccess;

/**
 * These are used for introspection purposes.
 * <p>One {@link IPropertyAccess} is
 * created for each instance that extends {@link PropertyAccessSupport}.
 * </p>
 * @see IPropertyAccessFactory
 * @author Andy Lee
 */
public interface IPropertyAccess {

	/**
	 * Get the value of the property that this {@link IPropertyAccess}
	 * represents from a specific instance.
	 * 
	 * @param instance
	 *            to get the value from.
	 * @return value of the property this property value represents
	 * @throws PropertyAccessException
	 */
	public Object getValue(Object instance) throws PropertyAccessException;

	/**
	 * Set the property belonging to an instance to the specified value.
	 * 
	 * @param instance
	 *            to set the value on.
	 * @param value
	 *            the value to set
	 * @throws PropertyAccessException
	 */
	public void setValue(Object instance, Object value) throws PropertyAccessException;
}
