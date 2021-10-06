import java.util.ArrayList;

public class Information {
    private String mainMenu = "Вас приветствует бот по подготовке к экзамену по математике. \n" +
            "Он поможет вам выучить определения.\n";
    private Data classData;
    private ArrayList<Data> data = new ArrayList<Data>();
    private void createData(){
        addData("Булеан", "множество всех подмножеств данного множества.");
        addData("Биекция", "функция, которая инъективна и сюръективна.");
        addData("Разбиение",  "представление множества в виде объединения произвольного количества попарно непересекающихся подмножеств.");
        addData("Поле", "коммутативное ассоциативное кольцо, в котором каждый ненулевой элемент обратим.");
        addData("Вектор",  "множество направленных отрезков, имеющих одинаковый модуль и сонаправленных друг другу.");
        addData("Базис", "максимальная по включению упорядоченная линейно независимая система.");
        addData("Форма",  "однородный многочлен от нескольких переменных.");
    }
    private void addData(String term, String definition){
        classData.setValues(term, definition);
        data.add(classData);
    }
    private String menu = "Отправьте:\n" +
            "Определения - если хотите выучить определения \n" +
            "Меню - если хотите вернуться обратно в меню\n";

    private String menu2 = "Сейчас Вам будет показано определение.\n" +
            "Напишите понятие, к которому оно относится.";



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