package br.com.idwall.desafio.telegram.bot;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Properties;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import br.com.idwall.desafio.model.SubredditThread;
import br.com.idwall.desafio.telegram.enums.CommandEnum;

public class SendSubredditThreadsMessages extends TelegramLongPollingBot {

	/**
	 * Constants can be found at properties file
	 */
	private static final String BOT_TOKEN = "bot.token";
	private static final String MINIMUN_UPVOTES_KEY = "param.minimun_upvotes";
	private FindSubredditThreadsTelegram findSubredditThreadsTelegram = new FindSubredditThreadsTelegram();

	@Override
	public void onUpdateReceived(Update update) {
		String receivedMessage = update.getMessage().getText();
		SendMessage sendMessage = new SendMessage();
		StringBuilder sbBotMessage = new StringBuilder(); // It should contains all the messages for the user -> will be
															// passed in <code>.setText<code> method of the
															// <code>SendMessage</code> object

		// If is a command
		if (isCommandMessage(receivedMessage)) {
			if (receivedMessage.toLowerCase().equals(CommandEnum.START.getCommand().toLowerCase())) {
				sbBotMessage.append(getBundleMessage("intro_msg.start_message",
						new Object[] { update.getMessage().getFrom().getFirstName() }));
				sbBotMessage.append(getBundleMessage("intro_msg.commands_message",
						new Object[] { getBundleMessage(MINIMUN_UPVOTES_KEY) }));
			} else if (receivedMessage.toLowerCase().equals(CommandEnum.HELP.getCommand().toLowerCase())) {
				sbBotMessage.append(
						"Você digitou /Help. Na verdade este comando não faz nada.. ><' \nSe estiver precisando de alguma ajuda digite /start para ler a introdução do bot de novo (:");
			} else if (receivedMessage.toLowerCase()
					.startsWith(CommandEnum.NADA_PRA_FAZER.getCommand().toLowerCase())) {
				Map<String, List<SubredditThread>> resultMap = findSubredditThreadsTelegram
						.run(receivedMessage.replace(CommandEnum.NADA_PRA_FAZER.getCommand(), "").trim().split(";"));

				resultMap.entrySet().stream().forEach(entry -> {
					sbBotMessage.append(
							"\n~~> Top threads for *" + entry.getKey().toUpperCase() + "* subreddit on reddit.com");
					if (entry.getValue().size() > 0) {
						entry.getValue().stream()
								.forEach(threadObject -> sbBotMessage.append("\n\n*Thread Title*: "
										+ threadObject.getTitle() + "\n*Thread Upvotes*: _" + threadObject.getUpVotes()
										+ "_" + "\n*Thread Link*: [" + threadObject.getThreadLink() + "]"
										+ "\n*Comments Link*: [" + threadObject.getCommentsLink() + "]\n\n"));
					} else {
						sbBotMessage.append("\n\n\n_== No thread with " + getBundleMessage(MINIMUN_UPVOTES_KEY)
								+ "+ upvotes were found for this subreddit==_\n\n\n");
					}
					sbBotMessage.append("|..:: End of *" + entry.getKey().toUpperCase() + "* subreddit threads\n\n\n");
				});

			} else {
				sbBotMessage.append("\"[" + receivedMessage
						+ "]\" command doesn't exist. :/ \nTry typping the follow one: \n/NadaPraFazer askreddit;worldnews;cats");
			}
		} else { // If is a simple text
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
		return "Guess they call me bot'something else'.. I don't remember :/";
	}

	@Override
	public String getBotToken() {
		return getBundleMessage(BOT_TOKEN);
	}

	public boolean isCommandMessage(String message) {
		if (message != null && message.startsWith("/")) {
			return true;
		} else {
			return false;
		}
	}

	private static Properties getProp() {
		Properties props = new Properties();
		FileInputStream file;
		try {
			file = new FileInputStream("./properties/config.properties");
			props.load(file);
		} catch (FileNotFoundException e) {
			System.err.println("Config properties file not found");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Config properties file not found");
			e.printStackTrace();
		}
		return props;
	}

	public static String getBundleMessage(String key) {
		try {
			return getProp().getProperty(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}

	public static String getBundleMessage(String key, Object... params) {
		try {
			return MessageFormat.format(getProp().getProperty(key), params);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}

}
