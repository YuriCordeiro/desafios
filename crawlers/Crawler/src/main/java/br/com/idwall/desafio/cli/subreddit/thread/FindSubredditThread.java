package br.com.idwall.desafio.cli.subreddit.thread;

import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.gargoylesoftware.htmlunit.BrowserVersion;

import br.com.idwall.desafio.cli.subreddit.thread.service.SubredditThreadService;
import br.com.idwall.desafio.cli.subreddit.thread.service.impl.SubredditThreadServiceImpl;
import br.com.idwall.desafio.model.SubredditThread;

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
	 * Must return a String containing the top threads list of a subreddit</br>
	 * 
	 * @param subreddit
	 *            a single sub-forum name
	 * @return a String containing the whole threads top list a subreddit
	 */
	public abstract String getSubredditInfo(String subreddit);

	/**
	 * Ends driver session
	 */
	public abstract void endDriverSession();

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
