package uk.co.optimisticpanda.gtest.dto.edit;

/**
  * An Edit that sets the specified property to null.
 * 
 * @param <D>
 *            The type of the dtos to be edited.
 * @author Andy Lee
 */
public class NullEditor<D> extends SetValueEditor<D> {
	
	/**
	 * Create an edit that always inserts null.
	 * @param context
	 */
	public NullEditor(Object context) {
		super(context, null);
	}

}
