package uk.co.optimisticpanda.gtest.dto.defaultfill.insgen.excl;

import java.util.ArrayList;
import java.util.List;

/**
 *The ExclusionHolder holds information about which paths are excluded
 * 
 * @author Andy Lee
 */
public class ExclusionHolder {

	private final List<String> exclusions;

	/**
	 * default constructor
	 */
	public ExclusionHolder() {
		this.exclusions = new ArrayList<String>();
		addDefaultExclusions();
	}

	private void addDefaultExclusions() {
		addExclusion("$VRc");
		addExclusion("serialVersionUID");
	}

	/**
	 * @param path
	 */
	public void addExclusion(String path) {
		exclusions.add(path);
	}

	/**
	 * Check whether the current path is excluded from further processing.
	 * 
	 * @param path
	 *            the path to check
	 * @return whether path this path is excluded
	 */
	public boolean isNotExcluded(String path) {
		return !exclusions.contains(path);
	}

}
