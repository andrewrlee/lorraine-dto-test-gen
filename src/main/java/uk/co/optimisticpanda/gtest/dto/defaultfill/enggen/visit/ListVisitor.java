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
import java.util.Collections;
import java.util.List;

/**
 *A Visitor that collects all visited dtos and allows access to them.
 * 
 * @param <D>
 * @author Andy Lee
 */
public class ListVisitor<D> implements IEngineVisitor<D> {

	private List<D> dtos;

	public ListVisitor() {
		this.dtos = new ArrayList<>();
	}

	@Override
	public void visit(int index, D dto) {
		dtos.add(dto);
	}

	public List<D> getDtos() {
		return Collections.unmodifiableList(dtos);
	}

}
