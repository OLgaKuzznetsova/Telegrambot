import java.util.ArrayList;

public class Information {
    private String mainMenu = "Вас приветствует бот по подготовке к экзамену по математике. \n" +
            "Он поможет вам выучить определения.\n";

    private ArrayList<Data> data = new ArrayList<>();

    public void Information() {
        addData("Булеан", "множество всех подмножеств данного множества.");
        addData("Биекция", "функция, которая инъективна и сюръективна.");
        addData("Разбиение", "представление множества в виде объединения произвольного количества попарно непересекающихся подмножеств.");
        addData("Поле", "коммутативное ассоциативное кольцо, в котором каждый ненулевой элемент обратим.");
        addData("Вектор", "множество направленных отрезков, имеющих одинаковый модуль и сонаправленных друг другу.");
        addData("Базис", "максимальная по включению упорядоченная линейно независимая система.");
        addData("Форма", "однородный многочлен от нескольких переменных.");
    }

    private void addData(String term, String definition) {
        Data classData = new Data();
        classData.setValues(term, definition);
        data.add(classData);
    }

    private String menu = "Отправьте:\n" +
            "Определения - если хотите выучить определения \n" +
            "Тест - если во второй \n" +
            "Меню - если хотите вернуться обратно в меню\n";

    private String menu2 = "Сейчас Вам будет показано определение.\n" +
            "Напишите понятие, к которому оно относится.";

    private StringBuilder menu1() {
        var message = new StringBuilder("Введите понятие, определение которого вы хотите узнать\n");
        for (int i = 0; i < data.size(); i++) {
            message.append(data.get(i).getTerm() + "\n");
        }
        return message;
    }

    public ArrayList<Data> getData() {
        Information();
        return data;
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
}
