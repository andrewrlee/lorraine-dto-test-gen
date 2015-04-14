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
import uk.co.optimisticpanda.gtest.dto.TestUtilsContext;
import uk.co.optimisticpanda.gtest.dto.defaultfill.defaultgens.DefaultValueGeneratorCache;
import uk.co.optimisticpanda.gtest.dto.defaultfill.defaultgens.ValueGeneratorFactory;
import uk.co.optimisticpanda.gtest.dto.defaultfill.insgen.InstanceGenerator;
import uk.co.optimisticpanda.gtest.dto.defaultfill.insgen.InstanceGeneratorBuilder;
import uk.co.optimisticpanda.gtest.dto.defaultfill.insgen.InstanceGeneratorException;
import uk.co.optimisticpanda.gtest.dto.test.utils.DetailedTestDto;
import uk.co.optimisticpanda.gtest.dto.test.utils.DetailedTestDtoComposite;
import uk.co.optimisticpanda.gtest.dto.test.utils.TestDto2;
import uk.co.optimisticpanda.gtest.dto.test.utils.TestDtoWithoutDefaultConstructor;

/**
 * Test Instance Generator
 * 
 * @author Andy Lee
 */
public class InstanceGeneratorBuilderTest extends TestCase {
	private IValueGenerator<Integer> integerGen;
	private IValueGenerator<?> primitiveGen;
	private IValueGenerator<?> nullGen;
	private IValueGenerator<?> nameGen;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		TestUtilsContext.useOgnl();

