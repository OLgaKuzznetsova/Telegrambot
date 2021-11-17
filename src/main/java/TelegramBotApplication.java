import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;


public class TelegramBotApplication extends TelegramLongPollingBot {
    private BotLogic bot;
    private TermRepository termRepository = new TermRepository();

    public TelegramBotApplication(BotLogic bot) {
        this.bot = bot;
    }

    @Override
    public String getBotUsername() {
        return System.getenv("BOT_USERNAME");
    }

    @Override
    public String getBotToken() {
        return System.getenv("BOT_TOKEN");
    }

    @Override
    public void onUpdateReceived(Update update) {

        try {
            if (!update.hasMessage()) {
                return;
            }

            var message = update.getMessage();
            var currentChatId = message.getChatId().toString();
            var response = bot.handleUserInput(currentChatId, message.getText());
            execute(new SendMessage(currentChatId, response));


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}


