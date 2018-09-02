package de.jcup.jenkinseditor.preferences;

import de.jcup.eclipse.commons.tasktags.AbstractTaskTagsPreferencePage;
import de.jcup.jenkinseditor.JenkinsEditorActivator;

public class JenkinsEditorTaskTagsPreferencePage extends AbstractTaskTagsPreferencePage{

	public JenkinsEditorTaskTagsPreferencePage() {
		super(JenkinsEditorActivator.getDefault().getTaskSupportProvider(), "Jenkinsfile TODOs","Here you find the definitions of TODOs in Jenkinsfile files");
	}

}
