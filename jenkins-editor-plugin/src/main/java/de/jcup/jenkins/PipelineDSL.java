package de.jcup.jenkins;

/**
 * See https://jenkins.io/doc/book/pipeline/syntax/
 * @author Albert Tregnaghi
 *
 */
public enum PipelineDSL{
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
			ABORTED
		
		,;
		/* @formatter:on*/
		
		private String path;
		
		private PipelineDSL(){
			path="/icons/jenkinseditor/outline/dsl/"+name().toLowerCase()+".png";
		}
		
		public String getRelativePathInsidePlugin(){
			return path;
		}
	}