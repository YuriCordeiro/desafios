package br.com.idwall.desafio;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.gargoylesoftware.htmlunit.BrowserVersion;

import br.com.idwall.desafio.model.SubredditThread;
import br.com.idwall.desafio.service.SubredditThreadService;
import br.com.idwall.desafio.service.impl.SubredditThreadServiceImpl;

public class FindSubredditThreads {
	private static final String BASE_URL = "http://www.reddit.com/r/";
	private static final String TOP_BASE_URL = "/top";
	private static final Integer MINIMUN_UP_VOTES = 5000;
	private static String[] subredditParameters = null;
	private static SubredditThreadService subredditThreadService;
	private static HtmlUnitDriver driver;

	public static void main(String[] args) {

		try {
			if (args[0] != null) {
				subredditParameters = args[0].toString().split(";");
			} else {
				System.out
						.println("You must give the parameters separated by ';'" + ". Try starting the program again!");
			}
		} catch (Exception e) {
			System.out.println("Something goes wrong: " + e);
		}

		init();
		for (String subreddit : subredditParameters) {
			findSubredditThreadsInformations(subreddit);
		}

		driver.quit();
	}

	private static void findSubredditThreadsInformations(String subreddit) {

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

					if (subredditThread.getUpVotes() > MINIMUN_UP_VOTES) {
						threadsList.add(subredditThread);
					} else {
						break;
					}

				}
			}
		}

		if (threadsList.size() > 0) {
			System.out.println(
					"\n\n---------------------------------------------------------------------------------------------------------------------------------------");
			System.out.println(
					"----------------------------------------------------- SHOWING MOST POPULAR(5k+ UpVotes) THREADS FOR SUBREDDIT => "
							+ subreddit.toUpperCase());
			for (SubredditThread threadItem : threadsList) {
				System.out.println(
						"----------------------------------------------------------------------------------------------------------------------------------------");
				System.out.println("\tTitle: " + threadItem.getTitle());
				System.out.println("\tUpvotes: " + threadItem.getUpVotes());
				System.out.println("\tThread Link: " + threadItem.getThreadLink());
				System.out.println("\tComments Link: " + threadItem.getCommentsLink());
			}

			System.out.println(
					"---------------------------------------------------------------------------------------------------------------------------------------");
			System.out.println("------------------------------------------------------------------ ENDING OPERATION");
			System.out.println(
					"----------------------------------------------------------------------------------------------------------------------------------------");
		}
	}

	/**
	 * Init the headless unit driver
	 */
	private static void init() {
		driver = new HtmlUnitDriver(BrowserVersion.CHROME);
		subredditThreadService = new SubredditThreadServiceImpl();
		System.getProperty("file.encoding", "UTF-8");
	}

}
