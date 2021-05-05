package de.jcup.jenkins.cli;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import de.jcup.jenkins.cli.AuthModeChecker.CheckResult;
import de.jcup.jenkins.cli.JenkinsCLIConfiguration.AuthMode;

public class AuthModeCheckerTest {

    private AuthModeChecker checkerToTest;
    private JenkinsCLIConfiguration config;

    @Before
    public void before() {
        checkerToTest = new AuthModeChecker();
        config = new JenkinsCLIConfiguration();
    }

    @Test
    public void user_and_token_set_api_token_does_NOT_fail() {
        /* prepare */
        config.setAuthMode(AuthMode.API_TOKEN);
        config.setUser("myuser");
        config.setAPIToken("apitoken1234");
        
        /* execute */
        CheckResult result = checkerToTest.checkAuthModeDataAvailable(config);
        
        /* test*/
        assertFalse(result.failed);
        assertEquals("",result.message);
    }
    
    @Test
    public void user_and_token_missing_api_token_fails() {
        /* prepare */
        config.setAuthMode(AuthMode.API_TOKEN);

        /* execute */
        CheckResult result = checkerToTest.checkAuthModeDataAvailable(config);
        
        /* test*/
        assertTrue(result.failed);
        assertTrue(result.message.contains("Username"));
        assertTrue(result.message.contains("ApiToken"));
    }
    
    @Test
    public void user_missing_api_token_fails() {
        /* prepare */
        config.setAuthMode(AuthMode.API_TOKEN);
        config.setAPIToken("apitoken1234");
        
        /* execute */
        CheckResult result = checkerToTest.checkAuthModeDataAvailable(config);
        
        /* test*/
        assertTrue(result.failed);
        assertTrue(result.message.contains("Username"));
        assertFalse(result.message.contains("ApiToken"));
        
    }
    
    @Test
    public void token_missing_api_token_fails() {
        /* prepare */
        config.setAuthMode(AuthMode.API_TOKEN);
        config.setUser("myuser");
        
        /* execute */
        CheckResult result = checkerToTest.checkAuthModeDataAvailable(config);
        
        /* test*/
        assertTrue(result.failed);
        assertTrue(result.message.contains("ApiToken"));
    }
    
    @Test
    public void token_missing_but_empty_strings_api_token_fails() {
        /* prepare */
        config.setAuthMode(AuthMode.API_TOKEN);
        config.setUser("");
        config.setAPIToken("");
        
        /* execute */
        CheckResult result = checkerToTest.checkAuthModeDataAvailable(config);
        
        /* test*/
        assertTrue(result.failed);
        assertTrue(result.message.contains("Username"));
        assertTrue(result.message.contains("ApiToken"));
    }

}
