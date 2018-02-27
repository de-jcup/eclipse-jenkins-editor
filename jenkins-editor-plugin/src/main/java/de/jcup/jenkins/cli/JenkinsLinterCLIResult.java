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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static de.jcup.jenkins.cli.JenkinsExitCodes.*;

public class JenkinsLinterCLIResult extends AbstractJenkinsCLIResult{
	
	private List<String> list = new ArrayList<>();
	

	void appendOutput(String line) {
		list.add(line);
	}
	
	public List<String> getLines(){
		return Collections.unmodifiableList(list);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName());
		sb.append(":\n");
		sb.append("- exitCode:"+exitCode).append(" (").append(exitCodeDescription(exitCode)).append(")");
		sb.append("\n");
		sb.append("- failureMessage:"+getCLICallFailureMessage());
		sb.append("\n-Output was:\n");
		
		for (String string: list){
			sb.append(string);
			sb.append('\n');
		}
		return sb.toString();
	}

	private String exitCodeDescription(int exitCode) {
		if (exitCode==-2){
			return "Linter was not executed at all";
		}
		for (JenkinsExitCodes code: JenkinsExitCodes.values()){
			if (code.getExitCode()==exitCode){
				return code.name();
			}
		}
		return "No explaination available";
	}

	@Override
	public boolean wasCLICallSuccessFul() {
		return isExitCode(OK) || isExitCode(LINTER_FAILURES);
	}
}
