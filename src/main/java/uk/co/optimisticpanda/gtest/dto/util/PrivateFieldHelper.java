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
package uk.co.optimisticpanda.gtest.dto.util;

import java.lang.reflect.Field;

import uk.co.optimisticpanda.gtest.dto.propertyaccess.PropertyAccessException;

/**
 * @author Andy Lee
 * 
 */
public class PrivateFieldHelper {

	/**
	 * Helper class to aid with introspection. It stores fields in a cache to
	 * reduce reflection related performance hits.
	 * 
	 */
	private static FieldCache cache = new FieldCache();
	private final String fieldName;

	/**
	 * Creates a new privateFieldInvocator object.
	 * 
	 * @param fieldName
	 *            Name of private field
	 */
	public PrivateFieldHelper(String fieldName) {
		this.fieldName = fieldName;
	}

	/**
	 * Get the required private field value
	 * 
	 * @param <F>
	 * @param instance
	 *            Object that field is within
	 * @return Object returned from field call
	 */
	@SuppressWarnings("unchecked")
	public <F> F get(Object instance) {
		Field privateField = getField(instance.getClass());
		boolean accessible = privateField.isAccessible();
		privateField.setAccessible(true);

		F result;
		try {
			result = (F) privateField.get(instance);
		} catch (Exception e) {
			throw new PropertyAccessException(e);
		} finally {
			privateField.setAccessible(accessible);
		}

		return result;
	}

	/**
	 * Set the required private field value
	 * 
	 * @param instance
	 *            Object that field is within
	 * @param value
	 *            Object to set field to
	 */
	public void set(Object instance, Object value) {
		Field privateField = getField(instance.getClass());
		boolean accessible = privateField.isAccessible();

		try {
			privateField.setAccessible(true);
			privateField.set(instance, value);
		} catch (Exception e) {
			throw new PropertyAccessException(e);
		} finally {
			privateField.setAccessible(accessible);
		}
	}

	private Field getField(Class<?> clazz) {
		Field field = searchClassHierachy(clazz);
		if (field == null) {
			throw new PropertyAccessException("Could not find field name called:" + fieldName);
		}
		if (cache.get(clazz, fieldName) == null) {
			cache.put(clazz, fieldName, field);
		}
		return field;
	}

	private Field searchClassHierachy(Class<?> clazz) {
		Class<?> currentClazz = clazz;
		while (currentClazz != null) {
			Field field = getFromClassOrCache(clazz);
			if (field != null) {
				return field;
			}
			currentClazz = currentClazz.getSuperclass();
		}
		return null;

	}

	private Field getFromClassOrCache(Class<?> clazz) {
		try {
			Field field = cache.get(clazz, fieldName);
			return field != null ? field : clazz.getDeclaredField(fieldName);
		} catch (SecurityException e) {
			return null;
		} catch (NoSuchFieldException e) {
			return null;
		}
	}

}
