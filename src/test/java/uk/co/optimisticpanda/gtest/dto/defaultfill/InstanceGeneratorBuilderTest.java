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

import java.util.function.Supplier;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import uk.co.optimisticpanda.gtest.dto.TestUtilsContext;
import uk.co.optimisticpanda.gtest.dto.defaultfill.insgen.InstanceGenerator;
import uk.co.optimisticpanda.gtest.dto.defaultfill.insgen.InstanceGenerator.InstanceGeneratorBuilder;
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
public class InstanceGeneratorBuilderTest {
	private Supplier<Integer> integerGen = () -> new Integer(4);
	private Supplier<?> primitiveGen = () -> 4;
	private Supplier<?> nullGen = () -> null;
	private Supplier<?> nameGen = () -> "editedName";

	@Before
	public void setUp() throws Exception {
		TestUtilsContext.useOgnl();
	}

	/**
	 * Tests the creation of an instance without a default constructor
	 */
	@Test
	public void createEmptyInstanceWithOutDefaultConstructor() {
		InstanceGenerator<TestDtoWithoutDefaultConstructor> generator = InstanceGenerator.create(TestDtoWithoutDefaultConstructor.class);

		try {
			generator.generate();
			Assert.fail("Should throw an exception as no default constructor");
		} catch (InstanceGeneratorException e) {
			String expectedMessage = InstanceGeneratorException.createInstanceCreationMessage("", TestDtoWithoutDefaultConstructor.class, e.getCause());
			assertThat(e.getMessage()).isEqualTo(expectedMessage);
		}
	}

