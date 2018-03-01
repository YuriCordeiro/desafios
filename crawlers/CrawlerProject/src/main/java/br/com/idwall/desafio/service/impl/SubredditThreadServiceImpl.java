package br.com.idwall.desafio.service.impl;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import br.com.idwall.desafio.model.SubredditThread;
import br.com.idwall.desafio.service.SubredditThreadService;

public class SubredditThreadServiceImpl implements SubredditThreadService {

	@Override
	public SubredditThread findSubredditThreadInformations(WebElement threadElement, String subreddit) {
		return new SubredditThread(subreddit, findThreadTitle(threadElement), findThreadUpVoted(threadElement),
				findThreadLink(threadElement), findThreadCommentsLink(threadElement));
	}

	@Override
	public String findThreadTitle(WebElement threadElement) {

		try {
			WebElement topMatterClass = getTopMatterWebElementClass(threadElement);
			return topMatterClass.findElement(By.className("may-blank")).getText(); // Contains the thread
																					// title

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
			WebElement midcolWebElement = threadElement.findElement(By.className("midcol"));
			WebElement unvotedWebElement = midcolWebElement.findElement(By.className("unvoted"));
			String upvotes = unvotedWebElement.getAttribute("title").toString();
			if (upvotes != null && upvotes.matches("[0-9].*")) {
				return Integer.valueOf(upvotes);
			}
		} catch (Exception e) {
			System.out.println("Midcol or Unvoted class not found:\n" + e);
		}

		return 0;
	}

	/**
	 * Should validate if a thread is promoted or not
	 * 
	 * @return <code>true</code> if it's promoted and <code>false</code> if it's
	 *         not.
	 */
	@Override
	public boolean isPromotedThread(WebElement threadElement) {
		WebElement topMatterClass = getTopMatterWebElementClass(threadElement);
		WebElement flatListButtonsClass = topMatterClass.findElement(By.className("flat-list"));
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
		WebElement topMatterClass = getTopMatterWebElementClass(threadElement);
		WebElement flatListButtonsClass = topMatterClass.findElement(By.className("flat-list"));
		WebElement firstClassWebElement = flatListButtonsClass.findElement(By.className("first"));
		return firstClassWebElement.findElement(By.tagName("a")).getAttribute("href");
	}

	private WebElement getTopMatterWebElementClass(WebElement threadElement) {
		WebElement entryClass = threadElement.findElement(By.className("entry"));
		return entryClass.findElement(By.className("top-matter"));
	}

}
