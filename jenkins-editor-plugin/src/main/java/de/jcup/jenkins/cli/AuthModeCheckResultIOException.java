package de.jcup.jenkins.cli;

import java.io.IOException;

import de.jcup.jenkins.cli.AuthModeChecker.AuthModeCheckResult;

public class AuthModeCheckResultIOException extends IOException{

    private static final long serialVersionUID = 1L;
    
    public AuthModeCheckResultIOException(AuthModeCheckResult result) {
        super("Authorization by "+result.authMode+" not possible!\n\n"+result.message +"\nPlease go to preferences and setup necessary parts!");
    }

}
