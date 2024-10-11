/*
 * Copyright 2024 Albert Tregnaghi
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
 package de.jcup.jenkinseditor.test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.List;

public class TestFileReader {
    
    public static final TestFileReader DEFAULT = new TestFileReader();
    

    public String readTestFile(Path path) {
        try {
            List<String> lines = Files.readAllLines(path);
            StringBuilder sb = new  StringBuilder();
            for (Iterator<String> lineIt=lines.iterator();lineIt.hasNext();) {
                sb.append(lineIt.next());
                if (lineIt.hasNext()) {
                    sb.append("\n");
                }
            }
            return sb.toString();
        } catch (Exception e) {
            throw new IllegalStateException("Cannot read test file: "+path, e);
        }
        
    }
}
