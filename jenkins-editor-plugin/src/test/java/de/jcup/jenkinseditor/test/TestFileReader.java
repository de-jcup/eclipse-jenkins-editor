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
