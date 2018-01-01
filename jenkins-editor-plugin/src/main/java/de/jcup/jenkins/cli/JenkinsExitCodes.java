package de.jcup.jenkins.cli;

public enum JenkinsExitCodes {
	OK(0),
	LINTER_FAILURES(1);
	
	private int exitCode;

	private JenkinsExitCodes(int exitCode){
		this.exitCode=exitCode;
	}

	public int getExitCode() {
		return exitCode;
	}
}
