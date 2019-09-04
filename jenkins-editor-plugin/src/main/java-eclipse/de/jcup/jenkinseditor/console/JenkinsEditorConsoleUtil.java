/*
 * Copyright 2018 Albert Tregnaghi
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

import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.MessageConsoleStream;

import de.jcup.eclipse.commons.ui.EclipseUtil;
import de.jcup.jenkinseditor.JenkinsEditorActivator;
import de.jcup.jenkinseditor.preferences.JenkinsEditorPreferences;

public class JenkinsEditorConsoleUtil {

    /**
     * Using this method will print out a error message. If "show always consolole
     * on error" is enabled in preferences the console will be opened if not visible
     * 
     * @param message
     */
    public static void error(String message) {
        output(message);
        showConsoleOnErrorIfEnabled();
    }

    private static void showConsoleOnErrorIfEnabled() {
        if (JenkinsEditorPreferences.getInstance().isConsoleAlwaysShownOnError()) {
            showConsole();
        }
    }

    /**
     * Will output to {@link JenkinsEditorConsole} instance
     * 
     * @param message
     */
    public static void output(String message) {
        JenkinsEditorConsole myConsole = findConsole();
        MessageConsoleStream out = myConsole.newMessageStream();
        out.println(message);
    }

    public static void showConsole() {
        getConsoleManager().showConsoleView(findConsole());
    }

    private static JenkinsEditorConsole findConsole() {
        IConsoleManager conMan = getConsoleManager();
        IConsole[] existing = conMan.getConsoles();
        for (int i = 0; i < existing.length; i++)
            if (existing[i] instanceof JenkinsEditorConsole) {
                return (JenkinsEditorConsole) existing[i];
            }
        // no console found, so create a new one
        JenkinsEditorConsole asciiDoctorConsole = new JenkinsEditorConsole(EclipseUtil.createImageDescriptor("icons/jenkins-editor.png", JenkinsEditorActivator.PLUGIN_ID));
        conMan.addConsoles(new IConsole[] { asciiDoctorConsole });

        return asciiDoctorConsole;
    }

    private static IConsoleManager getConsoleManager() {
        ConsolePlugin plugin = ConsolePlugin.getDefault();
        IConsoleManager conMan = plugin.getConsoleManager();
        return conMan;
    }

}
