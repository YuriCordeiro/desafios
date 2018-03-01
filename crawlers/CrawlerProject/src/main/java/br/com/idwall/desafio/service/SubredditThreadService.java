package br.com.idwall.desafio.service;

import org.openqa.selenium.WebElement;

import br.com.idwall.desafio.model.SubredditThread;

/**
 * Service interface responsible for {@link SubredditThread} data
 * 
 * @author YuriC
 *
 */
public interface SubredditThreadService {

	/**
	 * TODO: <strong>JAVADOC</strong>
	 * 
	 * @author YuriC
	 *
	 */
	public SubredditThread findSubredditThreadInformations(WebElement threadElement, String subreddit);

	/**
	 * TODO: <strong>JAVADOC</strong>
	 * 
	 * @author YuriC
	 *
	 */
	public String findThreadTitle(WebElement threadElement);

	/**
	 * TODO: <strong>JAVADOC</strong>
	 * 
	 * @author YuriC
	 *
	 */
	public int findThreadUpVoted(WebElement threadElement);

	/**
	 * TODO: <strong>JAVADOC</strong>
	 * 
	 * @author YuriC
	 *
	 */
	public boolean isPromotedThread(WebElement threadElement);

	/**
	 * TODO: <strong>JAVADOC</strong>
	 * 
	 * @author YuriC
	 *
	 */
	public String findThreadLink(WebElement threadElement);

	/**
	 * TODO: <strong>JAVADOC</strong>
	 * 
	 * @author YuriC
	 *
	 */
	public String findThreadCommentsLink(WebElement threadElement);

}
