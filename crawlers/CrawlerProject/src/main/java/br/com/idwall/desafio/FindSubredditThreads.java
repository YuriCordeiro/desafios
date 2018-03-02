package br.com.idwall.desafio;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

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
	private static String[] subredditParameters = null;
	private static SubredditThreadService subredditThreadService;
	private static HtmlUnitDriver driver;

	public static void main(String[] args) {

		try {
			subredditParameters = args[0].toString().split(";");
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println(
					"You must give the parameters separated by \";\". Try this example: \"askreddit;worldnews;cats\" \n"
							+ e);
		} catch (NullPointerException ex) {
			System.out.println(
					"You must give the parameters separated by ';'. Example: \"askreddit;worldnews;cats\" \n" + ex);
		} catch (Exception exc) {
			System.out.println("Something goes wrong: " + exc);
		}

		if (subredditParameters != null) {
			init();
			for (String subreddit : subredditParameters) {
				findSubredditThreadsInformations(subreddit);
			}

			driver.quit();
		}
	}

	public static void findSubredditThreadsInformations(String subreddit) {

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
	public static void init() {
		driver = new HtmlUnitDriver(BrowserVersion.CHROME);
		subredditThreadService = new SubredditThreadServiceImpl();
		System.getProperty("file.encoding", "UTF-8");
	}

	public static Properties getProp() throws IOException {
		Properties props = new Properties();
		FileInputStream file = new FileInputStream("./properties/config.properties");
		props.load(file);
		return props;

	}

}