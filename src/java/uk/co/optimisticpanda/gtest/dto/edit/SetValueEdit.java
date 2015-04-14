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
package uk.co.optimisticpanda.gtest.dto.edit;

import uk.co.optimisticpanda.gtest.dto.propertyaccess.IPropertyAccess;
import uk.co.optimisticpanda.gtest.dto.propertyaccess.IPropertyAccessFactory;

/**
 * An Edit which sets a property on a dto to a specific value
 * 
 * @param <D>
 *            The type of the dto to edit
 */
public class SetValueEdit<D> extends AbstractEdit<D> {

	private final Object value;

	/**
	 * Create a new IEdit for setting a specified property to a value.
	 * 
	 * The specified property is accessed via an {@link IPropertyAccess} which
	 * is created by passing in the context object to the currently configured
	 * {@link IPropertyAccessFactory}.
	 * 
	 * @param context
	 *            An object used for specifying which property to edit.
	 *            <p>
	 *            <i>This context is passed to the
	 *            {@link IPropertyAccessFactory} </i>
	 *            </p>
	 * @param value
	 *            the value to set the specified property to.
	 */
	public SetValueEdit(Object context, Object value) {
		super(context);
		this.value = value;
	}

	/**
	 * @see uk.co.optimisticpanda.gtest.dto.edit.IEdit#edit(int,
	 *      java.lang.Object)
	 */
	public void edit(int index, Object dataItem) {
		setValue(dataItem, value);
	}

	/**
	 * @return a human readable representation of the changes that are to take
	 *         place.
	 */
	@Override
	public String toString() {
		return "SET ['" + getContext() + "' to '" + value + "']";
	}
}
