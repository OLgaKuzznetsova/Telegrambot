import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Main {
    public static void main(String[] args) {


        var botLogic = new IBotLogic(){
            @Override
            public String handleUserInput(String chatId, String userInput) {
                return IBotLogic.super.handleUserInput(chatId, userInput);
            }
        };


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
