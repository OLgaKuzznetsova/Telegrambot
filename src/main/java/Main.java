
import java.util.Scanner;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Main {
    private  int chatId = 0;
    //private BotLogic botLogic = new BotLogic();
    private Information information = new Information();


    public static void main(String[] args) {
        BotLogic botLogic = new BotLogic();
        var telegramBot = new TelegramBotApplication(botLogic);

        //Main main1 = new Main();
        //Scanner input = new Scanner(System.in);
        //System.out.println(main1.information.getMainMenu());
        //System.out.println(main1.botLogic.handleUserInput(main1.chatId, ""));
        try {
            var botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(telegramBot);
        }
        catch (TelegramApiException e)
        {
            e.printStackTrace();
        }
//        while (true) {
//            var userInput = input.nextLine();
//            var reply = main1.botLogic.handleUserInput(main1.getChatId(), userInput);
//            System.out.println(reply);
//        }
    }

    private int getChatId()
    {
        return chatId;
    }
}
