package de.jcup.jenkins.cli;

public class DefaultJenkinsCLIResult extends AbstractJenkinsCLIResult{

    public DefaultJenkinsCLIResult() {
        
    }

    @Override
    public boolean wasCLICallSuccessFul() {
        return exitCode==0;
    }

}
