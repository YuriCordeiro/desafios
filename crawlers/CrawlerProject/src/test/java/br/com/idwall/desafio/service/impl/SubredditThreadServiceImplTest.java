package br.com.idwall.desafio.service.impl;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.opera.OperaOptions;

import br.com.idwall.desafio.Main;
import br.com.idwall.desafio.model.SubredditThread;
import br.com.idwall.desafio.service.SubredditThreadService;

public class SubredditThreadServiceImplTest {

	private static final String OPERA_DRIVER = "operadriver.exe";
	private static final StringBuilder BASE_URL = new StringBuilder("http://www.reddit.com/r/");
	private static final String TOP_URL = "/top";
	private static WebDriver driver;
	@SuppressWarnings("unused")
	private static Map<String, List<SubredditThread>> threadsMap;
	private static SubredditThreadService subredditThreadService = new SubredditThreadServiceImpl();
	private static final Integer MINIMUN_UP_VOTES = 5000;

	@Test
	public void testFindSubredditThreadInformations() {
		ini();

		driver.get(BASE_URL.append("worldnews").append(TOP_URL).toString());
		// List<SubredditThread> threadsReddit = null;
		List<WebElement> listaThing = null;
		listaThing = driver.findElements(By.className("thing"));

		if (listaThing != null) {
			// threadsReddit = new ArrayList<SubredditThread>();
			for (WebElement webElementThing : listaThing) {
				if (!subredditThreadService.isPromotedThread(webElementThing)) {

					SubredditThread subredditThread = subredditThreadService
							.findSubredditThreadInformations(webElementThing, "worldnews");

					if (subredditThread.getUpVotes() > MINIMUN_UP_VOTES) {
						System.out.println(
								"------------------------------------------------------------------------------------------");
						System.out.println("TITLE: " + subredditThread.getTitle());
						System.out.println("UPVOTE: " + subredditThread.getUpVotes());
						System.out.println("THREAD-LINK: " + subredditThread.getThreadLink());
						System.out.println("COMMENTS-LINK: " + subredditThread.getCommentsLink());
						System.out.println(
								"------------------------------------------------------------------------------------------");
					} else {
						break;
					}
					// threadsReddit.add(threadReddit);

				}
			}
		}
	}

	/**
	 * Opera Driver Configuration and System Initialization
	 */
	public void ini() {
		ClassLoader classLoader = new Main().getClass().getClassLoader();
		File file = new File(classLoader.getResource(OPERA_DRIVER).getFile());
		OperaOptions options = new OperaOptions();
		options.setBinary(new File("C:\\Program Files (x86)\\Opera\\launcher.exe"));
		System.setProperty("webdriver.opera.driver", file.getAbsolutePath());

		// Initialize Variables
		driver = new OperaDriver(options);
		threadsMap = new HashMap<>();
	}

}
