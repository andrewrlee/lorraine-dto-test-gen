package uk.co.optimisticpanda.gtest.dto.propertyaccess.rflc;

import uk.co.optimisticpanda.gtest.dto.propertyaccess.IPropertyAccess;
import uk.co.optimisticpanda.gtest.dto.propertyaccess.PropertyAccessException;
import uk.co.optimisticpanda.gtest.dto.util.PrivateFieldHelper;

class ReflectionPropertyAccess implements IPropertyAccess{

	private PrivateFieldHelper privateFieldHelper;

	public ReflectionPropertyAccess(String propertyName) {
		privateFieldHelper = new PrivateFieldHelper(propertyName);
	}
	
	@Override
	public Object getValue(Object instance) throws PropertyAccessException {
		return privateFieldHelper.get(instance);
	}

	@Override
	public void setValue(Object instance, Object value)
			throws PropertyAccessException {
		privateFieldHelper.set(instance, value);
	}
}
