package idwall.desafio;

import idwall.desafio.string.IdwallFormatter;
import idwall.desafio.string.StringFormatter;

/**
 * Created by Rodrigo Catão Araujo on 06/02/2018.
 */
public class Main { // TODO: Create a README.MD to this project

	private static final String DEFAULT_INPUT_TEXT = "In the beginning God created the heavens and the earth. Now the earth was formless and empty, darkness was over the surface of the deep, and the Spirit of God was hovering over the waters.\n"
			+ "\n"
			+ "And God said, \"Let there be light,\" and there was light. God saw that the light was good, and he separated the light from the darkness. God called the light \"day,\" and the darkness he called \"night.\" And there was evening, and there was morning - the first day.";
	private static final Integer DEFAULT_LIMIT = 40;
	private static final Boolean DEFAULT_JUSTIFY = true;

	public static void main(String[] args) {
		String text = DEFAULT_INPUT_TEXT;
		Integer limit = DEFAULT_LIMIT;
		Boolean justify = DEFAULT_JUSTIFY;
		switch (args.length) {
		case 1:
			text = args[0];
			break;
		case 2:
			text = args[0];
			try {
				limit = Integer.parseInt(args[1]);
			} catch (NumberFormatException e) {
				System.out.println(
						"\nHouve um problema ao converter um dado do tipo String para Integer.\nFavor verificar se o número passado para a variável 'limit' corresponde a um número.\nRodando o programa com o limit padrão de: "
								+ DEFAULT_LIMIT + "\n");
				System.out.println("=========================");
			}
			break;
		case 3:
			text = args[0];
			try {
				limit = Integer.parseInt(args[1]);
			} catch (NumberFormatException e) {
				System.out.println(
						"\nHouve um problema ao converter um dado do tipo String para Integer.\nFavor verificar se o número passado para a variável 'limit' corresponde a um número.\nRodando o programa com o limit padrão de: "
								+ DEFAULT_LIMIT + "\n");
				System.out.println("=========================");
			}
			justify = Boolean.parseBoolean(args[2]);
			break;

		default:
			break;
		}

		// Print input data
		System.out.println("Inputs: ");
		System.out.println("Text: " + text);
		System.out.println("Limit: " + limit);
		System.out.println("Should justify: " + justify);
		System.out.println("=========================");

		// Run IdwallFormatter
		final StringFormatter sf = new IdwallFormatter();
		String outputText = sf.format(text, limit, justify);

		// Print output text
		System.out.println("Output: ");
		System.out.println(outputText);
	}
}
