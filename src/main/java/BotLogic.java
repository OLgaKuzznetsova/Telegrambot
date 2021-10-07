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

    public String handleUserInput(String chatId, String userInput) {
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
            return information.getMenu2() + "\n" + "\n" + data.get(dialogStates.getQuestion(chatId)).getDefinition();
        }
        if (userInput.equalsIgnoreCase("Меню")) {
            dialogStates.changeState(chatId, DialogStates.States.MENU);
            return information.getMenu();
        }
        if (dialogStates.getState(chatId) == DialogStates.States.LEARNING)
            return optionOne(userInput);

        if (dialogStates.getState(chatId) == DialogStates.States.TEST)
            return optionTwo(userInput, chatId);

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

    public String optionTwo(String userInput, String chatId) {
        String answer = data.get(dialogStates.getQuestion(chatId)).getTerm();
        String question = data.get(dialogStates.getQuestion(chatId)).getDefinition();
        if (userInput.equalsIgnoreCase(answer)){
            if (dialogStates.getQuestion(chatId) == data.size() - 1)
                dialogStates.changeQuestion(chatId, 0);
            else
                dialogStates.changeQuestion(chatId, dialogStates.getQuestion(chatId) + 1);
            return "Правильный ответ \n" + "\n Следующее определение:" +  data.get(dialogStates.getQuestion(chatId)).getDefinition();
        }
        else
        {
            if (dialogStates.getQuestion(chatId) == data.size() - 1)
                dialogStates.changeQuestion(chatId, 0);
            else
                dialogStates.changeQuestion(chatId, dialogStates.getQuestion(chatId) + 1);
            return ("Неправильный ответ! \nПравильный ответ: " + answer +  "\n"  + "\n Следующее определение: " +  data.get(dialogStates.getQuestion(chatId)).getDefinition());
        }

    }
}