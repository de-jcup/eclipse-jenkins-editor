package de.jcup.jenkinseditor;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class VariableEscapeSupportTest {
    
    private VariableEscapeSupport supportToTest;
    private static final String PREFIX =  "________START_VARESCAPE____";
    private static final String POSTFIX = "________END___VARESCAPE___";

    @Before
    public void before() {
        supportToTest = new VariableEscapeSupport();
    }

    @Test
    public void escape_hello_world() {
        assertEquals("hello world", supportToTest.escape("hello world"));
    }
    
    @Test
    public void unescape_hello_world() {
        assertEquals("hello world", supportToTest.unescape("hello world"));
    }
    
    @Test
    public void escape_hello_var1() {
        assertEquals("hello "+PREFIX+"env.var1"+POSTFIX, supportToTest.escape("hello ${env.var1}"));    }
    
    @Test
    public void unescape_hello_var1() {
        assertEquals("hello ${env.var1}", supportToTest.unescape("hello "+PREFIX+"env.var1"+POSTFIX));
    }

}
