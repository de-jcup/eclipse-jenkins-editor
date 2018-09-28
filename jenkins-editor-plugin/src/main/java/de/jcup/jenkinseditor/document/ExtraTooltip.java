package de.jcup.jenkinseditor.document;

class ExtraTooltip{
	
	static ExtraTooltip POST_CONDITIONS_TOOLTIP = new ExtraTooltip("post_conditions","https://jenkins.io/doc/book/pipeline/syntax/#post-conditions");
	static ExtraTooltip OPTIONS_AVAILABLE_TOOLTIP = new ExtraTooltip("options","https://jenkins.io/doc/book/pipeline/syntax/#available-options");
	static ExtraTooltip WHEN_BUILD_IN_CONDITIONS_TOOLTIP = new ExtraTooltip("when","https://jenkins.io/doc/book/pipeline/syntax/#built-in-conditions");
	static ExtraTooltip TRIGGERS = new ExtraTooltip("triggers","https://jenkins.io/doc/book/pipeline/syntax/#triggers");
	static ExtraTooltip PARAMETERS = new ExtraTooltip("parameters","https://jenkins.io/doc/book/pipeline/syntax/#parameters");
	static ExtraTooltip AGENT_PARAMETERS = new ExtraTooltip("agent","https://jenkins.io/doc/book/pipeline/syntax/#agent-parameters");
	static ExtraTooltip OPTIONS = new ExtraTooltip("options","https://jenkins.io/doc/book/pipeline/syntax/#options");
	static ExtraTooltip TOOLS = new ExtraTooltip("tools","https://jenkins.io/doc/book/pipeline/syntax/#tools");
	static ExtraTooltip ENVIRONMENT = new ExtraTooltip("environment","https://jenkins.io/doc/book/pipeline/syntax/#environment");
	static ExtraTooltip INPUT = new ExtraTooltip("input","https://jenkins.io/doc/book/pipeline/syntax/#input");
	static ExtraTooltip WORKFLOW_BASIC_STEPS= new ExtraTooltip("basic_steps", "https://jenkins.io/doc/pipeline/steps/workflow-basic-steps/");
	
	private String identifier;
	private String linkToDocumentation;

	private ExtraTooltip(String identifier, String linkToDocumentation){
		this.identifier=identifier;
		this.linkToDocumentation=linkToDocumentation;
	}
	public String getIdentifier() {
		return identifier;
	}
	public String getLinkToDocumentation() {
		return linkToDocumentation;
	}
}