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

import uk.co.optimisticpanda.gtest.dto.condition.Condition;
import uk.co.optimisticpanda.gtest.dto.condition.Conditions;
import uk.co.optimisticpanda.gtest.dto.edit.Editor;
import uk.co.optimisticpanda.gtest.dto.rule.BaseEdit;
import uk.co.optimisticpanda.gtest.dto.rule.Edit;
import uk.co.optimisticpanda.gtest.dto.rule.LabeledEdit;
import uk.co.optimisticpanda.gtest.dto.rulebuilder.fluent.IEndBuilder;
import uk.co.optimisticpanda.gtest.dto.rulebuilder.fluent.IWhereBuilder;

public class Edits implements IWhereBuilder, IEndBuilder {

	private Editor editor;
	private Condition condition;
	private String label;
	
	public static Edits doThis(Editor editor){
		return new Edits(editor);
	} 
	
	private Edits(Editor editor) {
		this.editor = editor;
	}

	public Edits and(Editor otherEditor) {
		this.editor = this.editor.and(otherEditor);
		return this;
	}
	
	@Override
	public IEndBuilder where(Condition condition) {
		this.condition = condition;
		return this;
	}
	
	public IEndBuilder inEveryCase() {
		this.condition = Conditions.always();
		return this;
	}

	@Override
	public IEndBuilder setLabel(String label) {
		this.label = label;
		return this;
	}

	@Override
	public <D> Edit<D> forTheType(Class<D> clazz) {
		BaseEdit<D> rule = new BaseEdit<D>(editor, condition);
		if(this.label != null){
			rule = new LabeledEdit<D>(this.label, rule);
		}
		return rule;
	}

}
