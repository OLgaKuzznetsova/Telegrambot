import java.util.HashMap;
import java.util.Map;

public class ChatStateRepository {
    public enum State {
        SEARCH,
        ERROR,
        LEARN,
        NOT_LEARN,
        NOT_USE,
        CHECK,
        SMTH

    }

    private Map<String, State> stateByChatId = new HashMap<>();

    private Map<String, String> lastAnswerByChatId = new HashMap<>();

    private Map<String, Map<String, State>> historyOfTermsByChatId = new HashMap<>();


    public void addChatIdForHistory(String chatId) {historyOfTermsByChatId.put(chatId, new HashMap<>());}

    public void changeStateToTerm(String chatId, String term, State state) {historyOfTermsByChatId.get(chatId).put(term, state);}

    public State getStateToTerm(String chatId, String term) {return historyOfTermsByChatId.get(chatId).get(term);}

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

    public void addLastAnswerByChatId(String chatId, String intendedAnswer) {
        lastAnswerByChatId.put(chatId, intendedAnswer);
    }

    public String getLastAnswer(String chatId) {
        return lastAnswerByChatId.get(chatId);
    }

    public boolean containsAnswerByChatId(String chatId) {
        return lastAnswerByChatId.containsKey(chatId);
    }

    public void deleteLastAnswerByChatId(String chatId) {
        lastAnswerByChatId.remove(chatId);
    }


}
