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
 package de.jcup.jenkins;

import java.io.File;
import java.io.IOException;

public class PluginResourceLoader {
	private static File iconsRootFolder = new File("./icons");
	static{
		if (!iconsRootFolder.exists()){
			// workaround for difference between eclipse test and gradle execution (being in root folder...)
			iconsRootFolder = new File("./../jenkins-editor-plugin/icons");
		}
	}
	
	
	public static File getJenkinsEditorPluginRootFolder(){
		return iconsRootFolder.getParentFile();
	}
	
	public static File assertFileInPluginExists(String relativePath) throws IOException{
		assertTestscriptFolderExists();
		
		File file = new File(getJenkinsEditorPluginRootFolder(),relativePath);
		if (!file.exists()){
			throw new IllegalArgumentException("Test case corrupt! Path not exist:"+file);
		}
		return file;
	}

	private static void assertTestscriptFolderExists() {
		if (!iconsRootFolder.exists()){
			throw new IllegalArgumentException("Test setup corrupt! Root folder of plugin not found:"+iconsRootFolder);
		}
	}
}
