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
 * See https://jenkins.io/doc/book/pipeline/syntax/ for syntax information
 * @author Albert Tregnaghi
 *
 */
public enum JenkinsSpecialVariableKeyWords implements DocumentKeyWord, de.jcup.eclipse.commons.keyword.DocumentKeyWord {

	SYSTEM("System","http://docs.groovy-lang.org/2.4.3/html/groovy-jdk/java/lang/System.html"),
	
	JUNIT("junit","https://jenkins.io/doc/pipeline/steps/junit/"),
	
	ARCHIVE("archive","https://jenkins.io/doc/pipeline/steps/workflow-basic-steps/#archive-archive-artifacts"),
	/* agent parameters: */
	ARCHIVE_ARTIFACTS("archiveArtifacts","https://jenkins.io/doc/pipeline/steps/core/#-archiveartifacts-%20archive%20the%20artifacts"),
	
	
	IS_UNIX("isUnix","https://jenkins.io/doc/pipeline/steps/workflow-basic-steps/#isunix-checks-if-running-on-a-unix-like-node"),
	
	MAIL("mail","https://jenkins.io/doc/pipeline/steps/workflow-basic-steps/#mail-mail"),
	
	STEP("step","https://jenkins.io/doc/pipeline/steps/workflow-basic-steps/#step-general-build-step"),
	
	ANY("any"),
	NONE("none"),
	IMAGE("image"),
	LABEL("label"),
	ARGS("args"),
	CUSTOM_WORKSPACE("customWorkspace"),
	REUSE_NODE("reuseNode"),
	
	ECHO("echo","https://jenkins.io/doc/pipeline/steps/workflow-basic-steps/#echo-print-message"),
	
	SH("sh","https://jenkins.io/doc/pipeline/steps/workflow-durable-task-step/#sh-shell-script"),
	BAT("bat","https://jenkins.io/doc/pipeline/steps/workflow-durable-task-step/#-bat-%20windows%20batch%20script"),
	POWERSHELL("powershell","https://jenkins.io/doc/pipeline/steps/workflow-durable-task-step/#powershell-powershell-script"),
	
	CREDENTIALS("credentials"),
	
	BUILDDISCARDER("buildDiscarder"),
	
	DISABLECONCURRENTBUILDS("disableConcurrentBuilds"),
	
	OVERRIDEINDEXTRIGGERS("overrideIndexTriggers"),
	
	SKIPDEFAULTCHECKOUT("skipDefaultCheckout"),
	
	SKIPSTAGESAFTERUNSTABLE("skipStagesAfterUnstable"),
	
	TIMEOUT("timeout"),
	
	RETRY("retry"),
	
	TIMESTAMPS("timestamps"),
	
	
	/* available parameters */
	PARAM_STRING("string"),
	
	PARAM_BOOLEAN("booleanParam"),
	
	/* triggers */
	CRON("cron"),
	
	POLLSCM("pollSCM"),
	
	/* tools */
	MAVEN("maven"),
	JDK("jdk"),
	GRADLE("gradle"),
	
	
	;

	private String text;
	private String linkToDocumentation;
	private String tooltip;
	
	private JenkinsSpecialVariableKeyWords(String text) {
		this(text,null);
	}
	private JenkinsSpecialVariableKeyWords(String text, String linkToDocumentation) {
		this.text = text;
		this.linkToDocumentation=linkToDocumentation;
		this.tooltip=TooltipTextSupport.getTooltipText(name().toLowerCase());
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
}
