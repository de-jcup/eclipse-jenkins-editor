package de.jcup.jenkinseditor;

import org.eclipse.core.resources.IFile;

import de.jcup.eclipse.commons.tasktags.AbstractConfigurableTaskTagsSupportProvider;

public class JenkinsTaskTagsSupportProvider extends AbstractConfigurableTaskTagsSupportProvider{

	public JenkinsTaskTagsSupportProvider(JenkinsEditorActivator plugin) {
		super(plugin);
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
		if (file==null){
			return false;
		}
		String name = file.getName();
		if (name==null){
			return false;
		}
		return name.equals("Jenkinsfile") || name.endsWith(".jenkins") || name.endsWith(".jenkinsfile");
	}

	

}
