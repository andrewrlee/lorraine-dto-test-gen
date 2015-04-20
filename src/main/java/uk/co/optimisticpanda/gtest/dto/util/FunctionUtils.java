package uk.co.optimisticpanda.gtest.dto.util;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.function.Supplier;

public class FunctionUtils {

	/**
	 * A value generator instance that is used as a null object, it does not generate
	 * anything and throws an {@link UnsupportedOperationException} if {@link #generate()} is called.
	 */
	public static final Supplier NOT_COVERED = new Supplier() {
		
		@Override
		public Object get() {
			throw new UnsupportedOperationException("This generator does not generate values");
		}

	};

	
	   public static <T> Function<T, IndexedItem<T>> indexed() {
	        AtomicInteger count = new AtomicInteger();
	        return (t) -> new IndexedItem<T>(count.getAndIncrement(), t);
	    }

	    public static class IndexedItem<T> {
	        public final Integer index;
	        public final T item;

	        public IndexedItem(Integer index, T item) {
	            this.index = index;
	            this.item = item;
	        }
	    }
}
