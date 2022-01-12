/*
 * Copyright 2021 Albert Tregnaghi
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
 package de.jcup.jenkinseditor.preferences;

import java.awt.Desktop;
import java.net.URI;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import de.jcup.jenkinseditor.JenkinsEditorLogSupport;

public class JenkinsOpenLinkInBrowserListener extends SelectionAdapter {

    public interface URIFetcher {
        public URI getURI();
    }

    private URIFetcher fetcher;

    public JenkinsOpenLinkInBrowserListener(URIFetcher fetcher) {
        this.fetcher = fetcher;
    }

    @Override
    public void widgetSelected(SelectionEvent e) {
        if (!Desktop.isDesktopSupported()) {
            return;
        }
        Desktop desktop = Desktop.getDesktop();
        if (!desktop.isSupported(Desktop.Action.BROWSE)) {
            return;
        }
        URI uri = fetcher.getURI();
        if (uri == null) {
            return;
        }
        Thread t = new Thread(() -> {
            try {
                desktop.browse(uri);
            } catch (Exception e1) {
                JenkinsEditorLogSupport.INSTANCE.logError("Was not able to open uri:" + uri.toASCIIString(), e1);
            }
        });
        t.setName("tmp-open-browser");
        t.start();
    }
}
