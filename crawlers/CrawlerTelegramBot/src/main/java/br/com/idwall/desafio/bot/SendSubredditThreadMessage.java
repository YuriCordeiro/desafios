package br.com.idwall.desafio.bot;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import br.com.idwall.desafio.cli.subreddit.thread.FindSubredditThread;
import br.com.idwall.desafio.cli.subreddit.thread.IdwallFindSubredditThread;
import br.com.idwall.desafio.enums.CommandEnum;
import br.com.idwall.desafio.utils.PropertiesUtil;

public class SendSubredditThreadMessage extends TelegramLongPollingBot {

	/**
	 * Constants can be found at properties file on the resources folder
	 */
	private static final String BOT_TOKEN = "bot.token";
	private static final String INTRO_MSG = "telegram_bot_msg.start_message";
	private static final String HELP_COMMAND_MSG = "telegram_bot_msg.help_command";
	private static final String NOT_COMMAND_MSG = "telegram_bot_msg.not_command";
	private static final String SEND_MESSAGE_ERROR = "telegram_bot_err.send_message";
	private static final String NADA_PRA_FAZER_COMMAND_NULL_PARAMS = "telegram_bot_info.nada_pra_fazer_null_params";
	private static final String PARSE_MODE = "Markdown";

	private FindSubredditThread findSubredditThread;

	public SendSubredditThreadMessage() {
		this.findSubredditThread = new IdwallFindSubredditThread();
	}

	@Override
	public void onUpdateReceived(Update update) {
		Message receivedMessage = update.getMessage();

		// If received message it's a command
		if (isCommandMessage(receivedMessage.getText())) {

			if (receivedMessage.getText().equalsIgnoreCase(CommandEnum.START.getCommand())) {

				// Simple introduction Message
				try {
					execute(new SendMessage(receivedMessage.getChatId(),
							PropertiesUtil.getBundleMessage(INTRO_MSG,
									new Object[] { receivedMessage.getFrom().getFirstName() }) + "\n\n"
									+ CommandEnum.printFormatAllComands()).setParseMode(PARSE_MODE));
				} catch (TelegramApiException e) {
					System.out.println(PropertiesUtil.getBundleMessage(SEND_MESSAGE_ERROR, new Object[] { e }));
				}
			} else if (receivedMessage.getText().equalsIgnoreCase(CommandEnum.HELP.getCommand())) {
				try {
					execute(new SendMessage(receivedMessage.getChatId(),
							PropertiesUtil.getBundleMessage(HELP_COMMAND_MSG)));
				} catch (TelegramApiException e) {
					System.out.println(PropertiesUtil.getBundleMessage(SEND_MESSAGE_ERROR, new Object[] { e }));
				}
			} else if (receivedMessage.getText().equalsIgnoreCase(CommandEnum.COMMANDS.getCommand())) {
				try {
					execute(new SendMessage(receivedMessage.getChatId(), CommandEnum.printFormatAllComands()));
				} catch (TelegramApiException e) {
					System.out.println(PropertiesUtil.getBundleMessage(SEND_MESSAGE_ERROR, new Object[] { e }));
				}
			} else if (receivedMessage.getText().toLowerCase()
					.startsWith(CommandEnum.NADA_PRA_FAZER.getCommand().toLowerCase())) {

				if (receivedMessage.getText().replace(CommandEnum.NADA_PRA_FAZER.getCommand(), "").trim()
						.length() > 0) {
					try {
						execute(new SendMessage(receivedMessage.getChatId(),
								"Ok! I'm already searching for your information.. \n\nIt may take some seconds..."));
					} catch (TelegramApiException e) {
						System.out.println(PropertiesUtil.getBundleMessage(SEND_MESSAGE_ERROR, new Object[] { e }));
					}

					String[] subredditsSplitted = receivedMessage.getText()
							.replace(CommandEnum.NADA_PRA_FAZER.getCommand(), "").trim().split(";");

					for (String subreddit : subredditsSplitted) {
						try {
							execute(new SendMessage(receivedMessage.getChatId(),
									findSubredditThread.getSubredditInfo(subreddit)));
						} catch (TelegramApiException ex) {
							System.out
									.println(PropertiesUtil.getBundleMessage(SEND_MESSAGE_ERROR, new Object[] { ex }));
						}
					}
					try {
						execute(new SendMessage(receivedMessage.getChatId(), "That's all that I've found for now! (:"));
					} catch (TelegramApiException e) {
						e.printStackTrace();
					}
				} else {
					try {
						execute(new SendMessage(receivedMessage.getChatId(),
								PropertiesUtil.getBundleMessage(NADA_PRA_FAZER_COMMAND_NULL_PARAMS))
										.setParseMode(PARSE_MODE));
					} catch (TelegramApiException e) {
						System.out.println(PropertiesUtil.getBundleMessage(SEND_MESSAGE_ERROR, new Object[] { e }));
					}
				}

			}

		} else { // If received message ins't a command
			try {
				execute(new SendMessage(receivedMessage.getChatId(), PropertiesUtil.getBundleMessage(NOT_COMMAND_MSG,
						new Object[] { receivedMessage.getFrom().getFirstName() })).setParseMode(PARSE_MODE));
			} catch (TelegramApiException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Should verify if a received message is a command
	 * 
	 * @param textMessage
	 *            the received message
	 * @return <code>true</code> if the textMessage starts with a "/" character,
	 *         <code>false</code> if not.
	 */
	public boolean isCommandMessage(String textMessage) {
		return textMessage != null && textMessage.startsWith("/") ? true : false;
	}

	@Override
	public String getBotUsername() {
		return "I think they call me bot'something else'.. I don't remember :/";
	}

	@Override
	public String getBotToken() {
		return PropertiesUtil.getBundleMessage(BOT_TOKEN);
	}
}
