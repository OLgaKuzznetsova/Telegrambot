import java.util.LinkedList;

public class BotLogic {
    private ChatStateRepository chatStateRepository;
    private TermRepository termRepository;

    public BotLogic(ChatStateRepository chatStateRepository, TermRepository termRepository) {
        this.chatStateRepository = chatStateRepository;
        this.termRepository = termRepository;
    }

    public Response handleUserInput(long chatId, String userInput) {

        if (userInput.equals("/start")) {
            return new Response(mainMenu(), null);
        }
        if (!chatStateRepository.containsChatId(chatId)) {
            chatStateRepository.addChatId(chatId);
            chatStateRepository.addChatIdForHistory(chatId);
            for (var term : termRepository.getTerms()) {
                chatStateRepository.changeStateForTerm(chatId, term, ChatStateRepository.TermState.UNUSED);
            }
        }
        if (userInput.equalsIgnoreCase("/terms")) {
            chatStateRepository.changeState(chatId, ChatStateRepository.UserState.SEARCH);

            return new Response(getDescriptionToBot(), null);
        }
        if (userInput.equalsIgnoreCase("/help")) {
            chatStateRepository.changeState(chatId, ChatStateRepository.UserState.SEARCH);
            return new Response(menuToSelectTheMode, null);
        }

        if (userInput.equalsIgnoreCase("/see") &
                chatStateRepository.getState(chatId) == ChatStateRepository.UserState.CHECK) {
            return checkUserForKnowledge(chatId);
        }
        if (userInput.equalsIgnoreCase("/check")) {
            chatStateRepository.changeState(chatId, ChatStateRepository.UserState.CHECK);
            return new Response(menuForCheckingKnowledge(chatId), null);
        }
        if (chatStateRepository.getState(chatId) == ChatStateRepository.UserState.POLL) {
            return new Response(changeStateToAnswer(chatId, userInput), null);
        }

        if (chatStateRepository.getState(chatId) == ChatStateRepository.UserState.SEARCH) {
            return new Response(checkUserInput(chatId, userInput), null);
        }


        if (chatStateRepository.getState(chatId) == ChatStateRepository.UserState.ERROR) {
            chatStateRepository.changeState(chatId, ChatStateRepository.UserState.SEARCH);
            return new Response(getAnswerToWrongTerm(chatId, userInput), null);
        }

        return new Response("Данные введены неверно", null);
    }

    private String menuForCheckingKnowledge(long chatId) {
        for (var term : termRepository.getTerms()) {
            if (chatStateRepository.getStateForTerm(chatId, term) == ChatStateRepository.TermState.UNUSED) {
                chatStateRepository.addLastAnswerByChatId(chatId, term);
                return "Дайте определение этому термину:\n" +
                        term +
                        "\n\n" +
                        "Нажмите:\n" +
                        "/see - чтобы посмотреть определение.";
            }
        }
        for (var term : termRepository.getTerms()) {
            if (chatStateRepository.getStateForTerm(chatId, term) == ChatStateRepository.TermState.UNLEARNED) {
                chatStateRepository.addLastAnswerByChatId(chatId, term);
                return "Дайте определение этому термину:\n" +
                        term +
                        "\n\n" +
                        "Нажмите:\n" +
                        "/see - чтобы посмотреть определение.";
            }
        }
        for (var term : termRepository.getTerms()) {
            if (chatStateRepository.getStateForTerm(chatId, term) == ChatStateRepository.TermState.LEARNED) {
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

    private String changeStateToAnswer(long chatId, String userInput) {
        if (userInput.equalsIgnoreCase("Да")) {
            chatStateRepository.changeStateForTerm(chatId, chatStateRepository.getLastAnswer(chatId), ChatStateRepository.TermState.LEARNED);
            chatStateRepository.deleteLastAnswerByChatId(chatId);
            chatStateRepository.changeState(chatId, ChatStateRepository.UserState.CHECK);
            return menuForCheckingKnowledge(chatId);
        } else if (userInput.equalsIgnoreCase("Нет")) {
            chatStateRepository.changeStateForTerm(chatId, chatStateRepository.getLastAnswer(chatId), ChatStateRepository.TermState.UNLEARNED);
            chatStateRepository.deleteLastAnswerByChatId(chatId);
            chatStateRepository.changeState(chatId, ChatStateRepository.UserState.CHECK);
            return menuForCheckingKnowledge(chatId);
        } else {
            return "Данные введены неверно. Нужно ввести да или нет";
        }
    }

    private Response checkUserForKnowledge(long chatId) {
        chatStateRepository.changeState(chatId, ChatStateRepository.UserState.POLL);
        var term = chatStateRepository.getLastAnswer(chatId);
        var definition = termRepository.getDefinitionToTerm(term)[1];
        var answer = term + " - " + definition + "\n" +
                "Вы знали это определение?\n";
        var keyboardButtons = new LinkedList<KeyboardButton>();
        var buttonYes = new KeyboardButton();
        buttonYes.setText("да");
        buttonYes.setCallbackData("да");
        keyboardButtons.add(buttonYes);
        var buttonNo = new KeyboardButton();
        buttonNo.setText("нет");
        buttonNo.setCallbackData("нет");
        keyboardButtons.add(buttonNo);
        return new Response(answer, keyboardButtons);

    }

    private String getAnswerToWrongTerm(long chatId, String userInput) {
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

    private String checkUserInput(long chatId, String userInput) {
        if (userInput.matches("[а-яА-ЯёЁ ]+")) {
            return getDefinitionToTerm(chatId, userInput);
        }
        return "Данные введены неверно. Если вы забыли как пользоваться ботом введите /help";
    }

    private String getDefinitionToTerm(long chatId, String userInput) {
        var res = termRepository.getDefinitionToTerm(userInput);
        if (res == null) {
            chatStateRepository.changeState(chatId, ChatStateRepository.UserState.ERROR);
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