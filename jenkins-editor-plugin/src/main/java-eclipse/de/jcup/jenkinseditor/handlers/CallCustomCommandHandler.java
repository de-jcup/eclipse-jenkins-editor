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
import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.window.Window;

import de.jcup.eclipse.commons.ui.EclipseUtil;
import de.jcup.jenkins.cli.DefaultJenkinsCLIResult;
import de.jcup.jenkins.cli.JenkinsCLIConfiguration;
import de.jcup.jenkins.cli.JenkinsCustomCLICommand;
import de.jcup.jenkinseditor.JenkinsEditor;
import de.jcup.jenkinseditor.JenkinsEditorLogSupport;
import de.jcup.jenkinseditor.JenkinsEditorMessageDialogSupport;
import de.jcup.jenkinseditor.console.JenkinsEditorConsoleUtil;

public class CallCustomCommandHandler extends AbstractJenkinsCLIHandler {
    
    private String lastCommand="help";

    @Override
    protected void executeOnActiveJenkinsEditor(JenkinsEditor editor) {
        JenkinsCLIConfiguration configuration = buildConfiguration();
        if (configuration == null) {
            return;
        }
        String[] commands = null;
        String dialogMessage = "What CLI command do you want to execute?\n\nAdd ${SOURCE} somewhere at this line, when you want to use content of active editor as user input";
        InputDialog inputDialog = new InputDialog(EclipseUtil.getSafeDisplay().getActiveShell(), "CLI command", dialogMessage,
                lastCommand, null);
        if (inputDialog.open() != Window.OK) {
            return;
        }
        String sourceVariable = null;
        String commandsAsOneString = inputDialog.getValue().trim();
        lastCommand=commandsAsOneString;
        
        commands = commandsAsOneString.split(" ");
        for (int i = 0; i < commands.length; i++) {
            if ("${SOURCE}".contentEquals(commands[i])) {
                IDocument document = editor.getDocument();
                if (document != null) {
                    sourceVariable = document.get();
                } else {
                    sourceVariable = "";
                }
                commands[i] = "";
            }
        }
        String code = sourceVariable;

        JenkinsCustomCLICommand command = new JenkinsCustomCLICommand(commands);
        JenkinsEditorConsoleUtil.output("Execute cli command:" + commandsAsOneString);
        JenkinsEditorConsoleUtil.showConsole();
        ProgressMonitorDialog dialog = new ProgressMonitorDialog(EclipseUtil.getActiveWorkbenchShell());

        IRunnableWithProgress runnable = new IRunnableWithProgress() {

            @Override
            public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
                monitor.beginTask("CLI call to Jenkins", IProgressMonitor.UNKNOWN);
                try {
                    DefaultJenkinsCLIResult result = command.execute(configuration, code);

                    StringBuilder sb = new StringBuilder();
                    for (String string : result.getLines()) {
                        sb.append(string);
                        sb.append("\n");
                    }
                    JenkinsEditorConsoleUtil.output("[RESULT]\n" + sb.toString());
                    JenkinsEditorConsoleUtil.showConsole();

                    if (!result.wasCLICallSuccessFul()) {
                        JenkinsEditorConsoleUtil.output("[FAILED]");
                        JenkinsEditorMessageDialogSupport.INSTANCE.showErrorWithDetails("Jenkins CLI call failed.", result.toString());
                        return;
                    } else {
                        JenkinsEditorConsoleUtil.output("[SUCCESS]");
                    }

                } catch (IOException e) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Custom cli action failed at '");
                    sb.append(configuration.getJenkinsURL());
                    sb.append("'\nMessage:");
                    sb.append(e.getMessage());
                    JenkinsEditorConsoleUtil.output(sb.toString());
                    JenkinsEditorConsoleUtil.showConsole();
                    handleIOError(e, sb);
                } finally {
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
}
