
public class Information {
    private String mainMenu = "Вас приветствует бот по подготовке к экзамену по математике. \n" +
            "Он поможет вам выучить определения.\n";

    private String menu = "Отправьте:\n" +
            "Определения - если хотите выучить определения \n" +
            "Меню - если хотите вернуться обратно в меню\n";

    private String menu2 = "Сейчас Вам будет показано определение.\n" +
            "Напишите понятие, к которому оно относится.";

    private String[] words = new String[]{"Булеан", "Биекция", "Разбиение", "Поле", "Вектор", "Базис", "Форма"};

    private String[] definitions = new String[]{"множество всех подмножеств данного множества.",
            "функция, которая инъективна и сюръективна.",
            "представление множества в виде объединения произвольного количества попарно непересекающихся подмножеств.",
            "коммутативное ассоциативное кольцо, в котором каждый ненулевой элемент обратим.",
            "множество направленных отрезков, имеющих одинаковый модуль и сонаправленных друг другу.",
            "максимальная по включению упорядоченная линейно независимая система.",
            "однородный многочлен от нескольких переменных."};

    private StringBuilder menu1() {
        var message = new StringBuilder("Введите понятие, определение которого вы хотите узнать\n");
        for (int i = 0; i < words.length; i++) {
            message.append(words[i] + "\n");
        }
        return message;
    }

    public String getMainMenu() {
        return mainMenu + menu;
    }

    public String getMenu() {
        return menu;
    }

    public String getMenu1() {
        return menu1().toString();
    }

    public String getMenu2() {
        return menu2;
    }

    public String[] getWords() {
        return words;
    }

    public String[] getDefinitions() {
        return definitions;
    }
}
/*private String mainMenu = "Вас приветствует бот по подготовке к экзамену. \n" +
            "У бота есть 2 режима.\n" +
            "В первом режиме вы можете посмотреть опеределения понятий. А во втором проверить, как хорошо вы их выучили. \n";*/

//"Тест - если во второй \n" +