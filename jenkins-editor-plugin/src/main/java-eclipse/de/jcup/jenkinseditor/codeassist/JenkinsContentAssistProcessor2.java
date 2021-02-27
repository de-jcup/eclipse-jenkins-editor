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
package de.jcup.jenkinseditor.codeassist;

import de.jcup.eclipse.commons.codeassist.SupportableContentAssistProcessor;
import de.jcup.jenkinseditor.JenkinsEditorActivator;

public class JenkinsContentAssistProcessor2 extends SupportableContentAssistProcessor {

	public JenkinsContentAssistProcessor2(){
		super(new JenkinsDSLContentAssistSupport(JenkinsEditorActivator.getDefault()), new JenkinsEveryDSLPartContentAssistSupport(JenkinsEditorActivator.getDefault()));
	}
}
