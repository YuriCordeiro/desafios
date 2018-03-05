package br.com.idwall.desafio;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import br.com.idwall.desafio.bot.SendSubredditThreadMessage;
import br.com.idwall.desafio.utils.PropertiesUtil;

/**
 * Main class responsible for initialize and run the telegram bot.</br>
 * It must be running to the whole job works (:
 * 
 * @author YuriC
 *
 */
public class Main {

	public static void main(String[] args) {
		System.out.println("============================= INITIALIZING SYSTEM...=============================");
		if (PropertiesUtil.getBundleMessage("bot.token").equals("")) {
			System.out.println(
					"The key that must contain a bot token as value on properties file(src/main/resources/config.properties) were found without value..");
			System.out.println("You need to paste a valid api key there before running the application!");
			System.out.println("*OBS: after paste your token on properties file, you will need to build the application again using the command 'mvn clean install'");
			System.out.println("============================== END OF APPLICATION ===============================");
		} else {
			ApiContextInitializer.init();
			TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
			try {
				telegramBotsApi.registerBot(new SendSubredditThreadMessage());
				System.out.printf("%56s\n", "System initialized successfuly (:");
				System.out.println(
						"Now you can start playing with your bot. Remember to not close this window while you play!");
			} catch (TelegramApiException e) {
				System.out.println("OH, NO! Something went wrong: " + e);
			}
		}
	}

}
