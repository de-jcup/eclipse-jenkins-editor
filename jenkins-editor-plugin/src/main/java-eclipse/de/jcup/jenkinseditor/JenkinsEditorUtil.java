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
 package de.jcup.jenkinseditor;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;

import de.jcup.eclipse.commons.ui.EclipseUtil;
import de.jcup.egradle.eclipse.ui.UnpersistedMarkerHelper;
import de.jcup.jenkins.linter.JenkinsLinterError;

public class JenkinsEditorUtil {

	private static final String LINTER_ERROR_ID = "de.jcup.jenkinseditor.linter.error";
	private static UnpersistedMarkerHelper linterMarkerHelper = new UnpersistedMarkerHelper(
			LINTER_ERROR_ID);
	

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
	public static void addLinterError(IEditorPart editor, JenkinsLinterError error, int severity) {
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
			linterMarkerHelper.createMarker(editorResource, error.getMessage(), error.getLine(), LINTER_ERROR_ID, severity, -1, -1);
		} catch (CoreException e) {
			JenkinsEditorLogSupport.INSTANCE.logError("Was not able to add error markers", e);
		}

	}

	
    public static ImageDescriptor createImageDescriptor(String path) {
        return EclipseUtil.createImageDescriptor(path, JenkinsEditorActivator.PLUGIN_ID);
    }
}
