import java.util.ArrayList;
import java.util.HashMap;

public class BotLogic {

    private DialogStates dialogStates = new DialogStates();

    private int[] states = new int[]{};
    //private String userInput;
    private Information information = new Information();
    private ArrayList<Data> data = information.getData();
    private int counter = 0;
    public HashMap<Integer, int[]> dictionary = new HashMap<>();

    public String handleUserInput(int chatId, String userInput) {
        if (!dialogStates.containsChatId(chatId)){
            dialogStates.addChatId(chatId);
        }
//        if (states[0] == -1) {
//            states[0] = 0;
//            return information.getMainMenu();
//        }
        if (userInput.equalsIgnoreCase("Определения")) {
            dialogStates.changeState(chatId, DialogStates.States.LEARNING);
            return information.getMenu1();
        }
        if (userInput.equalsIgnoreCase("Тест")) {
            dialogStates.changeState(chatId, DialogStates.States.TEST);
            return information.getMenu2();
        }
        if (userInput.equalsIgnoreCase("Меню")) {
            dialogStates.changeState(chatId, DialogStates.States.MENU);
            return information.getMenu();
        }
        if (dialogStates.getState(chatId) == DialogStates.States.LEARNING)
            return optionOne(userInput);

//        if (dialogStates.getState(chatId) == DialogStates.States.TEST)
//            return optionTwo(userInput);

        return "Данные введены неверно";
    }

    public String optionOne(String userInput) {
        for (int i = 0; i < data.size(); i++) {
            if (userInput.equalsIgnoreCase(data.get(i).getTerm())) {
                return data.get(i).getTerm() + "-" + data.get(i).getDefinition();
            }
        }
        return "Нет такого определения.";
    }

//    public String optionTwo(String userInput) {
//        String answer = "";
//        if (states[1] == 0) {
//            states[1] = 1;
//            answer = words[counter];
//            return definition[counter] + "\n";
//        }
//        if (states[1] == 1) {
//            states[1] = 0;
//            if (words.length - 1 == counter) {
//                counter = 0;
//            } else {
//                counter++;
//            }
//            if (userInput.equalsIgnoreCase(answer)) {
//                return "Правильный ответ";
//            } else {
//                return ("Неправильный ответ! Правильный ответ - " + answer);
//
//            }
//        }
//        return "";
//    }
}