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
package uk.co.optimisticpanda.gtest.dto.rule;

import uk.co.optimisticpanda.gtest.dto.condition.Condition;
import uk.co.optimisticpanda.gtest.dto.edit.Editor;


/**
 * This decorates a {@link BaseEdit} to provide a description for the rule
 * @param <D> the type that is applicable for this rule 
 * @author Andy Lee
 */
public class LabeledEdit<D> extends BaseEdit<D>{

	private final String label;

	/**
	 * Creates a rule with a descriptive label.
	 * @param label a label that describes the rule
	 * @param edit the edit that occurs when the rule fires 
	 * @param conditions the conditions that determine if the rule should fire
	 */
	private LabeledEdit(String label, Editor edit, Condition condition) {
		super(edit, condition);
		this.label = label;
	}
	
	/**
	 * Creates a labeled rule which wraps a base rule
	 * @param label a label that describes the rule
	 * @param edit a base rule to wrap
	 */
	public LabeledEdit(String label, BaseEdit<D> edit){
		super(edit.edit, edit.conditions);
		this.label = label;
		
	}

	/**
	 * @return a description of the rule
	 */
	public String getLabel() {
		return label;
	}
	
	/**
	 * A string representation of the rule.
	 */
	@Override
	public String toString() {
		return label + " [" + super.toString() + "]";
	}

}
