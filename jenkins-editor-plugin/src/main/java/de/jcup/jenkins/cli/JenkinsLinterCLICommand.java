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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import de.jcup.jenkins.util.JenkinsLogAdapter;

/**
 * Transfers given code on execution time to jenkins server for validation
 * 
 * @author Albert Tregnaghi
 *
 */
public class JenkinsLinterCLICommand extends AbstractJenkinsCLICommand<JenkinsLinterCLIResult, String> {

	@Override
	public String getCLICommand() {
		return "declarative-linter";
	}
@Override
protected JenkinsLinterCLIResult handleStartedProcess(Process process, String code,
		CLIJarCommandMessageBuilder<String> mb) throws IOException {

		JenkinsLinterCLIResult result = new JenkinsLinterCLIResult();
		if (!process.isAlive()) {
			int exitValue = process.exitValue();
			result.exitCode = exitValue;
			result.cliCallFailureMessage = "Was not able to start linter process.linter.\nMaybe server is down or CLI credentials are not set correctly.";
			return result;
		}
		int exitValue =  result.exitCode;
		try{
			writeCode(process, code);
			fetchResult(process, result);
			waitForProcessTermination(process);
			exitValue = process.exitValue();
		}catch(IOException e){
			JenkinsLogAdapter.INSTANCE.logError("IO problems on executing jenkins linter command", e);
		}
		result.exitCode = exitValue;
		if (! result.wasCLICallSuccessFul()) {
			
			result.cliCallFailureMessage = buildNoAccessToJenkinsMessage(mb);
		}
		return result;
	}

	private String buildNoAccessToJenkinsMessage(CLIJarCommandMessageBuilder<String> mb) {
		StringBuilder sb = new StringBuilder();
		sb.append("Access to Jenkins was not possible by command:\n\n");
		sb.append(mb.buildMessage()).append("\n\n");
		sb.append("Maybe credentials not valid or hostname/firewall problems.\n");
		sb.append("Please check Jenkins CLI setup in preferences");
		return sb.toString();
	}

	protected void waitForProcessTermination(Process process) {
		while (process.isAlive()) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				break;
			}
		}
	}

	protected void fetchResult(Process process, JenkinsLinterCLIResult result) throws IOException {
		/* fetch output to result */
		try (BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
			String line = null;
			while ((line = br.readLine()) != null) {
				result.appendOutput(line);
			}
		}
	}

	protected void writeCode(Process process, String code) throws IOException {
		/* give code data as input */
		try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()))) {
			bw.write(code);
		}
	}

}
