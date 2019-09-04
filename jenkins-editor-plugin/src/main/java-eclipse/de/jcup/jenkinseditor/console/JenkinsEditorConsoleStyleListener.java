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

import static de.jcup.jenkinseditor.preferences.JenkinsEditorColorConstants.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.LineStyleEvent;
import org.eclipse.swt.custom.LineStyleListener;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;

import de.jcup.jenkinseditor.JenkinsEditorActivator;

public class JenkinsEditorConsoleStyleListener implements LineStyleListener {
    private Collection<ParseData> parseDataByIndex = new ArrayList<>();

    public JenkinsEditorConsoleStyleListener() {
        addParseDataByIndex("Finished: SUCCESS", GREEN);
        addParseDataByIndex("Finished: FAILURE", RED);
        addParseDataByIndex("[Pipeline] stage", MIDDLE_ORANGE, false);
        addParseDataByIndex("[Pipeline] End of Pipeline", BRIGHT_BLUE, true);
        addParseDataByIndex("[Pipeline]", BRIGHT_BLUE);
        addParseDataByIndex("deprecated", BRIGHT_RED);
        addParseDataByIndex("warning", BRIGHT_RED);
        addParseDataByIndex("Recording test results", MIDDLE_GRAY);
        addParseDataByIndex(FetchLastLogFilesFromServer.WAITING_FOR_MORE_JENKINS_BUILD_OUTPUT, MIDDLE_GRAY);
    }

    void addParseDataByIndex(String substring, RGB color) {
        addParseDataByIndex(substring, color, false);
    }

    void addParseDataByIndex(String substring, RGB color, boolean bold) {
        ParseData data = new ParseData();
        data.subString = substring;
        data.color = color;
        data.bold = bold;
        parseDataByIndex.add(data);
    }

    int lastRangeEnd = 0;

    @Override
    public void lineGetStyle(LineStyleEvent event) {
        if (event == null) {
            return;
        }
        String lineText = event.lineText;
        if (StringUtils.isBlank(lineText)) {
            return;
        }
        /* styling */
        StyleRange defStyle;

        boolean atLeastOneStyle = event.styles != null && event.styles.length > 0;
        if (atLeastOneStyle) {
            defStyle = (StyleRange) event.styles[0].clone();
            if (defStyle.background == null) {
                defStyle.background = getColor(BLACK);
            }
        } else {
            defStyle = new StyleRange(1, lastRangeEnd, getColor(BLACK), getColor(WHITE), SWT.NORMAL);
        }

        lastRangeEnd = 0;

        List<StyleRange> ranges = new ArrayList<StyleRange>();
        boolean handled = false;
        handled = handled || markLine(event, lineText, ranges, handled, "[Pipeline] { (", BRIGHT_BLUE, false, MIDDLE_ORANGE, true);
        handled = handled || markLine(event, lineText, ranges, handled, "[Pipeline] End of Pipeline", BRIGHT_BLUE, false, MIDDLE_ORANGE, true);
        handled = handled || markLine(event, lineText, ranges, handled, "ERROR: ", RED, false, MIDDLE_ORANGE, true);
        handled = handled || markLine(event, lineText, ranges, handled, "Started by user ", MIDDLE_GRAY, false, MIDDLE_ORANGE, true);
        handled = handled || markLine(event, lineText, ranges, handled, "Replayed ", MIDDLE_GRAY, false, MIDDLE_ORANGE, true);
        handled = handled || markLine(event, lineText, ranges, handled, "Sending email to: ", MIDDLE_GRAY, false, MIDDLE_ORANGE, true);

        /* index parts and other */
        if (!handled) {
            for (ParseData data : parseDataByIndex) {
                parse(event, defStyle, lineText, ranges, data);
            }
        }

        if (!ranges.isEmpty()) {
            event.styles = ranges.toArray(new StyleRange[ranges.size()]);
        }
    }

    private boolean markLine(LineStyleEvent event, String lineText, List<StyleRange> ranges, boolean handled, String searchText, RGB color1, boolean bold1, RGB color2, boolean bold2) {
        if (!handled) {

            if (lineText.startsWith(searchText)) {
                /*
                 * download itself is rendered by parsedata, here we only markup the remianing
                 * links
                 */
                addRange(ranges, event.lineOffset, searchText.length(), getColor(color1), bold1);
                addRange(ranges, event.lineOffset + searchText.length(), lineText.length(), getColor(color2), bold2);
                handled = true;
            }
        }
        return handled;
    }

    private void parse(LineStyleEvent event, StyleRange defStyle, String currentText, List<StyleRange> ranges, ParseData data) {
        if (data.isSearchingSimpleSubstring()) {
            parseByIndex(event, defStyle, currentText, ranges, data);
        } else {
            throw new UnsupportedOperationException("Unsupported/unimplemented");
        }

    }

    private void parseByIndex(LineStyleEvent event, StyleRange startStyle, String currentText, List<StyleRange> ranges, ParseData data) {
        int fromIndex = 0;
        int pos = 0;
        int length = currentText.length();
        do {
            if (fromIndex >= length) {
                break;
            }
            pos = currentText.indexOf(data.subString, fromIndex);
            fromIndex = pos + 1;

            if (pos != -1) {
                addRange(ranges, event.lineOffset + pos, data.subString.length(), getColor(data.color), data.bold);
            }
        } while (pos != -1);
    }

    private Color getColor(RGB rgb) {
        return JenkinsEditorActivator.getDefault().getColorManager().getColor(rgb);
    }

    private static class ParseData {
        public boolean bold;
        private String subString;
        private RGB color;

        private boolean isSearchingSimpleSubstring() {
            return subString != null;
        }
    }

    private void addRange(List<StyleRange> ranges, int start, int length, Color foreground, boolean bold) {
        addRange(ranges, start, length, foreground, null, bold);
    }

    private void addRange(List<StyleRange> ranges, int start, int length, Color foreground, Color background, boolean bold) {
        StyleRange range = new StyleRange(start, length, foreground, background);
        if (bold) {
            range.fontStyle = SWT.BOLD;
        }
        ranges.add(range);
        lastRangeEnd = lastRangeEnd + range.length;
    }
}