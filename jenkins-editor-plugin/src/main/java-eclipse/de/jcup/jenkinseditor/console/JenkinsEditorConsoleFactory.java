package de.jcup.jenkinseditor.console;

import org.eclipse.ui.console.IConsoleFactory;

public class JenkinsEditorConsoleFactory implements IConsoleFactory{

    @Override
    public void openConsole() {
        JenkinsEditorConsoleUtil.showConsole();
    }

}
