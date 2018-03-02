package br.com.idwall.desafio.telegram.enums;

/**
 * Enum should contains all the commands implemented
 * 
 * @author YuriC
 *
 */
public enum CommandEnum {

	START("/Start"), HELP("/Help"), NADA_PRA_FAZER("/NadaPraFazer");

	private final String command;

	CommandEnum(String commandValue) {
		command = commandValue;
	}

	public String getCommand() {
		return command;
	}

}
