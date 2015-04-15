/*
 * Copyright 2009 Andy Lee.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
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
