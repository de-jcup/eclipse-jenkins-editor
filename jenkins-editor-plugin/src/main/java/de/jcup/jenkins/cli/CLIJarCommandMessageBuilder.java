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
class CLIJarCommandMessageBuilder<P> {
		private AbstractJenkinsCLICommand<?, P> command;

		CLIJarCommandMessageBuilder(AbstractJenkinsCLICommand<?, P> command, JenkinsCLIConfiguration configuration, P parameter) {
			this.command=command;
			this.configuration = configuration;
			this.parameter = parameter;
		}

		private JenkinsCLIConfiguration configuration;
		private P parameter;

		/**
		 * Creates string representing the call but WITHOUT passwords
		 * 
		 * @return
		 */
		String buildMessage() {
			String[] commandsForMessage = command.createCommands(configuration, parameter, true);
			StringBuilder sb = new StringBuilder();
			for (String s : commandsForMessage) {
				sb.append(s);
				sb.append(" ");
			}
			return sb.toString();
		}

	}