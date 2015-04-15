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
package uk.co.optimisticpanda.gtest.dto.defaultfill.enggen.visit;

import uk.co.optimisticpanda.gtest.dto.defaultfill.enggen.DtoGenerationEngine;

/**
 * A visitor for use with the {@link DtoGenerationEngine}.
 * 
 * @param <D>
 *            the type of dto to visit
 * @author Andy Lee
 */
public interface IEngineVisitor<D> {

	/**
	 * @param index
	 *            the index of this object. Or the number of times this visitor
	 *            has visited a dto in the trip
	 * @param dto
	 *            the dto to visit
	 */
	public abstract void visit(int index, D dto);

}
