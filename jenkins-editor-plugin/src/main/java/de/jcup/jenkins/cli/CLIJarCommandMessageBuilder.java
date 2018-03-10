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