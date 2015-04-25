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

import uk.co.optimisticpanda.gtest.dto.IDataEditor;

/**
 * A Visitor that uses the passed in {@link IDataEditor} to edit the dtos that it visits
 * 
 * @param <D>
 * @author Andy Lee
 */
public class DataEditorVisitor<D> implements IEngineVisitor<D> {

	private final IDataEditor<D> editor;

	public DataEditorVisitor(IDataEditor<D> editor) {
		this.editor = editor;
	}

	@Override
	public void visit(int index, D dto) {
		editor.edit(index, dto);
	}

}
