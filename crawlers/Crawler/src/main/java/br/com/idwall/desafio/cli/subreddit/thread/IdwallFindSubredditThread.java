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
	 * Find a single subreddit info</br>
	 * It ever appends the StringBuilder using an empty map, reducing the final
	 * StringBuilder length
	 */
	@Override
	public String getSubredditInfo(String subreddit) {

		HashMap<String, List<SubredditThread>> singleSubredditHash = new HashMap<>();

		findWebSubredditThreadInformation(subreddit, singleSubredditHash);

		return printResults(singleSubredditHash);
	}

	@Override
	public void endDriverSession() {
		getDriver().quit();
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
						+ PropertiesUtil.getBundleMessage("url.base_url").replace("/r", "").replace("www.", "")
						+ "\n|");

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
	public Map<String, List<SubredditThread>> findWebSubredditThreadInformation(String subreddit,
			HashMap<String, List<SubredditThread>> threadsHash) {

		// Will contain all threads found
		List<SubredditThread> threadsList = new ArrayList<>();
		// Will contain all the <div> elements with the class name 'thing'
		List<WebElement> webElementsThreadsList = null;

		try {
			getDriver().get(PropertiesUtil.getBundleMessage("url.base_url").concat(subreddit)
					.concat(PropertiesUtil.getBundleMessage("url.top"))
					.concat(PropertiesUtil.getBundleMessage("url.limit_param")));
		} catch (Exception e) {
			System.out.println("Url not found: " + e);
		}

		try {
			webElementsThreadsList = getDriver().findElements(By.className("thing"));
		} catch (Exception e) {
			System.out.println(PropertiesUtil.getBundleMessage("cli_err.thing_class_not_found") + e);
		}

		System.out.println("\n(" + webElementsThreadsList.size() + ") " + subreddit
				+ " threads were found. (If you want to filter more threads, go to the properties file and change the limit param of the url)");
		System.out.println("Filtering them.. It may take some seconds(considering the url 'limit' param as 5)..\n");

		webElementsThreadsList.stream().forEach(thingClassWebElement -> {
			// Verify if it's a promoted thread, 'cuz we don't want to have it
			if (!getSubredditThreadService().isPromotedThread(thingClassWebElement)) {

				SubredditThread subredditThread = getSubredditThreadService()
						.findSubredditThreadInformations(thingClassWebElement, subreddit);

				if (subredditThread.getUpVotes() != null) {
					threadsList.add(subredditThread);
					System.out.println("\nYeah! Now I got " + threadsList.size() + " top ratted thread(s).");
				} else {
					System.out.println("\nthread with no relevance..");
				}
			}
		});

		if (threadsList.size() > 0) {
			threadsHash.put(subreddit, threadsList);
		} else {
			threadsHash.put(subreddit, new ArrayList<>());
		}

		return threadsHash;
	}

}
