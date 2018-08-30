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
import org.eclipse.jface.text.rules.IWordDetector;

import de.jcup.eclipse.commons.keyword.DocumentKeyWord;
import de.jcup.egradle.eclipse.document.AbstractGroovyBasedDocumentPartitionScanner;
import de.jcup.egradle.eclipse.document.ExactWordPatternRule;
public class JenkinsDocumentPartitionScanner extends AbstractGroovyBasedDocumentPartitionScanner {

	@Override
	protected void addOtherRules(List<IPredicateRule> rules) {
		IToken jenkinsDefaultClosureKeywords = createToken(JENKINS_KEYWORD);
		IToken jenkinsVariables = createToken(JENKINS_VARIABLE);
		
		buildWordRulesByCommons(rules, jenkinsDefaultClosureKeywords, JenkinsDefaultClosureKeyWords.values(),onlyLettersWordDetector);
		buildWordRulesByCommons(rules, jenkinsVariables, JenkinsSpecialVariableKeyWords.values(),onlyLettersWordDetector);
	}

	protected void buildWordRulesByCommons(List<IPredicateRule> rules, IToken token, DocumentKeyWord[] values,
			IWordDetector wordDetector) {
		for (DocumentKeyWord keyWord : values) {
			rules.add(new ExactWordPatternRule(wordDetector, createWordStartByCommons(keyWord), token));
		}
	}
	private String createWordStartByCommons(DocumentKeyWord keyWord) {
		return keyWord.getText();
	}
}
