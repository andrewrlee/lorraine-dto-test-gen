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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This edit encapsulates the effects of a collection of edits
 * 
 * @param <D>
 *            the type of the dto being edited.
 * @author Andy Lee
 */
public class CombinedEdit<D> implements IEdit<D> {

	private final List<IEdit<D>> edits;

	/**
	 * Creates an edit that does not perform any editing but can be expanded at
	 * a later time.
	 */
	public CombinedEdit() {
		this(new ArrayList<IEdit<D>>());
	}

	/**
	 * Wraps an edit providing it with combined edit functionality.
	 * 
	 * @param edit
	 *            the edit to wrap
	 */
	public CombinedEdit(IEdit<D> edit) {
		edits = new ArrayList<IEdit<D>>();
		if (edit != null) {
			edits.add(edit);
		}
	}

	/**
	 * Creates a CombinedEdit from a list of {@link IEdit}s.
	 * 
	 * @param edits
	 *            a list of edits to be combined
	 */
	public CombinedEdit(List<IEdit<D>> edits) {
		if (edits != null) {
			this.edits = edits;
		} else {
			this.edits = new ArrayList<IEdit<D>>();
		}
	}

	/**
	 * @param edit
	 *            the edit to be included
	 * @return this to allow chaining
	 */
	public CombinedEdit<D> addEdit(IEdit<D> edit) {
		edits.add(edit);
		return this;
	}

	/**
	 * Apply the effects of all contained edits
	 * 
	 * @param index
	 *            the index of this item.
	 * @param dataItem
	 *            the dto to be edited.
	 * */
	@Override
	public void edit(int index, D dataItem) {
		for (IEdit<D> edit : edits) {
			edit.edit(index, dataItem);
		}
	}

	/**
	 * @return a human readable representation of the changes that are to take
	 *         place.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String toString() {
		Iterator iterator = edits.iterator();

		StringBuilder builder = new StringBuilder(iterator.next().toString());
		while (iterator.hasNext()) {
			builder.append(" AND " + iterator.next());
		}
		return builder.toString();
	}
}
