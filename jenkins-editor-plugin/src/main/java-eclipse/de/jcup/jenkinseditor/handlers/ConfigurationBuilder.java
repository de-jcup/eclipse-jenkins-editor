package de.jcup.jenkinseditor.handlers;

import static de.jcup.jenkinseditor.JenkinsEditorConstants.*;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.core.net.proxy.IProxyData;
import org.eclipse.core.net.proxy.IProxyService;
import org.eclipse.equinox.security.storage.ISecurePreferences;
import org.eclipse.equinox.security.storage.SecurePreferencesFactory;
import org.eclipse.equinox.security.storage.StorageException;

import de.jcup.jenkins.cli.JenkinsCLIConfiguration;
import de.jcup.jenkins.cli.JenkinsDefaultURLProvider;
import de.jcup.jenkins.cli.JenkinsCLIConfiguration.AuthMode;
import de.jcup.jenkins.util.SystemPropertyListBuilder;
import de.jcup.jenkinseditor.JenkinsEditorActivator;
import de.jcup.jenkinseditor.JenkinsEditorLogSupport;
import de.jcup.jenkinseditor.JenkinsEditorMessageDialogSupport;
import de.jcup.jenkinseditor.preferences.JenkinsEditorPreferences;

public class ConfigurationBuilder {

	public JenkinsCLIConfiguration createConfiguration(JenkinsDefaultURLProvider jenkinsDefaultURLprovider) throws IOException {
		JenkinsCLIConfiguration configuration = new JenkinsCLIConfiguration();

		JenkinsEditorPreferences editorPreferences = JenkinsEditorPreferences.getInstance();

		String linterJenkinsURL = editorPreferences.getJenkinsURL();
		String pathToJenkinsCLIJar = editorPreferences.getPathToJenkinsCLIJar();
		if (pathToJenkinsCLIJar == null || pathToJenkinsCLIJar.trim().length() == 0) {
			/* fall back to embedded variant */
			pathToJenkinsCLIJar = createPathToEmbeddedCLIJar();
		}
		boolean certificateCheckDisabled = editorPreferences.isCertficateCheckDisabled();

		ISecurePreferences preferences = SecurePreferencesFactory.getDefault();

		if (preferences.nodeExists(ID_SECURED_CREDENTIALS)) {
			ISecurePreferences node = preferences.node(ID_SECURED_CREDENTIALS);
			try {
				String user = node.get(ID_SECURED_USER_KEY, "anonymous");
				String apiToken = node.get(ID_SECURED_API_KEY, "");

				configuration.setUser(user);
				configuration.setAPIToken(apiToken);

			} catch (StorageException e1) {
				JenkinsEditorMessageDialogSupport.INSTANCE.showError("No access to secured user credentials!");
				JenkinsEditorLogSupport.INSTANCE.logError("Was not able to fetch secured credentials", e1);
				return null;
			}
		}

		if (linterJenkinsURL == null || linterJenkinsURL.trim().length() == 0) {
			linterJenkinsURL = jenkinsDefaultURLprovider.getDefaultJenkinsURL();
		}
		configuration.setCertificateCheckDisabled(certificateCheckDisabled);
		configuration.setJenkinsURL(linterJenkinsURL);
		configuration.setAuthMode(AuthMode.API_TOKEN);// currently we support
														// only
														// API KEY- in future
														// maybe
														// more/ changeable in
														// preferences
		configuration.setPathToJenkinsCLIJar(pathToJenkinsCLIJar);

		configuration.setTimeoutInSeconds(10);
		/* FIXME Albert: 11.03.2018: make proxy usage configurable*/
		URI uri;
		try {
			uri = new URI(configuration.getJenkinsURL());
		} catch (URISyntaxException e) {
			throw new IOException("JENKINS URI not correct",e);
		}
		Set<String> systemProperties = buildProxySetup(uri);
		configuration.setProxySystemProperties(systemProperties);
		return configuration;
	}
	
	private SystemPropertyListBuilder builder;
	
	public ConfigurationBuilder() {
		builder = new SystemPropertyListBuilder();
	}

	
	protected Set<String> buildProxySetup(URI uri){
		IProxyService proxyService = JenkinsEditorActivator.getDefault().getProxyService();
        IProxyData[] proxyDataForHost = proxyService.select(uri);

        Set<String> propertyList = new LinkedHashSet<>();
        
        for (IProxyData data : proxyDataForHost) {
        	String host = data.getHost();
			if (host != null) {
        		String type = data.getType();
        		if (type!=null){
        			type=type.toLowerCase(); // ensure https is lowercased etc.
        		}
        		int port = data.getPort();
                
                propertyList.add(builder.build(type, "proxyHost", host));
                propertyList.add(builder.build(type, "proxySet", "true"));
                propertyList.add(builder.build(type,"proxyPort", String.valueOf(port)));
                
                if (data.isRequiresAuthentication()){
                	String userid = data.getUserId();
                	String pwd = data.getPassword();
                	propertyList.add(builder.build(type, "proxyUser", userid));
                	propertyList.add(builder.build(type, "proxyPassword", pwd));
                }
            }
        }
        // Close the service and close the service tracker
        proxyService = null;
        return propertyList;
	}
	
	private static String createPathToEmbeddedCLIJar() throws IOException {
		File file = JenkinsEditorActivator.getDefault().getEmbeddedJenkinsCLIJarFile();
		return file.getAbsolutePath();
	}
}
