package br.com.idwall.desafio.cli.subreddit.thread;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import br.com.idwall.desafio.cli.subreddit.thread.FindSubredditThread;
import br.com.idwall.desafio.cli.subreddit.thread.IdwallFindSubredditThread;
import br.com.idwall.desafio.model.SubredditThread;
import br.com.idwall.desafio.utils.PropertiesUtil;

/**
 * Class responsible for {@link IdwallFindSubredditThread} unit tests
 * 
 * @author YuriC
 *
 */
public class IdwallFindSubredditThreadTest {

	/**
	 * Testing the Default constructor</br>
	 * Must instantiate the necessary attributes
	 */
	@Test
	public void testFindSubredditThread() {
		FindSubredditThread cli = new IdwallFindSubredditThread();
		assertNotNull(cli);
		assertNotNull(cli.getDriver());
		assertNotNull(cli.getSubredditThreadService());
		assertNotNull(cli.getThreadsHash());
	}

	/**
	 * Should verify for each thread object in the HashMap if the 'upvotes'
	 * attribute it's bigger or equal to the 'param.minimum_upvote' value contained
	 * on the properties file
	 */
	@Test
	public void testFindSubredditThreadInformation() {
		System.out.println(
				"\n\nRunning this test may prints some DefaultCssErrorHandler errors, but don't worry, everything's under control by here!\n\n");
		IdwallFindSubredditThread cli = new IdwallFindSubredditThread();
		Map<String, List<SubredditThread>> threadHash = cli.findWebSubredditThreadInformation("askreddit",
				new HashMap<>());
		threadHash.entrySet().stream().forEach(entry -> entry.getValue().forEach(thread -> assertTrue(
				thread.getUpVotes() >= Integer.valueOf(PropertiesUtil.getBundleMessage("param.minimum_upvotes")))));
	}
}
