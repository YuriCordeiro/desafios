package br.com.idwall.desafio.telegram.bot;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.gargoylesoftware.htmlunit.BrowserVersion;

import br.com.idwall.desafio.model.SubredditThread;
import br.com.idwall.desafio.service.SubredditThreadService;
import br.com.idwall.desafio.service.impl.SubredditThreadServiceImpl;

public class FindSubredditThreadsTelegram {

	private static final String BASE_URL = "http://www.reddit.com/r/";
	private static final String TOP_BASE_URL = "/top";
	private static SubredditThreadService subredditThreadService;
	private static HtmlUnitDriver driver;
	private static Map<String, List<SubredditThread>> subredditThreadsHash;

	public Map<String, List<SubredditThread>> run(String[] subreddits) {

		if (subreddits != null) {
			init(); // Initialize headless HtmlUnit driver

			subredditThreadsHash = new HashMap<>();

			for (String subreddit : subreddits) {
				findSubredditThreadsInformations(subreddit);
			}

			driver.quit();
		}

		return subredditThreadsHash;
	}

	public Map<String, List<SubredditThread>> findSubredditThreadsInformations(String subreddit) {

		try {
			driver.get(BASE_URL.concat(subreddit).concat(TOP_BASE_URL).toString());
		} catch (Exception e) {
			System.out.println("Url not found: " + e);
		}

		List<SubredditThread> threadsList = null;
		List<WebElement> webElementsThreadsList = null;
		try {
			webElementsThreadsList = driver.findElements(By.className("thing"));
		} catch (Exception e) {
			System.out.println(
					"Something goes wrong when trying to find the 'thing' class on the page. Maybe the HTML has been changed.");
		}

		if (webElementsThreadsList != null) {
			threadsList = new ArrayList<SubredditThread>();
			for (WebElement thingClassWebElement : webElementsThreadsList) {
				if (!subredditThreadService.isPromotedThread(thingClassWebElement)) { // We don't wanna promoted threads

					SubredditThread subredditThread = subredditThreadService
							.findSubredditThreadInformations(thingClassWebElement, subreddit);

					try {
						if (subredditThread.getUpVotes() >= Integer
								.valueOf(getProp().get("MINIMUN_UPVOTE").toString())) {
							threadsList.add(subredditThread);
						} else {
							break;
						}
					} catch (NumberFormatException e) {
						System.out.println("Error while trying to format the number of Upvotes to String. \n" + e);
					} catch (IOException ex) {
						System.out.println("Properties file not found. \n" + ex);
					}

				}
			}
		}

		if (threadsList.size() > 0) {
			subredditThreadsHash.put(subreddit, threadsList);
		} else {
			subredditThreadsHash.put(subreddit, new ArrayList<>());
		}
		return subredditThreadsHash;
	}

	/**
	 * Init the headless unit driver
	 */
	public void init() {
		driver = new HtmlUnitDriver(BrowserVersion.CHROME);
		subredditThreadService = new SubredditThreadServiceImpl();
		System.getProperty("file.encoding", "UTF-8");
	}

	public Properties getProp() throws IOException {
		Properties props = new Properties();
		FileInputStream file = new FileInputStream("./src/main/resources/config.properties");
		props.load(file);
		return props;

	}

}