		integerGen = ValueGeneratorFactory.createIntegerGenerator(new Integer(4));
		primitiveGen = createPrimitiveIntGen();
		nullGen = ValueGeneratorFactory.createNullGenerator();
		nameGen = ValueGeneratorFactory.createStringGenerator("editedName");
	}

	@SuppressWarnings("unchecked")
	private <D> InstanceGeneratorBuilder<D> getInstanceGeneratorBuilder(Class<D> clazz) {
		return new InstanceGeneratorBuilder(clazz);
	}

	/**
	 * Tests the creation of an instance without a default constructor
	 */
	public void testCreateEmptyInstanceWithOutDefaultConstructor() {
		InstanceGenerator<TestDtoWithoutDefaultConstructor> generator = getInstanceGeneratorBuilder(TestDtoWithoutDefaultConstructor.class)//
				.build();

		try {
			generator.generate();
			fail("Should throw an exception as no default constructor");
		} catch (InstanceGeneratorException e) {
			String expectedMessage = InstanceGeneratorException.createInstanceCreationMessage("", TestDtoWithoutDefaultConstructor.class, e
					.getCause());
			assertEquals(expectedMessage, e.getMessage());
		}
	}

	/**
	 * Tests the creation of an empty instance if no value generators are
	 * registered
	 */
	public void testCreateEmptyInstance() {
		InstanceGenerator<DetailedTestDto> generator = getInstanceGeneratorBuilder(DetailedTestDto.class).build();

		DetailedTestDto dto1 = generator.generate();
		assertNotNull("should create instances", dto1);
		DetailedTestDto dto2 = generator.generate();
		assertNotNull("should create instances", dto2);
		assertEquals("should both be the same", dto1, dto2);
	}

	/**
	 * Check registering a property editor for parent to prevent infinite
	 * looping
	 */
	public void checkSettingParentToNull() {
		InstanceGenerator<DetailedTestDtoComposite> generator = getInstanceGeneratorBuilder(DetailedTestDtoComposite.class).build();

		generator.registerAClassNamePropertyNameGenerator(DetailedTestDtoComposite.class, "parent", nullGen);

		Field parentField = findAField(DetailedTestDtoComposite.class, "parent");
		IValueGenerator<?> foundParentGenerator = generator.lookUpGenerator(new ValueGeneratorCacheKey("", parentField));
		assertNotNull(foundParentGenerator);
		assertNull(foundParentGenerator.generate());
		try {
			DetailedTestDtoComposite item = generator.generate();
			assertNotNull(item);
			assertNull(item.getParent());
		} catch (StackOverflowError e) {
			fail("shouldn't overflow as there is a registered generator for parent, and just generates an empty list for children");
		}
	}

	/**
	 * test registering a value generator for class/propertyname/propertytype:
	 * on type to type
	 * 
	 * @throws Exception
	 */
	public void testRegisterAClassNamePropertyNameEditTypeToType() throws Exception {
		InstanceGenerator<DetailedTestDtoComposite> generator = getInstanceGeneratorBuilder(DetailedTestDtoComposite.class).build();

		generator.registerAClassNamePropertyNameGenerator(DetailedTestDtoComposite.class, "number", integerGen);
		generator.registerAClassNamePropertyNameGenerator(DetailedTestDtoComposite.class, "parent", nullGen);

		Field numberField = findAField(DetailedTestDtoComposite.class, "number");
		IValueGenerator<?> foundIntegerGenerator = generator.lookUpGenerator(new ValueGeneratorCacheKey("", numberField));
		assertNotNull(foundIntegerGenerator);
		assertTrue(foundIntegerGenerator.generate() instanceof Integer);

		DetailedTestDtoComposite item = generator.generate();
		assertNotNull(item);
		assertEquals(new Integer(4), item.getNumber());
	}

	/**
	 * test registering a value generator for class/propertyname/propertytype:
	 * on primitive to type
	 * 
	 * @throws Exception
	 */
	public void testRegisterAClassNamePropertyNameEditPrimitiveToType() throws Exception {
		InstanceGenerator<DetailedTestDtoComposite> generator = getInstanceGeneratorBuilder(DetailedTestDtoComposite.class).build();

		generator.registerAClassNamePropertyNameGenerator(DetailedTestDtoComposite.class, "number", primitiveGen);
		generator.registerAClassNamePropertyNameGenerator(DetailedTestDtoComposite.class, "parent", nullGen);

		Field numberField = findAField(DetailedTestDtoComposite.class, "number");
		IValueGenerator<?> foundIntegerGenerator = generator.lookUpGenerator(new ValueGeneratorCacheKey("", numberField));
		assertNotNull(foundIntegerGenerator);
		assertEquals(new Integer(4), foundIntegerGenerator.generate());

		DetailedTestDtoComposite item = generator.generate();
		assertNotNull(item);
		assertEquals(new Integer(4), item.getNumber());
	}

	/**
	 * test registering a value generator for class/propertyname/propertytype:
	 * on primitive to primitive
	 * 
	 * @throws Exception
	 */
	public void testRegisterAClassNamePropertyNameEditPrimitiveToPrimitive() throws Exception {
		InstanceGenerator<DetailedTestDtoComposite> generator = getInstanceGeneratorBuilder(DetailedTestDtoComposite.class).build();

		generator.registerAClassNamePropertyNameGenerator(DetailedTestDtoComposite.class, "primitiveNumber", primitiveGen);
		generator.registerAClassNamePropertyNameGenerator(DetailedTestDtoComposite.class, "parent", nullGen);

		Field numberField = findAField(DetailedTestDtoComposite.class, "primitiveNumber");
		IValueGenerator<?> foundIntegerGenerator = generator.lookUpGenerator(new ValueGeneratorCacheKey("", numberField));
		assertNotNull(foundIntegerGenerator);
		assertEquals(new Integer(4), foundIntegerGenerator.generate());

		DetailedTestDtoComposite item = generator.generate();
		assertNotNull(item);
		assertNull(item.getParent());
		assertEquals(4, item.getPrimitiveNumber());
	}

	/**
	 * test registering a value generator for class/propertyname/propertytype:
	 * on type to primitive
	 * 
	 * @throws Exception
	 */
	public void testRegisterAClassNamePropertyNameEditTypeToPrimitive() throws Exception {
		InstanceGenerator<DetailedTestDtoComposite> generator = getInstanceGeneratorBuilder(DetailedTestDtoComposite.class).build();

		generator.registerAClassNamePropertyNameGenerator(DetailedTestDtoComposite.class, "primitiveNumber", integerGen);
		generator.registerAClassNamePropertyNameGenerator(DetailedTestDtoComposite.class, "parent", nullGen);

		Field numberField = findAField(DetailedTestDtoComposite.class, "primitiveNumber");
		IValueGenerator<?> foundIntegerGenerator = generator.lookUpGenerator(new ValueGeneratorCacheKey("", numberField));
		assertNotNull(foundIntegerGenerator);
		assertEquals(new Integer(4), foundIntegerGenerator.generate());
		DetailedTestDtoComposite item = generator.generate();
		assertNotNull(item);
		assertEquals(4, item.getPrimitiveNumber());
	}

	/**
	 * test registering a value generator for class/propertyname/propertytype:
	 * on type to primitive
	 * 
	 * @throws Exception
	 */
	public void testCreatingABuilderFromAPrexistingBuilder() throws Exception {
		ValueGeneratorCache cache = new DefaultValueGeneratorCache();
		cache.registerAPropertyNameAndTypeGenerator("name", String.class, nameGen);

		InstanceGeneratorBuilder<DetailedTestDto> instanceGeneratorBuilder = getInstanceGeneratorBuilder(DetailedTestDto.class);
		instanceGeneratorBuilder.useCache(cache);

		InstanceGenerator<DetailedTestDto> generator = instanceGeneratorBuilder.build();

		Field numberField = findAField(DetailedTestDtoComposite.class, "name");
		IValueGenerator<?> foundNameGenerator = generator.lookUpGenerator(new ValueGeneratorCacheKey("", numberField));
		assertNotNull(foundNameGenerator);
		assertEquals("editedName", foundNameGenerator.generate());
		DetailedTestDto item = generator.generate();
		assertEquals("editedName", item.getName());
		assertNotNull(item);

		InstanceGeneratorBuilder<TestDto2> generatorBuilder = instanceGeneratorBuilder.getBuilderForClass(TestDto2.class);
		InstanceGenerator<TestDto2> testDto1Generator = generatorBuilder.build();
		TestDto2 testDto2 = testDto1Generator.generate();
		assertNotNull(testDto2);
		assertEquals("editedName", testDto2.getName());
	}

	/**
	 * @throws Exception
	 */
	public void testClearingABuilder() throws Exception {
		ValueGeneratorCache cache = new DefaultValueGeneratorCache();
		cache.registerAPropertyNameAndTypeGenerator("name", String.class, nameGen);

		InstanceGeneratorBuilder<DetailedTestDto> instanceGeneratorBuilder = getInstanceGeneratorBuilder(DetailedTestDto.class);
		instanceGeneratorBuilder.useCache(cache);

		InstanceGenerator<DetailedTestDto> generator = instanceGeneratorBuilder.build();

		DetailedTestDto item = generator.generate();
		assertNotNull(item);
		assertEquals("editedName", item.getName());

		generator.clear();

		try {
			generator.generate();
			fail("Should not be able to generate the item as its empty");
		} catch (Exception e) {
			String expectedMsg = InstanceGeneratorException.createInstanceCreationMessage("name.value", char[].class, e.getCause());
			assertEquals(expectedMsg, e.getMessage());
		}

	}

	/**
	 * test registering a value generator for class/propertyname/propertytype:
	 * on type to primitive
	 * 
	 * @throws Exception
	 */
	public void testExcludePropertyOnBuilder() throws Exception {
		InstanceGeneratorBuilder<DetailedTestDtoComposite> instanceGeneratorBuilder = getInstanceGeneratorBuilder(DetailedTestDtoComposite.class);
		instanceGeneratorBuilder.addELPathToIgnore("parent");
		InstanceGenerator<DetailedTestDtoComposite> generator = instanceGeneratorBuilder.build();

		try {
			DetailedTestDtoComposite item = generator.generate();
			assertNotNull(item);
		} catch (StackOverflowError e) {
			fail("shouldn't overflow as the parent path is excluded");
		}
	}

	private Field findAField(Class<?> clazz, String propertyName) {
		try {
			return clazz.getDeclaredField(propertyName);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private IValueGenerator<?> createPrimitiveIntGen() {
		return new IValueGenerator<Object>() {

			// boxing by nature..
			@SuppressWarnings("boxing")
			@Override
			public Object generate() {
				return 4;
			}
		};
	}

}
