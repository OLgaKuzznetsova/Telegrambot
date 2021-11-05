import java.util.ArrayList;

public class TermRepository {
    private ArrayList<TermDefinition> data = new ArrayList<>();

    public TermRepository() {
        addData("Булеан", "множество всех подмножеств данного множества.");
        addData("Биекция", "функция, которая инъективна и сюръективна.");
        addData("Разбиение", "представление множества в виде объединения произвольного количества попарно непересекающихся подмножеств.");
        addData("Поле", "коммутативное ассоциативное кольцо, в котором каждый ненулевой элемент обратим.");
        addData("Вектор", "множество направленных отрезков, имеющих одинаковый модуль и сонаправленных друг другу.");
        addData("Базис", "максимальная по включению упорядоченная линейно независимая система.");
        addData("Форма", "однородный многочлен от нескольких переменных.");
    }

    private void addData(String term, String definition) {
        TermDefinition classTermDefinition = new TermDefinition();
        classTermDefinition.setValues(term, definition);
        data.add(classTermDefinition);
    }

    public ArrayList<TermDefinition> getData() {
        return data;
    }

}
