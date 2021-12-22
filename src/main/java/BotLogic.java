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
            for (var term : termRepository.getTerms()) {
                chatStateRepository.changeStateForTerm(chatId, term, ChatStateRepository.TermState.UNUSED);
            }
            return new Response(mainMenu(), null);
        }
        if (!chatStateRepository.containsChatId(chatId)) {
            chatStateRepository.addChatId(chatId);
            chatStateRepository.addChatIdForHistory(chatId);
            for (var term : termRepository.getTerms()) {
                chatStateRepository.changeStateForTerm(chatId, term, ChatStateRepository.TermState.UNUSED);
            }
        }

        if (userInput.equalsIgnoreCase("/find")) {
            chatStateRepository.changeState(chatId, ChatStateRepository.UserState.SEARCH);
            return new Response("Введите термин, определение которого вы хотите узнать", null);
        }
        if (userInput.equalsIgnoreCase("/terms")) {
            chatStateRepository.changeState(chatId, ChatStateRepository.UserState.SEARCH);

            return new Response(getDescriptionToBot(), null);
        }
        if (userInput.equalsIgnoreCase("/help")) {
            chatStateRepository.changeState(chatId, ChatStateRepository.UserState.SEARCH);
            return new Response(menuToSelectTheMode, null);
        }

        if (userInput.equalsIgnoreCase("/see") &&
                chatStateRepository.getState(chatId) == ChatStateRepository.UserState.CHECK) {
            chatStateRepository.changeState(chatId, ChatStateRepository.UserState.POLL);
            return checkUserForKnowledge(chatId);
        }
        if (userInput.equalsIgnoreCase("/see") &&
                chatStateRepository.getState(chatId) == ChatStateRepository.UserState.REPEAT) {
            chatStateRepository.changeState(chatId, ChatStateRepository.UserState.REDIRECT);
            return checkUserForKnowledge(chatId);
        }

        if (userInput.equalsIgnoreCase("/check")) {
            chatStateRepository.changeState(chatId, ChatStateRepository.UserState.CHECK);
            return menuForCheckingKnowledge(chatId);
        }
        if (userInput.equalsIgnoreCase("/repeat")) {
            chatStateRepository.changeState(chatId, ChatStateRepository.UserState.REPEAT);
            return menuForRepetitionOfTerms(chatId);
        }
        if (chatStateRepository.getState(chatId) == ChatStateRepository.UserState.POLL
                || chatStateRepository.getState(chatId) == ChatStateRepository.UserState.REDIRECT) {
            return changeStateToAnswer(chatId, userInput);
        }

        if (chatStateRepository.getState(chatId) == ChatStateRepository.UserState.SEARCH) {
            return checkUserInput(chatId, userInput);
        }


        if (chatStateRepository.getState(chatId) == ChatStateRepository.UserState.ERROR) {
            chatStateRepository.changeState(chatId, ChatStateRepository.UserState.SEARCH);
            return getAnswerToWrongTerm(chatId, userInput);
        }

        return new Response("Данные введены неверно.Возможно, вы находитесь не в том режиме.", null);
    }

    private Response menuForCheckingKnowledge(long chatId) {
        for (var term : termRepository.getTerms()) {
            if (chatStateRepository.getStateForTerm(chatId, term) == ChatStateRepository.TermState.UNUSED) {
                chatStateRepository.lastUsedTerm(chatId, term);
                return menuForFlashcards(term);

            }
        }
        var answer = "Поздравляю! Вы узнали всю базу определений нашего бота.\n" +
                      "Хотите повторить всё снова?";
        var keyboardButtons = new LinkedList<KeyboardButton>();
        var buttonRepeat = new KeyboardButton("повторить определения", "/repeat");
        keyboardButtons.add(buttonRepeat);
        return new Response(answer, keyboardButtons);
    }

    private Response menuForFlashcards(String term){
        var answer = "Дайте определение этому термину:\n" +
                term +
                "\n\n";
        var keyboardButtons = new LinkedList<KeyboardButton>();
        var buttonSee = new KeyboardButton("посмотреть определение", "/see");
        keyboardButtons.add(buttonSee);
        return new Response(answer, keyboardButtons);
    }

    private Response menuForRepetitionOfTerms(long chatId) {
        var keyboardButtons = new LinkedList<KeyboardButton>();
        var buttonCheck = new KeyboardButton("изучение терминов", "/check");
        keyboardButtons.add(buttonCheck);
        for (var term : termRepository.getTerms()) {
            if (chatStateRepository.getStateForTerm(chatId, term) == ChatStateRepository.TermState.UNLEARNED) {
                chatStateRepository.lastUsedTerm(chatId, term);
                return menuForFlashcards(term);
            }
        }
        for (var term : termRepository.getTerms()) {
            if (chatStateRepository.getStateForTerm(chatId, term) == ChatStateRepository.TermState.LEARNED) {
                return new Response("Вы запомнили термины", keyboardButtons);
            }
        }
        return new Response("Вы еще не выучили ни одного термина", keyboardButtons);
    }

    private Response changeStateToAnswer(long chatId, String userInput) {
        if (userInput.equalsIgnoreCase("Да")) {
            chatStateRepository.changeStateForTerm(chatId, chatStateRepository.getLastAnswer(chatId), ChatStateRepository.TermState.LEARNED);
            return changeState(chatId);
        } else if (userInput.equalsIgnoreCase("Нет")) {
            chatStateRepository.changeStateForTerm(chatId, chatStateRepository.getLastAnswer(chatId), ChatStateRepository.TermState.UNLEARNED);
            return changeState(chatId);
        } else {
            return new Response("Данные введены неверно. Нужно ввести да или нет", null);
        }
    }

    private Response changeState(Long chatId){
        chatStateRepository.deleteLastAnswerByChatId(chatId);
        if (chatStateRepository.getState(chatId) == ChatStateRepository.UserState.POLL) {
            chatStateRepository.changeState(chatId, ChatStateRepository.UserState.CHECK);
            return menuForCheckingKnowledge(chatId);
        }
        chatStateRepository.changeState(chatId, ChatStateRepository.UserState.REPEAT);
        return  menuForRepetitionOfTerms(chatId);
    }

    private Response checkUserForKnowledge(long chatId) {

        var term = chatStateRepository.getLastAnswer(chatId);
        var definition = termRepository.getDefinitionToTerm(term)[1];
        var answer = term + " - " + definition + "\n" +
                "Вы знали это определение?\n";
        var keyboardButtons = new LinkedList<KeyboardButton>();
        var buttonYes = new KeyboardButton("да", "да");
        keyboardButtons.add(buttonYes);
        var buttonNo = new KeyboardButton("нет", "нет");
        keyboardButtons.add(buttonNo);
        return new Response(answer, keyboardButtons);

    }

    private Response getAnswerToWrongTerm(long chatId, String userInput) {
        if (userInput.equalsIgnoreCase("да")
                && chatStateRepository.containsAnswerByChatId(chatId)) {
            var message = checkUserInput(chatId, chatStateRepository.getLastAnswer(chatId));
            chatStateRepository.deleteLastAnswerByChatId(chatId);
            return message;
        }
        if ((userInput.equalsIgnoreCase("нет") && chatStateRepository.containsAnswerByChatId(chatId))) {
            return new Response("Извините, тогда у нас нет нужного вам термина.\n" +
                    "Попробйте найти другой термин или введите /terms, чтобы узнать какие термины есть в базе нашего бота.", null);
        }
        return checkUserInput(chatId, userInput);

    }

    private Response checkUserInput(long chatId, String userInput) {
        if (userInput.matches("[а-яА-ЯёЁ ]+")) {
            return getDefinitionToTerm(chatId, userInput);
        }
        return new Response("Данные введены неверно. Если вы забыли как пользоваться ботом введите /help", null);
    }

    private Response getDefinitionToTerm(long chatId, String userInput) {
        var res = termRepository.getDefinitionToTerm(userInput);
        if (res == null) {
            var keyboardButtons = new LinkedList<KeyboardButton>();
            chatStateRepository.changeState(chatId, ChatStateRepository.UserState.ERROR);
            var similarTerms = termRepository.getSimilarTerms(userInput);
            String message = "Мы не нашли такого определения. Вы имели ввиду это?\n\n" + similarTerms.get(0);
            for (var i = 1; i < similarTerms.size(); i++) {
                message = message + ", " + similarTerms.get(i);

            }
            if (similarTerms.size() == 1) {
                chatStateRepository.lastUsedTerm(chatId, similarTerms.get(0));
                var buttonYes = new KeyboardButton("да", "да");
                keyboardButtons.add(buttonYes);
                var buttonNo = new KeyboardButton("нет", "нет");
                keyboardButtons.add(buttonNo);
                return new Response(message, keyboardButtons);
            }
            return new Response(message, null);
        } else {
            return new Response(res[0] + " - " + res[1], null);
        }
    }


    private String menuForBotDescription = "Вас приветствует бот по поиску определений по дискретной математике. \n" +
            "Он поможет вам найти нужное определение.\n" +
            "Введите термин, чтобы узнать его определение\n";

    private String menuToSelectTheMode = "Отправьте:\n" +
            "/terms - посмотреть какие определения есть у бота\n" +
            "/check - проверить как хорошо вы знаете определения терминов. Проверка осущестляется в виде флеш-карт.\n" +
            "/repeat - повторить термины, которые вы не знали\n" +
            "/start - перезапустить бота и заново начать обучение";

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