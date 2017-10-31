package de.jcup.jenkins.cli;

public interface JenkinsCLIResult {

	public boolean wasCLICallSuccessFul();
	
	public String getCLICallFailureMessage();
}
