package de.jcup.jenkins.cli;

public class PipelineConfigData {
    
    public String branchName="";
    public String jobName="";
    public int buildNr=1;
    
    public String getReplayJobName() {
        if (branchName==null || branchName.isEmpty()) {
            return jobName;
        }
        return jobName+"/"+branchName;
    }
    
    public int getReplayBuildNr() {
        return buildNr;
    }

}