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

import de.jcup.jenkins.util.SystemAdapter;

/**
 * A simple URL provider which supports JENKINS_URL and a standard fallback for
 * localhost
 * 
 * @author Albert Tregnaghi
 *
 */
public class JenkinsDefaultURLProvider {
	SystemAdapter systemAdapter = new SystemAdapter();

	/**
	 * @return a default jenkins url. If JENKINS_URL is defined as system
	 *         variable (env) this value will be returned, otherwise a simple
	 *         "http://localhost:8080" will be returned
	 */
	public String getDefaultJenkinsURL() {
		String jenkinsURL = getJENKINS_URL();
		if (jenkinsURL != null && jenkinsURL.trim().length() > 0) {
			return jenkinsURL;
		}
		return getLocalhostURL();
	}

	public String createDefaultURLDescription() {
		String jenkinsURL = getJENKINS_URL();
		if (jenkinsURL != null) {
			return "JENKINS_URL defined as " + jenkinsURL + ". Using this as default value";
		}
		return "No JENKINS_URL env defined, so using " + getLocalhostURL() + " as default value";
	}

	protected String getLocalhostURL() {
		return "http://localhost:8080";
	}

	protected String getJENKINS_URL() {
		return systemAdapter.getEnv("JENKINS_URL");
	}
}
