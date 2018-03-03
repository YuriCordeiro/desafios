package br.com.idwall.desafio.telegram.enums;

/**
 * Enum should contains all the commands implemented
 * 
 * @author YuriC
 *
 */
public enum CommandEnum {

	START("/Start", ""), HELP("/Help", ""), NADA_PRA_FAZER("/NadaPraFazer", ""), COMMANDS("/Commands", "");

	private final String command;
	private final String description;

	CommandEnum(String commandValue, String descriptionValue) {
		command = commandValue;
		description = descriptionValue;
	}

	public String getCommand() {
		return command;
	}

	public String getDescription() {
		return description;
	}

	public static CommandEnum getCommand(String command) {
		for (CommandEnum commandEnum : CommandEnum.values()) {
			if (commandEnum.command.equalsIgnoreCase(command)) {
				return commandEnum;
			}
		}
		return null;
	}

}
