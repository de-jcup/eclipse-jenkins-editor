/*
 * Copyright 2016 Albert Tregnaghi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *		http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions
 * and limitations under the License.
 *
 */
 package de.jcup.jenkinseditor.outline;

import de.jcup.egradle.core.model.Item;
import de.jcup.egradle.core.model.ItemFilter;
import de.jcup.egradle.core.model.ItemType;

class JenkinsOutlineItemFilter implements ItemFilter {

	@Override
	public boolean isFiltered(Item item) {
		/*
		 * we do not show item which are remaining as METHOD_CALL will not
		 * be shown in outline
		 */
		if (ItemType.METHOD_CALL == item.getItemType()) {
			if (item.hasChildren()){
				return false;
			}
			/* no children... so only a normal call and be filtered*/
			return true;
		}
		return false;
	}

}