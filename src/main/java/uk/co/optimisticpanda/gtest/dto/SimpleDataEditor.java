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

import static uk.co.optimisticpanda.gtest.dto.util.FunctionUtils.indexed;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import uk.co.optimisticpanda.gtest.dto.rule.Edit;
/**
 * <p>
 * This is a basic {@link IDataEditor} that takes a list of dtos and then calls
 * {@link IDataEditor#edit(int, Object)} on each item passed in.
 * </p>
 * 
 * @author Andy Lee
 * @param <D>
 *            The type of dto that this will edit.
 */
public class SimpleDataEditor<D> implements IDataEditor<D> {

	private final List<Edit<D>> edits;

	/**
	 * Create a new SimpleDataEditor with no rules.
	 */
	@SafeVarargs
	private SimpleDataEditor(Edit<D>... edits) {
		this.edits = new ArrayList<Edit<D>>(Arrays.<Edit<D>>asList(edits));
	}

	public static <D> SimpleDataEditor<D> create(){
		return new SimpleDataEditor<D>();
	}
	
	public SimpleDataEditor<D> add(Edit<D> edit) {
		this.edits.add(edit);
		return this;
	}
	
	/**
	 * see {@link IDataEditor#edit(List)}
	 * 
	 * @param testData
	 *            the collection of dtos to apply the rules to.
	 */
	public void edit(List<D> testData) {
		testData.stream().map(indexed()).forEach(i -> edit(i.index, i.item));
	}

	/**
	 * see {@link IDataEditor#edit(int, Object)}
	 * 
	 * @param index
	 *            the current index of the dto in the list
	 * @param dataItem
	 *            the dto to apply the rule to.
	 */
	public D edit(int index, D dataItem) {
		edits.stream()
				.filter(r -> r.isValid(index, dataItem))
				.forEach(r-> r.edit(index, dataItem));
		return dataItem;
	}

}
