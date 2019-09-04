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

import java.util.List;

import de.jcup.eclipse.commons.keyword.TooltipTextSupport;
import de.jcup.eclipse.commons.source.SourceCodeBuilder;

/**
 * See https://jenkins.io/doc/book/pipeline/syntax/ for syntax information
 * 
 * @author Albert Tregnaghi
 *
 */
public enum JenkinsSpecialVariableKeyWords implements JenkinsfileKeyword {

	SYSTEM("System", "http://docs.groovy-lang.org/2.4.3/html/groovy-jdk/java/lang/System.html"),

	JUNIT("junit", "https://jenkins.io/doc/pipeline/steps/junit/"),

	ARCHIVE("archive", "https://jenkins.io/doc/pipeline/steps/workflow-basic-steps/#archive-archive-artifacts"),
	/* agent parameters: */
	ARCHIVE_ARTIFACTS("archiveArtifacts",
			"https://jenkins.io/doc/pipeline/steps/core/#-archiveartifacts-%20archive%20the%20artifacts"),

	IS_UNIX("isUnix",
			"https://jenkins.io/doc/pipeline/steps/workflow-basic-steps/#isunix-checks-if-running-on-a-unix-like-node"),

	MAIL("mail", "https://jenkins.io/doc/pipeline/steps/workflow-basic-steps/#mail-mail","mail to: team@example.com, subject: 'A special subject', body:'Interesting things have been done...'"),

	PWD("pwd",ExtraTooltip.WORKFLOW_BASIC_STEPS.getLinkToDocumentation(),"pwd"),
	
	STEP("step", "https://jenkins.io/doc/pipeline/steps/workflow-basic-steps/#step-general-build-step"),

	ANY("any",ExtraTooltip.AGENT_PARAMETERS),

	NONE("none",ExtraTooltip.AGENT_PARAMETERS),

	IMAGE("image",ExtraTooltip.AGENT_PARAMETERS),

	LABEL("label",ExtraTooltip.AGENT_PARAMETERS),

	ARGS("args",ExtraTooltip.AGENT_PARAMETERS),

	CUSTOM_WORKSPACE("customWorkspace",ExtraTooltip.AGENT_PARAMETERS),

	REUSE_NODE("reuseNode",ExtraTooltip.AGENT_PARAMETERS),


	/* basic steps */
	DELETE_DIR("deleteDir", ExtraTooltip.WORKFLOW_BASIC_STEPS.getLinkToDocumentation(),"deleteDir()"),

	DIR("dir", ExtraTooltip.WORKFLOW_BASIC_STEPS.getLinkToDocumentation(),"dir 'newCurrentDirPath'"),

	ECHO("echo", "https://jenkins.io/doc/pipeline/steps/workflow-basic-steps/#echo-print-message","echo 'Hello Jenkins!'"),

	ERROR("error", ExtraTooltip.WORKFLOW_BASIC_STEPS.getLinkToDocumentation(),"error 'custom failure message'"),

	FILE_EXISTS("fileExists",ExtraTooltip.WORKFLOW_BASIC_STEPS.getLinkToDocumentation(),"fileExists '/pathToFileToCheck'"),
	
	READ_FILE("readFile",ExtraTooltip.WORKFLOW_BASIC_STEPS.getLinkToDocumentation(), "readFile file:'/pathToFileToRead' encoding:'UTF-8'"),
	
	SH("sh", "https://jenkins.io/doc/pipeline/steps/workflow-durable-task-step/#sh-shell-script","sh 'gradlew build'"),

	BAT("bat", "https://jenkins.io/doc/pipeline/steps/workflow-durable-task-step/#-bat-%20windows%20batch%20script","sh 'gradlew build'"),

	POWERSHELL("powershell",
			"https://jenkins.io/doc/pipeline/steps/workflow-durable-task-step/#powershell-powershell-script"),


	CREDENTIALS("credentials", ExtraTooltip.ENVIRONMENT, "AN_ACCESSKEY = credentials('my-predefined-secret')"),

