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
        if (userInput.equalsIgnoreCase("/terms")) {
            dialogStates.changeState(chatId, DialogStates.State.SEARCH);
            return termRepository.getAllTerms();
        }
        if (userInput.equalsIgnoreCase("/help")) {
            dialogStates.changeState(chatId, DialogStates.State.SEARCH);
            return menuToSelectTheMode;
        }
        if (dialogStates.getState(chatId) == DialogStates.State.SEARCH) {
            return termRepository.getDefinitionToTerm(userInput);
        }
        return "Данные введены неверно";
    }


    private String menuForBotDescription = "Вас приветствует бот по поиску определений по математике. \n" +
            "Он поможет вам найти нужное определение.\n";

    private String menuToSelectTheMode = "Отправьте:\n" +
            "термин - если хотите узнать его определение\n"+
            "/help - если хотите вернуться обратно в меню\n" +
            "/terms - если хотите узнать всю базу терминов нашего бота";

    private String mainMenu() {
        return menuForBotDescription + menuToSelectTheMode;
    }



}