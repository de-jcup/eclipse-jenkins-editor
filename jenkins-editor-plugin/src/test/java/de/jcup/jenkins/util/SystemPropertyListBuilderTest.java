package de.jcup.jenkins.util;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class SystemPropertyListBuilderTest {
	private SystemPropertyListBuilder builderToTest;
	
	@Before
	public void before(){
		builderToTest = new SystemPropertyListBuilder();
	}
	@Test
	public void prefix_key_value_returned_as_system_property_entry() {
		assertEquals("-Dprefix.key=value",builderToTest.build("prefix", "key", "value"));
	}
	
	@Test
	public void null_key_value_returned_as_system_property_entry() {
		assertEquals("-Dnull.key=value",builderToTest.build(null, "key", "value"));
	}
	@Test
	public void prefix_null_value_returned_as_system_property_entry() {
		assertEquals("-Dprefix.null=value",builderToTest.build("prefix", null, "value"));
	}

	@Test
	public void prefix_key_null_returned_as_system_property_entry() {
		assertEquals("-Dprefix.key=null",builderToTest.build("prefix", "key",null));
	}
}
