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
            return getDescriptionToBot();
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
            return getAnswerToWrongTerm(chatId, userInput);
        }

        return "Данные введены неверно";
    }

    private String getAnswerToWrongTerm(String chatId, String userInput) {
        var message = "";
        if (userInput.equalsIgnoreCase("да") & chatStateRepository.containsAnswerByChatId(chatId)) {
            message = checkUserInput(chatId, chatStateRepository.getIntendedAnswer(chatId));
            chatStateRepository.deleteAnswerByChatId(chatId);
        } else {
            message = checkUserInput(chatId, userInput);
        }
        return message;
    }

    private String checkUserInput(String chatId, String userInput) {
        if (userInput == "написать код для проверки содержимого") {
            return "Данные введены неверно";
        }
        return getDefinitionToTerm(chatId, userInput);
    }

    private String getDefinitionToTerm(String chatId, String userInput) {
        var res = termRepository.getDefinitionToTerm(userInput);
        if (res == null) {
            chatStateRepository.changeState(chatId, ChatStateRepository.State.ERROR);
            var similarTerms = termRepository.getSimilarTerms(userInput);
            if (similarTerms.size() == 1) {
                chatStateRepository.addIntendedAnswerByChatId(chatId, similarTerms.get(0));
            }
            String message = "Мы не нашли такого определения. Вы имели ввиду это?\n\n" + similarTerms.get(0);
            for (var i = 1; i < similarTerms.size(); i++) {
                message = message + ", " + similarTerms.get(i);

            }
            return message;
        } else {
            return res[0] + " - " + res[1];
        }
    }


    private String menuForBotDescription = "Вас приветствует бот по поиску определений по дискретной математике. \n" +
            "Он поможет вам найти нужное определение.\n" +
            "Введите термин, чтобы узнать его определение\n";

    private String menuToSelectTheMode = "Отправьте:\n" +
            "/help - вернуться меню\n" +
            "/terms - посмотреть какие определения есть у бота";

    private String getDescriptionToBot() {
        return "Бот знает определения по дискретной математике 2 курса в институте.\n" +
                "Он знает термины по таким темам как:\n" +
                "   - теория множеств\n" +
                "   - графы\n" +
                "   - О-символика\n" +
                "   - булевы функции\n\n" +
                "Например, вы можете ввести такие термины как: биекция, граф, отношение эквивалентности и другие.";
    }

    private String mainMenu() {
        return menuForBotDescription + menuToSelectTheMode;
    }
}