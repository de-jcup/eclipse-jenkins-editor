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
