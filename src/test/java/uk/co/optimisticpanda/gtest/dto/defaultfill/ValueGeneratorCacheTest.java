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

import static org.assertj.core.api.Assertions.assertThat;
import static uk.co.optimisticpanda.gtest.dto.util.FunctionUtils.NOT_COVERED;

import java.lang.reflect.Field;
import java.util.function.Supplier;

import junit.framework.TestCase;
import uk.co.optimisticpanda.gtest.dto.defaultfill.defaultgens.DefaultValueGeneratorCache;
import uk.co.optimisticpanda.gtest.dto.test.utils.DetailedTestDtoComposite;
import uk.co.optimisticpanda.gtest.dto.test.utils.TestDto1;
import uk.co.optimisticpanda.gtest.dto.test.utils.TestDto2;
import uk.co.optimisticpanda.gtest.dto.util.Suppliers;

/**
 * @author Andy Lee
 * 
 */
public class ValueGeneratorCacheTest extends TestCase {

	private ValueGeneratorCache valueGeneratorCache;
	private Supplier<Object> generator1;
	private Supplier<Object> generator2;
	private Supplier<Object> generator3;
	private Supplier<Object> generator4;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		valueGeneratorCache = new DefaultValueGeneratorCache();

		generator1 = Suppliers.of("1");
		generator2 = Suppliers.of("2");
		generator3 = Suppliers.of("3");
		generator4 = Suppliers.of("4");

		valueGeneratorCache.registerAPropertyDepthGenerator("complexPath", () -> "1");
		valueGeneratorCache.registerAClassNamePropertyNameGenerator(DetailedTestDtoComposite.class, "name", () -> "2");
		valueGeneratorCache.registerAPropertyNameAndTypeGenerator("name", String.class, () -> "3");
		valueGeneratorCache.registerATypeGenerator(String.class, () -> "4");

	}

	/**
	 * test registering a value generator for class/propertyname/propertytype:
	 * on type to type
	 * 
	 * @throws Exception
	 */
	public void testFindValueGeneratorStrategies() throws Exception {
		// Based on a path
		Supplier<?> generator = valueGeneratorCache.lookUpGenerator("complexPath", field(DetailedTestDtoComposite.class, "name"));
		assertThat(generator.get()).isEqualTo(generator1.get());

		// Based on owning class and property name
		generator = valueGeneratorCache.lookUpGenerator("", field(DetailedTestDtoComposite.class, "name"));
		assertThat(generator.get()).isEqualTo(generator2.get());

		// Based on property name and property type
		generator = valueGeneratorCache.lookUpGenerator("", field(TestDto1.class, "name"));
		assertThat(generator.get()).isEqualTo(generator3.get());

		// Based on property name and property type
		generator = valueGeneratorCache.lookUpGenerator("", field(TestDto2.class, "description"));
		assertThat(generator.get()).isEqualTo(generator4.get());

		// Not Registered
		generator = valueGeneratorCache.lookUpGenerator("", field(DetailedTestDtoComposite.class, "parent"));
		assertThat(generator).isEqualTo(NOT_COVERED);
	}

	/**
	 * @throws Exception
	 */
	public void testClearCache() throws Exception {
		valueGeneratorCache.clear();
		ValueGeneratorCacheImpl cache = (ValueGeneratorCacheImpl) valueGeneratorCache;
		assertThat(cache.classAndPropertyNameAndTypeCache.isEmpty()).isTrue();
		assertThat(cache.propertyDepthCache.isEmpty()).isTrue();
		assertThat(cache.propertyNameAndTypeCache.isEmpty()).isTrue();
		assertThat(cache.typeCache.isEmpty()).isTrue();
	}

	/**
	 * 
	 */
	public void testNotCoveredValueGenerator() {
		Supplier<?> notCoveredGenerator = NOT_COVERED;
		try {
			notCoveredGenerator.get();
			fail("should throw an exception!");
		} catch (UnsupportedOperationException e) {
			assertThat(e.getMessage()).isEqualTo("This generator does not generate values");
		}
	}

	private Field field(Class<?> clazz, String propertyName) {
		try {
			return clazz.getDeclaredField(propertyName);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
