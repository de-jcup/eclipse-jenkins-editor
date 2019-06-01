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
