import java.util.ArrayList;

public class BotLogic {
    private int[] states = new int[]{-1};
    //private String userInput;
    private Information information = new Information();
    private ArrayList<Data> data = information.getData();
    private int counter = 0;

    public String handleUserInput(int chatId, String userInput) {
        if (states[0] == -1) {
            states[0] = 0;
            return information.getMainMenu();
        }
        if (userInput.equalsIgnoreCase("Определения")) {
            states[0] = 1;
            return information.getMenu1();
        }
        //if (userInput.equalsIgnoreCase("Тест")) {
        //states[0] = 2;
        //return data.getMenu2();
        //}
        if (userInput.equalsIgnoreCase("Меню")) {
            states[0] = 0;
            return information.getMenu();
        }
        if (states[0] == 1)
            return optionOne(userInput);
        //if (states[0] == 2)
        //return optionTwo(userInput);

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
//fjfjfjfjfj
    /*public String optionTwo(String userInput) {
        String answer = "";
        if (states[1] == 0) {
            states[1] = 1;
            answer = words[counter];
            return definition[counter] + "\n";
        }
        if (states[1] == 1) {
            states[1] = 0;
            if (words.length - 1 == counter) {
                counter = 0;
            } else {
                counter++;
            }
            if (userInput.equalsIgnoreCase(answer)) {
                return "Правильный ответ";
            } else {
                return ("Неправильный ответ! Правильный ответ - " + answer);

            }
        }
        return "";
    }*/
}