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
package uk.co.optimisticpanda.gtest.dto.defaultfill.insgen;

/**
 * A runtime exception that wraps checked and runtime exceptions thrown by
 * InstanceGenerator. This being thrown generally indicates some form of
 * reflection related exception- for instance no default constructor, fields not
 * existing etc.
 * 
 * @author Andy Lee
 */
@SuppressWarnings("serial")
public class InstanceGeneratorException extends RuntimeException {

	/**
	 * @param path
	 *            the current place in the object
	 * @param clazz
	 *            the class that failed to be instantiated.
	 * @param throwable
	 *            the throwable exception that occurred
	 */
	public InstanceGeneratorException(String path, Class<?> clazz, Throwable throwable) {
		super(createInstanceCreationMessage(path, clazz, throwable), throwable);
	}

	/**
	 * @param throwable
	 *            the exception to be wrapped
	 */
	public InstanceGeneratorException(Throwable throwable) {
		super(throwable);
	}

	/**
	 * Create a programmer readable message for instance creation exceptions
	 * 
	 * @param path
	 *            the current place in the object
	 * @param clazz
	 *            the class that failed to be instantiated.
	 * @param e
	 *            the throw-able exception that occurred
	 * @return the message
	 */
	public static String createInstanceCreationMessage(String path, Class<?> clazz, Throwable e) {
		StringBuilder builder = new StringBuilder();
		builder.append("Error Creating Instance:\n");
		builder.append("Path:" + path + "\n");
		builder.append("Class to create:" + clazz.getCanonicalName() + "\n");
		builder.append("ExceptionType:" + e.getClass() + "\n");
		builder.append("Message:" + e.getMessage() + "\n");
		return builder.toString();
	}

}
