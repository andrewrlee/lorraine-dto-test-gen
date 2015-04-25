package uk.co.optimisticpanda.gtest.dto.defaultfill.enggen;

import java.util.List;
import java.util.stream.IntStream;

import uk.co.optimisticpanda.gtest.dto.IDataEditor;
import uk.co.optimisticpanda.gtest.dto.defaultfill.enggen.visit.CombinedVisitor;
import uk.co.optimisticpanda.gtest.dto.defaultfill.enggen.visit.DataEditorVisitor;
import uk.co.optimisticpanda.gtest.dto.defaultfill.enggen.visit.DoNothingVisitor;
import uk.co.optimisticpanda.gtest.dto.defaultfill.enggen.visit.IEngineVisitor;
import uk.co.optimisticpanda.gtest.dto.defaultfill.enggen.visit.ListVisitor;
import uk.co.optimisticpanda.gtest.dto.defaultfill.insgen.InstanceGenerator;

/**
 * @param <D>
 *            the type of dto to generate
 * @author Andy Lee
 */
public class DtoGenerationEngine<D> {

	private final InstanceGenerator<D> generator;

	/**
	 * Create a new Dto Generation Engine.
	 * 
	 * @param clazz
	 * @param generator
	 */
	public DtoGenerationEngine(InstanceGenerator<D> generator) {
		this.generator = generator;
	}

	/**
	 * Generates a number of dtos and applies a visitor to them.
	 * 
	 * @param visitor
	 * @param numberToCreate
	 */
	public void generate(IEngineVisitor<D> visitor, int numberToCreate) {
		IntStream.range(0, numberToCreate).forEach(i -> {
			D dto = generator.generate();
			visitor.visit(i, dto);
		});
	}

	/**
	 * @param numberToCreate
	 * @return the list of generated dtos
	 */
	public List<D> collect(int numberToCreate) {
		return collectAndVisit(new DoNothingVisitor<D>(), numberToCreate);
	}

	/**
	 * Returns all generated dtos and applies the passed in visitor.
	 * 
	 * @param visitor
	 * @param numberToCreate
	 * @return a list of generated dtos
	 */
	public List<D> collectAndVisit(IEngineVisitor<D> visitor, int numberToCreate) {
		ListVisitor<D> listVisitor = new ListVisitor<D>();
		CombinedVisitor<D> combinedVisitor = new CombinedVisitor<D>(visitor, listVisitor);
		generate(combinedVisitor, numberToCreate);
		return listVisitor.getDtos();
	}

	/**
	 * Returns all generated dtos after editing them with the passed in
	 * {@link IDataEditor}.
	 * 
	 * @param numberToCreate
	 * @param editor
	 * @return a list of generated dtos
	 */
	public List<D> collectAndEdit(IDataEditor<D> editor, int numberToCreate) {
		return collectAndVisit(new DataEditorVisitor<D>(editor), numberToCreate);
	}
}
