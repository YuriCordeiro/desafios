package br.com.idwall.desafio.enums;

/**
 * Enum should contains all the commands implemented
 * 
 * @author YuriC
 *
 */
public enum CommandEnum {

	START("/start", "" ," - Type it to see the bot introduction again;"),
	NADA_PRA_FAZER("/NadaPraFazer", " [+ Lista de subrredits]", " - Find the the top threads for the subreddits you want. Try the following one: */NadaPraFazer dogs;cats;brazi;programming*\nYou can try some popular subreddits too, like *askreddit*, *cats* and *worldnews* for example. (_IMPORTANT THING: If you give more than one subreddit, remeber to give it splitted by \";\" character, like the example given_)"),
	COMMANDS("/commands", "", " - To show you all the commnds available;"),
	HELP("/help", "", " - If you are needing help;");

	private final String command;
	private final String parameter;
	private final String description;

	CommandEnum(String command, String parameter, String description) {
		this.command = command;
		this.parameter = parameter;
		this.description = description;
	}

	public String getCommand() {
		return command;
	}

	public String getDescription() {
		return description;
	}

	public String getParameter() {
		return parameter;
	}

	public static CommandEnum getCommand(String command) {
		for (CommandEnum commandEnum : CommandEnum.values()) {
			if (commandEnum.command.equalsIgnoreCase(command)) {
				return commandEnum;
			}
		}
		return null;
	}

	public static String printFormatAllComands() {
		StringBuilder sbCommandsFormatted = new StringBuilder();
		for (CommandEnum command : CommandEnum.values()) {
			sbCommandsFormatted.append(command.getCommand() + command.getParameter() + command.getDescription() + "\n\n");
		}
		return sbCommandsFormatted.toString();
	}

}