	/* available parameters */
	PARAM_STRING("string", ExtraTooltip.PARAMETERS,"string(name: '"+SourceCodeBuilder.CURSOR_TAG+"DEPLOY_ENV', defaultValue: 'staging', description: '')"),

	PARAM_TEXT("text", ExtraTooltip.PARAMETERS,"text(name: '"+SourceCodeBuilder.CURSOR_TAG+"DEPLOY_TEXT', defaultValue: 'One\\nTwo\\nThree\\n', description: '')"),

	PARAM_BOOLEAN("booleanParam", ExtraTooltip.PARAMETERS,"booleanParam(name: '"+SourceCodeBuilder.CURSOR_TAG+"DEBUG_BUILD', defaultValue: true, description: '')"),

	PARAM_CHOICE("choice", ExtraTooltip.PARAMETERS,"choice(name: '"+SourceCodeBuilder.CURSOR_TAG+"CHOICES', choices: 'one\ntwo\nthree', description: '')"),

	PARAM_FILE("file", ExtraTooltip.PARAMETERS,"file(name: '"+SourceCodeBuilder.CURSOR_TAG+"FILE', description: 'Some file to upload') "),

	PARAM_PASSWORD("password", ExtraTooltip.PARAMETERS,"password(name: '"+SourceCodeBuilder.CURSOR_TAG+"PASSWORD', defaultValue: 'SECRET', description: 'A secret password')"),

	/* triggers */
	CRON("cron", ExtraTooltip.TRIGGERS),

	POLLSCM("pollSCM", ExtraTooltip.TRIGGERS),

	UPSTREAM("upstream", ExtraTooltip.TRIGGERS),

	/* tools */
	MAVEN("maven",ExtraTooltip.TOOLS), 
	
	JDK("jdk",ExtraTooltip.TOOLS), 
	
	GRADLE("gradle",ExtraTooltip.TOOLS),


	BUILD_DISCARDER("buildDiscarder", ExtraTooltip.OPTIONS_AVAILABLE_TOOLTIP,"buildDiscarder(logRotator(numToKeepStr: '1'))"),

	CHECKOUT_TO_SUBDIRECTORY("checkoutToSubdirectory", ExtraTooltip.OPTIONS_AVAILABLE_TOOLTIP,"checkoutToSubdirectory('foo')"),

	DISABLE_CONCURRENT_BUILDS("disableConcurrentBuilds", ExtraTooltip.OPTIONS_AVAILABLE_TOOLTIP, "disableConcurrentBuilds()"),

	NEW_CONTAINER_PER_STAGE("newContainerPerStage", ExtraTooltip.OPTIONS_AVAILABLE_TOOLTIP),

	OVERRIDE_INDEX_TRIGGERS("overrideIndexTriggers", ExtraTooltip.OPTIONS_AVAILABLE_TOOLTIP, "overrideIndexTriggers(true)"),

	PRESERVE_STASHES("preserveStashes", ExtraTooltip.OPTIONS_AVAILABLE_TOOLTIP,"preserveStashes(1)"),

	RETRY("retry", ExtraTooltip.OPTIONS_AVAILABLE_TOOLTIP.getLinkToDocumentation(),"retry 3"),

	SLEEP("sleep", ExtraTooltip.OPTIONS_AVAILABLE_TOOLTIP.getLinkToDocumentation(),"sleep time:10, unit:SECONDS"),

	STASH("stash", ExtraTooltip.OPTIONS_AVAILABLE_TOOLTIP.getLinkToDocumentation(), "stash name:'Stash some files for later build' includes:'**/*.adoc'"),
	
	SKIP_DEFAULT_CHECKOUT("skipDefaultCheckout", ExtraTooltip.OPTIONS_AVAILABLE_TOOLTIP,"skipDefaultCheckout()"),

	SKIP_STAGES_AFTER_UNSTABLE("skipStagesAfterUnstable", ExtraTooltip.OPTIONS_AVAILABLE_TOOLTIP,"skipStagesAfterUnstable()"),

	TIMEOUT("timeout", ExtraTooltip.OPTIONS_AVAILABLE_TOOLTIP,"timeout(time: 1, unit: 'HOURS')"),

