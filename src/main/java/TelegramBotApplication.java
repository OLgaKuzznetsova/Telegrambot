import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;


public class TelegramBotApplication extends TelegramLongPollingBot {
    private BotLogic bot;
    private InlineKeyboard inlineKeyboard;
    String botUsername;
    String botToken;

    public TelegramBotApplication(BotLogic bot, InlineKeyboard inlineKeyboard, String botUsername, String botToken) {
        this.botUsername = botUsername;
        this.inlineKeyboard = inlineKeyboard;
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
        if (update.hasMessage() && update.getMessage().hasText()) {
            var chat_id = update.getMessage().getChatId();

            Response response = bot.handleUserInput(chat_id.toString(), update.getMessage().getText());
            String message_text = response.getMessage();
            String indicator = response.getIndicator();
            if (indicator.equals("")) {
                sendMessage(message_text, chat_id.toString());
            }
            else {
                setInlineKeyboard(chat_id, message_text, indicator);
            }
        }
        else if (update.hasCallbackQuery()) {
            var chat_id = update.getCallbackQuery().getMessage().getChatId();
            sendCallback(update, chat_id);
        }
    }


    public void sendMessage(String message, String currentChatId) {
        try {
            execute(new SendMessage(currentChatId, message));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setInlineKeyboard(Long chat_id, String message_text, String indicator) {

        SendMessage message = new SendMessage();
        message.setChatId(chat_id.toString());
        message.setText(message_text);
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();

        var textForButtons = inlineKeyboard.chooseTextForButton(indicator);
        for (var button: textForButtons) {
            var inline = new InlineKeyboardButton();
            inline.setText(button);
            var callbackData = inlineKeyboard.setCallbackData(button);
            inline.setCallbackData(callbackData);
            rowInline.add(inline);
        }
        rowsInline.add(rowInline);
        markupInline.setKeyboard(rowsInline);
        message.setReplyMarkup(markupInline);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }

    public void sendCallback(Update update, Long chat_id) {

        var call_data = update.getCallbackQuery().getData();
        var message_id = update.getCallbackQuery().getMessage().getMessageId();

        Response response_answer = bot.handleUserInput(chat_id.toString(), call_data);
        String answer = response_answer.getMessage();

        if (call_data.equals("see")){
            answer = "Ответ:";
            EditMessageText new_message = new EditMessageText();
            new_message.setChatId(chat_id.toString());
            new_message.setMessageId(message_id);
            new_message.setText(answer);
            try {
                execute(new_message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
            setInlineKeyboard(chat_id, answer, response_answer.getIndicator());
        }
        else if (call_data.equals("yes_i_know")){
            var response = bot.handleUserInput(chat_id.toString(), "да");
            answer = response.getMessage();
            sendMessage(response.getMessage(), chat_id.toString());
        }
        else if (call_data.equals("no_i_don't")) {
            var response = bot.handleUserInput(chat_id.toString(), "нет");
            answer = response.getMessage();
            sendMessage(response.getMessage(), chat_id.toString());
        }
        else if (call_data.equals("yes")){
            var response = bot.handleUserInput(chat_id.toString(), "да");
            answer = "Бот смог вам помочь!";
            sendMessage(response.getMessage(), chat_id.toString());
        }
        else if (call_data.equals("no")){
            var response = bot.handleUserInput(chat_id.toString(), "нет");
            answer = "Бот не нашёл похожего термина";
        }
        EditMessageText new_message = new EditMessageText();
        new_message.setChatId(chat_id.toString());
        new_message.setMessageId(message_id);
        new_message.setText(answer);
        try {
            execute(new_message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }

}