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
            chatStateRepository.addChatIdForHistory(chatId);
            for (var term : termRepository.getTerms()) {
                chatStateRepository.changeStateToTerm(chatId, term, ChatStateRepository.State.NOT_USE);
            }
        }
        if (userInput.equalsIgnoreCase("/terms")) {
            chatStateRepository.changeState(chatId, ChatStateRepository.State.SEARCH);
            return getDescriptionToBot();
        }
        if (userInput.equalsIgnoreCase("/help")) {
            chatStateRepository.changeState(chatId, ChatStateRepository.State.SEARCH);
            return menuToSelectTheMode;
        }

        if (userInput.equalsIgnoreCase("/see") &
                chatStateRepository.getState(chatId) == ChatStateRepository.State.CHECK) {
            return checkUserForKnowledge(chatId);
        }
        if (userInput.equalsIgnoreCase("/check")) {
            chatStateRepository.changeState(chatId, ChatStateRepository.State.CHECK);
            return menuForCheckingKnowledge(chatId);
        }
        if (chatStateRepository.getState(chatId) == ChatStateRepository.State.POLL) {
            return changeStateToAnswer(chatId, userInput);
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

    private String menuForCheckingKnowledge(String chatId) {
        for (var term : termRepository.getTerms()) {
            if (chatStateRepository.getStateToTerm(chatId, term) == ChatStateRepository.State.NOT_USE) {
                chatStateRepository.addLastAnswerByChatId(chatId, term);
                return "Дайте определение этому термину:\n" +
                        term +
                        "\n\n" +
                        "Нажмите:\n" +
                        "/see - чтобы посмотреть определение.";
            }
        }
        for (var term : termRepository.getTerms()) {
            if (chatStateRepository.getStateToTerm(chatId, term) == ChatStateRepository.State.NOT_LEARN) {
                chatStateRepository.addLastAnswerByChatId(chatId, term);
                return "Дайте определение этому термину:\n" +
                        term +
                        "\n\n" +
                        "Нажмите:\n" +
                        "/see - чтобы посмотреть определение.";
            }
        }
        for (var term : termRepository.getTerms()) {
            if (chatStateRepository.getStateToTerm(chatId, term) == ChatStateRepository.State.LEARN) {
                chatStateRepository.addLastAnswerByChatId(chatId, term);
                return "Дайте определение этому термину:\n" +
                        term +
                        "\n\n" +
                        "Нажмите:\n" +
                        "/see - чтобы посмотреть определение.";
            }
        }
        return "";
    }

    private String changeStateToAnswer(String chatId, String userInput) {
        if (userInput.equalsIgnoreCase("/1")) {
            chatStateRepository.changeStateToTerm(chatId, chatStateRepository.getLastAnswer(chatId), ChatStateRepository.State.LEARN);
            chatStateRepository.deleteLastAnswerByChatId(chatId);
            chatStateRepository.changeState(chatId, ChatStateRepository.State.CHECK);
            return menuForCheckingKnowledge(chatId);
        } else if (userInput.equalsIgnoreCase("/2")) {
            chatStateRepository.changeStateToTerm(chatId, chatStateRepository.getLastAnswer(chatId), ChatStateRepository.State.NOT_LEARN);
            chatStateRepository.deleteLastAnswerByChatId(chatId);
            chatStateRepository.changeState(chatId, ChatStateRepository.State.CHECK);
            return menuForCheckingKnowledge(chatId);
        } else {
            return "Данные введены неверно. Нужно ввести /1 или /2";
        }
    }

    private String checkUserForKnowledge(String chatId) {
        chatStateRepository.changeState(chatId, ChatStateRepository.State.POLL);
        var term = chatStateRepository.getLastAnswer(chatId);
        var definition = termRepository.getDefinitionToTerm(term)[1];
        return term + " - " + definition + "\n" +
                "Отправьте:\n" +
                "/1 - да, я знаю его\n" +
                "/2 - нет, я не знаю его";
    }

    private String getAnswerToWrongTerm(String chatId, String userInput) {
        var message = "";
        if ((userInput.equalsIgnoreCase("да")
                | userInput.equalsIgnoreCase("ага")
                | userInput.equalsIgnoreCase("конечно"))
                & chatStateRepository.containsAnswerByChatId(chatId)) {
            message = checkUserInput(chatId, chatStateRepository.getLastAnswer(chatId));
            chatStateRepository.deleteLastAnswerByChatId(chatId);
            return message;
        }
        if ((userInput.equalsIgnoreCase("нет") & chatStateRepository.containsAnswerByChatId(chatId))) {
            return "Извините, тогда у нас нет нужного вам термина.\n" +
                    "Попробйте найти другой термин или введите /terms, чтобы узнать какие термины есть в базе нашего бота.";
        }
        return checkUserInput(chatId, userInput);

    }

    private String checkUserInput(String chatId, String userInput) {
        if (userInput.matches("[а-яА-ЯёЁ ]+")) {
            return getDefinitionToTerm(chatId, userInput);
        }
        return "Данные введены неверно. Если вы забыли как пользоваться ботом введите /help";
    }

    private String getDefinitionToTerm(String chatId, String userInput) {
        var res = termRepository.getDefinitionToTerm(userInput);
        if (res == null) {
            chatStateRepository.changeState(chatId, ChatStateRepository.State.ERROR);
            var similarTerms = termRepository.getSimilarTerms(userInput);
            if (similarTerms.size() == 1) {
                chatStateRepository.addLastAnswerByChatId(chatId, similarTerms.get(0));
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
            "/terms - посмотреть какие определения есть у бота\n" +
            "/check - проверить как хорошо вы знаете определения терминов. Проверка осущестляется в виде флеш-карт.";

    private String getDescriptionToBot() {


        return "Бот знает определения по дискретной математике 2 курса в институте.\n" +
                "Он знает термины по таким темам как:\n" +
                "   - теория множеств\n" +
                "   - графы\n" +
                "   - комбинаторика\n" +
                "   - булевы функции\n" +
                "   - общая алгебра\n\n" +
                "Например, вы можете ввести такие термины как: биекция, граф, отношение эквивалентности и другие.";
    }

    private String mainMenu() {
        return menuForBotDescription + menuToSelectTheMode;
    }
}