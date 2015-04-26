package uk.co.optimisticpanda.gtest.dto.edit;

import static uk.co.optimisticpanda.gtest.dto.edit.IteratingCollectionEditor.CycleBehaviour.CYCLE;
import static uk.co.optimisticpanda.gtest.dto.edit.IteratingCollectionEditor.CycleBehaviour.LEAVE_UNTOUCHED;
import static uk.co.optimisticpanda.gtest.dto.edit.IteratingCollectionEditor.CycleBehaviour.NULL_FILL;
import static uk.co.optimisticpanda.gtest.dto.edit.IteratingCollectionEditor.CycleBehaviour.THROW_EXCEPTION;

public class Editors {

	public static IncrementBuilder incrementEach(Object context) {
		return new IncrementBuilder(context);
	}

	public static SetBuilder changeValueOf(Object context) {
		return new SetBuilder(context);
	}

	public static class IncrementBuilder {
		private Object context;
		private IncrementBuilder(Object context) {
			this.context = context;
		}
		public Editor withBase(String value) {
			return new IncrementingNameEditor(context, value);
		}
	}

	public static class SetBuilder {
		private Object context;
		private SetBuilder(Object context) {
			this.context = context;
		}
		public Editor to(Object value) {
			return new SetValueEditor(context, value);
		}
		public Editor toNull() {
			return new NullEditor(context);
		}
		public Editor cycleBetween(Object... values) {
			return new IteratingCollectionEditor(context, CYCLE, values);
		}
		public CycleBuilder use(Object... values) {
			return new CycleBuilder(context, values);
		}
	}

	public static class CycleBuilder {
		private Object context;
		private Object[] values;
		private CycleBuilder(Object context, Object... values) {
			this.context = context;
			this.values = values;
		}
		public Editor andLeaveTheRest() {
			return new IteratingCollectionEditor(context, LEAVE_UNTOUCHED, values);
		}
		public Editor andThenFillWithNulls() {
			return new IteratingCollectionEditor(context, NULL_FILL, values);
		}
		public Editor andThenThrowException() {
			return new IteratingCollectionEditor(context, THROW_EXCEPTION, values);
		}
	}
}
