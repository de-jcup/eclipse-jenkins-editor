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
 package de.jcup.jenkinseditor.handlers;

import java.io.IOException;

import de.jcup.jenkins.cli.JenkinsCLIConfiguration;
import de.jcup.jenkins.cli.JenkinsDefaultURLProvider;
import de.jcup.jenkinseditor.JenkinsEditorLogSupport;
import de.jcup.jenkinseditor.JenkinsEditorMessageDialogSupport;

public abstract class AbstractJenkinsCLIHandler extends AbstractJenkinsEditorHandler {

    protected JenkinsDefaultURLProvider jenkinsDefaultURLprovider = new JenkinsDefaultURLProvider();
    protected ConfigurationBuilder configBuilder = new ConfigurationBuilder();
    
    /**
     * Builds a valid jenkins cli configuration
     * @return configuration or <code>null</code>
     */
    protected final JenkinsCLIConfiguration buildConfiguration() {
        try {
            JenkinsCLIConfiguration configuration = configBuilder.createConfiguration(jenkinsDefaultURLprovider);
            return configuration;
        } catch (IOException e) {
            StringBuilder sb = new StringBuilder();
            sb.append("Was not able to build valid jenkins cli configuration");
            handleIOError(e, sb);
            return null;
        }
    }

    protected void handleIOError(IOException e, StringBuilder sb) {
        JenkinsEditorMessageDialogSupport.INSTANCE.showErrorWithDetails(e.getMessage(),sb.toString());
        
        JenkinsEditorLogSupport.INSTANCE.logError(sb.toString(), e);
    }
	
	
}