	/**
	 * Tests the creation of an empty instance if no value generators are
	 * registered
	 */
	@Test
	public void createEmptyInstance() {
		InstanceGenerator<DetailedTestDto> generator = InstanceGenerator.create(DetailedTestDto.class);

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
	@Test
	public void checkSettingParentToNull() {

		DefaultValueGenerator valueGenerator = new DefaultValueGenerator();
		valueGenerator.registerAClassNamePropertyNameGenerator(DetailedTestDtoComposite.class, "parent", nullGen);
		InstanceGenerator<DetailedTestDtoComposite> generator = 
				InstanceGenerator.create(DetailedTestDtoComposite.class, valueGenerator);

		try {
			DetailedTestDtoComposite item = generator.generate();
			assertThat(item).isNotNull();
			assertThat(item.getParent()).isNull();
		} catch (StackOverflowError e) {
			Assert.fail("shouldn't overflow as there is a registered generator for parent, and just generates an empty list for children");
		}
	}

	/**
	 * test registering a value generator for class/propertyname/propertytype:
	 * on type to type
	 * 
	 * @throws Exception
	 */
	@Test
	public void checkRegisterAClassNamePropertyNameEditTypeToType() throws Exception {

		DefaultValueGenerator valueGenerator = new DefaultValueGenerator();
		valueGenerator.registerAClassNamePropertyNameGenerator(DetailedTestDtoComposite.class, "number", integerGen);
		valueGenerator.registerAClassNamePropertyNameGenerator(DetailedTestDtoComposite.class, "parent", nullGen);
		
		InstanceGenerator<DetailedTestDtoComposite> generator = 
				InstanceGenerator.create(DetailedTestDtoComposite.class, valueGenerator);

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
	@Test
	public void registerAClassNamePropertyNameEditPrimitiveToType() throws Exception {

		ValueGenerator valueGenerator = new DefaultValueGenerator();
		valueGenerator.registerAClassNamePropertyNameGenerator(DetailedTestDtoComposite.class, "number", primitiveGen);
		valueGenerator.registerAClassNamePropertyNameGenerator(DetailedTestDtoComposite.class, "parent", nullGen);
		
		InstanceGenerator<DetailedTestDtoComposite> generator = 
				InstanceGenerator.create(DetailedTestDtoComposite.class, valueGenerator);


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
	@Test
	public void registerAClassNamePropertyNameEditPrimitiveToPrimitive() throws Exception {

		DefaultValueGenerator valueGenerator = new DefaultValueGenerator();
		valueGenerator.registerAClassNamePropertyNameGenerator(DetailedTestDtoComposite.class, "primitiveNumber", primitiveGen);
		valueGenerator.registerAClassNamePropertyNameGenerator(DetailedTestDtoComposite.class, "parent", nullGen);
		InstanceGenerator<DetailedTestDtoComposite> generator = 
				InstanceGenerator.create(DetailedTestDtoComposite.class, valueGenerator);

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
	@Test
	public void registerAClassNamePropertyNameEditTypeToPrimitive() throws Exception {

		DefaultValueGenerator valueGenerator = new DefaultValueGenerator();
		valueGenerator.registerAClassNamePropertyNameGenerator(DetailedTestDtoComposite.class, "primitiveNumber", integerGen);
		valueGenerator.registerAClassNamePropertyNameGenerator(DetailedTestDtoComposite.class, "parent", nullGen);
		
		InstanceGenerator<DetailedTestDtoComposite> generator = 
				InstanceGenerator.create(DetailedTestDtoComposite.class, valueGenerator);

		DetailedTestDtoComposite item = generator.generate();
		assertThat(item).isNotNull();
		assertThat(item.getPrimitiveNumber()).isEqualTo(4);
	}

	@Test
	public void creatingABuilderFromAPrexistingBuilder() throws Exception {
		ValueGenerator cache = new DefaultValueGenerator();
		cache.registerAPropertyNameAndTypeGenerator("name", String.class, nameGen);

		InstanceGeneratorBuilder<DetailedTestDto> instanceGeneratorBuilder = 
				InstanceGenerator.of(DetailedTestDto.class).useCache(cache);

		InstanceGenerator<DetailedTestDto> generator = instanceGeneratorBuilder.build();

		DetailedTestDto item = generator.generate();
		assertThat(item.getName()).isEqualTo("editedName");
		assertThat(item).isNotNull();

		InstanceGeneratorBuilder<TestDto2> generatorBuilder = instanceGeneratorBuilder.getBuilderForClass(TestDto2.class);
		InstanceGenerator<TestDto2> testDto1Generator = generatorBuilder.build();
		TestDto2 testDto2 = testDto1Generator.generate();
		assertThat(testDto2).isNotNull();
		assertThat(testDto2.getName()).isEqualTo("editedName");
	}

	@Test
	public void clearingABuilder() throws Exception {
		ValueGenerator cache = new DefaultValueGenerator();
		cache.registerAPropertyNameAndTypeGenerator("name", String.class, nameGen);

		InstanceGenerator<DetailedTestDto> instanceGenerator = 
				InstanceGenerator.create(DetailedTestDto.class, cache);

		DetailedTestDto item = instanceGenerator.generate();
		assertThat(item).isNotNull();
		assertThat(item.getName()).isEqualTo("editedName");

		cache.clear();

		try {
			instanceGenerator.generate();
			Assert.fail("Should not be able to generate the item as its empty");
		} catch (Exception e) {
			String expectedMsg = InstanceGeneratorException.createInstanceCreationMessage("name.value", char[].class, e.getCause());
			assertThat(e.getMessage()).isEqualTo(expectedMsg);
		}

	}

	@Test
	public void excludePropertyOnBuilder() throws Exception {
		InstanceGeneratorBuilder<DetailedTestDtoComposite> instanceGeneratorBuilder = InstanceGenerator.of(DetailedTestDtoComposite.class);
		instanceGeneratorBuilder.addELPathToIgnore("parent");
		InstanceGenerator<DetailedTestDtoComposite> generator = instanceGeneratorBuilder.build();

		try {
			DetailedTestDtoComposite item = generator.generate();
			assertThat(item).isNotNull();
		} catch (StackOverflowError e) {
			Assert.fail("shouldn't overflow as the parent path is excluded");
		}
	}
}
