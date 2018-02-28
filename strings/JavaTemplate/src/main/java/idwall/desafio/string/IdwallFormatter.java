package idwall.desafio.string;

/**
 * Created by Rodrigo Catão Araujo on 06/02/2018.
 */
public class IdwallFormatter extends StringFormatter {

	/**
	 * Should format as described in the challenge
	 *
	 * @param text
	 *            A String containing a text
	 * @return A formatted text
	 */
	@Override
	public String format(String text, Integer charsLimit, boolean Justify) {
		if (Justify) {
			return splitTextJustified(splitText(text, charsLimit), charsLimit);
		} else {
			return splitText(text, charsLimit);
		}
	}

	/**
	 * Should break the lines of a text when the <code>limit</code> of character per
	 * line were reached.
	 * 
	 * @param text
	 *            A String containing a text
	 * @param charsLimit
	 *            The limit of characters accepted per line
	 * @return A formatted text
	 */
	private String splitText(String text, int charsLimit) {
		StringBuilder sb = new StringBuilder();
		char[] textArray = text.toCharArray();
		boolean endOfString = false;
		int start = 0;
		int end = start;

		while (start < textArray.length) {
			int charCount = 0;
			int lastSpace = 0;
			while (charCount <= charsLimit) {
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

	/**
	 * Should justify a text
	 * 
	 * @param text
	 *            A string containing a text
	 * @param charsLimit
	 *            The limit of characters accepted per line
	 * @return A justified text
	 */
	private String splitTextJustified(String text, int charsLimit) {
		String[] textLines = text.split("\n");
		StringBuilder sb = new StringBuilder();
		for (String line : textLines) {
			if (line.length() < charsLimit) {
				StringBuilder sbLine = new StringBuilder();

				// Actual line length
				int lineLength = line.length();

				// Lefting chars to reach the chars limit
				int leftingSpaces = charsLimit - lineLength;

				// Apply spaces to the actual line
				String[] lineWordsVector = line.split(" ");
				lineWordsVector = applySpaces(lineWordsVector, leftingSpaces);

				for (String palavra : lineWordsVector) {
					sb.append(palavra);
				}

				sb.append(sbLine).append("\n");

			} else {
				sb.append(line).append("\n");
			}
		}
		return sb.toString();
	}

	/**
	 * Should fill the lefting spaces of a line to justify all the text
	 *
	 * @param lineWordsVector
	 *            A vector containing all the words of a line
	 * @param leftingSpaces
	 *            All the spaces that might be applyed to the line
	 * @return
	 */
	private String[] applySpaces(String[] lineWordsVector, int leftingSpaces) {

		if (lineWordsVector != null && lineWordsVector.length >= 2) {

			int iniPosition = 0;
			int wordsCount = lineWordsVector.length;
			int filledSpaces = 0;

			for (int i = 0; i < wordsCount - 1; i++) {
				String words = lineWordsVector[i];
				words += " ";
				lineWordsVector[i] = words;
			}

			while (filledSpaces < leftingSpaces) {
				for (int i = iniPosition; i < wordsCount - 1; i = i + 2) {
					String words = lineWordsVector[i];
					words += " ";
					lineWordsVector[i] = words;
					filledSpaces++;
					if (filledSpaces == leftingSpaces) {
						break;
					}
				}
				if (iniPosition == 0) {
					iniPosition = 1;
				} else {
					iniPosition = 0;
				}
			}

		}

		return lineWordsVector;
	}

}
