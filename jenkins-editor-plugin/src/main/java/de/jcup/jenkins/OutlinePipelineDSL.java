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
 package de.jcup.jenkins;

/**
 * Pipeline DSL enum containing parts rendered in outline.
 * See https://jenkins.io/doc/book/pipeline/syntax/
 * @author Albert Tregnaghi
 *
 */
public enum OutlinePipelineDSL{
	/* @formatter:off*/
		AGENT,
			DOCKER,
			DOCKERFILE,
		PARALLEL,
		PIPELINE,
		SCRIPT,
		STAGE,
		STAGES,
		STEPS,
		TOOLS,
		TRIGGERS,
		WHEN,
			ENVIRONMENT,
			EXPRESSION,
			NOT,
			ALLOF,
			ANYOF,
		
		OPTIONS,
		PARAMETERS,
		
		NODE,
		
		POST,
			ALWAYS,
			FAILURE,
			CHANGED,
			SUCCESS,
			UNSTABLE,
			ABORTED,
			
		INPUT,
		
		;
		/* @formatter:on*/
		
		private String path;
		private String id;
		
		private OutlinePipelineDSL(){
			path="/icons/jenkinseditor/outline/dsl/"+name().toLowerCase()+".png";
			id=name().toLowerCase();
		}
		
		public String getRelativePathInsidePlugin(){
			return path;
		}
		
		public static final OutlinePipelineDSL tryToFind(String lowerCasedId){
			for (OutlinePipelineDSL dsl: values()){
				if (dsl.id.equals(lowerCasedId)){
					return dsl;
				}
			}
			return null;
		}
	}