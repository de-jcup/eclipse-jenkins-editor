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
	protected JenkinsLinterCLIResult handleStartedProcess(Process process, String code) throws IOException {

		JenkinsLinterCLIResult result = new JenkinsLinterCLIResult();
		if (!process.isAlive()) {
			int exitValue = process.exitValue();
			result.exitValue = exitValue;
			result.cliCallFailureMessage = "Was not able to start process.";
			return result;
		}
		/* give code data as input */
		try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()))) {
			bw.write(code);
		}
		/* fetch output to result */
		try (BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
			String line = null;
			while ((line = br.readLine()) != null) {
				result.appendOutput(line);
			}
		}
		while (process.isAlive()) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				break;
			}
		}
		int exitValue = process.exitValue();
		if (exitValue != 0) {
			if (exitValue == -1) {
				result.cliCallFailureMessage = "Access to Jenkins was not possible.\nMaybe credentials not valid or hostname/firewall problems.\nPlease check Jenkins CLI setup in preferences";
			}
		}
		result.exitValue = exitValue;
		return result;
	}

}
