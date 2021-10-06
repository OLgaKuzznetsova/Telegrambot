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


    private Map<Integer, States> dictionary = new HashMap<>() {
    };

    public void addChatId(int chatId) {
        dictionary.put(chatId, States.MENU);
    }

    public void changeState(int chatId, States state) {
        dictionary.replace(chatId, state);
    }

    public States getState(int chatId) {
        return dictionary.get(chatId);
    }

    public boolean containsChatId(int chatId) {
        return dictionary.containsKey(chatId);
    }


}
