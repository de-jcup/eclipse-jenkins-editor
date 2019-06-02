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
 package de.jcup.jenkinseditor.codeassist;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.graphics.Image;

import de.jcup.eclipse.commons.PluginContextProvider;
import de.jcup.eclipse.commons.codeassist.ProposalInfoProvider;
import de.jcup.eclipse.commons.codeassist.ProposalProviderContentAssistSupport;
import de.jcup.eclipse.commons.keyword.TooltipTextSupport;
import de.jcup.eclipse.commons.ui.EclipseUtil;
import de.jcup.jenkins.OutlinePipelineDSL;
import de.jcup.jenkinseditor.outline.JenkinsEditorOutlineLabelProvider;

public class JenkinsDSLContentAssistSupport extends ProposalProviderContentAssistSupport{

	private JenkinsEditorOutlineLabelProvider labelProvider = new JenkinsEditorOutlineLabelProvider();
	
	public JenkinsDSLContentAssistSupport(PluginContextProvider provider) {
		super(provider, new JenkinsWordCompletion());
	}
	
	@Override
	protected ProposalInfoProvider createProposalInfoBuilder() {
		return new ProposalInfoProvider() {
			
			@Override
			public Object getProposalInfo(IProgressMonitor monitor, Object target) {
				if (! (target instanceof String)){
					return null;
				}
				String word = (String) target;
				return TooltipTextSupport.getTooltipText(word.toLowerCase());
			}

			@Override
			public Image getImage(Object target) {
				if (! (target instanceof String)){
					return null;
				}
				String word = (String) target;
				OutlinePipelineDSL found = OutlinePipelineDSL.tryToFind(word);
				if (found==null){
					return EclipseUtil.getImage("icons/jenkinseditor/codeassist/jenkins-dsl.png", getPluginContextProvider());
				}else{
					return labelProvider.getOutlineImage(found);
				}
			}
		};
	}
	
	
}
