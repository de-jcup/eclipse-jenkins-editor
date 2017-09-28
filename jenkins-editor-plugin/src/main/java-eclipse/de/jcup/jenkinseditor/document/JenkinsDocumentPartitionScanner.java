/*
 * Copyright 2016 Albert Tregnaghi
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
package de.jcup.jenkinseditor.document;

import static de.jcup.jenkinseditor.document.JenkinsDocumentIdentifiers.*;

import java.util.List;

import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.IToken;

import de.jcup.egradle.eclipse.document.AbstractGroovyBasedDocumentPartitionScanner;
public class JenkinsDocumentPartitionScanner extends AbstractGroovyBasedDocumentPartitionScanner {


	@Override
	protected void addOtherRules(List<IPredicateRule> rules) {
		IToken gradleClosureKeywords = createToken(JENKINS_KEYWORD);
		IToken gradleVariable = createToken(JENKINS_VARIABLE);
		
		buildWordRules(rules, gradleClosureKeywords, JenkinsDefaultClosureKeyWords.values(),onlyLettersWordDetector);
		buildWordRules(rules, gradleVariable, JenkinsSpecialVariableKeyWords.values(),onlyLettersWordDetector);
	}

}
