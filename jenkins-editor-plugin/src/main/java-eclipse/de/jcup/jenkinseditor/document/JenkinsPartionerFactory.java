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

import de.jcup.egradle.core.text.DocumentIdentifier;
import de.jcup.egradle.eclipse.document.GroovyDocumentIdentifiers;

public class JenkinsPartionerFactory {

	public static JenkinsPartitioner create() {
		String[] legalContentTypes = DocumentIdentifier.createStringIdBuilder()
				.addAll(GroovyDocumentIdentifiers.values()).addAll(JenkinsDocumentIdentifiers.values()).build();

		JenkinsDocumentPartitionScanner scanner = new JenkinsDocumentPartitionScanner();
		JenkinsPartitioner partitioner = new JenkinsPartitioner(scanner, legalContentTypes);

		return partitioner;
	}
}
