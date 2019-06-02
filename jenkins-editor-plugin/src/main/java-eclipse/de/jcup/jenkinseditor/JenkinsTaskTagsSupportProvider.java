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
