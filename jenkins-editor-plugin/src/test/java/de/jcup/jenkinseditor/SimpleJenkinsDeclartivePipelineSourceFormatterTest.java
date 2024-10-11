package de.jcup.jenkinseditor;

import static org.junit.Assert.*;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;

import de.jcup.jenkinseditor.test.TestFileReader;

public class SimpleJenkinsDeclartivePipelineSourceFormatterTest {

    private SimpleJenkinsDeclartivePipelineSourceFormatter toTest;
    private static final Path FORMATTER_PARENT_FOLDER = Paths.get("./src/test/resources/formatter");

    @Before
    public void before() {
        toTest = new SimpleJenkinsDeclartivePipelineSourceFormatter();
    }

    private void assertFormattedAsExpected(String filePartName) {
        /* prepare */
        Path originPath = FORMATTER_PARENT_FOLDER.resolve(filePartName + ".jenkinsfile");
        Path expectedPath = FORMATTER_PARENT_FOLDER.resolve(filePartName + "-expected.jenkinsfile");

        String origin = TestFileReader.DEFAULT.readTestFile(originPath);
        String expected = TestFileReader.DEFAULT.readTestFile(expectedPath);

        /* execute */
        String formatted = toTest.format(origin);

        /* test */
        assertEquals(expected, formatted);
    }

    @Test
    public void format1_file_formatted_as_expected() throws Exception {
        assertFormattedAsExpected("format1");
    }

    @Test
    public void format2_file_formatted_as_expected() throws Exception {
        assertFormattedAsExpected("format2");
    }

    @Test
    public void format3_file_formatted_as_expected() throws Exception {
        assertFormattedAsExpected("format3");
    }

    @Test
    public void format4_file_formatted_as_expected() throws Exception {
        assertFormattedAsExpected("format4");
    }

    @Test
    public void format5_file_formatted_as_expected() throws Exception {
        assertFormattedAsExpected("format5");
    }

    @Test
    public void format6_file_formatted_as_expected() throws Exception {
        assertFormattedAsExpected("format6");
    }
    @Test
    public void format7_indention_5_file_formatted_as_expected() throws Exception {
        toTest.setIndent(5);
        assertFormattedAsExpected("format7");
    }

    @Test
    public void format10_file_formatted_as_expected() throws Exception {
        assertFormattedAsExpected("format10");
    }
    
    @Test
    public void format11_file_formatted_as_expected() throws Exception {
        assertFormattedAsExpected("format11");
    }

    @Test
    public void null_works_as_well() {
        assertEquals(null, toTest.format(null));
    }

    @Test
    public void empty_string() {
        assertEquals("", toTest.format(""));
    }

    @Test
    public void blank_string() {
        assertEquals("", toTest.format("     "));
    }

    @Test
    public void pipeline_empty_one_liner() {
        assertEquals("pipeline {\n}", toTest.format("pipeline {}"));
    }

}
