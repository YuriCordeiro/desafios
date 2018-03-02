package br.com.idwall.desafio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.gargoylesoftware.htmlunit.BrowserVersion;

import br.com.idwall.desafio.model.SubredditThread;
import br.com.idwall.desafio.service.SubredditThreadService;
import br.com.idwall.desafio.service.impl.SubredditThreadServiceImpl;

/**
 * Simple CLI application that provides the top threads of subreddits on
 * <a href="http://www.reddit.com">Reddit's Website</a>
 * 
 * @author Yuri Cordeiro
 *
 */
public class FindSubredditThreadsCli {
	private static SubredditThreadService subredditThreadService;
	private static HtmlUnitDriver driver;
	private static Map<String, List<SubredditThread>> subredditThreadsHash;

	public static void main(String[] args) {
		if (args != null && args.length == 1) {
			runApplication(args[0].toString().split(";"));
		} else {
			System.out.println(getBundleMessage("cli_msg.null_parameters"));

			System.out.println(getBundleMessage("cli_msg.end_program"));
		}
	}

	/**
	 * Where the information takes the right way
	 */
	private static void runApplication(String[] subredditParameters) {
		initialize();
		subredditThreadsHash = new HashMap<>();
		StringBuilder sbPrintThreadsResults = new StringBuilder();

		System.out.println(getBundleMessage("cli_msg.before_process_info_message"));

		for (String subreddit : subredditParameters) {
			findSubredditThreadsInformations(subreddit);
		}

		if (subredditThreadsHash.size() > 0) {
			subredditThreadsHash.entrySet().stream().forEach(entry -> {
				sbPrintThreadsResults.append("\n~~> Top threads for *" + entry.getKey().toUpperCase()
						+ "* subreddit on " + getBundleMessage("url.base_url").replace("/r", "") + "\n|");
				if (entry.getValue().size() > 0) {

					entry.getValue().stream()
							.forEach(threadObject -> sbPrintThreadsResults.append("\n*Thread Title*: "
									+ threadObject.getTitle() + "\n*Thread Upvotes*: " + threadObject.getUpVotes()
									+ "\n*Thread Link*: [" + threadObject.getThreadLink() + "]" + "\n*Comments Link*: ["
									+ threadObject.getCommentsLink() + "]\n\n"));
				} else {
					sbPrintThreadsResults.append("\n=== No threads with " + getBundleMessage("param.minimum_upvotes")
							+ "+ upvotes were found for this subreddit===\n");
				}
				sbPrintThreadsResults
						.append("|\n|..:: End of *" + entry.getKey().toUpperCase() + "* subreddit threads\n\n\n");
			});
		}

		driver.quit();
		System.out.println(sbPrintThreadsResults);
		System.out.println(getBundleMessage("cli_msg.after_process"));
	}

	/**
	 * Searches for a subreddit top threads information
	 * 
	 * @param subreddit
	 *            <reddit it's a forum website, where> subreddits are sub-forums,
	 *            like <b>cats</b>
	 * @return a hash containing the subreddit keys, and a list of top threads
	 */
	public static Map<String, List<SubredditThread>> findSubredditThreadsInformations(String subreddit) {

		try {
			driver.get(
					getBundleMessage("url.base_url").concat(subreddit).concat(getBundleMessage("url.top")).toString());
		} catch (Exception e) {
			System.out.println("Url not found: " + e);
		}

		List<SubredditThread> threadsList = new ArrayList<SubredditThread>();
		List<WebElement> webElementsThreadsList = null;

		try {
			webElementsThreadsList = driver.findElements(By.className("thing"));
		} catch (Exception e) {
			System.out.println(getBundleMessage("cli_err.thing_class_not_found") + e);
		}

		webElementsThreadsList.stream().forEach(thingClassWebElement -> {
			if (!subredditThreadService.isPromotedThread(thingClassWebElement)) { // We don't wanna promoted threads

				SubredditThread subredditThread = subredditThreadService
						.findSubredditThreadInformations(thingClassWebElement, subreddit);

				if (subredditThread.getUpVotes() >= Integer.valueOf(getBundleMessage("param.minimum_upvotes"))) {
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

	/**
	 * Initialize the headless unit driver
	 */
	public static void initialize() {
		System.out.println(getBundleMessage("cli.msg_initializing"));
		driver = new HtmlUnitDriver(BrowserVersion.CHROME);
		subredditThreadService = new SubredditThreadServiceImpl();
		System.getProperty("file.encoding", "UTF-8");
	}

	/**
	 * Searches for the properties file
	 * 
	 * @return props
	 */
	private static Properties getProp() {
		Properties props = new Properties();
		FileInputStream file;
		try {
			file = new FileInputStream("./properties/config.properties");
			props.load(file);
		} catch (FileNotFoundException e) {
			System.err.println("Config properties file not found");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Config properties file not found");
			e.printStackTrace();
		}
		return props;
	}

	/**
	 * A shortcut method to the <code>getProperty()</code>
	 * 
	 * @param key
	 *            a key contained on .properties file
	 * @return the value contained on the key
	 */
	public static String getBundleMessage(String key) {
		try {
			return getProp().getProperty(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}

}
