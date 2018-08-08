/*
 * Copyright 2016 Albert Tregnaghi
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

import de.jcup.eclipse.commons.keyword.TooltipTextSupport;
import de.jcup.egradle.eclipse.document.DocumentKeyWord;

/**
 * See https://jenkins.io/doc/book/pipeline/syntax/ for dedicated information about syntax
 * @author Albert Tregnaghi
 *
 */
public enum JenkinsDefaultClosureKeyWords implements DocumentKeyWord, de.jcup.eclipse.commons.keyword.DocumentKeyWord {
	PIPELINE("pipeline","https://jenkins.io/doc/book/pipeline/syntax/#declarative-pipeline"),

	NODE("node","https://jenkins.io/doc/book/pipeline/#node"),
	
	DOCKER("docker"),
	
	DOCKERFILE("dockerfile"),
	
	
	/* -------------------------------------------- */
	/* ---------------- Post conditions ----------- */
	/* -------------------------------------------- */
	ALWAYS("always","https://jenkins.io/doc/book/pipeline/syntax/#post-conditions",ExtraTooltip.POST_CONDITIONS_TOOLTIP),
	
	CHANGED("changed","https://jenkins.io/doc/book/pipeline/syntax/#post-conditions",ExtraTooltip.POST_CONDITIONS_TOOLTIP),
	
	FIXED("fixed","https://jenkins.io/doc/book/pipeline/syntax/#post-conditions",ExtraTooltip.POST_CONDITIONS_TOOLTIP),
	
	REGRESSION("regression","https://jenkins.io/doc/book/pipeline/syntax/#post-conditions",ExtraTooltip.POST_CONDITIONS_TOOLTIP),

	ABORTED("aborted","https://jenkins.io/doc/book/pipeline/syntax/#post-conditions",ExtraTooltip.POST_CONDITIONS_TOOLTIP),
	
	FAILURE("failure","https://jenkins.io/doc/book/pipeline/syntax/#post-conditions",ExtraTooltip.POST_CONDITIONS_TOOLTIP),
	
	SUCCESS("success","https://jenkins.io/doc/book/pipeline/syntax/#post-conditions",ExtraTooltip.POST_CONDITIONS_TOOLTIP),
	
	UNSTABLE("unstable","https://jenkins.io/doc/book/pipeline/syntax/#post-conditions",ExtraTooltip.POST_CONDITIONS_TOOLTIP),
	
	CLEANUP("cleanup","https://jenkins.io/doc/book/pipeline/syntax/#post-conditions",ExtraTooltip.POST_CONDITIONS_TOOLTIP),
	
	

	/* -------------------------------------------- */
	/* ---------------- Section parts ------------- */
	/* -------------------------------------------- */
	
	AGENT("agent","https://jenkins.io/doc/book/pipeline/syntax/#agent"),

	POST("post","https://jenkins.io/doc/book/pipeline/syntax/#post"),

	STAGES("stages","https://jenkins.io/doc/book/pipeline/syntax/#stages"),

	STEPS("steps","https://jenkins.io/doc/book/pipeline/syntax/#steps"),
	
	
	/* -------------------------------------------- */
	/* ---------------- Available options --------- */
	/* -------------------------------------------- */
	
	BUILD_DISCARDER("buildDiscarder","https://jenkins.io/doc/book/pipeline/syntax/#available-options",ExtraTooltip.OPTIONS_AVAILABLE_TOOLTIP),
	
	CHECKOUT_TO_SUBDIRECTORY("checkoutToSubdirectory","https://jenkins.io/doc/book/pipeline/syntax/#available-options",ExtraTooltip.OPTIONS_AVAILABLE_TOOLTIP),
	
	DISABLE_CONCURRENT_BUILDS("disableConcurrentBuilds","https://jenkins.io/doc/book/pipeline/syntax/#available-options",ExtraTooltip.OPTIONS_AVAILABLE_TOOLTIP),

	NEW_CONTAINER_PER_STAGE("newContainerPerStage","https://jenkins.io/doc/book/pipeline/syntax/#available-options",ExtraTooltip.OPTIONS_AVAILABLE_TOOLTIP),
	
	OVERRIDE_INDEX_TRIGGERS("overrideIndexTriggers","https://jenkins.io/doc/book/pipeline/syntax/#available-options",ExtraTooltip.OPTIONS_AVAILABLE_TOOLTIP),
	
	PRESERVE_STASHES("preserveStashes","https://jenkins.io/doc/book/pipeline/syntax/#available-options",ExtraTooltip.OPTIONS_AVAILABLE_TOOLTIP),
	
	RETRY("retry","https://jenkins.io/doc/book/pipeline/syntax/#available-options",ExtraTooltip.OPTIONS_AVAILABLE_TOOLTIP),
	
	SKIP_DEFAULT_CHECKOUT("skipDefaultCheckout","https://jenkins.io/doc/book/pipeline/syntax/#available-options",ExtraTooltip.OPTIONS_AVAILABLE_TOOLTIP),
	
