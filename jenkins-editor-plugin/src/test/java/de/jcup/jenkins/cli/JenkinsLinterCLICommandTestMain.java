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

import de.jcup.jenkins.cli.JenkinsCLIConfiguration.AuthMode;

public class JenkinsLinterCLICommandTestMain {

	/* just an api key for an temporary test jenkins*/
	
	public static void main(String[] args) throws Exception {
		if (args.length!=2){
			throw new IllegalArgumentException("Call with parameters:'userName apiKEY' !");
		}
		String user=args[0];
		String apiKey= args[1];
		JenkinsLinterCLICommandTestMain testMain =  new JenkinsLinterCLICommandTestMain();
		testMain.test(user, apiKey);
		
	}
	
	public void test(String user, String apiToken) throws Exception{
		// https://jenkins.io/doc/book/managing/cli/
		JenkinsCLIConfiguration config = new JenkinsCLIConfiguration();
		config.setJenkinsURL("http://localhost:8080");
		config.setTimeoutInSeconds(10);
		config.setPathToJenkinsCLIJar("./lib/jenkins-cli.jar");
		config.setAuthMode(AuthMode.API_TOKEN);
		config.setUser(user);
		config.setAPIToken(apiToken);
		
		JenkinsLinterCLICommand cmd = new JenkinsLinterCLICommand();
		JenkinsLinterCLIResult result = cmd.execute(config,"pipeline{}");
	
		System.out.println("result:"+ result.toString());
	}
	
}
