import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class TelegramBotApplication extends TelegramLongPollingBot {
    private BotLogic bot;
    private String botUsername;
    private String botToken;

    public TelegramBotApplication(BotLogic bot, String botUsername, String botToken)
    {
        this.botUsername = botUsername;
        this.botToken = botToken;
        this.bot = bot;
    }

    @Override
    public void onUpdateReceived(Update update)
    {
        try
        {
            long currentChatId;
            Response response;


            if (update.hasMessage())
            {
                var message = update.getMessage();
                currentChatId = message.getChatId();

                response = bot.handleUserInput(currentChatId, message.getText());
            }
            else if (update.hasCallbackQuery())
            {
                var callbackQuery = update.getCallbackQuery();
                currentChatId = callbackQuery.getMessage().getChatId();
                response = bot.handleUserInput(currentChatId, callbackQuery.getData());
            }
            else
            {
                throw new Exception("No data found");
            }

            InlineKeyboardMarkup inlineKeyboard = null;
            if (response.getKeyboardMarkup() != null)
            {
                inlineKeyboard = new InlineKeyboardMarkup();
                inlineKeyboard.setKeyboard(setInlineKeyboardMarkup(response.getKeyboardMarkup()));
            }

            sendResponse(currentChatId, response.getMessageText(), inlineKeyboard);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void sendResponse(Long chatId, String msgText,
                              InlineKeyboardMarkup inlineKeyboard)
    {
        var sender = new SendMessage();
        sender.setChatId(chatId.toString());
        sender.setText(msgText);

        if (inlineKeyboard != null)
        {
            sender.setReplyMarkup(inlineKeyboard);
        }

        try
        {
            execute(sender);
        }
        catch (TelegramApiException e)
        {
            e.printStackTrace();
        }
    }

    private List<List<InlineKeyboardButton>> setInlineKeyboardMarkup(LinkedList<KeyboardButton> keyboardButtons)
    {
        var keyboardArray = new ArrayList<List<InlineKeyboardButton>>();

        for (var button :
                keyboardButtons)
        {
            var row = new ArrayList<InlineKeyboardButton>();
            var inlineKeyboardButton = new InlineKeyboardButton();

            inlineKeyboardButton.setText(button.getText());
            inlineKeyboardButton.setCallbackData(button.getCallbackData());

            row.add(inlineKeyboardButton);
            keyboardArray.add(row);
        }

        return keyboardArray;
    }

    @Override
    public String getBotUsername()
    {
        return botUsername;
    }

    @Override
    public String getBotToken()
    {
        return botToken;
    }
}