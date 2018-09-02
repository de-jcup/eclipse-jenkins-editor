package de.jcup.jenkinseditor.codeassist;

import java.util.ArrayList;
import java.util.List;

import de.jcup.jenkinseditor.document.JenkinsDefaultClosureKeyWords;
import de.jcup.jenkinseditor.document.JenkinsSpecialVariableKeyWords;
import de.jcup.jenkinseditor.document.JenkinsfileKeyword;

/**
 * DSL model.<br><br>
 * See https://jenkins.io/doc/book/pipeline/syntax/ for dedicated information about syntax
 * @author Albert Tregnaghi
 *
 */
public class JenkinsDSLModel {
	
	private Node pipelineNode;

	public JenkinsDSLModel(){
		buildModel();
	}

	private void buildModel() {
		this.pipelineNode=new Node(JenkinsDefaultClosureKeyWords.PIPELINE);
		
		Node stages = addNode(JenkinsDefaultClosureKeyWords.STAGES, pipelineNode);
		Node stage = addNode(JenkinsDefaultClosureKeyWords.STAGE, stages);
		Node when = addNode(JenkinsDefaultClosureKeyWords.WHEN, stage);
		buildWhen(when);
		Node parallel = addNode(JenkinsDefaultClosureKeyWords.PARALLEL, stage);
		parallel.children.add(stage);// parallel can contain stages again, see https://jenkins.io/doc/book/pipeline/syntax/#parallel
		
		Node tools = addNode(JenkinsDefaultClosureKeyWords.TOOLS, pipelineNode,stage);
		buildTools(tools);
		
		Node steps = addNode(JenkinsDefaultClosureKeyWords.STEPS, stage);
		buildSteps(steps);
		
		Node agent = addNode(JenkinsDefaultClosureKeyWords.AGENT, pipelineNode,stage);
		buildAgentParameters(agent);
		
		addNode(JenkinsDefaultClosureKeyWords.ENVIRONMENT, pipelineNode,stage);
		
		Node pipelineOptions = addNode(JenkinsDefaultClosureKeyWords.OPTIONS, pipelineNode);
		buildPipelineOptions(pipelineOptions);
		
		Node input = addNode(JenkinsDefaultClosureKeyWords.INPUT, stage);
		buildInput(input);

		Node parameters = addNode(JenkinsDefaultClosureKeyWords.PARAMETERS, pipelineNode,input);
		buildParameters(parameters);
		
		Node stageOptions = addNode(JenkinsDefaultClosureKeyWords.OPTIONS, stage);
		buildStageOptions(stageOptions);
		
		Node post = addNode(JenkinsDefaultClosureKeyWords.POST, pipelineNode, stage);
		buildPostConditions(post);
		
		Node triggers = addNode(JenkinsDefaultClosureKeyWords.TRIGGERS, pipelineNode);
		buildTriggers(triggers);
		
		
	}
	private void buildWhen(Node when) {
		/* see https://jenkins.io/doc/book/pipeline/syntax/#when */
		addNode(JenkinsSpecialVariableKeyWords.BRANCH, when);
		addNode(JenkinsSpecialVariableKeyWords.BUILDING_TAG, when);
		addNode(JenkinsSpecialVariableKeyWords.CHANGE_LOG, when);
		addNode(JenkinsSpecialVariableKeyWords.CHANGE_SET, when);
		addNode(JenkinsSpecialVariableKeyWords.CHANGE_REQUEST, when);
		addNode(JenkinsSpecialVariableKeyWords.WHEN_ENVIRONMENT, when);
		addNode(JenkinsSpecialVariableKeyWords.EQUALS, when);
		addNode(JenkinsSpecialVariableKeyWords.EXPRESSION, when);
		addNode(JenkinsSpecialVariableKeyWords.TAG, when);
		addNode(JenkinsDefaultClosureKeyWords.NOT, when);
		addNode(JenkinsDefaultClosureKeyWords.ALLOF, when);
		addNode(JenkinsDefaultClosureKeyWords.ANYOF, when);
		
	}

	private void buildInput(Node input) {
		/* see https://jenkins.io/doc/book/pipeline/syntax/#input */
		addNode(JenkinsSpecialVariableKeyWords.INPUT_ID, input);
		addNode(JenkinsSpecialVariableKeyWords.INPUT_OK, input);
		addNode(JenkinsSpecialVariableKeyWords.INPUT_SUBMITTER, input);
		addNode(JenkinsSpecialVariableKeyWords.INPUT_SUBMITTER_PARAM, input);
	}
	
	private void buildTools(Node tools) {
		/* see https://jenkins.io/doc/book/pipeline/syntax/#tools */
		addNode(JenkinsSpecialVariableKeyWords.MAVEN, tools);
		addNode(JenkinsSpecialVariableKeyWords.JDK, tools);
		addNode(JenkinsSpecialVariableKeyWords.GRADLE, tools);
		
	}

	private void buildTriggers(Node triggers) {
		/* see https://jenkins.io/doc/book/pipeline/syntax/#triggers */
		addNode(JenkinsSpecialVariableKeyWords.CRON, triggers);
		addNode(JenkinsSpecialVariableKeyWords.POLLSCM, triggers);
		addNode(JenkinsSpecialVariableKeyWords.UPSTREAM, triggers);
		addNode(JenkinsSpecialVariableKeyWords.CRON, triggers);
		
	}

