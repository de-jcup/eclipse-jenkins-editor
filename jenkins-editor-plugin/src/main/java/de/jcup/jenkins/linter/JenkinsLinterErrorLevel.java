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

public enum JenkinsLinterErrorLevel {

	INFO("info"),
	
	WARNING("warning"), 

	ERROR("error"), 
	;
	

	private String id;

	private JenkinsLinterErrorLevel(String id) {
		if (id == null) {
			throw new IllegalArgumentException("id may not be null!");
		}
		this.id = id;
	}

	public String getId() {
		return id;
	}


	/**
	 * @param id
	 * @return error level, never <code>null</code>. If not identifiable by id the default will be returned: {@link JenkinsLinterErrorLevel#ERROR}
	 */
	public static JenkinsLinterErrorLevel fromId(String id) {
		if (WARNING.getId().equals(id)) {
			return WARNING;
		}
		if (INFO.getId().equals(id)) {
			return INFO;
		}
		return ERROR;
	}
}
