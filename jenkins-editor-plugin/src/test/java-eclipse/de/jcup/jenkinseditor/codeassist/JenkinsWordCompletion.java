package de.jcup.jenkinseditor.codeassist;

import java.io.ByteArrayInputStream;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import de.jcup.eclipse.commons.codeassist.AbstractWordCodeCompletition;
import de.jcup.eclipse.commons.codeassist.ProposalProvider;
import de.jcup.egradle.core.model.BuildContext;
import de.jcup.egradle.core.model.Item;
import de.jcup.egradle.core.model.Model;
import de.jcup.egradle.core.model.ModelBuilder.ModelBuilderException;
import de.jcup.jenkins.JenkinsModelBuilder;
import de.jcup.jenkinseditor.codeassist.JenkinsDSLModel.Node;
import de.jcup.jenkinseditor.document.JenkinsDefaultClosureKeyWords;

public class JenkinsWordCompletion extends AbstractWordCodeCompletition{

	private JenkinsDSLModel model;

	public JenkinsWordCompletion(){
		model = new JenkinsDSLModel();
	}
	
	@Override
	public Set<ProposalProvider> calculate(String source, int offset) {
		if (source.trim().length()==0){
			return Collections.singleton(new JenkinsDSLTypeProposalProvider(JenkinsDefaultClosureKeyWords.PIPELINE));
		}
		String parentAsText = "";
		
		JenkinsModelBuilder builder = new JenkinsModelBuilder(new ByteArrayInputStream(source.getBytes()));
		BuildContext builderContext = new BuildContext();
		try {
			Model model = builder.build(builderContext);
			Item item = model.getParentItemOf(offset);
			parentAsText=item.getIdentifier();
			
		} catch (ModelBuilderException e) {
			return Collections.emptySet();
		}
		List<Node> foundParents = model.findByKeyword(parentAsText);
		if (foundParents.isEmpty()){
			return Collections.emptySet();
		}
		
		Set<ProposalProvider> set = new TreeSet<>();
		String alreadyTyped = getTextbefore(source, offset);

		List<Node> result = model.findByKeywordStartingWith(alreadyTyped);
		if (!result.isEmpty()){
			for (Node node: result){
				for (Node parent: node.parents){
					if (foundParents.contains(parent)){
						/* accepted by at least one parent */
						set.add(new JenkinsDSLTypeProposalProvider(node.keyword));
						break;
					}
				}
			}
		}
		return set;
	}


	@Override
	public void reset() {
		/* no dynamic parts - so ignore*/
	}

}
