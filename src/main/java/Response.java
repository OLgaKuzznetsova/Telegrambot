
import java.util.LinkedList;

public class Response
{
    private String messageText;
    private LinkedList<KeyboardButton> keyboardMarkup;

    public Response(String messageText, LinkedList<KeyboardButton> keyboardMarkup)
    {
        this.messageText = messageText;
        this.keyboardMarkup = keyboardMarkup;
    }

    public String getMessageText()
    {
        return messageText;
    }

    public LinkedList<KeyboardButton> getKeyboardMarkup()
    {
        return keyboardMarkup;
    }

}

