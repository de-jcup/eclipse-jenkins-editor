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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VariableEscapeSupport {
    
    private static final String PREFIX_1 =  "${";
    private static final String POSTFIX_1 = "}";
    private static final Pattern MASK_VARS = Pattern.compile("(\\$\\{)(.*)(\\})");
    private static final String QUOTE_REPLACEMENT1 = Matcher.quoteReplacement(PREFIX_1)+"$2"+Matcher.quoteReplacement(POSTFIX_1);
    
    private static final String PREFIX_2 =  "________START_VARESCAPE____";
    private static final String POSTFIX_2 = "________END___VARESCAPE___";
    private static final String QUOTE_REPLACEMENT2 = Matcher.quoteReplacement(PREFIX_2)+"$2"+Matcher.quoteReplacement(POSTFIX_2);

    private static final Pattern UNMASK_VARS = Pattern.compile("("+PREFIX_2+")(.*)("+POSTFIX_2+")");
    
    public String escape(String text) {
        Matcher matcher = MASK_VARS.matcher(text);
        return matcher.replaceAll(QUOTE_REPLACEMENT2);
    }
    
    public String unescape(String text) {
        Matcher matcher = UNMASK_VARS.matcher(text);
        return  matcher.replaceAll(QUOTE_REPLACEMENT1);
    }
}
