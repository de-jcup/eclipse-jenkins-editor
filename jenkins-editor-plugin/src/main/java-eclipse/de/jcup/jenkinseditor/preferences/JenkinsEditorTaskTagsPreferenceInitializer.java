package de.jcup.jenkinseditor.preferences;

import de.jcup.eclipse.commons.tasktags.AbstractTaskTagsPreferenceInitializer;
import de.jcup.jenkinseditor.JenkinsEditorActivator;

public class JenkinsEditorTaskTagsPreferenceInitializer extends AbstractTaskTagsPreferenceInitializer{

	public JenkinsEditorTaskTagsPreferenceInitializer() {
		super(JenkinsEditorActivator.PLUGIN_ID);
	}

}