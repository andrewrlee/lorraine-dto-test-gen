package uk.co.optimisticpanda.gtest.dto.rule;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import uk.co.optimisticpanda.gtest.dto.condition.ICondition;
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

	protected final List<ICondition> conditions;
	protected final Editor<D> edit;

	/**
	 * @param edit
	 *            the action to occur to the dto.
	 * @param conditions
	 *            a list of conditions for which this rule will fire if any are
	 *            true.
	 */
	public BaseEdit(Editor<D> edit, ICondition condition) {
		this.edit = edit;
		this.conditions = new ArrayList<ICondition>();
		this.conditions.add(condition);
	}

	/**
	 * @param edit
	 *            the action to occur to the dto.
	 * @param conditions
	 *            a list of conditions for which this rule will fire if any are
	 *            true.
	 */
	protected BaseEdit(Editor<D> edit, List<ICondition> conditions) {
		this.edit = edit;
		this.conditions = conditions;
	}
	
	public BaseEdit<D> or(ICondition condition){
		conditions.add(condition);
		return this;
	}
	
	/**
	 * (non-Javadoc)
	 * 
	 * @see uk.co.optimisticpanda.gtest.dto.rule.Edit#edit(int,
	 *      java.lang.Object)
	 */
	public void edit(int index, D dataItem) {
		edit.edit(index, dataItem);
	}

	public boolean isValid(int index, D dataItem) {
		for (ICondition condition : conditions) {
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
		Iterator<ICondition> iterator = conditions.iterator();
		StringBuilder builder = new StringBuilder(edit + " WHERE " + iterator.next());
		while (iterator.hasNext()) {
			builder.append(" AND " + iterator.next());
		}
		return builder.toString();
	}
}
