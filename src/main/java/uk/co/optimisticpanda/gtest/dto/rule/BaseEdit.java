package uk.co.optimisticpanda.gtest.dto.rule;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import uk.co.optimisticpanda.gtest.dto.condition.Condition;
import uk.co.optimisticpanda.gtest.dto.edit.Editor;

/**
 * A simple implementation of {@link Edit}. This wraps 1 or more condition. If
 * any single condition returns true then the effect of the edit occurs to
 * the current dto being processed.
 * 
 * @author Andy Lee
 * @param <D>
 *            the type that this rule should edit
 */
public class BaseEdit<D> implements Edit<D> {

	protected final List<Condition> conditions;
	protected final Editor edit;

	/**
	 * @param edit
	 *            the action to occur to the dto.
	 * @param conditions
	 *            a list of conditions for which this rule will fire if any are
	 *            true.
	 */
	public BaseEdit(Editor edit, Condition condition) {
		this.edit = edit;
		this.conditions = new ArrayList<Condition>();
		this.conditions.add(condition);
	}

	/**
	 * @param edit
	 *            the action to occur to the dto.
	 * @param conditions
	 *            a list of conditions for which this rule will fire if any are
	 *            true.
	 */
	protected BaseEdit(Editor edit, List<Condition> conditions) {
		this.edit = edit;
		this.conditions = conditions;
	}
	
	public BaseEdit<D> or(Condition condition){
		conditions.add(condition);
		return this;
	}
	
	/**
	 * (non-Javadoc)
	 * 
	 * @see uk.co.optimisticpanda.gtest.dto.rule.Edit#edit(int,
	 *      java.lang.Object)
	 */
	public void edit(int index, Object dataItem) {
		edit.edit(index, dataItem);
	}

	public boolean isValid(int index, D dataItem) {
		for (Condition condition : conditions) {
			if (condition.isValid(index, dataItem)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * A human readable representation of the rule.
	 */
	@Override
	public String toString() {
		Iterator<Condition> iterator = conditions.iterator();
		StringBuilder builder = new StringBuilder(edit + " WHERE " + iterator.next());
		while (iterator.hasNext()) {
			builder.append(" AND " + iterator.next());
		}
		return builder.toString();
	}
}
