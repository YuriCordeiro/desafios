package br.com.idwall.desafio.telegram.application;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import br.com.idwall.desafio.telegram.bot.SendSubredditThreadMessage;

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
		ApiContextInitializer.init();
		TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
		try {
			telegramBotsApi.registerBot(new SendSubredditThreadMessage());
			System.out.printf("%56s\n", "System initialized successfuly (:");
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}

}
