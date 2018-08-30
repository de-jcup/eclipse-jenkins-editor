package de.jcup.jenkinseditor.document;

import java.util.List;

import de.jcup.eclipse.commons.keyword.DocumentKeyWord;

public interface JenkinsfileKeyword extends DocumentKeyWord{

	List<String> getCodeTemplate();
	
}
