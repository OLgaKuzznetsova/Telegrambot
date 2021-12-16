public class KeyboardButton {
    private String text;
    private String callbackData;

    public KeyboardButton(String text, String callbackData)
    {
        this.text = text;
        this.callbackData = callbackData;
    }

    public String getText()
    {
        return text;
    }

    public String getCallbackData()
    {
        return callbackData;
    }

}