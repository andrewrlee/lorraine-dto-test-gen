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

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import uk.co.optimisticpanda.gtest.dto.defaultfill.IValueGenerator;

/**
 * A factory that returns value generators for common types. They all return
 * copies of an item passed into them.
 * 
 * Primitive generators are not included as unboxing works with reflection.
 * 
 * @author Andy Lee
 * 
 */
public enum ValueGeneratorFactory {
	/**
	 * Singleton instance
	 */
	INSTANCE; 
	/**
	 * Create a generator that creates new {@link Boolean} copies
	 * 
	 * @param b
	 *            the boolean to copy
	 * @return the created generator
	 */
	public static IValueGenerator<Boolean> createBooleanGenerator(final Boolean b) {
		return new IValueGenerator<Boolean>() {
			@Override
			public Boolean generate() {
				return new Boolean(b.booleanValue());
			}
		};
	}

	/**
	 * Create a generator that returns copies of the passed in {@link Short}
	 * 
	 * @param s
	 *            the short to copy
	 * @return the created generator
	 */
	public static IValueGenerator<Short> createShortGenerator(final Short s) {
		return new IValueGenerator<Short>() {
			@Override
			public Short generate() {
				return new Short(s.shortValue());
			}
		};
	}

	/**
	 * Create a generator that returns copies of the passed in {@link Integer}
	 * 
	 * @param i
	 *            the integer to copy
	 * @return the created generator
	 */
	public static IValueGenerator<Integer> createIntegerGenerator(final Integer i) {
		return new IValueGenerator<Integer>() {
			@Override
			public Integer generate() {
				return new Integer(i.intValue());
			}
		};
	}

	/**
	 * Create a generator that returns copies of the passed in {@link Double}
	 * 
	 * @param d
	 *            the Double to return a copy of.
	 * @return the created generator
	 */
	public static IValueGenerator<Double> createDoubleGenerator(final Double d) {
		return new IValueGenerator<Double>() {
			@Override
			public Double generate() {
				return new Double(d.doubleValue());
			}
		};
	}

	/**
	 * Create a generator that creates copies of the passed in {@link Long}
	 * 
	 * @param l
	 *            the long to return copies of.
	 * @return the created generator
	 */
	public static IValueGenerator<Long> createLongGenerator(final Long l) {
		return new IValueGenerator<Long>() {
			@Override
			public Long generate() {
				return new Long(l.longValue());
			}
		};
	}

	/**
	 * Create a generator that returns copies of the passed in {@link Character}.
	 * @param c the character to copy
	 * @return the created generator
	 */
	public static IValueGenerator<Character> createCharacterGenerator(final Character c) {
		return new IValueGenerator<Character>() {
			@Override
			public Character generate() {
				return new Character(c.charValue());
			}
		};
	}

	/**
	 * Create a generator that returns a copy of the passed in string.
	 * @param s the string to copy
	 * @return the created generator
	 */
	public static IValueGenerator<String> createStringGenerator(final String s) {
		return new IValueGenerator<String>() {
			@Override
			public String generate() {
				return new String(s);
			}
		};
	}

	
	/**
	 * Create a generator that returns a copy of the passed in {@link Byte}.
	 * @param b the Byte to copy.
	 * @return the created generator
	 */
	public static IValueGenerator<Byte> createByteGenerator(final Byte b) {
		return new IValueGenerator<Byte>() {
			@Override
			public Byte generate() {
				return new Byte(b.byteValue());
			}
		};
	}

	/**
	 * Create a generator that returns the passed in class.
	 * @param c the class to return.
	 * @return the created generator
	 */
	public static IValueGenerator<Class<?>> createClassGenerator(final Class<?> c) {
		return new IValueGenerator<Class<?>>() {
			@Override
			public Class<?> generate() {
				return c;
			}
		};
	}
	
	
	/**
	 * Create generator that returns null
	 * @return a new value generator that generates nulls.
	 */
	public static IValueGenerator<?> createNullGenerator() {
		return new IValueGenerator<Object>() {
			@Override
			public Object generate() {
				return null;
			}
		};
	}
	
	/**
	 * Create generator that returns new lists
	 * @return a new value generator that generates new lists.
	 */
	public static IValueGenerator<List<?>> createListGenerator() {
		return new IValueGenerator<List<?>>() {
			@Override
			public List<?> generate() {
				return new ArrayList<Object>();
			}
		};
	}
	
	/**
	 * Create generator that returns new sets
	 * @return a new value generator that generates new sets.
	 */
	public static IValueGenerator<Set<?>> createSetGenerator() {
		return new IValueGenerator<Set<?>>() {
			@Override
			public Set<?> generate() {
				return new HashSet<Object>();
			}
		};
	}
	
	/**
	 * Create generator that generates new maps
	 * @return a new value generator that generates new maps.
	 */
	public static IValueGenerator<Map<?,?>> createMapGenerator() {
		return new IValueGenerator<Map<?,?>>() {
			@Override
			public Map<?,?> generate() {
				return new HashMap<Object,Object>();
			}
		};
	}
	
	/**
	 * Create generator that generates new Dates
	 * @param date the date to copy
	 * @return a new value generator that generates copies of the passed in dates.
	 */
	public static IValueGenerator<Date> createDateGenerator(final Date date) {
		return new IValueGenerator<Date>() {
			@Override
			public Date generate() {
				return new Date(date.getTime());
			}
		};
	}
}
