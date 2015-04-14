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
import java.util.HashMap;
import java.util.Map;

/**
 * @author Andy Lee A reflection helper
 */
public class FieldCache {
	Map<FieldInfo, Field> cache;

	/**
	 * Create a cache.
	 */
	public FieldCache() {
		cache = new HashMap<FieldInfo, Field>();
	}

	/**
	 * Store a new field in the cache. The field is keyed upon the class and
	 * field name combined.
	 * 
	 * @param clazz
	 * @param fieldName
	 * @param field
	 */
	public void put(Class<?> clazz, String fieldName, Field field) {
		cache.put(new FieldInfo(clazz, fieldName), field);
	}

	/**
	 * Return the field based on a class and the name of the field.
	 * @param clazz
	 * @param fieldName
	 * @return field
	 */
	public Field get(Class<?> clazz, String fieldName) {
		return cache.get(new FieldInfo(clazz, fieldName));
	}

	/**
	 * Used as the key in the cache map.
	 * */
	private class FieldInfo {
		private final Class<?> classFieldIsOn;
		private final String fieldName;

		private FieldInfo(Class<?> clazz, String fieldName) {
			this.classFieldIsOn = clazz;
			this.fieldName = fieldName;
		}

		/**
		 * @see java.lang.Object#hashCode()
		 */
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((classFieldIsOn == null) ? 0 : classFieldIsOn.hashCode());
			result = prime * result + ((fieldName == null) ? 0 : fieldName.hashCode());
			return result;
		}

		/**
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			FieldInfo other = (FieldInfo) obj;
			if (classFieldIsOn == null) {
				if (other.classFieldIsOn != null)
					return false;
			} else if (!classFieldIsOn.equals(other.classFieldIsOn))
				return false;
			if (fieldName == null) {
				if (other.fieldName != null)
					return false;
			} else if (!fieldName.equals(other.fieldName))
				return false;
			return true;
		}
	}
}
