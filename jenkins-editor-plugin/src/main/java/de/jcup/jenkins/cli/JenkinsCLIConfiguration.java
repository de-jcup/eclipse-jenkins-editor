package de.jcup.jenkins.cli;

public class JenkinsCLIConfiguration {

	private String JenkinsURL;
	private String pathToPrivateKeyFile;
	private String pathToJenkinsCLIJar;
	private String user;

	private int timeoutInSeconds;
	private boolean sshEnabled;
	private String apiToken;
	private String password;

	public enum AuthMode{
		API_TOKEN("apitoken"),
		
		PASSWORD("secret"),
		
		PRIVATE_KEY("privatekey"),
		
		ANONYMOUS("anonymous");

		private String id;

		private AuthMode(String id){
			this.id=id;
		}
		
		public String getId() {
			return id;
		}
		
	}
	
	private AuthMode authMode;
	
	public void setAuthMode(AuthMode authMode) {
		this.authMode = authMode;
	}
	
	public AuthMode getAuthMode() {
		if (authMode==null){
			authMode=AuthMode.API_TOKEN;
		}
		return authMode;
	}
	
	public void setSSHenabled(boolean sshEnabled) {
		this.sshEnabled = sshEnabled;
	}

	public boolean isSSHenabled() {
		return sshEnabled;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getUser() {
		return user;
	}

	public String getJenkinsURL() {
		return JenkinsURL;
	}

	public void setJenkinsURL(String hostname) {
		this.JenkinsURL = hostname;
	}

	public String getPathToPrivateKeyFile() {
		return pathToPrivateKeyFile;
	}

	public void setPathToPrivateKeyFile(String pathToPrivatekey) {
		this.pathToPrivateKeyFile = pathToPrivatekey;
	}

	public void setTimeoutInSeconds(int timeoutInSeconds) {
		this.timeoutInSeconds = timeoutInSeconds;
	}

	public int getTimeOutInSeconds() {
		return timeoutInSeconds;
	}

	public String getPathToJenkinsCLIJar() {
		return pathToJenkinsCLIJar;
	}

	public void setPathToJenkinsCLIJar(String pathToJenkinsCLIJar) {
		this.pathToJenkinsCLIJar = pathToJenkinsCLIJar;
	}

	public void setAPIToken(String apiToken) {
		this.apiToken = apiToken;
	}

	public String getAPIToken() {
		return apiToken;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getPassword() {
		return password;
	}

}
