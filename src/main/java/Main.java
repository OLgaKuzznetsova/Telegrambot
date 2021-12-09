import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws MalformedURLException {
        URL google_sheets_repo_url = new URL(System.getenv("GOOGLE_SHEETS_REPO_URL"));
        var googleSheetsRepo = new GoogleSheetsRepo(google_sheets_repo_url);

        Map<String, String> dataFromCsvFile = googleSheetsRepo.getDataFromCSVFile();
        var errorSearch = new ErrorSearch();

        var termRepository = new TermRepository(errorSearch, dataFromCsvFile);
        var dialogStates = new ChatStateRepository();

        var botLogic = new BotLogic(dialogStates, termRepository);
        var botUsername = System.getenv("BOT_USERNAME");
        var botToken = System.getenv("BOT_TOKEN");
        var inlineKeyboard = new InlineKeyboard();

        var telegramBot = new TelegramBotApplication(botLogic, inlineKeyboard, botUsername, botToken);
        try {
            var botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(telegramBot);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }

    }
}
