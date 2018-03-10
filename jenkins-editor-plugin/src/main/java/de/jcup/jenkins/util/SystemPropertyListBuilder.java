package de.jcup.jenkins.util;

public class SystemPropertyListBuilder {
	
	
	public String build(String type, String key, String value){
		StringBuilder sb = new StringBuilder();
		sb.append("-D");
		sb.append(type);
		sb.append('.');
		sb.append(key);
		sb.append('=');
		sb.append(value);
		return sb.toString();
	}
}
