import java.util.HashMap;
import java.util.Map;

public class DialogStates {
    public enum State {
        MENU(),
        LEARNING(),
        TEST()
    }

    private Map<String, State> lastQuestionIndexByChatId = new HashMap<>();
    private Map<String, Integer> stateByChatId = new HashMap<>();

    public void addChatId(String chatId) {
        lastQuestionIndexByChatId.put(chatId, State.MENU);
        stateByChatId.put(chatId, 0);
    }

    public void changeState(String chatId, State state) {
        lastQuestionIndexByChatId.replace(chatId, state);
    }

    public void changeQuestion(String chatId, int number) {
        stateByChatId.replace(chatId, number);
    }

    public int getQuestion(String chatId) {
        return stateByChatId.get(chatId);
    }

    public State getState(String chatId) {
        return lastQuestionIndexByChatId.get(chatId);
    }

    public boolean containsChatId(String chatId) {
        return lastQuestionIndexByChatId.containsKey(chatId);
    }


}
