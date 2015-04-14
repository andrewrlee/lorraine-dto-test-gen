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

import uk.co.optimisticpanda.gtest.dto.defaultfill.ValueGeneratorCache;
import uk.co.optimisticpanda.gtest.dto.defaultfill.defaultgens.DefaultValueGeneratorCache;
import uk.co.optimisticpanda.gtest.dto.defaultfill.insgen.excl.ExclusionHolder;

/**
 *A builder which is used to create instance generators. By default it uses the
 * {@link DefaultValueGeneratorCache} but this can be altered. It uses fluent
 * builder pattern.
 * 
 * @author Andy Lee
 * @param <D> the type of instance to build
 */
public class InstanceGeneratorBuilder<D> {

	private ValueGeneratorCache generatorCache;
	private ExclusionHolder exclusions;
	private Class<D> rootClassToGenerate;

	/**
	 * Create a new instance generator builder
	 * 
	 * @param rootClassToGenerate
	 *            the class to generate an instance of.
	 */
	public InstanceGeneratorBuilder(Class<D> rootClassToGenerate) {
		this.rootClassToGenerate = rootClassToGenerate;
		this.generatorCache = new DefaultValueGeneratorCache();
		this.exclusions = new ExclusionHolder();
	}

	/**
	 * Create a new instance generator builder
	 * 
	 * @param rootClassToGenerate
	 *            the class to generate an instance of.
	 * @param valueGenCache a collection of value generators.
	 */
	public InstanceGeneratorBuilder(Class<D> rootClassToGenerate, ValueGeneratorCache valueGenCache) {
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
	 *            {@link DefaultValueGeneratorCache}
	 * @return this for chaining.
	 */
	public InstanceGeneratorBuilder<D> useCache(ValueGeneratorCache cache) {
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

	/**
	 * @return a newly created instance generator
	 */
	public InstanceGenerator<D> build() {
		return new InstanceGeneratorImpl<D>(rootClassToGenerate, generatorCache, exclusions);
	}

	/**
	 * @param <E> 
	 * @param clazz 
	 * @return a newly created instance generator
	 */
	public <E> InstanceGeneratorBuilder<E> getBuilderForClass(Class<E> clazz) {
		return new InstanceGeneratorBuilder<E>(clazz, this);
	}
}
