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
		assertNotNull(generatedBoolean);
		assertEquals(generatedBoolean.booleanValue(), true);
	}

	/**
	 * @throws Exception
	 */
	public void testIntGen() throws Exception {
		IValueGenerator<Integer> integerGenerator = ValueGeneratorFactory.createIntegerGenerator(new Integer(4));
		Integer generatedInteger = integerGenerator.generate();
		assertNotNull(generatedInteger);
		assertEquals(generatedInteger, new Integer(4));
		assertFalse(generatedInteger == new Integer(4));
		}
	
	/**
	 * @throws Exception
	 */
	public void testCharGen() throws Exception {
		IValueGenerator<Character> charGenerator = ValueGeneratorFactory.createCharacterGenerator(new Character('c'));
		Character generatedCharacter = charGenerator.generate();
		assertNotNull(generatedCharacter);
		assertEquals(new Character('c'),generatedCharacter);
		assertFalse(generatedCharacter == new Character('c'));
	}
	
	/**
	 * @throws Exception
	 */
	public void testStringGen() throws Exception {
		IValueGenerator<String> stringGenerator = ValueGeneratorFactory.createStringGenerator("test");
		String generatedString = stringGenerator.generate();
		assertNotNull(generatedString);
		assertEquals("test",generatedString);
		assertFalse(generatedString == "test");
	}
	
	/**
	 * @throws Exception
	 */
	public void testNullGen() throws Exception {
		IValueGenerator<?> nullGenerator = ValueGeneratorFactory.createNullGenerator();
		Object generatedNull= nullGenerator.generate();
		assertNull(generatedNull);
	}
	
	/**
	 * @throws Exception
	 */
	public void testDoubleGen() throws Exception {
		IValueGenerator<Double> doubleGenerator = ValueGeneratorFactory.createDoubleGenerator(new Double(4.4));
		Object generatedDouble= doubleGenerator.generate();
		assertNotNull(generatedDouble);
		assertEquals(new Double(4.4), generatedDouble);
		assertFalse(generatedDouble == new Double(4.4));
	}
	
	/**
	 * @throws Exception
	 */
	public void testLongGen() throws Exception {
		IValueGenerator<Long> longGenerator = ValueGeneratorFactory.createLongGenerator(new Long(4L));
		Object generatedLong= longGenerator.generate();
		assertNotNull(generatedLong);
		assertEquals(new Long(4L), generatedLong);
		assertFalse(generatedLong == new Long(4));
	}
	
	/**
	 * @throws Exception
	 */
	public void testClassGen() throws Exception {
		IValueGenerator<Class<?>> classGenerator = ValueGeneratorFactory.createClassGenerator(Integer.class);
		Object generatedClass= classGenerator.generate();
		assertNotNull(generatedClass);
		assertEquals(Integer.class, generatedClass);
	}

	/**
	 * @throws Exception
	 */
	public void testByteGen() throws Exception {
		IValueGenerator<Byte> classGenerator = ValueGeneratorFactory.createByteGenerator(new Byte(Byte.MAX_VALUE));
		Byte generatedByte= classGenerator.generate();
		assertNotNull(generatedByte);
		assertEquals(new Byte(Byte.MAX_VALUE), generatedByte);
	}

	/**
	 * @throws Exception
	 */
	public void testDateGen() throws Exception {
		long currentTimeMillis = System.currentTimeMillis();
		IValueGenerator<Date> dateGenerator = ValueGeneratorFactory.createDateGenerator(new Date(currentTimeMillis));
		Date generatedDate = dateGenerator.generate();
		assertNotNull(generatedDate );
		assertEquals(new Date(currentTimeMillis), generatedDate );
		assertFalse(generatedDate == new Date(currentTimeMillis));
	}
	
	/**
	 * @throws Exception
	 */
	public void testListGen() throws Exception {
		IValueGenerator<List<?>> listGenerator = ValueGeneratorFactory.createListGenerator();
		List<?> generatedList = listGenerator.generate();
		List<?> generatedList2 = listGenerator.generate();
		
		assertNotNull(generatedList );
		assertEquals(generatedList2, generatedList );
		assertFalse(generatedList == generatedList2);
	}
	
	/**
	 * @throws Exception
	 */
	public void testSetGen() throws Exception {
		IValueGenerator<Set<?>> setGenerator = ValueGeneratorFactory.createSetGenerator();
		Set<?> generatedSet = setGenerator.generate();
		Set<?> generatedSet2 = setGenerator.generate();
		
		assertNotNull(generatedSet );
		assertEquals(generatedSet, generatedSet2 );
		assertFalse(generatedSet == generatedSet2);
	}
	
	/**
	 * @throws Exception
	 */
	public void testMapGen() throws Exception {
		IValueGenerator<Map<?,?>> mapGenerator = ValueGeneratorFactory.createMapGenerator();
		Map<?, ?> generatedMap = mapGenerator.generate();
		Map<?, ?> generatedMap2 = mapGenerator.generate();
		
		assertNotNull(generatedMap );
		assertEquals(generatedMap, generatedMap2 );
		assertFalse(generatedMap == generatedMap2);
	}
}
