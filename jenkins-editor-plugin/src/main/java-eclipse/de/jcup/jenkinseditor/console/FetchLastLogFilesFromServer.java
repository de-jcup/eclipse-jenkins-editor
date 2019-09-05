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
 package de.jcup.jenkinseditor.console;

import java.io.IOException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

import de.jcup.jenkins.cli.DefaultJenkinsCLIResult;
import de.jcup.jenkins.cli.JenkinsCLIConfiguration;
import de.jcup.jenkins.cli.JenkinsCustomCLICommand;
import de.jcup.jenkins.cli.PipelineConfigData;
import de.jcup.jenkinseditor.JenkinsEditorActivator;
import de.jcup.jenkinseditor.preferences.JenkinsEditorPreferences;

public class FetchLastLogFilesFromServer {

    public static final String WAITING_FOR_MORE_JENKINS_BUILD_OUTPUT = ">>>.... waiting for more JENKINS build output ....<<<<";

    public void executeAsJob(JenkinsCLIConfiguration configuration, PipelineConfigData data) {

        String jobName = data.getReplayJobName();

        String commandsAsOneString = "console " + jobName + " -f ";
        String[] commands = commandsAsOneString.split(" ");
        JenkinsCustomCLICommand replayCommand = new JenkinsCustomCLICommand(commands);

        Job job = new Job("Fetch log files from jenkins") {

            @Override
            protected IStatus run(IProgressMonitor monitor) {
                try {
                    String info = "Fetch logs for last build of jobName " + jobName;
                    monitor.beginTask(info, IProgressMonitor.UNKNOWN);
                    JenkinsEditorConsoleUtil.showConsole();
                    JenkinsEditorConsoleUtil.output(info);
                    if (monitor.isCanceled()) {
                        return Status.CANCEL_STATUS;
                    }
                    String alreadyDoneOutput="";
                    DefaultJenkinsCLIResult result = null;
                    int waits=0;
                    do {
                        result = replayCommand.execute(configuration, "");

                        StringBuilder sb = new StringBuilder();
                        for (String string : result.getLines()) {
                            sb.append(string);
                            sb.append("\n");
                        }
                        String newOutput=sb.toString();
                        
                        JenkinsEditorConsoleUtil.output(newOutput.substring(alreadyDoneOutput.length()));
                        alreadyDoneOutput=newOutput;
                        
                        if (!result.wasCLICallSuccessFul()) {
                            waits++;
                            JenkinsEditorConsoleUtil.output(WAITING_FOR_MORE_JENKINS_BUILD_OUTPUT);
                            monitor.setTaskName(info+" - amount of waits:"+waits);
                            
                            /* We wait for JENKINS to produce log output - if we do not wait, this will
                             * slow down the build! This has influenced an integration test ... so we try to avoid this
                             */
                            int secondsToWait = JenkinsEditorPreferences.getInstance().getWaitForLogRefreshInSeconds();
                            for (int i=0;i<secondsToWait;i++) { // we wait 1 Minute
                                if (monitor.isCanceled()) {
                                    return Status.CANCEL_STATUS;   
                                }
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    Thread.currentThread().interrupt();
                                }
                            }
                            
                            
                        }
                    } while (!monitor.isCanceled() && !result.wasCLICallSuccessFul());

                } catch (IOException e) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Custom cli action failed at '");
                    sb.append(configuration.getJenkinsURL());
                    sb.append("'\nMessage:");
                    sb.append(e.getMessage());
                    JenkinsEditorConsoleUtil.output(sb.toString());
                    JenkinsEditorConsoleUtil.showConsole();
                    return new Status(Status.ERROR, JenkinsEditorActivator.PLUGIN_ID, "Replay failed", e);
                } finally {
                    monitor.done();
                }
                return Status.OK_STATUS;

            }

        };
        job.schedule();
    }
}
