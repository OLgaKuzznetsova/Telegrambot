import java.util.HashMap;
import java.util.Map;

public class DialogStates {
    public enum State {
        MENU(),
        LEARNING(),
        TEST()
    }

    private Map<String, State> stateByChatId = new HashMap<>();
    private Map<String, Integer> lastQuestionIndexByChatId = new HashMap<>();

    public void addChatId(String chatId) {
        stateByChatId.put(chatId, State.MENU);
        lastQuestionIndexByChatId.put(chatId, 0);
    }

    public void changeState(String chatId, State state) {
        stateByChatId.replace(chatId, state);
    }

    public void changeQuestion(String chatId, int number) {
        lastQuestionIndexByChatId.replace(chatId, number);
    }

    public int getQuestion(String chatId) {
        return lastQuestionIndexByChatId.get(chatId);
    }

    public State getState(String chatId) {
        return stateByChatId.get(chatId);
    }

    public boolean containsChatId(String chatId) {
        return stateByChatId.containsKey(chatId);
    }


}
