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
package uk.co.optimisticpanda.gtest.dto.edit;

import java.util.List;

import uk.co.optimisticpanda.gtest.dto.propertyaccess.IPropertyAccess;
import uk.co.optimisticpanda.gtest.dto.propertyaccess.IPropertyAccessFactory;

/**
 * An Edit that will take the next value from a list and set it on a defined
 * property on the passed in data item.
 * 
 * @param <D>
 *            The type of the dtos to be edited.
 * @author Andy Lee
 */
public class IteratingCollectionEditor<D> extends AbstractEditor<D> {

	private final CycleBehaviour cycleBehavior;
	private final List<?> values;
	private final int START_VALUE = 0;
	private int valuesIndex;

	/**
	 * An enum that specifies the behaviour that this edit will exhibit when it
	 * runs out of data items to iterate through.
	 */
	public enum CycleBehaviour {
		/** After all elements have been used keep cycling through them */
		CYCLE,
		/** After all elements have been used, fill value with null */
		NULL_FILL,
		/** After all elements have been used, don't alter value */
		LEAVE_UNTOUCHED,
		/**
		 * After all elements have been used, if attempt to set value with next
		 * element throw an exception
		 */
		THROW_EXCEPTION;
	}

	/**
	 * Create an {@link Editor} that will, each time edit is called, set a
	 * specified property on the current dto to the next available value from a
	 * list.
	 * 
	 * The specified property is accessed via an {@link IPropertyAccess} which
	 * is created by passing in the context object to the currently configured
	 * {@link IPropertyAccessFactory}.
	 * 
	 * @param context
	 *            An object used for specifying which property to edit.
	 *            <p>
	 *            <i>This context is passed to the
	 *            {@link IPropertyAccessFactory} </i>
	 *            </p>
	 * 
	 * @param values
	 *            A list of values that will be used by this edit.
	 * @param cycleBehavior
	 *            The behaviour that this edit will exhibit after all values
	 *            have been used.
	 */
	public IteratingCollectionEditor(Object context, List<?> values, CycleBehaviour cycleBehavior) {
		super(context);
		this.values = values;
		this.cycleBehavior = cycleBehavior;
		this.valuesIndex = START_VALUE;
	}

	/**
	 * @see uk.co.optimisticpanda.gtest.dto.edit.Editor#edit(int,
	 *      java.lang.Object)
	 */
	@Override
	public void edit(int notUsed, Object dataItem) {
		if (valuesIndex < values.size()) {
			setValue(dataItem, values.get(valuesIndex));
			valuesIndex++;
			return;
		}
		switch (cycleBehavior) {
		case CYCLE:
			setValue(dataItem, values.get(0));
			valuesIndex = START_VALUE + 1;
			return;
		case NULL_FILL:
			setValue(dataItem, null);
			return;
		case LEAVE_UNTOUCHED:
			return;
		case THROW_EXCEPTION:
			throw new IndexOutOfBoundsException("There are " + values.size() + " and already used all of them");
		default:
			throw new IllegalStateException("Cannot deal with CycleBehaviour with value of:" + cycleBehavior.name());
		}
	}

	/**
	 * @return a human readable representation of the changes that are to take
	 *         place.
	 */
	@Override
	public String toString() {
		return "ITERATING COLLECTION [SIZE:" + values.size() + " BEHAVIOUR:" + cycleBehavior.name() + "]";
	}
}
