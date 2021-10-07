import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class TelegramBotApplication extends TelegramLongPollingBot {
    private BotLogic bot;
    public TelegramBotApplication(BotLogic bot)
    {
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

        try
        {
            if (!update.hasMessage())
            {
                return;
            }

            var message = update.getMessage();
            var currentChatId = message.getChatId().toString();
            var response = bot.handleUserInput(currentChatId, message.toString());


            sendResponse(currentChatId, response);
        } catch (Exception e)
        {
            e.printStackTrace();
        }

    }
    private void sendResponse(String chatId, String msgText)
    {
        var sender = new SendMessage();
        sender.setChatId(chatId);
        sender.setText(msgText);

        try
        {
            execute(sender);
        } catch (TelegramApiException e)
        {
            e.printStackTrace();
        }
    }
}


