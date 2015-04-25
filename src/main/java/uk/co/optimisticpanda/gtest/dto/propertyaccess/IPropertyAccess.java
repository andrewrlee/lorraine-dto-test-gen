package uk.co.optimisticpanda.gtest.dto.propertyaccess;

/**
 * These are used for introspection purposes.
 * <p>One {@link IPropertyAccess} is
 * created for each instance that extends {@link PropertyAccessSupport}.
 * </p>
 * @see IPropertyAccessFactory
 * @author Andy Lee
 */
public interface IPropertyAccess {

	/**
	 * Get the value of the property that this {@link IPropertyAccess}
	 * represents from a specific instance.
	 * 
	 * @param instance
	 *            to get the value from.
	 * @return value of the property this property value represents
	 * @throws PropertyAccessException
	 */
	public Object getValue(Object instance) throws PropertyAccessException;

	/**
	 * Set the property belonging to an instance to the specified value.
	 * 
	 * @param instance
	 *            to set the value on.
	 * @param value
	 *            the value to set
	 * @throws PropertyAccessException
	 */
	public void setValue(Object instance, Object value) throws PropertyAccessException;
}
