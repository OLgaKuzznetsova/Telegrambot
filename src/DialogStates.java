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
    }
    private int chatId = 0;
    public HashMap<Integer, int[]> dictionary = new HashMap<>();


}
