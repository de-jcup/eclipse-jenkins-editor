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

import org.eclipse.core.commands.ExecutionException;

import de.jcup.jenkinseditor.JenkinsEditorLogSupport;
import de.jcup.jenkinseditor.handlers.CallCustomCommandHandler;

public class JenkinsEditorCallCLIConsoleAction extends JenkinsEditorConsoleAction{

    private CallCustomCommandHandler handler;

    JenkinsEditorCallCLIConsoleAction() {
        super("cli-exec.png");
        setToolTipText("Execute a jenkins cli command");
        handler = new CallCustomCommandHandler();
    }

    @Override
    protected void execute() {
        try {
           handler.execute(null);
        } catch (ExecutionException e) {
           JenkinsEditorLogSupport.INSTANCE.logError("Was not able to execute custom cli command", e);
        }
    }

    
}
