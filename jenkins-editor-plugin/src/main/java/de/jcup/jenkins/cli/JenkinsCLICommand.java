package de.jcup.jenkins.cli;

import java.io.IOException;

public interface JenkinsCLICommand<T extends JenkinsCLIResult,P> {
	
	public T execute(JenkinsCLIConfiguration configuration, P parameter) throws IOException;
}
