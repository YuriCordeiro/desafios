package idwall.desafio.string;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Test class responsible for methods of the class IdwallFormatter.java
 * 
 * @author YuriC
 */
public class IdWallFormatterTest {

	private static final String DEFAULT_INPUT_TEXT = "In the beginning God created the heavens and the earth. Now the earth was formless and empty, darkness was over the surface of the deep, and the Spirit of God was hovering over the waters.\n"
			+ "\n"
			+ "And God said, \"Let there be light,\" and there was light. God saw that the light was good, and he separated the light from the darkness. God called the light \"day,\" and the darkness he called \"night.\" And there was evening, and there was morning - the first day.";
	private static final Integer DEFAULT_LIMIT = 40;

	private StringFormatter stringFormatter;

	/**
	 * Should return TRUE for each line that contains less or equal characters than
	 * <code>DEFAULT_LIMIT</code> per line. </br>
	 * It tests the non-justified text
	 */
	@Test
	public void testFormat() {
		String lines[] = getStringFormatter().format(DEFAULT_INPUT_TEXT, DEFAULT_LIMIT, true).split("\n");

		for (String line : lines) {
			assertTrue(line.length() <= DEFAULT_LIMIT);
		}
	}

	@Test
	public void testFormatNotJustify() {
		String lines[] = getStringFormatter().format(DEFAULT_INPUT_TEXT, DEFAULT_LIMIT, false).split("\n");

		for (String line : lines) {
			assertTrue(line.length() <= DEFAULT_LIMIT);
		}
	}

	/**
	 * Returns a new instance of IdwallFormatter class
	 * 
	 * @return
	 */
	public StringFormatter getStringFormatter() {
		return this.stringFormatter = new IdwallFormatter();
	}

}
