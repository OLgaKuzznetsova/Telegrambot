import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.ArrayList;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        String google_sheets_repo_url = System.getenv("GOOGLE_SHEETS_REPO_URL");
        var googleSheetsRepo = new GoogleSheetsRepo(google_sheets_repo_url);

        Map<String, String> dataFromCsvFile = googleSheetsRepo.getDataFromCSVFile();
        ArrayList<TermDefinition> termDefinition = new ArrayList<>();
        var errorSearch = new ErrorSearch();

        var termRepository = new TermRepository(errorSearch, termDefinition, dataFromCsvFile);
        var dialogStates = new ChatStateRepository();

        var botLogic = new BotLogic(dialogStates, termRepository);
        var botUsername = System.getenv("BOT_USERNAME");
        var botToken = System.getenv("BOT_TOKEN");

        var telegramBot = new TelegramBotApplication(botLogic, botUsername, botToken);
        try {
            var botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(telegramBot);
        } catch (TelegramApiException e) {
            new RuntimeException(e);
        }

    }
}
