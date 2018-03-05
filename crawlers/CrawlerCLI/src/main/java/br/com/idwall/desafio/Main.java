package br.com.idwall.desafio;

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
		FindSubredditThread app = new IdwallFindSubredditThread();
		System.getProperty("file.encoding", "UTF-8");
		if (args != null && args.length == 1) {

			if (args[0].trim().split(";").length > 0) {
				System.out.println("Initializing program...\n\n");
				System.out.println(PropertiesUtil.getBundleMessage("cli_msg.before_process_info_message"));

				for (String subreddit : args[0].trim().split(";")) {
					System.out.println("\nFinding for " + subreddit.toUpperCase() + " top threads info");
					// Prints all the output infos
					System.out.println(app.getSubredditInfo(subreddit));
				}

			}

		} else {

			System.out.println(PropertiesUtil.getBundleMessage("cli_msg.null_parameters"));
			System.out.println("\n\nEnd of program processing.");

		}

		System.out.println(PropertiesUtil.getBundleMessage("cli_msg.after_process"));
		app.endDriverSession();

	}

}
