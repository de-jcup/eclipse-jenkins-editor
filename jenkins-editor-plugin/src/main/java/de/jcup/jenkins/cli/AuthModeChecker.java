package de.jcup.jenkins.cli;

import de.jcup.jenkins.cli.JenkinsCLIConfiguration.AuthMode;

public class AuthModeChecker {
    
    public class AuthModeCheckResult{
        String message="";
        boolean failed;
        AuthMode authMode;
    }
    
    public AuthModeCheckResult checkAuthModeDataAvailable(JenkinsCLIConfiguration config) {
        
        AuthModeCheckResult result = new AuthModeCheckResult();
        
        AuthMode authMode = config.getAuthMode();
        result.authMode=authMode;
        
        switch(authMode) {
        case ANONYMOUS:
            break; // no failures at all
        case SSH:
            userMayNotBenull(result, config);
            break;
        case API_TOKEN:
            userMayNotBenull(result,config);
            apiTokenMayNotBenull(result, config);
            break;
        case SECRET:
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

    private void userMayNotBenull(AuthModeCheckResult result, JenkinsCLIConfiguration config) {
       if (config.getUser()==null || config.getUser().trim().isEmpty()) {
           
           result.failed=true;
           result.message+="- Username not set but necessary!\n";
       }
        
    }
    
    private void apiTokenMayNotBenull(AuthModeCheckResult result, JenkinsCLIConfiguration config) {
        if (config.getAPIToken()==null || config.getAPIToken().trim().isEmpty()) {
            
            result.failed=true;
            result.message+="- ApiToken not set but necessary!\n";
        }
         
     }
    
    private void passwordMayNotBenull(AuthModeCheckResult result, JenkinsCLIConfiguration config) {
        if (config.getPassword()==null || config.getPassword().trim().isEmpty()) {
            
            result.failed=true;
            result.message+="- Password not set but necessary!\n";
        }
         
     }
    private void pathToPrivateKeyMayNotBeNull(AuthModeCheckResult result, JenkinsCLIConfiguration config) {
        if (config.getPathToPrivateKeyFile()==null || config.getPathToPrivateKeyFile().trim().isEmpty()) {
            
            result.failed=true;
            result.message+="- Path to private key not set but necessary!\n";
        }
        
    }
    
}
