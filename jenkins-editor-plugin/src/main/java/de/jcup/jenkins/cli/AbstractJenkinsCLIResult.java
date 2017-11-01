package de.jcup.jenkins.cli;

public class AbstractJenkinsCLIResult implements JenkinsCLIResult {
	int exitValue = -2;
	String cliCallFailureMessage = "undefined";

	public boolean wasCLICallSuccessFul() {
		return exitValue >=0;
	}

	public int getExitValue() {
		return exitValue;
	}

	public String getCLICallFailureMessage() {
		return cliCallFailureMessage;
	}
}
