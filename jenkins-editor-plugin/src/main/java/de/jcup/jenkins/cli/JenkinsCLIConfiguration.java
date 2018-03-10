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

import java.util.LinkedHashSet;
import java.util.Set;

public class JenkinsCLIConfiguration {

	private String JenkinsURL;
	private String pathToPrivateKeyFile;
	private String pathToJenkinsCLIJar;
	private String user;

	private int timeoutInSeconds;
	private boolean sshEnabled;
	private String apiToken;
	private String password;

	public enum AuthMode{
		API_TOKEN("apitoken"),
		
		PASSWORD("secret"),
		
		PRIVATE_KEY("privatekey"),
		
		ANONYMOUS("anonymous");

		private String id;

		private AuthMode(String id){
			this.id=id;
		}
		
		public String getId() {
			return id;
		}
		
	}
	
	private AuthMode authMode;
	private boolean certificateCheckDisabled;
	private Set<String> systemProxyProperties;
	
	public void setAuthMode(AuthMode authMode) {
		this.authMode = authMode;
	}
	
	public AuthMode getAuthMode() {
		if (authMode==null){
			authMode=AuthMode.API_TOKEN;
		}
		return authMode;
	}
	
	public void setSSHenabled(boolean sshEnabled) {
		this.sshEnabled = sshEnabled;
	}

	public boolean isSSHenabled() {
		return sshEnabled;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getUser() {
		return user;
	}

	public String getJenkinsURL() {
		return JenkinsURL;
	}

	public void setJenkinsURL(String hostname) {
		this.JenkinsURL = hostname;
	}

	public String getPathToPrivateKeyFile() {
		return pathToPrivateKeyFile;
	}

	public void setPathToPrivateKeyFile(String pathToPrivatekey) {
		this.pathToPrivateKeyFile = pathToPrivatekey;
	}

	public void setTimeoutInSeconds(int timeoutInSeconds) {
		this.timeoutInSeconds = timeoutInSeconds;
	}

	public int getTimeOutInSeconds() {
		return timeoutInSeconds;
	}

	public String getPathToJenkinsCLIJar() {
		return pathToJenkinsCLIJar;
	}

	public void setPathToJenkinsCLIJar(String pathToJenkinsCLIJar) {
		this.pathToJenkinsCLIJar = pathToJenkinsCLIJar;
	}

	public void setAPIToken(String apiToken) {
		this.apiToken = apiToken;
	}

	public String getAPIToken() {
		return apiToken;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getPassword() {
		return password;
	}

	public void setCertificateCheckDisabled(boolean certificateCheckDisabled) {
		this.certificateCheckDisabled = certificateCheckDisabled;
	}
	
	public boolean isCertificateCheckDisabled() {
		return certificateCheckDisabled;
	}

	public void setProxySystemProperties(Set<String> systemProperties) {
		systemProxyProperties=systemProperties;
	}
	
	public Set<String> getSystemProxyProperties() {
		if (systemProxyProperties==null){
			return new LinkedHashSet<>();
		}
		return systemProxyProperties;
	}
	
}
