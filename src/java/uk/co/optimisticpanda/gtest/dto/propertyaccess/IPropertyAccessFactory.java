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
package uk.co.optimisticpanda.gtest.dto.propertyaccess;

import uk.co.optimisticpanda.gtest.dto.TestUtilsContext;
import uk.co.optimisticpanda.gtest.dto.condition.ICondition;
import uk.co.optimisticpanda.gtest.dto.edit.IEdit;
import uk.co.optimisticpanda.gtest.dto.propertyaccess.ognl.OgnlPropertyAccessFactory;
import uk.co.optimisticpanda.gtest.dto.propertyaccess.rflc.ReflectionPropertyAccessFactory;

/**
 * <p>
 * A Factory that creates new {@link IPropertyAccess} instances.<br>
 * These are used by objects that extend {@link PropertyAccessSupport} such as
 * the built in {@link IEdit}s and {@link ICondition}es for introspection purposes.
 * </p>
 * <p>
 * The factory instance that will be used by the classes that extend
 * {@link PropertyAccessSupport} can be specified by
 * {@link TestUtilsContext#setPropertyAccessFactory(IPropertyAccessFactory)}
 * </p>
 * 
 * See {@link OgnlPropertyAccessFactory} or
 * {@link ReflectionPropertyAccessFactory} for example implementations.
 * 
 * @author Andy Lee
 */
public interface IPropertyAccessFactory {

	/**
	 * Return an instance of a {@link IPropertyAccess} Object.
	 * 
	 * @param context
	 *            An object that provides information on how to represent the
	 *            specific property.<br/>
	 *            In ognl this would be the ognl expression as a string
	 * @return a new IPropertyAccess.
	 */
	IPropertyAccess createPropertyAccess(Object context);

}
