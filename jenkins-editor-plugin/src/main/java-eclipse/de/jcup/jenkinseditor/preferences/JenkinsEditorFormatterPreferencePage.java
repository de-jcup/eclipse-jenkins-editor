package de.jcup.jenkinseditor.preferences;
/*
 * Copyright 2024 Albert Tregnaghi
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

import static de.jcup.jenkinseditor.preferences.JenkinsEditorPreferenceConstants.*;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IntegerFieldEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

public class JenkinsEditorFormatterPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	public JenkinsEditorFormatterPreferencePage() {
	    setPreferenceStore(JenkinsEditorPreferences.getInstance().getPreferenceStore());
	}
	
	@Override
	public void init(IWorkbench workbench) {
		
	}

	@Override
	protected void createFieldEditors() {
		Composite parent = getFieldEditorParent();
		  
        IntegerFieldEditor indentEditor = new IntegerFieldEditor(P_SOURCE_FORMAT_INDENT.getId(),
                "Indent / Tab replacement", parent);
        indentEditor.setValidRange(2, 10);
        addField(indentEditor);
        indentEditor.getLabelControl(parent).setToolTipText(
                "Amount of spaces used by source formatter to make indention");
			
		
	}
	
}