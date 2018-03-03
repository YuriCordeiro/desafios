package br.com.idwall.desafio.cli;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.gargoylesoftware.htmlunit.BrowserVersion;

import br.com.idwall.desafio.model.SubredditThread;
import br.com.idwall.desafio.service.SubredditThreadService;
import br.com.idwall.desafio.service.impl.SubredditThreadServiceImpl;
import br.com.idwall.desafio.utils.PropertiesUtil;

/**
 * Simple CLI class that should provides the top threads of subreddits on
 * <a href="http://www.reddit.com">Reddit's Web-site</a>
 * 
 * @author Yuri Cordeiro
 *
 */
public class FindSubredditThreadCli {
	private SubredditThreadService subredditThreadService;
	private HtmlUnitDriver driver;
	private HashMap<String, List<SubredditThread>> subredditThreadsHash;

	/**
	 * Default constructor should instantiate all the attributes only one time per
	 * instance
	 */
	public FindSubredditThreadCli() {
		super();
		this.subredditThreadService = new SubredditThreadServiceImpl();
		this.driver = new HtmlUnitDriver(BrowserVersion.CHROME);
		this.subredditThreadsHash = new HashMap<>();
	}

	/**
	 * Where the information takes the right way
	 */
	public void processSubredditParameters(String[] subredditParameters) {
		subredditThreadsHash = new HashMap<String, List<SubredditThread>>();
		StringBuilder sbPrintThreadsResults = new StringBuilder();

		System.out.println(PropertiesUtil.getBundleMessage("cli_msg.before_process_info_message"));

		for (String subreddit : subredditParameters) {
			findSubredditThreadsInformations(subreddit);
		}

		if (subredditThreadsHash.size() > 0) {
			subredditThreadsHash.entrySet().stream().forEach(entry -> {
				sbPrintThreadsResults
						.append("\n~~> Top threads for *" + entry.getKey().toUpperCase() + "* subreddit on "
								+ PropertiesUtil.getBundleMessage("url.base_url").replace("/r", "") + "\n|");
				if (entry.getValue().size() > 0) {

					entry.getValue().stream()
							.forEach(threadObject -> sbPrintThreadsResults.append("\n*Thread Title*: "
									+ threadObject.getTitle() + "\n*Thread Upvotes*: " + threadObject.getUpVotes()
									+ "\n*Thread Link*: [" + threadObject.getThreadLink() + "]" + "\n*Comments Link*: ["
									+ threadObject.getCommentsLink() + "]\n\n"));
				} else {
					sbPrintThreadsResults
							.append("\n=== No threads with " + PropertiesUtil.getBundleMessage("param.minimum_upvotes")
									+ "+ upvotes were found for this subreddit===\n");
				}
				sbPrintThreadsResults
						.append("|\n|..:: End of *" + entry.getKey().toUpperCase() + "* subreddit threads\n\n\n");
			});
		}

		driver.quit();
		System.out.println(sbPrintThreadsResults);
		System.out.println(PropertiesUtil.getBundleMessage("cli_msg.after_process"));
	}

	/**
	 * Searches for a subreddit top threads information
	 * 
	 * @param subreddit
	 *            <reddit it's a forum website, where> subreddits are sub-forums,
	 *            like <b>cats</b>
	 * @return a hash containing the subreddit keys, and a list of top threads
	 */
	public Map<String, List<SubredditThread>> findSubredditThreadsInformations(String subreddit) {

		try {
			driver.get(PropertiesUtil.getBundleMessage("url.base_url").concat(subreddit)
					.concat(PropertiesUtil.getBundleMessage("url.top")).toString());
		} catch (Exception e) {
			System.out.println("Url not found: " + e);
		}

		List<SubredditThread> threadsList = new ArrayList<SubredditThread>();
		List<WebElement> webElementsThreadsList = null;

		try {
			webElementsThreadsList = driver.findElements(By.className("thing"));
		} catch (Exception e) {
			System.out.println(PropertiesUtil.getBundleMessage("cli_err.thing_class_not_found") + e);
		}

		webElementsThreadsList.stream().forEach(thingClassWebElement -> {
			if (!subredditThreadService.isPromotedThread(thingClassWebElement)) { // We don't wanna promoted threads

				SubredditThread subredditThread = subredditThreadService
						.findSubredditThreadInformations(thingClassWebElement, subreddit);

				if (subredditThread.getUpVotes() >= Integer
						.valueOf(PropertiesUtil.getBundleMessage("param.minimum_upvotes"))) {
					threadsList.add(subredditThread);
				}
			}
		});

		if (threadsList.size() > 0) {
			subredditThreadsHash.put(subreddit, threadsList);
		} else {
			subredditThreadsHash.put(subreddit, new ArrayList<>());
		}

		return subredditThreadsHash;
	}

	public SubredditThreadService getSubredditThreadService() {
		return subredditThreadService;
	}

	public HtmlUnitDriver getDriver() {
		return driver;
	}

	public HashMap<String, List<SubredditThread>> getSubredditThreadsHash() {
		return subredditThreadsHash;
	}

}
