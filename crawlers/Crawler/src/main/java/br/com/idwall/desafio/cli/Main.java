package br.com.idwall.desafio.cli;

import br.com.idwall.desafio.cli.subreddit.thread.FindSubredditThread;
import br.com.idwall.desafio.cli.subreddit.thread.IdwallFindSubredditThread;
import br.com.idwall.desafio.utils.PropertiesUtil;

/**
 * Main class for {@link IdwallFindSubredditThread}<br/>
 * Should execute
 * 
 * @author YuriC
 *
 */
public class Main {

	public static void main(String[] args) {
		FindSubredditThread cliApp = new IdwallFindSubredditThread();
		System.getProperty("file.encoding", "UTF-8");
		if (args != null && args.length == 1) {

			System.out.println("Initializing program...\n\n");
			System.out.println(PropertiesUtil.getBundleMessage("cli_msg.before_process_info_message"));
			// Give the results output
			System.out.println(cliApp.getSubredditInfo(args[0].toString()));

		} else {

			System.out.println(PropertiesUtil.getBundleMessage("cli_msg.null_parameters"));
			System.out.println("\n\nEnd of program processing.");

		}

		System.out.println(PropertiesUtil.getBundleMessage("cli_msg.after_process"));

	}

}
