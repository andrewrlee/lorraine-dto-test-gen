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

import static org.assertj.core.api.Assertions.assertThat;
import static uk.co.optimisticpanda.gtest.dto.condition.Conditions.always;
import static uk.co.optimisticpanda.gtest.dto.edit.Editors.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.function.Supplier;

import junit.framework.TestCase;
import uk.co.optimisticpanda.gtest.dto.IDataEditor;
import uk.co.optimisticpanda.gtest.dto.SimpleDataEditor;
import uk.co.optimisticpanda.gtest.dto.TestUtilsContext;
import uk.co.optimisticpanda.gtest.dto.defaultfill.DefaultValueGenerator;
import uk.co.optimisticpanda.gtest.dto.defaultfill.ValueGenerator;
import uk.co.optimisticpanda.gtest.dto.defaultfill.enggen.visit.IEngineVisitor;
import uk.co.optimisticpanda.gtest.dto.defaultfill.enggen.visit.PrintVisitor;
import uk.co.optimisticpanda.gtest.dto.defaultfill.insgen.InstanceGenerator;
import uk.co.optimisticpanda.gtest.dto.rule.Edit;
import uk.co.optimisticpanda.gtest.dto.rulebuilder.impl.Edits;
import uk.co.optimisticpanda.gtest.dto.test.utils.TestDto3;
/**
 * the type of dto to generate
 * 
 * @author Andy Lee
 */
public class DtoGenerationEngineTest extends TestCase {

	private DtoGenerationEngine<TestDto3> engine;
	private InstanceGenerator<TestDto3> generator;
	private ValueGenerator valueGenerator;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		TestUtilsContext.useOgnl();
		valueGenerator = new DefaultValueGenerator();
		generator = InstanceGenerator.of(TestDto3.class).useCache(valueGenerator).build();
		engine = new DtoGenerationEngine<TestDto3>(generator);
	}

	/**
	 * Test Generate
	 */
	public void testGenerate() {
		String expectedName = "GENVALUE";
		valueGenerator.registerAPropertyDepthGenerator("name", () -> expectedName);
		engine.generate(assertNameVisitor(expectedName), 2);
	}

	/**
	 * Test Collect
	 */
	public void testCollect() {
		String expectedName = "GENVALUE";
		Supplier<String> gen = () -> expectedName;
		valueGenerator.registerAPropertyDepthGenerator("name", gen);
		List<TestDto3> generatedEntities = engine.collect(2);
		assertThat(generatedEntities).hasSize(2);
		assertThat(generatedEntities.get(0).getName()).isEqualTo(expectedName);
		assertThat(generatedEntities.get(1).getName()).isEqualTo(expectedName);
	}

	/**
	 * Test CollectAndVisit
	 */
	public void testCollectAndVisit() {
		String expectedName = "GENVALUE";
		Supplier<String> gen = () -> expectedName;
		valueGenerator.registerAPropertyDepthGenerator("name", gen);
		IEngineVisitor<TestDto3> visitor = appendIndexToDescriptionVisitor();
		List<TestDto3> generatedEntities = engine.collectAndVisit(visitor, 2);
		assertThat(generatedEntities).hasSize(2);
		assertThat(generatedEntities.get(0).getDescription()).isEqualTo(expectedName + "-0");
		assertThat(generatedEntities.get(1).getDescription()).isEqualTo(expectedName + "-1");
	}

	/**
	 * Test CollectAndEdit
	 */
	public void testCollectAndEdit() {
		String expectedName = "base";
		Supplier<String> gen = () -> expectedName;
		valueGenerator.registerAPropertyDepthGenerator("name", gen);
		
		IDataEditor<TestDto3> editor = getEditor(expectedName);
		List<TestDto3> generatedEntities = engine.collectAndEdit(editor, 2);
		assertThat(generatedEntities).hasSize(2);
		assertThat(generatedEntities.get(0).getName()).isEqualTo("base0");
		assertThat(generatedEntities.get(1).getName()).isEqualTo("base1");
	}

	/**
	 * Test CollectAndEdit
	 */
	public void testPrintVisitor() {
		String expectedName = "base";
		valueGenerator.registerAPropertyDepthGenerator("name", () -> expectedName);
		
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
				assertThat(dto.getName()).isEqualTo(expectedName);
			}
		};
	}

	private IEngineVisitor<TestDto3> printVisitor(PrintStream stream) {
		return new PrintVisitor<TestDto3>(stream);
	}
	
	private IDataEditor<TestDto3> getEditor(final String baseName) {
		Edit<TestDto3> edit = Edits.doThis(//
				incrementEach("name").withBase(baseName))//
				.where(always()).forTheType(TestDto3.class);
		return SimpleDataEditor.<TestDto3>create().add(edit);
	}
}
