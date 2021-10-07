import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DialogStates {
    public enum States {
        CURRENT(0),
        QUESTION(1),
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


    private Map<Integer, States> dictionary = new HashMap<>();
    private Map<Integer, Integer> questions = new HashMap<>();

    public void addChatId(int chatId) {
        dictionary.put(chatId, States.MENU);
        questions.put(chatId, 0);
    }

    public void changeState(int chatId, States state) {
        dictionary.replace(chatId, state);
    }

    public void changeQuestion(int chatId, int number) {
        questions.replace(chatId, number);
    }

    public int getQuestion(int chatId) {
        return questions.get(chatId);
    }

    public States getState(int chatId) {
        return dictionary.get(chatId);
    }

    public boolean containsChatId(int chatId) {
        return dictionary.containsKey(chatId);
    }



}
