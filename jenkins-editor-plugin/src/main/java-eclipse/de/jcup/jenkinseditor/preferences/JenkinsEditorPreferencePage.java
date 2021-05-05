package de.jcup.jenkinseditor.preferences;
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

import static de.jcup.jenkinseditor.JenkinsEditorConstants.*;
import static de.jcup.jenkinseditor.preferences.JenkinsEditorPreferenceConstants.*;

import java.util.ArrayList;

import org.eclipse.core.runtime.Assert;
import org.eclipse.equinox.security.storage.ISecurePreferences;
import org.eclipse.equinox.security.storage.SecurePreferencesFactory;
import org.eclipse.equinox.security.storage.StorageException;
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.ColorFieldEditor;
import org.eclipse.jface.preference.ComboFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.FileFieldEditor;
import org.eclipse.jface.preference.IntegerFieldEditor;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import de.jcup.egradle.eclipse.preferences.AbstractEditorPreferences;
import de.jcup.egradle.eclipse.ui.SWTFactory;
import de.jcup.egradle.eclipse.util.EclipseDevelopmentSettings;
import de.jcup.jenkins.cli.AbstractJenkinsCLIResult;
import de.jcup.jenkins.cli.JenkinsDefaultURLProvider;
import de.jcup.jenkins.cli.JenkinsHelpCommand;
import de.jcup.jenkins.cli.JenkinsLinterCLICommand;
import de.jcup.jenkins.cli.JenkinsCLIConfiguration.AuthMode;
import de.jcup.jenkins.linter.JenkinsLinterErrorLevel;
import de.jcup.jenkinseditor.JenkinsEditorLogSupport;
import de.jcup.jenkinseditor.JenkinsEditorMessageDialogSupport;
import de.jcup.jenkinseditor.console.JenkinsEditorConsoleUtil;
import de.jcup.jenkinseditor.handlers.ConfigurationBuilder;

/**
 * Parts are inspired by <a href=
 * "https://github.com/eclipse/eclipse.jdt.ui/blob/master/org.eclipse.jdt.ui/ui/org/eclipse/jdt/internal/ui/preferences/JavaEditorAppearanceConfigurationBlock.java">org.eclipse.jdt.internal.ui.preferences.JavaEditorAppearanceConfigurationBlock
 * </a>
 * 
 * @author Albert Tregnaghi
 *
 */
