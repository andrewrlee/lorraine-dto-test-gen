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
package uk.co.optimisticpanda.gtest.dto.rulebuilder.fluent;

import uk.co.optimisticpanda.gtest.dto.condition.ICondition;
import uk.co.optimisticpanda.gtest.dto.edit.IEdit;

/**
 * An interface used by the fluent builder. 
 * @param <D>
 *            Type of the object this rule is being created for.
 * @author Andy Lee
 */
public interface IAddEditOrWhereBuilder<D> {
	
	/**
	 * @param edit to add
	 * @return this to allow chaining
	 */
	IAddEditOrWhereBuilder<D> and(IEdit<D> edit);
	
	/**
	 * @param condition base condition
	 * @return this to allow chaining
	 */
	IAddWhereOrEndBuilder<D> where(ICondition condition);
}
