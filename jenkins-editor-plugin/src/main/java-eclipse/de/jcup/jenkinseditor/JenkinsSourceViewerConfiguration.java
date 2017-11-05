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

import static de.jcup.egradle.eclipse.document.GroovyDocumentIdentifiers.*;
import static de.jcup.jenkinseditor.document.JenkinsDocumentIdentifiers.*;
import static de.jcup.jenkinseditor.preferences.JenkinsEditorSyntaxColorPreferenceConstants.*;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.hyperlink.IHyperlinkDetector;
import org.eclipse.jface.text.hyperlink.URLHyperlinkDetector;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.swt.SWT;

import de.jcup.egradle.core.text.DocumentIdentifier;
import de.jcup.egradle.eclipse.AbstractGroovySourceViewerConfiguration;
import de.jcup.egradle.eclipse.document.GroovyDocumentIdentifiers;
import de.jcup.egradle.eclipse.preferences.IEditorPreferences;
import de.jcup.jenkinseditor.document.JenkinsDocumentIdentifiers;
import de.jcup.jenkinseditor.preferences.JenkinsEditorPreferences;

public class JenkinsSourceViewerConfiguration extends AbstractGroovySourceViewerConfiguration {

	public JenkinsSourceViewerConfiguration(JenkinsEditor jenkinsEditor) {
		super(jenkinsEditor,COLOR_NORMAL_TEXT);
	}

	@Override
	public void updateTextScannerDefaultColorToken() {

	}

	@Override
	public IPresentationReconciler getPresentationReconciler(ISourceViewer sourceViewer) {
		PresentationReconciler reconciler = new PresentationReconciler();

		addDefaultPresentation(reconciler);

		addPresentation(reconciler, JAVA_KEYWORD.getId(), getPreferences().getColor(COLOR_JAVA_KEYWORD), SWT.BOLD);
		addPresentation(reconciler, GROOVY_KEYWORD.getId(), getPreferences().getColor(COLOR_GROOVY_KEYWORD), SWT.BOLD);
		// Groovy provides different strings: simple and GStrings, so we use
		// separate colors:
		addPresentation(reconciler, STRING.getId(), getPreferences().getColor(COLOR_NORMAL_STRING), SWT.NONE);
		addPresentation(reconciler, GSTRING.getId(), getPreferences().getColor(COLOR_GSTRING), SWT.NONE);

		addPresentation(reconciler, COMMENT.getId(), getPreferences().getColor(COLOR_COMMENT), SWT.NONE);
		addPresentation(reconciler, ANNOTATION.getId(), getPreferences().getColor(COLOR_ANNOTATION), SWT.NONE);
		addPresentation(reconciler, GROOVY_DOC.getId(), getPreferences().getColor(COLOR_GROOVY_DOC), SWT.NONE);
		addPresentation(reconciler, JENKINS_KEYWORD.getId(), getPreferences().getColor(COLOR_JENKINS_KEYWORDS),
				SWT.BOLD);

		addPresentation(reconciler, JENKINS_VARIABLE.getId(), getPreferences().getColor(COLOR_JENKINS_VARIABLES),
				SWT.ITALIC);
		addPresentation(reconciler, JAVA_LITERAL.getId(), getPreferences().getColor(COLOR_JAVA_LITERAL), SWT.BOLD);
		return reconciler;
	}

	
	protected IEditorPreferences getPreferences() {
		return JenkinsEditorPreferences.getInstance();
	}

	protected String[] createDefaultConfiguredContentTypes() {
		/* @formatter:off */
		return DocumentIdentifier.createStringIdBuilder().
				add(IDocument.DEFAULT_CONTENT_TYPE). 
				addAll(GroovyDocumentIdentifiers.values()).
				addAll(JenkinsDocumentIdentifiers.values())
				
				.build();
		/* @formatter:on */
	}

	@Override
	public IHyperlinkDetector[] getHyperlinkDetectors(ISourceViewer sourceViewer) {
		if (sourceViewer == null){
			return null;
		}

		return new IHyperlinkDetector[] { new URLHyperlinkDetector() };
	}

}
