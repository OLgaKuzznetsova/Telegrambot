import org.apache.commons.collections.map.HashedMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ButtonRepository {
    private Map<String, Map<Integer, String>> buttonsByChatId = new HashedMap();



    public void addChatIdForHistory(String chatId) {
        buttonsByChatId.put(chatId, new HashMap<>());
    }

    public void putButtonForChatID(String chatId, Integer number, String name) {
        buttonsByChatId.get(chatId).put(number, name);
    }

    public String getStateForTerm(String chatId, Integer number) {
        return buttonsByChatId.get(chatId).get(number);
    }
}
