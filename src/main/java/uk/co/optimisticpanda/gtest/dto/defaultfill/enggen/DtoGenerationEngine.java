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
package uk.co.optimisticpanda.gtest.dto.defaultfill.enggen;

import java.util.List;

import uk.co.optimisticpanda.gtest.dto.IDataEditor;
import uk.co.optimisticpanda.gtest.dto.defaultfill.enggen.visit.CombinedVisitor;
import uk.co.optimisticpanda.gtest.dto.defaultfill.enggen.visit.DataEditorVisitor;
import uk.co.optimisticpanda.gtest.dto.defaultfill.enggen.visit.DoNothingVisitor;
import uk.co.optimisticpanda.gtest.dto.defaultfill.enggen.visit.IEngineVisitor;
import uk.co.optimisticpanda.gtest.dto.defaultfill.enggen.visit.ListVisitor;
import uk.co.optimisticpanda.gtest.dto.defaultfill.insgen.InstanceGenerator;

/**
 * @param <D>
 *            the type of dto to generate
 * @author Andy Lee
 */
public class DtoGenerationEngine<D> {

	private final InstanceGenerator<D> generator;
	private final Class<D> clazz;

	/**
	 * Create a new Dto Generation Engine.
	 * 
	 * @param clazz
	 * @param generator
	 */
	public DtoGenerationEngine(Class<D> clazz, InstanceGenerator<D> generator) {
		this.clazz = clazz;
		this.generator = generator;
	}

	/**
	 * Generates a number of dtos and applies a visitor to them.
	 * 
	 * @param visitor
	 * @param numberToCreate
	 */
	public void generate(IEngineVisitor<D> visitor, int numberToCreate) {
		for (int i = 0; i < numberToCreate; i++) {
			D dto = generator.generate();
			visitor.visit(i, dto);
		}
	}

	/**
	 * @param numberToCreate
	 * @return the list of generated dtos
	 */
	public List<D> collect(int numberToCreate) {
		return collectAndVisit(new DoNothingVisitor<D>(), numberToCreate);
	}

	/**
	 * Returns all generated dtos and applies the passed in visitor.
	 * 
	 * @param visitor
	 * @param numberToCreate
	 * @return a list of generated dtos
	 */
	public List<D> collectAndVisit(IEngineVisitor<D> visitor, int numberToCreate) {
		ListVisitor<D> listVisitor = new ListVisitor<D>(clazz);
		CombinedVisitor<D> combinedVisitor = new CombinedVisitor<D>(visitor)// 
				.add(listVisitor); //
		generate(combinedVisitor, numberToCreate);
		return listVisitor.getDtos();
	}

	/**
	 * Returns all generated dtos after editing them with the passed in
	 * {@link IDataEditor}.
	 * 
	 * @param numberToCreate
	 * @param editor
	 * @return a list of generated dtos
	 */
	public List<D> collectAndEdit(IDataEditor<D> editor, int numberToCreate) {
		return collectAndVisit(new DataEditorVisitor<D>(editor), numberToCreate);
	}
}
