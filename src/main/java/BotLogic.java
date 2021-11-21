import java.util.ArrayList;

public class BotLogic {
    private DialogStates dialogStates = new DialogStates();
    private TermRepository termRepository = new TermRepository();
    private ArrayList<TermDefinition> termDefinition;
    private ErrorSearch errorSearch = new ErrorSearch();

    public String handleUserInput(String chatId, String userInput) {
        termDefinition = termRepository.getData();
        if (userInput.equals("/start")) {
            return mainMenu();
        }
        if (!dialogStates.containsChatId(chatId)) {
            dialogStates.addChatId(chatId);
        }
        if (userInput.equalsIgnoreCase("Термины")) {
            dialogStates.changeState(chatId, DialogStates.State.MENU);
        }
        if (userInput.equalsIgnoreCase("Поиск")) {
            dialogStates.changeState(chatId, DialogStates.State.SEARCH);
        }
        if (userInput.equalsIgnoreCase("Меню")) {
            dialogStates.changeState(chatId, DialogStates.State.MENU);
        }

        if (dialogStates.getState(chatId) == DialogStates.State.SEARCH) {
            return modeForDefiningTerms(userInput);
        }
        return "Данные введены неверно";
    }

    private String modeForSearchSimilarWords(String userInput) {
        var message = new StringBuilder("Мы не нашли такого определения. Возможно вы имели ввиду одно из этих?\n\n");

        for (var term : errorSearch.getSimilarTerms(userInput)) {

            message.append(term + ", ");
        }
        return message.toString();
    }

    private String modeForDefiningTerms(String userInput) {
        for (int i = 0; i < termDefinition.size(); i++) {
            if (userInput.equalsIgnoreCase(termDefinition.get(i).getTerm())) {
                return termDefinition.get(i).getTerm() + " - " + termDefinition.get(i).getDefinition(false);
            }
        }
        return modeForSearchSimilarWords(userInput);
    }

    private String menuForBotDescription = "Вас приветствует бот по поиску определений по математике. \n" +
            "Он поможет вам найти нужное определение определения.\n";

    private String menuToSelectTheMode = "Отправьте:\n" +

            "Поиск - если хотите найти определения \n" +
            "Меню - если хотите вернуться обратно в меню\n" +
            "Термины - если хотите узнать всю базу терминов нашего бота";

    private String mainMenu() {
        return menuForBotDescription + menuToSelectTheMode;
    }

    public String menuForDisplayingTerms() {
        var message = new StringBuilder();
        for (int i = 0; i < termDefinition.size(); i++) {
            message.append(termDefinition.get(i).getTerm() + ", ");
        }
        return message.toString();
    }
}