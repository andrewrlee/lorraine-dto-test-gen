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
import uk.co.optimisticpanda.gtest.dto.propertyaccess.PropertyAccessException;
import uk.co.optimisticpanda.gtest.dto.util.PrivateFieldHelper;


class ReflectionPropertyAccess implements IPropertyAccess{

	private PrivateFieldHelper privateFieldHelper;

	public ReflectionPropertyAccess(String propertyName) {
		privateFieldHelper = new PrivateFieldHelper(propertyName);
	}
	
	@Override
	public Object getValue(Object instance) throws PropertyAccessException {
		return privateFieldHelper.get(instance);
	}

	@Override
	public void setValue(Object instance, Object value)
			throws PropertyAccessException {
		privateFieldHelper.set(instance, value);
	}

}
