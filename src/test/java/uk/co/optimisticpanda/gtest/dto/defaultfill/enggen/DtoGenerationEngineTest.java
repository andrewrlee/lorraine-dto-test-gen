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
package uk.co.optimisticpanda.gtest.dto.defaultfill.enggen;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import junit.framework.TestCase;
import uk.co.optimisticpanda.gtest.dto.IDataEditor;
import uk.co.optimisticpanda.gtest.dto.RuleUtils;
import uk.co.optimisticpanda.gtest.dto.SimpleDataEditor;
import uk.co.optimisticpanda.gtest.dto.TestUtilsContext;
import uk.co.optimisticpanda.gtest.dto.defaultfill.IValueGenerator;
import uk.co.optimisticpanda.gtest.dto.defaultfill.defaultgens.ValueGeneratorFactory;
import uk.co.optimisticpanda.gtest.dto.defaultfill.enggen.visit.IEngineVisitor;
import uk.co.optimisticpanda.gtest.dto.defaultfill.enggen.visit.PrintVisitor;
import uk.co.optimisticpanda.gtest.dto.defaultfill.insgen.InstanceGenerator;
import uk.co.optimisticpanda.gtest.dto.defaultfill.insgen.InstanceGeneratorBuilder;
import uk.co.optimisticpanda.gtest.dto.rule.IRule;
import uk.co.optimisticpanda.gtest.dto.rulebuilder.impl.RuleFactory;
import uk.co.optimisticpanda.gtest.dto.test.utils.TestDto3;

/**
 * the type of dto to generate
 * 
 * @author Andy Lee
 */
public class DtoGenerationEngineTest extends TestCase {

	private DtoGenerationEngine<TestDto3> engine;
	private InstanceGenerator<TestDto3> generator;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		TestUtilsContext.useOgnl();
		InstanceGeneratorBuilder<TestDto3> builder = InstanceGeneratorBuilder.create(TestDto3.class);
		generator = builder.build();
		engine = new DtoGenerationEngine<TestDto3>(TestDto3.class, generator);
	}

	/**
	 * Test Generate
	 */
	public void testGenerate() {
		String expectedName = "GENVALUE";
		IValueGenerator<String> gen = ValueGeneratorFactory.createStringGenerator(expectedName);
		generator.registerAPropertyDepthGenerator("name", gen);
		engine.generate(assertNameVisitor(expectedName), 2);
	}

	/**
	 * Test Collect
	 */
	public void testCollect() {
		String expectedName = "GENVALUE";
		IValueGenerator<String> gen = ValueGeneratorFactory.createStringGenerator(expectedName);
		generator.registerAPropertyDepthGenerator("name", gen);
		List<TestDto3> generatedEntities = engine.collect(2);
		assertEquals(2, generatedEntities.size());
		assertEquals(expectedName, generatedEntities.get(0).getName());
		assertEquals(expectedName, generatedEntities.get(1).getName());
	}

	/**
	 * Test CollectAndVisit
	 */
	public void testCollectAndVisit() {
		String expectedName = "GENVALUE";
		IValueGenerator<String> gen = ValueGeneratorFactory.createStringGenerator(expectedName);
		generator.registerAPropertyDepthGenerator("name", gen);
		IEngineVisitor<TestDto3> visitor = appendIndexToDescriptionVisitor();
		List<TestDto3> generatedEntities = engine.collectAndVisit(visitor, 2);
		assertEquals(2, generatedEntities.size());
		assertEquals(expectedName + "-0", generatedEntities.get(0).getDescription());
		assertEquals(expectedName + "-1", generatedEntities.get(1).getDescription());
	}

	/**
	 * Test CollectAndEdit
	 */
	public void testCollectAndEdit() {
		String expectedName = "base";
		IValueGenerator<String> gen = ValueGeneratorFactory.createStringGenerator(expectedName);
		generator.registerAPropertyDepthGenerator("name", gen);
		
		IDataEditor<TestDto3> editor = getEditor(expectedName);
		List<TestDto3> generatedEntities = engine.collectAndEdit(editor, 2);
		assertEquals(2, generatedEntities.size());
		assertEquals("base0", generatedEntities.get(0).getName());
		assertEquals("base1", generatedEntities.get(1).getName());
	}

	/**
	 * Test CollectAndEdit
	 */
	public void testPrintVisitor() {
		String expectedName = "base";
		IValueGenerator<String> gen = ValueGeneratorFactory.createStringGenerator(expectedName);
		generator.registerAPropertyDepthGenerator("name", gen);
		
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		PrintStream printStream = new PrintStream(stream);
		
		IEngineVisitor<TestDto3> visitor = printVisitor(printStream);
		engine.generate(visitor, 2);
		String string = new String(stream.toByteArray());
		System.out.println(string);
	}
	
	private IEngineVisitor<TestDto3> appendIndexToDescriptionVisitor() {
		return new IEngineVisitor<TestDto3>() {
			@Override
			public void visit(int index, TestDto3 dto) {
				dto.setDescription(dto.getName() + "-" + index);
			}
		};
	}

	private IEngineVisitor<TestDto3> assertNameVisitor(final String expectedName) {
		return new IEngineVisitor<TestDto3>() {
			@Override
			public void visit(int index, TestDto3 dto) {
				assertEquals(expectedName, dto.getName());
			}
		};
	}

	private IEngineVisitor<TestDto3> printVisitor(PrintStream stream) {
		return new PrintVisitor<TestDto3>(stream);
	}
	
	private IDataEditor<TestDto3> getEditor(final String baseName) {
		RuleUtils<TestDto3> utils = new RuleUtils<TestDto3>();
		IRule<TestDto3> rule = RuleFactory.startRule(//
				utils.increment("name", baseName))//
				.where(utils.all()).build();
		return new SimpleDataEditor<TestDto3>().addRule(rule);
	}
	
}
