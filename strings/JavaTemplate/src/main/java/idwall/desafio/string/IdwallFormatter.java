package idwall.desafio.string;

/**
 * Created by Rodrigo Catão Araujo on 06/02/2018.
 */
public class IdwallFormatter extends StringFormatter {

	/**
	 * Should format as described in the challenge
	 *
	 * @param text
	 * @return
	 */
	@Override
	public String format(String text, Integer limit) {
		// throw new UnsupportedOperationException();
		return splitText(text, limit);
	}

	public String splitText(String text, int limit) {
		StringBuilder sb = new StringBuilder();
		char[] textArray = text.toCharArray();
		boolean endOfString = false;
		int start = 0;
		int end = start;

		while (start < textArray.length) {
			int charCount = 0;
			int lastSpace = 0;
			while (charCount <= limit) {
				if (textArray[charCount + start] == ' ' || textArray[charCount + start] == '\n') {
					lastSpace = charCount;
				}
				charCount++;
				if (charCount + start == text.length()) {
					endOfString = true;
					break;
				}
			}
			end = endOfString ? text.length() : (lastSpace > 0) ? lastSpace + start : charCount + start;
			sb.append(text.substring(start, end) + "\n");
			start = end + 1;
		}
		return sb.toString();
	}
}
