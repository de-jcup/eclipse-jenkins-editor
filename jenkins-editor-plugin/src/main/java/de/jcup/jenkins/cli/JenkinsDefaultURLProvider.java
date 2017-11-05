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
