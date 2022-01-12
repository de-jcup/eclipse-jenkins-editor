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
