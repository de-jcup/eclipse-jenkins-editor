/*
 * Copyright 2017 Albert Tregnaghi
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
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

import de.jcup.jenkins.util.SystemAdapter;

public class JenkinsDefaultURLProviderTest {
	private JenkinsDefaultURLProvider providerToTest;
	private SystemAdapter mockedSystemAdapter;

	@Before
	public void before() {
		providerToTest = new JenkinsDefaultURLProvider();
		mockedSystemAdapter = mock(SystemAdapter.class);
		providerToTest.systemAdapter=mockedSystemAdapter;
	}

	@Test
	public void system_provider_returns_null_for_JENKINS_URL___then_fallback_url_with_localhost_is_returned() {
		/* prepare */
		when(mockedSystemAdapter.getEnv("JENKINS_URL")).thenReturn(null);
		
		/* execute + test */
		assertEquals("http://localhost:8080", providerToTest.getDefaultJenkinsURL());
	}
	
	@Test
	public void system_provider_returns_empty_string_for_JENKINS_URL___then_fallback_url_with_localhost_is_returned() {
		/* prepare */
		when(mockedSystemAdapter.getEnv("JENKINS_URL")).thenReturn("");
		
		/* execute + test */
		assertEquals("http://localhost:8080", providerToTest.getDefaultJenkinsURL());
	}

	@Test
	public void system_provider_returns_blubber_for_JENKINS_URL___then_blubber_is_returned() {
		/* prepare */
		when(mockedSystemAdapter.getEnv("JENKINS_URL")).thenReturn("blubber");
		
		/* execute + test */
		assertEquals("blubber", providerToTest.getDefaultJenkinsURL());
	}
	
	
	@Test
	public void system_provider_returns_null_for_JENKINS_URL___then_description_about_missing_JENKINS_URL_and_usage_of_localhost_is_returned() {
		/* prepare */
		when(mockedSystemAdapter.getEnv("JENKINS_URL")).thenReturn(null);
		
		/* execute + test */
		assertEquals("No JENKINS_URL env defined, so using http://localhost:8080 as default value", providerToTest.createDefaultURLDescription());
	}

	@Test
	public void system_provider_returns_blubber_for_JENKINS_URL___then_description_JENKINS_URLthen_blubber_is_returned() {
		/* prepare */
		when(mockedSystemAdapter.getEnv("JENKINS_URL")).thenReturn("blubber");
		
		/* execute + test */
		assertEquals("JENKINS_URL defined as blubber. Using this as default value", providerToTest.createDefaultURLDescription());
	}
}
