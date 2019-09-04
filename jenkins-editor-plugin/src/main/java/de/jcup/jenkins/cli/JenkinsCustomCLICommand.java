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
package de.jcup.jenkins.cli;

import java.io.IOException;
import java.util.Arrays;

import de.jcup.jenkins.util.JenkinsLogAdapter;

/**
 * Get help output
 * 
 * @author Albert Tregnaghi
 *
 */
public class JenkinsCustomCLICommand extends AbstractJenkinsCLICommand<DefaultJenkinsCLIResult, String> {

    private String[] commands;

    public JenkinsCustomCLICommand(String... commands) {
        this.commands = commands;
    }

    @Override
    public String[] getCLICommands() {
        return commands;
    }

    @Override
    protected DefaultJenkinsCLIResult handleStartedProcess(Process process, String code, CLIJarCommandMessageBuilder<String> mb) throws IOException {

        DefaultJenkinsCLIResult result = new DefaultJenkinsCLIResult();

        if (!process.isAlive()) {
            int exitValue = process.exitValue();
            result.exitCode = exitValue;
            result.cliCallFailureMessage = "Was not able to start commands:"+Arrays.asList(commands)+".\nMaybe command is invalid, server is down or CLI credentials are not set correctly.";
            return result;
        }
        int exitValue = result.exitCode;
        try {
            if (code!=null) {
                writeCode(process, code);
            }
            fetchResult(process, result);
            waitForProcessTermination(process);
            exitValue = process.exitValue();
        } catch (IOException e) {
            JenkinsLogAdapter.INSTANCE.logError("IO problems on executing jenkins custom command:"+Arrays.asList(commands), e);
        }
        handleExitCode(mb, result, exitValue);
        return result;
    }

}
