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
 * See https://jenkins.io/doc/book/pipeline/syntax/ for dedicated information about syntax
 * @author Albert Tregnaghi
 *
 */
public enum JenkinsDefaultClosureKeyWords implements DocumentKeyWord {
	PIPELINE("pipeline"),
	
	AGENT("agent"),

	NODE("node"),
	
	DOCKER("docker"),
	
	DOCKERFILE("dockerfile"),
	
	/* -------------------------------------------- */
	/* ---------------- POST parts ---------------- */
	/* -------------------------------------------- */
	POST("post"),
	
	/* conditions */
	ALWAYS("always"),
	
	CHANGED("changed"),
	
	FAILURE("failure"),
	
	SUCCESS("success"),
	
	UNSTABLE("unstable"),
	
	ABORTED("aborted"),
	
	/* -------------------------------------------- */
	/* ---------------- STAGES parts -------------- */
	/* -------------------------------------------- */
	STAGES("stages"),

	STAGE("stage"),
	
	STEPS("steps"),
	
	
	/* -------------------------------------------- */
	/* ---------------- ENVIRONMENT parts --------- */
	/* -------------------------------------------- */
	ENVIRONMENT("environment"),
	
	/* -------------------------------------------- */
	/* ---------------- OPTION parts -------------- */
	/* -------------------------------------------- */
	OPTIONS("options"),// 0:1 inside pipeline block
	
	/* -------------------------------------------- */
	/* ---------------- PARAM parts -------------- */
	/* -------------------------------------------- */
	PARAMETERS("parameters"),
	
	/* -------------------------------------------- */
	/* ---------------- TRIGGER parts ------------- */
	/* -------------------------------------------- */
	TRIGGERS("triggers"),
	
	/* -------------------------------------------- */
	/* ---------------- TOOLS parts --------------- */
	/* -------------------------------------------- */
	TOOLS("tools"),
	
	/* -------------------------------------------- */
	/* ---------------- WHEN parts ---------------- */
	/* -------------------------------------------- */
	WHEN("when"),

	BRANCH("branch"),
	// environment
	EXPRESSION("expression"),
	NOT("not"),
	ALLOF("allOf"),
	ANYOF("anyOf"),
	
	/* -------------------------------------------- */
	/* ---------------- PARALLEL parts ------------ */
	/* -------------------------------------------- */
	PARALLEL("parallel"),
	
	
	/* -------------------------------------------- */
	/* ---------------- SCRIPT parts -------------- */
	/* -------------------------------------------- */
	SCRIPT("script"),
	
	
	;
	private String text;

	private JenkinsDefaultClosureKeyWords(String text) {
		this.text = text;
	}


	@Override
	public String getText() {
		return text;
	}
}
