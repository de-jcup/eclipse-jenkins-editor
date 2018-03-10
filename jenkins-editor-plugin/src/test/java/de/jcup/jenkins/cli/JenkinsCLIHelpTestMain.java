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

import java.io.IOException;

import de.jcup.jenkins.cli.JenkinsCLIConfiguration.AuthMode;

public class JenkinsCLIHelpTestMain {

	/* just an api key for an temporary test jenkins*/
	
	public static void main(String[] args) throws Exception {
		JenkinsCLIHelpTestMain testMain =  new JenkinsCLIHelpTestMain();
		testMain.test();
		
	}
	
	public void test() throws Exception{
		// https://jenkins.io/doc/book/managing/cli/
		
		HelpCommand cmd = new HelpCommand();
		JenkinsCLIConfiguration config = new JenkinsCLIConfiguration();
		config.setTimeoutInSeconds(10);
		config.setPathToJenkinsCLIJar("./lib/jenkins-cli.jar");
		
		JenkinsCLIResult result = cmd.execute(config ,"pipeline{}");
	
		System.out.println("result:"+ result.toString());
	}
	
	private class HelpCommand extends AbstractJenkinsCLICommand<JenkinsCLIResult, String>{

		@Override
		protected String getCLICommand() {
			return "help";
		}

		@Override
		protected JenkinsCLIResult handleStartedProcess(Process process, String parameter,
				CLIJarCommandMessageBuilder<String> mb) throws IOException {
			return new JenkinsHelpResult();
		}
		
	}
	
	private class JenkinsHelpResult extends AbstractJenkinsCLIResult{

		@Override
		public boolean wasCLICallSuccessFul() {
			return true;
		}
		
	}
	
}
