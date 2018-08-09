package de.jcup.jenkinseditor.codeassist;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.graphics.Image;

import de.jcup.eclipse.commons.PluginContextProvider;
import de.jcup.eclipse.commons.codeassist.ProposalInfoProvider;
import de.jcup.eclipse.commons.codeassist.simpleword.WordContentAssistSupport;
import de.jcup.eclipse.commons.keyword.TooltipTextSupport;
import de.jcup.eclipse.commons.ui.EclipseUtil;

public class JenkinsDSLContentAssistSupport extends WordContentAssistSupport{

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
				return EclipseUtil.getImage("icons/jenkinseditor/codeassist/jenkins-dsl.png", getPluginContextProvider());
			}
		};
	}
	
	
}
