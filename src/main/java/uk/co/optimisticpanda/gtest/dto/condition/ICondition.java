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
package uk.co.optimisticpanda.gtest.dto.condition;

/**
 * A check to see if a rule should fire or not
 * 
 * @author Andy Lee
 */
public interface ICondition {

	/**
	 * See if the rule is valid for this dto and index. This is used to
	 * determine if the effects of an edit should be applied.
	 * 
	 * @param <D>
	 *            type of dto.
	 * @param index
	 *            the index of this item.
	 * @param dataItem
	 *            the item that the rule may fire on.
	 * @return whether the edit should occur or not.
	 */
	<D> boolean isValid(int index, D dataItem);

}
