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

/**
 * A change to occur to a dto. This is seperate from whether the change should
 * take place.
 * 
 * @author Andy Lee
 * @param <D>
 *            The type of the dto being edited.
 * 
 */
public interface IEdit<D> {

	/**
	 * Apply the effects of the edit.
	 * 
	 * @param index
	 *            The index of the item to be edited
	 * @param dataItem
	 *            The dto to be edited
	 */
	public void edit(int index, D dataItem);

}
