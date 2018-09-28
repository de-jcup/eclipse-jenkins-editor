package de.jcup.jenkinseditor.codeassist;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import de.jcup.jenkinseditor.codeassist.JenkinsDSLModel.Node;
import de.jcup.jenkinseditor.document.JenkinsDefaultClosureKeyWords;
import de.jcup.jenkinseditor.document.JenkinsSpecialVariableKeyWords;
import de.jcup.jenkinseditor.document.JenkinsfileKeyword;

/**
 * Check DSL healthy
 * @author Albert Tregnaghi
 *
 */
public class DSLHealthCheckTest {

	/**
	 * All enum parts must be contained in DSL model - if not test will fail (system words like SYSTEM are not tested)
	 */
	@Test
	public void created_dsl_model_contains_all_enum_parts() {
		JenkinsDSLModel model = new JenkinsDSLModel();
		Set<JenkinsfileKeyword> keywordsFound = new HashSet<>();
		Set<Node> inspected = new HashSet<>();
		inspect(model.pipelineNode,inspected, keywordsFound);
		
		List<JenkinsfileKeyword> missingKeywords = new ArrayList<>();
		checkContainingAll(keywordsFound, missingKeywords, JenkinsSpecialVariableKeyWords.values());
		checkContainingAll(keywordsFound, missingKeywords, JenkinsDefaultClosureKeyWords.values());
		
		missingKeywords.remove(JenkinsSpecialVariableKeyWords.SYSTEM);
		
		if (! missingKeywords.isEmpty()){
			fail("There are "+missingKeywords.size()+" keywords missing, means not defined/used in DSL model, but available as enum value:\n"+missingKeywords);
		}
		
	}


	protected void checkContainingAll(Set<JenkinsfileKeyword> keywordsFound, List<JenkinsfileKeyword> missingKeywords,
			JenkinsfileKeyword[] values) {
		for (JenkinsfileKeyword keyword: values) {
			if (! keywordsFound.contains(keyword)){
				missingKeywords.add(keyword);
			}
		}
	}
	
	
	private void inspect(Node node, Set<Node> alreadyInspected, Set<JenkinsfileKeyword> keywordsFound){
		if (alreadyInspected.contains(node)){
			return;
		}
		alreadyInspected.add(node);
		
		keywordsFound.add(node.keyword);
		for (Node child:node.children){
			inspect(child, alreadyInspected, keywordsFound);
		}
	}

}
