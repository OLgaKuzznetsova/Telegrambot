import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DialogStates {
    public enum States {
        MENU(2),
        LEARNING(3),
        TEST(4);

        private int value;

        States(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    private Map<String, States> dictionary = new HashMap<>();
    private Map<String, Integer> questions = new HashMap<>();

    public void addChatId(String chatId) {
        dictionary.put(chatId, States.MENU);
        questions.put(chatId, 0);
    }

    public void changeState(String chatId, States state) {
        dictionary.replace(chatId, state);
    }

    public void changeQuestion(String chatId, int number) {
        questions.replace(chatId, number);
    }

    public int getQuestion(String chatId) {
        return questions.get(chatId);
    }

    public States getState(String chatId) {
        return dictionary.get(chatId);
    }

    public boolean containsChatId(String chatId) {
        return dictionary.containsKey(chatId);
    }



}
