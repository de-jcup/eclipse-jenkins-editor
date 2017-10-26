package de.jcup.jenkins.linter;

public class JenkinsLinterError {
	String message;
	int line;
	int column;
	
	public int getLine() {
		return line;
	}
	public int getColumn() {
		return column;
	}
	
	public String getMessage() {
		return message;
	}
}
