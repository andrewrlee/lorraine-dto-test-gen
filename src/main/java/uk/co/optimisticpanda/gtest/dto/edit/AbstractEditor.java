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

import uk.co.optimisticpanda.gtest.dto.propertyaccess.IPropertyAccessFactory;
import uk.co.optimisticpanda.gtest.dto.propertyaccess.PropertyAccessSupport;

/**
 * This is a base class that {@link Editor}s can extend to provide property
 * access support.
 * 
 * @param <D>
 *            The type of object that will be edited.
 * @author Andy Lee
 */
public abstract class AbstractEditor<D> extends PropertyAccessSupport implements Editor<D> {

	/**
	 * @param context
	 *            The context to be passed to the
	 *            {@link IPropertyAccessFactory#createPropertyAccess(Object)}
	 */
	public AbstractEditor(Object context) {
		super(context);
	}

}
