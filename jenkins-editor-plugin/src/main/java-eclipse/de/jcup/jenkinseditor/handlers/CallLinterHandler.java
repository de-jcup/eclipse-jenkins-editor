package de.jcup.jenkinseditor.handlers;

import java.io.File;
import java.io.IOException;

import org.eclipse.jface.text.IDocument;

import de.jcup.jenkins.cli.JenkinsCLIConfiguration;
import de.jcup.jenkins.cli.JenkinsCLIConfiguration.AuthMode;
import de.jcup.jenkins.cli.JenkinsLinterCLICommand;
import de.jcup.jenkins.cli.JenkinsLinterCLIResult;
import de.jcup.jenkins.linter.JenkinsLinterError;
import de.jcup.jenkins.linter.JenkinsLinterErrorBuilder;
import de.jcup.jenkinseditor.JenkinsEditor;
import de.jcup.jenkinseditor.JenkinsEditorActivator;
import de.jcup.jenkinseditor.JenkinsEditorMessageDialogSupport;
import de.jcup.jenkinseditor.JenkinsEditorUtil;
import de.jcup.jenkinseditor.preferences.JenkinsEditorPreferences;
import org.eclipse.equinox.security.storage.ISecurePreferences;
import org.eclipse.equinox.security.storage.SecurePreferencesFactory;
import org.eclipse.equinox.security.storage.StorageException;
import static de.jcup.jenkinseditor.JenkinsEditorConstants.*;
public class CallLinterHandler extends AbstractJenkinsEditorHandler {

	
	private JenkinsLinterErrorBuilder errorBuilder = new JenkinsLinterErrorBuilder();

	@Override
	protected void executeOnActiveJenkinsEditor(JenkinsEditor editor) {
		if (editor == null) {
			return;
		}
		IDocument document = editor.getDocument();
		if (document == null) {
			return;
		}
		String code = document.get();

		try {
			executeLinterFor(code, editor);
		} catch (IOException e) {
			JenkinsEditorUtil.logError("Lint call not possible", e);
		}
	}

	protected void executeLinterFor(String code, JenkinsEditor editor) throws IOException {
		JenkinsCLIConfiguration configuration = new JenkinsCLIConfiguration();

		JenkinsEditorPreferences editorPreferences = JenkinsEditorPreferences.getInstance();

		String linterJenkinsURL = editorPreferences.getJenkinsURL();
		String pathToJenkinsCLIJar = editorPreferences.getPathToJenkinsCLIJar();
		if (pathToJenkinsCLIJar == null || pathToJenkinsCLIJar.trim().length() == 0) {
			/* fall back to embedded variant */
			pathToJenkinsCLIJar = createPathToEmbeddedCLIJar();
		}
		/* FIXME ATR, 31.10.2017: remove... */
		
		ISecurePreferences preferences = SecurePreferencesFactory.getDefault();

		if (preferences.nodeExists(ID_SECURED_CREDENTIALS)) {
			ISecurePreferences node = preferences.node(ID_SECURED_CREDENTIALS);
			try {
				String user = node.get(ID_SECURED_USER_KEY, "anonymous");
				String apiToken = node.get(ID_SECURED_API_KEY, "");
				
				configuration.setUser(user);
				configuration.setAPIToken(apiToken);
				
			} catch (StorageException e1) {
				e1.printStackTrace();
			}
		}

		if (linterJenkinsURL == null || linterJenkinsURL.trim().length() == 0) {
			linterJenkinsURL = "http://localhost:8080";
		}
		configuration.setJenkinsURL(linterJenkinsURL);
		configuration.setAuthMode(AuthMode.API_TOKEN);// currently we support only
													// API KEY- in future maybe
													// more/ changeable in
													// preferences
		configuration.setPathToJenkinsCLIJar(pathToJenkinsCLIJar);

		configuration.setTimeoutInSeconds(10);

		JenkinsLinterCLICommand command = new JenkinsLinterCLICommand();
		try {
			JenkinsLinterCLIResult result = command.execute(configuration, code);
			if (! result.wasCLICallSuccessFul()){
				JenkinsEditorMessageDialogSupport.INSTANCE.showError("Jenkins CLI call failed:\n"+result.getCLICallFailureMessage());
				return;
			}
			/* remove former linter errors (after call was possible ) */
			JenkinsEditorUtil.removeLinterErrors(editor);

			boolean foundAtLeastOneError = false;
			for (String line : result.getLines()) {
				JenkinsLinterError error = errorBuilder.build(line);
				if (error == null) {
					continue;
				}
				/* add linter error */
				JenkinsEditorUtil.addLinterError(editor, error);
				foundAtLeastOneError = true;
			}

			if (foundAtLeastOneError) {
				JenkinsEditorMessageDialogSupport.INSTANCE.showWarning("This script has errors inside!");
			} else {
				JenkinsEditorMessageDialogSupport.INSTANCE
						.showInfo("Your Jenkins server did found no lint errors for this script!");
			}

		} catch (IOException e) {
			JenkinsEditorMessageDialogSupport.INSTANCE.showError("Linter action failed" + e.getMessage());
		}
	}

	private String createPathToEmbeddedCLIJar() throws IOException {
		File file = JenkinsEditorActivator.getDefault().getEmbeddedJenkinsCLIJarFile();
		return file.getAbsolutePath();
	}

}
