import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;


public class TelegramBotApplication extends TelegramLongPollingBot {
    private BotLogic bot;
    String botUsername;
    String botToken;

    public TelegramBotApplication(BotLogic bot, String botUsername, String botToken) {
        this.botUsername = botUsername;
        this.botToken = botToken;
        this.bot = bot;
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
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