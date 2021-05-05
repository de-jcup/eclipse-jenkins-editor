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
