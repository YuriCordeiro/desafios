package br.com.idwall.desafio.telegram;

import java.io.FileInputStream;
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

public class SendSubredditThreadsMessages extends TelegramLongPollingBot {

	private static final String BOT_TOKEN = "BOT_TOKEN";
	private static final String BASE_COMMAND = "/NadaPraFazer ";

	@Override
	public void onUpdateReceived(Update update) {
		String command = update.getMessage().getText();

		SendMessage sendMessage = new SendMessage();
		StringBuilder sbThread = new StringBuilder();

		if (command != null && command.toLowerCase().startsWith(BASE_COMMAND.toLowerCase())) {
			Map<String, List<SubredditThread>> resultMap = FindSubredditThreadsTelegram
					.run(command.replace(BASE_COMMAND, "").split(";"));

			for (String key : resultMap.keySet()) {
				int countThread = 1;
				sbThread.append("|     The top threads for *" + key.toUpperCase() + "* subreddit     |");
				for (Entry<String, List<SubredditThread>> entry : resultMap.entrySet()) {
					entry.getValue().stream()
							.forEach(threadObject -> sbThread.append(countThread + ".   *Thread Title*: "
									+ threadObject.getTitle() + "\n\t*Thread Upvotes*: " + threadObject.getUpVotes()
									+ "   *Thread Link*: [" + threadObject.getThreadLink() + "]"
									+ "   *Comments Link*: [" + threadObject.getCommentsLink() + "]\n\n"));
					countThread++;
				}
				sbThread.append("|     END OF *" + key.toUpperCase() + "* subreddit threads     |");
			}

		}

		sendMessage.setText(sbThread.toString()).setParseMode("Markdown");
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
		return "";
	}

	@Override
	public String getBotToken() {
		try {
			return getProp().get(BOT_TOKEN).toString();
		} catch (IOException e) {
			System.out.println("Propeties file not found. \n" + e);
		}
		return "";
	}

	public static Properties getProp() throws IOException {
		Properties props = new Properties();
		FileInputStream file = new FileInputStream("./properties/config.properties");
		props.load(file);
		return props;

	}
}
