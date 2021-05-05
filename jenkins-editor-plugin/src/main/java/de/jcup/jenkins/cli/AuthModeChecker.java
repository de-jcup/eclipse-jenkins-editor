package de.jcup.jenkins.cli;

import de.jcup.jenkins.cli.JenkinsCLIConfiguration.AuthMode;

public class AuthModeChecker {

    
    public class CheckResult{
        String message="";
        boolean failed;
    }
    
    public CheckResult checkAuthModeDataAvailable(JenkinsCLIConfiguration config) {
        
        CheckResult result = new CheckResult();
        
        AuthMode authMode = config.getAuthMode();
        switch(authMode) {
        case ANONYMOUS:
            break; // no failures at all
        case API_TOKEN:
            userMayNotBenull(result,config);
            apiTokenMayNotBenull(result, config);
            break;
        case PASSWORD:
            userMayNotBenull(result,config);
            passwordMayNotBenull(result, config);
            break;
        case PRIVATE_KEY:
            pathToPrivateKeyMayNotBeNull(result,config);
            break;
        default:
            break;
        }
        
        return result;
    }

    private void userMayNotBenull(CheckResult result, JenkinsCLIConfiguration config) {
       if (config.getUser()==null || config.getUser().trim().isEmpty()) {
           
           result.failed=true;
           result.message+="Username not set but necessary!\n";
       }
        
    }
    
    private void apiTokenMayNotBenull(CheckResult result, JenkinsCLIConfiguration config) {
        if (config.getAPIToken()==null || config.getAPIToken().trim().isEmpty()) {
            
            result.failed=true;
            result.message+="ApiToken not set but necessary!\\n";
        }
         
     }
    
    private void passwordMayNotBenull(CheckResult result, JenkinsCLIConfiguration config) {
        if (config.getPassword()==null || config.getPassword().trim().isEmpty()) {
            
            result.failed=true;
            result.message+="Password not set but necessary!\\n";
        }
         
     }
    private void pathToPrivateKeyMayNotBeNull(CheckResult result, JenkinsCLIConfiguration config) {
        if (config.getPathToPrivateKeyFile()==null || config.getPathToPrivateKeyFile().trim().isEmpty()) {
            
            result.failed=true;
            result.message+="Path to private key not set but necessary!\\n";
        }
        
    }
    
}
