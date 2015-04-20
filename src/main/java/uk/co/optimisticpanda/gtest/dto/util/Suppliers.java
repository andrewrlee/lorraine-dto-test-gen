package uk.co.optimisticpanda.gtest.dto.util;

import java.util.function.Supplier;

public class Suppliers {

	private Suppliers() {
	}

	public static <T> Supplier<T> of(T instance) {
		return () -> instance;
	}

	public static <T> Supplier<T> of(Supplier<T> instance) {
		return () -> instance.get();
	}
}