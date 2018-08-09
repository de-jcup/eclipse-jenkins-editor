package de.jcup.jenkinseditor.codeassist;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import de.jcup.eclipse.commons.codeassist.words.AbstractWordCodeCompletition;
import de.jcup.jenkinseditor.codeassist.JenkinsDSLModel.Node;
import de.jcup.jenkinseditor.document.JenkinsDefaultClosureKeyWords;

public class JenkinsWordCompletion extends AbstractWordCodeCompletition{

	private JenkinsDSLModel model;

	public JenkinsWordCompletion(){
		model = new JenkinsDSLModel();
	}
	
	@Override
	public Set<String> calculate(String source, int offset) {
		if (source.trim().length()==0){
			return Collections.singleton(JenkinsDefaultClosureKeyWords.PIPELINE.getText());
		}
		Set<String> set = new TreeSet<>();
		String alreadyTyped = getTextbefore(source, offset);
		if (alreadyTyped.isEmpty()){
			/* we must find the upper element (parent) */
		}
		
		List<Node> result = model.findByKeywordStartingWith(alreadyTyped);
		if (!result.isEmpty()){
			for (Node node: result){
				set.add(node.keyword.getText());
			}
		}
		return set;
	}

	@Override
	public void reset() {
		/* no dynamic parts - so ignore*/
	}

}
