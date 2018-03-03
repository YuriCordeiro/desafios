package br.com.idwall.desafio.telegram.bot;

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

public class FindSubredditThreadTelegram {

	private static final String BASE_URL = "url.base_url";
	private static final String TOP_URL = "url.top";
	private SubredditThreadService subredditThreadService;
	private HtmlUnitDriver driver;
	private static Map<String, List<SubredditThread>> subredditThreadsHash;

	public FindSubredditThreadTelegram() {
		super();
		this.driver = new HtmlUnitDriver(BrowserVersion.CHROME);
		this.subredditThreadService = new SubredditThreadServiceImpl();
		System.getProperty("file.encoding", "UTF-8");
	}

	public Map<String, List<SubredditThread>> run(String[] subreddits) {
		if (subreddits != null) {
			subredditThreadsHash = new HashMap<>();
			for (String subreddit : subreddits) {
				findSubredditThreadsInformations(subreddit);
			}
			driver.quit();
		}
		return subredditThreadsHash;
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
			driver.get(PropertiesUtil.getBundleMessage(BASE_URL).concat(subreddit)
					.concat(PropertiesUtil.getBundleMessage(TOP_URL)).toString());
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

}
