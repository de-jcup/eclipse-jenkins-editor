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

import de.jcup.egradle.eclipse.document.DocumentKeyWord;

/**
 * See https://jenkins.io/doc/book/pipeline/syntax/ for syntax information
 * @author Albert Tregnaghi
 *
 */
public enum JenkinsSpecialVariableKeyWords implements DocumentKeyWord {

	SYSTEM("System"),
	
	JUNIT("junit"),
	
	
	/* agent parameters: */
	
	ANY("any"),
	NONE("none"),
	IMAGE("image"),
	LABEL("label"),
	ARGS("args"),
	CUSTOM_WORKSPACE("customWorkspace"),
	REUSE_NODE("reuseNode"),
	
	ECHO("echo"),
	SH("sh"),
	
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

	private JenkinsSpecialVariableKeyWords(String text) {
		this.text = text;
	}


	@Override
	public String getText() {
		return text;
	}
}
