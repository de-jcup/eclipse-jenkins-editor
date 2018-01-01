package de.jcup.jenkinseditor.preferences;

import de.jcup.egradle.eclipse.util.PreferenceIdentifiable;

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
 

/**
 * Constant definitions for plug-in preferences
 */
public enum JenkinsEditorPreferenceConstants implements PreferenceIdentifiable{
	
	/**
	 * Tasks which are executed on test
	 */
	P_LINK_OUTLINE_WITH_EDITOR("linkOutlineWithEditor"),
	
	P_EDITOR_MATCHING_BRACKETS_ENABLED("matchingBrackets"),
	P_EDITOR_HIGHLIGHT_BRACKET_AT_CARET_LOCATION("highlightBracketAtCaretLocation"),
	P_EDITOR_ENCLOSING_BRACKETS("enclosingBrackets"),
	P_EDITOR_MATCHING_BRACKETS_COLOR("matchingBracketsColor"),
	P_EDITOR_AUTO_CREATE_END_BRACKETSY("autoCreateEndBrackets"),
	
	P_EDITOR_CODEASSIST_PROPOSALS_ENABLED("codeAssistProposalsEnabled"),
	P_EDITOR_CODEASSIST_NO_PROPOSALS_FOR_GETTER_OR_SETTERS("codeAssistNoProposalsForGetterOrSetter"),
	P_EDITOR_CODEASSIST_TOOLTIPS_ENABLED("codeAssistTooltipsEnabled"), 
	
	P_JENKINS_URL("jenkinsURL"),
	
	P_PATH_TO_JENKINS_CLI_JAR("pathToJenkinsCliJar"),

	JENKINS_LINTER_ERROR_LEVEL("jenkinsLinterErrorLevel"),
	;

	private String id;

	private JenkinsEditorPreferenceConstants(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}
}
