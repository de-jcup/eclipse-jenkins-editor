package de.jcup.jenkinseditor;

import static de.jcup.jenkinseditor.document.JenkinsDocumentIdentifiers.*;
import static de.jcup.jenkinseditor.preferences.JenkinsEditorSyntaxColorPreferenceConstants.*;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.hyperlink.IHyperlinkDetector;
import org.eclipse.jface.text.hyperlink.URLHyperlinkDetector;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.swt.SWT;

import de.jcup.egradle.eclipse.AbstractGroovySourceViewerConfiguration;
import de.jcup.egradle.eclipse.preferences.IEditorPreferences;
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

	@Override
	public String[] getConfiguredContentTypes(ISourceViewer sourceViewer) {
		/* @formatter:off */
		return allIdsToStringArray( 
				IDocument.DEFAULT_CONTENT_TYPE);
		/* @formatter:on */
	}

	@Override
	public IHyperlinkDetector[] getHyperlinkDetectors(ISourceViewer sourceViewer) {
		if (sourceViewer == null)
			return null;

		return new IHyperlinkDetector[] { new URLHyperlinkDetector() };
	}

}
