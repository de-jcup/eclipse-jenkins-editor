/*
 * Copyright 2016 Albert Tregnaghi
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
package de.jcup.jenkinseditor;

import static de.jcup.egradle.eclipse.util.EclipseUtil.*;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

import de.jcup.egradle.eclipse.util.EclipseUtil;
import de.jcup.jenkinseditor.preferences.UserCredentials;
import de.jcup.jenkinseditor.preferences.UserCredentialsDialog;

public class JenkinsEditorMessageDialogSupport {

	private static final String JENKINS_EDITOR = "Jenkins-Editor";
	public static final JenkinsEditorMessageDialogSupport INSTANCE = new JenkinsEditorMessageDialogSupport();

	public void showWarning(String message) {
		EclipseUtil.safeAsyncExec(new Runnable() {

			@Override
			public void run() {
				Shell shell = getActiveWorkbenchShell();
				MessageDialog.openWarning(shell, JENKINS_EDITOR, message);
			}

		});

	}
	
	public void showInfo(String message) {
		EclipseUtil.safeAsyncExec(new Runnable() {

			@Override
			public void run() {
				Shell shell = getActiveWorkbenchShell();
				MessageDialog.openInformation(shell, JENKINS_EDITOR, message);
			}

		});

	}

	public void showError(String message) {
		EclipseUtil.safeAsyncExec(new Runnable() {

			@Override
			public void run() {
				Shell shell = getActiveWorkbenchShell();
				MessageDialog.openError(shell, JENKINS_EDITOR, message);
			}

		});

	}
	
	public void showErrorWithDetails(String message,String details) {
		EclipseUtil.safeAsyncExec(new Runnable() {

			@Override
			public void run() {
				Shell shell = getActiveWorkbenchShell();
				IStatus status = new Status(IStatus.ERROR,JenkinsEditorActivator.PLUGIN_ID,message, new Throwable(details));
				ErrorDialog.openError(shell, JENKINS_EDITOR, null, status);
			}

		});

	}

	public UserCredentials showUsernamePassword(String labelForPassword) {
		final UserCredentials result = new UserCredentials();
		EclipseUtil.safeSyncExec(new Runnable() {

			@Override
			public void run() {
				Shell shell = getActiveWorkbenchShell();
				
				UserCredentialsDialog dialog = new UserCredentialsDialog(shell,labelForPassword,result);
				dialog.open();
			}

		});
		return result;
		
	}

}