	SKIP_STAGES_AFTER_UNSTABLE("skipStagesAfterUnstable","https://jenkins.io/doc/book/pipeline/syntax/#available-options",ExtraTooltip.OPTIONS_AVAILABLE_TOOLTIP),
	
	TIMEOUT("timeout","https://jenkins.io/doc/book/pipeline/syntax/#available-options",ExtraTooltip.OPTIONS_AVAILABLE_TOOLTIP),
	
	TIMESTAMPS("timestamps","https://jenkins.io/doc/book/pipeline/syntax/#available-options",ExtraTooltip.OPTIONS_AVAILABLE_TOOLTIP),
	
	
	/* -------------------------------------------- */
	/* ---------------- when built-in-conditions -- */
	/* -------------------------------------------- */
	BRANCH("branch","https://jenkins.io/doc/book/pipeline/syntax/#built-in-conditions",ExtraTooltip.WHEN_BUILD_IN_CONDITIONS_TOOLTIP),
	
	BUILDING_TAG("buildingTag","https://jenkins.io/doc/book/pipeline/syntax/#built-in-conditions",ExtraTooltip.WHEN_BUILD_IN_CONDITIONS_TOOLTIP),

	CHANGE_LOG("changelog","https://jenkins.io/doc/book/pipeline/syntax/#built-in-conditions",ExtraTooltip.WHEN_BUILD_IN_CONDITIONS_TOOLTIP),

	CHANGE_SET("changeset","https://jenkins.io/doc/book/pipeline/syntax/#built-in-conditions",ExtraTooltip.WHEN_BUILD_IN_CONDITIONS_TOOLTIP),
	
	CHANGE_REQUEST("changeRequest","https://jenkins.io/doc/book/pipeline/syntax/#built-in-conditions",ExtraTooltip.WHEN_BUILD_IN_CONDITIONS_TOOLTIP),

//	we do not support next part, becausse problems with 
//	ENVIRONMENT_IN_WHEN("environment name","https://jenkins.io/doc/book/pipeline/syntax/#built-in-conditions",ExtraTooltip.WHEN_BUILD_IN_CONDITIONS_TOOLTIP),

	EQUALS("equals","https://jenkins.io/doc/book/pipeline/syntax/#built-in-conditions",ExtraTooltip.WHEN_BUILD_IN_CONDITIONS_TOOLTIP),

	EXPRESSION("expression","https://jenkins.io/doc/book/pipeline/syntax/#built-in-conditions",ExtraTooltip.WHEN_BUILD_IN_CONDITIONS_TOOLTIP),
	
	TAG("tag","https://jenkins.io/doc/book/pipeline/syntax/#built-in-conditions",ExtraTooltip.WHEN_BUILD_IN_CONDITIONS_TOOLTIP),

	NOT("not","https://jenkins.io/doc/book/pipeline/syntax/#built-in-conditions",ExtraTooltip.WHEN_BUILD_IN_CONDITIONS_TOOLTIP),

	ALLOF("allOf","https://jenkins.io/doc/book/pipeline/syntax/#built-in-conditions",ExtraTooltip.WHEN_BUILD_IN_CONDITIONS_TOOLTIP),
	
	ANYOF("anyOf","https://jenkins.io/doc/book/pipeline/syntax/#built-in-conditions",ExtraTooltip.WHEN_BUILD_IN_CONDITIONS_TOOLTIP),
	
	
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
	
	PARAMETERS("parameters","https://jenkins.io/doc/book/pipeline/syntax/#parameters"),

	TRIGGERS("triggers","https://jenkins.io/doc/book/pipeline/syntax/#triggers"),

	STAGE("stage","https://jenkins.io/doc/book/pipeline/syntax/#stage"),
	
	TOOLS("tools","https://jenkins.io/doc/book/pipeline/syntax/#tools"),
	
	INPUT("input","https://jenkins.io/doc/book/pipeline/syntax/#input"),

	WHEN("when","https://jenkins.io/doc/book/pipeline/syntax/#when"),
	
	
	;
	private String text;
	private String tooltip;
	private String linkToDocumentation;

	private JenkinsDefaultClosureKeyWords(String text) {
		this(text,null,null);
	}
	private JenkinsDefaultClosureKeyWords(String text, String linkToDocumentation) {
		this(text,linkToDocumentation,null);
	}
	
	private JenkinsDefaultClosureKeyWords(String text, String linkToDocumentation, ExtraTooltip extraTooltip) {
		this.text = text;
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
	
	private static class ExtraTooltip{
		
		private static ExtraTooltip POST_CONDITIONS_TOOLTIP = new ExtraTooltip("post_conditions");
		private static ExtraTooltip OPTIONS_AVAILABLE_TOOLTIP = new ExtraTooltip("options");
		private static ExtraTooltip WHEN_BUILD_IN_CONDITIONS_TOOLTIP = new ExtraTooltip("when");
		
		private String identifier;

		private ExtraTooltip(String identifier){
			this.identifier=identifier;
		}
		public String getIdentifier() {
			return identifier;
		}
	}
	
}
