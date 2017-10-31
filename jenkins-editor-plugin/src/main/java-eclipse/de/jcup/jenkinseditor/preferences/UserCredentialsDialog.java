package de.jcup.jenkinseditor.preferences;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class UserCredentialsDialog extends Dialog {
	
	private String labelForSecrectKey;

	public UserCredentialsDialog(Shell parentShell, String labelForSecrectKey, UserCredentials data) {
		super(parentShell);
		this.labelForSecrectKey=labelForSecrectKey;
		this.result=data;
	}

	private static final int RESET_ID = IDialogConstants.NO_TO_ALL_ID + 1;

	private Text usernameField;

	private Text secretField;

	private UserCredentials result;

	protected Control createDialogArea(Composite parent) {
		Composite comp = (Composite) super.createDialogArea(parent);

		GridLayout layout = (GridLayout) comp.getLayout();
		layout.numColumns = 2;

		Label usernameLabel = new Label(comp, SWT.RIGHT);
		usernameLabel.setText("Username: ");

		usernameField = new Text(comp, SWT.SINGLE);
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		usernameField.setLayoutData(data);

		Label passwordLabel = new Label(comp, SWT.RIGHT);
		passwordLabel.setText(labelForSecrectKey+": ");

		secretField = new Text(comp, SWT.SINGLE | SWT.PASSWORD);
		data = new GridData(GridData.FILL_HORIZONTAL);
		secretField.setLayoutData(data);

		return comp;
	}

	protected void createButtonsForButtonBar(Composite parent) {
		super.createButtonsForButtonBar(parent);
		createButton(parent, RESET_ID, "Reset All", false);
	}

	protected void buttonPressed(int buttonId) {
		if (buttonId == RESET_ID) {
			usernameField.setText("");
			secretField.setText("");
		} else {
			super.buttonPressed(buttonId);
		}
	}
	
	@Override
	protected void okPressed() {
		result.username=usernameField.getText();
		result.secret=secretField.getText();
		
		super.okPressed();
	}

}
