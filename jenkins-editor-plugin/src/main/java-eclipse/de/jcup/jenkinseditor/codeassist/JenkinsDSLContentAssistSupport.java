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
