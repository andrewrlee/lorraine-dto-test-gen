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
package uk.co.optimisticpanda.gtest.dto.test.utils;

/**
 * @author Andy Lee
 * 
 */
public class TestDto2 {

	/**
	 * @param name
	 * @param description
	 */
	public TestDto2(String name, String description) {
		this.name = name;
		this.description = description;
	}

	/**
     * 
     */
	public TestDto2() {
		// Default constructor
	}

	/**
         * 
         */
	public String name;
	/**
         * 
         */
	public String description;

	/**
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return description
	 */
	public String getDescription() {
		return description;
	}

}
