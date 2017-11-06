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
