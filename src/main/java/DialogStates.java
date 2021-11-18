import java.util.HashMap;
import java.util.Map;

public class DialogStates {
    public enum State {
        MENU(),
        SEARCH(),
    }
    private Map<String, State> stateByChatId = new HashMap<>();
    public void addChatId(String chatId) {
        stateByChatId.put(chatId, State.MENU);
    }
    public void changeState(String chatId, State state) {
        stateByChatId.replace(chatId, state);
    }
    public State getState(String chatId) {
        return stateByChatId.get(chatId);
    }
    public boolean containsChatId(String chatId) {
        return stateByChatId.containsKey(chatId);
    }


}
