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

/**
 * A runtime exception that wraps checked and runtime exceptions thrown by
 * {@link IPropertyAccess}. This being thrown generally means that the property
 * does not exist, or that it cannot be set or read for some reason.
 * 
 * @author Andy Lee
 */
public class PropertyAccessException extends RuntimeException {

	/**
	 * @param message
	 *            an error message
	 * @param throwable
	 *            the exception to wrap
	 */
	public PropertyAccessException(String message, Throwable throwable) {
		super(message, throwable);
	}

	/**
	 * @param throwable
	 *            the exception to be wrapped
	 */
	public PropertyAccessException(Throwable throwable) {
		super(throwable);
	}

	/**
	 * @param message
	 *            an error message providing feedback for the user.
	 */
	public PropertyAccessException(String message) {
		super(message);
	}

}
