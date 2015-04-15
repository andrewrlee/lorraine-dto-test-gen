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
package uk.co.optimisticpanda.gtest.dto.propertyaccess.rflc;

import uk.co.optimisticpanda.gtest.dto.propertyaccess.IPropertyAccess;
import uk.co.optimisticpanda.gtest.dto.propertyaccess.IPropertyAccessFactory;
import uk.co.optimisticpanda.gtest.dto.propertyaccess.PropertyAccessException;

/**
 * A simple {@link IPropertyAccessFactory} that provides basic property support
 * via the usage of reflection. see ReflectionPropertyAccess
 * 
 * @author Andy Lee
 */
public class ReflectionPropertyAccessFactory implements IPropertyAccessFactory {

	/**
	 * Creates a new {@link IPropertyAccess} based on the passed in property
	 * name.
	 * 
	 * @param context
	 *            the name of the property to provide support for.
	 * @see uk.co.optimisticpanda.gtest.dto.propertyaccess.IPropertyAccessFactory#createPropertyAccess(java.lang.Object)
	 */
	@Override
	public IPropertyAccess createPropertyAccess(Object context) {
		if (context instanceof String) {
			return new ReflectionPropertyAccess((String) context);
		}
		throw new PropertyAccessException(this.getClass() + " can only accept Strings.");
	}

}
