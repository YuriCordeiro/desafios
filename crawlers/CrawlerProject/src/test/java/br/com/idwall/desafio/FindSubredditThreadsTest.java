package br.com.idwall.desafio;

//import static org.junit.Assert.*;
//
//import java.io.File;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.junit.Test;
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.opera.OperaDriver;
//import org.openqa.selenium.opera.OperaOptions;
//
//import br.com.idwall.desafio.model.SubredditThread;;

public class FindSubredditThreadsTest {

//	private static final String OPERA_DRIVER = "operadriver.exe";
//	private static final StringBuilder BASE_URL = new StringBuilder("http://www.reddit.com/r/");
//	private static final String TOP_URL = "/top";
//	private static WebDriver driver;
//	private static Map<String, List<SubredditThread>> threadsMap;
//
//	@Test
//	public void getRedditThread() {
//		ini();
//		getSubredditInfo("cats");
//	}
//
//	public void getSubredditInfo(String subredit) {
//
//		List<SubredditThread> redditList = null;
//
//		try {
//			driver.get(BASE_URL.append(subredit.trim()).append(TOP_URL).toString());
//		} catch (RuntimeException e) {
//			e.printStackTrace();
//			System.out.println("Something goes wrong when trying to connect to the '/" + subredit + "' subreddit.");
//		}
//		List<WebElement> thingIdElementList = null;
//		thingIdElementList = driver.findElements(By.id("thing"));
//
//		if (thingIdElementList != null) {
//			redditList = new ArrayList<>();
//			int threadCount = 0;
//			for (WebElement webElement : thingIdElementList) {
//				threadCount++;
//				if (threadCount > 1) { // The first thread will ever be a promoted thread. Something that doesn't
//										// makes sense for that subreddit wich we are looking for, so I gonna ignore it.
////					SubredditThread redditThread = //TODO: Service that finds everything that I need.
////							
////							if(redditThread.getUpVotes() < 5000) {
////								break;
////							} else {
////								redditList.add(redditThread);
////							}
//
//				}
//			}
//		}
//
//	}
//
//	/**
//	 * Opera Driver Configuration and System Initialization
//	 */
//	public void ini() {
//		ClassLoader classLoader = new Main().getClass().getClassLoader();
//		File file = new File(classLoader.getResource(OPERA_DRIVER).getFile());
//		OperaOptions options = new OperaOptions();
//		options.setBinary(new File("C:\\Program Files (x86)\\Opera\\launcher.exe"));
//		System.setProperty("webdriver.opera.driver", file.getAbsolutePath());
//
//		// Initialize Variables
//		driver = new OperaDriver(options);
//		threadsMap = new HashMap<>();
//	}

}
