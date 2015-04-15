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
package uk.co.optimisticpanda.gtest.dto.defaultfill.enggen.visit;

import java.io.PrintStream;

/**
 *A visitor that just prints out the visited dto
 * @param <D> the type of dto to visit
 * @author Andy Lee
 */
public class PrintVisitor<D> implements IEngineVisitor<D>{

	private PrintStream stream;

	/**
	 * Print out toString of passed in dto
	 * @param stream the stream to output to
	 */
	public PrintVisitor(PrintStream stream){
		this.stream = stream;
		if (stream == null){
			this.stream = System.out;
		}
	}

	/**
	 * Print out toString of passed in dto to {@link System#out}
	 */
	public PrintVisitor(){
		this(null);
	}

	
	/**
	 * @see uk.co.optimisticpanda.gtest.dto.defaultfill.enggen.visit.IEngineVisitor#visit(int, java.lang.Object)
	 */
	@Override
	public void visit(int index, D dto) {
		stream.println(dto);
	}

}
