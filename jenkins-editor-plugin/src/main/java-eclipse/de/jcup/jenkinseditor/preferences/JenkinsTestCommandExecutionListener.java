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
 package de.jcup.jenkinseditor.preferences;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import de.jcup.jenkins.cli.AbstractJenkinsCLICommand;
import de.jcup.jenkins.cli.AbstractJenkinsCLIResult;
import de.jcup.jenkins.cli.JenkinsCLIConfiguration;
import de.jcup.jenkins.cli.JenkinsCLIResult;
import de.jcup.jenkins.cli.JenkinsDefaultURLProvider;
import de.jcup.jenkins.cli.JenkinsHelpCommand;
import de.jcup.jenkinseditor.JenkinsEditorLogSupport;
import de.jcup.jenkinseditor.JenkinsEditorMessageDialogSupport;
import de.jcup.jenkinseditor.preferences.JenkinsEditorPreferencePage.JenkinsResultHandler;

public class JenkinsTestCommandExecutionListener extends SelectionAdapter {

    private final JenkinsEditorPreferencePage jenkinsEditorPreferencePage;
    private JenkinsResultHandler resultHandler;
    private String startMessage="Try to connect to Jenkins";
    private AbstractJenkinsCLICommand<?, String> command;
    private String successmessage = "Connection test successful!";
    private String errorMessage = "Connection test failed!";
    
    public JenkinsTestCommandExecutionListener(JenkinsEditorPreferencePage jenkinsEditorPreferencePage, AbstractJenkinsCLICommand<?, String> command, JenkinsResultHandler resultHandler) {
        this.jenkinsEditorPreferencePage = jenkinsEditorPreferencePage;
        this.resultHandler=resultHandler;
        this.command=command;
    }
    public void setStartMessage(String startMessage) {
        this.startMessage = startMessage;
    }
    public void setSuccessmessage(String successmessage) {
        this.successmessage = successmessage;
    }
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    
    @Override
    public void widgetSelected(SelectionEvent e) {
        JenkinsEditorMessageDialogSupport dialogSupport = JenkinsEditorMessageDialogSupport.INSTANCE;
        try {
            JenkinsCLIConfiguration configuration = this.jenkinsEditorPreferencePage.configBuilder.createConfiguration(new JenkinsDefaultURLProvider());
            if (this.jenkinsEditorPreferencePage.temporaryCredentials != null) {
                /*
                 * if temporary credentials exists, we use the temporary
                 * user and password settings- currently only api token
                 * is supported.
                 */
                configuration.setUser(this.jenkinsEditorPreferencePage.temporaryCredentials.username);
                configuration.setAPIToken(this.jenkinsEditorPreferencePage.temporaryCredentials.secret);
            }
            configuration.setCertificateCheckDisabled(jenkinsEditorPreferencePage.certificateCheckDisabled.getBooleanValue());
            
            String enteredURL = this.jenkinsEditorPreferencePage.jenkinsUrl.getStringValue();
            if (enteredURL != null && enteredURL.trim().length() > 0) {
                /* we got an entry here - so we use it ..*/
                configuration.setJenkinsURL(enteredURL);
            }
            ProgressMonitorDialog dialog = new ProgressMonitorDialog(this.jenkinsEditorPreferencePage.getShell());
            IRunnableWithProgress runnable = new IRunnableWithProgress() {
                
                @Override
                public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
                    try{
                        
                        monitor.beginTask(startMessage, IProgressMonitor.UNKNOWN);
                        JenkinsHelpCommand helpCmd = new JenkinsHelpCommand();
                        JenkinsCLIResult result = command.execute(configuration, "");
                        if (result.wasCLICallSuccessFul()) {
                            if (resultHandler!=null && result instanceof AbstractJenkinsCLIResult) {
                                resultHandler.handle((AbstractJenkinsCLIResult) result);
                            }
                            dialogSupport.showInfo(successmessage);
                        } else {
                            StringBuilder sb = new StringBuilder();
                            sb.append("Connection test to '").append(configuration.getJenkinsURL()).append("' was NOT successful!\n\n");
                            sb.append(result.toString());
                            
                            String errorMessage = sb.toString();
                            dialogSupport.showErrorWithDetails("Connection test failed", errorMessage);
                            JenkinsEditorLogSupport.INSTANCE.logError(errorMessage, null);
                        }
                        // use this to open a Shell in the UI thread
                    } catch (IOException e1) { 
                        dialogSupport.showError(errorMessage+"\n" + e1.getMessage());
                    } finally{
                        monitor.done();
                    }
                    
                    
                }
            };
            dialog.run(true, false, runnable);
            
        } catch (Exception e1) { 
            dialogSupport.showError("Was not able to create connection configuration\n" + e1.getMessage());
        }
    }
}