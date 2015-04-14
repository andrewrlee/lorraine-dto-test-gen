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

import java.util.Arrays;
import java.util.Iterator;

import uk.co.optimisticpanda.gtest.dto.condition.ICondition;
import uk.co.optimisticpanda.gtest.dto.edit.IEdit;

/**
 * A simple implementation of {@link IRule}. This wraps 1 or more condition. If
 * any single condition returns true then the effect of the edit occurs to
 * the current dto being processed.
 * 
 * @author Andy Lee
 * @param <D>
 *            the type that this rule should edit
 */
public class BaseRule<D> implements IRule<D> {

	protected final ICondition[] conditions;
	protected final IEdit<D> edit;

	/**
	 * @param edit
	 *            the action to occur to the dto.
	 * @param conditions
	 *            a list of conditions for which this rule will fire if any are
	 *            true.
	 */
	public BaseRule(IEdit<D> edit, ICondition... conditions) {
		this.edit = edit;
		this.conditions = conditions;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see uk.co.optimisticpanda.gtest.dto.rule.IRule#edit(int,
	 *      java.lang.Object)
	 */
	public void edit(int index, D dataItem) {
		edit.edit(index, dataItem);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see uk.co.optimisticpanda.gtest.dto.rule.IRule#isValid(int,
	 *      java.lang.Object)
	 */
	public boolean isValid(int index, D dataItem) {
		for (ICondition condition : conditions) {
			if (condition.isValid(index, dataItem)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * A human readable representation of the rule.
	 */
	@Override
	public String toString() {
		Iterator<ICondition> iterator = Arrays.asList(conditions).iterator();
		StringBuilder builder = new StringBuilder(edit + " WHERE " + iterator.next());
		while (iterator.hasNext()) {
			builder.append(" AND " + iterator.next());
		}
		return builder.toString();
	}
}
