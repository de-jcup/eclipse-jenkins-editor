package de.jcup.jenkins.cli;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import org.apache.commons.io.FileUtils;

public class JenkinsLinterCLICommand extends AbstractJenkinsCLICommand<JenkinsLinterCLIResult, File>{

	@Override
	public String getCLICommand() {
		return "declarative-linter";
	}

	@Override
	protected JenkinsLinterCLIResult handleStartedProcess(Process process, File fileToLint) throws IOException {
		if (fileToLint==null){
			throw new IllegalArgumentException("file to lint may not be null!");
		}
		if (! fileToLint.exists()){
			throw new FileNotFoundException("File to lint does not exist:"+fileToLint);
		}
		JenkinsLinterCLIResult result = new JenkinsLinterCLIResult();
		result.fileToLint = fileToLint;
		if (! process.isAlive()){
			return result;
		}
		String data = FileUtils.readFileToString(fileToLint);
		/* give file data as input */
		try(BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()))){
			bw.write(data);
		}
		/* fetch output to result */
		try(BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()))){
			String line = null;
			while ((line=br.readLine())!=null){
				result.appendOutput(line);
			}
		}
		
		return result;
	}

}
