/*
 * Copyright 2019 Albert Tregnaghi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions
 * and limitations under the License.
 *
 */
package de.jcup.jenkinseditor.handlers;

import java.io.IOException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.text.IDocument;

import de.jcup.eclipse.commons.ui.EclipseUtil;
import de.jcup.jenkins.cli.DefaultJenkinsCLIResult;
import de.jcup.jenkins.cli.JenkinsCLIConfiguration;
import de.jcup.jenkins.cli.JenkinsCustomCLICommand;
import de.jcup.jenkins.cli.PipelineConfigData;
import de.jcup.jenkinseditor.JenkinsEditor;
import de.jcup.jenkinseditor.JenkinsEditorActivator;
import de.jcup.jenkinseditor.PipelineDataDialog;
import de.jcup.jenkinseditor.PipelineDataDialog.Type;
import de.jcup.jenkinseditor.console.FetchLastLogFilesFromServer;
import de.jcup.jenkinseditor.console.JenkinsEditorConsoleUtil;

public class ReplayPipelineHandler extends AbstractJenkinsCLIHandler {

    private FetchLastLogFilesFromServer fetchLastLogFilesFromServer = new FetchLastLogFilesFromServer();
    
    @Override
    protected void executeOnActiveJenkinsEditor(JenkinsEditor editor) {
        if (editor == null) {
            return;
        }
        JenkinsCLIConfiguration configuration = buildConfiguration();
        if (configuration == null) {
            return;
        }
        IDocument document = editor.getDocument();
        if (document == null) {
            return;
        }
        String code = document.get();
        PipelineConfigData data = editor.getReplayData();
        
        
        PipelineDataDialog dialog = new PipelineDataDialog(EclipseUtil.getSafeDisplay().getActiveShell(), data, Type.REPLAY);
        int result = dialog.open();
        if (result != Dialog.OK) {
            return;
        }
        
        String jobName=data.getReplayJobName();
        int buildNrToReplay=data.getReplayBuildNr();
        
        String commandsAsOneString = "replay-pipeline "+jobName+" -n "+buildNrToReplay;
        String[] commands = commandsAsOneString.split(" ");
        JenkinsCustomCLICommand replayCommand = new JenkinsCustomCLICommand(commands);
        
        Job job = new Job("Replay Jenkins pipeline") {
            
            @Override
            protected IStatus run(IProgressMonitor monitor) {
                try {
                    String info="Start replay of jobName "+jobName+" from build #"+buildNrToReplay;
                    monitor.beginTask(info, IProgressMonitor.UNKNOWN);
                    JenkinsEditorConsoleUtil.showConsole();
                    JenkinsEditorConsoleUtil.output(info);
                    if (monitor.isCanceled()) {
                        return Status.CANCEL_STATUS;
                    }

                    DefaultJenkinsCLIResult result = replayCommand.execute(configuration, code);

                    StringBuilder sb = new StringBuilder();
                    for (String string : result.getLines()) {
                        sb.append(string);
                        sb.append("\n");
                    }
                    if (monitor.isCanceled()) {
                        return Status.CANCEL_STATUS;
                    }
                    JenkinsEditorConsoleUtil.output(sb.toString());

                    if (!result.wasCLICallSuccessFul()) {
                        return new Status(Status.ERROR, JenkinsEditorActivator.PLUGIN_ID,"Replay trigger failed", new IllegalStateException(result.toString()));
                    }else{
                        JenkinsEditorConsoleUtil.output("[REPLAY triggered]");
                    }
                    if (monitor.isCanceled()) {
                        return Status.CANCEL_STATUS;
                    }
                    fetchLastLogFilesFromServer.executeAsJob(configuration, data);
                } catch (IOException e) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Replay cli action failed at '");
                    sb.append(configuration.getJenkinsURL());
                    sb.append("'\nMessage:");
                    sb.append(e.getMessage());
                    JenkinsEditorConsoleUtil.output(sb.toString());
                    JenkinsEditorConsoleUtil.showConsole();
                    handleIOError(e, sb);
                    return new Status(Status.ERROR, JenkinsEditorActivator.PLUGIN_ID,"Replay failed",  e);
                } finally {
                    monitor.done();
                }
                return Status.OK_STATUS;

            }

        };
        job.schedule();
        
    }
    
    
}
