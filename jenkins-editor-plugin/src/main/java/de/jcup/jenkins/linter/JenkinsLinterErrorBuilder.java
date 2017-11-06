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
