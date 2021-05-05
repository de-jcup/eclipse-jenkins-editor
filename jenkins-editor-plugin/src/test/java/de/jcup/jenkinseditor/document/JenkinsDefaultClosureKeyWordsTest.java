package de.jcup.jenkinseditor.document;

import static org.junit.Assert.*;

import org.junit.Test;

public class JenkinsDefaultClosureKeyWordsTest {

    @Test
    public void post_conditions_tooltip_link_is_available() {
        /* prepare */
        String expected = ExtraTooltip.POST_CONDITIONS_TOOLTIP.getLinkToDocumentation();

        /* execute*/
        assertEquals(expected, JenkinsDefaultClosureKeyWords.SUCCESS.getLinkToDocumentation());
    }

}
