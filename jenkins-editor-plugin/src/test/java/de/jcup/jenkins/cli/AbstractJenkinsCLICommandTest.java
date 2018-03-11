package de.jcup.jenkins.cli;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import de.jcup.jenkins.cli.JenkinsCLIConfiguration.AuthMode;

public class AbstractJenkinsCLICommandTest {
	private static final String DHTTPS_PROXY_PASSWORD_MYSECRET2 = "-Dhttps.proxyPassword=mysecret2";
	private static final String DHTTP_PROXY_PASSWORD_MYSECRET1 = "-Dhttp.proxyPassword=mysecret1";
	private static final String USER = "user1";
	private static final String JENKINS_URL = "http://jenkinshostname.org:8080";
	private static final String API_TOKEN_DUMMY = "api2345";
	private static final String PASSWORD = "pwd12345";
	private static final String USER_API_TOKEN_COMBI = USER + ":" + API_TOKEN_DUMMY;

	private TestCommand commandToTest;
	private String parameter;
	private JenkinsCLIConfiguration configuration;
	private Set<String> systemProperties;

	@Before
	public void before() {
		commandToTest = new TestCommand();
		configuration = new JenkinsCLIConfiguration();
		parameter = "param1";

		systemProperties = new LinkedHashSet<>();
		systemProperties.add(DHTTP_PROXY_PASSWORD_MYSECRET1);
		systemProperties.add(DHTTPS_PROXY_PASSWORD_MYSECRET2);

		configuration.setAPIToken(API_TOKEN_DUMMY);
		configuration.setAuthMode(AuthMode.API_TOKEN);
		configuration.setCertificateCheckDisabled(true);
		configuration.setJenkinsURL(JENKINS_URL);
		configuration.setPassword(PASSWORD);
		configuration.setPathToJenkinsCLIJar("thePath");
		configuration.setUser(USER);
		configuration.setProxySystemProperties(systemProperties);
	}

	/*
	 * not the best way to test CliJarCommandMessageBuilder uses command to
	 * test, but currently we got no access to mockito in eclipse project...
	 * So doing it this simple way
	 */
	@Test
	public void jar_message_builder_uses_command_hidden_output() {
		/* prepare */
		String expected = "java -Dhttp.proxyPassword=****** -Dhttps.proxyPassword=****** -jar thePath -s http://jenkinshostname.org:8080 -http -noCertificateCheck -auth user1:******* theCommand ";
		
		/* execute */
		CLIJarCommandMessageBuilder<String> builder = new CLIJarCommandMessageBuilder<>(commandToTest, configuration,
				parameter);
		/* test */
		assertEquals(expected, builder.buildMessage());
	}

	@Test
	public void commands_NOT_hidden_expected_output_done() {
		/* prepare */
		String expected = "[java, -Dhttp.proxyPassword=mysecret1, -Dhttps.proxyPassword=mysecret2, -jar, thePath, -s, http://jenkinshostname.org:8080, -http, -noCertificateCheck, -auth, user1:api2345, theCommand]";
		
		/* execute */
		String[] commands = commandToTest.createCommands(configuration, parameter, false);
		List<String> list = Arrays.asList(commands);

		/* test */
		assertEquals(expected, list.toString());

	}

	@Test
	public void commands_hidden_expected_output_done() {
		/* prepare */
		String expected = "[java, -Dhttp.proxyPassword=******, -Dhttps.proxyPassword=******, -jar, thePath, -s, http://jenkinshostname.org:8080, -http, -noCertificateCheck, -auth, user1:*******, theCommand]";

		/* execute */
		String[] commands = commandToTest.createCommands(configuration, parameter, true);
		List<String> list = Arrays.asList(commands);

		/* test */
		assertEquals(expected, list.toString());

	}

	@Test
	public void commands_hidden_contains_no_http_proxy_passwords() throws Exception {
		/* execute */
		String[] commands = commandToTest.createCommands(configuration, parameter, true);

		/* test */
		List<String> list = Arrays.asList(commands);
		assertFalse(list.contains(DHTTP_PROXY_PASSWORD_MYSECRET1));
		assertFalse(list.contains(DHTTPS_PROXY_PASSWORD_MYSECRET2));
	}

	@Test
	public void commands_NOT_hidden_contains_http_proxy_passwords() throws Exception {
		/* execute */
		String[] commands = commandToTest.createCommands(configuration, parameter, false);

		/* test */
		List<String> list = Arrays.asList(commands);
		assertTrue(list.contains(DHTTP_PROXY_PASSWORD_MYSECRET1));
		assertTrue(list.contains(DHTTPS_PROXY_PASSWORD_MYSECRET2));
	}

	@Test
	public void commands_hidden_contains_no_auth_token() throws Exception {
		testOptionContentIsHiddenOrNot(true, "-auth", USER_API_TOKEN_COMBI);
	}

	@Test
	public void commands_NOT_hidden_contains_auth_token() throws Exception {
		testOptionContentIsHiddenOrNot(false, "-auth", USER_API_TOKEN_COMBI);
	}

	@Test
	public void command_auth_no_proxy_set() throws Exception {
		/* execute */
		String[] commands = commandToTest.createCommands(configuration, parameter, true);

		/* test */
		List<String> list = Arrays.asList(commands);
		System.out.println(list);
	}

	private void testOptionContentIsHiddenOrNot(boolean hidePasswords, String optionToCheck, String fullValue) {
		String[] commands = commandToTest.createCommands(configuration, parameter, hidePasswords);

		List<String> list = Arrays.asList(commands);
		/* test */
		boolean foundApiTokenKey = false;
		Iterator<String> it = list.iterator();
		while (it.hasNext()) {
			String command = it.next();
			if (command.equals(optionToCheck)) {
				foundApiTokenKey = true;
				if (it.hasNext()) {
					String apiTokenValue = it.next();
					if (hidePasswords) {
						assertFalse(fullValue.equals(apiTokenValue));
						assertTrue(apiTokenValue.contains("*"));
					} else {
						assertEquals(fullValue, apiTokenValue);
						assertFalse(apiTokenValue.contains("*"));
					}
				}
			}
		}
		assertTrue(foundApiTokenKey);
	}

	class TestCommand extends AbstractJenkinsCLICommand<JenkinsCLIResult, String> {

		@Override
		protected String getCLICommand() {
			return "theCommand";
		}

		@Override
		protected JenkinsCLIResult handleStartedProcess(Process process, String parameter,
				CLIJarCommandMessageBuilder<String> mb) throws IOException {
			return null;
		}

	}

}
