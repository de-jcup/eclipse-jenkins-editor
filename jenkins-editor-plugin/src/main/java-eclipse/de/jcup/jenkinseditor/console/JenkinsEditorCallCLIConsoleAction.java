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
