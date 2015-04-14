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

import uk.co.optimisticpanda.gtest.dto.condition.ICondition;
import uk.co.optimisticpanda.gtest.dto.edit.IEdit;


/**
 * This wraps a {@link BaseRule} to provide a description for the rule
 * @param <D> the type that is applicable for this rule 
 * @author Andy Lee
 */
public class LabeledRule<D> extends BaseRule<D>{

	private final String label;

	/**
	 * Creates a rule with a descriptive label.
	 * @param label a label that describes the rule
	 * @param edit the edit that occurs when the rule fires 
	 * @param conditions the conditions that determine if the rule should fire
	 */
	public LabeledRule(String label,IEdit<D> edit, ICondition[] conditions) {
		super(edit, conditions);
		this.label = label;
	}
	
	/**
	 * Creates a labeled rule which wraps a base rule
	 * @param label a label that describes the rule
	 * @param rule a base rule to wrap
	 */
	public LabeledRule(String label, BaseRule<D> rule){
		this(label, rule.edit, rule.conditions);
		
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
