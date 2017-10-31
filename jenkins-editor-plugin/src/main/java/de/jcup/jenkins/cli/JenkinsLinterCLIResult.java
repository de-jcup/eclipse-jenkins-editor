package de.jcup.jenkins.cli;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JenkinsLinterCLIResult extends AbstractJenkinsCLIResult{
	
	private List<String> list = new ArrayList<>();
	

	void appendOutput(String line) {
		list.add(line);
	}
	
	public List<String> getLines(){
		return Collections.unmodifiableList(list);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName());
		sb.append(":\n");
		for (String string: list){
			sb.append(string);
			sb.append('\n');
		}
		return sb.toString();
	}
}
