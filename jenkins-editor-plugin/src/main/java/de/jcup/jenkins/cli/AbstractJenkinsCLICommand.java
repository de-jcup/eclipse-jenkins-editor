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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import de.jcup.jenkins.cli.AuthModeChecker.AuthModeCheckResult;
import de.jcup.jenkins.cli.JenkinsCLIConfiguration.AuthMode;

public abstract class AbstractJenkinsCLICommand<T extends JenkinsCLIResult, P> implements JenkinsCLICommand<T, P> {
	private static final String PROXY_PASSWORD = "proxyPassword=";
	private final static boolean DEBUG = Boolean.valueOf(System.getProperty("de.jcup.jenkins.cli.command.debug"));

	protected abstract String[] getCLICommands();

	/**
	 * Execute the jenkins CLI command
	 * 
	 * @param configuration
	 *            jenkins CLI configuration
	 * @param parameter
	 *            parameter for the command
	 */
	public final T execute(JenkinsCLIConfiguration configuration, P parameter) throws IOException {
	    AuthModeChecker checker = new AuthModeChecker();
	    AuthModeCheckResult authModeCheckResult = checker.checkAuthModeDataAvailable(configuration);
	    if (authModeCheckResult.failed) {
	        throw new AuthModeCheckResultIOException(authModeCheckResult);
	    }
		CLIJarCommandMessageBuilder<P> mb = new CLIJarCommandMessageBuilder<P>(this, configuration, parameter);

		if (DEBUG) {
			debug("execute:" + mb.buildMessage());
		}
		
		
		
		String[] commands = createCommands(configuration, parameter, false);
		List<String> list = Arrays.asList(commands).stream().filter(content -> content!=null && !content.isEmpty()).collect(Collectors.toList());

		ProcessBuilder pb = new ProcessBuilder();
		pb.command(list);

		Process process = pb.start();
		int timeOut = configuration.getTimeOutInSeconds();
		if (timeOut > 0) {
			JenkinsCommandTimeoutChecker timeOutChecker = new JenkinsCommandTimeoutChecker(process, timeOut);
			Thread t = new Thread(timeOutChecker);
			t.setName("Jenkins command [" + getCLICommands() + "] timeout checker[" + timeOut + " seconds]");
			t.start();
		}
		T result = handleStartedProcess(process, parameter, mb);
		return result;
	}

	protected void debug(String string) {
		System.out.println("DEBUG: execute:" + string);
	}

	  protected void writeCode(Process process, String code) throws IOException {
	        /* give code data as input */
	        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()))) {
	            bw.write(code);
	        }
	    }
	
	protected abstract T handleStartedProcess(Process process, P parameter, CLIJarCommandMessageBuilder<P> mb)
			throws IOException;

	protected String[] createCommands(JenkinsCLIConfiguration configuration, P parameter, boolean hidePasswords) {
		return createExecutionStrings(configuration, hidePasswords);
	}

	protected String[] createExecutionStrings(JenkinsCLIConfiguration configuration, boolean hidePasswords,
			String... parameters) {
		List<String> list = new ArrayList<>();
		list.add("java");
		addSystemProxyProperties(list, configuration, hidePasswords);
		list.add("-jar");
		list.add(configuration.getPathToJenkinsCLIJar());

		if (configuration.getJenkinsURL() != null) {
			list.add("-s");
			list.add(configuration.getJenkinsURL());
		}

		addOptions(configuration, hidePasswords, list);
		// special handling for secret auth mode
		if (AuthMode.SECRET.equals(configuration.getAuthMode())) {
			list.add("--username");
			list.add("" + configuration.getUser());
			list.add("--secret");
			if (hidePasswords) {
				/* when for output we do NOT show the password... */
				list.add("**********");
			} else {
				list.add("" + configuration.getPassword());
			}
		}
		for (String cliCommand: getCLICommands()) {
		    if (cliCommand==null || cliCommand.isEmpty()) {
		        continue;
		    }
            list.add(cliCommand);
        }
		addParameters(list, parameters);

		return list.toArray(new String[list.size()]);
	}

	private void addSystemProxyProperties(List<String> list, JenkinsCLIConfiguration configuration,
			boolean hidePasswords) {

		Set<String> systemProxyProperties = configuration.getSystemProxyProperties();
		for (String property : systemProxyProperties) {
			if (property == null) {
				continue;
			}
			int index = property.indexOf(PROXY_PASSWORD);
			if (hidePasswords && index != -1) {
				String sub = property.substring(0,index+PROXY_PASSWORD.length());
				String newProperty = sub+"******";
				list.add(newProperty);
			}else{
				list.add(property);
			}
		}

	}

	protected void addParameters(List<String> list, String... parameters) {
		for (String parameter : parameters) {
			if (parameter == null) {
				continue;
			}
			list.add(parameter);
		}
	}

	protected void addOptions(JenkinsCLIConfiguration configuration, boolean hidePasswords, List<String> list) {
		/* ------------------ */
		/* - OPTIONS (opts) - */
		/* ------------------ */
		String pathToPrivateKeyFile = configuration.getPathToPrivateKeyFile();
		String user = configuration.getUser();

		AuthMode authMode = configuration.getAuthMode();
		if (authMode==AuthMode.SSH) {
			list.add("-ssh");
            list.add("-user");
            list.add(user);
		} else {
			list.add("-http");
		}
		String proxyParam = configuration.getProxyParameter();
		if (proxyParam != null && !proxyParam.isEmpty()) {
			list.add("-p");
			list.add(proxyParam);
		}
		if (configuration.isCertificateCheckDisabled()) {
			list.add("-noCertificateCheck");
		}

		switch (authMode) {
		case API_TOKEN:
			String apiToken = configuration.getAPIToken();
			list.add("-auth");
			StringBuilder authSb = new StringBuilder();
			authSb.append(user).append(":");
			if (hidePasswords) {
				authSb.append("*******");
			} else {
				authSb.append(apiToken);
			}
			list.add(authSb.toString());
			break;
		case SSH:
            break;
		case SECRET:
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
	
	protected void waitForProcessTermination(Process process) {
        while (process.isAlive()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    
    protected void fetchResult(Process process, AbstractJenkinsCLIResult result) throws IOException {
        /* fetch output to result */
        try (BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line = null;
            while ((line = br.readLine()) != null) {
                result.appendOutput(line);
            }
        }
        try (BufferedReader br = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
            String line = null;
            while ((line = br.readLine()) != null) {
                result.appendOutput(line);
            }
        }
    }
    
    protected void handleExitCode(CLIJarCommandMessageBuilder<String> mb, AbstractJenkinsCLIResult result, int exitValue) {
        result.exitCode = exitValue;
        if (!result.wasCLICallSuccessFul()) {

            result.cliCallFailureMessage = buildNoAccessToJenkinsMessage(mb);
        }
    }

    protected String buildNoAccessToJenkinsMessage(CLIJarCommandMessageBuilder<String> mb) {
        StringBuilder sb = new StringBuilder();
        sb.append("Access to Jenkins was not possible by command:\n\n");
        sb.append(mb.buildMessage()).append("\n\n");
        sb.append("Maybe credentials not valid or hostname/firewall problems.\n");
        sb.append("Please check Jenkins CLI setup in preferences");
        return sb.toString();
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
					Thread.currentThread().interrupt();
				}
			}
		}

	}
}
