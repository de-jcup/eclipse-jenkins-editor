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
 package de.jcup.jenkins.linter;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class JenkinsLinterErrorBuilderTest {

	private JenkinsLinterErrorBuilder builderToTest;

	@Before
	public void before() {
		builderToTest = new JenkinsLinterErrorBuilder();
	}

	@Test
	public void workflowscript_missing_required_againt_at_line_1_comma_column1__is_recognized_as_error() {
		/* prepare */
		String line = "WorkflowScript: 1: Missing required section \"agent\" @ line 1, column 1.";

		/* execute */
		JenkinsLinterError error = builderToTest.build(line);
		
		/* test */
		assertNotNull(error);
		assertEquals(1,error.getLine());
		assertEquals(1,error.getColumn());
		assertEquals(line,error.getMessage());

	}
	
	@Test
	public void workflowscript_no_stages_specified_at_line_3_comma_column2__is_recognized_as_error() {
		/* prepare */
		String line = "WorkflowScript: 2: No stages specified @ line 3, column 2.";

		/* execute */
		JenkinsLinterError error = builderToTest.build(line);
		
		/* test */
		assertNotNull(error);
		assertEquals(3,error.getLine());
		assertEquals(2,error.getColumn());
		assertEquals(line,error.getMessage());

	}
	
	@Test
	public void workflowscript_no_stages_specified_at_line_3000_comma_column22__is_recognized_as_error() {
		/* prepare */
		String line = "WorkflowScript: 2: No stages specified @ line 3000, column 22.";

		/* execute */
		JenkinsLinterError error = builderToTest.build(line);
		
		/* test */
		assertNotNull(error);
		assertEquals(3000,error.getLine());
		assertEquals(22,error.getColumn());
		assertEquals(line,error.getMessage());

	}

	@Test
	public void errors_encountered_validating_jenkinsfile_is_not_an_error_itself() {
		/* prepare */
		String line = "Errors encountered validating Jenkinsfile:";
		
		/* execute */
		JenkinsLinterError error = builderToTest.build(line);
		
		/* test */
		assertNull(error);
		
	}
	
	@Test
	public void infotext_about_pipeline_code_is_not_a_error() {
		/* prepare */
		String line = "   pipeline{";
		
		/* execute */
		JenkinsLinterError error = builderToTest.build(line);
		
		/* test */
		assertNull(error);
		
	}

}
