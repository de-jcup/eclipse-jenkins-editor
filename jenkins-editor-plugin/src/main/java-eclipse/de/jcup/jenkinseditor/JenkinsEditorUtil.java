package de.jcup.jenkinseditor;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;

import de.jcup.egradle.eclipse.ui.UnpersistedMarkerHelper;
import de.jcup.jenkins.linter.JenkinsLinterError;

public class JenkinsEditorUtil {

	private static UnpersistedMarkerHelper linterMarkerHelper = new UnpersistedMarkerHelper(
			"de.jcup.jenkinseditor.linter.error");
	public static void logInfo(String info) {
		getLog().log(new Status(IStatus.INFO, JenkinsEditorActivator.PLUGIN_ID, info));
	}

	public static void logWarning(String warning) {
		getLog().log(new Status(IStatus.WARNING, JenkinsEditorActivator.PLUGIN_ID, warning));
	}

	public static void logError(String error, Throwable t) {
		getLog().log(new Status(IStatus.ERROR, JenkinsEditorActivator.PLUGIN_ID, error, t));
	}

	public static void removeLinterErrors(IEditorPart editor) {
		if (editor == null) {
			return;
		}
		IEditorInput input = editor.getEditorInput();
		if (input == null) {
			return;
		}
		IResource editorResource = input.getAdapter(IResource.class);
		if (editorResource == null) {
			return;
		}
		linterMarkerHelper.removeMarkers(editorResource);
	}

	public static void addLinterError(IEditorPart editor, JenkinsLinterError error) {
		if (editor == null) {
			return;
		}
		if (error == null) {
			return;
		}

		IEditorInput input = editor.getEditorInput();
		if (input == null) {
			return;
		}
		IResource editorResource = input.getAdapter(IResource.class);
		if (editorResource == null) {
			return;
		}
		try {
			linterMarkerHelper.createErrorMarker(editorResource, error.getMessage(), error.getLine());
		} catch (CoreException e) {
			logError("Was not able to add error markers", e);
		}

	}

	private static ILog getLog() {
		ILog log = JenkinsEditorActivator.getDefault().getLog();
		return log;
	}
}
