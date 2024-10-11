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
