import java.util.ArrayList;

public interface IBotLogic extends IDialogStates, IErrorSearch {
    TermRepository termRepository = new TermRepository();

    default String handleUserInput(String chatId, String userInput) {
        if (userInput.equals("/start")) {
            return mainMenu();
        }
        if (!IDialogStates.containsChatId(chatId)) {
            IDialogStates.addChatId(chatId);
        }
        if (userInput.equalsIgnoreCase("Термины")) {
            IDialogStates.changeState(chatId, IDialogStates.State.MENU);
            return menuForDisplayingTerms();
        }
        if (userInput.equalsIgnoreCase("Поиск")) {
            IDialogStates.changeState(chatId, IDialogStates.State.SEARCH);
            return "Введите термин";
        }
        if (userInput.equalsIgnoreCase("Меню")) {
            IDialogStates.changeState(chatId, IDialogStates.State.MENU);
            return menuToSelectTheMode;
        }

        if (IDialogStates.getState(chatId) == IDialogStates.State.SEARCH) {
            return modeForDefiningTerms(userInput);
        }
        return "Данные введены неверно";
    }

    private String modeForSearchSimilarWords(String userInput) {
        var message = new StringBuilder("Мы не нашли такого определения. Возможно вы имели ввиду одно из этих?\n\n");

        for (var term : IErrorSearch.getSimilarTerms(userInput)) {
            message.append(term + ", ");
        }
        return message.toString();
    }

    private String modeForDefiningTerms(String userInput) {
        ArrayList<TermDefinition> termDefinition = termRepository.getData();
        for (int i = 0; i < termDefinition.size(); i++) {
            if (userInput.equalsIgnoreCase(termDefinition.get(i).getTerm())) {
                return termDefinition.get(i).getTerm() + " - " + termDefinition.get(i).getDefinition(false);
            }
        }
        return modeForSearchSimilarWords(userInput);
    }

    String menuForBotDescription = "Вас приветствует бот по поиску определений по математике. \n" +
            "Он поможет вам найти нужное определение определения.\n";

    String menuToSelectTheMode = "Отправьте:\n" +
            "Поиск - если хотите найти определения \n" +
            "Меню - если хотите вернуться обратно в меню\n" +
            "Термины - если хотите узнать всю базу терминов нашего бота";

    private String mainMenu() {
        return menuForBotDescription + menuToSelectTheMode;
    }

    default String menuForDisplayingTerms() {
        ArrayList<TermDefinition> termDefinition = termRepository.getData();
        var message = new StringBuilder();
        for (int i = 0; i < termDefinition.size(); i++) {
            message.append(termDefinition.get(i).getTerm() + ", ");
        }
        return message.toString();
    }
}