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
package uk.co.optimisticpanda.gtest.dto.rulebuilder.impl;

import uk.co.optimisticpanda.gtest.dto.edit.CombinedEdit;
import uk.co.optimisticpanda.gtest.dto.edit.IEdit;

class EditBuilder<D> {

	private final CombinedEdit<D> edit;

	public EditBuilder(IEdit<D> edit ) {
		if(edit == null){
			throw new IllegalArgumentException("Edit must not be null");
		}
		this.edit = new CombinedEdit<D>(edit);
	}

	public void and(IEdit<D> andEdit){
		this.edit.addEdit(andEdit);
	}

	public IEdit<D> build(){
		return this.edit;
	}
}
