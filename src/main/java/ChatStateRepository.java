import java.util.HashMap;
import java.util.Map;

public class ChatStateRepository {
    public enum State {
        SEARCH,
        ERROR
    }

    private Map<String, State> stateByChatId = new HashMap<>();

    private Map<String, String> intendedAnswerByChatId = new HashMap<>();

    public void addChatId(String chatId) {
        stateByChatId.put(chatId, State.SEARCH);
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

    public void addIntendedAnswerByChatId(String chatId, String intendedAnswer) {
        intendedAnswerByChatId.put(chatId, intendedAnswer);
    }

    public String getIntendedAnswer(String chatId) {
        return intendedAnswerByChatId.get(chatId);
    }

    public boolean containsAnswerByChatId(String chatId) {
        return intendedAnswerByChatId.containsKey(chatId);
    }

    public void deleteAnswerByChatId(String chatId) {
        intendedAnswerByChatId.remove(chatId);
    }


}
