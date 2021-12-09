import java.util.ArrayList;

public class InlineKeyboard {

    public ArrayList<String> chooseTextForButton(String indicator) {
        if (indicator.equals("poll")) {
            ArrayList<String> polling = new ArrayList<>();
            polling.add("да, конечно");
            polling.add("нет");
            return polling;

        }
        if (indicator.equals("flashcard")) {
            ArrayList<String> turning = new ArrayList<>();
            turning.add("посмотреть ответ");
            return turning;
        }
        if (indicator.equals("confirm")) {
            ArrayList<String> confirming = new ArrayList<>();
            confirming.add("да");
            confirming.add("нет, совсем не это");
            return confirming;
        }
        return new ArrayList<>();
    }

    public String setCallbackData(String text) {
        if (text.equals("посмотреть ответ")){
            return "see";
        }
        if (text.equals("да, конечно")) {
            return "yes_i_know";
        }
        if (text.equals("нет")) {
            return "no_i_don't";
        }
        if (text.equals("да")){
            return "yes";
        }
        if (text.equals("нет, совсем не это")){
            return "no";
        }
        return "";
    }

}
