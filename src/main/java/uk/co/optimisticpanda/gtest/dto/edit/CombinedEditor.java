package uk.co.optimisticpanda.gtest.dto.edit;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * An editor that encapsulates the effects of a collection of edits
 * 
 * @param <D>
 *            the type of the dto being edited.
 * @author Andy Lee
 */
public class CombinedEditor<D> implements Editor<D> {

	private final List<Editor<D>> edits;

	/**
	 * Creates an edit that does not perform any editing but can be expanded at
	 * a later time.
	 */
	public CombinedEditor() {
		this(new ArrayList<Editor<D>>());
	}

	/**
	 * Wraps an edit providing it with combined edit functionality.
	 * 
	 * @param edit
	 *            the edit to wrap
	 */
	public CombinedEditor(Editor<D> edit) {
		edits = new ArrayList<Editor<D>>();
		if (edit != null) {
			edits.add(edit);
		}
	}

	/**
	 * Creates a CombinedEdit from a list of {@link Editor}s.
	 * 
	 * @param edits
	 *            a list of edits to be combined
	 */
	public CombinedEditor(List<Editor<D>> edits) {
		if (edits != null) {
			this.edits = edits;
		} else {
			this.edits = new ArrayList<Editor<D>>();
		}
	}

	/**
	 * @param edit
	 *            the edit to be included
	 * @return this to allow chaining
	 */
	public CombinedEditor<D> addEdit(Editor<D> edit) {
		edits.add(edit);
		return this;
	}

	/**
	 * Apply the effects of all contained edits
	 * 
	 * @param index
	 *            the index of this item.
	 * @param dataItem
	 *            the dto to be edited.
	 * */
	@Override
	public void edit(int index, D dataItem) {
		for (Editor<D> edit : edits) {
			edit.edit(index, dataItem);
		}
	}

	/**
	 * @return a human readable representation of the changes that are to take
	 *         place.
	 */
	@Override
	public String toString() {
		Iterator<Editor<D>> iterator = edits.iterator();

		StringBuilder builder = new StringBuilder(iterator.next().toString());
		while (iterator.hasNext()) {
			builder.append(" AND " + iterator.next());
		}
		return builder.toString();
	}
}
