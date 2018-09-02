package de.jcup.jenkinseditor;

import org.eclipse.core.resources.IFile;

import de.jcup.eclipse.commons.tasktags.AbstractConfigurableTaskTagsSupportProvider;

public class JenkinsTaskTagsSupportProvider extends AbstractConfigurableTaskTagsSupportProvider{

	public JenkinsTaskTagsSupportProvider(JenkinsEditorActivator plugin) {
		super(plugin, plugin.getPluginID());
	}

	@Override
	public boolean isLineCheckforTodoTaskNessary(String line, int lineNumber, String[] lines) {
		/* single line: */
		if (line.trim().startsWith("//")){
			return true;
		}
		/* Multi line:*/
		if (line.trim().startsWith("/*")){
			return true;
		}
		if (line.trim().startsWith("*")){
			return true;
		}
		return false;
	}

	@Override
	public String getTodoTaskMarkerId() {
		return "de.jcup.jenkinseditor.script.task";
	}
	
	@Override
	public boolean isFileHandled(IFile file) {
		return file.getName().equals("Jenkinsfile");
	}

	

}
