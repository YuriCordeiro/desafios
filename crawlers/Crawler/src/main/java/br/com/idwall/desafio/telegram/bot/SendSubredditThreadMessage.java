package br.com.idwall.desafio.telegram.bot;

import java.util.List;
import java.util.Map;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import br.com.idwall.desafio.model.SubredditThread;
import br.com.idwall.desafio.telegram.enums.CommandEnum;
import br.com.idwall.desafio.utils.PropertiesUtil;

public class SendSubredditThreadMessage extends TelegramLongPollingBot {

	/**
	 * Constants can be found at properties file
	 */
	private static final String BOT_TOKEN = "bot.token";
	private static final String MINIMUN_UPVOTES_KEY = "param.minimun_upvotes";
	private FindSubredditThreadTelegram findSubredditThreadsTelegram;

	public SendSubredditThreadMessage() {
		this.findSubredditThreadsTelegram = new FindSubredditThreadTelegram();
	}

	@Override
	public void onUpdateReceived(Update update) {
		String receivedMessage = update.getMessage().getText();
		SendMessage sendMessage = new SendMessage();
		StringBuilder sbBotMessage = new StringBuilder(); // It should contains all the messages for the user -> will be
															// passed in <code>.setText<code> method of the
															// <code>SendMessage</code> object

		// If it's a command
		if (isCommandMessage(receivedMessage)) {
			if (receivedMessage.toLowerCase().equals(CommandEnum.START.getCommand().toLowerCase())) {
				sbBotMessage.append(PropertiesUtil.getBundleMessage("intro_msg.start_message",
						new Object[] { update.getMessage().getFrom().getFirstName() }));
				sbBotMessage.append(PropertiesUtil.getBundleMessage("intro_msg.commands_message",
						new Object[] { PropertiesUtil.getBundleMessage(MINIMUN_UPVOTES_KEY) }));
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
						sbBotMessage.append(
								"\n\n\n_== No thread with " + PropertiesUtil.getBundleMessage(MINIMUN_UPVOTES_KEY)
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
		return PropertiesUtil.getBundleMessage(BOT_TOKEN);
	}

	/**
	 * Should verify if a received message is a command
	 * 
	 * @param message
	 * @return
	 */
	public boolean isCommandMessage(String message) {
		if (message != null && message.startsWith("/")) {
			return true;
		} else {
			return false;
		}
	}

}
