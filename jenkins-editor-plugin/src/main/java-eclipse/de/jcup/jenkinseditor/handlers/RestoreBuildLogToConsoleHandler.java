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

import org.eclipse.jface.dialogs.Dialog;

import de.jcup.eclipse.commons.ui.EclipseUtil;
import de.jcup.jenkins.cli.JenkinsCLIConfiguration;
import de.jcup.jenkins.cli.PipelineConfigData;
import de.jcup.jenkinseditor.JenkinsEditor;
import de.jcup.jenkinseditor.PipelineDataDialog;
import de.jcup.jenkinseditor.PipelineDataDialog.Type;
import de.jcup.jenkinseditor.console.FetchLastLogFilesFromServer;

public class RestoreBuildLogToConsoleHandler extends AbstractJenkinsCLIHandler {

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
        PipelineConfigData data = editor.getReplayData();
        
        PipelineDataDialog dialog = new PipelineDataDialog(EclipseUtil.getSafeDisplay().getActiveShell(), data, Type.GET_LAST_CONSOLE_OUTPUT);
        int result = dialog.open();
        if (result != Dialog.OK) {
            return;
        }
        
        fetchLastLogFilesFromServer.executeAsJob(configuration, data);
        
    }
    
    
}
