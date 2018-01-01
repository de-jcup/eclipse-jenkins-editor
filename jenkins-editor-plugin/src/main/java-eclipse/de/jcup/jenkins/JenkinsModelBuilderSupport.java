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
package de.jcup.jenkins;

import antlr.collections.AST;
import de.jcup.egradle.core.model.Item;
import de.jcup.egradle.core.model.groovyantlr.AbstractGroovyModelBuilderSupport;

public class JenkinsModelBuilderSupport extends AbstractGroovyModelBuilderSupport {
	/*
	 * TODO ATR, 01.01.2018: those methods are not groovy but gradle specific
	 * and shold be removed from abstract part
	 */
	public Item handleDependencyAndReturnItem(AST methodCall, Item item) {
		return item;
	}

	public void handleApplyType(Item item, AST nextAST) {
		return;
	}

	public AST handleTasksWithTypeClosure(String enameString, Item item, AST nextAST) {
		AST newLastAst = nextAST;
		return newLastAst;
	}

	/**
	 * @param enameString
	 * @param item
	 * @param nextAST
	 * @return next AST to inspect for further details. If the next hierarchy
	 *         part is a closure the closure element (CLOSABLE_BLOCK=50) must be
	 *         returned!
	 */
	public AST handleTaskClosure(String enameString, Item item, AST nextAST) {
		if (nextAST == null) {
			return null;
		}
		ASTResultInfo nextASTData = handleTaskNameResolving(enameString, item, nextAST);
		if (nextASTData == null) {
			return null;
		}
		if (nextASTData.terminated) {
			return nextASTData.nextAST;
		}
		nextAST = nextASTData.nextAST;
		nextAST = handleTaskTypeResolving(item, nextAST);
		return nextAST;
	}

}
