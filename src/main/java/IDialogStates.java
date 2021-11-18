import java.util.HashMap;
import java.util.Map;

public interface IDialogStates {
    public enum State {
        MENU(),
        SEARCH(),
    }

    Map<String, State> stateByChatId = new HashMap<>();

    static void addChatId(String chatId) {
        stateByChatId.put(chatId, State.MENU);
    }

    static void changeState(String chatId, State state) {
        stateByChatId.replace(chatId, state);
    }

    static State getState(String chatId) {
        return stateByChatId.get(chatId);
    }

    static boolean containsChatId(String chatId) {
        return stateByChatId.containsKey(chatId);
    }


}
