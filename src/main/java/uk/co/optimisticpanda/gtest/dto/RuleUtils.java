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

import uk.co.optimisticpanda.gtest.dto.condition.ICondition;
import uk.co.optimisticpanda.gtest.dto.condition.NthPlaceCondition;
import uk.co.optimisticpanda.gtest.dto.edit.Editor;
import uk.co.optimisticpanda.gtest.dto.edit.IncrementingNameEditor;
import uk.co.optimisticpanda.gtest.dto.edit.IteratingCollectionEditor;
import uk.co.optimisticpanda.gtest.dto.edit.IteratingCollectionEditor.CycleBehaviour;
import uk.co.optimisticpanda.gtest.dto.edit.SetValueEditor;
import uk.co.optimisticpanda.gtest.dto.propertyaccess.IPropertyAccessFactory;

/**
 * A class that provides easy access to various built-in matches and edits. To
 * preserve type safety, a {@link RuleUtils} will need to be created for each data type
 * a rule is being made for.
 * 
 * @param <D>
 *            The data type of the item that the rule is being made for.
 * @author Andy Lee
 */
public class RuleUtils {

	/**
	 * Create a new instance of a rules utils.
	 */
	public RuleUtils() {
		//create new instance
	}
	/**
	 * @param n
	 * @return a new {@link NthPlaceCondition} instance.
	 */
	public ICondition nth(int n) {
		return new NthPlaceCondition(n);
	}

	// EDITS

	/**
	 * @param context
	 *            the context passed to the {@link IPropertyAccessFactory}
	 * @param baseNameValue
	 *            the string label that will used as the prefix.
	 * @return a new {@link IncrementingNameEditor} instance.
	 */
	public <D> Editor<D> increment(Object context, String baseNameValue) {
		return new IncrementingNameEditor<D>(context, baseNameValue);
	}

	/**
	 * @param context
	 *            the context passed to the {@link IPropertyAccessFactory}
	 * @param values
	 *            a list of values that will be used by the edit
	 * @param cycleBehaviour
	 *            a value of {@link CycleBehaviour} that determines how the edit
	 *            will behave when reaches the end of its list of values
	 * @return a new {@link IteratingCollectionEditor} instance.
	 */
	public <D> Editor<D> iterate(Object context, List<?> values, CycleBehaviour cycleBehaviour) {
		return new IteratingCollectionEditor<D>(context, values, cycleBehaviour);
	}

	/**
	 * @param context
	 *            the context passed to the {@link IPropertyAccessFactory} that
	 *            will indicate one property on the item that this rule is
	 *            applicable for.
	 * @param value
	 *            the value to set
	 * @return a new {@link SetValueEditor} instance.
	 */
	public <D> Editor<D> set(Object context, Object value) {
		return new SetValueEditor<D>(context, value);
	}
}
