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
 package de.jcup.jenkinseditor.preferences;

import static de.jcup.jenkinseditor.preferences.JenkinsEditorPreferenceConstants.*;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.preferences.ScopedPreferenceStore;

import de.jcup.egradle.eclipse.preferences.AbstractEditorPreferences;
import de.jcup.egradle.eclipse.util.PreferenceIdentifiable;
import de.jcup.jenkinseditor.JenkinsEditor;
import de.jcup.jenkinseditor.JenkinsEditorActivator;

public class JenkinsEditorPreferences extends AbstractEditorPreferences {

	private static JenkinsEditorPreferences INSTANCE = new JenkinsEditorPreferences();

	private JenkinsEditorPreferences() {
	}

	public static JenkinsEditorPreferences getInstance() {
		return INSTANCE;
	}

	protected void updateEditorColors(IEditorPart editor) {
		if (! (editor instanceof JenkinsEditor)){
			return;
		}
		JenkinsEditor geditor = (JenkinsEditor) editor;
		geditor.handleColorSettingsChanged();
	}
	
	protected boolean checkPropertyMeansEditorColorsChanged(String property) {
		boolean colorChanged = false;
		for (JenkinsEditorSyntaxColorPreferenceConstants c: JenkinsEditorSyntaxColorPreferenceConstants.values()){
			if (property.equals(c.getId())){
				colorChanged=true;
				break;
			}
		}
		return colorChanged;
	}

	protected IPreferenceStore createStore() {
		return new ScopedPreferenceStore(InstanceScope.INSTANCE, JenkinsEditorActivator.PLUGIN_ID);
	}

	public boolean isCodeAssistProposalsEnabled() {
		return getBooleanPreference(JenkinsEditorPreferenceConstants.P_EDITOR_CODEASSIST_PROPOSALS_ENABLED);
	}

	public boolean isCodeAssistNoProposalsForGetterOrSetter() {
		return getBooleanPreference(JenkinsEditorPreferenceConstants.P_EDITOR_CODEASSIST_NO_PROPOSALS_FOR_GETTER_OR_SETTERS);
	}

	public boolean isCodeAssistTooltipsEnabled() {
		return getBooleanPreference(JenkinsEditorPreferenceConstants.P_EDITOR_CODEASSIST_TOOLTIPS_ENABLED);
	}

	public String getJenkinsURL(){
		return getStringPreference(JenkinsEditorPreferenceConstants.P_EDITOR_JENKINS_URL);
	}
	
	@Override
	public boolean isEditorAutoCreateEndBracketsEnabled() {
		return getBooleanPreference(JenkinsEditorPreferenceConstants.P_EDITOR_AUTO_CREATE_END_BRACKETSY);
	}
	@Override
	public boolean isLinkOutlineWithEditorEnabled() {
		return getBooleanPreference(P_LINK_OUTLINE_WITH_EDITOR);
	}

	@Override
	public PreferenceIdentifiable getP_EDITOR_MATCHING_BRACKETS_ENABLED() {
		return P_EDITOR_MATCHING_BRACKETS_ENABLED;
	}

	@Override
	public PreferenceIdentifiable getP_EDITOR_MATCHING_BRACKETS_COLOR() {
		return P_EDITOR_MATCHING_BRACKETS_COLOR;
	}

	@Override
	public PreferenceIdentifiable getP_EDITOR_HIGHLIGHT_BRACKET_AT_CARET_LOCATION() {
		return P_EDITOR_HIGHLIGHT_BRACKET_AT_CARET_LOCATION;
	}

	@Override
	public PreferenceIdentifiable getP_EDITOR_ENCLOSING_BRACKETS() {
		return P_EDITOR_ENCLOSING_BRACKETS;
	}

	public String getPathToJenkinsCLIJar() {
		return getStringPreference(P_EDITOR_PATH_TO_JENKINS_CLI_JAR);
	}

}
