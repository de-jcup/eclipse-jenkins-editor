package de.jcup.jenkins.util;

/**
 * System adapter - wrap dedicated static methods from java.lang.System. This 
 * adapter makes it possible to create mockable tests...
 * 
 * @author Albert Tregnaghi
 *
 */
public class SystemAdapter {

	public String getEnv(String var){
		return System.getenv(var);
	}
	
}
