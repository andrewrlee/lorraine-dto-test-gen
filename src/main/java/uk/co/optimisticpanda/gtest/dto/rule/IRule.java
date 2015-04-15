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

/**
 * An interface that encapsulates what change should occur and when it
 * under what conditions the change should occur.
 * 
 * @param <D>
 *            the type of the dto that this rule is applicable for.
 * @author Andy Lee
 */
public interface IRule<D> {
    
    /**
     * The change that should occur to the passed in data item.
     * @param index the index of the data item that can be used in the edit. 
     * @param data the data item.
     */
    public void edit(int index, D data );

    /**
     * @param index The index of the data item.
     * @param dataItem The data item that is being checked.
     * @return true if the rule should be applied to this rule.
     */
    public boolean isValid(int index, D dataItem );
    
}
