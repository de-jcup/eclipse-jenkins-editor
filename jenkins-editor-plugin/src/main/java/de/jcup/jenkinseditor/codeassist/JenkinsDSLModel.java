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
	
	private enum SearchType{
		LOWERCASE_EQUAL,
		LOWERCASE_STARTSWITH,
	}
	
	private class SearchScope{
		private List<Node> result = new ArrayList<>();
		private String keyword;
		private SearchType searchType = SearchType.LOWERCASE_EQUAL;
		public String keywordLowerCased;
	}
	
	public List<Node> findByKeyword(String keyword){
		return findBy(keyword, SearchType.LOWERCASE_EQUAL);
	}
	
	public List<Node> findByKeywordStartingWith(String start){
		return findBy(start, SearchType.LOWERCASE_STARTSWITH);
	}


	protected List<Node> findBy(String keyword, SearchType type) {
		SearchScope scope = new SearchScope();
		if (keyword==null){
			return scope.result;
		}
		scope.keyword=keyword;
		scope.keywordLowerCased=keyword.toLowerCase();
		scope.searchType=type;
		findByKeyword(scope, pipelineNode);
		return scope.result;
	}
	
	
	private void findByKeyword(SearchScope scope, Node parent){
		if (parent==null){
			return;
		}
		if (parent.keyword==null){
			return;
		}
		for (Node child: parent.children){
			findByKeyword(scope,child);
		}
		if (parent.lowercasedKeywordText==null){
			return;
		}
		switch(scope.searchType){
		case LOWERCASE_STARTSWITH:
			if (parent.lowercasedKeywordText.startsWith(scope.keywordLowerCased)){
				scope.result.add(parent);
			}
			break;
		case LOWERCASE_EQUAL:
			if (parent.lowercasedKeywordText.equals(scope.keywordLowerCased)){
				scope.result.add(parent);
			}
			break;
		}
	}
	
	private Node addNode(DocumentKeyWord keyword, Node parent){
		Node node = new Node(keyword,parent);
		parent.children.add(node);
		return node;
	}
	
	public class Node {
		List<Node> children = new ArrayList<>();
		DocumentKeyWord keyword;
		String lowercasedKeywordText;
		Node parent;
		public Node(DocumentKeyWord keyword) {
			this(keyword,null);
		}
		public Node(DocumentKeyWord keyword, Node parent) {
			this.keyword=keyword;
			this.parent=parent;
			String text = keyword.getText();
			if (text==null){
				throw new IllegalStateException("keyword text may not be null, but is in keyword:"+keyword);
			}
			this.lowercasedKeywordText=text.toLowerCase();
		}
	}

}
