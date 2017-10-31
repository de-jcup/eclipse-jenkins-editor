package de.jcup.jenkins.linter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JenkinsLinterErrorBuilder {

	private static final Pattern ERROR_PATTERN = Pattern.compile("@ line ([0-9]+), column ([0-9]+)");
	
	/**
	 * Detects error information in given line.
	 * 
	 * @param line
	 * @return If there is an error information detected an error object is
	 *         build and returned, otherwise <code>null</code>
	 */
	public JenkinsLinterError build(String line) {
		if (line==null){
			return null;
		}
		Matcher matcher = ERROR_PATTERN.matcher(line);
		if (! matcher.find()){
			return null;
		}
		
		JenkinsLinterError error = new JenkinsLinterError();
		error.message=line;
		error.line = Integer.valueOf(matcher.group(1));
		error.column = Integer.valueOf(matcher.group(2));
		
		return error;
	}
}
