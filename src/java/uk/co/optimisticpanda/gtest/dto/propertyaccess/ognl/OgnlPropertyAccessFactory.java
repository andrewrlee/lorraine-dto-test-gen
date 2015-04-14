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
package uk.co.optimisticpanda.gtest.dto.propertyaccess.ognl;

import uk.co.optimisticpanda.gtest.dto.propertyaccess.IPropertyAccess;
import uk.co.optimisticpanda.gtest.dto.propertyaccess.IPropertyAccessFactory;
import uk.co.optimisticpanda.gtest.dto.propertyaccess.PropertyAccessException;

/**
 * A {@link IPropertyAccessFactory} that provides property support via the usage
 * of ognl.
 * 
 * @author Andy Lee
 */
public class OgnlPropertyAccessFactory implements IPropertyAccessFactory {

	/**
	 * Creates a new {@link IPropertyAccess} based on a passed in ognl
	 * expression.
	 * 
	 * @param context
	 *            the ognl expression that defines a property on the object
	 * @see uk.co.optimisticpanda.gtest.dto.propertyaccess.IPropertyAccessFactory#createPropertyAccess(java.lang.Object)
	 */
	@Override
	public IPropertyAccess createPropertyAccess(Object context) {
		if (context instanceof String) {
			return new OgnlPropertyAccess((String) context);
		}
		throw new PropertyAccessException(this.getClass() + " can only accept Strings.");
	}

}
