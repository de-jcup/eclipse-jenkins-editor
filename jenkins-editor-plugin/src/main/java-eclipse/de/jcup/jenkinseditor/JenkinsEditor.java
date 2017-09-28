/*
 * Copyright 2016 Albert Tregnaghi
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

import org.eclipse.jface.text.source.SourceViewerConfiguration;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.texteditor.IDocumentProvider;

import de.jcup.egradle.core.util.ILogSupport;
import de.jcup.egradle.eclipse.preferences.IEditorPreferences;
import de.jcup.egradle.eclipse.ui.AbstractGroovyBasedContentOutlinePage;
import de.jcup.egradle.eclipse.ui.AbstractGroovyBasedEditor;
import de.jcup.egradle.eclipse.ui.AbstractGroovyBasedEditorOutlineContentProvider;
import de.jcup.egradle.eclipse.ui.AbstractGroovyBasedQuickOutline;
import de.jcup.egradle.eclipse.util.ColorManager;
import de.jcup.jenkinseditor.document.JenkinsFileDocumentProvider;
import de.jcup.jenkinseditor.document.JenkinsTextFileDocumentProvider;
import de.jcup.jenkinseditor.outline.JenkinsEditorContentOutlinePage;
import de.jcup.jenkinseditor.outline.JenkinsEditorOutlineContentProvider;
import de.jcup.jenkinseditor.outline.JenkinsQuickOutlineDialog;
import de.jcup.jenkinseditor.preferences.JenkinsEditorPreferences;

public class JenkinsEditor extends AbstractGroovyBasedEditor {
	/** The COMMAND_ID of this editor as defined in plugin.xml */
	public static final String EDITOR_ID = "de.jcup.jenkinseditor.editors.JenkinsEditor";
	/** The COMMAND_ID of the editor context menu */
	public static final String EDITOR_CONTEXT_MENU_ID = EDITOR_ID + ".context";
	/** The COMMAND_ID of the editor ruler context menu */
	public static final String EDITOR_RULER_CONTEXT_MENU_ID = EDITOR_CONTEXT_MENU_ID + ".ruler";

	@Override
	public ILogSupport getLogSupport() {
		return JenkinsEditorLogSupport.INSTANCE;
	}

	@Override
	public IEditorPreferences getPreferences() {
		return JenkinsEditorPreferences.getInstance();
	}

	@Override
	protected AbstractGroovyBasedContentOutlinePage createContentOutlinePage() {
		return new JenkinsEditorContentOutlinePage(this);
	}

	@Override
	protected IDocumentProvider createFileStoreEditorInputProvider() {
		return new JenkinsTextFileDocumentProvider();
	}

	@Override
	protected AbstractGroovyBasedEditorOutlineContentProvider createOutlineContentProvider() {
		return new JenkinsEditorOutlineContentProvider(this);
	}

	@Override
	protected AbstractGroovyBasedQuickOutline createQuickOutlineDialog(Shell shell) {
		return new JenkinsQuickOutlineDialog(this, shell, "Quick outline");
	}

	@Override
	protected SourceViewerConfiguration createSourceViewerConfiguration() {
		return new JenkinsSourceViewerConfiguration(this);
	}

	@Override
	protected IDocumentProvider createStandardEditorInputProvider() {
		return new JenkinsFileDocumentProvider();
	}

	@Override
	protected ColorManager getColorManager() {
		return JenkinsEditorActivator.getDefault().getColorManager();
	}

	protected String getEditorInstanceRulerContextId() {
		return EDITOR_RULER_CONTEXT_MENU_ID;
	}

	protected String getEditorInstanceContextId() {
		return EDITOR_CONTEXT_MENU_ID;
	}

	@Override
	protected String getPluginId() {
		return JenkinsEditorActivator.PLUGIN_ID;
	}

	protected String getEditorIconPath() {
		return "icons/jenkinseditor/jenkins-editor.png";
	}

	protected String getEditorIconPathOnError() {
		return "icons/jenkinseditor/jenkins-editor-with-error.png";
	}
}
