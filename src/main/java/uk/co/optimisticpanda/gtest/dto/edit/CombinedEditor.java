package uk.co.optimisticpanda.gtest.dto.edit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * An editor that encapsulates the effects of a collection of edits
 * 
 * @param <D>
 *            the type of the dto being edited.
 * @author Andy Lee
 */
class CombinedEditor implements Editor {

	private final List<Editor> edits;

	/**
	 * Creates an edit that does not perform any editing but can be expanded at
	 * a later time.
	 */
	CombinedEditor(Editor editor1, Editor editor2, Editor... others) {
		this.edits = new ArrayList<Editor>();
		edits.add(editor1);
		edits.add(editor2);
		edits.addAll(Arrays.asList(others));
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
	public void edit(int index, Object dataItem) {
		for (Editor edit : edits) {
			edit.edit(index, dataItem);
		}
	}

	/**
	 * @return a human readable representation of the changes that are to take
	 *         place.
	 */
	@Override
	public String toString() {
		Iterator<Editor> iterator = edits.iterator();

		StringBuilder builder = new StringBuilder(iterator.next().toString());
		while (iterator.hasNext()) {
			builder.append(" AND " + iterator.next());
		}
		return builder.toString();
	}
}
