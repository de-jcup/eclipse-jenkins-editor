/*
 * Copyright 2021 Albert Tregnaghi
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
 package de.jcup.jenkins.cli;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import de.jcup.jenkins.cli.AuthModeChecker.AuthModeCheckResult;
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
    public void user_set_ssh_does_NOT_fail() {
        /* prepare */
        config.setAuthMode(AuthMode.SSH);
        config.setUser("myuser");
        
        /* execute */
        AuthModeCheckResult result = checkerToTest.checkAuthModeDataAvailable(config);
        
        /* test*/
        assertFalse(result.failed);
        assertEquals("",result.message);
    }
    
    @Test
    public void user_not_set_ssh_does_fail() {
        /* prepare */
        config.setAuthMode(AuthMode.SSH);

        /* execute */
        AuthModeCheckResult result = checkerToTest.checkAuthModeDataAvailable(config);
        
        /* test*/
        assertTrue(result.failed);
        assertTrue(result.message.contains("Username"));
    }

    @Test
    public void user_and_token_set_api_token_does_NOT_fail() {
        /* prepare */
        config.setAuthMode(AuthMode.API_TOKEN);
        config.setUser("myuser");
        config.setAPIToken("apitoken1234");
        
        /* execute */
        AuthModeCheckResult result = checkerToTest.checkAuthModeDataAvailable(config);
        
        /* test*/
        assertFalse(result.failed);
        assertEquals("",result.message);
    }
    
    @Test
    public void user_and_token_missing_api_token_fails() {
        /* prepare */
        config.setAuthMode(AuthMode.API_TOKEN);

        /* execute */
        AuthModeCheckResult result = checkerToTest.checkAuthModeDataAvailable(config);
        
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
        AuthModeCheckResult result = checkerToTest.checkAuthModeDataAvailable(config);
        
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
        AuthModeCheckResult result = checkerToTest.checkAuthModeDataAvailable(config);
        
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
        AuthModeCheckResult result = checkerToTest.checkAuthModeDataAvailable(config);
        
        /* test*/
        assertTrue(result.failed);
        assertTrue(result.message.contains("Username"));
        assertTrue(result.message.contains("ApiToken"));
    }

}
