package de.jcup.jenkinseditor.codeassist;

import java.util.ArrayList;
import java.util.List;

import de.jcup.eclipse.commons.keyword.DocumentKeyWord;
import de.jcup.jenkinseditor.document.JenkinsDefaultClosureKeyWords;
import de.jcup.jenkinseditor.document.JenkinsSpecialVariableKeyWords;

public class JenkinsDSLModel {
	
	private Node pipelineNode;

	public JenkinsDSLModel(){
		buildModel();
	}

	private void buildModel() {
		this.pipelineNode=new Node(JenkinsDefaultClosureKeyWords.PIPELINE);
		Node agent = addNode(JenkinsDefaultClosureKeyWords.AGENT, pipelineNode);
		Node stages = addNode(JenkinsDefaultClosureKeyWords.STAGES, pipelineNode);
		Node stage = addNode(JenkinsDefaultClosureKeyWords.STAGE, stages);
		Node steps = addNode(JenkinsDefaultClosureKeyWords.STEPS, stage);
		/* build steps */
		addNode(JenkinsSpecialVariableKeyWords.ECHO, steps);
		addNode(JenkinsSpecialVariableKeyWords.ARCHIVE, steps);
		addNode(JenkinsSpecialVariableKeyWords.SH, steps);
		addNode(JenkinsSpecialVariableKeyWords.BAT, steps);
		addNode(JenkinsSpecialVariableKeyWords.POWERSHELL, steps);
		
		Node post = addNode(JenkinsDefaultClosureKeyWords.POST, stage);
		/* build post conditions */
		addNode(JenkinsDefaultClosureKeyWords.ALWAYS, post);
		addNode(JenkinsDefaultClosureKeyWords.CHANGED, post);
		addNode(JenkinsDefaultClosureKeyWords.FIXED, post);
		addNode(JenkinsDefaultClosureKeyWords.REGRESSION, post);
		addNode(JenkinsDefaultClosureKeyWords.ABORTED, post);
		addNode(JenkinsDefaultClosureKeyWords.FAILURE, post);
		addNode(JenkinsDefaultClosureKeyWords.SUCCESS, post);
		addNode(JenkinsDefaultClosureKeyWords.UNSTABLE, post);
		addNode(JenkinsDefaultClosureKeyWords.CLEANUP, post);
		
		
	}
	
	private Node addNode(DocumentKeyWord keyword, Node parent){
		Node node = new Node(keyword,parent);
		parent.children.add(node);
		return node;
	}
	
	public class Node {
		List<Node> children = new ArrayList<>();
		DocumentKeyWord keyword;
		Node parent;
		public Node(DocumentKeyWord keyword) {
			this(keyword,null);
		}
		public Node(DocumentKeyWord keyword, Node parent) {
			this.keyword=keyword;
			this.parent=parent;
		}
	}

}