public class JenkinsEditorPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

    protected static final int INDENT = 20;

    protected static void indent(Control control) {
        ((GridData) control.getLayoutData()).horizontalIndent += INDENT;
    }

    private JenkinsDefaultURLProvider jenkinsDefaultURLProvider = new JenkinsDefaultURLProvider();

    private Button bracketHighlightingCheckbox;
    private Button enclosingBracketsRadioButton;
    private Button matchingBracketAndCaretLocationRadioButton;
    private Button matchingBracketRadioButton;

    private BooleanFieldEditor linkEditorWithOutline;

    private ColorFieldEditor matchingBracketsColor;

    private ArrayList<MasterButtonSlaveSelectionListener> masterSlaveListeners = new ArrayList<>();

    private boolean enclosingBrackets;
    private boolean highlightBracketAtCaretLocation;
    private boolean matchingBrackets;
    private BooleanFieldEditor autoCreateEndBrackets;
    private IntegerFieldEditor waitForLogsInSeconds;
    StringFieldEditor jenkinsUrl;
    private FileFieldEditor jarFileLocation;
    // private RadioGroupFieldEditor authorizationType;
    UserCredentials temporaryCredentials;
    ConfigurationBuilder configBuilder;

    BooleanFieldEditor certificateCheckDisabled;

    private BooleanFieldEditor onlyStrictCodeCompletion;

    public JenkinsEditorPreferencePage() {
        super(GRID);
        setPreferenceStore(JenkinsEditorPreferences.getInstance().getPreferenceStore());
        configBuilder = new ConfigurationBuilder();
    }

    @Override
    public void init(IWorkbench workbench) {

    }

    @Override
    public void performDefaults() {
        super.performDefaults();
        reloadBracketHighlightingPreferenceDefaults();
    }

    @Override
    public boolean performOk() {
        boolean ok = super.performOk();
        if (ok) {
            setBoolean(P_EDITOR_MATCHING_BRACKETS_ENABLED, matchingBrackets);
            setBoolean(P_EDITOR_HIGHLIGHT_BRACKET_AT_CARET_LOCATION, highlightBracketAtCaretLocation);
            setBoolean(P_EDITOR_ENCLOSING_BRACKETS, enclosingBrackets);

            if (temporaryCredentials != null) {
                ISecurePreferences preferences = SecurePreferencesFactory.getDefault();
                ISecurePreferences node = preferences.node(ID_SECURED_CREDENTIALS);
                try {
                    node.put(ID_SECURED_USER_KEY, temporaryCredentials.username, true);
                    node.put(ID_SECURED_API_KEY, temporaryCredentials.secret, true);
                } catch (StorageException e1) {
                    JenkinsEditorLogSupport.INSTANCE.logError("Wasn't able to store credentials", e1);
                    return false;
                }
            }
        }
        return ok;
    }

    protected void createDependency(Button master, Control slave) {
        Assert.isNotNull(slave);
        indent(slave);
        MasterButtonSlaveSelectionListener listener = new MasterButtonSlaveSelectionListener(master, slave);
        master.addSelectionListener(listener);
        this.masterSlaveListeners.add(listener);
    }

    private void createJenkinsWaitForLogsInSeconds(Composite parent) {
        String name = JenkinsEditorPreferenceConstants.P_WAIT_FOR_JENKINS_LOGS_UNTIL_REFERSH_IN_SECONDS.getId();
        String labelText = "Wait for log refresh in seconds";

        /* @formatter:on */
        waitForLogsInSeconds = new IntegerFieldEditor(name, labelText, parent);
        waitForLogsInSeconds.getLabelControl(parent).setToolTipText(
                "Setup this value to handle the amount of seconds, editor will wait until fetching new console output.\n"+
                "This is interesting for actions grabbing the log output - like replay action");
        addField(waitForLogsInSeconds);
    }

    private void createJenkinsLinterErrorLevelComboBox(Composite parent) {
        String name = JenkinsEditorPreferenceConstants.JENKINS_LINTER_ERROR_LEVEL.getId();
        String labelText = "Jenkins validation failures are shown as ";

        /* @formatter:off */
        String[][] entryNamesAndValues = 
                new String[][] { 
            getLabelAndValue(JenkinsLinterErrorLevel.ERROR),
            getLabelAndValue(JenkinsLinterErrorLevel.WARNING),
            getLabelAndValue(JenkinsLinterErrorLevel.INFO)
        };
        /* @formatter:on */
        ComboFieldEditor comboFieldEditor = new ComboFieldEditor(name, labelText, entryNamesAndValues, parent);
        addField(comboFieldEditor);
    }

    private String[] getLabelAndValue(JenkinsLinterErrorLevel errorLevel) {
        return new String[] { errorLevel.name(), errorLevel.getId() };
    }
    
    private String[] getLabelAndValue(AuthMode authMode) {
        return new String[] { authMode.name(), authMode.getId() };
    }

    @Override
    protected void createFieldEditors() {

        /* -------------------------------------------------------------- */
        /* ------------------------ CLI setup ------------------------- */
        /* -------------------------------------------------------------- */
        createJenkinsCLIFieldEditors();

        /* -------------------------------------------------------------- */
        /* ------------------------ Log action ------------------------- */
        /* -------------------------------------------------------------- */
        createJenkinsWaitForLogsInSeconds(getFieldEditorParent());
        /* -------------------------------------------------------------- */
        /* ------------------------ ERROR LEVEL ------------------------- */
        /* -------------------------------------------------------------- */
        createJenkinsLinterErrorLevelComboBox(getFieldEditorParent());

        /* -------------------------------------------------------------- */
        /* ------------------------ APPEARANCE -------------------------- */
        /* -------------------------------------------------------------- */
        GridData appearanceLayoutData = new GridData();
        appearanceLayoutData.horizontalAlignment = GridData.FILL;
        appearanceLayoutData.verticalAlignment = GridData.BEGINNING;
        appearanceLayoutData.grabExcessHorizontalSpace = true;
        appearanceLayoutData.grabExcessVerticalSpace = false;
        appearanceLayoutData.verticalSpan = 2;
        appearanceLayoutData.horizontalSpan = 3;

        Composite appearanceComposite = new Composite(getFieldEditorParent(), SWT.NONE);
        GridLayout layout = new GridLayout();
        layout.numColumns = 2;
        appearanceComposite.setLayout(layout);
        appearanceComposite.setLayoutData(appearanceLayoutData);

        createOtherFieldEditors(appearanceComposite);

        createBracketsFieldEditors(appearanceComposite);
    }

    protected void createOtherFieldEditors(Composite appearanceComposite) {
        /* OTHER */
        Composite otherComposite = new Composite(appearanceComposite, SWT.NONE);
        GridLayout otherLayout = new GridLayout();
        otherLayout.marginWidth = 0;
        otherLayout.marginHeight = 0;
        otherComposite.setLayout(otherLayout);

        /* linking with outline */
        linkEditorWithOutline = new BooleanFieldEditor(P_LINK_OUTLINE_WITH_EDITOR.getId(), "New opened editors are linked with outline", otherComposite);
        linkEditorWithOutline.getDescriptionControl(otherComposite).setToolTipText("Via this setting the default behaviour for new opened outlines is set");
        addField(linkEditorWithOutline);
    }

    protected void createBracketsFieldEditors(Composite appearanceComposite) {
        /* BRACKETS */
        /*
         * Why so ugly implemented and not using field editors ? Because
         * SourceViewerDecorationSupport needs 3 different preference keys to do its
         * jobName, so this preference doing must be same as on Java editor preferences.
         */
        Label spacer = new Label(appearanceComposite, SWT.LEFT);
        GridData gd = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
        gd.horizontalSpan = 2;
        gd.heightHint = convertHeightInCharsToPixels(1) / 2;
        spacer.setLayoutData(gd);

        String label = "Bracket highlighting";

        bracketHighlightingCheckbox = addButton(appearanceComposite, SWT.CHECK, label, 0, new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                matchingBrackets = bracketHighlightingCheckbox.getSelection();
            }
        });

        Composite radioComposite = new Composite(appearanceComposite, SWT.NONE);
        GridLayout radioLayout = new GridLayout();
        radioLayout.marginWidth = 0;
        radioLayout.marginHeight = 0;
        radioComposite.setLayout(radioLayout);

        label = "highlight matching bracket";
        matchingBracketRadioButton = addButton(radioComposite, SWT.RADIO, label, 0, new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                if (matchingBracketRadioButton.getSelection()) {
                    highlightBracketAtCaretLocation = false;
                }
            }
        });
        createDependency(bracketHighlightingCheckbox, matchingBracketRadioButton);

        label = "highlight matching bracket and caret location";
        matchingBracketAndCaretLocationRadioButton = addButton(radioComposite, SWT.RADIO, label, 0, new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                if (matchingBracketAndCaretLocationRadioButton.getSelection()) {
                    highlightBracketAtCaretLocation = true;
                }
            }
        });
        createDependency(bracketHighlightingCheckbox, matchingBracketAndCaretLocationRadioButton);

        label = "highlight enclosing brackets";
        enclosingBracketsRadioButton = addButton(radioComposite, SWT.RADIO, label, 0, new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                boolean selection = enclosingBracketsRadioButton.getSelection();
                enclosingBrackets = selection;
                if (selection) {
                    highlightBracketAtCaretLocation = true;
                }
            }
        });
        createDependency(bracketHighlightingCheckbox, enclosingBracketsRadioButton);

        matchingBracketsColor = new ColorFieldEditor(P_EDITOR_MATCHING_BRACKETS_COLOR.getId(), "Matching brackets color", radioComposite);
        addField(matchingBracketsColor);
        createDependency(bracketHighlightingCheckbox, matchingBracketsColor.getLabelControl(radioComposite));
        createDependency(bracketHighlightingCheckbox, matchingBracketsColor.getColorSelector().getButton());

        autoCreateEndBrackets = new BooleanFieldEditor(P_EDITOR_AUTO_CREATE_END_BRACKETSY.getId(), "Auto create ending brackets", getFieldEditorParent());
        addField(autoCreateEndBrackets);
        
        onlyStrictCodeCompletion = new BooleanFieldEditor(P_EDITOR_SHOW_ALSO_NON_STRICT_CODE_PROPOSALS.getId(), "Show also NON strict code proposals", getFieldEditorParent());
        onlyStrictCodeCompletion.getDescriptionControl(getFieldEditorParent()).setToolTipText("When enabled even code proposals for not correct context situations are proposed (but with a warn icon).\n\nYou need to close editors and open again when changing this value!");
        addField(onlyStrictCodeCompletion);

    }

    protected void createJenkinsCLIFieldEditors() {
        /* -------------------------------------------------------------- */
        /* ------------------------ JENKINS CLI ------------------------- */
        /* -------------------------------------------------------------- */
        Group jenkinsCLIComposite = new Group(getFieldEditorParent(), SWT.NONE);
        jenkinsCLIComposite.setText("Jenkins CLI setup");
        GridLayout jenkinsCLICompositeLayout = new GridLayout(3, true);
        jenkinsCLICompositeLayout.marginWidth = 10;
        jenkinsCLICompositeLayout.marginHeight = 0;
        jenkinsCLICompositeLayout.marginLeft = 20;
        jenkinsCLIComposite.setLayout(jenkinsCLICompositeLayout);

        GridData jenkinsCLICompositeLayoutData = new GridData();
        jenkinsCLICompositeLayoutData.horizontalAlignment = GridData.FILL;
        jenkinsCLICompositeLayoutData.verticalAlignment = GridData.BEGINNING;
        jenkinsCLICompositeLayoutData.grabExcessHorizontalSpace = true;
        jenkinsCLICompositeLayoutData.grabExcessVerticalSpace = false;
        // jenkinsCLICompositeLayoutData.verticalSpan = 2;
        jenkinsCLICompositeLayoutData.horizontalSpan = 3;

        jenkinsCLIComposite.setLayoutData(jenkinsCLICompositeLayoutData);

        jenkinsUrl = new StringFieldEditor(P_JENKINS_URL.getId(), "Jenkins URL (optional)", jenkinsCLIComposite);
        jenkinsUrl.getLabelControl(jenkinsCLIComposite).setToolTipText("Set jenkins URL - when empty default value will be used");
        jenkinsUrl.setEmptyStringAllowed(true);
        addField(jenkinsUrl);

        Text jenkinsDefaultURLtext = SWTFactory.createText(jenkinsCLIComposite, SWT.NONE, SWT.FILL);
        jenkinsDefaultURLtext.setFont(JFaceResources.getFontRegistry().getItalic(JFaceResources.DEFAULT_FONT));
        jenkinsDefaultURLtext.setEditable(false);
        jenkinsDefaultURLtext.setText("(" + jenkinsDefaultURLProvider.createDefaultURLDescription() + ")");

        // disable certificate check
        certificateCheckDisabled = new BooleanFieldEditor(P_CERTIFICATE_CHECK_DISABLED.getId(), "Disable certificate check", jenkinsCLIComposite);
        addField(certificateCheckDisabled);

        BooleanFieldEditor useEclipseProxySettingsEnabled = new BooleanFieldEditor(P_USE_ECLIPSE_PROXY_SETTINGS_ENABLED.getId(), "Use eclipse proxy settings", jenkinsCLIComposite);
        addField(useEclipseProxySettingsEnabled);
        
        String name = JenkinsEditorPreferenceConstants.JENKINS_AUTH_MODE.getId();
        String labelText = "Authentication done by";

        /* @formatter:off */
        String[][] entryNamesAndValues = 
                new String[][] { 
            getLabelAndValue(AuthMode.API_TOKEN),
            getLabelAndValue(AuthMode.SSH)
        };
        /* @formatter:on */
        ComboFieldEditor comboFieldEditor = new ComboFieldEditor(name, labelText, entryNamesAndValues, jenkinsCLIComposite);
        addField(comboFieldEditor);
        
        

        jarFileLocation = new FileFieldEditor(P_PATH_TO_JENKINS_CLI_JAR.getId(), "Path to jenkins-cli.jar (optional)", jenkinsCLIComposite);
        jarFileLocation.setFileExtensions(new String[] { "*.jar" });
        jarFileLocation.getLabelControl(jenkinsCLIComposite).setToolTipText("You can set here the location of another jenkins-cli.jar which you can download by your running Jenkins instance.");
        addField(jarFileLocation);

        Label passwordLabel = new Label(jenkinsCLIComposite, SWT.LEFT);
        passwordLabel.setText("User credentials : ");

        Text passwordField = new Text(jenkinsCLIComposite, SWT.SINGLE | SWT.PASSWORD);
        GridData data = new GridData(GridData.FILL_HORIZONTAL);
        passwordField.setLayoutData(data);
        passwordField.setText("encryptedthings");

        Button credentialsButton = new Button(jenkinsCLIComposite, SWT.PUSH);
        credentialsButton.setText("Credentials ...");
        credentialsButton.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                temporaryCredentials = JenkinsEditorMessageDialogSupport.INSTANCE.showUsernamePassword("API Key");
            }
        });
        credentialsButton.setLayoutData(data);

        Button connectionTestButton = new Button(jenkinsCLIComposite, SWT.PUSH);
        connectionTestButton.setText("Test connection");
        connectionTestButton.addSelectionListener(new JenkinsTestCommandExecutionListener(this, new JenkinsLinterCLICommand(), null));


        if (EclipseDevelopmentSettings.DEBUG_ADD_SPECIAL_MENUS) {
            Button showCommandsTestButton = new Button(jenkinsCLIComposite, SWT.PUSH);
            JenkinsTestCommandExecutionListener listener = new JenkinsTestCommandExecutionListener(this, new JenkinsHelpCommand(), new HelpCommandResultHandler());
            listener.setErrorMessage("Was not able to fetch commands");
            listener.setStartMessage("Try to fetch commands from server");
            listener.setSuccessmessage("Fetched commands from server look at console");
            showCommandsTestButton.setText("Show commands");
            showCommandsTestButton.addSelectionListener(listener);
        }

    }

    interface JenkinsResultHandler {
        public void handle(AbstractJenkinsCLIResult result);
    }

    private class HelpCommandResultHandler implements JenkinsResultHandler {

        @Override
        public void handle(AbstractJenkinsCLIResult result) {
            StringBuilder sb = new StringBuilder();
            for (String string : result.getLines()) {
                sb.append(string);
                sb.append("\n");
            }
            JenkinsEditorConsoleUtil.output("Help command output\n" + sb.toString());
            JenkinsEditorConsoleUtil.showConsole();
        }

    }

    @Override
    protected void initialize() {
        initializeBracketHighlightingPreferences();
        super.initialize();
        updateSlaveComponents();
    }

    private Button addButton(Composite parent, int style, String label, int indentation, SelectionListener listener) {
        Button button = new Button(parent, style);
        button.setText(label);

        GridData gd = new GridData(32);
        gd.horizontalIndent = indentation;
        gd.horizontalSpan = 2;
        button.setLayoutData(gd);
        button.addSelectionListener(listener);

        return button;
    }

    private void setBoolean(JenkinsEditorPreferenceConstants id, boolean value) {
        getPreferences().setBooleanPreference(id, value);
    }

    private boolean getBoolean(JenkinsEditorPreferenceConstants id) {
        return getPreferences().getBooleanPreference(id);
    }

    private boolean getDefaultBoolean(JenkinsEditorPreferenceConstants id) {
        return getPreferences().getDefaultBooleanPreference(id);
    }

    private AbstractEditorPreferences getPreferences() {
        return JenkinsEditorPreferences.getInstance();
    }

    private void initializeBracketHighlightingPreferences() {
        matchingBrackets = getBoolean(P_EDITOR_MATCHING_BRACKETS_ENABLED);
        highlightBracketAtCaretLocation = getBoolean(P_EDITOR_HIGHLIGHT_BRACKET_AT_CARET_LOCATION);
        enclosingBrackets = getBoolean(P_EDITOR_ENCLOSING_BRACKETS);

        updateBracketUI();
    }

    private void reloadBracketHighlightingPreferenceDefaults() {
        matchingBrackets = getDefaultBoolean(P_EDITOR_MATCHING_BRACKETS_ENABLED);
        highlightBracketAtCaretLocation = getDefaultBoolean(P_EDITOR_HIGHLIGHT_BRACKET_AT_CARET_LOCATION);
        enclosingBrackets = getDefaultBoolean(P_EDITOR_ENCLOSING_BRACKETS);

        updateBracketUI();
    }

    private void updateBracketUI() {
        this.bracketHighlightingCheckbox.setSelection(matchingBrackets);

        this.enclosingBracketsRadioButton.setSelection(enclosingBrackets);
        if (!(enclosingBrackets)) {
            this.matchingBracketRadioButton.setSelection(!(highlightBracketAtCaretLocation));
            this.matchingBracketAndCaretLocationRadioButton.setSelection(highlightBracketAtCaretLocation);
        }
        updateSlaveComponents();
    }

    private void updateSlaveComponents() {
        for (MasterButtonSlaveSelectionListener listener : masterSlaveListeners) {
            listener.updateSlaveComponent();
        }
    }

    private class MasterButtonSlaveSelectionListener implements SelectionListener {
        private Button master;
        private Control slave;

        public MasterButtonSlaveSelectionListener(Button master, Control slave) {
            this.master = master;
            this.slave = slave;
        }

        @Override
        public void widgetDefaultSelected(SelectionEvent e) {

        }

        @Override
        public void widgetSelected(SelectionEvent e) {
            updateSlaveComponent();
        }

        private void updateSlaveComponent() {
            boolean state = master.getSelection();
            slave.setEnabled(state);
        }

    }

}
