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

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * An extreme simple code formatter for Jenkins pipeline syntax (see
 * https://www.jenkins.io/doc/book/pipeline/syntax/).
 * 
 * We do not parse the groovy model here, but just inspect text and make some
 * logic here. Simple, but should work
 * 
 * @author Albert Tregnaghi
 *
 */
public class SimpleJenkinsDeclartivePipelineSourceFormatter {

    private static final char CHAR_CLOSE_BRACKET = '}';
    private static final char CHAR_OPEN_BRACKET = '{';
    private static final String CLOSE_BRACKET = String.valueOf(CHAR_CLOSE_BRACKET);
    private static final String QUOTED_CLOSE_BRACKET = Pattern.quote(CLOSE_BRACKET);
    private static final String OPEN_BRACKET = String.valueOf(CHAR_OPEN_BRACKET);
    private static final String QUOTED_OPEN_BRACKET = Pattern.quote(OPEN_BRACKET);
    
    VariableEscapeSupport variableEscapeSupport = new VariableEscapeSupport();
 
    private int indent = 3;
    
    public void setIndent(int indent) {
        this.indent = indent;
    }

    public String format(String source) {
        if (source == null) {
            return null;
        }
        
        String[] linesArray = source.split("\n");
        maskVariables(linesArray);
        List<String> lines1 = createListWithTrimmedLinesAndClosingBracketsHandled(linesArray);
        List<String> lines2 = createListWithClosingBracketsHandled(lines1);
        handleIndention(lines2);

        return convertToString(lines2);
    }

    private void maskVariables(String[] linesArray) {
        for (int i = 0; i < linesArray.length; i++) {
            String line = linesArray[i];
            linesArray[i]=variableEscapeSupport.escape(line);
        }
    }

    private List<String> createListWithClosingBracketsHandled(List<String> lines) {
        List<String> lines1 = createListWithOpeningBracketsAllInNewLine(lines);
        List<String> lines2 = movebackOpeningBracketsToFormerLine(lines1);
        List<String> lines3 = movebackClosingBracketsToFormerElse(lines2);

        return lines3;
    }

    private List<String> movebackOpeningBracketsToFormerLine(List<String> linesWithOpenBracketsStandalone) {
        List<String> result2 = new ArrayList<String>(linesWithOpenBracketsStandalone.size());
        for (int i = 0; i < linesWithOpenBracketsStandalone.size(); i++) {
            String line = linesWithOpenBracketsStandalone.get(i);
            if (i == 0) {
                result2.add(line);
                // first one is not inspected - just added
                continue;
            }
            if (!line.contentEquals(OPEN_BRACKET)) {
                result2.add(line);
                continue;
            }
            // open bracket
            int index = result2.size() - 1;
            String old = result2.get(index); 
            if (old.trim().isEmpty()) {
                // empty line - here we do not format, wished by developer
                result2.add(line);
            }else {
                result2.remove(index);
                result2.add(old + " " + OPEN_BRACKET);
            }

        }
        return result2;
    }
    
    private List<String> movebackClosingBracketsToFormerElse(List<String> lines) {
        List<String> result2 = new ArrayList<String>(lines.size());
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i).trim();
            if (i == 0) {
                result2.add(line);
                // first one is not inspected - just added
                continue;
            }
            if (!line.toLowerCase().startsWith("else")) {
                result2.add(line);
                continue;
            }
            // open bracket
            int index = result2.size() - 1;
            String old = result2.get(index); 
            if (old.trim().startsWith(CLOSE_BRACKET)){
                result2.remove(index);
                result2.add(old+ " " + line.trim());
            }else {
                result2.add(line);
            }

        }
        return result2;
    }

    private List<String> createListWithOpeningBracketsAllInNewLine(List<String> lines) {
        List<String> result = new ArrayList<String>(lines.size());

        for (String line : lines) {
            int count = count(CHAR_OPEN_BRACKET, line);
            if (count > 0) {
                String[] openedParts = line.split(QUOTED_OPEN_BRACKET);
                if (openedParts.length > 0) {
                    int added = 0;
                    for (String open : openedParts) {
                        String trimmedPart = open.trim();
                        if (! ("".contentEquals(trimmedPart))) {
                            result.add(trimmedPart);
                        }
                        added++;
                        if (added <= count) {
                            result.add(OPEN_BRACKET);
                        }
                    }
                } else {
                    // edge case - only bracket(s)...
                    for (int b = 0; b < count; b++) {
                        result.add(OPEN_BRACKET);
                    }
                }

            } else {
                // just add as is
                result.add(line);
            }
        }
        return result;
    }

    // trim all lines + add as list + separate closing parts
    private List<String> createListWithTrimmedLinesAndClosingBracketsHandled(String[] lines) {
        List<String> result = new ArrayList<String>(lines.length);
        for (int i = 0; i < lines.length; i++) {
            String trimmed = lines[i].trim();
            
            int count = count(CHAR_CLOSE_BRACKET, trimmed);

            if (count > 0) {
                String[] closedParts = trimmed.split(QUOTED_CLOSE_BRACKET);
                if (closedParts.length > 0) {
                    int added = 0;
                    for (String closed : closedParts) {
                        String trimmedPart = closed.trim();
                        if (! ("".contentEquals(trimmedPart))) {
                            result.add(trimmedPart);
                        }
                        added++;
                        if (added <= count) {
                            result.add(CLOSE_BRACKET);
                        }
                    }
                } else {
                    // edge case - only bracket(s)...
                    for (int b = 0; b < count; b++) {
                        result.add(CLOSE_BRACKET);
                    }
                }

            } else {
                result.add(trimmed);
            }
        }
        return result;
    }

    private void handleIndention(List<String> list) {
        int level = 0;
        for (int i = 0; i < list.size(); i++) {
            String line = list.get(i);
            level = level - count('}', line);

            int currentLevel = level;

            level = level + count('{', line);

            String prefix = indent(currentLevel);
            list.remove(i);
            list.add(i, prefix + line);

        }
    }

    private String convertToString(List<String> list) {
        int max = list.size();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < max; i++) {
            String line = list.get(i);
            sb.append(variableEscapeSupport.unescape(line));
            if (i < max - 1) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    private int count(char part, String line) {
        int count = 0;
        for (char c : line.toCharArray()) {
            if (c == part) {
                count++;
            }
        }
        return count;
    }

    private String indent(int level) {
        StringBuilder sb = new StringBuilder();
        for (int l = 0; l < level; l++) {
            for (int i = 0; i < indent; i++) {
                sb.append(' ');
            }
        }
        return sb.toString();
    }

}
