package br.com.idwall.desafio.cli.subreddit.thread;

import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.gargoylesoftware.htmlunit.BrowserVersion;

import br.com.idwall.desafio.model.SubredditThread;
import br.com.idwall.desafio.service.SubredditThreadService;
import br.com.idwall.desafio.service.impl.SubredditThreadServiceImpl;

/**
 * Abstract Class FindSubredditThread
 * 
 * @author Yuri Cordeiro
 *
 */
public abstract class FindSubredditThread {

	private SubredditThreadService subredditThreadService;
	private HtmlUnitDriver driver;
	private HashMap<String, List<SubredditThread>> threadsHash;

	public FindSubredditThread() {
		super();
		this.subredditThreadService = new SubredditThreadServiceImpl();
		this.driver = new HtmlUnitDriver(BrowserVersion.CHROME);
		this.threadsHash = new HashMap<>();
	}

	/**
	 * Must return a String containing the whole threads top list of each subreddit
	 * 
	 * @param subreddits
	 *            subforum
	 * @return a String containing the whole threads top list of each subreddit
	 */
	public abstract String getSubredditInfo(String subreddits);

	public SubredditThreadService getSubredditThreadService() {
		return subredditThreadService;
	}

	public void setSubredditThreadService(SubredditThreadService subredditThreadService) {
		this.subredditThreadService = subredditThreadService;
	}

	public HtmlUnitDriver getDriver() {
		return driver;
	}

	public void setDriver(HtmlUnitDriver driver) {
		this.driver = driver;
	}

	public HashMap<String, List<SubredditThread>> getThreadsHash() {
		return threadsHash;
	}

	public void setThreadsHash(HashMap<String, List<SubredditThread>> threadsHash) {
		this.threadsHash = threadsHash;
	}

}
