import java.util.HashMap;
import java.util.Map;

public class ChatStateRepository {
    public enum UserState {
        SEARCH,
        ERROR,
        CHECK,
        POLL
    }
    public enum TermState {
        LEARNED,
        UNLEARNED,
        UNUSED
    }

    private Map<Long, UserState> stateByChatId = new HashMap<Long, UserState>();

    private Map<Long, String> lastAnswerByChatId = new HashMap<Long, String>();

    private Map<Long, HashMap<Object, Object>> historyOfTermsByChatId = new HashMap<Long, HashMap<Object, Object>>();


    public void addChatIdForHistory(long chatId) {
        historyOfTermsByChatId.put(chatId, new HashMap<>());
    }

    public void changeStateForTerm(long chatId, String term, TermState state) {
        historyOfTermsByChatId.get(chatId).put(term, state);
    }

    public Object getStateForTerm(long chatId, String term) {
        return historyOfTermsByChatId.get(chatId).get(term);
    }

    public void addChatId(long chatId) {
        stateByChatId.put(chatId, UserState.SEARCH);
    }

    public void changeState(long chatId, UserState state) {
        stateByChatId.replace(chatId, state);
    }

    public UserState getState(long chatId) {
        return stateByChatId.get(chatId);
    }

    public boolean containsChatId(long chatId) {
        return stateByChatId.containsKey(chatId);
    }

    public void lastUsedTerm(long chatId, String intendedAnswer) {
        lastAnswerByChatId.put(chatId, intendedAnswer);
    }

    public String getLastAnswer(long chatId) {
        return lastAnswerByChatId.get(chatId);
    }

    public boolean containsAnswerByChatId(long chatId) {
        return lastAnswerByChatId.containsKey(chatId);
    }

    public void deleteLastAnswerByChatId(long chatId) {
        lastAnswerByChatId.remove(chatId);
    }


}
