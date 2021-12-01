public class BotLogic {
    private ChatStateRepository chatStateRepository;
    private TermRepository termRepository;

    public BotLogic(ChatStateRepository chatStateRepository, TermRepository termRepository) {
        this.chatStateRepository = chatStateRepository;
        this.termRepository = termRepository;
    }

    public String handleUserInput(String chatId, String userInput) {
        if (userInput.equals("/start")) {
            return mainMenu();
        }
        if (!chatStateRepository.containsChatId(chatId)) {
            chatStateRepository.addChatId(chatId);
        }
        if (userInput.equalsIgnoreCase("/terms")) {
            chatStateRepository.changeState(chatId, ChatStateRepository.State.SEARCH);
            return termRepository.getAllTerms();
        }
        if (userInput.equalsIgnoreCase("/help")) {
            chatStateRepository.changeState(chatId, ChatStateRepository.State.SEARCH);
            return menuToSelectTheMode;
        }
        if (chatStateRepository.getState(chatId) == ChatStateRepository.State.SEARCH) {
            return checkUserInput(chatId, userInput);
        }
        if (chatStateRepository.getState(chatId) == ChatStateRepository.State.ERROR) {
            chatStateRepository.changeState(chatId, ChatStateRepository.State.SEARCH);
            return get(chatId,userInput);
        }

        return "Данные введены неверно";
    }
    private String get (String chatId, String userInput){
        var message = "";
        if (userInput.equalsIgnoreCase("да") & chatStateRepository.containsAnswerByChatId(chatId)){
            message = checkUserInput(chatId, chatStateRepository.getIntendedAnswer(chatId));
            chatStateRepository.deleteAnswerByChatId(chatId);
        } else {
            message = checkUserInput(chatId, userInput);
        }
        return message;
    }

    private String checkUserInput(String chatId, String userInput){
        if (userInput == "написать код для проверки содержимого"){
            return "Данные введены неверно";
        }
        return getDefinitionToTerm(chatId, userInput);
    }

    private String getDefinitionToTerm(String chatId, String userInput){
        var res = termRepository.getDefinitionToTerm(userInput);
        if (res == null){
            chatStateRepository.changeState(chatId, ChatStateRepository.State.ERROR);
            var similarTerms = termRepository.getSimilarTerms(userInput);
            if (similarTerms.size() == 1){
                chatStateRepository.addIntendedAnswerByChatId(chatId, similarTerms.get(0));
            }
            String message = "Мы не нашли такого определения. Вы имели ввиду это?\n\n" + similarTerms.get(0);
            for (var i = 1; i < similarTerms.size(); i++) {
                message = message + ", " + similarTerms.get(i);

            }
            return message;
        }
        else{
            return res[0] + " - " + res[1];
        }
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