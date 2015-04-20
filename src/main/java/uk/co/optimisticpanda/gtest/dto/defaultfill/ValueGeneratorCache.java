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
import java.util.function.Supplier;

/**
 *An interface for caches that hold value generators.
 * 
 * @author Andy Lee
 */
public interface ValueGeneratorCache {

	/**
	 * Register a generator for properties based on an ognl or el type path from the
	 * root dto.
	 * 
	 * @param propertyDepth
	 *            an el type expression
	 * @param valueGen
	 *            the value generator to register with this path
	 * */
	public void registerAPropertyDepthGenerator(String propertyDepth, Supplier<?> valueGen);

	/**
	 * Register a generator for all named properties with the specified parent class
	 * and of the specified property type.
	 * 
	 * @param owningClass
	 *            The class that owns the property that the edit is being
	 *            registered against.
	 * @param propertyName
	 *            The name of the property that the edit is being registered
	 *            against
	 * @param valueGenerator
	 *            the value generator being registered
	 * */
	public void registerAClassNamePropertyNameGenerator(Class<?> owningClass, String propertyName, Supplier<?> valueGenerator);

	/**
	 * Register a generator for all properties of a specific name and type.
	 * 
	 * @param propertyName
	 *            the name of the property the edit is being registered against
	 * @param propertyType
	 *            the type of the property
	 * @param valueGenerator
	 *            the value generator to register
	 * */
	public void registerAPropertyNameAndTypeGenerator(String propertyName, Class<?> propertyType, Supplier<?> valueGenerator);

	/**
	 * Register a value generator for all properties of a specific type.
	 * @param mode 
	 *            Whether parents of this class should be registered against
	 *            this generator
	 * @param propertyType
	 *            The type of the property to register an edit against
	 * @param valueGenerator
	 *            the value generator to register
	 * */
	public void registerATypeGenerator(RegisterTypeMode mode, Class<?> propertyType, Supplier<?> valueGenerator);

	/**
	 * Register a value generator for all properties of a specific type.
	 * @param propertyType
	 *            The type of the property to register an edit against
	 * @param valueGenerator
	 *            the value generator to register
	 * */
	public void registerATypeGenerator(Class<?> propertyType, Supplier<?> valueGenerator);

	/**
	 * Return the value generator applicable for this key. See class javadoc for
	 * the strategy used to retrieve the value generator.
	 * 
	 * @param key
	 *            the key to look up a generator for
	 * @return a valueGenerator
	 * 
	 * */
	public Supplier<?> lookUpGenerator(String propertyPath, Field field);

	/**
	 * Clears the cache of all registered property values
	 */
	public void clear();
}
