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
package de.jcup.jenkinseditor.console;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.console.IConsoleView;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;
import org.eclipse.ui.part.IPageBookViewPage;

public class JenkinsEditorConsole extends MessageConsole {
	public JenkinsEditorConsole(ImageDescriptor imageDescriptor) {
		this("JenkinsEditor",imageDescriptor);
	}
	public JenkinsEditorConsole(String name, ImageDescriptor imageDescriptor) {
		super(name, imageDescriptor);
	}

	@Override
	protected void init() {
	    super.init();
	}
	
	@Override
	public IPageBookViewPage createPage(IConsoleView view) {
	    IPageBookViewPage page = super.createPage(view);
        return page;
	}
	
	@Override
	public MessageConsoleStream newMessageStream() {
		return super.newMessageStream();
	}

}
