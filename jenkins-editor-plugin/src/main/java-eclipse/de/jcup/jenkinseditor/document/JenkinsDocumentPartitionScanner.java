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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.IWordDetector;
import org.eclipse.jface.text.rules.MultiLineRule;
import org.eclipse.jface.text.rules.RuleBasedPartitionScanner;
import org.eclipse.jface.text.rules.SingleLineRule;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.rules.WordPatternRule;

import de.jcup.egradle.eclipse.document.AnnotationWordDetector;
import de.jcup.egradle.eclipse.document.DocumentKeyWord;
import de.jcup.egradle.eclipse.document.ExactWordPatternRule;
import de.jcup.egradle.eclipse.document.GroovyKeyWords;
import de.jcup.egradle.eclipse.document.JavaKeyWords;
import de.jcup.egradle.eclipse.document.JavaLiteralKeyWords;
import de.jcup.egradle.eclipse.document.JavaWordDetector;
import de.jcup.egradle.eclipse.document.OnlyLettersKeyWordDetector;
public class JenkinsDocumentPartitionScanner extends RuleBasedPartitionScanner {

	private OnlyLettersKeyWordDetector onlyLettersWordDetector = new OnlyLettersKeyWordDetector();
	private AnnotationWordDetector onlyAnnotationWordDetector = new AnnotationWordDetector();
	private JavaWordDetector javaWordDetector = new JavaWordDetector();
	
	public JenkinsDocumentPartitionScanner() {
		
		IToken groovyAnnotation = createToken(ANNOTATION);
		IToken javaDocComment = createToken(GROOVY_DOC);
		IToken groovyComment = createToken(COMMENT);
		IToken groovySimpleString = createToken(STRING);
		IToken groovyGString = createToken(GSTRING);
		IToken groovyKeyWord = createToken(GROOVY_KEYWORD);
		IToken javaKeyWord = createToken(JAVA_KEYWORD);
		IToken javaLiteral = createToken(JAVA_LITERAL);

		IToken gradleClosureKeywords = createToken(JENKINS_KEYWORD);
		IToken gradleVariable = createToken(JENKINS_VARIABLE);

		List<IPredicateRule> rules = new ArrayList<>();
		rules.add(new MultiLineRule("/**", "*/", javaDocComment));
		rules.add(new MultiLineRule("/*", "*/", groovyComment));
		rules.add(new SingleLineRule("//", "", groovyComment));
		
		rules.add(new MultiLineRule("\"", "\"", groovyGString,'\\'));
		rules.add(new MultiLineRule("\'", "\'", groovySimpleString,'\\'));
		
		buildWordRules(rules, gradleClosureKeywords, JenkinsDefaultClosureKeyWords.values(),onlyLettersWordDetector);
		buildWordRules(rules, groovyKeyWord, GroovyKeyWords.values(),javaWordDetector);
		buildWordRules(rules, javaKeyWord, JavaKeyWords.values(),javaWordDetector);
		buildWordRules(rules, javaLiteral, JavaLiteralKeyWords.values(),javaWordDetector);
		buildWordRules(rules, gradleVariable, JenkinsSpecialVariableKeyWords.values(),onlyLettersWordDetector);

		buildAnnotationRules(rules, groovyAnnotation, onlyAnnotationWordDetector);
		
		setPredicateRules(rules.toArray(new IPredicateRule[rules.size()]));
	}

	private void buildAnnotationRules(List<IPredicateRule> rules, IToken token,
			IWordDetector wordDetector) {
		rules.add(new WordPatternRule(wordDetector,"@","", token));
		
	}
	private void buildWordRules(List<IPredicateRule> rules, IToken token,
			DocumentKeyWord[] values, IWordDetector wordDetector) {
		for (DocumentKeyWord keyWord: values){
			rules.add(new ExactWordPatternRule(wordDetector, createWordStart(keyWord),token));
		}
	}
	
	private String createWordStart(DocumentKeyWord keyWord) {
		return keyWord.getText();
	}

	private IToken createToken(JenkinsDocumentIdentifier identifier) {
		return new Token(identifier.getId());
	}
}
