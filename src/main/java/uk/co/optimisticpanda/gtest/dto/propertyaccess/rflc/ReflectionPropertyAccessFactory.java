package uk.co.optimisticpanda.gtest.dto.propertyaccess.rflc;

import uk.co.optimisticpanda.gtest.dto.propertyaccess.IPropertyAccess;
import uk.co.optimisticpanda.gtest.dto.propertyaccess.IPropertyAccessFactory;
import uk.co.optimisticpanda.gtest.dto.propertyaccess.PropertyAccessException;

/**
 * A simple {@link IPropertyAccessFactory} that provides basic property support
 * via the usage of reflection. see ReflectionPropertyAccess
 * 
 * @author Andy Lee
 */
public class ReflectionPropertyAccessFactory implements IPropertyAccessFactory {

	/**
	 * Creates a new {@link IPropertyAccess} based on the passed in property
	 * name.
	 * 
	 * @param context
	 *            the name of the property to provide support for.
	 * @see uk.co.optimisticpanda.gtest.dto.propertyaccess.IPropertyAccessFactory#createPropertyAccess(java.lang.Object)
	 */
	@Override
	public IPropertyAccess createPropertyAccess(Object context) {
		if (context instanceof String) {
			return new ReflectionPropertyAccess((String) context);
		}
		throw new PropertyAccessException(this.getClass() + " can only accept Strings.");
	}

}
