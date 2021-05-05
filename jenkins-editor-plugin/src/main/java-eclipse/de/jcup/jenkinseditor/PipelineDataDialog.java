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

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;

import de.jcup.jenkins.cli.PipelineConfigData;

public class PipelineDataDialog extends Dialog {


    private static final int RESET_ID = IDialogConstants.NO_TO_ALL_ID + 1;

    private Text jobNameText;
    private Text branchNameText;
    private Spinner buildNrSpinner;

    private PipelineConfigData result;

    private Type type;
    
    public enum Type{
        REPLAY("Replay pipeline build"),
        
        GET_LAST_CONSOLE_OUTPUT("Fetch last console output"),
        
        ;

        private String title;

        private Type(String title) {
            this.title=title;
        }
        
        public String getTitle() {
            return title;
        }
    }

    public PipelineDataDialog(Shell parentShell, PipelineConfigData data, Type type) {
        super(parentShell);
        this.result = data;
        this.type=type;
    }

    protected Control createDialogArea(Composite parent) {
        getShell().setText(type.getTitle());
        Composite comp = (Composite) super.createDialogArea(parent);
        GridLayout layout = (GridLayout) comp.getLayout();
        layout.numColumns = 2;
        
        jobNameText = appendTextField("Job: ", "Define jobName name", comp);
        jobNameText.setText(result.jobName);
        
        branchNameText = appendTextField("Branch: ", "Only necessary for multibranch pipeline builds!",comp);
        branchNameText.setText(result.branchName);
        branchNameText.setMessage("No branch defined");
        
        if (type!=Type.GET_LAST_CONSOLE_OUTPUT) {
            buildNrSpinner = appendSpinner("BuildNr.", "Build number where replay will start", comp);
            buildNrSpinner.setMinimum(1);
            buildNrSpinner.setMaximum(Integer.MAX_VALUE);
            buildNrSpinner.setSelection(result.buildNr);
        }
        
        return comp;
    }

    private Text appendTextField(String labelText, String tooltip, Composite comp) {

        Label label = new Label(comp, SWT.RIGHT);
        label.setText(labelText);
        label.setToolTipText(tooltip);

        Text text = new Text(comp, SWT.SINGLE);
        text.setToolTipText(tooltip);
        GridData data = new GridData(GridData.FILL_HORIZONTAL);
        text.setLayoutData(data);
        
        return text;

    }
    
    private Spinner appendSpinner(String labelText, String tooltip, Composite comp) {

        Label label = new Label(comp, SWT.RIGHT);
        label.setText(labelText);
        label.setToolTipText(tooltip);

        Spinner spinner = new Spinner(comp, SWT.BORDER);
        GridData data = new GridData(GridData.FILL_HORIZONTAL);
        spinner.setLayoutData(data);
        spinner.setToolTipText(tooltip);
        return spinner;

    }

    protected void createButtonsForButtonBar(Composite parent) {
        super.createButtonsForButtonBar(parent);
        createButton(parent, RESET_ID, "Reset All", false);
    }

    protected void buttonPressed(int buttonId) {
        if (buttonId == RESET_ID) {
            jobNameText.setText("");
            branchNameText.setText("");
            if (buildNrSpinner!=null) {
                buildNrSpinner.setSelection(1);
            }
        } else {
            super.buttonPressed(buttonId);
        }
    }

    @Override
    protected void okPressed() {
        if (buildNrSpinner!=null) {
            result.buildNr = buildNrSpinner.getSelection();
        }
        result.branchName = branchNameText.getText().trim();
        result.jobName = jobNameText.getText().trim();
        super.okPressed();
    }

}
