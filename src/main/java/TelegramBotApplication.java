import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;


public class TelegramBotApplication extends TelegramLongPollingBot {
    private BotLogic bot;
    private Information information = new Information();

    public TelegramBotApplication(BotLogic bot) {
        this.bot = bot;
    }

    @Override
    public String getBotUsername() {
        return "MathTestingBot";
    }

    @Override
    public String getBotToken() {
        return "2005218866:AAFFoIS9yGs7w2kE41r-tXyAMCGzpjsGmWQ";
    }

    @Override
    public void onUpdateReceived(Update update) {

        try {
            if (!update.hasMessage()) {
                return;
            }

            var message = update.getMessage();
            var currentChatId = message.getChatId().toString();
            if (update.getMessage().getText().equals("/start")) {
                execute(new SendMessage(currentChatId, information.getMainMenu()));
            } else {
                var response = bot.handleUserInput(currentChatId, message.getText());
                execute(new SendMessage(currentChatId, response));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}


