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

import java.util.Collections;
import java.util.List;

import de.jcup.eclipse.commons.WordListBuilder;
import de.jcup.eclipse.commons.codeassist.SimpleWordCodeCompletion;
import de.jcup.jenkinseditor.document.JenkinsDefaultClosureKeyWords;
import de.jcup.jenkinseditor.document.JenkinsSpecialVariableKeyWords;
import de.jcup.jenkinseditor.document.JenkinsfileKeyword;

public class JenkinsEveryDSLPartWordCompletion extends SimpleWordCodeCompletion{

	public JenkinsEveryDSLPartWordCompletion(){
	    /* add word list builder which is not adding source content parts - we want only DSL here */
	    setWordListBuilder(new WordListBuilder() {
            
            @Override
            public List<String> build(String arg0) {
                return Collections.emptyList();
            }
        });
	    add(JenkinsDefaultClosureKeyWords.values());
        add(JenkinsSpecialVariableKeyWords.values());
	}
	
	private void add (JenkinsfileKeyword[] keywords) {
        for (JenkinsfileKeyword keyword: keywords) {
            add(keyword.getText());
        }
    }
	
	@Override
	public void reset() {
	    
	}


}
