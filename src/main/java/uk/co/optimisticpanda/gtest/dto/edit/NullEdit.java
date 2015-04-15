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
  * An Edit that sets the specified property to null.
 * 
 * @param <D>
 *            The type of the dtos to be edited.
 * @author Andy Lee
 */
public class NullEdit<D> extends SetValueEdit<D> {
	
	/**
	 * Create an edit that always inserts null.
	 * @param context
	 */
	public NullEdit(Object context) {
		super(context, null);
	}

}
