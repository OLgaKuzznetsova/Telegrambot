import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class Main {
    public static void main(String[] args) throws GeneralSecurityException, IOException {
        var botLogic = new BotLogic();
        var telegramBot = new TelegramBotApplication(botLogic);

        try {
            var botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(telegramBot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        var ex = new Exel();
        System.out.println(ex.readFromExcel());
        var a = new ErrorSearch();
        System.out.println(a.getTheNumberOfTermMismatches("Форм"));



    }

}
