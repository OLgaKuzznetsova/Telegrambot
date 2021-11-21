import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.net.MalformedURLException;

public class Main {
    public static void main(String[] args) throws MalformedURLException {
        var botLogic = new BotLogic();
        var botUsername = System.getenv("BOT_USERNAME");
        var botToken = System.getenv("BOT_TOKEN");
        var telegramBot = new TelegramBotApplication(botLogic, botUsername, botToken);
        try {
            var botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(telegramBot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
