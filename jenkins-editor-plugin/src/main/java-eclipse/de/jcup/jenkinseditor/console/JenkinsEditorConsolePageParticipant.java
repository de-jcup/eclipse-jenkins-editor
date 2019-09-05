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

import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsolePageParticipant;
import org.eclipse.ui.part.IPageBookViewPage;
import org.eclipse.ui.part.IPageSite;

import de.jcup.jenkinseditor.JenkinsEditorActivator;

public class JenkinsEditorConsolePageParticipant implements IConsolePageParticipant {
    @Override
    public <T> T getAdapter(Class<T> adapter) {
        return null;
    }

    @Override
    public void activated() {
    }

    @Override
    public void deactivated() {
    }

    @Override
    public void dispose() {
        JenkinsEditorActivator.getDefault().removeViewerWithPageParticipant(this);
    }

    @Override
    public void init(IPageBookViewPage page, IConsole console) {
        IPageSite site = page.getSite();
        if (site==null) {
            return;
        }
        IActionBars actionBars = site.getActionBars();
        if (actionBars==null) {
            return;
        }
        IToolBarManager tm = actionBars.getToolBarManager();
        if (tm==null) {
            return;
        }
        tm.add(new Separator("jenkins.console.actions"));
        /* Why do we add this here as an action and not by standard way over plugin.xml and a command? 
         * 
         * Because the participant will only be added to jenkins console, so
         * we add the special action only to this one and not to any other console.
         * 
         */
        tm.add(new JenkinsEditorCallCLIConsoleAction());
        
        
        Control control = page.getControl();
        if (control instanceof StyledText) {
            /* Add EGradle process style listener to viewer */
            StyledText viewer = (StyledText) control;
            JenkinsEditorConsoleStyleListener myListener = new JenkinsEditorConsoleStyleListener();
            viewer.addLineStyleListener(myListener);

            JenkinsEditorActivator.getDefault().addViewer(viewer, this);
        }
    }
}