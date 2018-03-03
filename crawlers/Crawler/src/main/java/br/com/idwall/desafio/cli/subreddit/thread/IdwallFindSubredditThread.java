package br.com.idwall.desafio.cli.subreddit.thread;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import br.com.idwall.desafio.model.SubredditThread;
import br.com.idwall.desafio.utils.PropertiesUtil;

/**
 * Simple class that should provides the top threads of subreddits given, found
 * on <a href="http://www.reddit.com">Reddit's Web-site</a>
 * 
 * @author Yuri Cordeiro
 *
 */
public class IdwallFindSubredditThread extends FindSubredditThread {

	/**
	 * Split the subreddits by ';', and find itself informations for each one
	 */
	@Override
	public String getSubredditInfo(String subreddits) {

		for (String subreddit : subreddits.split(";")) {
			findWebSubredditThreadInformation(subreddit);
		}

		getDriver().quit();

		return printResults(getThreadsHash());
	}

	/**
	 * Build a StringBuilder object with all the subreddit's thread results
	 * 
	 * @param subredditThreadHash
	 *            A hashmap containing subreddits(key) and threads list(value)
	 * @return a String with all the subreddit's thread results
	 */
	private String printResults(HashMap<String, List<SubredditThread>> subredditThreadHash) {
		StringBuilder sbThreadsResults = new StringBuilder();
		if (subredditThreadHash.size() > 0) {

			// For each hasmap key (String)..
			subredditThreadHash.entrySet().stream().forEach(entry -> {
				sbThreadsResults.append("\n~~> Top threads for *" + entry.getKey().toUpperCase() + "* subreddit on "
						+ PropertiesUtil.getBundleMessage("url.base_url").replace("/r", "") + "\n|");

				if (entry.getValue().size() > 0) {
					// list all the values(List<SubredditThread>)
					entry.getValue().stream()
							.forEach(threadObject -> sbThreadsResults.append("\n*Thread Title*: "
									+ threadObject.getTitle() + "\n*Thread Upvotes*: " + threadObject.getUpVotes()
									+ "\n*Thread Link*: [" + threadObject.getThreadLink() + "]" + "\n*Comments Link*: ["
									+ threadObject.getCommentsLink() + "]\n\n"));

				} else {
					sbThreadsResults
							.append("\n=== No threads with " + PropertiesUtil.getBundleMessage("param.minimum_upvotes")
									+ "+ upvotes were found for this subreddit===\n");
				}
				sbThreadsResults
						.append("|\n|..:: End of *" + entry.getKey().toUpperCase() + "* subreddit threads\n\n\n");
			});
		}
		return sbThreadsResults.toString();
	}

	/**
	 * Searches for a subreddit top threads information
	 * 
	 * @param subreddit
	 *            <reddit it's a forum website, where> subreddits are sub-forums,
	 *            like <b>cats</b>
	 * @return a hash containing the subreddit keys, and a list of top threads
	 */
	public Map<String, List<SubredditThread>> findWebSubredditThreadInformation(String subreddit) {

		try {
			getDriver().get(PropertiesUtil.getBundleMessage("url.base_url").concat(subreddit)
					.concat(PropertiesUtil.getBundleMessage("url.top")).toString());
		} catch (Exception e) {
			System.out.println("Url not found: " + e);
		}

		List<SubredditThread> threadsList = new ArrayList<>();
		List<WebElement> webElementsThreadsList = null;

		try {
			webElementsThreadsList = getDriver().findElements(By.className("thing"));
		} catch (Exception e) {
			System.out.println(PropertiesUtil.getBundleMessage("cli_err.thing_class_not_found") + e);
		}

		webElementsThreadsList.stream().forEach(thingClassWebElement -> {
			// Verify if it's a promoted thread, 'cuz we don't want to have it
			if (!getSubredditThreadService().isPromotedThread(thingClassWebElement)) {

				SubredditThread subredditThread = getSubredditThreadService()
						.findSubredditThreadInformations(thingClassWebElement, subreddit);

				if (subredditThread.getUpVotes() >= Integer
						.valueOf(PropertiesUtil.getBundleMessage("param.minimum_upvotes"))) {
					threadsList.add(subredditThread);
				}
			}
		});

		if (threadsList.size() > 0) {
			getThreadsHash().put(subreddit, threadsList);
		} else {
			getThreadsHash().put(subreddit, new ArrayList<>());
		}

		return getThreadsHash();
	}

}
