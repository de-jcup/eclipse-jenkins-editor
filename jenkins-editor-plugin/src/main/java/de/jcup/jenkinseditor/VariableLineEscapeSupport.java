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
package de.jcup.jenkinseditor;

import java.util.UUID;

public class VariableLineEscapeSupport {

    public static final String END_VAR_ESCAPE = "END___VAR_ESCAPE_";
    public static final String START_VAR_ESCAPE = "_START_VAR_ESCAPE_";
    
    private final String unescapedPrefix="${";
    private final String unescapedPostfix="}";

    private final String escapedPrefix;
    private final String escapedPostfix;

    VariableLineEscapeSupport(UUID uuid) {
        escapedPrefix = attach( START_VAR_ESCAPE, uuid);
        escapedPostfix = attach(END_VAR_ESCAPE, uuid);
    }

    private String attach(String string, UUID uuid) {
        String attachment = "";
        if (uuid != null) {
            attachment = uuid.toString();
        }
        return string + attachment;
    }

    public String escape(String line) {
        int prefixPos = line.indexOf(unescapedPrefix);
        if (prefixPos == -1) {
            return line;
        }
        int unescapedPrefixLength = unescapedPrefix.length();
        int unescapedPostfixLength = unescapedPostfix.length();

        StringBuilder sb = new StringBuilder(line);
        while (prefixPos != -1) {
            sb.replace(prefixPos, prefixPos + unescapedPrefixLength, escapedPrefix);

            int postFixPos = sb.indexOf(unescapedPostfix, prefixPos + unescapedPrefixLength);
            if (postFixPos == -1) {
                break;
            }
            sb.replace(postFixPos, postFixPos + unescapedPostfixLength, escapedPostfix);

            prefixPos = sb.indexOf(unescapedPrefix);
        }

        return sb.toString();
    }

    public String unescape(String line) {
        int prefixPos = line.indexOf(escapedPrefix);
        if (prefixPos == -1) {
            return line;
        }
        int escapedPrefixLength = escapedPrefix.length();

        StringBuilder sb = new StringBuilder(line);
        while (prefixPos != -1) {
            sb.replace(prefixPos, prefixPos + escapedPrefixLength, unescapedPrefix);

            prefixPos = sb.indexOf(escapedPrefix);
        }

        int escapedPostfixLength = escapedPostfix.length();
        int postFixPos = sb.indexOf(escapedPostfix);
        while (postFixPos != -1) {
            sb.replace(postFixPos, postFixPos + escapedPostfixLength, unescapedPostfix);

            postFixPos = sb.indexOf(escapedPostfix);
        }

        return sb.toString();
    }
}
