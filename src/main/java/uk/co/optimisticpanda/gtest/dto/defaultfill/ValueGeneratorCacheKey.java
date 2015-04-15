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
package uk.co.optimisticpanda.gtest.dto.defaultfill;

import java.lang.reflect.Field;

import uk.co.optimisticpanda.gtest.dto.edit.IEdit;

/**
 * A key used to retrieve an {@link IEdit} from the {@link ValueGeneratorCacheImpl}
 * 
 * @author Andy Lee
 * */
public class ValueGeneratorCacheKey {

	private final String propertyPath;
	private final Class<?> owningClass;
	private final String propertyName;
	private final Class<?> propertyType;

	/**
	 * Create an edit cache key. This is a dto that can be used to see which
	 * edit has is applicable for a property with this information.
	 * @param propertyPath The path of the current property (represented as an el). 
	 * @param field The field
	 */
	public ValueGeneratorCacheKey(String propertyPath, Field field) {
		this.propertyPath = propertyPath;
		this.owningClass = field.getDeclaringClass();
		this.propertyName = field.getName();
		this.propertyType = field.getType();
	}

	/**
	 * @return the path of the property (expressed as el)
	 * */
	public String getPropertyPath() {
		return propertyPath;
	}

	/**
	 * @return the class that owns this property
	 */
	public Class<?> getOwningClass() {
		return owningClass;
	}

	/**
	 * @return the name of the property
	 */
	public String getPropertyName() {
		return propertyName;
	}

	/**
	 * @return the type of the property
	 */
	public String getPropertyTypeName() {
		return propertyType.getName();
	}

	/**
	 * @return the type of the property
	 */
	public Class<?> getPropertyType() {
		return propertyType;
	}

}