	private void buildParameters(Node parameters) {
		/* see https://jenkins.io/doc/book/pipeline/syntax/#parameters */
		addNode(JenkinsSpecialVariableKeyWords.PARAM_STRING, parameters);
		addNode(JenkinsSpecialVariableKeyWords.PARAM_TEXT, parameters);
		addNode(JenkinsSpecialVariableKeyWords.PARAM_BOOLEAN, parameters);
		addNode(JenkinsSpecialVariableKeyWords.PARAM_CHOICE, parameters);
		addNode(JenkinsSpecialVariableKeyWords.PARAM_FILE, parameters);
		addNode(JenkinsSpecialVariableKeyWords.PARAM_PASSWORD, parameters);
		
	}

	protected void buildEnvironment(Node environment) {
		/* see https://jenkins.io/doc/book/pipeline/syntax/#environment */ 
		addNode(JenkinsSpecialVariableKeyWords.BUILD_DISCARDER, environment);
	}
	
	protected void buildPipelineOptions(Node options) {
		/* see https://jenkins.io/doc/book/pipeline/syntax/#options */ 
		addNode(JenkinsSpecialVariableKeyWords.BUILD_DISCARDER, options);
		addNode(JenkinsSpecialVariableKeyWords.CHECKOUT_TO_SUBDIRECTORY, options);
		addNode(JenkinsSpecialVariableKeyWords.DISABLE_CONCURRENT_BUILDS, options);
		addNode(JenkinsSpecialVariableKeyWords.NEW_CONTAINER_PER_STAGE, options);
		addNode(JenkinsSpecialVariableKeyWords.OVERRIDE_INDEX_TRIGGERS, options);
		addNode(JenkinsSpecialVariableKeyWords.PRESERVE_STASHES, options);
		addNode(JenkinsSpecialVariableKeyWords.RETRY, options);
		addNode(JenkinsSpecialVariableKeyWords.SKIP_DEFAULT_CHECKOUT, options);
		addNode(JenkinsSpecialVariableKeyWords.SKIP_STAGES_AFTER_UNSTABLE, options);
		addNode(JenkinsSpecialVariableKeyWords.TIMEOUT, options);
		addNode(JenkinsSpecialVariableKeyWords.TIMESTAMPS, options);
	}
	
	protected void buildStageOptions(Node options) {
		/* see https://jenkins.io/doc/book/pipeline/syntax/#options */ 
		addNode(JenkinsSpecialVariableKeyWords.SKIP_DEFAULT_CHECKOUT, options);
		addNode(JenkinsSpecialVariableKeyWords.TIMEOUT, options);
		addNode(JenkinsSpecialVariableKeyWords.RETRY, options);
		addNode(JenkinsSpecialVariableKeyWords.TIMESTAMPS, options);
	}

	protected void buildAgentParameters(Node agent) {
		Node dockerNode = addNode(JenkinsDefaultClosureKeyWords.DOCKER, agent);
		Node dockerFileNode = addNode(JenkinsDefaultClosureKeyWords.DOCKERFILE, agent);
		addNode(JenkinsSpecialVariableKeyWords.REUSE_NODE, dockerNode,dockerFileNode);
		
		// see https://jenkins.io/doc/book/pipeline/syntax/#agent
		addNode(JenkinsSpecialVariableKeyWords.ANY, agent);
		addNode(JenkinsSpecialVariableKeyWords.NONE, agent);
		addNode(JenkinsSpecialVariableKeyWords.LABEL, agent);
		Node agentNode = addNode(JenkinsDefaultClosureKeyWords.NODE, agent);
		
		/* common */
		addNode(JenkinsSpecialVariableKeyWords.LABEL, agentNode,dockerNode,dockerFileNode);
		addNode(JenkinsSpecialVariableKeyWords.CUSTOM_WORKSPACE, agentNode,dockerNode,dockerFileNode);
		
		
	}
	
	protected void buildSteps(Node steps) {
		/* build steps */
		addNode(JenkinsDefaultClosureKeyWords.SCRIPT, steps);
		addNode(JenkinsSpecialVariableKeyWords.ECHO, steps);
		addNode(JenkinsSpecialVariableKeyWords.ARCHIVE, steps);
		addNode(JenkinsSpecialVariableKeyWords.SH, steps);
		addNode(JenkinsSpecialVariableKeyWords.BAT, steps);
		addNode(JenkinsSpecialVariableKeyWords.POWERSHELL, steps);
	}

	protected void buildPostConditions(Node post) {
		/* see https://jenkins.io/doc/book/pipeline/syntax/#post */
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
		private List<Node> alreadyInspected = new ArrayList<>();
		private List<Node> result = new ArrayList<>();
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
		/* avoid endless loops when having diamond relationship ala child-parent-child */
		if (scope.alreadyInspected.contains(parent)){
			return;
		}
		scope.alreadyInspected.add(parent);
		
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
	private Node addNode(JenkinsfileKeyword keyword, Node ... parents){
		Node node = new Node(keyword,parents);
		for (Node parent: parents){
			parent.children.add(node);
		}
		return node;
	}
	
	public class Node {
		List<Node> children = new ArrayList<>();
		List<Node> parents = new ArrayList<>();
		JenkinsfileKeyword keyword;
		String lowercasedKeywordText;
		
		public Node(JenkinsfileKeyword keyword) {
			this(keyword,new Node[]{});
		}
		public Node(JenkinsfileKeyword keyword, Node ... parents) {
			this.keyword=keyword;
			for (Node parent: parents){
				this.parents.add(parent);
			}
			String text = keyword.getText();
			if (text==null){
				throw new IllegalStateException("keyword text may not be null, but is in keyword:"+keyword);
			}
			this.lowercasedKeywordText=text.toLowerCase();
		}
		
	}
	
}
