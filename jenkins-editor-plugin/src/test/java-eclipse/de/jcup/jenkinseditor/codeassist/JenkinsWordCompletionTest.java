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
 package de.jcup.jenkinseditor.codeassist;

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import de.jcup.eclipse.commons.codeassist.ProposalProvider;
import de.jcup.eclipse.commons.source.SourceCodeBuilder;

/**
 * Test case eclipse only because dependency to egradle groovy parts which are currently
 * not accessible by gradle...
 * @author Albert Tregnaghi
 *
 */
public class JenkinsWordCompletionTest {

	private JenkinsWordCompletion completion;
	private String source1;
	private String source0;
	private int source1_pipeline_block_index;
	
	@Before
	public void before(){
		completion = new JenkinsWordCompletion();
		source0="";
		
		StringBuilder sb = new StringBuilder();
		sb.append("pipeline{\n");
		source1_pipeline_block_index=sb.length();
		sb.append("}\n");
		
		source1=sb.toString();
	}
	
	@Test
	public void an_empty_source_will_result_in_pipeline() {
		/* execute */
		Set<ProposalProvider> result = completion.calculate(source0, 0);
		
		/* test */
		assertFalse(result.isEmpty());
		assertEquals(1,result.size());
		
		ProposalProvider provider = result.iterator().next();
		assertTrue(provider.getLabel().equals("pipeline"));
		List<String> template = provider.getCodeTemplate();
		assertEquals(3, template.size());
		
		Iterator<String> it = template.iterator();
		assertEquals(it.next(), "pipeline {");
		assertEquals(it.next(), "    "+SourceCodeBuilder.CURSOR_TAG);
		assertEquals(it.next(), "}");
	
	}
	
	@Test
	public void an_pip_source_will_result_in_pipeline() {
		/* execute */
		Set<ProposalProvider> result = completion.calculate("pip", 0);
		
		/* test */
		assertFalse(result.isEmpty());
		assertEquals(1,result.size());
		
		ProposalProvider provider = result.iterator().next();
		assertTrue(provider.getLabel().equals("pipeline"));
		List<String> template = provider.getCodeTemplate();
		assertEquals(3, template.size());
		
		Iterator<String> it = template.iterator();
		assertEquals(it.next(), "pipeline {");
		assertEquals(it.next(), "    "+SourceCodeBuilder.CURSOR_TAG);
		assertEquals(it.next(), "}");
	
	}
	
	@Test
	public void after_pipeline_agent_is_suggested() {
		/* execute */
		Set<ProposalProvider> result = completion.calculate(source1, source1_pipeline_block_index);
		
		/* test */
		assertLabelFound("agent",result);
		
	}
	
	
	
	private void assertLabelFound(String string, Set<ProposalProvider> providers) {
		for (ProposalProvider provider: providers){
			if (string.equals(provider.getLabel())){
				return;
			}
		}
		fail("no label found:"+string);
		
	}
	
	private void assertNOLabelFound(String string, Set<ProposalProvider> providers) {
		for (ProposalProvider provider: providers){
			if (string.equals(provider.getLabel())){
				fail("label found:"+string);
			}
		}
		
	}

	@Test
	public void after_pipeline_stage_is_suggested() {
		/* execute */
		Set<ProposalProvider> result = completion.calculate(source1, source1_pipeline_block_index);
		
		/* test */
		assertLabelFound("stages",result);
	}
	
	@Test
	public void after_pipeline_pipeline_is_NOT_suggested() {
		/* execute */
		Set<ProposalProvider> result = completion.calculate(source1, source1_pipeline_block_index);
		
		/* test */
		assertNOLabelFound("pipeline",result);
	}

}
