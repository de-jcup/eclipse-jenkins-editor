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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.jcup.jenkins.cli.JenkinsCLIConfiguration.AuthMode;

public abstract class AbstractJenkinsCLICommand<T extends JenkinsCLIResult, P> implements JenkinsCLICommand<T, P> {
	final static boolean DEBUG = Boolean.valueOf(System.getProperty("de.jcup.jenkins.cli.command.debug"));

	protected abstract String getCLICommand();

	/**
	 * Execute the jenkins CLI command
	 * 
	 * @param configuration
	 *            jenkins CLI configuration
	 * @param parameter
	 *            parameter for the command
	 */
	public final T execute(JenkinsCLIConfiguration configuration, P parameter) throws IOException {

		String[] commands = createCommands(configuration, parameter);
		List<String> list = Arrays.asList(commands);
		if (DEBUG) {
			StringBuilder sb = new StringBuilder();
			for (String s : list) {
				sb.append(s);
				sb.append(" ");
			}
			debug(sb.toString());
		}

		ProcessBuilder pb = new ProcessBuilder();
		pb.command(list);
		Process process = pb.start();
		int timeOut = configuration.getTimeOutInSeconds();
		if (timeOut > 0) {
			JenkinsCommandTimeoutChecker timeOutChecker = new JenkinsCommandTimeoutChecker(process, timeOut);
			Thread t = new Thread(timeOutChecker);
			t.setName("Jenkins command [" + getCLICommand() + "] timeout checker[" + timeOut + " seconds]");
			t.start();
		}
		return handleStartedProcess(process, parameter);

	}

	protected void debug(String string) {
		System.out.println("DEBUG: execute:" + string);
	}

	protected abstract T handleStartedProcess(Process process, P parameter) throws IOException;

	protected String[] createCommands(JenkinsCLIConfiguration configuration, P parameter) {
		return createExecutionStrings(configuration);
	}

	protected String[] createExecutionStrings(JenkinsCLIConfiguration configuration, String... parameters) {
		List<String> list = new ArrayList<>();
		/* FIXME ATR: use pathToCurrentJavaUsedByEclispse.java... ? */
		list.add("java");
		list.add("-jar");
		list.add(configuration.getPathToJenkinsCLIJar());

		list.add("-s");
		list.add(configuration.getJenkinsURL());

		addOptions(configuration, list);

		list.add(getCLICommand());

		// special handling for secret auth mode
		if (AuthMode.PASSWORD.equals(configuration.getAuthMode())) {
			list.add("--username");
			list.add("" + configuration.getUser());
			list.add("--secret");
			list.add("" + configuration.getPassword());
		}

		addParameters(list, parameters);
		return list.toArray(new String[list.size()]);
	}

	protected void addParameters(List<String> list, String... parameters) {
		for (String parameter : parameters) {
			if (parameter == null) {
				continue;
			}
			list.add(parameter);
		}
	}

	protected void addOptions(JenkinsCLIConfiguration configuration, List<String> list) {
		/* ------------------ */
		/* - OPTIONS (opts) - */
		/* ------------------ */
		String pathToPrivateKeyFile = configuration.getPathToPrivateKeyFile();
		String user = configuration.getUser();

		if (configuration.isSSHenabled()) {
			list.add("-ssh");
		}

		AuthMode authMode = configuration.getAuthMode();
		switch (authMode) {
		case API_TOKEN:
			String apiToken = configuration.getAPIToken();
			list.add("-auth");
			list.add(user + ":" + apiToken);
			break;
		case PASSWORD:
			// done later:
			break;
		case PRIVATE_KEY:
			list.add("-i");
			list.add(pathToPrivateKeyFile);
			break;
		case ANONYMOUS:
			list.add("-noKeyAuth");

		}
	}

	private class JenkinsCommandTimeoutChecker implements Runnable {

		private Process process;
		private int timeOutInMilliSeconds;

		private JenkinsCommandTimeoutChecker(Process process, int timeOutInSeconds) {
			this.process = process;
			this.timeOutInMilliSeconds = timeOutInSeconds * 1000;
		}

		@Override
		public void run() {
			long startTime = System.currentTimeMillis();
			while (process.isAlive()) {
				try {
					Thread.sleep(300);
					long diff = System.currentTimeMillis() - startTime;
					if (diff > timeOutInMilliSeconds) {
						process.destroy();
						if (DEBUG) {
							System.out.println("process destroyed by timeout checker!");
						}
					}
				} catch (InterruptedException e) {
					/* ignore */
					break;
				}
			}
		}

	}
}
