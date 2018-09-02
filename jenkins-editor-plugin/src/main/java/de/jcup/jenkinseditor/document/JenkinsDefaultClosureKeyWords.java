/*
 * Copyright 2018 Albert Tregnaghi
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.jcup.eclipse.commons.keyword.TooltipTextSupport;
import de.jcup.eclipse.commons.source.SourceCodeBuilder;

/**
 * See https://jenkins.io/doc/book/pipeline/syntax/ for dedicated information about syntax
 * @author Albert Tregnaghi
 *
 */
public enum JenkinsDefaultClosureKeyWords implements JenkinsfileKeyword {
	
	/* -------------------------------------------- */
	/* ---------------- Section parts ------------- */
	/* -------------------------------------------- */
	PIPELINE("pipeline","https://jenkins.io/doc/book/pipeline/syntax/#declarative-pipeline"),
	
	NODE("node","https://jenkins.io/doc/book/pipeline/#node"),
	
	AGENT("agent","https://jenkins.io/doc/book/pipeline/syntax/#agent", Collections.singletonList("agent any")),
	
	POST("post","https://jenkins.io/doc/book/pipeline/syntax/#post"),
	
	STAGES("stages","https://jenkins.io/doc/book/pipeline/syntax/#stages"),
	
	STEPS("steps","https://jenkins.io/doc/book/pipeline/syntax/#steps"),
	
	
	/* -------------------------------------------- */
	/* ---------------- Post conditions ----------- */
	/* -------------------------------------------- */
	ALWAYS("always",ExtraTooltip.POST_CONDITIONS_TOOLTIP),
	
	CHANGED("changed",ExtraTooltip.POST_CONDITIONS_TOOLTIP),
	
	FIXED("fixed",ExtraTooltip.POST_CONDITIONS_TOOLTIP),
	
	REGRESSION("regression",ExtraTooltip.POST_CONDITIONS_TOOLTIP),

	ABORTED("aborted",ExtraTooltip.POST_CONDITIONS_TOOLTIP),
	
	FAILURE("failure",ExtraTooltip.POST_CONDITIONS_TOOLTIP),
	
	SUCCESS("success",ExtraTooltip.POST_CONDITIONS_TOOLTIP),
	
	UNSTABLE("unstable",ExtraTooltip.POST_CONDITIONS_TOOLTIP),
	
	CLEANUP("cleanup",ExtraTooltip.POST_CONDITIONS_TOOLTIP),
	
	/* -------------------------------------------- */
	/* ---------------- Agent options --------- */
	/* -------------------------------------------- */
	DOCKER("docker"),
	
	DOCKERFILE("dockerfile"),
	
	/* -------------------------------------------- */
	/* ---------------- Other --------------------- */
	/* -------------------------------------------- */
	
	
	/* -------------------------------------------- */
	/* ---------------- PARALLEL parts ------------ */
	/* -------------------------------------------- */
	PARALLEL("parallel","https://jenkins.io/doc/book/pipeline/syntax/#parallel"),
	
	/* -------------------------------------------- */
	/* ---------------- STEPS parts --------------- */
	/* -------------------------------------------- */
	SCRIPT("script","https://jenkins.io/doc/book/pipeline/syntax/#script"),
	
	
	
	/* -------------------------------------------- */
	/* ---------------- Directives parts --------- */
	/* -------------------------------------------- */
	ENVIRONMENT("environment","https://jenkins.io/doc/book/pipeline/syntax/#environment"),
	
	OPTIONS("options","https://jenkins.io/doc/book/pipeline/syntax/#options"),// 0:1 inside pipeline block
	
	OPTIONS_STAGE("options","https://jenkins.io/doc/book/pipeline/syntax/#options"),// 0:1 inside stage block
	
	PARAMETERS("parameters","https://jenkins.io/doc/book/pipeline/syntax/#parameters"),

	TRIGGERS("triggers","https://jenkins.io/doc/book/pipeline/syntax/#triggers"),

	STAGE("stage","https://jenkins.io/doc/book/pipeline/syntax/#stage", createStageTemplate()),
	
	TOOLS("tools","https://jenkins.io/doc/book/pipeline/syntax/#tools"),
	
	INPUT("input","https://jenkins.io/doc/book/pipeline/syntax/#input"),

	WHEN("when","https://jenkins.io/doc/book/pipeline/syntax/#when"),
	
	/* when not built in conditions - being closures */	
	NOT("not",ExtraTooltip.WHEN_BUILD_IN_CONDITIONS_TOOLTIP),

	ALLOF("allOf",ExtraTooltip.WHEN_BUILD_IN_CONDITIONS_TOOLTIP),
	
	ANYOF("anyOf",ExtraTooltip.WHEN_BUILD_IN_CONDITIONS_TOOLTIP),
	
	EXPRESSION("expression",ExtraTooltip.WHEN_BUILD_IN_CONDITIONS_TOOLTIP,createExpressionClosure()),
	
	;
	private String text;
	private String tooltip;
	private String linkToDocumentation;
	private List<String> template;

	private JenkinsDefaultClosureKeyWords(String text) {
		this(text,null,null,null);
	}

	private static List<String> createExpressionClosure() {

		List<String> list = new ArrayList<>();
		list.add("expression {");
		list.add("   return params.DEBUG_BUILD");
		list.add("}");
		return list;
	}

	private static List<String> createStageTemplate() {
		ArrayList<String> list = new ArrayList<>();
		list.add("stage('Example Build') {");
		list.add("   "+SourceCodeBuilder.CURSOR_TAG);
		list.add("}");
		return list;
	}
	
	private JenkinsDefaultClosureKeyWords(String text, String linkToDocumentation) {
		this(text,linkToDocumentation,null,null);
	}
	
	private JenkinsDefaultClosureKeyWords(String text, String linkToDocumentation, List<String> code) {
		this(text,linkToDocumentation,null,code);
	}
	
	private JenkinsDefaultClosureKeyWords(String text, ExtraTooltip extraTooltip) {
		this(text,null,extraTooltip,null);
	}
	
	private JenkinsDefaultClosureKeyWords(String text, ExtraTooltip extraTooltip, List<String> code) {
		this(text,null,extraTooltip,code);
	}
	
	private JenkinsDefaultClosureKeyWords(String text, String linkToDocumentation, ExtraTooltip extraTooltip,  List<String> code) {
		this.text = text;
		this.template = code;
		if (linkToDocumentation==null){
			this.linkToDocumentation="https://jenkins.io/doc/book/pipeline/syntax";
		}else{
			this.linkToDocumentation=linkToDocumentation;
		}
		String identifier=null;
		if (extraTooltip==null){
			identifier = name().toLowerCase();
		}else{
			identifier=extraTooltip.getIdentifier();
		}
		this.tooltip=TooltipTextSupport.getTooltipText(identifier);
	}


	@Override
	public String getText() {
		return text;
	}


	@Override
	public String getLinkToDocumentation() {
		return linkToDocumentation;
	}


	@Override
	public String getTooltip() {
		return tooltip;
	}

	@Override
	public boolean isBreakingOnEof() {
		return false;
	}
	
	private SourceCodeBuilder sourceCodeBuilder = new SourceCodeBuilder();
	
	@Override
	public List<String> getCodeTemplate() {
		if (template!=null){
			return template;
		}
		return sourceCodeBuilder.buildClosureTemplate(text);
	}
	
	
}
