package br.com.idwall.desafio.cli;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import br.com.idwall.desafio.model.SubredditThread;
import br.com.idwall.desafio.utils.PropertiesUtil;

/**
 * Class responsible for {@link CliApplication} unit tests
 * 
 * @author YuriC
 *
 */
public class CliApplicationTest {

	/**
	 * Testing the Default constructor
	 */
	@Test
	public void testCliApplication() {
		CliApplication cli = new CliApplication();
		assertNotNull(cli);
		assertNotNull(cli.getDriver());
		assertNotNull(cli.getSubredditThreadService());
		assertNotNull(cli.getSubredditThreadsHash());
	}

	/**
	 * Should verify for each thread object in the HashMap if the 'upvotes'
	 * attribute it's bigger or equal to the 'param.minimum_upvote' value contained
	 * on the properties file
	 */
	@Test
	public void testFindSubredditThreadsInformations() {
		System.out.println(
				"\n\nRunning this test may prints some DefaultCssErrorHandler errors, but don't worry, everything's under control by here!\n\n");
		CliApplication cli = new CliApplication();
		Map<String, List<SubredditThread>> threadHash = cli.findSubredditThreadsInformations("askreddit");
		threadHash.entrySet().stream().forEach(entry -> entry.getValue().forEach(thread -> assertTrue(
				thread.getUpVotes() >= Integer.valueOf(PropertiesUtil.getBundleMessage("param.minimum_upvotes")))));
	}
}
