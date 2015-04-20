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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *This visitor allows multiple visitors to visit the same item at the same type
 * 
 * @param <D>
 * @author Andy Lee
 */
public class CombinedVisitor<D> implements IEngineVisitor<D> {

	List<IEngineVisitor<D>> visitors;

	/**
	 * Create a new combined visitor
	 */
	public CombinedVisitor() {
		this.visitors = new ArrayList<IEngineVisitor<D>>();
	}

	/**
	 * Create a new combined visitor
	 * 
	 * @param visitor
	 */
	public CombinedVisitor(IEngineVisitor<D> visitor) {
		this();
		this.visitors.add(visitor);
	}

	public CombinedVisitor(IEngineVisitor<D>... visitor) {
		this();
		this.visitors.addAll(Arrays.<IEngineVisitor<D>>asList(visitor));
	}

	/**
	 * Adds a new visitor
	 * 
	 * @param visitor
	 * @return this for chaining
	 */
	public CombinedVisitor<D> add(IEngineVisitor<D> visitor) {
		visitors.add(visitor);
		return this;
	}

	/**
	 * @see uk.co.optimisticpanda.gtest.dto.defaultfill.enggen.visit.IEngineVisitor#visit(int,
	 *      java.lang.Object)
	 */
	@Override
	public void visit(int index, D dto) {
		visitors.forEach(v -> v.visit(index, dto));
	}

}
