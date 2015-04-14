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

import junit.framework.TestCase;
import uk.co.optimisticpanda.gtest.dto.defaultfill.defaultgens.DefaultValueGeneratorCache;
import uk.co.optimisticpanda.gtest.dto.test.utils.DetailedTestDtoComposite;
import uk.co.optimisticpanda.gtest.dto.test.utils.TestDto1;
import uk.co.optimisticpanda.gtest.dto.test.utils.TestDto2;

/**
 * @author Andy Lee
 * 
 */
public class ValueGeneratorCacheTest extends TestCase {

	private ValueGeneratorCache valueGeneratorCache;
	private IValueGenerator<Object> generator1;
	private IValueGenerator<Object> generator2;
	private IValueGenerator<Object> generator3;
	private IValueGenerator<Object> generator4;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		valueGeneratorCache = new DefaultValueGeneratorCache();

		generator1 = createValueGenerator("1");
		generator2 = createValueGenerator("2");
		generator3 = createValueGenerator("3");
		generator4 = createValueGenerator("4");

		valueGeneratorCache.registerAPropertyDepthGenerator("complexPath", generator1);
		valueGeneratorCache.registerAClassNamePropertyNameGenerator(DetailedTestDtoComposite.class, "name", generator2);
		valueGeneratorCache.registerAPropertyNameAndTypeGenerator("name", String.class, generator3);
		valueGeneratorCache.registerATypeGenerator(String.class, generator4);

	}

	/**
	 * test registering a value generator for class/propertyname/propertytype:
	 * on type to type
	 * 
	 * @throws Exception
	 */
	public void testFindValueGeneratorStrategies() throws Exception {
		// Based on a path
		ValueGeneratorCacheKey key = createKey("complexPath", DetailedTestDtoComposite.class, "name");
		IValueGenerator<?> generator = valueGeneratorCache.lookUpGenerator(key);
		assertEquals(generator1.generate(), generator.generate());

		// Based on owning class and property name
		key = createKey("", DetailedTestDtoComposite.class, "name");
		generator = valueGeneratorCache.lookUpGenerator(key);
		assertEquals(generator2.generate(), generator.generate());

		// Based on property name and property type
		key = createKey("", TestDto1.class, "name");
		generator = valueGeneratorCache.lookUpGenerator(key);
		assertEquals(generator3.generate(), generator.generate());

		// Based on property name and property type
		key = createKey("", TestDto2.class, "description");
		generator = valueGeneratorCache.lookUpGenerator(key);
		assertEquals(generator4.generate(), generator.generate());

		// Not Registered
		Field field = findAField(DetailedTestDtoComposite.class, "parent");
		assertEquals(IValueGenerator.NOT_COVERED, valueGeneratorCache.lookUpGenerator(new ValueGeneratorCacheKey("", field)));
	}

	/**
	 * @throws Exception
	 */
	public void testClearCache() throws Exception {
		valueGeneratorCache.clear();
		ValueGeneratorCacheImpl cache = (ValueGeneratorCacheImpl)valueGeneratorCache;
		assertTrue(cache.classAndPropertyNameAndTypeCache.isEmpty());
		assertTrue(cache.propertyDepthCache.isEmpty());
		assertTrue(cache.propertyNameAndTypeCache.isEmpty());
		assertTrue(cache.typeCache.isEmpty());
	}

	/**
	 * 
	 */
	public void testNotCoveredValueGenerator() {
		IValueGenerator<?> notCoveredGenerator = IValueGenerator.NOT_COVERED;
		try {
			notCoveredGenerator.generate();
			fail("should throw an exception!");
		} catch (UnsupportedOperationException e) {
			assertEquals("This generator does not generate values", e.getMessage());
		}
	}

	private ValueGeneratorCacheKey createKey(String path, Class<?> clazz, String propertyName) {
		return new ValueGeneratorCacheKey(path, findAField(clazz, propertyName));
	}

	private Field findAField(Class<?> clazz, String propertyName) {
		try {
			return clazz.getDeclaredField(propertyName);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * test registering a value generator for class/propertyname/propertytype:
	 * on primitive to type
	 * 
	 * @throws Exception
	 */
	private IValueGenerator<Object> createValueGenerator(final Object valueToSet) {
		return new IValueGenerator<Object>() {

			@Override
			public Object generate() {
				return valueToSet;
			}
		};
	}

}
