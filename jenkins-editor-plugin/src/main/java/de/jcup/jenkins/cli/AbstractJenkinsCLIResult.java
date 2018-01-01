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
 package de.jcup.jenkins.cli;

public abstract class AbstractJenkinsCLIResult implements JenkinsCLIResult {
	int exitCode = -2;
	String cliCallFailureMessage = "No dedicated failure message available";

	public int getExitCode() {
		return exitCode;
	}

	public String getCLICallFailureMessage() {
		return cliCallFailureMessage;
	}
	
	protected boolean isExitCode(JenkinsExitCodes expected){
		return expected!=null && expected.getExitCode()==exitCode;
	}
}
