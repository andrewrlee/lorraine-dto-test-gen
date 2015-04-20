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

import static java.lang.Boolean.FALSE;
import static uk.co.optimisticpanda.gtest.dto.defaultfill.RegisterTypeMode.ALL_INTERFACES;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

import uk.co.optimisticpanda.gtest.dto.defaultfill.ValueGeneratorCache;
import uk.co.optimisticpanda.gtest.dto.defaultfill.ValueGeneratorCacheImpl;

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
		registerATypeGenerator(String.class, () -> "DEFAULT");
		registerATypeGenerator(Class.class, () -> Class.class);
		
		Date date = new Date();
		registerATypeGenerator(Date.class, () -> new Date(date.getTime()));

		registerATypeGenerator(Character.class, () -> Character.valueOf('A'));
		registerATypeGenerator(char.class, () -> 'A');
		
		registerATypeGenerator(Integer.class, () -> Integer.valueOf(0));
		registerATypeGenerator(int.class, () -> 0);
		
		registerATypeGenerator(Double.class, () -> Double.valueOf(0.00d));
		registerATypeGenerator(double.class, () -> 0.00d);
		
		registerATypeGenerator(Boolean.class, () -> FALSE);
		registerATypeGenerator(boolean.class, () -> FALSE);
		
		registerATypeGenerator(Byte.class, () -> Byte.valueOf(Byte.MIN_VALUE));
		registerATypeGenerator(byte.class, () -> Byte.MIN_VALUE);

		registerATypeGenerator(ALL_INTERFACES, ArrayList.class, () -> new ArrayList<>());
		registerATypeGenerator(ALL_INTERFACES, HashSet.class, () -> new HashSet<>());
		registerATypeGenerator(ALL_INTERFACES, HashMap.class, () -> new HashMap<>());
		
		registerATypeGenerator(Object.class, () -> null);
	}
}
