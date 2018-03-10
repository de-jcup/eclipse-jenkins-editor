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

import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import de.jcup.egradle.core.util.ILogSupport;
import de.jcup.jenkins.util.JenkinsLogAdaptable;

public class JenkinsEditorLogSupport implements ILogSupport, JenkinsLogAdaptable{

	public static final JenkinsEditorLogSupport INSTANCE = new JenkinsEditorLogSupport();
	
	@Override
	public void logInfo(String info) {
		getLog().log(new Status(IStatus.INFO, JenkinsEditorActivator.PLUGIN_ID, info));
	}

	@Override
	public void logWarning(String warning) {
		getLog().log(new Status(IStatus.WARNING, JenkinsEditorActivator.PLUGIN_ID, warning));
	}
	
	@Override
	public void logError(String error, Throwable t) {
		getLog().log(new Status(IStatus.ERROR, JenkinsEditorActivator.PLUGIN_ID, error,t));
	}

	protected ILog getLog() {
		ILog log = JenkinsEditorActivator.getDefault().getLog();
		return log;
	}
}
