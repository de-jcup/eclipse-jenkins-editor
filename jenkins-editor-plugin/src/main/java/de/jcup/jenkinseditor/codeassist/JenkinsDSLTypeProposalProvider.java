/*
 * Copyright 2017 Albert Tregnaghi
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
 package de.jcup.jenkinseditor.codeassist;

import java.util.List;

import de.jcup.eclipse.commons.codeassist.ProposalProvider;
import de.jcup.jenkinseditor.document.JenkinsfileKeyword;

public class JenkinsDSLTypeProposalProvider implements ProposalProvider{

	private JenkinsfileKeyword keyword;

	public JenkinsDSLTypeProposalProvider(JenkinsfileKeyword keyword) {
		this.keyword=keyword;
	}
	
	@Override
	public String getLabel() {
		return keyword.getText();
	}
	
	@Override
	public List<String> getCodeTemplate() {
		return keyword.getCodeTemplate();
	}

	@Override
	public int compareTo(ProposalProvider o) {
		return getLabel().compareTo(o.getLabel());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((keyword == null) ? 0 : keyword.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		JenkinsDSLTypeProposalProvider other = (JenkinsDSLTypeProposalProvider) obj;
		if (keyword == null) {
			if (other.keyword != null)
				return false;
		} else if (!keyword.equals(other.keyword))
			return false;
		return true;
	}
	
	
	
	


}
