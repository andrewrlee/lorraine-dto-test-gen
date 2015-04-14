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
package uk.co.optimisticpanda.gtest.dto;

import ognl.DefaultMemberAccess;
import ognl.OgnlContext;
import uk.co.optimisticpanda.gtest.dto.propertyaccess.IPropertyAccessFactory;
import uk.co.optimisticpanda.gtest.dto.propertyaccess.PropertyAccessSupport;
import uk.co.optimisticpanda.gtest.dto.propertyaccess.ognl.OgnlPropertyAccessFactory;
import uk.co.optimisticpanda.gtest.dto.propertyaccess.rflc.ReflectionPropertyAccessFactory;

/**
 * Context Singleton that stores and allows the injection of configuration
 * information.
 * 
 * @author Andy Lee
 */
public enum TestUtilsContext {
	/**
	 * The singleton instance
	 */
	INSTANCE;

	private static IPropertyAccessFactory propertyAccessFactory;
	private static OgnlContext ognlContext;

	private TestUtilsContext() {
		//empty constructor
	}

	/**
	 * Helper method that configures the {@link PropertyAccessSupport} to use
	 * {@link OgnlPropertyAccessFactory}
	 */
	public static void useOgnl() {
		clearContext();
		OgnlContext context = new OgnlContext();
		context.setMemberAccess(new DefaultMemberAccess(true));
		setOgnlContext(context);
		setPropertyAccessFactory(new OgnlPropertyAccessFactory());
	}

	/**
	 * Helper method that configures the {@link PropertyAccessSupport} to use
	 * {@link ReflectionPropertyAccessFactory}.
	 */
	public static void useReflection() {
		clearContext();
		setPropertyAccessFactory(new ReflectionPropertyAccessFactory());
	}

	/**
	 * Clear out this context.
	 * */
	public static void clearContext() {
		setOgnlContext(null);
		setPropertyAccessFactory(null);
	}

	/**
	 * Set the {@link IPropertyAccessFactory} that is to be used by classes
	 * extending {@link PropertyAccessSupport}.
	 * 
	 * @param propertyAccessFactory
	 * 
	 * */
	public static void setPropertyAccessFactory(IPropertyAccessFactory propertyAccessFactory) {
		TestUtilsContext.propertyAccessFactory = propertyAccessFactory;
	}

	/**
	 * Set the OgnlContext that is to be used by the
	 * {@link OgnlPropertyAccessFactory}. The Default OgnlContext is configured
	 * to allow access to private fields and this may not suit all needs.
	 * 
	 * @param context
	 */
	public static void setOgnlContext(OgnlContext context) {
		TestUtilsContext.ognlContext = context;
	}

	/**
	 * @return Currently configured Property Access Factory
	 */
	public static IPropertyAccessFactory getPropertyAccessFactory() {
		checkInitialisedCorrectly();
		return propertyAccessFactory;
	}

	private static void checkInitialisedCorrectly() {
		if (propertyAccessFactory == null) {
			throw new IllegalStateException(
					"Test Utils Context is not initialised correctly. Call TestUtils.useOgnl() or TestUtils.useReflection() before using.");
		}
	}

	/**
	 * @return the currently configured OgnlContext
	 */
	public static OgnlContext getOgnlContext() {
		checkInitialisedCorrectly();
		return ognlContext;
	}

}
