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
package uk.co.optimisticpanda.gtest.dto.defaultfill.defaultgens;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import uk.co.optimisticpanda.gtest.dto.defaultfill.IValueGenerator;
import junit.framework.TestCase;
import static org.assertj.core.api.Assertions.assertThat;
/**
 * @author Andy Lee
 */
public class DefaultValueGeneratorFactoryTest extends TestCase {

	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}

	/**
	 * @throws Exception
	 */
	public void testBoolGen() throws Exception {
		IValueGenerator<Boolean> booleanGenerator = ValueGeneratorFactory.createBooleanGenerator(Boolean.TRUE);
		Boolean generatedBoolean = booleanGenerator.generate();
		assertThat(generatedBoolean).isNotNull();
		assertThat(true).isEqualTo(generatedBoolean.booleanValue());
	}

	/**
	 * @throws Exception
	 */
	public void testIntGen() throws Exception {
		IValueGenerator<Integer> integerGenerator = ValueGeneratorFactory.createIntegerGenerator(new Integer(4));
		Integer generatedInteger = integerGenerator.generate();
		assertThat(generatedInteger).isNotNull();
		assertThat(new Integer(4)).isEqualTo(generatedInteger);
		assertThat(generatedInteger == new Integer(4)).isFalse();
		}
	
	/**
	 * @throws Exception
	 */
	public void testCharGen() throws Exception {
		IValueGenerator<Character> charGenerator = ValueGeneratorFactory.createCharacterGenerator(new Character('c'));
		Character generatedCharacter = charGenerator.generate();
		assertThat(generatedCharacter).isNotNull();
		assertThat(generatedCharacter).isEqualTo(new Character('c'));
		assertThat(generatedCharacter == new Character('c')).isFalse();
	}
	
	/**
	 * @throws Exception
	 */
	public void testStringGen() throws Exception {
		IValueGenerator<String> stringGenerator = ValueGeneratorFactory.createStringGenerator("test");
		String generatedString = stringGenerator.generate();
		assertThat(generatedString).isNotNull();
		assertThat(generatedString).isEqualTo("test");
		assertThat(generatedString == "test").isFalse();
	}
	
	/**
	 * @throws Exception
	 */
	public void testNullGen() throws Exception {
		IValueGenerator<?> nullGenerator = ValueGeneratorFactory.createNullGenerator();
		Object generatedNull= nullGenerator.generate();
		assertThat(generatedNull).isNull();
	}
	
	/**
	 * @throws Exception
	 */
	public void testDoubleGen() throws Exception {
		IValueGenerator<Double> doubleGenerator = ValueGeneratorFactory.createDoubleGenerator(new Double(4.4));
		Object generatedDouble= doubleGenerator.generate();
		assertThat(generatedDouble).isNotNull();
		assertThat(generatedDouble).isEqualTo(new Double(4.4));
		assertThat(generatedDouble == new Double(4.4)).isFalse();
	}
	
	/**
	 * @throws Exception
	 */
	public void testLongGen() throws Exception {
		IValueGenerator<Long> longGenerator = ValueGeneratorFactory.createLongGenerator(new Long(4L));
		Object generatedLong= longGenerator.generate();
		assertThat(generatedLong).isNotNull();
		assertThat(generatedLong).isEqualTo(new Long(4L));
		assertThat(generatedLong == new Long(4)).isFalse();
	}
	
	/**
	 * @throws Exception
	 */
	public void testClassGen() throws Exception {
		IValueGenerator<Class<?>> classGenerator = ValueGeneratorFactory.createClassGenerator(Integer.class);
		Object generatedClass= classGenerator.generate();
		assertThat(generatedClass).isNotNull();
		assertThat(generatedClass).isEqualTo(Integer.class);
	}

	/**
	 * @throws Exception
	 */
	public void testByteGen() throws Exception {
		IValueGenerator<Byte> classGenerator = ValueGeneratorFactory.createByteGenerator(new Byte(Byte.MAX_VALUE));
		Byte generatedByte= classGenerator.generate();
		assertThat(generatedByte).isNotNull();
		assertThat(generatedByte).isEqualTo(new Byte(Byte.MAX_VALUE));
	}

	/**
	 * @throws Exception
	 */
	public void testDateGen() throws Exception {
		long currentTimeMillis = System.currentTimeMillis();
		IValueGenerator<Date> dateGenerator = ValueGeneratorFactory.createDateGenerator(new Date(currentTimeMillis));
		Date generatedDate = dateGenerator.generate();
		assertThat(generatedDate ).isNotNull();
		assertThat(generatedDate ).isEqualTo(new Date(currentTimeMillis));
		assertThat(generatedDate == new Date(currentTimeMillis)).isFalse();
	}
	
	/**
	 * @throws Exception
	 */
	public void testListGen() throws Exception {
		IValueGenerator<List<?>> listGenerator = ValueGeneratorFactory.createListGenerator();
		List<?> generatedList = listGenerator.generate();
		List<?> generatedList2 = listGenerator.generate();
		
		assertThat(generatedList ).isNotNull();
		assertThat(generatedList ).isEqualTo(generatedList2);
		assertThat(generatedList == generatedList2).isFalse();
	}
	
	/**
	 * @throws Exception
	 */
	public void testSetGen() throws Exception {
		IValueGenerator<Set<?>> setGenerator = ValueGeneratorFactory.createSetGenerator();
		Set<?> generatedSet = setGenerator.generate();
		Set<?> generatedSet2 = setGenerator.generate();
		
		assertThat(generatedSet ).isNotNull();
		assertThat(generatedSet2 ).isEqualTo(generatedSet);
		assertThat(generatedSet == generatedSet2).isFalse();
	}
	
	/**
	 * @throws Exception
	 */
	public void testMapGen() throws Exception {
		IValueGenerator<Map<?,?>> mapGenerator = ValueGeneratorFactory.createMapGenerator();
		Map<?, ?> generatedMap = mapGenerator.generate();
		Map<?, ?> generatedMap2 = mapGenerator.generate();
		
		assertThat(generatedMap ).isNotNull();
		assertThat(generatedMap2 ).isEqualTo(generatedMap);
		assertThat(generatedMap == generatedMap2).isFalse();
	}
}
