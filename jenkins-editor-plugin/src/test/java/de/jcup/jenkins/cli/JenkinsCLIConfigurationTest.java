package de.jcup.jenkins.cli;

import static org.junit.Assert.*;

import org.junit.Test;

import de.jcup.jenkins.cli.JenkinsCLIConfiguration.AuthMode;

public class JenkinsCLIConfigurationTest {

    @Test
    public void auth_mode_xx_from_string_returns_api_token() {
        assertEquals(AuthMode.API_TOKEN, AuthMode.fromString("xxx"));
    }
    
    @Test
    public void auth_mode_ssh_from_string_returns_ssh() {
        assertEquals(AuthMode.SSH, AuthMode.fromString("ssh"));
        assertEquals(AuthMode.SSH, AuthMode.fromString("SSH"));
        assertEquals(AuthMode.SSH, AuthMode.fromString("SsH"));
    }
    
    @Test
    public void auth_mode_secret_from_string_returns_password() {
        assertEquals(AuthMode.SECRET, AuthMode.fromString("secret"));
        assertEquals(AuthMode.SECRET, AuthMode.fromString("SECRET"));
    }
    
    @Test
    public void auth_mode_api_token_from_string_returns_api_token() {
        assertEquals(AuthMode.API_TOKEN, AuthMode.fromString("apitoken"));
        assertEquals(AuthMode.API_TOKEN, AuthMode.fromString("APITOKEN"));
    }


}
