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
package uk.co.optimisticpanda.gtest.dto.defaultfill.insgen;

import java.lang.reflect.Field;

import uk.co.optimisticpanda.gtest.dto.TestUtilsContext;
import uk.co.optimisticpanda.gtest.dto.defaultfill.IValueGenerator;
import uk.co.optimisticpanda.gtest.dto.defaultfill.ValueGeneratorCache;
import uk.co.optimisticpanda.gtest.dto.defaultfill.RegisterTypeMode;
import uk.co.optimisticpanda.gtest.dto.defaultfill.ValueGeneratorCacheImpl;
import uk.co.optimisticpanda.gtest.dto.defaultfill.ValueGeneratorCacheKey;
import uk.co.optimisticpanda.gtest.dto.defaultfill.insgen.excl.ExclusionHolder;
import uk.co.optimisticpanda.gtest.dto.propertyaccess.IPropertyAccess;
import uk.co.optimisticpanda.gtest.dto.propertyaccess.IPropertyAccessFactory;

/**
 *This creates populated instances of a specific class. Strategies are
 * registered for the generation of values of specific properties via
 * {@link IValueGenerator}s. These are registered in {@link ValueGeneratorCacheImpl}
 * </p> <em>NOTE:</em>
 * <ul>
 * <li/>An InstanceGenerator with an empty value generator cache will cycle
 * through the entire object tree and return an un-populated instance.
 * <li/>An InstanceGenerator with an value generator cache filled with just
 * {@link IValueGenerator} that generate primitives and Strings will traverse
 * the entire object tree and should return a mostly populated instance.
 * <li/>It is possible to register strategies against user defined classes to
 * deal with larger parts of the tree.
 * <li/><em>Currently cycles in the object tree aren't dealt with and so will
 * lead to infinite looping!</em>
 * <ul/>
 * 
 * @see ValueGeneratorCacheImpl
 * @param <D>
 *            the type of instance to create
 * @author Andy Lee
 */
class InstanceGeneratorImpl<D> implements InstanceGenerator<D> {

	private final Class<D> clazz;
	private final ValueGeneratorCache generatorCache;
	private IPropertyAccessFactory propertyAccessFactory;
	private final ExclusionHolder exclusions;

	/**
	 * Creates a new instance generator.
	 * 
	 * @param clazz
	 *            the type of class this generator will create instances of.
	 * @param valueGeneratorCache
	 *            This consists of strategies that should be used for the
	 *            generation of values of specific properties.
	 * @param exclusions
	 */
	public InstanceGeneratorImpl(Class<D> clazz, ValueGeneratorCache valueGeneratorCache, ExclusionHolder exclusions) {
		this.clazz = clazz;
		this.generatorCache = valueGeneratorCache;
		this.exclusions = exclusions;
		this.propertyAccessFactory = TestUtilsContext.getPropertyAccessFactory();
	}

	/**
	 * @return a new instance of an object of a specific class.
	 */
	@Override
	public D generate() {
		return processClass("", clazz);
	}

	private <E> E processClass(String path, Class<E> currentClassToProcess) {
		E instance = createInstance(path, currentClassToProcess);
		Field[] declaredFields = currentClassToProcess.getDeclaredFields();
		for (Field field : declaredFields) {
			String fieldPath = addPathElement(path, field);
			if (exclusions.isNotExcluded(fieldPath)) {
				Object propertyValue = processField(field, fieldPath);
				setValue(field, instance, propertyValue);
			}
		}
		return instance;
	}

	private Object processField(Field field, String propertyPath) {
		ValueGeneratorCacheKey key = new ValueGeneratorCacheKey(propertyPath, field);
		IValueGenerator<?> generator = generatorCache.lookUpGenerator(key);
		if (generator == IValueGenerator.NOT_COVERED) {
			return processClass(propertyPath, field.getType());
		}
		return generator.generate();
	}

	private <E> E createInstance(String path, Class<E> classToCreate) {
		try {
			return classToCreate.getConstructor().newInstance();
		} catch (Exception e) {
			throw new InstanceGeneratorException(path, classToCreate, e);
		}
	}

	private String addPathElement(String currentPath, Field field) {
		if (currentPath.length() == 0) {
			return field.getName();
		}
		return currentPath + "." + field.getName();
	}

	private void setValue(Field field, Object instance, Object propertyValue) {
		try {
			IPropertyAccess propertyAccess = propertyAccessFactory.createPropertyAccess(field.getName());
			propertyAccess.setValue(instance, propertyValue);
		} catch (IllegalArgumentException e) {
			throw new InstanceGeneratorException(e);
		}
	}

	@Override
	public void clear() {
		generatorCache.clear();
	}

	@Override
	public IValueGenerator<?> lookUpGenerator(ValueGeneratorCacheKey key) {
		return generatorCache.lookUpGenerator(key);
	}

	@Override
	public void registerAClassNamePropertyNameGenerator(Class<?> owningClass, String propertyName, IValueGenerator<?> valueGenerator) {
		generatorCache.registerAClassNamePropertyNameGenerator(owningClass, propertyName, valueGenerator);
	}

	@Override
	public void registerAPropertyDepthGenerator(String propertyDepth, IValueGenerator<?> valueGen) {
		generatorCache.registerAPropertyDepthGenerator(propertyDepth, valueGen);
	}

	@Override
	public void registerAPropertyNameAndTypeGenerator(String propertyName, Class<?> propertyType, IValueGenerator<?> valueGenerator) {
		generatorCache.registerAPropertyNameAndTypeGenerator(propertyName, propertyType, valueGenerator);
	}

	@Override
	public void registerATypeGenerator(RegisterTypeMode mode, Class<?> propertyType, IValueGenerator<?> valueGenerator) {
		generatorCache.registerATypeGenerator(mode, propertyType, valueGenerator);
	}

	@Override
	public void registerATypeGenerator(Class<?> propertyType, IValueGenerator<?> valueGenerator) {
		generatorCache.registerATypeGenerator(propertyType, valueGenerator);
	}

}
