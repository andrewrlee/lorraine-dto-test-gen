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
package uk.co.optimisticpanda.gtest.dto.condition;

/**
 * A condition that returns true on every nth item.
 * @author Andy Lee
 */
public class NthPlaceCondition implements ICondition{
    private final int n;

    /**
     * Creates a new condition for every nth data item.
     * @param n 
     */
    public NthPlaceCondition(int n){
        this.n = n;
    }
    
    /**
     * @see uk.co.optimisticpanda.gtest.dto.condition.ICondition#isValid(int, java.lang.Object)
     */
    @Override
    public <D> boolean isValid(int index, D dataItem) {
        return (index %n  == 0);
    }
    
    /**
	 * A human readable representation of this {@link ICondition}.
	 */
	@Override
    public String toString() {
    	return "NTH PLACE [n='"+ n + "']";
    }
}
