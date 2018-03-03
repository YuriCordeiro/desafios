package br.com.idwall.desafio.service.impl;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import br.com.idwall.desafio.model.SubredditThread;
import br.com.idwall.desafio.service.SubredditThreadService;

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
			if (upvotes != null && upvotes.matches("[0-9].*")) {
				return Integer.valueOf(upvotes);
			}
		} catch (Exception e) {
			System.out.println("Midcol or Unvoted class not found:\n" + e);
		}

		return 0;
	}

	@Override
	public boolean isPromotedThread(WebElement threadElement) {
		WebElement flatListButtonsClass = getTopMatterWebElementClass(threadElement)
				.findElement(By.className("flat-list"));
		try {
			if (flatListButtonsClass.findElement(By.className("promoted-tag")) != null)
				return true;
			else
				return false;
		} catch (Exception e) { // If findElementByClassName returns null, it isn't promoted
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
