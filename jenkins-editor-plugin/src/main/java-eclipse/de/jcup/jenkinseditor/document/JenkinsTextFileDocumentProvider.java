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

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentPartitioner;
import org.eclipse.ui.editors.text.TextFileDocumentProvider;

/**
 * Document provider for files outside of workspace
 * 
 * @author albert
 *
 */
public class JenkinsTextFileDocumentProvider extends TextFileDocumentProvider {

	@Override
	public IDocument getDocument(Object element) {
		IDocument document = super.getDocument(element);
		if (document == null) {
			return null;
		}
		IDocumentPartitioner formerPartitioner = document.getDocumentPartitioner();
		if (formerPartitioner instanceof JenkinsPartitioner) {
			return document;
		}
		/* installation necessary */
		JenkinsPartitioner partitioner = JenkinsPartionerFactory.create();
		partitioner.connect(document,true);
		document.setDocumentPartitioner(partitioner);
		
		return document;
	}

}