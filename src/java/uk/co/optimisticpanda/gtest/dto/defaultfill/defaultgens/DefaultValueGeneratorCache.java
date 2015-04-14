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

import uk.co.optimisticpanda.gtest.dto.defaultfill.ValueGeneratorCache;
import uk.co.optimisticpanda.gtest.dto.defaultfill.RegisterTypeMode;
import uk.co.optimisticpanda.gtest.dto.defaultfill.ValueGeneratorCacheImpl;
import static uk.co.optimisticpanda.gtest.dto.defaultfill.defaultgens.ValueGeneratorFactory.*;

/**
 *An {@link ValueGeneratorCache} that has generators registered for most
 * simple properties
 * 
 * @author Andy Lee
 */
public class DefaultValueGeneratorCache extends ValueGeneratorCacheImpl {

	/**
	 * Create a Default Value Generator Cache.
	 */
	public DefaultValueGeneratorCache() {
		registerATypeGenerator(String.class, createStringGenerator("DEFAULT"));
		registerATypeGenerator(Class.class, createClassGenerator(Class.class));
		registerATypeGenerator(Date.class, createDateGenerator(new Date()));

		registerATypeGenerator(Character.class, createCharacterGenerator(new Character('A')));
		registerATypeGenerator(char.class, createCharacterGenerator(new Character('A')));
		
		registerATypeGenerator(Integer.class, createIntegerGenerator(new Integer(0)));
		registerATypeGenerator(int.class, createIntegerGenerator(new Integer(0)));
		
		registerATypeGenerator(Double.class, createDoubleGenerator(new Double(0.00)));
		registerATypeGenerator(double.class, createDoubleGenerator(new Double(0.00)));
		
		registerATypeGenerator(Boolean.class, createBooleanGenerator(Boolean.FALSE));
		registerATypeGenerator(boolean.class, createBooleanGenerator(Boolean.FALSE));
		
		registerATypeGenerator(Byte.class, createByteGenerator(new Byte(Byte.MIN_VALUE)));
		registerATypeGenerator(byte.class, createByteGenerator(new Byte(Byte.MIN_VALUE)));

		registerATypeGenerator(RegisterTypeMode.ALL_INTERFACES, ArrayList.class, createListGenerator());
		registerATypeGenerator(RegisterTypeMode.ALL_INTERFACES, HashSet.class, createSetGenerator());
		registerATypeGenerator(RegisterTypeMode.ALL_INTERFACES, HashMap.class, createMapGenerator());
		
		registerATypeGenerator(Object.class, createNullGenerator());
	}

}
