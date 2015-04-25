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

import static uk.co.optimisticpanda.gtest.dto.util.FunctionUtils.NOT_COVERED;

import java.lang.reflect.Field;
import java.util.function.Supplier;

import uk.co.optimisticpanda.gtest.dto.TestUtilsContext;
import uk.co.optimisticpanda.gtest.dto.defaultfill.DefaultValueGenerator;
import uk.co.optimisticpanda.gtest.dto.defaultfill.ValueGenerator;
import uk.co.optimisticpanda.gtest.dto.defaultfill.insgen.excl.ExclusionHolder;
import uk.co.optimisticpanda.gtest.dto.propertyaccess.IPropertyAccess;
import uk.co.optimisticpanda.gtest.dto.propertyaccess.IPropertyAccessFactory;

/**
 *This creates populated instances of a specific class. Strategies are
 * registered for the generation of values of specific properties via
 * {@link Supplier}s. These are registered in {@link ValueGenerator}
 * </p> <em>NOTE:</em>
 * <ul>
 * <li/>An InstanceGenerator with an empty value generator cache will cycle
 * through the entire object tree and return an un-populated instance.
 * <li/>An InstanceGenerator with an value generator cache filled with just
 * {@link Supplier} that generate primitives and Strings will traverse
 * the entire object tree and should return a mostly populated instance.
 * <li/>It is possible to register strategies against user defined classes to
 * deal with larger parts of the tree.
 * <li/><em>Currently cycles in the object tree aren't dealt with and so will
 * lead to infinite looping!</em>
 * <ul/>
 * 
 * @see ValueGenerator
 * @param <D>
 *            the type of instance to create
 * @author Andy Lee
 */
public class InstanceGenerator<D> {

	private final Class<D> clazz;
	private final ValueGenerator generatorCache;
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
	private InstanceGenerator(Class<D> clazz, ValueGenerator valueGeneratorCache, ExclusionHolder exclusions) {
		this.clazz = clazz;
		this.generatorCache = valueGeneratorCache;
		this.exclusions = exclusions;
		this.propertyAccessFactory = TestUtilsContext.getPropertyAccessFactory();
	}

	public static <D> InstanceGeneratorBuilder<D> of(Class<D> rootClassToGenerate){
		return new InstanceGeneratorBuilder<D>(rootClassToGenerate, new DefaultValueGenerator());
	}
	
	public static <D> InstanceGenerator<D> create(Class<D> rootClassToGenerate, ValueGenerator valueGenCache){
		return new InstanceGeneratorBuilder<D>(rootClassToGenerate, valueGenCache).build();
	}
	
	public static <D> InstanceGenerator<D> create(Class<D> rootClassToGenerate){
		return new InstanceGeneratorBuilder<D>(rootClassToGenerate, new DefaultValueGenerator()).build();
	}
	
	/**
	 * @return a new instance of an object of a specific class.
	 */
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
		Supplier<?> generator = generatorCache.lookUpGenerator(propertyPath, field);
		if (generator == NOT_COVERED) {
			return processClass(propertyPath, field.getType());
		}
		return generator.get();
	}

	private <E> E createInstance(String path, Class<E> classToCreate) {
		try {
			return classToCreate.getConstructor().newInstance();
		} catch (Exception e) {
			throw new InstanceGeneratorException(path, classToCreate, e);
		}
	}

	private String addPathElement(String currentPath, Field field) {
		return currentPath.length() == 0 ? field.getName() : currentPath + "." + field.getName();
	}

	private void setValue(Field field, Object instance, Object propertyValue) {
		try {
			IPropertyAccess propertyAccess = propertyAccessFactory.createPropertyAccess(field.getName());
			propertyAccess.setValue(instance, propertyValue);
		} catch (IllegalArgumentException e) {
			throw new InstanceGeneratorException(e);
		}
	}
	
	public static class InstanceGeneratorBuilder<D> {

		private ValueGenerator generatorCache;
		private ExclusionHolder exclusions;
		private Class<D> rootClassToGenerate;

		private InstanceGeneratorBuilder(Class<D> rootClassToGenerate, ValueGenerator valueGenCache) {
			this.rootClassToGenerate = rootClassToGenerate;
			this.generatorCache = valueGenCache;
			this.exclusions = new ExclusionHolder();
		}
		
		/**
		 * Create a child builder that has the same configuration but can be used
		 * for creating generators that create different instances.
		 * @param <E> the new type of builder to create
		 * 
		 * @param clazz
		 * @param builder
		 */
		public <E> InstanceGeneratorBuilder(Class<D> clazz, InstanceGeneratorBuilder<E> builder) {
			this.rootClassToGenerate = clazz;
			this.generatorCache = builder.generatorCache;
			this.exclusions = builder.exclusions;
		}

		/**
		 * @param cache
		 *            use this cache instead of the
		 *            {@link DefaultValueGenerator}
		 * @return this for chaining.
		 */
		public InstanceGeneratorBuilder<D> useCache(ValueGenerator cache) {
			this.generatorCache = cache;
			return this;
		}

		/**
		 * @param pathToIgnore
		 *            ignore these paths and leave them as null. This can be used to
		 *            get round looping object graphs.
		 * @return this for chaining.
		 */
		public InstanceGeneratorBuilder<D> addELPathToIgnore(String pathToIgnore) {
			this.exclusions.addExclusion(pathToIgnore);
			return this;
		}

		public InstanceGenerator<D> build() {
			return new InstanceGenerator<D>(rootClassToGenerate, generatorCache, exclusions);
		}

		public <E> InstanceGeneratorBuilder<E> getBuilderForClass(Class<E> clazz) {
			return new InstanceGeneratorBuilder<E>(clazz, this);
		}
	}
}
