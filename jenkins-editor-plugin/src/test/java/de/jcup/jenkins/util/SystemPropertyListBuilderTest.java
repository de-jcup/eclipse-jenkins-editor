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
