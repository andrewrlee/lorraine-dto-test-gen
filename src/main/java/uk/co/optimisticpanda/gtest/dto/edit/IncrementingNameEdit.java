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
 * An Edit which ensures that each dto in a collection has a different string
 * value. It does this by simply appending the passed in index to a base name.
 * 
 * @author Andy Lee
 * @param <D>
 *            The type of the dto that will be edited
 */
public class IncrementingNameEdit<D> extends AbstractEdit<D> {

	private final String baseNameValue;

	/**
	 * Create an {@link IEdit} that will append the current index to a basename
	 * and set a specified property on the dto.
	 * 
	 * The specified property is accessed via an {@link IPropertyAccess} which
	 * is created by passing in the context object to the currently configured
	 * {@link IPropertyAccessFactory}.
	 * 
	 * @param context
	 *            An object used for specifying which property to edit. 
	 *            <p><i>This
	 *            context is passed to the {@link IPropertyAccessFactory}
	 *            </i></p>
	 * @param baseNameValue
	 *            the value that will be used as the base text.
	 */
	public IncrementingNameEdit(Object context, String baseNameValue) {
		super(context);
		this.baseNameValue = baseNameValue;
	}

	/**
	 *  see {@link IEdit#edit(int, Object)}
	 */
	public void edit(int index, Object dataItem) {
		setValue(dataItem, baseNameValue + index);
	}

	/**
	 * @return a human readable representation of the changes that are to take
	 *         place.
	 */
	@Override
	public String toString() {
		return "INCREMENT [" + getContext() + " to '" + baseNameValue + "'+i]";
	}
}
