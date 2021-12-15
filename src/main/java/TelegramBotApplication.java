import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;


public class TelegramBotApplication extends TelegramLongPollingBot {
    private ButtonRepository buttonRepository = new ButtonRepository();
    private ChatStateRepository chatStateRepository;
    private BotLogic bot;
    private InlineKeyboard inlineKeyboard;
    String botUsername;
    String botToken;
    public TelegramBotApplication(BotLogic bot, InlineKeyboard inlineKeyboard, String botUsername, String botToken, ChatStateRepository chatStateRepository) {
        this.botUsername = botUsername;
        this.inlineKeyboard = inlineKeyboard;
        this.botToken = botToken;
        this.bot = bot;
        this.chatStateRepository = chatStateRepository;
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
            Message message;
            String currentChatId = "";
            String messageStr = "";
            if (update.hasCallbackQuery()){
                var call_data = update.getCallbackQuery().getData();
                messageStr = call_data;
                currentChatId = update.getCallbackQuery().getMessage().getChatId().toString();
            }
            if (update.hasMessage()) {
                message = update.getMessage();
                currentChatId = message.getChatId().toString();
                messageStr = message.getText();
            }
            if (update.hasCallbackQuery() | update.hasMessage()){
                var response = bot.handleUserInput(currentChatId, messageStr);

                if (chatStateRepository.getStateNeedButton(currentChatId) == ChatStateRepository.State.NO) {
                    execute(new SendMessage(currentChatId, response));
                }
                if (chatStateRepository.getStateNeedButton(currentChatId) == ChatStateRepository.State.YES) {
                    SendMessage messages = new SendMessage();
                    messages.setChatId(currentChatId);
                    messages.setText(response);

                    String button1 = buttonRepository.getStateForTerm(currentChatId, 0);
                    String button2 = buttonRepository.getStateForTerm(currentChatId, 1);
                    messages.setReplyMarkup(getInlineMessageButtons(button1, button2));
                    execute(messages);

                }

            }




        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    private InlineKeyboardMarkup getInlineMessageButtons(String name1, String name2){
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();


        InlineKeyboardButton buttonYes =  new InlineKeyboardButton();
        InlineKeyboardButton buttonNo = new InlineKeyboardButton();
        buttonYes.setText(name1);
        buttonNo.setText(name2);
        buttonYes.setCallbackData("Да");
        buttonNo.setCallbackData("Нет");

        List<InlineKeyboardButton> keyboardButtons = new ArrayList<>();
        keyboardButtons.add(buttonYes);
        keyboardButtons.add(buttonNo);
        List<List<InlineKeyboardButton>> rowlist = new ArrayList<>();
        rowlist.add(keyboardButtons);

        inlineKeyboardMarkup.setKeyboard(rowlist);

        return inlineKeyboardMarkup;
    }
}