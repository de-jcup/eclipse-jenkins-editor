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
package de.jcup.jenkinseditor.outline;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.IStyledLabelProvider;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import de.jcup.egradle.eclipse.ui.AbstractGroovyBasedQuickOutline;
import de.jcup.egradle.eclipse.ui.IExtendedEditor;
import de.jcup.jenkinseditor.JenkinsEditorActivator;

/**
 * This dialog is inspired by: <a href=
 * "https://github.com/eclipse/eclipse.jdt.ui/blob/master/org.eclipse.jdt.ui/ui/org/eclipse/jdt/internal/ui/text/AbstractInformationControl.java">org.eclipse.jdt.internal.ui.text.AbstractInformationControl</a>
 * and <a href=
 * "https://github.com/eclipse/eclipse.jdt.ui/blob/master/org.eclipse.jdt.ui/ui/org/eclipse/jdt/internal/ui/text/JavaOutlineInformationControl.java">org.eclipse.jdt.internal.ui.text.JavaOutlineInformationControl</a>
 * 
 * @author Albert Tregnaghi
 *
 */
public class JenkinsQuickOutlineDialog extends AbstractGroovyBasedQuickOutline {

	/**
	 * Creates a quick outline dialog
	 * 
	 * @param adaptable
	 *            an adapter which should be able to provide a tree content
	 *            provider and gradle editor. If gradle editor is not set a
	 *            selected item will only close the dialog but do not select
	 *            editor parts..
	 * @param parent
	 *            shell to use is null the outline will have no content! If the
	 *            gradle editor is null location setting etc. will not work.
	 * @param infoText
	 *            information to show at bottom of dialog
	 */
	public JenkinsQuickOutlineDialog(IAdaptable adaptable, Shell parent, String infoText) {
		super(adaptable, parent, "EGradle quick outline", MIN_WIDTH, MIN_HEIGHT, infoText);
		this.editor = adaptable.getAdapter(IExtendedEditor.class);
	}

	@Override
	protected AbstractUIPlugin getUIPlugin() {
		JenkinsEditorActivator editorActivator = JenkinsEditorActivator.getDefault();
		return editorActivator;
	}

	protected IStyledLabelProvider createdStyledLabelProvider() {
		IStyledLabelProvider labelProvider = new JenkinsEditorOutlineLabelProvider();
		return labelProvider;
	}

}
