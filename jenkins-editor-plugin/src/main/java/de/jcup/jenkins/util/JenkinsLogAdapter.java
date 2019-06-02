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
 package de.jcup.jenkins.util;

/*
 * Adapter for logging - prevent dependency to eclipse parts on gradle build...
 */
public class JenkinsLogAdapter implements JenkinsLogAdaptable {

	public static final JenkinsLogAdapter INSTANCE = new JenkinsLogAdapter();

	JenkinsLogAdapter() {
		// only for test
	}

	public void delegatesTo(JenkinsLogAdaptable logsupport) {
		log = logsupport;
	}

	private JenkinsLogAdaptable log;

	@Override
	public void logInfo(String info) {
		getLog().logInfo(info);

	}

	@Override
	public void logWarning(String warning) {
		getLog().logWarning(warning);

	}

	@Override
	public void logError(String error, Throwable t) {
		getLog().logError(error, t);

	}

	private JenkinsLogAdaptable getLog() {
		if (log == null) {
			log = new SystemOutLogSupport();
		}
		return log;
	}

	private class SystemOutLogSupport implements JenkinsLogAdaptable {

		@Override
		public void logInfo(String info) {
			System.out.println("info:" + info);

		}

		@Override
		public void logWarning(String warning) {
			System.out.println("warn:" + warning);
		}

		@Override
		public void logError(String error, Throwable t) {
			System.err.println(error);
			t.printStackTrace();
		}

	}

}
