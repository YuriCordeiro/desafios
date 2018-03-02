package br.com.idwall.desafio.telegram;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import br.com.idwall.desafio.model.SubredditThread;
import br.com.idwall.desafio.telegram.enums.CommandEnum;

public class SendSubredditThreadsMessages extends TelegramLongPollingBot {

	/**
	 * can be found at properties file
	 */
	private static final String BOT_TOKEN = "BOT_TOKEN";
	private static final String MINIMUN_UPVOTES = "MINIMUN_UPVOTE";

	@Override
	public void onUpdateReceived(Update update) {
		String receivedMessage = update.getMessage().getText();
		SendMessage sendMessage = new SendMessage();
		StringBuilder sbBotMessage = new StringBuilder(); // It should contains all the messages for the user -> will be
															// passed in <code>.setText<code> method of the
															// <code>SendMessage</code> object

		if (isCommandMessage(receivedMessage)) {
			if (receivedMessage.toLowerCase().equals(CommandEnum.START.getCommand().toLowerCase())) {
				sbBotMessage.append("Hello, " + update.getMessage().getFrom().getFirstName()
						+ ". It's your first time here? Let me help you! (:");
				sbBotMessage.append(
						"\n\nYou can use the following commands at this time: \n\n*/Help* - If you are needing help.\n\n*/NadaPraFazer cats;dogs;brazil* - I gonna search for the threads with *"
								+ getProp().getProperty(MINIMUN_UPVOTES)
								+ "+* upvotes for the subreddits you told me (*cats*, *dogs* and *brazil*)\nYou can try another popular subreddits too like *askreddit* and *worldnews*, by example. (_IMPORTANT: Remeber to give me the subreddits splitted by \";\" like the example given_)");
				sbBotMessage.append("\n\nAre you ready? Let's begin!");
			} else if (receivedMessage.toLowerCase().equals(CommandEnum.HELP.getCommand().toLowerCase())) {
				sbBotMessage.append(
						"Você digitou /Help. Na verdade este comando não faz nada.. ><' \nSe estiver precisando de alguma ajuda digite /start para ler a introdução do bot de novo (:");
			} else if (receivedMessage.toLowerCase()
					.startsWith(CommandEnum.NADA_PRA_FAZER.getCommand().toLowerCase())) {
				Map<String, List<SubredditThread>> resultMap = FindSubredditThreadsTelegram
						.run(receivedMessage.replace(CommandEnum.NADA_PRA_FAZER.getCommand(), "").trim().split(";"));

				for (Entry<String, List<SubredditThread>> entry : resultMap.entrySet()) {
					sbBotMessage.append(
							"\n=> Top threads for *" + entry.getKey().toUpperCase() + "* subreddit on reddit.com");
					if (entry.getValue().size() > 0) {
						entry.getValue().stream()
								.forEach(threadObject -> sbBotMessage.append("\n\n  *Thread Title*: "
										+ threadObject.getTitle() + "\n  *Thread Upvotes*: " + threadObject.getUpVotes()
										+ "\n  *Thread Link*: [" + threadObject.getThreadLink() + "]"
										+ "\n  *Comments Link*: [" + threadObject.getCommentsLink() + "]\n\n"));
					} else {
						sbBotMessage.append("\n\n\n_== NO THREADS WITH " + getProp().getProperty(MINIMUN_UPVOTES)
								+ "+ UVPOTES WERE FOUND FOR THIS SUBREDDIT==_\n\n\n");
					}
					sbBotMessage.append("** END OF *" + entry.getKey().toUpperCase() + "* subreddit threads\n\n\n");
				}

			} else {
				sbBotMessage.append("\"[" + receivedMessage
						+ "]\" command doesn't exist. :/ \nTry typping the follow one: \n/NadaPraFazer askreddit;worldnews;cats");
			}
		} else {
			sbBotMessage.append("Sorry " + update.getMessage().getFrom().getFirstName()
					+ ", I can't understand what are you saying yet.. I'm a BOT in BETA Version (for tests only). \n\n If you wanna test me, please type: /help");
		}

		sendMessage.setText(sbBotMessage.toString()).setParseMode("Markdown");
		sendMessage.setChatId(update.getMessage().getChatId());

		try {
			execute(sendMessage);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getBotUsername() {
		// return "y1test_bot";
		return "They call me 'experience'.. I don't know why :(";
	}

	@Override
	public String getBotToken() {
		return getProp().get(BOT_TOKEN).toString();
	}

	public boolean isCommandMessage(String message) {
		if (message != null && message.startsWith("/")) {
			return true;
		} else {
			return false;
		}
	}

	public Properties getProp() {
		Properties props = new Properties();
		FileInputStream file;
		try {
			file = new FileInputStream("./properties/config.properties");
			props.load(file);
		} catch (FileNotFoundException e) {
			System.err.println("PROPERTIES FILE NOT FOUND");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("PROPERTIES FILE NOT FOUND");
			e.printStackTrace();
		}
		return props;

	}
}
