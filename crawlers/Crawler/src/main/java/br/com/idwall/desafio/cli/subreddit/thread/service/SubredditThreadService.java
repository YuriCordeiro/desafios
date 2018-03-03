package br.com.idwall.desafio.cli.subreddit.thread.service;

import org.openqa.selenium.WebElement;

import br.com.idwall.desafio.model.SubredditThread;

/**
 * Service interface responsible for {@link SubredditThread} data
 * 
 * @author Yuri Cordeiro
 *
 */
public interface SubredditThreadService {

	/**
	 * Method returns a new {@link SubredditThread} Object filled with a thread
	 * information given by <a href="http://www.reddit.com">Reddit's Web-site</a>
	 * 
	 * @param threadElement
	 *            The 'thing' class web element
	 * @param subreddit
	 *            it represents a sub-forum
	 * @return {@link SubredditThread}
	 */
	public SubredditThread findSubredditThreadInformations(WebElement threadElement, String subreddit);

	/**
	 * Returns a Thread title
	 * 
	 * @param threadElement
	 *            The 'thing' class web element
	 * @return title
	 */
	public String findThreadTitle(WebElement threadElement);

	/**
	 * Return how many upvotes a thread has
	 * 
	 * @param threadElement
	 *            The 'thing' class web element
	 * @return upvotes of a thread
	 */
	public int findThreadUpVoted(WebElement threadElement);

	/**
	 * Returns if a thread is promoted or not
	 * 
	 * @param threadElement
	 *            The 'thing' class web element
	 * @return <b>true</b> if a thread is promoted, <b>false</b> if not
	 */
	public boolean isPromotedThread(WebElement threadElement);

	/**
	 * Returns thread's link
	 * 
	 * @param threadElement
	 *            The 'thing' class web element
	 * @return thread's link
	 */
	public String findThreadLink(WebElement threadElement);

	/**
	 * Returns thread's comments link
	 * 
	 * @param threadElement
	 *            The 'thing' class web element
	 * @return thread's comments link
	 */
	public String findThreadCommentsLink(WebElement threadElement);

}