	TIMESTAMPS("timestamps", ExtraTooltip.OPTIONS_AVAILABLE_TOOLTIP,"timestamps()"), 
	
	INPUT_MESSAGE("message",ExtraTooltip.INPUT,"message \"Should we continue?\""),

	INPUT_ID("id",ExtraTooltip.INPUT,"id \"my_id_instead_stage_name\""),
	
	INPUT_OK("ok",ExtraTooltip.INPUT,"ok \"Yes, we should\""),
	
	INPUT_SUBMITTER("submitter",ExtraTooltip.INPUT,"submitter \"alice,bob\""),

	INPUT_SUBMITTER_PARAM("submitterParameter",ExtraTooltip.INPUT),
	
	WHEN_ENVIRONMENT("environment", ExtraTooltip.ENVIRONMENT, "environment name: 'DEPLOY_TO', value: 'production'"),
	
	/* -------------------------------------------- */
	/* ---------------- when built-in-conditions -- */
	/* -------------------------------------------- */
	BRANCH("branchName",ExtraTooltip.WHEN_BUILD_IN_CONDITIONS_TOOLTIP),
	
	BUILDING_TAG("buildingTag",ExtraTooltip.WHEN_BUILD_IN_CONDITIONS_TOOLTIP,"buildingTag()"),

	CHANGE_LOG("changelog",ExtraTooltip.WHEN_BUILD_IN_CONDITIONS_TOOLTIP,"changelog '.*^\\\\[DEPENDENCY\\] .+$'"),

	CHANGE_SET("changeset",ExtraTooltip.WHEN_BUILD_IN_CONDITIONS_TOOLTIP,"changeset \"**/*.js\""),
	
	CHANGE_REQUEST("changeRequest",ExtraTooltip.WHEN_BUILD_IN_CONDITIONS_TOOLTIP,"changeRequest()"),

	EQUALS("equals",ExtraTooltip.WHEN_BUILD_IN_CONDITIONS_TOOLTIP,"equals expected: 2, actual: currentBuild.number"),

	TAG("tag",ExtraTooltip.WHEN_BUILD_IN_CONDITIONS_TOOLTIP,"tag \"release-*\""), 
	

	;

	private String text;
	private String linkToDocumentation;
	private String tooltip;
	private String code;

	private JenkinsSpecialVariableKeyWords(String text) {
		this(text, (String) null);
	}

	private JenkinsSpecialVariableKeyWords(String text, String linkToDocumentation) {
		this(text, linkToDocumentation, null,null);
	}

	private JenkinsSpecialVariableKeyWords(String text, String linkToDocumentation, String code) {
		this(text, linkToDocumentation, null,code);
	}

	private JenkinsSpecialVariableKeyWords(String text, ExtraTooltip extraTooltip) {
		this(text, null, extraTooltip,null);
	}
	
	private JenkinsSpecialVariableKeyWords(String text, ExtraTooltip extraTooltip, String code) {
		this(text, null, extraTooltip,code);
	}
	
	private JenkinsSpecialVariableKeyWords(String text, String linkToDocumentation, ExtraTooltip extraTooltip, String code) {
		this.text = text;
		this.code= code== null ? text: code;

		if (linkToDocumentation == null) {
			this.linkToDocumentation = extraTooltip.getLinkToDocumentation();
		} else {
			this.linkToDocumentation = linkToDocumentation;
		}
		if (this.linkToDocumentation == null) {
			this.linkToDocumentation = "https://jenkins.io/doc/book/pipeline/syntax";
		}
		String identifier = null;
		if (extraTooltip == null) {
			identifier = name().toLowerCase();
		} else {
			identifier = extraTooltip.getIdentifier();
		}
		this.tooltip = TooltipTextSupport.getTooltipText(identifier);
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

	private SourceCodeBuilder builder = new SourceCodeBuilder();
	
	@Override
	public List<String> getCodeTemplate() {
		return builder.buildSingleLineTemplate(code);
	}
	
	
}
