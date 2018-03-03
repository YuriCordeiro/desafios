package br.com.idwall.desafio.cli.application;

import br.com.idwall.desafio.cli.CliApplication;
import br.com.idwall.desafio.utils.PropertiesUtil;

public class Main {

	public static void main(String[] args) {
		CliApplication cliApplication = new CliApplication();
		System.getProperty("file.encoding", "UTF-8");
		if (args != null && args.length == 1) {
			System.out.println("Initializing program...\n\n");
			cliApplication.processSubredditParameters(args[0].toString().split(";"));
		} else {
			System.out.println(PropertiesUtil.getBundleMessage("cli_msg.null_parameters"));
			System.out.println("\n\nEnd of program processing.");
		}
	}

}
