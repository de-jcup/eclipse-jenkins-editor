package de.jcup.jenkinseditor.document;

import static org.junit.Assert.*;

import org.junit.Test;

public class ExtraTooltipTest {

    @Test
    public void post_conditions_tooltip_link_as_expected() {
        /* execute */
        String fetchedLink = ExtraTooltip.POST_CONDITIONS_TOOLTIP.getLinkToDocumentation();
        
        /* test */
        assertEquals("https://jenkins.io/doc/book/pipeline/syntax/#post-conditions", fetchedLink);
    }

}
