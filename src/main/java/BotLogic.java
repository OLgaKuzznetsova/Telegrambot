public class BotLogic {
    private DialogStates dialogStates;
    private TermRepository termRepository;

    public BotLogic(DialogStates dialogStates, TermRepository termRepository) {
        this.dialogStates = dialogStates;
        this.termRepository = termRepository;
    }

    public String handleUserInput(String chatId, String userInput) {
        if (userInput.equals("/start")) {
            return mainMenu();
        }
        if (!dialogStates.containsChatId(chatId)) {
            dialogStates.addChatId(chatId);
        }
        if (userInput.equalsIgnoreCase("Термины")) {
            dialogStates.changeState(chatId, DialogStates.State.MENU);
            return termRepository.getAllTerms();
        }
        if (userInput.equalsIgnoreCase("Поиск")) {
            dialogStates.changeState(chatId, DialogStates.State.SEARCH);
            return "Введите термин";
        }
        if (userInput.equalsIgnoreCase("Меню")) {
            dialogStates.changeState(chatId, DialogStates.State.MENU);
            return menuToSelectTheMode;
        }

        if (dialogStates.getState(chatId) == DialogStates.State.SEARCH) {
            return termRepository.gatDefinitionToTerm(userInput);
        }
        return "Данные введены неверно";
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



}