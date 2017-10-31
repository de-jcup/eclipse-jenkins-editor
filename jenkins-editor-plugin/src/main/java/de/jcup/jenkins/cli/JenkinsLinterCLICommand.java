package de.jcup.jenkins.cli;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Transfers given code on execution time to jenkins server for validation
 * @author Albert Tregnaghi
 *
 */
public class JenkinsLinterCLICommand extends AbstractJenkinsCLICommand<JenkinsLinterCLIResult, String> {

	@Override
	public String getCLICommand() {
		return "declarative-linter";
	}

	@Override
	protected JenkinsLinterCLIResult handleStartedProcess(Process process, String code) throws IOException {

		JenkinsLinterCLIResult result = new JenkinsLinterCLIResult();
		if (!process.isAlive()) {
			return result;
		}
		/* give code data as input */
		try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()))) {
			bw.write(code);
		}
		/* fetch output to result */
		try (BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
			String line = null;
			while ((line = br.readLine()) != null) {
				result.appendOutput(line);
			}
		}

		return result;
	}

}
