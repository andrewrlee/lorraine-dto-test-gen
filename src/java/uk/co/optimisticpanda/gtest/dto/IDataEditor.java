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

import java.util.List;

import uk.co.optimisticpanda.gtest.dto.condition.EvenOddCondition;
import uk.co.optimisticpanda.gtest.dto.edit.IncrementingNameEdit;
import uk.co.optimisticpanda.gtest.dto.rule.IRule;

/**
 * An editor that applies one or more rules to a list of dtos.
 * 
 * @author Andy Lee
 * @param <D>
 *            The type of dto that this will edit.
 */
public interface IDataEditor<D> {

	/**
	 * Apply one or more rules to a list of dtos.
	 * 
	 * @param testData
	 *            The list of dtos to edit.
	 */
	void edit(List<D> testData);

	/**
	 * Apply one or more rules with a user specified index.
	 * 
	 * @param index
	 *            This is used by some {@link IRule }s to distinguish between
	 *            different dtos in the same list. For instance, rules which
	 *            contain {@link EvenOddCondition#ODD} will fire if the index is
	 *            odd and {@link IncrementingNameEdit} will append the index to
	 *            the end of a predefined base string.
	 * @param dataItem
	 *            the dto to apply the rule(s) to.
	 */
	public void edit(int index, D dataItem);
}
