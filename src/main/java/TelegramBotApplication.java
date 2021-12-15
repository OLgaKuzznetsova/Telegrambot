import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
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
    public BotApiMethod<?> hadleUpdate(Update update){
        SendMessage replyMessage = null;
        if (update.hasCallbackQuery()){
            CallbackQuery callbackQuery = update.getCallbackQuery();
        }
        return null;
    }



    private InlineKeyboardMarkup getInlineMessageButtons(){
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton buttonYes;
        buttonYes = new InlineKeyboardButton().setText("Да");
        InlineKeyboardButton buttonNo = new InlineKeyboardButton().setText("Нет");

        buttonYes.setCallbackData("buttonYes");
        buttonNo.setCallbackData("buttonNo");

        List<InlineKeyboardButton> keyboardButtons = new ArrayList<>();
        keyboardButtons.add(buttonYes);
        keyboardButtons.add(buttonNo);
        List<List<InlineKeyboardButton>> rowlist = new ArrayList<>();
        rowlist.add(keyboardButtons);

        inlineKeyboardMarkup.setKeyboard(rowlist);

        return inlineKeyboardMarkup;
    }
}