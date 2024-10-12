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

import static org.junit.Assert.*;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

public class VariableLineEscapeSupportTest {

    private VariableLineEscapeSupport supportToTest;
    private String expectedEscapedPrefix;
    private String expectedEscapedPostfix;

    @Before
    public void before() {

        UUID uuid = UUID.randomUUID();
        expectedEscapedPrefix = VariableLineEscapeSupport.START_VAR_ESCAPE + uuid.toString();
        expectedEscapedPostfix = VariableLineEscapeSupport.END_VAR_ESCAPE + uuid.toString();

        supportToTest = new VariableLineEscapeSupport(uuid);
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
        assertEquals("hello " + expectedEscapedPrefix + "env.var1" + expectedEscapedPostfix, supportToTest.escape("hello ${env.var1}"));
    }

    @Test
    public void unescape_hello_var1() {
        assertEquals("hello ${env.var1}", supportToTest.unescape("hello " + expectedEscapedPrefix + "env.var1" + expectedEscapedPostfix));
    }

    @Test
    public void escape_hello_var1_and_var2() {
        assertEquals("hello " + expectedEscapedPrefix + "env.var1" + expectedEscapedPostfix + " and " + expectedEscapedPrefix + "env.var2" + expectedEscapedPostfix,
                supportToTest.escape("hello ${env.var1} and ${env.var2}"));
    }

    @Test
    public void unescape_hello_var1_and_var2() {
        assertEquals("hello ${env.var1} and ${env.var2}",
                supportToTest.unescape("hello " + expectedEscapedPrefix + "env.var1" + expectedEscapedPostfix + " and " + expectedEscapedPrefix + "env.var2" + expectedEscapedPostfix));
    }

    @Test
    public void escape_var1_and_var2() {
        assertEquals(expectedEscapedPrefix + "env.var1" + expectedEscapedPostfix + " and " + expectedEscapedPrefix + "env.var2" + expectedEscapedPostfix,
                supportToTest.escape("${env.var1} and ${env.var2}"));
    }

    @Test
    public void escape_var1_var2() {
        assertEquals(expectedEscapedPrefix + "env.var1" + expectedEscapedPostfix + "" + expectedEscapedPrefix + "env.var2" + expectedEscapedPostfix, supportToTest.escape("${env.var1}${env.var2}"));
    }

    @Test
    public void escape_var1_dollar_var2() {
        assertEquals(expectedEscapedPrefix + "env.var1" + expectedEscapedPostfix + "$" + expectedEscapedPrefix + "env.var2" + expectedEscapedPostfix, supportToTest.escape("${env.var1}$${env.var2}"));
    }

    @Test
    public void unescape_var1_and_var2() {
        assertEquals("${env.var1} and ${env.var2}",
                supportToTest.unescape(expectedEscapedPrefix + "env.var1" + expectedEscapedPostfix + " and " + expectedEscapedPrefix + "env.var2" + expectedEscapedPostfix));
    }
}
