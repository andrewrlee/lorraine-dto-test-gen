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
package uk.co.optimisticpanda.gtest.dto.propertyaccess.ognl;

import ognl.Ognl;
import ognl.OgnlContext;
import ognl.OgnlException;
import uk.co.optimisticpanda.gtest.dto.TestUtilsContext;
import uk.co.optimisticpanda.gtest.dto.propertyaccess.IPropertyAccess;
import uk.co.optimisticpanda.gtest.dto.propertyaccess.PropertyAccessException;

class OgnlPropertyAccess implements IPropertyAccess {

	private Object expression;
	private OgnlContext context;

	public OgnlPropertyAccess(String expressionString) {
		super();
		try {
			expression = Ognl.parseExpression(expressionString);
			context = TestUtilsContext.getOgnlContext();
		} catch (OgnlException e) {
			throw new RuntimeException(e);
		}
	}

	public Object getExpression() {
		return expression;
	}

	@Override
	public Object getValue(Object rootObject) throws PropertyAccessException {
		try {
			return Ognl.getValue(getExpression(), context, rootObject);
		} catch (OgnlException e) {
			throw new PropertyAccessException("Problem parsing expression:", e);
		}
	}

	@Override
	public void setValue(Object rootObject, Object value) throws PropertyAccessException {
		try {
			Ognl.setValue(getExpression(), context, rootObject, value);
		} catch (OgnlException e) {
			throw new PropertyAccessException("Problem parsing expression:", e);
		}
	}

}
