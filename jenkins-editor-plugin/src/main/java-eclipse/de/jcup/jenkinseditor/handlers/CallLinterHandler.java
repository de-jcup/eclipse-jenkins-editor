/*
 * Copyright 2017 Albert Tregnaghi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *		http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions
 * and limitations under the License.
 *
 */
package de.jcup.jenkinseditor.handlers;

import static de.jcup.jenkinseditor.preferences.JenkinsEditorPreferenceConstants.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.text.IDocument;

import de.jcup.egradle.eclipse.util.EclipseUtil;
import de.jcup.jenkins.cli.JenkinsCLIConfiguration;
import de.jcup.jenkins.cli.JenkinsDefaultURLProvider;
import de.jcup.jenkins.cli.JenkinsLinterCLICommand;
import de.jcup.jenkins.cli.JenkinsLinterCLIResult;
import de.jcup.jenkins.linter.JenkinsLinterError;
import de.jcup.jenkins.linter.JenkinsLinterErrorBuilder;
import de.jcup.jenkins.linter.JenkinsLinterErrorLevel;
import de.jcup.jenkinseditor.JenkinsEditor;
import de.jcup.jenkinseditor.JenkinsEditorLogSupport;
import de.jcup.jenkinseditor.JenkinsEditorMessageDialogSupport;
import de.jcup.jenkinseditor.JenkinsEditorUtil;
import de.jcup.jenkinseditor.preferences.JenkinsEditorPreferences;
public class CallLinterHandler extends AbstractJenkinsCLIHandler {

	private JenkinsLinterErrorBuilder errorBuilder = new JenkinsLinterErrorBuilder();
	private JenkinsDefaultURLProvider jenkinsDefaultURLprovider = new JenkinsDefaultURLProvider();
	private ConfigurationBuilder configBuilder = new ConfigurationBuilder();

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

		executeLinterFor(code, editor);
	}

	protected void executeLinterFor(String code, JenkinsEditor editor) {
		JenkinsCLIConfiguration configuration;
		try {
			configuration = configBuilder.createConfiguration(jenkinsDefaultURLprovider);
		} catch (IOException e) {
			StringBuilder sb = new StringBuilder();
			sb.append("Was not able to build valid jenkins cli configuration");
			handleIOError(e, sb);
			
			return;
		}
		if (configuration == null) {
			return;
		}
		JenkinsLinterCLICommand command = new JenkinsLinterCLICommand();
		
		ProgressMonitorDialog dialog = new ProgressMonitorDialog(EclipseUtil.getActiveWorkbenchShell());
		
		String errorLevelId = JenkinsEditorPreferences.getInstance().getStringPreference(JENKINS_LINTER_ERROR_LEVEL);
		JenkinsLinterErrorLevel errorLevel = JenkinsLinterErrorLevel.fromId(errorLevelId);
		
		IRunnableWithProgress runnable = new IRunnableWithProgress() {
			
			@Override
			public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
				monitor.beginTask("Validate script by Jenkins", IProgressMonitor.UNKNOWN);
				try {
					JenkinsLinterCLIResult result = command.execute(configuration, code);
					if (!result.wasCLICallSuccessFul()) {
						JenkinsEditorMessageDialogSupport.INSTANCE
								.showErrorWithDetails("Jenkins CLI call failed.", result.toString());
						return;
					}
					/* remove former linter errors (after call was possible ) */
					JenkinsEditorUtil.removeLinterErrors(editor);

					int errorCount =0;
					int severity;
					if (JenkinsLinterErrorLevel.INFO.equals(errorLevel)) {
						severity = IMarker.SEVERITY_INFO;
					} else if (JenkinsLinterErrorLevel.WARNING.equals(errorLevel)) {
						severity = IMarker.SEVERITY_WARNING;
					} else {
						severity = IMarker.SEVERITY_ERROR;
					}
					for (String line : result.getLines()) {
						JenkinsLinterError error = errorBuilder.build(line);
						if (error == null) {
							continue;
						}
						/* add linter error */
						JenkinsEditorUtil.addLinterError(editor, error,severity);
						errorCount++;
					}

					if (errorCount>0) {
						JenkinsEditorMessageDialogSupport.INSTANCE.showWarning("This script has "+errorCount+" error(s) inside!");
					} else {
						JenkinsEditorMessageDialogSupport.INSTANCE
								.showInfo("This script has no failures.");
					}

				} catch (IOException e) {
					StringBuilder sb = new StringBuilder();
					sb.append("Linter action failed at '");
					sb.append(configuration.getJenkinsURL());
					sb.append("'\nMessage:");
					sb.append(e.getMessage());
					handleIOError(e, sb);
				} finally{
					monitor.done();
				}
				
			}

		};
		try {
			dialog.run(true, false, runnable);
		} catch (InvocationTargetException | InterruptedException e) {
			JenkinsEditorLogSupport.INSTANCE.logError("Linter execution failed", e);
		}
		
		
	}
	
	private void handleIOError(IOException e, StringBuilder sb) {
		JenkinsEditorMessageDialogSupport.INSTANCE.showErrorWithDetails(e.getMessage(),sb.toString());
		
		JenkinsEditorLogSupport.INSTANCE.logError(sb.toString(), e);
	}

}
