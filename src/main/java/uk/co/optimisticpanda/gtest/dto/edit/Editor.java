package uk.co.optimisticpanda.gtest.dto.edit;

/**
 * A change to occur to a dto. This is seperate from whether the change should
 * take place.
 * 
 * @author Andy Lee
 * @param <D>
 *            The type of the dto being edited.
 * 
 */
public interface Editor<D> {

	/**
	 * Apply the effects of the edit.
	 * 
	 * @param index
	 *            The index of the item to be edited
	 * @param dataItem
	 *            The dto to be edited
	 */
	public void edit(int index, D dataItem);

}
