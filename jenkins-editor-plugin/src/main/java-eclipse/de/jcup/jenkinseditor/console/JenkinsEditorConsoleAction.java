package de.jcup.jenkinseditor.console;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;

import de.jcup.jenkinseditor.JenkinsEditorUtil;

public abstract class JenkinsEditorConsoleAction extends Action {

    
    JenkinsEditorConsoleAction(String imageName){
        setImageDescriptor(createToolbarImageDescriptor(imageName));
    }
    
    @Override
    public void run() {
        execute();
    }
    
    protected abstract void execute();

    static ImageDescriptor createToolbarImageDescriptor(String name) {
        return JenkinsEditorUtil.createImageDescriptor("icons/jenkinseditor/console/" + name);
    }
}
