package br.com.idwall.desafio.cli.subreddit.thread.service.impl;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import br.com.idwall.desafio.cli.subreddit.thread.service.SubredditThreadService;
import br.com.idwall.desafio.model.SubredditThread;

/**
 * Implementation class of interface {@link SubredditThreadService}
 * 
 * @author Yuri Cordeiro
 *
 */
public class SubredditThreadServiceImpl implements SubredditThreadService {

	@Override
	public SubredditThread findSubredditThreadInformations(WebElement threadElement, String subreddit) {
		return new SubredditThread(subreddit, findThreadTitle(threadElement), findThreadUpVoted(threadElement),
				findThreadLink(threadElement), findThreadCommentsLink(threadElement));
	}

	@Override
	public String findThreadTitle(WebElement threadElement) {

		try {
			return getTopMatterWebElementClass(threadElement).findElement(By.className("may-blank")).getText();
		} catch (Exception e) {
			System.out.println(
					"Something goes wrong when trying to find the classes: .entry > .top-matter > .may-blank\nMaybe the HTML of this page has benn changed.");
		}
		return "";
	}

	@Override
	public String findThreadLink(WebElement threadElement) {
		try {
			return getTopMatterWebElementClass(threadElement).findElement(By.className("may-blank"))
					.getAttribute("href");
		} catch (Exception e) {
			System.out.println(
					"Something goes wrong when trying to find the classes: .entry > .top-matter > .may-blank + |attribute 'href'| ");
		}
		return "";
	}

	@Override
	public int findThreadUpVoted(WebElement threadElement) {
		try {
			String upvotes = threadElement.findElement(By.className("midcol")).findElement(By.className("unvoted"))
					.getAttribute("title").toString();

			return upvotes != null && upvotes.matches("[0-9].*") ? Integer.valueOf(upvotes) : 0;

		} catch (Exception e) {
			System.out.println("Midcol or Unvoted class not found:\n" + e);
		}
		return 0;
	}

	@Override
	public boolean isPromotedThread(WebElement threadElement) {

		try {
			return getTopMatterWebElementClass(threadElement).findElement(By.className("flat-list"))
					.findElement(By.className("promoted-tag")) != null ? true : false;
		} catch (Exception e) {
			return false;
		}

	}

	@Override
	public String findThreadCommentsLink(WebElement threadElement) {
		return getTopMatterWebElementClass(threadElement).findElement(By.className("flat-list"))
				.findElement(By.className("first")).findElement(By.tagName("a")).getAttribute("href");
	}

	/**
	 * Shortcut to top-matter web element class
	 * 
	 * @param threadElement
	 * @return WebElement top-matter (a div with class name 'top-matter')
	 */
	private WebElement getTopMatterWebElementClass(WebElement threadElement) {
		return threadElement.findElement(By.className("entry")).findElement(By.className("top-matter"));
	}

}
