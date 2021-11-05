import java.util.ArrayList;

public class BotLogic {
    private DialogStates dialogStates = new DialogStates();
    private TermRepository termRepository = new TermRepository();
    private ArrayList<TermDefinition> data = termRepository.getData();

    public String handleUserInput(String chatId, String userInput) {
        if (userInput.equals("/start")) {
            return mainMenu();
        }
        if (!dialogStates.containsChatId(chatId)) {
            dialogStates.addChatId(chatId);
        }
        if (userInput.equalsIgnoreCase("Определения")) {
            dialogStates.changeState(chatId, DialogStates.State.LEARNING);
            return menuForDisplayingDefinitions();
        }
        if (userInput.equalsIgnoreCase("Тест")) {
            dialogStates.changeState(chatId, DialogStates.State.TEST);
            return menuForDefiningTerms + "\n" + "\n" + data.get(dialogStates.getQuestion(chatId)).getDefinition(true);
        }
        if (userInput.equalsIgnoreCase("Меню")) {
            dialogStates.changeState(chatId, DialogStates.State.MENU);
            return menuToSelectTheMode;
        }
        if (dialogStates.getState(chatId) == DialogStates.State.LEARNING)
            return modeForDefiningTerms(userInput);

        if (dialogStates.getState(chatId) == DialogStates.State.TEST)
            return modeForCheckingTheEnteredTerm(userInput, chatId);

        return "Данные введены неверно";
    }

    private String modeForDefiningTerms(String userInput) {
        for (int i = 0; i < data.size(); i++) {
            if (userInput.equalsIgnoreCase(data.get(i).getTerm())) {
                return data.get(i).getTerm() + " - " + data.get(i).getDefinition(false);
            }
        }
        return "Нет такого определения.";
    }

    private String modeForCheckingTheEnteredTerm(String userInput, String chatId) {
        String answer = data.get(dialogStates.getQuestion(chatId)).getTerm();

        if (userInput.equalsIgnoreCase(answer)) {
            updateQuestionNumber(chatId);
            return "Правильный ответ \n" + "\nСледующее определение: " + data.get(dialogStates.getQuestion(chatId)).getDefinition(false);
        } else {
            updateQuestionNumber(chatId);
            return ("Неправильный ответ! \nПравильный ответ: " + answer + "\n"
                    + "\n Следующее определение: " + data.get(dialogStates.getQuestion(chatId)).getDefinition(false));
        }

    }
    private void updateQuestionNumber(String chatId)
    {
        if (dialogStates.getQuestion(chatId) == data.size() - 1)
            dialogStates.changeQuestion(chatId, 0);
        else
            dialogStates.changeQuestion(chatId, dialogStates.getQuestion(chatId) + 1);
    }

    private String menuForBotDescription = "Вас приветствует бот по подготовке к экзамену по математике. \n" +
            "Он поможет вам выучить определения.\n";

    private String menuToSelectTheMode = "Отправьте:\n" +
            "Определения - если хотите выучить определения \n" +
            "Тест - если хотите пройти тест \n" +
            "Меню - если хотите вернуться обратно в меню\n";

    private String menuForDefiningTerms = "Сейчас Вам будет показано определение.\n" +
            "Напишите понятие, к которому оно относится.";

    private String mainMenu() { return menuForBotDescription + menuToSelectTheMode; }

    private String menuForDisplayingDefinitions() {
        var message = new StringBuilder("Введите понятие, определение которого вы хотите узнать\n");
        for (int i = 0; i < data.size(); i++) {
            message.append(data.get(i).getTerm() + "\n");
        }
        return message.toString();
    }
}