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
package uk.co.optimisticpanda.gtest.dto.rule;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Andy Lee A rule that can be used to wrap multiple rules that are
 *         applicable for one type of dto.
 * @param <D>
 *            The type of the dto that is applicable for the rule
 */
public class CombinedRule<D> implements IRule<D> {

	private List<IRule<D>> list = new ArrayList<IRule<D>>();

	/**
	 * Add an edit rule
	 * 
	 * @param editRule
	 *            An applicable edit rule that is to be added. Applicable, in
	 *            this case being one that can be used to edit this type of dto.
	 * @return this to allow chaining
	 */
	public CombinedRule<D> addEditRule(IRule<D> editRule) {
		list.add(editRule);
		return this;
	}

	/**
	 * @see uk.co.optimisticpanda.gtest.dto.rule.IRule#edit(int,
	 *      java.lang.Object)
	 */
	@Override
	public void edit(int index, D data) {
		for (IRule<D> rule : list) {
			if (rule.isValid(index, data)) {
				rule.edit(index, data);
			}
		}
	}

	/**
	 * This rule is always valid - each child rule is validated separately
	 * */
	@Override
	public boolean isValid(int index, D dataItem) {
		return true;
	}

}
