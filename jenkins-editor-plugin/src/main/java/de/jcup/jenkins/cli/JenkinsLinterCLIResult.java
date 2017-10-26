package de.jcup.jenkins.cli;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class JenkinsLinterCLIResult implements JenkinsCLIResult{

	
	File fileToLint;
	
	private List<String> list = new ArrayList<>();

	void appendOutput(String line) {
		list.add(line);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("File:");
		sb.append(fileToLint);
		sb.append('\n');
		for (String string: list){
			sb.append(string);
			sb.append('\n');
		}
		return sb.toString();
	}

	@Override
	public boolean hasErrors() {
		return list.size()!=1; // one message is always returned
	}

}
