package de.jcup.jenkinseditor.document;

import static org.junit.Assert.*;

import org.junit.Test;

public class JenkinsSpecialVariableKeyWordsTest {

    @Test
    public void inputpost_conditions_tooltip_link_is_available() {
        /* prepare */
        String expected = ExtraTooltip.INPUT.getLinkToDocumentation();

        /* execute*/
        assertEquals(expected, JenkinsSpecialVariableKeyWords.INPUT_ID.getLinkToDocumentation());
    }

}
