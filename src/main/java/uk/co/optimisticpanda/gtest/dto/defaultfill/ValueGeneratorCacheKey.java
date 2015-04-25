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

import uk.co.optimisticpanda.gtest.dto.edit.Editor;

/**
 * A key used to retrieve an {@link Editor} from the {@link ValueGenerator}
 * 
 * @author Andy Lee
 * */
class ValueGeneratorCacheKey {

	private final String propertyPath;
	private final Class<?> owningClass;
	private final String propertyName;
	private final Class<?> propertyType;

	ValueGeneratorCacheKey(String propertyPath, Field field) {
		this.propertyPath = propertyPath;
		this.owningClass = field.getDeclaringClass();
		this.propertyName = field.getName();
		this.propertyType = field.getType();
	}

	String getPropertyPath() {
		return propertyPath;
	}

	public Class<?> getOwningClass() {
		return owningClass;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public String getPropertyTypeName() {
		return propertyType.getName();
	}

	public Class<?> getPropertyType() {
		return propertyType;
	}
}
