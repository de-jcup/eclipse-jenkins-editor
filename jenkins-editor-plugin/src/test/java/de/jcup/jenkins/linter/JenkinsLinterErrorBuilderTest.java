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
		JenkinsLinterError error = builderToTest.detectErrors(line);
		
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
		JenkinsLinterError error = builderToTest.detectErrors(line);
		
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
		JenkinsLinterError error = builderToTest.detectErrors(line);
		
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
		JenkinsLinterError error = builderToTest.detectErrors(line);
		
		/* test */
		assertNull(error);
		
	}
	
	@Test
	public void infotext_about_pipeline_code_is_not_a_error() {
		/* prepare */
		String line = "   pipeline{";
		
		/* execute */
		JenkinsLinterError error = builderToTest.detectErrors(line);
		
		/* test */
		assertNull(error);
		
	}

}
