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
package uk.co.optimisticpanda.gtest.dto;

import java.util.ArrayList;
import java.util.List;

import uk.co.optimisticpanda.gtest.dto.rule.IRule;

/**
 * <p>
 * This is a basic {@link IDataEditor} that takes a list of dtos and then calls
 * {@link IDataEditor#edit(int, Object)} on each item passed in.
 * </p>
 * <p>
 * The idea is that the user of the library would create helper methods that
 * would create varied sized lists of generic entities and also pass in a
 * {@link SimpleDataEditor}. The SimpleDataEditor will act a bit like a callback
 * to alter the list in ways defined by the rules.
 * </p>
 * 
 * <pre>
 * public List<Dto> getList(int numberOfDtosToBeCreated, TestDataEditor editor){ 
 *      List<Dto> dtos = new ArrayList<Dto>(); 
 *      for( int i=0; i< * numberOfDtosToBeCreated ; i++){ 
 *          Dto dto = createDto(); 
 *          editor.edit(i, dto); 
 *          store(dto); 
 *          dtos.add(dto); 
 *      } 
 *      return dtos;
 * }
 * </pre>
 * 
 * <i>NOTE - It is intended for future releases to provide increased user
 * support in this area -so these types of method will not be needed</i>
 * 
 * @author Andy Lee
 * @param <D>
 *            The type of dto that this will edit.
 */
public class SimpleDataEditor<D> implements IDataEditor<D> {

	private final List<IRule<D>> rules;

	/**
	 * Create a new SimpleDataEditor with no rules.
	 */
	public SimpleDataEditor() {
		rules = new ArrayList<IRule<D>>();
	}

	/**
	 * Add a new Rule
	 * 
	 * @param rule
	 *            the rule to add.
	 * @return the current {@link SimpleDataEditor} to allow chaining.
	 */
	public SimpleDataEditor<D> addRule(IRule<D> rule) {
		rules.add(rule);
		return this;
	}

	/**
	 * see {@link IDataEditor#edit(List)}
	 * 
	 * @param testData
	 *            the collection of dtos to apply the rules to.
	 */
	public void edit(List<D> testData) {
		for (int index = 0; index < testData.size(); index++) {
			D dataItem = testData.get(index);
			edit(index, dataItem);
		}
	}

	/**
	 * see {@link IDataEditor#edit(int, Object)}
	 * 
	 * @param index
	 *            the current index of the dto in the list
	 * @param dataItem
	 *            the dto to apply the rule to.
	 */
	@SuppressWarnings("unchecked")
	public void edit(int index, D dataItem) {
		for (IRule rule : rules) {
			if (rule.isValid(index, dataItem)) {
				rule.edit(index, dataItem);
			}
		}
	}

}
