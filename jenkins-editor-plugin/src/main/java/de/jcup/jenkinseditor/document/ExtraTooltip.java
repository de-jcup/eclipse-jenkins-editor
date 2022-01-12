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
 package de.jcup.jenkinseditor.document;

class ExtraTooltip{
	
	static final ExtraTooltip PLUGIN_EMAILEXT_TOOLTIP = new ExtraTooltip("plugin_emailext", "https://plugins.jenkins.io/email-ext");
    static final ExtraTooltip POST_CONDITIONS_TOOLTIP = new ExtraTooltip("post_conditions","https://jenkins.io/doc/book/pipeline/syntax/#post-conditions");
	static final ExtraTooltip OPTIONS_AVAILABLE_TOOLTIP = new ExtraTooltip("options","https://jenkins.io/doc/book/pipeline/syntax/#available-options");
	static final ExtraTooltip WHEN_BUILD_IN_CONDITIONS_TOOLTIP = new ExtraTooltip("when","https://jenkins.io/doc/book/pipeline/syntax/#built-in-conditions");
	static final ExtraTooltip TRIGGERS = new ExtraTooltip("triggers","https://jenkins.io/doc/book/pipeline/syntax/#triggers");
	static final ExtraTooltip PARAMETERS = new ExtraTooltip("parameters","https://jenkins.io/doc/book/pipeline/syntax/#parameters");
	static final ExtraTooltip AGENT_PARAMETERS = new ExtraTooltip("agent","https://jenkins.io/doc/book/pipeline/syntax/#agent-parameters");
	static final ExtraTooltip OPTIONS = new ExtraTooltip("options","https://jenkins.io/doc/book/pipeline/syntax/#options");
	static final ExtraTooltip TOOLS = new ExtraTooltip("tools","https://jenkins.io/doc/book/pipeline/syntax/#tools");
	static final ExtraTooltip ENVIRONMENT = new ExtraTooltip("environment","https://jenkins.io/doc/book/pipeline/syntax/#environment");
	static final ExtraTooltip INPUT = new ExtraTooltip("input","https://jenkins.io/doc/book/pipeline/syntax/#input");
	static final ExtraTooltip WORKFLOW_BASIC_STEPS= new ExtraTooltip("basic_steps", "https://jenkins.io/doc/pipeline/steps/workflow-basic-steps/");
	
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