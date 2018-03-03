package br.com.idwall.desafio.model;

import java.io.Serializable;

/**
 * Object represents a Subreddit thread
 * 
 * @author YuriC
 *
 */
public class SubredditThread implements Serializable {

	private static final long serialVersionUID = -8958315870806950260L;

	private String subreddit;
	private String title;
	private Integer upVotes;
	private String threadLink;
	private String commentsLink;

	/**
	 * Constructor using all fields
	 * 
	 * @param subreddit
	 *            it represents a subforum
	 * @param title
	 *            thread's title
	 * @param upVotes
	 *            how many likes thread has
	 * @param threadLink
	 *            thread's link
	 * @param commentsLink
	 *            thread's comments link
	 */
	public SubredditThread(String subreddit, String title, Integer upVotes, String threadLink, String commentsLink) {
		super();
		this.subreddit = subreddit;
		this.title = title;
		this.upVotes = upVotes;
		this.threadLink = threadLink;
		this.commentsLink = commentsLink;
	}

	/////////////////////////////////
	// Getters andSsetters
	/////////////////////////////////
	public String getSubreddit() {
		return subreddit;
	}

	public void setSubreddit(String subreddit) {
		this.subreddit = subreddit;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getUpVotes() {
		return upVotes;
	}

	public void setUpVotes(Integer upVotes) {
		this.upVotes = upVotes;
	}

	public String getThreadLink() {
		return threadLink;
	}

	public void setThreadLink(String threadLink) {
		this.threadLink = threadLink;
	}

	public String getCommentsLink() {
		return commentsLink;
	}

	public void setCommentsLink(String commentsLink) {
		this.commentsLink = commentsLink;
	}

}
