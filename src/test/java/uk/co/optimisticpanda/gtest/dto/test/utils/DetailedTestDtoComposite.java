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

import java.util.ArrayList;
import java.util.List;

/**
 * A composite object for use in test
 * @author Andy Lee 
 */
public class DetailedTestDtoComposite {

	private List<DetailedTestDtoComposite> children;
	private String name;
	private Integer number;
	private Integer otherNumber;
	private int primitiveNumber;
	private DetailedTestDtoComposite parent;

	/**
	 * Constructor
	 * @param name
	 * @param number 
	 * @param otherNumber 
	 * @param primitiveNumber 
	 */
	public DetailedTestDtoComposite(String name, Integer number, Integer otherNumber, int primitiveNumber) {
		this.number = number;
		this.otherNumber = otherNumber;
		this.primitiveNumber = primitiveNumber;
		this.children = new ArrayList<DetailedTestDtoComposite>();
		this.name = name;
	}

	/**
	 * 
	 */
	public DetailedTestDtoComposite(){
		//default constructor
	}
	
	/**
	 * @return all children
	 */
	public List<DetailedTestDtoComposite> getChildren() {
		return children;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param child a child
	 */
	public void addChild(DetailedTestDtoComposite child){
		children.add(child);
	}

	/**
	 * @return number
	 */
	public Integer getNumber() {
		return number;
	}

	/**
	 * @return otherNumber
	 */
	public Integer getOtherNumber() {
		return otherNumber;
	}

	/**
	 * @return primitiveNumber
	 */
	public int getPrimitiveNumber() {
		return primitiveNumber;
	}


	/**
	 * @return parent
	 */
	public DetailedTestDtoComposite getParent() {
		return parent;
	}
}
