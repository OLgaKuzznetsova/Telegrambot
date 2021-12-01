import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.ArrayList;
import java.util.Map;

public class Main {
    public static void main(String[] args) {

        String google_sheets_repo_url = "https://docs.google.com/spreadsheets/d/e/2PACX-1vR_HwF6CXNajSikw_pLaDhXmCNlQiXZlm6lhvQNR5NF0uxInHEVfZ-utVoKnOJEV6bATE8XfebcRpO2/pub?output=csv";
        var googleSheetsRepo = new GoogleSheetsRepo(google_sheets_repo_url);

        Map<String, String> dataFromCsvFile = googleSheetsRepo.getDataFromCSV();
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
            e.printStackTrace();
        }

    }
}
