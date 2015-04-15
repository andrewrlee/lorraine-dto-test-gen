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

import uk.co.optimisticpanda.gtest.dto.TestUtilsContext;

/**
 * A class that provides access to a correctly configured
 * {@link IPropertyAccess}.
 * 
 * @author Andy Lee
 */
public abstract class PropertyAccessSupport {

	private final IPropertyAccess propertyAccess;
	private final Object context;

	/**
	 * @param context
	 *            The context that is passed to the currently configured
	 *            {@link IPropertyAccessFactory}
	 */
	public PropertyAccessSupport(Object context) {
		this.context = context;
		IPropertyAccessFactory propertyAccessFactory = TestUtilsContext.getPropertyAccessFactory();
		propertyAccess = propertyAccessFactory.createPropertyAccess(context);
	}

	/**
	 * Set the property on this instance to a value.
	 * 
	 * @param instance
	 *            the instance that should have this property set.
	 * @param value
	 *            the value that should be set.
	 * */
	protected void setValue(Object instance, Object value) {
		propertyAccess.setValue(instance, value);
	}

	/**
	 * Get the value of the represented property from this specific instance.
	 * 
	 * @param instance
	 *            the instance that the value will be received from.
	 * */
	protected Object getValue(Object instance) {
		return propertyAccess.getValue(instance);
	}

	/**
	 * @return the current context for the property that is accessed by this.
	 */
	public Object getContext() {
		return context;
	}
}
