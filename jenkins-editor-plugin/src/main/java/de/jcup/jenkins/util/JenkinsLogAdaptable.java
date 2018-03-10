package de.jcup.jenkins.util;

/**
 * Own adaptable - we do not use egradle logadapter interface but our own
 * to have no dependency in gradle build to egradle. The only dependencies
 * to egradle exist in eclipse plugin build
 * @author albert
 *
 */
public interface JenkinsLogAdaptable {
	
	void logInfo(String info);

	void logWarning(String warning);

	void logError(String error, Throwable t);
}
