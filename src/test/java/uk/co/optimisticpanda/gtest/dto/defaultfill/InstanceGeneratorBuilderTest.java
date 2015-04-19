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
import static org.assertj.core.api.Assertions.assertThat;

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

	private <D> InstanceGeneratorBuilder<D> getInstanceGeneratorBuilder(Class<D> clazz) {
		return InstanceGeneratorBuilder.create(clazz);
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
			assertThat(e.getMessage()).isEqualTo(expectedMessage);
		}
	}

	/**
	 * Tests the creation of an empty instance if no value generators are
	 * registered
	 */
	public void testCreateEmptyInstance() {
		InstanceGenerator<DetailedTestDto> generator = getInstanceGeneratorBuilder(DetailedTestDto.class).build();

		DetailedTestDto dto1 = generator.generate();
		assertThat(dto1).as("should create instances").isNotNull();
		DetailedTestDto dto2 = generator.generate();
		assertThat(dto2).as("should create instances").isNotNull();
		assertThat(dto2).as("should both be the same").isEqualTo(dto1);
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
		assertThat(foundParentGenerator).isNotNull();
		assertThat(foundParentGenerator.generate()).isNull();
		try {
			DetailedTestDtoComposite item = generator.generate();
			assertThat(item).isNotNull();
			assertThat(item.getParent()).isNull();
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
		assertThat(foundIntegerGenerator).isNotNull();
		assertThat(foundIntegerGenerator.generate() instanceof Integer).isTrue();

		DetailedTestDtoComposite item = generator.generate();
		assertThat(item).isNotNull();
		assertThat(item.getNumber()).isEqualTo(new Integer(4));
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
		assertThat(foundIntegerGenerator).isNotNull();
		assertThat(foundIntegerGenerator.generate()).isEqualTo(new Integer(4));

		DetailedTestDtoComposite item = generator.generate();
		assertThat(item).isNotNull();
		assertThat(item.getNumber()).isEqualTo(new Integer(4));
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
		assertThat(foundIntegerGenerator).isNotNull();
		assertThat(foundIntegerGenerator.generate()).isEqualTo(new Integer(4));

		DetailedTestDtoComposite item = generator.generate();
		assertThat(item).isNotNull();
		assertThat(item.getParent()).isNull();
		assertThat(item.getPrimitiveNumber()).isEqualTo(4);
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
		assertThat(foundIntegerGenerator).isNotNull();
		assertThat(foundIntegerGenerator.generate()).isEqualTo(new Integer(4));
		DetailedTestDtoComposite item = generator.generate();
		assertThat(item).isNotNull();
		assertThat(item.getPrimitiveNumber()).isEqualTo(4);
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
		assertThat(foundNameGenerator).isNotNull();
		assertThat(foundNameGenerator.generate()).isEqualTo("editedName");
		DetailedTestDto item = generator.generate();
		assertThat(item.getName()).isEqualTo("editedName");
		assertThat(item).isNotNull();

		InstanceGeneratorBuilder<TestDto2> generatorBuilder = instanceGeneratorBuilder.getBuilderForClass(TestDto2.class);
		InstanceGenerator<TestDto2> testDto1Generator = generatorBuilder.build();
		TestDto2 testDto2 = testDto1Generator.generate();
		assertThat(testDto2).isNotNull();
		assertThat(testDto2.getName()).isEqualTo("editedName");
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
		assertThat(item).isNotNull();
		assertThat(item.getName()).isEqualTo("editedName");

		generator.clear();

		try {
			generator.generate();
			fail("Should not be able to generate the item as its empty");
		} catch (Exception e) {
			String expectedMsg = InstanceGeneratorException.createInstanceCreationMessage("name.value", char[].class, e.getCause());
			assertThat(e.getMessage()).isEqualTo(expectedMsg);
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
			assertThat(item).isNotNull();
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
